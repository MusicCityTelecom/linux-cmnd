/*
 * Decompiled with CFR 0.152.
 */
package com.android.bundle;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AppDependenciesOuterClass {
    private static final Descriptors.Descriptor internal_static_android_bundle_AppDependencies_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_AppDependencies_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_LibraryDependencies_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_LibraryDependencies_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ModuleDependencies_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ModuleDependencies_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_Library_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Library_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_Library_Digests_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Library_Digests_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_MavenLibrary_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_MavenLibrary_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private AppDependenciesOuterClass() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        AppDependenciesOuterClass.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0016app_dependencies.proto\u0012\u000eandroid.bundle\"\u00bf\u0001\n\u000fAppDependencies\u0012(\n\u0007library\u0018\u0001 \u0003(\u000b2\u0017.android.bundle.Library\u0012A\n\u0014library_dependencies\u0018\u0002 \u0003(\u000b2#.android.bundle.LibraryDependencies\u0012?\n\u0013module_dependencies\u0018\u0003 \u0003(\u000b2\".android.bundle.ModuleDependencies\"G\n\u0013LibraryDependencies\u0012\u0015\n\rlibrary_index\u0018\u0001 \u0001(\u0005\u0012\u0019\n\u0011library_dep_index\u0018\u0002 \u0003(\u0005\"C\n\u0012ModuleDependencies\u0012\u0013\n\u000bmodule_name\u0018\u0001 \u0001(\t\u0012\u0018\n\u0010dependency_index\u0018\u0002 \u0003(\u0005\"\u009e\u0001\n\u0007Library\u00125\n\rmaven_li", "brary\u0018\u0001 \u0001(\u000b2\u001c.android.bundle.MavenLibraryH\u0000\u00120\n\u0007digests\u0018\u0002 \u0001(\u000b2\u001f.android.bundle.Library.Digests\u001a\u0019\n\u0007Digests\u0012\u000e\n\u0006sha256\u0018\u0001 \u0001(\fB\u000f\n\rlibrary_oneof\"m\n\fMavenLibrary\u0012\u0010\n\bgroup_id\u0018\u0001 \u0001(\t\u0012\u0013\n\u000bartifact_id\u0018\u0002 \u0001(\t\u0012\u0011\n\tpackaging\u0018\u0003 \u0001(\t\u0012\u0012\n\nclassifier\u0018\u0004 \u0001(\t\u0012\u000f\n\u0007version\u0018\u0005 \u0001(\tB\u0014\n\u0012com.android.bundle"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            @Override
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0], assigner);
        internal_static_android_bundle_AppDependencies_descriptor = AppDependenciesOuterClass.getDescriptor().getMessageTypes().get(0);
        internal_static_android_bundle_AppDependencies_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_AppDependencies_descriptor, new String[]{"Library", "LibraryDependencies", "ModuleDependencies"});
        internal_static_android_bundle_LibraryDependencies_descriptor = AppDependenciesOuterClass.getDescriptor().getMessageTypes().get(1);
        internal_static_android_bundle_LibraryDependencies_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_LibraryDependencies_descriptor, new String[]{"LibraryIndex", "LibraryDepIndex"});
        internal_static_android_bundle_ModuleDependencies_descriptor = AppDependenciesOuterClass.getDescriptor().getMessageTypes().get(2);
        internal_static_android_bundle_ModuleDependencies_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ModuleDependencies_descriptor, new String[]{"ModuleName", "DependencyIndex"});
        internal_static_android_bundle_Library_descriptor = AppDependenciesOuterClass.getDescriptor().getMessageTypes().get(3);
        internal_static_android_bundle_Library_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Library_descriptor, new String[]{"MavenLibrary", "Digests", "LibraryOneof"});
        internal_static_android_bundle_Library_Digests_descriptor = internal_static_android_bundle_Library_descriptor.getNestedTypes().get(0);
        internal_static_android_bundle_Library_Digests_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Library_Digests_descriptor, new String[]{"Sha256"});
        internal_static_android_bundle_MavenLibrary_descriptor = AppDependenciesOuterClass.getDescriptor().getMessageTypes().get(4);
        internal_static_android_bundle_MavenLibrary_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_MavenLibrary_descriptor, new String[]{"GroupId", "ArtifactId", "Packaging", "Classifier", "Version"});
    }

    public static final class MavenLibrary
    extends GeneratedMessageV3
    implements MavenLibraryOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int GROUP_ID_FIELD_NUMBER = 1;
        private volatile Object groupId_;
        public static final int ARTIFACT_ID_FIELD_NUMBER = 2;
        private volatile Object artifactId_;
        public static final int PACKAGING_FIELD_NUMBER = 3;
        private volatile Object packaging_;
        public static final int CLASSIFIER_FIELD_NUMBER = 4;
        private volatile Object classifier_;
        public static final int VERSION_FIELD_NUMBER = 5;
        private volatile Object version_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final MavenLibrary DEFAULT_INSTANCE = new MavenLibrary();
        @Deprecated
        public static final Parser<MavenLibrary> PARSER = new AbstractParser<MavenLibrary>(){

            @Override
            public MavenLibrary parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MavenLibrary(input, extensionRegistry);
            }
        };

        private MavenLibrary(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private MavenLibrary() {
            this.groupId_ = "";
            this.artifactId_ = "";
            this.packaging_ = "";
            this.classifier_ = "";
            this.version_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MavenLibrary(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block14: while (!done) {
                    ByteString bs;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block14;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block14;
                            done = true;
                            continue block14;
                        }
                        case 10: {
                            bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.groupId_ = bs;
                            continue block14;
                        }
                        case 18: {
                            bs = input.readBytes();
                            this.bitField0_ |= 2;
                            this.artifactId_ = bs;
                            continue block14;
                        }
                        case 26: {
                            bs = input.readBytes();
                            this.bitField0_ |= 4;
                            this.packaging_ = bs;
                            continue block14;
                        }
                        case 34: {
                            bs = input.readBytes();
                            this.bitField0_ |= 8;
                            this.classifier_ = bs;
                            continue block14;
                        }
                        case 42: 
                    }
                    bs = input.readBytes();
                    this.bitField0_ |= 0x10;
                    this.version_ = bs;
                }
            }
            catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(this);
            }
            catch (IOException e3) {
                throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_MavenLibrary_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_MavenLibrary_fieldAccessorTable.ensureFieldAccessorsInitialized(MavenLibrary.class, Builder.class);
        }

        @Override
        public boolean hasGroupId() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public String getGroupId() {
            Object ref = this.groupId_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.groupId_ = s3;
            }
            return s3;
        }

        @Override
        public ByteString getGroupIdBytes() {
            Object ref = this.groupId_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.groupId_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasArtifactId() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public String getArtifactId() {
            Object ref = this.artifactId_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.artifactId_ = s3;
            }
            return s3;
        }

        @Override
        public ByteString getArtifactIdBytes() {
            Object ref = this.artifactId_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.artifactId_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasPackaging() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public String getPackaging() {
            Object ref = this.packaging_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.packaging_ = s3;
            }
            return s3;
        }

        @Override
        public ByteString getPackagingBytes() {
            Object ref = this.packaging_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.packaging_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasClassifier() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override
        public String getClassifier() {
            Object ref = this.classifier_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.classifier_ = s3;
            }
            return s3;
        }

        @Override
        public ByteString getClassifierBytes() {
            Object ref = this.classifier_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.classifier_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasVersion() {
            return (this.bitField0_ & 0x10) == 16;
        }

        @Override
        public String getVersion() {
            Object ref = this.version_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.version_ = s3;
            }
            return s3;
        }

        @Override
        public ByteString getVersionBytes() {
            Object ref = this.version_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.version_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                GeneratedMessageV3.writeString(output, 1, this.groupId_);
            }
            if ((this.bitField0_ & 2) == 2) {
                GeneratedMessageV3.writeString(output, 2, this.artifactId_);
            }
            if ((this.bitField0_ & 4) == 4) {
                GeneratedMessageV3.writeString(output, 3, this.packaging_);
            }
            if ((this.bitField0_ & 8) == 8) {
                GeneratedMessageV3.writeString(output, 4, this.classifier_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                GeneratedMessageV3.writeString(output, 5, this.version_);
            }
            this.unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += GeneratedMessageV3.computeStringSize(1, this.groupId_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += GeneratedMessageV3.computeStringSize(2, this.artifactId_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += GeneratedMessageV3.computeStringSize(3, this.packaging_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += GeneratedMessageV3.computeStringSize(4, this.classifier_);
            }
            if ((this.bitField0_ & 0x10) == 16) {
                size += GeneratedMessageV3.computeStringSize(5, this.version_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MavenLibrary)) {
                return super.equals(obj);
            }
            MavenLibrary other = (MavenLibrary)obj;
            boolean result = true;
            boolean bl = result = result && this.hasGroupId() == other.hasGroupId();
            if (this.hasGroupId()) {
                result = result && this.getGroupId().equals(other.getGroupId());
            }
            boolean bl2 = result = result && this.hasArtifactId() == other.hasArtifactId();
            if (this.hasArtifactId()) {
                result = result && this.getArtifactId().equals(other.getArtifactId());
            }
            boolean bl3 = result = result && this.hasPackaging() == other.hasPackaging();
            if (this.hasPackaging()) {
                result = result && this.getPackaging().equals(other.getPackaging());
            }
            boolean bl4 = result = result && this.hasClassifier() == other.hasClassifier();
            if (this.hasClassifier()) {
                result = result && this.getClassifier().equals(other.getClassifier());
            }
            boolean bl5 = result = result && this.hasVersion() == other.hasVersion();
            if (this.hasVersion()) {
                result = result && this.getVersion().equals(other.getVersion());
            }
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + MavenLibrary.getDescriptor().hashCode();
            if (this.hasGroupId()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getGroupId().hashCode();
            }
            if (this.hasArtifactId()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getArtifactId().hashCode();
            }
            if (this.hasPackaging()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getPackaging().hashCode();
            }
            if (this.hasClassifier()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getClassifier().hashCode();
            }
            if (this.hasVersion()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getVersion().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static MavenLibrary parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MavenLibrary parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MavenLibrary parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MavenLibrary parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MavenLibrary parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MavenLibrary parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MavenLibrary parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MavenLibrary parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MavenLibrary parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MavenLibrary parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MavenLibrary parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MavenLibrary parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return MavenLibrary.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MavenLibrary prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static MavenLibrary getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MavenLibrary> parser() {
            return PARSER;
        }

        public Parser<MavenLibrary> getParserForType() {
            return PARSER;
        }

        @Override
        public MavenLibrary getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements MavenLibraryOrBuilder {
            private int bitField0_;
            private Object groupId_ = "";
            private Object artifactId_ = "";
            private Object packaging_ = "";
            private Object classifier_ = "";
            private Object version_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_MavenLibrary_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_MavenLibrary_fieldAccessorTable.ensureFieldAccessorsInitialized(MavenLibrary.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    // empty if block
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.groupId_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                this.artifactId_ = "";
                this.bitField0_ &= 0xFFFFFFFD;
                this.packaging_ = "";
                this.bitField0_ &= 0xFFFFFFFB;
                this.classifier_ = "";
                this.bitField0_ &= 0xFFFFFFF7;
                this.version_ = "";
                this.bitField0_ &= 0xFFFFFFEF;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_MavenLibrary_descriptor;
            }

            @Override
            public MavenLibrary getDefaultInstanceForType() {
                return MavenLibrary.getDefaultInstance();
            }

            @Override
            public MavenLibrary build() {
                MavenLibrary result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public MavenLibrary buildPartial() {
                MavenLibrary result = new MavenLibrary(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.groupId_ = this.groupId_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.artifactId_ = this.artifactId_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.packaging_ = this.packaging_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.classifier_ = this.classifier_;
                if ((from_bitField0_ & 0x10) == 16) {
                    to_bitField0_ |= 0x10;
                }
                result.version_ = this.version_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder)super.setField(field, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field) {
                return (Builder)super.clearField(field);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
                return (Builder)super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder)super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof MavenLibrary) {
                    return this.mergeFrom((MavenLibrary)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MavenLibrary other) {
                if (other == MavenLibrary.getDefaultInstance()) {
                    return this;
                }
                if (other.hasGroupId()) {
                    this.bitField0_ |= 1;
                    this.groupId_ = other.groupId_;
                    this.onChanged();
                }
                if (other.hasArtifactId()) {
                    this.bitField0_ |= 2;
                    this.artifactId_ = other.artifactId_;
                    this.onChanged();
                }
                if (other.hasPackaging()) {
                    this.bitField0_ |= 4;
                    this.packaging_ = other.packaging_;
                    this.onChanged();
                }
                if (other.hasClassifier()) {
                    this.bitField0_ |= 8;
                    this.classifier_ = other.classifier_;
                    this.onChanged();
                }
                if (other.hasVersion()) {
                    this.bitField0_ |= 0x10;
                    this.version_ = other.version_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MavenLibrary parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (MavenLibrary)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean hasGroupId() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public String getGroupId() {
                Object ref = this.groupId_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.groupId_ = s3;
                    }
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getGroupIdBytes() {
                Object ref = this.groupId_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.groupId_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setGroupId(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.groupId_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearGroupId() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.groupId_ = MavenLibrary.getDefaultInstance().getGroupId();
                this.onChanged();
                return this;
            }

            public Builder setGroupIdBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.groupId_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasArtifactId() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public String getArtifactId() {
                Object ref = this.artifactId_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.artifactId_ = s3;
                    }
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getArtifactIdBytes() {
                Object ref = this.artifactId_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.artifactId_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setArtifactId(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.artifactId_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearArtifactId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.artifactId_ = MavenLibrary.getDefaultInstance().getArtifactId();
                this.onChanged();
                return this;
            }

            public Builder setArtifactIdBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.artifactId_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPackaging() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public String getPackaging() {
                Object ref = this.packaging_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.packaging_ = s3;
                    }
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getPackagingBytes() {
                Object ref = this.packaging_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.packaging_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setPackaging(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.packaging_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPackaging() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.packaging_ = MavenLibrary.getDefaultInstance().getPackaging();
                this.onChanged();
                return this;
            }

            public Builder setPackagingBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.packaging_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasClassifier() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override
            public String getClassifier() {
                Object ref = this.classifier_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.classifier_ = s3;
                    }
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getClassifierBytes() {
                Object ref = this.classifier_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.classifier_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setClassifier(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.classifier_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearClassifier() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.classifier_ = MavenLibrary.getDefaultInstance().getClassifier();
                this.onChanged();
                return this;
            }

            public Builder setClassifierBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.classifier_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasVersion() {
                return (this.bitField0_ & 0x10) == 16;
            }

            @Override
            public String getVersion() {
                Object ref = this.version_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.version_ = s3;
                    }
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getVersionBytes() {
                Object ref = this.version_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.version_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setVersion(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x10;
                this.version_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.bitField0_ &= 0xFFFFFFEF;
                this.version_ = MavenLibrary.getDefaultInstance().getVersion();
                this.onChanged();
                return this;
            }

            public Builder setVersionBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x10;
                this.version_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface MavenLibraryOrBuilder
    extends MessageOrBuilder {
        public boolean hasGroupId();

        public String getGroupId();

        public ByteString getGroupIdBytes();

        public boolean hasArtifactId();

        public String getArtifactId();

        public ByteString getArtifactIdBytes();

        public boolean hasPackaging();

        public String getPackaging();

        public ByteString getPackagingBytes();

        public boolean hasClassifier();

        public String getClassifier();

        public ByteString getClassifierBytes();

        public boolean hasVersion();

        public String getVersion();

        public ByteString getVersionBytes();
    }

    public static final class Library
    extends GeneratedMessageV3
    implements LibraryOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        private int libraryOneofCase_ = 0;
        private Object libraryOneof_;
        public static final int MAVEN_LIBRARY_FIELD_NUMBER = 1;
        public static final int DIGESTS_FIELD_NUMBER = 2;
        private Digests digests_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Library DEFAULT_INSTANCE = new Library();
        @Deprecated
        public static final Parser<Library> PARSER = new AbstractParser<Library>(){

            @Override
            public Library parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Library(input, extensionRegistry);
            }
        };

        private Library(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Library() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Library(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block11: while (!done) {
                    GeneratedMessageV3.Builder subBuilder;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block11;
                            done = true;
                            continue block11;
                        }
                        case 10: {
                            subBuilder = null;
                            if (this.libraryOneofCase_ == 1) {
                                subBuilder = ((MavenLibrary)this.libraryOneof_).toBuilder();
                            }
                            this.libraryOneof_ = input.readMessage(MavenLibrary.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                ((MavenLibrary.Builder)subBuilder).mergeFrom((MavenLibrary)this.libraryOneof_);
                                this.libraryOneof_ = ((MavenLibrary.Builder)subBuilder).buildPartial();
                            }
                            this.libraryOneofCase_ = 1;
                            continue block11;
                        }
                        case 18: 
                    }
                    subBuilder = null;
                    if ((this.bitField0_ & 2) == 2) {
                        subBuilder = this.digests_.toBuilder();
                    }
                    this.digests_ = input.readMessage(Digests.PARSER, extensionRegistry);
                    if (subBuilder != null) {
                        ((Digests.Builder)subBuilder).mergeFrom(this.digests_);
                        this.digests_ = ((Digests.Builder)subBuilder).buildPartial();
                    }
                    this.bitField0_ |= 2;
                }
            }
            catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(this);
            }
            catch (IOException e3) {
                throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
            }
            finally {
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_Library_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Library_fieldAccessorTable.ensureFieldAccessorsInitialized(Library.class, Builder.class);
        }

        @Override
        public LibraryOneofCase getLibraryOneofCase() {
            return LibraryOneofCase.forNumber(this.libraryOneofCase_);
        }

        @Override
        public boolean hasMavenLibrary() {
            return this.libraryOneofCase_ == 1;
        }

        @Override
        public MavenLibrary getMavenLibrary() {
            if (this.libraryOneofCase_ == 1) {
                return (MavenLibrary)this.libraryOneof_;
            }
            return MavenLibrary.getDefaultInstance();
        }

        @Override
        public MavenLibraryOrBuilder getMavenLibraryOrBuilder() {
            if (this.libraryOneofCase_ == 1) {
                return (MavenLibrary)this.libraryOneof_;
            }
            return MavenLibrary.getDefaultInstance();
        }

        @Override
        public boolean hasDigests() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public Digests getDigests() {
            return this.digests_ == null ? Digests.getDefaultInstance() : this.digests_;
        }

        @Override
        public DigestsOrBuilder getDigestsOrBuilder() {
            return this.digests_ == null ? Digests.getDefaultInstance() : this.digests_;
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            if (this.libraryOneofCase_ == 1) {
                output.writeMessage(1, (MavenLibrary)this.libraryOneof_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, this.getDigests());
            }
            this.unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if (this.libraryOneofCase_ == 1) {
                size += CodedOutputStream.computeMessageSize(1, (MavenLibrary)this.libraryOneof_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeMessageSize(2, this.getDigests());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Library)) {
                return super.equals(obj);
            }
            Library other = (Library)obj;
            boolean result = true;
            boolean bl = result = result && this.hasDigests() == other.hasDigests();
            if (this.hasDigests()) {
                result = result && this.getDigests().equals(other.getDigests());
            }
            boolean bl2 = result = result && this.getLibraryOneofCase().equals(other.getLibraryOneofCase());
            if (!result) {
                return false;
            }
            switch (this.libraryOneofCase_) {
                case 1: {
                    result = result && this.getMavenLibrary().equals(other.getMavenLibrary());
                    break;
                }
            }
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Library.getDescriptor().hashCode();
            if (this.hasDigests()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getDigests().hashCode();
            }
            switch (this.libraryOneofCase_) {
                case 1: {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getMavenLibrary().hashCode();
                    break;
                }
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Library parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Library parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Library parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Library parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Library parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Library parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Library parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Library parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Library parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Library parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Library parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Library parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Library.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Library prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static Library getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Library> parser() {
            return PARSER;
        }

        public Parser<Library> getParserForType() {
            return PARSER;
        }

        @Override
        public Library getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements LibraryOrBuilder {
            private int libraryOneofCase_ = 0;
            private Object libraryOneof_;
            private int bitField0_;
            private SingleFieldBuilderV3<MavenLibrary, MavenLibrary.Builder, MavenLibraryOrBuilder> mavenLibraryBuilder_;
            private Digests digests_ = null;
            private SingleFieldBuilderV3<Digests, Digests.Builder, DigestsOrBuilder> digestsBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Library_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Library_fieldAccessorTable.ensureFieldAccessorsInitialized(Library.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    this.getDigestsFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.digestsBuilder_ == null) {
                    this.digests_ = null;
                } else {
                    this.digestsBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                this.libraryOneofCase_ = 0;
                this.libraryOneof_ = null;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Library_descriptor;
            }

            @Override
            public Library getDefaultInstanceForType() {
                return Library.getDefaultInstance();
            }

            @Override
            public Library build() {
                Library result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Library buildPartial() {
                Library result = new Library(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if (this.libraryOneofCase_ == 1) {
                    if (this.mavenLibraryBuilder_ == null) {
                        result.libraryOneof_ = this.libraryOneof_;
                    } else {
                        result.libraryOneof_ = this.mavenLibraryBuilder_.build();
                    }
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.digestsBuilder_ == null) {
                    result.digests_ = this.digests_;
                } else {
                    result.digests_ = this.digestsBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                result.libraryOneofCase_ = this.libraryOneofCase_;
                this.onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder)super.setField(field, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field) {
                return (Builder)super.clearField(field);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
                return (Builder)super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder)super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof Library) {
                    return this.mergeFrom((Library)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Library other) {
                if (other == Library.getDefaultInstance()) {
                    return this;
                }
                if (other.hasDigests()) {
                    this.mergeDigests(other.getDigests());
                }
                switch (other.getLibraryOneofCase()) {
                    case MAVEN_LIBRARY: {
                        this.mergeMavenLibrary(other.getMavenLibrary());
                        break;
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Library parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Library)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public LibraryOneofCase getLibraryOneofCase() {
                return LibraryOneofCase.forNumber(this.libraryOneofCase_);
            }

            public Builder clearLibraryOneof() {
                this.libraryOneofCase_ = 0;
                this.libraryOneof_ = null;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasMavenLibrary() {
                return this.libraryOneofCase_ == 1;
            }

            @Override
            public MavenLibrary getMavenLibrary() {
                if (this.mavenLibraryBuilder_ == null) {
                    if (this.libraryOneofCase_ == 1) {
                        return (MavenLibrary)this.libraryOneof_;
                    }
                    return MavenLibrary.getDefaultInstance();
                }
                if (this.libraryOneofCase_ == 1) {
                    return this.mavenLibraryBuilder_.getMessage();
                }
                return MavenLibrary.getDefaultInstance();
            }

            public Builder setMavenLibrary(MavenLibrary value) {
                if (this.mavenLibraryBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.libraryOneof_ = value;
                    this.onChanged();
                } else {
                    this.mavenLibraryBuilder_.setMessage(value);
                }
                this.libraryOneofCase_ = 1;
                return this;
            }

            public Builder setMavenLibrary(MavenLibrary.Builder builderForValue) {
                if (this.mavenLibraryBuilder_ == null) {
                    this.libraryOneof_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.mavenLibraryBuilder_.setMessage(builderForValue.build());
                }
                this.libraryOneofCase_ = 1;
                return this;
            }

            public Builder mergeMavenLibrary(MavenLibrary value) {
                if (this.mavenLibraryBuilder_ == null) {
                    this.libraryOneof_ = this.libraryOneofCase_ == 1 && this.libraryOneof_ != MavenLibrary.getDefaultInstance() ? MavenLibrary.newBuilder((MavenLibrary)this.libraryOneof_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.libraryOneofCase_ == 1) {
                        this.mavenLibraryBuilder_.mergeFrom(value);
                    }
                    this.mavenLibraryBuilder_.setMessage(value);
                }
                this.libraryOneofCase_ = 1;
                return this;
            }

            public Builder clearMavenLibrary() {
                if (this.mavenLibraryBuilder_ == null) {
                    if (this.libraryOneofCase_ == 1) {
                        this.libraryOneofCase_ = 0;
                        this.libraryOneof_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.libraryOneofCase_ == 1) {
                        this.libraryOneofCase_ = 0;
                        this.libraryOneof_ = null;
                    }
                    this.mavenLibraryBuilder_.clear();
                }
                return this;
            }

            public MavenLibrary.Builder getMavenLibraryBuilder() {
                return this.getMavenLibraryFieldBuilder().getBuilder();
            }

            @Override
            public MavenLibraryOrBuilder getMavenLibraryOrBuilder() {
                if (this.libraryOneofCase_ == 1 && this.mavenLibraryBuilder_ != null) {
                    return this.mavenLibraryBuilder_.getMessageOrBuilder();
                }
                if (this.libraryOneofCase_ == 1) {
                    return (MavenLibrary)this.libraryOneof_;
                }
                return MavenLibrary.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MavenLibrary, MavenLibrary.Builder, MavenLibraryOrBuilder> getMavenLibraryFieldBuilder() {
                if (this.mavenLibraryBuilder_ == null) {
                    if (this.libraryOneofCase_ != 1) {
                        this.libraryOneof_ = MavenLibrary.getDefaultInstance();
                    }
                    this.mavenLibraryBuilder_ = new SingleFieldBuilderV3((MavenLibrary)this.libraryOneof_, this.getParentForChildren(), this.isClean());
                    this.libraryOneof_ = null;
                }
                this.libraryOneofCase_ = 1;
                this.onChanged();
                return this.mavenLibraryBuilder_;
            }

            @Override
            public boolean hasDigests() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public Digests getDigests() {
                if (this.digestsBuilder_ == null) {
                    return this.digests_ == null ? Digests.getDefaultInstance() : this.digests_;
                }
                return this.digestsBuilder_.getMessage();
            }

            public Builder setDigests(Digests value) {
                if (this.digestsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.digests_ = value;
                    this.onChanged();
                } else {
                    this.digestsBuilder_.setMessage(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setDigests(Digests.Builder builderForValue) {
                if (this.digestsBuilder_ == null) {
                    this.digests_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.digestsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeDigests(Digests value) {
                if (this.digestsBuilder_ == null) {
                    this.digests_ = (this.bitField0_ & 2) == 2 && this.digests_ != null && this.digests_ != Digests.getDefaultInstance() ? Digests.newBuilder(this.digests_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.digestsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearDigests() {
                if (this.digestsBuilder_ == null) {
                    this.digests_ = null;
                    this.onChanged();
                } else {
                    this.digestsBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            public Digests.Builder getDigestsBuilder() {
                this.bitField0_ |= 2;
                this.onChanged();
                return this.getDigestsFieldBuilder().getBuilder();
            }

            @Override
            public DigestsOrBuilder getDigestsOrBuilder() {
                if (this.digestsBuilder_ != null) {
                    return this.digestsBuilder_.getMessageOrBuilder();
                }
                return this.digests_ == null ? Digests.getDefaultInstance() : this.digests_;
            }

            private SingleFieldBuilderV3<Digests, Digests.Builder, DigestsOrBuilder> getDigestsFieldBuilder() {
                if (this.digestsBuilder_ == null) {
                    this.digestsBuilder_ = new SingleFieldBuilderV3(this.getDigests(), this.getParentForChildren(), this.isClean());
                    this.digests_ = null;
                }
                return this.digestsBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }

        public static enum LibraryOneofCase implements Internal.EnumLite
        {
            MAVEN_LIBRARY(1),
            LIBRARYONEOF_NOT_SET(0);

            private final int value;

            private LibraryOneofCase(int value) {
                this.value = value;
            }

            @Deprecated
            public static LibraryOneofCase valueOf(int value) {
                return LibraryOneofCase.forNumber(value);
            }

            public static LibraryOneofCase forNumber(int value) {
                switch (value) {
                    case 1: {
                        return MAVEN_LIBRARY;
                    }
                    case 0: {
                        return LIBRARYONEOF_NOT_SET;
                    }
                }
                return null;
            }

            @Override
            public int getNumber() {
                return this.value;
            }
        }

        public static final class Digests
        extends GeneratedMessageV3
        implements DigestsOrBuilder {
            private static final long serialVersionUID = 0L;
            private int bitField0_;
            public static final int SHA256_FIELD_NUMBER = 1;
            private ByteString sha256_;
            private byte memoizedIsInitialized = (byte)-1;
            private static final Digests DEFAULT_INSTANCE = new Digests();
            @Deprecated
            public static final Parser<Digests> PARSER = new AbstractParser<Digests>(){

                @Override
                public Digests parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Digests(input, extensionRegistry);
                }
            };

            private Digests(GeneratedMessageV3.Builder<?> builder) {
                super(builder);
            }

            private Digests() {
                this.sha256_ = ByteString.EMPTY;
            }

            @Override
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private Digests(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this();
                boolean mutable_bitField0_ = false;
                UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
                try {
                    boolean done = false;
                    block10: while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0: {
                                done = true;
                                continue block10;
                            }
                            default: {
                                if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block10;
                                done = true;
                                continue block10;
                            }
                            case 10: 
                        }
                        this.bitField0_ |= 1;
                        this.sha256_ = input.readBytes();
                    }
                }
                catch (InvalidProtocolBufferException e2) {
                    throw e2.setUnfinishedMessage(this);
                }
                catch (IOException e3) {
                    throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                }
                finally {
                    this.unknownFields = unknownFields.build();
                    this.makeExtensionsImmutable();
                }
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Library_Digests_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Library_Digests_fieldAccessorTable.ensureFieldAccessorsInitialized(Digests.class, Builder.class);
            }

            @Override
            public boolean hasSha256() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public ByteString getSha256() {
                return this.sha256_;
            }

            @Override
            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == 1) {
                    return true;
                }
                if (isInitialized == 0) {
                    return false;
                }
                this.memoizedIsInitialized = 1;
                return true;
            }

            @Override
            public void writeTo(CodedOutputStream output) throws IOException {
                if ((this.bitField0_ & 1) == 1) {
                    output.writeBytes(1, this.sha256_);
                }
                this.unknownFields.writeTo(output);
            }

            @Override
            public int getSerializedSize() {
                int size = this.memoizedSize;
                if (size != -1) {
                    return size;
                }
                size = 0;
                if ((this.bitField0_ & 1) == 1) {
                    size += CodedOutputStream.computeBytesSize(1, this.sha256_);
                }
                this.memoizedSize = size += this.unknownFields.getSerializedSize();
                return size;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Digests)) {
                    return super.equals(obj);
                }
                Digests other = (Digests)obj;
                boolean result = true;
                boolean bl = result = result && this.hasSha256() == other.hasSha256();
                if (this.hasSha256()) {
                    result = result && this.getSha256().equals(other.getSha256());
                }
                result = result && this.unknownFields.equals(other.unknownFields);
                return result;
            }

            @Override
            public int hashCode() {
                if (this.memoizedHashCode != 0) {
                    return this.memoizedHashCode;
                }
                int hash = 41;
                hash = 19 * hash + Digests.getDescriptor().hashCode();
                if (this.hasSha256()) {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getSha256().hashCode();
                }
                this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
                return hash;
            }

            public static Digests parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Digests parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Digests parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Digests parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Digests parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Digests parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Digests parseFrom(InputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Digests parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public static Digests parseDelimitedFrom(InputStream input) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
            }

            public static Digests parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }

            public static Digests parseFrom(CodedInputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Digests parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            @Override
            public Builder newBuilderForType() {
                return Digests.newBuilder();
            }

            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }

            public static Builder newBuilder(Digests prototype) {
                return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
            }

            @Override
            public Builder toBuilder() {
                return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
            }

            @Override
            protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
                Builder builder = new Builder(parent);
                return builder;
            }

            public static Digests getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Digests> parser() {
                return PARSER;
            }

            public Parser<Digests> getParserForType() {
                return PARSER;
            }

            @Override
            public Digests getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }

            public static final class Builder
            extends GeneratedMessageV3.Builder<Builder>
            implements DigestsOrBuilder {
                private int bitField0_;
                private ByteString sha256_ = ByteString.EMPTY;

                public static final Descriptors.Descriptor getDescriptor() {
                    return internal_static_android_bundle_Library_Digests_descriptor;
                }

                @Override
                protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                    return internal_static_android_bundle_Library_Digests_fieldAccessorTable.ensureFieldAccessorsInitialized(Digests.class, Builder.class);
                }

                private Builder() {
                    this.maybeForceBuilderInitialization();
                }

                private Builder(GeneratedMessageV3.BuilderParent parent) {
                    super(parent);
                    this.maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    if (alwaysUseFieldBuilders) {
                        // empty if block
                    }
                }

                @Override
                public Builder clear() {
                    super.clear();
                    this.sha256_ = ByteString.EMPTY;
                    this.bitField0_ &= 0xFFFFFFFE;
                    return this;
                }

                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return internal_static_android_bundle_Library_Digests_descriptor;
                }

                @Override
                public Digests getDefaultInstanceForType() {
                    return Digests.getDefaultInstance();
                }

                @Override
                public Digests build() {
                    Digests result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw Builder.newUninitializedMessageException(result);
                    }
                    return result;
                }

                @Override
                public Digests buildPartial() {
                    Digests result = new Digests(this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ |= 1;
                    }
                    result.sha256_ = this.sha256_;
                    result.bitField0_ = to_bitField0_;
                    this.onBuilt();
                    return result;
                }

                @Override
                public Builder clone() {
                    return (Builder)super.clone();
                }

                @Override
                public Builder setField(Descriptors.FieldDescriptor field, Object value) {
                    return (Builder)super.setField(field, value);
                }

                @Override
                public Builder clearField(Descriptors.FieldDescriptor field) {
                    return (Builder)super.clearField(field);
                }

                @Override
                public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                    return (Builder)super.clearOneof(oneof);
                }

                @Override
                public Builder setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
                    return (Builder)super.setRepeatedField(field, index, value);
                }

                @Override
                public Builder addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
                    return (Builder)super.addRepeatedField(field, value);
                }

                @Override
                public Builder mergeFrom(Message other) {
                    if (other instanceof Digests) {
                        return this.mergeFrom((Digests)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(Digests other) {
                    if (other == Digests.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasSha256()) {
                        this.setSha256(other.getSha256());
                    }
                    this.mergeUnknownFields(other.unknownFields);
                    this.onChanged();
                    return this;
                }

                @Override
                public final boolean isInitialized() {
                    return true;
                }

                @Override
                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    Digests parsedMessage = null;
                    try {
                        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e2) {
                        parsedMessage = (Digests)e2.getUnfinishedMessage();
                        throw e2.unwrapIOException();
                    }
                    finally {
                        if (parsedMessage != null) {
                            this.mergeFrom(parsedMessage);
                        }
                    }
                    return this;
                }

                @Override
                public boolean hasSha256() {
                    return (this.bitField0_ & 1) == 1;
                }

                @Override
                public ByteString getSha256() {
                    return this.sha256_;
                }

                public Builder setSha256(ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 1;
                    this.sha256_ = value;
                    this.onChanged();
                    return this;
                }

                public Builder clearSha256() {
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.sha256_ = Digests.getDefaultInstance().getSha256();
                    this.onChanged();
                    return this;
                }

                @Override
                public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                    return (Builder)super.setUnknownFields(unknownFields);
                }

                @Override
                public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                    return (Builder)super.mergeUnknownFields(unknownFields);
                }
            }
        }

        public static interface DigestsOrBuilder
        extends MessageOrBuilder {
            public boolean hasSha256();

            public ByteString getSha256();
        }
    }

    public static interface LibraryOrBuilder
    extends MessageOrBuilder {
        public boolean hasMavenLibrary();

        public MavenLibrary getMavenLibrary();

        public MavenLibraryOrBuilder getMavenLibraryOrBuilder();

        public boolean hasDigests();

        public Library.Digests getDigests();

        public Library.DigestsOrBuilder getDigestsOrBuilder();

        public Library.LibraryOneofCase getLibraryOneofCase();
    }

    public static final class ModuleDependencies
    extends GeneratedMessageV3
    implements ModuleDependenciesOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int MODULE_NAME_FIELD_NUMBER = 1;
        private volatile Object moduleName_;
        public static final int DEPENDENCY_INDEX_FIELD_NUMBER = 2;
        private List<Integer> dependencyIndex_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ModuleDependencies DEFAULT_INSTANCE = new ModuleDependencies();
        @Deprecated
        public static final Parser<ModuleDependencies> PARSER = new AbstractParser<ModuleDependencies>(){

            @Override
            public ModuleDependencies parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ModuleDependencies(input, extensionRegistry);
            }
        };

        private ModuleDependencies(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ModuleDependencies() {
            this.moduleName_ = "";
            this.dependencyIndex_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ModuleDependencies(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block12: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block12;
                            done = true;
                            continue block12;
                        }
                        case 10: {
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.moduleName_ = bs;
                            continue block12;
                        }
                        case 16: {
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.dependencyIndex_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 2;
                            }
                            this.dependencyIndex_.add(input.readInt32());
                            continue block12;
                        }
                        case 18: 
                    }
                    int length = input.readRawVarint32();
                    int limit = input.pushLimit(length);
                    if ((mutable_bitField0_ & 2) != 2 && input.getBytesUntilLimit() > 0) {
                        this.dependencyIndex_ = new ArrayList<Integer>();
                        mutable_bitField0_ |= 2;
                    }
                    while (input.getBytesUntilLimit() > 0) {
                        this.dependencyIndex_.add(input.readInt32());
                    }
                    input.popLimit(limit);
                }
            }
            catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(this);
            }
            catch (IOException e3) {
                throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 2) == 2) {
                    this.dependencyIndex_ = Collections.unmodifiableList(this.dependencyIndex_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_ModuleDependencies_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ModuleDependencies_fieldAccessorTable.ensureFieldAccessorsInitialized(ModuleDependencies.class, Builder.class);
        }

        @Override
        public boolean hasModuleName() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public String getModuleName() {
            Object ref = this.moduleName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.moduleName_ = s3;
            }
            return s3;
        }

        @Override
        public ByteString getModuleNameBytes() {
            Object ref = this.moduleName_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.moduleName_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public List<Integer> getDependencyIndexList() {
            return this.dependencyIndex_;
        }

        @Override
        public int getDependencyIndexCount() {
            return this.dependencyIndex_.size();
        }

        @Override
        public int getDependencyIndex(int index) {
            return this.dependencyIndex_.get(index);
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                GeneratedMessageV3.writeString(output, 1, this.moduleName_);
            }
            for (int i2 = 0; i2 < this.dependencyIndex_.size(); ++i2) {
                output.writeInt32(2, this.dependencyIndex_.get(i2));
            }
            this.unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += GeneratedMessageV3.computeStringSize(1, this.moduleName_);
            }
            int dataSize = 0;
            for (int i2 = 0; i2 < this.dependencyIndex_.size(); ++i2) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.dependencyIndex_.get(i2));
            }
            size += dataSize;
            size += 1 * this.getDependencyIndexList().size();
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ModuleDependencies)) {
                return super.equals(obj);
            }
            ModuleDependencies other = (ModuleDependencies)obj;
            boolean result = true;
            boolean bl = result = result && this.hasModuleName() == other.hasModuleName();
            if (this.hasModuleName()) {
                result = result && this.getModuleName().equals(other.getModuleName());
            }
            result = result && this.getDependencyIndexList().equals(other.getDependencyIndexList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ModuleDependencies.getDescriptor().hashCode();
            if (this.hasModuleName()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getModuleName().hashCode();
            }
            if (this.getDependencyIndexCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getDependencyIndexList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ModuleDependencies parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ModuleDependencies parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ModuleDependencies parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ModuleDependencies parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ModuleDependencies parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ModuleDependencies parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ModuleDependencies parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ModuleDependencies parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ModuleDependencies parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ModuleDependencies parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ModuleDependencies parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ModuleDependencies parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ModuleDependencies.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ModuleDependencies prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static ModuleDependencies getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ModuleDependencies> parser() {
            return PARSER;
        }

        public Parser<ModuleDependencies> getParserForType() {
            return PARSER;
        }

        @Override
        public ModuleDependencies getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ModuleDependenciesOrBuilder {
            private int bitField0_;
            private Object moduleName_ = "";
            private List<Integer> dependencyIndex_ = Collections.emptyList();

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ModuleDependencies_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ModuleDependencies_fieldAccessorTable.ensureFieldAccessorsInitialized(ModuleDependencies.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    // empty if block
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.moduleName_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                this.dependencyIndex_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ModuleDependencies_descriptor;
            }

            @Override
            public ModuleDependencies getDefaultInstanceForType() {
                return ModuleDependencies.getDefaultInstance();
            }

            @Override
            public ModuleDependencies build() {
                ModuleDependencies result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ModuleDependencies buildPartial() {
                ModuleDependencies result = new ModuleDependencies(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.moduleName_ = this.moduleName_;
                if ((this.bitField0_ & 2) == 2) {
                    this.dependencyIndex_ = Collections.unmodifiableList(this.dependencyIndex_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.dependencyIndex_ = this.dependencyIndex_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder)super.setField(field, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field) {
                return (Builder)super.clearField(field);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
                return (Builder)super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder)super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof ModuleDependencies) {
                    return this.mergeFrom((ModuleDependencies)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ModuleDependencies other) {
                if (other == ModuleDependencies.getDefaultInstance()) {
                    return this;
                }
                if (other.hasModuleName()) {
                    this.bitField0_ |= 1;
                    this.moduleName_ = other.moduleName_;
                    this.onChanged();
                }
                if (!other.dependencyIndex_.isEmpty()) {
                    if (this.dependencyIndex_.isEmpty()) {
                        this.dependencyIndex_ = other.dependencyIndex_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureDependencyIndexIsMutable();
                        this.dependencyIndex_.addAll(other.dependencyIndex_);
                    }
                    this.onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ModuleDependencies parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ModuleDependencies)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean hasModuleName() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public String getModuleName() {
                Object ref = this.moduleName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.moduleName_ = s3;
                    }
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getModuleNameBytes() {
                Object ref = this.moduleName_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.moduleName_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setModuleName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearModuleName() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.moduleName_ = ModuleDependencies.getDefaultInstance().getModuleName();
                this.onChanged();
                return this;
            }

            public Builder setModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            private void ensureDependencyIndexIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.dependencyIndex_ = new ArrayList<Integer>(this.dependencyIndex_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<Integer> getDependencyIndexList() {
                return Collections.unmodifiableList(this.dependencyIndex_);
            }

            @Override
            public int getDependencyIndexCount() {
                return this.dependencyIndex_.size();
            }

            @Override
            public int getDependencyIndex(int index) {
                return this.dependencyIndex_.get(index);
            }

            public Builder setDependencyIndex(int index, int value) {
                this.ensureDependencyIndexIsMutable();
                this.dependencyIndex_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addDependencyIndex(int value) {
                this.ensureDependencyIndexIsMutable();
                this.dependencyIndex_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllDependencyIndex(Iterable<? extends Integer> values2) {
                this.ensureDependencyIndexIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.dependencyIndex_);
                this.onChanged();
                return this;
            }

            public Builder clearDependencyIndex() {
                this.dependencyIndex_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface ModuleDependenciesOrBuilder
    extends MessageOrBuilder {
        public boolean hasModuleName();

        public String getModuleName();

        public ByteString getModuleNameBytes();

        public List<Integer> getDependencyIndexList();

        public int getDependencyIndexCount();

        public int getDependencyIndex(int var1);
    }

    public static final class LibraryDependencies
    extends GeneratedMessageV3
    implements LibraryDependenciesOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int LIBRARY_INDEX_FIELD_NUMBER = 1;
        private int libraryIndex_;
        public static final int LIBRARY_DEP_INDEX_FIELD_NUMBER = 2;
        private List<Integer> libraryDepIndex_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final LibraryDependencies DEFAULT_INSTANCE = new LibraryDependencies();
        @Deprecated
        public static final Parser<LibraryDependencies> PARSER = new AbstractParser<LibraryDependencies>(){

            @Override
            public LibraryDependencies parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new LibraryDependencies(input, extensionRegistry);
            }
        };

        private LibraryDependencies(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private LibraryDependencies() {
            this.libraryIndex_ = 0;
            this.libraryDepIndex_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private LibraryDependencies(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block12: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block12;
                            done = true;
                            continue block12;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.libraryIndex_ = input.readInt32();
                            continue block12;
                        }
                        case 16: {
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.libraryDepIndex_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 2;
                            }
                            this.libraryDepIndex_.add(input.readInt32());
                            continue block12;
                        }
                        case 18: 
                    }
                    int length = input.readRawVarint32();
                    int limit = input.pushLimit(length);
                    if ((mutable_bitField0_ & 2) != 2 && input.getBytesUntilLimit() > 0) {
                        this.libraryDepIndex_ = new ArrayList<Integer>();
                        mutable_bitField0_ |= 2;
                    }
                    while (input.getBytesUntilLimit() > 0) {
                        this.libraryDepIndex_.add(input.readInt32());
                    }
                    input.popLimit(limit);
                }
            }
            catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(this);
            }
            catch (IOException e3) {
                throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 2) == 2) {
                    this.libraryDepIndex_ = Collections.unmodifiableList(this.libraryDepIndex_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_LibraryDependencies_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_LibraryDependencies_fieldAccessorTable.ensureFieldAccessorsInitialized(LibraryDependencies.class, Builder.class);
        }

        @Override
        public boolean hasLibraryIndex() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public int getLibraryIndex() {
            return this.libraryIndex_;
        }

        @Override
        public List<Integer> getLibraryDepIndexList() {
            return this.libraryDepIndex_;
        }

        @Override
        public int getLibraryDepIndexCount() {
            return this.libraryDepIndex_.size();
        }

        @Override
        public int getLibraryDepIndex(int index) {
            return this.libraryDepIndex_.get(index);
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.libraryIndex_);
            }
            for (int i2 = 0; i2 < this.libraryDepIndex_.size(); ++i2) {
                output.writeInt32(2, this.libraryDepIndex_.get(i2));
            }
            this.unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeInt32Size(1, this.libraryIndex_);
            }
            int dataSize = 0;
            for (int i2 = 0; i2 < this.libraryDepIndex_.size(); ++i2) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.libraryDepIndex_.get(i2));
            }
            size += dataSize;
            size += 1 * this.getLibraryDepIndexList().size();
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LibraryDependencies)) {
                return super.equals(obj);
            }
            LibraryDependencies other = (LibraryDependencies)obj;
            boolean result = true;
            boolean bl = result = result && this.hasLibraryIndex() == other.hasLibraryIndex();
            if (this.hasLibraryIndex()) {
                result = result && this.getLibraryIndex() == other.getLibraryIndex();
            }
            result = result && this.getLibraryDepIndexList().equals(other.getLibraryDepIndexList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + LibraryDependencies.getDescriptor().hashCode();
            if (this.hasLibraryIndex()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getLibraryIndex();
            }
            if (this.getLibraryDepIndexCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getLibraryDepIndexList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static LibraryDependencies parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LibraryDependencies parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LibraryDependencies parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LibraryDependencies parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LibraryDependencies parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LibraryDependencies parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LibraryDependencies parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static LibraryDependencies parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static LibraryDependencies parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static LibraryDependencies parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static LibraryDependencies parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static LibraryDependencies parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return LibraryDependencies.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(LibraryDependencies prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static LibraryDependencies getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LibraryDependencies> parser() {
            return PARSER;
        }

        public Parser<LibraryDependencies> getParserForType() {
            return PARSER;
        }

        @Override
        public LibraryDependencies getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements LibraryDependenciesOrBuilder {
            private int bitField0_;
            private int libraryIndex_;
            private List<Integer> libraryDepIndex_ = Collections.emptyList();

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_LibraryDependencies_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_LibraryDependencies_fieldAccessorTable.ensureFieldAccessorsInitialized(LibraryDependencies.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    // empty if block
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.libraryIndex_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                this.libraryDepIndex_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_LibraryDependencies_descriptor;
            }

            @Override
            public LibraryDependencies getDefaultInstanceForType() {
                return LibraryDependencies.getDefaultInstance();
            }

            @Override
            public LibraryDependencies build() {
                LibraryDependencies result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public LibraryDependencies buildPartial() {
                LibraryDependencies result = new LibraryDependencies(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= 1;
                }
                result.libraryIndex_ = this.libraryIndex_;
                if ((this.bitField0_ & 2) == 2) {
                    this.libraryDepIndex_ = Collections.unmodifiableList(this.libraryDepIndex_);
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.libraryDepIndex_ = this.libraryDepIndex_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder)super.setField(field, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field) {
                return (Builder)super.clearField(field);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
                return (Builder)super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder)super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof LibraryDependencies) {
                    return this.mergeFrom((LibraryDependencies)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(LibraryDependencies other) {
                if (other == LibraryDependencies.getDefaultInstance()) {
                    return this;
                }
                if (other.hasLibraryIndex()) {
                    this.setLibraryIndex(other.getLibraryIndex());
                }
                if (!other.libraryDepIndex_.isEmpty()) {
                    if (this.libraryDepIndex_.isEmpty()) {
                        this.libraryDepIndex_ = other.libraryDepIndex_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureLibraryDepIndexIsMutable();
                        this.libraryDepIndex_.addAll(other.libraryDepIndex_);
                    }
                    this.onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                LibraryDependencies parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (LibraryDependencies)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean hasLibraryIndex() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public int getLibraryIndex() {
                return this.libraryIndex_;
            }

            public Builder setLibraryIndex(int value) {
                this.bitField0_ |= 1;
                this.libraryIndex_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearLibraryIndex() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.libraryIndex_ = 0;
                this.onChanged();
                return this;
            }

            private void ensureLibraryDepIndexIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.libraryDepIndex_ = new ArrayList<Integer>(this.libraryDepIndex_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<Integer> getLibraryDepIndexList() {
                return Collections.unmodifiableList(this.libraryDepIndex_);
            }

            @Override
            public int getLibraryDepIndexCount() {
                return this.libraryDepIndex_.size();
            }

            @Override
            public int getLibraryDepIndex(int index) {
                return this.libraryDepIndex_.get(index);
            }

            public Builder setLibraryDepIndex(int index, int value) {
                this.ensureLibraryDepIndexIsMutable();
                this.libraryDepIndex_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addLibraryDepIndex(int value) {
                this.ensureLibraryDepIndexIsMutable();
                this.libraryDepIndex_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllLibraryDepIndex(Iterable<? extends Integer> values2) {
                this.ensureLibraryDepIndexIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.libraryDepIndex_);
                this.onChanged();
                return this;
            }

            public Builder clearLibraryDepIndex() {
                this.libraryDepIndex_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface LibraryDependenciesOrBuilder
    extends MessageOrBuilder {
        public boolean hasLibraryIndex();

        public int getLibraryIndex();

        public List<Integer> getLibraryDepIndexList();

        public int getLibraryDepIndexCount();

        public int getLibraryDepIndex(int var1);
    }

    public static final class AppDependencies
    extends GeneratedMessageV3
    implements AppDependenciesOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int LIBRARY_FIELD_NUMBER = 1;
        private List<Library> library_;
        public static final int LIBRARY_DEPENDENCIES_FIELD_NUMBER = 2;
        private List<LibraryDependencies> libraryDependencies_;
        public static final int MODULE_DEPENDENCIES_FIELD_NUMBER = 3;
        private List<ModuleDependencies> moduleDependencies_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final AppDependencies DEFAULT_INSTANCE = new AppDependencies();
        @Deprecated
        public static final Parser<AppDependencies> PARSER = new AbstractParser<AppDependencies>(){

            @Override
            public AppDependencies parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AppDependencies(input, extensionRegistry);
            }
        };

        private AppDependencies(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private AppDependencies() {
            this.library_ = Collections.emptyList();
            this.libraryDependencies_ = Collections.emptyList();
            this.moduleDependencies_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AppDependencies(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block12: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block12;
                            done = true;
                            continue block12;
                        }
                        case 10: {
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.library_ = new ArrayList<Library>();
                                mutable_bitField0_ |= 1;
                            }
                            this.library_.add(input.readMessage(Library.PARSER, extensionRegistry));
                            continue block12;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.libraryDependencies_ = new ArrayList<LibraryDependencies>();
                                mutable_bitField0_ |= 2;
                            }
                            this.libraryDependencies_.add(input.readMessage(LibraryDependencies.PARSER, extensionRegistry));
                            continue block12;
                        }
                        case 26: 
                    }
                    if ((mutable_bitField0_ & 4) != 4) {
                        this.moduleDependencies_ = new ArrayList<ModuleDependencies>();
                        mutable_bitField0_ |= 4;
                    }
                    this.moduleDependencies_.add(input.readMessage(ModuleDependencies.PARSER, extensionRegistry));
                }
            }
            catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(this);
            }
            catch (IOException e3) {
                throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
            }
            finally {
                if (mutable_bitField0_ & true) {
                    this.library_ = Collections.unmodifiableList(this.library_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.libraryDependencies_ = Collections.unmodifiableList(this.libraryDependencies_);
                }
                if ((mutable_bitField0_ & 4) == 4) {
                    this.moduleDependencies_ = Collections.unmodifiableList(this.moduleDependencies_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_AppDependencies_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_AppDependencies_fieldAccessorTable.ensureFieldAccessorsInitialized(AppDependencies.class, Builder.class);
        }

        @Override
        public List<Library> getLibraryList() {
            return this.library_;
        }

        @Override
        public List<? extends LibraryOrBuilder> getLibraryOrBuilderList() {
            return this.library_;
        }

        @Override
        public int getLibraryCount() {
            return this.library_.size();
        }

        @Override
        public Library getLibrary(int index) {
            return this.library_.get(index);
        }

        @Override
        public LibraryOrBuilder getLibraryOrBuilder(int index) {
            return this.library_.get(index);
        }

        @Override
        public List<LibraryDependencies> getLibraryDependenciesList() {
            return this.libraryDependencies_;
        }

        @Override
        public List<? extends LibraryDependenciesOrBuilder> getLibraryDependenciesOrBuilderList() {
            return this.libraryDependencies_;
        }

        @Override
        public int getLibraryDependenciesCount() {
            return this.libraryDependencies_.size();
        }

        @Override
        public LibraryDependencies getLibraryDependencies(int index) {
            return this.libraryDependencies_.get(index);
        }

        @Override
        public LibraryDependenciesOrBuilder getLibraryDependenciesOrBuilder(int index) {
            return this.libraryDependencies_.get(index);
        }

        @Override
        public List<ModuleDependencies> getModuleDependenciesList() {
            return this.moduleDependencies_;
        }

        @Override
        public List<? extends ModuleDependenciesOrBuilder> getModuleDependenciesOrBuilderList() {
            return this.moduleDependencies_;
        }

        @Override
        public int getModuleDependenciesCount() {
            return this.moduleDependencies_.size();
        }

        @Override
        public ModuleDependencies getModuleDependencies(int index) {
            return this.moduleDependencies_.get(index);
        }

        @Override
        public ModuleDependenciesOrBuilder getModuleDependenciesOrBuilder(int index) {
            return this.moduleDependencies_.get(index);
        }

        @Override
        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(CodedOutputStream output) throws IOException {
            int i2;
            for (i2 = 0; i2 < this.library_.size(); ++i2) {
                output.writeMessage(1, this.library_.get(i2));
            }
            for (i2 = 0; i2 < this.libraryDependencies_.size(); ++i2) {
                output.writeMessage(2, this.libraryDependencies_.get(i2));
            }
            for (i2 = 0; i2 < this.moduleDependencies_.size(); ++i2) {
                output.writeMessage(3, this.moduleDependencies_.get(i2));
            }
            this.unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int i2;
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (i2 = 0; i2 < this.library_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.library_.get(i2));
            }
            for (i2 = 0; i2 < this.libraryDependencies_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.libraryDependencies_.get(i2));
            }
            for (i2 = 0; i2 < this.moduleDependencies_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(3, this.moduleDependencies_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AppDependencies)) {
                return super.equals(obj);
            }
            AppDependencies other = (AppDependencies)obj;
            boolean result = true;
            result = result && this.getLibraryList().equals(other.getLibraryList());
            result = result && this.getLibraryDependenciesList().equals(other.getLibraryDependenciesList());
            result = result && this.getModuleDependenciesList().equals(other.getModuleDependenciesList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + AppDependencies.getDescriptor().hashCode();
            if (this.getLibraryCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getLibraryList().hashCode();
            }
            if (this.getLibraryDependenciesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getLibraryDependenciesList().hashCode();
            }
            if (this.getModuleDependenciesCount() > 0) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getModuleDependenciesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static AppDependencies parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AppDependencies parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AppDependencies parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AppDependencies parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AppDependencies parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AppDependencies parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AppDependencies parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AppDependencies parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AppDependencies parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AppDependencies parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AppDependencies parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AppDependencies parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return AppDependencies.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AppDependencies prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        public static AppDependencies getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AppDependencies> parser() {
            return PARSER;
        }

        public Parser<AppDependencies> getParserForType() {
            return PARSER;
        }

        @Override
        public AppDependencies getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AppDependenciesOrBuilder {
            private int bitField0_;
            private List<Library> library_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Library, Library.Builder, LibraryOrBuilder> libraryBuilder_;
            private List<LibraryDependencies> libraryDependencies_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<LibraryDependencies, LibraryDependencies.Builder, LibraryDependenciesOrBuilder> libraryDependenciesBuilder_;
            private List<ModuleDependencies> moduleDependencies_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<ModuleDependencies, ModuleDependencies.Builder, ModuleDependenciesOrBuilder> moduleDependenciesBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_AppDependencies_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_AppDependencies_fieldAccessorTable.ensureFieldAccessorsInitialized(AppDependencies.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    this.getLibraryFieldBuilder();
                    this.getLibraryDependenciesFieldBuilder();
                    this.getModuleDependenciesFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.libraryBuilder_ == null) {
                    this.library_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.libraryBuilder_.clear();
                }
                if (this.libraryDependenciesBuilder_ == null) {
                    this.libraryDependencies_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.libraryDependenciesBuilder_.clear();
                }
                if (this.moduleDependenciesBuilder_ == null) {
                    this.moduleDependencies_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFB;
                } else {
                    this.moduleDependenciesBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_AppDependencies_descriptor;
            }

            @Override
            public AppDependencies getDefaultInstanceForType() {
                return AppDependencies.getDefaultInstance();
            }

            @Override
            public AppDependencies build() {
                AppDependencies result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public AppDependencies buildPartial() {
                AppDependencies result = new AppDependencies(this);
                int from_bitField0_ = this.bitField0_;
                if (this.libraryBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.library_ = Collections.unmodifiableList(this.library_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.library_ = this.library_;
                } else {
                    result.library_ = this.libraryBuilder_.build();
                }
                if (this.libraryDependenciesBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.libraryDependencies_ = Collections.unmodifiableList(this.libraryDependencies_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.libraryDependencies_ = this.libraryDependencies_;
                } else {
                    result.libraryDependencies_ = this.libraryDependenciesBuilder_.build();
                }
                if (this.moduleDependenciesBuilder_ == null) {
                    if ((this.bitField0_ & 4) == 4) {
                        this.moduleDependencies_ = Collections.unmodifiableList(this.moduleDependencies_);
                        this.bitField0_ &= 0xFFFFFFFB;
                    }
                    result.moduleDependencies_ = this.moduleDependencies_;
                } else {
                    result.moduleDependencies_ = this.moduleDependenciesBuilder_.build();
                }
                this.onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return (Builder)super.clone();
            }

            @Override
            public Builder setField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder)super.setField(field, value);
            }

            @Override
            public Builder clearField(Descriptors.FieldDescriptor field) {
                return (Builder)super.clearField(field);
            }

            @Override
            public Builder clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
                return (Builder)super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder)super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(Message other) {
                if (other instanceof AppDependencies) {
                    return this.mergeFrom((AppDependencies)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AppDependencies other) {
                if (other == AppDependencies.getDefaultInstance()) {
                    return this;
                }
                if (this.libraryBuilder_ == null) {
                    if (!other.library_.isEmpty()) {
                        if (this.library_.isEmpty()) {
                            this.library_ = other.library_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureLibraryIsMutable();
                            this.library_.addAll(other.library_);
                        }
                        this.onChanged();
                    }
                } else if (!other.library_.isEmpty()) {
                    if (this.libraryBuilder_.isEmpty()) {
                        this.libraryBuilder_.dispose();
                        this.libraryBuilder_ = null;
                        this.library_ = other.library_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.libraryBuilder_ = alwaysUseFieldBuilders ? this.getLibraryFieldBuilder() : null;
                    } else {
                        this.libraryBuilder_.addAllMessages(other.library_);
                    }
                }
                if (this.libraryDependenciesBuilder_ == null) {
                    if (!other.libraryDependencies_.isEmpty()) {
                        if (this.libraryDependencies_.isEmpty()) {
                            this.libraryDependencies_ = other.libraryDependencies_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureLibraryDependenciesIsMutable();
                            this.libraryDependencies_.addAll(other.libraryDependencies_);
                        }
                        this.onChanged();
                    }
                } else if (!other.libraryDependencies_.isEmpty()) {
                    if (this.libraryDependenciesBuilder_.isEmpty()) {
                        this.libraryDependenciesBuilder_.dispose();
                        this.libraryDependenciesBuilder_ = null;
                        this.libraryDependencies_ = other.libraryDependencies_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.libraryDependenciesBuilder_ = alwaysUseFieldBuilders ? this.getLibraryDependenciesFieldBuilder() : null;
                    } else {
                        this.libraryDependenciesBuilder_.addAllMessages(other.libraryDependencies_);
                    }
                }
                if (this.moduleDependenciesBuilder_ == null) {
                    if (!other.moduleDependencies_.isEmpty()) {
                        if (this.moduleDependencies_.isEmpty()) {
                            this.moduleDependencies_ = other.moduleDependencies_;
                            this.bitField0_ &= 0xFFFFFFFB;
                        } else {
                            this.ensureModuleDependenciesIsMutable();
                            this.moduleDependencies_.addAll(other.moduleDependencies_);
                        }
                        this.onChanged();
                    }
                } else if (!other.moduleDependencies_.isEmpty()) {
                    if (this.moduleDependenciesBuilder_.isEmpty()) {
                        this.moduleDependenciesBuilder_.dispose();
                        this.moduleDependenciesBuilder_ = null;
                        this.moduleDependencies_ = other.moduleDependencies_;
                        this.bitField0_ &= 0xFFFFFFFB;
                        this.moduleDependenciesBuilder_ = alwaysUseFieldBuilders ? this.getModuleDependenciesFieldBuilder() : null;
                    } else {
                        this.moduleDependenciesBuilder_.addAllMessages(other.moduleDependencies_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                AppDependencies parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (AppDependencies)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureLibraryIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.library_ = new ArrayList<Library>(this.library_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<Library> getLibraryList() {
                if (this.libraryBuilder_ == null) {
                    return Collections.unmodifiableList(this.library_);
                }
                return this.libraryBuilder_.getMessageList();
            }

            @Override
            public int getLibraryCount() {
                if (this.libraryBuilder_ == null) {
                    return this.library_.size();
                }
                return this.libraryBuilder_.getCount();
            }

            @Override
            public Library getLibrary(int index) {
                if (this.libraryBuilder_ == null) {
                    return this.library_.get(index);
                }
                return this.libraryBuilder_.getMessage(index);
            }

            public Builder setLibrary(int index, Library value) {
                if (this.libraryBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureLibraryIsMutable();
                    this.library_.set(index, value);
                    this.onChanged();
                } else {
                    this.libraryBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setLibrary(int index, Library.Builder builderForValue) {
                if (this.libraryBuilder_ == null) {
                    this.ensureLibraryIsMutable();
                    this.library_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.libraryBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addLibrary(Library value) {
                if (this.libraryBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureLibraryIsMutable();
                    this.library_.add(value);
                    this.onChanged();
                } else {
                    this.libraryBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addLibrary(int index, Library value) {
                if (this.libraryBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureLibraryIsMutable();
                    this.library_.add(index, value);
                    this.onChanged();
                } else {
                    this.libraryBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addLibrary(Library.Builder builderForValue) {
                if (this.libraryBuilder_ == null) {
                    this.ensureLibraryIsMutable();
                    this.library_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.libraryBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addLibrary(int index, Library.Builder builderForValue) {
                if (this.libraryBuilder_ == null) {
                    this.ensureLibraryIsMutable();
                    this.library_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.libraryBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllLibrary(Iterable<? extends Library> values2) {
                if (this.libraryBuilder_ == null) {
                    this.ensureLibraryIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.library_);
                    this.onChanged();
                } else {
                    this.libraryBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearLibrary() {
                if (this.libraryBuilder_ == null) {
                    this.library_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.libraryBuilder_.clear();
                }
                return this;
            }

            public Builder removeLibrary(int index) {
                if (this.libraryBuilder_ == null) {
                    this.ensureLibraryIsMutable();
                    this.library_.remove(index);
                    this.onChanged();
                } else {
                    this.libraryBuilder_.remove(index);
                }
                return this;
            }

            public Library.Builder getLibraryBuilder(int index) {
                return this.getLibraryFieldBuilder().getBuilder(index);
            }

            @Override
            public LibraryOrBuilder getLibraryOrBuilder(int index) {
                if (this.libraryBuilder_ == null) {
                    return this.library_.get(index);
                }
                return this.libraryBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends LibraryOrBuilder> getLibraryOrBuilderList() {
                if (this.libraryBuilder_ != null) {
                    return this.libraryBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.library_);
            }

            public Library.Builder addLibraryBuilder() {
                return this.getLibraryFieldBuilder().addBuilder(Library.getDefaultInstance());
            }

            public Library.Builder addLibraryBuilder(int index) {
                return this.getLibraryFieldBuilder().addBuilder(index, Library.getDefaultInstance());
            }

            public List<Library.Builder> getLibraryBuilderList() {
                return this.getLibraryFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Library, Library.Builder, LibraryOrBuilder> getLibraryFieldBuilder() {
                if (this.libraryBuilder_ == null) {
                    this.libraryBuilder_ = new RepeatedFieldBuilderV3(this.library_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.library_ = null;
                }
                return this.libraryBuilder_;
            }

            private void ensureLibraryDependenciesIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.libraryDependencies_ = new ArrayList<LibraryDependencies>(this.libraryDependencies_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<LibraryDependencies> getLibraryDependenciesList() {
                if (this.libraryDependenciesBuilder_ == null) {
                    return Collections.unmodifiableList(this.libraryDependencies_);
                }
                return this.libraryDependenciesBuilder_.getMessageList();
            }

            @Override
            public int getLibraryDependenciesCount() {
                if (this.libraryDependenciesBuilder_ == null) {
                    return this.libraryDependencies_.size();
                }
                return this.libraryDependenciesBuilder_.getCount();
            }

            @Override
            public LibraryDependencies getLibraryDependencies(int index) {
                if (this.libraryDependenciesBuilder_ == null) {
                    return this.libraryDependencies_.get(index);
                }
                return this.libraryDependenciesBuilder_.getMessage(index);
            }

            public Builder setLibraryDependencies(int index, LibraryDependencies value) {
                if (this.libraryDependenciesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureLibraryDependenciesIsMutable();
                    this.libraryDependencies_.set(index, value);
                    this.onChanged();
                } else {
                    this.libraryDependenciesBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setLibraryDependencies(int index, LibraryDependencies.Builder builderForValue) {
                if (this.libraryDependenciesBuilder_ == null) {
                    this.ensureLibraryDependenciesIsMutable();
                    this.libraryDependencies_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.libraryDependenciesBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addLibraryDependencies(LibraryDependencies value) {
                if (this.libraryDependenciesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureLibraryDependenciesIsMutable();
                    this.libraryDependencies_.add(value);
                    this.onChanged();
                } else {
                    this.libraryDependenciesBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addLibraryDependencies(int index, LibraryDependencies value) {
                if (this.libraryDependenciesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureLibraryDependenciesIsMutable();
                    this.libraryDependencies_.add(index, value);
                    this.onChanged();
                } else {
                    this.libraryDependenciesBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addLibraryDependencies(LibraryDependencies.Builder builderForValue) {
                if (this.libraryDependenciesBuilder_ == null) {
                    this.ensureLibraryDependenciesIsMutable();
                    this.libraryDependencies_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.libraryDependenciesBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addLibraryDependencies(int index, LibraryDependencies.Builder builderForValue) {
                if (this.libraryDependenciesBuilder_ == null) {
                    this.ensureLibraryDependenciesIsMutable();
                    this.libraryDependencies_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.libraryDependenciesBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllLibraryDependencies(Iterable<? extends LibraryDependencies> values2) {
                if (this.libraryDependenciesBuilder_ == null) {
                    this.ensureLibraryDependenciesIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.libraryDependencies_);
                    this.onChanged();
                } else {
                    this.libraryDependenciesBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearLibraryDependencies() {
                if (this.libraryDependenciesBuilder_ == null) {
                    this.libraryDependencies_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.libraryDependenciesBuilder_.clear();
                }
                return this;
            }

            public Builder removeLibraryDependencies(int index) {
                if (this.libraryDependenciesBuilder_ == null) {
                    this.ensureLibraryDependenciesIsMutable();
                    this.libraryDependencies_.remove(index);
                    this.onChanged();
                } else {
                    this.libraryDependenciesBuilder_.remove(index);
                }
                return this;
            }

            public LibraryDependencies.Builder getLibraryDependenciesBuilder(int index) {
                return this.getLibraryDependenciesFieldBuilder().getBuilder(index);
            }

            @Override
            public LibraryDependenciesOrBuilder getLibraryDependenciesOrBuilder(int index) {
                if (this.libraryDependenciesBuilder_ == null) {
                    return this.libraryDependencies_.get(index);
                }
                return this.libraryDependenciesBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends LibraryDependenciesOrBuilder> getLibraryDependenciesOrBuilderList() {
                if (this.libraryDependenciesBuilder_ != null) {
                    return this.libraryDependenciesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.libraryDependencies_);
            }

            public LibraryDependencies.Builder addLibraryDependenciesBuilder() {
                return this.getLibraryDependenciesFieldBuilder().addBuilder(LibraryDependencies.getDefaultInstance());
            }

            public LibraryDependencies.Builder addLibraryDependenciesBuilder(int index) {
                return this.getLibraryDependenciesFieldBuilder().addBuilder(index, LibraryDependencies.getDefaultInstance());
            }

            public List<LibraryDependencies.Builder> getLibraryDependenciesBuilderList() {
                return this.getLibraryDependenciesFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<LibraryDependencies, LibraryDependencies.Builder, LibraryDependenciesOrBuilder> getLibraryDependenciesFieldBuilder() {
                if (this.libraryDependenciesBuilder_ == null) {
                    this.libraryDependenciesBuilder_ = new RepeatedFieldBuilderV3(this.libraryDependencies_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.libraryDependencies_ = null;
                }
                return this.libraryDependenciesBuilder_;
            }

            private void ensureModuleDependenciesIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.moduleDependencies_ = new ArrayList<ModuleDependencies>(this.moduleDependencies_);
                    this.bitField0_ |= 4;
                }
            }

            @Override
            public List<ModuleDependencies> getModuleDependenciesList() {
                if (this.moduleDependenciesBuilder_ == null) {
                    return Collections.unmodifiableList(this.moduleDependencies_);
                }
                return this.moduleDependenciesBuilder_.getMessageList();
            }

            @Override
            public int getModuleDependenciesCount() {
                if (this.moduleDependenciesBuilder_ == null) {
                    return this.moduleDependencies_.size();
                }
                return this.moduleDependenciesBuilder_.getCount();
            }

            @Override
            public ModuleDependencies getModuleDependencies(int index) {
                if (this.moduleDependenciesBuilder_ == null) {
                    return this.moduleDependencies_.get(index);
                }
                return this.moduleDependenciesBuilder_.getMessage(index);
            }

            public Builder setModuleDependencies(int index, ModuleDependencies value) {
                if (this.moduleDependenciesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureModuleDependenciesIsMutable();
                    this.moduleDependencies_.set(index, value);
                    this.onChanged();
                } else {
                    this.moduleDependenciesBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setModuleDependencies(int index, ModuleDependencies.Builder builderForValue) {
                if (this.moduleDependenciesBuilder_ == null) {
                    this.ensureModuleDependenciesIsMutable();
                    this.moduleDependencies_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.moduleDependenciesBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addModuleDependencies(ModuleDependencies value) {
                if (this.moduleDependenciesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureModuleDependenciesIsMutable();
                    this.moduleDependencies_.add(value);
                    this.onChanged();
                } else {
                    this.moduleDependenciesBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addModuleDependencies(int index, ModuleDependencies value) {
                if (this.moduleDependenciesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureModuleDependenciesIsMutable();
                    this.moduleDependencies_.add(index, value);
                    this.onChanged();
                } else {
                    this.moduleDependenciesBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addModuleDependencies(ModuleDependencies.Builder builderForValue) {
                if (this.moduleDependenciesBuilder_ == null) {
                    this.ensureModuleDependenciesIsMutable();
                    this.moduleDependencies_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.moduleDependenciesBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addModuleDependencies(int index, ModuleDependencies.Builder builderForValue) {
                if (this.moduleDependenciesBuilder_ == null) {
                    this.ensureModuleDependenciesIsMutable();
                    this.moduleDependencies_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.moduleDependenciesBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllModuleDependencies(Iterable<? extends ModuleDependencies> values2) {
                if (this.moduleDependenciesBuilder_ == null) {
                    this.ensureModuleDependenciesIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.moduleDependencies_);
                    this.onChanged();
                } else {
                    this.moduleDependenciesBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearModuleDependencies() {
                if (this.moduleDependenciesBuilder_ == null) {
                    this.moduleDependencies_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.onChanged();
                } else {
                    this.moduleDependenciesBuilder_.clear();
                }
                return this;
            }

            public Builder removeModuleDependencies(int index) {
                if (this.moduleDependenciesBuilder_ == null) {
                    this.ensureModuleDependenciesIsMutable();
                    this.moduleDependencies_.remove(index);
                    this.onChanged();
                } else {
                    this.moduleDependenciesBuilder_.remove(index);
                }
                return this;
            }

            public ModuleDependencies.Builder getModuleDependenciesBuilder(int index) {
                return this.getModuleDependenciesFieldBuilder().getBuilder(index);
            }

            @Override
            public ModuleDependenciesOrBuilder getModuleDependenciesOrBuilder(int index) {
                if (this.moduleDependenciesBuilder_ == null) {
                    return this.moduleDependencies_.get(index);
                }
                return this.moduleDependenciesBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends ModuleDependenciesOrBuilder> getModuleDependenciesOrBuilderList() {
                if (this.moduleDependenciesBuilder_ != null) {
                    return this.moduleDependenciesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.moduleDependencies_);
            }

            public ModuleDependencies.Builder addModuleDependenciesBuilder() {
                return this.getModuleDependenciesFieldBuilder().addBuilder(ModuleDependencies.getDefaultInstance());
            }

            public ModuleDependencies.Builder addModuleDependenciesBuilder(int index) {
                return this.getModuleDependenciesFieldBuilder().addBuilder(index, ModuleDependencies.getDefaultInstance());
            }

            public List<ModuleDependencies.Builder> getModuleDependenciesBuilderList() {
                return this.getModuleDependenciesFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<ModuleDependencies, ModuleDependencies.Builder, ModuleDependenciesOrBuilder> getModuleDependenciesFieldBuilder() {
                if (this.moduleDependenciesBuilder_ == null) {
                    this.moduleDependenciesBuilder_ = new RepeatedFieldBuilderV3(this.moduleDependencies_, (this.bitField0_ & 4) == 4, this.getParentForChildren(), this.isClean());
                    this.moduleDependencies_ = null;
                }
                return this.moduleDependenciesBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface AppDependenciesOrBuilder
    extends MessageOrBuilder {
        public List<Library> getLibraryList();

        public Library getLibrary(int var1);

        public int getLibraryCount();

        public List<? extends LibraryOrBuilder> getLibraryOrBuilderList();

        public LibraryOrBuilder getLibraryOrBuilder(int var1);

        public List<LibraryDependencies> getLibraryDependenciesList();

        public LibraryDependencies getLibraryDependencies(int var1);

        public int getLibraryDependenciesCount();

        public List<? extends LibraryDependenciesOrBuilder> getLibraryDependenciesOrBuilderList();

        public LibraryDependenciesOrBuilder getLibraryDependenciesOrBuilder(int var1);

        public List<ModuleDependencies> getModuleDependenciesList();

        public ModuleDependencies getModuleDependencies(int var1);

        public int getModuleDependenciesCount();

        public List<? extends ModuleDependenciesOrBuilder> getModuleDependenciesOrBuilderList();

        public ModuleDependenciesOrBuilder getModuleDependenciesOrBuilder(int var1);
    }
}

