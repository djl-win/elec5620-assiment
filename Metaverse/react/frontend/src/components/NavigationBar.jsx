import React from 'react';
import {Link, useNavigate} from "react-router-dom";
import {IoMdAdd, IoMdSearch} from "react-icons/io";

const NavigationBar = ({searchContent, setSearchContent, user}) => {
    const navigate = useNavigate();

    if(!user) return null;

    return(
        <div className="flex w-full gap-2 mt-5 pb-7 md:gap-5">
            <div className="flex w-full px-2 justify-start items-center rounded-md bg-white border-none outline-none focus-within:shadow-sm">
                <IoMdSearch fontSize={20} className="ml-1" />
                <input type="text" onChange={(e) => setSearchContent(e.target.value)} placeholder="Search" value={searchContent} onFocus={() => navigate('/search')} className="w-full p-2 bg-white outline-none" />
            </div>
            <div className="flex gap-2">
                <Link to={`user_profile/${user?._id}`} className="hidden md:block">
                    <img src={user.image} className="w-14 h-12 rounded-full" alt="portrait" />
                </Link>
                <Link to='create_NFT' className="flex gap-2 w-10 h-10 md:w-12 md:h-12 justify-center items-center bg-black text-white rounded-lg">
                    <IoMdAdd  />
                </Link>
            </div>
        </div>
    )
}

export default NavigationBar;