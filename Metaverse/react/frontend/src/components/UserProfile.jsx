import React, {useState, useEffect} from "react";
import {AiOutlineLogout} from "react-icons/ai";
import {useParams, useNavigate} from "react-router-dom";

import {userQuery, userCreatedNFTsQuery, userSavedNFTsQuery} from "../utils/data";
import {client} from "../client";
import Layout from "./Layout";
import Spinner from "./Spinner";
import NFTs from "../container/NFTs";

const UserProfile = () => {
    const [user, setUser] = useState(null);
    const [NFT, setNFTs] = useState(null);
    const [text, setText] = useState('Created');
    const [activeButton, setActiveButton] = useState('created');
    const navigate = useNavigate();
    const {userId} = useParams();

    const randomImage = 'https://www.dmoe.cc/random.php'
    const activeButtonStyle = 'bg-red-500 text-white font-bold p-2 rounded-full w-20 outline-none';
    const notActiveButtonStyle = 'bg-primary text-black font-bold mr-4 p-2 rounded-full w-20 outline-none';

    const logout = () => {
        localStorage.clear();

        navigate('/login');
    }

    useEffect(() => {
        const query = userQuery(userId);

        client.fetch(query).then((data) => {
            setUser(data[0]);
        })
    }, [userId])

    useEffect(() => {
        if (text === 'Created') {
            const createdNFTsQuery = userCreatedNFTsQuery(userId);

            client.fetch(createdNFTsQuery).then((data) => {
                setNFTs(data);
            })
        } else {
            const savedNFTsQuery = userSavedNFTsQuery(userId);

            client.fetch(savedNFTsQuery).then((data) => {
                setNFTs(data);
            })
        }
    }, [text, userId])

    if(!user) {
        return <Spinner message="Loading profile..." />
    }

    return (
        <div className="relative pb-2 h-full justify-center items-center">
            <div className="flex flex-col pb-5">
                <div className="relative flex flex-col mb-7">
                    <div className="flex flex-col justify-center items-center">
                        <img src={randomImage} className="w-full h-360 2xl:h-500 shadow-lg object-cover" alt="banner"/>
                        <img className="rounded-full w-20 h-20 -mt-10 shadow-xl object-cover" src={user.image} alt="portrait" />
                        <h1 className="font-bold text-3xl text-center mt-4"> {user.userName}</h1>
                        <div className="absolute top-0 z-1 right-0 p-2">
                            {userId === user._id && (
                                <button type="button" onClick={logout} className=" bg-white p-2 rounded-full cursor-pointer outline-none shadow-md">
                                    <AiOutlineLogout color="blue" fontSize={20} />
                                </button>
                            )}
                        </div>
                    </div>
                    <div className="text-center mb-6">
                        <button type="button"
                                onClick={(e) => {
                                    setText(e.target.textContent);
                                    setActiveButton('created');
                                }}
                                className={`${activeButton === 'created' ? activeButtonStyle : notActiveButtonStyle}`}
                        >
                            Created
                        </button>
                        <button type="button"
                                onClick={(e) => {
                                    setText(e.target.textContent);
                                    setActiveButton('saved');
                                }}
                                className={`${activeButton === 'saved' ? activeButtonStyle : notActiveButtonStyle}`}
                        >
                            Saved
                        </button>
                    </div>
                    {NFTs?.length ? (
                        <div className="px-2">
                            <Layout NFTs={NFT} />
                        </div>
                    ) : (
                        <div className="flex justify-center items-center w-full font-bold text-xl mt-2">
                            There is no NFTs founded.
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}

export default UserProfile
