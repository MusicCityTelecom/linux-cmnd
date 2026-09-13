/*
 * Decompiled with CFR 0.152.
 */
package reactor.util;

public interface Logger {
    public String getName();

    public boolean isTraceEnabled();

    public void trace(String var1);

    public void trace(String var1, Object ... var2);

    public void trace(String var1, Throwable var2);

    public boolean isDebugEnabled();

    public void debug(String var1);

    public void debug(String var1, Object ... var2);

    public void debug(String var1, Throwable var2);

    public boolean isInfoEnabled();

    public void info(String var1);

    public void info(String var1, Object ... var2);

    public void info(String var1, Throwable var2);

    default public void infoOrDebug(ChoiceOfMessageSupplier messageSupplier) {
        if (this.isDebugEnabled()) {
            this.debug(messageSupplier.get(true));
        } else if (this.isInfoEnabled()) {
            this.info(messageSupplier.get(false));
        }
    }

    default public void infoOrDebug(ChoiceOfMessageSupplier messageSupplier, Throwable cause) {
        if (this.isDebugEnabled()) {
            this.debug(messageSupplier.get(true), cause);
        } else if (this.isInfoEnabled()) {
            this.info(messageSupplier.get(false), cause);
        }
    }

    public boolean isWarnEnabled();

    public void warn(String var1);

    public void warn(String var1, Object ... var2);

    public void warn(String var1, Throwable var2);

    default public void warnOrDebug(ChoiceOfMessageSupplier messageSupplier) {
        if (this.isDebugEnabled()) {
            this.debug(messageSupplier.get(true));
        } else if (this.isWarnEnabled()) {
            this.warn(messageSupplier.get(false));
        }
    }

    default public void warnOrDebug(ChoiceOfMessageSupplier messageSupplier, Throwable cause) {
        if (this.isDebugEnabled()) {
            this.debug(messageSupplier.get(true), cause);
        } else if (this.isWarnEnabled()) {
            this.warn(messageSupplier.get(false), cause);
        }
    }

    public boolean isErrorEnabled();

    public void error(String var1);

    public void error(String var1, Object ... var2);

    public void error(String var1, Throwable var2);

    default public void errorOrDebug(ChoiceOfMessageSupplier messageSupplier) {
        if (this.isDebugEnabled()) {
            this.debug(messageSupplier.get(true));
        } else if (this.isErrorEnabled()) {
            this.error(messageSupplier.get(false));
        }
    }

    default public void errorOrDebug(ChoiceOfMessageSupplier messageSupplier, Throwable cause) {
        if (this.isDebugEnabled()) {
            this.debug(messageSupplier.get(true), cause);
        } else if (this.isErrorEnabled()) {
            this.error(messageSupplier.get(false), cause);
        }
    }

    @FunctionalInterface
    public static interface ChoiceOfMessageSupplier {
        public String get(boolean var1);
    }
}

