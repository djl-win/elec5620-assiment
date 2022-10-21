import React from 'react';
import Masonry from "react-masonry-css";

import NFT from "./NFT";

const breakpointObject = {
    default: 4,
    3000: 6,
    2500: 5,
    1500: 3,
    1000: 2,
    500: 1
}

const Layout = ({NFTs}) => {
    return (
        <Masonry className="flex animate-slide-fwd" breakpointCols={breakpointObject}>
            {NFTs?.map((nft) => <NFT key={nft._id} nft={nft} className="w-max" />)}
        </Masonry>
    );
};

export default Layout;