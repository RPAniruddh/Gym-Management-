# Gym Management 

![Flow Chart](https://github.com/RPAniruddh/Gym-Management-/blob/main/Docs/flow.png)

## Microservices Architecture

### 1. Member Management Service
- Manages member profiles and subscriptions

### 2. Fitness Tracking Service
- Tracks workout logs and fitness progress

### 3. Security Service
- Handles authentication and authorization with JWT tokens

## Use Cases

### Member Management Service
- Register members
- Track membership status
- Handle renewals and upgrades

### Fitness Tracking Service
- Log workouts
- Track sets, reps, and weights

### Security Service
- User authentication with JWT tokens
- Define roles

## Technical Stack
- Spring Boot for microservices
- Eureka for service discovery
- API Gateway for routing
- MySQL for database
- JWT tokens for security
- React with Vite for frontend

## Frontend Setup

### React with Vite
1. Install Vite:
   ```bash
   npm create vite@latest my-gym-app --template react
   cd my-gym-app
   npm install

