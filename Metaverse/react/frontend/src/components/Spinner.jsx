import React from 'react';
import {Rings} from "react-loader-spinner";

const Spinner = ({message}) => {
    return (
        <div className="flex flex-col w-full h-full justify-center items-center">
            <Rings height="80" width="80" color="#1FB9FF" radius="10" wrapperStyle={{}} wrapperClass="" visible={true} ariaLabel="rings-loading" />
            <p className="px-2 text-lg text-center">
                {message}
            </p>
        </div>
    );
};

export default Spinner;