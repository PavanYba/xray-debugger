# X-Ray Debugger

Multi-step decision pipeline debugger for non-deterministic algorithmic workflows.

## Overview

X-Ray Debugger provides transparency into complex decision processes by capturing the "WHY" at each step of a pipeline, not just the "WHAT". Built for debugging AI agents, recommendation systems, and other multi-step algorithms where understanding decision logic is critical.

## Features

- 🔍 **Decision Trail Capture** - Records input, output, and reasoning at each pipeline step
- 📊 **Visual Dashboard** - React-based UI for exploring execution trails
- ✅ **Detailed Filter Analysis** - Shows pass/fail reasons for each candidate in filtering steps
- 💾 **Persistent Storage** - H2 database with file-based persistence
- 🎯 **Production-Ready Code** - Clean architecture, constructor injection, zero warnings

## Demo Application

Competitor product selection pipeline for Amazon sellers:
1. **Keyword Generation** - LLM extracts search terms from product
2. **Candidate Search** - Fetches potential competitors from catalog
3. **Apply Filters** - Business logic selects best match with detailed reasoning

## Tech Stack

**Backend:**
- Java 17 + Spring Boot 3.2
- Spring Data JPA + H2 Database
- Lombok for clean code
- Maven build system

**Frontend:**
- React 18 + TypeScript
- Tailwind CSS for styling
- React Router for navigation

## Setup Instructions

### Prerequisites
- Java 17+
- Node.js 18+
- Maven 3.6+

### Backend Setup
```bash
cd backend

# Build and run
mvn clean install
mvn spring-boot:run
```

Backend starts on **http://localhost:8080**

**Verify:**
- API: http://localhost:8080/api/executions
- H2 Console: http://localhost:8080/h2-console

### Frontend Setup
```bash
cd frontend

# Install dependencies (first time only)
npm install

# Start development server
npm start
```

Frontend opens automatically at **http://localhost:3000**

### Run Demo

1. Ensure backend is running
2. Open http://localhost:3000 in browser
3. Click "▶ Run Demo" button
4. Explore execution details and filter evaluations

## Architecture

### XRay Library API
```java
// Start execution
String execId = tracer.startExecution(context);

// Record steps
tracer.recordStep(execId, StepRecord.builder()
    .stepName("keyword_generation")
    .input(inputData)
    .output(outputData)
    .reasoning("Extracted key product attributes...")
    .build());

// Complete execution
tracer.endExecution(execId);
```

### Data Model

- **XRayExecution** - Container for pipeline run (executionId, status, context, steps)
- **XRayStep** - Individual decision point (stepName, input, output, reasoning, metadata)
- JSON columns for flexible schemas across different pipeline types

### Design Decisions

**Why H2 Database?**
- Zero external dependencies
- File-based persistence (data survives restarts)
- Easy demo setup
- Production would use PostgreSQL/MongoDB

**Why JSON Columns?**
- Different pipelines need different data structures
- No schema migrations for new step types
- Maximum flexibility

**Why Constructor Injection?**
- Immutable dependencies (thread-safe)
- Easier testing with mocks
- Spring Boot best practice

**Why Emphasize Reasoning?**
- Traditional tracing shows "what happened" (function calls, timing)
- X-Ray shows "why decisions were made" (business logic, filters)
- Debugging non-deterministic systems requires understanding reasoning

## Known Limitations

1. **Single-user demo** - No authentication or multi-tenancy
2. **Mock data** - Demo uses hardcoded products instead of real APIs
3. **No search/filter** - Dashboard shows all executions (no date range filter)
4. **No export** - Can't download executions as JSON

## Future Improvements

**With more time, I would add:**

1. **Comparison View** - Side-by-side comparison of executions to spot differences
2. **Search & Filtering** - Filter by date, status, step type, or search reasoning text
3. **Export Functionality** - Download executions as JSON for offline analysis
4. **Performance Metrics** - Per-step timing to identify bottlenecks
5. **Real-time Streaming** - WebSocket support for long-running pipelines
6. **Schema Validation** - Define and enforce step schemas with type-safe recording
7. **Alerting** - Notify on specific failure patterns
8. **Aggregate Analytics** - Success/failure rates over time

## Code Quality

- ✅ Zero IntelliJ warnings
- ✅ Constructor injection (not field injection)
- ✅ No unused methods or imports
- ✅ Clean, well-documented code
- ✅ Type-safe TypeScript
- ✅ Comprehensive error handling

## Project Structure
```
xray-debugger/
├── backend/
│   ├── src/main/java/com/equalcollective/xray/
│   │   ├── model/          # JPA entities (XRayExecution, XRayStep)
│   │   ├── repository/     # Spring Data repositories
│   │   ├── service/        # XRayTracer core library
│   │   ├── controller/     # REST API endpoints
│   │   └── demo/           # Competitor selection demo
│   └── pom.xml
└── frontend/
    ├── src/
    │   ├── components/     # React components
    │   ├── services/       # API client
    │   └── types/          # TypeScript types
    └── package.json
```

## API Endpoints

- `GET /api/executions` - List all executions
- `GET /api/executions/{id}` - Get execution with steps
- `POST /api/demo/run-competitor-selection` - Run demo pipeline
- `DELETE /api/executions/{id}` - Delete execution
- `DELETE /api/executions` - Delete all executions
