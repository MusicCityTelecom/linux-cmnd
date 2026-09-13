/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.media.sse;

import java.util.Locale;
import java.util.ResourceBundle;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory;
import org.glassfish.jersey.internal.l10n.Localizer;

public final class LocalizationMessages {
    private static final String BUNDLE_NAME = "org.glassfish.jersey.media.sse.localization";
    private static final LocalizableMessageFactory MESSAGE_FACTORY = new LocalizableMessageFactory("org.glassfish.jersey.media.sse.localization", new BundleSupplier());
    private static final Localizer LOCALIZER = new Localizer();

    public static Localizable localizableOUT_EVENT_NOT_BUILDABLE() {
        return MESSAGE_FACTORY.getMessage("out.event.not.buildable", new Object[0]);
    }

    public static String OUT_EVENT_NOT_BUILDABLE() {
        return LOCALIZER.localize(LocalizationMessages.localizableOUT_EVENT_NOT_BUILDABLE());
    }

    public static Localizable localizableEVENT_SOURCE_DEFAULT_ONERROR() {
        return MESSAGE_FACTORY.getMessage("event.source.default.onerror", new Object[0]);
    }

    public static String EVENT_SOURCE_DEFAULT_ONERROR() {
        return LOCALIZER.localize(LocalizationMessages.localizableEVENT_SOURCE_DEFAULT_ONERROR());
    }

    public static Localizable localizablePARAM_NULL(Object arg0) {
        return MESSAGE_FACTORY.getMessage("param.null", arg0);
    }

    public static String PARAM_NULL(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizablePARAM_NULL(arg0));
    }

    public static Localizable localizableOUT_EVENT_MEDIA_TYPE_NULL() {
        return MESSAGE_FACTORY.getMessage("out.event.media.type.null", new Object[0]);
    }

    public static String OUT_EVENT_MEDIA_TYPE_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableOUT_EVENT_MEDIA_TYPE_NULL());
    }

    public static Localizable localizablePARAMS_NULL() {
        return MESSAGE_FACTORY.getMessage("params.null", new Object[0]);
    }

    public static String PARAMS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizablePARAMS_NULL());
    }

    public static Localizable localizableEVENT_DATA_READER_NOT_FOUND() {
        return MESSAGE_FACTORY.getMessage("event.data.reader.not.found", new Object[0]);
    }

    public static String EVENT_DATA_READER_NOT_FOUND() {
        return LOCALIZER.localize(LocalizationMessages.localizableEVENT_DATA_READER_NOT_FOUND());
    }

    public static Localizable localizableIN_EVENT_FIELD_NOT_RECOGNIZED(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("in.event.field.not.recognized", arg0, arg1);
    }

    public static String IN_EVENT_FIELD_NOT_RECOGNIZED(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableIN_EVENT_FIELD_NOT_RECOGNIZED(arg0, arg1));
    }

    public static Localizable localizableUNSUPPORTED_WEBTARGET_TYPE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("unsupported.webtarget.type", arg0);
    }

    public static String UNSUPPORTED_WEBTARGET_TYPE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableUNSUPPORTED_WEBTARGET_TYPE(arg0));
    }

    public static Localizable localizableEVENT_SOURCE_ALREADY_CONNECTED() {
        return MESSAGE_FACTORY.getMessage("event.source.already.connected", new Object[0]);
    }

    public static String EVENT_SOURCE_ALREADY_CONNECTED() {
        return LOCALIZER.localize(LocalizationMessages.localizableEVENT_SOURCE_ALREADY_CONNECTED());
    }

    public static Localizable localizableEVENT_SINK_CLOSE_FAILED() {
        return MESSAGE_FACTORY.getMessage("event.sink.close.failed", new Object[0]);
    }

    public static String EVENT_SINK_CLOSE_FAILED() {
        return LOCALIZER.localize(LocalizationMessages.localizableEVENT_SINK_CLOSE_FAILED());
    }

    public static Localizable localizableIN_EVENT_RETRY_PARSE_ERROR(Object arg0) {
        return MESSAGE_FACTORY.getMessage("in.event.retry.parse.error", arg0);
    }

    public static String IN_EVENT_RETRY_PARSE_ERROR(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableIN_EVENT_RETRY_PARSE_ERROR(arg0));
    }

    public static Localizable localizableEVENT_SOURCE_SHUTDOWN_INTERRUPTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("event.source.shutdown.interrupted", arg0);
    }

    public static String EVENT_SOURCE_SHUTDOWN_INTERRUPTED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableEVENT_SOURCE_SHUTDOWN_INTERRUPTED(arg0));
    }

    public static Localizable localizableOUT_EVENT_DATA_NULL() {
        return MESSAGE_FACTORY.getMessage("out.event.data.null", new Object[0]);
    }

    public static String OUT_EVENT_DATA_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableOUT_EVENT_DATA_NULL());
    }

    public static Localizable localizableEVENT_SOURCE_SHUTDOWN_TIMEOUT(Object arg0) {
        return MESSAGE_FACTORY.getMessage("event.source.shutdown.timeout", arg0);
    }

    public static String EVENT_SOURCE_SHUTDOWN_TIMEOUT(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableEVENT_SOURCE_SHUTDOWN_TIMEOUT(arg0));
    }

    public static Localizable localizableEVENT_SOURCE_OPEN_CONNECTION_INTERRUPTED() {
        return MESSAGE_FACTORY.getMessage("event.source.open.connection.interrupted", new Object[0]);
    }

    public static String EVENT_SOURCE_OPEN_CONNECTION_INTERRUPTED() {
        return LOCALIZER.localize(LocalizationMessages.localizableEVENT_SOURCE_OPEN_CONNECTION_INTERRUPTED());
    }

    public static Localizable localizableOUT_EVENT_DATA_TYPE_NULL() {
        return MESSAGE_FACTORY.getMessage("out.event.data.type.null", new Object[0]);
    }

    public static String OUT_EVENT_DATA_TYPE_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableOUT_EVENT_DATA_TYPE_NULL());
    }

    public static Localizable localizableEVENT_SOURCE_ALREADY_CLOSED() {
        return MESSAGE_FACTORY.getMessage("event.source.already.closed", new Object[0]);
    }

    public static String EVENT_SOURCE_ALREADY_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableEVENT_SOURCE_ALREADY_CLOSED());
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

