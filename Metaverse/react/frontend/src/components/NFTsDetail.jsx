import React,{useState, useEffect} from 'react';
import {MdDownloadForOffline} from "react-icons/md";
import {Link, useParams} from "react-router-dom";
import {v4 as uuid_v4} from 'uuid';

import {client, urlFor} from "../client";
import {nftDetailQuery, nftDetailMoreNftQuery} from "../utils/data";
import Layout from "./Layout";
import Spinner from "./Spinner";
import {data} from "autoprefixer";
import {BsFillArrowUpRightCircleFill} from "react-icons/bs";

const NFTsDetail = ({user}) => {
    const [NFTs, setNFTs] = useState(null);
    const [nftDetail, setNftDetail] = useState(null);
    const [comment, setComment] = useState('');
    const [addingComment, setAddingComment] = useState(false);
    const {nftId} = useParams();

    const reply = () => {
        if(comment) {
            setAddingComment(true);

            client.patch(nftId).setIfMissing({comments: []}).insert('after', 'comments[-1]', [{
                comment,
                _key: uuid_v4(),
                createdBy: {
                    _type: 'createdBy',
                    _ref: user._id
                }
            }]).commit().then(() => {
                fetchNftDetail();
                setComment('');
                setAddingComment(false);
            })
        }
    }

    const fetchNftDetail = () => {
        let query = nftDetailQuery(nftId);

        if (query) {
            client.fetch(query).then((data) => {
                setNftDetail(data[0]);

                if(data[0]) {
                    query = nftDetailMoreNftQuery(data[0]);

                    client.fetch(query).then((response) => setNFTs(response));
                }
            })
        }
    }

    // useEffect should be above all if statement.
    useEffect(() => {
        fetchNftDetail();
    }, [nftId])

    if(!nftDetail) return <Spinner message="loading NFT Detail..." />

    return (
        <>
        <div className="flex xl:flex-row flex-col m-auto bg-white" style={{maxWidth: '1500px', borderRadius: '32px'}}>
            <div className="flex justify-center items-center md:items-start flex-initial">
                <img src={nftDetail?.image && urlFor(nftDetail.image).url()} className="rounded-t-3xl rounded-b-lg" alt="nft" />
            </div>
            <div className="w-full p-5 flex-1 xl:min-w-620">
                <div className="flex items-center justify-between">
                    <div className="flex gap-2 items-center">
                        <a href={`${nftDetail.image?.asset?.url}?dl=`} download className="flex w-10 h-10 rounded-full items-center justify-center text-dark text-xl bg-white opacity-60 hover:opacity-90 hover:shadow-md outline-none">
                            <MdDownloadForOffline />
                        </a>
                    </div>
                    <a href={nftDetail.url} target="_blank" rel="noreferrer" className="flex gap-2 p-2 pl-4 pr-4 items-center bg-white font-bold text-black rounded-full opacity-60 hover:opacity-90 hover:shadow-md">
                        {nftDetail.url}
                    </a>
                </div>
                <div>
                    <h1 className="font-bold text-4xl break-words mt-2">{nftDetail.title}</h1>
                    <p className="mt-3">{nftDetail.about}</p>
                </div>
                <Link to={`/user_profile/${nftDetail.createdBy?._id}`} className="flex gap-2 mt-5 items-center bg-white rounded-lg">
                    <img className="w-8 h-8 rounded-full object-cover" src={nftDetail.createdBy?.image} alt="user_profile" />
                    <p className="font-semibold capitalize">{nftDetail.createdBy?.userName}</p>
                </Link>
                <h2 className="mt-4 text-2xl">Comments</h2>
                <div className="max-h-360 overflow-y-auto">
                    {nftDetail?.comments?.map((comment, i) => (
                        <div className="flex gap-2 mt-4 items-center bg-white rounded-lg" key={i}>
                            <img src={comment.createdBy.image} alt="user_profile" className="w-8 h-8 rounded-full cursor-pointer" />
                            <div className="flex flex-col">
                                <p className="font-bold">{comment.createdBy.userName}</p>
                                <p>{comment.comment}</p>
                            </div>
                        </div>
                    ))}
                </div>
                <div className="flex flex-wrap mt-6 gap-4">
                    <Link to={`/user_profile/${user?._id}`}>
                        <img className="w-8 h-8 mt-1.5 rounded-full cursor-pointer" src={user?.image} alt="user_profile" />
                    </Link>
                    <input type="text" placeholder="Write your comment here" value={comment} onChange={(e) => setComment(e.target.value)} className="flex-1 border-gray-100 focus:border-gray-300 border-2 p-2 rounded-2xl outline-none" />
                    <button type="button" className="px-4 py-2 font-bold text-base bg-white rounded-full hover:shadow-md outlined-none" onClick={reply}>
                        {addingComment ? 'Replying' : 'Reply'}
                    </button>
                </div>
            </div>
        </div>
        {/*{console.log(NFTs)}*/}
        {NFTs?.length > 0 ? (
            <>
                <h2 className="text-center font-bold text-2xl mt-8 mb-4">
                    More like this
                </h2>
                <Layout NFTs={NFTs} />
            </>
        ) : (
            <Spinner message="Loading more NFTs" />
        )}
        </>
    );
};

export default NFTsDetail;