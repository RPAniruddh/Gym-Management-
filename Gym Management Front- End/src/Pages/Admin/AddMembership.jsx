import React, { useState } from 'react';
import axiosInstance from '../../AxiosInstance';
import { toast } from 'react-toastify';

export const AddMembership = ({ memberId }) => {
    const [error, setError] = useState(null);

    const addMembership = () => {
        const selectedType = document.getElementById('membershipType').value;
    
        axiosInstance.post(`/memberships/${memberId}`, null, {
            params: { type: selectedType }
        })
            .then(response => {
                toast.success("Membership added successfully!");
                console.log("Membership ID:", response.data.id);
                window.location.reload();
            })
            .catch(error => {
                setError("There was an error adding the membership!");
                console.error(error);
            });
    };

    return (
        <div style={{ marginTop: '20px' }}>
            <select id="membershipType" style={{ marginRight: '10px' }}>
                <option value="BASIC">BASIC</option>
                <option value="PREMIUM">PREMIUM</option>
            </select>
            <button className="btn btn-sm btn-primary" onClick={addMembership} style={{ padding: '5px 10px', fontSize: '14px' }}>Add Membership</button>
        </div>
    )
}
