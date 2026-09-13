/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas;

public interface CasProtocolConstants {
    public static final String VALIDATION_CAS_MODEL_PROXY_GRANTING_TICKET = "proxyGrantingTicket";
    public static final String VALIDATION_CAS_MODEL_PROXY_GRANTING_TICKET_IOU = "pgtIou";
    public static final String VALIDATION_REMEMBER_ME_ATTRIBUTE_NAME = "longTermAuthenticationRequestTokenUsed";
    public static final String VALIDATION_CAS_MODEL_ATTRIBUTE_NAME_ATTRIBUTES = "attributes";
    public static final String VALIDATION_CAS_MODEL_ATTRIBUTE_NAME_FORMATTED_ATTRIBUTES = "formattedAttributes";
    public static final String VALIDATION_CAS_MODEL_ATTRIBUTE_NAME_AUTHENTICATION_DATE = "authenticationDate";
    public static final String VALIDATION_CAS_MODEL_ATTRIBUTE_NAME_FROM_NEW_LOGIN = "isFromNewLogin";
    public static final String PARAMETER_PROXY_CALLBACK_URL = "pgtUrl";
    public static final String PARAMETER_RENEW = "renew";
    public static final String PARAMETER_USERNAME = "username";
    public static final String PARAMETER_PASSWORD = "password";
    public static final String PARAMETER_GATEWAY = "gateway";
    public static final String PARAMETER_SERVICE = "service";
    public static final String PARAMETER_TICKET = "ticket";
    public static final String PARAMETER_FORMAT = "format";
    public static final String PARAMETER_TARGET_SERVICE = "targetService";
    public static final String PARAMETER_METHOD = "method";
    public static final String PARAMETER_PROXY_GRANTING_TICKET_ID = "pgtId";
    public static final String PARAMETER_PROXY_GRANTING_TICKET = "pgt";
    public static final String PARAMETER_PROXY_GRANTING_TICKET_IOU = "pgtIou";
    public static final String PARAMETER_PROXY_GRANTING_TICKET_URL = "pgtUrl";
    public static final String ERROR_CODE_INVALID_REQUEST = "INVALID_REQUEST";
    public static final String ERROR_CODE_INVALID_PROXY_CALLBACK = "INVALID_PROXY_CALLBACK";
    public static final String ERROR_CODE_INVALID_TICKET = "INVALID_TICKET";
    public static final String ERROR_CODE_INVALID_REQUEST_PROXY = "INVALID_REQUEST_PROXY";
    public static final String ERROR_CODE_UNAUTHORIZED_SERVICE = "UNAUTHORIZED_SERVICE";
    public static final String ERROR_CODE_UNAUTHORIZED_SERVICE_PROXY = "UNAUTHORIZED_SERVICE_PROXY";
    public static final String ENDPOINT_LOGIN = "/login";
    public static final String ENDPOINT_LOGOUT = "/logout";
    public static final String ENDPOINT_PROXY_VALIDATE = "/proxyValidate";
    public static final String ENDPOINT_PROXY_VALIDATE_V3 = "/p3/proxyValidate";
    public static final String ENDPOINT_VALIDATE = "/validate";
    public static final String ENDPOINT_SERVICE_VALIDATE = "/serviceValidate";
    public static final String ENDPOINT_SERVICE_VALIDATE_V3 = "/p3/serviceValidate";
    public static final String ENDPOINT_PROXY = "/proxy";
}

