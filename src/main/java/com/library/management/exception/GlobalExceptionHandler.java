package com.library.management.exception;

import com.library.management.model.ErrorTransfer;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static String shortenedStackTrace(Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        String[] lines = writer.toString().split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(lines.length, 3); i++) {
            sb.append(lines[i]).append("\n");
        }
        return sb.toString();
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ErrorTransfer webApplicationException(BaseException baseException, HttpServletResponse response) {
        log.error("baseException ", baseException);
        if (log.isDebugEnabled())
            log.warn(shortenedStackTrace(baseException));
        response.setStatus(baseException.getStatus());

        return createErrorTransfer(baseException);
    }


    private ErrorTransfer createErrorTransfer(BaseException baseException) {
    return new ErrorTransfer( baseException.getErrorMessage(), baseException.getErrorCode());
    }

    //global exception
    @ExceptionHandler(value = { IOException.class, InvocationTargetException.class, NoSuchMethodException.class,
            Exception.class })
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> exceptionHandle(Exception exception) {
        log.warn("exceptionHandle", exception);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("message", exception.getMessage());
        log.error(exception.getMessage());
        return responseData;
    }

    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbortException(ClientAbortException clientAbortException) {
        log.warn(clientAbortException.getMessage());
    }

}
