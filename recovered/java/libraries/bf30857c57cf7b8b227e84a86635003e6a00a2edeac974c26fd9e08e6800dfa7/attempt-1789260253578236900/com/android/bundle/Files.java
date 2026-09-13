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

public final class Files {
    private static final Descriptors.Descriptor internal_static_android_bundle_Assets_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Assets_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_NativeLibraries_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_NativeLibraries_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ApexImages_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ApexImages_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_TargetedAssetsDirectory_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_TargetedAssetsDirectory_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_TargetedNativeDirectory_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_TargetedNativeDirectory_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_TargetedApexImage_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_TargetedApexImage_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private Files() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        Files.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u000bfiles.proto\u0012\u000eandroid.bundle\u001a\fconfig.proto\u001a\u000ftargeting.proto\"D\n\u0006Assets\u0012:\n\tdirectory\u0018\u0001 \u0003(\u000b2'.android.bundle.TargetedAssetsDirectory\"M\n\u000fNativeLibraries\u0012:\n\tdirectory\u0018\u0001 \u0003(\u000b2'.android.bundle.TargetedNativeDirectory\"\u0087\u0001\n\nApexImages\u00120\n\u0005image\u0018\u0001 \u0003(\u000b2!.android.bundle.TargetedApexImage\u0012G\n\u0018apex_embedded_apk_config\u0018\u0002 \u0003(\u000b2%.android.bundle.ApexEmbeddedApkConfig\"d\n\u0017TargetedAssetsDirectory\u0012\f\n\u0004path\u0018\u0001 \u0001(\t\u0012;\n\ttargetin", "g\u0018\u0002 \u0001(\u000b2(.android.bundle.AssetsDirectoryTargeting\"d\n\u0017TargetedNativeDirectory\u0012\f\n\u0004path\u0018\u0001 \u0001(\t\u0012;\n\ttargeting\u0018\u0002 \u0001(\u000b2(.android.bundle.NativeDirectoryTargeting\"q\n\u0011TargetedApexImage\u0012\f\n\u0004path\u0018\u0001 \u0001(\t\u0012\u0017\n\u000fbuild_info_path\u0018\u0003 \u0001(\t\u00125\n\ttargeting\u0018\u0002 \u0001(\u000b2\".android.bundle.ApexImageTargetingB\u0014\n\u0012com.android.bundleb\u0006proto3"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            @Override
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{Config.getDescriptor(), Targeting.getDescriptor()}, assigner);
        internal_static_android_bundle_Assets_descriptor = Files.getDescriptor().getMessageTypes().get(0);
        internal_static_android_bundle_Assets_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Assets_descriptor, new String[]{"Directory"});
        internal_static_android_bundle_NativeLibraries_descriptor = Files.getDescriptor().getMessageTypes().get(1);
        internal_static_android_bundle_NativeLibraries_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_NativeLibraries_descriptor, new String[]{"Directory"});
        internal_static_android_bundle_ApexImages_descriptor = Files.getDescriptor().getMessageTypes().get(2);
        internal_static_android_bundle_ApexImages_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ApexImages_descriptor, new String[]{"Image", "ApexEmbeddedApkConfig"});
        internal_static_android_bundle_TargetedAssetsDirectory_descriptor = Files.getDescriptor().getMessageTypes().get(3);
        internal_static_android_bundle_TargetedAssetsDirectory_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_TargetedAssetsDirectory_descriptor, new String[]{"Path", "Targeting"});
        internal_static_android_bundle_TargetedNativeDirectory_descriptor = Files.getDescriptor().getMessageTypes().get(4);
        internal_static_android_bundle_TargetedNativeDirectory_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_TargetedNativeDirectory_descriptor, new String[]{"Path", "Targeting"});
        internal_static_android_bundle_TargetedApexImage_descriptor = Files.getDescriptor().getMessageTypes().get(5);
        internal_static_android_bundle_TargetedApexImage_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_TargetedApexImage_descriptor, new String[]{"Path", "BuildInfoPath", "Targeting"});
        Config.getDescriptor();
        Targeting.getDescriptor();
    }

    public static final class TargetedApexImage
    extends GeneratedMessageV3
    implements TargetedApexImageOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int PATH_FIELD_NUMBER = 1;
        private volatile Object path_;
        public static final int BUILD_INFO_PATH_FIELD_NUMBER = 3;
        private volatile Object buildInfoPath_;
        public static final int TARGETING_FIELD_NUMBER = 2;
        private Targeting.ApexImageTargeting targeting_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final TargetedApexImage DEFAULT_INSTANCE = new TargetedApexImage();
        private static final Parser<TargetedApexImage> PARSER = new AbstractParser<TargetedApexImage>(){

            @Override
            public TargetedApexImage parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new TargetedApexImage(input, extensionRegistry);
            }
        };

        private TargetedApexImage(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private TargetedApexImage() {
            this.path_ = "";
            this.buildInfoPath_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private TargetedApexImage(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block12: while (!done) {
                    String s3;
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
                            s3 = input.readStringRequireUtf8();
                            this.path_ = s3;
                            continue block12;
                        }
                        case 18: {
                            Targeting.ApexImageTargeting.Builder subBuilder = null;
                            if (this.targeting_ != null) {
                                subBuilder = this.targeting_.toBuilder();
                            }
                            this.targeting_ = input.readMessage(Targeting.ApexImageTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block12;
                            subBuilder.mergeFrom(this.targeting_);
                            this.targeting_ = subBuilder.buildPartial();
                            continue block12;
                        }
                        case 26: 
                    }
                    s3 = input.readStringRequireUtf8();
                    this.buildInfoPath_ = s3;
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
            return internal_static_android_bundle_TargetedApexImage_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_TargetedApexImage_fieldAccessorTable.ensureFieldAccessorsInitialized(TargetedApexImage.class, Builder.class);
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
        public String getBuildInfoPath() {
            Object ref = this.buildInfoPath_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.buildInfoPath_ = s3;
            return s3;
        }

        @Override
        public ByteString getBuildInfoPathBytes() {
            Object ref = this.buildInfoPath_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.buildInfoPath_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasTargeting() {
            return this.targeting_ != null;
        }

        @Override
        public Targeting.ApexImageTargeting getTargeting() {
            return this.targeting_ == null ? Targeting.ApexImageTargeting.getDefaultInstance() : this.targeting_;
        }

        @Override
        public Targeting.ApexImageTargetingOrBuilder getTargetingOrBuilder() {
            return this.getTargeting();
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
            if (!this.getPathBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.path_);
            }
            if (this.targeting_ != null) {
                output.writeMessage(2, this.getTargeting());
            }
            if (!this.getBuildInfoPathBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 3, this.buildInfoPath_);
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
            if (!this.getPathBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.path_);
            }
            if (this.targeting_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getTargeting());
            }
            if (!this.getBuildInfoPathBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(3, this.buildInfoPath_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof TargetedApexImage)) {
                return super.equals(obj);
            }
            TargetedApexImage other = (TargetedApexImage)obj;
            boolean result = true;
            result = result && this.getPath().equals(other.getPath());
            result = result && this.getBuildInfoPath().equals(other.getBuildInfoPath());
            boolean bl = result = result && this.hasTargeting() == other.hasTargeting();
            if (this.hasTargeting()) {
                result = result && this.getTargeting().equals(other.getTargeting());
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
            hash = 19 * hash + TargetedApexImage.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getPath().hashCode();
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getBuildInfoPath().hashCode();
            if (this.hasTargeting()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getTargeting().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static TargetedApexImage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TargetedApexImage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TargetedApexImage parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TargetedApexImage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TargetedApexImage parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TargetedApexImage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TargetedApexImage parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static TargetedApexImage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static TargetedApexImage parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static TargetedApexImage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static TargetedApexImage parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static TargetedApexImage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return TargetedApexImage.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(TargetedApexImage prototype) {
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

        public static TargetedApexImage getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TargetedApexImage> parser() {
            return PARSER;
        }

        public Parser<TargetedApexImage> getParserForType() {
            return PARSER;
        }

        @Override
        public TargetedApexImage getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements TargetedApexImageOrBuilder {
            private Object path_ = "";
            private Object buildInfoPath_ = "";
            private Targeting.ApexImageTargeting targeting_ = null;
            private SingleFieldBuilderV3<Targeting.ApexImageTargeting, Targeting.ApexImageTargeting.Builder, Targeting.ApexImageTargetingOrBuilder> targetingBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_TargetedApexImage_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_TargetedApexImage_fieldAccessorTable.ensureFieldAccessorsInitialized(TargetedApexImage.class, Builder.class);
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
                this.path_ = "";
                this.buildInfoPath_ = "";
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = null;
                } else {
                    this.targeting_ = null;
                    this.targetingBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_TargetedApexImage_descriptor;
            }

            @Override
            public TargetedApexImage getDefaultInstanceForType() {
                return TargetedApexImage.getDefaultInstance();
            }

            @Override
            public TargetedApexImage build() {
                TargetedApexImage result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public TargetedApexImage buildPartial() {
                TargetedApexImage result = new TargetedApexImage(this);
                result.path_ = this.path_;
                result.buildInfoPath_ = this.buildInfoPath_;
                if (this.targetingBuilder_ == null) {
                    result.targeting_ = this.targeting_;
                } else {
                    result.targeting_ = this.targetingBuilder_.build();
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
                if (other instanceof TargetedApexImage) {
                    return this.mergeFrom((TargetedApexImage)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(TargetedApexImage other) {
                if (other == TargetedApexImage.getDefaultInstance()) {
                    return this;
                }
                if (!other.getPath().isEmpty()) {
                    this.path_ = other.path_;
                    this.onChanged();
                }
                if (!other.getBuildInfoPath().isEmpty()) {
                    this.buildInfoPath_ = other.buildInfoPath_;
                    this.onChanged();
                }
                if (other.hasTargeting()) {
                    this.mergeTargeting(other.getTargeting());
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
                TargetedApexImage parsedMessage = null;
                try {
                    parsedMessage = (TargetedApexImage)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (TargetedApexImage)e2.getUnfinishedMessage();
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
                this.path_ = TargetedApexImage.getDefaultInstance().getPath();
                this.onChanged();
                return this;
            }

            public Builder setPathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                TargetedApexImage.checkByteStringIsUtf8(value);
                this.path_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public String getBuildInfoPath() {
                Object ref = this.buildInfoPath_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.buildInfoPath_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getBuildInfoPathBytes() {
                Object ref = this.buildInfoPath_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.buildInfoPath_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setBuildInfoPath(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.buildInfoPath_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearBuildInfoPath() {
                this.buildInfoPath_ = TargetedApexImage.getDefaultInstance().getBuildInfoPath();
                this.onChanged();
                return this;
            }

            public Builder setBuildInfoPathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                TargetedApexImage.checkByteStringIsUtf8(value);
                this.buildInfoPath_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasTargeting() {
                return this.targetingBuilder_ != null || this.targeting_ != null;
            }

            @Override
            public Targeting.ApexImageTargeting getTargeting() {
                if (this.targetingBuilder_ == null) {
                    return this.targeting_ == null ? Targeting.ApexImageTargeting.getDefaultInstance() : this.targeting_;
                }
                return this.targetingBuilder_.getMessage();
            }

            public Builder setTargeting(Targeting.ApexImageTargeting value) {
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

            public Builder setTargeting(Targeting.ApexImageTargeting.Builder builderForValue) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.targetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTargeting(Targeting.ApexImageTargeting value) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = this.targeting_ != null ? Targeting.ApexImageTargeting.newBuilder(this.targeting_).mergeFrom(value).buildPartial() : value;
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

            public Targeting.ApexImageTargeting.Builder getTargetingBuilder() {
                this.onChanged();
                return this.getTargetingFieldBuilder().getBuilder();
            }

            @Override
            public Targeting.ApexImageTargetingOrBuilder getTargetingOrBuilder() {
                if (this.targetingBuilder_ != null) {
                    return this.targetingBuilder_.getMessageOrBuilder();
                }
                return this.targeting_ == null ? Targeting.ApexImageTargeting.getDefaultInstance() : this.targeting_;
            }

            private SingleFieldBuilderV3<Targeting.ApexImageTargeting, Targeting.ApexImageTargeting.Builder, Targeting.ApexImageTargetingOrBuilder> getTargetingFieldBuilder() {
                if (this.targetingBuilder_ == null) {
                    this.targetingBuilder_ = new SingleFieldBuilderV3(this.getTargeting(), this.getParentForChildren(), this.isClean());
                    this.targeting_ = null;
                }
                return this.targetingBuilder_;
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

    public static interface TargetedApexImageOrBuilder
    extends MessageOrBuilder {
        public String getPath();

        public ByteString getPathBytes();

        public String getBuildInfoPath();

        public ByteString getBuildInfoPathBytes();

        public boolean hasTargeting();

        public Targeting.ApexImageTargeting getTargeting();

        public Targeting.ApexImageTargetingOrBuilder getTargetingOrBuilder();
    }

    public static final class TargetedNativeDirectory
    extends GeneratedMessageV3
    implements TargetedNativeDirectoryOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int PATH_FIELD_NUMBER = 1;
        private volatile Object path_;
        public static final int TARGETING_FIELD_NUMBER = 2;
        private Targeting.NativeDirectoryTargeting targeting_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final TargetedNativeDirectory DEFAULT_INSTANCE = new TargetedNativeDirectory();
        private static final Parser<TargetedNativeDirectory> PARSER = new AbstractParser<TargetedNativeDirectory>(){

            @Override
            public TargetedNativeDirectory parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new TargetedNativeDirectory(input, extensionRegistry);
            }
        };

        private TargetedNativeDirectory(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private TargetedNativeDirectory() {
            this.path_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private TargetedNativeDirectory(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.path_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    Targeting.NativeDirectoryTargeting.Builder subBuilder = null;
                    if (this.targeting_ != null) {
                        subBuilder = this.targeting_.toBuilder();
                    }
                    this.targeting_ = input.readMessage(Targeting.NativeDirectoryTargeting.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    subBuilder.mergeFrom(this.targeting_);
                    this.targeting_ = subBuilder.buildPartial();
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
            return internal_static_android_bundle_TargetedNativeDirectory_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_TargetedNativeDirectory_fieldAccessorTable.ensureFieldAccessorsInitialized(TargetedNativeDirectory.class, Builder.class);
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
        public boolean hasTargeting() {
            return this.targeting_ != null;
        }

        @Override
        public Targeting.NativeDirectoryTargeting getTargeting() {
            return this.targeting_ == null ? Targeting.NativeDirectoryTargeting.getDefaultInstance() : this.targeting_;
        }

        @Override
        public Targeting.NativeDirectoryTargetingOrBuilder getTargetingOrBuilder() {
            return this.getTargeting();
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
            if (!this.getPathBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.path_);
            }
            if (this.targeting_ != null) {
                output.writeMessage(2, this.getTargeting());
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
            if (!this.getPathBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.path_);
            }
            if (this.targeting_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getTargeting());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof TargetedNativeDirectory)) {
                return super.equals(obj);
            }
            TargetedNativeDirectory other = (TargetedNativeDirectory)obj;
            boolean result = true;
            result = result && this.getPath().equals(other.getPath());
            boolean bl = result = result && this.hasTargeting() == other.hasTargeting();
            if (this.hasTargeting()) {
                result = result && this.getTargeting().equals(other.getTargeting());
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
            hash = 19 * hash + TargetedNativeDirectory.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getPath().hashCode();
            if (this.hasTargeting()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getTargeting().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static TargetedNativeDirectory parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TargetedNativeDirectory parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TargetedNativeDirectory parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TargetedNativeDirectory parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TargetedNativeDirectory parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TargetedNativeDirectory parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TargetedNativeDirectory parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static TargetedNativeDirectory parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static TargetedNativeDirectory parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static TargetedNativeDirectory parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static TargetedNativeDirectory parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static TargetedNativeDirectory parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return TargetedNativeDirectory.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(TargetedNativeDirectory prototype) {
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

        public static TargetedNativeDirectory getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TargetedNativeDirectory> parser() {
            return PARSER;
        }

        public Parser<TargetedNativeDirectory> getParserForType() {
            return PARSER;
        }

        @Override
        public TargetedNativeDirectory getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements TargetedNativeDirectoryOrBuilder {
            private Object path_ = "";
            private Targeting.NativeDirectoryTargeting targeting_ = null;
            private SingleFieldBuilderV3<Targeting.NativeDirectoryTargeting, Targeting.NativeDirectoryTargeting.Builder, Targeting.NativeDirectoryTargetingOrBuilder> targetingBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_TargetedNativeDirectory_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_TargetedNativeDirectory_fieldAccessorTable.ensureFieldAccessorsInitialized(TargetedNativeDirectory.class, Builder.class);
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
                this.path_ = "";
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = null;
                } else {
                    this.targeting_ = null;
                    this.targetingBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_TargetedNativeDirectory_descriptor;
            }

            @Override
            public TargetedNativeDirectory getDefaultInstanceForType() {
                return TargetedNativeDirectory.getDefaultInstance();
            }

            @Override
            public TargetedNativeDirectory build() {
                TargetedNativeDirectory result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public TargetedNativeDirectory buildPartial() {
                TargetedNativeDirectory result = new TargetedNativeDirectory(this);
                result.path_ = this.path_;
                if (this.targetingBuilder_ == null) {
                    result.targeting_ = this.targeting_;
                } else {
                    result.targeting_ = this.targetingBuilder_.build();
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
                if (other instanceof TargetedNativeDirectory) {
                    return this.mergeFrom((TargetedNativeDirectory)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(TargetedNativeDirectory other) {
                if (other == TargetedNativeDirectory.getDefaultInstance()) {
                    return this;
                }
                if (!other.getPath().isEmpty()) {
                    this.path_ = other.path_;
                    this.onChanged();
                }
                if (other.hasTargeting()) {
                    this.mergeTargeting(other.getTargeting());
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
                TargetedNativeDirectory parsedMessage = null;
                try {
                    parsedMessage = (TargetedNativeDirectory)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (TargetedNativeDirectory)e2.getUnfinishedMessage();
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
                this.path_ = TargetedNativeDirectory.getDefaultInstance().getPath();
                this.onChanged();
                return this;
            }

            public Builder setPathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                TargetedNativeDirectory.checkByteStringIsUtf8(value);
                this.path_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasTargeting() {
                return this.targetingBuilder_ != null || this.targeting_ != null;
            }

            @Override
            public Targeting.NativeDirectoryTargeting getTargeting() {
                if (this.targetingBuilder_ == null) {
                    return this.targeting_ == null ? Targeting.NativeDirectoryTargeting.getDefaultInstance() : this.targeting_;
                }
                return this.targetingBuilder_.getMessage();
            }

            public Builder setTargeting(Targeting.NativeDirectoryTargeting value) {
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

            public Builder setTargeting(Targeting.NativeDirectoryTargeting.Builder builderForValue) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.targetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTargeting(Targeting.NativeDirectoryTargeting value) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = this.targeting_ != null ? Targeting.NativeDirectoryTargeting.newBuilder(this.targeting_).mergeFrom(value).buildPartial() : value;
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

            public Targeting.NativeDirectoryTargeting.Builder getTargetingBuilder() {
                this.onChanged();
                return this.getTargetingFieldBuilder().getBuilder();
            }

            @Override
            public Targeting.NativeDirectoryTargetingOrBuilder getTargetingOrBuilder() {
                if (this.targetingBuilder_ != null) {
                    return this.targetingBuilder_.getMessageOrBuilder();
                }
                return this.targeting_ == null ? Targeting.NativeDirectoryTargeting.getDefaultInstance() : this.targeting_;
            }

            private SingleFieldBuilderV3<Targeting.NativeDirectoryTargeting, Targeting.NativeDirectoryTargeting.Builder, Targeting.NativeDirectoryTargetingOrBuilder> getTargetingFieldBuilder() {
                if (this.targetingBuilder_ == null) {
                    this.targetingBuilder_ = new SingleFieldBuilderV3(this.getTargeting(), this.getParentForChildren(), this.isClean());
                    this.targeting_ = null;
                }
                return this.targetingBuilder_;
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

    public static interface TargetedNativeDirectoryOrBuilder
    extends MessageOrBuilder {
        public String getPath();

        public ByteString getPathBytes();

        public boolean hasTargeting();

        public Targeting.NativeDirectoryTargeting getTargeting();

        public Targeting.NativeDirectoryTargetingOrBuilder getTargetingOrBuilder();
    }

    public static final class TargetedAssetsDirectory
    extends GeneratedMessageV3
    implements TargetedAssetsDirectoryOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int PATH_FIELD_NUMBER = 1;
        private volatile Object path_;
        public static final int TARGETING_FIELD_NUMBER = 2;
        private Targeting.AssetsDirectoryTargeting targeting_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final TargetedAssetsDirectory DEFAULT_INSTANCE = new TargetedAssetsDirectory();
        private static final Parser<TargetedAssetsDirectory> PARSER = new AbstractParser<TargetedAssetsDirectory>(){

            @Override
            public TargetedAssetsDirectory parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new TargetedAssetsDirectory(input, extensionRegistry);
            }
        };

        private TargetedAssetsDirectory(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private TargetedAssetsDirectory() {
            this.path_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private TargetedAssetsDirectory(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.path_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    Targeting.AssetsDirectoryTargeting.Builder subBuilder = null;
                    if (this.targeting_ != null) {
                        subBuilder = this.targeting_.toBuilder();
                    }
                    this.targeting_ = input.readMessage(Targeting.AssetsDirectoryTargeting.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    subBuilder.mergeFrom(this.targeting_);
                    this.targeting_ = subBuilder.buildPartial();
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
            return internal_static_android_bundle_TargetedAssetsDirectory_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_TargetedAssetsDirectory_fieldAccessorTable.ensureFieldAccessorsInitialized(TargetedAssetsDirectory.class, Builder.class);
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
        public boolean hasTargeting() {
            return this.targeting_ != null;
        }

        @Override
        public Targeting.AssetsDirectoryTargeting getTargeting() {
            return this.targeting_ == null ? Targeting.AssetsDirectoryTargeting.getDefaultInstance() : this.targeting_;
        }

        @Override
        public Targeting.AssetsDirectoryTargetingOrBuilder getTargetingOrBuilder() {
            return this.getTargeting();
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
            if (!this.getPathBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.path_);
            }
            if (this.targeting_ != null) {
                output.writeMessage(2, this.getTargeting());
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
            if (!this.getPathBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.path_);
            }
            if (this.targeting_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getTargeting());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof TargetedAssetsDirectory)) {
                return super.equals(obj);
            }
            TargetedAssetsDirectory other = (TargetedAssetsDirectory)obj;
            boolean result = true;
            result = result && this.getPath().equals(other.getPath());
            boolean bl = result = result && this.hasTargeting() == other.hasTargeting();
            if (this.hasTargeting()) {
                result = result && this.getTargeting().equals(other.getTargeting());
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
            hash = 19 * hash + TargetedAssetsDirectory.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getPath().hashCode();
            if (this.hasTargeting()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getTargeting().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static TargetedAssetsDirectory parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TargetedAssetsDirectory parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TargetedAssetsDirectory parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TargetedAssetsDirectory parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TargetedAssetsDirectory parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TargetedAssetsDirectory parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TargetedAssetsDirectory parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static TargetedAssetsDirectory parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static TargetedAssetsDirectory parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static TargetedAssetsDirectory parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static TargetedAssetsDirectory parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static TargetedAssetsDirectory parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return TargetedAssetsDirectory.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(TargetedAssetsDirectory prototype) {
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

        public static TargetedAssetsDirectory getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TargetedAssetsDirectory> parser() {
            return PARSER;
        }

        public Parser<TargetedAssetsDirectory> getParserForType() {
            return PARSER;
        }

        @Override
        public TargetedAssetsDirectory getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements TargetedAssetsDirectoryOrBuilder {
            private Object path_ = "";
            private Targeting.AssetsDirectoryTargeting targeting_ = null;
            private SingleFieldBuilderV3<Targeting.AssetsDirectoryTargeting, Targeting.AssetsDirectoryTargeting.Builder, Targeting.AssetsDirectoryTargetingOrBuilder> targetingBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_TargetedAssetsDirectory_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_TargetedAssetsDirectory_fieldAccessorTable.ensureFieldAccessorsInitialized(TargetedAssetsDirectory.class, Builder.class);
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
                this.path_ = "";
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = null;
                } else {
                    this.targeting_ = null;
                    this.targetingBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_TargetedAssetsDirectory_descriptor;
            }

            @Override
            public TargetedAssetsDirectory getDefaultInstanceForType() {
                return TargetedAssetsDirectory.getDefaultInstance();
            }

            @Override
            public TargetedAssetsDirectory build() {
                TargetedAssetsDirectory result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public TargetedAssetsDirectory buildPartial() {
                TargetedAssetsDirectory result = new TargetedAssetsDirectory(this);
                result.path_ = this.path_;
                if (this.targetingBuilder_ == null) {
                    result.targeting_ = this.targeting_;
                } else {
                    result.targeting_ = this.targetingBuilder_.build();
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
                if (other instanceof TargetedAssetsDirectory) {
                    return this.mergeFrom((TargetedAssetsDirectory)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(TargetedAssetsDirectory other) {
                if (other == TargetedAssetsDirectory.getDefaultInstance()) {
                    return this;
                }
                if (!other.getPath().isEmpty()) {
                    this.path_ = other.path_;
                    this.onChanged();
                }
                if (other.hasTargeting()) {
                    this.mergeTargeting(other.getTargeting());
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
                TargetedAssetsDirectory parsedMessage = null;
                try {
                    parsedMessage = (TargetedAssetsDirectory)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (TargetedAssetsDirectory)e2.getUnfinishedMessage();
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
                this.path_ = TargetedAssetsDirectory.getDefaultInstance().getPath();
                this.onChanged();
                return this;
            }

            public Builder setPathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                TargetedAssetsDirectory.checkByteStringIsUtf8(value);
                this.path_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasTargeting() {
                return this.targetingBuilder_ != null || this.targeting_ != null;
            }

            @Override
            public Targeting.AssetsDirectoryTargeting getTargeting() {
                if (this.targetingBuilder_ == null) {
                    return this.targeting_ == null ? Targeting.AssetsDirectoryTargeting.getDefaultInstance() : this.targeting_;
                }
                return this.targetingBuilder_.getMessage();
            }

            public Builder setTargeting(Targeting.AssetsDirectoryTargeting value) {
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

            public Builder setTargeting(Targeting.AssetsDirectoryTargeting.Builder builderForValue) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.targetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTargeting(Targeting.AssetsDirectoryTargeting value) {
                if (this.targetingBuilder_ == null) {
                    this.targeting_ = this.targeting_ != null ? Targeting.AssetsDirectoryTargeting.newBuilder(this.targeting_).mergeFrom(value).buildPartial() : value;
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

            public Targeting.AssetsDirectoryTargeting.Builder getTargetingBuilder() {
                this.onChanged();
                return this.getTargetingFieldBuilder().getBuilder();
            }

            @Override
            public Targeting.AssetsDirectoryTargetingOrBuilder getTargetingOrBuilder() {
                if (this.targetingBuilder_ != null) {
                    return this.targetingBuilder_.getMessageOrBuilder();
                }
                return this.targeting_ == null ? Targeting.AssetsDirectoryTargeting.getDefaultInstance() : this.targeting_;
            }

            private SingleFieldBuilderV3<Targeting.AssetsDirectoryTargeting, Targeting.AssetsDirectoryTargeting.Builder, Targeting.AssetsDirectoryTargetingOrBuilder> getTargetingFieldBuilder() {
                if (this.targetingBuilder_ == null) {
                    this.targetingBuilder_ = new SingleFieldBuilderV3(this.getTargeting(), this.getParentForChildren(), this.isClean());
                    this.targeting_ = null;
                }
                return this.targetingBuilder_;
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

    public static interface TargetedAssetsDirectoryOrBuilder
    extends MessageOrBuilder {
        public String getPath();

        public ByteString getPathBytes();

        public boolean hasTargeting();

        public Targeting.AssetsDirectoryTargeting getTargeting();

        public Targeting.AssetsDirectoryTargetingOrBuilder getTargetingOrBuilder();
    }

    public static final class ApexImages
    extends GeneratedMessageV3
    implements ApexImagesOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int IMAGE_FIELD_NUMBER = 1;
        private List<TargetedApexImage> image_;
        public static final int APEX_EMBEDDED_APK_CONFIG_FIELD_NUMBER = 2;
        private List<Config.ApexEmbeddedApkConfig> apexEmbeddedApkConfig_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ApexImages DEFAULT_INSTANCE = new ApexImages();
        private static final Parser<ApexImages> PARSER = new AbstractParser<ApexImages>(){

            @Override
            public ApexImages parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ApexImages(input, extensionRegistry);
            }
        };

        private ApexImages(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ApexImages() {
            this.image_ = Collections.emptyList();
            this.apexEmbeddedApkConfig_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ApexImages(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.image_ = new ArrayList<TargetedApexImage>();
                                mutable_bitField0_ |= 1;
                            }
                            this.image_.add(input.readMessage(TargetedApexImage.parser(), extensionRegistry));
                            continue block11;
                        }
                        case 18: 
                    }
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.apexEmbeddedApkConfig_ = new ArrayList<Config.ApexEmbeddedApkConfig>();
                        mutable_bitField0_ |= 2;
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
                    this.image_ = Collections.unmodifiableList(this.image_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.apexEmbeddedApkConfig_ = Collections.unmodifiableList(this.apexEmbeddedApkConfig_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_ApexImages_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ApexImages_fieldAccessorTable.ensureFieldAccessorsInitialized(ApexImages.class, Builder.class);
        }

        @Override
        public List<TargetedApexImage> getImageList() {
            return this.image_;
        }

        @Override
        public List<? extends TargetedApexImageOrBuilder> getImageOrBuilderList() {
            return this.image_;
        }

        @Override
        public int getImageCount() {
            return this.image_.size();
        }

        @Override
        public TargetedApexImage getImage(int index) {
            return this.image_.get(index);
        }

        @Override
        public TargetedApexImageOrBuilder getImageOrBuilder(int index) {
            return this.image_.get(index);
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
            int i2;
            for (i2 = 0; i2 < this.image_.size(); ++i2) {
                output.writeMessage(1, this.image_.get(i2));
            }
            for (i2 = 0; i2 < this.apexEmbeddedApkConfig_.size(); ++i2) {
                output.writeMessage(2, this.apexEmbeddedApkConfig_.get(i2));
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
            for (i2 = 0; i2 < this.image_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.image_.get(i2));
            }
            for (i2 = 0; i2 < this.apexEmbeddedApkConfig_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.apexEmbeddedApkConfig_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ApexImages)) {
                return super.equals(obj);
            }
            ApexImages other = (ApexImages)obj;
            boolean result = true;
            result = result && this.getImageList().equals(other.getImageList());
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
            hash = 19 * hash + ApexImages.getDescriptor().hashCode();
            if (this.getImageCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getImageList().hashCode();
            }
            if (this.getApexEmbeddedApkConfigCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getApexEmbeddedApkConfigList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ApexImages parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexImages parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexImages parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexImages parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexImages parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexImages parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexImages parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApexImages parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApexImages parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ApexImages parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApexImages parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApexImages parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ApexImages.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ApexImages prototype) {
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

        public static ApexImages getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ApexImages> parser() {
            return PARSER;
        }

        public Parser<ApexImages> getParserForType() {
            return PARSER;
        }

        @Override
        public ApexImages getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ApexImagesOrBuilder {
            private int bitField0_;
            private List<TargetedApexImage> image_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<TargetedApexImage, TargetedApexImage.Builder, TargetedApexImageOrBuilder> imageBuilder_;
            private List<Config.ApexEmbeddedApkConfig> apexEmbeddedApkConfig_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Config.ApexEmbeddedApkConfig, Config.ApexEmbeddedApkConfig.Builder, Config.ApexEmbeddedApkConfigOrBuilder> apexEmbeddedApkConfigBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ApexImages_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ApexImages_fieldAccessorTable.ensureFieldAccessorsInitialized(ApexImages.class, Builder.class);
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
                    this.getImageFieldBuilder();
                    this.getApexEmbeddedApkConfigFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.imageBuilder_ == null) {
                    this.image_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.imageBuilder_.clear();
                }
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    this.apexEmbeddedApkConfig_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.apexEmbeddedApkConfigBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ApexImages_descriptor;
            }

            @Override
            public ApexImages getDefaultInstanceForType() {
                return ApexImages.getDefaultInstance();
            }

            @Override
            public ApexImages build() {
                ApexImages result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ApexImages buildPartial() {
                ApexImages result = new ApexImages(this);
                int from_bitField0_ = this.bitField0_;
                if (this.imageBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.image_ = Collections.unmodifiableList(this.image_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.image_ = this.image_;
                } else {
                    result.image_ = this.imageBuilder_.build();
                }
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.apexEmbeddedApkConfig_ = Collections.unmodifiableList(this.apexEmbeddedApkConfig_);
                        this.bitField0_ &= 0xFFFFFFFD;
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
                if (other instanceof ApexImages) {
                    return this.mergeFrom((ApexImages)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ApexImages other) {
                if (other == ApexImages.getDefaultInstance()) {
                    return this;
                }
                if (this.imageBuilder_ == null) {
                    if (!other.image_.isEmpty()) {
                        if (this.image_.isEmpty()) {
                            this.image_ = other.image_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureImageIsMutable();
                            this.image_.addAll(other.image_);
                        }
                        this.onChanged();
                    }
                } else if (!other.image_.isEmpty()) {
                    if (this.imageBuilder_.isEmpty()) {
                        this.imageBuilder_.dispose();
                        this.imageBuilder_ = null;
                        this.image_ = other.image_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.imageBuilder_ = alwaysUseFieldBuilders ? this.getImageFieldBuilder() : null;
                    } else {
                        this.imageBuilder_.addAllMessages(other.image_);
                    }
                }
                if (this.apexEmbeddedApkConfigBuilder_ == null) {
                    if (!other.apexEmbeddedApkConfig_.isEmpty()) {
                        if (this.apexEmbeddedApkConfig_.isEmpty()) {
                            this.apexEmbeddedApkConfig_ = other.apexEmbeddedApkConfig_;
                            this.bitField0_ &= 0xFFFFFFFD;
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
                        this.bitField0_ &= 0xFFFFFFFD;
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
                ApexImages parsedMessage = null;
                try {
                    parsedMessage = (ApexImages)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ApexImages)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureImageIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.image_ = new ArrayList<TargetedApexImage>(this.image_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<TargetedApexImage> getImageList() {
                if (this.imageBuilder_ == null) {
                    return Collections.unmodifiableList(this.image_);
                }
                return this.imageBuilder_.getMessageList();
            }

            @Override
            public int getImageCount() {
                if (this.imageBuilder_ == null) {
                    return this.image_.size();
                }
                return this.imageBuilder_.getCount();
            }

            @Override
            public TargetedApexImage getImage(int index) {
                if (this.imageBuilder_ == null) {
                    return this.image_.get(index);
                }
                return this.imageBuilder_.getMessage(index);
            }

            public Builder setImage(int index, TargetedApexImage value) {
                if (this.imageBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureImageIsMutable();
                    this.image_.set(index, value);
                    this.onChanged();
                } else {
                    this.imageBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setImage(int index, TargetedApexImage.Builder builderForValue) {
                if (this.imageBuilder_ == null) {
                    this.ensureImageIsMutable();
                    this.image_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.imageBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addImage(TargetedApexImage value) {
                if (this.imageBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureImageIsMutable();
                    this.image_.add(value);
                    this.onChanged();
                } else {
                    this.imageBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addImage(int index, TargetedApexImage value) {
                if (this.imageBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureImageIsMutable();
                    this.image_.add(index, value);
                    this.onChanged();
                } else {
                    this.imageBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addImage(TargetedApexImage.Builder builderForValue) {
                if (this.imageBuilder_ == null) {
                    this.ensureImageIsMutable();
                    this.image_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.imageBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addImage(int index, TargetedApexImage.Builder builderForValue) {
                if (this.imageBuilder_ == null) {
                    this.ensureImageIsMutable();
                    this.image_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.imageBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllImage(Iterable<? extends TargetedApexImage> values2) {
                if (this.imageBuilder_ == null) {
                    this.ensureImageIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.image_);
                    this.onChanged();
                } else {
                    this.imageBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearImage() {
                if (this.imageBuilder_ == null) {
                    this.image_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.imageBuilder_.clear();
                }
                return this;
            }

            public Builder removeImage(int index) {
                if (this.imageBuilder_ == null) {
                    this.ensureImageIsMutable();
                    this.image_.remove(index);
                    this.onChanged();
                } else {
                    this.imageBuilder_.remove(index);
                }
                return this;
            }

            public TargetedApexImage.Builder getImageBuilder(int index) {
                return this.getImageFieldBuilder().getBuilder(index);
            }

            @Override
            public TargetedApexImageOrBuilder getImageOrBuilder(int index) {
                if (this.imageBuilder_ == null) {
                    return this.image_.get(index);
                }
                return this.imageBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends TargetedApexImageOrBuilder> getImageOrBuilderList() {
                if (this.imageBuilder_ != null) {
                    return this.imageBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.image_);
            }

            public TargetedApexImage.Builder addImageBuilder() {
                return this.getImageFieldBuilder().addBuilder(TargetedApexImage.getDefaultInstance());
            }

            public TargetedApexImage.Builder addImageBuilder(int index) {
                return this.getImageFieldBuilder().addBuilder(index, TargetedApexImage.getDefaultInstance());
            }

            public List<TargetedApexImage.Builder> getImageBuilderList() {
                return this.getImageFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<TargetedApexImage, TargetedApexImage.Builder, TargetedApexImageOrBuilder> getImageFieldBuilder() {
                if (this.imageBuilder_ == null) {
                    this.imageBuilder_ = new RepeatedFieldBuilderV3(this.image_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.image_ = null;
                }
                return this.imageBuilder_;
            }

            private void ensureApexEmbeddedApkConfigIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.apexEmbeddedApkConfig_ = new ArrayList<Config.ApexEmbeddedApkConfig>(this.apexEmbeddedApkConfig_);
                    this.bitField0_ |= 2;
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
                    this.bitField0_ &= 0xFFFFFFFD;
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
                    this.apexEmbeddedApkConfigBuilder_ = new RepeatedFieldBuilderV3(this.apexEmbeddedApkConfig_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
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

    public static interface ApexImagesOrBuilder
    extends MessageOrBuilder {
        public List<TargetedApexImage> getImageList();

        public TargetedApexImage getImage(int var1);

        public int getImageCount();

        public List<? extends TargetedApexImageOrBuilder> getImageOrBuilderList();

        public TargetedApexImageOrBuilder getImageOrBuilder(int var1);

        public List<Config.ApexEmbeddedApkConfig> getApexEmbeddedApkConfigList();

        public Config.ApexEmbeddedApkConfig getApexEmbeddedApkConfig(int var1);

        public int getApexEmbeddedApkConfigCount();

        public List<? extends Config.ApexEmbeddedApkConfigOrBuilder> getApexEmbeddedApkConfigOrBuilderList();

        public Config.ApexEmbeddedApkConfigOrBuilder getApexEmbeddedApkConfigOrBuilder(int var1);
    }

    public static final class NativeLibraries
    extends GeneratedMessageV3
    implements NativeLibrariesOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int DIRECTORY_FIELD_NUMBER = 1;
        private List<TargetedNativeDirectory> directory_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final NativeLibraries DEFAULT_INSTANCE = new NativeLibraries();
        private static final Parser<NativeLibraries> PARSER = new AbstractParser<NativeLibraries>(){

            @Override
            public NativeLibraries parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new NativeLibraries(input, extensionRegistry);
            }
        };

        private NativeLibraries(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private NativeLibraries() {
            this.directory_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private NativeLibraries(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.directory_ = new ArrayList<TargetedNativeDirectory>();
                        mutable_bitField0_ |= true;
                    }
                    this.directory_.add(input.readMessage(TargetedNativeDirectory.parser(), extensionRegistry));
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
                    this.directory_ = Collections.unmodifiableList(this.directory_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_NativeLibraries_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_NativeLibraries_fieldAccessorTable.ensureFieldAccessorsInitialized(NativeLibraries.class, Builder.class);
        }

        @Override
        public List<TargetedNativeDirectory> getDirectoryList() {
            return this.directory_;
        }

        @Override
        public List<? extends TargetedNativeDirectoryOrBuilder> getDirectoryOrBuilderList() {
            return this.directory_;
        }

        @Override
        public int getDirectoryCount() {
            return this.directory_.size();
        }

        @Override
        public TargetedNativeDirectory getDirectory(int index) {
            return this.directory_.get(index);
        }

        @Override
        public TargetedNativeDirectoryOrBuilder getDirectoryOrBuilder(int index) {
            return this.directory_.get(index);
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
            for (int i2 = 0; i2 < this.directory_.size(); ++i2) {
                output.writeMessage(1, this.directory_.get(i2));
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
            for (int i2 = 0; i2 < this.directory_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.directory_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof NativeLibraries)) {
                return super.equals(obj);
            }
            NativeLibraries other = (NativeLibraries)obj;
            boolean result = true;
            result = result && this.getDirectoryList().equals(other.getDirectoryList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + NativeLibraries.getDescriptor().hashCode();
            if (this.getDirectoryCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getDirectoryList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static NativeLibraries parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static NativeLibraries parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static NativeLibraries parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static NativeLibraries parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static NativeLibraries parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static NativeLibraries parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static NativeLibraries parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static NativeLibraries parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static NativeLibraries parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static NativeLibraries parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static NativeLibraries parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static NativeLibraries parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return NativeLibraries.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(NativeLibraries prototype) {
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

        public static NativeLibraries getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<NativeLibraries> parser() {
            return PARSER;
        }

        public Parser<NativeLibraries> getParserForType() {
            return PARSER;
        }

        @Override
        public NativeLibraries getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements NativeLibrariesOrBuilder {
            private int bitField0_;
            private List<TargetedNativeDirectory> directory_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<TargetedNativeDirectory, TargetedNativeDirectory.Builder, TargetedNativeDirectoryOrBuilder> directoryBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_NativeLibraries_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_NativeLibraries_fieldAccessorTable.ensureFieldAccessorsInitialized(NativeLibraries.class, Builder.class);
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
                    this.getDirectoryFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.directoryBuilder_ == null) {
                    this.directory_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.directoryBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_NativeLibraries_descriptor;
            }

            @Override
            public NativeLibraries getDefaultInstanceForType() {
                return NativeLibraries.getDefaultInstance();
            }

            @Override
            public NativeLibraries build() {
                NativeLibraries result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public NativeLibraries buildPartial() {
                NativeLibraries result = new NativeLibraries(this);
                int from_bitField0_ = this.bitField0_;
                if (this.directoryBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.directory_ = Collections.unmodifiableList(this.directory_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.directory_ = this.directory_;
                } else {
                    result.directory_ = this.directoryBuilder_.build();
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
                if (other instanceof NativeLibraries) {
                    return this.mergeFrom((NativeLibraries)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(NativeLibraries other) {
                if (other == NativeLibraries.getDefaultInstance()) {
                    return this;
                }
                if (this.directoryBuilder_ == null) {
                    if (!other.directory_.isEmpty()) {
                        if (this.directory_.isEmpty()) {
                            this.directory_ = other.directory_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureDirectoryIsMutable();
                            this.directory_.addAll(other.directory_);
                        }
                        this.onChanged();
                    }
                } else if (!other.directory_.isEmpty()) {
                    if (this.directoryBuilder_.isEmpty()) {
                        this.directoryBuilder_.dispose();
                        this.directoryBuilder_ = null;
                        this.directory_ = other.directory_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.directoryBuilder_ = alwaysUseFieldBuilders ? this.getDirectoryFieldBuilder() : null;
                    } else {
                        this.directoryBuilder_.addAllMessages(other.directory_);
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
                NativeLibraries parsedMessage = null;
                try {
                    parsedMessage = (NativeLibraries)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (NativeLibraries)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureDirectoryIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.directory_ = new ArrayList<TargetedNativeDirectory>(this.directory_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<TargetedNativeDirectory> getDirectoryList() {
                if (this.directoryBuilder_ == null) {
                    return Collections.unmodifiableList(this.directory_);
                }
                return this.directoryBuilder_.getMessageList();
            }

            @Override
            public int getDirectoryCount() {
                if (this.directoryBuilder_ == null) {
                    return this.directory_.size();
                }
                return this.directoryBuilder_.getCount();
            }

            @Override
            public TargetedNativeDirectory getDirectory(int index) {
                if (this.directoryBuilder_ == null) {
                    return this.directory_.get(index);
                }
                return this.directoryBuilder_.getMessage(index);
            }

            public Builder setDirectory(int index, TargetedNativeDirectory value) {
                if (this.directoryBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDirectoryIsMutable();
                    this.directory_.set(index, value);
                    this.onChanged();
                } else {
                    this.directoryBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setDirectory(int index, TargetedNativeDirectory.Builder builderForValue) {
                if (this.directoryBuilder_ == null) {
                    this.ensureDirectoryIsMutable();
                    this.directory_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.directoryBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addDirectory(TargetedNativeDirectory value) {
                if (this.directoryBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDirectoryIsMutable();
                    this.directory_.add(value);
                    this.onChanged();
                } else {
                    this.directoryBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addDirectory(int index, TargetedNativeDirectory value) {
                if (this.directoryBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDirectoryIsMutable();
                    this.directory_.add(index, value);
                    this.onChanged();
                } else {
                    this.directoryBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addDirectory(TargetedNativeDirectory.Builder builderForValue) {
                if (this.directoryBuilder_ == null) {
                    this.ensureDirectoryIsMutable();
                    this.directory_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.directoryBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addDirectory(int index, TargetedNativeDirectory.Builder builderForValue) {
                if (this.directoryBuilder_ == null) {
                    this.ensureDirectoryIsMutable();
                    this.directory_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.directoryBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllDirectory(Iterable<? extends TargetedNativeDirectory> values2) {
                if (this.directoryBuilder_ == null) {
                    this.ensureDirectoryIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.directory_);
                    this.onChanged();
                } else {
                    this.directoryBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearDirectory() {
                if (this.directoryBuilder_ == null) {
                    this.directory_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.directoryBuilder_.clear();
                }
                return this;
            }

            public Builder removeDirectory(int index) {
                if (this.directoryBuilder_ == null) {
                    this.ensureDirectoryIsMutable();
                    this.directory_.remove(index);
                    this.onChanged();
                } else {
                    this.directoryBuilder_.remove(index);
                }
                return this;
            }

            public TargetedNativeDirectory.Builder getDirectoryBuilder(int index) {
                return this.getDirectoryFieldBuilder().getBuilder(index);
            }

            @Override
            public TargetedNativeDirectoryOrBuilder getDirectoryOrBuilder(int index) {
                if (this.directoryBuilder_ == null) {
                    return this.directory_.get(index);
                }
                return this.directoryBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends TargetedNativeDirectoryOrBuilder> getDirectoryOrBuilderList() {
                if (this.directoryBuilder_ != null) {
                    return this.directoryBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.directory_);
            }

            public TargetedNativeDirectory.Builder addDirectoryBuilder() {
                return this.getDirectoryFieldBuilder().addBuilder(TargetedNativeDirectory.getDefaultInstance());
            }

            public TargetedNativeDirectory.Builder addDirectoryBuilder(int index) {
                return this.getDirectoryFieldBuilder().addBuilder(index, TargetedNativeDirectory.getDefaultInstance());
            }

            public List<TargetedNativeDirectory.Builder> getDirectoryBuilderList() {
                return this.getDirectoryFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<TargetedNativeDirectory, TargetedNativeDirectory.Builder, TargetedNativeDirectoryOrBuilder> getDirectoryFieldBuilder() {
                if (this.directoryBuilder_ == null) {
                    this.directoryBuilder_ = new RepeatedFieldBuilderV3(this.directory_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.directory_ = null;
                }
                return this.directoryBuilder_;
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

    public static interface NativeLibrariesOrBuilder
    extends MessageOrBuilder {
        public List<TargetedNativeDirectory> getDirectoryList();

        public TargetedNativeDirectory getDirectory(int var1);

        public int getDirectoryCount();

        public List<? extends TargetedNativeDirectoryOrBuilder> getDirectoryOrBuilderList();

        public TargetedNativeDirectoryOrBuilder getDirectoryOrBuilder(int var1);
    }

    public static final class Assets
    extends GeneratedMessageV3
    implements AssetsOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int DIRECTORY_FIELD_NUMBER = 1;
        private List<TargetedAssetsDirectory> directory_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Assets DEFAULT_INSTANCE = new Assets();
        private static final Parser<Assets> PARSER = new AbstractParser<Assets>(){

            @Override
            public Assets parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Assets(input, extensionRegistry);
            }
        };

        private Assets(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Assets() {
            this.directory_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Assets(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.directory_ = new ArrayList<TargetedAssetsDirectory>();
                        mutable_bitField0_ |= true;
                    }
                    this.directory_.add(input.readMessage(TargetedAssetsDirectory.parser(), extensionRegistry));
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
                    this.directory_ = Collections.unmodifiableList(this.directory_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_Assets_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Assets_fieldAccessorTable.ensureFieldAccessorsInitialized(Assets.class, Builder.class);
        }

        @Override
        public List<TargetedAssetsDirectory> getDirectoryList() {
            return this.directory_;
        }

        @Override
        public List<? extends TargetedAssetsDirectoryOrBuilder> getDirectoryOrBuilderList() {
            return this.directory_;
        }

        @Override
        public int getDirectoryCount() {
            return this.directory_.size();
        }

        @Override
        public TargetedAssetsDirectory getDirectory(int index) {
            return this.directory_.get(index);
        }

        @Override
        public TargetedAssetsDirectoryOrBuilder getDirectoryOrBuilder(int index) {
            return this.directory_.get(index);
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
            for (int i2 = 0; i2 < this.directory_.size(); ++i2) {
                output.writeMessage(1, this.directory_.get(i2));
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
            for (int i2 = 0; i2 < this.directory_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.directory_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Assets)) {
                return super.equals(obj);
            }
            Assets other = (Assets)obj;
            boolean result = true;
            result = result && this.getDirectoryList().equals(other.getDirectoryList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Assets.getDescriptor().hashCode();
            if (this.getDirectoryCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getDirectoryList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Assets parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Assets parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Assets parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Assets parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Assets parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Assets parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Assets parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Assets parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Assets parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Assets parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Assets parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Assets parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Assets.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Assets prototype) {
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

        public static Assets getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Assets> parser() {
            return PARSER;
        }

        public Parser<Assets> getParserForType() {
            return PARSER;
        }

        @Override
        public Assets getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AssetsOrBuilder {
            private int bitField0_;
            private List<TargetedAssetsDirectory> directory_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<TargetedAssetsDirectory, TargetedAssetsDirectory.Builder, TargetedAssetsDirectoryOrBuilder> directoryBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Assets_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Assets_fieldAccessorTable.ensureFieldAccessorsInitialized(Assets.class, Builder.class);
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
                    this.getDirectoryFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.directoryBuilder_ == null) {
                    this.directory_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.directoryBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Assets_descriptor;
            }

            @Override
            public Assets getDefaultInstanceForType() {
                return Assets.getDefaultInstance();
            }

            @Override
            public Assets build() {
                Assets result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Assets buildPartial() {
                Assets result = new Assets(this);
                int from_bitField0_ = this.bitField0_;
                if (this.directoryBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.directory_ = Collections.unmodifiableList(this.directory_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.directory_ = this.directory_;
                } else {
                    result.directory_ = this.directoryBuilder_.build();
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
                if (other instanceof Assets) {
                    return this.mergeFrom((Assets)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Assets other) {
                if (other == Assets.getDefaultInstance()) {
                    return this;
                }
                if (this.directoryBuilder_ == null) {
                    if (!other.directory_.isEmpty()) {
                        if (this.directory_.isEmpty()) {
                            this.directory_ = other.directory_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureDirectoryIsMutable();
                            this.directory_.addAll(other.directory_);
                        }
                        this.onChanged();
                    }
                } else if (!other.directory_.isEmpty()) {
                    if (this.directoryBuilder_.isEmpty()) {
                        this.directoryBuilder_.dispose();
                        this.directoryBuilder_ = null;
                        this.directory_ = other.directory_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.directoryBuilder_ = alwaysUseFieldBuilders ? this.getDirectoryFieldBuilder() : null;
                    } else {
                        this.directoryBuilder_.addAllMessages(other.directory_);
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
                Assets parsedMessage = null;
                try {
                    parsedMessage = (Assets)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Assets)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureDirectoryIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.directory_ = new ArrayList<TargetedAssetsDirectory>(this.directory_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<TargetedAssetsDirectory> getDirectoryList() {
                if (this.directoryBuilder_ == null) {
                    return Collections.unmodifiableList(this.directory_);
                }
                return this.directoryBuilder_.getMessageList();
            }

            @Override
            public int getDirectoryCount() {
                if (this.directoryBuilder_ == null) {
                    return this.directory_.size();
                }
                return this.directoryBuilder_.getCount();
            }

            @Override
            public TargetedAssetsDirectory getDirectory(int index) {
                if (this.directoryBuilder_ == null) {
                    return this.directory_.get(index);
                }
                return this.directoryBuilder_.getMessage(index);
            }

            public Builder setDirectory(int index, TargetedAssetsDirectory value) {
                if (this.directoryBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDirectoryIsMutable();
                    this.directory_.set(index, value);
                    this.onChanged();
                } else {
                    this.directoryBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setDirectory(int index, TargetedAssetsDirectory.Builder builderForValue) {
                if (this.directoryBuilder_ == null) {
                    this.ensureDirectoryIsMutable();
                    this.directory_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.directoryBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addDirectory(TargetedAssetsDirectory value) {
                if (this.directoryBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDirectoryIsMutable();
                    this.directory_.add(value);
                    this.onChanged();
                } else {
                    this.directoryBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addDirectory(int index, TargetedAssetsDirectory value) {
                if (this.directoryBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDirectoryIsMutable();
                    this.directory_.add(index, value);
                    this.onChanged();
                } else {
                    this.directoryBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addDirectory(TargetedAssetsDirectory.Builder builderForValue) {
                if (this.directoryBuilder_ == null) {
                    this.ensureDirectoryIsMutable();
                    this.directory_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.directoryBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addDirectory(int index, TargetedAssetsDirectory.Builder builderForValue) {
                if (this.directoryBuilder_ == null) {
                    this.ensureDirectoryIsMutable();
                    this.directory_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.directoryBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllDirectory(Iterable<? extends TargetedAssetsDirectory> values2) {
                if (this.directoryBuilder_ == null) {
                    this.ensureDirectoryIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.directory_);
                    this.onChanged();
                } else {
                    this.directoryBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearDirectory() {
                if (this.directoryBuilder_ == null) {
                    this.directory_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.directoryBuilder_.clear();
                }
                return this;
            }

            public Builder removeDirectory(int index) {
                if (this.directoryBuilder_ == null) {
                    this.ensureDirectoryIsMutable();
                    this.directory_.remove(index);
                    this.onChanged();
                } else {
                    this.directoryBuilder_.remove(index);
                }
                return this;
            }

            public TargetedAssetsDirectory.Builder getDirectoryBuilder(int index) {
                return this.getDirectoryFieldBuilder().getBuilder(index);
            }

            @Override
            public TargetedAssetsDirectoryOrBuilder getDirectoryOrBuilder(int index) {
                if (this.directoryBuilder_ == null) {
                    return this.directory_.get(index);
                }
                return this.directoryBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends TargetedAssetsDirectoryOrBuilder> getDirectoryOrBuilderList() {
                if (this.directoryBuilder_ != null) {
                    return this.directoryBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.directory_);
            }

            public TargetedAssetsDirectory.Builder addDirectoryBuilder() {
                return this.getDirectoryFieldBuilder().addBuilder(TargetedAssetsDirectory.getDefaultInstance());
            }

            public TargetedAssetsDirectory.Builder addDirectoryBuilder(int index) {
                return this.getDirectoryFieldBuilder().addBuilder(index, TargetedAssetsDirectory.getDefaultInstance());
            }

            public List<TargetedAssetsDirectory.Builder> getDirectoryBuilderList() {
                return this.getDirectoryFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<TargetedAssetsDirectory, TargetedAssetsDirectory.Builder, TargetedAssetsDirectoryOrBuilder> getDirectoryFieldBuilder() {
                if (this.directoryBuilder_ == null) {
                    this.directoryBuilder_ = new RepeatedFieldBuilderV3(this.directory_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.directory_ = null;
                }
                return this.directoryBuilder_;
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

    public static interface AssetsOrBuilder
    extends MessageOrBuilder {
        public List<TargetedAssetsDirectory> getDirectoryList();

        public TargetedAssetsDirectory getDirectory(int var1);

        public int getDirectoryCount();

        public List<? extends TargetedAssetsDirectoryOrBuilder> getDirectoryOrBuilderList();

        public TargetedAssetsDirectoryOrBuilder getDirectoryOrBuilder(int var1);
    }
}

