/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.availability.ApplicationAvailability
 *  org.springframework.boot.availability.AvailabilityState
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.availability;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.util.Assert;

public class AvailabilityStateHealthIndicator
extends AbstractHealthIndicator {
    private final ApplicationAvailability applicationAvailability;
    private final Class<? extends AvailabilityState> stateType;
    private final Map<AvailabilityState, Status> statusMappings = new HashMap<AvailabilityState, Status>();

    public <S extends AvailabilityState> AvailabilityStateHealthIndicator(ApplicationAvailability applicationAvailability, Class<S> stateType, Consumer<StatusMappings<S>> statusMappings) {
        Assert.notNull((Object)applicationAvailability, (String)"ApplicationAvailability must not be null");
        Assert.notNull(stateType, (String)"StateType must not be null");
        Assert.notNull(statusMappings, (String)"StatusMappings must not be null");
        this.applicationAvailability = applicationAvailability;
        this.stateType = stateType;
        statusMappings.accept(this.statusMappings::put);
        this.assertAllEnumsMapped(stateType);
    }

    private <S extends AvailabilityState> void assertAllEnumsMapped(Class<S> stateType) {
        if (!this.statusMappings.containsKey(null) && Enum.class.isAssignableFrom(stateType)) {
            EnumSet elements = EnumSet.allOf(stateType);
            for (Object element : elements) {
                Assert.isTrue((boolean)this.statusMappings.containsKey(element), () -> "StatusMappings does not include " + element);
            }
        }
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        AvailabilityState state = this.getState(this.applicationAvailability);
        Status status = this.statusMappings.get(state);
        if (status == null) {
            status = this.statusMappings.get(null);
        }
        Assert.state((status != null ? 1 : 0) != 0, () -> "No mapping provided for " + state);
        builder.status(status);
    }

    protected AvailabilityState getState(ApplicationAvailability applicationAvailability) {
        return applicationAvailability.getState(this.stateType);
    }

    public static interface StatusMappings<S extends AvailabilityState> {
        default public void addDefaultStatus(Status status) {
            this.add(null, status);
        }

        public void add(S var1, Status var2);
    }
}

