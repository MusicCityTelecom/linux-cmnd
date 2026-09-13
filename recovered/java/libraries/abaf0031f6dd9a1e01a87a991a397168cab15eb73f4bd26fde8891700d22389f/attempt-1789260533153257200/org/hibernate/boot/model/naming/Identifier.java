/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.boot.model.naming;

import java.util.Locale;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.hibernate.dialect.Dialect;
import org.hibernate.internal.util.StringHelper;

public class Identifier
implements Comparable<Identifier> {
    private final String text;
    private final boolean isQuoted;

    public static Identifier toIdentifier(String text) {
        if (StringHelper.isEmpty(text)) {
            return null;
        }
        String trimmedText = text.trim();
        if (Identifier.isQuoted(trimmedText)) {
            String bareName = trimmedText.substring(1, trimmedText.length() - 1);
            return new Identifier(bareName, true);
        }
        return new Identifier(trimmedText, false);
    }

    public static Identifier toIdentifier(String text, boolean quote) {
        if (StringHelper.isEmpty(text)) {
            return null;
        }
        String trimmedText = text.trim();
        if (Identifier.isQuoted(trimmedText)) {
            String bareName = trimmedText.substring(1, trimmedText.length() - 1);
            return new Identifier(bareName, true);
        }
        return new Identifier(trimmedText, quote);
    }

    public static boolean isQuoted(String name) {
        return name.startsWith("`") && name.endsWith("`") || name.startsWith("[") && name.endsWith("]") || name.startsWith("\"") && name.endsWith("\"");
    }

    public Identifier(String text, boolean quoted) {
        if (StringHelper.isEmpty(text)) {
            throw new IllegalIdentifierException("Identifier text cannot be null");
        }
        if (Identifier.isQuoted(text)) {
            throw new IllegalIdentifierException("Identifier text should not contain quote markers (` or \")");
        }
        this.text = text;
        this.isQuoted = quoted;
    }

    protected Identifier(String text) {
        this.text = text;
        this.isQuoted = false;
    }

    public String getText() {
        return this.text;
    }

    public boolean isQuoted() {
        return this.isQuoted;
    }

    public String render(Dialect dialect) {
        return this.isQuoted ? String.valueOf(dialect.openQuote()) + this.getText() + dialect.closeQuote() : this.getText();
    }

    public String render() {
        return this.isQuoted ? '`' + this.getText() + '`' : this.getText();
    }

    public String getCanonicalName() {
        return this.isQuoted ? this.text : this.text.toLowerCase(Locale.ENGLISH);
    }

    public String toString() {
        return this.render();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Identifier)) {
            return false;
        }
        Identifier that = (Identifier)o;
        return this.getCanonicalName().equals(that.getCanonicalName());
    }

    public int hashCode() {
        return this.isQuoted ? this.text.hashCode() : this.text.toLowerCase(Locale.ENGLISH).hashCode();
    }

    public static boolean areEqual(Identifier id1, Identifier id2) {
        if (id1 == null) {
            return id2 == null;
        }
        return id1.equals(id2);
    }

    public static Identifier quote(Identifier identifier) {
        return identifier.isQuoted() ? identifier : Identifier.toIdentifier(identifier.getText(), true);
    }

    @Override
    public int compareTo(Identifier o) {
        return this.getCanonicalName().compareTo(o.getCanonicalName());
    }
}

