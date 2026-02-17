# Resume Tailor API

Spring Boot backend for AI-driven resume tailoring with Spring AI, OpenAI GPT models, Tavily web search, and Postgres + pgvector for vector storage.

## Features
- Upload a resume file for processing
- Extract resume content and generate job search query
- Use Tavily to search and retrieve job details
- Tailor the resume to match job descriptions
- Persist tailored resumes and store embeddings for RAG

## Prerequisites
- Java 21
- Maven 3.9+
- OpenAI API key
- Tavily API key

## Environment Variables
- `OPENAI_API_KEY`
- `OPENAI_CHAT_MODEL` (default: `gpt-4o-mini`)
- `OPENAI_EMBEDDING_MODEL` (default: `text-embedding-3-small`)
- `TAVILY_API_KEY`
- `POSTGRES_URL` 
- `POSTGRES_USER` 
- `POSTGRES_PASSWORD`

  <img width="1274" height="824" alt="image" src="https://github.com/user-attachments/assets/b1c9c448-a8ef-4840-9341-49a776b0579f" />
<img width="1524" height="816" alt="image" src="https://github.com/user-attachments/assets/cf4d5df4-289b-49fa-838b-d6fc905bc81e" />
<img width="1510" height="858" alt="image" src="https://github.com/user-attachments/assets/18bd0577-ce35-409f-bf42-a9abf2c0fb5a" />


## Run the API
- Create and get API Keys for Open API and Tavily 
- Configure and set up a postgres database with user name and password 
- Update the environment variables using the above credentials
- Run progress
- Run the App ```bash 
mvn spring-boot:run
```

## Using Postman
- `POST /api/resume/tailor` (multipart): `file` required, `jobQuery` optional
- `GET /api/resume/{id}`
- `GET /api/resume/{id}/download`
## Using the Resume API 
- Set up and Run the Resume Tailor UI(https://github.com/njimosez/resume-tailor-ui)
