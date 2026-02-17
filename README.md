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
## ScreenShots
#API
<img width="1498" height="811" alt="image" src="https://github.com/user-attachments/assets/c8753dfd-4680-4d95-a4bb-509299317fb4" />
<img width="1516" height="450" alt="image" src="https://github.com/user-attachments/assets/16bc95ca-96be-49b3-a41a-870483d0ab4d" />
# from UI
<img width="1870" height="973" alt="image" src="https://github.com/user-attachments/assets/ae2fdbaa-ae31-4f39-851d-71bd89ed616b" />
<img width="1769" height="977" alt="image" src="https://github.com/user-attachments/assets/e6ed6fb6-2980-4ff6-8cdb-c415d35d6956" />




