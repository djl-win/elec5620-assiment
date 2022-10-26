import React,{useState} from 'react';
import {Routes, Route} from "react-router-dom";

import {NavigationBar, Search, CreateNFT, NFTsDetail, Feed} from "../components";

const NFTs = ({user}) => {
    const [searchContent, setSearchContent] = useState('')

    return (
        <div className="px-2 md:px-5">
            <div className="bg-gray-50">
                <NavigationBar searchContent={searchContent} setSearchContent={setSearchContent} user={user}/>
            </div>
            <div className="h-full">
                <Routes>
                    <Route path="/" element={<Feed />} />
                    <Route path="/category/:categoryId" element={<Feed />} />
                    <Route path="/NFT_detail/:nftId" element={<NFTsDetail user={user} />} />
                    <Route path="/create_NFT" element={<CreateNFT user={user} />} />
                    <Route path="/search" element={<Search searchContent={searchContent} setSearchContent={setSearchContent} />} />
                </Routes>
            </div>
        </div>
    );
};

export default NFTs;
