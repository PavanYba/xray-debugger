# X-Ray Debugger - Submission Checklist

## Pre-Submission Testing

### Backend Testing
- [ ] Backend starts without errors (`mvn spring-boot:run`)
- [ ] H2 console accessible at http://localhost:8080/h2-console
- [ ] API endpoints respond:
  - [ ] GET http://localhost:8080/api/executions (returns empty array initially)
  - [ ] POST http://localhost:8080/api/demo/run-competitor-selection (returns execution ID)
  - [ ] GET http://localhost:8080/api/executions/{id} (returns execution with steps)
- [ ] Database persists data (stop/start backend, data remains)

### Frontend Testing
- [ ] Frontend starts without errors (`npm start`)
- [ ] Opens automatically at http://localhost:3000
- [ ] "Run Demo" button works
- [ ] Execution list displays correctly
- [ ] Click execution navigates to detail page
- [ ] Step cards display properly
- [ ] Filter evaluations show green/red correctly
- [ ] Collapsible sections work (input, output, metadata)
- [ ] Back button returns to list

### End-to-End Testing
- [ ] Run demo 3 times
- [ ] Verify all 3 executions appear in list
- [ ] Verify each shows 3 steps (keyword_generation, candidate_search, apply_filters)
- [ ] Check Step 3 shows 50 candidate evaluations
- [ ] Verify green checkmarks for passing products (8-12 products)
- [ ] Verify red X marks for failing products
- [ ] Verify failure reasons are specific (not generic)
- [ ] Delete one execution (works correctly)
- [ ] Delete all executions (works correctly)

## Code Quality Check

### Backend Code
- [ ] No compiler warnings
- [ ] No unused imports
- [ ] Consistent formatting
- [ ] Meaningful variable names (no x, y, temp)
- [ ] Comments explain WHY, not WHAT
- [ ] Lombok annotations used correctly (@Data, @Builder, @Slf4j)
- [ ] Proper exception handling
- [ ] XRayTracer is clean and easy to use
- [ ] Mock data is realistic

### Frontend Code
- [ ] No TypeScript errors
- [ ] No console.log statements in production
- [ ] No unused imports
- [ ] Consistent formatting
- [ ] Proper TypeScript types (no 'any' where avoidable)
- [ ] Responsive design works (resize browser)
- [ ] Loading states handled
- [ ] Error states handled

## Documentation Check

### README.md
- [ ] Setup instructions are clear
- [ ] Design decisions explained
- [ ] Future improvements listed
- [ ] Technology stack documented
- [ ] Screenshots/GIFs would help (optional but nice)

### Code Comments
- [ ] XRayTracer has JavaDoc
- [ ] Complex logic has explanatory comments
- [ ] React components have description comments
- [ ] API endpoints documented

## Video Walkthrough Preparation

### Video Checklist (Record AFTER everything works)
- [ ] Loom or YouTube account ready
- [ ] Screen recording software tested
- [ ] Microphone works clearly
- [ ] Desktop cleaned up (close unnecessary apps)
- [ ] Browser zoom at 100% (for recording)
- [ ] Terminal font size readable
- [ ] Practice run (do full walkthrough once)

### Video Content (5-10 minutes)
- [ ] **Intro (1 min)**: Who you are, what you built
- [ ] **Architecture (2 min)**: Show code structure, explain key design decisions
- [ ] **Live Demo (4 min)**: 
  - [ ] Run demo from UI
  - [ ] Navigate to execution detail
  - [ ] Walk through each step
  - [ ] Highlight filter evaluations
  - [ ] Explain why visibility helps debugging
- [ ] **Trade-offs (2 min)**:
  - [ ] Why H2 vs PostgreSQL
  - [ ] Why simple REST API
  - [ ] What you'd improve with more time
- [ ] **Closing (1 min)**: Thanks and invitation for questions

## GitHub Repository Setup

### Repository Structure
- [ ] Create new public GitHub repository
- [ ] Repository name: `xray-debugger` or similar
- [ ] Add description and README
- [ ] Push all code

### Commit History
- [ ] Initial commit: "Project setup"
- [ ] Commit: "Backend XRay library implementation"
- [ ] Commit: "Demo application - competitor selection"
- [ ] Commit: "Frontend dashboard UI"
- [ ] Commit: "Documentation and README"
- [ ] Keep commits meaningful (not "fix", "update", "asdf")

### .gitignore
- [ ] node_modules/ excluded
- [ ] target/ excluded
- [ ] data/ excluded (H2 database files)
- [ ] IDE files excluded (.idea, .vscode)

## Final Submission

### Files to Submit
- [ ] GitHub repository link
- [ ] Video walkthrough link (YouTube unlisted or Loom)
- [ ] Ensure both links are publicly accessible

### README Must Include
- [ ] Setup instructions (backend + frontend)
- [ ] Quick start guide
- [ ] Architecture explanation
- [ ] Design decisions
- [ ] Known limitations
- [ ] Future improvements

### Test One More Time
- [ ] Clone your repo in a new folder
- [ ] Follow your own setup instructions
- [ ] Verify everything works from scratch
- [ ] If it doesn't work, fix README and re-test

## What Equal Collective is Evaluating

### 1. System Design (Most Important)
- [ ] Is XRayTracer general-purpose and extensible?
- [ ] Clean integration API (`recordStep()` is simple)
- [ ] Good separation of concerns
- [ ] Flexible data model (JSON for varying schemas)

### 2. Dashboard UX (Second Most Important)
- [ ] Can you quickly understand what happened?
- [ ] Is information hierarchy clear?
- [ ] Are failures easy to spot?
- [ ] Is the reasoning prominent?

### 3. Code Quality
- [ ] Clean, readable code
- [ ] Sensible abstractions
- [ ] Proper error handling
- [ ] Good naming conventions

## Remember

✅ **Scope aggressively** - Working subset > ambitious broken system
✅ **Focus on "why"** - Reasoning is more important than raw data
✅ **Dashboard UX > aesthetics** - Make it usable, not just pretty
✅ **Video shows thinking** - Explain decisions, not just features

## Time Spent

Estimated: 6 hours
- [ ] Planning: 30 min
- [ ] Backend: 2 hours
- [ ] Frontend: 2.5 hours
- [ ] Testing/polish: 30 min
- [ ] Video: 30 min

Actual: _____ hours

## Submission Date/Time

Target: ________________
Actual: ________________

---

**FINAL CHECK:** Run the demo one more time before submitting. Make sure it works perfectly!

Good luck! You've got this! 🚀
