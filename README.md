# X-Ray Debugger - Multi-Step Decision Pipeline Debugger

A debugging system for non-deterministic, multi-step algorithmic pipelines. Provides transparency into complex decision processes by capturing the "why" at each step.

## 🎯 Overview

X-Ray Debugger consists of three components:
1. **X-Ray Library**: Lightweight SDK for capturing decision context
2. **Dashboard UI**: Web interface for visualizing execution trails
3. **Demo Application**: Competitor product selection pipeline (Amazon use case)

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Node.js 18+
- Maven

### Backend Setup
```bash
cd backend
./mvnw spring-boot:run
```
Backend runs on `http://localhost:8080`

### Frontend Setup
```bash
cd frontend
npm install
npm start
```
Frontend runs on `http://localhost:3000`

### Running the Demo
1. Start backend server
2. Start frontend application
3. Click "Run Demo" button in the UI
4. View execution details and step-by-step decision trail

## 🏗️ Architecture

### X-Ray Library Design
```java
XRayTracer tracer = new XRayTracer();
String executionId = tracer.startExecution(context);

tracer.recordStep(StepRecord.builder()
    .stepName("keyword_generation")
    .input(input)
    .output(output)
    .reasoning("Extracted key attributes from product")
    .build());

tracer.endExecution();
```

### Data Model
- **XRayExecution**: Container for complete pipeline run
  - executionId, startTime, endTime, status, context
- **XRayStep**: Individual decision point
  - stepId, stepName, timestamp, input, output, reasoning, metadata

### Technology Stack
- **Backend**: Spring Boot 3.x, JPA, H2 Database
- **Frontend**: React 18, TypeScript, Tailwind CSS
- **Storage**: H2 embedded database (file-based)

## 📊 Design Decisions

### Why H2 Database?
- Zero external dependencies, embedded
- File-based persistence (data survives restarts)
- Easy to demo and setup
- Production system would use PostgreSQL/MongoDB

### Why Simple REST API?
- Clear separation of concerns
- Easy to test independently
- Frontend-agnostic (could build CLI, mobile app, etc.)

### Why Store Full JSON?
- Flexible schema for different pipeline types
- No schema migrations needed for new step types
- Easy to query and display in UI

## 🎨 Dashboard Features

### Execution List
- All pipeline executions with timestamps
- Quick status overview
- Click to view details

### Execution Detail
- Step-by-step timeline visualization
- Collapsible input/output/metadata
- Prominent reasoning display
- Filter evaluation details with pass/fail indicators

### Key UX Decisions
- **Scannability**: Failures highlighted in red, passes in green
- **Collapsible sections**: Don't overwhelm with data
- **Clear reasoning**: "Why" is the most prominent element
- **Drill-down**: Click to expand full details

## 🔮 Future Improvements

With more time, I would add:

1. **Search & Filter**: 
   - Filter executions by date range, status
   - Search by step name, reasoning content
   - Filter by failure patterns

2. **Comparison View**: 
   - Side-by-side comparison of two executions
   - Highlight differences in decisions
   - Track what changed between runs

3. **Export & Analytics**:
   - Export execution as JSON for offline analysis
   - Aggregate metrics across multiple runs
   - Trend analysis (success/failure rates over time)

4. **Real-time Streaming**:
   - WebSocket support for live execution tracking
   - See steps as they happen in long-running pipelines

5. **Schema Validation**:
   - Define and enforce step schemas
   - Type-safe step recording
   - Auto-generate TypeScript types from schemas

6. **Performance Tracking**:
   - Step duration metrics
   - Identify bottlenecks in pipeline
   - Historical performance trends

7. **Alerting**:
   - Notify on specific failure patterns
   - Threshold-based alerts (e.g., >50% filter failures)
   - Integration with Slack/PagerDuty

## 🧪 Demo Application

The included competitor selection demo simulates a 3-step pipeline:

1. **Keyword Generation** (LLM step)
   - Input: Product title and category
   - Output: Search keywords
   - Mock LLM response based on input patterns

2. **Candidate Search** (API step)
   - Input: Search keywords
   - Output: 50 candidate products from mock database
   - Simulates large result set (2,847 total products)

3. **Apply Filters** (Business logic step)
   - Input: Candidate products, reference product
   - Filters: Price range (0.5x-2x), min rating (3.8★), min reviews (100)
   - Output: Qualified competitors with detailed pass/fail reasoning

All data is mock/hardcoded - the focus is on demonstrating the X-Ray system's capabilities.

## 📝 License

MIT License - feel free to use this in your own projects!

## 🤝 Contributing

This is a demo project, but suggestions and improvements are welcome!
