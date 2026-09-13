/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.core.LogEvent
 *  org.apache.logging.log4j.core.config.Configuration
 *  org.apache.logging.log4j.core.config.plugins.Plugin
 *  org.apache.logging.log4j.core.pattern.ConverterKeys
 *  org.apache.logging.log4j.core.pattern.ExtendedThrowablePatternConverter
 *  org.apache.logging.log4j.core.pattern.ThrowablePatternConverter
 */
package org.springframework.boot.logging.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.ExtendedThrowablePatternConverter;
import org.apache.logging.log4j.core.pattern.ThrowablePatternConverter;

@Plugin(name="ExtendedWhitespaceThrowablePatternConverter", category="Converter")
@ConverterKeys(value={"xwEx", "xwThrowable", "xwException"})
public final class ExtendedWhitespaceThrowablePatternConverter
extends ThrowablePatternConverter {
    private final ExtendedThrowablePatternConverter delegate;

    private ExtendedWhitespaceThrowablePatternConverter(Configuration configuration, String[] options) {
        super("WhitespaceExtendedThrowable", "throwable", options, configuration);
        this.delegate = ExtendedThrowablePatternConverter.newInstance((Configuration)configuration, (String[])options);
    }

    public void format(LogEvent event, StringBuilder buffer) {
        if (event.getThrown() != null) {
            buffer.append(this.options.getSeparator());
            this.delegate.format(event, buffer);
            buffer.append(this.options.getSeparator());
        }
    }

    public static ExtendedWhitespaceThrowablePatternConverter newInstance(Configuration configuration, String[] options) {
        return new ExtendedWhitespaceThrowablePatternConverter(configuration, options);
    }
}

