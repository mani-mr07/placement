import React, { useEffect, useState } from "react";
import axios from "axios";

const StudentDashboard = () => {
  const [upcomingDrives, setUpcomingDrives] = useState([]);
  const [registeredDrives, setRegisteredDrives] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedDrive, setSelectedDrive] = useState(null); // Holds details of the selected drive
  const [isModalVisible, setIsModalVisible] = useState(false); // Controls modal visibility

  // Fetch upcoming drives
  const fetchUpcomingDrives = async () => {
    const userId = localStorage.getItem("userId");
    if (!userId) {
      console.error("User ID not found. Please log in.");
      return;
    }
    setLoading(true);
    try {
      const response = await axios.get(
        `http://localhost:8080/api/students/${userId}/retrieve-non-registered-drive`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );
      setUpcomingDrives(response.data);
    } catch (error) {
      console.error("Error fetching upcoming drives:", error);
    } finally {
      setLoading(false);
    }
  };

  // Fetch registered drives
  const fetchRegisteredDrives = async () => {
    const userId = localStorage.getItem("userId");
    if (!userId) {
      console.error("User ID not found. Please log in.");
      return;
    }
    setLoading(true);
    try {
      console.log("accessToken" ,`${localStorage.getItem("accessToken")}`);

      const response = await axios.get(
        `http://localhost:8080/api/students/${userId}/retrieve-registered-drive`,
        {
          headers: {
            'Content-type':'application/json',
             Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );
      setRegisteredDrives(response.data);
    } catch (error) {
      console.error("Error fetching registered drives:", error);
    } finally {
      setLoading(false);
    }
  };

  // Register for a drive
  const registerForDrive = async (driveId,userId) => {
    console.log(driveId);
    console.log(userId);
    const longdriveId=Number(driveId);
    const longuserId=Number(userId);
    const userid = localStorage.getItem("userId");
    if (!userid) {
      console.error("User ID not found. Please log in.");
      return;
    }
    console.log("User ID from localStorage:", userid);
console.log("User ID being sent in the URL:", userId);

    
    
    try {
      console.log("accessToken" ,`${localStorage.getItem("accessToken")}`);

      await axios.post(
        `http://localhost:8080/api/students/${longdriveId}/${userid}/registerForDrive`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );
      alert("Successfully registered for the drive!");
      fetchUpcomingDrives(); // Refresh upcoming drives
      fetchRegisteredDrives(); // Refresh registered drives
      closeModal(); // Close the modal if it's open
    } catch (error) {
      console.error("Error registering for drive:", error);
      alert("Failed to register for the drive.");
    }
  };

  // View details of a drive
  const viewDetails = async (driveId) => {
    setLoading(true);
    try {
      console.log("accessToken" ,`${localStorage.getItem("accessToken")}`);
      const response = await axios.get(
        `http://localhost:8080/api/students/reterive/${driveId}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );
      setSelectedDrive(response.data); // Set the selected drive details
      setIsModalVisible(true); // Show the modal
    } catch (error) {
      console.error("Error fetching drive details:", error);
      alert("Failed to fetch drive details.");
    } finally {
      setLoading(false);
    }
  };

  // Close the modal
  const closeModal = () => {
    setIsModalVisible(false);
    setSelectedDrive(null);
  };

  useEffect(() => {
    fetchUpcomingDrives();
    fetchRegisteredDrives();
  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <h1>Welcome to the Student Dashboard</h1>

      {loading && <p>Loading...</p>}

      {/* Registered Drives Section */}
      <section style={{ marginTop: "20px" }}>
        <h2>Your Registered Drives</h2>
        {registeredDrives.length > 0 ? (
          <ul>
            {registeredDrives.map((drive) => (
              <li key={drive.id}>
                <strong>{drive.companyName}</strong> - {drive.date}
              </li>
            ))}
          </ul>
        ) : (
          <p>You have not registered for any drives yet.</p>
        )}
      </section>

      {/* Upcoming Drives Section */}
      <section style={{ marginTop: "20px" }}>
        <h2>Upcoming Drives</h2>
        {upcomingDrives.length > 0 ? (
          <ul>
            {upcomingDrives.map((drive) => (
              <li key={drive.id} style={{ marginBottom: "10px" }}>
                <strong>{drive.companyName}</strong> - {drive.date}
                <button
                  style={{ marginLeft: "10px" }}
                  onClick={() => viewDetails(drive.driveId,localStorage.getItem("userId"))}
                >
                  View Details
                </button>
              </li>
            ))}
          </ul>
        ) : (
          <p>No upcoming drives available.</p>
        )}
      </section>

      {/* Modal for Viewing Drive Details */}
      {isModalVisible && selectedDrive && (
        <div
          style={{
            position: "fixed",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            backgroundColor: "white",
            padding: "20px",
            boxShadow: "0 4px 8px rgba(0, 0, 0, 0.2)",
            zIndex: 1000,
          }}
        >
          <h3>{selectedDrive.companyName}</h3>
          <p><strong>Date:</strong> {selectedDrive.date}</p>
          <p><strong>Description:</strong> {selectedDrive.description}</p>
          <button onClick={() => registerForDrive(selectedDrive.driveId,localStorage.getItem("userId"))}>
            Register
          </button>
          <button onClick={closeModal} style={{ marginLeft: "10px" }}>
            Close
          </button>
        </div>
      )}

      {/* Overlay for the Modal */}
      {isModalVisible && (
        <div
          onClick={closeModal}
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            backgroundColor: "rgba(0, 0, 0, 0.5)",
            zIndex: 999,
          }}
        ></div>
      )}
    </div>
  );
};

export default StudentDashboard;
