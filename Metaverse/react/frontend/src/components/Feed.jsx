import React, {useState, useEffect} from 'react';
import {useParams} from "react-router-dom";

import {client} from "../client";
import Layout from "./Layout";
import Spinner from "./Spinner";
import search from "./Search";
import {feedQuery, searchQuery} from "../utils/data";
import {data} from "autoprefixer";

const Feed = () => {

    const [loading, setLoading] = useState(false);
    const [NFTs, setNFTs] = useState(null);
    const {categoryId} = useParams();

    useEffect(() => {
        setLoading(true);

        if (categoryId) {
            const query = searchQuery(categoryId);

            client.fetch(query).then((data) => {
                setNFTs(data);
                setLoading(false);
            })
        } else {
            client.fetch(feedQuery).then((data) => {
                setNFTs(data);
                setLoading(false);
            })
        }
    }, [categoryId])

    if (loading) return <Spinner message="We are adding new NFTs to your feed!"/>

    return (
        <div>
            {NFTs && <Layout NFTs={NFTs} />}
        </div>
    );
};

export default Feed;