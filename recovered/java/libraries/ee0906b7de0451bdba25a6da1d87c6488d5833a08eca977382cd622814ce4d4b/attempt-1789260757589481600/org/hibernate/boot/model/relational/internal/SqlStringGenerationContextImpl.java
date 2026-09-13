/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.boot.model.relational.internal;

import java.util.Map;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.QualifiedSequenceName;
import org.hibernate.boot.model.relational.QualifiedTableName;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.IdentifierHelper;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.jdbc.env.spi.NameQualifierSupport;
import org.hibernate.engine.jdbc.env.spi.QualifiedObjectNameFormatter;

public class SqlStringGenerationContextImpl
implements SqlStringGenerationContext {
    private final Dialect dialect;
    private final IdentifierHelper identifierHelper;
    private final QualifiedObjectNameFormatter qualifiedObjectNameFormatter;
    private final Identifier defaultCatalog;
    private final Identifier defaultSchema;

    public static SqlStringGenerationContext fromConfigurationMap(JdbcEnvironment jdbcEnvironment, Database database, Map<String, Object> configurationMap) {
        String defaultCatalog = (String)configurationMap.get("hibernate.default_catalog");
        String defaultSchema = (String)configurationMap.get("hibernate.default_schema");
        return SqlStringGenerationContextImpl.fromExplicit(jdbcEnvironment, database, defaultCatalog, defaultSchema);
    }

    public static SqlStringGenerationContext fromExplicit(JdbcEnvironment jdbcEnvironment, Database database, String defaultCatalog, String defaultSchema) {
        Namespace.Name implicitNamespaceName = database.getPhysicalImplicitNamespaceName();
        IdentifierHelper identifierHelper = jdbcEnvironment.getIdentifierHelper();
        NameQualifierSupport nameQualifierSupport = jdbcEnvironment.getNameQualifierSupport();
        Identifier actualDefaultCatalog = null;
        if (nameQualifierSupport.supportsCatalogs() && (actualDefaultCatalog = identifierHelper.toIdentifier(defaultCatalog)) == null) {
            actualDefaultCatalog = implicitNamespaceName.getCatalog();
        }
        Identifier actualDefaultSchema = null;
        if (nameQualifierSupport.supportsSchemas()) {
            actualDefaultSchema = identifierHelper.toIdentifier(defaultSchema);
            if (defaultSchema == null) {
                actualDefaultSchema = implicitNamespaceName.getSchema();
            }
        }
        return new SqlStringGenerationContextImpl(jdbcEnvironment, actualDefaultCatalog, actualDefaultSchema);
    }

    public static SqlStringGenerationContext forTests(JdbcEnvironment jdbcEnvironment) {
        return SqlStringGenerationContextImpl.forTests(jdbcEnvironment, null, null);
    }

    public static SqlStringGenerationContext forTests(JdbcEnvironment jdbcEnvironment, String defaultCatalog, String defaultSchema) {
        IdentifierHelper identifierHelper = jdbcEnvironment.getIdentifierHelper();
        return new SqlStringGenerationContextImpl(jdbcEnvironment, identifierHelper.toIdentifier(defaultCatalog), identifierHelper.toIdentifier(defaultSchema));
    }

    private SqlStringGenerationContextImpl(JdbcEnvironment jdbcEnvironment, Identifier defaultCatalog, Identifier defaultSchema) {
        this.dialect = jdbcEnvironment.getDialect();
        this.identifierHelper = jdbcEnvironment.getIdentifierHelper();
        this.qualifiedObjectNameFormatter = jdbcEnvironment.getQualifiedObjectNameFormatter();
        this.defaultCatalog = defaultCatalog;
        this.defaultSchema = defaultSchema;
    }

    @Override
    public Dialect getDialect() {
        return this.dialect;
    }

    @Override
    public IdentifierHelper getIdentifierHelper() {
        return this.identifierHelper;
    }

    @Override
    public Identifier getDefaultCatalog() {
        return this.defaultCatalog;
    }

    @Override
    public Identifier catalogWithDefault(Identifier explicitCatalogOrNull) {
        return explicitCatalogOrNull != null ? explicitCatalogOrNull : this.defaultCatalog;
    }

    @Override
    public Identifier getDefaultSchema() {
        return this.defaultSchema;
    }

    @Override
    public Identifier schemaWithDefault(Identifier explicitSchemaOrNull) {
        return explicitSchemaOrNull != null ? explicitSchemaOrNull : this.defaultSchema;
    }

    private QualifiedTableName withDefaults(QualifiedTableName name) {
        if (name.getCatalogName() == null && this.defaultCatalog != null || name.getSchemaName() == null && this.defaultSchema != null) {
            return new QualifiedTableName(this.catalogWithDefault(name.getCatalogName()), this.schemaWithDefault(name.getSchemaName()), name.getTableName());
        }
        return name;
    }

    private QualifiedSequenceName withDefaults(QualifiedSequenceName name) {
        if (name.getCatalogName() == null && this.defaultCatalog != null || name.getSchemaName() == null && this.defaultSchema != null) {
            return new QualifiedSequenceName(this.catalogWithDefault(name.getCatalogName()), this.schemaWithDefault(name.getSchemaName()), name.getSequenceName());
        }
        return name;
    }

    private QualifiedName withDefaults(QualifiedName name) {
        if (name.getCatalogName() == null && this.defaultCatalog != null || name.getSchemaName() == null && this.defaultSchema != null) {
            return new QualifiedSequenceName(this.catalogWithDefault(name.getCatalogName()), this.schemaWithDefault(name.getSchemaName()), name.getObjectName());
        }
        return name;
    }

    @Override
    public String format(QualifiedTableName qualifiedName) {
        return this.qualifiedObjectNameFormatter.format(this.withDefaults(qualifiedName), this.dialect);
    }

    @Override
    public String format(QualifiedSequenceName qualifiedName) {
        return this.qualifiedObjectNameFormatter.format(this.withDefaults(qualifiedName), this.dialect);
    }

    @Override
    public String format(QualifiedName qualifiedName) {
        return this.qualifiedObjectNameFormatter.format(this.withDefaults(qualifiedName), this.dialect);
    }

    @Override
    public String formatWithoutCatalog(QualifiedSequenceName qualifiedName) {
        QualifiedSequenceName nameToFormat = qualifiedName.getCatalogName() != null || qualifiedName.getSchemaName() == null && this.defaultSchema != null ? new QualifiedSequenceName(null, this.schemaWithDefault(qualifiedName.getSchemaName()), qualifiedName.getSequenceName()) : qualifiedName;
        return this.qualifiedObjectNameFormatter.format(nameToFormat, this.dialect);
    }
}

