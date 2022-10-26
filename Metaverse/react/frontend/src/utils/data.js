export const categories = [
    {
        name: 'Category_1',
        image: 'https://cdn-icons-png.flaticon.com/512/4020/4020010.png',
    },
    {
        name: 'Category_2',
        image: 'https://cdn-icons-png.flaticon.com/512/4020/4020011.png',
    },
    {
        name: 'Category_3',
        image: 'https://cdn-icons-png.flaticon.com/512/4020/4020012.png',
    },
    {
        name: 'Category_4',
        image: 'https://cdn-icons-png.flaticon.com/512/4020/4020013.png',
    },
];

export const userQuery = (userId) => {
    const query = `*[_type == "user" && _id == '${userId}']`;

    return query;
};

export const searchQuery = (searchContent) => {
    const query = `*[_type == "nft" && title match '${searchContent}*' || category match '${searchContent}*' || about match '${searchContent}*']{
        _id,
        url,
        image {
            asset -> {
                url
            }
        },
        createdBy -> {
            _id,
            userName,
            image
        },
        save[] {
            _key,
            createdBy -> {
                _id,
                userName,
                image
            },
        },
    }`;

    return query;
};

export const feedQuery = `*[_type == 'nft'] | order(_createAt desc) {
    _id,
    url,
    image {
        asset -> {
            url
        }
    },
    createdBy -> {
        _id,
        userName,
        image
    },
    save[] {
        _key,
        createdBy -> {
            _id,
            userName,
            image
        },
    },
}`;