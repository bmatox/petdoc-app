package com.petdoc.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura QUALQUER exceção não tratada na aplicação.
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("error/error-debug");

        // Adiciona os objetos ao modelo para serem exibidos na página de erro padrão
        modelAndView.addObject("timestamp", LocalDateTime.now());
        modelAndView.addObject("status", getStatus(request));
        modelAndView.addObject("error", ex.getClass().getSimpleName());
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("path", request.getRequestURI());
        modelAndView.addObject("browser", request.getHeader("User-Agent"));

        return modelAndView;
    }

    private Object getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        return statusCode != null ? statusCode : "N/A";
    }
}