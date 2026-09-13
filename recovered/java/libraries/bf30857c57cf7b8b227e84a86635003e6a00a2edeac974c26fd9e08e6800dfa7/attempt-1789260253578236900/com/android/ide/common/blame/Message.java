/*
 * Decompiled with CFR 0.152.
 */
package com.android.ide.common.blame;

import com.android.ide.common.blame.SourceFile;
import com.android.ide.common.blame.SourceFilePosition;
import com.android.ide.common.blame.SourcePosition;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 9}, bv={1, 0, 2}, k=1, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0087\b\u0018\u00002\u00020\u0001:\u00013B3\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\t\"\u00020\u0007\u00a2\u0006\u0002\u0010\nBE\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\t\"\u00020\u0007\u00a2\u0006\u0002\u0010\rB7\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f\u00a2\u0006\u0002\u0010\u0010B;\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f\u00a2\u0006\u0002\u0010\u0012B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0014J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013H\u00c6\u0003J\t\u0010+\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003JC\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00132\b\b\u0002\u0010\u000b\u001a\u00020\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u00020\u0016H\u00d6\u0001J\t\u00102\u001a\u00020\u0005H\u00d6\u0001R\u001a\u0010\u0015\u001a\u00020\u00168FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u00168FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u001e\u0010\u0018\u001a\u0004\b\u001f\u0010\u001aR\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0013\u0010$\u001a\u0004\u0018\u00010\u00058F\u00a2\u0006\u0006\u001a\u0004\b%\u0010!R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010!R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b'\u0010!\u00a8\u00064"}, d2={"Lcom/android/ide/common/blame/Message;", "", "kind", "Lcom/android/ide/common/blame/Message$Kind;", "text", "", "sourceFilePosition", "Lcom/android/ide/common/blame/SourceFilePosition;", "sourceFilePositions", "", "(Lcom/android/ide/common/blame/Message$Kind;Ljava/lang/String;Lcom/android/ide/common/blame/SourceFilePosition;[Lcom/android/ide/common/blame/SourceFilePosition;)V", "rawMessage", "toolName", "(Lcom/android/ide/common/blame/Message$Kind;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/android/ide/common/blame/SourceFilePosition;[Lcom/android/ide/common/blame/SourceFilePosition;)V", "positions", "Lcom/google/common/collect/ImmutableList;", "(Lcom/android/ide/common/blame/Message$Kind;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/google/common/collect/ImmutableList;)V", "Lcom/google/common/base/Optional;", "(Lcom/android/ide/common/blame/Message$Kind;Ljava/lang/String;Ljava/lang/String;Lcom/google/common/base/Optional;Lcom/google/common/collect/ImmutableList;)V", "", "(Lcom/android/ide/common/blame/Message$Kind;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "column", "", "column$annotations", "()V", "getColumn", "()I", "getKind", "()Lcom/android/ide/common/blame/Message$Kind;", "lineNumber", "lineNumber$annotations", "getLineNumber", "getRawMessage", "()Ljava/lang/String;", "getSourceFilePositions", "()Ljava/util/List;", "sourcePath", "getSourcePath", "getText", "getToolName", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "Kind", "common"})
public final class Message {
    @NotNull
    private final Kind kind;
    @NotNull
    private final String text;
    @NotNull
    private final List<SourceFilePosition> sourceFilePositions;
    @NotNull
    private final String rawMessage;
    @Nullable
    private final String toolName;

    @Nullable
    public final String getSourcePath() {
        File file;
        SourceFile sourceFile = this.sourceFilePositions.get(0).getFile();
        Intrinsics.checkExpressionValueIsNotNull(sourceFile, "sourceFilePositions[0].file");
        File file2 = sourceFile.getSourceFile();
        if (file2 == null) {
            return null;
        }
        File file3 = file = file2;
        Intrinsics.checkExpressionValueIsNotNull(file3, "file");
        return file3.getAbsolutePath();
    }

    @Deprecated(message="Use sourceFilePositions", replaceWith=@ReplaceWith(imports={}, expression="sourceFilePositions[0].position.startLine + 1"))
    public static /* synthetic */ void lineNumber$annotations() {
    }

    public final int getLineNumber() {
        SourcePosition sourcePosition = this.sourceFilePositions.get(0).getPosition();
        Intrinsics.checkExpressionValueIsNotNull(sourcePosition, "sourceFilePositions[0].position");
        return sourcePosition.getStartLine() + 1;
    }

    @Deprecated(message="Use sourceFilePositions", replaceWith=@ReplaceWith(imports={}, expression="sourceFilePositions[0].position.startColumn + 1"))
    public static /* synthetic */ void column$annotations() {
    }

    public final int getColumn() {
        SourcePosition sourcePosition = this.sourceFilePositions.get(0).getPosition();
        Intrinsics.checkExpressionValueIsNotNull(sourcePosition, "sourceFilePositions[0].position");
        return sourcePosition.getStartColumn() + 1;
    }

    @NotNull
    public final Kind getKind() {
        return this.kind;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }

    @NotNull
    public final List<SourceFilePosition> getSourceFilePositions() {
        return this.sourceFilePositions;
    }

    @NotNull
    public final String getRawMessage() {
        return this.rawMessage;
    }

    @Nullable
    public final String getToolName() {
        return this.toolName;
    }

    public Message(@NotNull Kind kind, @NotNull String text, @NotNull List<SourceFilePosition> sourceFilePositions, @NotNull String rawMessage, @Nullable String toolName) {
        Intrinsics.checkParameterIsNotNull((Object)kind, "kind");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(sourceFilePositions, "sourceFilePositions");
        Intrinsics.checkParameterIsNotNull(rawMessage, "rawMessage");
        this.kind = kind;
        this.text = text;
        this.sourceFilePositions = sourceFilePositions;
        this.rawMessage = rawMessage;
        this.toolName = toolName;
        if (this.sourceFilePositions.isEmpty()) {
            throw (Throwable)new IllegalArgumentException("Source file positions cannot be empty.");
        }
    }

    public /* synthetic */ Message(Kind kind, String string, List list, String string2, String string3, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 4) != 0) {
            ImmutableList<SourceFilePosition> immutableList = ImmutableList.of(SourceFilePosition.UNKNOWN);
            Intrinsics.checkExpressionValueIsNotNull(immutableList, "ImmutableList.of(SourceFilePosition.UNKNOWN)");
            list = immutableList;
        }
        if ((n2 & 8) != 0) {
            string2 = string;
        }
        if ((n2 & 0x10) != 0) {
            string3 = null;
        }
        this(kind, string, list, string2, string3);
    }

    public Message(@NotNull Kind kind, @NotNull String text, @NotNull SourceFilePosition sourceFilePosition, SourceFilePosition ... sourceFilePositions) {
        Intrinsics.checkParameterIsNotNull((Object)kind, "kind");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(sourceFilePosition, "sourceFilePosition");
        Intrinsics.checkParameterIsNotNull(sourceFilePositions, "sourceFilePositions");
        ImmutableCollection immutableCollection = ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().add(sourceFilePosition)).add(Arrays.copyOf(sourceFilePositions, sourceFilePositions.length))).build();
        Intrinsics.checkExpressionValueIsNotNull(immutableCollection, "ImmutableList.builder<So\u2026rceFilePositions).build()");
        String string = null;
        List list = (List)((Object)immutableCollection);
        String string2 = text;
        this(kind, text, list, string2, string, 16, null);
    }

    public Message(@NotNull Kind kind, @NotNull String text, @NotNull String rawMessage, @Nullable String toolName, @NotNull SourceFilePosition sourceFilePosition, SourceFilePosition ... sourceFilePositions) {
        Intrinsics.checkParameterIsNotNull((Object)kind, "kind");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(rawMessage, "rawMessage");
        Intrinsics.checkParameterIsNotNull(sourceFilePosition, "sourceFilePosition");
        Intrinsics.checkParameterIsNotNull(sourceFilePositions, "sourceFilePositions");
        ImmutableCollection immutableCollection = ((ImmutableList.Builder)((ImmutableList.Builder)ImmutableList.builder().add(sourceFilePosition)).add(Arrays.copyOf(sourceFilePositions, sourceFilePositions.length))).build();
        Intrinsics.checkExpressionValueIsNotNull(immutableCollection, "ImmutableList.builder<So\u2026rceFilePositions).build()");
        List list = (List)((Object)immutableCollection);
        String string = toolName;
        String string2 = rawMessage;
        this(kind, text, list, string2, string);
    }

    public Message(@NotNull Kind kind, @NotNull String text, @NotNull String rawMessage, @Nullable String toolName, @NotNull ImmutableList<SourceFilePosition> positions) {
        Intrinsics.checkParameterIsNotNull((Object)kind, "kind");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(rawMessage, "rawMessage");
        Intrinsics.checkParameterIsNotNull(positions, "positions");
        ImmutableList<SourceFilePosition> immutableList = positions.isEmpty() ? ImmutableList.of(SourceFilePosition.UNKNOWN) : positions;
        Intrinsics.checkExpressionValueIsNotNull(immutableList, "if (positions.isEmpty())\u2026  positions\n            }");
        List list = immutableList;
        String string = toolName;
        String string2 = rawMessage;
        this(kind, text, list, string2, string);
    }

    @Deprecated(message="Used by kotlin plugin.")
    public Message(@NotNull Kind kind, @NotNull String text, @NotNull String rawMessage, @NotNull Optional<String> toolName, @NotNull ImmutableList<SourceFilePosition> positions) {
        Intrinsics.checkParameterIsNotNull((Object)kind, "kind");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(rawMessage, "rawMessage");
        Intrinsics.checkParameterIsNotNull(toolName, "toolName");
        Intrinsics.checkParameterIsNotNull(positions, "positions");
        ImmutableList<SourceFilePosition> immutableList = positions.isEmpty() ? ImmutableList.of(SourceFilePosition.UNKNOWN) : positions;
        Intrinsics.checkExpressionValueIsNotNull(immutableList, "if (positions.isEmpty())\u2026  positions\n            }");
        List list = immutableList;
        String string = toolName.orNull();
        String string2 = rawMessage;
        this(kind, text, list, string2, string);
    }

    @NotNull
    public final Kind component1() {
        return this.kind;
    }

    @NotNull
    public final String component2() {
        return this.text;
    }

    @NotNull
    public final List<SourceFilePosition> component3() {
        return this.sourceFilePositions;
    }

    @NotNull
    public final String component4() {
        return this.rawMessage;
    }

    @Nullable
    public final String component5() {
        return this.toolName;
    }

    @NotNull
    public final Message copy(@NotNull Kind kind, @NotNull String text, @NotNull List<SourceFilePosition> sourceFilePositions, @NotNull String rawMessage, @Nullable String toolName) {
        Intrinsics.checkParameterIsNotNull((Object)kind, "kind");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(sourceFilePositions, "sourceFilePositions");
        Intrinsics.checkParameterIsNotNull(rawMessage, "rawMessage");
        return new Message(kind, text, sourceFilePositions, rawMessage, toolName);
    }

    @NotNull
    public static /* bridge */ /* synthetic */ Message copy$default(Message message, Kind kind, String string, List list, String string2, String string3, int n2, Object object) {
        if ((n2 & 1) != 0) {
            kind = message.kind;
        }
        if ((n2 & 2) != 0) {
            string = message.text;
        }
        if ((n2 & 4) != 0) {
            list = message.sourceFilePositions;
        }
        if ((n2 & 8) != 0) {
            string2 = message.rawMessage;
        }
        if ((n2 & 0x10) != 0) {
            string3 = message.toolName;
        }
        return message.copy(kind, string, list, string2, string3);
    }

    public String toString() {
        return "Message(kind=" + (Object)((Object)this.kind) + ", text=" + this.text + ", sourceFilePositions=" + this.sourceFilePositions + ", rawMessage=" + this.rawMessage + ", toolName=" + this.toolName + ")";
    }

    public int hashCode() {
        Kind kind = this.kind;
        String string = this.text;
        List<SourceFilePosition> list = this.sourceFilePositions;
        String string2 = this.rawMessage;
        String string3 = this.toolName;
        return ((((kind != null ? ((Object)((Object)kind)).hashCode() : 0) * 31 + (string != null ? string.hashCode() : 0)) * 31 + (list != null ? ((Object)list).hashCode() : 0)) * 31 + (string2 != null ? string2.hashCode() : 0)) * 31 + (string3 != null ? string3.hashCode() : 0);
    }

    public boolean equals(Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Message)) break block3;
                Message message = (Message)object;
                if (!Intrinsics.areEqual((Object)this.kind, (Object)message.kind) || !Intrinsics.areEqual(this.text, message.text) || !Intrinsics.areEqual(this.sourceFilePositions, message.sourceFilePositions) || !Intrinsics.areEqual(this.rawMessage, message.rawMessage) || !Intrinsics.areEqual(this.toolName, message.toolName)) break block3;
            }
            return true;
        }
        return false;
    }

    @Metadata(mv={1, 1, 9}, bv={1, 0, 2}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\t\b\u0086\u0001\u0018\u0000 \t2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\tB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\n"}, d2={"Lcom/android/ide/common/blame/Message$Kind;", "", "(Ljava/lang/String;I)V", "ERROR", "WARNING", "INFO", "STATISTICS", "UNKNOWN", "SIMPLE", "Companion", "common"})
    public static final class Kind
    extends Enum<Kind> {
        public static final /* enum */ Kind ERROR;
        public static final /* enum */ Kind WARNING;
        public static final /* enum */ Kind INFO;
        public static final /* enum */ Kind STATISTICS;
        public static final /* enum */ Kind UNKNOWN;
        public static final /* enum */ Kind SIMPLE;
        private static final /* synthetic */ Kind[] $VALUES;
        public static final Companion Companion;

        static {
            Kind[] kindArray = new Kind[6];
            Kind[] kindArray2 = kindArray;
            kindArray[0] = ERROR = new Kind();
            kindArray[1] = WARNING = new Kind();
            kindArray[2] = INFO = new Kind();
            kindArray[3] = STATISTICS = new Kind();
            kindArray[4] = UNKNOWN = new Kind();
            kindArray[5] = SIMPLE = new Kind();
            $VALUES = kindArray;
            Companion = new Companion(null);
        }

        public static Kind[] values() {
            return (Kind[])$VALUES.clone();
        }

        public static Kind valueOf(String string) {
            return Enum.valueOf(Kind.class, string);
        }

        @JvmStatic
        @Nullable
        public static final Kind findIgnoringCase(@NotNull String s3, @Nullable Kind defaultKind) {
            Intrinsics.checkParameterIsNotNull(s3, "s");
            return Companion.findIgnoringCase(s3, defaultKind);
        }

        @Metadata(mv={1, 1, 9}, bv={1, 0, 2}, k=1, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0007\u00a8\u0006\b"}, d2={"Lcom/android/ide/common/blame/Message$Kind$Companion;", "", "()V", "findIgnoringCase", "Lcom/android/ide/common/blame/Message$Kind;", "s", "", "defaultKind", "common"})
        public static final class Companion {
            @JvmStatic
            @Nullable
            public final Kind findIgnoringCase(@NotNull String s3, @Nullable Kind defaultKind) {
                Intrinsics.checkParameterIsNotNull(s3, "s");
                Kind[] kindArray = Kind.values();
                for (int i2 = 0; i2 < kindArray.length; ++i2) {
                    Kind kind = kindArray[i2];
                    if (!StringsKt.equals(kind.toString(), s3, true)) continue;
                    return kind;
                }
                return defaultKind;
            }

            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }
}

