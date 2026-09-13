/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.message.internal.Qualified;
import org.glassfish.jersey.message.internal.Quality;

public class QualitySourceMediaType
extends MediaType
implements Qualified {
    public static final Comparator<QualitySourceMediaType> COMPARATOR = new Comparator<QualitySourceMediaType>(){

        @Override
        public int compare(QualitySourceMediaType o1, QualitySourceMediaType o2) {
            int i = Quality.QUALIFIED_COMPARATOR.compare(o1, o2);
            if (i != 0) {
                return i;
            }
            return MediaTypes.PARTIAL_ORDER_COMPARATOR.compare(o1, o2);
        }
    };
    private final int qs;

    public QualitySourceMediaType(String type, String subtype) {
        super(type, subtype);
        this.qs = 1000;
    }

    public QualitySourceMediaType(String type, String subtype, int quality, Map<String, String> parameters) {
        super(type, subtype, Quality.enhanceWithQualityParameter(parameters, "qs", quality));
        this.qs = quality;
    }

    private QualitySourceMediaType(String type, String subtype, Map<String, String> parameters, int quality) {
        super(type, subtype, parameters);
        this.qs = quality;
    }

    @Override
    public int getQuality() {
        return this.qs;
    }

    public static QualitySourceMediaType valueOf(HttpHeaderReader reader) throws ParseException {
        reader.hasNext();
        String type = reader.nextToken().toString();
        reader.nextSeparator('/');
        String subType = reader.nextToken().toString();
        int qs = 1000;
        Map<String, String> parameters = null;
        if (reader.hasNext() && (parameters = HttpHeaderReader.readParameters(reader)) != null) {
            qs = QualitySourceMediaType.getQs(parameters.get("qs"));
        }
        return new QualitySourceMediaType(type, subType, parameters, qs);
    }

    public static int getQualitySource(MediaType mediaType) throws IllegalArgumentException {
        if (mediaType instanceof QualitySourceMediaType) {
            return ((QualitySourceMediaType)mediaType).getQuality();
        }
        return QualitySourceMediaType.getQs(mediaType);
    }

    private static int getQs(MediaType mt) throws IllegalArgumentException {
        try {
            return QualitySourceMediaType.getQs(mt.getParameters().get("qs"));
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private static int getQs(String v) throws ParseException {
        if (v == null) {
            return 1000;
        }
        return HttpHeaderReader.readQualityFactor(v);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (obj instanceof QualitySourceMediaType) {
            QualitySourceMediaType other = (QualitySourceMediaType)obj;
            return this.qs == other.qs;
        }
        return this.qs == 1000;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        return this.qs == 1000 ? hash : 47 * hash + this.qs;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + ", qs=" + this.qs + "}";
    }
}

