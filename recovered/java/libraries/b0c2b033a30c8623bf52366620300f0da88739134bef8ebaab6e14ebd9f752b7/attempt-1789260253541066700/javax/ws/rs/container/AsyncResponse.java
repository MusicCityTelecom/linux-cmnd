/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.container;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.container.TimeoutHandler;

public interface AsyncResponse {
    public static final long NO_TIMEOUT = 0L;

    public boolean resume(Object var1);

    public boolean resume(Throwable var1);

    public boolean cancel();

    public boolean cancel(int var1);

    public boolean cancel(Date var1);

    public boolean isSuspended();

    public boolean isCancelled();

    public boolean isDone();

    public boolean setTimeout(long var1, TimeUnit var3);

    public void setTimeoutHandler(TimeoutHandler var1);

    public Collection<Class<?>> register(Class<?> var1);

    public Map<Class<?>, Collection<Class<?>>> register(Class<?> var1, Class<?> ... var2);

    public Collection<Class<?>> register(Object var1);

    public Map<Class<?>, Collection<Class<?>>> register(Object var1, Object ... var2);
}

