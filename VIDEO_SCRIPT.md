# Video Walkthrough Script (5-10 minutes)

## Setup Before Recording
- [ ] Close all unnecessary apps and browser tabs
- [ ] Have backend and frontend running
- [ ] Have a fresh execution ready (or run demo during video)
- [ ] Terminal with readable font size
- [ ] Code editor open to key files
- [ ] Browser zoom at 100%

---

## INTRO (60 seconds)

**Screen:** Show project folder structure

"Hi, I'm Pavan. I built an X-Ray debugging system for multi-step algorithmic pipelines. The goal is to provide transparency into complex decision processes by capturing not just WHAT happened, but WHY decisions were made at each step.

The system has three components: an X-Ray library that developers integrate into their code, a React dashboard for visualization, and a demo application that simulates competitor product selection on Amazon."

---

## ARCHITECTURE OVERVIEW (90 seconds)

**Screen:** Show backend code structure

"Let me walk you through the architecture. The core is the XRayTracer library. Developers use a simple fluent API to record steps:

[Show XRayTracer.java]

tracer.startExecution(context)
tracer.recordStep(...) 
tracer.endExecution()

Each step captures input, output, reasoning, and optional metadata. The reasoning field is critical - it explains WHY a decision was made, not just what happened.

[Show XRayExecution and XRayStep models]

The data model is simple: an Execution contains multiple Steps. I used JSON columns for flexible schemas, so the library works across different pipeline types without schema migrations.

[Show CompetitorSelectionService.java]

The demo implements a 3-step competitor selection pipeline. Step 1 generates keywords using a mock LLM. Step 2 searches for candidates. Step 3 applies filters and selects the best match. Each step records detailed decision context."

---

## LIVE DEMO (4 minutes)

**Screen:** Switch to browser at http://localhost:3000

"Now let's see it in action. This is the dashboard. I'll click Run Demo to execute the pipeline."

[Click Run Demo]

"The system runs all three steps and stores the execution. Now we're looking at the detailed execution trail.

[Point to timeline]

This timeline shows the flow: Keyword Generation, Candidate Search, and Apply Filters.

[Scroll to Step 1]

Step 1 generated search keywords from the product title. The reasoning explains that it extracted key attributes like material, capacity, and features. This is a mock LLM call - in production, this would call GPT-4 or Claude.

[Show Input/Output]

I can expand to see the raw input and output data, but the reasoning is what helps debugging. If the keywords were wrong, I'd immediately see WHY they were generated.

[Scroll to Step 2]

Step 2 searched for candidate products. It fetched 50 results from a mock database of 2,847 total products. Again, the reasoning explains the selection criteria.

[Scroll to Step 3 - THIS IS THE STAR]

Step 3 is where X-Ray really shines. This is the filter evaluation step. Let me expand the filter evaluations.

[Click to expand evaluations]

Here you can see EVERY candidate product and exactly why it passed or failed. 

[Point to a passing product - green checkmark]

HydroFlask passed all filters - price is within range, rating above 3.8, review count above 100. 

[Point to a failing product - red X]

Generic Bottle failed three filters: price too low at $8.99, rating below threshold at 3.2, and only 45 reviews. You can see the exact failure reason for each filter.

This level of detail is what makes debugging easy. Instead of guessing why a product was excluded, you immediately see: 'Failed - price $8.99 < $14.99 minimum'.

[Scroll through a few more evaluations]

The system evaluated all 50 candidates. 12 passed, 38 failed. Without X-Ray, you'd just see the final selection and have no idea why the other 49 products were rejected."

---

## DESIGN DECISIONS (90 seconds)

**Screen:** Can stay on dashboard or switch to code

"Let me talk about some key design decisions.

**Why H2 database?**
I chose H2 embedded for ease of demo and zero external dependencies. It's file-based, so data persists across restarts. In production, I'd use PostgreSQL or MongoDB, but for this assignment, H2 lets you clone the repo and start immediately without database setup.

**Why simple REST API?**
I kept the backend as a straightforward REST API for clear separation of concerns. This makes the system testable and allows for different frontends - you could build a CLI tool or mobile app using the same API.

**Why store full JSON?**
I used JSON columns for input, output, and metadata to support flexible schemas. Different pipeline types need different data structures. This approach means no schema migrations when you add new step types - just record whatever JSON you need.

**Why emphasize reasoning?**
Traditional tracing tools focus on performance - what functions were called, how long they took. X-Ray focuses on decision logic - WHY this output was selected. That's why the reasoning field is always prominently displayed."

---

## TRADE-OFFS & IMPROVEMENTS (90 seconds)

**Screen:** Can show code or dashboard

"With more time, I would add several improvements:

**Search and filtering:** Right now you see all executions. I'd add the ability to filter by date range, status, or step type. Also search within reasoning text - like 'show me all executions where the price filter failed'.

**Comparison view:** Compare two executions side-by-side to see what changed. This would be helpful for A/B testing different filter criteria or debugging regressions.

**Export functionality:** Download executions as JSON for offline analysis or sharing with team members.

**Performance metrics:** Track step duration and identify bottlenecks. Right now we track total execution time, but not per-step timing.

**Real-time streaming:** For long-running pipelines, use WebSockets to stream steps as they happen instead of polling.

**Schema validation:** Optionally define and enforce schemas for steps, with auto-generated TypeScript types for type-safe recording.

I deliberately kept the demo app simple with hardcoded mock data. The focus is on showcasing the X-Ray system, not building a production competitor selection algorithm. The mock data is realistic enough to demonstrate the debugging capabilities."

---

## CLOSING (30 seconds)

**Screen:** Back to execution detail or code

"To summarize: X-Ray provides transparency into multi-step decision processes by capturing the WHY at each step. The library is easy to integrate, the dashboard makes debugging intuitive, and the flexible data model works across different pipeline types.

Thanks for watching. I'm happy to discuss any questions about the implementation or design decisions."

---

## Recording Tips

1. **Speak clearly and at a moderate pace**
   - Don't rush - you have 10 minutes
   - Pause between sections
   - Emphasize key points

2. **Keep it conversational**
   - Don't read a script word-for-word
   - Use natural language
   - Show enthusiasm for your work

3. **Navigate smoothly**
   - Practice the demo flow beforehand
   - Know which code files to show
   - Don't fumble around looking for things

4. **Highlight what matters**
   - Reasoning field is THE key differentiator
   - Filter evaluations demonstrate value
   - Show, don't just tell

5. **Be honest about limitations**
   - Acknowledge trade-offs openly
   - Discuss what you'd improve
   - Shows mature engineering thinking

6. **Time management**
   - If running long, skip the code walkthrough
   - If running short, dive deeper into Step 3
   - Aim for 7-9 minutes (sweet spot)

## Common Mistakes to Avoid

❌ Apologizing ("Sorry, this isn't perfect...")
❌ Reading code line by line
❌ Getting lost in implementation details
❌ Forgetting to show the actual demo
❌ Talking too fast
❌ Long awkward pauses

✅ Confident delivery
✅ Focus on system design and UX
✅ Clear demonstration of value
✅ Natural conversation
✅ Thoughtful explanations

## Final Check Before Recording

- [ ] Backend running
- [ ] Frontend running  
- [ ] Demo tested recently
- [ ] Code files open in editor
- [ ] Desktop clean
- [ ] Recording software ready
- [ ] Practiced run-through once

**Now record! You've got this! 🎬**
