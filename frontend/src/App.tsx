import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ExecutionList from './components/ExecutionList';
import ExecutionDetail from './components/ExecutionDetail';

/**
 * Main App component with routing
 */
function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Routes>
          <Route path="/" element={<ExecutionList />} />
          <Route path="/execution/:executionId" element={<ExecutionDetail />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
