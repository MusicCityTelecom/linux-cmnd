/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import org.apache.groovy.lang.annotation.Incubating;
import org.codehaus.groovy.runtime.FormatHelper;

@Incubating
public class NamedValue<T>
implements Serializable {
    private static final long serialVersionUID = 8853713635573845253L;
    private final String name;
    private final T val;

    public String getName() {
        return this.name;
    }

    public T getVal() {
        return this.val;
    }

    public NamedValue(String name, T val) {
        this.name = name;
        this.val = val;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        NamedValue that = (NamedValue)o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.val, that.val);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.val);
    }

    public String toString() {
        return this.name + "=" + FormatHelper.format(this.val, true, false, -1, true);
    }

    public String toString(Map<String, Object> options) {
        return this.name + "=" + FormatHelper.toString(options, this.val);
    }
}

