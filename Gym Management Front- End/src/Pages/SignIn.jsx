import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import NavBar from '../Components/NavBar/NavBar'
import Footer from '../Components/Footer'
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const SignIn = () => {

  useEffect(() => {
    localStorage.clear();
  }, []);

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:9194/auth/authenticate', {
        email,
        password,
      });
      const token = response.data;
      localStorage.setItem('jwtToken', token);
      const decodedToken = jwtDecode(token);
      let userRole = decodedToken.roles;
      let userEmail = decodedToken.sub;
      localStorage.setItem('role', userRole);
      localStorage.setItem('email', userEmail);
      console.log(userRole);
      console.log(userEmail);

      if (userRole.toLowerCase() === 'admin') {
        navigate('/Admin');
      } else {

        try {
          const userResponse = await axios.get(`http://localhost:1235/members/getByEmail/${userEmail}`, {
            headers: {
              'Authorization': `Bearer ${token}`
            }
          });
          if (userResponse.data) {
            toast.success("welcome Back")
            navigate('/user');
          } else {
            navigate('/userRegistrationForm');
          }
        } catch (userError) {
          console.error('Error checking user existence!', userError);
          navigate('/userRegistrationForm');
        }
      }

      console.log(response.data);
    } catch (error) {
      if (error.response && error.response.status === 403) {
        toast.error('Either email or password is wrong, please check again.');
      } else {
        console.error('There was an error!', error);
      }
    }
  };

  return (
    <>
      <div className="container col-xl-10 col-xxl-8 px-4 py-5">
        <div className="row align-items-center g-lg-5 py-5">
          <div className="col-lg-7 text-center text-lg-start">
            <h1 className="display-4 fw-bold lh-1 text-body-emphasis mb-3">Welcome Back!</h1>
            <p className="col-lg-10 fs-4">Sign in to continue your fitness journey with us. Let's achieve your goals together!</p>
          </div>
          <div className="col-md-10 mx-auto col-lg-5">
            <form className="p-4 p-md-5 border rounded-3 bg-body-tertiary" onSubmit={handleSubmit}>
              <div className="form-floating mb-3">
                <input
                  type="email"
                  className="form-control"
                  id="floatingInput"
                  placeholder="name@example.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
                <label htmlFor="floatingInput">Email address</label>
              </div>
              <div className="form-floating mb-3">
                <input
                  type="password"
                  className="form-control"
                  id="floatingPassword"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
                <label htmlFor="floatingPassword">Password</label>
              </div>
              <button className="w-100 btn btn-lg btn-primary" type="submit">Sign In</button>
              <hr className="my-4" />
              <small className="text-body-secondary">By clicking Sign up, you agree to the terms of use.</small>
            </form>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default SignIn;