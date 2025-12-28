import { XRayExecution, DemoResponse } from '../types';

const API_BASE_URL = 'http://localhost:8080/api';

export const api = {

  async getExecutions(): Promise<XRayExecution[]> {
    const response = await fetch(`${API_BASE_URL}/executions`);
    if (!response.ok) {
      throw new Error('Failed to fetch executions');
    }
    return response.json();
  },

  async getExecution(executionId: string): Promise<XRayExecution> {
    const response = await fetch(`${API_BASE_URL}/executions/${executionId}`);
    if (!response.ok) {
      throw new Error(`Failed to fetch execution: ${executionId}`);
    }
    return response.json();
  },

  async deleteAllExecutions(): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/executions`, {
      method: 'DELETE',
    });
    if (!response.ok) {
      throw new Error('Failed to delete all executions');
    }
  },

  async runDemo(): Promise<DemoResponse> {
    const response = await fetch(`${API_BASE_URL}/demo/run-competitor-selection`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    if (!response.ok) {
      throw new Error('Failed to run demo');
    }
    return response.json();
  },
};
