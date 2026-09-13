/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.audit;

public interface AuditableActions {
    public static final String MULTIFACTOR_AUTHENTICATION_BYPASS = "MULTIFACTOR_AUTHENTICATION_BYPASS";
    public static final String AUTHENTICATION_EVENT = "AUTHENTICATION_EVENT";
    public static final String AUTHENTICATION = "AUTHENTICATION";
    public static final String SERVICE_TICKET = "SERVICE_TICKET";
    public static final String PROXY_TICKET = "PROXY_TICKET";
    public static final String TICKET_GRANTING_TICKET = "TICKET_GRANTING_TICKET";
    public static final String TICKET_DESTROYED = "TICKET_DESTROYED";
    public static final String PROXY_GRANTING_TICKET = "PROXY_GRANTING_TICKET";
    public static final String SERVICE_TICKET_VALIDATE = "SERVICE_TICKET_VALIDATE";
    public static final String PROTOCOL_SPECIFICATION_VALIDATE = "PROTOCOL_SPECIFICATION_VALIDATE";
    public static final String REST_API_SERVICE_TICKET = "REST_API_SERVICE_TICKET";
    public static final String REST_API_TICKET_GRANTING_TICKET = "REST_API_TICKET_GRANTING_TICKET";
    public static final String EVALUATE_RISKY_AUTHENTICATION = "EVALUATE_RISKY_AUTHENTICATION";
    public static final String MITIGATE_RISKY_AUTHENTICATION = "MITIGATE_RISKY_AUTHENTICATION";
    public static final String TRUSTED_AUTHENTICATION = "TRUSTED_AUTHENTICATION";
    public static final String SURROGATE_AUTHENTICATION_ELIGIBILITY_SELECTION = "SURROGATE_AUTHENTICATION_ELIGIBILITY_SELECTION";
    public static final String SURROGATE_AUTHENTICATION_ELIGIBILITY_VERIFICATION = "SURROGATE_AUTHENTICATION_ELIGIBILITY_VERIFICATION";
    public static final String SERVICE_ACCESS_ENFORCEMENT = "SERVICE_ACCESS_ENFORCEMENT";
    public static final String SAVE_SERVICE = "SAVE_SERVICE";
    public static final String DELETE_SERVICE = "DELETE_SERVICE";
    public static final String DELEGATED_CLIENT = "DELEGATED_CLIENT";
    public static final String SAVE_CONSENT = "SAVE_CONSENT";
    public static final String SAML2_RESPONSE = "SAML2_RESPONSE";
    public static final String SAML2_REQUEST = "SAML2_REQUEST";
    public static final String OAUTH2_ACCESS_TOKEN_RESPONSE = "OAUTH2_ACCESS_TOKEN_RESPONSE";
    public static final String OAUTH2_USER_PROFILE = "OAUTH2_USER_PROFILE";
    public static final String OAUTH2_CODE_RESPONSE = "OAUTH2_CODE_RESPONSE";
    public static final String OAUTH2_ACCESS_TOKEN_REQUEST = "OAUTH2_ACCESS_TOKEN_REQUEST";
    public static final String AUP_VERIFY = "AUP_VERIFY";
    public static final String AUP_SUBMIT = "AUP_SUBMIT";
    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    public static final String REQUEST_CHANGE_PASSWORD = "REQUEST_CHANGE_PASSWORD";
    public static final String REQUEST_FORGOT_USERNAME = "REQUEST_FORGOT_USERNAME";
    public static final String ACCOUNT_REGISTRATION = "ACCOUNT_REGISTRATION";
    public static final String LOGOUT = "LOGOUT";
}

