/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.internal.jdkconnector;

import java.util.Locale;
import java.util.ResourceBundle;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory;
import org.glassfish.jersey.internal.l10n.Localizer;

public final class LocalizationMessages {
    private static final String BUNDLE_NAME = "org.glassfish.jersey.client.internal.jdkconnector.localization";
    private static final LocalizableMessageFactory MESSAGE_FACTORY = new LocalizableMessageFactory("org.glassfish.jersey.client.internal.jdkconnector.localization", new BundleSupplier());
    private static final Localizer LOCALIZER = new Localizer();

    public static Localizable localizableNEGATIVE_CHUNK_SIZE(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("negative.chunk.size", arg0, arg1);
    }

    public static String NEGATIVE_CHUNK_SIZE(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableNEGATIVE_CHUNK_SIZE(arg0, arg1));
    }

    public static Localizable localizableHTTP_UNEXPECTED_CHUNK_HEADER() {
        return MESSAGE_FACTORY.getMessage("http.unexpected.chunk.header", new Object[0]);
    }

    public static String HTTP_UNEXPECTED_CHUNK_HEADER() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_UNEXPECTED_CHUNK_HEADER());
    }

    public static Localizable localizableREDIRECT_ERROR_DETERMINING_LOCATION() {
        return MESSAGE_FACTORY.getMessage("redirect.error.determining.location", new Object[0]);
    }

    public static String REDIRECT_ERROR_DETERMINING_LOCATION() {
        return LOCALIZER.localize(LocalizationMessages.localizableREDIRECT_ERROR_DETERMINING_LOCATION());
    }

    public static Localizable localizableHTTP_TRAILER_HEADER_OVERFLOW() {
        return MESSAGE_FACTORY.getMessage("http.trailer.header.overflow", new Object[0]);
    }

    public static String HTTP_TRAILER_HEADER_OVERFLOW() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_TRAILER_HEADER_OVERFLOW());
    }

    public static Localizable localizableREDIRECT_INFINITE_LOOP() {
        return MESSAGE_FACTORY.getMessage("redirect.infinite.loop", new Object[0]);
    }

    public static String REDIRECT_INFINITE_LOOP() {
        return LOCALIZER.localize(LocalizationMessages.localizableREDIRECT_INFINITE_LOOP());
    }

    public static Localizable localizableWRITING_FAILED() {
        return MESSAGE_FACTORY.getMessage("writing.failed", new Object[0]);
    }

    public static String WRITING_FAILED() {
        return LOCALIZER.localize(LocalizationMessages.localizableWRITING_FAILED());
    }

    public static Localizable localizableTIMEOUT_RECEIVING_RESPONSE_BODY() {
        return MESSAGE_FACTORY.getMessage("timeout.receiving.response.body", new Object[0]);
    }

    public static String TIMEOUT_RECEIVING_RESPONSE_BODY() {
        return LOCALIZER.localize(LocalizationMessages.localizableTIMEOUT_RECEIVING_RESPONSE_BODY());
    }

    public static Localizable localizableSYNC_OPERATION_NOT_SUPPORTED() {
        return MESSAGE_FACTORY.getMessage("sync.operation.not.supported", new Object[0]);
    }

    public static String SYNC_OPERATION_NOT_SUPPORTED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSYNC_OPERATION_NOT_SUPPORTED());
    }

    public static Localizable localizablePROXY_FAIL_AUTH_HEADER() {
        return MESSAGE_FACTORY.getMessage("proxy.fail.auth.header", new Object[0]);
    }

    public static String PROXY_FAIL_AUTH_HEADER() {
        return LOCALIZER.localize(LocalizationMessages.localizablePROXY_FAIL_AUTH_HEADER());
    }

    public static Localizable localizableWRITE_WHEN_NOT_READY() {
        return MESSAGE_FACTORY.getMessage("write.when.not.ready", new Object[0]);
    }

    public static String WRITE_WHEN_NOT_READY() {
        return LOCALIZER.localize(LocalizationMessages.localizableWRITE_WHEN_NOT_READY());
    }

    public static Localizable localizableCONNECTION_CHANGING_STATE(Object arg0, Object arg1, Object arg2, Object arg3) {
        return MESSAGE_FACTORY.getMessage("connection.changing.state", arg0, arg1, arg2, arg3);
    }

    public static String CONNECTION_CHANGING_STATE(Object arg0, Object arg1, Object arg2, Object arg3) {
        return LOCALIZER.localize(LocalizationMessages.localizableCONNECTION_CHANGING_STATE(arg0, arg1, arg2, arg3));
    }

    public static Localizable localizableCLOSED_WHILE_RECEIVING_RESPONSE() {
        return MESSAGE_FACTORY.getMessage("closed.while.receiving.response", new Object[0]);
    }

    public static String CLOSED_WHILE_RECEIVING_RESPONSE() {
        return LOCALIZER.localize(LocalizationMessages.localizableCLOSED_WHILE_RECEIVING_RESPONSE());
    }

    public static Localizable localizableCONNECTOR_CONFIGURATION(Object arg0) {
        return MESSAGE_FACTORY.getMessage("connector.configuration", arg0);
    }

    public static String CONNECTOR_CONFIGURATION(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableCONNECTOR_CONFIGURATION(arg0));
    }

    public static Localizable localizableSTREAM_CLOSED_FOR_INPUT() {
        return MESSAGE_FACTORY.getMessage("stream.closed.for.input", new Object[0]);
    }

    public static String STREAM_CLOSED_FOR_INPUT() {
        return LOCALIZER.localize(LocalizationMessages.localizableSTREAM_CLOSED_FOR_INPUT());
    }

    public static Localizable localizablePROXY_QOP_NO_SUPPORTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("proxy.qop.no.supported", arg0);
    }

    public static String PROXY_QOP_NO_SUPPORTED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizablePROXY_QOP_NO_SUPPORTED(arg0));
    }

    public static Localizable localizableCLOSED_BY_CLIENT_WHILE_RECEIVING_BODY() {
        return MESSAGE_FACTORY.getMessage("closed.by.client.while.receiving.body", new Object[0]);
    }

    public static String CLOSED_BY_CLIENT_WHILE_RECEIVING_BODY() {
        return LOCALIZER.localize(LocalizationMessages.localizableCLOSED_BY_CLIENT_WHILE_RECEIVING_BODY());
    }

    public static Localizable localizableREDIRECT_LIMIT_REACHED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("redirect.limit.reached", arg0);
    }

    public static String REDIRECT_LIMIT_REACHED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableREDIRECT_LIMIT_REACHED(arg0));
    }

    public static Localizable localizableHTTP_CHUNK_ENCODING_PREFIX_OVERFLOW() {
        return MESSAGE_FACTORY.getMessage("http.chunk.encoding.prefix.overflow", new Object[0]);
    }

    public static String HTTP_CHUNK_ENCODING_PREFIX_OVERFLOW() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_CHUNK_ENCODING_PREFIX_OVERFLOW());
    }

    public static Localizable localizableHTTP_CONNECTION_ESTABLISHING_ILLEGAL_STATE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("http.connection.establishing.illegal.state", arg0);
    }

    public static String HTTP_CONNECTION_ESTABLISHING_ILLEGAL_STATE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_CONNECTION_ESTABLISHING_ILLEGAL_STATE(arg0));
    }

    public static Localizable localizableTRANSPORT_SET_CLASS_LOADER_FAILED() {
        return MESSAGE_FACTORY.getMessage("transport.set.class.loader.failed", new Object[0]);
    }

    public static String TRANSPORT_SET_CLASS_LOADER_FAILED() {
        return LOCALIZER.localize(LocalizationMessages.localizableTRANSPORT_SET_CLASS_LOADER_FAILED());
    }

    public static Localizable localizableCONNECTION_TIMEOUT() {
        return MESSAGE_FACTORY.getMessage("connection.timeout", new Object[0]);
    }

    public static String CONNECTION_TIMEOUT() {
        return LOCALIZER.localize(LocalizationMessages.localizableCONNECTION_TIMEOUT());
    }

    public static Localizable localizableHTTP_REQUEST_NO_BUFFERED_BODY() {
        return MESSAGE_FACTORY.getMessage("http.request.no.buffered.body", new Object[0]);
    }

    public static String HTTP_REQUEST_NO_BUFFERED_BODY() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_REQUEST_NO_BUFFERED_BODY());
    }

    public static Localizable localizableHTTP_BODY_SIZE_OVERFLOW() {
        return MESSAGE_FACTORY.getMessage("http.body.size.overflow", new Object[0]);
    }

    public static String HTTP_BODY_SIZE_OVERFLOW() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_BODY_SIZE_OVERFLOW());
    }

    public static Localizable localizableCLOSED_BY_CLIENT_WHILE_RECEIVING() {
        return MESSAGE_FACTORY.getMessage("closed.by.client.while.receiving", new Object[0]);
    }

    public static String CLOSED_BY_CLIENT_WHILE_RECEIVING() {
        return LOCALIZER.localize(LocalizationMessages.localizableCLOSED_BY_CLIENT_WHILE_RECEIVING());
    }

    public static Localizable localizableCLOSED_WHILE_RECEIVING_BODY() {
        return MESSAGE_FACTORY.getMessage("closed.while.receiving.body", new Object[0]);
    }

    public static String CLOSED_WHILE_RECEIVING_BODY() {
        return LOCALIZER.localize(LocalizationMessages.localizableCLOSED_WHILE_RECEIVING_BODY());
    }

    public static Localizable localizableHTTP_INVALID_CHUNK_SIZE_HEX_VALUE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("http.invalid.chunk.size.hex.value", arg0);
    }

    public static String HTTP_INVALID_CHUNK_SIZE_HEX_VALUE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_INVALID_CHUNK_SIZE_HEX_VALUE(arg0));
    }

    public static Localizable localizableWRITE_LISTENER_SET_ONLY_ONCE() {
        return MESSAGE_FACTORY.getMessage("write.listener.set.only.once", new Object[0]);
    }

    public static String WRITE_LISTENER_SET_ONLY_ONCE() {
        return LOCALIZER.localize(LocalizationMessages.localizableWRITE_LISTENER_SET_ONLY_ONCE());
    }

    public static Localizable localizablePROXY_UNSUPPORTED_SCHEME(Object arg0) {
        return MESSAGE_FACTORY.getMessage("proxy.unsupported.scheme", arg0);
    }

    public static String PROXY_UNSUPPORTED_SCHEME(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizablePROXY_UNSUPPORTED_SCHEME(arg0));
    }

    public static Localizable localizableHTTP_NEGATIVE_CONTENT_LENGTH() {
        return MESSAGE_FACTORY.getMessage("http.negative.content.length", new Object[0]);
    }

    public static String HTTP_NEGATIVE_CONTENT_LENGTH() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_NEGATIVE_CONTENT_LENGTH());
    }

    public static Localizable localizableSSL_SESSION_CLOSED() {
        return MESSAGE_FACTORY.getMessage("ssl.session.closed", new Object[0]);
    }

    public static String SSL_SESSION_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_SESSION_CLOSED());
    }

    public static Localizable localizableCLOSED_WHILE_SENDING_REQUEST() {
        return MESSAGE_FACTORY.getMessage("closed.while.sending.request", new Object[0]);
    }

    public static String CLOSED_WHILE_SENDING_REQUEST() {
        return LOCALIZER.localize(LocalizationMessages.localizableCLOSED_WHILE_SENDING_REQUEST());
    }

    public static Localizable localizableHTTP_INITIAL_LINE_OVERFLOW() {
        return MESSAGE_FACTORY.getMessage("http.initial.line.overflow", new Object[0]);
    }

    public static String HTTP_INITIAL_LINE_OVERFLOW() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_INITIAL_LINE_OVERFLOW());
    }

    public static Localizable localizableTRANSPORT_EXECUTOR_QUEUE_LIMIT_REACHED() {
        return MESSAGE_FACTORY.getMessage("transport.executor.queue.limit.reached", new Object[0]);
    }

    public static String TRANSPORT_EXECUTOR_QUEUE_LIMIT_REACHED() {
        return LOCALIZER.localize(LocalizationMessages.localizableTRANSPORT_EXECUTOR_QUEUE_LIMIT_REACHED());
    }

    public static Localizable localizablePROXY_MISSING_AUTH_HEADER() {
        return MESSAGE_FACTORY.getMessage("proxy.missing.auth.header", new Object[0]);
    }

    public static String PROXY_MISSING_AUTH_HEADER() {
        return LOCALIZER.localize(LocalizationMessages.localizablePROXY_MISSING_AUTH_HEADER());
    }

    public static Localizable localizableTHREAD_POOL_MAX_SIZE_TOO_SMALL() {
        return MESSAGE_FACTORY.getMessage("thread.pool.max.size.too.small", new Object[0]);
    }

    public static String THREAD_POOL_MAX_SIZE_TOO_SMALL() {
        return LOCALIZER.localize(LocalizationMessages.localizableTHREAD_POOL_MAX_SIZE_TOO_SMALL());
    }

    public static Localizable localizableCLOSED_BY_CLIENT_WHILE_SENDING() {
        return MESSAGE_FACTORY.getMessage("closed.by.client.while.sending", new Object[0]);
    }

    public static String CLOSED_BY_CLIENT_WHILE_SENDING() {
        return LOCALIZER.localize(LocalizationMessages.localizableCLOSED_BY_CLIENT_WHILE_SENDING());
    }

    public static Localizable localizableHTTP_REQUEST_BODY_SIZE_NOT_AVAILABLE() {
        return MESSAGE_FACTORY.getMessage("http.request.body.size.not.available", new Object[0]);
    }

    public static String HTTP_REQUEST_BODY_SIZE_NOT_AVAILABLE() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_REQUEST_BODY_SIZE_NOT_AVAILABLE());
    }

    public static Localizable localizableTRANSPORT_EXECUTOR_CLOSED() {
        return MESSAGE_FACTORY.getMessage("transport.executor.closed", new Object[0]);
    }

    public static String TRANSPORT_EXECUTOR_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableTRANSPORT_EXECUTOR_CLOSED());
    }

    public static Localizable localizableHTTP_PACKET_HEADER_OVERFLOW() {
        return MESSAGE_FACTORY.getMessage("http.packet.header.overflow", new Object[0]);
    }

    public static String HTTP_PACKET_HEADER_OVERFLOW() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_PACKET_HEADER_OVERFLOW());
    }

    public static Localizable localizableHTTP_CONNECTION_NOT_IDLE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("http.connection.not.idle", arg0);
    }

    public static String HTTP_CONNECTION_NOT_IDLE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_CONNECTION_NOT_IDLE(arg0));
    }

    public static Localizable localizableTIMEOUT_RECEIVING_RESPONSE() {
        return MESSAGE_FACTORY.getMessage("timeout.receiving.response", new Object[0]);
    }

    public static String TIMEOUT_RECEIVING_RESPONSE() {
        return LOCALIZER.localize(LocalizationMessages.localizableTIMEOUT_RECEIVING_RESPONSE());
    }

    public static Localizable localizablePROXY_407_TWICE() {
        return MESSAGE_FACTORY.getMessage("proxy.407.twice", new Object[0]);
    }

    public static String PROXY_407_TWICE() {
        return LOCALIZER.localize(LocalizationMessages.localizablePROXY_407_TWICE());
    }

    public static Localizable localizableTRANSPORT_CONNECTION_NOT_CLOSED() {
        return MESSAGE_FACTORY.getMessage("transport.connection.not.closed", new Object[0]);
    }

    public static String TRANSPORT_CONNECTION_NOT_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableTRANSPORT_CONNECTION_NOT_CLOSED());
    }

    public static Localizable localizableHTTP_REQUEST_NO_BODY() {
        return MESSAGE_FACTORY.getMessage("http.request.no.body", new Object[0]);
    }

    public static String HTTP_REQUEST_NO_BODY() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_REQUEST_NO_BODY());
    }

    public static Localizable localizablePROXY_PASSWORD_MISSING() {
        return MESSAGE_FACTORY.getMessage("proxy.password.missing", new Object[0]);
    }

    public static String PROXY_PASSWORD_MISSING() {
        return LOCALIZER.localize(LocalizationMessages.localizablePROXY_PASSWORD_MISSING());
    }

    public static Localizable localizableHTTP_INVALID_CONTENT_LENGTH() {
        return MESSAGE_FACTORY.getMessage("http.invalid.content.length", new Object[0]);
    }

    public static String HTTP_INVALID_CONTENT_LENGTH() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_INVALID_CONTENT_LENGTH());
    }

    public static Localizable localizableASYNC_OPERATION_NOT_SUPPORTED() {
        return MESSAGE_FACTORY.getMessage("async.operation.not.supported", new Object[0]);
    }

    public static String ASYNC_OPERATION_NOT_SUPPORTED() {
        return LOCALIZER.localize(LocalizationMessages.localizableASYNC_OPERATION_NOT_SUPPORTED());
    }

    public static Localizable localizablePROXY_USER_NAME_MISSING() {
        return MESSAGE_FACTORY.getMessage("proxy.user.name.missing", new Object[0]);
    }

    public static String PROXY_USER_NAME_MISSING() {
        return LOCALIZER.localize(LocalizationMessages.localizablePROXY_USER_NAME_MISSING());
    }

    public static Localizable localizablePROXY_CONNECT_FAIL(Object arg0) {
        return MESSAGE_FACTORY.getMessage("proxy.connect.fail", arg0);
    }

    public static String PROXY_CONNECT_FAIL(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizablePROXY_CONNECT_FAIL(arg0));
    }

    public static Localizable localizableREDIRECT_NO_LOCATION() {
        return MESSAGE_FACTORY.getMessage("redirect.no.location", new Object[0]);
    }

    public static String REDIRECT_NO_LOCATION() {
        return LOCALIZER.localize(LocalizationMessages.localizableREDIRECT_NO_LOCATION());
    }

    public static Localizable localizableSTREAM_CLOSED() {
        return MESSAGE_FACTORY.getMessage("stream.closed", new Object[0]);
    }

    public static String STREAM_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSTREAM_CLOSED());
    }

    public static Localizable localizableTHREAD_POOL_CORE_SIZE_TOO_SMALL() {
        return MESSAGE_FACTORY.getMessage("thread.pool.core.size.too.small", new Object[0]);
    }

    public static String THREAD_POOL_CORE_SIZE_TOO_SMALL() {
        return LOCALIZER.localize(LocalizationMessages.localizableTHREAD_POOL_CORE_SIZE_TOO_SMALL());
    }

    public static Localizable localizableCONNECTION_CLOSED() {
        return MESSAGE_FACTORY.getMessage("connection.closed", new Object[0]);
    }

    public static String CONNECTION_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableCONNECTION_CLOSED());
    }

    public static Localizable localizableBUFFER_INCORRECT_LENGTH() {
        return MESSAGE_FACTORY.getMessage("buffer.incorrect.length", new Object[0]);
    }

    public static String BUFFER_INCORRECT_LENGTH() {
        return LOCALIZER.localize(LocalizationMessages.localizableBUFFER_INCORRECT_LENGTH());
    }

    public static Localizable localizableREAD_LISTENER_SET_ONLY_ONCE() {
        return MESSAGE_FACTORY.getMessage("read.listener.set.only.once", new Object[0]);
    }

    public static String READ_LISTENER_SET_ONLY_ONCE() {
        return LOCALIZER.localize(LocalizationMessages.localizableREAD_LISTENER_SET_ONLY_ONCE());
    }

    public static Localizable localizableUNEXPECTED_DATA_IN_BUFFER() {
        return MESSAGE_FACTORY.getMessage("unexpected.data.in.buffer", new Object[0]);
    }

    public static String UNEXPECTED_DATA_IN_BUFFER() {
        return LOCALIZER.localize(LocalizationMessages.localizableUNEXPECTED_DATA_IN_BUFFER());
    }

    private static class BundleSupplier
    implements LocalizableMessageFactory.ResourceBundleSupplier {
        private BundleSupplier() {
        }

        @Override
        public ResourceBundle getResourceBundle(Locale locale) {
            return ResourceBundle.getBundle(LocalizationMessages.BUNDLE_NAME, locale);
        }
    }
}

