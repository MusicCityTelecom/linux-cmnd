/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.prometheus.client.Collector$MetricFamilySamples
 *  io.prometheus.client.exporter.common.TextFormat
 *  org.springframework.util.MimeType
 *  org.springframework.util.MimeTypeUtils
 */
package org.springframework.boot.actuate.metrics.export.prometheus;

import io.prometheus.client.Collector;
import io.prometheus.client.exporter.common.TextFormat;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import org.springframework.boot.actuate.endpoint.Producible;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

public enum TextOutputFormat implements Producible<TextOutputFormat>
{
    CONTENT_TYPE_004("text/plain; version=0.0.4; charset=utf-8"){

        @Override
        void write(Writer writer, Enumeration<Collector.MetricFamilySamples> samples) throws IOException {
            TextFormat.write004((Writer)writer, samples);
        }

        @Override
        public boolean isDefault() {
            return true;
        }
    }
    ,
    CONTENT_TYPE_OPENMETRICS_100("application/openmetrics-text; version=1.0.0; charset=utf-8"){

        @Override
        void write(Writer writer, Enumeration<Collector.MetricFamilySamples> samples) throws IOException {
            TextFormat.writeOpenMetrics100((Writer)writer, samples);
        }
    };

    private final MimeType mimeType;

    private TextOutputFormat(String mimeType) {
        this.mimeType = MimeTypeUtils.parseMimeType((String)mimeType);
    }

    @Override
    public MimeType getProducedMimeType() {
        return this.mimeType;
    }

    abstract void write(Writer var1, Enumeration<Collector.MetricFamilySamples> var2) throws IOException;
}

