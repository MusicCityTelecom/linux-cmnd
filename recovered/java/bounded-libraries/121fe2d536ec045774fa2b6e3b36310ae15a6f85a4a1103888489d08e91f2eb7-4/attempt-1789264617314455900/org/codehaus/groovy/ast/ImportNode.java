/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.util.Objects;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;

public class ImportNode
extends AnnotatedNode {
    private ClassNode type;
    private final String alias;
    private final String fieldName;
    private final String packageName;
    private final boolean isStar;
    private final boolean isStatic;
    private transient int hashCode;

    public ImportNode(ClassNode type, String alias) {
        this.type = Objects.requireNonNull(type);
        this.alias = alias;
        this.isStar = false;
        this.isStatic = false;
        this.packageName = null;
        this.fieldName = null;
    }

    public ImportNode(String packageName) {
        this.type = null;
        this.alias = null;
        this.isStar = true;
        this.isStatic = false;
        this.packageName = Objects.requireNonNull(packageName);
        this.fieldName = null;
    }

    public ImportNode(ClassNode type) {
        this.type = Objects.requireNonNull(type);
        this.alias = null;
        this.isStar = true;
        this.isStatic = true;
        this.packageName = null;
        this.fieldName = null;
    }

    public ImportNode(ClassNode type, String fieldName, String alias) {
        this.type = Objects.requireNonNull(type);
        this.alias = alias;
        this.isStar = false;
        this.isStatic = true;
        this.packageName = null;
        this.fieldName = Objects.requireNonNull(fieldName);
    }

    @Override
    public String getText() {
        String typeName = this.getClassName();
        if (this.isStar && !this.isStatic) {
            return "import " + this.packageName + "*";
        }
        if (this.isStar) {
            return "import static " + typeName + ".*";
        }
        if (this.isStatic) {
            if (this.alias != null && !this.alias.isEmpty() && !this.alias.equals(this.fieldName)) {
                return "import static " + typeName + "." + this.fieldName + " as " + this.alias;
            }
            return "import static " + typeName + "." + this.fieldName;
        }
        if (this.alias == null || this.alias.isEmpty()) {
            return "import " + typeName;
        }
        return "import " + typeName + " as " + this.alias;
    }

    public boolean isStar() {
        return this.isStar;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getClassName() {
        return this.type == null ? null : this.type.getName();
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public ClassNode getType() {
        return this.type;
    }

    public void setType(ClassNode type) {
        this.type = Objects.requireNonNull(type);
        this.hashCode = 0;
    }

    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof ImportNode)) {
            return false;
        }
        ImportNode node = (ImportNode)that;
        if (!Objects.equals(this.type, node.type)) {
            return false;
        }
        if (!Objects.equals(this.alias, node.alias)) {
            return false;
        }
        if (!Objects.equals(this.fieldName, node.fieldName)) {
            return false;
        }
        if (!Objects.equals(this.packageName, node.packageName)) {
            return false;
        }
        return this.isStar == node.isStar && this.isStatic == node.isStatic;
    }

    public int hashCode() {
        int result = this.hashCode;
        if (result == 0) {
            this.hashCode = Objects.hash(this.type, this.alias, this.fieldName, this.packageName, this.isStar, this.isStatic);
        }
        return result;
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
    }
}

