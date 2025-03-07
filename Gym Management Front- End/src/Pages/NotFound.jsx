import React from 'react';

const NotFound = () => {
    return (
        <div className="p-5 text-center bg-body-tertiary rounded-3">
            <svg className="bi mt-4 mb-3" style={{ color: 'var(--bs-indigo)' }} width="100" height="100">
                <use xlinkHref="#bootstrap"></use>
            </svg>
            <h1 className="text-body-emphasis">404 - Page Not Found</h1>
            <p className="col-lg-8 mx-auto fs-5 text-muted">
                Oops! The page you are looking for does not exist. It might have been moved or deleted.
            </p>
            <div className="d-inline-flex gap-2 mb-5">
                <button className="d-inline-flex align-items-center btn btn-primary btn-sm px-4 rounded-pill" type="button" onClick={() => window.history.back()}>
                    Go Back
                </button>
            </div>
        </div>
    );
};

export default NotFound;