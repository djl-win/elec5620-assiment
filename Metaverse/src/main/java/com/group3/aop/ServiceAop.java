package com.group3.aop;


import com.group3.domain.User;
import com.group3.domain.UserDetail;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceAop {

    /**
     * all service pointcut
     */
    @Pointcut("execution(* com.group3.service.*Service.*(..))")
    private void servicePt(){};

    /**
     * record the execution time
     */
    @Around("servicePt()")
    public Object timeRecord(ProceedingJoinPoint p) throws Throwable {

        long current = System.currentTimeMillis();

        Object proceed = p.proceed();

        long end = System.currentTimeMillis();

        String methodName = p.getSignature().getName();

        String className = p.getSignature().getDeclaringTypeName();

        System.out.println(className + " " + methodName + " execute for " + (end - current) + " ms");
        return proceed;
    }

    /**
     * process login info, subtract empty space
     */
    @Pointcut("execution(* com.group3.service.*Service.*(..))")
    private void userPt(){};

    /**
     * subtract empty space of login info
     */
    @Around("userPt()")
    public Object subtractSpaceOfLogin(ProceedingJoinPoint p) throws Throwable {
        Object[] args = p.getArgs();

        for (int i = 0; i < args.length; i++) {
            if(args[i].getClass().equals(User.class)){
                User user = (User)args[i];
//                System.out.println(user.getUserinfoUsername());
//                System.out.println(user.getUserinfoPassword());
                //avoid null point
                if (user.getUserUsername() != null && user.getUserPassword() != null) {
                    user.setUserUsername(user.getUserUsername().trim());
                    user.setUserPassword(user.getUserPassword().trim());
                }
//                System.out.println(user.getUserinfoUsername());
//                System.out.println(user.getUserinfoPassword());
            }

            //trim userinfo
            if(args[i].getClass().equals(UserDetail.class)){
                UserDetail userDetail = (UserDetail)args[i];
//                System.out.println(userDetail.getUserDetailName());
//                System.out.println(userDetail.getUserDetailPhone());
//                System.out.println(userDetail.getUserDetailEmail());
                //avoid null point
                if (userDetail.getUserDetailName() != null && userDetail.getUserDetailPhone() != null && userDetail.getUserDetailEmail() != null) {
                    userDetail.setUserDetailName(userDetail.getUserDetailName().trim());
                    userDetail.setUserDetailPhone(userDetail.getUserDetailPhone().trim());
                    userDetail.setUserDetailEmail(userDetail.getUserDetailEmail().trim());
                }
//                System.out.println(userDetail.getUserDetailName());
//                System.out.println(userDetail.getUserDetailPhone());
//                System.out.println(userDetail.getUserDetailEmail());
            }

        }

        return p.proceed(args);
    }
//    This method can not be trimmed (because the aop has not been intercepted), so it is stored in the cache.
//    To implement it, you need to intercept the last controller, so let's not modify it.
//    /**
//     * process login info, subtract empty space
//     */
//    @Pointcut("execution(* com.group3.service.MailService.sentMailCode(..))")
//    private void emailPt(){};
//
//    /**
//     * subtract empty space of login info
//     */
//    @Around("emailPt()")
//    public Object subtractSpaceOfEmail(ProceedingJoinPoint p) throws Throwable {
//        Object[] args = p.getArgs();
//        System.out.println(args[0]);
//        for (int i = 0; i < args.length; i++) {
//            if(args[i].getClass().equals(String.class)){
//                args[i] = args[i].toString().trim();
//            }
//        }
//        System.out.println(args[0]);
//        return p.proceed(args);
//    }

}
