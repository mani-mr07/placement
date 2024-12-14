import { useState } from "react";
import axios from "axios";

function Register() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    phone: "",
    resume: "",
    skills: "",
    cgpa: "",
    arrears: "",
    historyOfArrear: false,
    placed: false,
  });
  
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(null);

    try {
      const response = await axios.post("http://localhost:8080/api/students", formData);

      if (response.status === 200) {
        setSuccess("Registration successful!");
        // Redirect to login page after 2 seconds
        setTimeout(() => {
          window.location.href = "/"; // Assuming "/" is your login page route
        }, 2000);
      }
    } catch (error) {
      setError("Registration failed. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: "400px", margin: "auto", padding: "20px", border: "1px solid #ddd" }}>
      <h2>Student Registration</h2>
      <form onSubmit={handleRegister}>
        <div>
          <label>Name:</label>
          <input type="text" name="name" value={formData.name} onChange={handleChange} required />
        </div>
        <div>
          <label>RegisterNumber:</label>
          <input type="Number" name="registerNumber" value={formData.registerNumber} onChange={handleChange} required />
        </div>
        <div>
          <label>Email:</label>
          <input type="email" name="email" value={formData.email} onChange={handleChange} required />
        </div>
        <div>
          <label>Password:</label>
          <input type="password" name="password" value={formData.password} onChange={handleChange} required />
        </div>
        <div>
          <label>Department:</label>
          <input type="text" name="department" value={formData.department} onChange={handleChange} required />
        </div>
        <div>
          <label>Date of Birth:</label>
          <input type="date" name="dob" value={formData.dob} onChange={handleChange} required />
        </div>
        <div>
          <label>Phone:</label>
          <input type="tel" name="phone" value={formData.phone} onChange={handleChange}  pattern="[0-9]{10}"   // Allows exactly 10 digits
    maxLength="10"   required />
        </div>
        <div>
          <label>Resume URL:</label>
          <input type="url" name="resume" value={formData.resume} onChange={handleChange} required />
        </div>
        <div>
          <label>Skills:</label>
          <input type="text" name="skills" value={formData.skills} onChange={handleChange} required />
        </div>
        <div>
          <label>CGPA:</label>
          <input type="number" step="0.01" name="cgpa" value={formData.cgpa} onChange={handleChange} max="10" required />
        </div>
        <div>
          <label>Arrears:</label>
          <input type="number" name="arrears" value={formData.arrears} onChange={handleChange} required />
        </div>
        <div>
          <label>History of Arrear:</label>
          <input type="checkbox" name="historyOfArrear" checked={formData.historyOfArrear} onChange={handleChange} />
        </div>
        
        <button type="submit" disabled={loading}>
          {loading ? "Registering..." : "Register"}
        </button>
        {error && <p style={{ color: "red", marginTop: "10px" }}>{error}</p>}
        {success && <p style={{ color: "green", marginTop: "10px" }}>{success}</p>}
      </form>
    </div>
  );
}

export default Register;
