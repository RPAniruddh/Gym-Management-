import axios from 'axios';
import React from 'react'
import { useLocation } from 'react-router-dom';
import axiosInstance from '../AxiosInstance';
import { toast } from 'react-toastify';
const UpdateMember = () => {

  const location = useLocation();
  const member = location.state?.member || {};


  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
        const response = await axiosInstance.put(`/members/update/${member.id}`, formData, {
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.status === 200) {
            toast.success('Details updated successfully');
            window.history.back();
        } else {
            toast.error('Failed to update member details');
        }
    } catch (error) {
        console.error('Error updating member:', error);
        toast.error('An error occurred while updating the member');
    }
};



  const [formData, setFormData] = React.useState({
    firstName: member.firstName || '',
    lastName: member.lastName || '',
    email: member.email || '',
    phoneNumber: member.phoneNumber || '',
    dateOfBirth: member.dateOfBirth ? new Date(member.dateOfBirth).toISOString().split('T')[0] : ''
  });

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [id]: value
    }));
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
                  <input type="text" className="form-control" id="firstName" placeholder="John" value={formData.firstName} onChange={handleChange} required="" />
                  <div className="invalid-feedback">
                    Valid first name is required.
                  </div>
                </div>

                <div className="col-sm-6">
                  <label htmlFor="lastName" className="form-label">Last name</label>
                  <input type="text" className="form-control" id="lastName" placeholder="Doe" value={formData.lastName} onChange={handleChange} required="" />
                  <div className="invalid-feedback">
                    Valid last name is required.
                  </div>
                </div>

                <div className="col-12">
                  <label htmlFor="email" className="form-label">Email</label>
                  <input type="email" className="form-control" id="email" placeholder="you@example.com" value={formData.email} onChange={handleChange} readOnly />
                  <div className="invalid-feedback">
                    Please enter a valid email address for shipping updates.
                  </div>
                </div>

                <div className="col-12">
                  <label htmlFor="phoneNumber" className="form-label">Phone Number</label>
                  <input type="tel" className="form-control" id="phoneNumber" placeholder="123-456-7890" value={formData.phoneNumber} onChange={handleChange} required="" />
                  <div className="invalid-feedback">
                    Please enter your phone number.
                  </div>
                </div>

                <div className="col-12">
                  <label htmlFor="dateOfBirth" className="form-label">Date of birth</label>
                  <input type="date" className="form-control" id="dateOfBirth" value={formData.dateOfBirth} onChange={handleChange} required="" />
                  <div className="invalid-feedback">
                    Please enter a valid date.
                  </div>
                </div>
              </div>

              <hr className="my-4" />

              <button className="w-100 btn btn-primary btn-lg" type="submit">Update Member</button>
            </form>
          </div>
        </div>
      </main>
    </div>
  )
}

export default UpdateMember;
