/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Variant;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.message.internal.AcceptableLanguageTag;
import org.glassfish.jersey.message.internal.AcceptableMediaType;
import org.glassfish.jersey.message.internal.AcceptableToken;
import org.glassfish.jersey.message.internal.InboundMessageContext;
import org.glassfish.jersey.message.internal.Qualified;
import org.glassfish.jersey.message.internal.QualitySourceMediaType;

public final class VariantSelector {
    private static final DimensionChecker<AcceptableMediaType, MediaType> MEDIA_TYPE_DC = new DimensionChecker<AcceptableMediaType, MediaType>(){

        @Override
        public MediaType getDimension(VariantHolder v) {
            return v.v.getMediaType();
        }

        @Override
        public boolean isCompatible(AcceptableMediaType t, MediaType u) {
            return t.isCompatible(u);
        }

        @Override
        public int getQualitySource(VariantHolder v, MediaType u) {
            return v.mediaTypeQs;
        }

        @Override
        public String getVaryHeaderValue() {
            return "Accept";
        }
    };
    private static final DimensionChecker<AcceptableLanguageTag, Locale> LANGUAGE_TAG_DC = new DimensionChecker<AcceptableLanguageTag, Locale>(){

        @Override
        public Locale getDimension(VariantHolder v) {
            return v.v.getLanguage();
        }

        @Override
        public boolean isCompatible(AcceptableLanguageTag t, Locale u) {
            return t.isCompatible(u);
        }

        @Override
        public int getQualitySource(VariantHolder qsv, Locale u) {
            return 0;
        }

        @Override
        public String getVaryHeaderValue() {
            return "Accept-Language";
        }
    };
    private static final DimensionChecker<AcceptableToken, String> CHARSET_DC = new DimensionChecker<AcceptableToken, String>(){

        @Override
        public String getDimension(VariantHolder v) {
            MediaType m = v.v.getMediaType();
            return m != null ? m.getParameters().get("charset") : null;
        }

        @Override
        public boolean isCompatible(AcceptableToken t, String u) {
            return t.isCompatible(u);
        }

        @Override
        public int getQualitySource(VariantHolder qsv, String u) {
            return 0;
        }

        @Override
        public String getVaryHeaderValue() {
            return "Accept-Charset";
        }
    };
    private static final DimensionChecker<AcceptableToken, String> ENCODING_DC = new DimensionChecker<AcceptableToken, String>(){

        @Override
        public String getDimension(VariantHolder v) {
            return v.v.getEncoding();
        }

        @Override
        public boolean isCompatible(AcceptableToken t, String u) {
            return t.isCompatible(u);
        }

        @Override
        public int getQualitySource(VariantHolder qsv, String u) {
            return 0;
        }

        @Override
        public String getVaryHeaderValue() {
            return "Accept-Encoding";
        }
    };

    private VariantSelector() {
    }

    private static <T extends Qualified, U> LinkedList<VariantHolder> selectVariants(List<VariantHolder> variantHolders, List<T> acceptableValues, DimensionChecker<T, U> dimensionChecker, Set<String> vary) {
        int cq = 0;
        int cqs = 0;
        LinkedList<VariantHolder> selected = new LinkedList<VariantHolder>();
        for (Qualified a : acceptableValues) {
            int q = a.getQuality();
            Iterator<VariantHolder> iv = variantHolders.iterator();
            while (iv.hasNext()) {
                VariantHolder v = iv.next();
                U d = dimensionChecker.getDimension(v);
                if (d == null) continue;
                vary.add(dimensionChecker.getVaryHeaderValue());
                int qs = dimensionChecker.getQualitySource(v, d);
                if (qs < cqs || !dimensionChecker.isCompatible(a, d)) continue;
                if (qs > cqs) {
                    cqs = qs;
                    cq = q;
                    selected.clear();
                    selected.add(v);
                } else if (q > cq) {
                    cq = q;
                    selected.addFirst(v);
                } else if (q == cq) {
                    selected.add(v);
                }
                iv.remove();
            }
        }
        for (VariantHolder v : variantHolders) {
            if (dimensionChecker.getDimension(v) != null) continue;
            selected.add(v);
        }
        return selected;
    }

    private static LinkedList<VariantHolder> getVariantHolderList(List<Variant> variants) {
        LinkedList<VariantHolder> l = new LinkedList<VariantHolder>();
        for (Variant v : variants) {
            MediaType mt = v.getMediaType();
            if (mt != null) {
                if (mt instanceof QualitySourceMediaType || mt.getParameters().containsKey("qs")) {
                    int qs = QualitySourceMediaType.getQualitySource(mt);
                    l.add(new VariantHolder(v, qs));
                    continue;
                }
                l.add(new VariantHolder(v));
                continue;
            }
            l.add(new VariantHolder(v));
        }
        return l;
    }

    public static Variant selectVariant(InboundMessageContext context, List<Variant> variants, Ref<String> varyHeaderValue) {
        List<Variant> selectedVariants = VariantSelector.selectVariants(context, variants, varyHeaderValue);
        return selectedVariants.isEmpty() ? null : selectedVariants.get(0);
    }

    public static List<Variant> selectVariants(InboundMessageContext context, List<Variant> variants, Ref<String> varyHeaderValue) {
        LinkedList<VariantHolder> vhs = VariantSelector.getVariantHolderList(variants);
        HashSet<String> vary = new HashSet<String>();
        vhs = VariantSelector.selectVariants(vhs, context.getQualifiedAcceptableMediaTypes(), MEDIA_TYPE_DC, vary);
        vhs = VariantSelector.selectVariants(vhs, context.getQualifiedAcceptableLanguages(), LANGUAGE_TAG_DC, vary);
        vhs = VariantSelector.selectVariants(vhs, context.getQualifiedAcceptCharset(), CHARSET_DC, vary);
        if ((vhs = VariantSelector.selectVariants(vhs, context.getQualifiedAcceptEncoding(), ENCODING_DC, vary)).isEmpty()) {
            return Collections.emptyList();
        }
        StringBuilder varyHeader = new StringBuilder();
        for (String v : vary) {
            if (varyHeader.length() > 0) {
                varyHeader.append(',');
            }
            varyHeader.append(v);
        }
        String varyValue = varyHeader.toString();
        if (!varyValue.isEmpty()) {
            varyHeaderValue.set(varyValue);
        }
        return vhs.stream().map(variantHolder -> ((VariantHolder)variantHolder).v).collect(Collectors.toList());
    }

    private static class VariantHolder {
        private final Variant v;
        private final int mediaTypeQs;

        VariantHolder(Variant v) {
            this(v, 1000);
        }

        VariantHolder(Variant v, int mediaTypeQs) {
            this.v = v;
            this.mediaTypeQs = mediaTypeQs;
        }
    }

    private static interface DimensionChecker<T, U> {
        public U getDimension(VariantHolder var1);

        public int getQualitySource(VariantHolder var1, U var2);

        public boolean isCompatible(T var1, U var2);

        public String getVaryHeaderValue();
    }
}

