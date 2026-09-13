/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.util.concurrent.ExecutorService;
import javax.ws.rs.client.RxInvoker;
import javax.ws.rs.client.SyncInvoker;

public interface RxInvokerProvider<T extends RxInvoker> {
    public boolean isProviderFor(Class<?> var1);

    public T getRxInvoker(SyncInvoker var1, ExecutorService var2);
}

