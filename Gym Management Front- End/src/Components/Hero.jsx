import React from 'react'

const Hero = () => {
  return (
    <div className="container col-xxl-8 px-4 py-5">
      <div className="row flex-lg-row-reverse align-items-center g-5 py-5">
        <div className="col-10 col-sm-8 col-lg-6">
          <img
            src="../../public/Assets/Home.png"
            className="d-block mx-lg-auto img-fluid rounded"
            alt="Bootstrap Themes"
            width="700"
            height="500"
            loading="lazy"
          />
        </div>
        <div className="col-lg-6">
          <h2
            className="display-6 fw-bold text-body-emphasis lh-1 mb-3"
          >
            Your journey to a healthier life starts here!!
          </h2>
          <p className="lead" style={{ textAlign: 'justify' }}>
            Join us at FitLife Gym and transform your fitness routine with our
            , expert trainers, and supportive
            community. Whether you're a beginner or a seasoned athlete, we have
            everything you need to achieve your fitness goals.
          </p>
          <div className="d-grid gap-2 d-md-flex justify-content-md-start">
            <a href="https://www.health.harvard.edu/topics/exercise-and-fitness" className="btn btn-primary btn-sm" role="button" aria-pressed="true">
              Know more
            </a>
          </div>
        </div>
      </div>
    </div>

  )
}

export default Hero