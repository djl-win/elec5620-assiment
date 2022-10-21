import React from 'react';

import {urlFor} from "../client";

const NFT = ({nft: {_id, image, createBy, url}}) => {
    return (
        <div>
            <img className="w-full rounded-lg" alt="user_post" src={urlFor(image).width(500).url()} />
        </div>
    );
};

export default NFT;