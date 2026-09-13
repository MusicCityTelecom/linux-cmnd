/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.core.convert.converter.ConverterFactory
 *  org.springframework.core.convert.converter.ConverterRegistry
 *  org.springframework.core.convert.converter.GenericConverter
 *  org.springframework.core.convert.converter.GenericConverter$ConvertiblePair
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.format.AnnotationFormatterFactory
 *  org.springframework.format.Formatter
 *  org.springframework.format.FormatterRegistry
 *  org.springframework.format.Parser
 *  org.springframework.format.Printer
 *  org.springframework.format.support.DefaultFormattingConversionService
 *  org.springframework.format.support.FormattingConversionService
 *  org.springframework.util.StringValueResolver
 */
package org.springframework.boot.convert;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.convert.ArrayToDelimitedStringConverter;
import org.springframework.boot.convert.CharArrayFormatter;
import org.springframework.boot.convert.CharSequenceToObjectConverter;
import org.springframework.boot.convert.CollectionToDelimitedStringConverter;
import org.springframework.boot.convert.DelimitedStringToArrayConverter;
import org.springframework.boot.convert.DelimitedStringToCollectionConverter;
import org.springframework.boot.convert.DurationToNumberConverter;
import org.springframework.boot.convert.DurationToStringConverter;
import org.springframework.boot.convert.InetAddressFormatter;
import org.springframework.boot.convert.InputStreamSourceToByteArrayConverter;
import org.springframework.boot.convert.IsoOffsetFormatter;
import org.springframework.boot.convert.LenientBooleanToEnumConverterFactory;
import org.springframework.boot.convert.LenientStringToEnumConverterFactory;
import org.springframework.boot.convert.NumberToDataSizeConverter;
import org.springframework.boot.convert.NumberToDurationConverter;
import org.springframework.boot.convert.NumberToPeriodConverter;
import org.springframework.boot.convert.PeriodToStringConverter;
import org.springframework.boot.convert.StringToDataSizeConverter;
import org.springframework.boot.convert.StringToDurationConverter;
import org.springframework.boot.convert.StringToFileConverter;
import org.springframework.boot.convert.StringToPeriodConverter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.StringValueResolver;

