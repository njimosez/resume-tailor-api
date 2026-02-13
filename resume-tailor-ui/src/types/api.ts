export interface JobMatch {
  title: string;
  url: string;
  snippet: string;
  score: number;
}

export interface ResumeTailorResponse {
  resumeId: string;
  tailoredResume: string;
  jobMatches: JobMatch[];
  createdAt: string;
  downloadUrl: string;
}

export interface ApiError {
  message: string;
  status: number;
  timestamp?: string;
}
