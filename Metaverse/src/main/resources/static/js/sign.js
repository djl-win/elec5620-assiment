new Vue({
    el: "#loginForm",
    data() {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('Please enter your password again'));
            } else if (value !== this.registerForm.userPassword) {
                callback(new Error('The passwords entered twice do not match!'));
            } else {
                callback();
            }
        };
        return {

            //verification code
            VerificationEl: {
                el1:"",
                el2:"",
                el3:"",
                el4:"",
                el5:"",
                el6:"",
            },
            //login page visible
            showLoginForm: "block",

            //verification code page visible
            VisibleVerification: "none",


            codeUrl: '#',

            //register page visible
            dialogVisible: false,

            //login form
            loginForm: {
                userPassword: "",
                userUsername: "",
            },

            //register form
            registerForm: {
                userPassword: "",
                userUsername: "",
                checkPassword: "",
                userDetailName: "",
                useDetailEmail: "",
                userDetailPhone: "",
                mailCode: ""
            },

            checked: false,

            //rule
            rules: {
                userUsername: [
                    {required: true, message: "Username cannot be empty", trigger: "blur"},
                    {max: 10, message: "Cannot be longer than 10 characters", trigger: "blur"},
                ],
                userPassword: [
                    {required: true, message: "Password cannot be empty", trigger: "blur"},
                    {max: 10, message: "Cannot be longer than 10 characters", trigger: "blur"},
                ],
                checkPassword: [
                    {required: true, message: "Please enter your password again", trigger: "blur"},
                    {validator: validatePass, trigger: "blur", required: true}
                ],
                userDetailName: [
                    {required: true, message: "Please enter your full name", trigger: "blur"}
                ],
                userDetailEmail: [
                    {required: true, message: "Please enter your email", trigger: "blur"}
                ],
                userDetailPhone: [
                    {required: true, message: "Please enter your phone number", trigger: "blur"}
                ],
                mailCode: [
                    {required: true, message: "Please enter mail code", trigger: "blur"},
                ]
            },
        };
    },

    mounted() {
        //get from url of the register form availability
        var showRegister = location.href.split("=");
        this.dialogVisible = Boolean(showRegister[1]);

        //after mounted loading username and password
        if (localStorage.getItem("news")) {
            this.loginForm = JSON.parse(localStorage.getItem("news"))
            this.checked = true
        }
    },

    methods: {

        //Sent email  code
        sendEmail() {
            var _this = this;
            axios({
                method: "get",
                url: "/5620/users/?emailAddress=" + _this.registerForm.userDetailEmail
            }).then(function (res) {
                    // console.log(res.data)
                    //response code 40011
                    if (res.data.code === 40011) {
                        _this.$message({
                            message: res.data.msg,
                            type: "success",
                            showClose: true,
                        })
                    } else if (res.data.code === 40010) {
                        _this.$message({
                            message: res.data.msg,
                            type: "error",
                            showClose: true,
                        })
                    }
                }
            ).catch((err) => {
                _this.$message({
                    message: "Fail to sent code to your email",
                    type: "error",
                    showClose: true,
                });
            });
        },
        //verify verification code
        submitVerificationCode(form){
            //Combined verification code
            const checkCode = this.VerificationEl.el1 + this.VerificationEl.el2 + this.VerificationEl.el3 + this.VerificationEl.el4 + this.VerificationEl.el5 + this.VerificationEl.el6;
            console.log(checkCode);
            console.log(checkCode.length)
            if(checkCode.length !== 6){
                this.VerificationEl.el1="";
                this.VerificationEl.el2="";
                this.VerificationEl.el3="";
                this.VerificationEl.el4="";
                this.VerificationEl.el5="";
                this.VerificationEl.el6="";
                window.alert("Please enter a 6-digit verification code！")
                return;
            }
            var _this = this;
            this.$refs[form].validate((valid) => {
                if (valid) {
                    axios({
                        method: "get",
                        url: "/5620/users/"+checkCode,
                    }).then(function (res) {
                            console.log(res.data)
                            //response code 40011
                            if (res.data.code === 40011) {
                                //integration is comflex，spring needs cross domain，index page is different，redirect is complex，cross domain in react
                                location.href = "http://localhost:3000/5620/pages/index.html?username=" + res.data.data;
                                // location.href = "http://localhost:3000/5620/pages/index.html";
                            } else if (res.data.code === 40010) {
                                //clear
                                _this.showLoginForm = "block";
                                _this.VisibleVerification = "none";
                                _this.VerificationEl.el1="";
                                _this.VerificationEl.el2="";
                                _this.VerificationEl.el3="";
                                _this.VerificationEl.el4="";
                                _this.VerificationEl.el5="";
                                _this.VerificationEl.el6="";

                                _this.$message({
                                    message: res.data.msg,
                                    type: "error",
                                    showClose: true,
                                })
                            }
                        }
                    ).catch((err) => {
                        _this.$message({
                            message: "Fail to log in",
                            type: "error",
                            showClose: true,
                        });
                    });
                } else {
                    return false;
                }
            });
        },


        //register page
        login(form) {
            var _this = this;
            this.$refs[form].validate((valid) => {
                if (valid) {
                    axios({
                        method: "post",
                        url: "/5620/users",
                        data: _this.loginForm
                    }).then(function (res) {
                            //response code 40011
                            if (res.data.code === 40011) {
                                _this.showLoginForm = "none";
                                _this.VisibleVerification = "block";

                                _this.$notify({
                                    title:"Simulate phone verification code",
                                    message: "You phone verification code is " + res.data.data,
                                    type: "success",
                                    showClose: true,
                                    duration:0
                                })

                                // location.href = "/5620/pages/detail.html";
                            } else if (res.data.code === 40010) {
                                //clear
                                _this.loginForm.userUsername = "";
                                _this.loginForm.userPassword = "";
                                _this.$message({
                                    message: res.data.msg,
                                    type: "error",
                                    showClose: true,
                                })
                            }
                        }
                    ).catch((err) => {
                        _this.$message({
                            message: "Fail to log in",
                            type: "error",
                            showClose: true,
                        });
                    });
                } else {
                    return false;
                }
            });
        },

        //record the info
        remember(data) {
            this.checked = data
            if (this.checked) {
                localStorage.setItem("news", JSON.stringify(this.loginForm))
            } else {
                localStorage.removeItem("news")
            }
        },

        //forget pas
        forgetpas() {
            this.$message({
                type: "info",
                message: "Not completed😥",
                showClose: true
            })
        },

        //register function
        register(form) {
            var _this = this;
            this.$refs[form].validate((valid) => {
                if (valid) {
                    axios({
                        method: "put",
                        url: "/5620/users",
                        data: _this.registerForm
                    }).then(function (res) {
                            //response code 10011
                            if (res.data.code === 10011) {
                                // setToken(res.data.token);
                                // localStorage.setItem("USERNAME", res.data.username);
                                _this.$message({
                                    message: res.data.msg,
                                    type: "success",
                                    showClose: true,
                                })
                                //dialogVisible set to false
                                _this.dialogVisible = false;
                                //clear
                                _this.registerForm.userUsername = "";
                                _this.registerForm.userPassword = "";
                                _this.registerForm.checkPassword = "";
                                _this.registerForm.userDetailName = "";
                                _this.registerForm.userDetailEmail = "";
                                _this.registerForm.userDetailPhone = "";
                                _this.registerForm.mailCode = "";

                            } else if (res.data.code === 60010) {

                                //clear verification code
                                _this.registerForm.mailCode = "";
                                _this.$message({
                                    message: res.data.msg,
                                    type: "error",
                                    showClose: true,
                                })
                            }else if (res.data.code === 10010) {
                                //clear
                                _this.registerForm.userUsername = "";
                                _this.registerForm.userPassword = "";
                                _this.registerForm.checkPassword = "";
                                _this.registerForm.userDetailName = "";
                                _this.registerForm.userDetailEmail = "";
                                _this.registerForm.userDetailPhone = "";
                                _this.registerForm.mailCode = "";

                                _this.$message({
                                    message: res.data.msg,
                                    type: "error",
                                    showClose: true,
                                })
                            }
                        }
                    ).catch((err) => {
                        //clear
                        _this.registerForm.userUsername = "";
                        _this.registerForm.userPassword = "";
                        _this.registerForm.checkPassword = "";
                        _this.registerForm.userDetailName = "";
                        _this.registerForm.userDetailEmail = "";
                        _this.registerForm.userDetailPhone = "";
                        _this.registerForm.mailCode = "";

                        _this.$message({
                            message: "Fail to sign up",
                            type: "error",
                            showClose: true,
                        });
                    });
                } else {
                    return false;
                }
            });
        },

        //close dialog function
        handleClose(done) {
            this.$confirm('Confirm to close？')
                .then(_ => {
                    done();
                })
                .catch(_ => {
                });
        }
    },
});