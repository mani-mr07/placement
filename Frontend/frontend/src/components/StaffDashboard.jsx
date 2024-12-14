import React, { useState, useEffect } from "react";
import axios from "axios";

const StaffDashboard = () => {
  const [companyName, setCompanyName] = useState("");
  const [companies, setCompanies] = useState([]);
  const [selectedCompany, setSelectedCompany] = useState(null);
  const [driveDetails, setDriveDetails] = useState({
    date: "",
    salary: "",
    description: "",
    eligibleCGPA: "",
    standingArrearLimit: "",
    historyOfArrearAllowed: false,
  });
  const [upcomingDrives, setUpcomingDrives] = useState([]);
  const [registeredStudents, setRegisteredStudents] = useState([]);
  const [selectedDrive, setSelectedDrive] = useState(null);

  // Fetch companies from backend
  const fetchCompanies = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/staff/retrieve/allCompany",
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        },
      });
      setCompanies(response.data);
    } catch (error) {
      console.error("Error fetching companies:", error);
      alert("Failed to fetch companies. Please try again.");
    }
  };

  useEffect(() => {
    fetchCompanies();
  }, []);

  // Handle company creation
  const handleCreateCompany = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/staff/uploadCompany", {
        companyName,
      });
      setCompanies([...companies, response.data]); // Update companies list
      setCompanyName("");
      alert("Company added successfully!");
    } catch (error) {
      console.error("Error creating company:", error);
      alert("Failed to add company. Please try again.");
    }
  };

  // Handle drive creation
  const handleCreateDrive = async (e) => {
    e.preventDefault();
    if (!selectedCompany) {
      alert("Please select a company first.");
      return;
    }
    try {
      const response = await axios.post(
        `http://localhost:8080/api/staff/${selectedCompany.id}/uploadDrive`,
        {
          ...driveDetails,
          companyId: selectedCompany.id,
        }
      );
      alert("Drive added successfully!");
      setDriveDetails({
        date: "",
        salary: "",
        description: "",
        eligibleCGPA: "",
        standingArrearLimit: "",
        historyOfArrearAllowed: false,
      });
      fetchDrives(selectedCompany.id); // Refresh drives list
    } catch (error) {
      console.error("Error adding drive:", error);
      alert("Failed to add drive. Please try again.");
    }
  };

  const fetchRegisteredStudents = async (driveId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/staff/retrieve/driveRegisteredStudent/${driveId}`
      );
      setRegisteredStudents(response.data);
    } catch (error) {
      console.error("Error fetching registered students:", error);
      alert("Failed to fetch registered students. Please try again.");
    }
  };

  const fetchDrives = async (companyId) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/staff/${companyId}/getDrives`);
      setUpcomingDrives(response.data);
    } catch (error) {
      console.error("Error fetching drives:", error);
      alert("Failed to fetch drives. Please try again.");
    }
  };

  useEffect(() => {
    if (selectedCompany) {
      fetchDrives(selectedCompany.id);
    }
  }, [selectedCompany]);

  return (
    <div style={{ padding: "20px" }}>
      <h1>Welcome to the Staff Dashboard</h1>

      {/* Company Form */}
      <form onSubmit={handleCreateCompany} style={{ marginBottom: "20px" }}>
        <h2>Create a Company</h2>
        <input
          type="text"
          value={companyName}
          onChange={(e) => setCompanyName(e.target.value)}
          placeholder="Enter company name"
          required
          style={{ width: "300px", marginRight: "10px" }}
        />
        <button type="submit">Add Company</button>
      </form>

      {/* Display Existing Companies */}
      <h2>Available Companies</h2>
      <div style={{ display: "flex", flexWrap: "wrap", gap: "20px" }}>
        {companies.length > 0 ? (
          companies.map((company) => (
            <div
              key={company.id}
              onClick={() => setSelectedCompany(company)}
              style={{
                border: selectedCompany?.id === company.id ? "2px solid green" : "1px solid black",
                padding: "10px",
                cursor: "pointer",
                width: "200px",
              }}
            >
              <h3>{company.companyName}</h3>
            </div>
          ))
        ) : (
          <p>No companies available. Please add one.</p>
        )}
      </div>

      {/* Drive Form */}
      {selectedCompany && (
        <form onSubmit={handleCreateDrive} style={{ marginTop: "20px" }}>
          <h2>Add a Drive for {selectedCompany.companyName}</h2>
          <div style={{ marginBottom: "10px" }}>
            <label>Date:</label>
            <input
              type="date"
              value={driveDetails.date}
              onChange={(e) => setDriveDetails({ ...driveDetails, date: e.target.value })}
              required
            />
          </div>
          <div style={{ marginBottom: "10px" }}>
            <label>Salary:</label>
            <input
              type="number"
              value={driveDetails.salary}
              onChange={(e) => setDriveDetails({ ...driveDetails, salary: e.target.value })}
              required
            />
          </div>
          <div style={{ marginBottom: "10px" }}>
            <label>Description:</label>
            <textarea
              value={driveDetails.description}
              onChange={(e) => setDriveDetails({ ...driveDetails, description: e.target.value })}
              required
            />
          </div>
          <div style={{ marginBottom: "10px" }}>
            <label>Eligible CGPA:</label>
            <input
              type="number"
              value={driveDetails.eligibleCGPA}
              onChange={(e) => setDriveDetails({ ...driveDetails, eligibleCGPA: e.target.value })}
              required
            />
          </div>
          <div style={{ marginBottom: "10px" }}>
            <label>Standing Arrear Limit:</label>
            <input
              type="number"
              value={driveDetails.standingArrearLimit}
              onChange={(e) =>
                setDriveDetails({ ...driveDetails, standingArrearLimit: e.target.value })
              }
              required
            />
          </div>
          <div style={{ marginBottom: "10px" }}>
            <label>History of Arrear Allowed:</label>
            <input
              type="checkbox"
              checked={driveDetails.historyOfArrearAllowed}
              onChange={(e) =>
                setDriveDetails({ ...driveDetails, historyOfArrearAllowed: e.target.checked })
              }
            />
          </div>
          <button type="submit">Add Drive</button>
        </form>
      )}

      {/* Display Upcoming Drives */}
      {selectedCompany && (
        <div style={{ marginTop: "20px" }}>
          <h2>Upcoming Drives for {selectedCompany.companyName}</h2>
          {upcomingDrives.length > 0 ? (
            upcomingDrives.map((drive) => (
              <div
                key={drive.id}
                onClick={() => {
                  setSelectedDrive(drive);
                  fetchRegisteredStudents(drive.driveId);
                }}
                style={{
                  border: selectedDrive?.id === drive.driveId ? "2px solid blue" : "1px solid black",
                  padding: "10px",
                  marginBottom: "10px",
                  cursor: "pointer",
                }}
              >
                <h3>{drive.description}</h3>
                <p>Date: {drive.date}</p>
                <p>Salary: {drive.salary}</p>
              </div>
            ))
          ) : (
            <p>No upcoming drives available.</p>
          )}
        </div>
      )}

      {/* Display Registered Students */}
      {selectedDrive && (
        <div style={{ marginTop: "20px" }}>
          <h2>Registered Students for Drive: {selectedDrive.description}</h2>
          {registeredStudents.length > 0 ? (
            <ul>
              {registeredStudents.map((student) => (
                <li key={student.id}>
                  {student.name} ({student.email})
                </li>
              ))}
            </ul>
          ) : (
            <p>No students registered for this drive.</p>
          )}
        </div>
      )}
    </div>
  );
};

export default StaffDashboard;
