# elec5620-assiment
jdk 1.8

pgsql

port 8080

context-path: /5620

http://localhost:8080/5620/pages/index.html

**待办：**
- username need unique, remember to correct -- ok
- register function needs all information of user --ok
- 安全问题还没去写，限制用户获取验证码次数，以及验证码过期时间(120s 已设置)
- 验证码布局，拦截问题 --ok
- 邮箱验证码发送功能实现（300s过期时间），校验功能待实现(实现)
- question: sql优化，避免全表扫描，
- 注册校验 --ok
- 登录表单trim --ok 但是发送邮件的方法中mail不能被trim，因为aop还没拦截，就把他存进去缓存了，要实现的话，拦截上一届controller，就先不修改了
- front-end register function --ok