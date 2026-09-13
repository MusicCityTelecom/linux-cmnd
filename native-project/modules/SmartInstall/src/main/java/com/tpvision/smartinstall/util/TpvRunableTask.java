package com.tpvision.smartinstall.util;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.CloseableThreadContext.Instance;

public abstract class TpvRunableTask implements Runnable {
   private Map<String, String> threadContextMap = ThreadContext.getImmutableContext();
   private List<String> threadContextMessages = ThreadContext.getImmutableStack().asList();

   @Override
   public final void run() {
      try (Instance ctc = CloseableThreadContext.putAll(this.threadContextMap).pushAll(this.threadContextMessages)) {
         this.execute();
      }
   }

   public abstract void execute();
}
