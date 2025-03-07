import React from 'react';
import axiosInstance from '../AxiosInstance';

export const ExerciseWorkout = () => {

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const workoutId = formData.get('workoutId');
    const exerciseId = formData.get('exerciseId');
    const sets = formData.get('sets');
    const reps = formData.get('reps');
    const weight = formData.get('weight');
    const url = `/fitness/workouts/${workoutId}/exercises?exerciseId=${exerciseId}&sets=${sets}&reps=${reps}&weight=${weight}`;

    try {
      const response = await axiosInstance.post(url, null);

      if (response.status === 200) {
          console.log('Success:', response.data);
          alert('Request was successful!');
          window.location.reload();
      } else {
          alert('ID not present. Please check the ID and try again.');
          throw new Error('Network response was not ok');
      }
  } catch (error) {
      if (error.response && error.response.status === 404) {
          alert("Resource not found. Please check the ID's and try again.");
      } else {
          alert('An error occurred. Please try again later.');
      }
      console.error('Error:', error);
  }
  }

  return (
    <div className="container mt-5" style={{ border: '1px solid black', padding: '10px' }}>
      <div className='mb-4'><h6>Add Exercise to wokout</h6></div>
      <form onSubmit={handleSubmit}>
        <div className="row mb-3">
          <div className="col-md-6">
            <label htmlFor="workoutId" className="form-label">Workout ID:</label>
            <input type="number" className="form-control" id="workoutId" maxLength="5" name="workoutId" required />
          </div>
          <div className="col-md-6">
            <label htmlFor="exerciseId" className="form-label">Exercise ID:</label>
            <input type="number" className="form-control" id="exerciseId" maxLength="5" name="exerciseId" required />
          </div>
        </div>
        <div className="row mb-3">
          <div className="col-md-4">
            <label htmlFor="sets" className="form-label">Sets:</label>
            <input type="number" className="form-control" id="sets" maxLength="5" name="sets" required />
          </div>
          <div className="col-md-4">
            <label htmlFor="reps" className="form-label">Reps:</label>
            <input type="number" className="form-control" id="reps" maxLength="5" name="reps" required />
          </div>
          <div className="col-md-4">
            <label htmlFor="weight" className="form-label">Weight:</label>
            <input type="number" className="form-control" id="weight" maxLength="5" name="weight" required />
          </div>
        </div>
        <button type="submit" className="btn btn-primary">Submit</button>
      </form>
    </div>
  )
}
