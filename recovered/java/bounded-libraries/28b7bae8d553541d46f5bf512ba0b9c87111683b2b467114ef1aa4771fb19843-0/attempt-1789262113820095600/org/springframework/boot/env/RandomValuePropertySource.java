/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.MutablePropertySources
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.DigestUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.env;

import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

public class RandomValuePropertySource
extends PropertySource<Random> {
    public static final String RANDOM_PROPERTY_SOURCE_NAME = "random";
    private static final String PREFIX = "random.";
    private static final Log logger = LogFactory.getLog(RandomValuePropertySource.class);

    public RandomValuePropertySource() {
        this(RANDOM_PROPERTY_SOURCE_NAME);
    }

    public RandomValuePropertySource(String name) {
        super(name, (Object)new Random());
    }

    public Object getProperty(String name) {
        if (!name.startsWith(PREFIX)) {
            return null;
        }
        logger.trace((Object)LogMessage.format((String)"Generating random property for '%s'", (Object)name));
        return this.getRandomValue(name.substring(PREFIX.length()));
    }

    private Object getRandomValue(String type) {
        if (type.equals("int")) {
            return ((Random)this.getSource()).nextInt();
        }
        if (type.equals("long")) {
            return ((Random)this.getSource()).nextLong();
        }
        String range = this.getRange(type, "int");
        if (range != null) {
            return this.getNextIntInRange(Range.of(range, Integer::parseInt));
        }
        range = this.getRange(type, "long");
        if (range != null) {
            return this.getNextLongInRange(Range.of(range, Long::parseLong));
        }
        if (type.equals("uuid")) {
            return UUID.randomUUID().toString();
        }
        return this.getRandomBytes();
    }

    private String getRange(String type, String prefix) {
        if (type.startsWith(prefix)) {
            int startIndex = prefix.length() + 1;
            if (type.length() > startIndex) {
                return type.substring(startIndex, type.length() - 1);
            }
        }
        return null;
    }

    private int getNextIntInRange(Range<Integer> range) {
        OptionalInt first = ((Random)this.getSource()).ints(1L, range.getMin(), range.getMax()).findFirst();
        this.assertPresent(first.isPresent(), range);
        return first.getAsInt();
    }

    private long getNextLongInRange(Range<Long> range) {
        OptionalLong first = ((Random)this.getSource()).longs(1L, range.getMin(), range.getMax()).findFirst();
        this.assertPresent(first.isPresent(), range);
        return first.getAsLong();
    }

    private void assertPresent(boolean present, Range<?> range) {
        Assert.state((boolean)present, () -> "Could not get random number for range '" + range + "'");
    }

    private Object getRandomBytes() {
        byte[] bytes = new byte[32];
        ((Random)this.getSource()).nextBytes(bytes);
        return DigestUtils.md5DigestAsHex((byte[])bytes);
    }

    public static void addToEnvironment(ConfigurableEnvironment environment) {
        RandomValuePropertySource.addToEnvironment(environment, logger);
    }

    static void addToEnvironment(ConfigurableEnvironment environment, Log logger) {
        MutablePropertySources sources = environment.getPropertySources();
        PropertySource existing = sources.get(RANDOM_PROPERTY_SOURCE_NAME);
        if (existing != null) {
            logger.trace((Object)"RandomValuePropertySource already present");
            return;
        }
        RandomValuePropertySource randomSource = new RandomValuePropertySource(RANDOM_PROPERTY_SOURCE_NAME);
        if (sources.get("systemEnvironment") != null) {
            sources.addAfter("systemEnvironment", (PropertySource)randomSource);
        } else {
            sources.addLast((PropertySource)randomSource);
        }
        logger.trace((Object)"RandomValuePropertySource add to Environment");
    }

    static final class Range<T extends Number> {
        private final String value;
        private final T min;
        private final T max;

        private Range(String value, T min, T max) {
            this.value = value;
            this.min = min;
            this.max = max;
        }

        T getMin() {
            return this.min;
        }

        T getMax() {
            return this.max;
        }

        public String toString() {
            return this.value;
        }

        static <T extends Number> Range<T> of(String value, Function<String, T> parse) {
            Number zero = (Number)parse.apply("0");
            String[] tokens = StringUtils.commaDelimitedListToStringArray((String)value);
            Number min = (Number)parse.apply(tokens[0]);
            if (tokens.length == 1) {
                Assert.isTrue((((Comparable)((Object)min)).compareTo(zero) > 0 ? 1 : 0) != 0, (String)"Bound must be positive.");
                return new Range<Number>(value, zero, min);
            }
            Number max = (Number)parse.apply(tokens[1]);
            Assert.isTrue((((Comparable)((Object)min)).compareTo(max) < 0 ? 1 : 0) != 0, (String)"Lower bound must be less than upper bound.");
            return new Range<Number>(value, min, max);
        }
    }
}

