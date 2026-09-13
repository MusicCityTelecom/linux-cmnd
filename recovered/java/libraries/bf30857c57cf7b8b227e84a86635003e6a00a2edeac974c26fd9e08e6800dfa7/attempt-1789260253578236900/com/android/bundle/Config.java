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
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Config {
    private static final Descriptors.Descriptor internal_static_android_bundle_BundleConfig_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_BundleConfig_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_Bundletool_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Bundletool_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_Compression_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Compression_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_MasterResources_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_MasterResources_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_Optimizations_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Optimizations_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_UncompressNativeLibraries_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_UncompressNativeLibraries_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_UncompressDexFiles_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_UncompressDexFiles_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_SplitsConfig_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_SplitsConfig_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_StandaloneConfig_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_StandaloneConfig_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_SplitDimension_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_SplitDimension_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_SuffixStripping_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_SuffixStripping_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ApexConfig_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ApexConfig_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ApexEmbeddedApkConfig_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ApexEmbeddedApkConfig_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private Config() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        Config.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\fconfig.proto\u0012\u000eandroid.bundle\"\u0092\u0002\n\fBundleConfig\u0012.\n\nbundletool\u0018\u0001 \u0001(\u000b2\u001a.android.bundle.Bundletool\u00124\n\roptimizations\u0018\u0002 \u0001(\u000b2\u001d.android.bundle.Optimizations\u00120\n\u000bcompression\u0018\u0003 \u0001(\u000b2\u001b.android.bundle.Compression\u00129\n\u0010master_resources\u0018\u0004 \u0001(\u000b2\u001f.android.bundle.MasterResources\u0012/\n\u000bapex_config\u0018\u0005 \u0001(\u000b2\u001a.android.bundle.ApexConfig\"#\n\nBundletool\u0012\u000f\n\u0007version\u0018\u0002 \u0001(\tJ\u0004\b\u0001\u0010\u0002\"(\n\u000bCompression\u0012\u0019\n\u0011uncompressed_glob\u0018\u0001 \u0003(\t\"?\n\u000fMasterReso", "urces\u0012\u0014\n\fresource_ids\u0018\u0001 \u0003(\u0005\u0012\u0016\n\u000eresource_names\u0018\u0002 \u0003(\t\"\u0093\u0002\n\rOptimizations\u00123\n\rsplits_config\u0018\u0001 \u0001(\u000b2\u001c.android.bundle.SplitsConfig\u0012N\n\u001buncompress_native_libraries\u0018\u0002 \u0001(\u000b2).android.bundle.UncompressNativeLibraries\u0012@\n\u0014uncompress_dex_files\u0018\u0003 \u0001(\u000b2\".android.bundle.UncompressDexFiles\u0012;\n\u0011standalone_config\u0018\u0004 \u0001(\u000b2 .android.bundle.StandaloneConfig\",\n\u0019UncompressNativeLibraries\u0012\u000f\n\u0007enabled\u0018\u0001 \u0001(\b\"%\n\u0012UncompressDexFiles\u0012\u000f\n", "\u0007enabled\u0018\u0001 \u0001(\b\"G\n\fSplitsConfig\u00127\n\u000fsplit_dimension\u0018\u0001 \u0003(\u000b2\u001e.android.bundle.SplitDimension\"k\n\u0010StandaloneConfig\u00127\n\u000fsplit_dimension\u0018\u0001 \u0003(\u000b2\u001e.android.bundle.SplitDimension\u0012\u001e\n\u0016strip_64_bit_libraries\u0018\u0002 \u0001(\b\"\u00fb\u0001\n\u000eSplitDimension\u00123\n\u0005value\u0018\u0001 \u0001(\u000e2$.android.bundle.SplitDimension.Value\u0012\u000e\n\u0006negate\u0018\u0002 \u0001(\b\u00129\n\u0010suffix_stripping\u0018\u0003 \u0001(\u000b2\u001f.android.bundle.SuffixStripping\"i\n\u0005Value\u0012\u0015\n\u0011UNSPECIFIED_VALUE\u0010\u0000\u0012\u0007\n\u0003ABI\u0010\u0001\u0012\u0012\n\u000eSCREEN_DENSI", "TY\u0010\u0002\u0012\f\n\bLANGUAGE\u0010\u0003\u0012\u001e\n\u001aTEXTURE_COMPRESSION_FORMAT\u0010\u0004\":\n\u000fSuffixStripping\u0012\u000f\n\u0007enabled\u0018\u0001 \u0001(\b\u0012\u0016\n\u000edefault_suffix\u0018\u0002 \u0001(\t\"U\n\nApexConfig\u0012G\n\u0018apex_embedded_apk_config\u0018\u0001 \u0003(\u000b2%.android.bundle.ApexEmbeddedApkConfig\";\n\u0015ApexEmbeddedApkConfig\u0012\u0014\n\fpackage_name\u0018\u0001 \u0001(\t\u0012\f\n\u0004path\u0018\u0002 \u0001(\tB\u0014\n\u0012com.android.bundleb\u0006proto3"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            @Override
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0], assigner);
        internal_static_android_bundle_BundleConfig_descriptor = Config.getDescriptor().getMessageTypes().get(0);
        internal_static_android_bundle_BundleConfig_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_BundleConfig_descriptor, new String[]{"Bundletool", "Optimizations", "Compression", "MasterResources", "ApexConfig"});
        internal_static_android_bundle_Bundletool_descriptor = Config.getDescriptor().getMessageTypes().get(1);
        internal_static_android_bundle_Bundletool_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Bundletool_descriptor, new String[]{"Version"});
        internal_static_android_bundle_Compression_descriptor = Config.getDescriptor().getMessageTypes().get(2);
        internal_static_android_bundle_Compression_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Compression_descriptor, new String[]{"UncompressedGlob"});
        internal_static_android_bundle_MasterResources_descriptor = Config.getDescriptor().getMessageTypes().get(3);
        internal_static_android_bundle_MasterResources_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_MasterResources_descriptor, new String[]{"ResourceIds", "ResourceNames"});
        internal_static_android_bundle_Optimizations_descriptor = Config.getDescriptor().getMessageTypes().get(4);
        internal_static_android_bundle_Optimizations_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Optimizations_descriptor, new String[]{"SplitsConfig", "UncompressNativeLibraries", "UncompressDexFiles", "StandaloneConfig"});
        internal_static_android_bundle_UncompressNativeLibraries_descriptor = Config.getDescriptor().getMessageTypes().get(5);
        internal_static_android_bundle_UncompressNativeLibraries_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_UncompressNativeLibraries_descriptor, new String[]{"Enabled"});
        internal_static_android_bundle_UncompressDexFiles_descriptor = Config.getDescriptor().getMessageTypes().get(6);
        internal_static_android_bundle_UncompressDexFiles_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_UncompressDexFiles_descriptor, new String[]{"Enabled"});
        internal_static_android_bundle_SplitsConfig_descriptor = Config.getDescriptor().getMessageTypes().get(7);
        internal_static_android_bundle_SplitsConfig_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_SplitsConfig_descriptor, new String[]{"SplitDimension"});
        internal_static_android_bundle_StandaloneConfig_descriptor = Config.getDescriptor().getMessageTypes().get(8);
        internal_static_android_bundle_StandaloneConfig_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_StandaloneConfig_descriptor, new String[]{"SplitDimension", "Strip64BitLibraries"});
        internal_static_android_bundle_SplitDimension_descriptor = Config.getDescriptor().getMessageTypes().get(9);
        internal_static_android_bundle_SplitDimension_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_SplitDimension_descriptor, new String[]{"Value", "Negate", "SuffixStripping"});
        internal_static_android_bundle_SuffixStripping_descriptor = Config.getDescriptor().getMessageTypes().get(10);
        internal_static_android_bundle_SuffixStripping_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_SuffixStripping_descriptor, new String[]{"Enabled", "DefaultSuffix"});
        internal_static_android_bundle_ApexConfig_descriptor = Config.getDescriptor().getMessageTypes().get(11);
        internal_static_android_bundle_ApexConfig_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ApexConfig_descriptor, new String[]{"ApexEmbeddedApkConfig"});
        internal_static_android_bundle_ApexEmbeddedApkConfig_descriptor = Config.getDescriptor().getMessageTypes().get(12);
        internal_static_android_bundle_ApexEmbeddedApkConfig_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ApexEmbeddedApkConfig_descriptor, new String[]{"PackageName", "Path"});
    }

    public static final class ApexEmbeddedApkConfig
    extends GeneratedMessageV3
    implements ApexEmbeddedApkConfigOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
        private volatile Object packageName_;
        public static final int PATH_FIELD_NUMBER = 2;
        private volatile Object path_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ApexEmbeddedApkConfig DEFAULT_INSTANCE = new ApexEmbeddedApkConfig();
        private static final Parser<ApexEmbeddedApkConfig> PARSER = new AbstractParser<ApexEmbeddedApkConfig>(){

            @Override
            public ApexEmbeddedApkConfig parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ApexEmbeddedApkConfig(input, extensionRegistry);
            }
        };

        private ApexEmbeddedApkConfig(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ApexEmbeddedApkConfig() {
            this.packageName_ = "";
            this.path_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ApexEmbeddedApkConfig(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block11: while (!done) {
                    String s3;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        default: {
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block11;
                            done = true;
                            continue block11;
                        }
                        case 10: {
                            s3 = input.readStringRequireUtf8();
                            this.packageName_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    s3 = input.readStringRequireUtf8();
                    this.path_ = s3;
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
            return internal_static_android_bundle_ApexEmbeddedApkConfig_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ApexEmbeddedApkConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(ApexEmbeddedApkConfig.class, Builder.class);
        }

        @Override
        public String getPackageName() {
            Object ref = this.packageName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.packageName_ = s3;
            return s3;
        }

        @Override
        public ByteString getPackageNameBytes() {
            Object ref = this.packageName_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.packageName_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public String getPath() {
            Object ref = this.path_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.path_ = s3;
            return s3;
        }

        @Override
        public ByteString getPathBytes() {
            Object ref = this.path_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.path_ = b2;
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
            if (!this.getPackageNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.packageName_);
            }
            if (!this.getPathBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.path_);
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
            if (!this.getPackageNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.packageName_);
            }
            if (!this.getPathBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.path_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ApexEmbeddedApkConfig)) {
                return super.equals(obj);
            }
            ApexEmbeddedApkConfig other = (ApexEmbeddedApkConfig)obj;
            boolean result = true;
            result = result && this.getPackageName().equals(other.getPackageName());
            result = result && this.getPath().equals(other.getPath());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ApexEmbeddedApkConfig.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getPackageName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getPath().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ApexEmbeddedApkConfig parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexEmbeddedApkConfig parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexEmbeddedApkConfig parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexEmbeddedApkConfig parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexEmbeddedApkConfig parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexEmbeddedApkConfig parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexEmbeddedApkConfig parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApexEmbeddedApkConfig parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApexEmbeddedApkConfig parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ApexEmbeddedApkConfig parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApexEmbeddedApkConfig parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApexEmbeddedApkConfig parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ApexEmbeddedApkConfig.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ApexEmbeddedApkConfig prototype) {
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

        public static ApexEmbeddedApkConfig getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ApexEmbeddedApkConfig> parser() {
            return PARSER;
        }

        public Parser<ApexEmbeddedApkConfig> getParserForType() {
            return PARSER;
        }

        @Override
        public ApexEmbeddedApkConfig getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ApexEmbeddedApkConfigOrBuilder {
            private Object packageName_ = "";
            private Object path_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ApexEmbeddedApkConfig_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ApexEmbeddedApkConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(ApexEmbeddedApkConfig.class, Builder.class);
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
                this.packageName_ = "";
                this.path_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ApexEmbeddedApkConfig_descriptor;
            }

            @Override
            public ApexEmbeddedApkConfig getDefaultInstanceForType() {
                return ApexEmbeddedApkConfig.getDefaultInstance();
            }

            @Override
            public ApexEmbeddedApkConfig build() {
                ApexEmbeddedApkConfig result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ApexEmbeddedApkConfig buildPartial() {
                ApexEmbeddedApkConfig result = new ApexEmbeddedApkConfig(this);
                result.packageName_ = this.packageName_;
                result.path_ = this.path_;
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
                if (other instanceof ApexEmbeddedApkConfig) {
                    return this.mergeFrom((ApexEmbeddedApkConfig)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ApexEmbeddedApkConfig other) {
                if (other == ApexEmbeddedApkConfig.getDefaultInstance()) {
                    return this;
                }
                if (!other.getPackageName().isEmpty()) {
                    this.packageName_ = other.packageName_;
                    this.onChanged();
                }
                if (!other.getPath().isEmpty()) {
                    this.path_ = other.path_;
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
                ApexEmbeddedApkConfig parsedMessage = null;
                try {
                    parsedMessage = (ApexEmbeddedApkConfig)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ApexEmbeddedApkConfig)e2.getUnfinishedMessage();
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
            public String getPackageName() {
                Object ref = this.packageName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.packageName_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getPackageNameBytes() {
                Object ref = this.packageName_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.packageName_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setPackageName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.packageName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPackageName() {
                this.packageName_ = ApexEmbeddedApkConfig.getDefaultInstance().getPackageName();
                this.onChanged();
                return this;
            }

            public Builder setPackageNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ApexEmbeddedApkConfig.checkByteStringIsUtf8(value);
                this.packageName_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public String getPath() {
                Object ref = this.path_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.path_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getPathBytes() {
                Object ref = this.path_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.path_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setPath(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.path_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPath() {
                this.path_ = ApexEmbeddedApkConfig.getDefaultInstance().getPath();
                this.onChanged();
                return this;
            }

            public Builder setPathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ApexEmbeddedApkConfig.checkByteStringIsUtf8(value);
                this.path_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface ApexEmbeddedApkConfigOrBuilder
    extends MessageOrBuilder {
        public String getPackageName();

        public ByteString getPackageNameBytes();

        public String getPath();

        public ByteString getPathBytes();
    }

    public static final class ApexConfig
    extends GeneratedMessageV3
    implements ApexConfigOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int APEX_EMBEDDED_APK_CONFIG_FIELD_NUMBER = 1;
        private List<ApexEmbeddedApkConfig> apexEmbeddedApkConfig_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ApexConfig DEFAULT_INSTANCE = new ApexConfig();
        private static final Parser<ApexConfig> PARSER = new AbstractParser<ApexConfig>(){

            @Override
            public ApexConfig parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ApexConfig(input, extensionRegistry);
            }
        };

        private ApexConfig(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ApexConfig() {
            this.apexEmbeddedApkConfig_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ApexConfig(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block10;
                            done = true;
                            continue block10;
                        }
                        case 10: 
                    }
                    if (!(mutable_bitField0_ & true)) {
                        this.apexEmbeddedApkConfig_ = new ArrayList<ApexEmbeddedApkConfig>();
                        mutable_bitField0_ |= true;
                    }
                    this.apexEmbeddedApkConfig_.add(input.readMessage(ApexEmbeddedApkConfig.parser(), extensionRegistry));
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
                    this.apexEmbeddedApkConfig_ = Collections.unmodifiableList(this.apexEmbeddedApkConfig_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_ApexConfig_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ApexConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(ApexConfig.class, Builder.class);
        }

        @Override
        public List<ApexEmbeddedApkConfig> getApexEmbeddedApkConfigList() {
            return this.apexEmbeddedApkConfig_;
        }

        @Override
        public List<? extends ApexEmbeddedApkConfigOrBuilder> getApexEmbeddedApkConfigOrBuilderList() {
            return this.apexEmbeddedApkConfig_;
        }

        @Override
        public int getApexEmbeddedApkConfigCount() {
            return this.apexEmbeddedApkConfig_.size();
        }

        @Override
        public ApexEmbeddedApkConfig getApexEmbeddedApkConfig(int index) {
            return this.apexEmbeddedApkConfig_.get(index);
        }

        @Override
        public ApexEmbeddedApkConfigOrBuilder getApexEmbeddedApkConfigOrBuilder(int index) {
            return this.apexEmbeddedApkConfig_.get(index);
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
            for (int i2 = 0; i2 < this.apexEmbeddedApkConfig_.size(); ++i2) {
                output.writeMessage(1, this.apexEmbeddedApkConfig_.get(i2));
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
            for (int i2 = 0; i2 < this.apexEmbeddedApkConfig_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.apexEmbeddedApkConfig_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ApexConfig)) {
                return super.equals(obj);
            }
            ApexConfig other = (ApexConfig)obj;
            boolean result = true;
            result = result && this.getApexEmbeddedApkConfigList().equals(other.getApexEmbeddedApkConfigList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ApexConfig.getDescriptor().hashCode();
            if (this.getApexEmbeddedApkConfigCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getApexEmbeddedApkConfigList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ApexConfig parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexConfig parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexConfig parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexConfig parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexConfig parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexConfig parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexConfig parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApexConfig parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApexConfig parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ApexConfig parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApexConfig parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApexConfig parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ApexConfig.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ApexConfig prototype) {
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

        public static ApexConfig getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ApexConfig> parser() {
            return PARSER;
        }

        public Parser<ApexConfig> getParserForType() {
            return PARSER;
        }

        @Override
        public ApexConfig getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ApexConfigOrBuilder {
            private int bitField0_;
            private List<ApexEmbeddedApkConfig> apexEmbeddedApkConfig_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<ApexEmbeddedApkConfig, ApexEmbeddedApkConfig.Builder, ApexEmbeddedApkConfigOrBuilder> apexEmbeddedApkConfigBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ApexConfig_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ApexConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(ApexConfig.class, Builder.class);
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
                    this.getApexEmbeddedApkConfigFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.apexEmbeddedApkConfig_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.apexEmbeddedApkConfigBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ApexConfig_descriptor;
            }

            @Override
            public ApexConfig getDefaultInstanceForType() {
                return ApexConfig.getDefaultInstance();
            }

            @Override
            public ApexConfig build() {
                ApexConfig result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ApexConfig buildPartial() {
                ApexConfig result = new ApexConfig(this);
                int from_bitField0_ = this.bitField0_;
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.apexEmbeddedApkConfig_ = Collections.unmodifiableList(this.apexEmbeddedApkConfig_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.apexEmbeddedApkConfig_ = this.apexEmbeddedApkConfig_;
                } else {
                    result.apexEmbeddedApkConfig_ = this.apexEmbeddedApkConfigBuilder_.build();
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
                if (other instanceof ApexConfig) {
                    return this.mergeFrom((ApexConfig)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ApexConfig other) {
                if (other == ApexConfig.getDefaultInstance()) {
                    return this;
                }
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    if (!other.apexEmbeddedApkConfig_.isEmpty()) {
                        if (this.apexEmbeddedApkConfig_.isEmpty()) {
                            this.apexEmbeddedApkConfig_ = other.apexEmbeddedApkConfig_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureApexEmbeddedApkConfigIsMutable();
                            this.apexEmbeddedApkConfig_.addAll(other.apexEmbeddedApkConfig_);
                        }
                        this.onChanged();
                    }
                } else if (!other.apexEmbeddedApkConfig_.isEmpty()) {
                    if (this.apexEmbeddedApkConfigBuilder_.isEmpty()) {
                        this.apexEmbeddedApkConfigBuilder_.dispose();
                        this.apexEmbeddedApkConfigBuilder_ = null;
                        this.apexEmbeddedApkConfig_ = other.apexEmbeddedApkConfig_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.apexEmbeddedApkConfigBuilder_ = alwaysUseFieldBuilders ? this.getApexEmbeddedApkConfigFieldBuilder() : null;
                    } else {
                        this.apexEmbeddedApkConfigBuilder_.addAllMessages(other.apexEmbeddedApkConfig_);
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
                ApexConfig parsedMessage = null;
                try {
                    parsedMessage = (ApexConfig)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ApexConfig)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureApexEmbeddedApkConfigIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.apexEmbeddedApkConfig_ = new ArrayList<ApexEmbeddedApkConfig>(this.apexEmbeddedApkConfig_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<ApexEmbeddedApkConfig> getApexEmbeddedApkConfigList() {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    return Collections.unmodifiableList(this.apexEmbeddedApkConfig_);
                }
                return this.apexEmbeddedApkConfigBuilder_.getMessageList();
            }

            @Override
            public int getApexEmbeddedApkConfigCount() {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    return this.apexEmbeddedApkConfig_.size();
                }
                return this.apexEmbeddedApkConfigBuilder_.getCount();
            }

            @Override
            public ApexEmbeddedApkConfig getApexEmbeddedApkConfig(int index) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    return this.apexEmbeddedApkConfig_.get(index);
                }
                return this.apexEmbeddedApkConfigBuilder_.getMessage(index);
            }

            public Builder setApexEmbeddedApkConfig(int index, ApexEmbeddedApkConfig value) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    this.apexEmbeddedApkConfig_.set(index, value);
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setApexEmbeddedApkConfig(int index, ApexEmbeddedApkConfig.Builder builderForValue) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    this.apexEmbeddedApkConfig_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addApexEmbeddedApkConfig(ApexEmbeddedApkConfig value) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    this.apexEmbeddedApkConfig_.add(value);
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addApexEmbeddedApkConfig(int index, ApexEmbeddedApkConfig value) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    this.apexEmbeddedApkConfig_.add(index, value);
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addApexEmbeddedApkConfig(ApexEmbeddedApkConfig.Builder builderForValue) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    this.apexEmbeddedApkConfig_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addApexEmbeddedApkConfig(int index, ApexEmbeddedApkConfig.Builder builderForValue) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    this.apexEmbeddedApkConfig_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllApexEmbeddedApkConfig(Iterable<? extends ApexEmbeddedApkConfig> values2) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.apexEmbeddedApkConfig_);
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearApexEmbeddedApkConfig() {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.apexEmbeddedApkConfig_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.clear();
                }
                return this;
            }

            public Builder removeApexEmbeddedApkConfig(int index) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    this.apexEmbeddedApkConfig_.remove(index);
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.remove(index);
                }
                return this;
            }

            public ApexEmbeddedApkConfig.Builder getApexEmbeddedApkConfigBuilder(int index) {
                return this.getApexEmbeddedApkConfigFieldBuilder().getBuilder(index);
            }

            @Override
            public ApexEmbeddedApkConfigOrBuilder getApexEmbeddedApkConfigOrBuilder(int index) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    return this.apexEmbeddedApkConfig_.get(index);
                }
                return this.apexEmbeddedApkConfigBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends ApexEmbeddedApkConfigOrBuilder> getApexEmbeddedApkConfigOrBuilderList() {
                if (this.apexEmbeddedApkConfigBuilder_ != null) {
                    return this.apexEmbeddedApkConfigBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.apexEmbeddedApkConfig_);
            }

            public ApexEmbeddedApkConfig.Builder addApexEmbeddedApkConfigBuilder() {
                return this.getApexEmbeddedApkConfigFieldBuilder().addBuilder(ApexEmbeddedApkConfig.getDefaultInstance());
            }

            public ApexEmbeddedApkConfig.Builder addApexEmbeddedApkConfigBuilder(int index) {
                return this.getApexEmbeddedApkConfigFieldBuilder().addBuilder(index, ApexEmbeddedApkConfig.getDefaultInstance());
            }

            public List<ApexEmbeddedApkConfig.Builder> getApexEmbeddedApkConfigBuilderList() {
                return this.getApexEmbeddedApkConfigFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<ApexEmbeddedApkConfig, ApexEmbeddedApkConfig.Builder, ApexEmbeddedApkConfigOrBuilder> getApexEmbeddedApkConfigFieldBuilder() {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.apexEmbeddedApkConfigBuilder_ = new RepeatedFieldBuilderV3(this.apexEmbeddedApkConfig_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.apexEmbeddedApkConfig_ = null;
                }
                return this.apexEmbeddedApkConfigBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface ApexConfigOrBuilder
    extends MessageOrBuilder {
        public List<ApexEmbeddedApkConfig> getApexEmbeddedApkConfigList();

        public ApexEmbeddedApkConfig getApexEmbeddedApkConfig(int var1);

        public int getApexEmbeddedApkConfigCount();

        public List<? extends ApexEmbeddedApkConfigOrBuilder> getApexEmbeddedApkConfigOrBuilderList();

        public ApexEmbeddedApkConfigOrBuilder getApexEmbeddedApkConfigOrBuilder(int var1);
    }

    public static final class SuffixStripping
    extends GeneratedMessageV3
    implements SuffixStrippingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ENABLED_FIELD_NUMBER = 1;
        private boolean enabled_;
        public static final int DEFAULT_SUFFIX_FIELD_NUMBER = 2;
        private volatile Object defaultSuffix_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SuffixStripping DEFAULT_INSTANCE = new SuffixStripping();
        private static final Parser<SuffixStripping> PARSER = new AbstractParser<SuffixStripping>(){

            @Override
            public SuffixStripping parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SuffixStripping(input, extensionRegistry);
            }
        };

        private SuffixStripping(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SuffixStripping() {
            this.enabled_ = false;
            this.defaultSuffix_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SuffixStripping(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block11: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        default: {
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block11;
                            done = true;
                            continue block11;
                        }
                        case 8: {
                            this.enabled_ = input.readBool();
                            continue block11;
                        }
                        case 18: 
                    }
                    String s3 = input.readStringRequireUtf8();
                    this.defaultSuffix_ = s3;
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
            return internal_static_android_bundle_SuffixStripping_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_SuffixStripping_fieldAccessorTable.ensureFieldAccessorsInitialized(SuffixStripping.class, Builder.class);
        }

        @Override
        public boolean getEnabled() {
            return this.enabled_;
        }

        @Override
        public String getDefaultSuffix() {
            Object ref = this.defaultSuffix_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.defaultSuffix_ = s3;
            return s3;
        }

        @Override
        public ByteString getDefaultSuffixBytes() {
            Object ref = this.defaultSuffix_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.defaultSuffix_ = b2;
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
            if (this.enabled_) {
                output.writeBool(1, this.enabled_);
            }
            if (!this.getDefaultSuffixBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.defaultSuffix_);
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
            if (this.enabled_) {
                size += CodedOutputStream.computeBoolSize(1, this.enabled_);
            }
            if (!this.getDefaultSuffixBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.defaultSuffix_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SuffixStripping)) {
                return super.equals(obj);
            }
            SuffixStripping other = (SuffixStripping)obj;
            boolean result = true;
            result = result && this.getEnabled() == other.getEnabled();
            result = result && this.getDefaultSuffix().equals(other.getDefaultSuffix());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + SuffixStripping.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getEnabled());
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getDefaultSuffix().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static SuffixStripping parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SuffixStripping parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SuffixStripping parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SuffixStripping parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SuffixStripping parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SuffixStripping parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SuffixStripping parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SuffixStripping parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SuffixStripping parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SuffixStripping parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SuffixStripping parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SuffixStripping parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return SuffixStripping.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SuffixStripping prototype) {
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

        public static SuffixStripping getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SuffixStripping> parser() {
            return PARSER;
        }

        public Parser<SuffixStripping> getParserForType() {
            return PARSER;
        }

        @Override
        public SuffixStripping getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SuffixStrippingOrBuilder {
            private boolean enabled_;
            private Object defaultSuffix_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_SuffixStripping_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_SuffixStripping_fieldAccessorTable.ensureFieldAccessorsInitialized(SuffixStripping.class, Builder.class);
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
                this.enabled_ = false;
                this.defaultSuffix_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_SuffixStripping_descriptor;
            }

            @Override
            public SuffixStripping getDefaultInstanceForType() {
                return SuffixStripping.getDefaultInstance();
            }

            @Override
            public SuffixStripping build() {
                SuffixStripping result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public SuffixStripping buildPartial() {
                SuffixStripping result = new SuffixStripping(this);
                result.enabled_ = this.enabled_;
                result.defaultSuffix_ = this.defaultSuffix_;
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
                if (other instanceof SuffixStripping) {
                    return this.mergeFrom((SuffixStripping)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SuffixStripping other) {
                if (other == SuffixStripping.getDefaultInstance()) {
                    return this;
                }
                if (other.getEnabled()) {
                    this.setEnabled(other.getEnabled());
                }
                if (!other.getDefaultSuffix().isEmpty()) {
                    this.defaultSuffix_ = other.defaultSuffix_;
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
                SuffixStripping parsedMessage = null;
                try {
                    parsedMessage = (SuffixStripping)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (SuffixStripping)e2.getUnfinishedMessage();
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
            public boolean getEnabled() {
                return this.enabled_;
            }

            public Builder setEnabled(boolean value) {
                this.enabled_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearEnabled() {
                this.enabled_ = false;
                this.onChanged();
                return this;
            }

            @Override
            public String getDefaultSuffix() {
                Object ref = this.defaultSuffix_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.defaultSuffix_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getDefaultSuffixBytes() {
                Object ref = this.defaultSuffix_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.defaultSuffix_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setDefaultSuffix(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.defaultSuffix_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearDefaultSuffix() {
                this.defaultSuffix_ = SuffixStripping.getDefaultInstance().getDefaultSuffix();
                this.onChanged();
                return this;
            }

            public Builder setDefaultSuffixBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                SuffixStripping.checkByteStringIsUtf8(value);
                this.defaultSuffix_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface SuffixStrippingOrBuilder
    extends MessageOrBuilder {
        public boolean getEnabled();

        public String getDefaultSuffix();

        public ByteString getDefaultSuffixBytes();
    }

    public static final class SplitDimension
    extends GeneratedMessageV3
    implements SplitDimensionOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private int value_;
        public static final int NEGATE_FIELD_NUMBER = 2;
        private boolean negate_;
        public static final int SUFFIX_STRIPPING_FIELD_NUMBER = 3;
        private SuffixStripping suffixStripping_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SplitDimension DEFAULT_INSTANCE = new SplitDimension();
        private static final Parser<SplitDimension> PARSER = new AbstractParser<SplitDimension>(){

            @Override
            public SplitDimension parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SplitDimension(input, extensionRegistry);
            }
        };

        private SplitDimension(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SplitDimension() {
            this.value_ = 0;
            this.negate_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SplitDimension(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
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
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block12;
                            done = true;
                            continue block12;
                        }
                        case 8: {
                            int rawValue;
                            this.value_ = rawValue = input.readEnum();
                            continue block12;
                        }
                        case 16: {
                            this.negate_ = input.readBool();
                            continue block12;
                        }
                        case 26: 
                    }
                    SuffixStripping.Builder subBuilder = null;
                    if (this.suffixStripping_ != null) {
                        subBuilder = this.suffixStripping_.toBuilder();
                    }
                    this.suffixStripping_ = input.readMessage(SuffixStripping.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    subBuilder.mergeFrom(this.suffixStripping_);
                    this.suffixStripping_ = subBuilder.buildPartial();
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
            return internal_static_android_bundle_SplitDimension_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_SplitDimension_fieldAccessorTable.ensureFieldAccessorsInitialized(SplitDimension.class, Builder.class);
        }

        @Override
        public int getValueValue() {
            return this.value_;
        }

        @Override
        public Value getValue() {
            Value result = Value.valueOf(this.value_);
            return result == null ? Value.UNRECOGNIZED : result;
        }

        @Override
        public boolean getNegate() {
            return this.negate_;
        }

        @Override
        public boolean hasSuffixStripping() {
            return this.suffixStripping_ != null;
        }

        @Override
        public SuffixStripping getSuffixStripping() {
            return this.suffixStripping_ == null ? SuffixStripping.getDefaultInstance() : this.suffixStripping_;
        }

        @Override
        public SuffixStrippingOrBuilder getSuffixStrippingOrBuilder() {
            return this.getSuffixStripping();
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
            if (this.value_ != Value.UNSPECIFIED_VALUE.getNumber()) {
                output.writeEnum(1, this.value_);
            }
            if (this.negate_) {
                output.writeBool(2, this.negate_);
            }
            if (this.suffixStripping_ != null) {
                output.writeMessage(3, this.getSuffixStripping());
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
            if (this.value_ != Value.UNSPECIFIED_VALUE.getNumber()) {
                size += CodedOutputStream.computeEnumSize(1, this.value_);
            }
            if (this.negate_) {
                size += CodedOutputStream.computeBoolSize(2, this.negate_);
            }
            if (this.suffixStripping_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getSuffixStripping());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SplitDimension)) {
                return super.equals(obj);
            }
            SplitDimension other = (SplitDimension)obj;
            boolean result = true;
            result = result && this.value_ == other.value_;
            result = result && this.getNegate() == other.getNegate();
            boolean bl = result = result && this.hasSuffixStripping() == other.hasSuffixStripping();
            if (this.hasSuffixStripping()) {
                result = result && this.getSuffixStripping().equals(other.getSuffixStripping());
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
            hash = 19 * hash + SplitDimension.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.value_;
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashBoolean(this.getNegate());
            if (this.hasSuffixStripping()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getSuffixStripping().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static SplitDimension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SplitDimension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SplitDimension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SplitDimension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SplitDimension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SplitDimension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SplitDimension parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SplitDimension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SplitDimension parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SplitDimension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SplitDimension parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SplitDimension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return SplitDimension.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SplitDimension prototype) {
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

        public static SplitDimension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SplitDimension> parser() {
            return PARSER;
        }

        public Parser<SplitDimension> getParserForType() {
            return PARSER;
        }

        @Override
        public SplitDimension getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SplitDimensionOrBuilder {
            private int value_ = 0;
            private boolean negate_;
            private SuffixStripping suffixStripping_ = null;
            private SingleFieldBuilderV3<SuffixStripping, SuffixStripping.Builder, SuffixStrippingOrBuilder> suffixStrippingBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_SplitDimension_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_SplitDimension_fieldAccessorTable.ensureFieldAccessorsInitialized(SplitDimension.class, Builder.class);
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
                this.value_ = 0;
                this.negate_ = false;
                if (this.suffixStrippingBuilder_ == null) {
                    this.suffixStripping_ = null;
                } else {
                    this.suffixStripping_ = null;
                    this.suffixStrippingBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_SplitDimension_descriptor;
            }

            @Override
            public SplitDimension getDefaultInstanceForType() {
                return SplitDimension.getDefaultInstance();
            }

            @Override
            public SplitDimension build() {
                SplitDimension result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public SplitDimension buildPartial() {
                SplitDimension result = new SplitDimension(this);
                result.value_ = this.value_;
                result.negate_ = this.negate_;
                if (this.suffixStrippingBuilder_ == null) {
                    result.suffixStripping_ = this.suffixStripping_;
                } else {
                    result.suffixStripping_ = this.suffixStrippingBuilder_.build();
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
                if (other instanceof SplitDimension) {
                    return this.mergeFrom((SplitDimension)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SplitDimension other) {
                if (other == SplitDimension.getDefaultInstance()) {
                    return this;
                }
                if (other.value_ != 0) {
                    this.setValueValue(other.getValueValue());
                }
                if (other.getNegate()) {
                    this.setNegate(other.getNegate());
                }
                if (other.hasSuffixStripping()) {
                    this.mergeSuffixStripping(other.getSuffixStripping());
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
                SplitDimension parsedMessage = null;
                try {
                    parsedMessage = (SplitDimension)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (SplitDimension)e2.getUnfinishedMessage();
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
            public int getValueValue() {
                return this.value_;
            }

            public Builder setValueValue(int value) {
                this.value_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public Value getValue() {
                Value result = Value.valueOf(this.value_);
                return result == null ? Value.UNRECOGNIZED : result;
            }

            public Builder setValue(Value value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.value_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearValue() {
                this.value_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean getNegate() {
                return this.negate_;
            }

            public Builder setNegate(boolean value) {
                this.negate_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearNegate() {
                this.negate_ = false;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasSuffixStripping() {
                return this.suffixStrippingBuilder_ != null || this.suffixStripping_ != null;
            }

            @Override
            public SuffixStripping getSuffixStripping() {
                if (this.suffixStrippingBuilder_ == null) {
                    return this.suffixStripping_ == null ? SuffixStripping.getDefaultInstance() : this.suffixStripping_;
                }
                return this.suffixStrippingBuilder_.getMessage();
            }

            public Builder setSuffixStripping(SuffixStripping value) {
                if (this.suffixStrippingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.suffixStripping_ = value;
                    this.onChanged();
                } else {
                    this.suffixStrippingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setSuffixStripping(SuffixStripping.Builder builderForValue) {
                if (this.suffixStrippingBuilder_ == null) {
                    this.suffixStripping_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.suffixStrippingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeSuffixStripping(SuffixStripping value) {
                if (this.suffixStrippingBuilder_ == null) {
                    this.suffixStripping_ = this.suffixStripping_ != null ? SuffixStripping.newBuilder(this.suffixStripping_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.suffixStrippingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearSuffixStripping() {
                if (this.suffixStrippingBuilder_ == null) {
                    this.suffixStripping_ = null;
                    this.onChanged();
                } else {
                    this.suffixStripping_ = null;
                    this.suffixStrippingBuilder_ = null;
                }
                return this;
            }

            public SuffixStripping.Builder getSuffixStrippingBuilder() {
                this.onChanged();
                return this.getSuffixStrippingFieldBuilder().getBuilder();
            }

            @Override
            public SuffixStrippingOrBuilder getSuffixStrippingOrBuilder() {
                if (this.suffixStrippingBuilder_ != null) {
                    return this.suffixStrippingBuilder_.getMessageOrBuilder();
                }
                return this.suffixStripping_ == null ? SuffixStripping.getDefaultInstance() : this.suffixStripping_;
            }

            private SingleFieldBuilderV3<SuffixStripping, SuffixStripping.Builder, SuffixStrippingOrBuilder> getSuffixStrippingFieldBuilder() {
                if (this.suffixStrippingBuilder_ == null) {
                    this.suffixStrippingBuilder_ = new SingleFieldBuilderV3(this.getSuffixStripping(), this.getParentForChildren(), this.isClean());
                    this.suffixStripping_ = null;
                }
                return this.suffixStrippingBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }

        public static enum Value implements ProtocolMessageEnum
        {
            UNSPECIFIED_VALUE(0),
            ABI(1),
            SCREEN_DENSITY(2),
            LANGUAGE(3),
            TEXTURE_COMPRESSION_FORMAT(4),
            UNRECOGNIZED(-1);

            public static final int UNSPECIFIED_VALUE_VALUE = 0;
            public static final int ABI_VALUE = 1;
            public static final int SCREEN_DENSITY_VALUE = 2;
            public static final int LANGUAGE_VALUE = 3;
            public static final int TEXTURE_COMPRESSION_FORMAT_VALUE = 4;
            private static final Internal.EnumLiteMap<Value> internalValueMap;
            private static final Value[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static Value valueOf(int value) {
                return Value.forNumber(value);
            }

            public static Value forNumber(int value) {
                switch (value) {
                    case 0: {
                        return UNSPECIFIED_VALUE;
                    }
                    case 1: {
                        return ABI;
                    }
                    case 2: {
                        return SCREEN_DENSITY;
                    }
                    case 3: {
                        return LANGUAGE;
                    }
                    case 4: {
                        return TEXTURE_COMPRESSION_FORMAT;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<Value> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return Value.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return Value.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return SplitDimension.getDescriptor().getEnumTypes().get(0);
            }

            public static Value valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != Value.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private Value(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<Value>(){

                    @Override
                    public Value findValueByNumber(int number) {
                        return Value.forNumber(number);
                    }
                };
                VALUES = Value.values();
            }
        }
    }

    public static interface SplitDimensionOrBuilder
    extends MessageOrBuilder {
        public int getValueValue();

        public SplitDimension.Value getValue();

        public boolean getNegate();

        public boolean hasSuffixStripping();

        public SuffixStripping getSuffixStripping();

        public SuffixStrippingOrBuilder getSuffixStrippingOrBuilder();
    }

    public static final class StandaloneConfig
    extends GeneratedMessageV3
    implements StandaloneConfigOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int SPLIT_DIMENSION_FIELD_NUMBER = 1;
        private List<SplitDimension> splitDimension_;
        public static final int STRIP_64_BIT_LIBRARIES_FIELD_NUMBER = 2;
        private boolean strip64BitLibraries_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final StandaloneConfig DEFAULT_INSTANCE = new StandaloneConfig();
        private static final Parser<StandaloneConfig> PARSER = new AbstractParser<StandaloneConfig>(){

            @Override
            public StandaloneConfig parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new StandaloneConfig(input, extensionRegistry);
            }
        };

        private StandaloneConfig(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private StandaloneConfig() {
            this.splitDimension_ = Collections.emptyList();
            this.strip64BitLibraries_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private StandaloneConfig(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block11: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        default: {
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block11;
                            done = true;
                            continue block11;
                        }
                        case 10: {
                            if (!(mutable_bitField0_ & true)) {
                                this.splitDimension_ = new ArrayList<SplitDimension>();
                                mutable_bitField0_ |= true;
                            }
                            this.splitDimension_.add(input.readMessage(SplitDimension.parser(), extensionRegistry));
                            continue block11;
                        }
                        case 16: 
                    }
                    this.strip64BitLibraries_ = input.readBool();
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
                    this.splitDimension_ = Collections.unmodifiableList(this.splitDimension_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_StandaloneConfig_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_StandaloneConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(StandaloneConfig.class, Builder.class);
        }

        @Override
        public List<SplitDimension> getSplitDimensionList() {
            return this.splitDimension_;
        }

        @Override
        public List<? extends SplitDimensionOrBuilder> getSplitDimensionOrBuilderList() {
            return this.splitDimension_;
        }

        @Override
        public int getSplitDimensionCount() {
            return this.splitDimension_.size();
        }

        @Override
        public SplitDimension getSplitDimension(int index) {
            return this.splitDimension_.get(index);
        }

        @Override
        public SplitDimensionOrBuilder getSplitDimensionOrBuilder(int index) {
            return this.splitDimension_.get(index);
        }

        @Override
        public boolean getStrip64BitLibraries() {
            return this.strip64BitLibraries_;
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
            for (int i2 = 0; i2 < this.splitDimension_.size(); ++i2) {
                output.writeMessage(1, this.splitDimension_.get(i2));
            }
            if (this.strip64BitLibraries_) {
                output.writeBool(2, this.strip64BitLibraries_);
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
            for (int i2 = 0; i2 < this.splitDimension_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.splitDimension_.get(i2));
            }
            if (this.strip64BitLibraries_) {
                size += CodedOutputStream.computeBoolSize(2, this.strip64BitLibraries_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof StandaloneConfig)) {
                return super.equals(obj);
            }
            StandaloneConfig other = (StandaloneConfig)obj;
            boolean result = true;
            result = result && this.getSplitDimensionList().equals(other.getSplitDimensionList());
            result = result && this.getStrip64BitLibraries() == other.getStrip64BitLibraries();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + StandaloneConfig.getDescriptor().hashCode();
            if (this.getSplitDimensionCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getSplitDimensionList().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashBoolean(this.getStrip64BitLibraries());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static StandaloneConfig parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static StandaloneConfig parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static StandaloneConfig parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static StandaloneConfig parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static StandaloneConfig parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static StandaloneConfig parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static StandaloneConfig parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static StandaloneConfig parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static StandaloneConfig parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static StandaloneConfig parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static StandaloneConfig parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static StandaloneConfig parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return StandaloneConfig.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(StandaloneConfig prototype) {
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

        public static StandaloneConfig getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<StandaloneConfig> parser() {
            return PARSER;
        }

        public Parser<StandaloneConfig> getParserForType() {
            return PARSER;
        }

        @Override
        public StandaloneConfig getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements StandaloneConfigOrBuilder {
            private int bitField0_;
            private List<SplitDimension> splitDimension_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<SplitDimension, SplitDimension.Builder, SplitDimensionOrBuilder> splitDimensionBuilder_;
            private boolean strip64BitLibraries_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_StandaloneConfig_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_StandaloneConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(StandaloneConfig.class, Builder.class);
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
                    this.getSplitDimensionFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.splitDimensionBuilder_ == null) {
                    this.splitDimension_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.splitDimensionBuilder_.clear();
                }
                this.strip64BitLibraries_ = false;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_StandaloneConfig_descriptor;
            }

            @Override
            public StandaloneConfig getDefaultInstanceForType() {
                return StandaloneConfig.getDefaultInstance();
            }

            @Override
            public StandaloneConfig build() {
                StandaloneConfig result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public StandaloneConfig buildPartial() {
                StandaloneConfig result = new StandaloneConfig(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if (this.splitDimensionBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.splitDimension_ = Collections.unmodifiableList(this.splitDimension_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.splitDimension_ = this.splitDimension_;
                } else {
                    result.splitDimension_ = this.splitDimensionBuilder_.build();
                }
                result.strip64BitLibraries_ = this.strip64BitLibraries_;
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
                if (other instanceof StandaloneConfig) {
                    return this.mergeFrom((StandaloneConfig)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(StandaloneConfig other) {
                if (other == StandaloneConfig.getDefaultInstance()) {
                    return this;
                }
                if (this.splitDimensionBuilder_ == null) {
                    if (!other.splitDimension_.isEmpty()) {
                        if (this.splitDimension_.isEmpty()) {
                            this.splitDimension_ = other.splitDimension_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureSplitDimensionIsMutable();
                            this.splitDimension_.addAll(other.splitDimension_);
                        }
                        this.onChanged();
                    }
                } else if (!other.splitDimension_.isEmpty()) {
                    if (this.splitDimensionBuilder_.isEmpty()) {
                        this.splitDimensionBuilder_.dispose();
                        this.splitDimensionBuilder_ = null;
                        this.splitDimension_ = other.splitDimension_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.splitDimensionBuilder_ = alwaysUseFieldBuilders ? this.getSplitDimensionFieldBuilder() : null;
                    } else {
                        this.splitDimensionBuilder_.addAllMessages(other.splitDimension_);
                    }
                }
                if (other.getStrip64BitLibraries()) {
                    this.setStrip64BitLibraries(other.getStrip64BitLibraries());
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
                StandaloneConfig parsedMessage = null;
                try {
                    parsedMessage = (StandaloneConfig)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (StandaloneConfig)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureSplitDimensionIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.splitDimension_ = new ArrayList<SplitDimension>(this.splitDimension_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<SplitDimension> getSplitDimensionList() {
                if (this.splitDimensionBuilder_ == null) {
                    return Collections.unmodifiableList(this.splitDimension_);
                }
                return this.splitDimensionBuilder_.getMessageList();
            }

            @Override
            public int getSplitDimensionCount() {
                if (this.splitDimensionBuilder_ == null) {
                    return this.splitDimension_.size();
                }
                return this.splitDimensionBuilder_.getCount();
            }

            @Override
            public SplitDimension getSplitDimension(int index) {
                if (this.splitDimensionBuilder_ == null) {
                    return this.splitDimension_.get(index);
                }
                return this.splitDimensionBuilder_.getMessage(index);
            }

            public Builder setSplitDimension(int index, SplitDimension value) {
                if (this.splitDimensionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.set(index, value);
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setSplitDimension(int index, SplitDimension.Builder builderForValue) {
                if (this.splitDimensionBuilder_ == null) {
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addSplitDimension(SplitDimension value) {
                if (this.splitDimensionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.add(value);
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addSplitDimension(int index, SplitDimension value) {
                if (this.splitDimensionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.add(index, value);
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addSplitDimension(SplitDimension.Builder builderForValue) {
                if (this.splitDimensionBuilder_ == null) {
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addSplitDimension(int index, SplitDimension.Builder builderForValue) {
                if (this.splitDimensionBuilder_ == null) {
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllSplitDimension(Iterable<? extends SplitDimension> values2) {
                if (this.splitDimensionBuilder_ == null) {
                    this.ensureSplitDimensionIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.splitDimension_);
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearSplitDimension() {
                if (this.splitDimensionBuilder_ == null) {
                    this.splitDimension_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.clear();
                }
                return this;
            }

            public Builder removeSplitDimension(int index) {
                if (this.splitDimensionBuilder_ == null) {
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.remove(index);
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.remove(index);
                }
                return this;
            }

            public SplitDimension.Builder getSplitDimensionBuilder(int index) {
                return this.getSplitDimensionFieldBuilder().getBuilder(index);
            }

            @Override
            public SplitDimensionOrBuilder getSplitDimensionOrBuilder(int index) {
                if (this.splitDimensionBuilder_ == null) {
                    return this.splitDimension_.get(index);
                }
                return this.splitDimensionBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends SplitDimensionOrBuilder> getSplitDimensionOrBuilderList() {
                if (this.splitDimensionBuilder_ != null) {
                    return this.splitDimensionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.splitDimension_);
            }

            public SplitDimension.Builder addSplitDimensionBuilder() {
                return this.getSplitDimensionFieldBuilder().addBuilder(SplitDimension.getDefaultInstance());
            }

            public SplitDimension.Builder addSplitDimensionBuilder(int index) {
                return this.getSplitDimensionFieldBuilder().addBuilder(index, SplitDimension.getDefaultInstance());
            }

            public List<SplitDimension.Builder> getSplitDimensionBuilderList() {
                return this.getSplitDimensionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<SplitDimension, SplitDimension.Builder, SplitDimensionOrBuilder> getSplitDimensionFieldBuilder() {
                if (this.splitDimensionBuilder_ == null) {
                    this.splitDimensionBuilder_ = new RepeatedFieldBuilderV3(this.splitDimension_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.splitDimension_ = null;
                }
                return this.splitDimensionBuilder_;
            }

            @Override
            public boolean getStrip64BitLibraries() {
                return this.strip64BitLibraries_;
            }

            public Builder setStrip64BitLibraries(boolean value) {
                this.strip64BitLibraries_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearStrip64BitLibraries() {
                this.strip64BitLibraries_ = false;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface StandaloneConfigOrBuilder
    extends MessageOrBuilder {
        public List<SplitDimension> getSplitDimensionList();

        public SplitDimension getSplitDimension(int var1);

        public int getSplitDimensionCount();

        public List<? extends SplitDimensionOrBuilder> getSplitDimensionOrBuilderList();

        public SplitDimensionOrBuilder getSplitDimensionOrBuilder(int var1);

        public boolean getStrip64BitLibraries();
    }

    public static final class SplitsConfig
    extends GeneratedMessageV3
    implements SplitsConfigOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int SPLIT_DIMENSION_FIELD_NUMBER = 1;
        private List<SplitDimension> splitDimension_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SplitsConfig DEFAULT_INSTANCE = new SplitsConfig();
        private static final Parser<SplitsConfig> PARSER = new AbstractParser<SplitsConfig>(){

            @Override
            public SplitsConfig parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SplitsConfig(input, extensionRegistry);
            }
        };

        private SplitsConfig(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SplitsConfig() {
            this.splitDimension_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SplitsConfig(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block10;
                            done = true;
                            continue block10;
                        }
                        case 10: 
                    }
                    if (!(mutable_bitField0_ & true)) {
                        this.splitDimension_ = new ArrayList<SplitDimension>();
                        mutable_bitField0_ |= true;
                    }
                    this.splitDimension_.add(input.readMessage(SplitDimension.parser(), extensionRegistry));
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
                    this.splitDimension_ = Collections.unmodifiableList(this.splitDimension_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_SplitsConfig_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_SplitsConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(SplitsConfig.class, Builder.class);
        }

        @Override
        public List<SplitDimension> getSplitDimensionList() {
            return this.splitDimension_;
        }

        @Override
        public List<? extends SplitDimensionOrBuilder> getSplitDimensionOrBuilderList() {
            return this.splitDimension_;
        }

        @Override
        public int getSplitDimensionCount() {
            return this.splitDimension_.size();
        }

        @Override
        public SplitDimension getSplitDimension(int index) {
            return this.splitDimension_.get(index);
        }

        @Override
        public SplitDimensionOrBuilder getSplitDimensionOrBuilder(int index) {
            return this.splitDimension_.get(index);
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
            for (int i2 = 0; i2 < this.splitDimension_.size(); ++i2) {
                output.writeMessage(1, this.splitDimension_.get(i2));
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
            for (int i2 = 0; i2 < this.splitDimension_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.splitDimension_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SplitsConfig)) {
                return super.equals(obj);
            }
            SplitsConfig other = (SplitsConfig)obj;
            boolean result = true;
            result = result && this.getSplitDimensionList().equals(other.getSplitDimensionList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + SplitsConfig.getDescriptor().hashCode();
            if (this.getSplitDimensionCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getSplitDimensionList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static SplitsConfig parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SplitsConfig parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SplitsConfig parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SplitsConfig parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SplitsConfig parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SplitsConfig parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SplitsConfig parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SplitsConfig parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SplitsConfig parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SplitsConfig parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SplitsConfig parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SplitsConfig parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return SplitsConfig.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SplitsConfig prototype) {
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

        public static SplitsConfig getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SplitsConfig> parser() {
            return PARSER;
        }

        public Parser<SplitsConfig> getParserForType() {
            return PARSER;
        }

        @Override
        public SplitsConfig getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SplitsConfigOrBuilder {
            private int bitField0_;
            private List<SplitDimension> splitDimension_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<SplitDimension, SplitDimension.Builder, SplitDimensionOrBuilder> splitDimensionBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_SplitsConfig_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_SplitsConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(SplitsConfig.class, Builder.class);
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
                    this.getSplitDimensionFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.splitDimensionBuilder_ == null) {
                    this.splitDimension_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.splitDimensionBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_SplitsConfig_descriptor;
            }

            @Override
            public SplitsConfig getDefaultInstanceForType() {
                return SplitsConfig.getDefaultInstance();
            }

            @Override
            public SplitsConfig build() {
                SplitsConfig result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public SplitsConfig buildPartial() {
                SplitsConfig result = new SplitsConfig(this);
                int from_bitField0_ = this.bitField0_;
                if (this.splitDimensionBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.splitDimension_ = Collections.unmodifiableList(this.splitDimension_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.splitDimension_ = this.splitDimension_;
                } else {
                    result.splitDimension_ = this.splitDimensionBuilder_.build();
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
                if (other instanceof SplitsConfig) {
                    return this.mergeFrom((SplitsConfig)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SplitsConfig other) {
                if (other == SplitsConfig.getDefaultInstance()) {
                    return this;
                }
                if (this.splitDimensionBuilder_ == null) {
                    if (!other.splitDimension_.isEmpty()) {
                        if (this.splitDimension_.isEmpty()) {
                            this.splitDimension_ = other.splitDimension_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureSplitDimensionIsMutable();
                            this.splitDimension_.addAll(other.splitDimension_);
                        }
                        this.onChanged();
                    }
                } else if (!other.splitDimension_.isEmpty()) {
                    if (this.splitDimensionBuilder_.isEmpty()) {
                        this.splitDimensionBuilder_.dispose();
                        this.splitDimensionBuilder_ = null;
                        this.splitDimension_ = other.splitDimension_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.splitDimensionBuilder_ = alwaysUseFieldBuilders ? this.getSplitDimensionFieldBuilder() : null;
                    } else {
                        this.splitDimensionBuilder_.addAllMessages(other.splitDimension_);
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
                SplitsConfig parsedMessage = null;
                try {
                    parsedMessage = (SplitsConfig)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (SplitsConfig)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureSplitDimensionIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.splitDimension_ = new ArrayList<SplitDimension>(this.splitDimension_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<SplitDimension> getSplitDimensionList() {
                if (this.splitDimensionBuilder_ == null) {
                    return Collections.unmodifiableList(this.splitDimension_);
                }
                return this.splitDimensionBuilder_.getMessageList();
            }

            @Override
            public int getSplitDimensionCount() {
                if (this.splitDimensionBuilder_ == null) {
                    return this.splitDimension_.size();
                }
                return this.splitDimensionBuilder_.getCount();
            }

            @Override
            public SplitDimension getSplitDimension(int index) {
                if (this.splitDimensionBuilder_ == null) {
                    return this.splitDimension_.get(index);
                }
                return this.splitDimensionBuilder_.getMessage(index);
            }

            public Builder setSplitDimension(int index, SplitDimension value) {
                if (this.splitDimensionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.set(index, value);
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setSplitDimension(int index, SplitDimension.Builder builderForValue) {
                if (this.splitDimensionBuilder_ == null) {
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addSplitDimension(SplitDimension value) {
                if (this.splitDimensionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.add(value);
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addSplitDimension(int index, SplitDimension value) {
                if (this.splitDimensionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.add(index, value);
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addSplitDimension(SplitDimension.Builder builderForValue) {
                if (this.splitDimensionBuilder_ == null) {
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addSplitDimension(int index, SplitDimension.Builder builderForValue) {
                if (this.splitDimensionBuilder_ == null) {
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllSplitDimension(Iterable<? extends SplitDimension> values2) {
                if (this.splitDimensionBuilder_ == null) {
                    this.ensureSplitDimensionIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.splitDimension_);
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearSplitDimension() {
                if (this.splitDimensionBuilder_ == null) {
                    this.splitDimension_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.clear();
                }
                return this;
            }

            public Builder removeSplitDimension(int index) {
                if (this.splitDimensionBuilder_ == null) {
                    this.ensureSplitDimensionIsMutable();
                    this.splitDimension_.remove(index);
                    this.onChanged();
                } else {
                    this.splitDimensionBuilder_.remove(index);
                }
                return this;
            }

            public SplitDimension.Builder getSplitDimensionBuilder(int index) {
                return this.getSplitDimensionFieldBuilder().getBuilder(index);
            }

            @Override
            public SplitDimensionOrBuilder getSplitDimensionOrBuilder(int index) {
                if (this.splitDimensionBuilder_ == null) {
                    return this.splitDimension_.get(index);
                }
                return this.splitDimensionBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends SplitDimensionOrBuilder> getSplitDimensionOrBuilderList() {
                if (this.splitDimensionBuilder_ != null) {
                    return this.splitDimensionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.splitDimension_);
            }

            public SplitDimension.Builder addSplitDimensionBuilder() {
                return this.getSplitDimensionFieldBuilder().addBuilder(SplitDimension.getDefaultInstance());
            }

            public SplitDimension.Builder addSplitDimensionBuilder(int index) {
                return this.getSplitDimensionFieldBuilder().addBuilder(index, SplitDimension.getDefaultInstance());
            }

            public List<SplitDimension.Builder> getSplitDimensionBuilderList() {
                return this.getSplitDimensionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<SplitDimension, SplitDimension.Builder, SplitDimensionOrBuilder> getSplitDimensionFieldBuilder() {
                if (this.splitDimensionBuilder_ == null) {
                    this.splitDimensionBuilder_ = new RepeatedFieldBuilderV3(this.splitDimension_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.splitDimension_ = null;
                }
                return this.splitDimensionBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface SplitsConfigOrBuilder
    extends MessageOrBuilder {
        public List<SplitDimension> getSplitDimensionList();

        public SplitDimension getSplitDimension(int var1);

        public int getSplitDimensionCount();

        public List<? extends SplitDimensionOrBuilder> getSplitDimensionOrBuilderList();

        public SplitDimensionOrBuilder getSplitDimensionOrBuilder(int var1);
    }

    public static final class UncompressDexFiles
    extends GeneratedMessageV3
    implements UncompressDexFilesOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ENABLED_FIELD_NUMBER = 1;
        private boolean enabled_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final UncompressDexFiles DEFAULT_INSTANCE = new UncompressDexFiles();
        private static final Parser<UncompressDexFiles> PARSER = new AbstractParser<UncompressDexFiles>(){

            @Override
            public UncompressDexFiles parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new UncompressDexFiles(input, extensionRegistry);
            }
        };

        private UncompressDexFiles(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private UncompressDexFiles() {
            this.enabled_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private UncompressDexFiles(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block10;
                            done = true;
                            continue block10;
                        }
                        case 8: 
                    }
                    this.enabled_ = input.readBool();
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
            return internal_static_android_bundle_UncompressDexFiles_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_UncompressDexFiles_fieldAccessorTable.ensureFieldAccessorsInitialized(UncompressDexFiles.class, Builder.class);
        }

        @Override
        public boolean getEnabled() {
            return this.enabled_;
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
            if (this.enabled_) {
                output.writeBool(1, this.enabled_);
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
            if (this.enabled_) {
                size += CodedOutputStream.computeBoolSize(1, this.enabled_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof UncompressDexFiles)) {
                return super.equals(obj);
            }
            UncompressDexFiles other = (UncompressDexFiles)obj;
            boolean result = true;
            result = result && this.getEnabled() == other.getEnabled();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + UncompressDexFiles.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getEnabled());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static UncompressDexFiles parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UncompressDexFiles parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UncompressDexFiles parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UncompressDexFiles parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UncompressDexFiles parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UncompressDexFiles parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UncompressDexFiles parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static UncompressDexFiles parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static UncompressDexFiles parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static UncompressDexFiles parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static UncompressDexFiles parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static UncompressDexFiles parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return UncompressDexFiles.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(UncompressDexFiles prototype) {
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

        public static UncompressDexFiles getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<UncompressDexFiles> parser() {
            return PARSER;
        }

        public Parser<UncompressDexFiles> getParserForType() {
            return PARSER;
        }

        @Override
        public UncompressDexFiles getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements UncompressDexFilesOrBuilder {
            private boolean enabled_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_UncompressDexFiles_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_UncompressDexFiles_fieldAccessorTable.ensureFieldAccessorsInitialized(UncompressDexFiles.class, Builder.class);
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
                this.enabled_ = false;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_UncompressDexFiles_descriptor;
            }

            @Override
            public UncompressDexFiles getDefaultInstanceForType() {
                return UncompressDexFiles.getDefaultInstance();
            }

            @Override
            public UncompressDexFiles build() {
                UncompressDexFiles result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public UncompressDexFiles buildPartial() {
                UncompressDexFiles result = new UncompressDexFiles(this);
                result.enabled_ = this.enabled_;
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
                if (other instanceof UncompressDexFiles) {
                    return this.mergeFrom((UncompressDexFiles)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(UncompressDexFiles other) {
                if (other == UncompressDexFiles.getDefaultInstance()) {
                    return this;
                }
                if (other.getEnabled()) {
                    this.setEnabled(other.getEnabled());
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
                UncompressDexFiles parsedMessage = null;
                try {
                    parsedMessage = (UncompressDexFiles)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (UncompressDexFiles)e2.getUnfinishedMessage();
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
            public boolean getEnabled() {
                return this.enabled_;
            }

            public Builder setEnabled(boolean value) {
                this.enabled_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearEnabled() {
                this.enabled_ = false;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface UncompressDexFilesOrBuilder
    extends MessageOrBuilder {
        public boolean getEnabled();
    }

    public static final class UncompressNativeLibraries
    extends GeneratedMessageV3
    implements UncompressNativeLibrariesOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ENABLED_FIELD_NUMBER = 1;
        private boolean enabled_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final UncompressNativeLibraries DEFAULT_INSTANCE = new UncompressNativeLibraries();
        private static final Parser<UncompressNativeLibraries> PARSER = new AbstractParser<UncompressNativeLibraries>(){

            @Override
            public UncompressNativeLibraries parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new UncompressNativeLibraries(input, extensionRegistry);
            }
        };

        private UncompressNativeLibraries(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private UncompressNativeLibraries() {
            this.enabled_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private UncompressNativeLibraries(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block10;
                            done = true;
                            continue block10;
                        }
                        case 8: 
                    }
                    this.enabled_ = input.readBool();
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
            return internal_static_android_bundle_UncompressNativeLibraries_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_UncompressNativeLibraries_fieldAccessorTable.ensureFieldAccessorsInitialized(UncompressNativeLibraries.class, Builder.class);
        }

        @Override
        public boolean getEnabled() {
            return this.enabled_;
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
            if (this.enabled_) {
                output.writeBool(1, this.enabled_);
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
            if (this.enabled_) {
                size += CodedOutputStream.computeBoolSize(1, this.enabled_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof UncompressNativeLibraries)) {
                return super.equals(obj);
            }
            UncompressNativeLibraries other = (UncompressNativeLibraries)obj;
            boolean result = true;
            result = result && this.getEnabled() == other.getEnabled();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + UncompressNativeLibraries.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getEnabled());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static UncompressNativeLibraries parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UncompressNativeLibraries parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UncompressNativeLibraries parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UncompressNativeLibraries parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UncompressNativeLibraries parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UncompressNativeLibraries parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UncompressNativeLibraries parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static UncompressNativeLibraries parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static UncompressNativeLibraries parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static UncompressNativeLibraries parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static UncompressNativeLibraries parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static UncompressNativeLibraries parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return UncompressNativeLibraries.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(UncompressNativeLibraries prototype) {
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

        public static UncompressNativeLibraries getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<UncompressNativeLibraries> parser() {
            return PARSER;
        }

        public Parser<UncompressNativeLibraries> getParserForType() {
            return PARSER;
        }

        @Override
        public UncompressNativeLibraries getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements UncompressNativeLibrariesOrBuilder {
            private boolean enabled_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_UncompressNativeLibraries_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_UncompressNativeLibraries_fieldAccessorTable.ensureFieldAccessorsInitialized(UncompressNativeLibraries.class, Builder.class);
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
                this.enabled_ = false;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_UncompressNativeLibraries_descriptor;
            }

            @Override
            public UncompressNativeLibraries getDefaultInstanceForType() {
                return UncompressNativeLibraries.getDefaultInstance();
            }

            @Override
            public UncompressNativeLibraries build() {
                UncompressNativeLibraries result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public UncompressNativeLibraries buildPartial() {
                UncompressNativeLibraries result = new UncompressNativeLibraries(this);
                result.enabled_ = this.enabled_;
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
                if (other instanceof UncompressNativeLibraries) {
                    return this.mergeFrom((UncompressNativeLibraries)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(UncompressNativeLibraries other) {
                if (other == UncompressNativeLibraries.getDefaultInstance()) {
                    return this;
                }
                if (other.getEnabled()) {
                    this.setEnabled(other.getEnabled());
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
                UncompressNativeLibraries parsedMessage = null;
                try {
                    parsedMessage = (UncompressNativeLibraries)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (UncompressNativeLibraries)e2.getUnfinishedMessage();
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
            public boolean getEnabled() {
                return this.enabled_;
            }

            public Builder setEnabled(boolean value) {
                this.enabled_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearEnabled() {
                this.enabled_ = false;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface UncompressNativeLibrariesOrBuilder
    extends MessageOrBuilder {
        public boolean getEnabled();
    }

    public static final class Optimizations
    extends GeneratedMessageV3
    implements OptimizationsOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int SPLITS_CONFIG_FIELD_NUMBER = 1;
        private SplitsConfig splitsConfig_;
        public static final int UNCOMPRESS_NATIVE_LIBRARIES_FIELD_NUMBER = 2;
        private UncompressNativeLibraries uncompressNativeLibraries_;
        public static final int UNCOMPRESS_DEX_FILES_FIELD_NUMBER = 3;
        private UncompressDexFiles uncompressDexFiles_;
        public static final int STANDALONE_CONFIG_FIELD_NUMBER = 4;
        private StandaloneConfig standaloneConfig_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Optimizations DEFAULT_INSTANCE = new Optimizations();
        private static final Parser<Optimizations> PARSER = new AbstractParser<Optimizations>(){

            @Override
            public Optimizations parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Optimizations(input, extensionRegistry);
            }
        };

        private Optimizations(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Optimizations() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Optimizations(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block13: while (!done) {
                    GeneratedMessageV3.Builder subBuilder;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block13;
                        }
                        default: {
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block13;
                            done = true;
                            continue block13;
                        }
                        case 10: {
                            subBuilder = null;
                            if (this.splitsConfig_ != null) {
                                subBuilder = this.splitsConfig_.toBuilder();
                            }
                            this.splitsConfig_ = input.readMessage(SplitsConfig.parser(), extensionRegistry);
                            if (subBuilder == null) continue block13;
                            ((SplitsConfig.Builder)subBuilder).mergeFrom(this.splitsConfig_);
                            this.splitsConfig_ = ((SplitsConfig.Builder)subBuilder).buildPartial();
                            continue block13;
                        }
                        case 18: {
                            subBuilder = null;
                            if (this.uncompressNativeLibraries_ != null) {
                                subBuilder = this.uncompressNativeLibraries_.toBuilder();
                            }
                            this.uncompressNativeLibraries_ = input.readMessage(UncompressNativeLibraries.parser(), extensionRegistry);
                            if (subBuilder == null) continue block13;
                            ((UncompressNativeLibraries.Builder)subBuilder).mergeFrom(this.uncompressNativeLibraries_);
                            this.uncompressNativeLibraries_ = ((UncompressNativeLibraries.Builder)subBuilder).buildPartial();
                            continue block13;
                        }
                        case 26: {
                            subBuilder = null;
                            if (this.uncompressDexFiles_ != null) {
                                subBuilder = this.uncompressDexFiles_.toBuilder();
                            }
                            this.uncompressDexFiles_ = input.readMessage(UncompressDexFiles.parser(), extensionRegistry);
                            if (subBuilder == null) continue block13;
                            ((UncompressDexFiles.Builder)subBuilder).mergeFrom(this.uncompressDexFiles_);
                            this.uncompressDexFiles_ = ((UncompressDexFiles.Builder)subBuilder).buildPartial();
                            continue block13;
                        }
                        case 34: 
                    }
                    subBuilder = null;
                    if (this.standaloneConfig_ != null) {
                        subBuilder = this.standaloneConfig_.toBuilder();
                    }
                    this.standaloneConfig_ = input.readMessage(StandaloneConfig.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    ((StandaloneConfig.Builder)subBuilder).mergeFrom(this.standaloneConfig_);
                    this.standaloneConfig_ = ((StandaloneConfig.Builder)subBuilder).buildPartial();
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
            return internal_static_android_bundle_Optimizations_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Optimizations_fieldAccessorTable.ensureFieldAccessorsInitialized(Optimizations.class, Builder.class);
        }

        @Override
        public boolean hasSplitsConfig() {
            return this.splitsConfig_ != null;
        }

        @Override
        public SplitsConfig getSplitsConfig() {
            return this.splitsConfig_ == null ? SplitsConfig.getDefaultInstance() : this.splitsConfig_;
        }

        @Override
        public SplitsConfigOrBuilder getSplitsConfigOrBuilder() {
            return this.getSplitsConfig();
        }

        @Override
        public boolean hasUncompressNativeLibraries() {
            return this.uncompressNativeLibraries_ != null;
        }

        @Override
        public UncompressNativeLibraries getUncompressNativeLibraries() {
            return this.uncompressNativeLibraries_ == null ? UncompressNativeLibraries.getDefaultInstance() : this.uncompressNativeLibraries_;
        }

        @Override
        public UncompressNativeLibrariesOrBuilder getUncompressNativeLibrariesOrBuilder() {
            return this.getUncompressNativeLibraries();
        }

        @Override
        public boolean hasUncompressDexFiles() {
            return this.uncompressDexFiles_ != null;
        }

        @Override
        public UncompressDexFiles getUncompressDexFiles() {
            return this.uncompressDexFiles_ == null ? UncompressDexFiles.getDefaultInstance() : this.uncompressDexFiles_;
        }

        @Override
        public UncompressDexFilesOrBuilder getUncompressDexFilesOrBuilder() {
            return this.getUncompressDexFiles();
        }

        @Override
        public boolean hasStandaloneConfig() {
            return this.standaloneConfig_ != null;
        }

        @Override
        public StandaloneConfig getStandaloneConfig() {
            return this.standaloneConfig_ == null ? StandaloneConfig.getDefaultInstance() : this.standaloneConfig_;
        }

        @Override
        public StandaloneConfigOrBuilder getStandaloneConfigOrBuilder() {
            return this.getStandaloneConfig();
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
            if (this.splitsConfig_ != null) {
                output.writeMessage(1, this.getSplitsConfig());
            }
            if (this.uncompressNativeLibraries_ != null) {
                output.writeMessage(2, this.getUncompressNativeLibraries());
            }
            if (this.uncompressDexFiles_ != null) {
                output.writeMessage(3, this.getUncompressDexFiles());
            }
            if (this.standaloneConfig_ != null) {
                output.writeMessage(4, this.getStandaloneConfig());
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
            if (this.splitsConfig_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getSplitsConfig());
            }
            if (this.uncompressNativeLibraries_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getUncompressNativeLibraries());
            }
            if (this.uncompressDexFiles_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getUncompressDexFiles());
            }
            if (this.standaloneConfig_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getStandaloneConfig());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Optimizations)) {
                return super.equals(obj);
            }
            Optimizations other = (Optimizations)obj;
            boolean result = true;
            boolean bl = result = result && this.hasSplitsConfig() == other.hasSplitsConfig();
            if (this.hasSplitsConfig()) {
                result = result && this.getSplitsConfig().equals(other.getSplitsConfig());
            }
            boolean bl2 = result = result && this.hasUncompressNativeLibraries() == other.hasUncompressNativeLibraries();
            if (this.hasUncompressNativeLibraries()) {
                result = result && this.getUncompressNativeLibraries().equals(other.getUncompressNativeLibraries());
            }
            boolean bl3 = result = result && this.hasUncompressDexFiles() == other.hasUncompressDexFiles();
            if (this.hasUncompressDexFiles()) {
                result = result && this.getUncompressDexFiles().equals(other.getUncompressDexFiles());
            }
            boolean bl4 = result = result && this.hasStandaloneConfig() == other.hasStandaloneConfig();
            if (this.hasStandaloneConfig()) {
                result = result && this.getStandaloneConfig().equals(other.getStandaloneConfig());
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
            hash = 19 * hash + Optimizations.getDescriptor().hashCode();
            if (this.hasSplitsConfig()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getSplitsConfig().hashCode();
            }
            if (this.hasUncompressNativeLibraries()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getUncompressNativeLibraries().hashCode();
            }
            if (this.hasUncompressDexFiles()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getUncompressDexFiles().hashCode();
            }
            if (this.hasStandaloneConfig()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getStandaloneConfig().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Optimizations parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Optimizations parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Optimizations parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Optimizations parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Optimizations parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Optimizations parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Optimizations parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Optimizations parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Optimizations parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Optimizations parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Optimizations parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Optimizations parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Optimizations.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Optimizations prototype) {
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

        public static Optimizations getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Optimizations> parser() {
            return PARSER;
        }

        public Parser<Optimizations> getParserForType() {
            return PARSER;
        }

        @Override
        public Optimizations getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements OptimizationsOrBuilder {
            private SplitsConfig splitsConfig_ = null;
            private SingleFieldBuilderV3<SplitsConfig, SplitsConfig.Builder, SplitsConfigOrBuilder> splitsConfigBuilder_;
            private UncompressNativeLibraries uncompressNativeLibraries_ = null;
            private SingleFieldBuilderV3<UncompressNativeLibraries, UncompressNativeLibraries.Builder, UncompressNativeLibrariesOrBuilder> uncompressNativeLibrariesBuilder_;
            private UncompressDexFiles uncompressDexFiles_ = null;
            private SingleFieldBuilderV3<UncompressDexFiles, UncompressDexFiles.Builder, UncompressDexFilesOrBuilder> uncompressDexFilesBuilder_;
            private StandaloneConfig standaloneConfig_ = null;
            private SingleFieldBuilderV3<StandaloneConfig, StandaloneConfig.Builder, StandaloneConfigOrBuilder> standaloneConfigBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Optimizations_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Optimizations_fieldAccessorTable.ensureFieldAccessorsInitialized(Optimizations.class, Builder.class);
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
                if (this.splitsConfigBuilder_ == null) {
                    this.splitsConfig_ = null;
                } else {
                    this.splitsConfig_ = null;
                    this.splitsConfigBuilder_ = null;
                }
                if (this.uncompressNativeLibrariesBuilder_ == null) {
                    this.uncompressNativeLibraries_ = null;
                } else {
                    this.uncompressNativeLibraries_ = null;
                    this.uncompressNativeLibrariesBuilder_ = null;
                }
                if (this.uncompressDexFilesBuilder_ == null) {
                    this.uncompressDexFiles_ = null;
                } else {
                    this.uncompressDexFiles_ = null;
                    this.uncompressDexFilesBuilder_ = null;
                }
                if (this.standaloneConfigBuilder_ == null) {
                    this.standaloneConfig_ = null;
                } else {
                    this.standaloneConfig_ = null;
                    this.standaloneConfigBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Optimizations_descriptor;
            }

            @Override
            public Optimizations getDefaultInstanceForType() {
                return Optimizations.getDefaultInstance();
            }

            @Override
            public Optimizations build() {
                Optimizations result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Optimizations buildPartial() {
                Optimizations result = new Optimizations(this);
                if (this.splitsConfigBuilder_ == null) {
                    result.splitsConfig_ = this.splitsConfig_;
                } else {
                    result.splitsConfig_ = this.splitsConfigBuilder_.build();
                }
                if (this.uncompressNativeLibrariesBuilder_ == null) {
                    result.uncompressNativeLibraries_ = this.uncompressNativeLibraries_;
                } else {
                    result.uncompressNativeLibraries_ = this.uncompressNativeLibrariesBuilder_.build();
                }
                if (this.uncompressDexFilesBuilder_ == null) {
                    result.uncompressDexFiles_ = this.uncompressDexFiles_;
                } else {
                    result.uncompressDexFiles_ = this.uncompressDexFilesBuilder_.build();
                }
                if (this.standaloneConfigBuilder_ == null) {
                    result.standaloneConfig_ = this.standaloneConfig_;
                } else {
                    result.standaloneConfig_ = this.standaloneConfigBuilder_.build();
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
                if (other instanceof Optimizations) {
                    return this.mergeFrom((Optimizations)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Optimizations other) {
                if (other == Optimizations.getDefaultInstance()) {
                    return this;
                }
                if (other.hasSplitsConfig()) {
                    this.mergeSplitsConfig(other.getSplitsConfig());
                }
                if (other.hasUncompressNativeLibraries()) {
                    this.mergeUncompressNativeLibraries(other.getUncompressNativeLibraries());
                }
                if (other.hasUncompressDexFiles()) {
                    this.mergeUncompressDexFiles(other.getUncompressDexFiles());
                }
                if (other.hasStandaloneConfig()) {
                    this.mergeStandaloneConfig(other.getStandaloneConfig());
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
                Optimizations parsedMessage = null;
                try {
                    parsedMessage = (Optimizations)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Optimizations)e2.getUnfinishedMessage();
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
            public boolean hasSplitsConfig() {
                return this.splitsConfigBuilder_ != null || this.splitsConfig_ != null;
            }

            @Override
            public SplitsConfig getSplitsConfig() {
                if (this.splitsConfigBuilder_ == null) {
                    return this.splitsConfig_ == null ? SplitsConfig.getDefaultInstance() : this.splitsConfig_;
                }
                return this.splitsConfigBuilder_.getMessage();
            }

            public Builder setSplitsConfig(SplitsConfig value) {
                if (this.splitsConfigBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.splitsConfig_ = value;
                    this.onChanged();
                } else {
                    this.splitsConfigBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setSplitsConfig(SplitsConfig.Builder builderForValue) {
                if (this.splitsConfigBuilder_ == null) {
                    this.splitsConfig_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.splitsConfigBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeSplitsConfig(SplitsConfig value) {
                if (this.splitsConfigBuilder_ == null) {
                    this.splitsConfig_ = this.splitsConfig_ != null ? SplitsConfig.newBuilder(this.splitsConfig_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.splitsConfigBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearSplitsConfig() {
                if (this.splitsConfigBuilder_ == null) {
                    this.splitsConfig_ = null;
                    this.onChanged();
                } else {
                    this.splitsConfig_ = null;
                    this.splitsConfigBuilder_ = null;
                }
                return this;
            }

            public SplitsConfig.Builder getSplitsConfigBuilder() {
                this.onChanged();
                return this.getSplitsConfigFieldBuilder().getBuilder();
            }

            @Override
            public SplitsConfigOrBuilder getSplitsConfigOrBuilder() {
                if (this.splitsConfigBuilder_ != null) {
                    return this.splitsConfigBuilder_.getMessageOrBuilder();
                }
                return this.splitsConfig_ == null ? SplitsConfig.getDefaultInstance() : this.splitsConfig_;
            }

            private SingleFieldBuilderV3<SplitsConfig, SplitsConfig.Builder, SplitsConfigOrBuilder> getSplitsConfigFieldBuilder() {
                if (this.splitsConfigBuilder_ == null) {
                    this.splitsConfigBuilder_ = new SingleFieldBuilderV3(this.getSplitsConfig(), this.getParentForChildren(), this.isClean());
                    this.splitsConfig_ = null;
                }
                return this.splitsConfigBuilder_;
            }

            @Override
            public boolean hasUncompressNativeLibraries() {
                return this.uncompressNativeLibrariesBuilder_ != null || this.uncompressNativeLibraries_ != null;
            }

            @Override
            public UncompressNativeLibraries getUncompressNativeLibraries() {
                if (this.uncompressNativeLibrariesBuilder_ == null) {
                    return this.uncompressNativeLibraries_ == null ? UncompressNativeLibraries.getDefaultInstance() : this.uncompressNativeLibraries_;
                }
                return this.uncompressNativeLibrariesBuilder_.getMessage();
            }

            public Builder setUncompressNativeLibraries(UncompressNativeLibraries value) {
                if (this.uncompressNativeLibrariesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.uncompressNativeLibraries_ = value;
                    this.onChanged();
                } else {
                    this.uncompressNativeLibrariesBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setUncompressNativeLibraries(UncompressNativeLibraries.Builder builderForValue) {
                if (this.uncompressNativeLibrariesBuilder_ == null) {
                    this.uncompressNativeLibraries_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.uncompressNativeLibrariesBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeUncompressNativeLibraries(UncompressNativeLibraries value) {
                if (this.uncompressNativeLibrariesBuilder_ == null) {
                    this.uncompressNativeLibraries_ = this.uncompressNativeLibraries_ != null ? UncompressNativeLibraries.newBuilder(this.uncompressNativeLibraries_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.uncompressNativeLibrariesBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearUncompressNativeLibraries() {
                if (this.uncompressNativeLibrariesBuilder_ == null) {
                    this.uncompressNativeLibraries_ = null;
                    this.onChanged();
                } else {
                    this.uncompressNativeLibraries_ = null;
                    this.uncompressNativeLibrariesBuilder_ = null;
                }
                return this;
            }

            public UncompressNativeLibraries.Builder getUncompressNativeLibrariesBuilder() {
                this.onChanged();
                return this.getUncompressNativeLibrariesFieldBuilder().getBuilder();
            }

            @Override
            public UncompressNativeLibrariesOrBuilder getUncompressNativeLibrariesOrBuilder() {
                if (this.uncompressNativeLibrariesBuilder_ != null) {
                    return this.uncompressNativeLibrariesBuilder_.getMessageOrBuilder();
                }
                return this.uncompressNativeLibraries_ == null ? UncompressNativeLibraries.getDefaultInstance() : this.uncompressNativeLibraries_;
            }

            private SingleFieldBuilderV3<UncompressNativeLibraries, UncompressNativeLibraries.Builder, UncompressNativeLibrariesOrBuilder> getUncompressNativeLibrariesFieldBuilder() {
                if (this.uncompressNativeLibrariesBuilder_ == null) {
                    this.uncompressNativeLibrariesBuilder_ = new SingleFieldBuilderV3(this.getUncompressNativeLibraries(), this.getParentForChildren(), this.isClean());
                    this.uncompressNativeLibraries_ = null;
                }
                return this.uncompressNativeLibrariesBuilder_;
            }

            @Override
            public boolean hasUncompressDexFiles() {
                return this.uncompressDexFilesBuilder_ != null || this.uncompressDexFiles_ != null;
            }

            @Override
            public UncompressDexFiles getUncompressDexFiles() {
                if (this.uncompressDexFilesBuilder_ == null) {
                    return this.uncompressDexFiles_ == null ? UncompressDexFiles.getDefaultInstance() : this.uncompressDexFiles_;
                }
                return this.uncompressDexFilesBuilder_.getMessage();
            }

            public Builder setUncompressDexFiles(UncompressDexFiles value) {
                if (this.uncompressDexFilesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.uncompressDexFiles_ = value;
                    this.onChanged();
                } else {
                    this.uncompressDexFilesBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setUncompressDexFiles(UncompressDexFiles.Builder builderForValue) {
                if (this.uncompressDexFilesBuilder_ == null) {
                    this.uncompressDexFiles_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.uncompressDexFilesBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeUncompressDexFiles(UncompressDexFiles value) {
                if (this.uncompressDexFilesBuilder_ == null) {
                    this.uncompressDexFiles_ = this.uncompressDexFiles_ != null ? UncompressDexFiles.newBuilder(this.uncompressDexFiles_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.uncompressDexFilesBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearUncompressDexFiles() {
                if (this.uncompressDexFilesBuilder_ == null) {
                    this.uncompressDexFiles_ = null;
                    this.onChanged();
                } else {
                    this.uncompressDexFiles_ = null;
                    this.uncompressDexFilesBuilder_ = null;
                }
                return this;
            }

            public UncompressDexFiles.Builder getUncompressDexFilesBuilder() {
                this.onChanged();
                return this.getUncompressDexFilesFieldBuilder().getBuilder();
            }

            @Override
            public UncompressDexFilesOrBuilder getUncompressDexFilesOrBuilder() {
                if (this.uncompressDexFilesBuilder_ != null) {
                    return this.uncompressDexFilesBuilder_.getMessageOrBuilder();
                }
                return this.uncompressDexFiles_ == null ? UncompressDexFiles.getDefaultInstance() : this.uncompressDexFiles_;
            }

            private SingleFieldBuilderV3<UncompressDexFiles, UncompressDexFiles.Builder, UncompressDexFilesOrBuilder> getUncompressDexFilesFieldBuilder() {
                if (this.uncompressDexFilesBuilder_ == null) {
                    this.uncompressDexFilesBuilder_ = new SingleFieldBuilderV3(this.getUncompressDexFiles(), this.getParentForChildren(), this.isClean());
                    this.uncompressDexFiles_ = null;
                }
                return this.uncompressDexFilesBuilder_;
            }

            @Override
            public boolean hasStandaloneConfig() {
                return this.standaloneConfigBuilder_ != null || this.standaloneConfig_ != null;
            }

            @Override
            public StandaloneConfig getStandaloneConfig() {
                if (this.standaloneConfigBuilder_ == null) {
                    return this.standaloneConfig_ == null ? StandaloneConfig.getDefaultInstance() : this.standaloneConfig_;
                }
                return this.standaloneConfigBuilder_.getMessage();
            }

            public Builder setStandaloneConfig(StandaloneConfig value) {
                if (this.standaloneConfigBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.standaloneConfig_ = value;
                    this.onChanged();
                } else {
                    this.standaloneConfigBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setStandaloneConfig(StandaloneConfig.Builder builderForValue) {
                if (this.standaloneConfigBuilder_ == null) {
                    this.standaloneConfig_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.standaloneConfigBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeStandaloneConfig(StandaloneConfig value) {
                if (this.standaloneConfigBuilder_ == null) {
                    this.standaloneConfig_ = this.standaloneConfig_ != null ? StandaloneConfig.newBuilder(this.standaloneConfig_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.standaloneConfigBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearStandaloneConfig() {
                if (this.standaloneConfigBuilder_ == null) {
                    this.standaloneConfig_ = null;
                    this.onChanged();
                } else {
                    this.standaloneConfig_ = null;
                    this.standaloneConfigBuilder_ = null;
                }
                return this;
            }

            public StandaloneConfig.Builder getStandaloneConfigBuilder() {
                this.onChanged();
                return this.getStandaloneConfigFieldBuilder().getBuilder();
            }

            @Override
            public StandaloneConfigOrBuilder getStandaloneConfigOrBuilder() {
                if (this.standaloneConfigBuilder_ != null) {
                    return this.standaloneConfigBuilder_.getMessageOrBuilder();
                }
                return this.standaloneConfig_ == null ? StandaloneConfig.getDefaultInstance() : this.standaloneConfig_;
            }

            private SingleFieldBuilderV3<StandaloneConfig, StandaloneConfig.Builder, StandaloneConfigOrBuilder> getStandaloneConfigFieldBuilder() {
                if (this.standaloneConfigBuilder_ == null) {
                    this.standaloneConfigBuilder_ = new SingleFieldBuilderV3(this.getStandaloneConfig(), this.getParentForChildren(), this.isClean());
                    this.standaloneConfig_ = null;
                }
                return this.standaloneConfigBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface OptimizationsOrBuilder
    extends MessageOrBuilder {
        public boolean hasSplitsConfig();

        public SplitsConfig getSplitsConfig();

        public SplitsConfigOrBuilder getSplitsConfigOrBuilder();

        public boolean hasUncompressNativeLibraries();

        public UncompressNativeLibraries getUncompressNativeLibraries();

        public UncompressNativeLibrariesOrBuilder getUncompressNativeLibrariesOrBuilder();

        public boolean hasUncompressDexFiles();

        public UncompressDexFiles getUncompressDexFiles();

        public UncompressDexFilesOrBuilder getUncompressDexFilesOrBuilder();

        public boolean hasStandaloneConfig();

        public StandaloneConfig getStandaloneConfig();

        public StandaloneConfigOrBuilder getStandaloneConfigOrBuilder();
    }

    public static final class MasterResources
    extends GeneratedMessageV3
    implements MasterResourcesOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int RESOURCE_IDS_FIELD_NUMBER = 1;
        private List<Integer> resourceIds_;
        private int resourceIdsMemoizedSerializedSize = -1;
        public static final int RESOURCE_NAMES_FIELD_NUMBER = 2;
        private LazyStringList resourceNames_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final MasterResources DEFAULT_INSTANCE = new MasterResources();
        private static final Parser<MasterResources> PARSER = new AbstractParser<MasterResources>(){

            @Override
            public MasterResources parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MasterResources(input, extensionRegistry);
            }
        };

        private MasterResources(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private MasterResources() {
            this.resourceIds_ = Collections.emptyList();
            this.resourceNames_ = LazyStringArrayList.EMPTY;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MasterResources(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block12;
                            done = true;
                            continue block12;
                        }
                        case 8: {
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.resourceIds_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 1;
                            }
                            this.resourceIds_.add(input.readInt32());
                            continue block12;
                        }
                        case 10: {
                            int length = input.readRawVarint32();
                            int limit = input.pushLimit(length);
                            if ((mutable_bitField0_ & 1) != 1 && input.getBytesUntilLimit() > 0) {
                                this.resourceIds_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= 1;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.resourceIds_.add(input.readInt32());
                            }
                            input.popLimit(limit);
                            continue block12;
                        }
                        case 18: 
                    }
                    String s3 = input.readStringRequireUtf8();
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.resourceNames_ = new LazyStringArrayList();
                        mutable_bitField0_ |= 2;
                    }
                    this.resourceNames_.add(s3);
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
                    this.resourceIds_ = Collections.unmodifiableList(this.resourceIds_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.resourceNames_ = this.resourceNames_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_MasterResources_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_MasterResources_fieldAccessorTable.ensureFieldAccessorsInitialized(MasterResources.class, Builder.class);
        }

        @Override
        public List<Integer> getResourceIdsList() {
            return this.resourceIds_;
        }

        @Override
        public int getResourceIdsCount() {
            return this.resourceIds_.size();
        }

        @Override
        public int getResourceIds(int index) {
            return this.resourceIds_.get(index);
        }

        public ProtocolStringList getResourceNamesList() {
            return this.resourceNames_;
        }

        @Override
        public int getResourceNamesCount() {
            return this.resourceNames_.size();
        }

        @Override
        public String getResourceNames(int index) {
            return (String)this.resourceNames_.get(index);
        }

        @Override
        public ByteString getResourceNamesBytes(int index) {
            return this.resourceNames_.getByteString(index);
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
            this.getSerializedSize();
            if (this.getResourceIdsList().size() > 0) {
                output.writeUInt32NoTag(10);
                output.writeUInt32NoTag(this.resourceIdsMemoizedSerializedSize);
            }
            for (i2 = 0; i2 < this.resourceIds_.size(); ++i2) {
                output.writeInt32NoTag(this.resourceIds_.get(i2));
            }
            for (i2 = 0; i2 < this.resourceNames_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 2, this.resourceNames_.getRaw(i2));
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
            int dataSize = 0;
            for (i2 = 0; i2 < this.resourceIds_.size(); ++i2) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.resourceIds_.get(i2));
            }
            size += dataSize;
            if (!this.getResourceIdsList().isEmpty()) {
                ++size;
                size += CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }
            this.resourceIdsMemoizedSerializedSize = dataSize;
            dataSize = 0;
            for (i2 = 0; i2 < this.resourceNames_.size(); ++i2) {
                dataSize += MasterResources.computeStringSizeNoTag(this.resourceNames_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getResourceNamesList().size();
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MasterResources)) {
                return super.equals(obj);
            }
            MasterResources other = (MasterResources)obj;
            boolean result = true;
            result = result && this.getResourceIdsList().equals(other.getResourceIdsList());
            result = result && this.getResourceNamesList().equals(other.getResourceNamesList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + MasterResources.getDescriptor().hashCode();
            if (this.getResourceIdsCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getResourceIdsList().hashCode();
            }
            if (this.getResourceNamesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getResourceNamesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static MasterResources parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MasterResources parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MasterResources parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MasterResources parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MasterResources parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MasterResources parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MasterResources parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MasterResources parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MasterResources parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MasterResources parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MasterResources parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MasterResources parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return MasterResources.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MasterResources prototype) {
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

        public static MasterResources getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MasterResources> parser() {
            return PARSER;
        }

        public Parser<MasterResources> getParserForType() {
            return PARSER;
        }

        @Override
        public MasterResources getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements MasterResourcesOrBuilder {
            private int bitField0_;
            private List<Integer> resourceIds_ = Collections.emptyList();
            private LazyStringList resourceNames_ = LazyStringArrayList.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_MasterResources_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_MasterResources_fieldAccessorTable.ensureFieldAccessorsInitialized(MasterResources.class, Builder.class);
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
                this.resourceIds_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.resourceNames_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_MasterResources_descriptor;
            }

            @Override
            public MasterResources getDefaultInstanceForType() {
                return MasterResources.getDefaultInstance();
            }

            @Override
            public MasterResources build() {
                MasterResources result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public MasterResources buildPartial() {
                MasterResources result = new MasterResources(this);
                int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.resourceIds_ = Collections.unmodifiableList(this.resourceIds_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.resourceIds_ = this.resourceIds_;
                if ((this.bitField0_ & 2) == 2) {
                    this.resourceNames_ = this.resourceNames_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.resourceNames_ = this.resourceNames_;
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
                if (other instanceof MasterResources) {
                    return this.mergeFrom((MasterResources)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MasterResources other) {
                if (other == MasterResources.getDefaultInstance()) {
                    return this;
                }
                if (!other.resourceIds_.isEmpty()) {
                    if (this.resourceIds_.isEmpty()) {
                        this.resourceIds_ = other.resourceIds_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureResourceIdsIsMutable();
                        this.resourceIds_.addAll(other.resourceIds_);
                    }
                    this.onChanged();
                }
                if (!other.resourceNames_.isEmpty()) {
                    if (this.resourceNames_.isEmpty()) {
                        this.resourceNames_ = other.resourceNames_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureResourceNamesIsMutable();
                        this.resourceNames_.addAll(other.resourceNames_);
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
                MasterResources parsedMessage = null;
                try {
                    parsedMessage = (MasterResources)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (MasterResources)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureResourceIdsIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.resourceIds_ = new ArrayList<Integer>(this.resourceIds_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<Integer> getResourceIdsList() {
                return Collections.unmodifiableList(this.resourceIds_);
            }

            @Override
            public int getResourceIdsCount() {
                return this.resourceIds_.size();
            }

            @Override
            public int getResourceIds(int index) {
                return this.resourceIds_.get(index);
            }

            public Builder setResourceIds(int index, int value) {
                this.ensureResourceIdsIsMutable();
                this.resourceIds_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addResourceIds(int value) {
                this.ensureResourceIdsIsMutable();
                this.resourceIds_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllResourceIds(Iterable<? extends Integer> values2) {
                this.ensureResourceIdsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.resourceIds_);
                this.onChanged();
                return this;
            }

            public Builder clearResourceIds() {
                this.resourceIds_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            private void ensureResourceNamesIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.resourceNames_ = new LazyStringArrayList(this.resourceNames_);
                    this.bitField0_ |= 2;
                }
            }

            public ProtocolStringList getResourceNamesList() {
                return this.resourceNames_.getUnmodifiableView();
            }

            @Override
            public int getResourceNamesCount() {
                return this.resourceNames_.size();
            }

            @Override
            public String getResourceNames(int index) {
                return (String)this.resourceNames_.get(index);
            }

            @Override
            public ByteString getResourceNamesBytes(int index) {
                return this.resourceNames_.getByteString(index);
            }

            public Builder setResourceNames(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureResourceNamesIsMutable();
                this.resourceNames_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addResourceNames(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureResourceNamesIsMutable();
                this.resourceNames_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllResourceNames(Iterable<String> values2) {
                this.ensureResourceNamesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.resourceNames_);
                this.onChanged();
                return this;
            }

            public Builder clearResourceNames() {
                this.resourceNames_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder addResourceNamesBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                MasterResources.checkByteStringIsUtf8(value);
                this.ensureResourceNamesIsMutable();
                this.resourceNames_.add(value);
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface MasterResourcesOrBuilder
    extends MessageOrBuilder {
        public List<Integer> getResourceIdsList();

        public int getResourceIdsCount();

        public int getResourceIds(int var1);

        public List<String> getResourceNamesList();

        public int getResourceNamesCount();

        public String getResourceNames(int var1);

        public ByteString getResourceNamesBytes(int var1);
    }

    public static final class Compression
    extends GeneratedMessageV3
    implements CompressionOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int UNCOMPRESSED_GLOB_FIELD_NUMBER = 1;
        private LazyStringList uncompressedGlob_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Compression DEFAULT_INSTANCE = new Compression();
        private static final Parser<Compression> PARSER = new AbstractParser<Compression>(){

            @Override
            public Compression parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Compression(input, extensionRegistry);
            }
        };

        private Compression(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Compression() {
            this.uncompressedGlob_ = LazyStringArrayList.EMPTY;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Compression(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block10;
                            done = true;
                            continue block10;
                        }
                        case 10: 
                    }
                    String s3 = input.readStringRequireUtf8();
                    if (!(mutable_bitField0_ & true)) {
                        this.uncompressedGlob_ = new LazyStringArrayList();
                        mutable_bitField0_ |= true;
                    }
                    this.uncompressedGlob_.add(s3);
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
                    this.uncompressedGlob_ = this.uncompressedGlob_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_Compression_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Compression_fieldAccessorTable.ensureFieldAccessorsInitialized(Compression.class, Builder.class);
        }

        public ProtocolStringList getUncompressedGlobList() {
            return this.uncompressedGlob_;
        }

        @Override
        public int getUncompressedGlobCount() {
            return this.uncompressedGlob_.size();
        }

        @Override
        public String getUncompressedGlob(int index) {
            return (String)this.uncompressedGlob_.get(index);
        }

        @Override
        public ByteString getUncompressedGlobBytes(int index) {
            return this.uncompressedGlob_.getByteString(index);
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
            for (int i2 = 0; i2 < this.uncompressedGlob_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 1, this.uncompressedGlob_.getRaw(i2));
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
            int dataSize = 0;
            for (int i2 = 0; i2 < this.uncompressedGlob_.size(); ++i2) {
                dataSize += Compression.computeStringSizeNoTag(this.uncompressedGlob_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getUncompressedGlobList().size();
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Compression)) {
                return super.equals(obj);
            }
            Compression other = (Compression)obj;
            boolean result = true;
            result = result && this.getUncompressedGlobList().equals(other.getUncompressedGlobList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Compression.getDescriptor().hashCode();
            if (this.getUncompressedGlobCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getUncompressedGlobList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Compression parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Compression parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Compression parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Compression parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Compression parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Compression parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Compression parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Compression parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Compression parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Compression parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Compression parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Compression parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Compression.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Compression prototype) {
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

        public static Compression getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Compression> parser() {
            return PARSER;
        }

        public Parser<Compression> getParserForType() {
            return PARSER;
        }

        @Override
        public Compression getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements CompressionOrBuilder {
            private int bitField0_;
            private LazyStringList uncompressedGlob_ = LazyStringArrayList.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Compression_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Compression_fieldAccessorTable.ensureFieldAccessorsInitialized(Compression.class, Builder.class);
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
                this.uncompressedGlob_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Compression_descriptor;
            }

            @Override
            public Compression getDefaultInstanceForType() {
                return Compression.getDefaultInstance();
            }

            @Override
            public Compression build() {
                Compression result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Compression buildPartial() {
                Compression result = new Compression(this);
                int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.uncompressedGlob_ = this.uncompressedGlob_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.uncompressedGlob_ = this.uncompressedGlob_;
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
                if (other instanceof Compression) {
                    return this.mergeFrom((Compression)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Compression other) {
                if (other == Compression.getDefaultInstance()) {
                    return this;
                }
                if (!other.uncompressedGlob_.isEmpty()) {
                    if (this.uncompressedGlob_.isEmpty()) {
                        this.uncompressedGlob_ = other.uncompressedGlob_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureUncompressedGlobIsMutable();
                        this.uncompressedGlob_.addAll(other.uncompressedGlob_);
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
                Compression parsedMessage = null;
                try {
                    parsedMessage = (Compression)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Compression)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureUncompressedGlobIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.uncompressedGlob_ = new LazyStringArrayList(this.uncompressedGlob_);
                    this.bitField0_ |= 1;
                }
            }

            public ProtocolStringList getUncompressedGlobList() {
                return this.uncompressedGlob_.getUnmodifiableView();
            }

            @Override
            public int getUncompressedGlobCount() {
                return this.uncompressedGlob_.size();
            }

            @Override
            public String getUncompressedGlob(int index) {
                return (String)this.uncompressedGlob_.get(index);
            }

            @Override
            public ByteString getUncompressedGlobBytes(int index) {
                return this.uncompressedGlob_.getByteString(index);
            }

            public Builder setUncompressedGlob(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureUncompressedGlobIsMutable();
                this.uncompressedGlob_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addUncompressedGlob(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureUncompressedGlobIsMutable();
                this.uncompressedGlob_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllUncompressedGlob(Iterable<String> values2) {
                this.ensureUncompressedGlobIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.uncompressedGlob_);
                this.onChanged();
                return this;
            }

            public Builder clearUncompressedGlob() {
                this.uncompressedGlob_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder addUncompressedGlobBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Compression.checkByteStringIsUtf8(value);
                this.ensureUncompressedGlobIsMutable();
                this.uncompressedGlob_.add(value);
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface CompressionOrBuilder
    extends MessageOrBuilder {
        public List<String> getUncompressedGlobList();

        public int getUncompressedGlobCount();

        public String getUncompressedGlob(int var1);

        public ByteString getUncompressedGlobBytes(int var1);
    }

    public static final class Bundletool
    extends GeneratedMessageV3
    implements BundletoolOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VERSION_FIELD_NUMBER = 2;
        private volatile Object version_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Bundletool DEFAULT_INSTANCE = new Bundletool();
        private static final Parser<Bundletool> PARSER = new AbstractParser<Bundletool>(){

            @Override
            public Bundletool parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Bundletool(input, extensionRegistry);
            }
        };

        private Bundletool(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Bundletool() {
            this.version_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Bundletool(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block10;
                            done = true;
                            continue block10;
                        }
                        case 18: 
                    }
                    String s3 = input.readStringRequireUtf8();
                    this.version_ = s3;
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
            return internal_static_android_bundle_Bundletool_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Bundletool_fieldAccessorTable.ensureFieldAccessorsInitialized(Bundletool.class, Builder.class);
        }

        @Override
        public String getVersion() {
            Object ref = this.version_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.version_ = s3;
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
            if (!this.getVersionBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.version_);
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
            if (!this.getVersionBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.version_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Bundletool)) {
                return super.equals(obj);
            }
            Bundletool other = (Bundletool)obj;
            boolean result = true;
            result = result && this.getVersion().equals(other.getVersion());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Bundletool.getDescriptor().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getVersion().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Bundletool parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Bundletool parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Bundletool parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Bundletool parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Bundletool parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Bundletool parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Bundletool parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Bundletool parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Bundletool parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Bundletool parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Bundletool parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Bundletool parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Bundletool.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Bundletool prototype) {
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

        public static Bundletool getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Bundletool> parser() {
            return PARSER;
        }

        public Parser<Bundletool> getParserForType() {
            return PARSER;
        }

        @Override
        public Bundletool getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements BundletoolOrBuilder {
            private Object version_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Bundletool_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Bundletool_fieldAccessorTable.ensureFieldAccessorsInitialized(Bundletool.class, Builder.class);
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
                this.version_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Bundletool_descriptor;
            }

            @Override
            public Bundletool getDefaultInstanceForType() {
                return Bundletool.getDefaultInstance();
            }

            @Override
            public Bundletool build() {
                Bundletool result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Bundletool buildPartial() {
                Bundletool result = new Bundletool(this);
                result.version_ = this.version_;
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
                if (other instanceof Bundletool) {
                    return this.mergeFrom((Bundletool)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Bundletool other) {
                if (other == Bundletool.getDefaultInstance()) {
                    return this;
                }
                if (!other.getVersion().isEmpty()) {
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
                Bundletool parsedMessage = null;
                try {
                    parsedMessage = (Bundletool)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Bundletool)e2.getUnfinishedMessage();
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
            public String getVersion() {
                Object ref = this.version_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.version_ = s3;
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
                this.version_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.version_ = Bundletool.getDefaultInstance().getVersion();
                this.onChanged();
                return this;
            }

            public Builder setVersionBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Bundletool.checkByteStringIsUtf8(value);
                this.version_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface BundletoolOrBuilder
    extends MessageOrBuilder {
        public String getVersion();

        public ByteString getVersionBytes();
    }

    public static final class BundleConfig
    extends GeneratedMessageV3
    implements BundleConfigOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int BUNDLETOOL_FIELD_NUMBER = 1;
        private Bundletool bundletool_;
        public static final int OPTIMIZATIONS_FIELD_NUMBER = 2;
        private Optimizations optimizations_;
        public static final int COMPRESSION_FIELD_NUMBER = 3;
        private Compression compression_;
        public static final int MASTER_RESOURCES_FIELD_NUMBER = 4;
        private MasterResources masterResources_;
        public static final int APEX_CONFIG_FIELD_NUMBER = 5;
        private ApexConfig apexConfig_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final BundleConfig DEFAULT_INSTANCE = new BundleConfig();
        private static final Parser<BundleConfig> PARSER = new AbstractParser<BundleConfig>(){

            @Override
            public BundleConfig parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new BundleConfig(input, extensionRegistry);
            }
        };

        private BundleConfig(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private BundleConfig() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private BundleConfig(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block14: while (!done) {
                    GeneratedMessageV3.Builder subBuilder;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block14;
                        }
                        default: {
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block14;
                            done = true;
                            continue block14;
                        }
                        case 10: {
                            subBuilder = null;
                            if (this.bundletool_ != null) {
                                subBuilder = this.bundletool_.toBuilder();
                            }
                            this.bundletool_ = input.readMessage(Bundletool.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((Bundletool.Builder)subBuilder).mergeFrom(this.bundletool_);
                            this.bundletool_ = ((Bundletool.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 18: {
                            subBuilder = null;
                            if (this.optimizations_ != null) {
                                subBuilder = this.optimizations_.toBuilder();
                            }
                            this.optimizations_ = input.readMessage(Optimizations.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((Optimizations.Builder)subBuilder).mergeFrom(this.optimizations_);
                            this.optimizations_ = ((Optimizations.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 26: {
                            subBuilder = null;
                            if (this.compression_ != null) {
                                subBuilder = this.compression_.toBuilder();
                            }
                            this.compression_ = input.readMessage(Compression.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((Compression.Builder)subBuilder).mergeFrom(this.compression_);
                            this.compression_ = ((Compression.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 34: {
                            subBuilder = null;
                            if (this.masterResources_ != null) {
                                subBuilder = this.masterResources_.toBuilder();
                            }
                            this.masterResources_ = input.readMessage(MasterResources.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((MasterResources.Builder)subBuilder).mergeFrom(this.masterResources_);
                            this.masterResources_ = ((MasterResources.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 42: 
                    }
                    subBuilder = null;
                    if (this.apexConfig_ != null) {
                        subBuilder = this.apexConfig_.toBuilder();
                    }
                    this.apexConfig_ = input.readMessage(ApexConfig.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    ((ApexConfig.Builder)subBuilder).mergeFrom(this.apexConfig_);
                    this.apexConfig_ = ((ApexConfig.Builder)subBuilder).buildPartial();
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
            return internal_static_android_bundle_BundleConfig_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_BundleConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(BundleConfig.class, Builder.class);
        }

        @Override
        public boolean hasBundletool() {
            return this.bundletool_ != null;
        }

        @Override
        public Bundletool getBundletool() {
            return this.bundletool_ == null ? Bundletool.getDefaultInstance() : this.bundletool_;
        }

        @Override
        public BundletoolOrBuilder getBundletoolOrBuilder() {
            return this.getBundletool();
        }

        @Override
        public boolean hasOptimizations() {
            return this.optimizations_ != null;
        }

        @Override
        public Optimizations getOptimizations() {
            return this.optimizations_ == null ? Optimizations.getDefaultInstance() : this.optimizations_;
        }

        @Override
        public OptimizationsOrBuilder getOptimizationsOrBuilder() {
            return this.getOptimizations();
        }

        @Override
        public boolean hasCompression() {
            return this.compression_ != null;
        }

        @Override
        public Compression getCompression() {
            return this.compression_ == null ? Compression.getDefaultInstance() : this.compression_;
        }

        @Override
        public CompressionOrBuilder getCompressionOrBuilder() {
            return this.getCompression();
        }

        @Override
        public boolean hasMasterResources() {
            return this.masterResources_ != null;
        }

        @Override
        public MasterResources getMasterResources() {
            return this.masterResources_ == null ? MasterResources.getDefaultInstance() : this.masterResources_;
        }

        @Override
        public MasterResourcesOrBuilder getMasterResourcesOrBuilder() {
            return this.getMasterResources();
        }

        @Override
        public boolean hasApexConfig() {
            return this.apexConfig_ != null;
        }

        @Override
        public ApexConfig getApexConfig() {
            return this.apexConfig_ == null ? ApexConfig.getDefaultInstance() : this.apexConfig_;
        }

        @Override
        public ApexConfigOrBuilder getApexConfigOrBuilder() {
            return this.getApexConfig();
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
            if (this.bundletool_ != null) {
                output.writeMessage(1, this.getBundletool());
            }
            if (this.optimizations_ != null) {
                output.writeMessage(2, this.getOptimizations());
            }
            if (this.compression_ != null) {
                output.writeMessage(3, this.getCompression());
            }
            if (this.masterResources_ != null) {
                output.writeMessage(4, this.getMasterResources());
            }
            if (this.apexConfig_ != null) {
                output.writeMessage(5, this.getApexConfig());
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
            if (this.bundletool_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getBundletool());
            }
            if (this.optimizations_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getOptimizations());
            }
            if (this.compression_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getCompression());
            }
            if (this.masterResources_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getMasterResources());
            }
            if (this.apexConfig_ != null) {
                size += CodedOutputStream.computeMessageSize(5, this.getApexConfig());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof BundleConfig)) {
                return super.equals(obj);
            }
            BundleConfig other = (BundleConfig)obj;
            boolean result = true;
            boolean bl = result = result && this.hasBundletool() == other.hasBundletool();
            if (this.hasBundletool()) {
                result = result && this.getBundletool().equals(other.getBundletool());
            }
            boolean bl2 = result = result && this.hasOptimizations() == other.hasOptimizations();
            if (this.hasOptimizations()) {
                result = result && this.getOptimizations().equals(other.getOptimizations());
            }
            boolean bl3 = result = result && this.hasCompression() == other.hasCompression();
            if (this.hasCompression()) {
                result = result && this.getCompression().equals(other.getCompression());
            }
            boolean bl4 = result = result && this.hasMasterResources() == other.hasMasterResources();
            if (this.hasMasterResources()) {
                result = result && this.getMasterResources().equals(other.getMasterResources());
            }
            boolean bl5 = result = result && this.hasApexConfig() == other.hasApexConfig();
            if (this.hasApexConfig()) {
                result = result && this.getApexConfig().equals(other.getApexConfig());
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
            hash = 19 * hash + BundleConfig.getDescriptor().hashCode();
            if (this.hasBundletool()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getBundletool().hashCode();
            }
            if (this.hasOptimizations()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getOptimizations().hashCode();
            }
            if (this.hasCompression()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getCompression().hashCode();
            }
            if (this.hasMasterResources()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getMasterResources().hashCode();
            }
            if (this.hasApexConfig()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getApexConfig().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static BundleConfig parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BundleConfig parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BundleConfig parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BundleConfig parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BundleConfig parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BundleConfig parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BundleConfig parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BundleConfig parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static BundleConfig parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static BundleConfig parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static BundleConfig parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BundleConfig parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return BundleConfig.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(BundleConfig prototype) {
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

        public static BundleConfig getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BundleConfig> parser() {
            return PARSER;
        }

        public Parser<BundleConfig> getParserForType() {
            return PARSER;
        }

        @Override
        public BundleConfig getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements BundleConfigOrBuilder {
            private Bundletool bundletool_ = null;
            private SingleFieldBuilderV3<Bundletool, Bundletool.Builder, BundletoolOrBuilder> bundletoolBuilder_;
            private Optimizations optimizations_ = null;
            private SingleFieldBuilderV3<Optimizations, Optimizations.Builder, OptimizationsOrBuilder> optimizationsBuilder_;
            private Compression compression_ = null;
            private SingleFieldBuilderV3<Compression, Compression.Builder, CompressionOrBuilder> compressionBuilder_;
            private MasterResources masterResources_ = null;
            private SingleFieldBuilderV3<MasterResources, MasterResources.Builder, MasterResourcesOrBuilder> masterResourcesBuilder_;
            private ApexConfig apexConfig_ = null;
            private SingleFieldBuilderV3<ApexConfig, ApexConfig.Builder, ApexConfigOrBuilder> apexConfigBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_BundleConfig_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_BundleConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(BundleConfig.class, Builder.class);
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
                if (this.bundletoolBuilder_ == null) {
                    this.bundletool_ = null;
                } else {
                    this.bundletool_ = null;
                    this.bundletoolBuilder_ = null;
                }
                if (this.optimizationsBuilder_ == null) {
                    this.optimizations_ = null;
                } else {
                    this.optimizations_ = null;
                    this.optimizationsBuilder_ = null;
                }
                if (this.compressionBuilder_ == null) {
                    this.compression_ = null;
                } else {
                    this.compression_ = null;
                    this.compressionBuilder_ = null;
                }
                if (this.masterResourcesBuilder_ == null) {
                    this.masterResources_ = null;
                } else {
                    this.masterResources_ = null;
                    this.masterResourcesBuilder_ = null;
                }
                if (this.apexConfigBuilder_ == null) {
                    this.apexConfig_ = null;
                } else {
                    this.apexConfig_ = null;
                    this.apexConfigBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_BundleConfig_descriptor;
            }

            @Override
            public BundleConfig getDefaultInstanceForType() {
                return BundleConfig.getDefaultInstance();
            }

            @Override
            public BundleConfig build() {
                BundleConfig result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public BundleConfig buildPartial() {
                BundleConfig result = new BundleConfig(this);
                if (this.bundletoolBuilder_ == null) {
                    result.bundletool_ = this.bundletool_;
                } else {
                    result.bundletool_ = this.bundletoolBuilder_.build();
                }
                if (this.optimizationsBuilder_ == null) {
                    result.optimizations_ = this.optimizations_;
                } else {
                    result.optimizations_ = this.optimizationsBuilder_.build();
                }
                if (this.compressionBuilder_ == null) {
                    result.compression_ = this.compression_;
                } else {
                    result.compression_ = this.compressionBuilder_.build();
                }
                if (this.masterResourcesBuilder_ == null) {
                    result.masterResources_ = this.masterResources_;
                } else {
                    result.masterResources_ = this.masterResourcesBuilder_.build();
                }
                if (this.apexConfigBuilder_ == null) {
                    result.apexConfig_ = this.apexConfig_;
                } else {
                    result.apexConfig_ = this.apexConfigBuilder_.build();
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
                if (other instanceof BundleConfig) {
                    return this.mergeFrom((BundleConfig)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(BundleConfig other) {
                if (other == BundleConfig.getDefaultInstance()) {
                    return this;
                }
                if (other.hasBundletool()) {
                    this.mergeBundletool(other.getBundletool());
                }
                if (other.hasOptimizations()) {
                    this.mergeOptimizations(other.getOptimizations());
                }
                if (other.hasCompression()) {
                    this.mergeCompression(other.getCompression());
                }
                if (other.hasMasterResources()) {
                    this.mergeMasterResources(other.getMasterResources());
                }
                if (other.hasApexConfig()) {
                    this.mergeApexConfig(other.getApexConfig());
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
                BundleConfig parsedMessage = null;
                try {
                    parsedMessage = (BundleConfig)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (BundleConfig)e2.getUnfinishedMessage();
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
            public boolean hasBundletool() {
                return this.bundletoolBuilder_ != null || this.bundletool_ != null;
            }

            @Override
            public Bundletool getBundletool() {
                if (this.bundletoolBuilder_ == null) {
                    return this.bundletool_ == null ? Bundletool.getDefaultInstance() : this.bundletool_;
                }
                return this.bundletoolBuilder_.getMessage();
            }

            public Builder setBundletool(Bundletool value) {
                if (this.bundletoolBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bundletool_ = value;
                    this.onChanged();
                } else {
                    this.bundletoolBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setBundletool(Bundletool.Builder builderForValue) {
                if (this.bundletoolBuilder_ == null) {
                    this.bundletool_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.bundletoolBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeBundletool(Bundletool value) {
                if (this.bundletoolBuilder_ == null) {
                    this.bundletool_ = this.bundletool_ != null ? Bundletool.newBuilder(this.bundletool_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.bundletoolBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearBundletool() {
                if (this.bundletoolBuilder_ == null) {
                    this.bundletool_ = null;
                    this.onChanged();
                } else {
                    this.bundletool_ = null;
                    this.bundletoolBuilder_ = null;
                }
                return this;
            }

            public Bundletool.Builder getBundletoolBuilder() {
                this.onChanged();
                return this.getBundletoolFieldBuilder().getBuilder();
            }

            @Override
            public BundletoolOrBuilder getBundletoolOrBuilder() {
                if (this.bundletoolBuilder_ != null) {
                    return this.bundletoolBuilder_.getMessageOrBuilder();
                }
                return this.bundletool_ == null ? Bundletool.getDefaultInstance() : this.bundletool_;
            }

            private SingleFieldBuilderV3<Bundletool, Bundletool.Builder, BundletoolOrBuilder> getBundletoolFieldBuilder() {
                if (this.bundletoolBuilder_ == null) {
                    this.bundletoolBuilder_ = new SingleFieldBuilderV3(this.getBundletool(), this.getParentForChildren(), this.isClean());
                    this.bundletool_ = null;
                }
                return this.bundletoolBuilder_;
            }

            @Override
            public boolean hasOptimizations() {
                return this.optimizationsBuilder_ != null || this.optimizations_ != null;
            }

            @Override
            public Optimizations getOptimizations() {
                if (this.optimizationsBuilder_ == null) {
                    return this.optimizations_ == null ? Optimizations.getDefaultInstance() : this.optimizations_;
                }
                return this.optimizationsBuilder_.getMessage();
            }

            public Builder setOptimizations(Optimizations value) {
                if (this.optimizationsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.optimizations_ = value;
                    this.onChanged();
                } else {
                    this.optimizationsBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setOptimizations(Optimizations.Builder builderForValue) {
                if (this.optimizationsBuilder_ == null) {
                    this.optimizations_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.optimizationsBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeOptimizations(Optimizations value) {
                if (this.optimizationsBuilder_ == null) {
                    this.optimizations_ = this.optimizations_ != null ? Optimizations.newBuilder(this.optimizations_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.optimizationsBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearOptimizations() {
                if (this.optimizationsBuilder_ == null) {
                    this.optimizations_ = null;
                    this.onChanged();
                } else {
                    this.optimizations_ = null;
                    this.optimizationsBuilder_ = null;
                }
                return this;
            }

            public Optimizations.Builder getOptimizationsBuilder() {
                this.onChanged();
                return this.getOptimizationsFieldBuilder().getBuilder();
            }

            @Override
            public OptimizationsOrBuilder getOptimizationsOrBuilder() {
                if (this.optimizationsBuilder_ != null) {
                    return this.optimizationsBuilder_.getMessageOrBuilder();
                }
                return this.optimizations_ == null ? Optimizations.getDefaultInstance() : this.optimizations_;
            }

            private SingleFieldBuilderV3<Optimizations, Optimizations.Builder, OptimizationsOrBuilder> getOptimizationsFieldBuilder() {
                if (this.optimizationsBuilder_ == null) {
                    this.optimizationsBuilder_ = new SingleFieldBuilderV3(this.getOptimizations(), this.getParentForChildren(), this.isClean());
                    this.optimizations_ = null;
                }
                return this.optimizationsBuilder_;
            }

            @Override
            public boolean hasCompression() {
                return this.compressionBuilder_ != null || this.compression_ != null;
            }

            @Override
            public Compression getCompression() {
                if (this.compressionBuilder_ == null) {
                    return this.compression_ == null ? Compression.getDefaultInstance() : this.compression_;
                }
                return this.compressionBuilder_.getMessage();
            }

            public Builder setCompression(Compression value) {
                if (this.compressionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.compression_ = value;
                    this.onChanged();
                } else {
                    this.compressionBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setCompression(Compression.Builder builderForValue) {
                if (this.compressionBuilder_ == null) {
                    this.compression_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.compressionBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeCompression(Compression value) {
                if (this.compressionBuilder_ == null) {
                    this.compression_ = this.compression_ != null ? Compression.newBuilder(this.compression_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.compressionBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearCompression() {
                if (this.compressionBuilder_ == null) {
                    this.compression_ = null;
                    this.onChanged();
                } else {
                    this.compression_ = null;
                    this.compressionBuilder_ = null;
                }
                return this;
            }

            public Compression.Builder getCompressionBuilder() {
                this.onChanged();
                return this.getCompressionFieldBuilder().getBuilder();
            }

            @Override
            public CompressionOrBuilder getCompressionOrBuilder() {
                if (this.compressionBuilder_ != null) {
                    return this.compressionBuilder_.getMessageOrBuilder();
                }
                return this.compression_ == null ? Compression.getDefaultInstance() : this.compression_;
            }

            private SingleFieldBuilderV3<Compression, Compression.Builder, CompressionOrBuilder> getCompressionFieldBuilder() {
                if (this.compressionBuilder_ == null) {
                    this.compressionBuilder_ = new SingleFieldBuilderV3(this.getCompression(), this.getParentForChildren(), this.isClean());
                    this.compression_ = null;
                }
                return this.compressionBuilder_;
            }

            @Override
            public boolean hasMasterResources() {
                return this.masterResourcesBuilder_ != null || this.masterResources_ != null;
            }

            @Override
            public MasterResources getMasterResources() {
                if (this.masterResourcesBuilder_ == null) {
                    return this.masterResources_ == null ? MasterResources.getDefaultInstance() : this.masterResources_;
                }
                return this.masterResourcesBuilder_.getMessage();
            }

            public Builder setMasterResources(MasterResources value) {
                if (this.masterResourcesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.masterResources_ = value;
                    this.onChanged();
                } else {
                    this.masterResourcesBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setMasterResources(MasterResources.Builder builderForValue) {
                if (this.masterResourcesBuilder_ == null) {
                    this.masterResources_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.masterResourcesBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeMasterResources(MasterResources value) {
                if (this.masterResourcesBuilder_ == null) {
                    this.masterResources_ = this.masterResources_ != null ? MasterResources.newBuilder(this.masterResources_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.masterResourcesBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearMasterResources() {
                if (this.masterResourcesBuilder_ == null) {
                    this.masterResources_ = null;
                    this.onChanged();
                } else {
                    this.masterResources_ = null;
                    this.masterResourcesBuilder_ = null;
                }
                return this;
            }

            public MasterResources.Builder getMasterResourcesBuilder() {
                this.onChanged();
                return this.getMasterResourcesFieldBuilder().getBuilder();
            }

            @Override
            public MasterResourcesOrBuilder getMasterResourcesOrBuilder() {
                if (this.masterResourcesBuilder_ != null) {
                    return this.masterResourcesBuilder_.getMessageOrBuilder();
                }
                return this.masterResources_ == null ? MasterResources.getDefaultInstance() : this.masterResources_;
            }

            private SingleFieldBuilderV3<MasterResources, MasterResources.Builder, MasterResourcesOrBuilder> getMasterResourcesFieldBuilder() {
                if (this.masterResourcesBuilder_ == null) {
                    this.masterResourcesBuilder_ = new SingleFieldBuilderV3(this.getMasterResources(), this.getParentForChildren(), this.isClean());
                    this.masterResources_ = null;
                }
                return this.masterResourcesBuilder_;
            }

            @Override
            public boolean hasApexConfig() {
                return this.apexConfigBuilder_ != null || this.apexConfig_ != null;
            }

            @Override
            public ApexConfig getApexConfig() {
                if (this.apexConfigBuilder_ == null) {
                    return this.apexConfig_ == null ? ApexConfig.getDefaultInstance() : this.apexConfig_;
                }
                return this.apexConfigBuilder_.getMessage();
            }

            public Builder setApexConfig(ApexConfig value) {
                if (this.apexConfigBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.apexConfig_ = value;
                    this.onChanged();
                } else {
                    this.apexConfigBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setApexConfig(ApexConfig.Builder builderForValue) {
                if (this.apexConfigBuilder_ == null) {
                    this.apexConfig_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.apexConfigBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeApexConfig(ApexConfig value) {
                if (this.apexConfigBuilder_ == null) {
                    this.apexConfig_ = this.apexConfig_ != null ? ApexConfig.newBuilder(this.apexConfig_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.apexConfigBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearApexConfig() {
                if (this.apexConfigBuilder_ == null) {
                    this.apexConfig_ = null;
                    this.onChanged();
                } else {
                    this.apexConfig_ = null;
                    this.apexConfigBuilder_ = null;
                }
                return this;
            }

            public ApexConfig.Builder getApexConfigBuilder() {
                this.onChanged();
                return this.getApexConfigFieldBuilder().getBuilder();
            }

            @Override
            public ApexConfigOrBuilder getApexConfigOrBuilder() {
                if (this.apexConfigBuilder_ != null) {
                    return this.apexConfigBuilder_.getMessageOrBuilder();
                }
                return this.apexConfig_ == null ? ApexConfig.getDefaultInstance() : this.apexConfig_;
            }

            private SingleFieldBuilderV3<ApexConfig, ApexConfig.Builder, ApexConfigOrBuilder> getApexConfigFieldBuilder() {
                if (this.apexConfigBuilder_ == null) {
                    this.apexConfigBuilder_ = new SingleFieldBuilderV3(this.getApexConfig(), this.getParentForChildren(), this.isClean());
                    this.apexConfig_ = null;
                }
                return this.apexConfigBuilder_;
            }

            @Override
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface BundleConfigOrBuilder
    extends MessageOrBuilder {
        public boolean hasBundletool();

        public Bundletool getBundletool();

        public BundletoolOrBuilder getBundletoolOrBuilder();

        public boolean hasOptimizations();

        public Optimizations getOptimizations();

        public OptimizationsOrBuilder getOptimizationsOrBuilder();

        public boolean hasCompression();

        public Compression getCompression();

        public CompressionOrBuilder getCompressionOrBuilder();

        public boolean hasMasterResources();

        public MasterResources getMasterResources();

        public MasterResourcesOrBuilder getMasterResourcesOrBuilder();

        public boolean hasApexConfig();

        public ApexConfig getApexConfig();

        public ApexConfigOrBuilder getApexConfigOrBuilder();
    }
}

