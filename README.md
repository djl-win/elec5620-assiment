USYD 2022 S2 COMP5620 stage 2 Group 3 -- backend

- This project is a NFTs trading platform, and the backend was implemented by Springboot + SpringMvc + Mybatis + PostgreSQL.
- Here are some details of the project.

### Project information:
1. JDK version: 1.8
2. Database: PostgreSQL 9.6.24
3. Maven: 3.6.3
4. Port: 8080
5. Database file in the root directory

### Third party libraries:
1. SpringBoot: 2.7.2
2. mybatis: 3.5.9
3. druid: 1.1.16
4. ehcache: 2.10.9.2
5. lombok: 1.18.24
6. fastjson: 1.2.48
7. javamail: 1.6.7
8. freemarker: 2.3.31
9. commons-codec: 1.15

### Functionalities:
1. Login function
2. Register function
3. Apply wallet function
4. Charge wallet function
5. Nft automatically generate function
6. Like NFTs function
7. Collect NFTs function
8. View NFTs information function
9. Transaction function
10. Order function
11. Rank NFTs function
12. Public NFTs transaction function

### Step to deploy the environment:
1. Maven install
2. Maven clear
3. Maven package

### Website:  
http://localhost:8080/5620/pages/mIndex.html

### Note!!!
You must modify the NftServiceImpl file in the impl folder under the service layer by changing the address in the generateNft method to "File download location/USYD-2022-S2-ELEC5620-Stage 2/front-end/CreatX/src/assets/ nftWorks/"

##### Example:
String nftImageUrl = "D:/USYD-2022-S2-ELEC5620-Stage 2/front-end/CreatX/src/assets/nftWorks/" + signature + ".png";