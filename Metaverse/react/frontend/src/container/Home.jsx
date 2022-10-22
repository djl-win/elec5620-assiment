import React, {useState, useRef, useEffect} from "react";
import {HiMenu} from "react-icons/hi";
import {AiFillCloseCircle} from "react-icons/ai";
import {Link, Route, Routes} from 'react-router-dom';

import {Sidebar, UserProfile} from '../components'; //通过components下的index.js指向，避免多行引入
import {client} from "../client";
import {userQuery} from "../utils/data";
import {fetchUser} from "../utils/fetchUser";
import NFTs from "./NFTs";

import logo from '../assets/logo.gif';

const Home = () => {

    const [toggleSidebar, setToggleSidebar] = useState(false);
    const [user, setUser] = useState(null);
    const scrollRef = useRef(null);

    const userInfo = fetchUser()

    useEffect(() => {

        const query = userQuery(userInfo?.sub);
        // console.log(query);
        client.fetch(query).then((data) => {
            // console.log(JSON.stringify(data));
            setUser(data[0]);
        })
    }, []);
    useEffect(() => {

        scrollRef.current.scrollTo(0, 0)
    }, [])

    return (
        <div className="flex flex-col bg-gray-50 md:flex-row h-screen transaction-height duration-75 ease-out">
            <div className="hidden md:flex h-screen flex-initial">
                <Sidebar user={user && user}/>
            </div>
            <div className="flex flex-row md:hidden">
                <div className="p-2 w-full flex flex-row justify-between items-center shadow-md">
                    <HiMenu fontSize={40} className="cursor-point" onClick={() => setToggleSidebar(true)}/>
                    <Link to="/">
                        <img src={logo} alt="logo" className="w-10"/>
                    </Link>
                    <Link to={`user_profile/${user?._id}`}>
                        <img src={user?.image} className="w-10 h-10 rounded-full" alt="portrait" />
                    </Link>
                </div>
                {toggleSidebar && (
                    <div className="fixed w-4/5 h-screen overflow-y-auto shadow-md z-10 animate-slide-in">
                        <div className="absolute w-full flex justify-end items-center p-2">
                            <AiFillCloseCircle fontSize={30} className="cursor-pointer"
                                               onClick={() => setToggleSidebar(false)}/>
                        </div>
                        <Sidebar user={user && user} closeToggle={setToggleSidebar}/>
                    </div>
                )}
            </div>

            <div className="flex-1 h-screen pb-2 overflow-y-scroll" ref={scrollRef}>
                <Routes>
                    <Route path="/*" element={<NFTs user={user && user}/>}/>
                    <Route path="/user_profile/:userId" element={<UserProfile/>}/>
                </Routes>
            </div>
        </div>
    )
}

export default Home
