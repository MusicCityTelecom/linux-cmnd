/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.format.AnnotationFormatterFactory
 *  org.springframework.format.Formatter
 *  org.springframework.format.FormatterRegistry
 *  org.springframework.format.datetime.DateFormatter
 *  org.springframework.format.datetime.DateFormatterRegistrar
 *  org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
 *  org.springframework.format.number.NumberFormatAnnotationFormatterFactory
 *  org.springframework.format.number.money.CurrencyUnitFormatter
 *  org.springframework.format.number.money.Jsr354NumberFormatAnnotationFormatterFactory
 *  org.springframework.format.number.money.MonetaryAmountFormatter
 *  org.springframework.format.support.DefaultFormattingConversionService
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure.web.format;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.number.money.CurrencyUnitFormatter;
import org.springframework.format.number.money.Jsr354NumberFormatAnnotationFormatterFactory;
import org.springframework.format.number.money.MonetaryAmountFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.util.ClassUtils;

public class WebConversionService
extends DefaultFormattingConversionService {
    private static final boolean JSR_354_PRESENT = ClassUtils.isPresent((String)"javax.money.MonetaryAmount", (ClassLoader)WebConversionService.class.getClassLoader());

    public WebConversionService(DateTimeFormatters dateTimeFormatters) {
        super(false);
        if (dateTimeFormatters.isCustomized()) {
            this.addFormatters(dateTimeFormatters);
        } else {
            WebConversionService.addDefaultFormatters((FormatterRegistry)this);
        }
    }

    private void addFormatters(DateTimeFormatters dateTimeFormatters) {
        this.addFormatterForFieldAnnotation((AnnotationFormatterFactory)new NumberFormatAnnotationFormatterFactory());
        if (JSR_354_PRESENT) {
            this.addFormatter((Formatter)new CurrencyUnitFormatter());
            this.addFormatter((Formatter)new MonetaryAmountFormatter());
            this.addFormatterForFieldAnnotation((AnnotationFormatterFactory)new Jsr354NumberFormatAnnotationFormatterFactory());
        }
        this.registerJsr310(dateTimeFormatters);
        this.registerJavaDate(dateTimeFormatters);
    }

    private void registerJsr310(DateTimeFormatters dateTimeFormatters) {
        DateTimeFormatterRegistrar dateTime = new DateTimeFormatterRegistrar();
        this.configure(dateTimeFormatters::getDateFormatter, arg_0 -> ((DateTimeFormatterRegistrar)dateTime).setDateFormatter(arg_0));
        this.configure(dateTimeFormatters::getTimeFormatter, arg_0 -> ((DateTimeFormatterRegistrar)dateTime).setTimeFormatter(arg_0));
        this.configure(dateTimeFormatters::getDateTimeFormatter, arg_0 -> ((DateTimeFormatterRegistrar)dateTime).setDateTimeFormatter(arg_0));
        dateTime.registerFormatters((FormatterRegistry)this);
    }

    private void configure(Supplier<DateTimeFormatter> supplier, Consumer<DateTimeFormatter> consumer) {
        DateTimeFormatter formatter = supplier.get();
        if (formatter != null) {
            consumer.accept(formatter);
        }
    }

    private void registerJavaDate(DateTimeFormatters dateTimeFormatters) {
        DateFormatterRegistrar dateFormatterRegistrar = new DateFormatterRegistrar();
        String datePattern = dateTimeFormatters.getDatePattern();
        if (datePattern != null) {
            DateFormatter dateFormatter = new DateFormatter(datePattern);
            dateFormatterRegistrar.setFormatter(dateFormatter);
        }
        dateFormatterRegistrar.registerFormatters((FormatterRegistry)this);
    }
}

