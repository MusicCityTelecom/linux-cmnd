package com.tpvision.smartinstall.api;

public class ApiErrorException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private final ApiErrorCode apiErrorCode;

   public ApiErrorException(ApiErrorCode apiErrorCode) {
      this.apiErrorCode = apiErrorCode;
   }

   public ApiErrorCode getApiErrorCode() {
      return this.apiErrorCode;
   }
}
