import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { XRayExecution } from '../types';
import { api } from '../services/api';
import StepCard from './StepCard';

/**
 * ExecutionDetail component - shows detailed step-by-step execution trail
 */
const ExecutionDetail: React.FC = () => {
  const { executionId } = useParams<{ executionId: string }>();
  const [execution, setExecution] = useState<XRayExecution | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    void loadExecution();
  }, [executionId]);

  const loadExecution = async () => {
    if (!executionId) return;
    
    try {
      setLoading(true);
      setError(null);
      const data = await api.getExecution(executionId);
      setExecution(data);
    } catch (err) {
      setError('Failed to load execution details');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const formatDuration = (): string => {
    if (!execution || !execution.endTime) return 'In progress';
    
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

  if (error || !execution) {
    return (
      <div className="container mx-auto px-4 py-8 max-w-7xl">
        <div className="card bg-red-50 border-red-200">
          <p className="text-red-800">{error || 'Execution not found'}</p>
          <button
            onClick={() => navigate('/')}
            className="btn-secondary mt-4"
          >
            ← Back to List
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8 max-w-7xl">
      {/* Header */}
      <div className="mb-6">
        <button
          onClick={() => navigate('/')}
          className="text-blue-600 hover:text-blue-700 mb-4 inline-flex items-center gap-2"
        >
          ← Back to Executions
        </button>
        
        <div className="card">
          <div className="flex items-center justify-between mb-4">
            <h1 className="text-3xl font-bold text-gray-900">
              {execution.executionId}
            </h1>
            <span className={
              execution.status === 'COMPLETED' 
                ? 'badge-success text-base px-4 py-1' 
                : 'badge-error text-base px-4 py-1'
            }>
              {execution.status}
            </span>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-sm">
            <div>
              <span className="text-gray-600 font-medium">Started:</span>
              <p className="text-gray-900">
                {new Date(execution.startTime).toLocaleString()}
              </p>
            </div>
            
            <div>
              <span className="text-gray-600 font-medium">Duration:</span>
              <p className="text-gray-900">{formatDuration()}</p>
            </div>
            
            <div>
              <span className="text-gray-600 font-medium">Steps:</span>
              <p className="text-gray-900">{execution.steps.length}</p>
            </div>
          </div>

          {execution.context && (
            <details className="mt-4">
              <summary className="cursor-pointer text-sm font-medium text-gray-700 hover:text-gray-900">
                View Execution Context
              </summary>
              <pre className="json-container mt-2 text-xs">
                {JSON.stringify(execution.context, null, 2)}
              </pre>
            </details>
          )}
        </div>
      </div>

      {/* Timeline - Visual representation */}
      <div className="mb-6 card">
        <h2 className="text-xl font-semibold mb-4">Pipeline Timeline</h2>
        <div className="flex items-center gap-2">
          {execution.steps.map((step, index) => (
            <React.Fragment key={step.stepId}>
              <div className="flex flex-col items-center flex-1">
                <div className="w-10 h-10 rounded-full bg-blue-600 text-white flex items-center justify-center font-semibold">
                  {index + 1}
                </div>
                <p className="text-xs text-gray-600 mt-1 text-center">
                  {step.stepName.replace(/_/g, ' ')}
                </p>
              </div>
              {index < execution.steps.length - 1 && (
                <div className="flex-1 h-1 bg-blue-200" />
              )}
            </React.Fragment>
          ))}
        </div>
      </div>

      {/* Steps detail */}
      <div className="space-y-6">
        <h2 className="text-2xl font-bold text-gray-900">
          Step-by-Step Execution Trail
        </h2>
        
        {execution.steps.map((step, index) => (
          <StepCard 
            key={step.stepId} 
            step={step} 
            stepNumber={index + 1}
          />
        ))}
      </div>
    </div>
  );
};

export default ExecutionDetail;
