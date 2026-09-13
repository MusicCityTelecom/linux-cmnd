/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.notifications.sms;

public interface SmsSender {
    public static final String BEAN_NAME = "smsSender";

    default public boolean send(String from, String to, String message) {
        return false;
    }

    default public boolean canSend() {
        return true;
    }

    public static SmsSender noOp() {
        return new SmsSender(){

            @Override
            public boolean canSend() {
                return false;
            }
        };
    }
}

