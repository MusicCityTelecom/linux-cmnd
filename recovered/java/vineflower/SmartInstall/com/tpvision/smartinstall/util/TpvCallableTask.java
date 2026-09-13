package com.tpvision.smartinstall.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.CloseableThreadContext.Instance;

public abstract class TpvCallableTask<V> implements Callable<V> {
   private Map<String, String> threadContextMap = ThreadContext.getImmutableContext();
   private List<String> threadContextMessages = ThreadContext.getImmutableStack().asList();

   @Override
   public final V call() {
      try (Instance ctc = CloseableThreadContext.putAll(this.threadContextMap).pushAll(this.threadContextMessages)) {
         return this.execute();
      }
   }

   public abstract V execute();
}
