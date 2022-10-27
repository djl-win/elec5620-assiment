# elec5620-assiment
临时文件夹
尝试使用React, Tailwind和Sanity重构前端, sanity作为临时后端

- React：用于构建用户界面的JavaScript库
- Tailwind：CSS框架
- Sanity：结构化内容平台，提供一个简单的后端框架（临时使用，搭建好前端后连接家乐的后端）

**运行方式**

**后端**
- npm install -g @sanity/cli
- sanity start

**前端**
- npm install
- npm start

**已知问题**
- UserProfile.jsx: line 93 用NFTs的length判断没有意义，当没有NFTs可以显示时，不能正确显示"There is no NFTs founded."
- NFTsDetail.jsx: line 24 comments在数据库对应的表为comment，会导致数据不能正常存入，但line 89的查询能正确返回评论内容