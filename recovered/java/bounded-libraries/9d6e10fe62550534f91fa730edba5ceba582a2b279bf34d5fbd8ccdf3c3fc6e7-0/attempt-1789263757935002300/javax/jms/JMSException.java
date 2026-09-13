/*
 * Decompiled with CFR 0.152.
 */
package javax.jms;

public class JMSException
extends Exception {
    private String errorCode;
    private Exception linkedException;

    public JMSException(String reason, String errorCode) {
        super(reason);
        this.errorCode = errorCode;
        this.linkedException = null;
    }

    public JMSException(String reason) {
        super(reason);
        this.errorCode = null;
        this.linkedException = null;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public Exception getLinkedException() {
        return this.linkedException;
    }

    public synchronized void setLinkedException(Exception ex) {
        this.linkedException = ex;
    }
}

