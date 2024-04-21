package ru.charushnikov.microauthservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(ru.charushnikov.microauthservice.controller.*)")
    private void controllersMethodsReturnResponseEntity() {
    }

    //@Pointcut("execution(public * ru.charushnikov.microauthservice.handler.ControllerExceptionHandler.*(RuntimeException))")
    @Pointcut("execution(* ru.charushnikov.microauthservice.handler.ControllerExceptionHandler.*(..))")// так пишет логи до момента перехвата исключения
    private void loggingErrorMessage(){}

    @Before("loggingErrorMessage()")
    public void beforeThrowException(JoinPoint joinPoint) {
        Object[] arg = joinPoint.getArgs();
        String exceptionInfo = arg[0].toString();
        log.error("Exception: {}", exceptionInfo);
    }

    @Around("controllersMethodsReturnResponseEntity()")
    public ResponseEntity<?> loggingRequestAndResponseReturnRespEntity(ProceedingJoinPoint joinPoint) throws Throwable {
        long timeBegin = System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = Arrays.stream(methodSignature.getMethod().getParameters())
                .filter(parameter -> parameter.isVarArgs() || parameter.isAnnotationPresent(RequestBody.class))
                .toArray(Parameter[]::new);
        Object[] parameterValues = joinPoint.getArgs();
        Map<String, String> parameterNamesAndValues = Arrays.stream(parameters)
                .filter(parameter -> parameter.isAnnotationPresent(RequestBody.class) ||
                        parameter.isAnnotationPresent(io.swagger.v3.oas.annotations.Parameter.class))
                .collect(Collectors.toMap(Parameter::getName, parameter -> parameterValues[Arrays.asList(parameters).indexOf(parameter)].toString()));
        log.info("Enter: {}. {}(): argument[s] {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), parameterNamesAndValues); // всё, что идет выше выполниться до основной логики
        ResponseEntity<?> result = (ResponseEntity<?>) joinPoint.proceed(); // joinPoint.proceed() после вызова этого метода события(логирование) будут выполняться после отработки основной логики
        long timeEnd = System.currentTimeMillis();
        log.info("Exit: {}. {}(): result {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
        log.info("Request processing time: {} ms", timeEnd - timeBegin);
        return result;
    }
}
