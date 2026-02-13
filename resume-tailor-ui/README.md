# Resume Tailor UI

A modern, AI-powered React front-end application for tailoring resumes to match job opportunities.

## Features

- 📤 **Resume Upload**: Upload your resume in multiple formats (PDF, DOC, DOCX, TXT)
- 🤖 **AI-Powered Tailoring**: Automatically tailor your resume using AI
- 💼 **Job Matching**: Find relevant job opportunities that match your resume
- 📊 **Match Scoring**: See how well each job matches your profile
- 📥 **Download**: Download your tailored resume
- 🎨 **Modern UI**: Beautiful, responsive design with AI-powered aesthetics
- ⚡ **Real-time Updates**: Instant feedback and loading states
- 🚨 **Error Handling**: Comprehensive error messages and retry options

## Tech Stack

- **React 18** - UI library
- **TypeScript** - Type safety
- **Vite** - Build tool and dev server
- **Axios** - HTTP client
- **CSS3** - Modern styling with animations

## Getting Started

### Prerequisites

- Node.js 16+ and npm
- Resume Tailor API running on `http://localhost:8080`

### Installation

1. Navigate to the project directory:
```bash
cd resume-tailor-ui
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

The application will be available at `http://localhost:3000`

### Build for Production

```bash
npm run build
```

The production-ready files will be in the `dist` directory.

### Preview Production Build

```bash
npm run preview
```

## API Integration

The application connects to the Resume Tailor API with the following endpoints:

- **POST** `/api/resume/tailor` - Upload and tailor a resume
- **GET** `/api/resume/:id` - Retrieve a tailored resume
- **GET** `/api/resume/:id/download` - Download a tailored resume

The Vite dev server is configured to proxy `/api` requests to `http://localhost:8080`.

## Project Structure

```
resume-tailor-ui/
├── src/
│   ├── components/         # React components
│   │   ├── UploadForm.tsx
│   │   ├── ResultsDisplay.tsx
│   │   └── ErrorDisplay.tsx
│   ├── services/           # API services
│   │   └── api.ts
│   ├── types/              # TypeScript types
│   │   └── api.ts
│   ├── App.tsx             # Main application component
│   ├── App.css             # Application styles
│   ├── main.tsx            # Application entry point
│   └── index.css           # Global styles
├── index.html              # HTML template
├── vite.config.ts          # Vite configuration
├── tsconfig.json           # TypeScript configuration
└── package.json            # Project dependencies
```

## Usage

1. **Upload a Resume**: Click on the upload area or drag and drop your resume file
2. **Add Job Query** (Optional): Enter specific job search terms to find targeted opportunities
3. **Tailor Resume**: Click the "Tailor My Resume" button
4. **View Results**: 
   - Review your AI-tailored resume on the left
   - Browse matched job opportunities on the right
   - Check match scores for each job
5. **Download**: Click the download button to save your tailored resume
6. **Start Over**: Click "New Upload" to process another resume

## Features in Detail

### Resume Upload
- Supports multiple file formats: PDF, DOC, DOCX, TXT
- Drag and drop support
- File validation
- Visual feedback for selected files

### AI-Powered Tailoring
- Automatic resume optimization
- Job-specific customization
- Intelligent content enhancement

### Job Matching
- Web search for relevant jobs
- Match score calculation
- Direct links to job postings
- Job snippets and descriptions

### Download Options
- Download tailored resume as text file
- Preserves formatting
- Unique filename with resume ID

### Error Handling
- Network error detection
- API error messages
- Retry functionality
- User-friendly error displays

## Customization

### API Base URL

To change the backend API URL, edit [vite.config.ts](vite.config.ts#L8-L13):

```typescript
server: {
  proxy: {
    '/api': {
      target: 'http://your-api-url:port',
      changeOrigin: true,
    }
  }
}
```

### Styling

The application uses CSS custom properties (variables) for theming. Edit [src/index.css](src/index.css#L1-L13) to customize colors:

```css
:root {
  --bg-primary: #0a0e27;
  --bg-secondary: #131735;
  --accent-primary: #6366f1;
  /* ... more variables */
}
```

## License

This project is part of the Resume Tailor API ecosystem.

## Support

For issues or questions, please refer to the main Resume Tailor API documentation.
