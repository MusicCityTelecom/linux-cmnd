/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs.client;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Locale;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Variant;

public final class Entity<T> {
    private static final Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];
    private final T entity;
    private final Variant variant;
    private final Annotation[] annotations;

    public static <T> Entity<T> entity(T entity, MediaType mediaType) {
        return new Entity<T>(entity, mediaType);
    }

    public static <T> Entity<T> entity(T entity, MediaType mediaType, Annotation[] annotations) {
        return new Entity<T>(entity, mediaType, annotations);
    }

    public static <T> Entity<T> entity(T entity, String mediaType) {
        return new Entity<T>(entity, MediaType.valueOf(mediaType));
    }

    public static <T> Entity<T> entity(T entity, Variant variant) {
        return new Entity<T>(entity, variant);
    }

    public static <T> Entity<T> entity(T entity, Variant variant, Annotation[] annotations) {
        return new Entity<T>(entity, variant, annotations);
    }

    public static <T> Entity<T> text(T entity) {
        return new Entity<T>(entity, MediaType.TEXT_PLAIN_TYPE);
    }

    public static <T> Entity<T> xml(T entity) {
        return new Entity<T>(entity, MediaType.APPLICATION_XML_TYPE);
    }

    public static <T> Entity<T> json(T entity) {
        return new Entity<T>(entity, MediaType.APPLICATION_JSON_TYPE);
    }

    public static <T> Entity<T> html(T entity) {
        return new Entity<T>(entity, MediaType.TEXT_HTML_TYPE);
    }

    public static <T> Entity<T> xhtml(T entity) {
        return new Entity<T>(entity, MediaType.APPLICATION_XHTML_XML_TYPE);
    }

    public static Entity<Form> form(Form form) {
        return new Entity<Form>(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
    }

    public static Entity<Form> form(MultivaluedMap<String, String> formData) {
        return new Entity<Form>(new Form(formData), MediaType.APPLICATION_FORM_URLENCODED_TYPE);
    }

    private Entity(T entity, MediaType mediaType) {
        this(entity, new Variant(mediaType, (Locale)null, null), null);
    }

    private Entity(T entity, Variant variant) {
        this(entity, variant, null);
    }

    private Entity(T entity, MediaType mediaType, Annotation[] annotations) {
        this(entity, new Variant(mediaType, (Locale)null, null), annotations);
    }

    private Entity(T entity, Variant variant, Annotation[] annotations) {
        this.entity = entity;
        this.variant = variant;
        this.annotations = annotations == null ? EMPTY_ANNOTATIONS : annotations;
    }

    public Variant getVariant() {
        return this.variant;
    }

    public MediaType getMediaType() {
        return this.variant.getMediaType();
    }

    public String getEncoding() {
        return this.variant.getEncoding();
    }

    public Locale getLanguage() {
        return this.variant.getLanguage();
    }

    public T getEntity() {
        return this.entity;
    }

    public Annotation[] getAnnotations() {
        return this.annotations;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entity)) {
            return false;
        }
        Entity entity1 = (Entity)o;
        if (!Arrays.equals(this.annotations, entity1.annotations)) {
            return false;
        }
        if (this.entity != null ? !this.entity.equals(entity1.entity) : entity1.entity != null) {
            return false;
        }
        return !(this.variant != null ? !this.variant.equals(entity1.variant) : entity1.variant != null);
    }

    public int hashCode() {
        int result = this.entity != null ? this.entity.hashCode() : 0;
        result = 31 * result + (this.variant != null ? this.variant.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(this.annotations);
        return result;
    }

    public String toString() {
        return "Entity{entity=" + this.entity + ", variant=" + this.variant + ", annotations=" + Arrays.toString(this.annotations) + '}';
    }
}

