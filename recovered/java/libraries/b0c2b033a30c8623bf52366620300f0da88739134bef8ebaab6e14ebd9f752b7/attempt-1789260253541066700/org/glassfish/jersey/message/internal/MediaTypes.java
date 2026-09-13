/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.message.internal.AcceptableMediaType;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.QualitySourceMediaType;

public final class MediaTypes {
    public static final MediaType WADL_TYPE = MediaType.valueOf("application/vnd.sun.wadl+xml");
    public static final Comparator<MediaType> PARTIAL_ORDER_COMPARATOR = new Comparator<MediaType>(){

        private int rank(MediaType type) {
            return (type.isWildcardType() ? 1 : 0) << 1 | (type.isWildcardSubtype() ? 1 : 0);
        }

        @Override
        public int compare(MediaType typeA, MediaType typeB) {
            return this.rank(typeA) - this.rank(typeB);
        }
    };
    public static final Comparator<List<? extends MediaType>> MEDIA_TYPE_LIST_COMPARATOR = new Comparator<List<? extends MediaType>>(){

        @Override
        public int compare(List<? extends MediaType> o1, List<? extends MediaType> o2) {
            return PARTIAL_ORDER_COMPARATOR.compare(this.getLeastSpecific(o1), this.getLeastSpecific(o2));
        }

        private MediaType getLeastSpecific(List<? extends MediaType> l) {
            return l.get(l.size() - 1);
        }
    };
    public static final List<MediaType> WILDCARD_TYPE_SINGLETON_LIST = Collections.singletonList(MediaType.WILDCARD_TYPE);
    public static final AcceptableMediaType WILDCARD_ACCEPTABLE_TYPE = new AcceptableMediaType("*", "*");
    public static final QualitySourceMediaType WILDCARD_QS_TYPE = new QualitySourceMediaType("*", "*");
    public static final List<MediaType> WILDCARD_QS_TYPE_SINGLETON_LIST = Collections.singletonList(WILDCARD_QS_TYPE);
    private static final Map<String, MediaType> WILDCARD_SUBTYPE_CACHE = new HashMap<String, MediaType>(){
        private static final long serialVersionUID = 3109256773218160485L;
        {
            this.put("application", new MediaType("application", "*"));
            this.put("multipart", new MediaType("multipart", "*"));
            this.put("text", new MediaType("text", "*"));
        }
    };
    private static final Predicate<String> QUALITY_PARAM_FILTERING_PREDICATE = input -> !"qs".equals(input) && !"q".equals(input);

    private MediaTypes() {
        throw new AssertionError((Object)"Instantiation not allowed.");
    }

    public static boolean typeEqual(MediaType m1, MediaType m2) {
        if (m1 == null || m2 == null) {
            return false;
        }
        return m1.getSubtype().equalsIgnoreCase(m2.getSubtype()) && m1.getType().equalsIgnoreCase(m2.getType());
    }

    public static boolean intersect(List<? extends MediaType> ml1, List<? extends MediaType> ml2) {
        for (MediaType mediaType : ml1) {
            for (MediaType mediaType2 : ml2) {
                if (!MediaTypes.typeEqual(mediaType, mediaType2)) continue;
                return true;
            }
        }
        return false;
    }

    public static MediaType mostSpecific(MediaType m1, MediaType m2) {
        if (m1.isWildcardType() && !m2.isWildcardType()) {
            return m2;
        }
        if (m1.isWildcardSubtype() && !m2.isWildcardSubtype()) {
            return m2;
        }
        if (m2.getParameters().size() > m1.getParameters().size()) {
            return m2;
        }
        return m1;
    }

    public static List<MediaType> createFrom(Consumes annotation) {
        if (annotation == null) {
            return WILDCARD_TYPE_SINGLETON_LIST;
        }
        return MediaTypes.createFrom(annotation.value());
    }

    public static List<MediaType> createFrom(Produces annotation) {
        if (annotation == null) {
            return WILDCARD_TYPE_SINGLETON_LIST;
        }
        return MediaTypes.createFrom(annotation.value());
    }

    public static List<MediaType> createFrom(String[] mediaTypes) {
        ArrayList<MediaType> result = new ArrayList<MediaType>();
        try {
            for (String mediaType : mediaTypes) {
                HttpHeaderReader.readMediaTypes(result, mediaType);
            }
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
        Collections.sort(result, PARTIAL_ORDER_COMPARATOR);
        return Collections.unmodifiableList(result);
    }

    public static List<MediaType> createQualitySourceMediaTypes(Produces mime) {
        if (mime == null || mime.value().length == 0) {
            return WILDCARD_QS_TYPE_SINGLETON_LIST;
        }
        return new ArrayList<MediaType>(MediaTypes.createQualitySourceMediaTypes(mime.value()));
    }

    public static List<QualitySourceMediaType> createQualitySourceMediaTypes(String[] mediaTypes) {
        try {
            return HttpHeaderReader.readQualitySourceMediaType(mediaTypes);
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static int getQuality(MediaType mt) {
        String qParam = mt.getParameters().get("q");
        return MediaTypes.readQualityFactor(qParam);
    }

    private static int readQualityFactor(String qParam) throws IllegalArgumentException {
        if (qParam == null) {
            return 1000;
        }
        try {
            return HttpHeaderReader.readQualityFactor(qParam);
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static MediaType stripQualityParams(MediaType mediaType) {
        Map<String, String> oldParameters = mediaType.getParameters();
        if (oldParameters.isEmpty() || !oldParameters.containsKey("qs") && !oldParameters.containsKey("q")) {
            return mediaType;
        }
        return new MediaType(mediaType.getType(), mediaType.getSubtype(), oldParameters.entrySet().stream().filter(entry -> QUALITY_PARAM_FILTERING_PREDICATE.test((String)entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public static MediaType getTypeWildCart(MediaType mediaType) {
        MediaType mt = WILDCARD_SUBTYPE_CACHE.get(mediaType.getType());
        if (mt == null) {
            mt = new MediaType(mediaType.getType(), "*");
        }
        return mt;
    }

    public static String convertToString(Iterable<MediaType> mediaTypes) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (MediaType mediaType : mediaTypes) {
            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append("\"").append(mediaType.toString()).append("\"");
        }
        return sb.toString();
    }

    public static boolean isWildcard(MediaType mediaType) {
        return mediaType.isWildcardType() || mediaType.isWildcardSubtype();
    }
}

