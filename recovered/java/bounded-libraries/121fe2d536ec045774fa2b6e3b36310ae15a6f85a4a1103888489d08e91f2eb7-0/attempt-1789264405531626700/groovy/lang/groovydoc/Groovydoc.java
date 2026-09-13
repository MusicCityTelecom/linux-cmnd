/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang.groovydoc;

import groovy.lang.groovydoc.GroovydocHolder;
import groovy.lang.groovydoc.GroovydocTag;
import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Groovydoc {
    private final String content;
    private List<GroovydocTag> tagList = Collections.emptyList();
    private GroovydocHolder holder;
    public static final Groovydoc EMPTY_GROOVYDOC = new Groovydoc(""){

        @Override
        public List<GroovydocTag> getTagList() {
            return Collections.emptyList();
        }
    };

    private Groovydoc(String content) {
        this.content = content;
    }

    public Groovydoc(String content, GroovydocHolder groovydocHolder) {
        this(content);
        this.holder = groovydocHolder;
    }

    public Groovydoc(String content, final AnnotatedElement annotatedElement) {
        this(content);
        this.holder = new GroovydocHolder<AnnotatedElement>(){

            @Override
            public Groovydoc getGroovydoc() {
                return Groovydoc.this;
            }

            @Override
            public AnnotatedElement getInstance() {
                return annotatedElement;
            }
        };
    }

    public boolean isPresent() {
        return EMPTY_GROOVYDOC != this;
    }

    public String getContent() {
        return this.content;
    }

    public List<GroovydocTag> getTagList() {
        throw new UnsupportedOperationException("[TODO]parsing tags will be a new features of the next releases");
    }

    public GroovydocHolder getHolder() {
        return this.holder;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Groovydoc groovydoc = (Groovydoc)o;
        return Objects.equals(this.content, groovydoc.content) && Objects.equals(this.holder, groovydoc.holder);
    }

    public int hashCode() {
        return Objects.hash(this.content, this.holder);
    }

    public String toString() {
        return this.content;
    }
}

