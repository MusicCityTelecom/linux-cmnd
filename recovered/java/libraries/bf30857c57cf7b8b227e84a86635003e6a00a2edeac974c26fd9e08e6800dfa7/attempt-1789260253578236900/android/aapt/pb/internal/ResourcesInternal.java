/*
 * Decompiled with CFR 0.152.
 */
package android.aapt.pb.internal;

import com.android.aapt.ConfigurationOuterClass;
import com.android.aapt.Resources;
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

public final class ResourcesInternal {
    private static final Descriptors.Descriptor internal_static_aapt_pb_internal_CompiledFile_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_aapt_pb_internal_CompiledFile_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_aapt_pb_internal_CompiledFile_Symbol_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_aapt_pb_internal_CompiledFile_Symbol_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private ResourcesInternal() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        ResourcesInternal.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0017ResourcesInternal.proto\u0012\u0010aapt.pb.internal\u001a\u0013Configuration.proto\u001a\u000fResources.proto\"\u0097\u0002\n\fCompiledFile\u0012\u0015\n\rresource_name\u0018\u0001 \u0001(\t\u0012&\n\u0006config\u0018\u0002 \u0001(\u000b2\u0016.aapt.pb.Configuration\u0012)\n\u0004type\u0018\u0003 \u0001(\u000e2\u001b.aapt.pb.FileReference.Type\u0012\u0013\n\u000bsource_path\u0018\u0004 \u0001(\t\u0012>\n\u000fexported_symbol\u0018\u0005 \u0003(\u000b2%.aapt.pb.internal.CompiledFile.Symbol\u001aH\n\u0006Symbol\u0012\u0015\n\rresource_name\u0018\u0001 \u0001(\t\u0012'\n\u0006source\u0018\u0002 \u0001(\u000b2\u0017.aapt.pb.SourcePositionB\u001a\n\u0018android.aapt.pb.internalb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{ConfigurationOuterClass.getDescriptor(), Resources.getDescriptor()});
        internal_static_aapt_pb_internal_CompiledFile_descriptor = ResourcesInternal.getDescriptor().getMessageTypes().get(0);
        internal_static_aapt_pb_internal_CompiledFile_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_aapt_pb_internal_CompiledFile_descriptor, new String[]{"ResourceName", "Config", "Type", "SourcePath", "ExportedSymbol"});
        internal_static_aapt_pb_internal_CompiledFile_Symbol_descriptor = internal_static_aapt_pb_internal_CompiledFile_descriptor.getNestedTypes().get(0);
        internal_static_aapt_pb_internal_CompiledFile_Symbol_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_aapt_pb_internal_CompiledFile_Symbol_descriptor, new String[]{"ResourceName", "Source"});
        ConfigurationOuterClass.getDescriptor();
        Resources.getDescriptor();
    }

    public static final class CompiledFile
    extends GeneratedMessageV3
    implements CompiledFileOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int RESOURCE_NAME_FIELD_NUMBER = 1;
        private volatile Object resourceName_;
        public static final int CONFIG_FIELD_NUMBER = 2;
        private ConfigurationOuterClass.Configuration config_;
        public static final int TYPE_FIELD_NUMBER = 3;
        private int type_;
        public static final int SOURCE_PATH_FIELD_NUMBER = 4;
        private volatile Object sourcePath_;
        public static final int EXPORTED_SYMBOL_FIELD_NUMBER = 5;
        private List<Symbol> exportedSymbol_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final CompiledFile DEFAULT_INSTANCE = new CompiledFile();
        private static final Parser<CompiledFile> PARSER = new AbstractParser<CompiledFile>(){

            @Override
            public CompiledFile parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new CompiledFile(input, extensionRegistry);
            }
        };

        private CompiledFile(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private CompiledFile() {
            this.resourceName_ = "";
            this.type_ = 0;
            this.sourcePath_ = "";
            this.exportedSymbol_ = Collections.emptyList();
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused) {
            return new CompiledFile();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private CompiledFile(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block14: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block14;
                        }
                        case 10: {
                            String s3 = input.readStringRequireUtf8();
                            this.resourceName_ = s3;
                            continue block14;
                        }
                        case 18: {
                            ConfigurationOuterClass.Configuration.Builder subBuilder = null;
                            if (this.config_ != null) {
                                subBuilder = this.config_.toBuilder();
                            }
                            this.config_ = input.readMessage(ConfigurationOuterClass.Configuration.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            subBuilder.mergeFrom(this.config_);
                            this.config_ = subBuilder.buildPartial();
                            continue block14;
                        }
                        case 24: {
                            int rawValue;
                            this.type_ = rawValue = input.readEnum();
                            continue block14;
                        }
                        case 34: {
                            String s4 = input.readStringRequireUtf8();
                            this.sourcePath_ = s4;
                            continue block14;
                        }
                        case 42: {
                            if (!(mutable_bitField0_ & true)) {
                                this.exportedSymbol_ = new ArrayList<Symbol>();
                                mutable_bitField0_ |= true;
                            }
                            this.exportedSymbol_.add(input.readMessage(Symbol.parser(), extensionRegistry));
                            continue block14;
                        }
                    }
                    if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue;
                    done = true;
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
                    this.exportedSymbol_ = Collections.unmodifiableList(this.exportedSymbol_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_aapt_pb_internal_CompiledFile_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_aapt_pb_internal_CompiledFile_fieldAccessorTable.ensureFieldAccessorsInitialized(CompiledFile.class, Builder.class);
        }

        @Override
        public String getResourceName() {
            Object ref = this.resourceName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.resourceName_ = s3;
            return s3;
        }

        @Override
        public ByteString getResourceNameBytes() {
            Object ref = this.resourceName_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.resourceName_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasConfig() {
            return this.config_ != null;
        }

        @Override
        public ConfigurationOuterClass.Configuration getConfig() {
            return this.config_ == null ? ConfigurationOuterClass.Configuration.getDefaultInstance() : this.config_;
        }

        @Override
        public ConfigurationOuterClass.ConfigurationOrBuilder getConfigOrBuilder() {
            return this.getConfig();
        }

        @Override
        public int getTypeValue() {
            return this.type_;
        }

        @Override
        public Resources.FileReference.Type getType() {
            Resources.FileReference.Type result = Resources.FileReference.Type.valueOf(this.type_);
            return result == null ? Resources.FileReference.Type.UNRECOGNIZED : result;
        }

        @Override
        public String getSourcePath() {
            Object ref = this.sourcePath_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.sourcePath_ = s3;
            return s3;
        }

        @Override
        public ByteString getSourcePathBytes() {
            Object ref = this.sourcePath_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.sourcePath_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public List<Symbol> getExportedSymbolList() {
            return this.exportedSymbol_;
        }

        @Override
        public List<? extends SymbolOrBuilder> getExportedSymbolOrBuilderList() {
            return this.exportedSymbol_;
        }

        @Override
        public int getExportedSymbolCount() {
            return this.exportedSymbol_.size();
        }

        @Override
        public Symbol getExportedSymbol(int index) {
            return this.exportedSymbol_.get(index);
        }

        @Override
        public SymbolOrBuilder getExportedSymbolOrBuilder(int index) {
            return this.exportedSymbol_.get(index);
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
            if (!this.getResourceNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.resourceName_);
            }
            if (this.config_ != null) {
                output.writeMessage(2, this.getConfig());
            }
            if (this.type_ != Resources.FileReference.Type.UNKNOWN.getNumber()) {
                output.writeEnum(3, this.type_);
            }
            if (!this.getSourcePathBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 4, this.sourcePath_);
            }
            for (int i2 = 0; i2 < this.exportedSymbol_.size(); ++i2) {
                output.writeMessage(5, this.exportedSymbol_.get(i2));
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
            if (!this.getResourceNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.resourceName_);
            }
            if (this.config_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getConfig());
            }
            if (this.type_ != Resources.FileReference.Type.UNKNOWN.getNumber()) {
                size += CodedOutputStream.computeEnumSize(3, this.type_);
            }
            if (!this.getSourcePathBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(4, this.sourcePath_);
            }
            for (int i2 = 0; i2 < this.exportedSymbol_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(5, this.exportedSymbol_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CompiledFile)) {
                return super.equals(obj);
            }
            CompiledFile other = (CompiledFile)obj;
            if (!this.getResourceName().equals(other.getResourceName())) {
                return false;
            }
            if (this.hasConfig() != other.hasConfig()) {
                return false;
            }
            if (this.hasConfig() && !this.getConfig().equals(other.getConfig())) {
                return false;
            }
            if (this.type_ != other.type_) {
                return false;
            }
            if (!this.getSourcePath().equals(other.getSourcePath())) {
                return false;
            }
            if (!this.getExportedSymbolList().equals(other.getExportedSymbolList())) {
                return false;
            }
            return this.unknownFields.equals(other.unknownFields);
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + CompiledFile.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getResourceName().hashCode();
            if (this.hasConfig()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getConfig().hashCode();
            }
            hash = 37 * hash + 3;
            hash = 53 * hash + this.type_;
            hash = 37 * hash + 4;
            hash = 53 * hash + this.getSourcePath().hashCode();
            if (this.getExportedSymbolCount() > 0) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getExportedSymbolList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static CompiledFile parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CompiledFile parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CompiledFile parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CompiledFile parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CompiledFile parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static CompiledFile parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static CompiledFile parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CompiledFile parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static CompiledFile parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static CompiledFile parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static CompiledFile parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static CompiledFile parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return CompiledFile.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(CompiledFile prototype) {
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

        public static CompiledFile getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CompiledFile> parser() {
            return PARSER;
        }

        public Parser<CompiledFile> getParserForType() {
            return PARSER;
        }

        @Override
        public CompiledFile getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements CompiledFileOrBuilder {
            private int bitField0_;
            private Object resourceName_ = "";
            private ConfigurationOuterClass.Configuration config_;
            private SingleFieldBuilderV3<ConfigurationOuterClass.Configuration, ConfigurationOuterClass.Configuration.Builder, ConfigurationOuterClass.ConfigurationOrBuilder> configBuilder_;
            private int type_ = 0;
            private Object sourcePath_ = "";
            private List<Symbol> exportedSymbol_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Symbol, Symbol.Builder, SymbolOrBuilder> exportedSymbolBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_aapt_pb_internal_CompiledFile_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_aapt_pb_internal_CompiledFile_fieldAccessorTable.ensureFieldAccessorsInitialized(CompiledFile.class, Builder.class);
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
                    this.getExportedSymbolFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                this.resourceName_ = "";
                if (this.configBuilder_ == null) {
                    this.config_ = null;
                } else {
                    this.config_ = null;
                    this.configBuilder_ = null;
                }
                this.type_ = 0;
                this.sourcePath_ = "";
                if (this.exportedSymbolBuilder_ == null) {
                    this.exportedSymbol_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.exportedSymbolBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_aapt_pb_internal_CompiledFile_descriptor;
            }

            @Override
            public CompiledFile getDefaultInstanceForType() {
                return CompiledFile.getDefaultInstance();
            }

            @Override
            public CompiledFile build() {
                CompiledFile result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public CompiledFile buildPartial() {
                CompiledFile result = new CompiledFile(this);
                int from_bitField0_ = this.bitField0_;
                result.resourceName_ = this.resourceName_;
                if (this.configBuilder_ == null) {
                    result.config_ = this.config_;
                } else {
                    result.config_ = this.configBuilder_.build();
                }
                result.type_ = this.type_;
                result.sourcePath_ = this.sourcePath_;
                if (this.exportedSymbolBuilder_ == null) {
                    if ((this.bitField0_ & 1) != 0) {
                        this.exportedSymbol_ = Collections.unmodifiableList(this.exportedSymbol_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.exportedSymbol_ = this.exportedSymbol_;
                } else {
                    result.exportedSymbol_ = this.exportedSymbolBuilder_.build();
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
                if (other instanceof CompiledFile) {
                    return this.mergeFrom((CompiledFile)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(CompiledFile other) {
                if (other == CompiledFile.getDefaultInstance()) {
                    return this;
                }
                if (!other.getResourceName().isEmpty()) {
                    this.resourceName_ = other.resourceName_;
                    this.onChanged();
                }
                if (other.hasConfig()) {
                    this.mergeConfig(other.getConfig());
                }
                if (other.type_ != 0) {
                    this.setTypeValue(other.getTypeValue());
                }
                if (!other.getSourcePath().isEmpty()) {
                    this.sourcePath_ = other.sourcePath_;
                    this.onChanged();
                }
                if (this.exportedSymbolBuilder_ == null) {
                    if (!other.exportedSymbol_.isEmpty()) {
                        if (this.exportedSymbol_.isEmpty()) {
                            this.exportedSymbol_ = other.exportedSymbol_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureExportedSymbolIsMutable();
                            this.exportedSymbol_.addAll(other.exportedSymbol_);
                        }
                        this.onChanged();
                    }
                } else if (!other.exportedSymbol_.isEmpty()) {
                    if (this.exportedSymbolBuilder_.isEmpty()) {
                        this.exportedSymbolBuilder_.dispose();
                        this.exportedSymbolBuilder_ = null;
                        this.exportedSymbol_ = other.exportedSymbol_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.exportedSymbolBuilder_ = alwaysUseFieldBuilders ? this.getExportedSymbolFieldBuilder() : null;
                    } else {
                        this.exportedSymbolBuilder_.addAllMessages(other.exportedSymbol_);
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
                CompiledFile parsedMessage = null;
                try {
                    parsedMessage = (CompiledFile)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (CompiledFile)e2.getUnfinishedMessage();
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
            public String getResourceName() {
                Object ref = this.resourceName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.resourceName_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getResourceNameBytes() {
                Object ref = this.resourceName_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.resourceName_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setResourceName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.resourceName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearResourceName() {
                this.resourceName_ = CompiledFile.getDefaultInstance().getResourceName();
                this.onChanged();
                return this;
            }

            public Builder setResourceNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                CompiledFile.checkByteStringIsUtf8(value);
                this.resourceName_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasConfig() {
                return this.configBuilder_ != null || this.config_ != null;
            }

            @Override
            public ConfigurationOuterClass.Configuration getConfig() {
                if (this.configBuilder_ == null) {
                    return this.config_ == null ? ConfigurationOuterClass.Configuration.getDefaultInstance() : this.config_;
                }
                return this.configBuilder_.getMessage();
            }

            public Builder setConfig(ConfigurationOuterClass.Configuration value) {
                if (this.configBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.config_ = value;
                    this.onChanged();
                } else {
                    this.configBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setConfig(ConfigurationOuterClass.Configuration.Builder builderForValue) {
                if (this.configBuilder_ == null) {
                    this.config_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.configBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeConfig(ConfigurationOuterClass.Configuration value) {
                if (this.configBuilder_ == null) {
                    this.config_ = this.config_ != null ? ConfigurationOuterClass.Configuration.newBuilder(this.config_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.configBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearConfig() {
                if (this.configBuilder_ == null) {
                    this.config_ = null;
                    this.onChanged();
                } else {
                    this.config_ = null;
                    this.configBuilder_ = null;
                }
                return this;
            }

            public ConfigurationOuterClass.Configuration.Builder getConfigBuilder() {
                this.onChanged();
                return this.getConfigFieldBuilder().getBuilder();
            }

            @Override
            public ConfigurationOuterClass.ConfigurationOrBuilder getConfigOrBuilder() {
                if (this.configBuilder_ != null) {
                    return this.configBuilder_.getMessageOrBuilder();
                }
                return this.config_ == null ? ConfigurationOuterClass.Configuration.getDefaultInstance() : this.config_;
            }

            private SingleFieldBuilderV3<ConfigurationOuterClass.Configuration, ConfigurationOuterClass.Configuration.Builder, ConfigurationOuterClass.ConfigurationOrBuilder> getConfigFieldBuilder() {
                if (this.configBuilder_ == null) {
                    this.configBuilder_ = new SingleFieldBuilderV3(this.getConfig(), this.getParentForChildren(), this.isClean());
                    this.config_ = null;
                }
                return this.configBuilder_;
            }

            @Override
            public int getTypeValue() {
                return this.type_;
            }

            public Builder setTypeValue(int value) {
                this.type_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public Resources.FileReference.Type getType() {
                Resources.FileReference.Type result = Resources.FileReference.Type.valueOf(this.type_);
                return result == null ? Resources.FileReference.Type.UNRECOGNIZED : result;
            }

            public Builder setType(Resources.FileReference.Type value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.type_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearType() {
                this.type_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public String getSourcePath() {
                Object ref = this.sourcePath_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.sourcePath_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getSourcePathBytes() {
                Object ref = this.sourcePath_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.sourcePath_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setSourcePath(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.sourcePath_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearSourcePath() {
                this.sourcePath_ = CompiledFile.getDefaultInstance().getSourcePath();
                this.onChanged();
                return this;
            }

            public Builder setSourcePathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                CompiledFile.checkByteStringIsUtf8(value);
                this.sourcePath_ = value;
                this.onChanged();
                return this;
            }

            private void ensureExportedSymbolIsMutable() {
                if ((this.bitField0_ & 1) == 0) {
                    this.exportedSymbol_ = new ArrayList<Symbol>(this.exportedSymbol_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<Symbol> getExportedSymbolList() {
                if (this.exportedSymbolBuilder_ == null) {
                    return Collections.unmodifiableList(this.exportedSymbol_);
                }
                return this.exportedSymbolBuilder_.getMessageList();
            }

            @Override
            public int getExportedSymbolCount() {
                if (this.exportedSymbolBuilder_ == null) {
                    return this.exportedSymbol_.size();
                }
                return this.exportedSymbolBuilder_.getCount();
            }

            @Override
            public Symbol getExportedSymbol(int index) {
                if (this.exportedSymbolBuilder_ == null) {
                    return this.exportedSymbol_.get(index);
                }
                return this.exportedSymbolBuilder_.getMessage(index);
            }

            public Builder setExportedSymbol(int index, Symbol value) {
                if (this.exportedSymbolBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureExportedSymbolIsMutable();
                    this.exportedSymbol_.set(index, value);
                    this.onChanged();
                } else {
                    this.exportedSymbolBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setExportedSymbol(int index, Symbol.Builder builderForValue) {
                if (this.exportedSymbolBuilder_ == null) {
                    this.ensureExportedSymbolIsMutable();
                    this.exportedSymbol_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.exportedSymbolBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addExportedSymbol(Symbol value) {
                if (this.exportedSymbolBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureExportedSymbolIsMutable();
                    this.exportedSymbol_.add(value);
                    this.onChanged();
                } else {
                    this.exportedSymbolBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addExportedSymbol(int index, Symbol value) {
                if (this.exportedSymbolBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureExportedSymbolIsMutable();
                    this.exportedSymbol_.add(index, value);
                    this.onChanged();
                } else {
                    this.exportedSymbolBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addExportedSymbol(Symbol.Builder builderForValue) {
                if (this.exportedSymbolBuilder_ == null) {
                    this.ensureExportedSymbolIsMutable();
                    this.exportedSymbol_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.exportedSymbolBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addExportedSymbol(int index, Symbol.Builder builderForValue) {
                if (this.exportedSymbolBuilder_ == null) {
                    this.ensureExportedSymbolIsMutable();
                    this.exportedSymbol_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.exportedSymbolBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllExportedSymbol(Iterable<? extends Symbol> values2) {
                if (this.exportedSymbolBuilder_ == null) {
                    this.ensureExportedSymbolIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.exportedSymbol_);
                    this.onChanged();
                } else {
                    this.exportedSymbolBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearExportedSymbol() {
                if (this.exportedSymbolBuilder_ == null) {
                    this.exportedSymbol_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.exportedSymbolBuilder_.clear();
                }
                return this;
            }

            public Builder removeExportedSymbol(int index) {
                if (this.exportedSymbolBuilder_ == null) {
                    this.ensureExportedSymbolIsMutable();
                    this.exportedSymbol_.remove(index);
                    this.onChanged();
                } else {
                    this.exportedSymbolBuilder_.remove(index);
                }
                return this;
            }

            public Symbol.Builder getExportedSymbolBuilder(int index) {
                return this.getExportedSymbolFieldBuilder().getBuilder(index);
            }

            @Override
            public SymbolOrBuilder getExportedSymbolOrBuilder(int index) {
                if (this.exportedSymbolBuilder_ == null) {
                    return this.exportedSymbol_.get(index);
                }
                return this.exportedSymbolBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends SymbolOrBuilder> getExportedSymbolOrBuilderList() {
                if (this.exportedSymbolBuilder_ != null) {
                    return this.exportedSymbolBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.exportedSymbol_);
            }

            public Symbol.Builder addExportedSymbolBuilder() {
                return this.getExportedSymbolFieldBuilder().addBuilder(Symbol.getDefaultInstance());
            }

            public Symbol.Builder addExportedSymbolBuilder(int index) {
                return this.getExportedSymbolFieldBuilder().addBuilder(index, Symbol.getDefaultInstance());
            }

            public List<Symbol.Builder> getExportedSymbolBuilderList() {
                return this.getExportedSymbolFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Symbol, Symbol.Builder, SymbolOrBuilder> getExportedSymbolFieldBuilder() {
                if (this.exportedSymbolBuilder_ == null) {
                    this.exportedSymbolBuilder_ = new RepeatedFieldBuilderV3(this.exportedSymbol_, (this.bitField0_ & 1) != 0, this.getParentForChildren(), this.isClean());
                    this.exportedSymbol_ = null;
                }
                return this.exportedSymbolBuilder_;
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

        public static final class Symbol
        extends GeneratedMessageV3
        implements SymbolOrBuilder {
            private static final long serialVersionUID = 0L;
            public static final int RESOURCE_NAME_FIELD_NUMBER = 1;
            private volatile Object resourceName_;
            public static final int SOURCE_FIELD_NUMBER = 2;
            private Resources.SourcePosition source_;
            private byte memoizedIsInitialized = (byte)-1;
            private static final Symbol DEFAULT_INSTANCE = new Symbol();
            private static final Parser<Symbol> PARSER = new AbstractParser<Symbol>(){

                @Override
                public Symbol parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Symbol(input, extensionRegistry);
                }
            };

            private Symbol(GeneratedMessageV3.Builder<?> builder) {
                super(builder);
            }

            private Symbol() {
                this.resourceName_ = "";
            }

            @Override
            protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused) {
                return new Symbol();
            }

            @Override
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private Symbol(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this();
                if (extensionRegistry == null) {
                    throw new NullPointerException();
                }
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
                            case 10: {
                                String s3 = input.readStringRequireUtf8();
                                this.resourceName_ = s3;
                                continue block11;
                            }
                            case 18: {
                                Resources.SourcePosition.Builder subBuilder = null;
                                if (this.source_ != null) {
                                    subBuilder = this.source_.toBuilder();
                                }
                                this.source_ = input.readMessage(Resources.SourcePosition.parser(), extensionRegistry);
                                if (subBuilder == null) continue block11;
                                subBuilder.mergeFrom(this.source_);
                                this.source_ = subBuilder.buildPartial();
                                continue block11;
                            }
                        }
                        if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue;
                        done = true;
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
                return internal_static_aapt_pb_internal_CompiledFile_Symbol_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_aapt_pb_internal_CompiledFile_Symbol_fieldAccessorTable.ensureFieldAccessorsInitialized(Symbol.class, Builder.class);
            }

            @Override
            public String getResourceName() {
                Object ref = this.resourceName_;
                if (ref instanceof String) {
                    return (String)ref;
                }
                ByteString bs = (ByteString)ref;
                String s3 = bs.toStringUtf8();
                this.resourceName_ = s3;
                return s3;
            }

            @Override
            public ByteString getResourceNameBytes() {
                Object ref = this.resourceName_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.resourceName_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            @Override
            public boolean hasSource() {
                return this.source_ != null;
            }

            @Override
            public Resources.SourcePosition getSource() {
                return this.source_ == null ? Resources.SourcePosition.getDefaultInstance() : this.source_;
            }

            @Override
            public Resources.SourcePositionOrBuilder getSourceOrBuilder() {
                return this.getSource();
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
                if (!this.getResourceNameBytes().isEmpty()) {
                    GeneratedMessageV3.writeString(output, 1, this.resourceName_);
                }
                if (this.source_ != null) {
                    output.writeMessage(2, this.getSource());
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
                if (!this.getResourceNameBytes().isEmpty()) {
                    size += GeneratedMessageV3.computeStringSize(1, this.resourceName_);
                }
                if (this.source_ != null) {
                    size += CodedOutputStream.computeMessageSize(2, this.getSource());
                }
                this.memoizedSize = size += this.unknownFields.getSerializedSize();
                return size;
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Symbol)) {
                    return super.equals(obj);
                }
                Symbol other = (Symbol)obj;
                if (!this.getResourceName().equals(other.getResourceName())) {
                    return false;
                }
                if (this.hasSource() != other.hasSource()) {
                    return false;
                }
                if (this.hasSource() && !this.getSource().equals(other.getSource())) {
                    return false;
                }
                return this.unknownFields.equals(other.unknownFields);
            }

            @Override
            public int hashCode() {
                if (this.memoizedHashCode != 0) {
                    return this.memoizedHashCode;
                }
                int hash = 41;
                hash = 19 * hash + Symbol.getDescriptor().hashCode();
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getResourceName().hashCode();
                if (this.hasSource()) {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getSource().hashCode();
                }
                this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
                return hash;
            }

            public static Symbol parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Symbol parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Symbol parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Symbol parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Symbol parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }

            public static Symbol parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }

            public static Symbol parseFrom(InputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Symbol parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            public static Symbol parseDelimitedFrom(InputStream input) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
            }

            public static Symbol parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }

            public static Symbol parseFrom(CodedInputStream input) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input);
            }

            public static Symbol parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
            }

            @Override
            public Builder newBuilderForType() {
                return Symbol.newBuilder();
            }

            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }

            public static Builder newBuilder(Symbol prototype) {
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

            public static Symbol getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Symbol> parser() {
                return PARSER;
            }

            public Parser<Symbol> getParserForType() {
                return PARSER;
            }

            @Override
            public Symbol getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }

            public static final class Builder
            extends GeneratedMessageV3.Builder<Builder>
            implements SymbolOrBuilder {
                private Object resourceName_ = "";
                private Resources.SourcePosition source_;
                private SingleFieldBuilderV3<Resources.SourcePosition, Resources.SourcePosition.Builder, Resources.SourcePositionOrBuilder> sourceBuilder_;

                public static final Descriptors.Descriptor getDescriptor() {
                    return internal_static_aapt_pb_internal_CompiledFile_Symbol_descriptor;
                }

                @Override
                protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                    return internal_static_aapt_pb_internal_CompiledFile_Symbol_fieldAccessorTable.ensureFieldAccessorsInitialized(Symbol.class, Builder.class);
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
                    this.resourceName_ = "";
                    if (this.sourceBuilder_ == null) {
                        this.source_ = null;
                    } else {
                        this.source_ = null;
                        this.sourceBuilder_ = null;
                    }
                    return this;
                }

                @Override
                public Descriptors.Descriptor getDescriptorForType() {
                    return internal_static_aapt_pb_internal_CompiledFile_Symbol_descriptor;
                }

                @Override
                public Symbol getDefaultInstanceForType() {
                    return Symbol.getDefaultInstance();
                }

                @Override
                public Symbol build() {
                    Symbol result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw Builder.newUninitializedMessageException(result);
                    }
                    return result;
                }

                @Override
                public Symbol buildPartial() {
                    Symbol result = new Symbol(this);
                    result.resourceName_ = this.resourceName_;
                    if (this.sourceBuilder_ == null) {
                        result.source_ = this.source_;
                    } else {
                        result.source_ = this.sourceBuilder_.build();
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
                    if (other instanceof Symbol) {
                        return this.mergeFrom((Symbol)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(Symbol other) {
                    if (other == Symbol.getDefaultInstance()) {
                        return this;
                    }
                    if (!other.getResourceName().isEmpty()) {
                        this.resourceName_ = other.resourceName_;
                        this.onChanged();
                    }
                    if (other.hasSource()) {
                        this.mergeSource(other.getSource());
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
                    Symbol parsedMessage = null;
                    try {
                        parsedMessage = (Symbol)PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e2) {
                        parsedMessage = (Symbol)e2.getUnfinishedMessage();
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
                public String getResourceName() {
                    Object ref = this.resourceName_;
                    if (!(ref instanceof String)) {
                        ByteString bs = (ByteString)ref;
                        String s3 = bs.toStringUtf8();
                        this.resourceName_ = s3;
                        return s3;
                    }
                    return (String)ref;
                }

                @Override
                public ByteString getResourceNameBytes() {
                    Object ref = this.resourceName_;
                    if (ref instanceof String) {
                        ByteString b2 = ByteString.copyFromUtf8((String)ref);
                        this.resourceName_ = b2;
                        return b2;
                    }
                    return (ByteString)ref;
                }

                public Builder setResourceName(String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.resourceName_ = value;
                    this.onChanged();
                    return this;
                }

                public Builder clearResourceName() {
                    this.resourceName_ = Symbol.getDefaultInstance().getResourceName();
                    this.onChanged();
                    return this;
                }

                public Builder setResourceNameBytes(ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    Symbol.checkByteStringIsUtf8(value);
                    this.resourceName_ = value;
                    this.onChanged();
                    return this;
                }

                @Override
                public boolean hasSource() {
                    return this.sourceBuilder_ != null || this.source_ != null;
                }

                @Override
                public Resources.SourcePosition getSource() {
                    if (this.sourceBuilder_ == null) {
                        return this.source_ == null ? Resources.SourcePosition.getDefaultInstance() : this.source_;
                    }
                    return this.sourceBuilder_.getMessage();
                }

                public Builder setSource(Resources.SourcePosition value) {
                    if (this.sourceBuilder_ == null) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.source_ = value;
                        this.onChanged();
                    } else {
                        this.sourceBuilder_.setMessage(value);
                    }
                    return this;
                }

                public Builder setSource(Resources.SourcePosition.Builder builderForValue) {
                    if (this.sourceBuilder_ == null) {
                        this.source_ = builderForValue.build();
                        this.onChanged();
                    } else {
                        this.sourceBuilder_.setMessage(builderForValue.build());
                    }
                    return this;
                }

                public Builder mergeSource(Resources.SourcePosition value) {
                    if (this.sourceBuilder_ == null) {
                        this.source_ = this.source_ != null ? Resources.SourcePosition.newBuilder(this.source_).mergeFrom(value).buildPartial() : value;
                        this.onChanged();
                    } else {
                        this.sourceBuilder_.mergeFrom(value);
                    }
                    return this;
                }

                public Builder clearSource() {
                    if (this.sourceBuilder_ == null) {
                        this.source_ = null;
                        this.onChanged();
                    } else {
                        this.source_ = null;
                        this.sourceBuilder_ = null;
                    }
                    return this;
                }

                public Resources.SourcePosition.Builder getSourceBuilder() {
                    this.onChanged();
                    return this.getSourceFieldBuilder().getBuilder();
                }

                @Override
                public Resources.SourcePositionOrBuilder getSourceOrBuilder() {
                    if (this.sourceBuilder_ != null) {
                        return this.sourceBuilder_.getMessageOrBuilder();
                    }
                    return this.source_ == null ? Resources.SourcePosition.getDefaultInstance() : this.source_;
                }

                private SingleFieldBuilderV3<Resources.SourcePosition, Resources.SourcePosition.Builder, Resources.SourcePositionOrBuilder> getSourceFieldBuilder() {
                    if (this.sourceBuilder_ == null) {
                        this.sourceBuilder_ = new SingleFieldBuilderV3(this.getSource(), this.getParentForChildren(), this.isClean());
                        this.source_ = null;
                    }
                    return this.sourceBuilder_;
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

        public static interface SymbolOrBuilder
        extends MessageOrBuilder {
            public String getResourceName();

            public ByteString getResourceNameBytes();

            public boolean hasSource();

            public Resources.SourcePosition getSource();

            public Resources.SourcePositionOrBuilder getSourceOrBuilder();
        }
    }

    public static interface CompiledFileOrBuilder
    extends MessageOrBuilder {
        public String getResourceName();

        public ByteString getResourceNameBytes();

        public boolean hasConfig();

        public ConfigurationOuterClass.Configuration getConfig();

        public ConfigurationOuterClass.ConfigurationOrBuilder getConfigOrBuilder();

        public int getTypeValue();

        public Resources.FileReference.Type getType();

        public String getSourcePath();

        public ByteString getSourcePathBytes();

        public List<CompiledFile.Symbol> getExportedSymbolList();

        public CompiledFile.Symbol getExportedSymbol(int var1);

        public int getExportedSymbolCount();

        public List<? extends CompiledFile.SymbolOrBuilder> getExportedSymbolOrBuilderList();

        public CompiledFile.SymbolOrBuilder getExportedSymbolOrBuilder(int var1);
    }
}

