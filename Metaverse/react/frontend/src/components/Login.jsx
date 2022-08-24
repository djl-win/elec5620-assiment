import React from "react";
import {GoogleLogin, googleLogout} from "@react-oauth/google";
import {useNavigate} from "react-router-dom";
import {FcGoogle} from "react-icons/fc";

import loginBackground from '../assets/login_background.mp4';
import logo from '../assets/logo.gif';

import {user} from "../user";
import jwtDecode from "jwt-decode";

const Login = () => {

    const navigate = useNavigate();

    const responseGoogleSuccess = (response) => {
        const decoded = jwtDecode(response.credential);
        // console.log(decoded); //检查Google返回的数据
        localStorage.setItem('user', JSON.stringify(decoded));

        const {sub, name, picture} = decoded;

        /**
         * 将获取的数据传入sanity后端
         * @type {{image: string, _type: string, _id: string, userName: string}}
         */
        const doc = {
            _id: sub,
            _type: 'user', //指向sanity后端schema中的user
            userName: name,
            image: picture,
        }

        user.createIfNotExists(doc)
            .then(() => {
                navigate('/', {replace: true})
            })
    }
    const responseGoogleError = () => {
        console.log('Error')
    }

    return (
        <div className="flex justify-start items-center flex-col h-screen">
            <div className="relative w-full h-full">
                <video
                    src={loginBackground}
                    className="w-full h-full object-cover"
                    controls={false}
                    type="video/mp4"
                    autoPlay
                    muted
                    loop
                />
            </div>

            <div
                className="absolute flex flex-col justify-center items-center top-0 bottom-0 left-0 right-0 bg-blackOverlay">
                <div className="p-5">
                    <img src={logo} width="80px" alt="logo"/>
                </div>

                <div className="shadow-2xl">
                    <GoogleLogin
                        onSuccess={responseGoogleSuccess}
                        onError={responseGoogleError}
                    />
                </div>
            </div>
        </div>
    )
}

export default Login