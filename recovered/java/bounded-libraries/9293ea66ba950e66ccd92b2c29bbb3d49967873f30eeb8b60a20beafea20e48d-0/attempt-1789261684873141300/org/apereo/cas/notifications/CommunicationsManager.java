/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Principal
 */
package org.apereo.cas.notifications;

import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.notifications.mail.EmailCommunicationResult;
import org.apereo.cas.notifications.mail.EmailMessageRequest;
import org.apereo.cas.notifications.sms.SmsRequest;

public interface CommunicationsManager {
    public static final String BEAN_NAME = "communicationsManager";

    public boolean isMailSenderDefined();

    public boolean isSmsSenderDefined();

    public boolean isNotificationSenderDefined();

    public boolean notify(Principal var1, String var2, String var3);

    public EmailCommunicationResult email(EmailMessageRequest var1);

    public boolean sms(SmsRequest var1);

    public boolean validate();
}

