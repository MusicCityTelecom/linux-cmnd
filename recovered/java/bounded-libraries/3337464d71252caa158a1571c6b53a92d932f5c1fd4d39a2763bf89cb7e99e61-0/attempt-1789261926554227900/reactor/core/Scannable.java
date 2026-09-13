/*
 * Decompiled with CFR 0.152.
 */
package reactor.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;

@FunctionalInterface
public interface Scannable {
    public static final Pattern OPERATOR_NAME_UNRELATED_WORDS_PATTERN = Pattern.compile("Parallel|Flux|Mono|Publisher|Subscriber|Fuseable|Operator|Conditional");

    public static Scannable from(@Nullable Object o) {
        if (o == null) {
            return Attr.NULL_SCAN;
        }
        if (o instanceof Scannable) {
            return (Scannable)o;
        }
        return Attr.UNAVAILABLE_SCAN;
    }

    default public Stream<? extends Scannable> actuals() {
        return Attr.recurse(this, Attr.ACTUAL);
    }

    default public Stream<? extends Scannable> inners() {
        return Stream.empty();
    }

    default public boolean isScanAvailable() {
        return true;
    }

    default public String name() {
        String thisName = this.scan(Attr.NAME);
        if (thisName != null) {
            return thisName;
        }
        return this.parents().map(s -> s.scan(Attr.NAME)).filter(Objects::nonNull).findFirst().orElse(this.stepName());
    }

    default public String stepName() {
        String stripped;
        int stripPackageIndex;
        String name = this.getClass().getName();
        int innerClassIndex = name.indexOf(36);
        if (innerClassIndex != -1) {
            name = name.substring(0, innerClassIndex);
        }
        if ((stripPackageIndex = name.lastIndexOf(46)) != -1) {
            name = name.substring(stripPackageIndex + 1);
        }
        if (!(stripped = OPERATOR_NAME_UNRELATED_WORDS_PATTERN.matcher(name).replaceAll("")).isEmpty()) {
            return stripped.substring(0, 1).toLowerCase() + stripped.substring(1);
        }
        return stripped;
    }

    default public Stream<String> steps() {
        ArrayList<Scannable> chain = new ArrayList<Scannable>();
        chain.addAll(this.parents().collect(Collectors.toList()));
        Collections.reverse(chain);
        chain.add(this);
        chain.addAll(this.actuals().collect(Collectors.toList()));
        ArrayList<String> chainNames = new ArrayList<String>(chain.size());
        for (int i = 0; i < chain.size(); ++i) {
            Scannable step = (Scannable)chain.get(i);
            Scannable stepAfter = null;
            if (i < chain.size() - 1) {
                stepAfter = (Scannable)chain.get(i + 1);
            }
            if (stepAfter != null && stepAfter.scan(Attr.ACTUAL_METADATA).booleanValue()) {
                chainNames.add(stepAfter.stepName());
                ++i;
                continue;
            }
            chainNames.add(step.stepName());
        }
        return chainNames.stream();
    }

    default public Stream<? extends Scannable> parents() {
        return Attr.recurse(this, Attr.PARENT);
    }

    @Nullable
    public Object scanUnsafe(Attr var1);

    @Nullable
    default public <T> T scan(Attr<T> key) {
        T value = key.tryConvert(this.scanUnsafe(key));
        if (value == null) {
            return key.defaultValue();
        }
        return value;
    }

    default public <T> T scanOrDefault(Attr<T> key, T defaultValue) {
        T v = key.tryConvert(this.scanUnsafe(key));
        if (v == null) {
            return Objects.requireNonNull(defaultValue, "defaultValue");
        }
        return v;
    }

    default public Stream<Tuple2<String, String>> tags() {
        Stream<Tuple2<String, String>> parentTags = this.parents().flatMap(s -> s.scan(Attr.TAGS));
        Stream<Tuple2<String, String>> thisTags = this.scan(Attr.TAGS);
        if (thisTags == null) {
            return parentTags;
        }
        return Stream.concat(thisTags, parentTags);
    }

