/*
 * Decompiled with CFR 0.152.
 */
package com.android.support;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 9}, bv={1, 0, 2}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J0\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005H&J \u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005H&J\u0018\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&\u00a8\u0006\u0011"}, d2={"Lcom/android/support/MigrationParserVisitor;", "", "visitClass", "", "old", "", "new", "visitGradleCoordinate", "oldGroupName", "oldArtifactName", "newGroupName", "newArtifactName", "newBaseVersion", "visitGradleCoordinateUpgrade", "groupName", "artifactName", "visitPackage", "common"})
public interface MigrationParserVisitor {
    public void visitClass(@NotNull String var1, @NotNull String var2);

    public void visitPackage(@NotNull String var1, @NotNull String var2);

    public void visitGradleCoordinate(@NotNull String var1, @NotNull String var2, @NotNull String var3, @NotNull String var4, @NotNull String var5);

    public void visitGradleCoordinateUpgrade(@NotNull String var1, @NotNull String var2, @NotNull String var3);
}

