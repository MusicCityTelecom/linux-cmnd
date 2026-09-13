/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.dialect.unique;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;
import org.hibernate.mapping.UniqueKey;

public interface UniqueDelegate {
    public String getColumnDefinitionUniquenessFragment(Column var1, SqlStringGenerationContext var2);

    public String getTableCreationUniqueConstraintsFragment(Table var1, SqlStringGenerationContext var2);

    public String getAlterTableToAddUniqueKeyCommand(UniqueKey var1, Metadata var2, SqlStringGenerationContext var3);

    public String getAlterTableToDropUniqueKeyCommand(UniqueKey var1, Metadata var2, SqlStringGenerationContext var3);
}

