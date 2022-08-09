package com.group3.aop;


import com.group3.domain.User;
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
     * record the exection time
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
    @Pointcut("execution(* com.group3.service.UserService.*(..))")
    private void userPt(){};

    /**
     * subtract empty space
     */
    @Around("userPt()")
    public Object subtractSpace(ProceedingJoinPoint p) throws Throwable {
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
        }

        return p.proceed(args);

    }
}
