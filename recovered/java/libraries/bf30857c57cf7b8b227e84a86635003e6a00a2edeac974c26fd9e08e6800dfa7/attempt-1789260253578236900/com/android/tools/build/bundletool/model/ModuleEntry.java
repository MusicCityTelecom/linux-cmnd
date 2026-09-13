/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_ModuleEntry;
import com.android.tools.build.bundletool.model.InputStreamSupplier;
import com.android.tools.build.bundletool.model.ZipPath;
import com.google.auto.value.AutoValue;
import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.MustBeClosed;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Objects;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class ModuleEntry {
    @MustBeClosed
    public final InputStream getContent() {
        try {
            return this.getContentSupplier().get();
        }
        catch (IOException e2) {
            throw new UncheckedIOException(e2);
        }
    }

    public abstract ZipPath getPath();

    public abstract boolean getShouldCompress();

    public abstract InputStreamSupplier getContentSupplier();

    /*
     * Exception decompiling
     */
    public final boolean equals(Object obj2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public final int hashCode() {
        return Objects.hash(this.getPath(), this.getShouldCompress());
    }

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_ModuleEntry.Builder().setShouldCompress(true);
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setPath(ZipPath var1);

        public abstract Builder setShouldCompress(boolean var1);

        public abstract Builder setContentSupplier(InputStreamSupplier var1);

        public abstract ModuleEntry build();
    }
}

