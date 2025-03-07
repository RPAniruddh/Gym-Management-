import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './MembershipInfo.css';
import { AddMembership } from '../../Pages/Admin/AddMembership';
import axiosInstance from '../../AxiosInstance';

export default function MembershipInfo() {
    const [membershipDetails, setMembershipDetails] = useState(null);
    const [error, setError] = useState(null);
    const memberId = localStorage.getItem("memberId");
    const token = localStorage.getItem('jwtToken');
    const role = localStorage.getItem('role');
    console.log(role);


    useEffect(() => {
        if (memberId) {
            const source = axios.CancelToken.source();
            console.log("role", role);
            
            axiosInstance.get(`/memberships/${memberId}`, {
                cancelToken: source.token
            })
                .then(response => {
                    setMembershipDetails(response.data);
                })
                .catch(error => {
                    if (axios.isCancel(error)) {
                        console.log('Request canceled', error.message);
                    } else if (error.response && error.response.status === 404) {
                        if (role.toLowerCase() === 'admin') {
                            setError(
                                <div className='errorStyle'>
                                    Add the Membership
                                    <AddMembership memberId={memberId} />
                                </div>
                            );
                        } else {
                            setError(
                                <div className='errorStyle'>
                                    Wait for the Admin to add membership.
                                </div>
                            );
                        }
                    } else {
                        setError("There was an error fetching the membership details!");
                        console.error(error);
                    }
                });
    
            return () => {
                source.cancel("Component unmounted");
            };
        } else {
            setError("No membership ID found in local storage.");
        }
    }, []);
    
    if (error) {
        return <div>{error}</div>;
    }
    
    if (!membershipDetails) {
        return <div>Loading...</div>;
    }
 
const handleDeactivate = () => {
    if (confirm('Are you sure you want to deactivate the plan?')) {
        axiosInstance.post(`/memberships/${memberId}/deactivate`)
            .then(response => {
                alert("Membership deactivated successfully!");
                window.location.reload();
            })
            .catch(error => {
                setError("There was an error deactivating the membership!");
                console.error(error);
            });
    }
}
    const handleRenew = () => {
        if (confirm('Are you sure you want to renew the plan?')) {
            axiosInstance.put(`/memberships/${memberId}/renew`)
                .then(response => {
                    alert("Membership renewed successfully!");
                    window.location.reload();
                })
                .catch(error => {
                    setError("There was an error renewing the membership!");
                    console.error(error);
                });
        }
    }

    const handleChangePlan = () => {
        if (confirm('Are you sure you want to change the plan?')) {
            const newType = membershipDetails.membershipType === 'BASIC' ? 'PREMIUM' : 'BASIC';
            axiosInstance.put(`/memberships/${memberId}/upgrade?newType=${newType}`)
                .then(response => {
                    alert(`Membership upgraded to ${newType} successfully!`);
                    window.location.reload();
                })
                .catch(error => {
                    setError("There was an error upgrading the membership!");
                    console.error(error);
                });
        }
    }

    return (
        <div className="container mt-5">
            <h2>Membership Details</h2>
            <div className="card mt-3">
                <div className="card-body">
                    <div className="row">
                        <p className="card-text"><strong>Membership Type:</strong> {membershipDetails.membershipType}</p>
                        <p className="card-text"><strong>Status:</strong> {membershipDetails.status}</p>
                        <p className="card-text"><strong>Start Date:</strong> {new Date(membershipDetails.startDate).toLocaleDateString()}</p>
                        <p className="card-text"><strong>End Date:</strong> {new Date(membershipDetails.endDate).toLocaleDateString()}</p>
                        <p className="card-text"><strong>Created At:</strong> {new Date(membershipDetails.createdAt).toLocaleString()}</p>
                    </div>
                </div>
                {role.toLowerCase() === 'admin' && (
                    <div className='errorStyle'>
                        <p>Want to update membership details?</p>
                        <div className="buttonContainerStyle">
                            <button className="btn btn-sm btn-danger buttonStyle" onClick={handleDeactivate} >Deactivate</button>
                            <button className="btn btn-sm btn-success buttonStyle" onClick={handleRenew} >Renew</button>
                            <button className="btn btn-sm btn-info buttonStyle" onClick={handleChangePlan} >Change plan</button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}