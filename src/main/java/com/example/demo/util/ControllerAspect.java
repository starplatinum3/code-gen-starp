package com.example.demo.util;

//import com.gm.wj.result.ResultFactory;

//import com.starp.exam.base.RestResponse;
import com.example.demo.entity.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class ControllerAspect {

//    @Around("execution(* com.example.writer.controller..*.*(..))")
//    zucc.kinect.controller
 public  String cutStr(String args){
    int argMaxLen = 400;
//        if (args.length() > 1000) {
    if (args.length() > argMaxLen) {
      return args.substring(0, argMaxLen);
    }
    return args;
}
    /**
     * 这样 controller 那边是不是就不用管错误 处理了 只要当作返回正确就行了，
     * 错误的话 会在这里返回 那边也不返回
     *
     * @param joinPoint
     * @return
     */
//    @Around("execution(* com.mindskip.xzs.controller..*.*(..))")
    @Around("execution(* com.example.demo.controller..*.*(..))")
    public Object record(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
//        joinPoint.getArgs().length
        String args = Arrays.toString(joinPoint.getArgs());
//        int argMaxLen=100;
        int argMaxLen = 400;
//        if (args.length() > 1000) {
        if (args.length() > argMaxLen) {
            args = args.substring(0, argMaxLen);
        }
//        log.info("传入参数: " + Arrays.toString(joinPoint.getArgs()));
        log.info("传入参数: " + args);
//        传参 打印太多东西了
        Object result;
        log.info("开始执行 {}.{}",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());
        try {
            result = joinPoint.proceed();
//            if (result instanceof Page) {
//                Page  page= (Page )result;
//                page.toString()
//            }
//            HttpUt
            if(result!=null){
                String stringResult = result.toString();
//            if (args.length() > argMaxLen) {
//                args = args.substring(0, argMaxLen);
//            }
                final String stringResultCutted = cutStr(stringResult);
//            stringResult = stringResult.substring(0, argMaxLen);
                log.info("result {}",stringResultCutted);
            }

        } catch (Throwable e) {
            e.printStackTrace();
//           return RestResponse.fail(2,"失败 "+e.getMessage());
           return ReturnT.error("失败 "+e.getMessage());
        }
//        log.info("result {}",result);
        long endTime = System.currentTimeMillis();
        log.info("方法执行完毕, 共用时:" + (endTime - startTime) + "毫秒");
        return result;
    }


    //定义增强，pointcut连接点使用@annotation（xxx）进行定义
//    @Around(value = "@annotation(around)") //around 与 下面参数名around对应
//    public Object processAuthority(ProceedingJoinPoint point, MyAnnotation around) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        System.out.println("ANNOTATION welcome");
//        System.out.println("ANNOTATION 调用方法：" + around.methodName());
//        System.out.println("ANNOTATION 调用类：" + point.getSignature().getDeclaringTypeName());
//        System.out.println("ANNOTATION 调用类名" + point.getSignature().getDeclaringType().getSimpleName());
//        Object result = point.proceed(); //调用目标方法
//        System.out.println("ANNOTATION login success");
//        long endTime = System.currentTimeMillis();
//        log.info("方法执行完毕, 共用时:" + (endTime - startTime) + "毫秒");
//        return result;
//    }
//————————————————
//    版权声明：本文为CSDN博主「咚咚大帝」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/qq_41981107/article/details/85260765

//    @Around("execution(* com.example.writer.controller..*.*(..))")
//    public Object recordService(ProceedingJoinPoint joinPoint) {
//        long startTime = System.currentTimeMillis();
//        log.info("传入参数: " + Arrays.toString(joinPoint.getArgs()));
//        Object result;
//        log.info("开始执行 {}.{}", joinPoint.getTarget().getClass(),
//                joinPoint.getSignature().getName());
//        try {
//            result = joinPoint.proceed();
//        } catch (UsernameNotFoundException e) {
//            log.error("登录错误, 错误信息: " + e.getMessage());
//            return Result.error(ResultCode.ERROR_LOGIN, e.getMessage());
//        } catch (BusinessException e) {
//            log.error("执行业务错误, 错误信息: " + e.getMessage());
//            return Result.error(e.getResultCode(), e.getMessage());
//        } catch (Throwable throwable) {
//            log.error("其他错误");
//            throwable.printStackTrace();
//            return Result.error(throwable.getMessage());
//        }
//        long endTime = System.currentTimeMillis();
//        log.info("方法执行完毕, 共用时:" + (endTime - startTime) + "毫秒");
//        return result;
//    }
}
