import React from 'react';

const Loader: React.FC = () => {
    return (
        <div className="loader flex justify-center items-center">
            <div className="loader-spinner border-t-4 border-blue-500 rounded-full animate-spin h-12 w-12"></div>
        </div>
    );
};

export default Loader;