public class ApplicationConversionService
extends FormattingConversionService {
    private static volatile ApplicationConversionService sharedInstance;
    private final boolean unmodifiable;

    public ApplicationConversionService() {
        this(null);
    }

    public ApplicationConversionService(StringValueResolver embeddedValueResolver) {
        this(embeddedValueResolver, false);
    }

    private ApplicationConversionService(StringValueResolver embeddedValueResolver, boolean unmodifiable) {
        if (embeddedValueResolver != null) {
            this.setEmbeddedValueResolver(embeddedValueResolver);
        }
        ApplicationConversionService.configure((FormatterRegistry)this);
        this.unmodifiable = unmodifiable;
    }

    public void addPrinter(Printer<?> printer) {
        this.assertModifiable();
        super.addPrinter(printer);
    }

    public void addParser(Parser<?> parser) {
        this.assertModifiable();
        super.addParser(parser);
    }

    public void addFormatter(Formatter<?> formatter) {
        this.assertModifiable();
        super.addFormatter(formatter);
    }

    public void addFormatterForFieldType(Class<?> fieldType, Formatter<?> formatter) {
        this.assertModifiable();
        super.addFormatterForFieldType(fieldType, formatter);
    }

    public void addConverter(Converter<?, ?> converter) {
        this.assertModifiable();
        super.addConverter(converter);
    }

    public void addFormatterForFieldType(Class<?> fieldType, Printer<?> printer, Parser<?> parser) {
        this.assertModifiable();
        super.addFormatterForFieldType(fieldType, printer, parser);
    }

    public void addFormatterForFieldAnnotation(AnnotationFormatterFactory<? extends Annotation> annotationFormatterFactory) {
        this.assertModifiable();
        super.addFormatterForFieldAnnotation(annotationFormatterFactory);
    }

    public <S, T> void addConverter(Class<S> sourceType, Class<T> targetType, Converter<? super S, ? extends T> converter) {
        this.assertModifiable();
        super.addConverter(sourceType, targetType, converter);
    }

    public void addConverter(GenericConverter converter) {
        this.assertModifiable();
        super.addConverter(converter);
    }

    public void addConverterFactory(ConverterFactory<?, ?> factory) {
        this.assertModifiable();
        super.addConverterFactory(factory);
    }

    public void removeConvertible(Class<?> sourceType, Class<?> targetType) {
        this.assertModifiable();
        super.removeConvertible(sourceType, targetType);
    }

    private void assertModifiable() {
        if (this.unmodifiable) {
            throw new UnsupportedOperationException("This ApplicationConversionService cannot be modified");
        }
    }

    public boolean isConvertViaObjectSourceType(TypeDescriptor sourceType, TypeDescriptor targetType) {
        Set pairs;
        GenericConverter converter = this.getConverter(sourceType, targetType);
        Set set = pairs = converter != null ? converter.getConvertibleTypes() : null;
        if (pairs != null) {
            for (GenericConverter.ConvertiblePair pair : pairs) {
                if (!Object.class.equals((Object)pair.getSourceType())) continue;
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ConversionService getSharedInstance() {
        ApplicationConversionService sharedInstance = ApplicationConversionService.sharedInstance;
        if (sharedInstance != null) return sharedInstance;
        Class<ApplicationConversionService> clazz = ApplicationConversionService.class;
        synchronized (ApplicationConversionService.class) {
            sharedInstance = ApplicationConversionService.sharedInstance;
            if (sharedInstance != null) return sharedInstance;
            ApplicationConversionService.sharedInstance = sharedInstance = new ApplicationConversionService(null, true);
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return sharedInstance;
        }
    }

    public static void configure(FormatterRegistry registry) {
        DefaultConversionService.addDefaultConverters((ConverterRegistry)registry);
        DefaultFormattingConversionService.addDefaultFormatters((FormatterRegistry)registry);
        ApplicationConversionService.addApplicationFormatters(registry);
        ApplicationConversionService.addApplicationConverters((ConverterRegistry)registry);
    }

    public static void addApplicationConverters(ConverterRegistry registry) {
        ApplicationConversionService.addDelimitedStringConverters(registry);
        registry.addConverter((GenericConverter)new StringToDurationConverter());
        registry.addConverter((GenericConverter)new DurationToStringConverter());
        registry.addConverter((GenericConverter)new NumberToDurationConverter());
        registry.addConverter((GenericConverter)new DurationToNumberConverter());
        registry.addConverter((GenericConverter)new StringToPeriodConverter());
        registry.addConverter((GenericConverter)new PeriodToStringConverter());
        registry.addConverter((GenericConverter)new NumberToPeriodConverter());
        registry.addConverter((GenericConverter)new StringToDataSizeConverter());
        registry.addConverter((GenericConverter)new NumberToDataSizeConverter());
        registry.addConverter((Converter)new StringToFileConverter());
        registry.addConverter((Converter)new InputStreamSourceToByteArrayConverter());
        registry.addConverterFactory((ConverterFactory)new LenientStringToEnumConverterFactory());
        registry.addConverterFactory((ConverterFactory)new LenientBooleanToEnumConverterFactory());
        if (registry instanceof ConversionService) {
            ApplicationConversionService.addApplicationConverters(registry, (ConversionService)registry);
        }
    }

    private static void addApplicationConverters(ConverterRegistry registry, ConversionService conversionService) {
        registry.addConverter((GenericConverter)new CharSequenceToObjectConverter(conversionService));
    }

    public static void addDelimitedStringConverters(ConverterRegistry registry) {
        ConversionService service = (ConversionService)registry;
        registry.addConverter((GenericConverter)new ArrayToDelimitedStringConverter(service));
        registry.addConverter((GenericConverter)new CollectionToDelimitedStringConverter(service));
        registry.addConverter((GenericConverter)new DelimitedStringToArrayConverter(service));
        registry.addConverter((GenericConverter)new DelimitedStringToCollectionConverter(service));
    }

    public static void addApplicationFormatters(FormatterRegistry registry) {
        registry.addFormatter((Formatter)new CharArrayFormatter());
        registry.addFormatter((Formatter)new InetAddressFormatter());
        registry.addFormatter((Formatter)new IsoOffsetFormatter());
    }

    public static void addBeans(FormatterRegistry registry, ListableBeanFactory beanFactory) {
        LinkedHashSet beans = new LinkedHashSet();
        beans.addAll(beanFactory.getBeansOfType(GenericConverter.class).values());
        beans.addAll(beanFactory.getBeansOfType(Converter.class).values());
        beans.addAll(beanFactory.getBeansOfType(Printer.class).values());
        beans.addAll(beanFactory.getBeansOfType(Parser.class).values());
        for (Object bean : beans) {
            if (bean instanceof GenericConverter) {
                registry.addConverter((GenericConverter)bean);
                continue;
            }
            if (bean instanceof Converter) {
                registry.addConverter((Converter)bean);
                continue;
            }
            if (bean instanceof Formatter) {
                registry.addFormatter((Formatter)bean);
                continue;
            }
            if (bean instanceof Printer) {
                registry.addPrinter((Printer)bean);
                continue;
            }
            if (!(bean instanceof Parser)) continue;
            registry.addParser((Parser)bean);
        }
    }
}

