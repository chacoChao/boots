//package com.chaco.chao.tools.ascept;
//
//import org.hibernate.validator.internal.engine.path.PathImpl;
//import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
//import org.springframework.core.ParameterNameDiscoverer;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.BeanPropertyBindingResult;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.ValidatorFactory;
//import javax.validation.executable.ExecutableValidator;
//import java.lang.reflect.Method;
//import java.util.List;
//import java.util.Set;
//
///**
// * author:zhaopeiyan001
// * Date:2019-03-13 20:02
// */
//@Aspect
//@Component
//public class VaildAspect {
//    /**************************你要监控的controller路径**********************/
////    @Pointcut("execution(public * com.ke.breeze.percentageWeb.controller.*.*(..))")
//    @Pointcut("execution(public * com.ke.breeze.finance.*.*(..))")
//    public void valid() {
//    }
//
//    @Around("valid()")
//    public Object arround(ProceedingJoinPoint pjp) {
//        try {
//            Object[] objects = pjp.getArgs();
//            if (objects.length == 0) {
//                return pjp.proceed();
//            }
//            /**************************校验封装好的javabean**********************/
//            //寻找带BindingResult参数的方法，判断是否有error，如果有则校验不通过
//            for (Object object : objects) {
//                if (object instanceof BeanPropertyBindingResult) {
//                    //有校验
//                    BindingResult result = (BeanPropertyBindingResult) object;
//                    if (result.hasErrors()) {
//                        List<FieldError> list = result.getFieldErrors();
//                        for (FieldError error : list) {
//                            /**************************error处理**********************/
////                            return ResponseUtil.faliureResponse(ResponseCode.PARAM_ERROR_WITH_NAME.code(), ResponseCode.PARAM_ERROR_WITH_NAME.message(), error.getField());
//                            return null;
//                        }
//                    }
//                }
//            }
//            /**************************校验普通参数*************************/
//            //  获得切入目标对象
//            Object target = pjp.getThis();
//            // 获得切入的方法
//            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
//            // 执行校验，获得校验结果
//            Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, objects);
//            //如果有校验不通过的
//            if (!validResult.isEmpty()) {
//                String[] parameterNames = parameterNameDiscoverer.getParameterNames(method); // 获得方法的参数名称
//
//                for (ConstraintViolation<Object> constraintViolation : validResult) {
//                    /**************************error处理**********************/
//                    PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();  // 获得校验的参数路径信息
//                    int paramIndex = pathImpl.getLeafNode().getParameterIndex(); // 获得校验的参数位置
//                    String paramName = parameterNames[paramIndex];  // 获得校验的参数名称
////                    return ResponseUtil.faliureResponse(ResponseCode.PARAM_ERROR_WITH_NAME.code(), ResponseCode.PARAM_ERROR_WITH_NAME.message(), paramName);
//                    return null;
//                }
//            }
//            return pjp.proceed();
//        } catch (Throwable e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
//    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//    private final ExecutableValidator validator = factory.getValidator().forExecutables();
//
//    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object[] params) {
//        return validator.validateParameters(obj, method, params);
//    }
//}
