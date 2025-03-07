import React, { useEffect, useState } from 'react';
import axiosInstance from '../AxiosInstance';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBan } from "@fortawesome/free-solid-svg-icons";


const Excercise = () => {
    const [exercises, setExercises] = useState([]);
    const role = localStorage.getItem('role');
    console.log(role);
    

    useEffect(() => {
        const fetchExercises = async () => {
            try {
                const response = await axiosInstance.get('/fitness/exercises');
                setExercises(response.data);
            } catch (error) {
                console.error('There was an error fetching the exercises!', error);
            }
        };

        fetchExercises();
    }, []);

    if (exercises.length === 0) {
        return (
            <div className="container mt-5" style={{ border: '2px solid red', textAlign: 'center', padding: '20px' }}>
                <h4>No Exercises are present</h4>
            </div>
        );
    }

    const handleDelete = async (id) => {
        if (confirm('Are you sure you want to delete this Exercise?')) {
            try {
                await axiosInstance.delete(`/fitness/exercises/${id}`);
                setExercises(exercises.filter(exercise => exercise.id !== id));
            } catch (error) {
                console.error('There was an error deleting the exercise!', error);
            }
        }

    }

    return (
        <div className="container mt-5">
            <h3>Exercise Available</h3>
            <table className="table table-hover table-bordered">
                <thead className="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Muscle Group</th>
                        <th>Created At</th>
                        {role.toLowerCase() === 'admin' && (
                            <th colSpan="2">Action</th>
                        )}
                    </tr>
                </thead>
                <tbody className='table-group-divider'>
                    {exercises.map(exercise => (
                        <tr key={exercise.id}>
                            <td>{exercise.id}</td>
                            <td>{exercise.name}</td>
                            <td>{exercise.category}</td>
                            <td>{exercise.muscleGroup}</td>
                            <td>{new Date(exercise.createdAt).toLocaleString()}</td>
                            {role.toLowerCase() === 'admin' && (
                                <td>
                                    <button className="btn btn-danger btn-sm" onClick={() => handleDelete(exercise.id)}><FontAwesomeIcon icon={faBan} /></button>
                                </td>
                            )}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );

};

export default Excercise;
