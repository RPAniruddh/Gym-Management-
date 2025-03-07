import React from 'react'
import { useNavigate } from 'react-router-dom';
import NavBar from '../../Components/NavBar/NavBar'
import Footer from '../../Components/Footer'
import ShowMembers from './showMembers';
import Excercise from '../../Components/Excercise';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUserPlus, faDumbbell } from "@fortawesome/free-solid-svg-icons";

export default function Admin() {
  const navigate = useNavigate();

  const handleAddMember = () => {
    navigate('/signUp');
  };

  return (
    <>
    <NavBar />
    <div className="container">
      <ShowMembers />
      <Excercise />
      <div style={{ display: 'flex', flexDirection: 'row',   gap:"10px"}}>
        <div style={{ border: '1px solid black', padding: '20px', marginTop: '30px', width: '100%', maxWidth: '600px' }}>
          <p>Want to add new members?</p>
          <button className="btn btn-sm btn-success" onClick={handleAddMember} style={{ padding: '5px 10px', fontSize: '14px' }}>Add Member <FontAwesomeIcon icon={faUserPlus} /></button>
        </div>
        
        <div style={{ border: '1px solid black', padding: '20px', marginTop: '30px', width: '100%', maxWidth: '600px' }}>
          <p>Want to add new exercises?</p>
          <button className="btn btn-sm btn-success" onClick={() => navigate('/addExercise')} style={{ padding: '5px 10px', fontSize: '14px' }}>Add Exercise</button>
        </div>

      </div>
      <Footer />
    </div>
    </>
  )
}
