import { ResumeTailorResponse } from '../types/api';
import { resumeApi } from '../services/api';
import { useState } from 'react';
import './ResultsDisplay.css';

interface ResultsDisplayProps {
  result: ResumeTailorResponse;
  onReset: () => void;
}

const ResultsDisplay: React.FC<ResultsDisplayProps> = ({ result, onReset }) => {
  const [isJobMatchesExpanded, setIsJobMatchesExpanded] = useState(true);
  const handleDownload = async () => {
    try {
      const blob = await resumeApi.downloadResume(result.resumeId);
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `tailored-resume-${result.resumeId}.txt`;
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);
    } catch (error) {
      console.error('Download failed:', error);
      alert('Failed to download resume. Please try again.');
    }
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  const formatScore = (score: number) => {
    return `${Math.round(score * 100)}%`;
  };

  return (
    <div className="results-display">
      <div className="results-header">
        <h2 className="results-title">✨ Your Tailored Resume</h2>
        <div className="results-actions">
          <button className="action-button primary" onClick={handleDownload}>
            <span>⬇️</span>
            <span>Download</span>
          </button>
          <button className="action-button" onClick={onReset}>
            <span>🔄</span>
            <span>New Upload</span>
          </button>
        </div>
      </div>

      <div className="results-content">
        <div className="result-card">
          <div className="card-header">
            <span className="card-icon">📝</span>
            <h3 className="card-title">Tailored Resume</h3>
          </div>
          <div className="resume-content">{result.tailoredResume}</div>
        </div>

        <div className="result-card collapsible">
          <div className="card-header clickable" onClick={() => setIsJobMatchesExpanded(!isJobMatchesExpanded)}>
            <div className="card-header-left">
              <span className="card-icon">💼</span>
              <h3 className="card-title">Job Matches ({result.jobMatches.length})</h3>
            </div>
            <button className="collapse-button" aria-label={isJobMatchesExpanded ? 'Collapse' : 'Expand'}>
              {isJobMatchesExpanded ? '▼' : '▶'}
            </button>
          </div>
          <div className={`collapsible-content ${isJobMatchesExpanded ? 'expanded' : 'collapsed'}`}>
            <div className="job-matches">
              {result.jobMatches.length > 0 ? (
                result.jobMatches.map((job, index) => (
                  <div key={index} className="job-card">
                    <div className="job-header">
                      <h4 className="job-title">{job.title}</h4>
                      <span className="job-score">
                        ⭐ {formatScore(job.score)}
                      </span>
                    </div>
                    <p className="job-snippet">{job.snippet}</p>
                    <a
                      href={job.url}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="job-link"
                    >
                      View Job Details →
                    </a>
                  </div>
                ))
              ) : (
                <p className="no-results">No job matches found</p>
              )}
            </div>
          </div>
        </div>
      </div>

      <div className="metadata">
        <div className="metadata-item">
          <span className="metadata-label">Resume ID</span>
          <span className="metadata-value">{result.resumeId}</span>
        </div>
        <div className="metadata-item">
          <span className="metadata-label">Created At</span>
          <span className="metadata-value">{formatDate(result.createdAt)}</span>
        </div>
        <div className="metadata-item">
          <span className="metadata-label">Job Matches</span>
          <span className="metadata-value">{result.jobMatches.length}</span>
        </div>
      </div>
    </div>
  );
};

export default ResultsDisplay;
