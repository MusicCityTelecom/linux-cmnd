/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Variant;

public class VariantListBuilder
extends Variant.VariantListBuilder {
    private List<Variant> variants;
    private final List<MediaType> mediaTypes = new ArrayList<MediaType>();
    private final List<Locale> languages = new ArrayList<Locale>();
    private final List<String> encodings = new ArrayList<String>();

    @Override
    public List<Variant> build() {
        if (!(this.mediaTypes.isEmpty() && this.languages.isEmpty() && this.encodings.isEmpty())) {
            this.add();
        }
        if (this.variants == null) {
            this.variants = new ArrayList<Variant>();
        }
        return this.variants;
    }

    @Override
    public VariantListBuilder add() {
        if (this.variants == null) {
            this.variants = new ArrayList<Variant>();
        }
        this.addMediaTypes();
        this.languages.clear();
        this.encodings.clear();
        this.mediaTypes.clear();
        return this;
    }

    private void addMediaTypes() {
        if (this.mediaTypes.isEmpty()) {
            this.addLanguages(null);
        } else {
            for (MediaType mediaType : this.mediaTypes) {
                this.addLanguages(mediaType);
            }
        }
    }

    private void addLanguages(MediaType mediaType) {
        if (this.languages.isEmpty()) {
            this.addEncodings(mediaType, null);
        } else {
            for (Locale language : this.languages) {
                this.addEncodings(mediaType, language);
            }
        }
    }

    private void addEncodings(MediaType mediaType, Locale language) {
        if (this.encodings.isEmpty()) {
            this.addVariant(mediaType, language, null);
        } else {
            for (String encoding : this.encodings) {
                this.addVariant(mediaType, language, encoding);
            }
        }
    }

    private void addVariant(MediaType mediaType, Locale language, String encoding) {
        this.variants.add(new Variant(mediaType, language, encoding));
    }

    @Override
    public VariantListBuilder languages(Locale ... languages) {
        this.languages.addAll(Arrays.asList(languages));
        return this;
    }

    @Override
    public VariantListBuilder encodings(String ... encodings) {
        this.encodings.addAll(Arrays.asList(encodings));
        return this;
    }

    @Override
    public VariantListBuilder mediaTypes(MediaType ... mediaTypes) {
        this.mediaTypes.addAll(Arrays.asList(mediaTypes));
        return this;
    }
}

