import { ApiError } from '../types/api';
import './ErrorDisplay.css';

interface ErrorDisplayProps {
  error: ApiError;
  onReset: () => void;
}

const ErrorDisplay: React.FC<ErrorDisplayProps> = ({ error, onReset }) => {
  return (
    <div className="error-display">
      <div className="error-container">
        <h2 className="error-title">⚠️ Error</h2>
        <p className="error-message">{error.message}</p>
        {error.status > 0 && (
          <span className="error-status">Status Code: {error.status}</span>
        )}
        <button className="retry-button" onClick={onReset}>
          Try Again
        </button>
      </div>
    </div>
  );
};

export default ErrorDisplay;
