/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.transform.stc.ClosureParams
 *  groovy.transform.stc.FromString
 */
package groovy.json;

import groovy.json.DefaultJsonGenerator;
import groovy.lang.Closure;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.FromString;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public interface JsonGenerator {
    public String toJson(Object var1);

    public boolean isExcludingFieldsNamed(String var1);

    public boolean isExcludingValues(Object var1);

    public static class Options {
        protected static final String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
        protected static final Locale JSON_DATE_FORMAT_LOCALE = Locale.US;
        protected static final String DEFAULT_TIMEZONE = "GMT";
        protected boolean excludeNulls;
        protected boolean disableUnicodeEscaping;
        protected String dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
        protected Locale dateLocale = JSON_DATE_FORMAT_LOCALE;
        protected TimeZone timezone = TimeZone.getTimeZone("GMT");
        protected final Set<Converter> converters = new LinkedHashSet<Converter>();
        protected final Set<String> excludedFieldNames = new HashSet<String>();
        protected final Set<Class<?>> excludedFieldTypes = new HashSet();

        public Options excludeNulls() {
            this.excludeNulls = true;
            return this;
        }

        public Options disableUnicodeEscaping() {
            this.disableUnicodeEscaping = true;
            return this;
        }

        public Options dateFormat(String format) {
            return this.dateFormat(format, JSON_DATE_FORMAT_LOCALE);
        }

        public Options dateFormat(String format, Locale locale) {
            new SimpleDateFormat(format, locale);
            this.dateFormat = format;
            this.dateLocale = locale;
            return this;
        }

        public Options timezone(String timezone) {
            this.timezone = TimeZone.getTimeZone(timezone);
            return this;
        }

        public Options addConverter(Converter converter) {
            if (converter != null) {
                this.converters.add(converter);
            }
            return this;
        }

        public <T> Options addConverter(Class<T> type, @ClosureParams(value=FromString.class, options={"T", "T,String"}) Closure<?> closure) {
            DefaultJsonGenerator.ClosureConverter converter = new DefaultJsonGenerator.ClosureConverter(type, closure);
            this.converters.remove(converter);
            return this.addConverter(converter);
        }

        public Options excludeFieldsByName(CharSequence ... fieldNames) {
            return this.excludeFieldsByName(Arrays.asList(fieldNames));
        }

        public Options excludeFieldsByName(Iterable<? extends CharSequence> fieldNames) {
            for (CharSequence charSequence : fieldNames) {
                if (charSequence == null) continue;
                this.excludedFieldNames.add(charSequence.toString());
            }
            return this;
        }

        public Options excludeFieldsByType(Class<?> ... types) {
            return this.excludeFieldsByType(Arrays.asList(types));
        }

        public Options excludeFieldsByType(Iterable<Class<?>> types) {
            for (Class<?> c : types) {
                if (c == null) continue;
                this.excludedFieldTypes.add(c);
            }
            return this;
        }

        public JsonGenerator build() {
            return new DefaultJsonGenerator(this);
        }
    }

    public static interface Converter {
        public boolean handles(Class<?> var1);

        public Object convert(Object var1, String var2);
    }
}

