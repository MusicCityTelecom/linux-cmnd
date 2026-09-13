/*
 * Decompiled with CFR 0.152.
 */
package com.android.support;

import com.android.support.InvalidDataException;
import com.android.support.MigrationParserVisitor;
import com.android.utils.XmlUtils;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Metadata(mv={1, 1, 9}, bv={1, 0, 2}, k=2, d1={"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"ATTR_ARTIFACT_NAME", "", "ATTR_GROUP_NAME", "ATTR_NEW_ARTIFACT_NAME", "ATTR_NEW_BASE_VERSION_NAME", "ATTR_NEW_GROUP_NAME", "ATTR_NEW_NAME", "ATTR_OLD_ARTIFACT_NAME", "ATTR_OLD_GROUP_NAME", "ATTR_OLD_NAME", "ATTR_TYPE", "MIGRATE_DEPENDENCY_NAME", "MIGRATE_ENTRY_NAME", "ROOT_ELEMENT", "TYPE_CLASS", "TYPE_PACKAGE", "UPGRADE_DEPENDENCY_NAME", "parseMigrationFile", "", "visitor", "Lcom/android/support/MigrationParserVisitor;", "common"})
public final class AndroidxMigrationParserKt {
    private static final String ROOT_ELEMENT = "migration-map";
    private static final String MIGRATE_ENTRY_NAME = "migrate";
    private static final String ATTR_OLD_NAME = "old-name";
    private static final String ATTR_NEW_NAME = "new-name";
    private static final String ATTR_TYPE = "type";
    private static final String TYPE_CLASS = "CLASS";
    private static final String TYPE_PACKAGE = "PACKAGE";
    private static final String MIGRATE_DEPENDENCY_NAME = "migrate-dependency";
    private static final String ATTR_OLD_GROUP_NAME = "old-group-name";
    private static final String ATTR_OLD_ARTIFACT_NAME = "old-artifact-name";
    private static final String ATTR_NEW_GROUP_NAME = "new-group-name";
    private static final String ATTR_NEW_ARTIFACT_NAME = "new-artifact-name";
    private static final String ATTR_NEW_BASE_VERSION_NAME = "base-version";
    private static final String UPGRADE_DEPENDENCY_NAME = "upgrade-dependency";
    private static final String ATTR_GROUP_NAME = "group-name";
    private static final String ATTR_ARTIFACT_NAME = "artifact-name";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final void parseMigrationFile(@NotNull MigrationParserVisitor visitor) {
        Intrinsics.checkParameterIsNotNull(visitor, "visitor");
        InputStream stream = visitor.getClass().getClassLoader().getResourceAsStream("migrateToAndroidx/migration.xml");
        Closeable closeable = stream;
        Throwable throwable = null;
        try {
            Element root;
            Document document;
            InputStream it = (InputStream)closeable;
            Document document2 = document = XmlUtils.parseDocument(new InputStreamReader(stream), false);
            Intrinsics.checkExpressionValueIsNotNull(document2, "document");
            Element element = root = document2.getDocumentElement();
            Intrinsics.checkExpressionValueIsNotNull(element, "root");
            if (Intrinsics.areEqual(ROOT_ELEMENT, element.getNodeName()) ^ true) {
                throw (Throwable)new InvalidDataException("Migration file does not start with <migration-map>");
            }
            Iterable<Element> iterable = XmlUtils.getSubTags(root);
            Intrinsics.checkExpressionValueIsNotNull(iterable, "XmlUtils.getSubTags(root)");
            Iterable<Element> $receiver$iv = iterable;
            Iterator<Element> iterator2 = $receiver$iv.iterator();
            while (iterator2.hasNext()) {
                Element node;
                block12: {
                    String type;
                    String newName;
                    String oldName;
                    block13: {
                        String string;
                        Element element$iv;
                        Element element2 = node = (element$iv = iterator2.next());
                        Intrinsics.checkExpressionValueIsNotNull(element2, "node");
                        if (!Intrinsics.areEqual(element2.getNodeName(), MIGRATE_ENTRY_NAME)) break block12;
                        oldName = node.getAttribute(ATTR_OLD_NAME);
                        newName = node.getAttribute(ATTR_NEW_NAME);
                        String string2 = string = (type = node.getAttribute(ATTR_TYPE));
                        if (string2 == null) throw (Throwable)new InvalidDataException("Invalid type " + type);
                        switch (string2.hashCode()) {
                            case -89079770: {
                                if (!string.equals(TYPE_PACKAGE)) throw (Throwable)new InvalidDataException("Invalid type " + type);
                                break;
                            }
                            case 64205144: {
                                if (!string.equals(TYPE_CLASS)) throw (Throwable)new InvalidDataException("Invalid type " + type);
                                break block13;
                            }
                        }
                        String string3 = oldName;
                        Intrinsics.checkExpressionValueIsNotNull(string3, "oldName");
                        String string4 = newName;
                        Intrinsics.checkExpressionValueIsNotNull(string4, "newName");
                        visitor.visitPackage(string3, string4);
                        continue;
                    }
                    String string = oldName;
                    Intrinsics.checkExpressionValueIsNotNull(string, "oldName");
                    String string5 = newName;
                    Intrinsics.checkExpressionValueIsNotNull(string5, "newName");
                    visitor.visitClass(string, string5);
                    continue;
                    throw (Throwable)new InvalidDataException("Invalid type " + type);
                }
                if (Intrinsics.areEqual(node.getNodeName(), MIGRATE_DEPENDENCY_NAME)) {
                    String oldGroupName = node.getAttribute(ATTR_OLD_GROUP_NAME);
                    String oldArtifactName = node.getAttribute(ATTR_OLD_ARTIFACT_NAME);
                    String newGroupName = node.getAttribute(ATTR_NEW_GROUP_NAME);
                    String newArtifactName = node.getAttribute(ATTR_NEW_ARTIFACT_NAME);
                    String newBaseVersion = node.getAttribute(ATTR_NEW_BASE_VERSION_NAME);
                    String string = oldGroupName;
                    Intrinsics.checkExpressionValueIsNotNull(string, "oldGroupName");
                    String string6 = oldArtifactName;
                    Intrinsics.checkExpressionValueIsNotNull(string6, "oldArtifactName");
                    String string7 = newGroupName;
                    Intrinsics.checkExpressionValueIsNotNull(string7, "newGroupName");
                    String string8 = newArtifactName;
                    Intrinsics.checkExpressionValueIsNotNull(string8, "newArtifactName");
                    String string9 = newBaseVersion;
                    Intrinsics.checkExpressionValueIsNotNull(string9, "newBaseVersion");
                    visitor.visitGradleCoordinate(string, string6, string7, string8, string9);
                    continue;
                }
                if (!Intrinsics.areEqual(node.getNodeName(), UPGRADE_DEPENDENCY_NAME)) continue;
                String groupName = node.getAttribute(ATTR_GROUP_NAME);
                String artifactName = node.getAttribute(ATTR_ARTIFACT_NAME);
                String baseVersion = node.getAttribute(ATTR_NEW_BASE_VERSION_NAME);
                String string = groupName;
                Intrinsics.checkExpressionValueIsNotNull(string, "groupName");
                String string10 = artifactName;
                Intrinsics.checkExpressionValueIsNotNull(string10, "artifactName");
                String string11 = baseVersion;
                Intrinsics.checkExpressionValueIsNotNull(string11, "baseVersion");
                visitor.visitGradleCoordinateUpgrade(string, string10, string11);
            }
            Unit unit = Unit.INSTANCE;
            return;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally(closeable, throwable);
        }
    }
}

