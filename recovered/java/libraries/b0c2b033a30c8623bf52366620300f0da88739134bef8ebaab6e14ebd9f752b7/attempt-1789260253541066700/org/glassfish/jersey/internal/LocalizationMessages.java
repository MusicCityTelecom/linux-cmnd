/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.util.Locale;
import java.util.ResourceBundle;
import org.glassfish.jersey.internal.l10n.Localizable;
import org.glassfish.jersey.internal.l10n.LocalizableMessageFactory;
import org.glassfish.jersey.internal.l10n.Localizer;

public final class LocalizationMessages {
    private static final String BUNDLE_NAME = "org.glassfish.jersey.internal.localization";
    private static final LocalizableMessageFactory MESSAGE_FACTORY = new LocalizableMessageFactory("org.glassfish.jersey.internal.localization", new BundleSupplier());
    private static final Localizer LOCALIZER = new Localizer();

    public static Localizable localizableERRORS_AND_WARNINGS_DETECTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("errors.and.warnings.detected", arg0);
    }

    public static String ERRORS_AND_WARNINGS_DETECTED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERRORS_AND_WARNINGS_DETECTED(arg0));
    }

    public static Localizable localizableCOMMITTING_STREAM_BUFFERING_ILLEGAL_STATE() {
        return MESSAGE_FACTORY.getMessage("committing.stream.buffering.illegal.state", new Object[0]);
    }

    public static String COMMITTING_STREAM_BUFFERING_ILLEGAL_STATE() {
        return LOCALIZER.localize(LocalizationMessages.localizableCOMMITTING_STREAM_BUFFERING_ILLEGAL_STATE());
    }

    public static Localizable localizableLOCALE_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("locale.is.null", new Object[0]);
    }

    public static String LOCALE_IS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableLOCALE_IS_NULL());
    }

    public static Localizable localizableSSL_KMF_PROVIDER_NOT_REGISTERED() {
        return MESSAGE_FACTORY.getMessage("ssl.kmf.provider.not.registered", new Object[0]);
    }

    public static String SSL_KMF_PROVIDER_NOT_REGISTERED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KMF_PROVIDER_NOT_REGISTERED());
    }

    public static Localizable localizableURI_COMPONENT_ENCODED_OCTET_INVALID_DIGIT(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("uri.component.encoded.octet.invalid.digit", arg0, arg1);
    }

    public static String URI_COMPONENT_ENCODED_OCTET_INVALID_DIGIT(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_COMPONENT_ENCODED_OCTET_INVALID_DIGIT(arg0, arg1));
    }

    public static Localizable localizableURI_PARSER_COMPONENT_DELIMITER(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("uri.parser.component.delimiter", arg0, arg1);
    }

    public static String URI_PARSER_COMPONENT_DELIMITER(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_PARSER_COMPONENT_DELIMITER(arg0, arg1));
    }

    public static Localizable localizableSSL_KMF_ALGORITHM_NOT_SUPPORTED() {
        return MESSAGE_FACTORY.getMessage("ssl.kmf.algorithm.not.supported", new Object[0]);
    }

    public static String SSL_KMF_ALGORITHM_NOT_SUPPORTED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KMF_ALGORITHM_NOT_SUPPORTED());
    }

    public static Localizable localizableERROR_MBR_ISREADABLE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.mbr.isreadable", arg0);
    }

    public static String ERROR_MBR_ISREADABLE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_MBR_ISREADABLE(arg0));
    }

    public static Localizable localizableSSL_KMF_INIT_FAILED() {
        return MESSAGE_FACTORY.getMessage("ssl.kmf.init.failed", new Object[0]);
    }

    public static String SSL_KMF_INIT_FAILED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KMF_INIT_FAILED());
    }

    public static Localizable localizableOVERRIDING_METHOD_CANNOT_BE_FOUND(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("overriding.method.cannot.be.found", arg0, arg1);
    }

    public static String OVERRIDING_METHOD_CANNOT_BE_FOUND(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableOVERRIDING_METHOD_CANNOT_BE_FOUND(arg0, arg1));
    }

    public static Localizable localizableERROR_INTERCEPTOR_READER_PROCEED() {
        return MESSAGE_FACTORY.getMessage("error.interceptor.reader.proceed", new Object[0]);
    }

    public static String ERROR_INTERCEPTOR_READER_PROCEED() {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_INTERCEPTOR_READER_PROCEED());
    }

    public static Localizable localizableMEDIA_TYPE_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("media.type.is.null", new Object[0]);
    }

    public static String MEDIA_TYPE_IS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableMEDIA_TYPE_IS_NULL());
    }

    public static Localizable localizableURI_COMPONENT_ENCODED_OCTET_MALFORMED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("uri.component.encoded.octet.malformed", arg0);
    }

    public static String URI_COMPONENT_ENCODED_OCTET_MALFORMED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_COMPONENT_ENCODED_OCTET_MALFORMED(arg0));
    }

    public static Localizable localizableSSL_KS_INTEGRITY_ALGORITHM_NOT_FOUND() {
        return MESSAGE_FACTORY.getMessage("ssl.ks.integrity.algorithm.not.found", new Object[0]);
    }

    public static String SSL_KS_INTEGRITY_ALGORITHM_NOT_FOUND() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KS_INTEGRITY_ALGORITHM_NOT_FOUND());
    }

    public static Localizable localizableMESSAGE_CONTENT_BUFFER_RESET_FAILED() {
        return MESSAGE_FACTORY.getMessage("message.content.buffer.reset.failed", new Object[0]);
    }

    public static String MESSAGE_CONTENT_BUFFER_RESET_FAILED() {
        return LOCALIZER.localize(LocalizationMessages.localizableMESSAGE_CONTENT_BUFFER_RESET_FAILED());
    }

    public static Localizable localizableTEMPLATE_PARAM_NULL() {
        return MESSAGE_FACTORY.getMessage("template.param.null", new Object[0]);
    }

    public static String TEMPLATE_PARAM_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableTEMPLATE_PARAM_NULL());
    }

    public static Localizable localizableSSL_TMF_INIT_FAILED() {
        return MESSAGE_FACTORY.getMessage("ssl.tmf.init.failed", new Object[0]);
    }

    public static String SSL_TMF_INIT_FAILED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_TMF_INIT_FAILED());
    }

    public static Localizable localizableURI_BUILDER_CLASS_PATH_ANNOTATION_MISSING(Object arg0) {
        return MESSAGE_FACTORY.getMessage("uri.builder.class.path.annotation.missing", arg0);
    }

    public static String URI_BUILDER_CLASS_PATH_ANNOTATION_MISSING(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_BUILDER_CLASS_PATH_ANNOTATION_MISSING(arg0));
    }

    public static Localizable localizableUNHANDLED_EXCEPTION_DETECTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("unhandled.exception.detected", arg0);
    }

    public static String UNHANDLED_EXCEPTION_DETECTED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableUNHANDLED_EXCEPTION_DETECTED(arg0));
    }

    public static Localizable localizableNOT_SUPPORTED_ON_OUTBOUND_MESSAGE() {
        return MESSAGE_FACTORY.getMessage("not.supported.on.outbound.message", new Object[0]);
    }

    public static String NOT_SUPPORTED_ON_OUTBOUND_MESSAGE() {
        return LOCALIZER.localize(LocalizationMessages.localizableNOT_SUPPORTED_ON_OUTBOUND_MESSAGE());
    }

    public static Localizable localizableUNABLE_TO_PARSE_HEADER_VALUE(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("unable.to.parse.header.value", arg0, arg1);
    }

    public static String UNABLE_TO_PARSE_HEADER_VALUE(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableUNABLE_TO_PARSE_HEADER_VALUE(arg0, arg1));
    }

    public static Localizable localizableERROR_PROVIDER_CONSTRAINED_TO_WRONG_RUNTIME(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("error.provider.constrainedTo.wrong.runtime", arg0, arg1, arg2);
    }

    public static String ERROR_PROVIDER_CONSTRAINED_TO_WRONG_RUNTIME(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_PROVIDER_CONSTRAINED_TO_WRONG_RUNTIME(arg0, arg1, arg2));
    }

    public static Localizable localizableSSL_KMF_NO_PASSWORD_SET(Object arg0) {
        return MESSAGE_FACTORY.getMessage("ssl.kmf.no.password.set", arg0);
    }

    public static String SSL_KMF_NO_PASSWORD_SET(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KMF_NO_PASSWORD_SET(arg0));
    }

    public static Localizable localizablePARAM_NULL(Object arg0) {
        return MESSAGE_FACTORY.getMessage("param.null", arg0);
    }

    public static String PARAM_NULL(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizablePARAM_NULL(arg0));
    }

    public static Localizable localizableHTTP_HEADER_UNBALANCED_QUOTED() {
        return MESSAGE_FACTORY.getMessage("http.header.unbalanced.quoted", new Object[0]);
    }

    public static String HTTP_HEADER_UNBALANCED_QUOTED() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_HEADER_UNBALANCED_QUOTED());
    }

    public static Localizable localizableLINK_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("link.is.null", new Object[0]);
    }

    public static String LINK_IS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableLINK_IS_NULL());
    }

    public static Localizable localizableERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_PART_OF_NAME(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("error.template.parser.illegal.char.partOf.name", arg0, arg1, arg2);
    }

    public static String ERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_PART_OF_NAME(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_PART_OF_NAME(arg0, arg1, arg2));
    }

    public static Localizable localizablePROPERTIES_HELPER_DEPRECATED_PROPERTY_NAME(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("properties.helper.deprecated.property.name", arg0, arg1);
    }

    public static String PROPERTIES_HELPER_DEPRECATED_PROPERTY_NAME(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizablePROPERTIES_HELPER_DEPRECATED_PROPERTY_NAME(arg0, arg1));
    }

    public static Localizable localizableCOMPONENT_CANNOT_BE_NULL() {
        return MESSAGE_FACTORY.getMessage("component.cannot.be.null", new Object[0]);
    }

    public static String COMPONENT_CANNOT_BE_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableCOMPONENT_CANNOT_BE_NULL());
    }

    public static Localizable localizableURI_BUILDER_ANNOTATEDELEMENT_PATH_ANNOTATION_MISSING(Object arg0) {
        return MESSAGE_FACTORY.getMessage("uri.builder.annotatedelement.path.annotation.missing", arg0);
    }

    public static String URI_BUILDER_ANNOTATEDELEMENT_PATH_ANNOTATION_MISSING(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_BUILDER_ANNOTATEDELEMENT_PATH_ANNOTATION_MISSING(arg0));
    }

    public static Localizable localizableERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_CONTEXT(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.service.locator.provider.instance.feature.context", arg0);
    }

    public static String ERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_CONTEXT(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_CONTEXT(arg0));
    }

    public static Localizable localizableERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_START_NAME(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("error.template.parser.illegal.char.start.name", arg0, arg1, arg2);
    }

    public static String ERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_START_NAME(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_START_NAME(arg0, arg1, arg2));
    }

    public static Localizable localizableCONFIGURATION_NOT_MODIFIABLE() {
        return MESSAGE_FACTORY.getMessage("configuration.not.modifiable", new Object[0]);
    }

    public static String CONFIGURATION_NOT_MODIFIABLE() {
        return LOCALIZER.localize(LocalizationMessages.localizableCONFIGURATION_NOT_MODIFIABLE());
    }

    public static Localizable localizableSSL_TS_CERT_LOAD_ERROR() {
        return MESSAGE_FACTORY.getMessage("ssl.ts.cert.load.error", new Object[0]);
    }

    public static String SSL_TS_CERT_LOAD_ERROR() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_TS_CERT_LOAD_ERROR());
    }

    public static Localizable localizableERROR_FINDING_EXCEPTION_MAPPER_TYPE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.finding.exception.mapper.type", arg0);
    }

    public static String ERROR_FINDING_EXCEPTION_MAPPER_TYPE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_FINDING_EXCEPTION_MAPPER_TYPE(arg0));
    }

    public static Localizable localizableERROR_NEWCOOKIE_EXPIRES(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.newcookie.expires", arg0);
    }

    public static String ERROR_NEWCOOKIE_EXPIRES(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_NEWCOOKIE_EXPIRES(arg0));
    }

    public static Localizable localizableILLEGAL_INITIAL_CAPACITY(Object arg0) {
        return MESSAGE_FACTORY.getMessage("illegal.initial.capacity", arg0);
    }

    public static String ILLEGAL_INITIAL_CAPACITY(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableILLEGAL_INITIAL_CAPACITY(arg0));
    }

    public static Localizable localizableSSL_KS_CERT_LOAD_ERROR() {
        return MESSAGE_FACTORY.getMessage("ssl.ks.cert.load.error", new Object[0]);
    }

    public static String SSL_KS_CERT_LOAD_ERROR() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KS_CERT_LOAD_ERROR());
    }

    public static Localizable localizableERROR_READING_ENTITY_FROM_INPUT_STREAM() {
        return MESSAGE_FACTORY.getMessage("error.reading.entity.from.input.stream", new Object[0]);
    }

    public static String ERROR_READING_ENTITY_FROM_INPUT_STREAM() {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_READING_ENTITY_FROM_INPUT_STREAM());
    }

    public static Localizable localizableERROR_PROVIDER_CONSTRAINED_TO_IGNORED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.provider.constrainedTo.ignored", arg0);
    }

    public static String ERROR_PROVIDER_CONSTRAINED_TO_IGNORED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_PROVIDER_CONSTRAINED_TO_IGNORED(arg0));
    }

    public static Localizable localizableHTTP_HEADER_WHITESPACE_NOT_ALLOWED() {
        return MESSAGE_FACTORY.getMessage("http.header.whitespace.not.allowed", new Object[0]);
    }

    public static String HTTP_HEADER_WHITESPACE_NOT_ALLOWED() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_HEADER_WHITESPACE_NOT_ALLOWED());
    }

    public static Localizable localizableILLEGAL_CONFIG_SYNTAX() {
        return MESSAGE_FACTORY.getMessage("illegal.config.syntax", new Object[0]);
    }

    public static String ILLEGAL_CONFIG_SYNTAX() {
        return LOCALIZER.localize(LocalizationMessages.localizableILLEGAL_CONFIG_SYNTAX());
    }

    public static Localizable localizableSSL_TS_FILE_NOT_FOUND(Object arg0) {
        return MESSAGE_FACTORY.getMessage("ssl.ts.file.not.found", arg0);
    }

    public static String SSL_TS_FILE_NOT_FOUND(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_TS_FILE_NOT_FOUND(arg0));
    }

    public static Localizable localizableERROR_CAUGHT_WHILE_LOADING_SPI_PROVIDERS() {
        return MESSAGE_FACTORY.getMessage("error.caught.while.loading.spi.providers", new Object[0]);
    }

    public static String ERROR_CAUGHT_WHILE_LOADING_SPI_PROVIDERS() {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_CAUGHT_WHILE_LOADING_SPI_PROVIDERS());
    }

    public static Localizable localizableMULTIPLE_MATCHING_CONSTRUCTORS_FOUND(Object arg0, Object arg1, Object arg2, Object arg3) {
        return MESSAGE_FACTORY.getMessage("multiple.matching.constructors.found", arg0, arg1, arg2, arg3);
    }

    public static String MULTIPLE_MATCHING_CONSTRUCTORS_FOUND(Object arg0, Object arg1, Object arg2, Object arg3) {
        return LOCALIZER.localize(LocalizationMessages.localizableMULTIPLE_MATCHING_CONSTRUCTORS_FOUND(arg0, arg1, arg2, arg3));
    }

    public static Localizable localizableMETHOD_NOT_GETTER_NOR_SETTER() {
        return MESSAGE_FACTORY.getMessage("method.not.getter.nor.setter", new Object[0]);
    }

    public static String METHOD_NOT_GETTER_NOR_SETTER() {
        return LOCALIZER.localize(LocalizationMessages.localizableMETHOD_NOT_GETTER_NOR_SETTER());
    }

    public static Localizable localizableERROR_PARSING_ENTITY_TAG(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.parsing.entity.tag", arg0);
    }

    public static String ERROR_PARSING_ENTITY_TAG(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_PARSING_ENTITY_TAG(arg0));
    }

    public static Localizable localizableSSL_CTX_ALGORITHM_NOT_SUPPORTED() {
        return MESSAGE_FACTORY.getMessage("ssl.ctx.algorithm.not.supported", new Object[0]);
    }

    public static String SSL_CTX_ALGORITHM_NOT_SUPPORTED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_CTX_ALGORITHM_NOT_SUPPORTED());
    }

    public static Localizable localizableERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_AFTER_NAME(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("error.template.parser.illegal.char.after.name", arg0, arg1, arg2);
    }

    public static String ERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_AFTER_NAME(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_TEMPLATE_PARSER_ILLEGAL_CHAR_AFTER_NAME(arg0, arg1, arg2));
    }

    public static Localizable localizableINJECTION_MANAGER_FACTORY_NOT_FOUND() {
        return MESSAGE_FACTORY.getMessage("injection.manager.factory.not.found", new Object[0]);
    }

    public static String INJECTION_MANAGER_FACTORY_NOT_FOUND() {
        return LOCALIZER.localize(LocalizationMessages.localizableINJECTION_MANAGER_FACTORY_NOT_FOUND());
    }

    public static Localizable localizableOUTPUT_STREAM_CLOSED() {
        return MESSAGE_FACTORY.getMessage("output.stream.closed", new Object[0]);
    }

    public static String OUTPUT_STREAM_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableOUTPUT_STREAM_CLOSED());
    }

    public static Localizable localizableDEPENDENT_CLASS_OF_DEFAULT_PROVIDER_NOT_FOUND(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("dependent.class.of.default.provider.not.found", arg0, arg1);
    }

    public static String DEPENDENT_CLASS_OF_DEFAULT_PROVIDER_NOT_FOUND(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableDEPENDENT_CLASS_OF_DEFAULT_PROVIDER_NOT_FOUND(arg0, arg1));
    }

    public static Localizable localizableENTITY_TAG_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("entity.tag.is.null", new Object[0]);
    }

    public static String ENTITY_TAG_IS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableENTITY_TAG_IS_NULL());
    }

    public static Localizable localizableFEATURE_CONSTRAINED_TO_IGNORED(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("feature.constrainedTo.ignored", arg0, arg1, arg2);
    }

    public static String FEATURE_CONSTRAINED_TO_IGNORED(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizableFEATURE_CONSTRAINED_TO_IGNORED(arg0, arg1, arg2));
    }

    public static Localizable localizableINPUT_STREAM_CLOSED() {
        return MESSAGE_FACTORY.getMessage("input.stream.closed", new Object[0]);
    }

    public static String INPUT_STREAM_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableINPUT_STREAM_CLOSED());
    }

    public static Localizable localizableCOOKIE_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("cookie.is.null", new Object[0]);
    }

    public static String COOKIE_IS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableCOOKIE_IS_NULL());
    }

    public static Localizable localizableNEW_COOKIE_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("new.cookie.is.null", new Object[0]);
    }

    public static String NEW_COOKIE_IS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableNEW_COOKIE_IS_NULL());
    }

    public static Localizable localizableINJECTION_ERROR_LOCAL_CLASS_NOT_SUPPORTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("injection.error.local.class.not.supported", arg0);
    }

    public static String INJECTION_ERROR_LOCAL_CLASS_NOT_SUPPORTED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableINJECTION_ERROR_LOCAL_CLASS_NOT_SUPPORTED(arg0));
    }

    public static Localizable localizableSSL_TS_PROVIDERS_NOT_REGISTERED() {
        return MESSAGE_FACTORY.getMessage("ssl.ts.providers.not.registered", new Object[0]);
    }

    public static String SSL_TS_PROVIDERS_NOT_REGISTERED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_TS_PROVIDERS_NOT_REGISTERED());
    }

    public static Localizable localizableINJECTION_ERROR_NONSTATIC_MEMBER_CLASS_NOT_SUPPORTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("injection.error.nonstatic.member.class.not.supported", arg0);
    }

    public static String INJECTION_ERROR_NONSTATIC_MEMBER_CLASS_NOT_SUPPORTED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableINJECTION_ERROR_NONSTATIC_MEMBER_CLASS_NOT_SUPPORTED(arg0));
    }

    public static Localizable localizableUNKNOWN_DESCRIPTOR_TYPE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("unknown.descriptor.type", arg0);
    }

    public static String UNKNOWN_DESCRIPTOR_TYPE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableUNKNOWN_DESCRIPTOR_TYPE(arg0));
    }

    public static Localizable localizableURI_BUILDER_SCHEME_PART_NULL() {
        return MESSAGE_FACTORY.getMessage("uri.builder.scheme.part.null", new Object[0]);
    }

    public static String URI_BUILDER_SCHEME_PART_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_BUILDER_SCHEME_PART_NULL());
    }

    public static Localizable localizableMATRIX_PARAM_NULL() {
        return MESSAGE_FACTORY.getMessage("matrix.param.null", new Object[0]);
    }

    public static String MATRIX_PARAM_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableMATRIX_PARAM_NULL());
    }

    public static Localizable localizableWARNINGS_DETECTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("warnings.detected", arg0);
    }

    public static String WARNINGS_DETECTED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableWARNINGS_DETECTED(arg0));
    }

    public static Localizable localizableHINT_MSG(Object arg0) {
        return MESSAGE_FACTORY.getMessage("hint.msg", arg0);
    }

    public static String HINT_MSG(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableHINT_MSG(arg0));
    }

    public static Localizable localizableSSL_TS_LOAD_ERROR(Object arg0) {
        return MESSAGE_FACTORY.getMessage("ssl.ts.load.error", arg0);
    }

    public static String SSL_TS_LOAD_ERROR(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_TS_LOAD_ERROR(arg0));
    }

    public static Localizable localizableERROR_PROVIDER_REGISTERED_WRONG_RUNTIME(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("error.provider.registered.wrong.runtime", arg0, arg1);
    }

    public static String ERROR_PROVIDER_REGISTERED_WRONG_RUNTIME(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_PROVIDER_REGISTERED_WRONG_RUNTIME(arg0, arg1));
    }

    public static Localizable localizableSSL_KMF_NO_PASSWORD_FOR_PROVIDER_BASED_KS() {
        return MESSAGE_FACTORY.getMessage("ssl.kmf.no.password.for.provider.based.ks", new Object[0]);
    }

    public static String SSL_KMF_NO_PASSWORD_FOR_PROVIDER_BASED_KS() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KMF_NO_PASSWORD_FOR_PROVIDER_BASED_KS());
    }

    public static Localizable localizableERROR_PARAMETER_INVALID_CHAR_VALUE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.parameter.invalid.char.value", arg0);
    }

    public static String ERROR_PARAMETER_INVALID_CHAR_VALUE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_PARAMETER_INVALID_CHAR_VALUE(arg0));
    }

    public static Localizable localizableURI_PARSER_SCHEME_EXPECTED(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("uri.parser.scheme.expected", arg0, arg1);
    }

    public static String URI_PARSER_SCHEME_EXPECTED(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_PARSER_SCHEME_EXPECTED(arg0, arg1));
    }

    public static Localizable localizableTHREAD_POOL_EXECUTOR_PROVIDER_CLOSED() {
        return MESSAGE_FACTORY.getMessage("thread.pool.executor.provider.closed", new Object[0]);
    }

    public static String THREAD_POOL_EXECUTOR_PROVIDER_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableTHREAD_POOL_EXECUTOR_PROVIDER_CLOSED());
    }

    public static Localizable localizableMBW_TRYING_TO_CLOSE_STREAM(Object arg0) {
        return MESSAGE_FACTORY.getMessage("mbw.trying.to.close.stream", arg0);
    }

    public static String MBW_TRYING_TO_CLOSE_STREAM(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableMBW_TRYING_TO_CLOSE_STREAM(arg0));
    }

    public static Localizable localizableCOMPONENT_CONTRACTS_EMPTY_OR_NULL(Object arg0) {
        return MESSAGE_FACTORY.getMessage("component.contracts.empty.or.null", arg0);
    }

    public static String COMPONENT_CONTRACTS_EMPTY_OR_NULL(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableCOMPONENT_CONTRACTS_EMPTY_OR_NULL(arg0));
    }

    public static Localizable localizablePROVIDER_NOT_FOUND(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("provider.not.found", arg0, arg1);
    }

    public static String PROVIDER_NOT_FOUND(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizablePROVIDER_NOT_FOUND(arg0, arg1));
    }

    public static Localizable localizableTOO_MANY_HEADER_VALUES(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("too.many.header.values", arg0, arg1);
    }

    public static String TOO_MANY_HEADER_VALUES(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableTOO_MANY_HEADER_VALUES(arg0, arg1));
    }

    public static Localizable localizableCACHE_CONTROL_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("cache.control.is.null", new Object[0]);
    }

    public static String CACHE_CONTROL_IS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableCACHE_CONTROL_IS_NULL());
    }

    public static Localizable localizableHTTP_HEADER_END_OF_HEADER() {
        return MESSAGE_FACTORY.getMessage("http.header.end.of.header", new Object[0]);
    }

    public static String HTTP_HEADER_END_OF_HEADER() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_HEADER_END_OF_HEADER());
    }

    public static Localizable localizableUSING_SCHEDULER_PROVIDER(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("using.scheduler.provider", arg0, arg1);
    }

    public static String USING_SCHEDULER_PROVIDER(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableUSING_SCHEDULER_PROVIDER(arg0, arg1));
    }

    public static Localizable localizableHTTP_HEADER_COMMENTS_NOT_ALLOWED() {
        return MESSAGE_FACTORY.getMessage("http.header.comments.not.allowed", new Object[0]);
    }

    public static String HTTP_HEADER_COMMENTS_NOT_ALLOWED() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_HEADER_COMMENTS_NOT_ALLOWED());
    }

    public static Localizable localizableCOMPONENT_CLASS_CANNOT_BE_NULL() {
        return MESSAGE_FACTORY.getMessage("component.class.cannot.be.null", new Object[0]);
    }

    public static String COMPONENT_CLASS_CANNOT_BE_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableCOMPONENT_CLASS_CANNOT_BE_NULL());
    }

    public static Localizable localizableURI_BUILDER_SCHEMA_PART_OPAQUE() {
        return MESSAGE_FACTORY.getMessage("uri.builder.schema.part.opaque", new Object[0]);
    }

    public static String URI_BUILDER_SCHEMA_PART_OPAQUE() {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_BUILDER_SCHEMA_PART_OPAQUE());
    }

    public static Localizable localizableNO_ERROR_PROCESSING_IN_SCOPE() {
        return MESSAGE_FACTORY.getMessage("no.error.processing.in.scope", new Object[0]);
    }

    public static String NO_ERROR_PROCESSING_IN_SCOPE() {
        return LOCALIZER.localize(LocalizationMessages.localizableNO_ERROR_PROCESSING_IN_SCOPE());
    }

    public static Localizable localizableCONTRACT_NOT_SUPPORTED(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("contract.not.supported", arg0, arg1);
    }

    public static String CONTRACT_NOT_SUPPORTED(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableCONTRACT_NOT_SUPPORTED(arg0, arg1));
    }

    public static Localizable localizableINVALID_SPI_CLASSES(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("invalid.spi.classes", arg0, arg1);
    }

    public static String INVALID_SPI_CLASSES(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableINVALID_SPI_CLASSES(arg0, arg1));
    }

    public static Localizable localizablePROVIDER_COULD_NOT_BE_CREATED(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("provider.could.not.be.created", arg0, arg1, arg2);
    }

    public static String PROVIDER_COULD_NOT_BE_CREATED(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizablePROVIDER_COULD_NOT_BE_CREATED(arg0, arg1, arg2));
    }

    public static Localizable localizableERROR_NOTFOUND_MESSAGEBODYREADER(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("error.notfound.messagebodyreader", arg0, arg1, arg2);
    }

    public static String ERROR_NOTFOUND_MESSAGEBODYREADER(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_NOTFOUND_MESSAGEBODYREADER(arg0, arg1, arg2));
    }

    public static Localizable localizableERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_READER_INTERCEPTOR_CONTEXT(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.service.locator.provider.instance.feature.reader.interceptor.context", arg0);
    }

    public static String ERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_READER_INTERCEPTOR_CONTEXT(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_READER_INTERCEPTOR_CONTEXT(arg0));
    }

    public static Localizable localizableUSING_EXECUTOR_PROVIDER(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("using.executor.provider", arg0, arg1);
    }

    public static String USING_EXECUTOR_PROVIDER(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableUSING_EXECUTOR_PROVIDER(arg0, arg1));
    }

    public static Localizable localizableERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_WRITER_INTERCEPTOR_CONTEXT(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.service.locator.provider.instance.feature.writer.interceptor.context", arg0);
    }

    public static String ERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_WRITER_INTERCEPTOR_CONTEXT(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_SERVICE_LOCATOR_PROVIDER_INSTANCE_FEATURE_WRITER_INTERCEPTOR_CONTEXT(arg0));
    }

    public static Localizable localizableIGNORED_SCHEDULER_PROVIDERS(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("ignored.scheduler.providers", arg0, arg1);
    }

    public static String IGNORED_SCHEDULER_PROVIDERS(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableIGNORED_SCHEDULER_PROVIDERS(arg0, arg1));
    }

    public static Localizable localizableDEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("dependent.class.of.provider.not.found", arg0, arg1, arg2);
    }

    public static String DEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizableDEPENDENT_CLASS_OF_PROVIDER_NOT_FOUND(arg0, arg1, arg2));
    }

    public static Localizable localizableHTTP_HEADER_NO_END_SEPARATOR(Object arg0) {
        return MESSAGE_FACTORY.getMessage("http.header.no.end.separator", arg0);
    }

    public static String HTTP_HEADER_NO_END_SEPARATOR(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_HEADER_NO_END_SEPARATOR(arg0));
    }

    public static Localizable localizableWARNING_PROPERTIES() {
        return MESSAGE_FACTORY.getMessage("warning.properties", new Object[0]);
    }

    public static String WARNING_PROPERTIES() {
        return LOCALIZER.localize(LocalizationMessages.localizableWARNING_PROPERTIES());
    }

    public static Localizable localizableSSL_KS_IMPL_NOT_FOUND() {
        return MESSAGE_FACTORY.getMessage("ssl.ks.impl.not.found", new Object[0]);
    }

    public static String SSL_KS_IMPL_NOT_FOUND() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KS_IMPL_NOT_FOUND());
    }

    public static Localizable localizableERROR_PROVIDER_AND_RESOURCE_CONSTRAINED_TO_IGNORED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.provider.and.resource.constrainedTo.ignored", arg0);
    }

    public static String ERROR_PROVIDER_AND_RESOURCE_CONSTRAINED_TO_IGNORED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_PROVIDER_AND_RESOURCE_CONSTRAINED_TO_IGNORED(arg0));
    }

    public static Localizable localizableINVALID_PORT() {
        return MESSAGE_FACTORY.getMessage("invalid.port", new Object[0]);
    }

    public static String INVALID_PORT() {
        return LOCALIZER.localize(LocalizationMessages.localizableINVALID_PORT());
    }

    public static Localizable localizableERROR_INTERCEPTOR_WRITER_PROCEED() {
        return MESSAGE_FACTORY.getMessage("error.interceptor.writer.proceed", new Object[0]);
    }

    public static String ERROR_INTERCEPTOR_WRITER_PROCEED() {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_INTERCEPTOR_WRITER_PROCEED());
    }

    public static Localizable localizableHTTP_HEADER_NO_CHARS_BETWEEN_SEPARATORS(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("http.header.no.chars.between.separators", arg0, arg1);
    }

    public static String HTTP_HEADER_NO_CHARS_BETWEEN_SEPARATORS(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_HEADER_NO_CHARS_BETWEEN_SEPARATORS(arg0, arg1));
    }

    public static Localizable localizableILLEGAL_LOAD_FACTOR(Object arg0) {
        return MESSAGE_FACTORY.getMessage("illegal.load.factor", arg0);
    }

    public static String ILLEGAL_LOAD_FACTOR(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableILLEGAL_LOAD_FACTOR(arg0));
    }

    public static Localizable localizableSOME_HEADERS_NOT_SENT(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("some.headers.not.sent", arg0, arg1);
    }

    public static String SOME_HEADERS_NOT_SENT(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableSOME_HEADERS_NOT_SENT(arg0, arg1));
    }

    public static Localizable localizableQUERY_PARAM_NULL() {
        return MESSAGE_FACTORY.getMessage("query.param.null", new Object[0]);
    }

    public static String QUERY_PARAM_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableQUERY_PARAM_NULL());
    }

    public static Localizable localizableILLEGAL_PROVIDER_CLASS_NAME(Object arg0) {
        return MESSAGE_FACTORY.getMessage("illegal.provider.class.name", arg0);
    }

    public static String ILLEGAL_PROVIDER_CLASS_NAME(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableILLEGAL_PROVIDER_CLASS_NAME(arg0));
    }

    public static Localizable localizableSTREAM_PROVIDER_NULL() {
        return MESSAGE_FACTORY.getMessage("stream.provider.null", new Object[0]);
    }

    public static String STREAM_PROVIDER_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableSTREAM_PROVIDER_NULL());
    }

    public static Localizable localizableINJECTION_MANAGER_STRATEGY_NOT_SUPPORTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("injection.manager.strategy.not.supported", arg0);
    }

    public static String INJECTION_MANAGER_STRATEGY_NOT_SUPPORTED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableINJECTION_MANAGER_STRATEGY_NOT_SUPPORTED(arg0));
    }

    public static Localizable localizableMETHOD_PARAMETER_CANNOT_BE_NULL(Object arg0) {
        return MESSAGE_FACTORY.getMessage("method.parameter.cannot.be.null", arg0);
    }

    public static String METHOD_PARAMETER_CANNOT_BE_NULL(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableMETHOD_PARAMETER_CANNOT_BE_NULL(arg0));
    }

    public static Localizable localizableSSL_TMF_PROVIDER_NOT_REGISTERED() {
        return MESSAGE_FACTORY.getMessage("ssl.tmf.provider.not.registered", new Object[0]);
    }

    public static String SSL_TMF_PROVIDER_NOT_REGISTERED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_TMF_PROVIDER_NOT_REGISTERED());
    }

    public static Localizable localizableNO_CONTAINER_AVAILABLE() {
        return MESSAGE_FACTORY.getMessage("no.container.available", new Object[0]);
    }

    public static String NO_CONTAINER_AVAILABLE() {
        return LOCALIZER.localize(LocalizationMessages.localizableNO_CONTAINER_AVAILABLE());
    }

    public static Localizable localizableERROR_ENTITY_PROVIDER_BASICTYPES_CONSTRUCTOR(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.entity.provider.basictypes.constructor", arg0);
    }

    public static String ERROR_ENTITY_PROVIDER_BASICTYPES_CONSTRUCTOR(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_ENTITY_PROVIDER_BASICTYPES_CONSTRUCTOR(arg0));
    }

    public static Localizable localizableERROR_NOTFOUND_MESSAGEBODYWRITER(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("error.notfound.messagebodywriter", arg0, arg1, arg2);
    }

    public static String ERROR_NOTFOUND_MESSAGEBODYWRITER(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_NOTFOUND_MESSAGEBODYWRITER(arg0, arg1, arg2));
    }

    public static Localizable localizableCONTRACT_NOT_ASSIGNABLE(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("contract.not.assignable", arg0, arg1);
    }

    public static String CONTRACT_NOT_ASSIGNABLE(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableCONTRACT_NOT_ASSIGNABLE(arg0, arg1));
    }

    public static Localizable localizableSSL_TMF_ALGORITHM_NOT_SUPPORTED() {
        return MESSAGE_FACTORY.getMessage("ssl.tmf.algorithm.not.supported", new Object[0]);
    }

    public static String SSL_TMF_ALGORITHM_NOT_SUPPORTED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_TMF_ALGORITHM_NOT_SUPPORTED());
    }

    public static Localizable localizableOSGI_REGISTRY_ERROR_OPENING_RESOURCE_STREAM(Object arg0) {
        return MESSAGE_FACTORY.getMessage("osgi.registry.error.opening.resource.stream", arg0);
    }

    public static String OSGI_REGISTRY_ERROR_OPENING_RESOURCE_STREAM(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableOSGI_REGISTRY_ERROR_OPENING_RESOURCE_STREAM(arg0));
    }

    public static Localizable localizableMBR_TRYING_TO_CLOSE_STREAM(Object arg0) {
        return MESSAGE_FACTORY.getMessage("mbr.trying.to.close.stream", arg0);
    }

    public static String MBR_TRYING_TO_CLOSE_STREAM(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableMBR_TRYING_TO_CLOSE_STREAM(arg0));
    }

    public static Localizable localizableIGNORED_EXECUTOR_PROVIDERS(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("ignored.executor.providers", arg0, arg1);
    }

    public static String IGNORED_EXECUTOR_PROVIDERS(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableIGNORED_EXECUTOR_PROVIDERS(arg0, arg1));
    }

    public static Localizable localizableURI_PARSER_NOT_EXECUTED() {
        return MESSAGE_FACTORY.getMessage("uri.parser.not.executed", new Object[0]);
    }

    public static String URI_PARSER_NOT_EXECUTED() {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_PARSER_NOT_EXECUTED());
    }

    public static Localizable localizableMESSAGE_CONTENT_BUFFERING_FAILED() {
        return MESSAGE_FACTORY.getMessage("message.content.buffering.failed", new Object[0]);
    }

    public static String MESSAGE_CONTENT_BUFFERING_FAILED() {
        return LOCALIZER.localize(LocalizationMessages.localizableMESSAGE_CONTENT_BUFFERING_FAILED());
    }

    public static Localizable localizableRESPONSE_CLOSED() {
        return MESSAGE_FACTORY.getMessage("response.closed", new Object[0]);
    }

    public static String RESPONSE_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableRESPONSE_CLOSED());
    }

    public static Localizable localizableSSL_KS_LOAD_ERROR(Object arg0) {
        return MESSAGE_FACTORY.getMessage("ssl.ks.load.error", arg0);
    }

    public static String SSL_KS_LOAD_ERROR(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KS_LOAD_ERROR(arg0));
    }

    public static Localizable localizableCOMMITTING_STREAM_ALREADY_INITIALIZED() {
        return MESSAGE_FACTORY.getMessage("committing.stream.already.initialized", new Object[0]);
    }

    public static String COMMITTING_STREAM_ALREADY_INITIALIZED() {
        return LOCALIZER.localize(LocalizationMessages.localizableCOMMITTING_STREAM_ALREADY_INITIALIZED());
    }

    public static Localizable localizableERROR_ENTITY_PROVIDER_BASICTYPES_CHARACTER_MORECHARS() {
        return MESSAGE_FACTORY.getMessage("error.entity.provider.basictypes.character.morechars", new Object[0]);
    }

    public static String ERROR_ENTITY_PROVIDER_BASICTYPES_CHARACTER_MORECHARS() {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_ENTITY_PROVIDER_BASICTYPES_CHARACTER_MORECHARS());
    }

    public static Localizable localizableERROR_ENTITY_STREAM_CLOSED() {
        return MESSAGE_FACTORY.getMessage("error.entity.stream.closed", new Object[0]);
    }

    public static String ERROR_ENTITY_STREAM_CLOSED() {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_ENTITY_STREAM_CLOSED());
    }

    public static Localizable localizableMESSAGE_CONTENT_INPUT_STREAM_CLOSE_FAILED() {
        return MESSAGE_FACTORY.getMessage("message.content.input.stream.close.failed", new Object[0]);
    }

    public static String MESSAGE_CONTENT_INPUT_STREAM_CLOSE_FAILED() {
        return LOCALIZER.localize(LocalizationMessages.localizableMESSAGE_CONTENT_INPUT_STREAM_CLOSE_FAILED());
    }

    public static Localizable localizableERROR_PROVIDER_CONSTRAINED_TO_WRONG_PACKAGE(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("error.provider.constrainedTo.wrong.package", arg0, arg1);
    }

    public static String ERROR_PROVIDER_CONSTRAINED_TO_WRONG_PACKAGE(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_PROVIDER_CONSTRAINED_TO_WRONG_PACKAGE(arg0, arg1));
    }

    public static Localizable localizableSSL_KS_PROVIDERS_NOT_REGISTERED() {
        return MESSAGE_FACTORY.getMessage("ssl.ks.providers.not.registered", new Object[0]);
    }

    public static String SSL_KS_PROVIDERS_NOT_REGISTERED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KS_PROVIDERS_NOT_REGISTERED());
    }

    public static Localizable localizablePROPERTIES_HELPER_GET_VALUE_NO_TRANSFORM(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("properties.helper.get.value.no.transform", arg0, arg1, arg2);
    }

    public static String PROPERTIES_HELPER_GET_VALUE_NO_TRANSFORM(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizablePROPERTIES_HELPER_GET_VALUE_NO_TRANSFORM(arg0, arg1, arg2));
    }

    public static Localizable localizableERROR_TEMPLATE_PARSER_INVALID_SYNTAX_TERMINATED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.template.parser.invalid.syntax.terminated", arg0);
    }

    public static String ERROR_TEMPLATE_PARSER_INVALID_SYNTAX_TERMINATED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_TEMPLATE_PARSER_INVALID_SYNTAX_TERMINATED(arg0));
    }

    public static Localizable localizableURI_BUILDER_URI_PART_FRAGMENT(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("uri.builder.uri.part.fragment", arg0, arg1);
    }

    public static String URI_BUILDER_URI_PART_FRAGMENT(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_BUILDER_URI_PART_FRAGMENT(arg0, arg1));
    }

    public static Localizable localizableERROR_MBW_ISWRITABLE(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.mbw.iswritable", arg0);
    }

    public static String ERROR_MBW_ISWRITABLE(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_MBW_ISWRITABLE(arg0));
    }

    public static Localizable localizableERROR_READING_ENTITY_MISSING() {
        return MESSAGE_FACTORY.getMessage("error.reading.entity.missing", new Object[0]);
    }

    public static String ERROR_READING_ENTITY_MISSING() {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_READING_ENTITY_MISSING());
    }

    public static Localizable localizableINVALID_HOST() {
        return MESSAGE_FACTORY.getMessage("invalid.host", new Object[0]);
    }

    public static String INVALID_HOST() {
        return LOCALIZER.localize(LocalizationMessages.localizableINVALID_HOST());
    }

    public static Localizable localizableDEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("dependent.class.of.provider.format.error", arg0, arg1, arg2);
    }

    public static String DEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizableDEPENDENT_CLASS_OF_PROVIDER_FORMAT_ERROR(arg0, arg1, arg2));
    }

    public static Localizable localizablePARAM_CREATION_FACTORY_NOT_FOUND(Object arg0) {
        return MESSAGE_FACTORY.getMessage("param.creation.factory.not.found", arg0);
    }

    public static String PARAM_CREATION_FACTORY_NOT_FOUND(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizablePARAM_CREATION_FACTORY_NOT_FOUND(arg0));
    }

    public static Localizable localizableEXCEPTION_MAPPER_SUPPORTED_TYPE_UNKNOWN(Object arg0) {
        return MESSAGE_FACTORY.getMessage("exception.mapper.supported.type.unknown", arg0);
    }

    public static String EXCEPTION_MAPPER_SUPPORTED_TYPE_UNKNOWN(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableEXCEPTION_MAPPER_SUPPORTED_TYPE_UNKNOWN(arg0));
    }

    public static Localizable localizableINJECTION_MANAGER_NOT_PROVIDED() {
        return MESSAGE_FACTORY.getMessage("injection.manager.not.provided", new Object[0]);
    }

    public static String INJECTION_MANAGER_NOT_PROVIDED() {
        return LOCALIZER.localize(LocalizationMessages.localizableINJECTION_MANAGER_NOT_PROVIDED());
    }

    public static Localizable localizableSSL_KMF_NO_PASSWORD_FOR_BYTE_BASED_KS() {
        return MESSAGE_FACTORY.getMessage("ssl.kmf.no.password.for.byte.based.ks", new Object[0]);
    }

    public static String SSL_KMF_NO_PASSWORD_FOR_BYTE_BASED_KS() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KMF_NO_PASSWORD_FOR_BYTE_BASED_KS());
    }

    public static Localizable localizableSLOW_SUBSCRIBER(Object arg0) {
        return MESSAGE_FACTORY.getMessage("slow.subscriber", arg0);
    }

    public static String SLOW_SUBSCRIBER(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSLOW_SUBSCRIBER(arg0));
    }

    public static Localizable localizableTYPE_TO_CLASS_CONVERSION_NOT_SUPPORTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("type.to.class.conversion.not.supported", arg0);
    }

    public static String TYPE_TO_CLASS_CONVERSION_NOT_SUPPORTED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableTYPE_TO_CLASS_CONVERSION_NOT_SUPPORTED(arg0));
    }

    public static Localizable localizableUNKNOWN_SUBSCRIBER() {
        return MESSAGE_FACTORY.getMessage("unknown.subscriber", new Object[0]);
    }

    public static String UNKNOWN_SUBSCRIBER() {
        return LOCALIZER.localize(LocalizationMessages.localizableUNKNOWN_SUBSCRIBER());
    }

    public static Localizable localizableFEATURE_HAS_ALREADY_BEEN_PROCESSED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("feature.has.already.been.processed", arg0);
    }

    public static String FEATURE_HAS_ALREADY_BEEN_PROCESSED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableFEATURE_HAS_ALREADY_BEEN_PROCESSED(arg0));
    }

    public static Localizable localizableSSL_CTX_INIT_FAILED() {
        return MESSAGE_FACTORY.getMessage("ssl.ctx.init.failed", new Object[0]);
    }

    public static String SSL_CTX_INIT_FAILED() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_CTX_INIT_FAILED());
    }

    public static Localizable localizableERROR_TEMPLATE_PARSER_INVALID_SYNTAX(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("error.template.parser.invalid.syntax", arg0, arg1, arg2);
    }

    public static String ERROR_TEMPLATE_PARSER_INVALID_SYNTAX(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_TEMPLATE_PARSER_INVALID_SYNTAX(arg0, arg1, arg2));
    }

    public static Localizable localizableURI_BUILDER_SCHEME_PART_UNEXPECTED_COMPONENT(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("uri.builder.scheme.part.unexpected.component", arg0, arg1);
    }

    public static String URI_BUILDER_SCHEME_PART_UNEXPECTED_COMPONENT(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_BUILDER_SCHEME_PART_UNEXPECTED_COMPONENT(arg0, arg1));
    }

    public static Localizable localizableSSL_TS_IMPL_NOT_FOUND() {
        return MESSAGE_FACTORY.getMessage("ssl.ts.impl.not.found", new Object[0]);
    }

    public static String SSL_TS_IMPL_NOT_FOUND() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_TS_IMPL_NOT_FOUND());
    }

    public static Localizable localizableWARNING_MSG(Object arg0) {
        return MESSAGE_FACTORY.getMessage("warning.msg", arg0);
    }

    public static String WARNING_MSG(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableWARNING_MSG(arg0));
    }

    public static Localizable localizableWARNING_PROVIDER_CONSTRAINED_TO_WRONG_PACKAGE(Object arg0, Object arg1, Object arg2, Object arg3) {
        return MESSAGE_FACTORY.getMessage("warning.provider.constrainedTo.wrong.package", arg0, arg1, arg2, arg3);
    }

    public static String WARNING_PROVIDER_CONSTRAINED_TO_WRONG_PACKAGE(Object arg0, Object arg1, Object arg2, Object arg3) {
        return LOCALIZER.localize(LocalizationMessages.localizableWARNING_PROVIDER_CONSTRAINED_TO_WRONG_PACKAGE(arg0, arg1, arg2, arg3));
    }

    public static Localizable localizableHINTS_DETECTED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("hints.detected", arg0);
    }

    public static String HINTS_DETECTED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableHINTS_DETECTED(arg0));
    }

    public static Localizable localizableHTTP_HEADER_UNBALANCED_COMMENTS() {
        return MESSAGE_FACTORY.getMessage("http.header.unbalanced.comments", new Object[0]);
    }

    public static String HTTP_HEADER_UNBALANCED_COMMENTS() {
        return LOCALIZER.localize(LocalizationMessages.localizableHTTP_HEADER_UNBALANCED_COMMENTS());
    }

    public static Localizable localizableURI_BUILDER_METHODNAME_NOT_SPECIFIED(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("uri.builder.methodname.not.specified", arg0, arg1);
    }

    public static String URI_BUILDER_METHODNAME_NOT_SPECIFIED(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_BUILDER_METHODNAME_NOT_SPECIFIED(arg0, arg1));
    }

    public static Localizable localizableSSL_KMF_UNRECOVERABLE_KEY() {
        return MESSAGE_FACTORY.getMessage("ssl.kmf.unrecoverable.key", new Object[0]);
    }

    public static String SSL_KMF_UNRECOVERABLE_KEY() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KMF_UNRECOVERABLE_KEY());
    }

    public static Localizable localizableINJECTION_ERROR_SUITABLE_CONSTRUCTOR_NOT_FOUND(Object arg0) {
        return MESSAGE_FACTORY.getMessage("injection.error.suitable.constructor.not.found", arg0);
    }

    public static String INJECTION_ERROR_SUITABLE_CONSTRUCTOR_NOT_FOUND(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableINJECTION_ERROR_SUITABLE_CONSTRUCTOR_NOT_FOUND(arg0));
    }

    public static Localizable localizableAUTODISCOVERABLE_CONFIGURATION_FAILED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("autodiscoverable.configuration.failed", arg0);
    }

    public static String AUTODISCOVERABLE_CONFIGURATION_FAILED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableAUTODISCOVERABLE_CONFIGURATION_FAILED(arg0));
    }

    public static Localizable localizableURI_COMPONENT_INVALID_CHARACTER(Object arg0, Object arg1, Object arg2, Object arg3) {
        return MESSAGE_FACTORY.getMessage("uri.component.invalid.character", arg0, arg1, arg2, arg3);
    }

    public static String URI_COMPONENT_INVALID_CHARACTER(Object arg0, Object arg1, Object arg2, Object arg3) {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_COMPONENT_INVALID_CHARACTER(arg0, arg1, arg2, arg3));
    }

    public static Localizable localizableSSL_KS_FILE_NOT_FOUND(Object arg0) {
        return MESSAGE_FACTORY.getMessage("ssl.ks.file.not.found", arg0);
    }

    public static String SSL_KS_FILE_NOT_FOUND(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_KS_FILE_NOT_FOUND(arg0));
    }

    public static Localizable localizableEXCEPTION_CAUGHT_WHILE_LOADING_SPI_PROVIDERS() {
        return MESSAGE_FACTORY.getMessage("exception.caught.while.loading.spi.providers", new Object[0]);
    }

    public static String EXCEPTION_CAUGHT_WHILE_LOADING_SPI_PROVIDERS() {
        return LOCALIZER.localize(LocalizationMessages.localizableEXCEPTION_CAUGHT_WHILE_LOADING_SPI_PROVIDERS());
    }

    public static Localizable localizableERROR_MSG(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.msg", arg0);
    }

    public static String ERROR_MSG(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_MSG(arg0));
    }

    public static Localizable localizableURI_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("uri.is.null", new Object[0]);
    }

    public static String URI_IS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableURI_IS_NULL());
    }

    public static Localizable localizableOSGI_REGISTRY_ERROR_PROCESSING_RESOURCE_STREAM(Object arg0) {
        return MESSAGE_FACTORY.getMessage("osgi.registry.error.processing.resource.stream", arg0);
    }

    public static String OSGI_REGISTRY_ERROR_PROCESSING_RESOURCE_STREAM(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableOSGI_REGISTRY_ERROR_PROCESSING_RESOURCE_STREAM(arg0));
    }

    public static Localizable localizablePROVIDER_CLASS_COULD_NOT_BE_LOADED(Object arg0, Object arg1, Object arg2) {
        return MESSAGE_FACTORY.getMessage("provider.class.could.not.be.loaded", arg0, arg1, arg2);
    }

    public static String PROVIDER_CLASS_COULD_NOT_BE_LOADED(Object arg0, Object arg1, Object arg2) {
        return LOCALIZER.localize(LocalizationMessages.localizablePROVIDER_CLASS_COULD_NOT_BE_LOADED(arg0, arg1, arg2));
    }

    public static Localizable localizableCOMPONENT_TYPE_ALREADY_REGISTERED(Object arg0) {
        return MESSAGE_FACTORY.getMessage("component.type.already.registered", arg0);
    }

    public static String COMPONENT_TYPE_ALREADY_REGISTERED(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableCOMPONENT_TYPE_ALREADY_REGISTERED(arg0));
    }

    public static Localizable localizableSYSTEM_PROPERTIES_WARNING() {
        return MESSAGE_FACTORY.getMessage("system.properties.warning", new Object[0]);
    }

    public static String SYSTEM_PROPERTIES_WARNING() {
        return LOCALIZER.localize(LocalizationMessages.localizableSYSTEM_PROPERTIES_WARNING());
    }

    public static Localizable localizableERROR_ENTITY_PROVIDER_BASICTYPES_UNKWNOWN(Object arg0) {
        return MESSAGE_FACTORY.getMessage("error.entity.provider.basictypes.unkwnown", arg0);
    }

    public static String ERROR_ENTITY_PROVIDER_BASICTYPES_UNKWNOWN(Object arg0) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_ENTITY_PROVIDER_BASICTYPES_UNKWNOWN(arg0));
    }

    public static Localizable localizableSTRING_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("string.is.null", new Object[0]);
    }

    public static String STRING_IS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableSTRING_IS_NULL());
    }

    public static Localizable localizableDATE_IS_NULL() {
        return MESSAGE_FACTORY.getMessage("date.is.null", new Object[0]);
    }

    public static String DATE_IS_NULL() {
        return LOCALIZER.localize(LocalizationMessages.localizableDATE_IS_NULL());
    }

    public static Localizable localizableERROR_RESOLVING_GENERIC_TYPE_VALUE(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("error.resolving.generic.type.value", arg0, arg1);
    }

    public static String ERROR_RESOLVING_GENERIC_TYPE_VALUE(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_RESOLVING_GENERIC_TYPE_VALUE(arg0, arg1));
    }

    public static Localizable localizableERROR_TEMPLATE_PARSER_NAME_MORE_THAN_ONCE(Object arg0, Object arg1) {
        return MESSAGE_FACTORY.getMessage("error.template.parser.name.more.than.once", arg0, arg1);
    }

    public static String ERROR_TEMPLATE_PARSER_NAME_MORE_THAN_ONCE(Object arg0, Object arg1) {
        return LOCALIZER.localize(LocalizationMessages.localizableERROR_TEMPLATE_PARSER_NAME_MORE_THAN_ONCE(arg0, arg1));
    }

    public static Localizable localizableSSL_TS_INTEGRITY_ALGORITHM_NOT_FOUND() {
        return MESSAGE_FACTORY.getMessage("ssl.ts.integrity.algorithm.not.found", new Object[0]);
    }

    public static String SSL_TS_INTEGRITY_ALGORITHM_NOT_FOUND() {
        return LOCALIZER.localize(LocalizationMessages.localizableSSL_TS_INTEGRITY_ALGORITHM_NOT_FOUND());
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

