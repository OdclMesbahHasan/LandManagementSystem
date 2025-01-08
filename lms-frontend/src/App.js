import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import RegistrationForm from './components/RegistrationForm';
import HomePage from './pages/Home'; // Import as default

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} /> {/* Home Page */}
                <Route path="/register" element={<RegistrationForm />} /> {/* Registration Page */}
            </Routes>
        </Router>
    );
};

export default App;
