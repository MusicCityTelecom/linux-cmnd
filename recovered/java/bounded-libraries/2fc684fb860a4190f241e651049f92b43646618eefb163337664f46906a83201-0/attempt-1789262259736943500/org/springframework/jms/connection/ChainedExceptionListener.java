/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ExceptionListener
 *  javax.jms.JMSException
 *  org.springframework.util.Assert
 */
package org.springframework.jms.connection;

import java.util.ArrayList;
import java.util.List;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import org.springframework.util.Assert;

public class ChainedExceptionListener
implements ExceptionListener {
    private final List<ExceptionListener> delegates = new ArrayList<ExceptionListener>(2);

    public final void addDelegate(ExceptionListener listener) {
        Assert.notNull((Object)listener, (String)"ExceptionListener must not be null");
        this.delegates.add(listener);
    }

    public final ExceptionListener[] getDelegates() {
        return this.delegates.toArray(new ExceptionListener[0]);
    }

    public void onException(JMSException ex) {
        for (ExceptionListener listener : this.delegates) {
            listener.onException(ex);
        }
    }
}

