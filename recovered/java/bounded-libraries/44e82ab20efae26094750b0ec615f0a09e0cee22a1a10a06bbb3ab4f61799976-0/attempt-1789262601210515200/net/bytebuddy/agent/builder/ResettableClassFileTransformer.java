/*
 * Decompiled with CFR 0.152.
 */
package net.bytebuddy.agent.builder;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Iterator;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.utility.JavaModule;
import net.bytebuddy.utility.nullability.MaybeNull;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ResettableClassFileTransformer
extends ClassFileTransformer {
    public Iterator<AgentBuilder.Transformer> iterator(TypeDescription var1, @MaybeNull ClassLoader var2, @MaybeNull JavaModule var3, @MaybeNull Class<?> var4, ProtectionDomain var5);

    public boolean reset(Instrumentation var1, AgentBuilder.RedefinitionStrategy var2);

    public boolean reset(Instrumentation var1, AgentBuilder.RedefinitionStrategy var2, AgentBuilder.RedefinitionStrategy.BatchAllocator var3);

    public boolean reset(Instrumentation var1, AgentBuilder.RedefinitionStrategy var2, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy var3);

    public boolean reset(Instrumentation var1, AgentBuilder.RedefinitionStrategy var2, AgentBuilder.RedefinitionStrategy.BatchAllocator var3, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy var4);

    public boolean reset(Instrumentation var1, AgentBuilder.RedefinitionStrategy var2, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy var3, AgentBuilder.RedefinitionStrategy.Listener var4);

    public boolean reset(Instrumentation var1, AgentBuilder.RedefinitionStrategy var2, AgentBuilder.RedefinitionStrategy.BatchAllocator var3, AgentBuilder.RedefinitionStrategy.Listener var4);

    public boolean reset(Instrumentation var1, AgentBuilder.RedefinitionStrategy var2, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy var3, AgentBuilder.RedefinitionStrategy.BatchAllocator var4, AgentBuilder.RedefinitionStrategy.Listener var5);

    public boolean reset(Instrumentation var1, ResettableClassFileTransformer var2, AgentBuilder.RedefinitionStrategy var3, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy var4, AgentBuilder.RedefinitionStrategy.BatchAllocator var5, AgentBuilder.RedefinitionStrategy.Listener var6);

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @HashCodeAndEqualsPlugin.Enhance
    public static abstract class WithDelegation
    extends AbstractBase {
        protected final ResettableClassFileTransformer classFileTransformer;

        protected WithDelegation(ResettableClassFileTransformer classFileTransformer) {
            this.classFileTransformer = classFileTransformer;
        }

        @Override
        public Iterator<AgentBuilder.Transformer> iterator(TypeDescription typeDescription, @MaybeNull ClassLoader classLoader, @MaybeNull JavaModule module, @MaybeNull Class<?> classBeingRedefined, ProtectionDomain protectionDomain) {
            return this.classFileTransformer.iterator(typeDescription, classLoader, module, classBeingRedefined, protectionDomain);
        }

        @Override
        public boolean reset(Instrumentation instrumentation, ResettableClassFileTransformer classFileTransformer, AgentBuilder.RedefinitionStrategy redefinitionStrategy, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy redefinitionDiscoveryStrategy, AgentBuilder.RedefinitionStrategy.BatchAllocator redefinitionBatchAllocator, AgentBuilder.RedefinitionStrategy.Listener redefinitionListener) {
            return this.classFileTransformer.reset(instrumentation, classFileTransformer, redefinitionStrategy, redefinitionDiscoveryStrategy, redefinitionBatchAllocator, redefinitionListener);
        }

        public boolean equals(@MaybeNull Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            return this.classFileTransformer.equals(((WithDelegation)object).classFileTransformer);
        }

        public int hashCode() {
            return this.getClass().hashCode() * 31 + this.classFileTransformer.hashCode();
        }
    }

    public static abstract class AbstractBase
    implements ResettableClassFileTransformer {
        public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy) {
            return this.reset(instrumentation, redefinitionStrategy, AgentBuilder.RedefinitionStrategy.BatchAllocator.ForTotal.INSTANCE);
        }

        public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy, AgentBuilder.RedefinitionStrategy.BatchAllocator redefinitionBatchAllocator) {
            return this.reset(instrumentation, redefinitionStrategy, redefinitionBatchAllocator, (AgentBuilder.RedefinitionStrategy.Listener)AgentBuilder.RedefinitionStrategy.Listener.NoOp.INSTANCE);
        }

        public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy redefinitionDiscoveryStrategy) {
            return this.reset(instrumentation, redefinitionStrategy, redefinitionDiscoveryStrategy, (AgentBuilder.RedefinitionStrategy.Listener)AgentBuilder.RedefinitionStrategy.Listener.NoOp.INSTANCE);
        }

        public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy, AgentBuilder.RedefinitionStrategy.BatchAllocator redefinitionBatchAllocator, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy redefinitionDiscoveryStrategy) {
            return this.reset(instrumentation, redefinitionStrategy, redefinitionDiscoveryStrategy, redefinitionBatchAllocator, AgentBuilder.RedefinitionStrategy.Listener.NoOp.INSTANCE);
        }

        public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy redefinitionDiscoveryStrategy, AgentBuilder.RedefinitionStrategy.Listener redefinitionListener) {
            return this.reset(instrumentation, redefinitionStrategy, redefinitionDiscoveryStrategy, AgentBuilder.RedefinitionStrategy.BatchAllocator.ForTotal.INSTANCE, redefinitionListener);
        }

        public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy, AgentBuilder.RedefinitionStrategy.BatchAllocator redefinitionBatchAllocator, AgentBuilder.RedefinitionStrategy.Listener redefinitionListener) {
            return this.reset(instrumentation, redefinitionStrategy, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy.SinglePass.INSTANCE, redefinitionBatchAllocator, redefinitionListener);
        }

        public boolean reset(Instrumentation instrumentation, AgentBuilder.RedefinitionStrategy redefinitionStrategy, AgentBuilder.RedefinitionStrategy.DiscoveryStrategy redefinitionDiscoveryStrategy, AgentBuilder.RedefinitionStrategy.BatchAllocator redefinitionBatchAllocator, AgentBuilder.RedefinitionStrategy.Listener redefinitionListener) {
            return this.reset(instrumentation, this, redefinitionStrategy, redefinitionDiscoveryStrategy, redefinitionBatchAllocator, redefinitionListener);
        }
    }
}

