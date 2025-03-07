import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

const UserWorkouts = () => {
    const location = useLocation();
    const id = new URLSearchParams(location.search).get('id');
    const [workouts, setWorkouts] = useState([]);
    const token = localStorage.getItem('jwtToken');

    useEffect(() => {
        const fetchWorkouts = async () => {
            try {
                const response = await axios.get(`http://localhost:1235/fitness/workouts/${id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                const data = await response.data;
                setWorkouts(data);
            } catch (error) {
                console.error('Error fetching workouts:', error);
            }
        };

        if (id) {
            fetchWorkouts();
        }
    }, [id]);
    if (workouts.length === 0) {
        return <div>No workouts available</div>;
    }

    return (
        <div>
            <h2>User Workouts</h2>
            <table className="table table-hover">
                <thead className="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Exercise Name</th>
                        <th>Sets</th>
                        <th>Reps</th>
                        <th>Weight</th>
                    </tr>
                </thead>
                <tbody className='table-group-divider'>
                    {workouts.map(workout => (
                        <tr key={workout.id}>
                            <td>{workout.id}</td>
                            <td>{workout.exerciseName}</td>
                            <td>{workout.sets}</td>
                            <td>{workout.reps}</td>
                            <td>{workout.weight}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserWorkouts;