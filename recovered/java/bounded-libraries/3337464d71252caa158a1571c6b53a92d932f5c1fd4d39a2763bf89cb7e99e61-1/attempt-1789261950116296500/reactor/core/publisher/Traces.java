/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  sun.misc.JavaLangAccess
 *  sun.misc.SharedSecrets
 */
package reactor.core.publisher;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;

final class Traces {
    static final boolean full = Boolean.parseBoolean(System.getProperty("reactor.trace.assembly.fullstacktrace", "false"));
    static final String CALL_SITE_GLUE = " \u21e2 ";
    static Supplier<Supplier<String>> callSiteSupplierFactory;

    Traces() {
    }

    static boolean shouldSanitize(String stackTraceRow) {
        return stackTraceRow.startsWith("java.util.function") || stackTraceRow.startsWith("reactor.core.publisher.Mono.onAssembly") || stackTraceRow.equals("reactor.core.publisher.Mono.onAssembly") || stackTraceRow.equals("reactor.core.publisher.Flux.onAssembly") || stackTraceRow.equals("reactor.core.publisher.ParallelFlux.onAssembly") || stackTraceRow.startsWith("reactor.core.publisher.SignalLogger") || stackTraceRow.startsWith("reactor.core.publisher.FluxOnAssembly") || stackTraceRow.startsWith("reactor.core.publisher.MonoOnAssembly.") || stackTraceRow.startsWith("reactor.core.publisher.MonoCallableOnAssembly.") || stackTraceRow.startsWith("reactor.core.publisher.FluxCallableOnAssembly.") || stackTraceRow.startsWith("reactor.core.publisher.Hooks") || stackTraceRow.startsWith("sun.reflect") || stackTraceRow.startsWith("java.util.concurrent.ThreadPoolExecutor") || stackTraceRow.startsWith("java.lang.reflect");
    }

    static String extractOperatorAssemblyInformation(String source) {
        CharSequence[] parts = Traces.extractOperatorAssemblyInformationParts(source);
        switch (parts.length) {
            case 0: {
                return "[no operator assembly information]";
            }
        }
        return String.join((CharSequence)CALL_SITE_GLUE, parts);
    }

    static boolean isUserCode(String line) {
        return !line.startsWith("reactor.core.publisher") || line.contains("Test");
    }

    static String[] extractOperatorAssemblyInformationParts(String source) {
        String userCodeLine;
        String apiLine;
        int i;
        String[] uncleanTraces = source.split("\n");
        List traces = Stream.of(uncleanTraces).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        if (traces.isEmpty()) {
            return new String[0];
        }
        for (i = 0; i < traces.size() && !Traces.isUserCode((String)traces.get(i)); ++i) {
        }
        if (i == 0) {
            apiLine = "";
            userCodeLine = (String)traces.get(0);
        } else if (i == traces.size()) {
            apiLine = "";
            userCodeLine = ((String)traces.get(i - 1)).replaceFirst("reactor.core.publisher.", "");
        } else {
            apiLine = (String)traces.get(i - 1);
            userCodeLine = (String)traces.get(i);
        }
        if (apiLine.isEmpty()) {
            return new String[]{userCodeLine};
        }
        int linePartIndex = apiLine.indexOf(40);
        if (linePartIndex > 0) {
            apiLine = apiLine.substring(0, linePartIndex);
        }
        apiLine = apiLine.replaceFirst("reactor.core.publisher.", "");
        return new String[]{apiLine, "at " + userCodeLine};
    }

    static {
        String[] strategyClasses = new String[]{Traces.class.getName() + "$StackWalkerCallSiteSupplierFactory", Traces.class.getName() + "$SharedSecretsCallSiteSupplierFactory", Traces.class.getName() + "$ExceptionCallSiteSupplierFactory"};
        callSiteSupplierFactory = (Supplier)Stream.of(strategyClasses).flatMap(className -> {
            try {
                Class<?> clazz = Class.forName(className);
                Supplier function = (Supplier)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                return Stream.of(function);
            }
            catch (LinkageError e) {}
            finally {
                return Stream.empty();
            }
        }).findFirst().orElseThrow(() -> new IllegalStateException("Valid strategy not found"));
    }

