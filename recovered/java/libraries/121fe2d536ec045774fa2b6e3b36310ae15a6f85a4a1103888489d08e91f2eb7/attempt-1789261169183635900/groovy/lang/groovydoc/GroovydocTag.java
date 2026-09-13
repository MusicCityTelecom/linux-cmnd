/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang.groovydoc;

import groovy.lang.groovydoc.Groovydoc;
import java.util.Objects;

public class GroovydocTag {
    private String name;
    private String content;
    private Groovydoc groovydoc;

    public GroovydocTag(String name, String content, Groovydoc groovydoc) {
        this.name = name;
        this.content = content;
        this.groovydoc = groovydoc;
    }

    public String getName() {
        return this.name;
    }

    public String getContent() {
        return this.content;
    }

    public Groovydoc getGroovydoc() {
        return this.groovydoc;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GroovydocTag that = (GroovydocTag)o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.content, that.content) && Objects.equals(this.groovydoc, that.groovydoc);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.content, this.groovydoc);
    }

    public String toString() {
        return this.content;
    }
}

