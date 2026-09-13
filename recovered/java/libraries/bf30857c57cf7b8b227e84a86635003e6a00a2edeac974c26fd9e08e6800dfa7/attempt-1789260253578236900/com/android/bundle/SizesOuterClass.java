/*
 * Decompiled with CFR 0.152.
 */
package com.android.bundle;

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
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class SizesOuterClass {
    private static final Descriptors.Descriptor internal_static_android_bundle_Sizes_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Sizes_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_Breakdown_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Breakdown_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private SizesOuterClass() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        SizesOuterClass.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u000bsizes.proto\u0012\u000eandroid.bundle\"1\n\u0005Sizes\u0012\u0011\n\tdisk_size\u0018\u0001 \u0001(\u0003\u0012\u0015\n\rdownload_size\u0018\u0002 \u0001(\u0003\"\u00f8\u0001\n\tBreakdown\u0012$\n\u0005total\u0018\u0001 \u0001(\u000b2\u0015.android.bundle.Sizes\u0012\"\n\u0003dex\u0018\u0002 \u0001(\u000b2\u0015.android.bundle.Sizes\u0012(\n\tresources\u0018\u0003 \u0001(\u000b2\u0015.android.bundle.Sizes\u0012%\n\u0006assets\u0018\u0004 \u0001(\u000b2\u0015.android.bundle.Sizes\u0012*\n\u000bnative_libs\u0018\u0005 \u0001(\u000b2\u0015.android.bundle.Sizes\u0012$\n\u0005other\u0018\u0006 \u0001(\u000b2\u0015.android.bundle.SizesB\u0014\n\u0012com.android.bundleb\u0006proto3"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            @Override
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0], assigner);
        internal_static_android_bundle_Sizes_descriptor = SizesOuterClass.getDescriptor().getMessageTypes().get(0);
        internal_static_android_bundle_Sizes_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Sizes_descriptor, new String[]{"DiskSize", "DownloadSize"});
        internal_static_android_bundle_Breakdown_descriptor = SizesOuterClass.getDescriptor().getMessageTypes().get(1);
        internal_static_android_bundle_Breakdown_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Breakdown_descriptor, new String[]{"Total", "Dex", "Resources", "Assets", "NativeLibs", "Other"});
    }

    public static final class Breakdown
    extends GeneratedMessageV3
    implements BreakdownOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int TOTAL_FIELD_NUMBER = 1;
        private Sizes total_;
        public static final int DEX_FIELD_NUMBER = 2;
        private Sizes dex_;
        public static final int RESOURCES_FIELD_NUMBER = 3;
        private Sizes resources_;
        public static final int ASSETS_FIELD_NUMBER = 4;
        private Sizes assets_;
        public static final int NATIVE_LIBS_FIELD_NUMBER = 5;
        private Sizes nativeLibs_;
        public static final int OTHER_FIELD_NUMBER = 6;
        private Sizes other_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Breakdown DEFAULT_INSTANCE = new Breakdown();
        private static final Parser<Breakdown> PARSER = new AbstractParser<Breakdown>(){

            @Override
            public Breakdown parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Breakdown(input, extensionRegistry);
            }
        };

        private Breakdown(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Breakdown() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Breakdown(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block15: while (!done) {
                    Sizes.Builder subBuilder;
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
                            subBuilder = null;
                            if (this.total_ != null) {
                                subBuilder = this.total_.toBuilder();
                            }
                            this.total_ = input.readMessage(Sizes.parser(), extensionRegistry);
                            if (subBuilder == null) continue block15;
                            subBuilder.mergeFrom(this.total_);
                            this.total_ = subBuilder.buildPartial();
                            continue block15;
                        }
                        case 18: {
                            subBuilder = null;
                            if (this.dex_ != null) {
                                subBuilder = this.dex_.toBuilder();
                            }
                            this.dex_ = input.readMessage(Sizes.parser(), extensionRegistry);
                            if (subBuilder == null) continue block15;
                            subBuilder.mergeFrom(this.dex_);
                            this.dex_ = subBuilder.buildPartial();
                            continue block15;
                        }
                        case 26: {
                            subBuilder = null;
                            if (this.resources_ != null) {
                                subBuilder = this.resources_.toBuilder();
                            }
                            this.resources_ = input.readMessage(Sizes.parser(), extensionRegistry);
                            if (subBuilder == null) continue block15;
                            subBuilder.mergeFrom(this.resources_);
                            this.resources_ = subBuilder.buildPartial();
                            continue block15;
                        }
                        case 34: {
                            subBuilder = null;
                            if (this.assets_ != null) {
                                subBuilder = this.assets_.toBuilder();
                            }
                            this.assets_ = input.readMessage(Sizes.parser(), extensionRegistry);
                            if (subBuilder == null) continue block15;
                            subBuilder.mergeFrom(this.assets_);
                            this.assets_ = subBuilder.buildPartial();
                            continue block15;
                        }
                        case 42: {
                            subBuilder = null;
                            if (this.nativeLibs_ != null) {
                                subBuilder = this.nativeLibs_.toBuilder();
                            }
                            this.nativeLibs_ = input.readMessage(Sizes.parser(), extensionRegistry);
                            if (subBuilder == null) continue block15;
                            subBuilder.mergeFrom(this.nativeLibs_);
                            this.nativeLibs_ = subBuilder.buildPartial();
                            continue block15;
                        }
                        case 50: 
                    }
                    subBuilder = null;
                    if (this.other_ != null) {
                        subBuilder = this.other_.toBuilder();
                    }
                    this.other_ = input.readMessage(Sizes.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    subBuilder.mergeFrom(this.other_);
                    this.other_ = subBuilder.buildPartial();
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
            return internal_static_android_bundle_Breakdown_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Breakdown_fieldAccessorTable.ensureFieldAccessorsInitialized(Breakdown.class, Builder.class);
        }

        @Override
        public boolean hasTotal() {
            return this.total_ != null;
        }

        @Override
        public Sizes getTotal() {
            return this.total_ == null ? Sizes.getDefaultInstance() : this.total_;
        }

        @Override
        public SizesOrBuilder getTotalOrBuilder() {
            return this.getTotal();
        }

        @Override
        public boolean hasDex() {
            return this.dex_ != null;
        }

        @Override
        public Sizes getDex() {
            return this.dex_ == null ? Sizes.getDefaultInstance() : this.dex_;
        }

        @Override
        public SizesOrBuilder getDexOrBuilder() {
            return this.getDex();
        }

        @Override
        public boolean hasResources() {
            return this.resources_ != null;
        }

        @Override
        public Sizes getResources() {
            return this.resources_ == null ? Sizes.getDefaultInstance() : this.resources_;
        }

        @Override
        public SizesOrBuilder getResourcesOrBuilder() {
            return this.getResources();
        }

        @Override
        public boolean hasAssets() {
            return this.assets_ != null;
        }

        @Override
        public Sizes getAssets() {
            return this.assets_ == null ? Sizes.getDefaultInstance() : this.assets_;
        }

        @Override
        public SizesOrBuilder getAssetsOrBuilder() {
            return this.getAssets();
        }

        @Override
        public boolean hasNativeLibs() {
            return this.nativeLibs_ != null;
        }

        @Override
        public Sizes getNativeLibs() {
            return this.nativeLibs_ == null ? Sizes.getDefaultInstance() : this.nativeLibs_;
        }

        @Override
        public SizesOrBuilder getNativeLibsOrBuilder() {
            return this.getNativeLibs();
        }

        @Override
        public boolean hasOther() {
            return this.other_ != null;
        }

        @Override
        public Sizes getOther() {
            return this.other_ == null ? Sizes.getDefaultInstance() : this.other_;
        }

        @Override
        public SizesOrBuilder getOtherOrBuilder() {
            return this.getOther();
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
            if (this.total_ != null) {
                output.writeMessage(1, this.getTotal());
            }
            if (this.dex_ != null) {
                output.writeMessage(2, this.getDex());
            }
            if (this.resources_ != null) {
                output.writeMessage(3, this.getResources());
            }
            if (this.assets_ != null) {
                output.writeMessage(4, this.getAssets());
            }
            if (this.nativeLibs_ != null) {
                output.writeMessage(5, this.getNativeLibs());
            }
            if (this.other_ != null) {
                output.writeMessage(6, this.getOther());
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
            if (this.total_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getTotal());
            }
            if (this.dex_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getDex());
            }
            if (this.resources_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getResources());
            }
            if (this.assets_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getAssets());
            }
            if (this.nativeLibs_ != null) {
                size += CodedOutputStream.computeMessageSize(5, this.getNativeLibs());
            }
            if (this.other_ != null) {
                size += CodedOutputStream.computeMessageSize(6, this.getOther());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Breakdown)) {
                return super.equals(obj);
            }
            Breakdown other = (Breakdown)obj;
            boolean result = true;
            boolean bl = result = result && this.hasTotal() == other.hasTotal();
            if (this.hasTotal()) {
                result = result && this.getTotal().equals(other.getTotal());
            }
            boolean bl2 = result = result && this.hasDex() == other.hasDex();
            if (this.hasDex()) {
                result = result && this.getDex().equals(other.getDex());
            }
            boolean bl3 = result = result && this.hasResources() == other.hasResources();
            if (this.hasResources()) {
                result = result && this.getResources().equals(other.getResources());
            }
            boolean bl4 = result = result && this.hasAssets() == other.hasAssets();
            if (this.hasAssets()) {
                result = result && this.getAssets().equals(other.getAssets());
            }
            boolean bl5 = result = result && this.hasNativeLibs() == other.hasNativeLibs();
            if (this.hasNativeLibs()) {
                result = result && this.getNativeLibs().equals(other.getNativeLibs());
            }
            boolean bl6 = result = result && this.hasOther() == other.hasOther();
            if (this.hasOther()) {
                result = result && this.getOther().equals(other.getOther());
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
            hash = 19 * hash + Breakdown.getDescriptor().hashCode();
            if (this.hasTotal()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getTotal().hashCode();
            }
            if (this.hasDex()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getDex().hashCode();
            }
            if (this.hasResources()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getResources().hashCode();
            }
            if (this.hasAssets()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getAssets().hashCode();
            }
            if (this.hasNativeLibs()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getNativeLibs().hashCode();
            }
            if (this.hasOther()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getOther().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Breakdown parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Breakdown parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Breakdown parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Breakdown parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Breakdown parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Breakdown parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Breakdown parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Breakdown parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Breakdown parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Breakdown parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Breakdown parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Breakdown parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Breakdown.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Breakdown prototype) {
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

        public static Breakdown getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Breakdown> parser() {
            return PARSER;
        }

        public Parser<Breakdown> getParserForType() {
            return PARSER;
        }

        @Override
        public Breakdown getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements BreakdownOrBuilder {
            private Sizes total_ = null;
            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> totalBuilder_;
            private Sizes dex_ = null;
            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> dexBuilder_;
            private Sizes resources_ = null;
            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> resourcesBuilder_;
            private Sizes assets_ = null;
            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> assetsBuilder_;
            private Sizes nativeLibs_ = null;
            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> nativeLibsBuilder_;
            private Sizes other_ = null;
            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> otherBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Breakdown_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Breakdown_fieldAccessorTable.ensureFieldAccessorsInitialized(Breakdown.class, Builder.class);
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
                if (this.totalBuilder_ == null) {
                    this.total_ = null;
                } else {
                    this.total_ = null;
                    this.totalBuilder_ = null;
                }
                if (this.dexBuilder_ == null) {
                    this.dex_ = null;
                } else {
                    this.dex_ = null;
                    this.dexBuilder_ = null;
                }
                if (this.resourcesBuilder_ == null) {
                    this.resources_ = null;
                } else {
                    this.resources_ = null;
                    this.resourcesBuilder_ = null;
                }
                if (this.assetsBuilder_ == null) {
                    this.assets_ = null;
                } else {
                    this.assets_ = null;
                    this.assetsBuilder_ = null;
                }
                if (this.nativeLibsBuilder_ == null) {
                    this.nativeLibs_ = null;
                } else {
                    this.nativeLibs_ = null;
                    this.nativeLibsBuilder_ = null;
                }
                if (this.otherBuilder_ == null) {
                    this.other_ = null;
                } else {
                    this.other_ = null;
                    this.otherBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Breakdown_descriptor;
            }

            @Override
            public Breakdown getDefaultInstanceForType() {
                return Breakdown.getDefaultInstance();
            }

            @Override
            public Breakdown build() {
                Breakdown result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Breakdown buildPartial() {
                Breakdown result = new Breakdown(this);
                if (this.totalBuilder_ == null) {
                    result.total_ = this.total_;
                } else {
                    result.total_ = this.totalBuilder_.build();
                }
                if (this.dexBuilder_ == null) {
                    result.dex_ = this.dex_;
                } else {
                    result.dex_ = this.dexBuilder_.build();
                }
                if (this.resourcesBuilder_ == null) {
                    result.resources_ = this.resources_;
                } else {
                    result.resources_ = this.resourcesBuilder_.build();
                }
                if (this.assetsBuilder_ == null) {
                    result.assets_ = this.assets_;
                } else {
                    result.assets_ = this.assetsBuilder_.build();
                }
                if (this.nativeLibsBuilder_ == null) {
                    result.nativeLibs_ = this.nativeLibs_;
                } else {
                    result.nativeLibs_ = this.nativeLibsBuilder_.build();
                }
                if (this.otherBuilder_ == null) {
                    result.other_ = this.other_;
                } else {
                    result.other_ = this.otherBuilder_.build();
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
                if (other instanceof Breakdown) {
                    return this.mergeFrom((Breakdown)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Breakdown other) {
                if (other == Breakdown.getDefaultInstance()) {
                    return this;
                }
                if (other.hasTotal()) {
                    this.mergeTotal(other.getTotal());
                }
                if (other.hasDex()) {
                    this.mergeDex(other.getDex());
                }
                if (other.hasResources()) {
                    this.mergeResources(other.getResources());
                }
                if (other.hasAssets()) {
                    this.mergeAssets(other.getAssets());
                }
                if (other.hasNativeLibs()) {
                    this.mergeNativeLibs(other.getNativeLibs());
                }
                if (other.hasOther()) {
                    this.mergeOther(other.getOther());
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
                Breakdown parsedMessage = null;
                try {
                    parsedMessage = (Breakdown)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Breakdown)e2.getUnfinishedMessage();
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
            public boolean hasTotal() {
                return this.totalBuilder_ != null || this.total_ != null;
            }

            @Override
            public Sizes getTotal() {
                if (this.totalBuilder_ == null) {
                    return this.total_ == null ? Sizes.getDefaultInstance() : this.total_;
                }
                return this.totalBuilder_.getMessage();
            }

            public Builder setTotal(Sizes value) {
                if (this.totalBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.total_ = value;
                    this.onChanged();
                } else {
                    this.totalBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setTotal(Sizes.Builder builderForValue) {
                if (this.totalBuilder_ == null) {
                    this.total_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.totalBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTotal(Sizes value) {
                if (this.totalBuilder_ == null) {
                    this.total_ = this.total_ != null ? Sizes.newBuilder(this.total_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.totalBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearTotal() {
                if (this.totalBuilder_ == null) {
                    this.total_ = null;
                    this.onChanged();
                } else {
                    this.total_ = null;
                    this.totalBuilder_ = null;
                }
                return this;
            }

            public Sizes.Builder getTotalBuilder() {
                this.onChanged();
                return this.getTotalFieldBuilder().getBuilder();
            }

            @Override
            public SizesOrBuilder getTotalOrBuilder() {
                if (this.totalBuilder_ != null) {
                    return this.totalBuilder_.getMessageOrBuilder();
                }
                return this.total_ == null ? Sizes.getDefaultInstance() : this.total_;
            }

            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> getTotalFieldBuilder() {
                if (this.totalBuilder_ == null) {
                    this.totalBuilder_ = new SingleFieldBuilderV3(this.getTotal(), this.getParentForChildren(), this.isClean());
                    this.total_ = null;
                }
                return this.totalBuilder_;
            }

            @Override
            public boolean hasDex() {
                return this.dexBuilder_ != null || this.dex_ != null;
            }

            @Override
            public Sizes getDex() {
                if (this.dexBuilder_ == null) {
                    return this.dex_ == null ? Sizes.getDefaultInstance() : this.dex_;
                }
                return this.dexBuilder_.getMessage();
            }

            public Builder setDex(Sizes value) {
                if (this.dexBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.dex_ = value;
                    this.onChanged();
                } else {
                    this.dexBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setDex(Sizes.Builder builderForValue) {
                if (this.dexBuilder_ == null) {
                    this.dex_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.dexBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeDex(Sizes value) {
                if (this.dexBuilder_ == null) {
                    this.dex_ = this.dex_ != null ? Sizes.newBuilder(this.dex_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.dexBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearDex() {
                if (this.dexBuilder_ == null) {
                    this.dex_ = null;
                    this.onChanged();
                } else {
                    this.dex_ = null;
                    this.dexBuilder_ = null;
                }
                return this;
            }

            public Sizes.Builder getDexBuilder() {
                this.onChanged();
                return this.getDexFieldBuilder().getBuilder();
            }

            @Override
            public SizesOrBuilder getDexOrBuilder() {
                if (this.dexBuilder_ != null) {
                    return this.dexBuilder_.getMessageOrBuilder();
                }
                return this.dex_ == null ? Sizes.getDefaultInstance() : this.dex_;
            }

            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> getDexFieldBuilder() {
                if (this.dexBuilder_ == null) {
                    this.dexBuilder_ = new SingleFieldBuilderV3(this.getDex(), this.getParentForChildren(), this.isClean());
                    this.dex_ = null;
                }
                return this.dexBuilder_;
            }

            @Override
            public boolean hasResources() {
                return this.resourcesBuilder_ != null || this.resources_ != null;
            }

            @Override
            public Sizes getResources() {
                if (this.resourcesBuilder_ == null) {
                    return this.resources_ == null ? Sizes.getDefaultInstance() : this.resources_;
                }
                return this.resourcesBuilder_.getMessage();
            }

            public Builder setResources(Sizes value) {
                if (this.resourcesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.resources_ = value;
                    this.onChanged();
                } else {
                    this.resourcesBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setResources(Sizes.Builder builderForValue) {
                if (this.resourcesBuilder_ == null) {
                    this.resources_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.resourcesBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeResources(Sizes value) {
                if (this.resourcesBuilder_ == null) {
                    this.resources_ = this.resources_ != null ? Sizes.newBuilder(this.resources_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.resourcesBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearResources() {
                if (this.resourcesBuilder_ == null) {
                    this.resources_ = null;
                    this.onChanged();
                } else {
                    this.resources_ = null;
                    this.resourcesBuilder_ = null;
                }
                return this;
            }

            public Sizes.Builder getResourcesBuilder() {
                this.onChanged();
                return this.getResourcesFieldBuilder().getBuilder();
            }

            @Override
            public SizesOrBuilder getResourcesOrBuilder() {
                if (this.resourcesBuilder_ != null) {
                    return this.resourcesBuilder_.getMessageOrBuilder();
                }
                return this.resources_ == null ? Sizes.getDefaultInstance() : this.resources_;
            }

            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> getResourcesFieldBuilder() {
                if (this.resourcesBuilder_ == null) {
                    this.resourcesBuilder_ = new SingleFieldBuilderV3(this.getResources(), this.getParentForChildren(), this.isClean());
                    this.resources_ = null;
                }
                return this.resourcesBuilder_;
            }

            @Override
            public boolean hasAssets() {
                return this.assetsBuilder_ != null || this.assets_ != null;
            }

            @Override
            public Sizes getAssets() {
                if (this.assetsBuilder_ == null) {
                    return this.assets_ == null ? Sizes.getDefaultInstance() : this.assets_;
                }
                return this.assetsBuilder_.getMessage();
            }

            public Builder setAssets(Sizes value) {
                if (this.assetsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.assets_ = value;
                    this.onChanged();
                } else {
                    this.assetsBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setAssets(Sizes.Builder builderForValue) {
                if (this.assetsBuilder_ == null) {
                    this.assets_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.assetsBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeAssets(Sizes value) {
                if (this.assetsBuilder_ == null) {
                    this.assets_ = this.assets_ != null ? Sizes.newBuilder(this.assets_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.assetsBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearAssets() {
                if (this.assetsBuilder_ == null) {
                    this.assets_ = null;
                    this.onChanged();
                } else {
                    this.assets_ = null;
                    this.assetsBuilder_ = null;
                }
                return this;
            }

            public Sizes.Builder getAssetsBuilder() {
                this.onChanged();
                return this.getAssetsFieldBuilder().getBuilder();
            }

            @Override
            public SizesOrBuilder getAssetsOrBuilder() {
                if (this.assetsBuilder_ != null) {
                    return this.assetsBuilder_.getMessageOrBuilder();
                }
                return this.assets_ == null ? Sizes.getDefaultInstance() : this.assets_;
            }

            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> getAssetsFieldBuilder() {
                if (this.assetsBuilder_ == null) {
                    this.assetsBuilder_ = new SingleFieldBuilderV3(this.getAssets(), this.getParentForChildren(), this.isClean());
                    this.assets_ = null;
                }
                return this.assetsBuilder_;
            }

            @Override
            public boolean hasNativeLibs() {
                return this.nativeLibsBuilder_ != null || this.nativeLibs_ != null;
            }

            @Override
            public Sizes getNativeLibs() {
                if (this.nativeLibsBuilder_ == null) {
                    return this.nativeLibs_ == null ? Sizes.getDefaultInstance() : this.nativeLibs_;
                }
                return this.nativeLibsBuilder_.getMessage();
            }

            public Builder setNativeLibs(Sizes value) {
                if (this.nativeLibsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.nativeLibs_ = value;
                    this.onChanged();
                } else {
                    this.nativeLibsBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setNativeLibs(Sizes.Builder builderForValue) {
                if (this.nativeLibsBuilder_ == null) {
                    this.nativeLibs_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.nativeLibsBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeNativeLibs(Sizes value) {
                if (this.nativeLibsBuilder_ == null) {
                    this.nativeLibs_ = this.nativeLibs_ != null ? Sizes.newBuilder(this.nativeLibs_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.nativeLibsBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearNativeLibs() {
                if (this.nativeLibsBuilder_ == null) {
                    this.nativeLibs_ = null;
                    this.onChanged();
                } else {
                    this.nativeLibs_ = null;
                    this.nativeLibsBuilder_ = null;
                }
                return this;
            }

            public Sizes.Builder getNativeLibsBuilder() {
                this.onChanged();
                return this.getNativeLibsFieldBuilder().getBuilder();
            }

            @Override
            public SizesOrBuilder getNativeLibsOrBuilder() {
                if (this.nativeLibsBuilder_ != null) {
                    return this.nativeLibsBuilder_.getMessageOrBuilder();
                }
                return this.nativeLibs_ == null ? Sizes.getDefaultInstance() : this.nativeLibs_;
            }

            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> getNativeLibsFieldBuilder() {
                if (this.nativeLibsBuilder_ == null) {
                    this.nativeLibsBuilder_ = new SingleFieldBuilderV3(this.getNativeLibs(), this.getParentForChildren(), this.isClean());
                    this.nativeLibs_ = null;
                }
                return this.nativeLibsBuilder_;
            }

            @Override
            public boolean hasOther() {
                return this.otherBuilder_ != null || this.other_ != null;
            }

            @Override
            public Sizes getOther() {
                if (this.otherBuilder_ == null) {
                    return this.other_ == null ? Sizes.getDefaultInstance() : this.other_;
                }
                return this.otherBuilder_.getMessage();
            }

            public Builder setOther(Sizes value) {
                if (this.otherBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.other_ = value;
                    this.onChanged();
                } else {
                    this.otherBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setOther(Sizes.Builder builderForValue) {
                if (this.otherBuilder_ == null) {
                    this.other_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.otherBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeOther(Sizes value) {
                if (this.otherBuilder_ == null) {
                    this.other_ = this.other_ != null ? Sizes.newBuilder(this.other_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.otherBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearOther() {
                if (this.otherBuilder_ == null) {
                    this.other_ = null;
                    this.onChanged();
                } else {
                    this.other_ = null;
                    this.otherBuilder_ = null;
                }
                return this;
            }

            public Sizes.Builder getOtherBuilder() {
                this.onChanged();
                return this.getOtherFieldBuilder().getBuilder();
            }

            @Override
            public SizesOrBuilder getOtherOrBuilder() {
                if (this.otherBuilder_ != null) {
                    return this.otherBuilder_.getMessageOrBuilder();
                }
                return this.other_ == null ? Sizes.getDefaultInstance() : this.other_;
            }

            private SingleFieldBuilderV3<Sizes, Sizes.Builder, SizesOrBuilder> getOtherFieldBuilder() {
                if (this.otherBuilder_ == null) {
                    this.otherBuilder_ = new SingleFieldBuilderV3(this.getOther(), this.getParentForChildren(), this.isClean());
                    this.other_ = null;
                }
                return this.otherBuilder_;
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

    public static interface BreakdownOrBuilder
    extends MessageOrBuilder {
        public boolean hasTotal();

        public Sizes getTotal();

        public SizesOrBuilder getTotalOrBuilder();

        public boolean hasDex();

        public Sizes getDex();

        public SizesOrBuilder getDexOrBuilder();

        public boolean hasResources();

        public Sizes getResources();

        public SizesOrBuilder getResourcesOrBuilder();

        public boolean hasAssets();

        public Sizes getAssets();

        public SizesOrBuilder getAssetsOrBuilder();

        public boolean hasNativeLibs();

        public Sizes getNativeLibs();

        public SizesOrBuilder getNativeLibsOrBuilder();

        public boolean hasOther();

        public Sizes getOther();

        public SizesOrBuilder getOtherOrBuilder();
    }

    public static final class Sizes
    extends GeneratedMessageV3
    implements SizesOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int DISK_SIZE_FIELD_NUMBER = 1;
        private long diskSize_;
        public static final int DOWNLOAD_SIZE_FIELD_NUMBER = 2;
        private long downloadSize_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Sizes DEFAULT_INSTANCE = new Sizes();
        private static final Parser<Sizes> PARSER = new AbstractParser<Sizes>(){

            @Override
            public Sizes parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Sizes(input, extensionRegistry);
            }
        };

        private Sizes(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Sizes() {
            this.diskSize_ = 0L;
            this.downloadSize_ = 0L;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Sizes(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.diskSize_ = input.readInt64();
                            continue block11;
                        }
                        case 16: 
                    }
                    this.downloadSize_ = input.readInt64();
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
            return internal_static_android_bundle_Sizes_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Sizes_fieldAccessorTable.ensureFieldAccessorsInitialized(Sizes.class, Builder.class);
        }

        @Override
        public long getDiskSize() {
            return this.diskSize_;
        }

        @Override
        public long getDownloadSize() {
            return this.downloadSize_;
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
            if (this.diskSize_ != 0L) {
                output.writeInt64(1, this.diskSize_);
            }
            if (this.downloadSize_ != 0L) {
                output.writeInt64(2, this.downloadSize_);
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
            if (this.diskSize_ != 0L) {
                size += CodedOutputStream.computeInt64Size(1, this.diskSize_);
            }
            if (this.downloadSize_ != 0L) {
                size += CodedOutputStream.computeInt64Size(2, this.downloadSize_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Sizes)) {
                return super.equals(obj);
            }
            Sizes other = (Sizes)obj;
            boolean result = true;
            result = result && this.getDiskSize() == other.getDiskSize();
            result = result && this.getDownloadSize() == other.getDownloadSize();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Sizes.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashLong(this.getDiskSize());
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashLong(this.getDownloadSize());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Sizes parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Sizes parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Sizes parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Sizes parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Sizes parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Sizes parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Sizes parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Sizes parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Sizes parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Sizes parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Sizes parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Sizes parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Sizes.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Sizes prototype) {
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

        public static Sizes getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Sizes> parser() {
            return PARSER;
        }

        public Parser<Sizes> getParserForType() {
            return PARSER;
        }

        @Override
        public Sizes getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SizesOrBuilder {
            private long diskSize_;
            private long downloadSize_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Sizes_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Sizes_fieldAccessorTable.ensureFieldAccessorsInitialized(Sizes.class, Builder.class);
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
                this.diskSize_ = 0L;
                this.downloadSize_ = 0L;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Sizes_descriptor;
            }

            @Override
            public Sizes getDefaultInstanceForType() {
                return Sizes.getDefaultInstance();
            }

            @Override
            public Sizes build() {
                Sizes result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Sizes buildPartial() {
                Sizes result = new Sizes(this);
                result.diskSize_ = this.diskSize_;
                result.downloadSize_ = this.downloadSize_;
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
                if (other instanceof Sizes) {
                    return this.mergeFrom((Sizes)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Sizes other) {
                if (other == Sizes.getDefaultInstance()) {
                    return this;
                }
                if (other.getDiskSize() != 0L) {
                    this.setDiskSize(other.getDiskSize());
                }
                if (other.getDownloadSize() != 0L) {
                    this.setDownloadSize(other.getDownloadSize());
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
                Sizes parsedMessage = null;
                try {
                    parsedMessage = (Sizes)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Sizes)e2.getUnfinishedMessage();
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
            public long getDiskSize() {
                return this.diskSize_;
            }

            public Builder setDiskSize(long value) {
                this.diskSize_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearDiskSize() {
                this.diskSize_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public long getDownloadSize() {
                return this.downloadSize_;
            }

            public Builder setDownloadSize(long value) {
                this.downloadSize_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearDownloadSize() {
                this.downloadSize_ = 0L;
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

    public static interface SizesOrBuilder
    extends MessageOrBuilder {
        public long getDiskSize();

        public long getDownloadSize();
    }
}

