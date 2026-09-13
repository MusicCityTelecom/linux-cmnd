/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.api;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public enum ApiErrorCode {
    SUCCESS_OK(0, "OK", HttpStatus.OK),
    SYSTEM_ERROR(-1, "system error", HttpStatus.INTERNAL_SERVER_ERROR),
    REQUEST_METHOD_ERROR(-2, "request method error", HttpStatus.METHOD_NOT_ALLOWED),
    NO_LICENSE(-3, "no license", HttpStatus.UNAUTHORIZED),
    EXPIRED_LICENSE(-4, "expired license", HttpStatus.UNAUTHORIZED),
    REQUEST_HTTPS_REQURED(-5, "must use https to use exapi", HttpStatus.NOT_ACCEPTABLE),
    EXAPI_SWITCH_CLOSED(-6, "exapi function closed", HttpStatus.NOT_ACCEPTABLE),
    CLIENT_API_VERSION_TOO_HIGH(-7, "client api version higher than server version,please upgrade server", HttpStatus.NOT_ACCEPTABLE),
    SERVER_API_NOT_COMPATIBLE_WITH_CLINET_API_VERSION(-8, "server api version incompatible with the request api version, please upgrade your client", HttpStatus.NOT_ACCEPTABLE),
    DEVICE_LIMIT_REACHED(-9, "You have reached the maximum limit of devices", HttpStatus.NOT_ACCEPTABLE),
    JWT_EMPTY(1, "jwt token empty", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_ERROR(2, "jwt token error", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_FORMATE_ERROR(3, "jwt token format error", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_EXPIRE(4, "jwt token expire", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_SUBJECT_ERROR(5, "jwt token invalid", HttpStatus.UNAUTHORIZED),
    USER_USERNAME_PASSWORD_EMPTY(100, "username or password empty"),
    USER_VALIDATE_CHECK_FAILURE(101, "error username or password"),
    USER_LOGIN_FAILURE(102, "user login failure", HttpStatus.UNAUTHORIZED),
    USER_ROLE_PERMISSION_NOT_ENOUGH(103, "insufficient privileges role", HttpStatus.UNAUTHORIZED),
    USER_USERINFO_ENC_USERNAME_ERROR(104, "username error"),
    USER_USERINFO_ENC_PASSWORD_ERROR(105, "password error"),
    PLAYOUT_WORKING(200, "play status is working"),
    PLAYOUT_STARTING(201, "play status is starting"),
    PLAYOUT_NO_RF_ROOMS(202, "no rf rooms required to start play"),
    PLAYOUT_DEKTECCARD_CHECK_FAILURE(203, "no dektec card found for RF play"),
    PMS_STATUS_DIABLED(300, "pms status is disabled"),
    PMS_CHECK_ROOMNO_EMPTY(301, "room number is empty"),
    PMS_GUESTNAME_EMPTY(302, "guest name is empty"),
    PMS_CHECK_OUT_TIME_ERROR(303, "checkout time error"),
    PMS_CHECK_OUT_TIME_INVALID(304, "invalid checkout time"),
    PMS_CHECK_IN_TIME_INVALID(305, "invalid check-in time"),
    PMS_CHECK_IN_TIME_AFTER_CHECK_OUT_TIME(306, "check-in time must before check-out time"),
    PMS_ROOM_ALREADY_CHECKED(307, "room already checked"),
    PMS_CHECK_ROOMNO_INVALID(308, "room number is invalid"),
    PMS_GUEST_CHECK_OUT_TIME_CONFILIT_WITH_FUTURE_CHECK_RECORD(309, "guest check out time conflict with future check record"),
    PMS_FUTURE_CHECK_TIME_CONFILIT_WITH_CURRENT_CHECK_IN_RECORD(310, "future check time conflict with the current checked record"),
    PMS_FUTURE_CHECK_TIME_CONFILIT_WITH_OTHER_FUTURE_RECORD(311, "future check time conflict with other record"),
    PMS_GUEST_ID_EMPTY(312, "guest id is empty"),
    PMS_FUTURE_ID_EMPTY(313, "future check-in id is empty"),
    PMS_FUTURE_CHECK_ID_RECORD_NOT_EXSIT(314, "future check-in record not exist"),
    PMS_GUEST_NOT_EXISTS(315, "guest not exists"),
    PMS_NO_SUCH_ROOMS(316, "unknown room number"),
    PMS_CHECKOUT_TIME_EMPTY(317, "empty checkout time"),
    PMS_CHECKIN_TIME_UPDATE_FAILURE(318, "check-in time update error"),
    PMS_CHECKOUT_TIME_UPDATE_FAILURE(319, "checkout time update error"),
    PMS_CHECK_IN_GUEST_MODIFY_NAME_FAILURE(320, "update new guest name failure"),
    PMS_GUEST_ROOMNO_SAME(321, "room number not changed"),
    PMS_UPDATE_CHECK_IN_ROOMNO_FAILURE(322, "guest room update failure"),
    PMS_MESSAGE_ICON_UPLOAD_FAILURE(323, "save icon failure"),
    PMS_CHECK_DEVICE_OFFLINE(324, "TV offline"),
    PMS_CHECK_UNKONWN_FAILURE(325, "unknown error"),
    PMS_GUEST_LANGUAGE_EMPTY(326, "guest language is empty"),
    PMS_CHECK_IN_GUEST_MODIFY_LANGUAGE_FAILURE(327, "update guest language failure"),
    PMS_UPDATE_ROOMNO_FAILURE_AS_CHECKED_BY_OTHER_SYSTEM(328, "current guest checked by other system"),
    PMS_DEFAULT_MESSAGE_ICON_UPLOAD_FAILURE(329, "save default message icon failure"),
    PMS_DEFAULT_BILL_ICON_UPLOAD_FAILURE(330, "save default bill icon failure"),
    PMS_ROOM_NOT_CHECKED(331, "room not checked"),
    PMS_ALARM_STATUS_QUERY_FAILURE(332, "PMS alarm query failure"),
    PMS_ALARM_TIME_FORMAT_ERROR(333, "pms alarm time format error"),
    PMS_ALARM_TIME_SET_FAILURE(334, "set alarm time failure"),
    PMS_CHECK_IN_ONLY_RF_DEVICES(335, "RF connected TVs cannot receive guest details"),
    RC_FIND_ROOMNO_FORMAT_ERROR(400, "room number format is error"),
    RC_CANT_FIND_DEVICE_FOR_ROOMNO(401, "cant find tv for the room number"),
    RC_FAIL_GET_ROOMINFO_FOR_ROOMNO(402, "cant query tv information for the room number"),
    RC_FAIL_UPDATE_SETTING_FOR_ROOMNO(403, "cant update setting for the room number"),
    RC_FAIL_TO_UPGRADE_CONTENT_AS_TV_NOT_IN_STOP_STATUS(404, "TV already in upgrading status"),
    RC_FAIL_TO_CREATE_CONTENT_CLONE(405, "assigned content data failure"),
    RC_ERROR_GROUP_NAME(406, "error group name"),
    RC_FAIL_GET_ROOMINFO_FOR_GROUPN(407, "cant get setting information for the group"),
    RC_UPDATE_CONTENT_DEVICES_EMPTY(408, "cant find valid updated devices"),
    RC_FAIL_UPDATE_SETTING_FOR_GROUP(409, "cant update setting for group"),
    RC_FAIL_GET_SETTING_VALUE_FOR_ROOM(410, "get setting from room failure"),
    RC_SETTING_TYPE_KEY_ERROR(411, "invalid setting key name"),
    TVS_QUERY_TV_NOT_EXIST(500, "cant find query tv", HttpStatus.NOT_FOUND),
    TVS_ASSIGN_PARAMETER_INVALID(501, "invalid assign parameter"),
    TVS_ASSIGN_CLONE_DATA_INVALID(502, "invalid assign clone data"),
    TVS_ASSIGN_TV_IN_UPGRADING_STATUS(503, "assigned TV in upgrading status", HttpStatus.NOT_ACCEPTABLE),
    TVS_ASSIGN_FAILRE(504, "assign failure"),
    TVS_ASSIGN_CLONE_DATA_NOT_EXIST(505, "assign clone data error"),
    TVS_UNASSIGN_PARAMETER_INVALID(506, "unassign parameter invalid"),
    TVS_UNASSIGN_PACKAGE_TYPE_INVALID(507, "unassign package type invalid"),
    TVS_UNASSIGN_TV_NOT_IN_ASSIGNED_STATUS(508, "unassign tv not in assign status"),
    TVS_UNASSIGN_NOT_SUPPORTED(509, "not support unassign ui/welcome from a clone"),
    TVS_CLONE_PARAMETER_ERROR(510, "clone parameter error"),
    PACKAGE_QUERY_TYPE_ERROR(600, "query package type error"),
    PLAYOUT_QUERY_ROOM_PARA_ERROR(700, "room parameter invalid"),
    PLAYOUT_QUERY_PLAYOUT_NOT_EXIST(701, "query playout record not exist", HttpStatus.NOT_FOUND),
    PLAYOUT_ADD_PARAMETER_ERROR(702, "playout add parameter error"),
    PLAYOUT_DEKTECK_CHECK_FAILURE(703, "dektec check failure"),
    PLAYOUT_PLAY_DATA_ERROR(704, "playout out data not exist"),
    PLAYOUT_ADD_FAILURE(705, "add playout failure", HttpStatus.INTERNAL_SERVER_ERROR),
    PLAYOUT_ADD_CLONE_DATA_NOT_EXIST(706, "playout clone data not exist"),
    PLAYOUT_PLATFORM_NOT_COMPATIBLE_ERROR(707, "playout content not compatible with the platform error"),
    PLAYOUT_REACH_MAX_SUPPORT_COUNT(708, "playout reach max support count 42"),
    MYCHOICE_API_KEY_NOT_SET(800, "mychoice api key not set"),
    MYCHOICE_API_KEY_ERROR(801, "mychoice api key error"),
    MYCHOICE_BAD_MYCHOICE_REQUEST(802, "mychoice data query failure"),
    MYCHOICE_UNKNOW_ROOM_FOR_PACKAGE(803, "my-choice.tv SOAP error:Unknown room"),
    MYCHOICE_DEVICES_DATA_EMPTY_ERROR(804, "Cant find devices for the room number"),
    MYCHOICE_ONLY_SUPPORT_DIGIT_ROOM_NO(805, "CMND only support digit format room number"),
    MYCHOICE_RP_PLAYOUT_ERROR(806, "mychoice rf playout error"),
    MYCHOICE_IP_UPGRADE_ERROR(807, "mychoice ip upgrade error"),
    MYCHOICE_IP_UPGRADE_DEVICES_OFFLINE_ERROR(808, "mychoice ip device offline"),
    MYCHOICE_PINCODE_HISOTRY_NOT_EXIST(809, "pincode history not exisit", HttpStatus.NOT_FOUND),
    MYCHOICE_PINCODE_ALREADY_DEACTIVED(810, "pincode already deactivated"),
    MYCHOICE_PINCODE_ALREADY_EXPIRED(811, "pincode already expired"),
    MYCHOICE_QUERY_NOT_SUPPORT_FOR_RF_DEVICE(812, "not support rf device mychoice query"),
    MYCHOICE_DURATION_PARAMETER_ERROR(813, "duration parameter error"),
    MYCHOICE_PACKAGE_PARAMETER_ERROR(814, "package_number parameter error"),
    NOTIFICATION_NOT_FOUND(900, "notification not found"),
    TRIGGER_TYPE_ERROR(1000, "error trigger type value", HttpStatus.NOT_ACCEPTABLE),
    LAUNCH_TRIGGER_ID_EMPTY(1001, "launch trigger id empty", HttpStatus.NOT_ACCEPTABLE),
    LAUNCH_TRIGGER_NOT_EXIST(1002, "launch trigger not exisit", HttpStatus.NOT_FOUND),
    LAUNCH_TRIGGER_IN_ACTIVE(1003, "launch trigger is not activated", HttpStatus.INTERNAL_SERVER_ERROR),
    LAUNCH_TRIGGER_TYPE_NOT_MANUAL(1004, "only manual type trigger can be launched", HttpStatus.INTERNAL_SERVER_ERROR),
    LAUNCH_TRIGGER_INVALID_TVIDS(1005, "target tvids empty", HttpStatus.NOT_ACCEPTABLE);

    private int code;
    private String msg;
    private HttpStatus status;

    private ApiErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.status = HttpStatus.BAD_REQUEST;
    }

    private ApiErrorCode(int code, String msg, HttpStatus status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", this.getCode());
        map.put("msg", this.getMsg());
        return map;
    }

    public String toString() {
        return JSONObject.valueToString(this.toMap());
    }

    public static class JsonConverter
    extends AbstractHttpMessageConverter<ApiErrorCode> {
        public JsonConverter() {
            super(new MediaType("application", "json", StandardCharsets.UTF_8), new MediaType("application", "*+json", StandardCharsets.UTF_8));
        }

        @Override
        protected ApiErrorCode readInternal(Class<? extends ApiErrorCode> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
            return null;
        }

        @Override
        protected boolean supports(Class<?> clazz) {
            return clazz.equals(ApiErrorCode.class);
        }

        @Override
        protected void writeInternal(ApiErrorCode t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
            OutputStream out = outputMessage.getBody();
            String text = null;
            if (t != null) {
                text = JSONObject.valueToString(t.toMap());
                byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
                out.write(bytes);
            }
        }
    }
}

