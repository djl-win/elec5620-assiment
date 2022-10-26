import React, {useState} from 'react';
import {AiOutlineCloudUpload} from "react-icons/ai";
import {MdDelete} from "react-icons/md";
import {useNavigate} from "react-router-dom";

import {client} from "../client";
import {categories} from "../utils/data";
import Spinner from "./Spinner";

const CreateNFT = ({user}) => {
    const [title, setTitle] = useState('');
    const [about, setAbout] = useState('');
    const [url, setUrl] = useState('');
    const [fields, setFields] = useState(null);
    const [category, setCategory] = useState(null);
    const [imageAsset, setImageAsset] = useState(null);
    const [loading, setLoading] = useState(false);
    const [imageTypeNG, setImageTypeNG] = useState(false);

    const navigate = useNavigate();

    const uploadImage = (e) => {
        const {type, name} = e.target.files[0];

        if(type === 'image/jpeg' || type === 'image/png' || type === 'image/gif' || type === 'image/svg'){
            setImageTypeNG(false);
            setLoading(true);
            client.assets.upload('image', e.target.files[0], {contentType: type, filename: name}).then((document) => {
                setImageAsset(document);
                setLoading(false);
            }).catch((error) => {
                console.log('Image upload error', error);
            })
        } else {
            setImageTypeNG(true);
        }
    }

    const submit = () => {
        if(title && about && url && imageAsset?._id && category){
            const doc = {
                _type:'nft',
                title,
                about,
                url,
                image: {
                    _type:'image',
                    asset: {
                        _type: 'reference',
                        _ref: imageAsset?._id
                    }
                },
                userId: user._id,
                createdBy: {
                    _type: 'createdBy',
                    _ref: user._id,
                },
                category,
            }
            client.create(doc).then(() => {
                navigate('/')
            })
        } else {
            setFields(true);

            setTimeout(() => setFields(false), 3000)
        }
    }

    return (
        <div className="flex flex-col mt-5 lg:h-4/5 justify-center items-center">
            {fields && (
                <p className="text-red-500 mb-5 text-xl transition-all duration-150 ease-in">
                    Please fill in all the fields.
                </p>
            )}
            <div className="flex lg:flex-row flex-col lg:p-5 p-3 lg:w-4/5 w-full justify-center items-center bg-white">
                <div className="bg-secondaryColor p-3 flex flex-0.7 w-full">
                    <div className="flex flex-col p-2 w-full h-420 justify-center items-center border-2 border-dotted border-gray-300">
                        {loading && <Spinner />}
                        {imageTypeNG && <p>The file type is not allowed, please reselect a file.</p>}
                        {!imageAsset ? (
                            <label>
                                <div className="flex flex-col h-full items-center justify-center">
                                    <div className="flex flex-col justify-center items-center">
                                        <p className="font-bold text-2xl">
                                            <AiOutlineCloudUpload />
                                        </p>
                                        <p className="text-lg">
                                            Click to upload
                                        </p>
                                    </div>
                                    <p className="mt-32 text-gray-400">
                                        Support JPG, PNG, GIF and SVG (each file should be less than 10MB).
                                    </p>
                                </div>
                                <input type="file" name="upload_nft" onChange={uploadImage} className="w-0 h-0" />
                            </label>
                        ) : (
                            <div className="relative h-full">
                                <img src={imageAsset?.url} alt="upload_nft" className="h-full w-full" />
                                <button type="button" className="absolute bottom-3 right-3 p-3 rounded-full bg-white text-xl cursor-pointer outline-none hover:shadow-md transition-all duration-599 eas-in-out" onClick={() => setImageAsset(null)}>
                                    <MdDelete />
                                </button>
                            </div>
                        )}
                    </div>
                </div>
                <div className="flex flex-1 flex-col gap-6 lg:pl-5 mt-5 w-full">
                    <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} placeholder="Add NFT title here" className="outline-none text-2xl sm:text-3xl font-bold border-b-2 border-gray-200 p-2" />
                    {user && (
                        <div className="flex gap-2 my-2 items-center bg-white rounded-lg">
                            <img src={user.image} className="w-8 h-8 rounded-full" alt="portrait" />
                            <p className="font-bold"> {user.userName}</p>
                        </div>
                    )}
                    <input type="text" value={about} onChange={(e) => setAbout(e.target.value)} placeholder="Add NFT introduction here" className="outline-none text-base sm:text-lg border-b-2 border-gray-200 p-2" />
                    <input type="url" value={url} onChange={(e) => setUrl(e.target.value)} placeholder="Add NFT url here" className="outline-none text-base sm:text-lg border-b-2 border-gray-200 p-2" />
                    <div className="flex flex-col">
                        <div>
                            <p className="mb-2 font-bold text-lg sm:text-xl">Choose NFT Category</p>
                            <select onChange={(e) => setCategory(e.target.value)} className="outline-none w-4/5 text-base border-b-2 border-gray-200 p-2 rounded-md cursor-pointer">
                                {categories.map((category) => (
                                    <option className="text-base border-0 outline-none capitalize bg-white text-black" value={category.name} key={category.name}>{category.name}</option>
                                ))}
                            </select>
                        </div>
                        <div className="flex justify-end items-end mt-5">
                            <button type="button" onClick={submit} className="bg-gray-300 text-dark font-bold p-2 rounded-full 2-30 outline-none">
                                Submit
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CreateNFT;