    static class ExceptionCallSiteSupplierFactory
    implements Supplier<Supplier<String>> {
        ExceptionCallSiteSupplierFactory() {
        }

        @Override
        public Supplier<String> get() {
            return new TracingException();
        }

        static class TracingException
        extends Throwable
        implements Supplier<String> {
            TracingException() {
            }

            @Override
            public String get() {
                StackTraceElement previousElement = null;
                StackTraceElement[] stackTrace = this.getStackTrace();
                for (int i = 2; i < stackTrace.length; ++i) {
                    StackTraceElement e = stackTrace[i];
                    String className = e.getClassName();
                    if (Traces.isUserCode(className)) {
                        StringBuilder sb = new StringBuilder();
                        if (previousElement != null) {
                            sb.append("\t").append(previousElement.toString()).append("\n");
                        }
                        sb.append("\t").append(e.toString()).append("\n");
                        return sb.toString();
                    }
                    if (!full && e.getLineNumber() <= 1) continue;
                    String classAndMethod = className + "." + e.getMethodName();
                    if (!full && Traces.shouldSanitize(classAndMethod)) continue;
                    previousElement = e;
                }
                return "";
            }
        }
    }

    static class SharedSecretsCallSiteSupplierFactory
    implements Supplier<Supplier<String>> {
        SharedSecretsCallSiteSupplierFactory() {
        }

        @Override
        public Supplier<String> get() {
            return new TracingException();
        }

        static class TracingException
        extends Throwable
        implements Supplier<String> {
            static final JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();

            TracingException() {
            }

            @Override
            public String get() {
                int stackTraceDepth = javaLangAccess.getStackTraceDepth((Throwable)this);
                StackTraceElement previousElement = null;
                for (int i = 2; i < stackTraceDepth; ++i) {
                    StackTraceElement e = javaLangAccess.getStackTraceElement((Throwable)this, i);
                    String className = e.getClassName();
                    if (Traces.isUserCode(className)) {
                        StringBuilder sb = new StringBuilder();
                        if (previousElement != null) {
                            sb.append("\t").append(previousElement.toString()).append("\n");
                        }
                        sb.append("\t").append(e.toString()).append("\n");
                        return sb.toString();
                    }
                    if (!full && e.getLineNumber() <= 1) continue;
                    String classAndMethod = className + "." + e.getMethodName();
                    if (!full && Traces.shouldSanitize(classAndMethod)) continue;
                    previousElement = e;
                }
                return "";
            }
        }
    }

    static final class StackWalkerCallSiteSupplierFactory
    implements Supplier<Supplier<String>> {
        StackWalkerCallSiteSupplierFactory() {
        }

        @Override
        public Supplier<String> get() {
            StackWalker.StackFrame[] stack = StackWalker.getInstance().walk(s -> {
                StackWalker.StackFrame[] result = new StackWalker.StackFrame[10];
                Iterator iterator = s.iterator();
                iterator.next();
                int i = 0;
                while (iterator.hasNext()) {
                    StackWalker.StackFrame frame = (StackWalker.StackFrame)iterator.next();
                    if (i >= result.length) {
                        return new StackWalker.StackFrame[0];
                    }
                    result[i++] = frame;
                    if (!Traces.isUserCode(frame.getClassName())) continue;
                    break;
                }
                StackWalker.StackFrame[] copy = new StackWalker.StackFrame[i];
                System.arraycopy(result, 0, copy, 0, i);
                return copy;
            });
            if (stack.length == 0) {
                return () -> "";
            }
            if (stack.length == 1) {
                return () -> "\t" + stack[0].toString() + "\n";
            }
            return () -> {
                StringBuilder sb = new StringBuilder();
                for (int j = stack.length - 2; j > 0; --j) {
                    String previousRow;
                    StackWalker.StackFrame previous = stack[j];
                    if (!full && (previous.isNativeMethod() || Traces.shouldSanitize(previousRow = previous.getClassName() + "." + previous.getMethodName()))) continue;
                    sb.append("\t").append(previous.toString()).append("\n");
                    break;
                }
                sb.append("\t").append(stack[stack.length - 1].toString()).append("\n");
                return sb.toString();
            };
        }

        static {
            StackWalker.getInstance();
        }
    }
}

