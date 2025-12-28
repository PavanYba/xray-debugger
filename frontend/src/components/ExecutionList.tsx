import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { XRayExecution } from '../types';
import { api } from '../services/api';

/**
 * ExecutionList component - displays all pipeline executions
 */
const ExecutionList: React.FC = () => {
  const [executions, setExecutions] = useState<XRayExecution[]>([]);
  const [loading, setLoading] = useState(true);
  const [runningDemo, setRunningDemo] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    void loadExecutions();
  }, []);

  const loadExecutions = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await api.getExecutions();
      setExecutions(data);
    } catch (err) {
      setError('Failed to load executions. Make sure the backend is running.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const runDemo = async () => {
    try {
      setRunningDemo(true);
      setError(null);
      const response = await api.runDemo();
      
      if (response.success) {
        // Reload executions to show new one
        await loadExecutions();
        // Navigate to the new execution
        navigate(`/execution/${response.executionId}`);
      } else {
        setError(response.message);
      }
    } catch (err) {
      setError('Failed to run demo. Make sure the backend is running.');
      console.error(err);
    } finally {
      setRunningDemo(false);
    }
  };

  const deleteAll = async () => {
    if (!window.confirm('Delete all executions? This cannot be undone.')) {
      return;
    }
    
    try {
      await api.deleteAllExecutions();
      await loadExecutions();
    } catch (err) {
      setError('Failed to delete executions');
      console.error(err);
    }
  };

  const formatDuration = (execution: XRayExecution): string => {
    if (!execution.endTime) return 'In progress';
    
    const start = new Date(execution.startTime).getTime();
    const end = new Date(execution.endTime).getTime();
    const durationMs = end - start;
    
    if (durationMs < 1000) return `${durationMs}ms`;
    return `${(durationMs / 1000).toFixed(2)}s`;
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="loading-spinner"></div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8 max-w-7xl">
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-4xl font-bold text-gray-900 mb-2">
          X-Ray Debugger
        </h1>
        <p className="text-gray-600">
          Multi-step decision pipeline visualization and debugging
        </p>
      </div>

      {/* Error message */}
      {error && (
        <div className="mb-6 p-4 bg-red-50 border border-red-200 rounded-lg">
          <p className="text-red-800">{error}</p>
        </div>
      )}

      {/* Actions */}
      <div className="mb-6 flex gap-4">
        <button
          onClick={runDemo}
          disabled={runningDemo}
          className="btn-primary disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {runningDemo ? 'Running Demo...' : '▶ Run Demo'}
        </button>
        
        <button
          onClick={loadExecutions}
          className="btn-secondary"
        >
          🔄 Refresh
        </button>

        {executions.length > 0 && (
          <button
            onClick={deleteAll}
            className="btn-secondary text-red-600 hover:text-red-700"
          >
            🗑 Delete All
          </button>
        )}
      </div>

      {/* Execution list */}
      {executions.length === 0 ? (
        <div className="card text-center py-12">
          <p className="text-gray-500 text-lg mb-4">
            No executions yet. Run the demo to get started!
          </p>
        </div>
      ) : (
        <div className="space-y-4">
          {executions.map((execution) => (
            <div
              key={execution.executionId}
              onClick={() => navigate(`/execution/${execution.executionId}`)}
              className="card hover:shadow-lg transition-shadow cursor-pointer"
            >
              <div className="flex items-center justify-between">
                <div className="flex-1">
                  <div className="flex items-center gap-3 mb-2">
                    <h3 className="text-lg font-semibold text-gray-900">
                      {execution.executionId}
                    </h3>
                    <span className={
                      execution.status === 'COMPLETED' 
                        ? 'badge-success' 
                        : 'badge-error'
                    }>
                      {execution.status}
                    </span>
                  </div>
                  
                  <div className="flex items-center gap-6 text-sm text-gray-600">
                    <span>
                      📅 {new Date(execution.startTime).toLocaleString()}
                    </span>
                    <span>
                      ⏱ {formatDuration(execution)}
                    </span>
                    <span>
                      📋 {execution.steps.length} steps
                    </span>
                  </div>
                </div>
                
                <div className="text-gray-400">
                  →
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ExecutionList;
