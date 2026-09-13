/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.IdGenerator
 */
package org.springframework.integration.support;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.util.IdGenerator;

public class IdGenerators {

    public static class SimpleIncrementingIdGenerator
    implements IdGenerator {
        private final AtomicLong topBits = new AtomicLong();
        private final AtomicLong bottomBits = new AtomicLong();

        public UUID generateId() {
            long lowerBits = this.bottomBits.incrementAndGet();
            if (lowerBits == 0L) {
                return new UUID(this.topBits.incrementAndGet(), lowerBits);
            }
            return new UUID(this.topBits.get(), lowerBits);
        }
    }

    public static class JdkIdGenerator
    implements IdGenerator {
        public UUID generateId() {
            return UUID.randomUUID();
        }
    }
}

