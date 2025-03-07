import React, { useState } from 'react';
import axios from 'axios';
import axiosInstance from '../../AxiosInstance';
import { toast } from 'react-toastify';

const RegistrationFrom = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    dateOfBirth: ''
  });

  const handleChange = (e) => {
    console.log("clicked");
    const { id, value } = e.target;
    setFormData({ ...formData, [id]: value });
  };

 

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log(formData);
      const response = await axiosInstance.post('/members/add', formData);
      console.log('Form submitted successfully:', response.data);
      toast.success('Form submitted successfully!');
      window.history.back();
    } catch (error) {
      if (error.response && error.response.data.includes('Email already exists')) {
        toast.error(error.response.data);
      } else {
        console.error('Error submitting form:', error);
      }
    }
  };

  return (
    <div className="container">
      <main>
        <div className="py-5 text-center">
          <img className="d-block mx-auto mb-4" src="../../public/Assets/Logo.png" alt="Brand Logo" width="90" height="90" style={{ borderRadius: "50%" }} />
          <h2>Registration form</h2>
          <p className="lead">Below is an example form built entirely with Bootstrap’s form controls. Each required form group has a validation state that can be triggered by attempting to submit the form without completing it.</p>
        </div>

        <div className="d-flex justify-content-center align-items-center">
          <div className="col-md-7 col-lg-8 ">
            <h4 className="mb-3">Add details</h4>
            <form className="needs-validation" noValidate="" onSubmit={handleSubmit}>
              <div className="row g-3">
                <div className="col-sm-6">
                  <label htmlFor="firstName" className="form-label">First name</label>
                  <input type="text" className="form-control" id="firstName" maxLength="10" placeholder="John" value={formData.firstName} onChange={handleChange} required />
                  <div className="invalid-feedback">
                    Valid first name is required.
                  </div>
                </div>

                <div className="col-sm-6">
                  <label htmlFor="lastName" className="form-label">Last name</label>
                  <input type="text" className="form-control" id="lastName" maxLength="10" placeholder="Doe" value={formData.lastName} onChange={handleChange} required />
                  <div className="invalid-feedback">
                    Valid last name is required.
                  </div>
                </div>

                <div className="col-12">
                  <label htmlFor="email" className="form-label">Email</label>
                  <input type="email" className="form-control" id="email" maxLength="20" placeholder="you@gmail.com" value={formData.email} onChange={handleChange} required />
                  <div className="invalid-feedback">
                    Please enter a valid email address for shipping updates.
                  </div>
                </div>

                <div className="col-12">
                  <label htmlFor="phoneNumber" className="form-label">Phone Number</label>
                  <input type="tel" className="form-control" id="phoneNumber" placeholder="1234567890" maxLength="10" value={formData.phoneNumber} onChange={handleChange} required />
                  <div className="invalid-feedback">
                    Please enter your phone number.
                  </div>
                </div>

                <div className="col-12">
                  <label htmlFor="dateOfBirth" className="form-label">Date of birth</label>
                  <input type="date" className="form-control" id="dateOfBirth" value={formData.dateOfBirth} onChange={handleChange} required />
                  <div className="invalid-feedback">
                    Please enter a valid date.
                  </div>
                </div>
              </div>

              <hr className="my-4" />

              <button className="w-100 btn btn-primary btn-lg" type="submit" disabled={!formData.firstName || !formData.lastName || !formData.email || !formData.phoneNumber || !formData.dateOfBirth}>Register</button>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
}

export default RegistrationFrom;