/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.PropertyNamingStrategy
 *  com.fasterxml.jackson.databind.cfg.MapperConfig
 *  com.fasterxml.jackson.databind.introspect.AnnotatedField
 *  com.fasterxml.jackson.databind.introspect.TypeResolutionContext
 *  com.fasterxml.jackson.databind.introspect.TypeResolutionContext$Empty
 */
package com.fasterxml.jackson.datatype.guava.deser.util;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import java.io.Serializable;
import java.lang.reflect.Field;

public class RangeHelper {
    private static final RangeProperties STD_NAMES = new RangeProperties();
    private static final Field[] FIELDS = STD_NAMES.fields();

    public static RangeProperties standardNames() {
        return STD_NAMES;
    }

    public static RangeProperties getPropertyNames(MapperConfig<?> config, PropertyNamingStrategy pns) {
        if (pns == null) {
            return STD_NAMES;
        }
        TypeResolutionContext.Empty typeCtxt = new TypeResolutionContext.Empty(config.getTypeFactory());
        return new RangeProperties(RangeHelper._find(config, (TypeResolutionContext)typeCtxt, pns, FIELDS[0]), RangeHelper._find(config, (TypeResolutionContext)typeCtxt, pns, FIELDS[1]), RangeHelper._find(config, (TypeResolutionContext)typeCtxt, pns, FIELDS[2]), RangeHelper._find(config, (TypeResolutionContext)typeCtxt, pns, FIELDS[3]));
    }

    private static String _find(MapperConfig<?> config, TypeResolutionContext typeCtxt, PropertyNamingStrategy pns, Field field) {
        AnnotatedField af = new AnnotatedField(typeCtxt, field, null);
        return pns.nameForField(config, af, field.getName());
    }

    public static class RangeProperties
    implements Serializable {
        private static final long serialVersionUID = 2L;
        public final String lowerEndpoint;
        public final String upperEndpoint;
        public final String lowerBoundType;
        public final String upperBoundType;

        protected RangeProperties() {
            this("lowerEndpoint", "upperEndpoint", "lowerBoundType", "upperBoundType");
        }

        public RangeProperties(String lowerEP, String upperEP, String lowerBT, String upperBT) {
            this.lowerEndpoint = lowerEP;
            this.upperEndpoint = upperEP;
            this.lowerBoundType = lowerBT;
            this.upperBoundType = upperBT;
        }

        protected Field[] fields() {
            return new Field[]{this._field(this.lowerEndpoint), this._field(this.upperEndpoint), this._field(this.lowerBoundType), this._field(this.upperBoundType)};
        }

        private Field _field(String name) {
            try {
                return this.getClass().getField(name);
            }
            catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}

