/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.api.ApiErrorCode;

public class ApiErrorException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final ApiErrorCode apiErrorCode;

    public ApiErrorException(ApiErrorCode apiErrorCode) {
        this.apiErrorCode = apiErrorCode;
    }

    public ApiErrorCode getApiErrorCode() {
        return this.apiErrorCode;
    }
}

