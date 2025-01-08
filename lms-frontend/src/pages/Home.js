import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/HomePage.css';
import Logo from '../assets/OLMS Logo.png'; // Import the logo directly

const HomePage = () => {
    const navigate = useNavigate();

    const handleClick = () => {
        navigate('/register');
    };

    return (
        <div className="home-page" onClick={handleClick} style={{ cursor: 'pointer' }}>
            <div className="home-page-overlay">
                <img src={Logo} alt="Company Logo" className="background-logo" />
                {/*<hr className="separator" />*/}
            </div>
        </div>
    );
};

export default HomePage;
