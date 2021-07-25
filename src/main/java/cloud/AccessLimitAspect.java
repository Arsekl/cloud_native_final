package cloud;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Scope
@Aspect
public class AccessLimitAspect {
    private static final RateLimiter rateLimiter = RateLimiter.create(3.0);

    @Pointcut("@annotation(cloud.AccessLimit)")
    public void ServiceAspect() { System.out.println("123");}

    @Around("ServiceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        try {
            if (rateLimiter.tryAcquire()) {
                obj = joinPoint.proceed();
            } else {
                obj = new ResponseEntity<String>("Too many requests", HttpStatus.TOO_MANY_REQUESTS);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }
}