    public static class Attr<T> {
        public static final Attr<Scannable> ACTUAL = new Attr<Scannable>(null, Scannable::from);
        public static final Attr<Boolean> ACTUAL_METADATA = new Attr<Boolean>(false);
        public static final Attr<Integer> BUFFERED = new Attr<Integer>(0);
        public static final Attr<Integer> CAPACITY = new Attr<Integer>(0);
        public static final Attr<Boolean> CANCELLED = new Attr<Boolean>(false);
        public static final Attr<Boolean> DELAY_ERROR = new Attr<Boolean>(false);
        public static final Attr<Throwable> ERROR = new Attr<Object>(null);
        public static final Attr<Long> LARGE_BUFFERED = new Attr<Object>(null);
        public static final Attr<String> NAME = new Attr<Object>(null);
        public static final Attr<Scannable> PARENT = new Attr<Scannable>(null, Scannable::from);
        public static final Attr<Scannable> RUN_ON = new Attr<Scannable>(null, Scannable::from);
        public static final Attr<Integer> PREFETCH = new Attr<Integer>(0);
        public static final Attr<Long> REQUESTED_FROM_DOWNSTREAM = new Attr<Long>(0L);
        public static final Attr<Boolean> TERMINATED = new Attr<Boolean>(false);
        public static final Attr<Stream<Tuple2<String, String>>> TAGS = new Attr<Object>(null);
        public static final Attr<RunStyle> RUN_STYLE = new Attr<RunStyle>(RunStyle.UNKNOWN);
        public static final Attr<String> LIFTER = new Attr<Object>(null);
        final T defaultValue;
        final Function<Object, ? extends T> safeConverter;
        static final Scannable UNAVAILABLE_SCAN = new Scannable(){

            @Override
            public Object scanUnsafe(Attr key) {
                return null;
            }

            @Override
            public boolean isScanAvailable() {
                return false;
            }

            public String toString() {
                return "UNAVAILABLE_SCAN";
            }

            @Override
            public String stepName() {
                return "UNAVAILABLE_SCAN";
            }
        };
        static final Scannable NULL_SCAN = new Scannable(){

            @Override
            public Object scanUnsafe(Attr key) {
                return null;
            }

            @Override
            public boolean isScanAvailable() {
                return false;
            }

            public String toString() {
                return "NULL_SCAN";
            }

            @Override
            public String stepName() {
                return "NULL_SCAN";
            }
        };

        @Nullable
        public T defaultValue() {
            return this.defaultValue;
        }

        boolean isConversionSafe() {
            return this.safeConverter != null;
        }

        @Nullable
        T tryConvert(@Nullable Object o) {
            if (o == null) {
                return null;
            }
            if (this.safeConverter == null) {
                Object t = o;
                return (T)t;
            }
            return this.safeConverter.apply(o);
        }

        protected Attr(@Nullable T defaultValue) {
            this(defaultValue, null);
        }

        protected Attr(@Nullable T defaultValue, @Nullable Function<Object, ? extends T> safeConverter) {
            this.defaultValue = defaultValue;
            this.safeConverter = safeConverter;
        }

        static Stream<? extends Scannable> recurse(Scannable _s, final Attr<Scannable> key) {
            final Scannable s = Scannable.from(_s.scan(key));
            if (!s.isScanAvailable()) {
                return Stream.empty();
            }
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<Scannable>(){
                Scannable c;
                {
                    this.c = s;
                }

                @Override
                public boolean hasNext() {
                    return this.c != null && this.c.isScanAvailable();
                }

                @Override
                public Scannable next() {
                    Scannable _c = this.c;
                    this.c = Scannable.from(this.c.scan(key));
                    return _c;
                }
            }, 0), false);
        }

        public static enum RunStyle {
            UNKNOWN,
            ASYNC,
            SYNC;

        }
    }
}

