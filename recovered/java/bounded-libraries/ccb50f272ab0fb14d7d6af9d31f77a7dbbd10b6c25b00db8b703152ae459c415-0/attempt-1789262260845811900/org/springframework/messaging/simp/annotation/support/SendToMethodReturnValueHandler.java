/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.MethodParameter
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.PropertyPlaceholderHelper
 *  org.springframework.util.PropertyPlaceholderHelper$PlaceholderResolver
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.simp.annotation.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.support.DestinationVariableMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.support.MissingSessionUserException;
import org.springframework.messaging.simp.user.DestinationUserNameProvider;
import org.springframework.messaging.support.MessageHeaderInitializer;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringUtils;

public class SendToMethodReturnValueHandler
implements HandlerMethodReturnValueHandler {
    private final SimpMessageSendingOperations messagingTemplate;
    private final boolean annotationRequired;
    private String defaultDestinationPrefix = "/topic";
    private String defaultUserDestinationPrefix = "/queue";
    private PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}", null, false);
    @Nullable
    private MessageHeaderInitializer headerInitializer;

    public SendToMethodReturnValueHandler(SimpMessageSendingOperations messagingTemplate, boolean annotationRequired) {
        Assert.notNull((Object)messagingTemplate, (String)"'messagingTemplate' must not be null");
        this.messagingTemplate = messagingTemplate;
        this.annotationRequired = annotationRequired;
    }

    public void setDefaultDestinationPrefix(String defaultDestinationPrefix) {
        this.defaultDestinationPrefix = defaultDestinationPrefix;
    }

    public String getDefaultDestinationPrefix() {
        return this.defaultDestinationPrefix;
    }

    public void setDefaultUserDestinationPrefix(String prefix) {
        this.defaultUserDestinationPrefix = prefix;
    }

    public String getDefaultUserDestinationPrefix() {
        return this.defaultUserDestinationPrefix;
    }

    public void setHeaderInitializer(@Nullable MessageHeaderInitializer headerInitializer) {
        this.headerInitializer = headerInitializer;
    }

    @Nullable
    public MessageHeaderInitializer getHeaderInitializer() {
        return this.headerInitializer;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(SendTo.class) || AnnotatedElementUtils.hasAnnotation((AnnotatedElement)returnType.getDeclaringClass(), SendTo.class) || returnType.hasMethodAnnotation(SendToUser.class) || AnnotatedElementUtils.hasAnnotation((AnnotatedElement)returnType.getDeclaringClass(), SendToUser.class) || !this.annotationRequired;
    }

    @Override
    public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, Message<?> message) throws Exception {
        SendTo sendTo;
        if (returnValue == null) {
            return;
        }
        MessageHeaders headers = message.getHeaders();
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        DestinationHelper destinationHelper = this.getDestinationHelper(headers, returnType);
        SendToUser sendToUser = destinationHelper.getSendToUser();
        if (sendToUser != null) {
            String[] destinations;
            boolean broadcast = sendToUser.broadcast();
            String user = this.getUserName(message, headers);
            if (user == null) {
                if (sessionId == null) {
                    throw new MissingSessionUserException(message);
                }
                user = sessionId;
                broadcast = false;
            }
            String[] stringArray = destinations = this.getTargetDestinations(sendToUser, message, this.defaultUserDestinationPrefix);
            int n = stringArray.length;
            for (int j = 0; j < n; ++j) {
                String destination = stringArray[j];
                destination = destinationHelper.expandTemplateVars(destination);
                if (broadcast) {
                    this.messagingTemplate.convertAndSendToUser(user, destination, returnValue, this.createHeaders(null, returnType));
                    continue;
                }
                this.messagingTemplate.convertAndSendToUser(user, destination, returnValue, this.createHeaders(sessionId, returnType));
            }
        }
        if ((sendTo = destinationHelper.getSendTo()) != null || sendToUser == null) {
            String[] destinations;
            for (String destination : destinations = this.getTargetDestinations(sendTo, message, this.defaultDestinationPrefix)) {
                destination = destinationHelper.expandTemplateVars(destination);
                this.messagingTemplate.convertAndSend(destination, returnValue, this.createHeaders(sessionId, returnType));
            }
        }
    }

    private DestinationHelper getDestinationHelper(MessageHeaders headers, MethodParameter returnType) {
        SendToUser m1 = (SendToUser)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)returnType.getExecutable(), SendToUser.class);
        SendTo m2 = (SendTo)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)returnType.getExecutable(), SendTo.class);
        if (m1 != null && !ObjectUtils.isEmpty((Object[])m1.value()) || m2 != null && !ObjectUtils.isEmpty((Object[])m2.value())) {
            return new DestinationHelper(headers, m1, m2);
        }
        SendToUser c1 = (SendToUser)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)returnType.getDeclaringClass(), SendToUser.class);
        SendTo c2 = (SendTo)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)returnType.getDeclaringClass(), SendTo.class);
        if (c1 != null && !ObjectUtils.isEmpty((Object[])c1.value()) || c2 != null && !ObjectUtils.isEmpty((Object[])c2.value())) {
            return new DestinationHelper(headers, c1, c2);
        }
        return m1 != null || m2 != null ? new DestinationHelper(headers, m1, m2) : new DestinationHelper(headers, c1, c2);
    }

    @Nullable
    protected String getUserName(Message<?> message, MessageHeaders headers) {
        Principal principal = SimpMessageHeaderAccessor.getUser(headers);
        if (principal != null) {
            return principal instanceof DestinationUserNameProvider ? ((DestinationUserNameProvider)((Object)principal)).getDestinationUserName() : principal.getName();
        }
        return null;
    }

    protected String[] getTargetDestinations(@Nullable Annotation annotation, Message<?> message, String defaultPrefix) {
        String[] stringArray;
        Object[] value;
        if (annotation != null && !ObjectUtils.isEmpty((Object[])(value = (String[])AnnotationUtils.getValue((Annotation)annotation)))) {
            return value;
        }
        String name = "lookupDestination";
        String destination = (String)message.getHeaders().get(name);
        if (!StringUtils.hasText((String)destination)) {
            throw new IllegalStateException("No lookup destination header in " + message);
        }
        if (destination.startsWith("/")) {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = defaultPrefix + destination;
        } else {
            String[] stringArray3 = new String[1];
            stringArray = stringArray3;
            stringArray3[0] = defaultPrefix + '/' + destination;
        }
        return stringArray;
    }

    private MessageHeaders createHeaders(@Nullable String sessionId, MethodParameter returnType) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        if (this.getHeaderInitializer() != null) {
            this.getHeaderInitializer().initHeaders(headerAccessor);
        }
        if (sessionId != null) {
            headerAccessor.setSessionId(sessionId);
        }
        headerAccessor.setHeader("conversionHint", returnType);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }

    public String toString() {
        return "SendToMethodReturnValueHandler [annotationRequired=" + this.annotationRequired + "]";
    }

    private class DestinationHelper {
        private final PropertyPlaceholderHelper.PlaceholderResolver placeholderResolver;
        @Nullable
        private final SendTo sendTo;
        @Nullable
        private final SendToUser sendToUser;

        public DestinationHelper(@Nullable MessageHeaders headers, @Nullable SendToUser sendToUser, SendTo sendTo) {
            Map<String, String> variables = this.getTemplateVariables(headers);
            this.placeholderResolver = variables::get;
            this.sendTo = sendTo;
            this.sendToUser = sendToUser;
        }

        private Map<String, String> getTemplateVariables(MessageHeaders headers) {
            String name = DestinationVariableMethodArgumentResolver.DESTINATION_TEMPLATE_VARIABLES_HEADER;
            return headers.getOrDefault(name, Collections.emptyMap());
        }

        @Nullable
        public SendTo getSendTo() {
            return this.sendTo;
        }

        @Nullable
        public SendToUser getSendToUser() {
            return this.sendToUser;
        }

        public String expandTemplateVars(String destination) {
            return SendToMethodReturnValueHandler.this.placeholderHelper.replacePlaceholders(destination, this.placeholderResolver);
        }
    }
}

