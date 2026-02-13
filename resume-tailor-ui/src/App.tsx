import { useState, useEffect } from 'react';
import './App.css';
import { ResumeTailorResponse, ApiError } from './types/api';
import { resumeApi } from './services/api';
import UploadForm from './components/UploadForm';
import ResultsDisplay from './components/ResultsDisplay';
import ErrorDisplay from './components/ErrorDisplay';
import axios from 'axios';

function App() {
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState<ResumeTailorResponse | null>(null);
  const [error, setError] = useState<ApiError | null>(null);
  const [theme, setTheme] = useState<'light' | 'dark'>(() => {
    // Check localStorage, default to dark mode
    const savedTheme = localStorage.getItem('theme') as 'light' | 'dark' | null;
    return savedTheme || 'dark';
  });

  useEffect(() => {
    // Apply theme to document
    document.documentElement.setAttribute('data-theme', theme);
    localStorage.setItem('theme', theme);
  }, [theme]);

  const toggleTheme = () => {
    setTheme(prevTheme => prevTheme === 'dark' ? 'light' : 'dark');
  };

  const handleUpload = async (file: File, jobQuery: string) => {
    setLoading(true);
    setError(null);
    setResult(null);

    try {
      const response = await resumeApi.tailorResume(file, jobQuery);
      setResult(response);
    } catch (err) {
      if (axios.isAxiosError(err) && err.response) {
        setError({
          message: err.response.data.message || 'An error occurred while processing your resume',
          status: err.response.status,
          timestamp: err.response.data.timestamp,
        });
      } else {
        setError({
          message: 'Network error. Please check your connection and try again.',
          status: 0,
        });
      }
    } finally {
      setLoading(false);
    }
  };

  const handleReset = () => {
    setResult(null);
    setError(null);
  };

  return (
    <div className="app">
      <button 
        className="theme-toggle" 
        onClick={toggleTheme}
        aria-label={`Switch to ${theme === 'dark' ? 'light' : 'dark'} mode`}
      >
        {theme === 'dark' ? '☀️' : '🌙'}
      </button>
      <div className="container">
        <header className="header">
          <h1>Resume Tailor AI</h1>
          <p>Upload your resume and let AI tailor it to match relevant job opportunities</p>
          <span className="ai-badge">✨ Powered by AI</span>
        </header>

        {loading && (
          <div className="loading">
            <div className="spinner"></div>
            <p className="loading-text">
              AI is analyzing your resume and finding matching jobs...
              <br />
              This may take a few moments.
            </p>
          </div>
        )}

        {error && <ErrorDisplay error={error} onReset={handleReset} />}

        {!loading && !result && <UploadForm onUpload={handleUpload} />}

        {result && <ResultsDisplay result={result} onReset={handleReset} />}
      </div>
    </div>
  );
}

export default App;
