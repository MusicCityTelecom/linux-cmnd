/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.simp.stomp;

import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

public interface StompSession {
    public String getSessionId();

    public boolean isConnected();

    public void setAutoReceipt(boolean var1);

    public Receiptable send(String var1, Object var2);

    public Receiptable send(StompHeaders var1, Object var2);

    public Subscription subscribe(String var1, StompFrameHandler var2);

    public Subscription subscribe(StompHeaders var1, StompFrameHandler var2);

    public Receiptable acknowledge(String var1, boolean var2);

    public Receiptable acknowledge(StompHeaders var1, boolean var2);

    public void disconnect();

    public void disconnect(StompHeaders var1);

    public static interface Subscription
    extends Receiptable {
        @Nullable
        public String getSubscriptionId();

        public StompHeaders getSubscriptionHeaders();

        public void unsubscribe();

        public void unsubscribe(@Nullable StompHeaders var1);
    }

    public static interface Receiptable {
        @Nullable
        public String getReceiptId();

        public void addReceiptTask(Runnable var1);

        public void addReceiptLostTask(Runnable var1);
    }
}

