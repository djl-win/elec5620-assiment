import React, {useState, useRef, useEffect} from "react";
import {HiMenu} from "react-icons/hi";
import {AiFillCloseCircle} from "react-icons/ai";
import {Link, Route, Routes} from 'react-router-dom';

import {Sidebar, UserProfile} from '../components'; //通过components下的index.js指向，避免多行引入
import {client} from "../client";
import {userQuery} from "../utils/data";
import Pins from "./Pins";

import logo from '../assets/logo.gif';

const Home = () => {

    const [toggleSidebar, setToggleSidebar] = useState(false);
    const [user, setUser] = useState(null);

    const userInfo = localStorage.getItem('user') !== 'undefined' ? JSON.parse(localStorage.getItem('user')) : localStorage.clear();

    useEffect(() => {
        
        const query = userQuery(userInfo?.sub);
        // console.log(query);
        client.fetch(query).then((data) => {
            setUser(data[0]);
        })

    }, []);

    return (
        <div className="flex bg-gray-50 md:flex-row flex-col h-screen transaction-height duration-75 ease-out">
            <div className="hidden md:flex h-screen flex-initial">
                <Sidebar/>
            </div>
            <div className="flex md:hidden flex-row">
                <HiMenu fontSize={40} className="cursor-point" onClick={() => setToggleSidebar(false)}/>
                <Link to="/">
                    <img src={logo} alt="logo" className="w-10"/>
                </Link>
                <Link to={`user-profile/${user?._id}`}>
                    <img src={user?.image} alt="portrait" className="w-10"/>
                </Link>
            </div>
        </div>
    )
}

export default Home
