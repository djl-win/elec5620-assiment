import React, {useState} from 'react';
import {Link, useNavigate} from "react-router-dom";
import {v4 as uuid_v4} from 'uuid'
import {MdDownloadForOffline} from "react-icons/md";
import {AiTwotoneDelete} from "react-icons/ai";
import {BsFillArrowUpRightCircleFill} from "react-icons/bs";

import {client, urlFor} from "../client";
import {fetchUser} from "../utils/fetchUser";

const NFT = ({nft: {_id, image, createdBy, url, save}}) => {
    const [NFTHovered, setNFTHovered] = useState(false);

    const navigate = useNavigate();

    const userInfo = fetchUser();

    //1, [1, 2, 3] -> [1].length -> 1 -> !1 -> false -> !false -> true
    //4, [1, 2, 3] -> [].length -> 0 -> !0 -> true -> !true -> false
    // console.log(save);
    const alreadySaved = !!(save?.filter((item) => item.createdBy._id === userInfo?.sub))?.length;

    const saveNFT = (id) => {
        if(!alreadySaved) {
            client.patch(id).setIfMissing({save: []}).insert('after', 'save[-1]', [{
                _key: uuid_v4(),
                userId: userInfo.sub,
                createdBy: {
                    _type: 'createdBy',
                    _ref: userInfo.sub
                }
            }]).commit().then(() => {
                window.location.reload();
            })
        }
    }

    const deleteNFT = (id) => {
        client.delete(id).then(() => {
            window.location.reload();
        })
    }

    return (
        <div className="m-2">
            <div onMouseEnter={() => setNFTHovered(true)} onMouseLeave={() => setNFTHovered(false)} onClick={() => navigate(`/NFT_detail/${_id}`)} className="relative w-auto cursor-zoom-in hover:shadow-lg rounded-lg overflow-hidden transition-all duration-500 ease-in-out">
                <img className="w-full rounded-lg" alt="user_post" src={urlFor(image).width(500).url()} />
                {NFTHovered && (
                    <div className="flex flex-col absolute w-full h-full top-0 p-1 pr-2 pt-2 pb-2 z-50 justify-between" style={{height: '100%'}}>
                        <div className="flex items-center justify-between">
                            <div className="flex gap-2">
                                <a href={`${image?.asset?.url}?dl=`} download onClick={(e) => e.stopPropagation()} className="flex w-10 h-10 rounded-full items-center justify-center text-dark text-xl bg-white opacity-60 hover:opacity-90 hover:shadow-md outline-none">
                                    <MdDownloadForOffline />
                                </a>
                            </div>
                            {alreadySaved ? (
                                <button type="button" onClick={(e) => e.stopPropagation()} className="px-5 py-1 font-bold text-base bg-white opacity-60 hover:opacity-90 rounded-3xl hover:shadow-md outlined-none">
                                    {save?.length} Saved
                                </button>
                            ) : (
                                <button type="button" className="px-5 py-1 font-bold text-base bg-white opacity-60 hover:opacity-90 rounded-3xl hover:shadow-md outlined-none" onClick={(e) => {
                                    e.stopPropagation();
                                    saveNFT(_id);
                                }}>
                                    Save
                                </button>
                            )}
                        </div>
                        <div className="flex w-full gap-2 justify-between items-center">
                            {url && (
                                <a href={url} target="_blank" rel="noreferrer" onClick={(e) => e.stopPropagation()} className="flex gap-2 p-2 pl-4 pr-4 items-center bg-white font-bold text-black rounded-full opacity-60 hover:opacity-90 hover:shadow-md">
                                    <BsFillArrowUpRightCircleFill />
                                    {/*{url.length > 15 ? `${url.slice(0, 15)}...` : url}*/}
                                </a>
                            )}
                            {createdBy?._id === userInfo?.sub && (
                                <button type="button" className="p-2 font-bold text-base bg-white opacity-60 hover:opacity-90 rounded-3xl hover:shadow-md outlined-none" onClick={(e) => {
                                    e.stopPropagation();
                                    deleteNFT(_id);
                                }}>
                                    <AiTwotoneDelete />
                                </button>
                            )}
                        </div>
                    </div>
                )}
            </div>
            <Link to={`user_profile/${createdBy?._id}`} className="flex gap-2 mt-2 items-center">
                <img className="w-8 h-8 rounded-full object-cover" src={createdBy?.image} alt="user_profile" />
                <p className="font-semibold capitalize">{createdBy?.userName}</p>
            </Link>
        </div>
    );
};

export default NFT;