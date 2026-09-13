/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.AssemblyOp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Traces;
import reactor.util.annotation.Nullable;

final class FluxOnAssembly<T>
extends InternalFluxOperator<T, T>
implements Fuseable,
AssemblyOp {
    final AssemblySnapshot snapshotStack;

    FluxOnAssembly(Flux<? extends T> source, AssemblySnapshot snapshotStack) {
        super(source);
        this.snapshotStack = snapshotStack;
    }

    @Override
    public String stepName() {
        return this.snapshotStack.operatorAssemblyInformation();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.ACTUAL_METADATA) {
            return !this.snapshotStack.isCheckpoint;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    @Override
    public String toString() {
        return this.snapshotStack.operatorAssemblyInformation();
    }

    static void fillStacktraceHeader(StringBuilder sb, Class<?> sourceClass, @Nullable String description) {
        sb.append("\nAssembly trace from producer [").append(sourceClass.getName()).append("]");
        if (description != null) {
            sb.append(", described as [").append(description).append("]");
        }
        sb.append(" :\n");
    }

    static <T> CoreSubscriber<? super T> wrapSubscriber(CoreSubscriber<? super T> actual, Flux<? extends T> source, Publisher<?> current, @Nullable AssemblySnapshot snapshotStack) {
        if (snapshotStack != null) {
            if (actual instanceof Fuseable.ConditionalSubscriber) {
                Fuseable.ConditionalSubscriber cs = (Fuseable.ConditionalSubscriber)actual;
                return new OnAssemblyConditionalSubscriber(cs, snapshotStack, (Publisher<?>)source, current);
            }
            return new OnAssemblySubscriber<T>(actual, snapshotStack, source, current);
        }
        return actual;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return FluxOnAssembly.wrapSubscriber(actual, this.source, this, this.snapshotStack);
    }

    static final class OnAssemblyConditionalSubscriber<T>
    extends OnAssemblySubscriber<T>
    implements Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> actualCS;

        OnAssemblyConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, AssemblySnapshot stacktrace, Publisher<?> parent, Publisher<?> current) {
            super(actual, stacktrace, parent, current);
            this.actualCS = actual;
        }

        @Override
        public boolean tryOnNext(T t) {
            return this.actualCS.tryOnNext(t);
        }
    }

    static class OnAssemblySubscriber<T>
    implements InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final AssemblySnapshot snapshotStack;
        final Publisher<?> parent;
        final Publisher<?> current;
        final CoreSubscriber<? super T> actual;
        Fuseable.QueueSubscription<T> qs;
        Subscription s;
        int fusionMode;

        OnAssemblySubscriber(CoreSubscriber<? super T> actual, AssemblySnapshot snapshotStack, Publisher<?> parent, Publisher<?> current) {
            this.actual = actual;
            this.snapshotStack = snapshotStack;
            this.parent = parent;
            this.current = current;
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL_METADATA) {
                return !this.snapshotStack.isCheckpoint;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        public String toString() {
            return this.snapshotStack.operatorAssemblyInformation();
        }

        @Override
        public String stepName() {
            return this.toString();
        }

        public final void onNext(T t) {
            this.actual.onNext(t);
        }

        public final void onError(Throwable t) {
            this.actual.onError(this.fail(t));
        }

        public final void onComplete() {
            this.actual.onComplete();
        }

        @Override
        public final int requestFusion(int requestedMode) {
            Fuseable.QueueSubscription<T> qs = this.qs;
            if (qs != null) {
                int m = qs.requestFusion(requestedMode);
                if (m != 0) {
                    this.fusionMode = m;
                }
                return m;
            }
            return 0;
        }

        final Throwable fail(Throwable t) {
            boolean lightCheckpoint = this.snapshotStack.isLight();
            OnAssemblyException onAssemblyException = null;
            for (Throwable e : t.getSuppressed()) {
                if (!(e instanceof OnAssemblyException)) continue;
                onAssemblyException = (OnAssemblyException)e;
                break;
            }
            if (onAssemblyException == null) {
                if (lightCheckpoint) {
                    onAssemblyException = new OnAssemblyException("");
                } else {
                    StringBuilder sb = new StringBuilder();
                    FluxOnAssembly.fillStacktraceHeader(sb, this.parent.getClass(), this.snapshotStack.getDescription());
                    sb.append(this.snapshotStack.toAssemblyInformation().replaceFirst("\\n$", ""));
                    String description = sb.toString();
                    onAssemblyException = new OnAssemblyException(description);
                }
                t = Exceptions.addSuppressed(t, (Throwable)onAssemblyException);
                StackTraceElement[] stackTrace = t.getStackTrace();
                if (stackTrace.length > 0) {
                    StackTraceElement[] newStackTrace = new StackTraceElement[stackTrace.length];
                    int i = 0;
                    for (StackTraceElement stackTraceElement : stackTrace) {
                        String className = stackTraceElement.getClassName();
                        if (className.startsWith("reactor.core.publisher.") && className.contains("OnAssembly")) continue;
                        newStackTrace[i] = stackTraceElement;
                        ++i;
                    }
                    newStackTrace = Arrays.copyOf(newStackTrace, i);
                    onAssemblyException.setStackTrace(newStackTrace);
                    t.setStackTrace(new StackTraceElement[]{stackTrace[0]});
                }
            }
            onAssemblyException.add(this.parent, this.current, this.snapshotStack);
            return t;
        }

        @Override
        public final boolean isEmpty() {
            try {
                return this.qs.isEmpty();
            }
            catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                throw Exceptions.propagate(this.fail(ex));
            }
        }

        @Override
        public final void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.qs = Operators.as(s);
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public final int size() {
            return this.qs.size();
        }

        @Override
        public final void clear() {
            this.qs.clear();
        }

        public final void request(long n) {
            this.s.request(n);
        }

        public final void cancel() {
            this.s.cancel();
        }

        @Override
        @Nullable
        public final T poll() {
            try {
                return (T)this.qs.poll();
            }
            catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                throw Exceptions.propagate(this.fail(ex));
            }
        }
    }

    static final class OnAssemblyException
    extends RuntimeException {
        private static final long serialVersionUID = -6342981676020433721L;
        final Map<Integer, ObservedAtInformationNode> nodesPerId = new HashMap<Integer, ObservedAtInformationNode>();
        final ObservedAtInformationNode root = new ObservedAtInformationNode(-1, "ROOT", "ROOT");
        int maxOperatorSize = 0;

        OnAssemblyException(String message) {
            super(message);
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }

        void add(Publisher<?> parent, Publisher<?> current, AssemblySnapshot snapshot) {
            if (snapshot.isCheckpoint()) {
                if (snapshot.isLight()) {
                    this.add(parent, current, snapshot.lightPrefix(), Objects.requireNonNull(snapshot.getDescription()));
                } else {
                    String assemblyInformation = snapshot.toAssemblyInformation();
                    String[] parts = Traces.extractOperatorAssemblyInformationParts(assemblyInformation);
                    if (parts.length > 0) {
                        String line = parts[parts.length - 1];
                        this.add(parent, current, snapshot.lightPrefix(), line);
                    } else {
                        this.add(parent, current, snapshot.lightPrefix(), Objects.requireNonNull(snapshot.getDescription()));
                    }
                }
            } else {
                String assemblyInformation = snapshot.toAssemblyInformation();
                String[] parts = Traces.extractOperatorAssemblyInformationParts(assemblyInformation);
                if (parts.length > 0) {
                    String prefix = parts.length > 1 ? parts[0] : "";
                    String line = parts[parts.length - 1];
                    this.add(parent, current, prefix, line);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void add(Publisher<?> operator, Publisher<?> currentAssembly, String prefix, String line) {
            Scannable parentAssembly = Scannable.from(currentAssembly).parents().filter(s -> s instanceof AssemblyOp).findFirst().orElse(null);
            int thisId = System.identityHashCode(currentAssembly);
            int parentId = System.identityHashCode(parentAssembly);
            Map<Integer, ObservedAtInformationNode> map = this.nodesPerId;
            synchronized (map) {
                ObservedAtInformationNode thisNode = this.nodesPerId.get(thisId);
                if (thisNode != null) {
                    thisNode.incrementCount();
                } else {
                    thisNode = new ObservedAtInformationNode(thisId, prefix, line);
                    this.nodesPerId.put(thisId, thisNode);
                }
                if (parentAssembly == null) {
                    this.root.addNode(thisNode);
                } else {
                    ObservedAtInformationNode parentNode = this.nodesPerId.get(parentId);
                    if (parentNode != null) {
                        parentNode.addNode(thisNode);
                    } else {
                        this.root.addNode(thisNode);
                    }
                }
                int length = thisNode.operator.length();
                if (length > this.maxOperatorSize) {
                    this.maxOperatorSize = length;
                }
            }
        }

        void findPathToLeaves(ObservedAtInformationNode node, List<List<ObservedAtInformationNode>> rootPaths) {
            if (node.children.isEmpty()) {
                LinkedList<ObservedAtInformationNode> pathForLeaf = new LinkedList<ObservedAtInformationNode>();
                ObservedAtInformationNode traversed = node;
                while (traversed != null && traversed != this.root) {
                    pathForLeaf.add(0, traversed);
                    traversed = traversed.parent;
                }
                rootPaths.add(pathForLeaf);
                return;
            }
            node.children.forEach(n -> this.findPathToLeaves((ObservedAtInformationNode)n, rootPaths));
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public String getMessage() {
            Map<Integer, ObservedAtInformationNode> map = this.nodesPerId;
            synchronized (map) {
                if (this.root.children.isEmpty()) {
                    return super.getMessage();
                }
                StringBuilder sb = new StringBuilder(super.getMessage()).append(System.lineSeparator()).append("Error has been observed at the following site(s):").append(System.lineSeparator());
                ArrayList rootPaths = new ArrayList();
                this.root.children.forEach(actualRoot -> this.findPathToLeaves((ObservedAtInformationNode)actualRoot, rootPaths));
                rootPaths.forEach(path -> path.forEach(node -> {
                    boolean isRoot = node.parent == null || node.parent == this.root;
                    sb.append("\t");
                    String connector = "|_";
                    if (isRoot) {
                        connector = "*_";
                    }
                    sb.append(connector);
                    char filler = isRoot ? (char)'_' : (char)' ';
                    for (int i = node.operator.length(); i < this.maxOperatorSize; ++i) {
                        sb.append(filler);
                    }
                    sb.append(filler);
                    sb.append(node.operator);
                    sb.append(" \u21e2 ");
                    sb.append(node.message);
                    if (node.occurrenceCounter > 0) {
                        sb.append(" (observed ").append(node.occurrenceCounter + 1).append(" times)");
                    }
                    sb.append(System.lineSeparator());
                }));
                sb.append("Original Stack Trace:");
                return sb.toString();
            }
        }

        @Override
        public String toString() {
            String message = this.getLocalizedMessage();
            if (message == null) {
                return "The stacktrace should have been enhanced by Reactor, but there was no message in OnAssemblyException";
            }
            return "The stacktrace has been enhanced by Reactor, refer to additional information below: " + message;
        }
    }

    static final class ObservedAtInformationNode
    implements Serializable {
        private static final long serialVersionUID = 1L;
        final int id;
        final String operator;
        final String message;
        int occurrenceCounter;
        @Nullable
        ObservedAtInformationNode parent;
        Set<ObservedAtInformationNode> children;

        ObservedAtInformationNode(int id, String operator, String message) {
            this.id = id;
            this.operator = operator;
            this.message = message;
            this.occurrenceCounter = 0;
            this.children = new LinkedHashSet<ObservedAtInformationNode>();
        }

        void incrementCount() {
            ++this.occurrenceCounter;
        }

        void addNode(ObservedAtInformationNode node) {
            if (this == node) {
                return;
            }
            if (this.children.add(node)) {
                node.parent = this;
            }
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ObservedAtInformationNode node = (ObservedAtInformationNode)o;
            return this.id == node.id && this.operator.equals(node.operator) && this.message.equals(node.message);
        }

        public int hashCode() {
            return Objects.hash(this.id, this.operator, this.message);
        }

        public String toString() {
            return this.operator + "{@" + this.id + (this.children.isEmpty() ? "" : ", " + this.children.size() + " children") + '}';
        }
    }

    static final class MethodReturnSnapshot
    extends AssemblySnapshot {
        MethodReturnSnapshot(String method) {
            super(false, method, null);
            this.cached = method;
        }

        @Override
        public boolean isLight() {
            return true;
        }

        @Override
        String operatorAssemblyInformation() {
            return this.cached;
        }
    }

    static final class CheckpointHeavySnapshot
    extends AssemblySnapshot {
        CheckpointHeavySnapshot(@Nullable String description, Supplier<String> assemblyInformationSupplier) {
            super(true, description, assemblyInformationSupplier);
        }

        @Override
        public String lightPrefix() {
            return "checkpoint(" + (this.description == null ? "" : this.description) + ")";
        }
    }

    static final class CheckpointLightSnapshot
    extends AssemblySnapshot {
        CheckpointLightSnapshot(@Nullable String description) {
            super(true, description, null);
            this.cached = "checkpoint(\"" + (description == null ? "" : description) + "\")";
        }

        @Override
        public boolean isLight() {
            return true;
        }

        @Override
        public String lightPrefix() {
            return "checkpoint";
        }

        @Override
        String operatorAssemblyInformation() {
            return this.cached;
        }
    }

    static class AssemblySnapshot {
        final boolean isCheckpoint;
        @Nullable
        final String description;
        @Nullable
        final Supplier<String> assemblyInformationSupplier;
        String cached;

        AssemblySnapshot(@Nullable String description, Supplier<String> assemblyInformationSupplier) {
            this(description != null, description, assemblyInformationSupplier);
        }

        AssemblySnapshot(String assemblyInformation) {
            this.isCheckpoint = false;
            this.description = null;
            this.assemblyInformationSupplier = null;
            this.cached = assemblyInformation;
        }

        private AssemblySnapshot(boolean isCheckpoint, @Nullable String description, @Nullable Supplier<String> assemblyInformationSupplier) {
            this.isCheckpoint = isCheckpoint;
            this.description = description;
            this.assemblyInformationSupplier = assemblyInformationSupplier;
        }

        public boolean hasDescription() {
            return this.description != null;
        }

        @Nullable
        public String getDescription() {
            return this.description;
        }

        public boolean isCheckpoint() {
            return this.isCheckpoint;
        }

        public boolean isLight() {
            return false;
        }

        public String lightPrefix() {
            return "";
        }

        String toAssemblyInformation() {
            if (this.cached == null) {
                if (this.assemblyInformationSupplier == null) {
                    throw new IllegalStateException("assemblyInformation must either be supplied or resolvable");
                }
                this.cached = this.assemblyInformationSupplier.get();
            }
            return this.cached;
        }

        String operatorAssemblyInformation() {
            return Traces.extractOperatorAssemblyInformation(this.toAssemblyInformation());
        }
    }
}

