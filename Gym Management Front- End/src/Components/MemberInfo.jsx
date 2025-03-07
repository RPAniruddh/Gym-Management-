import { useNavigate } from 'react-router-dom';
import { React, useState, useEffect } from 'react';
import axiosInstance from '../AxiosInstance';

export default function MemberInfo() {
  const [member, setMember] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchMemberData = async () => {
      const role = localStorage.getItem('role');
      if (role === 'User') {
        const email = localStorage.getItem('email');
        if (email) {
          try {
            const response = await axiosInstance.get(`/members/getByEmail/${email}`, {
              headers: {
                Authorization: `Bearer ${localStorage.getItem('jwtToken')}`
              }
            });
            setMember(response.data);
            localStorage.setItem('memberId', response.data.id);
            console.log("hello",response.data);
          } catch (error) {
            console.error('Error fetching member data by email:', error);
          }
        }
      } else {
        const memberId = localStorage.getItem('memberId');
        if (memberId) {
          try {
            const response = await axiosInstance.get(`/members/get/${memberId}`);
            setMember(response.data);
          } catch (error) {
            console.error('Error fetching member data by ID:', error);
          }
        }
      }
    };

    fetchMemberData();
  }, []);

  const handleUpdateMember = async () => {
    if (confirm('Are you sure you want to update the Member Details?')){
      navigate('/updateMember', { state: { member } });
    }
   
  };

  return (
    <>
      <div className="container mt-5">
        <h2>Member Details</h2>
        <div className="card mt-3">
          <div className="card-body">
            {member ? (
              <div className="row">
                <div className="col-md-6">
                  <p className="card-text"><strong>ID:</strong> {member.id}</p>
                  <p className="card-text"><strong>First Name:</strong> {member.firstName}</p>
                  <p className="card-text"><strong>Last Name:</strong> {member.lastName}</p>
                </div>
                <div className="col-md-6">
                  <p className="card-text"><strong>Email:</strong> {member.email}</p>
                  <p className="card-text"><strong>Phone Number:</strong> {member.phoneNumber}</p>
                  <p className="card-text"><strong>Date of Birth:</strong> {new Date(member.dateOfBirth).toLocaleDateString()}</p>
                  <p className="card-text"><strong>Created At:</strong> {new Date(member.createdAt).toLocaleString()}</p>
                </div>
              </div>
            ) : (
              <p>Loading member data...</p>
            )}
          </div>
          <div style={{ border: '1px solid black', padding: '20px', marginTop: '30px', width: '100%' }}>
            <p>Want to update member details?</p>
            <button className="btn btn-sm btn-success" onClick={handleUpdateMember} style={{ padding: '5px 10px', fontSize: '14px' }}>Update Member</button>
          </div>
        </div>
      </div>
    </>
  );
}
