package ru.inno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Date;

@Aspect
@Component
public class LogAspect {
    @Around("@annotation(LogTransformation)")
    public Object beforeLogAddAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        String text = "DateTime start: " + new Date(start) +
                "\nметод: " + joinPoint.getSignature().getName() +
                "\nкласс: " + joinPoint.getTarget().getClass() +
                "\ninput data:";
        for (Object o : joinPoint.getArgs())
            text += "\n\t" + o.getClass().getName() + ": " + o.toString();

        String logFileName = "null";
        for (Method m : joinPoint.getTarget().getClass().getDeclaredMethods()) {
            if (m.getName() == joinPoint.getSignature().getName()) {
                logFileName = m.getAnnotation(LogTransformation.class).value();
                break;
            }
        }

        insLog(logFileName, text);

        return object;
    }
    public void insLog(String fileName, String text){
        try {
            Files.write(Path.of("C:/Temp/"+ fileName + "/"),
                    (text + "\n").getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}