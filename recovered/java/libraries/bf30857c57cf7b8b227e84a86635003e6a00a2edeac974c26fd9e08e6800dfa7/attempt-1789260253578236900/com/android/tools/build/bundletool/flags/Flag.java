/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.flags;

import com.android.tools.build.bundletool.flags.FlagParser;
import com.android.tools.build.bundletool.flags.ParsedFlags;
import com.android.tools.build.bundletool.model.Password;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.utils.OsPlatform;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Flag<T> {
    private static final Splitter KEY_VALUE_SPLITTER = Splitter.on(':').limit(2);
    private static final Pattern HOME_DIRECTORY_ALIAS = Pattern.compile("^~");
    private static final Splitter ITEM_SPLITTER = Splitter.on(',');
    protected final String name;

    public static Flag<Boolean> booleanFlag(String name) {
        return new BooleanFlag(name);
    }

    public static <T extends Enum<T>> Flag<T> enumFlag(String name, Class<T> enumClass) {
        return new EnumFlag<T>(name, enumClass);
    }

    public static <T extends Enum<T>> Flag<ImmutableSet<T>> enumSet(String name, Class<T> enumClass) {
        return new SetFlag<T>(new EnumFlag<T>(name, enumClass));
    }

    public static <K, V> Flag<Map.Entry<K, V>> keyValue(String name, Class<K> keyClass, Class<V> valueClass) {
        return Flag.keyValueInternal(name, keyClass, valueClass);
    }

    private static <K, V> KeyValueFlag<K, V> keyValueInternal(String name, Class<K> keyClass, Class<V> valueClass) {
        return new KeyValueFlag<K, V>(name, Flag.flagForType(name, keyClass), Flag.flagForType(name, valueClass));
    }

    public static <K, V> Flag<ImmutableMap<K, V>> mapCollector(String name, Class<K> keyClass, Class<V> valueClass) {
        return new MapCollectorFlag<K, V>(name, Flag.keyValueInternal(name, keyClass, valueClass));
    }

    public static Flag<Password> password(String name) {
        return new PasswordFlag(name);
    }

    public static Flag<Path> path(String name) {
        return new PathFlag(name);
    }

    public static Flag<ImmutableList<Path>> pathList(String name) {
        return new ListFlag<Path>(new PathFlag(name));
    }

    public static Flag<Integer> positiveInteger(String name) {
        return new IntegerFlag(name, n2 -> n2 > 0, "The value must be positive.");
    }

    public static Flag<String> string(String name) {
        return new StringFlag(name);
    }

    public static Flag<ImmutableList<String>> stringCollector(String name) {
        return new CollectorFlag<String>(new StringFlag(name));
    }

    public static Flag<ImmutableList<String>> stringList(String name) {
        return new ListFlag<String>(new StringFlag(name));
    }

    public static Flag<ImmutableSet<String>> stringSet(String name) {
        return new SetFlag<String>(new StringFlag(name));
    }

    Flag(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public final T getRequiredValue(ParsedFlags flags) {
        return this.getValue(flags).orElseThrow(() -> new RequiredFlagNotSetException(this.name));
    }

    public abstract Optional<T> getValue(ParsedFlags var1);

    private static <T> SingleValueFlag<T> flagForType(String name, Class<T> clazz) {
        if (clazz.equals(Boolean.class)) {
            return new BooleanFlag(name);
        }
        if (clazz.equals(Path.class)) {
            return new PathFlag(name);
        }
        if (clazz.equals(String.class)) {
            return new StringFlag(name);
        }
        if (clazz.equals(ZipPath.class)) {
            return new ZipPathFlag(name);
        }
        throw new IllegalArgumentException(String.format("Unrecognized flag type '%s'.", clazz));
    }

    public static final class RequiredFlagNotSetException
    extends FlagParser.FlagParseException {
        RequiredFlagNotSetException(String flagName) {
            super(String.format("Missing the required --%s flag.", flagName));
        }
    }

    static class SetFlag<T>
    extends SingleValueFlag<ImmutableSet<T>> {
        private final SingleValueFlag<T> singleFlag;

        SetFlag(SingleValueFlag<T> singleFlag) {
            super(singleFlag.name);
            this.singleFlag = singleFlag;
        }

        @Override
        protected ImmutableSet<T> parse(String value) {
            if (value.isEmpty()) {
                return ImmutableSet.of();
            }
            return ITEM_SPLITTER.splitToList(value).stream().map(this.singleFlag::parse).collect(ImmutableSet.toImmutableSet());
        }
    }

    static class ListFlag<T>
    extends SingleValueFlag<ImmutableList<T>> {
        private final SingleValueFlag<T> singleFlag;

        ListFlag(SingleValueFlag<T> singleFlag) {
            super(singleFlag.name);
            this.singleFlag = singleFlag;
        }

        @Override
        protected ImmutableList<T> parse(String value) {
            if (value.isEmpty()) {
                return ImmutableList.of();
            }
            return ITEM_SPLITTER.splitToList(value).stream().map(this.singleFlag::parse).collect(ImmutableList.toImmutableList());
        }
    }

    static class CollectorFlag<T>
    extends Flag<ImmutableList<T>> {
        private final SingleValueFlag<T> singleFlag;

        CollectorFlag(SingleValueFlag<T> singleFlag) {
            super(singleFlag.name);
            this.singleFlag = singleFlag;
        }

        @Override
        public final Optional<ImmutableList<T>> getValue(ParsedFlags flags) {
            ImmutableList<String> rawValues = flags.getFlagValues(this.name);
            if (rawValues.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(this.parseValues(rawValues));
        }

        private ImmutableList<T> parseValues(ImmutableList<String> rawValues) {
            return rawValues.stream().filter(Predicates.not(String::isEmpty)).map(this.singleFlag::parse).collect(ImmutableList.toImmutableList());
        }
    }

    static class ZipPathFlag
    extends SingleValueFlag<ZipPath> {
        public ZipPathFlag(String name) {
            super(name);
        }

        @Override
        protected ZipPath parse(String value) {
            return ZipPath.create(value);
        }
    }

    static class StringFlag
    extends SingleValueFlag<String> {
        public StringFlag(String name) {
            super(name);
        }

        @Override
        protected String parse(String value) {
            return value;
        }
    }

    static class PathFlag
    extends SingleValueFlag<Path> {
        public PathFlag(String name) {
            super(name);
        }

        @Override
        protected Path parse(String value) {
            if (!OsPlatform.getCurrentPlatform().equals((Object)OsPlatform.WINDOWS)) {
                value = HOME_DIRECTORY_ALIAS.matcher(value).replaceFirst(Matcher.quoteReplacement(System.getProperty("user.home")));
            }
            return Paths.get(value, new String[0]);
        }
    }

    static class PasswordFlag
    extends SingleValueFlag<Password> {
        public PasswordFlag(String name) {
            super(name);
        }

        @Override
        protected Password parse(String value) {
            return PasswordFlag.createFromFlagValue(value);
        }

        private static Password createFromFlagValue(String flagValue) {
            if (flagValue.startsWith("pass:")) {
                return new Password(() -> new KeyStore.PasswordProtection(flagValue.substring("pass:".length()).toCharArray()));
            }
            if (flagValue.startsWith("file:")) {
                Path passwordFile = Paths.get(flagValue.substring("file:".length()), new String[0]);
                FilePreconditions.checkFileExistsAndReadable(passwordFile);
                return new Password(() -> new KeyStore.PasswordProtection(PasswordFlag.readPasswordFromFile(passwordFile).toCharArray()));
            }
            throw new FlagParser.FlagParseException("Passwords must be prefixed with \"pass:\" or \"file:\".");
        }

        private static String readPasswordFromFile(Path passwordFile) {
            try {
                return Files.asCharSource(passwordFile.toFile(), StandardCharsets.UTF_8).readFirstLine();
            }
            catch (IOException e2) {
                throw new UncheckedIOException(String.format("Unable to read password from file '%s'.", passwordFile), e2);
            }
        }
    }

    static class MapCollectorFlag<K, V>
    extends Flag<ImmutableMap<K, V>> {
        private final KeyValueFlag<K, V> keyValueFlag;

        MapCollectorFlag(String name, KeyValueFlag<K, V> keyValueFlag) {
            super(name);
            this.keyValueFlag = keyValueFlag;
        }

        @Override
        public final Optional<ImmutableMap<K, V>> getValue(ParsedFlags flags) {
            ImmutableList<String> rawValues = flags.getFlagValues(this.name);
            if (rawValues.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(this.parseValues(rawValues));
        }

        private ImmutableMap<K, V> parseValues(ImmutableList<String> rawValues) {
            return rawValues.stream().filter(Predicates.not(String::isEmpty)).map(this.keyValueFlag::parse).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
        }
    }

    static class KeyValueFlag<K, V>
    extends SingleValueFlag<Map.Entry<K, V>> {
        private final SingleValueFlag<K> keyFlag;
        private final SingleValueFlag<V> valueFlag;

        KeyValueFlag(String name, SingleValueFlag<K> keyFlag, SingleValueFlag<V> valueFlag) {
            super(name);
            this.keyFlag = keyFlag;
            this.valueFlag = valueFlag;
        }

        @Override
        protected Map.Entry<K, V> parse(String value) {
            final List<String> keyValueList = KEY_VALUE_SPLITTER.splitToList(value);
            if (keyValueList.size() != 2) {
                throw new FlagParser.FlagParseException(String.format("Values of flag --%s must contain ':', but found '%s'.", this.name, value));
            }
            return new Map.Entry<K, V>(){

                @Override
                public K getKey() {
                    return keyFlag.parse((String)keyValueList.get(0));
                }

                @Override
                public V getValue() {
                    return valueFlag.parse((String)keyValueList.get(1));
                }

                @Override
                public V setValue(V value) {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    static class IntegerFlag
    extends SingleValueFlag<Integer> {
        private final Predicate<Integer> validator;
        private final String errorMessage;

        public IntegerFlag(String name, Predicate<Integer> validator, String errorMessage) {
            super(name);
            this.validator = validator;
            this.errorMessage = errorMessage;
        }

        @Override
        protected Integer parse(String value) {
            try {
                Integer parsedValue = Integer.parseInt(value);
                if (!this.validator.test(parsedValue)) {
                    throw new FlagParser.FlagParseException(String.format("Integer flag --%s has illegal value '%s'. %s.", this.name, value, this.errorMessage));
                }
                return parsedValue;
            }
            catch (NumberFormatException e2) {
                throw new FlagParser.FlagParseException(String.format("Error while parsing value '%s' of the integer flag --%s.", value, this.name));
            }
        }
    }

    static class EnumFlag<T extends Enum<T>>
    extends SingleValueFlag<T> {
        private final Class<T> enumType;

        public EnumFlag(String name, Class<T> enumType) {
            super(name);
            this.enumType = enumType;
        }

        @Override
        protected T parse(String value) {
            try {
                return Enum.valueOf(this.enumType, value.toUpperCase());
            }
            catch (IllegalArgumentException e2) {
                throw new FlagParser.FlagParseException(String.format("Not a valid enum value '%s' of flag --%s. Expected one of: %s", value, this.name, Arrays.stream(this.enumType.getEnumConstants()).map(Enum::name).collect(Collectors.joining(", "))));
            }
        }
    }

    static class BooleanFlag
    extends SingleValueFlag<Boolean> {
        public BooleanFlag(String name) {
            super(name);
        }

        @Override
        protected Boolean parse(String value) {
            if (value != null) {
                if (value.isEmpty() || value.equalsIgnoreCase("true")) {
                    return Boolean.TRUE;
                }
                if (value.equalsIgnoreCase("false")) {
                    return Boolean.FALSE;
                }
            }
            throw new FlagParser.FlagParseException(String.format("Error while parsing the boolean flag --%s. Expected values [<empty>|true|false] but found '%s'.", this.name, MoreObjects.firstNonNull(value, "null")));
        }
    }

    static abstract class SingleValueFlag<T>
    extends Flag<T> {
        SingleValueFlag(String name) {
            super(name);
        }

        @Override
        public final Optional<T> getValue(ParsedFlags flags) {
            return flags.getFlagValue(this.name).map(this::parse);
        }

        protected abstract T parse(String var1);
    }
}

