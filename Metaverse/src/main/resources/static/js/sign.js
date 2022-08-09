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
                verificationCode: ""
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
                verificationCode: [
                    {required: true, message: "Please enter checked code", trigger: "blur"},
                ]
            },
        };
    },

    mounted() {

        //挂载完成式，加载用户名密码
        if (localStorage.getItem("news")) {
            this.loginForm = JSON.parse(localStorage.getItem("news"))
            this.checked = true
        }
    },

    methods: {

        //改变验证码，（还没写）
        changeImg() {
            var milliseconds = new Date().getMilliseconds();
            this.codeUrl = '#?'+milliseconds;
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
                            console.log(res.data)
                            //响应状态码为40011
                            if (res.data.code === 40011) {
                                location.href = "/5620/pages/detail.html";
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

        //提交注册表单
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
                        method: "post",
                        url: "http://localhost:8080/online/userServlet/register",
                        data: _this.registerForm
                    }).then(function (res) {
                            //响应状态码为200
                            if (res.data === "success") {
                                // setToken(res.data.token);
                                // localStorage.setItem("USERNAME", res.data.username);
                                _this.$message({
                                    message: "注册成功啦",
                                    type: "success",
                                    showClose: true,
                                })
                                //dialogVisible设置为false
                                _this.dialogVisible = false;
                                //清空所有信息
                                _this.registerForm.userUsername = "";
                                _this.registerForm.userPassword = "";
                                _this.registerForm.checkPassword = "";
                                //改变图片
                                _this.changeImg();
                                //清空验证码
                                _this.registerForm.verificationCode = "";
                                // this.$router.replace("/");
                            } else if (res.data === "errorCode") {
                                //改变图片
                                _this.changeImg();
                                //清空验证码
                                _this.registerForm.verificationCode = "";
                                _this.$message({
                                    message: "验证码错误，请重新输入",
                                    type: "error",
                                    showClose: true,
                                })
                            }else if (res.data === "errorUser") {
                                //清空所有信息
                                _this.registerForm.userUsername = "";
                                _this.registerForm.userPassword = "";
                                _this.registerForm.checkPassword = "";
                                //改变图片
                                _this.changeImg();
                                //清空验证码
                                _this.registerForm.verificationCode = "";
                                _this.$message({
                                    message: "用户名已存在，请重新注册",
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
                        //改变图片
                        _this.changeImg();
                        //清空验证码
                        _this.registerForm.verificationCode = "";
                        _this.$message({
                            message: "注册失败",
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