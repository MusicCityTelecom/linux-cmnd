/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jboss.logging.Logger
 */
package org.hibernate.engine.jdbc.env.internal;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.SchemaNameResolver;
import org.jboss.logging.Logger;

public class DefaultSchemaNameResolver
implements SchemaNameResolver {
    private static final Logger log = Logger.getLogger(DefaultSchemaNameResolver.class);
    public static final DefaultSchemaNameResolver INSTANCE = new DefaultSchemaNameResolver();

    private DefaultSchemaNameResolver() {
    }

    private SchemaNameResolver determineAppropriateResolverDelegate(Connection connection) {
        try {
            Class<?> jdbcConnectionClass = connection.getClass();
            Method getSchemaMethod = jdbcConnectionClass.getMethod("getSchema", new Class[0]);
            if (getSchemaMethod != null && getSchemaMethod.getReturnType().equals(String.class)) {
                try {
                    connection.getSchema();
                    return new SchemaNameResolverJava17Delegate();
                }
                catch (AbstractMethodError e) {
                    log.debugf("Unable to use Java 1.7 Connection#getSchema", new Object[0]);
                    return SchemaNameResolverFallbackDelegate.INSTANCE;
                }
            }
            log.debugf("Unable to use Java 1.7 Connection#getSchema", new Object[0]);
            return SchemaNameResolverFallbackDelegate.INSTANCE;
        }
        catch (Exception ignore) {
            log.debugf("Unable to use Java 1.7 Connection#getSchema : An error occurred trying to resolve the connection default schema resolver: " + ignore.getMessage(), new Object[0]);
            return SchemaNameResolverFallbackDelegate.INSTANCE;
        }
    }

    @Override
    public String resolveSchemaName(Connection connection, Dialect dialect) throws SQLException {
        SchemaNameResolver delegate = this.determineAppropriateResolverDelegate(connection);
        return delegate.resolveSchemaName(connection, dialect);
    }

    public static class SchemaNameResolverFallbackDelegate
    implements SchemaNameResolver {
        public static final SchemaNameResolverFallbackDelegate INSTANCE = new SchemaNameResolverFallbackDelegate();

        /*
         * Exception decompiling
         */
        @Override
        public String resolveSchemaName(Connection connection, Dialect dialect) throws SQLException {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }
    }

    public static class SchemaNameResolverJava17Delegate
    implements SchemaNameResolver {
        @Override
        public String resolveSchemaName(Connection connection, Dialect dialect) throws SQLException {
            return connection.getSchema();
        }
    }
}

