export default {
    name: 'nft',
    title: 'NFT',
    type: 'document',
    fields: [
        {
            name: 'title',
            title: 'Title',
            type: 'string'
        },
        {
            name: 'about',
            title: 'About',
            type: 'string'
        },
        {
            name: 'url',
            title: 'Url',
            type: 'url'
        },
        {
            name: 'category',
            title: 'Category',
            type: 'string'
        },
        {
            name: 'image',
            title: 'Image',
            type: 'image',
            options: {
                hotspot: true // 使图像显示时适应不同的长宽比
            }
        },
        {
            name: 'userId',
            title: 'UserId',
            type: 'string'
        },
        {
            name: 'createdBy',
            title: 'CreatedBy',
            type: 'createdBy' // 另一个document的引用
        },
        {
            name: 'save',
            title: 'Save',
            type: 'array',
            of: [{ type: 'save' }] //save组成的array
        },
        {
            name: 'comment',
            title: 'Comment',
            type: 'array',
            of: [{ type: 'comment' }]
        }
    ]
}