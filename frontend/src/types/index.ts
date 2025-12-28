export interface XRayExecution {
  executionId: string;
  startTime: string;
  endTime?: string;
  status: string;
  context: any;
  steps: XRayStep[];
  createdAt: string;
}

export interface XRayStep {
  stepId: string;
  stepName: string;
  timestamp: string;
  input: any;
  output: any;
  reasoning: string;
  metadata?: any;
  createdAt: string;
}

export interface DemoResponse {
  executionId: string;
  message: string;
  success: boolean;
}

export interface FilterResult {
  passed: boolean;
  detail: string;
}

export interface CandidateEvaluation {
  asin: string;
  title: string;
  metrics: {
    price: number;
    rating: number;
    reviews: number;
  };
  filterResults: {
    [key: string]: FilterResult;
  };
  qualified: boolean;
}
