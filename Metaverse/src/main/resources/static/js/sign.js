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

            //绑定验证码
            VerificationEl: {
                el1:"",
                el2:"",
                el3:"",
                el4:"",
                el5:"",
                el6:"",
            },
            //登录页面的显示
            showLoginForm: "block",

            //验证码页面的显示
            VisibleVerification: "none",

            //验证码图片地址（未开发）
            codeUrl: '#',

            //新增表单的显示
            dialogVisible: false,

            //登录表单
            loginForm: {
                userPassword: "",
                userUsername: "",
            },

            //注册表单
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

            //规则
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

        //挂载完成式，加载用户名密码
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
                    //响应状态码为40011
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
        //检验验证码
        submitVerificationCode(form){
            //拼接验证码
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
                            //响应状态码为40011
                            if (res.data.code === 40011) {
                                //因为整合比较麻烦，spring需跨越，index页不同，跳转有些麻烦，在react中跨域即可，后期在调整！！
                                location.href = "http://localhost:3000/5620/pages/index.html?username=" + res.data.data;
                                // location.href = "http://localhost:3000/5620/pages/index.html";
                            } else if (res.data.code === 40010) {
                                //清空所有信息
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


        //登陆表单
        login(form) {
            var _this = this;
            this.$refs[form].validate((valid) => {
                if (valid) {
                    axios({
                        method: "post",
                        url: "/5620/users",
                        data: _this.loginForm
                    }).then(function (res) {
                            //响应状态码为40011
                            if (res.data.code === 40011) {
                                _this.showLoginForm = "none";
                                _this.VisibleVerification = "block";

                                _this.$message({
                                    message: "(Simulate phone verification code) You phone verification code is " + res.data.data,
                                    type: "success",
                                    showClose: true,
                                })

                                // location.href = "/5620/pages/detail.html";
                            } else if (res.data.code === 40010) {
                                //清空所有信息
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

        //忘记密码
        forgetpas() {
            this.$message({
                type: "info",
                message: "功能尚未开发额😥",
                showClose: true
            })
        },

        //注册功能
        register(form) {
            var _this = this;
            this.$refs[form].validate((valid) => {
                if (valid) {
                    axios({
                        method: "put",
                        url: "/5620/users",
                        data: _this.registerForm
                    }).then(function (res) {
                            //响应状态码为200
                            if (res.data.code === 10011) {
                                // setToken(res.data.token);
                                // localStorage.setItem("USERNAME", res.data.username);
                                _this.$message({
                                    message: res.data.msg,
                                    type: "success",
                                    showClose: true,
                                })
                                //dialogVisible设置为false
                                _this.dialogVisible = false;
                                //清空所有信息
                                _this.registerForm.userUsername = "";
                                _this.registerForm.userPassword = "";
                                _this.registerForm.checkPassword = "";
                                _this.registerForm.userDetailName = "";
                                _this.registerForm.userDetailEmail = "";
                                _this.registerForm.userDetailPhone = "";
                                _this.registerForm.mailCode = "";

                            } else if (res.data.code === 60010) {

                                //清空验证码
                                _this.registerForm.mailCode = "";
                                _this.$message({
                                    message: res.data.msg,
                                    type: "error",
                                    showClose: true,
                                })
                            }else if (res.data.code === 10010) {
                                //清空所有信息
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
                        //清空所有信息
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

        //新增对话框
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {
                });
        }
    },
});