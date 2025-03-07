import { useState } from 'react'
import {Route, Routes} from 'react-router-dom'


import './App.css'
import  Home  from './Pages/Home'
import RegistrationFrom from './Pages/Admin/RegistrationFrom'
import Admin from './Pages/Admin/Admin'
import SignUp from './Pages/SignUp'
import SignIn from './Pages/SignIn'
import User from './Pages/User/User'
import AddExercise from './Pages/Admin/AddExercise'
import AddWorkout from './Components/AddWorkout'
import UpdateForm from './Components/UpdateMember'
import UserWorkouts from './Components/UserWorkouts'
import Fitness from './Components/Fitness'
import UserRegistrationForm from './Pages/User/UserRegistrationForm'
import About  from './Pages/About'
import NotFound from './Pages/NotFound'

function App() {

  return (
    <Routes>
      <Route path='/' Component={Home}></Route>
      <Route path='*' Component={NotFound}></Route>
      <Route path='/signUp' Component={SignUp}></Route>
      <Route path='/signIn' Component={SignIn}></Route>
      <Route path='/About' Component={About}></Route>
      <Route path='/registrationForm' Component={RegistrationFrom}></Route>
      <Route path='/userRegistrationForm' Component={UserRegistrationForm}></Route>
      <Route path='/admin' Component={Admin}></Route>
      <Route path='/user' Component={User}></Route>
      <Route path='/fitnessCenter' Component={Fitness}></Route>
      <Route path='/addExercise' Component={AddExercise}></Route>
      <Route path='/addWorkout' Component={AddWorkout}></Route>
      <Route path='/updateMember' Component={UpdateForm}></Route>
      <Route path='/userWorkout' Component={UserWorkouts}></Route>
    </Routes>

  )
}

export default App
