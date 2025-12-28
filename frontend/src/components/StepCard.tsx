import React, { useState } from 'react';
import { XRayStep, CandidateEvaluation } from '../types';

interface StepCardProps {
  step: XRayStep;
  stepNumber: number;
}

const StepCard: React.FC<StepCardProps> = ({ step, stepNumber }) => {
  const [showInput, setShowInput] = useState(false);
  const [showOutput, setShowOutput] = useState(false);
  const [showMetadata, setShowMetadata] = useState(step.stepName === 'apply_filters');

  const isFilterStep = step.stepName === 'apply_filters';
  const evaluations = isFilterStep && step.metadata?.evaluations 
    ? (step.metadata.evaluations as CandidateEvaluation[])
    : null;

  return (
    <div className="card border-l-4 border-blue-500">
      {/* Step header */}
      <div className="flex items-start gap-4 mb-4">
        <div className="flex-shrink-0 w-12 h-12 rounded-full bg-blue-600 text-white flex items-center justify-center font-bold text-lg">
          {stepNumber}
        </div>
        
        <div className="flex-1">
          <h3 className="text-2xl font-semibold text-gray-900 mb-1">
            {step.stepName.split('_').map(word => 
              word.charAt(0).toUpperCase() + word.slice(1)
            ).join(' ')}
          </h3>
          
          <p className="text-sm text-gray-500">
            {new Date(step.timestamp).toLocaleString()}
          </p>
        </div>
      </div>

      {/* Reasoning - Most important part */}
      <div className="bg-blue-50 border-l-4 border-blue-500 p-4 mb-4">
        <p className="text-sm font-medium text-gray-700 mb-1">💡 Reasoning:</p>
        <p className="text-gray-900">{step.reasoning}</p>
      </div>

      {/* Input/Output toggles */}
      <div className="grid grid-cols-2 gap-4 mb-4">
        <button
          onClick={() => setShowInput(!showInput)}
          className="text-left p-3 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
        >
          <span className="text-sm font-medium text-gray-700">
            {showInput ? '▼' : '▶'} Input
          </span>
        </button>
        
        <button
          onClick={() => setShowOutput(!showOutput)}
          className="text-left p-3 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
        >
          <span className="text-sm font-medium text-gray-700">
            {showOutput ? '▼' : '▶'} Output
          </span>
        </button>
      </div>

      {/* Input detail */}
      {showInput && (
        <div className="mb-4">
          <pre className="json-container text-xs">
            {JSON.stringify(step.input, null, 2)}
          </pre>
        </div>
      )}

      {/* Output detail */}
      {showOutput && (
        <div className="mb-4">
          <pre className="json-container text-xs">
            {JSON.stringify(step.output, null, 2)}
          </pre>
        </div>
      )}

      {/* Special rendering for filter step metadata */}
      {isFilterStep && step.metadata && (
        <div className="mt-4">
          <button
            onClick={() => setShowMetadata(!showMetadata)}
            className="text-left p-3 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors w-full mb-3"
          >
            <span className="text-sm font-medium text-gray-700">
              {showMetadata ? '▼' : '▶'} Filter Evaluations ({evaluations?.length || 0} candidates)
            </span>
          </button>

          {showMetadata && evaluations && (
            <div className="space-y-3">
              {/* Filter criteria summary */}
              {step.metadata.filters_applied && (
                <div className="bg-gray-50 p-4 rounded-lg">
                  <p className="font-medium text-gray-700 mb-2">Filters Applied:</p>
                  <div className="grid grid-cols-1 md:grid-cols-3 gap-3 text-sm">
                    {Object.entries(step.metadata.filters_applied).map(([key, value]: [string, any]) => (
                      <div key={key}>
                        <p className="font-medium text-gray-600">
                          {key.split('_').join(' ')}:
                        </p>
                        <p className="text-gray-900">{value.rule || JSON.stringify(value)}</p>
                      </div>
                    ))}
                  </div>
                </div>
              )}

              {/* Candidate evaluations */}
              <div className="space-y-2">
                {evaluations.map((evaluation) => (
                  <div
                    key={evaluation.asin}
                    className={`p-3 rounded-lg border-2 ${
                      evaluation.qualified
                        ? 'bg-green-50 border-green-200'
                        : 'bg-red-50 border-red-200'
                    }`}
                  >
                    <div className="flex items-start gap-3">
                      <div className="flex-shrink-0 text-2xl">
                        {evaluation.qualified ? '✓' : '✗'}
                      </div>
                      
                      <div className="flex-1">
                        <p className={`font-medium ${
                          evaluation.qualified ? 'text-green-900' : 'text-red-900'
                        }`}>
                          {evaluation.title}
                        </p>
                        
                        <div className="flex gap-4 text-sm text-gray-600 mt-1">
                          <span>${evaluation.metrics.price.toFixed(2)}</span>
                          <span>{evaluation.metrics.rating.toFixed(1)}★</span>
                          <span>{evaluation.metrics.reviews.toLocaleString()} reviews</span>
                        </div>

                        {/* Filter results */}
                        <div className="mt-2 space-y-1">
                          {Object.entries(evaluation.filterResults).map(([filterName, result]) => (
                            <div
                              key={filterName}
                              className={`text-xs ${
                                result.passed ? 'text-green-700' : 'text-red-700'
                              }`}
                            >
                              <span className="font-medium">
                                {filterName.split('_').join(' ')}:
                              </span>{' '}
                              {result.detail}
                            </div>
                          ))}
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      )}

      {/* Metadata for non-filter steps */}
      {!isFilterStep && step.metadata && (
        <details className="mt-4">
          <summary className="cursor-pointer text-sm font-medium text-gray-700 hover:text-gray-900">
            View Metadata
          </summary>
          <pre className="json-container mt-2 text-xs">
            {JSON.stringify(step.metadata, null, 2)}
          </pre>
        </details>
      )}
    </div>
  );
};

export default StepCard;
