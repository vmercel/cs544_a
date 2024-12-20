package edu.miu.cs.cs544.mercel.jpa.monitoring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {

    private static final String LOG_FILE = "logs.txt";

    /**
     * Logs each method execution in all controllers.
     */
    @Before("execution(public * edu.miu.cs.cs544.mercel.jpa.monitoring.*..*Controller.*(..))")
    public void logControllerMethod(JoinPoint joinPoint) {
        logMethodCall(joinPoint, "CONTROLLER");
    }

    /**
     * Logs each method execution in all services.
     */
    @Before("execution(public * edu.miu.cs.cs544.mercel.jpa.monitoring.*..*Service.*(..))")
    public void logServiceMethod(JoinPoint joinPoint) {
        logMethodCall(joinPoint, "SERVICE");
    }

    /**
     * Logs each method execution in all repositories.
     */
    @Before("execution(public * edu.miu.cs.cs544.mercel.jpa.monitoring.*..*Repository.*(..))")
    public void logRepositoryMethod(JoinPoint joinPoint) {
        logMethodCall(joinPoint, "REPOSITORY");
    }

    /**
     * Logs the method call details.
     *
     * @param joinPoint the join point representing the method call
     * @param layer the layer where the method is called (Controller, Service, Repository)
     */
    private void logMethodCall(JoinPoint joinPoint, String layer) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String timestamp = LocalDateTime.now().toString();

        String logMessage = String.format("[%s] [%s] Called method: %s.%s", timestamp, layer, className, methodName);

        writeLog(logMessage);
    }

    /**
     * Writes the log message to the log file.
     *
     * @param logMessage the message to log
     */
    private void writeLog(String logMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }
}
