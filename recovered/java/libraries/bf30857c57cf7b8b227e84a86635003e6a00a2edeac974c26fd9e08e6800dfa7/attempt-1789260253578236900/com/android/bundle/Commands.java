/*
 * Decompiled with CFR 0.152.
 */
package com.android.bundle;

import com.android.bundle.Config;
import com.android.bundle.Targeting;
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

public final class Commands {
    private static final Descriptors.Descriptor internal_static_android_bundle_BuildApksResult_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_BuildApksResult_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_Variant_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Variant_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ApkSet_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ApkSet_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ModuleMetadata_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ModuleMetadata_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_AssetSliceSet_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_AssetSliceSet_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_AssetModuleMetadata_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_AssetModuleMetadata_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_InstantMetadata_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_InstantMetadata_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ApkDescription_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ApkDescription_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_SplitApkMetadata_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_SplitApkMetadata_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_StandaloneApkMetadata_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_StandaloneApkMetadata_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_SystemApkMetadata_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_SystemApkMetadata_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ApexApkMetadata_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ApexApkMetadata_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_LocalTestingInfo_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_LocalTestingInfo_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private Commands() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        Commands.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u000ecommands.proto\u0012\u000eandroid.bundle\u001a\fconfig.proto\u001a\u000ftargeting.proto\"\u00f7\u0001\n\u000fBuildApksResult\u0012\u0014\n\fpackage_name\u0018\u0004 \u0001(\t\u0012(\n\u0007variant\u0018\u0001 \u0003(\u000b2\u0017.android.bundle.Variant\u0012.\n\nbundletool\u0018\u0002 \u0001(\u000b2\u001a.android.bundle.Bundletool\u00126\n\u000fasset_slice_set\u0018\u0003 \u0003(\u000b2\u001d.android.bundle.AssetSliceSet\u0012<\n\u0012local_testing_info\u0018\u0005 \u0001(\u000b2 .android.bundle.LocalTestingInfo\"\u007f\n\u0007Variant\u00123\n\ttargeting\u0018\u0001 \u0001(\u000b2 .android.bundle.VariantTargeting\u0012'\n\u0007apk_set\u0018\u0002 \u0003(\u000b2\u0016.and", "roid.bundle.ApkSet\u0012\u0016\n\u000evariant_number\u0018\u0003 \u0001(\r\"z\n\u0006ApkSet\u00127\n\u000fmodule_metadata\u0018\u0001 \u0001(\u000b2\u001e.android.bundle.ModuleMetadata\u00127\n\u000fapk_description\u0018\u0002 \u0003(\u000b2\u001e.android.bundle.ApkDescription\"\u00d3\u0001\n\u000eModuleMetadata\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u00123\n\rdelivery_type\u0018\u0006 \u0001(\u000e2\u001c.android.bundle.DeliveryType\u0012\u0012\n\nis_instant\u0018\u0003 \u0001(\b\u0012\u0014\n\fdependencies\u0018\u0004 \u0003(\t\u00122\n\ttargeting\u0018\u0005 \u0001(\u000b2\u001f.android.bundle.ModuleTargeting\u0012 \n\u0014on_demand_deprecated\u0018\u0002 \u0001(\bB\u0002\u0018\u0001\"\u008c\u0001\n\rAssetSliceSet\u0012B", "\n\u0015asset_module_metadata\u0018\u0001 \u0001(\u000b2#.android.bundle.AssetModuleMetadata\u00127\n\u000fapk_description\u0018\u0002 \u0003(\u000b2\u001e.android.bundle.ApkDescription\"\u00b5\u0001\n\u0013AssetModuleMetadata\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u00123\n\rdelivery_type\u0018\u0004 \u0001(\u000e2\u001c.android.bundle.DeliveryType\u00129\n\u0010instant_metadata\u0018\u0003 \u0001(\u000b2\u001f.android.bundle.InstantMetadata\u0012 \n\u0014on_demand_deprecated\u0018\u0002 \u0001(\bB\u0002\u0018\u0001\"|\n\u000fInstantMetadata\u0012\u0012\n\nis_instant\u0018\u0001 \u0001(\b\u00123\n\rdelivery_type\u0018\u0003 \u0001(\u000e2\u001c.android.bundle.DeliveryType\u0012", " \n\u0014on_demand_deprecated\u0018\u0002 \u0001(\bB\u0002\u0018\u0001\"\u00f9\u0003\n\u000eApkDescription\u0012/\n\ttargeting\u0018\u0001 \u0001(\u000b2\u001c.android.bundle.ApkTargeting\u0012\f\n\u0004path\u0018\u0002 \u0001(\t\u0012>\n\u0012split_apk_metadata\u0018\u0003 \u0001(\u000b2 .android.bundle.SplitApkMetadataH\u0000\u0012H\n\u0017standalone_apk_metadata\u0018\u0004 \u0001(\u000b2%.android.bundle.StandaloneApkMetadataH\u0000\u0012@\n\u0014instant_apk_metadata\u0018\u0005 \u0001(\u000b2 .android.bundle.SplitApkMetadataH\u0000\u0012@\n\u0013system_apk_metadata\u0018\u0006 \u0001(\u000b2!.android.bundle.SystemApkMetadataH\u0000\u0012@\n\u0014asset_slice", "_metadata\u0018\u0007 \u0001(\u000b2 .android.bundle.SplitApkMetadataH\u0000\u0012<\n\u0011apex_apk_metadata\u0018\b \u0001(\u000b2\u001f.android.bundle.ApexApkMetadataH\u0000B\u001a\n\u0018apk_metadata_oneof_value\"=\n\u0010SplitApkMetadata\u0012\u0010\n\bsplit_id\u0018\u0001 \u0001(\t\u0012\u0017\n\u000fis_master_split\u0018\u0002 \u0001(\b\"8\n\u0015StandaloneApkMetadata\u0012\u0019\n\u0011fused_module_name\u0018\u0001 \u0003(\tJ\u0004\b\u0002\u0010\u0003\"\u00d4\u0001\n\u0011SystemApkMetadata\u0012\u0019\n\u0011fused_module_name\u0018\u0001 \u0003(\t\u0012H\n\u000fsystem_apk_type\u0018\u0002 \u0001(\u000e2/.android.bundle.SystemApkMetadata.SystemApkType\"Z\n\rSystemApkTy", "pe\u0012\u0015\n\u0011UNSPECIFIED_VALUE\u0010\u0000\u0012\n\n\u0006SYSTEM\u0010\u0001\u0012\u000f\n\u000bSYSTEM_STUB\u0010\u0002\u0012\u0015\n\u0011SYSTEM_COMPRESSED\u0010\u0003\"Z\n\u000fApexApkMetadata\u0012G\n\u0018apex_embedded_apk_config\u0018\u0001 \u0003(\u000b2%.android.bundle.ApexEmbeddedApkConfig\"?\n\u0010LocalTestingInfo\u0012\u000f\n\u0007enabled\u0018\u0001 \u0001(\b\u0012\u001a\n\u0012local_testing_path\u0018\u0002 \u0001(\t*[\n\fDeliveryType\u0012\u0019\n\u0015UNKNOWN_DELIVERY_TYPE\u0010\u0000\u0012\u0010\n\fINSTALL_TIME\u0010\u0001\u0012\r\n\tON_DEMAND\u0010\u0002\u0012\u000f\n\u000bFAST_FOLLOW\u0010\u0003B\u0014\n\u0012com.android.bundleb\u0006proto3"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            @Override
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{Config.getDescriptor(), Targeting.getDescriptor()}, assigner);
        internal_static_android_bundle_BuildApksResult_descriptor = Commands.getDescriptor().getMessageTypes().get(0);
        internal_static_android_bundle_BuildApksResult_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_BuildApksResult_descriptor, new String[]{"PackageName", "Variant", "Bundletool", "AssetSliceSet", "LocalTestingInfo"});
        internal_static_android_bundle_Variant_descriptor = Commands.getDescriptor().getMessageTypes().get(1);
        internal_static_android_bundle_Variant_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Variant_descriptor, new String[]{"Targeting", "ApkSet", "VariantNumber"});
        internal_static_android_bundle_ApkSet_descriptor = Commands.getDescriptor().getMessageTypes().get(2);
        internal_static_android_bundle_ApkSet_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ApkSet_descriptor, new String[]{"ModuleMetadata", "ApkDescription"});
        internal_static_android_bundle_ModuleMetadata_descriptor = Commands.getDescriptor().getMessageTypes().get(3);
        internal_static_android_bundle_ModuleMetadata_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ModuleMetadata_descriptor, new String[]{"Name", "DeliveryType", "IsInstant", "Dependencies", "Targeting", "OnDemandDeprecated"});
        internal_static_android_bundle_AssetSliceSet_descriptor = Commands.getDescriptor().getMessageTypes().get(4);
        internal_static_android_bundle_AssetSliceSet_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_AssetSliceSet_descriptor, new String[]{"AssetModuleMetadata", "ApkDescription"});
        internal_static_android_bundle_AssetModuleMetadata_descriptor = Commands.getDescriptor().getMessageTypes().get(5);
        internal_static_android_bundle_AssetModuleMetadata_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_AssetModuleMetadata_descriptor, new String[]{"Name", "DeliveryType", "InstantMetadata", "OnDemandDeprecated"});
        internal_static_android_bundle_InstantMetadata_descriptor = Commands.getDescriptor().getMessageTypes().get(6);
        internal_static_android_bundle_InstantMetadata_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_InstantMetadata_descriptor, new String[]{"IsInstant", "DeliveryType", "OnDemandDeprecated"});
        internal_static_android_bundle_ApkDescription_descriptor = Commands.getDescriptor().getMessageTypes().get(7);
        internal_static_android_bundle_ApkDescription_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ApkDescription_descriptor, new String[]{"Targeting", "Path", "SplitApkMetadata", "StandaloneApkMetadata", "InstantApkMetadata", "SystemApkMetadata", "AssetSliceMetadata", "ApexApkMetadata", "ApkMetadataOneofValue"});
        internal_static_android_bundle_SplitApkMetadata_descriptor = Commands.getDescriptor().getMessageTypes().get(8);
        internal_static_android_bundle_SplitApkMetadata_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_SplitApkMetadata_descriptor, new String[]{"SplitId", "IsMasterSplit"});
        internal_static_android_bundle_StandaloneApkMetadata_descriptor = Commands.getDescriptor().getMessageTypes().get(9);
        internal_static_android_bundle_StandaloneApkMetadata_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_StandaloneApkMetadata_descriptor, new String[]{"FusedModuleName"});
        internal_static_android_bundle_SystemApkMetadata_descriptor = Commands.getDescriptor().getMessageTypes().get(10);
        internal_static_android_bundle_SystemApkMetadata_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_SystemApkMetadata_descriptor, new String[]{"FusedModuleName", "SystemApkType"});
        internal_static_android_bundle_ApexApkMetadata_descriptor = Commands.getDescriptor().getMessageTypes().get(11);
        internal_static_android_bundle_ApexApkMetadata_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ApexApkMetadata_descriptor, new String[]{"ApexEmbeddedApkConfig"});
        internal_static_android_bundle_LocalTestingInfo_descriptor = Commands.getDescriptor().getMessageTypes().get(12);
        internal_static_android_bundle_LocalTestingInfo_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_LocalTestingInfo_descriptor, new String[]{"Enabled", "LocalTestingPath"});
        Config.getDescriptor();
        Targeting.getDescriptor();
    }

    public static final class LocalTestingInfo
    extends GeneratedMessageV3
    implements LocalTestingInfoOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ENABLED_FIELD_NUMBER = 1;
        private boolean enabled_;
        public static final int LOCAL_TESTING_PATH_FIELD_NUMBER = 2;
        private volatile Object localTestingPath_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final LocalTestingInfo DEFAULT_INSTANCE = new LocalTestingInfo();
        private static final Parser<LocalTestingInfo> PARSER = new AbstractParser<LocalTestingInfo>(){

            @Override
            public LocalTestingInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new LocalTestingInfo(input, extensionRegistry);
            }
        };

        private LocalTestingInfo(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private LocalTestingInfo() {
            this.enabled_ = false;
            this.localTestingPath_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private LocalTestingInfo(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.localTestingPath_ = s3;
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
            return internal_static_android_bundle_LocalTestingInfo_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_LocalTestingInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(LocalTestingInfo.class, Builder.class);
        }

        @Override
        public boolean getEnabled() {
            return this.enabled_;
        }

        @Override
        public String getLocalTestingPath() {
            Object ref = this.localTestingPath_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.localTestingPath_ = s3;
            return s3;
        }

        @Override
        public ByteString getLocalTestingPathBytes() {
            Object ref = this.localTestingPath_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.localTestingPath_ = b2;
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
            if (!this.getLocalTestingPathBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.localTestingPath_);
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
            if (!this.getLocalTestingPathBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.localTestingPath_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LocalTestingInfo)) {
                return super.equals(obj);
            }
            LocalTestingInfo other = (LocalTestingInfo)obj;
            boolean result = true;
            result = result && this.getEnabled() == other.getEnabled();
            result = result && this.getLocalTestingPath().equals(other.getLocalTestingPath());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + LocalTestingInfo.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getEnabled());
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getLocalTestingPath().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static LocalTestingInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LocalTestingInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LocalTestingInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LocalTestingInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LocalTestingInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LocalTestingInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LocalTestingInfo parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static LocalTestingInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static LocalTestingInfo parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static LocalTestingInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static LocalTestingInfo parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static LocalTestingInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return LocalTestingInfo.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(LocalTestingInfo prototype) {
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

        public static LocalTestingInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LocalTestingInfo> parser() {
            return PARSER;
        }

        public Parser<LocalTestingInfo> getParserForType() {
            return PARSER;
        }

        @Override
        public LocalTestingInfo getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements LocalTestingInfoOrBuilder {
            private boolean enabled_;
            private Object localTestingPath_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_LocalTestingInfo_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_LocalTestingInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(LocalTestingInfo.class, Builder.class);
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
                this.localTestingPath_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_LocalTestingInfo_descriptor;
            }

            @Override
            public LocalTestingInfo getDefaultInstanceForType() {
                return LocalTestingInfo.getDefaultInstance();
            }

            @Override
            public LocalTestingInfo build() {
                LocalTestingInfo result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public LocalTestingInfo buildPartial() {
                LocalTestingInfo result = new LocalTestingInfo(this);
                result.enabled_ = this.enabled_;
                result.localTestingPath_ = this.localTestingPath_;
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
                if (other instanceof LocalTestingInfo) {
                    return this.mergeFrom((LocalTestingInfo)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(LocalTestingInfo other) {
                if (other == LocalTestingInfo.getDefaultInstance()) {
                    return this;
                }
                if (other.getEnabled()) {
                    this.setEnabled(other.getEnabled());
                }
                if (!other.getLocalTestingPath().isEmpty()) {
                    this.localTestingPath_ = other.localTestingPath_;
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
                LocalTestingInfo parsedMessage = null;
                try {
                    parsedMessage = (LocalTestingInfo)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (LocalTestingInfo)e2.getUnfinishedMessage();
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
            public String getLocalTestingPath() {
                Object ref = this.localTestingPath_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.localTestingPath_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getLocalTestingPathBytes() {
                Object ref = this.localTestingPath_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.localTestingPath_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setLocalTestingPath(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.localTestingPath_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearLocalTestingPath() {
                this.localTestingPath_ = LocalTestingInfo.getDefaultInstance().getLocalTestingPath();
                this.onChanged();
                return this;
            }

            public Builder setLocalTestingPathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                LocalTestingInfo.checkByteStringIsUtf8(value);
                this.localTestingPath_ = value;
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

    public static interface LocalTestingInfoOrBuilder
    extends MessageOrBuilder {
        public boolean getEnabled();

        public String getLocalTestingPath();

        public ByteString getLocalTestingPathBytes();
    }

    public static final class ApexApkMetadata
    extends GeneratedMessageV3
    implements ApexApkMetadataOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int APEX_EMBEDDED_APK_CONFIG_FIELD_NUMBER = 1;
        private List<Config.ApexEmbeddedApkConfig> apexEmbeddedApkConfig_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ApexApkMetadata DEFAULT_INSTANCE = new ApexApkMetadata();
        private static final Parser<ApexApkMetadata> PARSER = new AbstractParser<ApexApkMetadata>(){

            @Override
            public ApexApkMetadata parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ApexApkMetadata(input, extensionRegistry);
            }
        };

        private ApexApkMetadata(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ApexApkMetadata() {
            this.apexEmbeddedApkConfig_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ApexApkMetadata(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.apexEmbeddedApkConfig_ = new ArrayList<Config.ApexEmbeddedApkConfig>();
                        mutable_bitField0_ |= true;
                    }
                    this.apexEmbeddedApkConfig_.add(input.readMessage(Config.ApexEmbeddedApkConfig.parser(), extensionRegistry));
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
            return internal_static_android_bundle_ApexApkMetadata_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ApexApkMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(ApexApkMetadata.class, Builder.class);
        }

        @Override
        public List<Config.ApexEmbeddedApkConfig> getApexEmbeddedApkConfigList() {
            return this.apexEmbeddedApkConfig_;
        }

        @Override
        public List<? extends Config.ApexEmbeddedApkConfigOrBuilder> getApexEmbeddedApkConfigOrBuilderList() {
            return this.apexEmbeddedApkConfig_;
        }

        @Override
        public int getApexEmbeddedApkConfigCount() {
            return this.apexEmbeddedApkConfig_.size();
        }

        @Override
        public Config.ApexEmbeddedApkConfig getApexEmbeddedApkConfig(int index) {
            return this.apexEmbeddedApkConfig_.get(index);
        }

        @Override
        public Config.ApexEmbeddedApkConfigOrBuilder getApexEmbeddedApkConfigOrBuilder(int index) {
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
            if (!(obj instanceof ApexApkMetadata)) {
                return super.equals(obj);
            }
            ApexApkMetadata other = (ApexApkMetadata)obj;
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
            hash = 19 * hash + ApexApkMetadata.getDescriptor().hashCode();
            if (this.getApexEmbeddedApkConfigCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getApexEmbeddedApkConfigList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ApexApkMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexApkMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexApkMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexApkMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexApkMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexApkMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexApkMetadata parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApexApkMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApexApkMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ApexApkMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApexApkMetadata parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApexApkMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ApexApkMetadata.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ApexApkMetadata prototype) {
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

        public static ApexApkMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ApexApkMetadata> parser() {
            return PARSER;
        }

        public Parser<ApexApkMetadata> getParserForType() {
            return PARSER;
        }

        @Override
        public ApexApkMetadata getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ApexApkMetadataOrBuilder {
            private int bitField0_;
            private List<Config.ApexEmbeddedApkConfig> apexEmbeddedApkConfig_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Config.ApexEmbeddedApkConfig, Config.ApexEmbeddedApkConfig.Builder, Config.ApexEmbeddedApkConfigOrBuilder> apexEmbeddedApkConfigBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ApexApkMetadata_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ApexApkMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(ApexApkMetadata.class, Builder.class);
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
                return internal_static_android_bundle_ApexApkMetadata_descriptor;
            }

            @Override
            public ApexApkMetadata getDefaultInstanceForType() {
                return ApexApkMetadata.getDefaultInstance();
            }

            @Override
            public ApexApkMetadata build() {
                ApexApkMetadata result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ApexApkMetadata buildPartial() {
                ApexApkMetadata result = new ApexApkMetadata(this);
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
                if (other instanceof ApexApkMetadata) {
                    return this.mergeFrom((ApexApkMetadata)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ApexApkMetadata other) {
                if (other == ApexApkMetadata.getDefaultInstance()) {
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
                ApexApkMetadata parsedMessage = null;
                try {
                    parsedMessage = (ApexApkMetadata)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ApexApkMetadata)e2.getUnfinishedMessage();
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
                    this.apexEmbeddedApkConfig_ = new ArrayList<Config.ApexEmbeddedApkConfig>(this.apexEmbeddedApkConfig_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<Config.ApexEmbeddedApkConfig> getApexEmbeddedApkConfigList() {
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
            public Config.ApexEmbeddedApkConfig getApexEmbeddedApkConfig(int index) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    return this.apexEmbeddedApkConfig_.get(index);
                }
                return this.apexEmbeddedApkConfigBuilder_.getMessage(index);
            }

            public Builder setApexEmbeddedApkConfig(int index, Config.ApexEmbeddedApkConfig value) {
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

            public Builder setApexEmbeddedApkConfig(int index, Config.ApexEmbeddedApkConfig.Builder builderForValue) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    this.apexEmbeddedApkConfig_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addApexEmbeddedApkConfig(Config.ApexEmbeddedApkConfig value) {
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

            public Builder addApexEmbeddedApkConfig(int index, Config.ApexEmbeddedApkConfig value) {
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

            public Builder addApexEmbeddedApkConfig(Config.ApexEmbeddedApkConfig.Builder builderForValue) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    this.apexEmbeddedApkConfig_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addApexEmbeddedApkConfig(int index, Config.ApexEmbeddedApkConfig.Builder builderForValue) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.ensureApexEmbeddedApkConfigIsMutable();
                    this.apexEmbeddedApkConfig_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.apexEmbeddedApkConfigBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllApexEmbeddedApkConfig(Iterable<? extends Config.ApexEmbeddedApkConfig> values2) {
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

            public Config.ApexEmbeddedApkConfig.Builder getApexEmbeddedApkConfigBuilder(int index) {
                return this.getApexEmbeddedApkConfigFieldBuilder().getBuilder(index);
            }

            @Override
            public Config.ApexEmbeddedApkConfigOrBuilder getApexEmbeddedApkConfigOrBuilder(int index) {
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    return this.apexEmbeddedApkConfig_.get(index);
                }
                return this.apexEmbeddedApkConfigBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends Config.ApexEmbeddedApkConfigOrBuilder> getApexEmbeddedApkConfigOrBuilderList() {
                if (this.apexEmbeddedApkConfigBuilder_ != null) {
                    return this.apexEmbeddedApkConfigBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.apexEmbeddedApkConfig_);
            }

            public Config.ApexEmbeddedApkConfig.Builder addApexEmbeddedApkConfigBuilder() {
                return this.getApexEmbeddedApkConfigFieldBuilder().addBuilder(Config.ApexEmbeddedApkConfig.getDefaultInstance());
            }

            public Config.ApexEmbeddedApkConfig.Builder addApexEmbeddedApkConfigBuilder(int index) {
                return this.getApexEmbeddedApkConfigFieldBuilder().addBuilder(index, Config.ApexEmbeddedApkConfig.getDefaultInstance());
            }

            public List<Config.ApexEmbeddedApkConfig.Builder> getApexEmbeddedApkConfigBuilderList() {
                return this.getApexEmbeddedApkConfigFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Config.ApexEmbeddedApkConfig, Config.ApexEmbeddedApkConfig.Builder, Config.ApexEmbeddedApkConfigOrBuilder> getApexEmbeddedApkConfigFieldBuilder() {
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

    public static interface ApexApkMetadataOrBuilder
    extends MessageOrBuilder {
        public List<Config.ApexEmbeddedApkConfig> getApexEmbeddedApkConfigList();

        public Config.ApexEmbeddedApkConfig getApexEmbeddedApkConfig(int var1);

        public int getApexEmbeddedApkConfigCount();

        public List<? extends Config.ApexEmbeddedApkConfigOrBuilder> getApexEmbeddedApkConfigOrBuilderList();

        public Config.ApexEmbeddedApkConfigOrBuilder getApexEmbeddedApkConfigOrBuilder(int var1);
    }

    public static final class SystemApkMetadata
    extends GeneratedMessageV3
    implements SystemApkMetadataOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int FUSED_MODULE_NAME_FIELD_NUMBER = 1;
        private LazyStringList fusedModuleName_;
        public static final int SYSTEM_APK_TYPE_FIELD_NUMBER = 2;
        private int systemApkType_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SystemApkMetadata DEFAULT_INSTANCE = new SystemApkMetadata();
        private static final Parser<SystemApkMetadata> PARSER = new AbstractParser<SystemApkMetadata>(){

            @Override
            public SystemApkMetadata parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SystemApkMetadata(input, extensionRegistry);
            }
        };

        private SystemApkMetadata(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SystemApkMetadata() {
            this.fusedModuleName_ = LazyStringArrayList.EMPTY;
            this.systemApkType_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SystemApkMetadata(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block11: while (!done) {
                    int rawValue;
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
                            String s3 = input.readStringRequireUtf8();
                            if (!(mutable_bitField0_ & true)) {
                                this.fusedModuleName_ = new LazyStringArrayList();
                                mutable_bitField0_ |= true;
                            }
                            this.fusedModuleName_.add(s3);
                            continue block11;
                        }
                        case 16: 
                    }
                    this.systemApkType_ = rawValue = input.readEnum();
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
                    this.fusedModuleName_ = this.fusedModuleName_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_SystemApkMetadata_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_SystemApkMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(SystemApkMetadata.class, Builder.class);
        }

        public ProtocolStringList getFusedModuleNameList() {
            return this.fusedModuleName_;
        }

        @Override
        public int getFusedModuleNameCount() {
            return this.fusedModuleName_.size();
        }

        @Override
        public String getFusedModuleName(int index) {
            return (String)this.fusedModuleName_.get(index);
        }

        @Override
        public ByteString getFusedModuleNameBytes(int index) {
            return this.fusedModuleName_.getByteString(index);
        }

        @Override
        public int getSystemApkTypeValue() {
            return this.systemApkType_;
        }

        @Override
        public SystemApkType getSystemApkType() {
            SystemApkType result = SystemApkType.valueOf(this.systemApkType_);
            return result == null ? SystemApkType.UNRECOGNIZED : result;
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
            for (int i2 = 0; i2 < this.fusedModuleName_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 1, this.fusedModuleName_.getRaw(i2));
            }
            if (this.systemApkType_ != SystemApkType.UNSPECIFIED_VALUE.getNumber()) {
                output.writeEnum(2, this.systemApkType_);
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
            for (int i2 = 0; i2 < this.fusedModuleName_.size(); ++i2) {
                dataSize += SystemApkMetadata.computeStringSizeNoTag(this.fusedModuleName_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getFusedModuleNameList().size();
            if (this.systemApkType_ != SystemApkType.UNSPECIFIED_VALUE.getNumber()) {
                size += CodedOutputStream.computeEnumSize(2, this.systemApkType_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SystemApkMetadata)) {
                return super.equals(obj);
            }
            SystemApkMetadata other = (SystemApkMetadata)obj;
            boolean result = true;
            result = result && this.getFusedModuleNameList().equals(other.getFusedModuleNameList());
            result = result && this.systemApkType_ == other.systemApkType_;
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + SystemApkMetadata.getDescriptor().hashCode();
            if (this.getFusedModuleNameCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getFusedModuleNameList().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + this.systemApkType_;
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static SystemApkMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SystemApkMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SystemApkMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SystemApkMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SystemApkMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SystemApkMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SystemApkMetadata parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SystemApkMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SystemApkMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SystemApkMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SystemApkMetadata parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SystemApkMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return SystemApkMetadata.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SystemApkMetadata prototype) {
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

        public static SystemApkMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SystemApkMetadata> parser() {
            return PARSER;
        }

        public Parser<SystemApkMetadata> getParserForType() {
            return PARSER;
        }

        @Override
        public SystemApkMetadata getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SystemApkMetadataOrBuilder {
            private int bitField0_;
            private LazyStringList fusedModuleName_ = LazyStringArrayList.EMPTY;
            private int systemApkType_ = 0;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_SystemApkMetadata_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_SystemApkMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(SystemApkMetadata.class, Builder.class);
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
                this.fusedModuleName_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.systemApkType_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_SystemApkMetadata_descriptor;
            }

            @Override
            public SystemApkMetadata getDefaultInstanceForType() {
                return SystemApkMetadata.getDefaultInstance();
            }

            @Override
            public SystemApkMetadata build() {
                SystemApkMetadata result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public SystemApkMetadata buildPartial() {
                SystemApkMetadata result = new SystemApkMetadata(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    this.fusedModuleName_ = this.fusedModuleName_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.fusedModuleName_ = this.fusedModuleName_;
                result.systemApkType_ = this.systemApkType_;
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
                if (other instanceof SystemApkMetadata) {
                    return this.mergeFrom((SystemApkMetadata)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SystemApkMetadata other) {
                if (other == SystemApkMetadata.getDefaultInstance()) {
                    return this;
                }
                if (!other.fusedModuleName_.isEmpty()) {
                    if (this.fusedModuleName_.isEmpty()) {
                        this.fusedModuleName_ = other.fusedModuleName_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureFusedModuleNameIsMutable();
                        this.fusedModuleName_.addAll(other.fusedModuleName_);
                    }
                    this.onChanged();
                }
                if (other.systemApkType_ != 0) {
                    this.setSystemApkTypeValue(other.getSystemApkTypeValue());
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
                SystemApkMetadata parsedMessage = null;
                try {
                    parsedMessage = (SystemApkMetadata)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (SystemApkMetadata)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureFusedModuleNameIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.fusedModuleName_ = new LazyStringArrayList(this.fusedModuleName_);
                    this.bitField0_ |= 1;
                }
            }

            public ProtocolStringList getFusedModuleNameList() {
                return this.fusedModuleName_.getUnmodifiableView();
            }

            @Override
            public int getFusedModuleNameCount() {
                return this.fusedModuleName_.size();
            }

            @Override
            public String getFusedModuleName(int index) {
                return (String)this.fusedModuleName_.get(index);
            }

            @Override
            public ByteString getFusedModuleNameBytes(int index) {
                return this.fusedModuleName_.getByteString(index);
            }

            public Builder setFusedModuleName(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFusedModuleNameIsMutable();
                this.fusedModuleName_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addFusedModuleName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFusedModuleNameIsMutable();
                this.fusedModuleName_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllFusedModuleName(Iterable<String> values2) {
                this.ensureFusedModuleNameIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.fusedModuleName_);
                this.onChanged();
                return this;
            }

            public Builder clearFusedModuleName() {
                this.fusedModuleName_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder addFusedModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                SystemApkMetadata.checkByteStringIsUtf8(value);
                this.ensureFusedModuleNameIsMutable();
                this.fusedModuleName_.add(value);
                this.onChanged();
                return this;
            }

            @Override
            public int getSystemApkTypeValue() {
                return this.systemApkType_;
            }

            public Builder setSystemApkTypeValue(int value) {
                this.systemApkType_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public SystemApkType getSystemApkType() {
                SystemApkType result = SystemApkType.valueOf(this.systemApkType_);
                return result == null ? SystemApkType.UNRECOGNIZED : result;
            }

            public Builder setSystemApkType(SystemApkType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.systemApkType_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearSystemApkType() {
                this.systemApkType_ = 0;
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

        public static enum SystemApkType implements ProtocolMessageEnum
        {
            UNSPECIFIED_VALUE(0),
            SYSTEM(1),
            SYSTEM_STUB(2),
            SYSTEM_COMPRESSED(3),
            UNRECOGNIZED(-1);

            public static final int UNSPECIFIED_VALUE_VALUE = 0;
            public static final int SYSTEM_VALUE = 1;
            public static final int SYSTEM_STUB_VALUE = 2;
            public static final int SYSTEM_COMPRESSED_VALUE = 3;
            private static final Internal.EnumLiteMap<SystemApkType> internalValueMap;
            private static final SystemApkType[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static SystemApkType valueOf(int value) {
                return SystemApkType.forNumber(value);
            }

            public static SystemApkType forNumber(int value) {
                switch (value) {
                    case 0: {
                        return UNSPECIFIED_VALUE;
                    }
                    case 1: {
                        return SYSTEM;
                    }
                    case 2: {
                        return SYSTEM_STUB;
                    }
                    case 3: {
                        return SYSTEM_COMPRESSED;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<SystemApkType> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return SystemApkType.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return SystemApkType.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return SystemApkMetadata.getDescriptor().getEnumTypes().get(0);
            }

            public static SystemApkType valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != SystemApkType.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private SystemApkType(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<SystemApkType>(){

                    @Override
                    public SystemApkType findValueByNumber(int number) {
                        return SystemApkType.forNumber(number);
                    }
                };
                VALUES = SystemApkType.values();
            }
        }
    }

    public static interface SystemApkMetadataOrBuilder
    extends MessageOrBuilder {
        public List<String> getFusedModuleNameList();

        public int getFusedModuleNameCount();

        public String getFusedModuleName(int var1);

        public ByteString getFusedModuleNameBytes(int var1);

        public int getSystemApkTypeValue();

        public SystemApkMetadata.SystemApkType getSystemApkType();
    }

    public static final class StandaloneApkMetadata
    extends GeneratedMessageV3
    implements StandaloneApkMetadataOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int FUSED_MODULE_NAME_FIELD_NUMBER = 1;
        private LazyStringList fusedModuleName_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final StandaloneApkMetadata DEFAULT_INSTANCE = new StandaloneApkMetadata();
        private static final Parser<StandaloneApkMetadata> PARSER = new AbstractParser<StandaloneApkMetadata>(){

            @Override
            public StandaloneApkMetadata parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new StandaloneApkMetadata(input, extensionRegistry);
            }
        };

        private StandaloneApkMetadata(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private StandaloneApkMetadata() {
            this.fusedModuleName_ = LazyStringArrayList.EMPTY;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private StandaloneApkMetadata(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.fusedModuleName_ = new LazyStringArrayList();
                        mutable_bitField0_ |= true;
                    }
                    this.fusedModuleName_.add(s3);
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
                    this.fusedModuleName_ = this.fusedModuleName_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_StandaloneApkMetadata_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_StandaloneApkMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(StandaloneApkMetadata.class, Builder.class);
        }

        public ProtocolStringList getFusedModuleNameList() {
            return this.fusedModuleName_;
        }

        @Override
        public int getFusedModuleNameCount() {
            return this.fusedModuleName_.size();
        }

        @Override
        public String getFusedModuleName(int index) {
            return (String)this.fusedModuleName_.get(index);
        }

        @Override
        public ByteString getFusedModuleNameBytes(int index) {
            return this.fusedModuleName_.getByteString(index);
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
            for (int i2 = 0; i2 < this.fusedModuleName_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 1, this.fusedModuleName_.getRaw(i2));
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
            for (int i2 = 0; i2 < this.fusedModuleName_.size(); ++i2) {
                dataSize += StandaloneApkMetadata.computeStringSizeNoTag(this.fusedModuleName_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getFusedModuleNameList().size();
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof StandaloneApkMetadata)) {
                return super.equals(obj);
            }
            StandaloneApkMetadata other = (StandaloneApkMetadata)obj;
            boolean result = true;
            result = result && this.getFusedModuleNameList().equals(other.getFusedModuleNameList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + StandaloneApkMetadata.getDescriptor().hashCode();
            if (this.getFusedModuleNameCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getFusedModuleNameList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static StandaloneApkMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static StandaloneApkMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static StandaloneApkMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static StandaloneApkMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static StandaloneApkMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static StandaloneApkMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static StandaloneApkMetadata parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static StandaloneApkMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static StandaloneApkMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static StandaloneApkMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static StandaloneApkMetadata parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static StandaloneApkMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return StandaloneApkMetadata.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(StandaloneApkMetadata prototype) {
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

        public static StandaloneApkMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<StandaloneApkMetadata> parser() {
            return PARSER;
        }

        public Parser<StandaloneApkMetadata> getParserForType() {
            return PARSER;
        }

        @Override
        public StandaloneApkMetadata getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements StandaloneApkMetadataOrBuilder {
            private int bitField0_;
            private LazyStringList fusedModuleName_ = LazyStringArrayList.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_StandaloneApkMetadata_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_StandaloneApkMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(StandaloneApkMetadata.class, Builder.class);
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
                this.fusedModuleName_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_StandaloneApkMetadata_descriptor;
            }

            @Override
            public StandaloneApkMetadata getDefaultInstanceForType() {
                return StandaloneApkMetadata.getDefaultInstance();
            }

            @Override
            public StandaloneApkMetadata build() {
                StandaloneApkMetadata result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public StandaloneApkMetadata buildPartial() {
                StandaloneApkMetadata result = new StandaloneApkMetadata(this);
                int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.fusedModuleName_ = this.fusedModuleName_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.fusedModuleName_ = this.fusedModuleName_;
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
                if (other instanceof StandaloneApkMetadata) {
                    return this.mergeFrom((StandaloneApkMetadata)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(StandaloneApkMetadata other) {
                if (other == StandaloneApkMetadata.getDefaultInstance()) {
                    return this;
                }
                if (!other.fusedModuleName_.isEmpty()) {
                    if (this.fusedModuleName_.isEmpty()) {
                        this.fusedModuleName_ = other.fusedModuleName_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureFusedModuleNameIsMutable();
                        this.fusedModuleName_.addAll(other.fusedModuleName_);
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
                StandaloneApkMetadata parsedMessage = null;
                try {
                    parsedMessage = (StandaloneApkMetadata)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (StandaloneApkMetadata)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureFusedModuleNameIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.fusedModuleName_ = new LazyStringArrayList(this.fusedModuleName_);
                    this.bitField0_ |= 1;
                }
            }

            public ProtocolStringList getFusedModuleNameList() {
                return this.fusedModuleName_.getUnmodifiableView();
            }

            @Override
            public int getFusedModuleNameCount() {
                return this.fusedModuleName_.size();
            }

            @Override
            public String getFusedModuleName(int index) {
                return (String)this.fusedModuleName_.get(index);
            }

            @Override
            public ByteString getFusedModuleNameBytes(int index) {
                return this.fusedModuleName_.getByteString(index);
            }

            public Builder setFusedModuleName(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFusedModuleNameIsMutable();
                this.fusedModuleName_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addFusedModuleName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFusedModuleNameIsMutable();
                this.fusedModuleName_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllFusedModuleName(Iterable<String> values2) {
                this.ensureFusedModuleNameIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.fusedModuleName_);
                this.onChanged();
                return this;
            }

            public Builder clearFusedModuleName() {
                this.fusedModuleName_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder addFusedModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                StandaloneApkMetadata.checkByteStringIsUtf8(value);
                this.ensureFusedModuleNameIsMutable();
                this.fusedModuleName_.add(value);
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

    public static interface StandaloneApkMetadataOrBuilder
    extends MessageOrBuilder {
        public List<String> getFusedModuleNameList();

        public int getFusedModuleNameCount();

        public String getFusedModuleName(int var1);

        public ByteString getFusedModuleNameBytes(int var1);
    }

    public static final class SplitApkMetadata
    extends GeneratedMessageV3
    implements SplitApkMetadataOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int SPLIT_ID_FIELD_NUMBER = 1;
        private volatile Object splitId_;
        public static final int IS_MASTER_SPLIT_FIELD_NUMBER = 2;
        private boolean isMasterSplit_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SplitApkMetadata DEFAULT_INSTANCE = new SplitApkMetadata();
        private static final Parser<SplitApkMetadata> PARSER = new AbstractParser<SplitApkMetadata>(){

            @Override
            public SplitApkMetadata parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SplitApkMetadata(input, extensionRegistry);
            }
        };

        private SplitApkMetadata(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SplitApkMetadata() {
            this.splitId_ = "";
            this.isMasterSplit_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SplitApkMetadata(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            String s3 = input.readStringRequireUtf8();
                            this.splitId_ = s3;
                            continue block11;
                        }
                        case 16: 
                    }
                    this.isMasterSplit_ = input.readBool();
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
            return internal_static_android_bundle_SplitApkMetadata_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_SplitApkMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(SplitApkMetadata.class, Builder.class);
        }

        @Override
        public String getSplitId() {
            Object ref = this.splitId_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.splitId_ = s3;
            return s3;
        }

        @Override
        public ByteString getSplitIdBytes() {
            Object ref = this.splitId_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.splitId_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean getIsMasterSplit() {
            return this.isMasterSplit_;
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
            if (!this.getSplitIdBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.splitId_);
            }
            if (this.isMasterSplit_) {
                output.writeBool(2, this.isMasterSplit_);
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
            if (!this.getSplitIdBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.splitId_);
            }
            if (this.isMasterSplit_) {
                size += CodedOutputStream.computeBoolSize(2, this.isMasterSplit_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SplitApkMetadata)) {
                return super.equals(obj);
            }
            SplitApkMetadata other = (SplitApkMetadata)obj;
            boolean result = true;
            result = result && this.getSplitId().equals(other.getSplitId());
            result = result && this.getIsMasterSplit() == other.getIsMasterSplit();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + SplitApkMetadata.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getSplitId().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashBoolean(this.getIsMasterSplit());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static SplitApkMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SplitApkMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SplitApkMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SplitApkMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SplitApkMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SplitApkMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SplitApkMetadata parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SplitApkMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SplitApkMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SplitApkMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SplitApkMetadata parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SplitApkMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return SplitApkMetadata.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SplitApkMetadata prototype) {
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

        public static SplitApkMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SplitApkMetadata> parser() {
            return PARSER;
        }

        public Parser<SplitApkMetadata> getParserForType() {
            return PARSER;
        }

        @Override
        public SplitApkMetadata getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SplitApkMetadataOrBuilder {
            private Object splitId_ = "";
            private boolean isMasterSplit_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_SplitApkMetadata_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_SplitApkMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(SplitApkMetadata.class, Builder.class);
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
                this.splitId_ = "";
                this.isMasterSplit_ = false;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_SplitApkMetadata_descriptor;
            }

            @Override
            public SplitApkMetadata getDefaultInstanceForType() {
                return SplitApkMetadata.getDefaultInstance();
            }

            @Override
            public SplitApkMetadata build() {
                SplitApkMetadata result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public SplitApkMetadata buildPartial() {
                SplitApkMetadata result = new SplitApkMetadata(this);
                result.splitId_ = this.splitId_;
                result.isMasterSplit_ = this.isMasterSplit_;
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
                if (other instanceof SplitApkMetadata) {
                    return this.mergeFrom((SplitApkMetadata)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SplitApkMetadata other) {
                if (other == SplitApkMetadata.getDefaultInstance()) {
                    return this;
                }
                if (!other.getSplitId().isEmpty()) {
                    this.splitId_ = other.splitId_;
                    this.onChanged();
                }
                if (other.getIsMasterSplit()) {
                    this.setIsMasterSplit(other.getIsMasterSplit());
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
                SplitApkMetadata parsedMessage = null;
                try {
                    parsedMessage = (SplitApkMetadata)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (SplitApkMetadata)e2.getUnfinishedMessage();
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
            public String getSplitId() {
                Object ref = this.splitId_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.splitId_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getSplitIdBytes() {
                Object ref = this.splitId_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.splitId_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setSplitId(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.splitId_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearSplitId() {
                this.splitId_ = SplitApkMetadata.getDefaultInstance().getSplitId();
                this.onChanged();
                return this;
            }

            public Builder setSplitIdBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                SplitApkMetadata.checkByteStringIsUtf8(value);
                this.splitId_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean getIsMasterSplit() {
                return this.isMasterSplit_;
            }

            public Builder setIsMasterSplit(boolean value) {
                this.isMasterSplit_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearIsMasterSplit() {
                this.isMasterSplit_ = false;
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

    public static interface SplitApkMetadataOrBuilder
    extends MessageOrBuilder {
        public String getSplitId();

        public ByteString getSplitIdBytes();

        public boolean getIsMasterSplit();
    }

    public static final class ApkDescription
    extends GeneratedMessageV3
    implements ApkDescriptionOrBuilder {
        private static final long serialVersionUID = 0L;
        private int apkMetadataOneofValueCase_ = 0;
        private Object apkMetadataOneofValue_;
        public static final int TARGETING_FIELD_NUMBER = 1;
        private Targeting.ApkTargeting targeting_;
        public static final int PATH_FIELD_NUMBER = 2;
        private volatile Object path_;
        public static final int SPLIT_APK_METADATA_FIELD_NUMBER = 3;
        public static final int STANDALONE_APK_METADATA_FIELD_NUMBER = 4;
        public static final int INSTANT_APK_METADATA_FIELD_NUMBER = 5;
        public static final int SYSTEM_APK_METADATA_FIELD_NUMBER = 6;
        public static final int ASSET_SLICE_METADATA_FIELD_NUMBER = 7;
        public static final int APEX_APK_METADATA_FIELD_NUMBER = 8;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ApkDescription DEFAULT_INSTANCE = new ApkDescription();
        private static final Parser<ApkDescription> PARSER = new AbstractParser<ApkDescription>(){

            @Override
            public ApkDescription parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ApkDescription(input, extensionRegistry);
            }
        };

        private ApkDescription(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ApkDescription() {
            this.path_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ApkDescription(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block17: while (!done) {
                    GeneratedMessageV3.Builder subBuilder;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block17;
                        }
                        default: {
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block17;
                            done = true;
                            continue block17;
                        }
                        case 10: {
                            subBuilder = null;
                            if (this.targeting_ != null) {
                                subBuilder = this.targeting_.toBuilder();
                            }
                            this.targeting_ = input.readMessage(Targeting.ApkTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block17;
                            ((Targeting.ApkTargeting.Builder)subBuilder).mergeFrom(this.targeting_);
                            this.targeting_ = ((Targeting.ApkTargeting.Builder)subBuilder).buildPartial();
                            continue block17;
                        }
                        case 18: {
                            String s3 = input.readStringRequireUtf8();
                            this.path_ = s3;
                            continue block17;
                        }
                        case 26: {
                            subBuilder = null;
                            if (this.apkMetadataOneofValueCase_ == 3) {
                                subBuilder = ((SplitApkMetadata)this.apkMetadataOneofValue_).toBuilder();
                            }
                            this.apkMetadataOneofValue_ = input.readMessage(SplitApkMetadata.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((SplitApkMetadata.Builder)subBuilder).mergeFrom((SplitApkMetadata)this.apkMetadataOneofValue_);
                                this.apkMetadataOneofValue_ = ((SplitApkMetadata.Builder)subBuilder).buildPartial();
                            }
                            this.apkMetadataOneofValueCase_ = 3;
                            continue block17;
                        }
                        case 34: {
                            subBuilder = null;
                            if (this.apkMetadataOneofValueCase_ == 4) {
                                subBuilder = ((StandaloneApkMetadata)this.apkMetadataOneofValue_).toBuilder();
                            }
                            this.apkMetadataOneofValue_ = input.readMessage(StandaloneApkMetadata.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((StandaloneApkMetadata.Builder)subBuilder).mergeFrom((StandaloneApkMetadata)this.apkMetadataOneofValue_);
                                this.apkMetadataOneofValue_ = ((StandaloneApkMetadata.Builder)subBuilder).buildPartial();
                            }
                            this.apkMetadataOneofValueCase_ = 4;
                            continue block17;
                        }
                        case 42: {
                            subBuilder = null;
                            if (this.apkMetadataOneofValueCase_ == 5) {
                                subBuilder = ((SplitApkMetadata)this.apkMetadataOneofValue_).toBuilder();
                            }
                            this.apkMetadataOneofValue_ = input.readMessage(SplitApkMetadata.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((SplitApkMetadata.Builder)subBuilder).mergeFrom((SplitApkMetadata)this.apkMetadataOneofValue_);
                                this.apkMetadataOneofValue_ = ((SplitApkMetadata.Builder)subBuilder).buildPartial();
                            }
                            this.apkMetadataOneofValueCase_ = 5;
                            continue block17;
                        }
                        case 50: {
                            subBuilder = null;
                            if (this.apkMetadataOneofValueCase_ == 6) {
                                subBuilder = ((SystemApkMetadata)this.apkMetadataOneofValue_).toBuilder();
                            }
                            this.apkMetadataOneofValue_ = input.readMessage(SystemApkMetadata.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((SystemApkMetadata.Builder)subBuilder).mergeFrom((SystemApkMetadata)this.apkMetadataOneofValue_);
                                this.apkMetadataOneofValue_ = ((SystemApkMetadata.Builder)subBuilder).buildPartial();
                            }
                            this.apkMetadataOneofValueCase_ = 6;
                            continue block17;
                        }
                        case 58: {
                            subBuilder = null;
                            if (this.apkMetadataOneofValueCase_ == 7) {
                                subBuilder = ((SplitApkMetadata)this.apkMetadataOneofValue_).toBuilder();
                            }
                            this.apkMetadataOneofValue_ = input.readMessage(SplitApkMetadata.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((SplitApkMetadata.Builder)subBuilder).mergeFrom((SplitApkMetadata)this.apkMetadataOneofValue_);
                                this.apkMetadataOneofValue_ = ((SplitApkMetadata.Builder)subBuilder).buildPartial();
                            }
                            this.apkMetadataOneofValueCase_ = 7;
                            continue block17;
                        }
                        case 66: 
                    }
                    subBuilder = null;
                    if (this.apkMetadataOneofValueCase_ == 8) {
                        subBuilder = ((ApexApkMetadata)this.apkMetadataOneofValue_).toBuilder();
                    }
                    this.apkMetadataOneofValue_ = input.readMessage(ApexApkMetadata.parser(), extensionRegistry);
                    if (subBuilder != null) {
                        ((ApexApkMetadata.Builder)subBuilder).mergeFrom((ApexApkMetadata)this.apkMetadataOneofValue_);
                        this.apkMetadataOneofValue_ = ((ApexApkMetadata.Builder)subBuilder).buildPartial();
                    }
                    this.apkMetadataOneofValueCase_ = 8;
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
            return internal_static_android_bundle_ApkDescription_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ApkDescription_fieldAccessorTable.ensureFieldAccessorsInitialized(ApkDescription.class, Builder.class);
        }

        @Override
        public ApkMetadataOneofValueCase getApkMetadataOneofValueCase() {
            return ApkMetadataOneofValueCase.forNumber(this.apkMetadataOneofValueCase_);
        }

        @Override
        public boolean hasTargeting() {
            return this.targeting_ != null;
        }

        @Override
        public Targeting.ApkTargeting getTargeting() {
            return this.targeting_ == null ? Targeting.ApkTargeting.getDefaultInstance() : this.targeting_;
        }

        @Override
        public Targeting.ApkTargetingOrBuilder getTargetingOrBuilder() {
            return this.getTargeting();
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
        public boolean hasSplitApkMetadata() {
            return this.apkMetadataOneofValueCase_ == 3;
        }

        @Override
        public SplitApkMetadata getSplitApkMetadata() {
            if (this.apkMetadataOneofValueCase_ == 3) {
                return (SplitApkMetadata)this.apkMetadataOneofValue_;
            }
            return SplitApkMetadata.getDefaultInstance();
        }

        @Override
        public SplitApkMetadataOrBuilder getSplitApkMetadataOrBuilder() {
            if (this.apkMetadataOneofValueCase_ == 3) {
                return (SplitApkMetadata)this.apkMetadataOneofValue_;
            }
            return SplitApkMetadata.getDefaultInstance();
        }

        @Override
        public boolean hasStandaloneApkMetadata() {
            return this.apkMetadataOneofValueCase_ == 4;
        }

        @Override
        public StandaloneApkMetadata getStandaloneApkMetadata() {
            if (this.apkMetadataOneofValueCase_ == 4) {
                return (StandaloneApkMetadata)this.apkMetadataOneofValue_;
            }
            return StandaloneApkMetadata.getDefaultInstance();
        }

        @Override
        public StandaloneApkMetadataOrBuilder getStandaloneApkMetadataOrBuilder() {
            if (this.apkMetadataOneofValueCase_ == 4) {
                return (StandaloneApkMetadata)this.apkMetadataOneofValue_;
            }
            return StandaloneApkMetadata.getDefaultInstance();
        }

        @Override
        public boolean hasInstantApkMetadata() {
            return this.apkMetadataOneofValueCase_ == 5;
        }

        @Override
        public SplitApkMetadata getInstantApkMetadata() {
            if (this.apkMetadataOneofValueCase_ == 5) {
                return (SplitApkMetadata)this.apkMetadataOneofValue_;
            }
            return SplitApkMetadata.getDefaultInstance();
        }

        @Override
        public SplitApkMetadataOrBuilder getInstantApkMetadataOrBuilder() {
            if (this.apkMetadataOneofValueCase_ == 5) {
                return (SplitApkMetadata)this.apkMetadataOneofValue_;
            }
            return SplitApkMetadata.getDefaultInstance();
        }

        @Override
        public boolean hasSystemApkMetadata() {
            return this.apkMetadataOneofValueCase_ == 6;
        }

        @Override
        public SystemApkMetadata getSystemApkMetadata() {
            if (this.apkMetadataOneofValueCase_ == 6) {
                return (SystemApkMetadata)this.apkMetadataOneofValue_;
            }
            return SystemApkMetadata.getDefaultInstance();
        }

        @Override
        public SystemApkMetadataOrBuilder getSystemApkMetadataOrBuilder() {
            if (this.apkMetadataOneofValueCase_ == 6) {
                return (SystemApkMetadata)this.apkMetadataOneofValue_;
            }
            return SystemApkMetadata.getDefaultInstance();
        }

        @Override
        public boolean hasAssetSliceMetadata() {
            return this.apkMetadataOneofValueCase_ == 7;
        }

        @Override
        public SplitApkMetadata getAssetSliceMetadata() {
            if (this.apkMetadataOneofValueCase_ == 7) {
                return (SplitApkMetadata)this.apkMetadataOneofValue_;
            }
            return SplitApkMetadata.getDefaultInstance();
        }

        @Override
        public SplitApkMetadataOrBuilder getAssetSliceMetadataOrBuilder() {
            if (this.apkMetadataOneofValueCase_ == 7) {
                return (SplitApkMetadata)this.apkMetadataOneofValue_;
            }
            return SplitApkMetadata.getDefaultInstance();
        }

        @Override
        public boolean hasApexApkMetadata() {
            return this.apkMetadataOneofValueCase_ == 8;
        }

        @Override
        public ApexApkMetadata getApexApkMetadata() {
            if (this.apkMetadataOneofValueCase_ == 8) {
                return (ApexApkMetadata)this.apkMetadataOneofValue_;
            }
            return ApexApkMetadata.getDefaultInstance();
        }

        @Override
        public ApexApkMetadataOrBuilder getApexApkMetadataOrBuilder() {
            if (this.apkMetadataOneofValueCase_ == 8) {
                return (ApexApkMetadata)this.apkMetadataOneofValue_;
            }
            return ApexApkMetadata.getDefaultInstance();
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
            if (this.targeting_ != null) {
                output.writeMessage(1, this.getTargeting());
            }
            if (!this.getPathBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.path_);
            }
            if (this.apkMetadataOneofValueCase_ == 3) {
                output.writeMessage(3, (SplitApkMetadata)this.apkMetadataOneofValue_);
            }
            if (this.apkMetadataOneofValueCase_ == 4) {
                output.writeMessage(4, (StandaloneApkMetadata)this.apkMetadataOneofValue_);
            }
            if (this.apkMetadataOneofValueCase_ == 5) {
                output.writeMessage(5, (SplitApkMetadata)this.apkMetadataOneofValue_);
            }
            if (this.apkMetadataOneofValueCase_ == 6) {
                output.writeMessage(6, (SystemApkMetadata)this.apkMetadataOneofValue_);
            }
            if (this.apkMetadataOneofValueCase_ == 7) {
                output.writeMessage(7, (SplitApkMetadata)this.apkMetadataOneofValue_);
            }
            if (this.apkMetadataOneofValueCase_ == 8) {
                output.writeMessage(8, (ApexApkMetadata)this.apkMetadataOneofValue_);
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
            if (this.targeting_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getTargeting());
            }
            if (!this.getPathBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.path_);
            }
            if (this.apkMetadataOneofValueCase_ == 3) {
                size += CodedOutputStream.computeMessageSize(3, (SplitApkMetadata)this.apkMetadataOneofValue_);
            }
            if (this.apkMetadataOneofValueCase_ == 4) {
                size += CodedOutputStream.computeMessageSize(4, (StandaloneApkMetadata)this.apkMetadataOneofValue_);
            }
            if (this.apkMetadataOneofValueCase_ == 5) {
                size += CodedOutputStream.computeMessageSize(5, (SplitApkMetadata)this.apkMetadataOneofValue_);
            }
            if (this.apkMetadataOneofValueCase_ == 6) {
                size += CodedOutputStream.computeMessageSize(6, (SystemApkMetadata)this.apkMetadataOneofValue_);
            }
            if (this.apkMetadataOneofValueCase_ == 7) {
                size += CodedOutputStream.computeMessageSize(7, (SplitApkMetadata)this.apkMetadataOneofValue_);
            }
            if (this.apkMetadataOneofValueCase_ == 8) {
                size += CodedOutputStream.computeMessageSize(8, (ApexApkMetadata)this.apkMetadataOneofValue_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ApkDescription)) {
                return super.equals(obj);
            }
            ApkDescription other = (ApkDescription)obj;
            boolean result = true;
            boolean bl = result = result && this.hasTargeting() == other.hasTargeting();
            if (this.hasTargeting()) {
                result = result && this.getTargeting().equals(other.getTargeting());
            }
            result = result && this.getPath().equals(other.getPath());
            boolean bl2 = result = result && this.getApkMetadataOneofValueCase().equals(other.getApkMetadataOneofValueCase());
            if (!result) {
                return false;
            }
            switch (this.apkMetadataOneofValueCase_) {
                case 3: {
                    result = result && this.getSplitApkMetadata().equals(other.getSplitApkMetadata());
                    break;
                }
                case 4: {
                    result = result && this.getStandaloneApkMetadata().equals(other.getStandaloneApkMetadata());
                    break;
                }
                case 5: {
                    result = result && this.getInstantApkMetadata().equals(other.getInstantApkMetadata());
                    break;
                }
                case 6: {
                    result = result && this.getSystemApkMetadata().equals(other.getSystemApkMetadata());
                    break;
                }
                case 7: {
                    result = result && this.getAssetSliceMetadata().equals(other.getAssetSliceMetadata());
                    break;
                }
                case 8: {
                    result = result && this.getApexApkMetadata().equals(other.getApexApkMetadata());
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
            hash = 19 * hash + ApkDescription.getDescriptor().hashCode();
            if (this.hasTargeting()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getTargeting().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getPath().hashCode();
            switch (this.apkMetadataOneofValueCase_) {
                case 3: {
                    hash = 37 * hash + 3;
                    hash = 53 * hash + this.getSplitApkMetadata().hashCode();
                    break;
                }
                case 4: {
                    hash = 37 * hash + 4;
                    hash = 53 * hash + this.getStandaloneApkMetadata().hashCode();
                    break;
                }
                case 5: {
                    hash = 37 * hash + 5;
                    hash = 53 * hash + this.getInstantApkMetadata().hashCode();
                    break;
                }
                case 6: {
                    hash = 37 * hash + 6;
                    hash = 53 * hash + this.getSystemApkMetadata().hashCode();
                    break;
                }
                case 7: {
                    hash = 37 * hash + 7;
                    hash = 53 * hash + this.getAssetSliceMetadata().hashCode();
                    break;
                }
                case 8: {
                    hash = 37 * hash + 8;
                    hash = 53 * hash + this.getApexApkMetadata().hashCode();
                    break;
                }
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ApkDescription parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApkDescription parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApkDescription parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApkDescription parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApkDescription parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApkDescription parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApkDescription parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApkDescription parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApkDescription parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ApkDescription parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApkDescription parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApkDescription parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ApkDescription.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ApkDescription prototype) {
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

        public static ApkDescription getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ApkDescription> parser() {
            return PARSER;
        }

        public Parser<ApkDescription> getParserForType() {
            return PARSER;
        }

        @Override
        public ApkDescription getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ApkDescriptionOrBuilder {
            private int apkMetadataOneofValueCase_ = 0;
            private Object apkMetadataOneofValue_;
            private Targeting.ApkTargeting targeting_ = null;
            private SingleFieldBuilderV3<Targeting.ApkTargeting, Targeting.ApkTargeting.Builder, Targeting.ApkTargetingOrBuilder> targetingBuilder_;
            private Object path_ = "";
            private SingleFieldBuilderV3<SplitApkMetadata, SplitApkMetadata.Builder, SplitApkMetadataOrBuilder> splitApkMetadataBuilder_;
            private SingleFieldBuilderV3<StandaloneApkMetadata, StandaloneApkMetadata.Builder, StandaloneApkMetadataOrBuilder> standaloneApkMetadataBuilder_;
            private SingleFieldBuilderV3<SplitApkMetadata, SplitApkMetadata.Builder, SplitApkMetadataOrBuilder> instantApkMetadataBuilder_;
            private SingleFieldBuilderV3<SystemApkMetadata, SystemApkMetadata.Builder, SystemApkMetadataOrBuilder> systemApkMetadataBuilder_;
            private SingleFieldBuilderV3<SplitApkMetadata, SplitApkMetadata.Builder, SplitApkMetadataOrBuilder> assetSliceMetadataBuilder_;
            private SingleFieldBuilderV3<ApexApkMetadata, ApexApkMetadata.Builder, ApexApkMetadataOrBuilder> apexApkMetadataBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ApkDescription_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ApkDescription_fieldAccessorTable.ensureFieldAccessorsInitialized(ApkDescription.class, Builder.class);
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
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = null;
                } else {
                    this.targeting_ = null;
                    this.targetingBuilder_ = null;
                }
                this.path_ = "";
                this.apkMetadataOneofValueCase_ = 0;
                this.apkMetadataOneofValue_ = null;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ApkDescription_descriptor;
            }

            @Override
            public ApkDescription getDefaultInstanceForType() {
                return ApkDescription.getDefaultInstance();
            }

            @Override
            public ApkDescription build() {
                ApkDescription result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ApkDescription buildPartial() {
                ApkDescription result = new ApkDescription(this);
                if (this.targetingBuilder_ == null) {
                    result.targeting_ = this.targeting_;
                } else {
                    result.targeting_ = this.targetingBuilder_.build();
                }
                result.path_ = this.path_;
                if (this.apkMetadataOneofValueCase_ == 3) {
                    if (this.splitApkMetadataBuilder_ == null) {
                        result.apkMetadataOneofValue_ = this.apkMetadataOneofValue_;
                    } else {
                        result.apkMetadataOneofValue_ = this.splitApkMetadataBuilder_.build();
                    }
                }
                if (this.apkMetadataOneofValueCase_ == 4) {
                    if (this.standaloneApkMetadataBuilder_ == null) {
                        result.apkMetadataOneofValue_ = this.apkMetadataOneofValue_;
                    } else {
                        result.apkMetadataOneofValue_ = this.standaloneApkMetadataBuilder_.build();
                    }
                }
                if (this.apkMetadataOneofValueCase_ == 5) {
                    if (this.instantApkMetadataBuilder_ == null) {
                        result.apkMetadataOneofValue_ = this.apkMetadataOneofValue_;
                    } else {
                        result.apkMetadataOneofValue_ = this.instantApkMetadataBuilder_.build();
                    }
                }
                if (this.apkMetadataOneofValueCase_ == 6) {
                    if (this.systemApkMetadataBuilder_ == null) {
                        result.apkMetadataOneofValue_ = this.apkMetadataOneofValue_;
                    } else {
                        result.apkMetadataOneofValue_ = this.systemApkMetadataBuilder_.build();
                    }
                }
                if (this.apkMetadataOneofValueCase_ == 7) {
                    if (this.assetSliceMetadataBuilder_ == null) {
                        result.apkMetadataOneofValue_ = this.apkMetadataOneofValue_;
                    } else {
                        result.apkMetadataOneofValue_ = this.assetSliceMetadataBuilder_.build();
                    }
                }
                if (this.apkMetadataOneofValueCase_ == 8) {
                    if (this.apexApkMetadataBuilder_ == null) {
                        result.apkMetadataOneofValue_ = this.apkMetadataOneofValue_;
                    } else {
                        result.apkMetadataOneofValue_ = this.apexApkMetadataBuilder_.build();
                    }
                }
                result.apkMetadataOneofValueCase_ = this.apkMetadataOneofValueCase_;
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
                if (other instanceof ApkDescription) {
                    return this.mergeFrom((ApkDescription)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ApkDescription other) {
                if (other == ApkDescription.getDefaultInstance()) {
                    return this;
                }
                if (other.hasTargeting()) {
                    this.mergeTargeting(other.getTargeting());
                }
                if (!other.getPath().isEmpty()) {
                    this.path_ = other.path_;
                    this.onChanged();
                }
                switch (other.getApkMetadataOneofValueCase()) {
                    case SPLIT_APK_METADATA: {
                        this.mergeSplitApkMetadata(other.getSplitApkMetadata());
                        break;
                    }
                    case STANDALONE_APK_METADATA: {
                        this.mergeStandaloneApkMetadata(other.getStandaloneApkMetadata());
                        break;
                    }
                    case INSTANT_APK_METADATA: {
                        this.mergeInstantApkMetadata(other.getInstantApkMetadata());
                        break;
                    }
                    case SYSTEM_APK_METADATA: {
                        this.mergeSystemApkMetadata(other.getSystemApkMetadata());
                        break;
                    }
                    case ASSET_SLICE_METADATA: {
                        this.mergeAssetSliceMetadata(other.getAssetSliceMetadata());
                        break;
                    }
                    case APEX_APK_METADATA: {
                        this.mergeApexApkMetadata(other.getApexApkMetadata());
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
                ApkDescription parsedMessage = null;
                try {
                    parsedMessage = (ApkDescription)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ApkDescription)e2.getUnfinishedMessage();
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
            public ApkMetadataOneofValueCase getApkMetadataOneofValueCase() {
                return ApkMetadataOneofValueCase.forNumber(this.apkMetadataOneofValueCase_);
            }

            public Builder clearApkMetadataOneofValue() {
                this.apkMetadataOneofValueCase_ = 0;
                this.apkMetadataOneofValue_ = null;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasTargeting() {
                return this.targetingBuilder_ != null || this.targeting_ != null;
            }

            @Override
            public Targeting.ApkTargeting getTargeting() {
                if (this.targetingBuilder_ == null) {
                    return this.targeting_ == null ? Targeting.ApkTargeting.getDefaultInstance() : this.targeting_;
                }
                return this.targetingBuilder_.getMessage();
            }

            public Builder setTargeting(Targeting.ApkTargeting value) {
                if (this.targetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.targeting_ = value;
                    this.onChanged();
                } else {
                    this.targetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setTargeting(Targeting.ApkTargeting.Builder builderForValue) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.targetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTargeting(Targeting.ApkTargeting value) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = this.targeting_ != null ? Targeting.ApkTargeting.newBuilder(this.targeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.targetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearTargeting() {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = null;
                    this.onChanged();
                } else {
                    this.targeting_ = null;
                    this.targetingBuilder_ = null;
                }
                return this;
            }

            public Targeting.ApkTargeting.Builder getTargetingBuilder() {
                this.onChanged();
                return this.getTargetingFieldBuilder().getBuilder();
            }

            @Override
            public Targeting.ApkTargetingOrBuilder getTargetingOrBuilder() {
                if (this.targetingBuilder_ != null) {
                    return this.targetingBuilder_.getMessageOrBuilder();
                }
                return this.targeting_ == null ? Targeting.ApkTargeting.getDefaultInstance() : this.targeting_;
            }

            private SingleFieldBuilderV3<Targeting.ApkTargeting, Targeting.ApkTargeting.Builder, Targeting.ApkTargetingOrBuilder> getTargetingFieldBuilder() {
                if (this.targetingBuilder_ == null) {
                    this.targetingBuilder_ = new SingleFieldBuilderV3(this.getTargeting(), this.getParentForChildren(), this.isClean());
                    this.targeting_ = null;
                }
                return this.targetingBuilder_;
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
                this.path_ = ApkDescription.getDefaultInstance().getPath();
                this.onChanged();
                return this;
            }

            public Builder setPathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ApkDescription.checkByteStringIsUtf8(value);
                this.path_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasSplitApkMetadata() {
                return this.apkMetadataOneofValueCase_ == 3;
            }

            @Override
            public SplitApkMetadata getSplitApkMetadata() {
                if (this.splitApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 3) {
                        return (SplitApkMetadata)this.apkMetadataOneofValue_;
                    }
                    return SplitApkMetadata.getDefaultInstance();
                }
                if (this.apkMetadataOneofValueCase_ == 3) {
                    return this.splitApkMetadataBuilder_.getMessage();
                }
                return SplitApkMetadata.getDefaultInstance();
            }

            public Builder setSplitApkMetadata(SplitApkMetadata value) {
                if (this.splitApkMetadataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.apkMetadataOneofValue_ = value;
                    this.onChanged();
                } else {
                    this.splitApkMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 3;
                return this;
            }

            public Builder setSplitApkMetadata(SplitApkMetadata.Builder builderForValue) {
                if (this.splitApkMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.splitApkMetadataBuilder_.setMessage(builderForValue.build());
                }
                this.apkMetadataOneofValueCase_ = 3;
                return this;
            }

            public Builder mergeSplitApkMetadata(SplitApkMetadata value) {
                if (this.splitApkMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = this.apkMetadataOneofValueCase_ == 3 && this.apkMetadataOneofValue_ != SplitApkMetadata.getDefaultInstance() ? SplitApkMetadata.newBuilder((SplitApkMetadata)this.apkMetadataOneofValue_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.apkMetadataOneofValueCase_ == 3) {
                        this.splitApkMetadataBuilder_.mergeFrom(value);
                    }
                    this.splitApkMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 3;
                return this;
            }

            public Builder clearSplitApkMetadata() {
                if (this.splitApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 3) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.apkMetadataOneofValueCase_ == 3) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                    }
                    this.splitApkMetadataBuilder_.clear();
                }
                return this;
            }

            public SplitApkMetadata.Builder getSplitApkMetadataBuilder() {
                return this.getSplitApkMetadataFieldBuilder().getBuilder();
            }

            @Override
            public SplitApkMetadataOrBuilder getSplitApkMetadataOrBuilder() {
                if (this.apkMetadataOneofValueCase_ == 3 && this.splitApkMetadataBuilder_ != null) {
                    return this.splitApkMetadataBuilder_.getMessageOrBuilder();
                }
                if (this.apkMetadataOneofValueCase_ == 3) {
                    return (SplitApkMetadata)this.apkMetadataOneofValue_;
                }
                return SplitApkMetadata.getDefaultInstance();
            }

            private SingleFieldBuilderV3<SplitApkMetadata, SplitApkMetadata.Builder, SplitApkMetadataOrBuilder> getSplitApkMetadataFieldBuilder() {
                if (this.splitApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ != 3) {
                        this.apkMetadataOneofValue_ = SplitApkMetadata.getDefaultInstance();
                    }
                    this.splitApkMetadataBuilder_ = new SingleFieldBuilderV3((SplitApkMetadata)this.apkMetadataOneofValue_, this.getParentForChildren(), this.isClean());
                    this.apkMetadataOneofValue_ = null;
                }
                this.apkMetadataOneofValueCase_ = 3;
                this.onChanged();
                return this.splitApkMetadataBuilder_;
            }

            @Override
            public boolean hasStandaloneApkMetadata() {
                return this.apkMetadataOneofValueCase_ == 4;
            }

            @Override
            public StandaloneApkMetadata getStandaloneApkMetadata() {
                if (this.standaloneApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 4) {
                        return (StandaloneApkMetadata)this.apkMetadataOneofValue_;
                    }
                    return StandaloneApkMetadata.getDefaultInstance();
                }
                if (this.apkMetadataOneofValueCase_ == 4) {
                    return this.standaloneApkMetadataBuilder_.getMessage();
                }
                return StandaloneApkMetadata.getDefaultInstance();
            }

            public Builder setStandaloneApkMetadata(StandaloneApkMetadata value) {
                if (this.standaloneApkMetadataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.apkMetadataOneofValue_ = value;
                    this.onChanged();
                } else {
                    this.standaloneApkMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 4;
                return this;
            }

            public Builder setStandaloneApkMetadata(StandaloneApkMetadata.Builder builderForValue) {
                if (this.standaloneApkMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.standaloneApkMetadataBuilder_.setMessage(builderForValue.build());
                }
                this.apkMetadataOneofValueCase_ = 4;
                return this;
            }

            public Builder mergeStandaloneApkMetadata(StandaloneApkMetadata value) {
                if (this.standaloneApkMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = this.apkMetadataOneofValueCase_ == 4 && this.apkMetadataOneofValue_ != StandaloneApkMetadata.getDefaultInstance() ? StandaloneApkMetadata.newBuilder((StandaloneApkMetadata)this.apkMetadataOneofValue_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.apkMetadataOneofValueCase_ == 4) {
                        this.standaloneApkMetadataBuilder_.mergeFrom(value);
                    }
                    this.standaloneApkMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 4;
                return this;
            }

            public Builder clearStandaloneApkMetadata() {
                if (this.standaloneApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 4) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.apkMetadataOneofValueCase_ == 4) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                    }
                    this.standaloneApkMetadataBuilder_.clear();
                }
                return this;
            }

            public StandaloneApkMetadata.Builder getStandaloneApkMetadataBuilder() {
                return this.getStandaloneApkMetadataFieldBuilder().getBuilder();
            }

            @Override
            public StandaloneApkMetadataOrBuilder getStandaloneApkMetadataOrBuilder() {
                if (this.apkMetadataOneofValueCase_ == 4 && this.standaloneApkMetadataBuilder_ != null) {
                    return this.standaloneApkMetadataBuilder_.getMessageOrBuilder();
                }
                if (this.apkMetadataOneofValueCase_ == 4) {
                    return (StandaloneApkMetadata)this.apkMetadataOneofValue_;
                }
                return StandaloneApkMetadata.getDefaultInstance();
            }

            private SingleFieldBuilderV3<StandaloneApkMetadata, StandaloneApkMetadata.Builder, StandaloneApkMetadataOrBuilder> getStandaloneApkMetadataFieldBuilder() {
                if (this.standaloneApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ != 4) {
                        this.apkMetadataOneofValue_ = StandaloneApkMetadata.getDefaultInstance();
                    }
                    this.standaloneApkMetadataBuilder_ = new SingleFieldBuilderV3((StandaloneApkMetadata)this.apkMetadataOneofValue_, this.getParentForChildren(), this.isClean());
                    this.apkMetadataOneofValue_ = null;
                }
                this.apkMetadataOneofValueCase_ = 4;
                this.onChanged();
                return this.standaloneApkMetadataBuilder_;
            }

            @Override
            public boolean hasInstantApkMetadata() {
                return this.apkMetadataOneofValueCase_ == 5;
            }

            @Override
            public SplitApkMetadata getInstantApkMetadata() {
                if (this.instantApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 5) {
                        return (SplitApkMetadata)this.apkMetadataOneofValue_;
                    }
                    return SplitApkMetadata.getDefaultInstance();
                }
                if (this.apkMetadataOneofValueCase_ == 5) {
                    return this.instantApkMetadataBuilder_.getMessage();
                }
                return SplitApkMetadata.getDefaultInstance();
            }

            public Builder setInstantApkMetadata(SplitApkMetadata value) {
                if (this.instantApkMetadataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.apkMetadataOneofValue_ = value;
                    this.onChanged();
                } else {
                    this.instantApkMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 5;
                return this;
            }

            public Builder setInstantApkMetadata(SplitApkMetadata.Builder builderForValue) {
                if (this.instantApkMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.instantApkMetadataBuilder_.setMessage(builderForValue.build());
                }
                this.apkMetadataOneofValueCase_ = 5;
                return this;
            }

            public Builder mergeInstantApkMetadata(SplitApkMetadata value) {
                if (this.instantApkMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = this.apkMetadataOneofValueCase_ == 5 && this.apkMetadataOneofValue_ != SplitApkMetadata.getDefaultInstance() ? SplitApkMetadata.newBuilder((SplitApkMetadata)this.apkMetadataOneofValue_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.apkMetadataOneofValueCase_ == 5) {
                        this.instantApkMetadataBuilder_.mergeFrom(value);
                    }
                    this.instantApkMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 5;
                return this;
            }

            public Builder clearInstantApkMetadata() {
                if (this.instantApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 5) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.apkMetadataOneofValueCase_ == 5) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                    }
                    this.instantApkMetadataBuilder_.clear();
                }
                return this;
            }

            public SplitApkMetadata.Builder getInstantApkMetadataBuilder() {
                return this.getInstantApkMetadataFieldBuilder().getBuilder();
            }

            @Override
            public SplitApkMetadataOrBuilder getInstantApkMetadataOrBuilder() {
                if (this.apkMetadataOneofValueCase_ == 5 && this.instantApkMetadataBuilder_ != null) {
                    return this.instantApkMetadataBuilder_.getMessageOrBuilder();
                }
                if (this.apkMetadataOneofValueCase_ == 5) {
                    return (SplitApkMetadata)this.apkMetadataOneofValue_;
                }
                return SplitApkMetadata.getDefaultInstance();
            }

            private SingleFieldBuilderV3<SplitApkMetadata, SplitApkMetadata.Builder, SplitApkMetadataOrBuilder> getInstantApkMetadataFieldBuilder() {
                if (this.instantApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ != 5) {
                        this.apkMetadataOneofValue_ = SplitApkMetadata.getDefaultInstance();
                    }
                    this.instantApkMetadataBuilder_ = new SingleFieldBuilderV3((SplitApkMetadata)this.apkMetadataOneofValue_, this.getParentForChildren(), this.isClean());
                    this.apkMetadataOneofValue_ = null;
                }
                this.apkMetadataOneofValueCase_ = 5;
                this.onChanged();
                return this.instantApkMetadataBuilder_;
            }

            @Override
            public boolean hasSystemApkMetadata() {
                return this.apkMetadataOneofValueCase_ == 6;
            }

            @Override
            public SystemApkMetadata getSystemApkMetadata() {
                if (this.systemApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 6) {
                        return (SystemApkMetadata)this.apkMetadataOneofValue_;
                    }
                    return SystemApkMetadata.getDefaultInstance();
                }
                if (this.apkMetadataOneofValueCase_ == 6) {
                    return this.systemApkMetadataBuilder_.getMessage();
                }
                return SystemApkMetadata.getDefaultInstance();
            }

            public Builder setSystemApkMetadata(SystemApkMetadata value) {
                if (this.systemApkMetadataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.apkMetadataOneofValue_ = value;
                    this.onChanged();
                } else {
                    this.systemApkMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 6;
                return this;
            }

            public Builder setSystemApkMetadata(SystemApkMetadata.Builder builderForValue) {
                if (this.systemApkMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.systemApkMetadataBuilder_.setMessage(builderForValue.build());
                }
                this.apkMetadataOneofValueCase_ = 6;
                return this;
            }

            public Builder mergeSystemApkMetadata(SystemApkMetadata value) {
                if (this.systemApkMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = this.apkMetadataOneofValueCase_ == 6 && this.apkMetadataOneofValue_ != SystemApkMetadata.getDefaultInstance() ? SystemApkMetadata.newBuilder((SystemApkMetadata)this.apkMetadataOneofValue_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.apkMetadataOneofValueCase_ == 6) {
                        this.systemApkMetadataBuilder_.mergeFrom(value);
                    }
                    this.systemApkMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 6;
                return this;
            }

            public Builder clearSystemApkMetadata() {
                if (this.systemApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 6) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.apkMetadataOneofValueCase_ == 6) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                    }
                    this.systemApkMetadataBuilder_.clear();
                }
                return this;
            }

            public SystemApkMetadata.Builder getSystemApkMetadataBuilder() {
                return this.getSystemApkMetadataFieldBuilder().getBuilder();
            }

            @Override
            public SystemApkMetadataOrBuilder getSystemApkMetadataOrBuilder() {
                if (this.apkMetadataOneofValueCase_ == 6 && this.systemApkMetadataBuilder_ != null) {
                    return this.systemApkMetadataBuilder_.getMessageOrBuilder();
                }
                if (this.apkMetadataOneofValueCase_ == 6) {
                    return (SystemApkMetadata)this.apkMetadataOneofValue_;
                }
                return SystemApkMetadata.getDefaultInstance();
            }

            private SingleFieldBuilderV3<SystemApkMetadata, SystemApkMetadata.Builder, SystemApkMetadataOrBuilder> getSystemApkMetadataFieldBuilder() {
                if (this.systemApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ != 6) {
                        this.apkMetadataOneofValue_ = SystemApkMetadata.getDefaultInstance();
                    }
                    this.systemApkMetadataBuilder_ = new SingleFieldBuilderV3((SystemApkMetadata)this.apkMetadataOneofValue_, this.getParentForChildren(), this.isClean());
                    this.apkMetadataOneofValue_ = null;
                }
                this.apkMetadataOneofValueCase_ = 6;
                this.onChanged();
                return this.systemApkMetadataBuilder_;
            }

            @Override
            public boolean hasAssetSliceMetadata() {
                return this.apkMetadataOneofValueCase_ == 7;
            }

            @Override
            public SplitApkMetadata getAssetSliceMetadata() {
                if (this.assetSliceMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 7) {
                        return (SplitApkMetadata)this.apkMetadataOneofValue_;
                    }
                    return SplitApkMetadata.getDefaultInstance();
                }
                if (this.apkMetadataOneofValueCase_ == 7) {
                    return this.assetSliceMetadataBuilder_.getMessage();
                }
                return SplitApkMetadata.getDefaultInstance();
            }

            public Builder setAssetSliceMetadata(SplitApkMetadata value) {
                if (this.assetSliceMetadataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.apkMetadataOneofValue_ = value;
                    this.onChanged();
                } else {
                    this.assetSliceMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 7;
                return this;
            }

            public Builder setAssetSliceMetadata(SplitApkMetadata.Builder builderForValue) {
                if (this.assetSliceMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.assetSliceMetadataBuilder_.setMessage(builderForValue.build());
                }
                this.apkMetadataOneofValueCase_ = 7;
                return this;
            }

            public Builder mergeAssetSliceMetadata(SplitApkMetadata value) {
                if (this.assetSliceMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = this.apkMetadataOneofValueCase_ == 7 && this.apkMetadataOneofValue_ != SplitApkMetadata.getDefaultInstance() ? SplitApkMetadata.newBuilder((SplitApkMetadata)this.apkMetadataOneofValue_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.apkMetadataOneofValueCase_ == 7) {
                        this.assetSliceMetadataBuilder_.mergeFrom(value);
                    }
                    this.assetSliceMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 7;
                return this;
            }

            public Builder clearAssetSliceMetadata() {
                if (this.assetSliceMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 7) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.apkMetadataOneofValueCase_ == 7) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                    }
                    this.assetSliceMetadataBuilder_.clear();
                }
                return this;
            }

            public SplitApkMetadata.Builder getAssetSliceMetadataBuilder() {
                return this.getAssetSliceMetadataFieldBuilder().getBuilder();
            }

            @Override
            public SplitApkMetadataOrBuilder getAssetSliceMetadataOrBuilder() {
                if (this.apkMetadataOneofValueCase_ == 7 && this.assetSliceMetadataBuilder_ != null) {
                    return this.assetSliceMetadataBuilder_.getMessageOrBuilder();
                }
                if (this.apkMetadataOneofValueCase_ == 7) {
                    return (SplitApkMetadata)this.apkMetadataOneofValue_;
                }
                return SplitApkMetadata.getDefaultInstance();
            }

            private SingleFieldBuilderV3<SplitApkMetadata, SplitApkMetadata.Builder, SplitApkMetadataOrBuilder> getAssetSliceMetadataFieldBuilder() {
                if (this.assetSliceMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ != 7) {
                        this.apkMetadataOneofValue_ = SplitApkMetadata.getDefaultInstance();
                    }
                    this.assetSliceMetadataBuilder_ = new SingleFieldBuilderV3((SplitApkMetadata)this.apkMetadataOneofValue_, this.getParentForChildren(), this.isClean());
                    this.apkMetadataOneofValue_ = null;
                }
                this.apkMetadataOneofValueCase_ = 7;
                this.onChanged();
                return this.assetSliceMetadataBuilder_;
            }

            @Override
            public boolean hasApexApkMetadata() {
                return this.apkMetadataOneofValueCase_ == 8;
            }

            @Override
            public ApexApkMetadata getApexApkMetadata() {
                if (this.apexApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 8) {
                        return (ApexApkMetadata)this.apkMetadataOneofValue_;
                    }
                    return ApexApkMetadata.getDefaultInstance();
                }
                if (this.apkMetadataOneofValueCase_ == 8) {
                    return this.apexApkMetadataBuilder_.getMessage();
                }
                return ApexApkMetadata.getDefaultInstance();
            }

            public Builder setApexApkMetadata(ApexApkMetadata value) {
                if (this.apexApkMetadataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.apkMetadataOneofValue_ = value;
                    this.onChanged();
                } else {
                    this.apexApkMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 8;
                return this;
            }

            public Builder setApexApkMetadata(ApexApkMetadata.Builder builderForValue) {
                if (this.apexApkMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.apexApkMetadataBuilder_.setMessage(builderForValue.build());
                }
                this.apkMetadataOneofValueCase_ = 8;
                return this;
            }

            public Builder mergeApexApkMetadata(ApexApkMetadata value) {
                if (this.apexApkMetadataBuilder_ == null) {
                    this.apkMetadataOneofValue_ = this.apkMetadataOneofValueCase_ == 8 && this.apkMetadataOneofValue_ != ApexApkMetadata.getDefaultInstance() ? ApexApkMetadata.newBuilder((ApexApkMetadata)this.apkMetadataOneofValue_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.apkMetadataOneofValueCase_ == 8) {
                        this.apexApkMetadataBuilder_.mergeFrom(value);
                    }
                    this.apexApkMetadataBuilder_.setMessage(value);
                }
                this.apkMetadataOneofValueCase_ = 8;
                return this;
            }

            public Builder clearApexApkMetadata() {
                if (this.apexApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ == 8) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.apkMetadataOneofValueCase_ == 8) {
                        this.apkMetadataOneofValueCase_ = 0;
                        this.apkMetadataOneofValue_ = null;
                    }
                    this.apexApkMetadataBuilder_.clear();
                }
                return this;
            }

            public ApexApkMetadata.Builder getApexApkMetadataBuilder() {
                return this.getApexApkMetadataFieldBuilder().getBuilder();
            }

            @Override
            public ApexApkMetadataOrBuilder getApexApkMetadataOrBuilder() {
                if (this.apkMetadataOneofValueCase_ == 8 && this.apexApkMetadataBuilder_ != null) {
                    return this.apexApkMetadataBuilder_.getMessageOrBuilder();
                }
                if (this.apkMetadataOneofValueCase_ == 8) {
                    return (ApexApkMetadata)this.apkMetadataOneofValue_;
                }
                return ApexApkMetadata.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ApexApkMetadata, ApexApkMetadata.Builder, ApexApkMetadataOrBuilder> getApexApkMetadataFieldBuilder() {
                if (this.apexApkMetadataBuilder_ == null) {
                    if (this.apkMetadataOneofValueCase_ != 8) {
                        this.apkMetadataOneofValue_ = ApexApkMetadata.getDefaultInstance();
                    }
                    this.apexApkMetadataBuilder_ = new SingleFieldBuilderV3((ApexApkMetadata)this.apkMetadataOneofValue_, this.getParentForChildren(), this.isClean());
                    this.apkMetadataOneofValue_ = null;
                }
                this.apkMetadataOneofValueCase_ = 8;
                this.onChanged();
                return this.apexApkMetadataBuilder_;
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

        public static enum ApkMetadataOneofValueCase implements Internal.EnumLite
        {
            SPLIT_APK_METADATA(3),
            STANDALONE_APK_METADATA(4),
            INSTANT_APK_METADATA(5),
            SYSTEM_APK_METADATA(6),
            ASSET_SLICE_METADATA(7),
            APEX_APK_METADATA(8),
            APKMETADATAONEOFVALUE_NOT_SET(0);

            private final int value;

            private ApkMetadataOneofValueCase(int value) {
                this.value = value;
            }

            @Deprecated
            public static ApkMetadataOneofValueCase valueOf(int value) {
                return ApkMetadataOneofValueCase.forNumber(value);
            }

            public static ApkMetadataOneofValueCase forNumber(int value) {
                switch (value) {
                    case 3: {
                        return SPLIT_APK_METADATA;
                    }
                    case 4: {
                        return STANDALONE_APK_METADATA;
                    }
                    case 5: {
                        return INSTANT_APK_METADATA;
                    }
                    case 6: {
                        return SYSTEM_APK_METADATA;
                    }
                    case 7: {
                        return ASSET_SLICE_METADATA;
                    }
                    case 8: {
                        return APEX_APK_METADATA;
                    }
                    case 0: {
                        return APKMETADATAONEOFVALUE_NOT_SET;
                    }
                }
                return null;
            }

            @Override
            public int getNumber() {
                return this.value;
            }
        }
    }

    public static interface ApkDescriptionOrBuilder
    extends MessageOrBuilder {
        public boolean hasTargeting();

        public Targeting.ApkTargeting getTargeting();

        public Targeting.ApkTargetingOrBuilder getTargetingOrBuilder();

        public String getPath();

        public ByteString getPathBytes();

        public boolean hasSplitApkMetadata();

        public SplitApkMetadata getSplitApkMetadata();

        public SplitApkMetadataOrBuilder getSplitApkMetadataOrBuilder();

        public boolean hasStandaloneApkMetadata();

        public StandaloneApkMetadata getStandaloneApkMetadata();

        public StandaloneApkMetadataOrBuilder getStandaloneApkMetadataOrBuilder();

        public boolean hasInstantApkMetadata();

        public SplitApkMetadata getInstantApkMetadata();

        public SplitApkMetadataOrBuilder getInstantApkMetadataOrBuilder();

        public boolean hasSystemApkMetadata();

        public SystemApkMetadata getSystemApkMetadata();

        public SystemApkMetadataOrBuilder getSystemApkMetadataOrBuilder();

        public boolean hasAssetSliceMetadata();

        public SplitApkMetadata getAssetSliceMetadata();

        public SplitApkMetadataOrBuilder getAssetSliceMetadataOrBuilder();

        public boolean hasApexApkMetadata();

        public ApexApkMetadata getApexApkMetadata();

        public ApexApkMetadataOrBuilder getApexApkMetadataOrBuilder();

        public ApkDescription.ApkMetadataOneofValueCase getApkMetadataOneofValueCase();
    }

    public static final class InstantMetadata
    extends GeneratedMessageV3
    implements InstantMetadataOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int IS_INSTANT_FIELD_NUMBER = 1;
        private boolean isInstant_;
        public static final int DELIVERY_TYPE_FIELD_NUMBER = 3;
        private int deliveryType_;
        public static final int ON_DEMAND_DEPRECATED_FIELD_NUMBER = 2;
        private boolean onDemandDeprecated_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final InstantMetadata DEFAULT_INSTANCE = new InstantMetadata();
        private static final Parser<InstantMetadata> PARSER = new AbstractParser<InstantMetadata>(){

            @Override
            public InstantMetadata parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new InstantMetadata(input, extensionRegistry);
            }
        };

        private InstantMetadata(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private InstantMetadata() {
            this.isInstant_ = false;
            this.deliveryType_ = 0;
            this.onDemandDeprecated_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private InstantMetadata(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block12: while (!done) {
                    int rawValue;
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
                            this.isInstant_ = input.readBool();
                            continue block12;
                        }
                        case 16: {
                            this.onDemandDeprecated_ = input.readBool();
                            continue block12;
                        }
                        case 24: 
                    }
                    this.deliveryType_ = rawValue = input.readEnum();
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
            return internal_static_android_bundle_InstantMetadata_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_InstantMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(InstantMetadata.class, Builder.class);
        }

        @Override
        public boolean getIsInstant() {
            return this.isInstant_;
        }

        @Override
        public int getDeliveryTypeValue() {
            return this.deliveryType_;
        }

        @Override
        public DeliveryType getDeliveryType() {
            DeliveryType result = DeliveryType.valueOf(this.deliveryType_);
            return result == null ? DeliveryType.UNRECOGNIZED : result;
        }

        @Override
        @Deprecated
        public boolean getOnDemandDeprecated() {
            return this.onDemandDeprecated_;
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
            if (this.isInstant_) {
                output.writeBool(1, this.isInstant_);
            }
            if (this.onDemandDeprecated_) {
                output.writeBool(2, this.onDemandDeprecated_);
            }
            if (this.deliveryType_ != DeliveryType.UNKNOWN_DELIVERY_TYPE.getNumber()) {
                output.writeEnum(3, this.deliveryType_);
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
            if (this.isInstant_) {
                size += CodedOutputStream.computeBoolSize(1, this.isInstant_);
            }
            if (this.onDemandDeprecated_) {
                size += CodedOutputStream.computeBoolSize(2, this.onDemandDeprecated_);
            }
            if (this.deliveryType_ != DeliveryType.UNKNOWN_DELIVERY_TYPE.getNumber()) {
                size += CodedOutputStream.computeEnumSize(3, this.deliveryType_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof InstantMetadata)) {
                return super.equals(obj);
            }
            InstantMetadata other = (InstantMetadata)obj;
            boolean result = true;
            result = result && this.getIsInstant() == other.getIsInstant();
            result = result && this.deliveryType_ == other.deliveryType_;
            result = result && this.getOnDemandDeprecated() == other.getOnDemandDeprecated();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + InstantMetadata.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getIsInstant());
            hash = 37 * hash + 3;
            hash = 53 * hash + this.deliveryType_;
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashBoolean(this.getOnDemandDeprecated());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static InstantMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static InstantMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static InstantMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static InstantMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static InstantMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static InstantMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static InstantMetadata parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static InstantMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static InstantMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static InstantMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static InstantMetadata parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static InstantMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return InstantMetadata.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(InstantMetadata prototype) {
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

        public static InstantMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<InstantMetadata> parser() {
            return PARSER;
        }

        public Parser<InstantMetadata> getParserForType() {
            return PARSER;
        }

        @Override
        public InstantMetadata getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements InstantMetadataOrBuilder {
            private boolean isInstant_;
            private int deliveryType_ = 0;
            private boolean onDemandDeprecated_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_InstantMetadata_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_InstantMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(InstantMetadata.class, Builder.class);
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
                this.isInstant_ = false;
                this.deliveryType_ = 0;
                this.onDemandDeprecated_ = false;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_InstantMetadata_descriptor;
            }

            @Override
            public InstantMetadata getDefaultInstanceForType() {
                return InstantMetadata.getDefaultInstance();
            }

            @Override
            public InstantMetadata build() {
                InstantMetadata result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public InstantMetadata buildPartial() {
                InstantMetadata result = new InstantMetadata(this);
                result.isInstant_ = this.isInstant_;
                result.deliveryType_ = this.deliveryType_;
                result.onDemandDeprecated_ = this.onDemandDeprecated_;
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
                if (other instanceof InstantMetadata) {
                    return this.mergeFrom((InstantMetadata)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(InstantMetadata other) {
                if (other == InstantMetadata.getDefaultInstance()) {
                    return this;
                }
                if (other.getIsInstant()) {
                    this.setIsInstant(other.getIsInstant());
                }
                if (other.deliveryType_ != 0) {
                    this.setDeliveryTypeValue(other.getDeliveryTypeValue());
                }
                if (other.getOnDemandDeprecated()) {
                    this.setOnDemandDeprecated(other.getOnDemandDeprecated());
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
                InstantMetadata parsedMessage = null;
                try {
                    parsedMessage = (InstantMetadata)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (InstantMetadata)e2.getUnfinishedMessage();
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
            public boolean getIsInstant() {
                return this.isInstant_;
            }

            public Builder setIsInstant(boolean value) {
                this.isInstant_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearIsInstant() {
                this.isInstant_ = false;
                this.onChanged();
                return this;
            }

            @Override
            public int getDeliveryTypeValue() {
                return this.deliveryType_;
            }

            public Builder setDeliveryTypeValue(int value) {
                this.deliveryType_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public DeliveryType getDeliveryType() {
                DeliveryType result = DeliveryType.valueOf(this.deliveryType_);
                return result == null ? DeliveryType.UNRECOGNIZED : result;
            }

            public Builder setDeliveryType(DeliveryType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.deliveryType_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearDeliveryType() {
                this.deliveryType_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            @Deprecated
            public boolean getOnDemandDeprecated() {
                return this.onDemandDeprecated_;
            }

            @Deprecated
            public Builder setOnDemandDeprecated(boolean value) {
                this.onDemandDeprecated_ = value;
                this.onChanged();
                return this;
            }

            @Deprecated
            public Builder clearOnDemandDeprecated() {
                this.onDemandDeprecated_ = false;
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

    public static interface InstantMetadataOrBuilder
    extends MessageOrBuilder {
        public boolean getIsInstant();

        public int getDeliveryTypeValue();

        public DeliveryType getDeliveryType();

        @Deprecated
        public boolean getOnDemandDeprecated();
    }

    public static final class AssetModuleMetadata
    extends GeneratedMessageV3
    implements AssetModuleMetadataOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int NAME_FIELD_NUMBER = 1;
        private volatile Object name_;
        public static final int DELIVERY_TYPE_FIELD_NUMBER = 4;
        private int deliveryType_;
        public static final int INSTANT_METADATA_FIELD_NUMBER = 3;
        private InstantMetadata instantMetadata_;
        public static final int ON_DEMAND_DEPRECATED_FIELD_NUMBER = 2;
        private boolean onDemandDeprecated_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final AssetModuleMetadata DEFAULT_INSTANCE = new AssetModuleMetadata();
        private static final Parser<AssetModuleMetadata> PARSER = new AbstractParser<AssetModuleMetadata>(){

            @Override
            public AssetModuleMetadata parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AssetModuleMetadata(input, extensionRegistry);
            }
        };

        private AssetModuleMetadata(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private AssetModuleMetadata() {
            this.name_ = "";
            this.deliveryType_ = 0;
            this.onDemandDeprecated_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AssetModuleMetadata(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block13: while (!done) {
                    int rawValue;
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
                            String s3 = input.readStringRequireUtf8();
                            this.name_ = s3;
                            continue block13;
                        }
                        case 16: {
                            this.onDemandDeprecated_ = input.readBool();
                            continue block13;
                        }
                        case 26: {
                            InstantMetadata.Builder subBuilder = null;
                            if (this.instantMetadata_ != null) {
                                subBuilder = this.instantMetadata_.toBuilder();
                            }
                            this.instantMetadata_ = input.readMessage(InstantMetadata.parser(), extensionRegistry);
                            if (subBuilder == null) continue block13;
                            subBuilder.mergeFrom(this.instantMetadata_);
                            this.instantMetadata_ = subBuilder.buildPartial();
                            continue block13;
                        }
                        case 32: 
                    }
                    this.deliveryType_ = rawValue = input.readEnum();
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
            return internal_static_android_bundle_AssetModuleMetadata_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_AssetModuleMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(AssetModuleMetadata.class, Builder.class);
        }

        @Override
        public String getName() {
            Object ref = this.name_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.name_ = s3;
            return s3;
        }

        @Override
        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.name_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public int getDeliveryTypeValue() {
            return this.deliveryType_;
        }

        @Override
        public DeliveryType getDeliveryType() {
            DeliveryType result = DeliveryType.valueOf(this.deliveryType_);
            return result == null ? DeliveryType.UNRECOGNIZED : result;
        }

        @Override
        public boolean hasInstantMetadata() {
            return this.instantMetadata_ != null;
        }

        @Override
        public InstantMetadata getInstantMetadata() {
            return this.instantMetadata_ == null ? InstantMetadata.getDefaultInstance() : this.instantMetadata_;
        }

        @Override
        public InstantMetadataOrBuilder getInstantMetadataOrBuilder() {
            return this.getInstantMetadata();
        }

        @Override
        @Deprecated
        public boolean getOnDemandDeprecated() {
            return this.onDemandDeprecated_;
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
            if (!this.getNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.name_);
            }
            if (this.onDemandDeprecated_) {
                output.writeBool(2, this.onDemandDeprecated_);
            }
            if (this.instantMetadata_ != null) {
                output.writeMessage(3, this.getInstantMetadata());
            }
            if (this.deliveryType_ != DeliveryType.UNKNOWN_DELIVERY_TYPE.getNumber()) {
                output.writeEnum(4, this.deliveryType_);
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
            if (!this.getNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.name_);
            }
            if (this.onDemandDeprecated_) {
                size += CodedOutputStream.computeBoolSize(2, this.onDemandDeprecated_);
            }
            if (this.instantMetadata_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getInstantMetadata());
            }
            if (this.deliveryType_ != DeliveryType.UNKNOWN_DELIVERY_TYPE.getNumber()) {
                size += CodedOutputStream.computeEnumSize(4, this.deliveryType_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AssetModuleMetadata)) {
                return super.equals(obj);
            }
            AssetModuleMetadata other = (AssetModuleMetadata)obj;
            boolean result = true;
            result = result && this.getName().equals(other.getName());
            result = result && this.deliveryType_ == other.deliveryType_;
            boolean bl = result = result && this.hasInstantMetadata() == other.hasInstantMetadata();
            if (this.hasInstantMetadata()) {
                result = result && this.getInstantMetadata().equals(other.getInstantMetadata());
            }
            result = result && this.getOnDemandDeprecated() == other.getOnDemandDeprecated();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + AssetModuleMetadata.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getName().hashCode();
            hash = 37 * hash + 4;
            hash = 53 * hash + this.deliveryType_;
            if (this.hasInstantMetadata()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getInstantMetadata().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashBoolean(this.getOnDemandDeprecated());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static AssetModuleMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AssetModuleMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AssetModuleMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AssetModuleMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AssetModuleMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AssetModuleMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AssetModuleMetadata parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AssetModuleMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AssetModuleMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AssetModuleMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AssetModuleMetadata parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AssetModuleMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return AssetModuleMetadata.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AssetModuleMetadata prototype) {
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

        public static AssetModuleMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AssetModuleMetadata> parser() {
            return PARSER;
        }

        public Parser<AssetModuleMetadata> getParserForType() {
            return PARSER;
        }

        @Override
        public AssetModuleMetadata getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AssetModuleMetadataOrBuilder {
            private Object name_ = "";
            private int deliveryType_ = 0;
            private InstantMetadata instantMetadata_ = null;
            private SingleFieldBuilderV3<InstantMetadata, InstantMetadata.Builder, InstantMetadataOrBuilder> instantMetadataBuilder_;
            private boolean onDemandDeprecated_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_AssetModuleMetadata_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_AssetModuleMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(AssetModuleMetadata.class, Builder.class);
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
                this.name_ = "";
                this.deliveryType_ = 0;
                if (this.instantMetadataBuilder_ == null) {
                    this.instantMetadata_ = null;
                } else {
                    this.instantMetadata_ = null;
                    this.instantMetadataBuilder_ = null;
                }
                this.onDemandDeprecated_ = false;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_AssetModuleMetadata_descriptor;
            }

            @Override
            public AssetModuleMetadata getDefaultInstanceForType() {
                return AssetModuleMetadata.getDefaultInstance();
            }

            @Override
            public AssetModuleMetadata build() {
                AssetModuleMetadata result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public AssetModuleMetadata buildPartial() {
                AssetModuleMetadata result = new AssetModuleMetadata(this);
                result.name_ = this.name_;
                result.deliveryType_ = this.deliveryType_;
                if (this.instantMetadataBuilder_ == null) {
                    result.instantMetadata_ = this.instantMetadata_;
                } else {
                    result.instantMetadata_ = this.instantMetadataBuilder_.build();
                }
                result.onDemandDeprecated_ = this.onDemandDeprecated_;
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
                if (other instanceof AssetModuleMetadata) {
                    return this.mergeFrom((AssetModuleMetadata)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AssetModuleMetadata other) {
                if (other == AssetModuleMetadata.getDefaultInstance()) {
                    return this;
                }
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    this.onChanged();
                }
                if (other.deliveryType_ != 0) {
                    this.setDeliveryTypeValue(other.getDeliveryTypeValue());
                }
                if (other.hasInstantMetadata()) {
                    this.mergeInstantMetadata(other.getInstantMetadata());
                }
                if (other.getOnDemandDeprecated()) {
                    this.setOnDemandDeprecated(other.getOnDemandDeprecated());
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
                AssetModuleMetadata parsedMessage = null;
                try {
                    parsedMessage = (AssetModuleMetadata)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (AssetModuleMetadata)e2.getUnfinishedMessage();
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
            public String getName() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.name_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.name_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.name_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearName() {
                this.name_ = AssetModuleMetadata.getDefaultInstance().getName();
                this.onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                AssetModuleMetadata.checkByteStringIsUtf8(value);
                this.name_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public int getDeliveryTypeValue() {
                return this.deliveryType_;
            }

            public Builder setDeliveryTypeValue(int value) {
                this.deliveryType_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public DeliveryType getDeliveryType() {
                DeliveryType result = DeliveryType.valueOf(this.deliveryType_);
                return result == null ? DeliveryType.UNRECOGNIZED : result;
            }

            public Builder setDeliveryType(DeliveryType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.deliveryType_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearDeliveryType() {
                this.deliveryType_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasInstantMetadata() {
                return this.instantMetadataBuilder_ != null || this.instantMetadata_ != null;
            }

            @Override
            public InstantMetadata getInstantMetadata() {
                if (this.instantMetadataBuilder_ == null) {
                    return this.instantMetadata_ == null ? InstantMetadata.getDefaultInstance() : this.instantMetadata_;
                }
                return this.instantMetadataBuilder_.getMessage();
            }

            public Builder setInstantMetadata(InstantMetadata value) {
                if (this.instantMetadataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.instantMetadata_ = value;
                    this.onChanged();
                } else {
                    this.instantMetadataBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setInstantMetadata(InstantMetadata.Builder builderForValue) {
                if (this.instantMetadataBuilder_ == null) {
                    this.instantMetadata_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.instantMetadataBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeInstantMetadata(InstantMetadata value) {
                if (this.instantMetadataBuilder_ == null) {
                    this.instantMetadata_ = this.instantMetadata_ != null ? InstantMetadata.newBuilder(this.instantMetadata_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.instantMetadataBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearInstantMetadata() {
                if (this.instantMetadataBuilder_ == null) {
                    this.instantMetadata_ = null;
                    this.onChanged();
                } else {
                    this.instantMetadata_ = null;
                    this.instantMetadataBuilder_ = null;
                }
                return this;
            }

            public InstantMetadata.Builder getInstantMetadataBuilder() {
                this.onChanged();
                return this.getInstantMetadataFieldBuilder().getBuilder();
            }

            @Override
            public InstantMetadataOrBuilder getInstantMetadataOrBuilder() {
                if (this.instantMetadataBuilder_ != null) {
                    return this.instantMetadataBuilder_.getMessageOrBuilder();
                }
                return this.instantMetadata_ == null ? InstantMetadata.getDefaultInstance() : this.instantMetadata_;
            }

            private SingleFieldBuilderV3<InstantMetadata, InstantMetadata.Builder, InstantMetadataOrBuilder> getInstantMetadataFieldBuilder() {
                if (this.instantMetadataBuilder_ == null) {
                    this.instantMetadataBuilder_ = new SingleFieldBuilderV3(this.getInstantMetadata(), this.getParentForChildren(), this.isClean());
                    this.instantMetadata_ = null;
                }
                return this.instantMetadataBuilder_;
            }

            @Override
            @Deprecated
            public boolean getOnDemandDeprecated() {
                return this.onDemandDeprecated_;
            }

            @Deprecated
            public Builder setOnDemandDeprecated(boolean value) {
                this.onDemandDeprecated_ = value;
                this.onChanged();
                return this;
            }

            @Deprecated
            public Builder clearOnDemandDeprecated() {
                this.onDemandDeprecated_ = false;
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

    public static interface AssetModuleMetadataOrBuilder
    extends MessageOrBuilder {
        public String getName();

        public ByteString getNameBytes();

        public int getDeliveryTypeValue();

        public DeliveryType getDeliveryType();

        public boolean hasInstantMetadata();

        public InstantMetadata getInstantMetadata();

        public InstantMetadataOrBuilder getInstantMetadataOrBuilder();

        @Deprecated
        public boolean getOnDemandDeprecated();
    }

    public static final class AssetSliceSet
    extends GeneratedMessageV3
    implements AssetSliceSetOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int ASSET_MODULE_METADATA_FIELD_NUMBER = 1;
        private AssetModuleMetadata assetModuleMetadata_;
        public static final int APK_DESCRIPTION_FIELD_NUMBER = 2;
        private List<ApkDescription> apkDescription_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final AssetSliceSet DEFAULT_INSTANCE = new AssetSliceSet();
        private static final Parser<AssetSliceSet> PARSER = new AbstractParser<AssetSliceSet>(){

            @Override
            public AssetSliceSet parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AssetSliceSet(input, extensionRegistry);
            }
        };

        private AssetSliceSet(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private AssetSliceSet() {
            this.apkDescription_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AssetSliceSet(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
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
                            AssetModuleMetadata.Builder subBuilder = null;
                            if (this.assetModuleMetadata_ != null) {
                                subBuilder = this.assetModuleMetadata_.toBuilder();
                            }
                            this.assetModuleMetadata_ = input.readMessage(AssetModuleMetadata.parser(), extensionRegistry);
                            if (subBuilder == null) continue block11;
                            subBuilder.mergeFrom(this.assetModuleMetadata_);
                            this.assetModuleMetadata_ = subBuilder.buildPartial();
                            continue block11;
                        }
                        case 18: 
                    }
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.apkDescription_ = new ArrayList<ApkDescription>();
                        mutable_bitField0_ |= 2;
                    }
                    this.apkDescription_.add(input.readMessage(ApkDescription.parser(), extensionRegistry));
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
                    this.apkDescription_ = Collections.unmodifiableList(this.apkDescription_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_AssetSliceSet_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_AssetSliceSet_fieldAccessorTable.ensureFieldAccessorsInitialized(AssetSliceSet.class, Builder.class);
        }

        @Override
        public boolean hasAssetModuleMetadata() {
            return this.assetModuleMetadata_ != null;
        }

        @Override
        public AssetModuleMetadata getAssetModuleMetadata() {
            return this.assetModuleMetadata_ == null ? AssetModuleMetadata.getDefaultInstance() : this.assetModuleMetadata_;
        }

        @Override
        public AssetModuleMetadataOrBuilder getAssetModuleMetadataOrBuilder() {
            return this.getAssetModuleMetadata();
        }

        @Override
        public List<ApkDescription> getApkDescriptionList() {
            return this.apkDescription_;
        }

        @Override
        public List<? extends ApkDescriptionOrBuilder> getApkDescriptionOrBuilderList() {
            return this.apkDescription_;
        }

        @Override
        public int getApkDescriptionCount() {
            return this.apkDescription_.size();
        }

        @Override
        public ApkDescription getApkDescription(int index) {
            return this.apkDescription_.get(index);
        }

        @Override
        public ApkDescriptionOrBuilder getApkDescriptionOrBuilder(int index) {
            return this.apkDescription_.get(index);
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
            if (this.assetModuleMetadata_ != null) {
                output.writeMessage(1, this.getAssetModuleMetadata());
            }
            for (int i2 = 0; i2 < this.apkDescription_.size(); ++i2) {
                output.writeMessage(2, this.apkDescription_.get(i2));
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
            if (this.assetModuleMetadata_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getAssetModuleMetadata());
            }
            for (int i2 = 0; i2 < this.apkDescription_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.apkDescription_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AssetSliceSet)) {
                return super.equals(obj);
            }
            AssetSliceSet other = (AssetSliceSet)obj;
            boolean result = true;
            boolean bl = result = result && this.hasAssetModuleMetadata() == other.hasAssetModuleMetadata();
            if (this.hasAssetModuleMetadata()) {
                result = result && this.getAssetModuleMetadata().equals(other.getAssetModuleMetadata());
            }
            result = result && this.getApkDescriptionList().equals(other.getApkDescriptionList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + AssetSliceSet.getDescriptor().hashCode();
            if (this.hasAssetModuleMetadata()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getAssetModuleMetadata().hashCode();
            }
            if (this.getApkDescriptionCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getApkDescriptionList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static AssetSliceSet parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AssetSliceSet parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AssetSliceSet parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AssetSliceSet parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AssetSliceSet parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AssetSliceSet parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AssetSliceSet parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AssetSliceSet parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AssetSliceSet parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AssetSliceSet parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AssetSliceSet parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AssetSliceSet parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return AssetSliceSet.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AssetSliceSet prototype) {
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

        public static AssetSliceSet getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AssetSliceSet> parser() {
            return PARSER;
        }

        public Parser<AssetSliceSet> getParserForType() {
            return PARSER;
        }

        @Override
        public AssetSliceSet getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AssetSliceSetOrBuilder {
            private int bitField0_;
            private AssetModuleMetadata assetModuleMetadata_ = null;
            private SingleFieldBuilderV3<AssetModuleMetadata, AssetModuleMetadata.Builder, AssetModuleMetadataOrBuilder> assetModuleMetadataBuilder_;
            private List<ApkDescription> apkDescription_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<ApkDescription, ApkDescription.Builder, ApkDescriptionOrBuilder> apkDescriptionBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_AssetSliceSet_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_AssetSliceSet_fieldAccessorTable.ensureFieldAccessorsInitialized(AssetSliceSet.class, Builder.class);
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
                    this.getApkDescriptionFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.assetModuleMetadataBuilder_ == null) {
                    this.assetModuleMetadata_ = null;
                } else {
                    this.assetModuleMetadata_ = null;
                    this.assetModuleMetadataBuilder_ = null;
                }
                if (this.apkDescriptionBuilder_ == null) {
                    this.apkDescription_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.apkDescriptionBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_AssetSliceSet_descriptor;
            }

            @Override
            public AssetSliceSet getDefaultInstanceForType() {
                return AssetSliceSet.getDefaultInstance();
            }

            @Override
            public AssetSliceSet build() {
                AssetSliceSet result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public AssetSliceSet buildPartial() {
                AssetSliceSet result = new AssetSliceSet(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if (this.assetModuleMetadataBuilder_ == null) {
                    result.assetModuleMetadata_ = this.assetModuleMetadata_;
                } else {
                    result.assetModuleMetadata_ = this.assetModuleMetadataBuilder_.build();
                }
                if (this.apkDescriptionBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.apkDescription_ = Collections.unmodifiableList(this.apkDescription_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.apkDescription_ = this.apkDescription_;
                } else {
                    result.apkDescription_ = this.apkDescriptionBuilder_.build();
                }
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
                if (other instanceof AssetSliceSet) {
                    return this.mergeFrom((AssetSliceSet)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AssetSliceSet other) {
                if (other == AssetSliceSet.getDefaultInstance()) {
                    return this;
                }
                if (other.hasAssetModuleMetadata()) {
                    this.mergeAssetModuleMetadata(other.getAssetModuleMetadata());
                }
                if (this.apkDescriptionBuilder_ == null) {
                    if (!other.apkDescription_.isEmpty()) {
                        if (this.apkDescription_.isEmpty()) {
                            this.apkDescription_ = other.apkDescription_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureApkDescriptionIsMutable();
                            this.apkDescription_.addAll(other.apkDescription_);
                        }
                        this.onChanged();
                    }
                } else if (!other.apkDescription_.isEmpty()) {
                    if (this.apkDescriptionBuilder_.isEmpty()) {
                        this.apkDescriptionBuilder_.dispose();
                        this.apkDescriptionBuilder_ = null;
                        this.apkDescription_ = other.apkDescription_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.apkDescriptionBuilder_ = alwaysUseFieldBuilders ? this.getApkDescriptionFieldBuilder() : null;
                    } else {
                        this.apkDescriptionBuilder_.addAllMessages(other.apkDescription_);
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
                AssetSliceSet parsedMessage = null;
                try {
                    parsedMessage = (AssetSliceSet)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (AssetSliceSet)e2.getUnfinishedMessage();
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
            public boolean hasAssetModuleMetadata() {
                return this.assetModuleMetadataBuilder_ != null || this.assetModuleMetadata_ != null;
            }

            @Override
            public AssetModuleMetadata getAssetModuleMetadata() {
                if (this.assetModuleMetadataBuilder_ == null) {
                    return this.assetModuleMetadata_ == null ? AssetModuleMetadata.getDefaultInstance() : this.assetModuleMetadata_;
                }
                return this.assetModuleMetadataBuilder_.getMessage();
            }

            public Builder setAssetModuleMetadata(AssetModuleMetadata value) {
                if (this.assetModuleMetadataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.assetModuleMetadata_ = value;
                    this.onChanged();
                } else {
                    this.assetModuleMetadataBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setAssetModuleMetadata(AssetModuleMetadata.Builder builderForValue) {
                if (this.assetModuleMetadataBuilder_ == null) {
                    this.assetModuleMetadata_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.assetModuleMetadataBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeAssetModuleMetadata(AssetModuleMetadata value) {
                if (this.assetModuleMetadataBuilder_ == null) {
                    this.assetModuleMetadata_ = this.assetModuleMetadata_ != null ? AssetModuleMetadata.newBuilder(this.assetModuleMetadata_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.assetModuleMetadataBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearAssetModuleMetadata() {
                if (this.assetModuleMetadataBuilder_ == null) {
                    this.assetModuleMetadata_ = null;
                    this.onChanged();
                } else {
                    this.assetModuleMetadata_ = null;
                    this.assetModuleMetadataBuilder_ = null;
                }
                return this;
            }

            public AssetModuleMetadata.Builder getAssetModuleMetadataBuilder() {
                this.onChanged();
                return this.getAssetModuleMetadataFieldBuilder().getBuilder();
            }

            @Override
            public AssetModuleMetadataOrBuilder getAssetModuleMetadataOrBuilder() {
                if (this.assetModuleMetadataBuilder_ != null) {
                    return this.assetModuleMetadataBuilder_.getMessageOrBuilder();
                }
                return this.assetModuleMetadata_ == null ? AssetModuleMetadata.getDefaultInstance() : this.assetModuleMetadata_;
            }

            private SingleFieldBuilderV3<AssetModuleMetadata, AssetModuleMetadata.Builder, AssetModuleMetadataOrBuilder> getAssetModuleMetadataFieldBuilder() {
                if (this.assetModuleMetadataBuilder_ == null) {
                    this.assetModuleMetadataBuilder_ = new SingleFieldBuilderV3(this.getAssetModuleMetadata(), this.getParentForChildren(), this.isClean());
                    this.assetModuleMetadata_ = null;
                }
                return this.assetModuleMetadataBuilder_;
            }

            private void ensureApkDescriptionIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.apkDescription_ = new ArrayList<ApkDescription>(this.apkDescription_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<ApkDescription> getApkDescriptionList() {
                if (this.apkDescriptionBuilder_ == null) {
                    return Collections.unmodifiableList(this.apkDescription_);
                }
                return this.apkDescriptionBuilder_.getMessageList();
            }

            @Override
            public int getApkDescriptionCount() {
                if (this.apkDescriptionBuilder_ == null) {
                    return this.apkDescription_.size();
                }
                return this.apkDescriptionBuilder_.getCount();
            }

            @Override
            public ApkDescription getApkDescription(int index) {
                if (this.apkDescriptionBuilder_ == null) {
                    return this.apkDescription_.get(index);
                }
                return this.apkDescriptionBuilder_.getMessage(index);
            }

            public Builder setApkDescription(int index, ApkDescription value) {
                if (this.apkDescriptionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.set(index, value);
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setApkDescription(int index, ApkDescription.Builder builderForValue) {
                if (this.apkDescriptionBuilder_ == null) {
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addApkDescription(ApkDescription value) {
                if (this.apkDescriptionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.add(value);
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addApkDescription(int index, ApkDescription value) {
                if (this.apkDescriptionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.add(index, value);
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addApkDescription(ApkDescription.Builder builderForValue) {
                if (this.apkDescriptionBuilder_ == null) {
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addApkDescription(int index, ApkDescription.Builder builderForValue) {
                if (this.apkDescriptionBuilder_ == null) {
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllApkDescription(Iterable<? extends ApkDescription> values2) {
                if (this.apkDescriptionBuilder_ == null) {
                    this.ensureApkDescriptionIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.apkDescription_);
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearApkDescription() {
                if (this.apkDescriptionBuilder_ == null) {
                    this.apkDescription_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.clear();
                }
                return this;
            }

            public Builder removeApkDescription(int index) {
                if (this.apkDescriptionBuilder_ == null) {
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.remove(index);
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.remove(index);
                }
                return this;
            }

            public ApkDescription.Builder getApkDescriptionBuilder(int index) {
                return this.getApkDescriptionFieldBuilder().getBuilder(index);
            }

            @Override
            public ApkDescriptionOrBuilder getApkDescriptionOrBuilder(int index) {
                if (this.apkDescriptionBuilder_ == null) {
                    return this.apkDescription_.get(index);
                }
                return this.apkDescriptionBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends ApkDescriptionOrBuilder> getApkDescriptionOrBuilderList() {
                if (this.apkDescriptionBuilder_ != null) {
                    return this.apkDescriptionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.apkDescription_);
            }

            public ApkDescription.Builder addApkDescriptionBuilder() {
                return this.getApkDescriptionFieldBuilder().addBuilder(ApkDescription.getDefaultInstance());
            }

            public ApkDescription.Builder addApkDescriptionBuilder(int index) {
                return this.getApkDescriptionFieldBuilder().addBuilder(index, ApkDescription.getDefaultInstance());
            }

            public List<ApkDescription.Builder> getApkDescriptionBuilderList() {
                return this.getApkDescriptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<ApkDescription, ApkDescription.Builder, ApkDescriptionOrBuilder> getApkDescriptionFieldBuilder() {
                if (this.apkDescriptionBuilder_ == null) {
                    this.apkDescriptionBuilder_ = new RepeatedFieldBuilderV3(this.apkDescription_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.apkDescription_ = null;
                }
                return this.apkDescriptionBuilder_;
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

    public static interface AssetSliceSetOrBuilder
    extends MessageOrBuilder {
        public boolean hasAssetModuleMetadata();

        public AssetModuleMetadata getAssetModuleMetadata();

        public AssetModuleMetadataOrBuilder getAssetModuleMetadataOrBuilder();

        public List<ApkDescription> getApkDescriptionList();

        public ApkDescription getApkDescription(int var1);

        public int getApkDescriptionCount();

        public List<? extends ApkDescriptionOrBuilder> getApkDescriptionOrBuilderList();

        public ApkDescriptionOrBuilder getApkDescriptionOrBuilder(int var1);
    }

    public static final class ModuleMetadata
    extends GeneratedMessageV3
    implements ModuleMetadataOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int NAME_FIELD_NUMBER = 1;
        private volatile Object name_;
        public static final int DELIVERY_TYPE_FIELD_NUMBER = 6;
        private int deliveryType_;
        public static final int IS_INSTANT_FIELD_NUMBER = 3;
        private boolean isInstant_;
        public static final int DEPENDENCIES_FIELD_NUMBER = 4;
        private LazyStringList dependencies_;
        public static final int TARGETING_FIELD_NUMBER = 5;
        private Targeting.ModuleTargeting targeting_;
        public static final int ON_DEMAND_DEPRECATED_FIELD_NUMBER = 2;
        private boolean onDemandDeprecated_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ModuleMetadata DEFAULT_INSTANCE = new ModuleMetadata();
        private static final Parser<ModuleMetadata> PARSER = new AbstractParser<ModuleMetadata>(){

            @Override
            public ModuleMetadata parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ModuleMetadata(input, extensionRegistry);
            }
        };

        private ModuleMetadata(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ModuleMetadata() {
            this.name_ = "";
            this.deliveryType_ = 0;
            this.isInstant_ = false;
            this.dependencies_ = LazyStringArrayList.EMPTY;
            this.onDemandDeprecated_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ModuleMetadata(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block15: while (!done) {
                    int rawValue;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block15;
                        }
                        default: {
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block15;
                            done = true;
                            continue block15;
                        }
                        case 10: {
                            String s3 = input.readStringRequireUtf8();
                            this.name_ = s3;
                            continue block15;
                        }
                        case 16: {
                            this.onDemandDeprecated_ = input.readBool();
                            continue block15;
                        }
                        case 24: {
                            this.isInstant_ = input.readBool();
                            continue block15;
                        }
                        case 34: {
                            String s4 = input.readStringRequireUtf8();
                            if ((mutable_bitField0_ & 8) != 8) {
                                this.dependencies_ = new LazyStringArrayList();
                                mutable_bitField0_ |= 8;
                            }
                            this.dependencies_.add(s4);
                            continue block15;
                        }
                        case 42: {
                            Targeting.ModuleTargeting.Builder subBuilder = null;
                            if (this.targeting_ != null) {
                                subBuilder = this.targeting_.toBuilder();
                            }
                            this.targeting_ = input.readMessage(Targeting.ModuleTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block15;
                            subBuilder.mergeFrom(this.targeting_);
                            this.targeting_ = subBuilder.buildPartial();
                            continue block15;
                        }
                        case 48: 
                    }
                    this.deliveryType_ = rawValue = input.readEnum();
                }
            }
            catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(this);
            }
            catch (IOException e3) {
                throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 8) == 8) {
                    this.dependencies_ = this.dependencies_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_ModuleMetadata_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ModuleMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(ModuleMetadata.class, Builder.class);
        }

        @Override
        public String getName() {
            Object ref = this.name_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.name_ = s3;
            return s3;
        }

        @Override
        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.name_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public int getDeliveryTypeValue() {
            return this.deliveryType_;
        }

        @Override
        public DeliveryType getDeliveryType() {
            DeliveryType result = DeliveryType.valueOf(this.deliveryType_);
            return result == null ? DeliveryType.UNRECOGNIZED : result;
        }

        @Override
        public boolean getIsInstant() {
            return this.isInstant_;
        }

        public ProtocolStringList getDependenciesList() {
            return this.dependencies_;
        }

        @Override
        public int getDependenciesCount() {
            return this.dependencies_.size();
        }

        @Override
        public String getDependencies(int index) {
            return (String)this.dependencies_.get(index);
        }

        @Override
        public ByteString getDependenciesBytes(int index) {
            return this.dependencies_.getByteString(index);
        }

        @Override
        public boolean hasTargeting() {
            return this.targeting_ != null;
        }

        @Override
        public Targeting.ModuleTargeting getTargeting() {
            return this.targeting_ == null ? Targeting.ModuleTargeting.getDefaultInstance() : this.targeting_;
        }

        @Override
        public Targeting.ModuleTargetingOrBuilder getTargetingOrBuilder() {
            return this.getTargeting();
        }

        @Override
        @Deprecated
        public boolean getOnDemandDeprecated() {
            return this.onDemandDeprecated_;
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
            if (!this.getNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.name_);
            }
            if (this.onDemandDeprecated_) {
                output.writeBool(2, this.onDemandDeprecated_);
            }
            if (this.isInstant_) {
                output.writeBool(3, this.isInstant_);
            }
            for (int i2 = 0; i2 < this.dependencies_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 4, this.dependencies_.getRaw(i2));
            }
            if (this.targeting_ != null) {
                output.writeMessage(5, this.getTargeting());
            }
            if (this.deliveryType_ != DeliveryType.UNKNOWN_DELIVERY_TYPE.getNumber()) {
                output.writeEnum(6, this.deliveryType_);
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
            if (!this.getNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.name_);
            }
            if (this.onDemandDeprecated_) {
                size += CodedOutputStream.computeBoolSize(2, this.onDemandDeprecated_);
            }
            if (this.isInstant_) {
                size += CodedOutputStream.computeBoolSize(3, this.isInstant_);
            }
            int dataSize = 0;
            for (int i2 = 0; i2 < this.dependencies_.size(); ++i2) {
                dataSize += ModuleMetadata.computeStringSizeNoTag(this.dependencies_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getDependenciesList().size();
            if (this.targeting_ != null) {
                size += CodedOutputStream.computeMessageSize(5, this.getTargeting());
            }
            if (this.deliveryType_ != DeliveryType.UNKNOWN_DELIVERY_TYPE.getNumber()) {
                size += CodedOutputStream.computeEnumSize(6, this.deliveryType_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ModuleMetadata)) {
                return super.equals(obj);
            }
            ModuleMetadata other = (ModuleMetadata)obj;
            boolean result = true;
            result = result && this.getName().equals(other.getName());
            result = result && this.deliveryType_ == other.deliveryType_;
            result = result && this.getIsInstant() == other.getIsInstant();
            result = result && this.getDependenciesList().equals(other.getDependenciesList());
            boolean bl = result = result && this.hasTargeting() == other.hasTargeting();
            if (this.hasTargeting()) {
                result = result && this.getTargeting().equals(other.getTargeting());
            }
            result = result && this.getOnDemandDeprecated() == other.getOnDemandDeprecated();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ModuleMetadata.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getName().hashCode();
            hash = 37 * hash + 6;
            hash = 53 * hash + this.deliveryType_;
            hash = 37 * hash + 3;
            hash = 53 * hash + Internal.hashBoolean(this.getIsInstant());
            if (this.getDependenciesCount() > 0) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getDependenciesList().hashCode();
            }
            if (this.hasTargeting()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getTargeting().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashBoolean(this.getOnDemandDeprecated());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ModuleMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ModuleMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ModuleMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ModuleMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ModuleMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ModuleMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ModuleMetadata parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ModuleMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ModuleMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ModuleMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ModuleMetadata parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ModuleMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ModuleMetadata.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ModuleMetadata prototype) {
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

        public static ModuleMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ModuleMetadata> parser() {
            return PARSER;
        }

        public Parser<ModuleMetadata> getParserForType() {
            return PARSER;
        }

        @Override
        public ModuleMetadata getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ModuleMetadataOrBuilder {
            private int bitField0_;
            private Object name_ = "";
            private int deliveryType_ = 0;
            private boolean isInstant_;
            private LazyStringList dependencies_ = LazyStringArrayList.EMPTY;
            private Targeting.ModuleTargeting targeting_ = null;
            private SingleFieldBuilderV3<Targeting.ModuleTargeting, Targeting.ModuleTargeting.Builder, Targeting.ModuleTargetingOrBuilder> targetingBuilder_;
            private boolean onDemandDeprecated_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ModuleMetadata_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ModuleMetadata_fieldAccessorTable.ensureFieldAccessorsInitialized(ModuleMetadata.class, Builder.class);
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
                this.name_ = "";
                this.deliveryType_ = 0;
                this.isInstant_ = false;
                this.dependencies_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFF7;
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = null;
                } else {
                    this.targeting_ = null;
                    this.targetingBuilder_ = null;
                }
                this.onDemandDeprecated_ = false;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ModuleMetadata_descriptor;
            }

            @Override
            public ModuleMetadata getDefaultInstanceForType() {
                return ModuleMetadata.getDefaultInstance();
            }

            @Override
            public ModuleMetadata build() {
                ModuleMetadata result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ModuleMetadata buildPartial() {
                ModuleMetadata result = new ModuleMetadata(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                result.name_ = this.name_;
                result.deliveryType_ = this.deliveryType_;
                result.isInstant_ = this.isInstant_;
                if ((this.bitField0_ & 8) == 8) {
                    this.dependencies_ = this.dependencies_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFF7;
                }
                result.dependencies_ = this.dependencies_;
                if (this.targetingBuilder_ == null) {
                    result.targeting_ = this.targeting_;
                } else {
                    result.targeting_ = this.targetingBuilder_.build();
                }
                result.onDemandDeprecated_ = this.onDemandDeprecated_;
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
                if (other instanceof ModuleMetadata) {
                    return this.mergeFrom((ModuleMetadata)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ModuleMetadata other) {
                if (other == ModuleMetadata.getDefaultInstance()) {
                    return this;
                }
                if (!other.getName().isEmpty()) {
                    this.name_ = other.name_;
                    this.onChanged();
                }
                if (other.deliveryType_ != 0) {
                    this.setDeliveryTypeValue(other.getDeliveryTypeValue());
                }
                if (other.getIsInstant()) {
                    this.setIsInstant(other.getIsInstant());
                }
                if (!other.dependencies_.isEmpty()) {
                    if (this.dependencies_.isEmpty()) {
                        this.dependencies_ = other.dependencies_;
                        this.bitField0_ &= 0xFFFFFFF7;
                    } else {
                        this.ensureDependenciesIsMutable();
                        this.dependencies_.addAll(other.dependencies_);
                    }
                    this.onChanged();
                }
                if (other.hasTargeting()) {
                    this.mergeTargeting(other.getTargeting());
                }
                if (other.getOnDemandDeprecated()) {
                    this.setOnDemandDeprecated(other.getOnDemandDeprecated());
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
                ModuleMetadata parsedMessage = null;
                try {
                    parsedMessage = (ModuleMetadata)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ModuleMetadata)e2.getUnfinishedMessage();
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
            public String getName() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.name_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.name_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.name_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearName() {
                this.name_ = ModuleMetadata.getDefaultInstance().getName();
                this.onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ModuleMetadata.checkByteStringIsUtf8(value);
                this.name_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public int getDeliveryTypeValue() {
                return this.deliveryType_;
            }

            public Builder setDeliveryTypeValue(int value) {
                this.deliveryType_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public DeliveryType getDeliveryType() {
                DeliveryType result = DeliveryType.valueOf(this.deliveryType_);
                return result == null ? DeliveryType.UNRECOGNIZED : result;
            }

            public Builder setDeliveryType(DeliveryType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.deliveryType_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearDeliveryType() {
                this.deliveryType_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean getIsInstant() {
                return this.isInstant_;
            }

            public Builder setIsInstant(boolean value) {
                this.isInstant_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearIsInstant() {
                this.isInstant_ = false;
                this.onChanged();
                return this;
            }

            private void ensureDependenciesIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.dependencies_ = new LazyStringArrayList(this.dependencies_);
                    this.bitField0_ |= 8;
                }
            }

            public ProtocolStringList getDependenciesList() {
                return this.dependencies_.getUnmodifiableView();
            }

            @Override
            public int getDependenciesCount() {
                return this.dependencies_.size();
            }

            @Override
            public String getDependencies(int index) {
                return (String)this.dependencies_.get(index);
            }

            @Override
            public ByteString getDependenciesBytes(int index) {
                return this.dependencies_.getByteString(index);
            }

            public Builder setDependencies(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureDependenciesIsMutable();
                this.dependencies_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addDependencies(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureDependenciesIsMutable();
                this.dependencies_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllDependencies(Iterable<String> values2) {
                this.ensureDependenciesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.dependencies_);
                this.onChanged();
                return this;
            }

            public Builder clearDependencies() {
                this.dependencies_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFF7;
                this.onChanged();
                return this;
            }

            public Builder addDependenciesBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ModuleMetadata.checkByteStringIsUtf8(value);
                this.ensureDependenciesIsMutable();
                this.dependencies_.add(value);
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasTargeting() {
                return this.targetingBuilder_ != null || this.targeting_ != null;
            }

            @Override
            public Targeting.ModuleTargeting getTargeting() {
                if (this.targetingBuilder_ == null) {
                    return this.targeting_ == null ? Targeting.ModuleTargeting.getDefaultInstance() : this.targeting_;
                }
                return this.targetingBuilder_.getMessage();
            }

            public Builder setTargeting(Targeting.ModuleTargeting value) {
                if (this.targetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.targeting_ = value;
                    this.onChanged();
                } else {
                    this.targetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setTargeting(Targeting.ModuleTargeting.Builder builderForValue) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.targetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTargeting(Targeting.ModuleTargeting value) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = this.targeting_ != null ? Targeting.ModuleTargeting.newBuilder(this.targeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.targetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearTargeting() {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = null;
                    this.onChanged();
                } else {
                    this.targeting_ = null;
                    this.targetingBuilder_ = null;
                }
                return this;
            }

            public Targeting.ModuleTargeting.Builder getTargetingBuilder() {
                this.onChanged();
                return this.getTargetingFieldBuilder().getBuilder();
            }

            @Override
            public Targeting.ModuleTargetingOrBuilder getTargetingOrBuilder() {
                if (this.targetingBuilder_ != null) {
                    return this.targetingBuilder_.getMessageOrBuilder();
                }
                return this.targeting_ == null ? Targeting.ModuleTargeting.getDefaultInstance() : this.targeting_;
            }

            private SingleFieldBuilderV3<Targeting.ModuleTargeting, Targeting.ModuleTargeting.Builder, Targeting.ModuleTargetingOrBuilder> getTargetingFieldBuilder() {
                if (this.targetingBuilder_ == null) {
                    this.targetingBuilder_ = new SingleFieldBuilderV3(this.getTargeting(), this.getParentForChildren(), this.isClean());
                    this.targeting_ = null;
                }
                return this.targetingBuilder_;
            }

            @Override
            @Deprecated
            public boolean getOnDemandDeprecated() {
                return this.onDemandDeprecated_;
            }

            @Deprecated
            public Builder setOnDemandDeprecated(boolean value) {
                this.onDemandDeprecated_ = value;
                this.onChanged();
                return this;
            }

            @Deprecated
            public Builder clearOnDemandDeprecated() {
                this.onDemandDeprecated_ = false;
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

    public static interface ModuleMetadataOrBuilder
    extends MessageOrBuilder {
        public String getName();

        public ByteString getNameBytes();

        public int getDeliveryTypeValue();

        public DeliveryType getDeliveryType();

        public boolean getIsInstant();

        public List<String> getDependenciesList();

        public int getDependenciesCount();

        public String getDependencies(int var1);

        public ByteString getDependenciesBytes(int var1);

        public boolean hasTargeting();

        public Targeting.ModuleTargeting getTargeting();

        public Targeting.ModuleTargetingOrBuilder getTargetingOrBuilder();

        @Deprecated
        public boolean getOnDemandDeprecated();
    }

    public static final class ApkSet
    extends GeneratedMessageV3
    implements ApkSetOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int MODULE_METADATA_FIELD_NUMBER = 1;
        private ModuleMetadata moduleMetadata_;
        public static final int APK_DESCRIPTION_FIELD_NUMBER = 2;
        private List<ApkDescription> apkDescription_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ApkSet DEFAULT_INSTANCE = new ApkSet();
        private static final Parser<ApkSet> PARSER = new AbstractParser<ApkSet>(){

            @Override
            public ApkSet parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ApkSet(input, extensionRegistry);
            }
        };

        private ApkSet(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ApkSet() {
            this.apkDescription_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ApkSet(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
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
                            ModuleMetadata.Builder subBuilder = null;
                            if (this.moduleMetadata_ != null) {
                                subBuilder = this.moduleMetadata_.toBuilder();
                            }
                            this.moduleMetadata_ = input.readMessage(ModuleMetadata.parser(), extensionRegistry);
                            if (subBuilder == null) continue block11;
                            subBuilder.mergeFrom(this.moduleMetadata_);
                            this.moduleMetadata_ = subBuilder.buildPartial();
                            continue block11;
                        }
                        case 18: 
                    }
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.apkDescription_ = new ArrayList<ApkDescription>();
                        mutable_bitField0_ |= 2;
                    }
                    this.apkDescription_.add(input.readMessage(ApkDescription.parser(), extensionRegistry));
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
                    this.apkDescription_ = Collections.unmodifiableList(this.apkDescription_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_ApkSet_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ApkSet_fieldAccessorTable.ensureFieldAccessorsInitialized(ApkSet.class, Builder.class);
        }

        @Override
        public boolean hasModuleMetadata() {
            return this.moduleMetadata_ != null;
        }

        @Override
        public ModuleMetadata getModuleMetadata() {
            return this.moduleMetadata_ == null ? ModuleMetadata.getDefaultInstance() : this.moduleMetadata_;
        }

        @Override
        public ModuleMetadataOrBuilder getModuleMetadataOrBuilder() {
            return this.getModuleMetadata();
        }

        @Override
        public List<ApkDescription> getApkDescriptionList() {
            return this.apkDescription_;
        }

        @Override
        public List<? extends ApkDescriptionOrBuilder> getApkDescriptionOrBuilderList() {
            return this.apkDescription_;
        }

        @Override
        public int getApkDescriptionCount() {
            return this.apkDescription_.size();
        }

        @Override
        public ApkDescription getApkDescription(int index) {
            return this.apkDescription_.get(index);
        }

        @Override
        public ApkDescriptionOrBuilder getApkDescriptionOrBuilder(int index) {
            return this.apkDescription_.get(index);
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
            if (this.moduleMetadata_ != null) {
                output.writeMessage(1, this.getModuleMetadata());
            }
            for (int i2 = 0; i2 < this.apkDescription_.size(); ++i2) {
                output.writeMessage(2, this.apkDescription_.get(i2));
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
            if (this.moduleMetadata_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getModuleMetadata());
            }
            for (int i2 = 0; i2 < this.apkDescription_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.apkDescription_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ApkSet)) {
                return super.equals(obj);
            }
            ApkSet other = (ApkSet)obj;
            boolean result = true;
            boolean bl = result = result && this.hasModuleMetadata() == other.hasModuleMetadata();
            if (this.hasModuleMetadata()) {
                result = result && this.getModuleMetadata().equals(other.getModuleMetadata());
            }
            result = result && this.getApkDescriptionList().equals(other.getApkDescriptionList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ApkSet.getDescriptor().hashCode();
            if (this.hasModuleMetadata()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getModuleMetadata().hashCode();
            }
            if (this.getApkDescriptionCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getApkDescriptionList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ApkSet parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApkSet parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApkSet parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApkSet parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApkSet parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApkSet parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApkSet parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApkSet parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApkSet parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ApkSet parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApkSet parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApkSet parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ApkSet.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ApkSet prototype) {
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

        public static ApkSet getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ApkSet> parser() {
            return PARSER;
        }

        public Parser<ApkSet> getParserForType() {
            return PARSER;
        }

        @Override
        public ApkSet getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ApkSetOrBuilder {
            private int bitField0_;
            private ModuleMetadata moduleMetadata_ = null;
            private SingleFieldBuilderV3<ModuleMetadata, ModuleMetadata.Builder, ModuleMetadataOrBuilder> moduleMetadataBuilder_;
            private List<ApkDescription> apkDescription_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<ApkDescription, ApkDescription.Builder, ApkDescriptionOrBuilder> apkDescriptionBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ApkSet_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ApkSet_fieldAccessorTable.ensureFieldAccessorsInitialized(ApkSet.class, Builder.class);
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
                    this.getApkDescriptionFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.moduleMetadataBuilder_ == null) {
                    this.moduleMetadata_ = null;
                } else {
                    this.moduleMetadata_ = null;
                    this.moduleMetadataBuilder_ = null;
                }
                if (this.apkDescriptionBuilder_ == null) {
                    this.apkDescription_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.apkDescriptionBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ApkSet_descriptor;
            }

            @Override
            public ApkSet getDefaultInstanceForType() {
                return ApkSet.getDefaultInstance();
            }

            @Override
            public ApkSet build() {
                ApkSet result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ApkSet buildPartial() {
                ApkSet result = new ApkSet(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if (this.moduleMetadataBuilder_ == null) {
                    result.moduleMetadata_ = this.moduleMetadata_;
                } else {
                    result.moduleMetadata_ = this.moduleMetadataBuilder_.build();
                }
                if (this.apkDescriptionBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.apkDescription_ = Collections.unmodifiableList(this.apkDescription_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.apkDescription_ = this.apkDescription_;
                } else {
                    result.apkDescription_ = this.apkDescriptionBuilder_.build();
                }
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
                if (other instanceof ApkSet) {
                    return this.mergeFrom((ApkSet)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ApkSet other) {
                if (other == ApkSet.getDefaultInstance()) {
                    return this;
                }
                if (other.hasModuleMetadata()) {
                    this.mergeModuleMetadata(other.getModuleMetadata());
                }
                if (this.apkDescriptionBuilder_ == null) {
                    if (!other.apkDescription_.isEmpty()) {
                        if (this.apkDescription_.isEmpty()) {
                            this.apkDescription_ = other.apkDescription_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureApkDescriptionIsMutable();
                            this.apkDescription_.addAll(other.apkDescription_);
                        }
                        this.onChanged();
                    }
                } else if (!other.apkDescription_.isEmpty()) {
                    if (this.apkDescriptionBuilder_.isEmpty()) {
                        this.apkDescriptionBuilder_.dispose();
                        this.apkDescriptionBuilder_ = null;
                        this.apkDescription_ = other.apkDescription_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.apkDescriptionBuilder_ = alwaysUseFieldBuilders ? this.getApkDescriptionFieldBuilder() : null;
                    } else {
                        this.apkDescriptionBuilder_.addAllMessages(other.apkDescription_);
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
                ApkSet parsedMessage = null;
                try {
                    parsedMessage = (ApkSet)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ApkSet)e2.getUnfinishedMessage();
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
            public boolean hasModuleMetadata() {
                return this.moduleMetadataBuilder_ != null || this.moduleMetadata_ != null;
            }

            @Override
            public ModuleMetadata getModuleMetadata() {
                if (this.moduleMetadataBuilder_ == null) {
                    return this.moduleMetadata_ == null ? ModuleMetadata.getDefaultInstance() : this.moduleMetadata_;
                }
                return this.moduleMetadataBuilder_.getMessage();
            }

            public Builder setModuleMetadata(ModuleMetadata value) {
                if (this.moduleMetadataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.moduleMetadata_ = value;
                    this.onChanged();
                } else {
                    this.moduleMetadataBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setModuleMetadata(ModuleMetadata.Builder builderForValue) {
                if (this.moduleMetadataBuilder_ == null) {
                    this.moduleMetadata_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.moduleMetadataBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeModuleMetadata(ModuleMetadata value) {
                if (this.moduleMetadataBuilder_ == null) {
                    this.moduleMetadata_ = this.moduleMetadata_ != null ? ModuleMetadata.newBuilder(this.moduleMetadata_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.moduleMetadataBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearModuleMetadata() {
                if (this.moduleMetadataBuilder_ == null) {
                    this.moduleMetadata_ = null;
                    this.onChanged();
                } else {
                    this.moduleMetadata_ = null;
                    this.moduleMetadataBuilder_ = null;
                }
                return this;
            }

            public ModuleMetadata.Builder getModuleMetadataBuilder() {
                this.onChanged();
                return this.getModuleMetadataFieldBuilder().getBuilder();
            }

            @Override
            public ModuleMetadataOrBuilder getModuleMetadataOrBuilder() {
                if (this.moduleMetadataBuilder_ != null) {
                    return this.moduleMetadataBuilder_.getMessageOrBuilder();
                }
                return this.moduleMetadata_ == null ? ModuleMetadata.getDefaultInstance() : this.moduleMetadata_;
            }

            private SingleFieldBuilderV3<ModuleMetadata, ModuleMetadata.Builder, ModuleMetadataOrBuilder> getModuleMetadataFieldBuilder() {
                if (this.moduleMetadataBuilder_ == null) {
                    this.moduleMetadataBuilder_ = new SingleFieldBuilderV3(this.getModuleMetadata(), this.getParentForChildren(), this.isClean());
                    this.moduleMetadata_ = null;
                }
                return this.moduleMetadataBuilder_;
            }

            private void ensureApkDescriptionIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.apkDescription_ = new ArrayList<ApkDescription>(this.apkDescription_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<ApkDescription> getApkDescriptionList() {
                if (this.apkDescriptionBuilder_ == null) {
                    return Collections.unmodifiableList(this.apkDescription_);
                }
                return this.apkDescriptionBuilder_.getMessageList();
            }

            @Override
            public int getApkDescriptionCount() {
                if (this.apkDescriptionBuilder_ == null) {
                    return this.apkDescription_.size();
                }
                return this.apkDescriptionBuilder_.getCount();
            }

            @Override
            public ApkDescription getApkDescription(int index) {
                if (this.apkDescriptionBuilder_ == null) {
                    return this.apkDescription_.get(index);
                }
                return this.apkDescriptionBuilder_.getMessage(index);
            }

            public Builder setApkDescription(int index, ApkDescription value) {
                if (this.apkDescriptionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.set(index, value);
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setApkDescription(int index, ApkDescription.Builder builderForValue) {
                if (this.apkDescriptionBuilder_ == null) {
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addApkDescription(ApkDescription value) {
                if (this.apkDescriptionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.add(value);
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addApkDescription(int index, ApkDescription value) {
                if (this.apkDescriptionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.add(index, value);
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addApkDescription(ApkDescription.Builder builderForValue) {
                if (this.apkDescriptionBuilder_ == null) {
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addApkDescription(int index, ApkDescription.Builder builderForValue) {
                if (this.apkDescriptionBuilder_ == null) {
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllApkDescription(Iterable<? extends ApkDescription> values2) {
                if (this.apkDescriptionBuilder_ == null) {
                    this.ensureApkDescriptionIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.apkDescription_);
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearApkDescription() {
                if (this.apkDescriptionBuilder_ == null) {
                    this.apkDescription_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.clear();
                }
                return this;
            }

            public Builder removeApkDescription(int index) {
                if (this.apkDescriptionBuilder_ == null) {
                    this.ensureApkDescriptionIsMutable();
                    this.apkDescription_.remove(index);
                    this.onChanged();
                } else {
                    this.apkDescriptionBuilder_.remove(index);
                }
                return this;
            }

            public ApkDescription.Builder getApkDescriptionBuilder(int index) {
                return this.getApkDescriptionFieldBuilder().getBuilder(index);
            }

            @Override
            public ApkDescriptionOrBuilder getApkDescriptionOrBuilder(int index) {
                if (this.apkDescriptionBuilder_ == null) {
                    return this.apkDescription_.get(index);
                }
                return this.apkDescriptionBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends ApkDescriptionOrBuilder> getApkDescriptionOrBuilderList() {
                if (this.apkDescriptionBuilder_ != null) {
                    return this.apkDescriptionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.apkDescription_);
            }

            public ApkDescription.Builder addApkDescriptionBuilder() {
                return this.getApkDescriptionFieldBuilder().addBuilder(ApkDescription.getDefaultInstance());
            }

            public ApkDescription.Builder addApkDescriptionBuilder(int index) {
                return this.getApkDescriptionFieldBuilder().addBuilder(index, ApkDescription.getDefaultInstance());
            }

            public List<ApkDescription.Builder> getApkDescriptionBuilderList() {
                return this.getApkDescriptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<ApkDescription, ApkDescription.Builder, ApkDescriptionOrBuilder> getApkDescriptionFieldBuilder() {
                if (this.apkDescriptionBuilder_ == null) {
                    this.apkDescriptionBuilder_ = new RepeatedFieldBuilderV3(this.apkDescription_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.apkDescription_ = null;
                }
                return this.apkDescriptionBuilder_;
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

    public static interface ApkSetOrBuilder
    extends MessageOrBuilder {
        public boolean hasModuleMetadata();

        public ModuleMetadata getModuleMetadata();

        public ModuleMetadataOrBuilder getModuleMetadataOrBuilder();

        public List<ApkDescription> getApkDescriptionList();

        public ApkDescription getApkDescription(int var1);

        public int getApkDescriptionCount();

        public List<? extends ApkDescriptionOrBuilder> getApkDescriptionOrBuilderList();

        public ApkDescriptionOrBuilder getApkDescriptionOrBuilder(int var1);
    }

    public static final class Variant
    extends GeneratedMessageV3
    implements VariantOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TARGETING_FIELD_NUMBER = 1;
        private Targeting.VariantTargeting targeting_;
        public static final int APK_SET_FIELD_NUMBER = 2;
        private List<ApkSet> apkSet_;
        public static final int VARIANT_NUMBER_FIELD_NUMBER = 3;
        private int variantNumber_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Variant DEFAULT_INSTANCE = new Variant();
        private static final Parser<Variant> PARSER = new AbstractParser<Variant>(){

            @Override
            public Variant parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Variant(input, extensionRegistry);
            }
        };

        private Variant(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Variant() {
            this.apkSet_ = Collections.emptyList();
            this.variantNumber_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Variant(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 10: {
                            Targeting.VariantTargeting.Builder subBuilder = null;
                            if (this.targeting_ != null) {
                                subBuilder = this.targeting_.toBuilder();
                            }
                            this.targeting_ = input.readMessage(Targeting.VariantTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block12;
                            subBuilder.mergeFrom(this.targeting_);
                            this.targeting_ = subBuilder.buildPartial();
                            continue block12;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.apkSet_ = new ArrayList<ApkSet>();
                                mutable_bitField0_ |= 2;
                            }
                            this.apkSet_.add(input.readMessage(ApkSet.parser(), extensionRegistry));
                            continue block12;
                        }
                        case 24: 
                    }
                    this.variantNumber_ = input.readUInt32();
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
                    this.apkSet_ = Collections.unmodifiableList(this.apkSet_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_Variant_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Variant_fieldAccessorTable.ensureFieldAccessorsInitialized(Variant.class, Builder.class);
        }

        @Override
        public boolean hasTargeting() {
            return this.targeting_ != null;
        }

        @Override
        public Targeting.VariantTargeting getTargeting() {
            return this.targeting_ == null ? Targeting.VariantTargeting.getDefaultInstance() : this.targeting_;
        }

        @Override
        public Targeting.VariantTargetingOrBuilder getTargetingOrBuilder() {
            return this.getTargeting();
        }

        @Override
        public List<ApkSet> getApkSetList() {
            return this.apkSet_;
        }

        @Override
        public List<? extends ApkSetOrBuilder> getApkSetOrBuilderList() {
            return this.apkSet_;
        }

        @Override
        public int getApkSetCount() {
            return this.apkSet_.size();
        }

        @Override
        public ApkSet getApkSet(int index) {
            return this.apkSet_.get(index);
        }

        @Override
        public ApkSetOrBuilder getApkSetOrBuilder(int index) {
            return this.apkSet_.get(index);
        }

        @Override
        public int getVariantNumber() {
            return this.variantNumber_;
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
            if (this.targeting_ != null) {
                output.writeMessage(1, this.getTargeting());
            }
            for (int i2 = 0; i2 < this.apkSet_.size(); ++i2) {
                output.writeMessage(2, this.apkSet_.get(i2));
            }
            if (this.variantNumber_ != 0) {
                output.writeUInt32(3, this.variantNumber_);
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
            if (this.targeting_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getTargeting());
            }
            for (int i2 = 0; i2 < this.apkSet_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.apkSet_.get(i2));
            }
            if (this.variantNumber_ != 0) {
                size += CodedOutputStream.computeUInt32Size(3, this.variantNumber_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Variant)) {
                return super.equals(obj);
            }
            Variant other = (Variant)obj;
            boolean result = true;
            boolean bl = result = result && this.hasTargeting() == other.hasTargeting();
            if (this.hasTargeting()) {
                result = result && this.getTargeting().equals(other.getTargeting());
            }
            result = result && this.getApkSetList().equals(other.getApkSetList());
            result = result && this.getVariantNumber() == other.getVariantNumber();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Variant.getDescriptor().hashCode();
            if (this.hasTargeting()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getTargeting().hashCode();
            }
            if (this.getApkSetCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getApkSetList().hashCode();
            }
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getVariantNumber();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Variant parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Variant parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Variant parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Variant parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Variant parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Variant parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Variant parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Variant parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Variant parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Variant parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Variant parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Variant parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Variant.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Variant prototype) {
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

        public static Variant getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Variant> parser() {
            return PARSER;
        }

        public Parser<Variant> getParserForType() {
            return PARSER;
        }

        @Override
        public Variant getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements VariantOrBuilder {
            private int bitField0_;
            private Targeting.VariantTargeting targeting_ = null;
            private SingleFieldBuilderV3<Targeting.VariantTargeting, Targeting.VariantTargeting.Builder, Targeting.VariantTargetingOrBuilder> targetingBuilder_;
            private List<ApkSet> apkSet_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<ApkSet, ApkSet.Builder, ApkSetOrBuilder> apkSetBuilder_;
            private int variantNumber_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Variant_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Variant_fieldAccessorTable.ensureFieldAccessorsInitialized(Variant.class, Builder.class);
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
                    this.getApkSetFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = null;
                } else {
                    this.targeting_ = null;
                    this.targetingBuilder_ = null;
                }
                if (this.apkSetBuilder_ == null) {
                    this.apkSet_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.apkSetBuilder_.clear();
                }
                this.variantNumber_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Variant_descriptor;
            }

            @Override
            public Variant getDefaultInstanceForType() {
                return Variant.getDefaultInstance();
            }

            @Override
            public Variant build() {
                Variant result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Variant buildPartial() {
                Variant result = new Variant(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if (this.targetingBuilder_ == null) {
                    result.targeting_ = this.targeting_;
                } else {
                    result.targeting_ = this.targetingBuilder_.build();
                }
                if (this.apkSetBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.apkSet_ = Collections.unmodifiableList(this.apkSet_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.apkSet_ = this.apkSet_;
                } else {
                    result.apkSet_ = this.apkSetBuilder_.build();
                }
                result.variantNumber_ = this.variantNumber_;
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
                if (other instanceof Variant) {
                    return this.mergeFrom((Variant)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Variant other) {
                if (other == Variant.getDefaultInstance()) {
                    return this;
                }
                if (other.hasTargeting()) {
                    this.mergeTargeting(other.getTargeting());
                }
                if (this.apkSetBuilder_ == null) {
                    if (!other.apkSet_.isEmpty()) {
                        if (this.apkSet_.isEmpty()) {
                            this.apkSet_ = other.apkSet_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureApkSetIsMutable();
                            this.apkSet_.addAll(other.apkSet_);
                        }
                        this.onChanged();
                    }
                } else if (!other.apkSet_.isEmpty()) {
                    if (this.apkSetBuilder_.isEmpty()) {
                        this.apkSetBuilder_.dispose();
                        this.apkSetBuilder_ = null;
                        this.apkSet_ = other.apkSet_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.apkSetBuilder_ = alwaysUseFieldBuilders ? this.getApkSetFieldBuilder() : null;
                    } else {
                        this.apkSetBuilder_.addAllMessages(other.apkSet_);
                    }
                }
                if (other.getVariantNumber() != 0) {
                    this.setVariantNumber(other.getVariantNumber());
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
                Variant parsedMessage = null;
                try {
                    parsedMessage = (Variant)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Variant)e2.getUnfinishedMessage();
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
            public boolean hasTargeting() {
                return this.targetingBuilder_ != null || this.targeting_ != null;
            }

            @Override
            public Targeting.VariantTargeting getTargeting() {
                if (this.targetingBuilder_ == null) {
                    return this.targeting_ == null ? Targeting.VariantTargeting.getDefaultInstance() : this.targeting_;
                }
                return this.targetingBuilder_.getMessage();
            }

            public Builder setTargeting(Targeting.VariantTargeting value) {
                if (this.targetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.targeting_ = value;
                    this.onChanged();
                } else {
                    this.targetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setTargeting(Targeting.VariantTargeting.Builder builderForValue) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.targetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTargeting(Targeting.VariantTargeting value) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = this.targeting_ != null ? Targeting.VariantTargeting.newBuilder(this.targeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.targetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearTargeting() {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = null;
                    this.onChanged();
                } else {
                    this.targeting_ = null;
                    this.targetingBuilder_ = null;
                }
                return this;
            }

            public Targeting.VariantTargeting.Builder getTargetingBuilder() {
                this.onChanged();
                return this.getTargetingFieldBuilder().getBuilder();
            }

            @Override
            public Targeting.VariantTargetingOrBuilder getTargetingOrBuilder() {
                if (this.targetingBuilder_ != null) {
                    return this.targetingBuilder_.getMessageOrBuilder();
                }
                return this.targeting_ == null ? Targeting.VariantTargeting.getDefaultInstance() : this.targeting_;
            }

            private SingleFieldBuilderV3<Targeting.VariantTargeting, Targeting.VariantTargeting.Builder, Targeting.VariantTargetingOrBuilder> getTargetingFieldBuilder() {
                if (this.targetingBuilder_ == null) {
                    this.targetingBuilder_ = new SingleFieldBuilderV3(this.getTargeting(), this.getParentForChildren(), this.isClean());
                    this.targeting_ = null;
                }
                return this.targetingBuilder_;
            }

            private void ensureApkSetIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.apkSet_ = new ArrayList<ApkSet>(this.apkSet_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<ApkSet> getApkSetList() {
                if (this.apkSetBuilder_ == null) {
                    return Collections.unmodifiableList(this.apkSet_);
                }
                return this.apkSetBuilder_.getMessageList();
            }

            @Override
            public int getApkSetCount() {
                if (this.apkSetBuilder_ == null) {
                    return this.apkSet_.size();
                }
                return this.apkSetBuilder_.getCount();
            }

            @Override
            public ApkSet getApkSet(int index) {
                if (this.apkSetBuilder_ == null) {
                    return this.apkSet_.get(index);
                }
                return this.apkSetBuilder_.getMessage(index);
            }

            public Builder setApkSet(int index, ApkSet value) {
                if (this.apkSetBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApkSetIsMutable();
                    this.apkSet_.set(index, value);
                    this.onChanged();
                } else {
                    this.apkSetBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setApkSet(int index, ApkSet.Builder builderForValue) {
                if (this.apkSetBuilder_ == null) {
                    this.ensureApkSetIsMutable();
                    this.apkSet_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.apkSetBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addApkSet(ApkSet value) {
                if (this.apkSetBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApkSetIsMutable();
                    this.apkSet_.add(value);
                    this.onChanged();
                } else {
                    this.apkSetBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addApkSet(int index, ApkSet value) {
                if (this.apkSetBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureApkSetIsMutable();
                    this.apkSet_.add(index, value);
                    this.onChanged();
                } else {
                    this.apkSetBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addApkSet(ApkSet.Builder builderForValue) {
                if (this.apkSetBuilder_ == null) {
                    this.ensureApkSetIsMutable();
                    this.apkSet_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.apkSetBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addApkSet(int index, ApkSet.Builder builderForValue) {
                if (this.apkSetBuilder_ == null) {
                    this.ensureApkSetIsMutable();
                    this.apkSet_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.apkSetBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllApkSet(Iterable<? extends ApkSet> values2) {
                if (this.apkSetBuilder_ == null) {
                    this.ensureApkSetIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.apkSet_);
                    this.onChanged();
                } else {
                    this.apkSetBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearApkSet() {
                if (this.apkSetBuilder_ == null) {
                    this.apkSet_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.apkSetBuilder_.clear();
                }
                return this;
            }

            public Builder removeApkSet(int index) {
                if (this.apkSetBuilder_ == null) {
                    this.ensureApkSetIsMutable();
                    this.apkSet_.remove(index);
                    this.onChanged();
                } else {
                    this.apkSetBuilder_.remove(index);
                }
                return this;
            }

            public ApkSet.Builder getApkSetBuilder(int index) {
                return this.getApkSetFieldBuilder().getBuilder(index);
            }

            @Override
            public ApkSetOrBuilder getApkSetOrBuilder(int index) {
                if (this.apkSetBuilder_ == null) {
                    return this.apkSet_.get(index);
                }
                return this.apkSetBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends ApkSetOrBuilder> getApkSetOrBuilderList() {
                if (this.apkSetBuilder_ != null) {
                    return this.apkSetBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.apkSet_);
            }

            public ApkSet.Builder addApkSetBuilder() {
                return this.getApkSetFieldBuilder().addBuilder(ApkSet.getDefaultInstance());
            }

            public ApkSet.Builder addApkSetBuilder(int index) {
                return this.getApkSetFieldBuilder().addBuilder(index, ApkSet.getDefaultInstance());
            }

            public List<ApkSet.Builder> getApkSetBuilderList() {
                return this.getApkSetFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<ApkSet, ApkSet.Builder, ApkSetOrBuilder> getApkSetFieldBuilder() {
                if (this.apkSetBuilder_ == null) {
                    this.apkSetBuilder_ = new RepeatedFieldBuilderV3(this.apkSet_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.apkSet_ = null;
                }
                return this.apkSetBuilder_;
            }

            @Override
            public int getVariantNumber() {
                return this.variantNumber_;
            }

            public Builder setVariantNumber(int value) {
                this.variantNumber_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearVariantNumber() {
                this.variantNumber_ = 0;
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

    public static interface VariantOrBuilder
    extends MessageOrBuilder {
        public boolean hasTargeting();

        public Targeting.VariantTargeting getTargeting();

        public Targeting.VariantTargetingOrBuilder getTargetingOrBuilder();

        public List<ApkSet> getApkSetList();

        public ApkSet getApkSet(int var1);

        public int getApkSetCount();

        public List<? extends ApkSetOrBuilder> getApkSetOrBuilderList();

        public ApkSetOrBuilder getApkSetOrBuilder(int var1);

        public int getVariantNumber();
    }

    public static final class BuildApksResult
    extends GeneratedMessageV3
    implements BuildApksResultOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int PACKAGE_NAME_FIELD_NUMBER = 4;
        private volatile Object packageName_;
        public static final int VARIANT_FIELD_NUMBER = 1;
        private List<Variant> variant_;
        public static final int BUNDLETOOL_FIELD_NUMBER = 2;
        private Config.Bundletool bundletool_;
        public static final int ASSET_SLICE_SET_FIELD_NUMBER = 3;
        private List<AssetSliceSet> assetSliceSet_;
        public static final int LOCAL_TESTING_INFO_FIELD_NUMBER = 5;
        private LocalTestingInfo localTestingInfo_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final BuildApksResult DEFAULT_INSTANCE = new BuildApksResult();
        private static final Parser<BuildApksResult> PARSER = new AbstractParser<BuildApksResult>(){

            @Override
            public BuildApksResult parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new BuildApksResult(input, extensionRegistry);
            }
        };

        private BuildApksResult(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private BuildApksResult() {
            this.packageName_ = "";
            this.variant_ = Collections.emptyList();
            this.assetSliceSet_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private BuildApksResult(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
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
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.variant_ = new ArrayList<Variant>();
                                mutable_bitField0_ |= 2;
                            }
                            this.variant_.add(input.readMessage(Variant.parser(), extensionRegistry));
                            continue block14;
                        }
                        case 18: {
                            subBuilder = null;
                            if (this.bundletool_ != null) {
                                subBuilder = this.bundletool_.toBuilder();
                            }
                            this.bundletool_ = input.readMessage(Config.Bundletool.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((Config.Bundletool.Builder)subBuilder).mergeFrom(this.bundletool_);
                            this.bundletool_ = ((Config.Bundletool.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 26: {
                            if ((mutable_bitField0_ & 8) != 8) {
                                this.assetSliceSet_ = new ArrayList<AssetSliceSet>();
                                mutable_bitField0_ |= 8;
                            }
                            this.assetSliceSet_.add(input.readMessage(AssetSliceSet.parser(), extensionRegistry));
                            continue block14;
                        }
                        case 34: {
                            String s3 = input.readStringRequireUtf8();
                            this.packageName_ = s3;
                            continue block14;
                        }
                        case 42: 
                    }
                    subBuilder = null;
                    if (this.localTestingInfo_ != null) {
                        subBuilder = this.localTestingInfo_.toBuilder();
                    }
                    this.localTestingInfo_ = input.readMessage(LocalTestingInfo.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    ((LocalTestingInfo.Builder)subBuilder).mergeFrom(this.localTestingInfo_);
                    this.localTestingInfo_ = ((LocalTestingInfo.Builder)subBuilder).buildPartial();
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
                    this.variant_ = Collections.unmodifiableList(this.variant_);
                }
                if ((mutable_bitField0_ & 8) == 8) {
                    this.assetSliceSet_ = Collections.unmodifiableList(this.assetSliceSet_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_BuildApksResult_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_BuildApksResult_fieldAccessorTable.ensureFieldAccessorsInitialized(BuildApksResult.class, Builder.class);
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
        public List<Variant> getVariantList() {
            return this.variant_;
        }

        @Override
        public List<? extends VariantOrBuilder> getVariantOrBuilderList() {
            return this.variant_;
        }

        @Override
        public int getVariantCount() {
            return this.variant_.size();
        }

        @Override
        public Variant getVariant(int index) {
            return this.variant_.get(index);
        }

        @Override
        public VariantOrBuilder getVariantOrBuilder(int index) {
            return this.variant_.get(index);
        }

        @Override
        public boolean hasBundletool() {
            return this.bundletool_ != null;
        }

        @Override
        public Config.Bundletool getBundletool() {
            return this.bundletool_ == null ? Config.Bundletool.getDefaultInstance() : this.bundletool_;
        }

        @Override
        public Config.BundletoolOrBuilder getBundletoolOrBuilder() {
            return this.getBundletool();
        }

        @Override
        public List<AssetSliceSet> getAssetSliceSetList() {
            return this.assetSliceSet_;
        }

        @Override
        public List<? extends AssetSliceSetOrBuilder> getAssetSliceSetOrBuilderList() {
            return this.assetSliceSet_;
        }

        @Override
        public int getAssetSliceSetCount() {
            return this.assetSliceSet_.size();
        }

        @Override
        public AssetSliceSet getAssetSliceSet(int index) {
            return this.assetSliceSet_.get(index);
        }

        @Override
        public AssetSliceSetOrBuilder getAssetSliceSetOrBuilder(int index) {
            return this.assetSliceSet_.get(index);
        }

        @Override
        public boolean hasLocalTestingInfo() {
            return this.localTestingInfo_ != null;
        }

        @Override
        public LocalTestingInfo getLocalTestingInfo() {
            return this.localTestingInfo_ == null ? LocalTestingInfo.getDefaultInstance() : this.localTestingInfo_;
        }

        @Override
        public LocalTestingInfoOrBuilder getLocalTestingInfoOrBuilder() {
            return this.getLocalTestingInfo();
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
            for (i2 = 0; i2 < this.variant_.size(); ++i2) {
                output.writeMessage(1, this.variant_.get(i2));
            }
            if (this.bundletool_ != null) {
                output.writeMessage(2, this.getBundletool());
            }
            for (i2 = 0; i2 < this.assetSliceSet_.size(); ++i2) {
                output.writeMessage(3, this.assetSliceSet_.get(i2));
            }
            if (!this.getPackageNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 4, this.packageName_);
            }
            if (this.localTestingInfo_ != null) {
                output.writeMessage(5, this.getLocalTestingInfo());
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
            for (i2 = 0; i2 < this.variant_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.variant_.get(i2));
            }
            if (this.bundletool_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getBundletool());
            }
            for (i2 = 0; i2 < this.assetSliceSet_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(3, this.assetSliceSet_.get(i2));
            }
            if (!this.getPackageNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(4, this.packageName_);
            }
            if (this.localTestingInfo_ != null) {
                size += CodedOutputStream.computeMessageSize(5, this.getLocalTestingInfo());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof BuildApksResult)) {
                return super.equals(obj);
            }
            BuildApksResult other = (BuildApksResult)obj;
            boolean result = true;
            result = result && this.getPackageName().equals(other.getPackageName());
            result = result && this.getVariantList().equals(other.getVariantList());
            boolean bl = result = result && this.hasBundletool() == other.hasBundletool();
            if (this.hasBundletool()) {
                result = result && this.getBundletool().equals(other.getBundletool());
            }
            result = result && this.getAssetSliceSetList().equals(other.getAssetSliceSetList());
            boolean bl2 = result = result && this.hasLocalTestingInfo() == other.hasLocalTestingInfo();
            if (this.hasLocalTestingInfo()) {
                result = result && this.getLocalTestingInfo().equals(other.getLocalTestingInfo());
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
            hash = 19 * hash + BuildApksResult.getDescriptor().hashCode();
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getPackageName().hashCode();
            if (this.getVariantCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getVariantList().hashCode();
            }
            if (this.hasBundletool()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getBundletool().hashCode();
            }
            if (this.getAssetSliceSetCount() > 0) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getAssetSliceSetList().hashCode();
            }
            if (this.hasLocalTestingInfo()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getLocalTestingInfo().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static BuildApksResult parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BuildApksResult parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BuildApksResult parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BuildApksResult parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BuildApksResult parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BuildApksResult parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BuildApksResult parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BuildApksResult parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static BuildApksResult parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static BuildApksResult parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static BuildApksResult parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BuildApksResult parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return BuildApksResult.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(BuildApksResult prototype) {
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

        public static BuildApksResult getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BuildApksResult> parser() {
            return PARSER;
        }

        public Parser<BuildApksResult> getParserForType() {
            return PARSER;
        }

        @Override
        public BuildApksResult getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements BuildApksResultOrBuilder {
            private int bitField0_;
            private Object packageName_ = "";
            private List<Variant> variant_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Variant, Variant.Builder, VariantOrBuilder> variantBuilder_;
            private Config.Bundletool bundletool_ = null;
            private SingleFieldBuilderV3<Config.Bundletool, Config.Bundletool.Builder, Config.BundletoolOrBuilder> bundletoolBuilder_;
            private List<AssetSliceSet> assetSliceSet_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<AssetSliceSet, AssetSliceSet.Builder, AssetSliceSetOrBuilder> assetSliceSetBuilder_;
            private LocalTestingInfo localTestingInfo_ = null;
            private SingleFieldBuilderV3<LocalTestingInfo, LocalTestingInfo.Builder, LocalTestingInfoOrBuilder> localTestingInfoBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_BuildApksResult_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_BuildApksResult_fieldAccessorTable.ensureFieldAccessorsInitialized(BuildApksResult.class, Builder.class);
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
                    this.getVariantFieldBuilder();
                    this.getAssetSliceSetFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.packageName_ = "";
                if (this.variantBuilder_ == null) {
                    this.variant_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.variantBuilder_.clear();
                }
                if (this.bundletoolBuilder_ == null) {
                    this.bundletool_ = null;
                } else {
                    this.bundletool_ = null;
                    this.bundletoolBuilder_ = null;
                }
                if (this.assetSliceSetBuilder_ == null) {
                    this.assetSliceSet_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFF7;
                } else {
                    this.assetSliceSetBuilder_.clear();
                }
                if (this.localTestingInfoBuilder_ == null) {
                    this.localTestingInfo_ = null;
                } else {
                    this.localTestingInfo_ = null;
                    this.localTestingInfoBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_BuildApksResult_descriptor;
            }

            @Override
            public BuildApksResult getDefaultInstanceForType() {
                return BuildApksResult.getDefaultInstance();
            }

            @Override
            public BuildApksResult build() {
                BuildApksResult result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public BuildApksResult buildPartial() {
                BuildApksResult result = new BuildApksResult(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                result.packageName_ = this.packageName_;
                if (this.variantBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.variant_ = Collections.unmodifiableList(this.variant_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.variant_ = this.variant_;
                } else {
                    result.variant_ = this.variantBuilder_.build();
                }
                if (this.bundletoolBuilder_ == null) {
                    result.bundletool_ = this.bundletool_;
                } else {
                    result.bundletool_ = this.bundletoolBuilder_.build();
                }
                if (this.assetSliceSetBuilder_ == null) {
                    if ((this.bitField0_ & 8) == 8) {
                        this.assetSliceSet_ = Collections.unmodifiableList(this.assetSliceSet_);
                        this.bitField0_ &= 0xFFFFFFF7;
                    }
                    result.assetSliceSet_ = this.assetSliceSet_;
                } else {
                    result.assetSliceSet_ = this.assetSliceSetBuilder_.build();
                }
                if (this.localTestingInfoBuilder_ == null) {
                    result.localTestingInfo_ = this.localTestingInfo_;
                } else {
                    result.localTestingInfo_ = this.localTestingInfoBuilder_.build();
                }
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
                if (other instanceof BuildApksResult) {
                    return this.mergeFrom((BuildApksResult)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(BuildApksResult other) {
                if (other == BuildApksResult.getDefaultInstance()) {
                    return this;
                }
                if (!other.getPackageName().isEmpty()) {
                    this.packageName_ = other.packageName_;
                    this.onChanged();
                }
                if (this.variantBuilder_ == null) {
                    if (!other.variant_.isEmpty()) {
                        if (this.variant_.isEmpty()) {
                            this.variant_ = other.variant_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureVariantIsMutable();
                            this.variant_.addAll(other.variant_);
                        }
                        this.onChanged();
                    }
                } else if (!other.variant_.isEmpty()) {
                    if (this.variantBuilder_.isEmpty()) {
                        this.variantBuilder_.dispose();
                        this.variantBuilder_ = null;
                        this.variant_ = other.variant_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.variantBuilder_ = alwaysUseFieldBuilders ? this.getVariantFieldBuilder() : null;
                    } else {
                        this.variantBuilder_.addAllMessages(other.variant_);
                    }
                }
                if (other.hasBundletool()) {
                    this.mergeBundletool(other.getBundletool());
                }
                if (this.assetSliceSetBuilder_ == null) {
                    if (!other.assetSliceSet_.isEmpty()) {
                        if (this.assetSliceSet_.isEmpty()) {
                            this.assetSliceSet_ = other.assetSliceSet_;
                            this.bitField0_ &= 0xFFFFFFF7;
                        } else {
                            this.ensureAssetSliceSetIsMutable();
                            this.assetSliceSet_.addAll(other.assetSliceSet_);
                        }
                        this.onChanged();
                    }
                } else if (!other.assetSliceSet_.isEmpty()) {
                    if (this.assetSliceSetBuilder_.isEmpty()) {
                        this.assetSliceSetBuilder_.dispose();
                        this.assetSliceSetBuilder_ = null;
                        this.assetSliceSet_ = other.assetSliceSet_;
                        this.bitField0_ &= 0xFFFFFFF7;
                        this.assetSliceSetBuilder_ = alwaysUseFieldBuilders ? this.getAssetSliceSetFieldBuilder() : null;
                    } else {
                        this.assetSliceSetBuilder_.addAllMessages(other.assetSliceSet_);
                    }
                }
                if (other.hasLocalTestingInfo()) {
                    this.mergeLocalTestingInfo(other.getLocalTestingInfo());
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
                BuildApksResult parsedMessage = null;
                try {
                    parsedMessage = (BuildApksResult)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (BuildApksResult)e2.getUnfinishedMessage();
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
                this.packageName_ = BuildApksResult.getDefaultInstance().getPackageName();
                this.onChanged();
                return this;
            }

            public Builder setPackageNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                BuildApksResult.checkByteStringIsUtf8(value);
                this.packageName_ = value;
                this.onChanged();
                return this;
            }

            private void ensureVariantIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.variant_ = new ArrayList<Variant>(this.variant_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<Variant> getVariantList() {
                if (this.variantBuilder_ == null) {
                    return Collections.unmodifiableList(this.variant_);
                }
                return this.variantBuilder_.getMessageList();
            }

            @Override
            public int getVariantCount() {
                if (this.variantBuilder_ == null) {
                    return this.variant_.size();
                }
                return this.variantBuilder_.getCount();
            }

            @Override
            public Variant getVariant(int index) {
                if (this.variantBuilder_ == null) {
                    return this.variant_.get(index);
                }
                return this.variantBuilder_.getMessage(index);
            }

            public Builder setVariant(int index, Variant value) {
                if (this.variantBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureVariantIsMutable();
                    this.variant_.set(index, value);
                    this.onChanged();
                } else {
                    this.variantBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setVariant(int index, Variant.Builder builderForValue) {
                if (this.variantBuilder_ == null) {
                    this.ensureVariantIsMutable();
                    this.variant_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.variantBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addVariant(Variant value) {
                if (this.variantBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureVariantIsMutable();
                    this.variant_.add(value);
                    this.onChanged();
                } else {
                    this.variantBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addVariant(int index, Variant value) {
                if (this.variantBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureVariantIsMutable();
                    this.variant_.add(index, value);
                    this.onChanged();
                } else {
                    this.variantBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addVariant(Variant.Builder builderForValue) {
                if (this.variantBuilder_ == null) {
                    this.ensureVariantIsMutable();
                    this.variant_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.variantBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addVariant(int index, Variant.Builder builderForValue) {
                if (this.variantBuilder_ == null) {
                    this.ensureVariantIsMutable();
                    this.variant_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.variantBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllVariant(Iterable<? extends Variant> values2) {
                if (this.variantBuilder_ == null) {
                    this.ensureVariantIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.variant_);
                    this.onChanged();
                } else {
                    this.variantBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearVariant() {
                if (this.variantBuilder_ == null) {
                    this.variant_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.variantBuilder_.clear();
                }
                return this;
            }

            public Builder removeVariant(int index) {
                if (this.variantBuilder_ == null) {
                    this.ensureVariantIsMutable();
                    this.variant_.remove(index);
                    this.onChanged();
                } else {
                    this.variantBuilder_.remove(index);
                }
                return this;
            }

            public Variant.Builder getVariantBuilder(int index) {
                return this.getVariantFieldBuilder().getBuilder(index);
            }

            @Override
            public VariantOrBuilder getVariantOrBuilder(int index) {
                if (this.variantBuilder_ == null) {
                    return this.variant_.get(index);
                }
                return this.variantBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends VariantOrBuilder> getVariantOrBuilderList() {
                if (this.variantBuilder_ != null) {
                    return this.variantBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.variant_);
            }

            public Variant.Builder addVariantBuilder() {
                return this.getVariantFieldBuilder().addBuilder(Variant.getDefaultInstance());
            }

            public Variant.Builder addVariantBuilder(int index) {
                return this.getVariantFieldBuilder().addBuilder(index, Variant.getDefaultInstance());
            }

            public List<Variant.Builder> getVariantBuilderList() {
                return this.getVariantFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Variant, Variant.Builder, VariantOrBuilder> getVariantFieldBuilder() {
                if (this.variantBuilder_ == null) {
                    this.variantBuilder_ = new RepeatedFieldBuilderV3(this.variant_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.variant_ = null;
                }
                return this.variantBuilder_;
            }

            @Override
            public boolean hasBundletool() {
                return this.bundletoolBuilder_ != null || this.bundletool_ != null;
            }

            @Override
            public Config.Bundletool getBundletool() {
                if (this.bundletoolBuilder_ == null) {
                    return this.bundletool_ == null ? Config.Bundletool.getDefaultInstance() : this.bundletool_;
                }
                return this.bundletoolBuilder_.getMessage();
            }

            public Builder setBundletool(Config.Bundletool value) {
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

            public Builder setBundletool(Config.Bundletool.Builder builderForValue) {
                if (this.bundletoolBuilder_ == null) {
                    this.bundletool_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.bundletoolBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeBundletool(Config.Bundletool value) {
                if (this.bundletoolBuilder_ == null) {
                    this.bundletool_ = this.bundletool_ != null ? Config.Bundletool.newBuilder(this.bundletool_).mergeFrom(value).buildPartial() : value;
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

            public Config.Bundletool.Builder getBundletoolBuilder() {
                this.onChanged();
                return this.getBundletoolFieldBuilder().getBuilder();
            }

            @Override
            public Config.BundletoolOrBuilder getBundletoolOrBuilder() {
                if (this.bundletoolBuilder_ != null) {
                    return this.bundletoolBuilder_.getMessageOrBuilder();
                }
                return this.bundletool_ == null ? Config.Bundletool.getDefaultInstance() : this.bundletool_;
            }

            private SingleFieldBuilderV3<Config.Bundletool, Config.Bundletool.Builder, Config.BundletoolOrBuilder> getBundletoolFieldBuilder() {
                if (this.bundletoolBuilder_ == null) {
                    this.bundletoolBuilder_ = new SingleFieldBuilderV3(this.getBundletool(), this.getParentForChildren(), this.isClean());
                    this.bundletool_ = null;
                }
                return this.bundletoolBuilder_;
            }

            private void ensureAssetSliceSetIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.assetSliceSet_ = new ArrayList<AssetSliceSet>(this.assetSliceSet_);
                    this.bitField0_ |= 8;
                }
            }

            @Override
            public List<AssetSliceSet> getAssetSliceSetList() {
                if (this.assetSliceSetBuilder_ == null) {
                    return Collections.unmodifiableList(this.assetSliceSet_);
                }
                return this.assetSliceSetBuilder_.getMessageList();
            }

            @Override
            public int getAssetSliceSetCount() {
                if (this.assetSliceSetBuilder_ == null) {
                    return this.assetSliceSet_.size();
                }
                return this.assetSliceSetBuilder_.getCount();
            }

            @Override
            public AssetSliceSet getAssetSliceSet(int index) {
                if (this.assetSliceSetBuilder_ == null) {
                    return this.assetSliceSet_.get(index);
                }
                return this.assetSliceSetBuilder_.getMessage(index);
            }

            public Builder setAssetSliceSet(int index, AssetSliceSet value) {
                if (this.assetSliceSetBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAssetSliceSetIsMutable();
                    this.assetSliceSet_.set(index, value);
                    this.onChanged();
                } else {
                    this.assetSliceSetBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setAssetSliceSet(int index, AssetSliceSet.Builder builderForValue) {
                if (this.assetSliceSetBuilder_ == null) {
                    this.ensureAssetSliceSetIsMutable();
                    this.assetSliceSet_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.assetSliceSetBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAssetSliceSet(AssetSliceSet value) {
                if (this.assetSliceSetBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAssetSliceSetIsMutable();
                    this.assetSliceSet_.add(value);
                    this.onChanged();
                } else {
                    this.assetSliceSetBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addAssetSliceSet(int index, AssetSliceSet value) {
                if (this.assetSliceSetBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAssetSliceSetIsMutable();
                    this.assetSliceSet_.add(index, value);
                    this.onChanged();
                } else {
                    this.assetSliceSetBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addAssetSliceSet(AssetSliceSet.Builder builderForValue) {
                if (this.assetSliceSetBuilder_ == null) {
                    this.ensureAssetSliceSetIsMutable();
                    this.assetSliceSet_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.assetSliceSetBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAssetSliceSet(int index, AssetSliceSet.Builder builderForValue) {
                if (this.assetSliceSetBuilder_ == null) {
                    this.ensureAssetSliceSetIsMutable();
                    this.assetSliceSet_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.assetSliceSetBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAssetSliceSet(Iterable<? extends AssetSliceSet> values2) {
                if (this.assetSliceSetBuilder_ == null) {
                    this.ensureAssetSliceSetIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.assetSliceSet_);
                    this.onChanged();
                } else {
                    this.assetSliceSetBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearAssetSliceSet() {
                if (this.assetSliceSetBuilder_ == null) {
                    this.assetSliceSet_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFF7;
                    this.onChanged();
                } else {
                    this.assetSliceSetBuilder_.clear();
                }
                return this;
            }

            public Builder removeAssetSliceSet(int index) {
                if (this.assetSliceSetBuilder_ == null) {
                    this.ensureAssetSliceSetIsMutable();
                    this.assetSliceSet_.remove(index);
                    this.onChanged();
                } else {
                    this.assetSliceSetBuilder_.remove(index);
                }
                return this;
            }

            public AssetSliceSet.Builder getAssetSliceSetBuilder(int index) {
                return this.getAssetSliceSetFieldBuilder().getBuilder(index);
            }

            @Override
            public AssetSliceSetOrBuilder getAssetSliceSetOrBuilder(int index) {
                if (this.assetSliceSetBuilder_ == null) {
                    return this.assetSliceSet_.get(index);
                }
                return this.assetSliceSetBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends AssetSliceSetOrBuilder> getAssetSliceSetOrBuilderList() {
                if (this.assetSliceSetBuilder_ != null) {
                    return this.assetSliceSetBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.assetSliceSet_);
            }

            public AssetSliceSet.Builder addAssetSliceSetBuilder() {
                return this.getAssetSliceSetFieldBuilder().addBuilder(AssetSliceSet.getDefaultInstance());
            }

            public AssetSliceSet.Builder addAssetSliceSetBuilder(int index) {
                return this.getAssetSliceSetFieldBuilder().addBuilder(index, AssetSliceSet.getDefaultInstance());
            }

            public List<AssetSliceSet.Builder> getAssetSliceSetBuilderList() {
                return this.getAssetSliceSetFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<AssetSliceSet, AssetSliceSet.Builder, AssetSliceSetOrBuilder> getAssetSliceSetFieldBuilder() {
                if (this.assetSliceSetBuilder_ == null) {
                    this.assetSliceSetBuilder_ = new RepeatedFieldBuilderV3(this.assetSliceSet_, (this.bitField0_ & 8) == 8, this.getParentForChildren(), this.isClean());
                    this.assetSliceSet_ = null;
                }
                return this.assetSliceSetBuilder_;
            }

            @Override
            public boolean hasLocalTestingInfo() {
                return this.localTestingInfoBuilder_ != null || this.localTestingInfo_ != null;
            }

            @Override
            public LocalTestingInfo getLocalTestingInfo() {
                if (this.localTestingInfoBuilder_ == null) {
                    return this.localTestingInfo_ == null ? LocalTestingInfo.getDefaultInstance() : this.localTestingInfo_;
                }
                return this.localTestingInfoBuilder_.getMessage();
            }

            public Builder setLocalTestingInfo(LocalTestingInfo value) {
                if (this.localTestingInfoBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.localTestingInfo_ = value;
                    this.onChanged();
                } else {
                    this.localTestingInfoBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setLocalTestingInfo(LocalTestingInfo.Builder builderForValue) {
                if (this.localTestingInfoBuilder_ == null) {
                    this.localTestingInfo_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.localTestingInfoBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeLocalTestingInfo(LocalTestingInfo value) {
                if (this.localTestingInfoBuilder_ == null) {
                    this.localTestingInfo_ = this.localTestingInfo_ != null ? LocalTestingInfo.newBuilder(this.localTestingInfo_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.localTestingInfoBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearLocalTestingInfo() {
                if (this.localTestingInfoBuilder_ == null) {
                    this.localTestingInfo_ = null;
                    this.onChanged();
                } else {
                    this.localTestingInfo_ = null;
                    this.localTestingInfoBuilder_ = null;
                }
                return this;
            }

            public LocalTestingInfo.Builder getLocalTestingInfoBuilder() {
                this.onChanged();
                return this.getLocalTestingInfoFieldBuilder().getBuilder();
            }

            @Override
            public LocalTestingInfoOrBuilder getLocalTestingInfoOrBuilder() {
                if (this.localTestingInfoBuilder_ != null) {
                    return this.localTestingInfoBuilder_.getMessageOrBuilder();
                }
                return this.localTestingInfo_ == null ? LocalTestingInfo.getDefaultInstance() : this.localTestingInfo_;
            }

            private SingleFieldBuilderV3<LocalTestingInfo, LocalTestingInfo.Builder, LocalTestingInfoOrBuilder> getLocalTestingInfoFieldBuilder() {
                if (this.localTestingInfoBuilder_ == null) {
                    this.localTestingInfoBuilder_ = new SingleFieldBuilderV3(this.getLocalTestingInfo(), this.getParentForChildren(), this.isClean());
                    this.localTestingInfo_ = null;
                }
                return this.localTestingInfoBuilder_;
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

    public static interface BuildApksResultOrBuilder
    extends MessageOrBuilder {
        public String getPackageName();

        public ByteString getPackageNameBytes();

        public List<Variant> getVariantList();

        public Variant getVariant(int var1);

        public int getVariantCount();

        public List<? extends VariantOrBuilder> getVariantOrBuilderList();

        public VariantOrBuilder getVariantOrBuilder(int var1);

        public boolean hasBundletool();

        public Config.Bundletool getBundletool();

        public Config.BundletoolOrBuilder getBundletoolOrBuilder();

        public List<AssetSliceSet> getAssetSliceSetList();

        public AssetSliceSet getAssetSliceSet(int var1);

        public int getAssetSliceSetCount();

        public List<? extends AssetSliceSetOrBuilder> getAssetSliceSetOrBuilderList();

        public AssetSliceSetOrBuilder getAssetSliceSetOrBuilder(int var1);

        public boolean hasLocalTestingInfo();

        public LocalTestingInfo getLocalTestingInfo();

        public LocalTestingInfoOrBuilder getLocalTestingInfoOrBuilder();
    }

    public static enum DeliveryType implements ProtocolMessageEnum
    {
        UNKNOWN_DELIVERY_TYPE(0),
        INSTALL_TIME(1),
        ON_DEMAND(2),
        FAST_FOLLOW(3),
        UNRECOGNIZED(-1);

        public static final int UNKNOWN_DELIVERY_TYPE_VALUE = 0;
        public static final int INSTALL_TIME_VALUE = 1;
        public static final int ON_DEMAND_VALUE = 2;
        public static final int FAST_FOLLOW_VALUE = 3;
        private static final Internal.EnumLiteMap<DeliveryType> internalValueMap;
        private static final DeliveryType[] VALUES;
        private final int value;

        @Override
        public final int getNumber() {
            if (this == UNRECOGNIZED) {
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }
            return this.value;
        }

        @Deprecated
        public static DeliveryType valueOf(int value) {
            return DeliveryType.forNumber(value);
        }

        public static DeliveryType forNumber(int value) {
            switch (value) {
                case 0: {
                    return UNKNOWN_DELIVERY_TYPE;
                }
                case 1: {
                    return INSTALL_TIME;
                }
                case 2: {
                    return ON_DEMAND;
                }
                case 3: {
                    return FAST_FOLLOW;
                }
            }
            return null;
        }

        public static Internal.EnumLiteMap<DeliveryType> internalGetValueMap() {
            return internalValueMap;
        }

        @Override
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            return DeliveryType.getDescriptor().getValues().get(this.ordinal());
        }

        @Override
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return DeliveryType.getDescriptor();
        }

        public static final Descriptors.EnumDescriptor getDescriptor() {
            return Commands.getDescriptor().getEnumTypes().get(0);
        }

        public static DeliveryType valueOf(Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != DeliveryType.getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            if (desc.getIndex() == -1) {
                return UNRECOGNIZED;
            }
            return VALUES[desc.getIndex()];
        }

        private DeliveryType(int value) {
            this.value = value;
        }

        static {
            internalValueMap = new Internal.EnumLiteMap<DeliveryType>(){

                @Override
                public DeliveryType findValueByNumber(int number) {
                    return DeliveryType.forNumber(number);
                }
            };
            VALUES = DeliveryType.values();
        }
    }
}

