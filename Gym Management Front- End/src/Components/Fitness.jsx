import React, { useState } from 'react'
import Excercise from './Excercise'
import AddWorkout from './AddWorkout'
import AllWorkouts from './AllWorkouts'
import { ExerciseWorkout } from './ExerciseWorkout'
import NavBar from './NavBar/NavBar'
import Footer from './Footer'

const Fitness = () => {
    const [isTrigger, setIsTrigger] = useState(false);

    const handleWorkoutAdded = () => {
        setIsTrigger((prev) => !prev); // Toggle the isTrigger state to re-render AllWorkouts
    };

    console.log(isTrigger);
    

    return (
        <>
            <NavBar />
            <Excercise />
            <AddWorkout onWorkoutAdded={handleWorkoutAdded} />
            <AllWorkouts isTrigger={isTrigger} />
            <ExerciseWorkout />
            <Footer />
        </>
    )
}


export default Fitness;