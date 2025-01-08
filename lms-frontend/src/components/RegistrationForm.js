// RegistrationForm.jsx
import React, { useState } from 'react';
import { Formik, Field, Form, ErrorMessage } from 'formik';
import * as Yup from 'yup';

const RegistrationForm = () => {
    const [formSubmitted, setFormSubmitted] = useState(false);

    const initialValues = {
        name: '',
        email: '',
        password: '',
        confirmPassword: ''
    };

    const validationSchema = Yup.object({
        name: Yup.string().required('Name is required'),
        email: Yup.string().email('Invalid email address').required('Email is required'),
        password: Yup.string().min(6, 'Password must be at least 6 characters').required('Password is required'),
        confirmPassword: Yup.string()
            .oneOf([Yup.ref('password'), null], 'Passwords must match')
            .required('Confirm password is required')
    });

    const handleSubmit = (values) => {
        console.log('Form data', values);
        setFormSubmitted(true);
    };

    return (
        <div className="container mt-5">
            <h2 className="text-center">Register</h2>

            {formSubmitted && <div className="alert alert-success">Registration successful!</div>}

            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
            >
                <Form>
                    <div className="mb-3">
                        <label htmlFor="name" className="form-label">Name</label>
                        <Field type="text" id="name" name="name" className="form-control" />
                        <ErrorMessage name="name" component="div" className="text-danger" />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email</label>
                        <Field type="email" id="email" name="email" className="form-control" />
                        <ErrorMessage name="email" component="div" className="text-danger" />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Password</label>
                        <Field type="password" id="password" name="password" className="form-control" />
                        <ErrorMessage name="password" component="div" className="text-danger" />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="confirmPassword" className="form-label">Confirm Password</label>
                        <Field type="password" id="confirmPassword" name="confirmPassword" className="form-control" />
                        <ErrorMessage name="confirmPassword" component="div" className="text-danger" />
                    </div>

                    <button type="submit" className="btn btn-primary w-100">Register</button>
                </Form>
            </Formik>
        </div>
    );
};

export default RegistrationForm;
