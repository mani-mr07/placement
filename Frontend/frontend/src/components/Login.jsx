import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import './logining.css';

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [otp, setOtp] = useState(""); // OTP input for verification
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [otpSent, setOtpSent] = useState(false); // Flag for OTP step
  const [userType, setUserType] = useState("student");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const endpoint = userType === "student" ? "/api/Auth/studentLogin" : "/api/Auth/staffLogin";
      const response = await axios.post(`http://localhost:8080${endpoint}`, {
        email,
        password,
      });

      if (response.status === 200) {
        console.log("Login successful");

        if (userType === "student") {
          // OTP sent, move to OTP step
          setOtpSent(true);
        } else {
          const { accessToken, staff } = response.data;

          // Store tokens and navigate to staff dashboard
          localStorage.setItem("accessToken", accessToken);
          localStorage.setItem("userId", staff.id);
          navigate("/staff-dashboard");
        }
      }
    } catch (error) {
      setError("Invalid email or password");
    } finally {
      setLoading(false);
    }
  };

  const handleOtpVerification = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      if (!otp) {
        console.log("OTP is not set");
        return;
      }
      const response = await axios.post("http://localhost:8080/api/Auth/verify-otp", {
        studentDTO: {
          email: email,
          password: password,
        },
        otp: otp,
      });
      if (response.status === 200) {
        console.log("OTP verified successfully");
        const { accessToken, student } = response.data;

        // Store tokens and navigate to student dashboard
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("userId", student.id);
        console.log("OTP Verification Response:", response.data);

        navigate("/student-dashboard");
      }
    } catch (error) {
      setError("Invalid OTP");
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = () => {
    if (userType === "student") {
      window.location.href = "/Register";
    } else {
      alert("Only students can register.");
    }
  };

  return (
    <div id="total">
      <div id="content">
        <div id="img">
          <img src="/imo.jpg" alt="Login Visual" />
        </div>
        <div id="box">
          <h2>PLACEMENT APP</h2>
          <h2><strong>Login to your Account</strong></h2>
          <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "10px" }}>
            <button onClick={() => setUserType("student")} style={{ flex: 1, marginRight: "5px" }}>
              Student Login
            </button>
            <button onClick={() => setUserType("staff")} style={{ flex: 1, marginLeft: "5px" }}>
              Staff Login
            </button>
          </div>

          {!otpSent ? (
            <form onSubmit={handleLogin}>
              <div>
                <label>Email:</label>
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  style={{ width: "96%", padding: "8px", marginBottom: "10px" }}
                />
              </div>
              <div>
                <label>Password:</label>
                <input
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                  style={{ width: "96%", padding: "8px", marginBottom: "10px" }}
                />
              </div>
              <button type="submit" disabled={loading}>
                {loading ? "Logging in..." : "Login"}
              </button>
              {error && <p style={{ color: "red", marginTop: "10px" }}>{error}</p>}
            </form>
          ) : (
            <form onSubmit={handleOtpVerification}>
              <div>
                <label>Enter OTP:</label>
                <input
                  type="text"
                  value={otp}
                  onChange={(e) => setOtp(e.target.value)}
                  required
                  style={{ width: "96%", padding: "8px", marginBottom: "10px" }}
                />
              </div>
              <button type="submit" disabled={loading}>
                {loading ? "Verifying OTP..." : "Verify OTP"}
              </button>
              {error && <p style={{ color: "red", marginTop: "10px" }}>{error}</p>}
            </form>
          )}

          {userType === "student" && !otpSent && (
            <button onClick={handleRegister}>
              Register
            </button>
          )}
        </div>
      </div>
    </div>
  );
}

export default Login;
