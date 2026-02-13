import axios from 'axios';
import { ResumeTailorResponse } from '../types/api';

const API_BASE_URL = '/api/resume';

export const resumeApi = {
  tailorResume: async (file: File, jobQuery?: string): Promise<ResumeTailorResponse> => {
    const formData = new FormData();
    formData.append('file', file);
    if (jobQuery) {
      formData.append('jobQuery', jobQuery);
    }

    const response = await axios.post<ResumeTailorResponse>(
      `${API_BASE_URL}/tailor`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );
    return response.data;
  },

  getResume: async (id: string): Promise<ResumeTailorResponse> => {
    const response = await axios.get<ResumeTailorResponse>(`${API_BASE_URL}/${id}`);
    return response.data;
  },

  downloadResume: async (id: string): Promise<Blob> => {
    const response = await axios.get(`${API_BASE_URL}/${id}/download`, {
      responseType: 'blob',
    });
    return response.data;
  },
};
