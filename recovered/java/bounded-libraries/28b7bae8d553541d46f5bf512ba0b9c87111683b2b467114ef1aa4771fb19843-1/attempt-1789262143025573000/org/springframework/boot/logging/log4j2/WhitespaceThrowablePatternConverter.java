/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.core.LogEvent
 *  org.apache.logging.log4j.core.config.Configuration
 *  org.apache.logging.log4j.core.config.plugins.Plugin
 *  org.apache.logging.log4j.core.pattern.ConverterKeys
 *  org.apache.logging.log4j.core.pattern.ThrowablePatternConverter
 */
package org.springframework.boot.logging.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.ThrowablePatternConverter;

@Plugin(name="WhitespaceThrowablePatternConverter", category="Converter")
@ConverterKeys(value={"wEx", "wThrowable", "wException"})
public final class WhitespaceThrowablePatternConverter
extends ThrowablePatternConverter {
    private WhitespaceThrowablePatternConverter(Configuration configuration, String[] options) {
        super("WhitespaceThrowable", "throwable", options, configuration);
    }

    public void format(LogEvent event, StringBuilder buffer) {
        if (event.getThrown() != null) {
            buffer.append(this.options.getSeparator());
            super.format(event, buffer);
            buffer.append(this.options.getSeparator());
        }
    }

    public static WhitespaceThrowablePatternConverter newInstance(Configuration configuration, String[] options) {
        return new WhitespaceThrowablePatternConverter(configuration, options);
    }
}

