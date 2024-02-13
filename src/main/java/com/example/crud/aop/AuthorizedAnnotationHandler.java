package com.example.crud.aop;

import com.example.crud.exception.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class AuthorizedAnnotationHandler {
    @Autowired
    private HttpServletRequest request;
    @Value("${security.auth.token}")
    private String authToken;

    @Around("@annotation(com.example.crud.aop.annotation.Authorized)")
    public Object handleAuthorized(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!authToken.equals(request.getHeader("X-Auth-Token"))) {
            throw new NotAuthorizedException("Not authorized");
        }
        return joinPoint.proceed();
    }
}

