import React, { useState, useEffect } from 'react'
import NavBar from '../../Components/NavBar/NavBar'
import Footer from '../../Components/Footer'
import MemberInfo from '../../Components/MemberInfo'
import MembershipInfo from '../../Components/MembershipInfo/MembershipInfo'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowRight } from "@fortawesome/free-solid-svg-icons";

export default function User() {
    const [showMembershipInfo, setShowMembershipInfo] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => {
            setShowMembershipInfo(true);
        }, 500); 

        return () => clearTimeout(timer);
    }, []);

    return (
        <>
            <NavBar />
            <div style={{ display: 'flex', flexDirection: 'column' }}>
                <MemberInfo />
                {showMembershipInfo && <MembershipInfo />}
            </div>
            <div style={{ border: '1px solid black', padding: '10px', marginTop: '20px' }}>
                <p>Want to go to Fitness Center?</p>
                <button onClick={() => window.location.href = '/fitnessCenter'}>Go <FontAwesomeIcon icon={faArrowRight} size='sm'/></button>
            </div>
            <Footer />
        </>
    )
}
