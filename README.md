# Resume Tailor API

Spring Boot backend for AI-driven resume tailoring with Spring AI, OpenAI GPT models, Tavily web search, and Postgres + pgvector for vector storage.

## Features
- Upload resume files for processing
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


## Run Postgres + pgvector
```bash
configure and set up a postgres database with user name and password 
```

## Observability (Grafana)
The docker compose stack also includes Prometheus, Grafana, Loki, Tempo, and Promtail.

- Grafana: http://localhost:3000 (admin/admin)
- Prometheus: http://localhost:9090
- Loki: http://localhost:3100
- Tempo: http://localhost:3200

Start the stack (includes Postgres + observability services):
```bash
docker compose up -d
```

Run the API, then check:
- Metrics: http://localhost:8080/actuator/prometheus
- Traces: Grafana -> Explore -> Tempo
- Logs: Grafana -> Explore -> Loki

## Run the API
- Create and get API Keys for Open API and Tavily 
- Configure and set up a postgres database with user name and password 
- Update the environment variables using the above credentials 
```bash
Ensure the 
mvn spring-boot:run
```

## API
- `POST /api/resume/tailor` (multipart): `file` required, `jobQuery` optional
- `GET /api/resume/{id}`
- `GET /api/resume/{id}/download`
