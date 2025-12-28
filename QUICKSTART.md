# X-Ray Debugger - Quick Start Guide

## Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- Maven (or use included mvnw)

## Step 1: Start Backend (5 minutes)

```bash
cd backend

# If Maven is installed:
mvn spring-boot:run

# OR use Maven Wrapper (no Maven installation needed):
# On Mac/Linux:
./mvnw spring-boot:run

# On Windows:
mvnw.cmd spring-boot:run
```

Backend will start on http://localhost:8080

**Verify backend is running:**
- Open http://localhost:8080/h2-console (H2 database console)
- Check terminal for "X-Ray Debugger API Started Successfully"

## Step 2: Start Frontend (5 minutes)

**Open a NEW terminal window**, then:

```bash
cd frontend

# Install dependencies (first time only):
npm install

# Start the React app:
npm start
```

Frontend will open automatically at http://localhost:3000

## Step 3: Run the Demo (30 seconds)

1. Click the **"▶ Run Demo"** button
2. You'll be redirected to the execution detail page
3. Explore the 3-step pipeline:
   - Step 1: Keyword Generation (mock LLM)
   - Step 2: Candidate Search (mock API with 50 products)
   - Step 3: Apply Filters (detailed pass/fail for each product)

## Troubleshooting

### Backend won't start
**Problem:** Port 8080 already in use
**Solution:** Kill the process using port 8080 or change server.port in `application.properties`

**Problem:** "JAVA_HOME not set"
**Solution:** 
```bash
# Mac/Linux
export JAVA_HOME=$(/usr/libexec/java_home)

# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17
```

### Frontend won't start
**Problem:** "npm: command not found"
**Solution:** Install Node.js from https://nodejs.org/

**Problem:** Port 3000 already in use
**Solution:** Kill the process or the app will prompt to use port 3001

### Can't connect to backend
**Problem:** "Failed to load executions"
**Solution:**
1. Verify backend is running (check http://localhost:8080/api/executions)
2. Check browser console for CORS errors
3. Restart both backend and frontend

## What to Look For

### In the Execution List
- Each execution shows timestamp, duration, step count
- Click any execution to view details

### In the Execution Detail
- **Timeline**: Visual flow of steps
- **Step Cards**: Detailed view of each step
  - **Reasoning**: WHY the decision was made (most important!)
  - Input/Output: Collapsible JSON data
  - Metadata: Additional context

### In Step 3 (Apply Filters)
- See ALL 50 candidate products
- Green ✓ = passed all filters
- Red ✗ = failed at least one filter
- Exact failure reasons for each product

## Next Steps

1. **Explore the code:**
   - Backend: `backend/src/main/java/com/equalcollective/xray/`
   - Frontend: `frontend/src/components/`

2. **Modify the demo:**
   - Change filter criteria in `CompetitorSelectionService.java`
   - Add more mock products in `MockData.java`
   - Run demo again and see how results change

3. **Create your own pipeline:**
   - Use `XRayTracer` in your own services
   - Record steps with `tracer.recordStep()`
   - View execution trail in the dashboard

## API Endpoints

- `GET /api/executions` - List all executions
- `GET /api/executions/{id}` - Get execution details
- `POST /api/demo/run-competitor-selection` - Run demo
- `DELETE /api/executions/{id}` - Delete execution
- `DELETE /api/executions` - Delete all executions

## Database Access

H2 Console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/xraydb`
- Username: `sa`
- Password: (leave blank)

You can query the database directly:
```sql
SELECT * FROM XRAY_EXECUTIONS;
SELECT * FROM XRAY_STEPS;
```

## Ready for Video Walkthrough?

Once you've explored the system, record your 5-10 minute Loom video covering:
1. Architecture overview (show code structure)
2. Live demo (run and navigate through execution)
3. Design decisions (why H2, why this data model, etc.)
4. Trade-offs and future improvements

Enjoy exploring X-Ray Debugger! 🚀
