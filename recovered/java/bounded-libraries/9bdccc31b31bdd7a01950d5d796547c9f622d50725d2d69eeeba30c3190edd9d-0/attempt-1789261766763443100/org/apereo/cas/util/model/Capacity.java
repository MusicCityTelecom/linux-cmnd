/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.util.model;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;

public class Capacity
implements Serializable {
    private static final long serialVersionUID = -331719796564884951L;
    private static final Pattern SIZE_PATTERN = Pattern.compile("(\\d+(\\.\\d+)*)\\s*(\\S+)");
    private final UnitOfMeasure unitOfMeasure;
    private final Double size;

    public static Capacity parse(String capacity) {
        Matcher matcher = SIZE_PATTERN.matcher(capacity);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid capacity definition: " + capacity);
        }
        double count = Double.parseDouble(matcher.group(1));
        UnitOfMeasure unit = UnitOfMeasure.valueOf(matcher.group(3).toUpperCase());
        return ((CapacityBuilder)((CapacityBuilder)Capacity.builder().unitOfMeasure(unit)).size(count)).build();
    }

    @Generated
    protected Capacity(CapacityBuilder<?, ?> b) {
        this.unitOfMeasure = b.unitOfMeasure;
        this.size = b.size;
    }

    @Generated
    public static CapacityBuilder<?, ?> builder() {
        return new CapacityBuilderImpl();
    }

    @Generated
    public UnitOfMeasure getUnitOfMeasure() {
        return this.unitOfMeasure;
    }

    @Generated
    public Double getSize() {
        return this.size;
    }

    @Generated
    private static final class CapacityBuilderImpl
    extends CapacityBuilder<Capacity, CapacityBuilderImpl> {
        @Generated
        private CapacityBuilderImpl() {
        }

        @Override
        @Generated
        protected CapacityBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public Capacity build() {
            return new Capacity(this);
        }
    }

    @Generated
    public static abstract class CapacityBuilder<C extends Capacity, B extends CapacityBuilder<C, B>> {
        @Generated
        private UnitOfMeasure unitOfMeasure;
        @Generated
        private Double size;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B unitOfMeasure(UnitOfMeasure unitOfMeasure) {
            this.unitOfMeasure = unitOfMeasure;
            return this.self();
        }

        @Generated
        public B size(Double size) {
            this.size = size;
            return this.self();
        }

        @Generated
        public String toString() {
            return "Capacity.CapacityBuilder(unitOfMeasure=" + this.unitOfMeasure + ", size=" + this.size + ")";
        }
    }

    public static enum UnitOfMeasure {
        B,
        KB,
        MB,
        GB,
        TB;

    }
}

