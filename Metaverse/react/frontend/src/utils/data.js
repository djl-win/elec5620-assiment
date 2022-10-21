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