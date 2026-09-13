package com.tpvision.smartinstall.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ApiExceptionHandler implements ResponseBodyAdvice<Object> {
   private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

   @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
   public ResponseEntity<ApiErrorCode> handleHttpRequestMethodNotSupportedException(Exception ex, WebRequest req) {
      return new ResponseEntity<>(ApiErrorCode.REQUEST_METHOD_ERROR, ApiErrorCode.REQUEST_METHOD_ERROR.getStatus());
   }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<ApiErrorCode> handleException(Exception ex, WebRequest req) {
      logger.error(ex.getMessage(), ex);
      return new ResponseEntity<>(ApiErrorCode.SYSTEM_ERROR, ApiErrorCode.SYSTEM_ERROR.getStatus());
   }

   @Override
   public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response
   ) {
      if (body instanceof ApiErrorCode) {
         response.setStatusCode(((ApiErrorCode)body).getStatus());
      }

      ApiLogger.logMethodExecutationReturn(body);
      return body;
   }

   @Override
   public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
      return true;
   }
}
