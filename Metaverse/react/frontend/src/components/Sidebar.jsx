import React from "react";
import {NavLink, Link} from "react-router-dom";
import {RiHomeFill} from "react-icons/ri";
import {IoIosArrowForward} from "react-icons/io";

import logo from '../assets/logo.gif';

const isNotActiveStyle = 'flex items-center px-5 gap-3 text-gray-500 hover:text-black transition-all duration-200 ease-in-out capitalize';
const isActiveStyle = 'flex items-center px-5 gap-3 font-extrabold border-r-2 border-black transition-all duration-200 ease-in-out capitalize';

const categories = [
    {name: 'category_1'},
    {name: 'category_2'},
    {name: 'category_3'},
    {name: 'category_4'},
]

const Sidebar = ({user, closeToggle}) => {
    const handleCloseSidebar = () => {
        if(closeToggle) closeToggle(false);
    }

    return (
        <div className="flex flex-col justify-between bg-white h-full overflow-y-scroll min-w-200 hide-scrollbar">
            <div className="flex flex-col">
                <Link to="/" className="flex px-4 gap-2 my-2 pt-1 w-190 items-center" onClick={handleCloseSidebar}>
                    <img src={logo} alt="logo" className="w-10" />
                </Link>
                <div className="flex flex-col gap-5">
                    <NavLink to="/" className={({isActive}) => isActive ? isActiveStyle : isNotActiveStyle} onClick={handleCloseSidebar}>
                        <RiHomeFill />
                        Home
                    </NavLink>
                    <h3 className="mt-2 px-5 text-base 2xl:text-xl">Discover NFTs Categories</h3>
                    {categories.slice(0, categories.length).map((category) => (
                        <NavLink to={`/category/${category.name}`} className={({isActive}) => isActive ? isActiveStyle : isNotActiveStyle} onClick={handleCloseSidebar} key={category.name}>
                            {category.name}
                        </NavLink>
                    ))}
                </div>
            </div>
            {user && (
                <Link to={`user_profile/${user._id}`} className="flex mx-4 my-4 mb-4 gap-2 p-4 items-center bg-white rounded-lg shadow-lg" onClick={handleCloseSidebar}>
                    <img src={user.image} className="w-10 h-10 rounded-full" alt="portrait" />
                    <h1>{user.userName}</h1>
                </Link>
            )}
        </div>
    )
}

export default Sidebar
