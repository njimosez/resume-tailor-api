import { useState, ChangeEvent, FormEvent } from 'react';
import './UploadForm.css';

interface UploadFormProps {
  onUpload: (file: File, jobQuery: string) => void;
}

const UploadForm: React.FC<UploadFormProps> = ({ onUpload }) => {
  const [file, setFile] = useState<File | null>(null);
  const [jobQuery, setJobQuery] = useState('');

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setFile(e.target.files[0]);
    }
  };

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    if (file) {
      onUpload(file, jobQuery);
    }
  };

  return (
    <form className="upload-form" onSubmit={handleSubmit}>
      <div className="form-group">
        <label className="form-label">Upload Resume</label>
        <div className="file-input-wrapper">
          <input
            type="file"
            id="resume-file"
            className="file-input"
            onChange={handleFileChange}
            accept=".pdf,.doc,.docx,.txt"
            required
          />
          <label
            htmlFor="resume-file"
            className={`file-input-label ${file ? 'has-file' : ''}`}
          >
            <span className="file-icon">📄</span>
            <span>{file ? file.name : 'Choose a file or drag it here'}</span>
          </label>
        </div>
        <span className="helper-text">
          Supported formats: PDF, DOC, DOCX, TXT (Max 10MB)
        </span>
      </div>

      <div className="form-group">
        <label className="form-label" htmlFor="job-query">
          Job Search Query (Optional)
        </label>
        <input
          type="text"
          id="job-query"
          className="text-input"
          value={jobQuery}
          onChange={(e) => setJobQuery(e.target.value)}
          placeholder="e.g., Senior Software Engineer, Remote"
        />
        <span className="helper-text">
          Leave empty to let AI find the best matches for your resume
        </span>
      </div>

      <button type="submit" className="submit-button" disabled={!file}>
        ✨ Tailor My Resume
      </button>
    </form>
  );
};

export default UploadForm;
