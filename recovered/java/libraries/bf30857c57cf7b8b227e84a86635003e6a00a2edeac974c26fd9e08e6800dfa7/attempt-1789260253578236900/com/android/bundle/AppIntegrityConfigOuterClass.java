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
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public final class AppIntegrityConfigOuterClass {
    private static final Descriptors.Descriptor internal_static_android_bundle_AppIntegrityConfig_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_AppIntegrityConfig_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_LicenseCheck_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_LicenseCheck_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_InstallerCheck_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_InstallerCheck_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_DebuggerCheck_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_DebuggerCheck_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_EmulatorCheck_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_EmulatorCheck_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_Policy_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Policy_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private AppIntegrityConfigOuterClass() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        AppIntegrityConfigOuterClass.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001aapp_integrity_config.proto\u0012\u000eandroid.bundle\"\u0081\u0002\n\u0012AppIntegrityConfig\u0012\u000f\n\u0007enabled\u0018\u0001 \u0001(\b\u00123\n\rlicense_check\u0018\u0002 \u0001(\u000b2\u001c.android.bundle.LicenseCheck\u00127\n\u000finstaller_check\u0018\u0003 \u0001(\u000b2\u001e.android.bundle.InstallerCheck\u00125\n\u000edebugger_check\u0018\u0004 \u0001(\u000b2\u001d.android.bundle.DebuggerCheck\u00125\n\u000eemulator_check\u0018\u0005 \u0001(\u000b2\u001d.android.bundle.EmulatorCheck\"\\\n\fLicenseCheck\u0012\u000f\n\u0007enabled\u0018\u0001 \u0001(\b\u0012\u0013\n\u000bonline_only\u0018\u0002 \u0001(\b\u0012&\n\u0006policy\u0018\u0003 \u0001(\u000b2\u0016.android.bundle.Policy\"l", "\n\u000eInstallerCheck\u0012\u000f\n\u0007enabled\u0018\u0001 \u0001(\b\u0012&\n\u0006policy\u0018\u0002 \u0001(\u000b2\u0016.android.bundle.Policy\u0012!\n\u0019additional_install_source\u0018\u0003 \u0003(\t\" \n\rDebuggerCheck\u0012\u000f\n\u0007enabled\u0018\u0001 \u0001(\b\" \n\rEmulatorCheck\u0012\u000f\n\u0007enabled\u0018\u0001 \u0001(\b\"\u0080\u0001\n\u0006Policy\u0012-\n\u0006action\u0018\u0001 \u0001(\u000e2\u001d.android.bundle.Policy.Action\"G\n\u0006Action\u0012\u000f\n\u000bUNSPECIFIED\u0010\u0000\u0012\b\n\u0004WARN\u0010\u0001\u0012\u000b\n\u0007DISABLE\u0010\u0002\u0012\u0015\n\u0011WARN_THEN_DISABLE\u0010\u0003B\u0014\n\u0012com.android.bundleb\u0006proto3"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            @Override
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0], assigner);
        internal_static_android_bundle_AppIntegrityConfig_descriptor = AppIntegrityConfigOuterClass.getDescriptor().getMessageTypes().get(0);
        internal_static_android_bundle_AppIntegrityConfig_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_AppIntegrityConfig_descriptor, new String[]{"Enabled", "LicenseCheck", "InstallerCheck", "DebuggerCheck", "EmulatorCheck"});
        internal_static_android_bundle_LicenseCheck_descriptor = AppIntegrityConfigOuterClass.getDescriptor().getMessageTypes().get(1);
        internal_static_android_bundle_LicenseCheck_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_LicenseCheck_descriptor, new String[]{"Enabled", "OnlineOnly", "Policy"});
        internal_static_android_bundle_InstallerCheck_descriptor = AppIntegrityConfigOuterClass.getDescriptor().getMessageTypes().get(2);
        internal_static_android_bundle_InstallerCheck_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_InstallerCheck_descriptor, new String[]{"Enabled", "Policy", "AdditionalInstallSource"});
        internal_static_android_bundle_DebuggerCheck_descriptor = AppIntegrityConfigOuterClass.getDescriptor().getMessageTypes().get(3);
        internal_static_android_bundle_DebuggerCheck_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_DebuggerCheck_descriptor, new String[]{"Enabled"});
        internal_static_android_bundle_EmulatorCheck_descriptor = AppIntegrityConfigOuterClass.getDescriptor().getMessageTypes().get(4);
        internal_static_android_bundle_EmulatorCheck_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_EmulatorCheck_descriptor, new String[]{"Enabled"});
        internal_static_android_bundle_Policy_descriptor = AppIntegrityConfigOuterClass.getDescriptor().getMessageTypes().get(5);
        internal_static_android_bundle_Policy_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Policy_descriptor, new String[]{"Action"});
    }

    public static final class Policy
    extends GeneratedMessageV3
    implements PolicyOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ACTION_FIELD_NUMBER = 1;
        private int action_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Policy DEFAULT_INSTANCE = new Policy();
        private static final Parser<Policy> PARSER = new AbstractParser<Policy>(){

            @Override
            public Policy parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Policy(input, extensionRegistry);
            }
        };

        private Policy(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Policy() {
            this.action_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Policy(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block10: while (!done) {
                    int rawValue;
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
                    this.action_ = rawValue = input.readEnum();
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
            return internal_static_android_bundle_Policy_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Policy_fieldAccessorTable.ensureFieldAccessorsInitialized(Policy.class, Builder.class);
        }

        @Override
        public int getActionValue() {
            return this.action_;
        }

        @Override
        public Action getAction() {
            Action result = Action.valueOf(this.action_);
            return result == null ? Action.UNRECOGNIZED : result;
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
            if (this.action_ != Action.UNSPECIFIED.getNumber()) {
                output.writeEnum(1, this.action_);
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
            if (this.action_ != Action.UNSPECIFIED.getNumber()) {
                size += CodedOutputStream.computeEnumSize(1, this.action_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Policy)) {
                return super.equals(obj);
            }
            Policy other = (Policy)obj;
            boolean result = true;
            result = result && this.action_ == other.action_;
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Policy.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.action_;
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Policy parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Policy parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Policy parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Policy parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Policy parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Policy parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Policy parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Policy parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Policy parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Policy parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Policy parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Policy parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Policy.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Policy prototype) {
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

        public static Policy getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Policy> parser() {
            return PARSER;
        }

        public Parser<Policy> getParserForType() {
            return PARSER;
        }

        @Override
        public Policy getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements PolicyOrBuilder {
            private int action_ = 0;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Policy_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Policy_fieldAccessorTable.ensureFieldAccessorsInitialized(Policy.class, Builder.class);
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
                this.action_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Policy_descriptor;
            }

            @Override
            public Policy getDefaultInstanceForType() {
                return Policy.getDefaultInstance();
            }

            @Override
            public Policy build() {
                Policy result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Policy buildPartial() {
                Policy result = new Policy(this);
                result.action_ = this.action_;
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
                if (other instanceof Policy) {
                    return this.mergeFrom((Policy)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Policy other) {
                if (other == Policy.getDefaultInstance()) {
                    return this;
                }
                if (other.action_ != 0) {
                    this.setActionValue(other.getActionValue());
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
                Policy parsedMessage = null;
                try {
                    parsedMessage = (Policy)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Policy)e2.getUnfinishedMessage();
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
            public int getActionValue() {
                return this.action_;
            }

            public Builder setActionValue(int value) {
                this.action_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public Action getAction() {
                Action result = Action.valueOf(this.action_);
                return result == null ? Action.UNRECOGNIZED : result;
            }

            public Builder setAction(Action value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.action_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearAction() {
                this.action_ = 0;
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

        public static enum Action implements ProtocolMessageEnum
        {
            UNSPECIFIED(0),
            WARN(1),
            DISABLE(2),
            WARN_THEN_DISABLE(3),
            UNRECOGNIZED(-1);

            public static final int UNSPECIFIED_VALUE = 0;
            public static final int WARN_VALUE = 1;
            public static final int DISABLE_VALUE = 2;
            public static final int WARN_THEN_DISABLE_VALUE = 3;
            private static final Internal.EnumLiteMap<Action> internalValueMap;
            private static final Action[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static Action valueOf(int value) {
                return Action.forNumber(value);
            }

            public static Action forNumber(int value) {
                switch (value) {
                    case 0: {
                        return UNSPECIFIED;
                    }
                    case 1: {
                        return WARN;
                    }
                    case 2: {
                        return DISABLE;
                    }
                    case 3: {
                        return WARN_THEN_DISABLE;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<Action> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return Action.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return Action.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Policy.getDescriptor().getEnumTypes().get(0);
            }

            public static Action valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != Action.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private Action(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<Action>(){

                    @Override
                    public Action findValueByNumber(int number) {
                        return Action.forNumber(number);
                    }
                };
                VALUES = Action.values();
            }
        }
    }

    public static interface PolicyOrBuilder
    extends MessageOrBuilder {
        public int getActionValue();

        public Policy.Action getAction();
    }

    public static final class EmulatorCheck
    extends GeneratedMessageV3
    implements EmulatorCheckOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ENABLED_FIELD_NUMBER = 1;
        private boolean enabled_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final EmulatorCheck DEFAULT_INSTANCE = new EmulatorCheck();
        private static final Parser<EmulatorCheck> PARSER = new AbstractParser<EmulatorCheck>(){

            @Override
            public EmulatorCheck parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new EmulatorCheck(input, extensionRegistry);
            }
        };

        private EmulatorCheck(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private EmulatorCheck() {
            this.enabled_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private EmulatorCheck(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return internal_static_android_bundle_EmulatorCheck_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_EmulatorCheck_fieldAccessorTable.ensureFieldAccessorsInitialized(EmulatorCheck.class, Builder.class);
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
            if (!(obj instanceof EmulatorCheck)) {
                return super.equals(obj);
            }
            EmulatorCheck other = (EmulatorCheck)obj;
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
            hash = 19 * hash + EmulatorCheck.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getEnabled());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static EmulatorCheck parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static EmulatorCheck parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static EmulatorCheck parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static EmulatorCheck parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static EmulatorCheck parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static EmulatorCheck parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static EmulatorCheck parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static EmulatorCheck parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static EmulatorCheck parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static EmulatorCheck parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static EmulatorCheck parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static EmulatorCheck parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return EmulatorCheck.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(EmulatorCheck prototype) {
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

        public static EmulatorCheck getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<EmulatorCheck> parser() {
            return PARSER;
        }

        public Parser<EmulatorCheck> getParserForType() {
            return PARSER;
        }

        @Override
        public EmulatorCheck getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements EmulatorCheckOrBuilder {
            private boolean enabled_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_EmulatorCheck_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_EmulatorCheck_fieldAccessorTable.ensureFieldAccessorsInitialized(EmulatorCheck.class, Builder.class);
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
                return internal_static_android_bundle_EmulatorCheck_descriptor;
            }

            @Override
            public EmulatorCheck getDefaultInstanceForType() {
                return EmulatorCheck.getDefaultInstance();
            }

            @Override
            public EmulatorCheck build() {
                EmulatorCheck result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public EmulatorCheck buildPartial() {
                EmulatorCheck result = new EmulatorCheck(this);
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
                if (other instanceof EmulatorCheck) {
                    return this.mergeFrom((EmulatorCheck)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(EmulatorCheck other) {
                if (other == EmulatorCheck.getDefaultInstance()) {
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
                EmulatorCheck parsedMessage = null;
                try {
                    parsedMessage = (EmulatorCheck)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (EmulatorCheck)e2.getUnfinishedMessage();
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

    public static interface EmulatorCheckOrBuilder
    extends MessageOrBuilder {
        public boolean getEnabled();
    }

    public static final class DebuggerCheck
    extends GeneratedMessageV3
    implements DebuggerCheckOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ENABLED_FIELD_NUMBER = 1;
        private boolean enabled_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final DebuggerCheck DEFAULT_INSTANCE = new DebuggerCheck();
        private static final Parser<DebuggerCheck> PARSER = new AbstractParser<DebuggerCheck>(){

            @Override
            public DebuggerCheck parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DebuggerCheck(input, extensionRegistry);
            }
        };

        private DebuggerCheck(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private DebuggerCheck() {
            this.enabled_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DebuggerCheck(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return internal_static_android_bundle_DebuggerCheck_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_DebuggerCheck_fieldAccessorTable.ensureFieldAccessorsInitialized(DebuggerCheck.class, Builder.class);
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
            if (!(obj instanceof DebuggerCheck)) {
                return super.equals(obj);
            }
            DebuggerCheck other = (DebuggerCheck)obj;
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
            hash = 19 * hash + DebuggerCheck.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getEnabled());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static DebuggerCheck parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DebuggerCheck parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DebuggerCheck parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DebuggerCheck parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DebuggerCheck parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DebuggerCheck parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DebuggerCheck parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DebuggerCheck parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DebuggerCheck parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DebuggerCheck parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DebuggerCheck parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DebuggerCheck parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return DebuggerCheck.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DebuggerCheck prototype) {
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

        public static DebuggerCheck getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DebuggerCheck> parser() {
            return PARSER;
        }

        public Parser<DebuggerCheck> getParserForType() {
            return PARSER;
        }

        @Override
        public DebuggerCheck getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements DebuggerCheckOrBuilder {
            private boolean enabled_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_DebuggerCheck_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_DebuggerCheck_fieldAccessorTable.ensureFieldAccessorsInitialized(DebuggerCheck.class, Builder.class);
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
                return internal_static_android_bundle_DebuggerCheck_descriptor;
            }

            @Override
            public DebuggerCheck getDefaultInstanceForType() {
                return DebuggerCheck.getDefaultInstance();
            }

            @Override
            public DebuggerCheck build() {
                DebuggerCheck result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public DebuggerCheck buildPartial() {
                DebuggerCheck result = new DebuggerCheck(this);
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
                if (other instanceof DebuggerCheck) {
                    return this.mergeFrom((DebuggerCheck)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DebuggerCheck other) {
                if (other == DebuggerCheck.getDefaultInstance()) {
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
                DebuggerCheck parsedMessage = null;
                try {
                    parsedMessage = (DebuggerCheck)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (DebuggerCheck)e2.getUnfinishedMessage();
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

    public static interface DebuggerCheckOrBuilder
    extends MessageOrBuilder {
        public boolean getEnabled();
    }

    public static final class InstallerCheck
    extends GeneratedMessageV3
    implements InstallerCheckOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int ENABLED_FIELD_NUMBER = 1;
        private boolean enabled_;
        public static final int POLICY_FIELD_NUMBER = 2;
        private Policy policy_;
        public static final int ADDITIONAL_INSTALL_SOURCE_FIELD_NUMBER = 3;
        private LazyStringList additionalInstallSource_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final InstallerCheck DEFAULT_INSTANCE = new InstallerCheck();
        private static final Parser<InstallerCheck> PARSER = new AbstractParser<InstallerCheck>(){

            @Override
            public InstallerCheck parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new InstallerCheck(input, extensionRegistry);
            }
        };

        private InstallerCheck(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private InstallerCheck() {
            this.enabled_ = false;
            this.additionalInstallSource_ = LazyStringArrayList.EMPTY;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private InstallerCheck(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.enabled_ = input.readBool();
                            continue block12;
                        }
                        case 18: {
                            Policy.Builder subBuilder = null;
                            if (this.policy_ != null) {
                                subBuilder = this.policy_.toBuilder();
                            }
                            this.policy_ = input.readMessage(Policy.parser(), extensionRegistry);
                            if (subBuilder == null) continue block12;
                            subBuilder.mergeFrom(this.policy_);
                            this.policy_ = subBuilder.buildPartial();
                            continue block12;
                        }
                        case 26: 
                    }
                    String s3 = input.readStringRequireUtf8();
                    if ((mutable_bitField0_ & 4) != 4) {
                        this.additionalInstallSource_ = new LazyStringArrayList();
                        mutable_bitField0_ |= 4;
                    }
                    this.additionalInstallSource_.add(s3);
                }
            }
            catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(this);
            }
            catch (IOException e3) {
                throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
            }
            finally {
                if ((mutable_bitField0_ & 4) == 4) {
                    this.additionalInstallSource_ = this.additionalInstallSource_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_InstallerCheck_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_InstallerCheck_fieldAccessorTable.ensureFieldAccessorsInitialized(InstallerCheck.class, Builder.class);
        }

        @Override
        public boolean getEnabled() {
            return this.enabled_;
        }

        @Override
        public boolean hasPolicy() {
            return this.policy_ != null;
        }

        @Override
        public Policy getPolicy() {
            return this.policy_ == null ? Policy.getDefaultInstance() : this.policy_;
        }

        @Override
        public PolicyOrBuilder getPolicyOrBuilder() {
            return this.getPolicy();
        }

        public ProtocolStringList getAdditionalInstallSourceList() {
            return this.additionalInstallSource_;
        }

        @Override
        public int getAdditionalInstallSourceCount() {
            return this.additionalInstallSource_.size();
        }

        @Override
        public String getAdditionalInstallSource(int index) {
            return (String)this.additionalInstallSource_.get(index);
        }

        @Override
        public ByteString getAdditionalInstallSourceBytes(int index) {
            return this.additionalInstallSource_.getByteString(index);
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
            if (this.policy_ != null) {
                output.writeMessage(2, this.getPolicy());
            }
            for (int i2 = 0; i2 < this.additionalInstallSource_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 3, this.additionalInstallSource_.getRaw(i2));
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
            if (this.policy_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getPolicy());
            }
            int dataSize = 0;
            for (int i2 = 0; i2 < this.additionalInstallSource_.size(); ++i2) {
                dataSize += InstallerCheck.computeStringSizeNoTag(this.additionalInstallSource_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getAdditionalInstallSourceList().size();
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof InstallerCheck)) {
                return super.equals(obj);
            }
            InstallerCheck other = (InstallerCheck)obj;
            boolean result = true;
            result = result && this.getEnabled() == other.getEnabled();
            boolean bl = result = result && this.hasPolicy() == other.hasPolicy();
            if (this.hasPolicy()) {
                result = result && this.getPolicy().equals(other.getPolicy());
            }
            result = result && this.getAdditionalInstallSourceList().equals(other.getAdditionalInstallSourceList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + InstallerCheck.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getEnabled());
            if (this.hasPolicy()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getPolicy().hashCode();
            }
            if (this.getAdditionalInstallSourceCount() > 0) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getAdditionalInstallSourceList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static InstallerCheck parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static InstallerCheck parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static InstallerCheck parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static InstallerCheck parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static InstallerCheck parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static InstallerCheck parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static InstallerCheck parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static InstallerCheck parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static InstallerCheck parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static InstallerCheck parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static InstallerCheck parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static InstallerCheck parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return InstallerCheck.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(InstallerCheck prototype) {
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

        public static InstallerCheck getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<InstallerCheck> parser() {
            return PARSER;
        }

        public Parser<InstallerCheck> getParserForType() {
            return PARSER;
        }

        @Override
        public InstallerCheck getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements InstallerCheckOrBuilder {
            private int bitField0_;
            private boolean enabled_;
            private Policy policy_ = null;
            private SingleFieldBuilderV3<Policy, Policy.Builder, PolicyOrBuilder> policyBuilder_;
            private LazyStringList additionalInstallSource_ = LazyStringArrayList.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_InstallerCheck_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_InstallerCheck_fieldAccessorTable.ensureFieldAccessorsInitialized(InstallerCheck.class, Builder.class);
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
                if (this.policyBuilder_ == null) {
                    this.policy_ = null;
                } else {
                    this.policy_ = null;
                    this.policyBuilder_ = null;
                }
                this.additionalInstallSource_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_InstallerCheck_descriptor;
            }

            @Override
            public InstallerCheck getDefaultInstanceForType() {
                return InstallerCheck.getDefaultInstance();
            }

            @Override
            public InstallerCheck build() {
                InstallerCheck result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public InstallerCheck buildPartial() {
                InstallerCheck result = new InstallerCheck(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                result.enabled_ = this.enabled_;
                if (this.policyBuilder_ == null) {
                    result.policy_ = this.policy_;
                } else {
                    result.policy_ = this.policyBuilder_.build();
                }
                if ((this.bitField0_ & 4) == 4) {
                    this.additionalInstallSource_ = this.additionalInstallSource_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                result.additionalInstallSource_ = this.additionalInstallSource_;
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
                if (other instanceof InstallerCheck) {
                    return this.mergeFrom((InstallerCheck)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(InstallerCheck other) {
                if (other == InstallerCheck.getDefaultInstance()) {
                    return this;
                }
                if (other.getEnabled()) {
                    this.setEnabled(other.getEnabled());
                }
                if (other.hasPolicy()) {
                    this.mergePolicy(other.getPolicy());
                }
                if (!other.additionalInstallSource_.isEmpty()) {
                    if (this.additionalInstallSource_.isEmpty()) {
                        this.additionalInstallSource_ = other.additionalInstallSource_;
                        this.bitField0_ &= 0xFFFFFFFB;
                    } else {
                        this.ensureAdditionalInstallSourceIsMutable();
                        this.additionalInstallSource_.addAll(other.additionalInstallSource_);
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
                InstallerCheck parsedMessage = null;
                try {
                    parsedMessage = (InstallerCheck)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (InstallerCheck)e2.getUnfinishedMessage();
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
            public boolean hasPolicy() {
                return this.policyBuilder_ != null || this.policy_ != null;
            }

            @Override
            public Policy getPolicy() {
                if (this.policyBuilder_ == null) {
                    return this.policy_ == null ? Policy.getDefaultInstance() : this.policy_;
                }
                return this.policyBuilder_.getMessage();
            }

            public Builder setPolicy(Policy value) {
                if (this.policyBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.policy_ = value;
                    this.onChanged();
                } else {
                    this.policyBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setPolicy(Policy.Builder builderForValue) {
                if (this.policyBuilder_ == null) {
                    this.policy_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.policyBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergePolicy(Policy value) {
                if (this.policyBuilder_ == null) {
                    this.policy_ = this.policy_ != null ? Policy.newBuilder(this.policy_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.policyBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearPolicy() {
                if (this.policyBuilder_ == null) {
                    this.policy_ = null;
                    this.onChanged();
                } else {
                    this.policy_ = null;
                    this.policyBuilder_ = null;
                }
                return this;
            }

            public Policy.Builder getPolicyBuilder() {
                this.onChanged();
                return this.getPolicyFieldBuilder().getBuilder();
            }

            @Override
            public PolicyOrBuilder getPolicyOrBuilder() {
                if (this.policyBuilder_ != null) {
                    return this.policyBuilder_.getMessageOrBuilder();
                }
                return this.policy_ == null ? Policy.getDefaultInstance() : this.policy_;
            }

            private SingleFieldBuilderV3<Policy, Policy.Builder, PolicyOrBuilder> getPolicyFieldBuilder() {
                if (this.policyBuilder_ == null) {
                    this.policyBuilder_ = new SingleFieldBuilderV3(this.getPolicy(), this.getParentForChildren(), this.isClean());
                    this.policy_ = null;
                }
                return this.policyBuilder_;
            }

            private void ensureAdditionalInstallSourceIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.additionalInstallSource_ = new LazyStringArrayList(this.additionalInstallSource_);
                    this.bitField0_ |= 4;
                }
            }

            public ProtocolStringList getAdditionalInstallSourceList() {
                return this.additionalInstallSource_.getUnmodifiableView();
            }

            @Override
            public int getAdditionalInstallSourceCount() {
                return this.additionalInstallSource_.size();
            }

            @Override
            public String getAdditionalInstallSource(int index) {
                return (String)this.additionalInstallSource_.get(index);
            }

            @Override
            public ByteString getAdditionalInstallSourceBytes(int index) {
                return this.additionalInstallSource_.getByteString(index);
            }

            public Builder setAdditionalInstallSource(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAdditionalInstallSourceIsMutable();
                this.additionalInstallSource_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addAdditionalInstallSource(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAdditionalInstallSourceIsMutable();
                this.additionalInstallSource_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllAdditionalInstallSource(Iterable<String> values2) {
                this.ensureAdditionalInstallSourceIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.additionalInstallSource_);
                this.onChanged();
                return this;
            }

            public Builder clearAdditionalInstallSource() {
                this.additionalInstallSource_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFB;
                this.onChanged();
                return this;
            }

            public Builder addAdditionalInstallSourceBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                InstallerCheck.checkByteStringIsUtf8(value);
                this.ensureAdditionalInstallSourceIsMutable();
                this.additionalInstallSource_.add(value);
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

    public static interface InstallerCheckOrBuilder
    extends MessageOrBuilder {
        public boolean getEnabled();

        public boolean hasPolicy();

        public Policy getPolicy();

        public PolicyOrBuilder getPolicyOrBuilder();

        public List<String> getAdditionalInstallSourceList();

        public int getAdditionalInstallSourceCount();

        public String getAdditionalInstallSource(int var1);

        public ByteString getAdditionalInstallSourceBytes(int var1);
    }

    public static final class LicenseCheck
    extends GeneratedMessageV3
    implements LicenseCheckOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ENABLED_FIELD_NUMBER = 1;
        private boolean enabled_;
        public static final int ONLINE_ONLY_FIELD_NUMBER = 2;
        private boolean onlineOnly_;
        public static final int POLICY_FIELD_NUMBER = 3;
        private Policy policy_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final LicenseCheck DEFAULT_INSTANCE = new LicenseCheck();
        private static final Parser<LicenseCheck> PARSER = new AbstractParser<LicenseCheck>(){

            @Override
            public LicenseCheck parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new LicenseCheck(input, extensionRegistry);
            }
        };

        private LicenseCheck(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private LicenseCheck() {
            this.enabled_ = false;
            this.onlineOnly_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private LicenseCheck(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.enabled_ = input.readBool();
                            continue block12;
                        }
                        case 16: {
                            this.onlineOnly_ = input.readBool();
                            continue block12;
                        }
                        case 26: 
                    }
                    Policy.Builder subBuilder = null;
                    if (this.policy_ != null) {
                        subBuilder = this.policy_.toBuilder();
                    }
                    this.policy_ = input.readMessage(Policy.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    subBuilder.mergeFrom(this.policy_);
                    this.policy_ = subBuilder.buildPartial();
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
            return internal_static_android_bundle_LicenseCheck_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_LicenseCheck_fieldAccessorTable.ensureFieldAccessorsInitialized(LicenseCheck.class, Builder.class);
        }

        @Override
        public boolean getEnabled() {
            return this.enabled_;
        }

        @Override
        public boolean getOnlineOnly() {
            return this.onlineOnly_;
        }

        @Override
        public boolean hasPolicy() {
            return this.policy_ != null;
        }

        @Override
        public Policy getPolicy() {
            return this.policy_ == null ? Policy.getDefaultInstance() : this.policy_;
        }

        @Override
        public PolicyOrBuilder getPolicyOrBuilder() {
            return this.getPolicy();
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
            if (this.onlineOnly_) {
                output.writeBool(2, this.onlineOnly_);
            }
            if (this.policy_ != null) {
                output.writeMessage(3, this.getPolicy());
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
            if (this.onlineOnly_) {
                size += CodedOutputStream.computeBoolSize(2, this.onlineOnly_);
            }
            if (this.policy_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getPolicy());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LicenseCheck)) {
                return super.equals(obj);
            }
            LicenseCheck other = (LicenseCheck)obj;
            boolean result = true;
            result = result && this.getEnabled() == other.getEnabled();
            result = result && this.getOnlineOnly() == other.getOnlineOnly();
            boolean bl = result = result && this.hasPolicy() == other.hasPolicy();
            if (this.hasPolicy()) {
                result = result && this.getPolicy().equals(other.getPolicy());
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
            hash = 19 * hash + LicenseCheck.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getEnabled());
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashBoolean(this.getOnlineOnly());
            if (this.hasPolicy()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getPolicy().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static LicenseCheck parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LicenseCheck parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LicenseCheck parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LicenseCheck parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LicenseCheck parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LicenseCheck parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LicenseCheck parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static LicenseCheck parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static LicenseCheck parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static LicenseCheck parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static LicenseCheck parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static LicenseCheck parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return LicenseCheck.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(LicenseCheck prototype) {
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

        public static LicenseCheck getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LicenseCheck> parser() {
            return PARSER;
        }

        public Parser<LicenseCheck> getParserForType() {
            return PARSER;
        }

        @Override
        public LicenseCheck getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements LicenseCheckOrBuilder {
            private boolean enabled_;
            private boolean onlineOnly_;
            private Policy policy_ = null;
            private SingleFieldBuilderV3<Policy, Policy.Builder, PolicyOrBuilder> policyBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_LicenseCheck_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_LicenseCheck_fieldAccessorTable.ensureFieldAccessorsInitialized(LicenseCheck.class, Builder.class);
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
                this.onlineOnly_ = false;
                if (this.policyBuilder_ == null) {
                    this.policy_ = null;
                } else {
                    this.policy_ = null;
                    this.policyBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_LicenseCheck_descriptor;
            }

            @Override
            public LicenseCheck getDefaultInstanceForType() {
                return LicenseCheck.getDefaultInstance();
            }

            @Override
            public LicenseCheck build() {
                LicenseCheck result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public LicenseCheck buildPartial() {
                LicenseCheck result = new LicenseCheck(this);
                result.enabled_ = this.enabled_;
                result.onlineOnly_ = this.onlineOnly_;
                if (this.policyBuilder_ == null) {
                    result.policy_ = this.policy_;
                } else {
                    result.policy_ = this.policyBuilder_.build();
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
                if (other instanceof LicenseCheck) {
                    return this.mergeFrom((LicenseCheck)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(LicenseCheck other) {
                if (other == LicenseCheck.getDefaultInstance()) {
                    return this;
                }
                if (other.getEnabled()) {
                    this.setEnabled(other.getEnabled());
                }
                if (other.getOnlineOnly()) {
                    this.setOnlineOnly(other.getOnlineOnly());
                }
                if (other.hasPolicy()) {
                    this.mergePolicy(other.getPolicy());
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
                LicenseCheck parsedMessage = null;
                try {
                    parsedMessage = (LicenseCheck)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (LicenseCheck)e2.getUnfinishedMessage();
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
            public boolean getOnlineOnly() {
                return this.onlineOnly_;
            }

            public Builder setOnlineOnly(boolean value) {
                this.onlineOnly_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearOnlineOnly() {
                this.onlineOnly_ = false;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPolicy() {
                return this.policyBuilder_ != null || this.policy_ != null;
            }

            @Override
            public Policy getPolicy() {
                if (this.policyBuilder_ == null) {
                    return this.policy_ == null ? Policy.getDefaultInstance() : this.policy_;
                }
                return this.policyBuilder_.getMessage();
            }

            public Builder setPolicy(Policy value) {
                if (this.policyBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.policy_ = value;
                    this.onChanged();
                } else {
                    this.policyBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setPolicy(Policy.Builder builderForValue) {
                if (this.policyBuilder_ == null) {
                    this.policy_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.policyBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergePolicy(Policy value) {
                if (this.policyBuilder_ == null) {
                    this.policy_ = this.policy_ != null ? Policy.newBuilder(this.policy_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.policyBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearPolicy() {
                if (this.policyBuilder_ == null) {
                    this.policy_ = null;
                    this.onChanged();
                } else {
                    this.policy_ = null;
                    this.policyBuilder_ = null;
                }
                return this;
            }

            public Policy.Builder getPolicyBuilder() {
                this.onChanged();
                return this.getPolicyFieldBuilder().getBuilder();
            }

            @Override
            public PolicyOrBuilder getPolicyOrBuilder() {
                if (this.policyBuilder_ != null) {
                    return this.policyBuilder_.getMessageOrBuilder();
                }
                return this.policy_ == null ? Policy.getDefaultInstance() : this.policy_;
            }

            private SingleFieldBuilderV3<Policy, Policy.Builder, PolicyOrBuilder> getPolicyFieldBuilder() {
                if (this.policyBuilder_ == null) {
                    this.policyBuilder_ = new SingleFieldBuilderV3(this.getPolicy(), this.getParentForChildren(), this.isClean());
                    this.policy_ = null;
                }
                return this.policyBuilder_;
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

    public static interface LicenseCheckOrBuilder
    extends MessageOrBuilder {
        public boolean getEnabled();

        public boolean getOnlineOnly();

        public boolean hasPolicy();

        public Policy getPolicy();

        public PolicyOrBuilder getPolicyOrBuilder();
    }

    public static final class AppIntegrityConfig
    extends GeneratedMessageV3
    implements AppIntegrityConfigOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ENABLED_FIELD_NUMBER = 1;
        private boolean enabled_;
        public static final int LICENSE_CHECK_FIELD_NUMBER = 2;
        private LicenseCheck licenseCheck_;
        public static final int INSTALLER_CHECK_FIELD_NUMBER = 3;
        private InstallerCheck installerCheck_;
        public static final int DEBUGGER_CHECK_FIELD_NUMBER = 4;
        private DebuggerCheck debuggerCheck_;
        public static final int EMULATOR_CHECK_FIELD_NUMBER = 5;
        private EmulatorCheck emulatorCheck_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final AppIntegrityConfig DEFAULT_INSTANCE = new AppIntegrityConfig();
        private static final Parser<AppIntegrityConfig> PARSER = new AbstractParser<AppIntegrityConfig>(){

            @Override
            public AppIntegrityConfig parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AppIntegrityConfig(input, extensionRegistry);
            }
        };

        private AppIntegrityConfig(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private AppIntegrityConfig() {
            this.enabled_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AppIntegrityConfig(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 8: {
                            this.enabled_ = input.readBool();
                            continue block14;
                        }
                        case 18: {
                            subBuilder = null;
                            if (this.licenseCheck_ != null) {
                                subBuilder = this.licenseCheck_.toBuilder();
                            }
                            this.licenseCheck_ = input.readMessage(LicenseCheck.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((LicenseCheck.Builder)subBuilder).mergeFrom(this.licenseCheck_);
                            this.licenseCheck_ = ((LicenseCheck.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 26: {
                            subBuilder = null;
                            if (this.installerCheck_ != null) {
                                subBuilder = this.installerCheck_.toBuilder();
                            }
                            this.installerCheck_ = input.readMessage(InstallerCheck.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((InstallerCheck.Builder)subBuilder).mergeFrom(this.installerCheck_);
                            this.installerCheck_ = ((InstallerCheck.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 34: {
                            subBuilder = null;
                            if (this.debuggerCheck_ != null) {
                                subBuilder = this.debuggerCheck_.toBuilder();
                            }
                            this.debuggerCheck_ = input.readMessage(DebuggerCheck.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((DebuggerCheck.Builder)subBuilder).mergeFrom(this.debuggerCheck_);
                            this.debuggerCheck_ = ((DebuggerCheck.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 42: 
                    }
                    subBuilder = null;
                    if (this.emulatorCheck_ != null) {
                        subBuilder = this.emulatorCheck_.toBuilder();
                    }
                    this.emulatorCheck_ = input.readMessage(EmulatorCheck.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    ((EmulatorCheck.Builder)subBuilder).mergeFrom(this.emulatorCheck_);
                    this.emulatorCheck_ = ((EmulatorCheck.Builder)subBuilder).buildPartial();
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
            return internal_static_android_bundle_AppIntegrityConfig_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_AppIntegrityConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(AppIntegrityConfig.class, Builder.class);
        }

        @Override
        public boolean getEnabled() {
            return this.enabled_;
        }

        @Override
        public boolean hasLicenseCheck() {
            return this.licenseCheck_ != null;
        }

        @Override
        public LicenseCheck getLicenseCheck() {
            return this.licenseCheck_ == null ? LicenseCheck.getDefaultInstance() : this.licenseCheck_;
        }

        @Override
        public LicenseCheckOrBuilder getLicenseCheckOrBuilder() {
            return this.getLicenseCheck();
        }

        @Override
        public boolean hasInstallerCheck() {
            return this.installerCheck_ != null;
        }

        @Override
        public InstallerCheck getInstallerCheck() {
            return this.installerCheck_ == null ? InstallerCheck.getDefaultInstance() : this.installerCheck_;
        }

        @Override
        public InstallerCheckOrBuilder getInstallerCheckOrBuilder() {
            return this.getInstallerCheck();
        }

        @Override
        public boolean hasDebuggerCheck() {
            return this.debuggerCheck_ != null;
        }

        @Override
        public DebuggerCheck getDebuggerCheck() {
            return this.debuggerCheck_ == null ? DebuggerCheck.getDefaultInstance() : this.debuggerCheck_;
        }

        @Override
        public DebuggerCheckOrBuilder getDebuggerCheckOrBuilder() {
            return this.getDebuggerCheck();
        }

        @Override
        public boolean hasEmulatorCheck() {
            return this.emulatorCheck_ != null;
        }

        @Override
        public EmulatorCheck getEmulatorCheck() {
            return this.emulatorCheck_ == null ? EmulatorCheck.getDefaultInstance() : this.emulatorCheck_;
        }

        @Override
        public EmulatorCheckOrBuilder getEmulatorCheckOrBuilder() {
            return this.getEmulatorCheck();
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
            if (this.licenseCheck_ != null) {
                output.writeMessage(2, this.getLicenseCheck());
            }
            if (this.installerCheck_ != null) {
                output.writeMessage(3, this.getInstallerCheck());
            }
            if (this.debuggerCheck_ != null) {
                output.writeMessage(4, this.getDebuggerCheck());
            }
            if (this.emulatorCheck_ != null) {
                output.writeMessage(5, this.getEmulatorCheck());
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
            if (this.licenseCheck_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getLicenseCheck());
            }
            if (this.installerCheck_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getInstallerCheck());
            }
            if (this.debuggerCheck_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getDebuggerCheck());
            }
            if (this.emulatorCheck_ != null) {
                size += CodedOutputStream.computeMessageSize(5, this.getEmulatorCheck());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AppIntegrityConfig)) {
                return super.equals(obj);
            }
            AppIntegrityConfig other = (AppIntegrityConfig)obj;
            boolean result = true;
            result = result && this.getEnabled() == other.getEnabled();
            boolean bl = result = result && this.hasLicenseCheck() == other.hasLicenseCheck();
            if (this.hasLicenseCheck()) {
                result = result && this.getLicenseCheck().equals(other.getLicenseCheck());
            }
            boolean bl2 = result = result && this.hasInstallerCheck() == other.hasInstallerCheck();
            if (this.hasInstallerCheck()) {
                result = result && this.getInstallerCheck().equals(other.getInstallerCheck());
            }
            boolean bl3 = result = result && this.hasDebuggerCheck() == other.hasDebuggerCheck();
            if (this.hasDebuggerCheck()) {
                result = result && this.getDebuggerCheck().equals(other.getDebuggerCheck());
            }
            boolean bl4 = result = result && this.hasEmulatorCheck() == other.hasEmulatorCheck();
            if (this.hasEmulatorCheck()) {
                result = result && this.getEmulatorCheck().equals(other.getEmulatorCheck());
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
            hash = 19 * hash + AppIntegrityConfig.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + Internal.hashBoolean(this.getEnabled());
            if (this.hasLicenseCheck()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getLicenseCheck().hashCode();
            }
            if (this.hasInstallerCheck()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getInstallerCheck().hashCode();
            }
            if (this.hasDebuggerCheck()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getDebuggerCheck().hashCode();
            }
            if (this.hasEmulatorCheck()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getEmulatorCheck().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static AppIntegrityConfig parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AppIntegrityConfig parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AppIntegrityConfig parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AppIntegrityConfig parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AppIntegrityConfig parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AppIntegrityConfig parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AppIntegrityConfig parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AppIntegrityConfig parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AppIntegrityConfig parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AppIntegrityConfig parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AppIntegrityConfig parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AppIntegrityConfig parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return AppIntegrityConfig.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AppIntegrityConfig prototype) {
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

        public static AppIntegrityConfig getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AppIntegrityConfig> parser() {
            return PARSER;
        }

        public Parser<AppIntegrityConfig> getParserForType() {
            return PARSER;
        }

        @Override
        public AppIntegrityConfig getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AppIntegrityConfigOrBuilder {
            private boolean enabled_;
            private LicenseCheck licenseCheck_ = null;
            private SingleFieldBuilderV3<LicenseCheck, LicenseCheck.Builder, LicenseCheckOrBuilder> licenseCheckBuilder_;
            private InstallerCheck installerCheck_ = null;
            private SingleFieldBuilderV3<InstallerCheck, InstallerCheck.Builder, InstallerCheckOrBuilder> installerCheckBuilder_;
            private DebuggerCheck debuggerCheck_ = null;
            private SingleFieldBuilderV3<DebuggerCheck, DebuggerCheck.Builder, DebuggerCheckOrBuilder> debuggerCheckBuilder_;
            private EmulatorCheck emulatorCheck_ = null;
            private SingleFieldBuilderV3<EmulatorCheck, EmulatorCheck.Builder, EmulatorCheckOrBuilder> emulatorCheckBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_AppIntegrityConfig_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_AppIntegrityConfig_fieldAccessorTable.ensureFieldAccessorsInitialized(AppIntegrityConfig.class, Builder.class);
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
                if (this.licenseCheckBuilder_ == null) {
                    this.licenseCheck_ = null;
                } else {
                    this.licenseCheck_ = null;
                    this.licenseCheckBuilder_ = null;
                }
                if (this.installerCheckBuilder_ == null) {
                    this.installerCheck_ = null;
                } else {
                    this.installerCheck_ = null;
                    this.installerCheckBuilder_ = null;
                }
                if (this.debuggerCheckBuilder_ == null) {
                    this.debuggerCheck_ = null;
                } else {
                    this.debuggerCheck_ = null;
                    this.debuggerCheckBuilder_ = null;
                }
                if (this.emulatorCheckBuilder_ == null) {
                    this.emulatorCheck_ = null;
                } else {
                    this.emulatorCheck_ = null;
                    this.emulatorCheckBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_AppIntegrityConfig_descriptor;
            }

            @Override
            public AppIntegrityConfig getDefaultInstanceForType() {
                return AppIntegrityConfig.getDefaultInstance();
            }

            @Override
            public AppIntegrityConfig build() {
                AppIntegrityConfig result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public AppIntegrityConfig buildPartial() {
                AppIntegrityConfig result = new AppIntegrityConfig(this);
                result.enabled_ = this.enabled_;
                if (this.licenseCheckBuilder_ == null) {
                    result.licenseCheck_ = this.licenseCheck_;
                } else {
                    result.licenseCheck_ = this.licenseCheckBuilder_.build();
                }
                if (this.installerCheckBuilder_ == null) {
                    result.installerCheck_ = this.installerCheck_;
                } else {
                    result.installerCheck_ = this.installerCheckBuilder_.build();
                }
                if (this.debuggerCheckBuilder_ == null) {
                    result.debuggerCheck_ = this.debuggerCheck_;
                } else {
                    result.debuggerCheck_ = this.debuggerCheckBuilder_.build();
                }
                if (this.emulatorCheckBuilder_ == null) {
                    result.emulatorCheck_ = this.emulatorCheck_;
                } else {
                    result.emulatorCheck_ = this.emulatorCheckBuilder_.build();
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
                if (other instanceof AppIntegrityConfig) {
                    return this.mergeFrom((AppIntegrityConfig)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AppIntegrityConfig other) {
                if (other == AppIntegrityConfig.getDefaultInstance()) {
                    return this;
                }
                if (other.getEnabled()) {
                    this.setEnabled(other.getEnabled());
                }
                if (other.hasLicenseCheck()) {
                    this.mergeLicenseCheck(other.getLicenseCheck());
                }
                if (other.hasInstallerCheck()) {
                    this.mergeInstallerCheck(other.getInstallerCheck());
                }
                if (other.hasDebuggerCheck()) {
                    this.mergeDebuggerCheck(other.getDebuggerCheck());
                }
                if (other.hasEmulatorCheck()) {
                    this.mergeEmulatorCheck(other.getEmulatorCheck());
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
                AppIntegrityConfig parsedMessage = null;
                try {
                    parsedMessage = (AppIntegrityConfig)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (AppIntegrityConfig)e2.getUnfinishedMessage();
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
            public boolean hasLicenseCheck() {
                return this.licenseCheckBuilder_ != null || this.licenseCheck_ != null;
            }

            @Override
            public LicenseCheck getLicenseCheck() {
                if (this.licenseCheckBuilder_ == null) {
                    return this.licenseCheck_ == null ? LicenseCheck.getDefaultInstance() : this.licenseCheck_;
                }
                return this.licenseCheckBuilder_.getMessage();
            }

            public Builder setLicenseCheck(LicenseCheck value) {
                if (this.licenseCheckBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.licenseCheck_ = value;
                    this.onChanged();
                } else {
                    this.licenseCheckBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setLicenseCheck(LicenseCheck.Builder builderForValue) {
                if (this.licenseCheckBuilder_ == null) {
                    this.licenseCheck_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.licenseCheckBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeLicenseCheck(LicenseCheck value) {
                if (this.licenseCheckBuilder_ == null) {
                    this.licenseCheck_ = this.licenseCheck_ != null ? LicenseCheck.newBuilder(this.licenseCheck_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.licenseCheckBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearLicenseCheck() {
                if (this.licenseCheckBuilder_ == null) {
                    this.licenseCheck_ = null;
                    this.onChanged();
                } else {
                    this.licenseCheck_ = null;
                    this.licenseCheckBuilder_ = null;
                }
                return this;
            }

            public LicenseCheck.Builder getLicenseCheckBuilder() {
                this.onChanged();
                return this.getLicenseCheckFieldBuilder().getBuilder();
            }

            @Override
            public LicenseCheckOrBuilder getLicenseCheckOrBuilder() {
                if (this.licenseCheckBuilder_ != null) {
                    return this.licenseCheckBuilder_.getMessageOrBuilder();
                }
                return this.licenseCheck_ == null ? LicenseCheck.getDefaultInstance() : this.licenseCheck_;
            }

            private SingleFieldBuilderV3<LicenseCheck, LicenseCheck.Builder, LicenseCheckOrBuilder> getLicenseCheckFieldBuilder() {
                if (this.licenseCheckBuilder_ == null) {
                    this.licenseCheckBuilder_ = new SingleFieldBuilderV3(this.getLicenseCheck(), this.getParentForChildren(), this.isClean());
                    this.licenseCheck_ = null;
                }
                return this.licenseCheckBuilder_;
            }

            @Override
            public boolean hasInstallerCheck() {
                return this.installerCheckBuilder_ != null || this.installerCheck_ != null;
            }

            @Override
            public InstallerCheck getInstallerCheck() {
                if (this.installerCheckBuilder_ == null) {
                    return this.installerCheck_ == null ? InstallerCheck.getDefaultInstance() : this.installerCheck_;
                }
                return this.installerCheckBuilder_.getMessage();
            }

            public Builder setInstallerCheck(InstallerCheck value) {
                if (this.installerCheckBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.installerCheck_ = value;
                    this.onChanged();
                } else {
                    this.installerCheckBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setInstallerCheck(InstallerCheck.Builder builderForValue) {
                if (this.installerCheckBuilder_ == null) {
                    this.installerCheck_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.installerCheckBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeInstallerCheck(InstallerCheck value) {
                if (this.installerCheckBuilder_ == null) {
                    this.installerCheck_ = this.installerCheck_ != null ? InstallerCheck.newBuilder(this.installerCheck_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.installerCheckBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearInstallerCheck() {
                if (this.installerCheckBuilder_ == null) {
                    this.installerCheck_ = null;
                    this.onChanged();
                } else {
                    this.installerCheck_ = null;
                    this.installerCheckBuilder_ = null;
                }
                return this;
            }

            public InstallerCheck.Builder getInstallerCheckBuilder() {
                this.onChanged();
                return this.getInstallerCheckFieldBuilder().getBuilder();
            }

            @Override
            public InstallerCheckOrBuilder getInstallerCheckOrBuilder() {
                if (this.installerCheckBuilder_ != null) {
                    return this.installerCheckBuilder_.getMessageOrBuilder();
                }
                return this.installerCheck_ == null ? InstallerCheck.getDefaultInstance() : this.installerCheck_;
            }

            private SingleFieldBuilderV3<InstallerCheck, InstallerCheck.Builder, InstallerCheckOrBuilder> getInstallerCheckFieldBuilder() {
                if (this.installerCheckBuilder_ == null) {
                    this.installerCheckBuilder_ = new SingleFieldBuilderV3(this.getInstallerCheck(), this.getParentForChildren(), this.isClean());
                    this.installerCheck_ = null;
                }
                return this.installerCheckBuilder_;
            }

            @Override
            public boolean hasDebuggerCheck() {
                return this.debuggerCheckBuilder_ != null || this.debuggerCheck_ != null;
            }

            @Override
            public DebuggerCheck getDebuggerCheck() {
                if (this.debuggerCheckBuilder_ == null) {
                    return this.debuggerCheck_ == null ? DebuggerCheck.getDefaultInstance() : this.debuggerCheck_;
                }
                return this.debuggerCheckBuilder_.getMessage();
            }

            public Builder setDebuggerCheck(DebuggerCheck value) {
                if (this.debuggerCheckBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.debuggerCheck_ = value;
                    this.onChanged();
                } else {
                    this.debuggerCheckBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setDebuggerCheck(DebuggerCheck.Builder builderForValue) {
                if (this.debuggerCheckBuilder_ == null) {
                    this.debuggerCheck_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.debuggerCheckBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeDebuggerCheck(DebuggerCheck value) {
                if (this.debuggerCheckBuilder_ == null) {
                    this.debuggerCheck_ = this.debuggerCheck_ != null ? DebuggerCheck.newBuilder(this.debuggerCheck_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.debuggerCheckBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearDebuggerCheck() {
                if (this.debuggerCheckBuilder_ == null) {
                    this.debuggerCheck_ = null;
                    this.onChanged();
                } else {
                    this.debuggerCheck_ = null;
                    this.debuggerCheckBuilder_ = null;
                }
                return this;
            }

            public DebuggerCheck.Builder getDebuggerCheckBuilder() {
                this.onChanged();
                return this.getDebuggerCheckFieldBuilder().getBuilder();
            }

            @Override
            public DebuggerCheckOrBuilder getDebuggerCheckOrBuilder() {
                if (this.debuggerCheckBuilder_ != null) {
                    return this.debuggerCheckBuilder_.getMessageOrBuilder();
                }
                return this.debuggerCheck_ == null ? DebuggerCheck.getDefaultInstance() : this.debuggerCheck_;
            }

            private SingleFieldBuilderV3<DebuggerCheck, DebuggerCheck.Builder, DebuggerCheckOrBuilder> getDebuggerCheckFieldBuilder() {
                if (this.debuggerCheckBuilder_ == null) {
                    this.debuggerCheckBuilder_ = new SingleFieldBuilderV3(this.getDebuggerCheck(), this.getParentForChildren(), this.isClean());
                    this.debuggerCheck_ = null;
                }
                return this.debuggerCheckBuilder_;
            }

            @Override
            public boolean hasEmulatorCheck() {
                return this.emulatorCheckBuilder_ != null || this.emulatorCheck_ != null;
            }

            @Override
            public EmulatorCheck getEmulatorCheck() {
                if (this.emulatorCheckBuilder_ == null) {
                    return this.emulatorCheck_ == null ? EmulatorCheck.getDefaultInstance() : this.emulatorCheck_;
                }
                return this.emulatorCheckBuilder_.getMessage();
            }

            public Builder setEmulatorCheck(EmulatorCheck value) {
                if (this.emulatorCheckBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.emulatorCheck_ = value;
                    this.onChanged();
                } else {
                    this.emulatorCheckBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setEmulatorCheck(EmulatorCheck.Builder builderForValue) {
                if (this.emulatorCheckBuilder_ == null) {
                    this.emulatorCheck_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.emulatorCheckBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeEmulatorCheck(EmulatorCheck value) {
                if (this.emulatorCheckBuilder_ == null) {
                    this.emulatorCheck_ = this.emulatorCheck_ != null ? EmulatorCheck.newBuilder(this.emulatorCheck_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.emulatorCheckBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearEmulatorCheck() {
                if (this.emulatorCheckBuilder_ == null) {
                    this.emulatorCheck_ = null;
                    this.onChanged();
                } else {
                    this.emulatorCheck_ = null;
                    this.emulatorCheckBuilder_ = null;
                }
                return this;
            }

            public EmulatorCheck.Builder getEmulatorCheckBuilder() {
                this.onChanged();
                return this.getEmulatorCheckFieldBuilder().getBuilder();
            }

            @Override
            public EmulatorCheckOrBuilder getEmulatorCheckOrBuilder() {
                if (this.emulatorCheckBuilder_ != null) {
                    return this.emulatorCheckBuilder_.getMessageOrBuilder();
                }
                return this.emulatorCheck_ == null ? EmulatorCheck.getDefaultInstance() : this.emulatorCheck_;
            }

            private SingleFieldBuilderV3<EmulatorCheck, EmulatorCheck.Builder, EmulatorCheckOrBuilder> getEmulatorCheckFieldBuilder() {
                if (this.emulatorCheckBuilder_ == null) {
                    this.emulatorCheckBuilder_ = new SingleFieldBuilderV3(this.getEmulatorCheck(), this.getParentForChildren(), this.isClean());
                    this.emulatorCheck_ = null;
                }
                return this.emulatorCheckBuilder_;
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

    public static interface AppIntegrityConfigOrBuilder
    extends MessageOrBuilder {
        public boolean getEnabled();

        public boolean hasLicenseCheck();

        public LicenseCheck getLicenseCheck();

        public LicenseCheckOrBuilder getLicenseCheckOrBuilder();

        public boolean hasInstallerCheck();

        public InstallerCheck getInstallerCheck();

        public InstallerCheckOrBuilder getInstallerCheckOrBuilder();

        public boolean hasDebuggerCheck();

        public DebuggerCheck getDebuggerCheck();

        public DebuggerCheckOrBuilder getDebuggerCheckOrBuilder();

        public boolean hasEmulatorCheck();

        public EmulatorCheck getEmulatorCheck();

        public EmulatorCheckOrBuilder getEmulatorCheckOrBuilder();
    }
}

