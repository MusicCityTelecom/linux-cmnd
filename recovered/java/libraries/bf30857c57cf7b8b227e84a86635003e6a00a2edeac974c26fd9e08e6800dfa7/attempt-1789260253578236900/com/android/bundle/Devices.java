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
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public final class Devices {
    private static final Descriptors.Descriptor internal_static_android_bundle_DeviceSpec_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_DeviceSpec_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private Devices() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        Devices.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\rdevices.proto\u0012\u000eandroid.bundle\"\u009c\u0001\n\nDeviceSpec\u0012\u0016\n\u000esupported_abis\u0018\u0001 \u0003(\t\u0012\u0019\n\u0011supported_locales\u0018\u0002 \u0003(\t\u0012\u0017\n\u000fdevice_features\u0018\u0003 \u0003(\t\u0012\u0015\n\rgl_extensions\u0018\u0004 \u0003(\t\u0012\u0016\n\u000escreen_density\u0018\u0005 \u0001(\r\u0012\u0013\n\u000bsdk_version\u0018\u0006 \u0001(\rB\u0014\n\u0012com.android.bundleb\u0006proto3"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            @Override
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0], assigner);
        internal_static_android_bundle_DeviceSpec_descriptor = Devices.getDescriptor().getMessageTypes().get(0);
        internal_static_android_bundle_DeviceSpec_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_DeviceSpec_descriptor, new String[]{"SupportedAbis", "SupportedLocales", "DeviceFeatures", "GlExtensions", "ScreenDensity", "SdkVersion"});
    }

    public static final class DeviceSpec
    extends GeneratedMessageV3
    implements DeviceSpecOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int SUPPORTED_ABIS_FIELD_NUMBER = 1;
        private LazyStringList supportedAbis_;
        public static final int SUPPORTED_LOCALES_FIELD_NUMBER = 2;
        private LazyStringList supportedLocales_;
        public static final int DEVICE_FEATURES_FIELD_NUMBER = 3;
        private LazyStringList deviceFeatures_;
        public static final int GL_EXTENSIONS_FIELD_NUMBER = 4;
        private LazyStringList glExtensions_;
        public static final int SCREEN_DENSITY_FIELD_NUMBER = 5;
        private int screenDensity_;
        public static final int SDK_VERSION_FIELD_NUMBER = 6;
        private int sdkVersion_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final DeviceSpec DEFAULT_INSTANCE = new DeviceSpec();
        private static final Parser<DeviceSpec> PARSER = new AbstractParser<DeviceSpec>(){

            @Override
            public DeviceSpec parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DeviceSpec(input, extensionRegistry);
            }
        };

        private DeviceSpec(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private DeviceSpec() {
            this.supportedAbis_ = LazyStringArrayList.EMPTY;
            this.supportedLocales_ = LazyStringArrayList.EMPTY;
            this.deviceFeatures_ = LazyStringArrayList.EMPTY;
            this.glExtensions_ = LazyStringArrayList.EMPTY;
            this.screenDensity_ = 0;
            this.sdkVersion_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DeviceSpec(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block15: while (!done) {
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
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.supportedAbis_ = new LazyStringArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.supportedAbis_.add(s3);
                            continue block15;
                        }
                        case 18: {
                            String s3 = input.readStringRequireUtf8();
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.supportedLocales_ = new LazyStringArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.supportedLocales_.add(s3);
                            continue block15;
                        }
                        case 26: {
                            String s3 = input.readStringRequireUtf8();
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.deviceFeatures_ = new LazyStringArrayList();
                                mutable_bitField0_ |= 4;
                            }
                            this.deviceFeatures_.add(s3);
                            continue block15;
                        }
                        case 34: {
                            String s3 = input.readStringRequireUtf8();
                            if ((mutable_bitField0_ & 8) != 8) {
                                this.glExtensions_ = new LazyStringArrayList();
                                mutable_bitField0_ |= 8;
                            }
                            this.glExtensions_.add(s3);
                            continue block15;
                        }
                        case 40: {
                            this.screenDensity_ = input.readUInt32();
                            continue block15;
                        }
                        case 48: 
                    }
                    this.sdkVersion_ = input.readUInt32();
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
                    this.supportedAbis_ = this.supportedAbis_.getUnmodifiableView();
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.supportedLocales_ = this.supportedLocales_.getUnmodifiableView();
                }
                if ((mutable_bitField0_ & 4) == 4) {
                    this.deviceFeatures_ = this.deviceFeatures_.getUnmodifiableView();
                }
                if ((mutable_bitField0_ & 8) == 8) {
                    this.glExtensions_ = this.glExtensions_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_DeviceSpec_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_DeviceSpec_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceSpec.class, Builder.class);
        }

        public ProtocolStringList getSupportedAbisList() {
            return this.supportedAbis_;
        }

        @Override
        public int getSupportedAbisCount() {
            return this.supportedAbis_.size();
        }

        @Override
        public String getSupportedAbis(int index) {
            return (String)this.supportedAbis_.get(index);
        }

        @Override
        public ByteString getSupportedAbisBytes(int index) {
            return this.supportedAbis_.getByteString(index);
        }

        public ProtocolStringList getSupportedLocalesList() {
            return this.supportedLocales_;
        }

        @Override
        public int getSupportedLocalesCount() {
            return this.supportedLocales_.size();
        }

        @Override
        public String getSupportedLocales(int index) {
            return (String)this.supportedLocales_.get(index);
        }

        @Override
        public ByteString getSupportedLocalesBytes(int index) {
            return this.supportedLocales_.getByteString(index);
        }

        public ProtocolStringList getDeviceFeaturesList() {
            return this.deviceFeatures_;
        }

        @Override
        public int getDeviceFeaturesCount() {
            return this.deviceFeatures_.size();
        }

        @Override
        public String getDeviceFeatures(int index) {
            return (String)this.deviceFeatures_.get(index);
        }

        @Override
        public ByteString getDeviceFeaturesBytes(int index) {
            return this.deviceFeatures_.getByteString(index);
        }

        public ProtocolStringList getGlExtensionsList() {
            return this.glExtensions_;
        }

        @Override
        public int getGlExtensionsCount() {
            return this.glExtensions_.size();
        }

        @Override
        public String getGlExtensions(int index) {
            return (String)this.glExtensions_.get(index);
        }

        @Override
        public ByteString getGlExtensionsBytes(int index) {
            return this.glExtensions_.getByteString(index);
        }

        @Override
        public int getScreenDensity() {
            return this.screenDensity_;
        }

        @Override
        public int getSdkVersion() {
            return this.sdkVersion_;
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
            for (i2 = 0; i2 < this.supportedAbis_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 1, this.supportedAbis_.getRaw(i2));
            }
            for (i2 = 0; i2 < this.supportedLocales_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 2, this.supportedLocales_.getRaw(i2));
            }
            for (i2 = 0; i2 < this.deviceFeatures_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 3, this.deviceFeatures_.getRaw(i2));
            }
            for (i2 = 0; i2 < this.glExtensions_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 4, this.glExtensions_.getRaw(i2));
            }
            if (this.screenDensity_ != 0) {
                output.writeUInt32(5, this.screenDensity_);
            }
            if (this.sdkVersion_ != 0) {
                output.writeUInt32(6, this.sdkVersion_);
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
            for (i2 = 0; i2 < this.supportedAbis_.size(); ++i2) {
                dataSize += DeviceSpec.computeStringSizeNoTag(this.supportedAbis_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getSupportedAbisList().size();
            dataSize = 0;
            for (i2 = 0; i2 < this.supportedLocales_.size(); ++i2) {
                dataSize += DeviceSpec.computeStringSizeNoTag(this.supportedLocales_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getSupportedLocalesList().size();
            dataSize = 0;
            for (i2 = 0; i2 < this.deviceFeatures_.size(); ++i2) {
                dataSize += DeviceSpec.computeStringSizeNoTag(this.deviceFeatures_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getDeviceFeaturesList().size();
            dataSize = 0;
            for (i2 = 0; i2 < this.glExtensions_.size(); ++i2) {
                dataSize += DeviceSpec.computeStringSizeNoTag(this.glExtensions_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getGlExtensionsList().size();
            if (this.screenDensity_ != 0) {
                size += CodedOutputStream.computeUInt32Size(5, this.screenDensity_);
            }
            if (this.sdkVersion_ != 0) {
                size += CodedOutputStream.computeUInt32Size(6, this.sdkVersion_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DeviceSpec)) {
                return super.equals(obj);
            }
            DeviceSpec other = (DeviceSpec)obj;
            boolean result = true;
            result = result && this.getSupportedAbisList().equals(other.getSupportedAbisList());
            result = result && this.getSupportedLocalesList().equals(other.getSupportedLocalesList());
            result = result && this.getDeviceFeaturesList().equals(other.getDeviceFeaturesList());
            result = result && this.getGlExtensionsList().equals(other.getGlExtensionsList());
            result = result && this.getScreenDensity() == other.getScreenDensity();
            result = result && this.getSdkVersion() == other.getSdkVersion();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + DeviceSpec.getDescriptor().hashCode();
            if (this.getSupportedAbisCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getSupportedAbisList().hashCode();
            }
            if (this.getSupportedLocalesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getSupportedLocalesList().hashCode();
            }
            if (this.getDeviceFeaturesCount() > 0) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getDeviceFeaturesList().hashCode();
            }
            if (this.getGlExtensionsCount() > 0) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getGlExtensionsList().hashCode();
            }
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getScreenDensity();
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getSdkVersion();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static DeviceSpec parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeviceSpec parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceSpec parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeviceSpec parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceSpec parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeviceSpec parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceSpec parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceSpec parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceSpec parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeviceSpec parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceSpec parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceSpec parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return DeviceSpec.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeviceSpec prototype) {
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

        public static DeviceSpec getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceSpec> parser() {
            return PARSER;
        }

        public Parser<DeviceSpec> getParserForType() {
            return PARSER;
        }

        @Override
        public DeviceSpec getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements DeviceSpecOrBuilder {
            private int bitField0_;
            private LazyStringList supportedAbis_ = LazyStringArrayList.EMPTY;
            private LazyStringList supportedLocales_ = LazyStringArrayList.EMPTY;
            private LazyStringList deviceFeatures_ = LazyStringArrayList.EMPTY;
            private LazyStringList glExtensions_ = LazyStringArrayList.EMPTY;
            private int screenDensity_;
            private int sdkVersion_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_DeviceSpec_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_DeviceSpec_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceSpec.class, Builder.class);
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
                this.supportedAbis_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.supportedLocales_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.deviceFeatures_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFB;
                this.glExtensions_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFF7;
                this.screenDensity_ = 0;
                this.sdkVersion_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_DeviceSpec_descriptor;
            }

            @Override
            public DeviceSpec getDefaultInstanceForType() {
                return DeviceSpec.getDefaultInstance();
            }

            @Override
            public DeviceSpec build() {
                DeviceSpec result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public DeviceSpec buildPartial() {
                DeviceSpec result = new DeviceSpec(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    this.supportedAbis_ = this.supportedAbis_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.supportedAbis_ = this.supportedAbis_;
                if ((this.bitField0_ & 2) == 2) {
                    this.supportedLocales_ = this.supportedLocales_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.supportedLocales_ = this.supportedLocales_;
                if ((this.bitField0_ & 4) == 4) {
                    this.deviceFeatures_ = this.deviceFeatures_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                result.deviceFeatures_ = this.deviceFeatures_;
                if ((this.bitField0_ & 8) == 8) {
                    this.glExtensions_ = this.glExtensions_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFF7;
                }
                result.glExtensions_ = this.glExtensions_;
                result.screenDensity_ = this.screenDensity_;
                result.sdkVersion_ = this.sdkVersion_;
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
                if (other instanceof DeviceSpec) {
                    return this.mergeFrom((DeviceSpec)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeviceSpec other) {
                if (other == DeviceSpec.getDefaultInstance()) {
                    return this;
                }
                if (!other.supportedAbis_.isEmpty()) {
                    if (this.supportedAbis_.isEmpty()) {
                        this.supportedAbis_ = other.supportedAbis_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureSupportedAbisIsMutable();
                        this.supportedAbis_.addAll(other.supportedAbis_);
                    }
                    this.onChanged();
                }
                if (!other.supportedLocales_.isEmpty()) {
                    if (this.supportedLocales_.isEmpty()) {
                        this.supportedLocales_ = other.supportedLocales_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureSupportedLocalesIsMutable();
                        this.supportedLocales_.addAll(other.supportedLocales_);
                    }
                    this.onChanged();
                }
                if (!other.deviceFeatures_.isEmpty()) {
                    if (this.deviceFeatures_.isEmpty()) {
                        this.deviceFeatures_ = other.deviceFeatures_;
                        this.bitField0_ &= 0xFFFFFFFB;
                    } else {
                        this.ensureDeviceFeaturesIsMutable();
                        this.deviceFeatures_.addAll(other.deviceFeatures_);
                    }
                    this.onChanged();
                }
                if (!other.glExtensions_.isEmpty()) {
                    if (this.glExtensions_.isEmpty()) {
                        this.glExtensions_ = other.glExtensions_;
                        this.bitField0_ &= 0xFFFFFFF7;
                    } else {
                        this.ensureGlExtensionsIsMutable();
                        this.glExtensions_.addAll(other.glExtensions_);
                    }
                    this.onChanged();
                }
                if (other.getScreenDensity() != 0) {
                    this.setScreenDensity(other.getScreenDensity());
                }
                if (other.getSdkVersion() != 0) {
                    this.setSdkVersion(other.getSdkVersion());
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
                DeviceSpec parsedMessage = null;
                try {
                    parsedMessage = (DeviceSpec)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (DeviceSpec)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureSupportedAbisIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.supportedAbis_ = new LazyStringArrayList(this.supportedAbis_);
                    this.bitField0_ |= 1;
                }
            }

            public ProtocolStringList getSupportedAbisList() {
                return this.supportedAbis_.getUnmodifiableView();
            }

            @Override
            public int getSupportedAbisCount() {
                return this.supportedAbis_.size();
            }

            @Override
            public String getSupportedAbis(int index) {
                return (String)this.supportedAbis_.get(index);
            }

            @Override
            public ByteString getSupportedAbisBytes(int index) {
                return this.supportedAbis_.getByteString(index);
            }

            public Builder setSupportedAbis(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureSupportedAbisIsMutable();
                this.supportedAbis_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addSupportedAbis(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureSupportedAbisIsMutable();
                this.supportedAbis_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllSupportedAbis(Iterable<String> values2) {
                this.ensureSupportedAbisIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.supportedAbis_);
                this.onChanged();
                return this;
            }

            public Builder clearSupportedAbis() {
                this.supportedAbis_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder addSupportedAbisBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                DeviceSpec.checkByteStringIsUtf8(value);
                this.ensureSupportedAbisIsMutable();
                this.supportedAbis_.add(value);
                this.onChanged();
                return this;
            }

            private void ensureSupportedLocalesIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.supportedLocales_ = new LazyStringArrayList(this.supportedLocales_);
                    this.bitField0_ |= 2;
                }
            }

            public ProtocolStringList getSupportedLocalesList() {
                return this.supportedLocales_.getUnmodifiableView();
            }

            @Override
            public int getSupportedLocalesCount() {
                return this.supportedLocales_.size();
            }

            @Override
            public String getSupportedLocales(int index) {
                return (String)this.supportedLocales_.get(index);
            }

            @Override
            public ByteString getSupportedLocalesBytes(int index) {
                return this.supportedLocales_.getByteString(index);
            }

            public Builder setSupportedLocales(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureSupportedLocalesIsMutable();
                this.supportedLocales_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addSupportedLocales(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureSupportedLocalesIsMutable();
                this.supportedLocales_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllSupportedLocales(Iterable<String> values2) {
                this.ensureSupportedLocalesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.supportedLocales_);
                this.onChanged();
                return this;
            }

            public Builder clearSupportedLocales() {
                this.supportedLocales_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder addSupportedLocalesBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                DeviceSpec.checkByteStringIsUtf8(value);
                this.ensureSupportedLocalesIsMutable();
                this.supportedLocales_.add(value);
                this.onChanged();
                return this;
            }

            private void ensureDeviceFeaturesIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.deviceFeatures_ = new LazyStringArrayList(this.deviceFeatures_);
                    this.bitField0_ |= 4;
                }
            }

            public ProtocolStringList getDeviceFeaturesList() {
                return this.deviceFeatures_.getUnmodifiableView();
            }

            @Override
            public int getDeviceFeaturesCount() {
                return this.deviceFeatures_.size();
            }

            @Override
            public String getDeviceFeatures(int index) {
                return (String)this.deviceFeatures_.get(index);
            }

            @Override
            public ByteString getDeviceFeaturesBytes(int index) {
                return this.deviceFeatures_.getByteString(index);
            }

            public Builder setDeviceFeatures(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureDeviceFeaturesIsMutable();
                this.deviceFeatures_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addDeviceFeatures(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureDeviceFeaturesIsMutable();
                this.deviceFeatures_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllDeviceFeatures(Iterable<String> values2) {
                this.ensureDeviceFeaturesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.deviceFeatures_);
                this.onChanged();
                return this;
            }

            public Builder clearDeviceFeatures() {
                this.deviceFeatures_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
                return this;
            }

            public Builder addDeviceFeaturesBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                DeviceSpec.checkByteStringIsUtf8(value);
                this.ensureDeviceFeaturesIsMutable();
                this.deviceFeatures_.add(value);
                this.onChanged();
                return this;
            }

            private void ensureGlExtensionsIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.glExtensions_ = new LazyStringArrayList(this.glExtensions_);
                    this.bitField0_ |= 8;
                }
            }

            public ProtocolStringList getGlExtensionsList() {
                return this.glExtensions_.getUnmodifiableView();
            }

            @Override
            public int getGlExtensionsCount() {
                return this.glExtensions_.size();
            }

            @Override
            public String getGlExtensions(int index) {
                return (String)this.glExtensions_.get(index);
            }

            @Override
            public ByteString getGlExtensionsBytes(int index) {
                return this.glExtensions_.getByteString(index);
            }

            public Builder setGlExtensions(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureGlExtensionsIsMutable();
                this.glExtensions_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addGlExtensions(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureGlExtensionsIsMutable();
                this.glExtensions_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllGlExtensions(Iterable<String> values2) {
                this.ensureGlExtensionsIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.glExtensions_);
                this.onChanged();
                return this;
            }

            public Builder clearGlExtensions() {
                this.glExtensions_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFF7;
                this.onChanged();
                return this;
            }

            public Builder addGlExtensionsBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                DeviceSpec.checkByteStringIsUtf8(value);
                this.ensureGlExtensionsIsMutable();
                this.glExtensions_.add(value);
                this.onChanged();
                return this;
            }

            @Override
            public int getScreenDensity() {
                return this.screenDensity_;
            }

            public Builder setScreenDensity(int value) {
                this.screenDensity_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearScreenDensity() {
                this.screenDensity_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getSdkVersion() {
                return this.sdkVersion_;
            }

            public Builder setSdkVersion(int value) {
                this.sdkVersion_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearSdkVersion() {
                this.sdkVersion_ = 0;
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

    public static interface DeviceSpecOrBuilder
    extends MessageOrBuilder {
        public List<String> getSupportedAbisList();

        public int getSupportedAbisCount();

        public String getSupportedAbis(int var1);

        public ByteString getSupportedAbisBytes(int var1);

        public List<String> getSupportedLocalesList();

        public int getSupportedLocalesCount();

        public String getSupportedLocales(int var1);

        public ByteString getSupportedLocalesBytes(int var1);

        public List<String> getDeviceFeaturesList();

        public int getDeviceFeaturesCount();

        public String getDeviceFeatures(int var1);

        public ByteString getDeviceFeaturesBytes(int var1);

        public List<String> getGlExtensionsList();

        public int getGlExtensionsCount();

        public String getGlExtensions(int var1);

        public ByteString getGlExtensionsBytes(int var1);

        public int getScreenDensity();

        public int getSdkVersion();
    }
}

