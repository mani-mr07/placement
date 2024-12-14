// // src/App.js
// // import React from 'react';
// import Login from './components/Login';

// function App() {
//     return (
//         <div className="App">
//             {/* <h1>HI</h1> */}
//             <Login />
//         </div>
//     //     <Router>
//     //     <Routes>
//     //       <Route path="/login" element={<Login />} />
//     //       <Route path="/register" element={<Register />} />
//     //       <Route path="/" element={<Login />} />
//     //     </Routes>
//     //   </Router>
//     );
// }

// export default App;
// src/App.js
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/register";
import StudentDashboard from "./components/StudentDashboard";
import StaffDashboard from "./components/StaffDashboard";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/student-dashboard" element={<StudentDashboard />} />
        <Route path="/staff-dashboard" element={<StaffDashboard />} />
        {/* Other routes */}
      </Routes>
    </Router>
  );
}

export default App;

