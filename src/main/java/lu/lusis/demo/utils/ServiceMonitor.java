package lu.lusis.demo.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {

    private Logger logger = LoggerFactory.getLogger(ServiceMonitor.class);

    @AfterReturning("execution(* lu.lusis.demo.ui.views..*(..))")
    public void logServiceAccess(JoinPoint joinPoint) {
        logger.debug("Completed: " + joinPoint);
    }

}