/*
 * Decompiled with CFR 0.152.
 */
package com.android.aapt;

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
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ConfigurationOuterClass {
    private static final Descriptors.Descriptor internal_static_aapt_pb_Configuration_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_aapt_pb_Configuration_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private ConfigurationOuterClass() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        ConfigurationOuterClass.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0013Configuration.proto\u0012\u0007aapt.pb\"\u00d9\u0014\n\rConfiguration\u0012\u000b\n\u0003mcc\u0018\u0001 \u0001(\r\u0012\u000b\n\u0003mnc\u0018\u0002 \u0001(\r\u0012\u000e\n\u0006locale\u0018\u0003 \u0001(\t\u0012@\n\u0010layout_direction\u0018\u0004 \u0001(\u000e2&.aapt.pb.Configuration.LayoutDirection\u0012\u0014\n\fscreen_width\u0018\u0005 \u0001(\r\u0012\u0015\n\rscreen_height\u0018\u0006 \u0001(\r\u0012\u0017\n\u000fscreen_width_dp\u0018\u0007 \u0001(\r\u0012\u0018\n\u0010screen_height_dp\u0018\b \u0001(\r\u0012 \n\u0018smallest_screen_width_dp\u0018\t \u0001(\r\u0012C\n\u0012screen_layout_size\u0018\n \u0001(\u000e2'.aapt.pb.Configuration.ScreenLayoutSize\u0012C\n\u0012screen_layout_long\u0018\u000b \u0001(\u000e2'.aapt.pb.Configuration.ScreenLayoutLong\u00128\n\fscreen_round\u0018\f \u0001(\u000e2\".aapt.pb.Configuration.ScreenRound\u0012?\n\u0010wide_color_gamut\u0018\r \u0001(\u000e2%.aapt.pb.Configuration.WideColorGamut\u0012'\n\u0003hdr\u0018\u000e \u0001(\u000e2\u001a.aapt.pb.Configuration.Hdr\u00127\n\u000borientation\u0018\u000f \u0001(\u000e2\".aapt.pb.Configuration.Orientation\u00127\n\fui_mode_type\u0018\u0010 \u0001(\u000e2!.aapt.pb.Configuration.UiModeType\u00129\n\rui_mode_night\u0018\u0011 \u0001(\u000e2\".aapt.pb.Configuration.UiModeNight\u0012\u000f\n\u0007density\u0018\u0012 \u0001(\r\u00127\n\u000btouchscreen\u0018\u0013 \u0001(\u000e2\".aapt.pb.Configuration.Touchscreen\u00126\n\u000bkeys_hidden\u0018\u0014 \u0001(\u000e2!.aapt.pb.Configuration.KeysHidden\u00121\n\bkeyboard\u0018\u0015 \u0001(\u000e2\u001f.aapt.pb.Configuration.Keyboard\u00124\n\nnav_hidden\u0018\u0016 \u0001(\u000e2 .aapt.pb.Configuration.NavHidden\u00125\n\nnavigation\u0018\u0017 \u0001(\u000e2!.aapt.pb.Configuration.Navigation\u0012\u0013\n\u000bsdk_version\u0018\u0018 \u0001(\r\u0012\u000f\n\u0007product\u0018\u0019 \u0001(\t\"a\n\u000fLayoutDirection\u0012\u001a\n\u0016LAYOUT_DIRECTION_UNSET\u0010\u0000\u0012\u0018\n\u0014LAYOUT_DIRECTION_LTR\u0010\u0001\u0012\u0018\n\u0014LAYOUT_DIRECTION_RTL\u0010\u0002\"\u00aa\u0001\n\u0010ScreenLayoutSize\u0012\u001c\n\u0018SCREEN_LAYOUT_SIZE_UNSET\u0010\u0000\u0012\u001c\n\u0018SCREEN_LAYOUT_SIZE_SMALL\u0010\u0001\u0012\u001d\n\u0019SCREEN_LAYOUT_SIZE_NORMAL\u0010\u0002\u0012\u001c\n\u0018SCREEN_LAYOUT_SIZE_LARGE\u0010\u0003\u0012\u001d\n\u0019SCREEN_LAYOUT_SIZE_XLARGE\u0010\u0004\"m\n\u0010ScreenLayoutLong\u0012\u001c\n\u0018SCREEN_LAYOUT_LONG_UNSET\u0010\u0000\u0012\u001b\n\u0017SCREEN_LAYOUT_LONG_LONG\u0010\u0001\u0012\u001e\n\u001aSCREEN_LAYOUT_LONG_NOTLONG\u0010\u0002\"X\n\u000bScreenRound\u0012\u0016\n\u0012SCREEN_ROUND_UNSET\u0010\u0000\u0012\u0016\n\u0012SCREEN_ROUND_ROUND\u0010\u0001\u0012\u0019\n\u0015SCREEN_ROUND_NOTROUND\u0010\u0002\"h\n\u000eWideColorGamut\u0012\u001a\n\u0016WIDE_COLOR_GAMUT_UNSET\u0010\u0000\u0012\u001b\n\u0017WIDE_COLOR_GAMUT_WIDECG\u0010\u0001\u0012\u001d\n\u0019WIDE_COLOR_GAMUT_NOWIDECG\u0010\u0002\"3\n\u0003Hdr\u0012\r\n\tHDR_UNSET\u0010\u0000\u0012\u000e\n\nHDR_HIGHDR\u0010\u0001\u0012\r\n\tHDR_LOWDR\u0010\u0002\"h\n\u000bOrientation\u0012\u0015\n\u0011ORIENTATION_UNSET\u0010\u0000\u0012\u0014\n\u0010ORIENTATION_PORT\u0010\u0001\u0012\u0014\n\u0010ORIENTATION_LAND\u0010\u0002\u0012\u0016\n\u0012ORIENTATION_SQUARE\u0010\u0003\"\u00d7\u0001\n\nUiModeType\u0012\u0016\n\u0012UI_MODE_TYPE_UNSET\u0010\u0000\u0012\u0017\n\u0013UI_MODE_TYPE_NORMAL\u0010\u0001\u0012\u0015\n\u0011UI_MODE_TYPE_DESK\u0010\u0002\u0012\u0014\n\u0010UI_MODE_TYPE_CAR\u0010\u0003\u0012\u001b\n\u0017UI_MODE_TYPE_TELEVISION\u0010\u0004\u0012\u001a\n\u0016UI_MODE_TYPE_APPLIANCE\u0010\u0005\u0012\u0016\n\u0012UI_MODE_TYPE_WATCH\u0010\u0006\u0012\u001a\n\u0016UI_MODE_TYPE_VRHEADSET\u0010\u0007\"[\n\u000bUiModeNight\u0012\u0017\n\u0013UI_MODE_NIGHT_UNSET\u0010\u0000\u0012\u0017\n\u0013UI_MODE_NIGHT_NIGHT\u0010\u0001\u0012\u001a\n\u0016UI_MODE_NIGHT_NOTNIGHT\u0010\u0002\"m\n\u000bTouchscreen\u0012\u0015\n\u0011TOUCHSCREEN_UNSET\u0010\u0000\u0012\u0017\n\u0013TOUCHSCREEN_NOTOUCH\u0010\u0001\u0012\u0016\n\u0012TOUCHSCREEN_STYLUS\u0010\u0002\u0012\u0016\n\u0012TOUCHSCREEN_FINGER\u0010\u0003\"v\n\nKeysHidden\u0012\u0015\n\u0011KEYS_HIDDEN_UNSET\u0010\u0000\u0012\u001b\n\u0017KEYS_HIDDEN_KEYSEXPOSED\u0010\u0001\u0012\u001a\n\u0016KEYS_HIDDEN_KEYSHIDDEN\u0010\u0002\u0012\u0018\n\u0014KEYS_HIDDEN_KEYSSOFT\u0010\u0003\"`\n\bKeyboard\u0012\u0012\n\u000eKEYBOARD_UNSET\u0010\u0000\u0012\u0013\n\u000fKEYBOARD_NOKEYS\u0010\u0001\u0012\u0013\n\u000fKEYBOARD_QWERTY\u0010\u0002\u0012\u0016\n\u0012KEYBOARD_TWELVEKEY\u0010\u0003\"V\n\tNavHidden\u0012\u0014\n\u0010NAV_HIDDEN_UNSET\u0010\u0000\u0012\u0019\n\u0015NAV_HIDDEN_NAVEXPOSED\u0010\u0001\u0012\u0018\n\u0014NAV_HIDDEN_NAVHIDDEN\u0010\u0002\"}\n\nNavigation\u0012\u0014\n\u0010NAVIGATION_UNSET\u0010\u0000\u0012\u0014\n\u0010NAVIGATION_NONAV\u0010\u0001\u0012\u0013\n\u000fNAVIGATION_DPAD\u0010\u0002\u0012\u0018\n\u0014NAVIGATION_TRACKBALL\u0010\u0003\u0012\u0014\n\u0010NAVIGATION_WHEEL\u0010\u0004B\u0012\n\u0010com.android.aaptb\u0006proto3"};
        descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0]);
        internal_static_aapt_pb_Configuration_descriptor = ConfigurationOuterClass.getDescriptor().getMessageTypes().get(0);
        internal_static_aapt_pb_Configuration_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_aapt_pb_Configuration_descriptor, new String[]{"Mcc", "Mnc", "Locale", "LayoutDirection", "ScreenWidth", "ScreenHeight", "ScreenWidthDp", "ScreenHeightDp", "SmallestScreenWidthDp", "ScreenLayoutSize", "ScreenLayoutLong", "ScreenRound", "WideColorGamut", "Hdr", "Orientation", "UiModeType", "UiModeNight", "Density", "Touchscreen", "KeysHidden", "Keyboard", "NavHidden", "Navigation", "SdkVersion", "Product"});
    }

    public static final class Configuration
    extends GeneratedMessageV3
    implements ConfigurationOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MCC_FIELD_NUMBER = 1;
        private int mcc_;
        public static final int MNC_FIELD_NUMBER = 2;
        private int mnc_;
        public static final int LOCALE_FIELD_NUMBER = 3;
        private volatile Object locale_;
        public static final int LAYOUT_DIRECTION_FIELD_NUMBER = 4;
        private int layoutDirection_;
        public static final int SCREEN_WIDTH_FIELD_NUMBER = 5;
        private int screenWidth_;
        public static final int SCREEN_HEIGHT_FIELD_NUMBER = 6;
        private int screenHeight_;
        public static final int SCREEN_WIDTH_DP_FIELD_NUMBER = 7;
        private int screenWidthDp_;
        public static final int SCREEN_HEIGHT_DP_FIELD_NUMBER = 8;
        private int screenHeightDp_;
        public static final int SMALLEST_SCREEN_WIDTH_DP_FIELD_NUMBER = 9;
        private int smallestScreenWidthDp_;
        public static final int SCREEN_LAYOUT_SIZE_FIELD_NUMBER = 10;
        private int screenLayoutSize_;
        public static final int SCREEN_LAYOUT_LONG_FIELD_NUMBER = 11;
        private int screenLayoutLong_;
        public static final int SCREEN_ROUND_FIELD_NUMBER = 12;
        private int screenRound_;
        public static final int WIDE_COLOR_GAMUT_FIELD_NUMBER = 13;
        private int wideColorGamut_;
        public static final int HDR_FIELD_NUMBER = 14;
        private int hdr_;
        public static final int ORIENTATION_FIELD_NUMBER = 15;
        private int orientation_;
        public static final int UI_MODE_TYPE_FIELD_NUMBER = 16;
        private int uiModeType_;
        public static final int UI_MODE_NIGHT_FIELD_NUMBER = 17;
        private int uiModeNight_;
        public static final int DENSITY_FIELD_NUMBER = 18;
        private int density_;
        public static final int TOUCHSCREEN_FIELD_NUMBER = 19;
        private int touchscreen_;
        public static final int KEYS_HIDDEN_FIELD_NUMBER = 20;
        private int keysHidden_;
        public static final int KEYBOARD_FIELD_NUMBER = 21;
        private int keyboard_;
        public static final int NAV_HIDDEN_FIELD_NUMBER = 22;
        private int navHidden_;
        public static final int NAVIGATION_FIELD_NUMBER = 23;
        private int navigation_;
        public static final int SDK_VERSION_FIELD_NUMBER = 24;
        private int sdkVersion_;
        public static final int PRODUCT_FIELD_NUMBER = 25;
        private volatile Object product_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Configuration DEFAULT_INSTANCE = new Configuration();
        private static final Parser<Configuration> PARSER = new AbstractParser<Configuration>(){

            @Override
            public Configuration parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Configuration(input, extensionRegistry);
            }
        };

        private Configuration(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Configuration() {
            this.locale_ = "";
            this.layoutDirection_ = 0;
            this.screenLayoutSize_ = 0;
            this.screenLayoutLong_ = 0;
            this.screenRound_ = 0;
            this.wideColorGamut_ = 0;
            this.hdr_ = 0;
            this.orientation_ = 0;
            this.uiModeType_ = 0;
            this.uiModeNight_ = 0;
            this.touchscreen_ = 0;
            this.keysHidden_ = 0;
            this.keyboard_ = 0;
            this.navHidden_ = 0;
            this.navigation_ = 0;
            this.product_ = "";
        }

        @Override
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unused) {
            return new Configuration();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Configuration(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block34: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block34;
                        }
                        case 8: {
                            this.mcc_ = input.readUInt32();
                            continue block34;
                        }
                        case 16: {
                            this.mnc_ = input.readUInt32();
                            continue block34;
                        }
                        case 26: {
                            String s3 = input.readStringRequireUtf8();
                            this.locale_ = s3;
                            continue block34;
                        }
                        case 32: {
                            int rawValue;
                            this.layoutDirection_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 40: {
                            this.screenWidth_ = input.readUInt32();
                            continue block34;
                        }
                        case 48: {
                            this.screenHeight_ = input.readUInt32();
                            continue block34;
                        }
                        case 56: {
                            this.screenWidthDp_ = input.readUInt32();
                            continue block34;
                        }
                        case 64: {
                            this.screenHeightDp_ = input.readUInt32();
                            continue block34;
                        }
                        case 72: {
                            this.smallestScreenWidthDp_ = input.readUInt32();
                            continue block34;
                        }
                        case 80: {
                            int rawValue;
                            this.screenLayoutSize_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 88: {
                            int rawValue;
                            this.screenLayoutLong_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 96: {
                            int rawValue;
                            this.screenRound_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 104: {
                            int rawValue;
                            this.wideColorGamut_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 112: {
                            int rawValue;
                            this.hdr_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 120: {
                            int rawValue;
                            this.orientation_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 128: {
                            int rawValue;
                            this.uiModeType_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 136: {
                            int rawValue;
                            this.uiModeNight_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 144: {
                            this.density_ = input.readUInt32();
                            continue block34;
                        }
                        case 152: {
                            int rawValue;
                            this.touchscreen_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 160: {
                            int rawValue;
                            this.keysHidden_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 168: {
                            int rawValue;
                            this.keyboard_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 176: {
                            int rawValue;
                            this.navHidden_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 184: {
                            int rawValue;
                            this.navigation_ = rawValue = input.readEnum();
                            continue block34;
                        }
                        case 192: {
                            this.sdkVersion_ = input.readUInt32();
                            continue block34;
                        }
                        case 202: {
                            String s3 = input.readStringRequireUtf8();
                            this.product_ = s3;
                            continue block34;
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
            return internal_static_aapt_pb_Configuration_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_aapt_pb_Configuration_fieldAccessorTable.ensureFieldAccessorsInitialized(Configuration.class, Builder.class);
        }

        @Override
        public int getMcc() {
            return this.mcc_;
        }

        @Override
        public int getMnc() {
            return this.mnc_;
        }

        @Override
        public String getLocale() {
            Object ref = this.locale_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.locale_ = s3;
            return s3;
        }

        @Override
        public ByteString getLocaleBytes() {
            Object ref = this.locale_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.locale_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public int getLayoutDirectionValue() {
            return this.layoutDirection_;
        }

        @Override
        public LayoutDirection getLayoutDirection() {
            LayoutDirection result = LayoutDirection.valueOf(this.layoutDirection_);
            return result == null ? LayoutDirection.UNRECOGNIZED : result;
        }

        @Override
        public int getScreenWidth() {
            return this.screenWidth_;
        }

        @Override
        public int getScreenHeight() {
            return this.screenHeight_;
        }

        @Override
        public int getScreenWidthDp() {
            return this.screenWidthDp_;
        }

        @Override
        public int getScreenHeightDp() {
            return this.screenHeightDp_;
        }

        @Override
        public int getSmallestScreenWidthDp() {
            return this.smallestScreenWidthDp_;
        }

        @Override
        public int getScreenLayoutSizeValue() {
            return this.screenLayoutSize_;
        }

        @Override
        public ScreenLayoutSize getScreenLayoutSize() {
            ScreenLayoutSize result = ScreenLayoutSize.valueOf(this.screenLayoutSize_);
            return result == null ? ScreenLayoutSize.UNRECOGNIZED : result;
        }

        @Override
        public int getScreenLayoutLongValue() {
            return this.screenLayoutLong_;
        }

        @Override
        public ScreenLayoutLong getScreenLayoutLong() {
            ScreenLayoutLong result = ScreenLayoutLong.valueOf(this.screenLayoutLong_);
            return result == null ? ScreenLayoutLong.UNRECOGNIZED : result;
        }

        @Override
        public int getScreenRoundValue() {
            return this.screenRound_;
        }

        @Override
        public ScreenRound getScreenRound() {
            ScreenRound result = ScreenRound.valueOf(this.screenRound_);
            return result == null ? ScreenRound.UNRECOGNIZED : result;
        }

        @Override
        public int getWideColorGamutValue() {
            return this.wideColorGamut_;
        }

        @Override
        public WideColorGamut getWideColorGamut() {
            WideColorGamut result = WideColorGamut.valueOf(this.wideColorGamut_);
            return result == null ? WideColorGamut.UNRECOGNIZED : result;
        }

        @Override
        public int getHdrValue() {
            return this.hdr_;
        }

        @Override
        public Hdr getHdr() {
            Hdr result = Hdr.valueOf(this.hdr_);
            return result == null ? Hdr.UNRECOGNIZED : result;
        }

        @Override
        public int getOrientationValue() {
            return this.orientation_;
        }

        @Override
        public Orientation getOrientation() {
            Orientation result = Orientation.valueOf(this.orientation_);
            return result == null ? Orientation.UNRECOGNIZED : result;
        }

        @Override
        public int getUiModeTypeValue() {
            return this.uiModeType_;
        }

        @Override
        public UiModeType getUiModeType() {
            UiModeType result = UiModeType.valueOf(this.uiModeType_);
            return result == null ? UiModeType.UNRECOGNIZED : result;
        }

        @Override
        public int getUiModeNightValue() {
            return this.uiModeNight_;
        }

        @Override
        public UiModeNight getUiModeNight() {
            UiModeNight result = UiModeNight.valueOf(this.uiModeNight_);
            return result == null ? UiModeNight.UNRECOGNIZED : result;
        }

        @Override
        public int getDensity() {
            return this.density_;
        }

        @Override
        public int getTouchscreenValue() {
            return this.touchscreen_;
        }

        @Override
        public Touchscreen getTouchscreen() {
            Touchscreen result = Touchscreen.valueOf(this.touchscreen_);
            return result == null ? Touchscreen.UNRECOGNIZED : result;
        }

        @Override
        public int getKeysHiddenValue() {
            return this.keysHidden_;
        }

        @Override
        public KeysHidden getKeysHidden() {
            KeysHidden result = KeysHidden.valueOf(this.keysHidden_);
            return result == null ? KeysHidden.UNRECOGNIZED : result;
        }

        @Override
        public int getKeyboardValue() {
            return this.keyboard_;
        }

        @Override
        public Keyboard getKeyboard() {
            Keyboard result = Keyboard.valueOf(this.keyboard_);
            return result == null ? Keyboard.UNRECOGNIZED : result;
        }

        @Override
        public int getNavHiddenValue() {
            return this.navHidden_;
        }

        @Override
        public NavHidden getNavHidden() {
            NavHidden result = NavHidden.valueOf(this.navHidden_);
            return result == null ? NavHidden.UNRECOGNIZED : result;
        }

        @Override
        public int getNavigationValue() {
            return this.navigation_;
        }

        @Override
        public Navigation getNavigation() {
            Navigation result = Navigation.valueOf(this.navigation_);
            return result == null ? Navigation.UNRECOGNIZED : result;
        }

        @Override
        public int getSdkVersion() {
            return this.sdkVersion_;
        }

        @Override
        public String getProduct() {
            Object ref = this.product_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.product_ = s3;
            return s3;
        }

        @Override
        public ByteString getProductBytes() {
            Object ref = this.product_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.product_ = b2;
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
            if (this.mcc_ != 0) {
                output.writeUInt32(1, this.mcc_);
            }
            if (this.mnc_ != 0) {
                output.writeUInt32(2, this.mnc_);
            }
            if (!this.getLocaleBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 3, this.locale_);
            }
            if (this.layoutDirection_ != LayoutDirection.LAYOUT_DIRECTION_UNSET.getNumber()) {
                output.writeEnum(4, this.layoutDirection_);
            }
            if (this.screenWidth_ != 0) {
                output.writeUInt32(5, this.screenWidth_);
            }
            if (this.screenHeight_ != 0) {
                output.writeUInt32(6, this.screenHeight_);
            }
            if (this.screenWidthDp_ != 0) {
                output.writeUInt32(7, this.screenWidthDp_);
            }
            if (this.screenHeightDp_ != 0) {
                output.writeUInt32(8, this.screenHeightDp_);
            }
            if (this.smallestScreenWidthDp_ != 0) {
                output.writeUInt32(9, this.smallestScreenWidthDp_);
            }
            if (this.screenLayoutSize_ != ScreenLayoutSize.SCREEN_LAYOUT_SIZE_UNSET.getNumber()) {
                output.writeEnum(10, this.screenLayoutSize_);
            }
            if (this.screenLayoutLong_ != ScreenLayoutLong.SCREEN_LAYOUT_LONG_UNSET.getNumber()) {
                output.writeEnum(11, this.screenLayoutLong_);
            }
            if (this.screenRound_ != ScreenRound.SCREEN_ROUND_UNSET.getNumber()) {
                output.writeEnum(12, this.screenRound_);
            }
            if (this.wideColorGamut_ != WideColorGamut.WIDE_COLOR_GAMUT_UNSET.getNumber()) {
                output.writeEnum(13, this.wideColorGamut_);
            }
            if (this.hdr_ != Hdr.HDR_UNSET.getNumber()) {
                output.writeEnum(14, this.hdr_);
            }
            if (this.orientation_ != Orientation.ORIENTATION_UNSET.getNumber()) {
                output.writeEnum(15, this.orientation_);
            }
            if (this.uiModeType_ != UiModeType.UI_MODE_TYPE_UNSET.getNumber()) {
                output.writeEnum(16, this.uiModeType_);
            }
            if (this.uiModeNight_ != UiModeNight.UI_MODE_NIGHT_UNSET.getNumber()) {
                output.writeEnum(17, this.uiModeNight_);
            }
            if (this.density_ != 0) {
                output.writeUInt32(18, this.density_);
            }
            if (this.touchscreen_ != Touchscreen.TOUCHSCREEN_UNSET.getNumber()) {
                output.writeEnum(19, this.touchscreen_);
            }
            if (this.keysHidden_ != KeysHidden.KEYS_HIDDEN_UNSET.getNumber()) {
                output.writeEnum(20, this.keysHidden_);
            }
            if (this.keyboard_ != Keyboard.KEYBOARD_UNSET.getNumber()) {
                output.writeEnum(21, this.keyboard_);
            }
            if (this.navHidden_ != NavHidden.NAV_HIDDEN_UNSET.getNumber()) {
                output.writeEnum(22, this.navHidden_);
            }
            if (this.navigation_ != Navigation.NAVIGATION_UNSET.getNumber()) {
                output.writeEnum(23, this.navigation_);
            }
            if (this.sdkVersion_ != 0) {
                output.writeUInt32(24, this.sdkVersion_);
            }
            if (!this.getProductBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 25, this.product_);
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
            if (this.mcc_ != 0) {
                size += CodedOutputStream.computeUInt32Size(1, this.mcc_);
            }
            if (this.mnc_ != 0) {
                size += CodedOutputStream.computeUInt32Size(2, this.mnc_);
            }
            if (!this.getLocaleBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(3, this.locale_);
            }
            if (this.layoutDirection_ != LayoutDirection.LAYOUT_DIRECTION_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(4, this.layoutDirection_);
            }
            if (this.screenWidth_ != 0) {
                size += CodedOutputStream.computeUInt32Size(5, this.screenWidth_);
            }
            if (this.screenHeight_ != 0) {
                size += CodedOutputStream.computeUInt32Size(6, this.screenHeight_);
            }
            if (this.screenWidthDp_ != 0) {
                size += CodedOutputStream.computeUInt32Size(7, this.screenWidthDp_);
            }
            if (this.screenHeightDp_ != 0) {
                size += CodedOutputStream.computeUInt32Size(8, this.screenHeightDp_);
            }
            if (this.smallestScreenWidthDp_ != 0) {
                size += CodedOutputStream.computeUInt32Size(9, this.smallestScreenWidthDp_);
            }
            if (this.screenLayoutSize_ != ScreenLayoutSize.SCREEN_LAYOUT_SIZE_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(10, this.screenLayoutSize_);
            }
            if (this.screenLayoutLong_ != ScreenLayoutLong.SCREEN_LAYOUT_LONG_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(11, this.screenLayoutLong_);
            }
            if (this.screenRound_ != ScreenRound.SCREEN_ROUND_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(12, this.screenRound_);
            }
            if (this.wideColorGamut_ != WideColorGamut.WIDE_COLOR_GAMUT_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(13, this.wideColorGamut_);
            }
            if (this.hdr_ != Hdr.HDR_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(14, this.hdr_);
            }
            if (this.orientation_ != Orientation.ORIENTATION_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(15, this.orientation_);
            }
            if (this.uiModeType_ != UiModeType.UI_MODE_TYPE_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(16, this.uiModeType_);
            }
            if (this.uiModeNight_ != UiModeNight.UI_MODE_NIGHT_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(17, this.uiModeNight_);
            }
            if (this.density_ != 0) {
                size += CodedOutputStream.computeUInt32Size(18, this.density_);
            }
            if (this.touchscreen_ != Touchscreen.TOUCHSCREEN_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(19, this.touchscreen_);
            }
            if (this.keysHidden_ != KeysHidden.KEYS_HIDDEN_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(20, this.keysHidden_);
            }
            if (this.keyboard_ != Keyboard.KEYBOARD_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(21, this.keyboard_);
            }
            if (this.navHidden_ != NavHidden.NAV_HIDDEN_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(22, this.navHidden_);
            }
            if (this.navigation_ != Navigation.NAVIGATION_UNSET.getNumber()) {
                size += CodedOutputStream.computeEnumSize(23, this.navigation_);
            }
            if (this.sdkVersion_ != 0) {
                size += CodedOutputStream.computeUInt32Size(24, this.sdkVersion_);
            }
            if (!this.getProductBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(25, this.product_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Configuration)) {
                return super.equals(obj);
            }
            Configuration other = (Configuration)obj;
            if (this.getMcc() != other.getMcc()) {
                return false;
            }
            if (this.getMnc() != other.getMnc()) {
                return false;
            }
            if (!this.getLocale().equals(other.getLocale())) {
                return false;
            }
            if (this.layoutDirection_ != other.layoutDirection_) {
                return false;
            }
            if (this.getScreenWidth() != other.getScreenWidth()) {
                return false;
            }
            if (this.getScreenHeight() != other.getScreenHeight()) {
                return false;
            }
            if (this.getScreenWidthDp() != other.getScreenWidthDp()) {
                return false;
            }
            if (this.getScreenHeightDp() != other.getScreenHeightDp()) {
                return false;
            }
            if (this.getSmallestScreenWidthDp() != other.getSmallestScreenWidthDp()) {
                return false;
            }
            if (this.screenLayoutSize_ != other.screenLayoutSize_) {
                return false;
            }
            if (this.screenLayoutLong_ != other.screenLayoutLong_) {
                return false;
            }
            if (this.screenRound_ != other.screenRound_) {
                return false;
            }
            if (this.wideColorGamut_ != other.wideColorGamut_) {
                return false;
            }
            if (this.hdr_ != other.hdr_) {
                return false;
            }
            if (this.orientation_ != other.orientation_) {
                return false;
            }
            if (this.uiModeType_ != other.uiModeType_) {
                return false;
            }
            if (this.uiModeNight_ != other.uiModeNight_) {
                return false;
            }
            if (this.getDensity() != other.getDensity()) {
                return false;
            }
            if (this.touchscreen_ != other.touchscreen_) {
                return false;
            }
            if (this.keysHidden_ != other.keysHidden_) {
                return false;
            }
            if (this.keyboard_ != other.keyboard_) {
                return false;
            }
            if (this.navHidden_ != other.navHidden_) {
                return false;
            }
            if (this.navigation_ != other.navigation_) {
                return false;
            }
            if (this.getSdkVersion() != other.getSdkVersion()) {
                return false;
            }
            if (!this.getProduct().equals(other.getProduct())) {
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
            hash = 19 * hash + Configuration.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMcc();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getMnc();
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getLocale().hashCode();
            hash = 37 * hash + 4;
            hash = 53 * hash + this.layoutDirection_;
            hash = 37 * hash + 5;
            hash = 53 * hash + this.getScreenWidth();
            hash = 37 * hash + 6;
            hash = 53 * hash + this.getScreenHeight();
            hash = 37 * hash + 7;
            hash = 53 * hash + this.getScreenWidthDp();
            hash = 37 * hash + 8;
            hash = 53 * hash + this.getScreenHeightDp();
            hash = 37 * hash + 9;
            hash = 53 * hash + this.getSmallestScreenWidthDp();
            hash = 37 * hash + 10;
            hash = 53 * hash + this.screenLayoutSize_;
            hash = 37 * hash + 11;
            hash = 53 * hash + this.screenLayoutLong_;
            hash = 37 * hash + 12;
            hash = 53 * hash + this.screenRound_;
            hash = 37 * hash + 13;
            hash = 53 * hash + this.wideColorGamut_;
            hash = 37 * hash + 14;
            hash = 53 * hash + this.hdr_;
            hash = 37 * hash + 15;
            hash = 53 * hash + this.orientation_;
            hash = 37 * hash + 16;
            hash = 53 * hash + this.uiModeType_;
            hash = 37 * hash + 17;
            hash = 53 * hash + this.uiModeNight_;
            hash = 37 * hash + 18;
            hash = 53 * hash + this.getDensity();
            hash = 37 * hash + 19;
            hash = 53 * hash + this.touchscreen_;
            hash = 37 * hash + 20;
            hash = 53 * hash + this.keysHidden_;
            hash = 37 * hash + 21;
            hash = 53 * hash + this.keyboard_;
            hash = 37 * hash + 22;
            hash = 53 * hash + this.navHidden_;
            hash = 37 * hash + 23;
            hash = 53 * hash + this.navigation_;
            hash = 37 * hash + 24;
            hash = 53 * hash + this.getSdkVersion();
            hash = 37 * hash + 25;
            hash = 53 * hash + this.getProduct().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Configuration parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Configuration parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Configuration parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Configuration parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Configuration parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Configuration parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Configuration parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Configuration parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Configuration parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Configuration parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Configuration parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Configuration parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Configuration.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Configuration prototype) {
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

        public static Configuration getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Configuration> parser() {
            return PARSER;
        }

        public Parser<Configuration> getParserForType() {
            return PARSER;
        }

        @Override
        public Configuration getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ConfigurationOrBuilder {
            private int mcc_;
            private int mnc_;
            private Object locale_ = "";
            private int layoutDirection_ = 0;
            private int screenWidth_;
            private int screenHeight_;
            private int screenWidthDp_;
            private int screenHeightDp_;
            private int smallestScreenWidthDp_;
            private int screenLayoutSize_ = 0;
            private int screenLayoutLong_ = 0;
            private int screenRound_ = 0;
            private int wideColorGamut_ = 0;
            private int hdr_ = 0;
            private int orientation_ = 0;
            private int uiModeType_ = 0;
            private int uiModeNight_ = 0;
            private int density_;
            private int touchscreen_ = 0;
            private int keysHidden_ = 0;
            private int keyboard_ = 0;
            private int navHidden_ = 0;
            private int navigation_ = 0;
            private int sdkVersion_;
            private Object product_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_aapt_pb_Configuration_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_aapt_pb_Configuration_fieldAccessorTable.ensureFieldAccessorsInitialized(Configuration.class, Builder.class);
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
                this.mcc_ = 0;
                this.mnc_ = 0;
                this.locale_ = "";
                this.layoutDirection_ = 0;
                this.screenWidth_ = 0;
                this.screenHeight_ = 0;
                this.screenWidthDp_ = 0;
                this.screenHeightDp_ = 0;
                this.smallestScreenWidthDp_ = 0;
                this.screenLayoutSize_ = 0;
                this.screenLayoutLong_ = 0;
                this.screenRound_ = 0;
                this.wideColorGamut_ = 0;
                this.hdr_ = 0;
                this.orientation_ = 0;
                this.uiModeType_ = 0;
                this.uiModeNight_ = 0;
                this.density_ = 0;
                this.touchscreen_ = 0;
                this.keysHidden_ = 0;
                this.keyboard_ = 0;
                this.navHidden_ = 0;
                this.navigation_ = 0;
                this.sdkVersion_ = 0;
                this.product_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_aapt_pb_Configuration_descriptor;
            }

            @Override
            public Configuration getDefaultInstanceForType() {
                return Configuration.getDefaultInstance();
            }

            @Override
            public Configuration build() {
                Configuration result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Configuration buildPartial() {
                Configuration result = new Configuration(this);
                result.mcc_ = this.mcc_;
                result.mnc_ = this.mnc_;
                result.locale_ = this.locale_;
                result.layoutDirection_ = this.layoutDirection_;
                result.screenWidth_ = this.screenWidth_;
                result.screenHeight_ = this.screenHeight_;
                result.screenWidthDp_ = this.screenWidthDp_;
                result.screenHeightDp_ = this.screenHeightDp_;
                result.smallestScreenWidthDp_ = this.smallestScreenWidthDp_;
                result.screenLayoutSize_ = this.screenLayoutSize_;
                result.screenLayoutLong_ = this.screenLayoutLong_;
                result.screenRound_ = this.screenRound_;
                result.wideColorGamut_ = this.wideColorGamut_;
                result.hdr_ = this.hdr_;
                result.orientation_ = this.orientation_;
                result.uiModeType_ = this.uiModeType_;
                result.uiModeNight_ = this.uiModeNight_;
                result.density_ = this.density_;
                result.touchscreen_ = this.touchscreen_;
                result.keysHidden_ = this.keysHidden_;
                result.keyboard_ = this.keyboard_;
                result.navHidden_ = this.navHidden_;
                result.navigation_ = this.navigation_;
                result.sdkVersion_ = this.sdkVersion_;
                result.product_ = this.product_;
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
                if (other instanceof Configuration) {
                    return this.mergeFrom((Configuration)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Configuration other) {
                if (other == Configuration.getDefaultInstance()) {
                    return this;
                }
                if (other.getMcc() != 0) {
                    this.setMcc(other.getMcc());
                }
                if (other.getMnc() != 0) {
                    this.setMnc(other.getMnc());
                }
                if (!other.getLocale().isEmpty()) {
                    this.locale_ = other.locale_;
                    this.onChanged();
                }
                if (other.layoutDirection_ != 0) {
                    this.setLayoutDirectionValue(other.getLayoutDirectionValue());
                }
                if (other.getScreenWidth() != 0) {
                    this.setScreenWidth(other.getScreenWidth());
                }
                if (other.getScreenHeight() != 0) {
                    this.setScreenHeight(other.getScreenHeight());
                }
                if (other.getScreenWidthDp() != 0) {
                    this.setScreenWidthDp(other.getScreenWidthDp());
                }
                if (other.getScreenHeightDp() != 0) {
                    this.setScreenHeightDp(other.getScreenHeightDp());
                }
                if (other.getSmallestScreenWidthDp() != 0) {
                    this.setSmallestScreenWidthDp(other.getSmallestScreenWidthDp());
                }
                if (other.screenLayoutSize_ != 0) {
                    this.setScreenLayoutSizeValue(other.getScreenLayoutSizeValue());
                }
                if (other.screenLayoutLong_ != 0) {
                    this.setScreenLayoutLongValue(other.getScreenLayoutLongValue());
                }
                if (other.screenRound_ != 0) {
                    this.setScreenRoundValue(other.getScreenRoundValue());
                }
                if (other.wideColorGamut_ != 0) {
                    this.setWideColorGamutValue(other.getWideColorGamutValue());
                }
                if (other.hdr_ != 0) {
                    this.setHdrValue(other.getHdrValue());
                }
                if (other.orientation_ != 0) {
                    this.setOrientationValue(other.getOrientationValue());
                }
                if (other.uiModeType_ != 0) {
                    this.setUiModeTypeValue(other.getUiModeTypeValue());
                }
                if (other.uiModeNight_ != 0) {
                    this.setUiModeNightValue(other.getUiModeNightValue());
                }
                if (other.getDensity() != 0) {
                    this.setDensity(other.getDensity());
                }
                if (other.touchscreen_ != 0) {
                    this.setTouchscreenValue(other.getTouchscreenValue());
                }
                if (other.keysHidden_ != 0) {
                    this.setKeysHiddenValue(other.getKeysHiddenValue());
                }
                if (other.keyboard_ != 0) {
                    this.setKeyboardValue(other.getKeyboardValue());
                }
                if (other.navHidden_ != 0) {
                    this.setNavHiddenValue(other.getNavHiddenValue());
                }
                if (other.navigation_ != 0) {
                    this.setNavigationValue(other.getNavigationValue());
                }
                if (other.getSdkVersion() != 0) {
                    this.setSdkVersion(other.getSdkVersion());
                }
                if (!other.getProduct().isEmpty()) {
                    this.product_ = other.product_;
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
                Configuration parsedMessage = null;
                try {
                    parsedMessage = (Configuration)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Configuration)e2.getUnfinishedMessage();
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
            public int getMcc() {
                return this.mcc_;
            }

            public Builder setMcc(int value) {
                this.mcc_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMcc() {
                this.mcc_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getMnc() {
                return this.mnc_;
            }

            public Builder setMnc(int value) {
                this.mnc_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMnc() {
                this.mnc_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public String getLocale() {
                Object ref = this.locale_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.locale_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getLocaleBytes() {
                Object ref = this.locale_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.locale_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setLocale(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.locale_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearLocale() {
                this.locale_ = Configuration.getDefaultInstance().getLocale();
                this.onChanged();
                return this;
            }

            public Builder setLocaleBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Configuration.checkByteStringIsUtf8(value);
                this.locale_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public int getLayoutDirectionValue() {
                return this.layoutDirection_;
            }

            public Builder setLayoutDirectionValue(int value) {
                this.layoutDirection_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public LayoutDirection getLayoutDirection() {
                LayoutDirection result = LayoutDirection.valueOf(this.layoutDirection_);
                return result == null ? LayoutDirection.UNRECOGNIZED : result;
            }

            public Builder setLayoutDirection(LayoutDirection value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.layoutDirection_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearLayoutDirection() {
                this.layoutDirection_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getScreenWidth() {
                return this.screenWidth_;
            }

            public Builder setScreenWidth(int value) {
                this.screenWidth_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearScreenWidth() {
                this.screenWidth_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getScreenHeight() {
                return this.screenHeight_;
            }

            public Builder setScreenHeight(int value) {
                this.screenHeight_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearScreenHeight() {
                this.screenHeight_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getScreenWidthDp() {
                return this.screenWidthDp_;
            }

            public Builder setScreenWidthDp(int value) {
                this.screenWidthDp_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearScreenWidthDp() {
                this.screenWidthDp_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getScreenHeightDp() {
                return this.screenHeightDp_;
            }

            public Builder setScreenHeightDp(int value) {
                this.screenHeightDp_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearScreenHeightDp() {
                this.screenHeightDp_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getSmallestScreenWidthDp() {
                return this.smallestScreenWidthDp_;
            }

            public Builder setSmallestScreenWidthDp(int value) {
                this.smallestScreenWidthDp_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearSmallestScreenWidthDp() {
                this.smallestScreenWidthDp_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getScreenLayoutSizeValue() {
                return this.screenLayoutSize_;
            }

            public Builder setScreenLayoutSizeValue(int value) {
                this.screenLayoutSize_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public ScreenLayoutSize getScreenLayoutSize() {
                ScreenLayoutSize result = ScreenLayoutSize.valueOf(this.screenLayoutSize_);
                return result == null ? ScreenLayoutSize.UNRECOGNIZED : result;
            }

            public Builder setScreenLayoutSize(ScreenLayoutSize value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.screenLayoutSize_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearScreenLayoutSize() {
                this.screenLayoutSize_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getScreenLayoutLongValue() {
                return this.screenLayoutLong_;
            }

            public Builder setScreenLayoutLongValue(int value) {
                this.screenLayoutLong_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public ScreenLayoutLong getScreenLayoutLong() {
                ScreenLayoutLong result = ScreenLayoutLong.valueOf(this.screenLayoutLong_);
                return result == null ? ScreenLayoutLong.UNRECOGNIZED : result;
            }

            public Builder setScreenLayoutLong(ScreenLayoutLong value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.screenLayoutLong_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearScreenLayoutLong() {
                this.screenLayoutLong_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getScreenRoundValue() {
                return this.screenRound_;
            }

            public Builder setScreenRoundValue(int value) {
                this.screenRound_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public ScreenRound getScreenRound() {
                ScreenRound result = ScreenRound.valueOf(this.screenRound_);
                return result == null ? ScreenRound.UNRECOGNIZED : result;
            }

            public Builder setScreenRound(ScreenRound value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.screenRound_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearScreenRound() {
                this.screenRound_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getWideColorGamutValue() {
                return this.wideColorGamut_;
            }

            public Builder setWideColorGamutValue(int value) {
                this.wideColorGamut_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public WideColorGamut getWideColorGamut() {
                WideColorGamut result = WideColorGamut.valueOf(this.wideColorGamut_);
                return result == null ? WideColorGamut.UNRECOGNIZED : result;
            }

            public Builder setWideColorGamut(WideColorGamut value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.wideColorGamut_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearWideColorGamut() {
                this.wideColorGamut_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getHdrValue() {
                return this.hdr_;
            }

            public Builder setHdrValue(int value) {
                this.hdr_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public Hdr getHdr() {
                Hdr result = Hdr.valueOf(this.hdr_);
                return result == null ? Hdr.UNRECOGNIZED : result;
            }

            public Builder setHdr(Hdr value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.hdr_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearHdr() {
                this.hdr_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getOrientationValue() {
                return this.orientation_;
            }

            public Builder setOrientationValue(int value) {
                this.orientation_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public Orientation getOrientation() {
                Orientation result = Orientation.valueOf(this.orientation_);
                return result == null ? Orientation.UNRECOGNIZED : result;
            }

            public Builder setOrientation(Orientation value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.orientation_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearOrientation() {
                this.orientation_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getUiModeTypeValue() {
                return this.uiModeType_;
            }

            public Builder setUiModeTypeValue(int value) {
                this.uiModeType_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public UiModeType getUiModeType() {
                UiModeType result = UiModeType.valueOf(this.uiModeType_);
                return result == null ? UiModeType.UNRECOGNIZED : result;
            }

            public Builder setUiModeType(UiModeType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.uiModeType_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearUiModeType() {
                this.uiModeType_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getUiModeNightValue() {
                return this.uiModeNight_;
            }

            public Builder setUiModeNightValue(int value) {
                this.uiModeNight_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public UiModeNight getUiModeNight() {
                UiModeNight result = UiModeNight.valueOf(this.uiModeNight_);
                return result == null ? UiModeNight.UNRECOGNIZED : result;
            }

            public Builder setUiModeNight(UiModeNight value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.uiModeNight_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearUiModeNight() {
                this.uiModeNight_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getDensity() {
                return this.density_;
            }

            public Builder setDensity(int value) {
                this.density_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearDensity() {
                this.density_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getTouchscreenValue() {
                return this.touchscreen_;
            }

            public Builder setTouchscreenValue(int value) {
                this.touchscreen_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public Touchscreen getTouchscreen() {
                Touchscreen result = Touchscreen.valueOf(this.touchscreen_);
                return result == null ? Touchscreen.UNRECOGNIZED : result;
            }

            public Builder setTouchscreen(Touchscreen value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.touchscreen_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearTouchscreen() {
                this.touchscreen_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getKeysHiddenValue() {
                return this.keysHidden_;
            }

            public Builder setKeysHiddenValue(int value) {
                this.keysHidden_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public KeysHidden getKeysHidden() {
                KeysHidden result = KeysHidden.valueOf(this.keysHidden_);
                return result == null ? KeysHidden.UNRECOGNIZED : result;
            }

            public Builder setKeysHidden(KeysHidden value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.keysHidden_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearKeysHidden() {
                this.keysHidden_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getKeyboardValue() {
                return this.keyboard_;
            }

            public Builder setKeyboardValue(int value) {
                this.keyboard_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public Keyboard getKeyboard() {
                Keyboard result = Keyboard.valueOf(this.keyboard_);
                return result == null ? Keyboard.UNRECOGNIZED : result;
            }

            public Builder setKeyboard(Keyboard value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.keyboard_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearKeyboard() {
                this.keyboard_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getNavHiddenValue() {
                return this.navHidden_;
            }

            public Builder setNavHiddenValue(int value) {
                this.navHidden_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public NavHidden getNavHidden() {
                NavHidden result = NavHidden.valueOf(this.navHidden_);
                return result == null ? NavHidden.UNRECOGNIZED : result;
            }

            public Builder setNavHidden(NavHidden value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.navHidden_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearNavHidden() {
                this.navHidden_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getNavigationValue() {
                return this.navigation_;
            }

            public Builder setNavigationValue(int value) {
                this.navigation_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public Navigation getNavigation() {
                Navigation result = Navigation.valueOf(this.navigation_);
                return result == null ? Navigation.UNRECOGNIZED : result;
            }

            public Builder setNavigation(Navigation value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.navigation_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearNavigation() {
                this.navigation_ = 0;
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
            public String getProduct() {
                Object ref = this.product_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.product_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getProductBytes() {
                Object ref = this.product_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.product_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setProduct(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.product_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearProduct() {
                this.product_ = Configuration.getDefaultInstance().getProduct();
                this.onChanged();
                return this;
            }

            public Builder setProductBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                Configuration.checkByteStringIsUtf8(value);
                this.product_ = value;
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

        public static enum Navigation implements ProtocolMessageEnum
        {
            NAVIGATION_UNSET(0),
            NAVIGATION_NONAV(1),
            NAVIGATION_DPAD(2),
            NAVIGATION_TRACKBALL(3),
            NAVIGATION_WHEEL(4),
            UNRECOGNIZED(-1);

            public static final int NAVIGATION_UNSET_VALUE = 0;
            public static final int NAVIGATION_NONAV_VALUE = 1;
            public static final int NAVIGATION_DPAD_VALUE = 2;
            public static final int NAVIGATION_TRACKBALL_VALUE = 3;
            public static final int NAVIGATION_WHEEL_VALUE = 4;
            private static final Internal.EnumLiteMap<Navigation> internalValueMap;
            private static final Navigation[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static Navigation valueOf(int value) {
                return Navigation.forNumber(value);
            }

            public static Navigation forNumber(int value) {
                switch (value) {
                    case 0: {
                        return NAVIGATION_UNSET;
                    }
                    case 1: {
                        return NAVIGATION_NONAV;
                    }
                    case 2: {
                        return NAVIGATION_DPAD;
                    }
                    case 3: {
                        return NAVIGATION_TRACKBALL;
                    }
                    case 4: {
                        return NAVIGATION_WHEEL;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<Navigation> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return Navigation.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return Navigation.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(13);
            }

            public static Navigation valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != Navigation.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private Navigation(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<Navigation>(){

                    @Override
                    public Navigation findValueByNumber(int number) {
                        return Navigation.forNumber(number);
                    }
                };
                VALUES = Navigation.values();
            }
        }

        public static enum NavHidden implements ProtocolMessageEnum
        {
            NAV_HIDDEN_UNSET(0),
            NAV_HIDDEN_NAVEXPOSED(1),
            NAV_HIDDEN_NAVHIDDEN(2),
            UNRECOGNIZED(-1);

            public static final int NAV_HIDDEN_UNSET_VALUE = 0;
            public static final int NAV_HIDDEN_NAVEXPOSED_VALUE = 1;
            public static final int NAV_HIDDEN_NAVHIDDEN_VALUE = 2;
            private static final Internal.EnumLiteMap<NavHidden> internalValueMap;
            private static final NavHidden[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static NavHidden valueOf(int value) {
                return NavHidden.forNumber(value);
            }

            public static NavHidden forNumber(int value) {
                switch (value) {
                    case 0: {
                        return NAV_HIDDEN_UNSET;
                    }
                    case 1: {
                        return NAV_HIDDEN_NAVEXPOSED;
                    }
                    case 2: {
                        return NAV_HIDDEN_NAVHIDDEN;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<NavHidden> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return NavHidden.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return NavHidden.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(12);
            }

            public static NavHidden valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != NavHidden.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private NavHidden(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<NavHidden>(){

                    @Override
                    public NavHidden findValueByNumber(int number) {
                        return NavHidden.forNumber(number);
                    }
                };
                VALUES = NavHidden.values();
            }
        }

        public static enum Keyboard implements ProtocolMessageEnum
        {
            KEYBOARD_UNSET(0),
            KEYBOARD_NOKEYS(1),
            KEYBOARD_QWERTY(2),
            KEYBOARD_TWELVEKEY(3),
            UNRECOGNIZED(-1);

            public static final int KEYBOARD_UNSET_VALUE = 0;
            public static final int KEYBOARD_NOKEYS_VALUE = 1;
            public static final int KEYBOARD_QWERTY_VALUE = 2;
            public static final int KEYBOARD_TWELVEKEY_VALUE = 3;
            private static final Internal.EnumLiteMap<Keyboard> internalValueMap;
            private static final Keyboard[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static Keyboard valueOf(int value) {
                return Keyboard.forNumber(value);
            }

            public static Keyboard forNumber(int value) {
                switch (value) {
                    case 0: {
                        return KEYBOARD_UNSET;
                    }
                    case 1: {
                        return KEYBOARD_NOKEYS;
                    }
                    case 2: {
                        return KEYBOARD_QWERTY;
                    }
                    case 3: {
                        return KEYBOARD_TWELVEKEY;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<Keyboard> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return Keyboard.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return Keyboard.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(11);
            }

            public static Keyboard valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != Keyboard.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private Keyboard(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<Keyboard>(){

                    @Override
                    public Keyboard findValueByNumber(int number) {
                        return Keyboard.forNumber(number);
                    }
                };
                VALUES = Keyboard.values();
            }
        }

        public static enum KeysHidden implements ProtocolMessageEnum
        {
            KEYS_HIDDEN_UNSET(0),
            KEYS_HIDDEN_KEYSEXPOSED(1),
            KEYS_HIDDEN_KEYSHIDDEN(2),
            KEYS_HIDDEN_KEYSSOFT(3),
            UNRECOGNIZED(-1);

            public static final int KEYS_HIDDEN_UNSET_VALUE = 0;
            public static final int KEYS_HIDDEN_KEYSEXPOSED_VALUE = 1;
            public static final int KEYS_HIDDEN_KEYSHIDDEN_VALUE = 2;
            public static final int KEYS_HIDDEN_KEYSSOFT_VALUE = 3;
            private static final Internal.EnumLiteMap<KeysHidden> internalValueMap;
            private static final KeysHidden[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static KeysHidden valueOf(int value) {
                return KeysHidden.forNumber(value);
            }

            public static KeysHidden forNumber(int value) {
                switch (value) {
                    case 0: {
                        return KEYS_HIDDEN_UNSET;
                    }
                    case 1: {
                        return KEYS_HIDDEN_KEYSEXPOSED;
                    }
                    case 2: {
                        return KEYS_HIDDEN_KEYSHIDDEN;
                    }
                    case 3: {
                        return KEYS_HIDDEN_KEYSSOFT;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<KeysHidden> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return KeysHidden.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return KeysHidden.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(10);
            }

            public static KeysHidden valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != KeysHidden.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private KeysHidden(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<KeysHidden>(){

                    @Override
                    public KeysHidden findValueByNumber(int number) {
                        return KeysHidden.forNumber(number);
                    }
                };
                VALUES = KeysHidden.values();
            }
        }

        public static enum Touchscreen implements ProtocolMessageEnum
        {
            TOUCHSCREEN_UNSET(0),
            TOUCHSCREEN_NOTOUCH(1),
            TOUCHSCREEN_STYLUS(2),
            TOUCHSCREEN_FINGER(3),
            UNRECOGNIZED(-1);

            public static final int TOUCHSCREEN_UNSET_VALUE = 0;
            public static final int TOUCHSCREEN_NOTOUCH_VALUE = 1;
            public static final int TOUCHSCREEN_STYLUS_VALUE = 2;
            public static final int TOUCHSCREEN_FINGER_VALUE = 3;
            private static final Internal.EnumLiteMap<Touchscreen> internalValueMap;
            private static final Touchscreen[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static Touchscreen valueOf(int value) {
                return Touchscreen.forNumber(value);
            }

            public static Touchscreen forNumber(int value) {
                switch (value) {
                    case 0: {
                        return TOUCHSCREEN_UNSET;
                    }
                    case 1: {
                        return TOUCHSCREEN_NOTOUCH;
                    }
                    case 2: {
                        return TOUCHSCREEN_STYLUS;
                    }
                    case 3: {
                        return TOUCHSCREEN_FINGER;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<Touchscreen> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return Touchscreen.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return Touchscreen.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(9);
            }

            public static Touchscreen valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != Touchscreen.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private Touchscreen(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<Touchscreen>(){

                    @Override
                    public Touchscreen findValueByNumber(int number) {
                        return Touchscreen.forNumber(number);
                    }
                };
                VALUES = Touchscreen.values();
            }
        }

        public static enum UiModeNight implements ProtocolMessageEnum
        {
            UI_MODE_NIGHT_UNSET(0),
            UI_MODE_NIGHT_NIGHT(1),
            UI_MODE_NIGHT_NOTNIGHT(2),
            UNRECOGNIZED(-1);

            public static final int UI_MODE_NIGHT_UNSET_VALUE = 0;
            public static final int UI_MODE_NIGHT_NIGHT_VALUE = 1;
            public static final int UI_MODE_NIGHT_NOTNIGHT_VALUE = 2;
            private static final Internal.EnumLiteMap<UiModeNight> internalValueMap;
            private static final UiModeNight[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static UiModeNight valueOf(int value) {
                return UiModeNight.forNumber(value);
            }

            public static UiModeNight forNumber(int value) {
                switch (value) {
                    case 0: {
                        return UI_MODE_NIGHT_UNSET;
                    }
                    case 1: {
                        return UI_MODE_NIGHT_NIGHT;
                    }
                    case 2: {
                        return UI_MODE_NIGHT_NOTNIGHT;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<UiModeNight> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return UiModeNight.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return UiModeNight.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(8);
            }

            public static UiModeNight valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != UiModeNight.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private UiModeNight(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<UiModeNight>(){

                    @Override
                    public UiModeNight findValueByNumber(int number) {
                        return UiModeNight.forNumber(number);
                    }
                };
                VALUES = UiModeNight.values();
            }
        }

        public static enum UiModeType implements ProtocolMessageEnum
        {
            UI_MODE_TYPE_UNSET(0),
            UI_MODE_TYPE_NORMAL(1),
            UI_MODE_TYPE_DESK(2),
            UI_MODE_TYPE_CAR(3),
            UI_MODE_TYPE_TELEVISION(4),
            UI_MODE_TYPE_APPLIANCE(5),
            UI_MODE_TYPE_WATCH(6),
            UI_MODE_TYPE_VRHEADSET(7),
            UNRECOGNIZED(-1);

            public static final int UI_MODE_TYPE_UNSET_VALUE = 0;
            public static final int UI_MODE_TYPE_NORMAL_VALUE = 1;
            public static final int UI_MODE_TYPE_DESK_VALUE = 2;
            public static final int UI_MODE_TYPE_CAR_VALUE = 3;
            public static final int UI_MODE_TYPE_TELEVISION_VALUE = 4;
            public static final int UI_MODE_TYPE_APPLIANCE_VALUE = 5;
            public static final int UI_MODE_TYPE_WATCH_VALUE = 6;
            public static final int UI_MODE_TYPE_VRHEADSET_VALUE = 7;
            private static final Internal.EnumLiteMap<UiModeType> internalValueMap;
            private static final UiModeType[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static UiModeType valueOf(int value) {
                return UiModeType.forNumber(value);
            }

            public static UiModeType forNumber(int value) {
                switch (value) {
                    case 0: {
                        return UI_MODE_TYPE_UNSET;
                    }
                    case 1: {
                        return UI_MODE_TYPE_NORMAL;
                    }
                    case 2: {
                        return UI_MODE_TYPE_DESK;
                    }
                    case 3: {
                        return UI_MODE_TYPE_CAR;
                    }
                    case 4: {
                        return UI_MODE_TYPE_TELEVISION;
                    }
                    case 5: {
                        return UI_MODE_TYPE_APPLIANCE;
                    }
                    case 6: {
                        return UI_MODE_TYPE_WATCH;
                    }
                    case 7: {
                        return UI_MODE_TYPE_VRHEADSET;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<UiModeType> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return UiModeType.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return UiModeType.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(7);
            }

            public static UiModeType valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != UiModeType.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private UiModeType(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<UiModeType>(){

                    @Override
                    public UiModeType findValueByNumber(int number) {
                        return UiModeType.forNumber(number);
                    }
                };
                VALUES = UiModeType.values();
            }
        }

        public static enum Orientation implements ProtocolMessageEnum
        {
            ORIENTATION_UNSET(0),
            ORIENTATION_PORT(1),
            ORIENTATION_LAND(2),
            ORIENTATION_SQUARE(3),
            UNRECOGNIZED(-1);

            public static final int ORIENTATION_UNSET_VALUE = 0;
            public static final int ORIENTATION_PORT_VALUE = 1;
            public static final int ORIENTATION_LAND_VALUE = 2;
            public static final int ORIENTATION_SQUARE_VALUE = 3;
            private static final Internal.EnumLiteMap<Orientation> internalValueMap;
            private static final Orientation[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static Orientation valueOf(int value) {
                return Orientation.forNumber(value);
            }

            public static Orientation forNumber(int value) {
                switch (value) {
                    case 0: {
                        return ORIENTATION_UNSET;
                    }
                    case 1: {
                        return ORIENTATION_PORT;
                    }
                    case 2: {
                        return ORIENTATION_LAND;
                    }
                    case 3: {
                        return ORIENTATION_SQUARE;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<Orientation> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return Orientation.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return Orientation.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(6);
            }

            public static Orientation valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != Orientation.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private Orientation(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<Orientation>(){

                    @Override
                    public Orientation findValueByNumber(int number) {
                        return Orientation.forNumber(number);
                    }
                };
                VALUES = Orientation.values();
            }
        }

        public static enum Hdr implements ProtocolMessageEnum
        {
            HDR_UNSET(0),
            HDR_HIGHDR(1),
            HDR_LOWDR(2),
            UNRECOGNIZED(-1);

            public static final int HDR_UNSET_VALUE = 0;
            public static final int HDR_HIGHDR_VALUE = 1;
            public static final int HDR_LOWDR_VALUE = 2;
            private static final Internal.EnumLiteMap<Hdr> internalValueMap;
            private static final Hdr[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static Hdr valueOf(int value) {
                return Hdr.forNumber(value);
            }

            public static Hdr forNumber(int value) {
                switch (value) {
                    case 0: {
                        return HDR_UNSET;
                    }
                    case 1: {
                        return HDR_HIGHDR;
                    }
                    case 2: {
                        return HDR_LOWDR;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<Hdr> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return Hdr.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return Hdr.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(5);
            }

            public static Hdr valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != Hdr.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private Hdr(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<Hdr>(){

                    @Override
                    public Hdr findValueByNumber(int number) {
                        return Hdr.forNumber(number);
                    }
                };
                VALUES = Hdr.values();
            }
        }

        public static enum WideColorGamut implements ProtocolMessageEnum
        {
            WIDE_COLOR_GAMUT_UNSET(0),
            WIDE_COLOR_GAMUT_WIDECG(1),
            WIDE_COLOR_GAMUT_NOWIDECG(2),
            UNRECOGNIZED(-1);

            public static final int WIDE_COLOR_GAMUT_UNSET_VALUE = 0;
            public static final int WIDE_COLOR_GAMUT_WIDECG_VALUE = 1;
            public static final int WIDE_COLOR_GAMUT_NOWIDECG_VALUE = 2;
            private static final Internal.EnumLiteMap<WideColorGamut> internalValueMap;
            private static final WideColorGamut[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static WideColorGamut valueOf(int value) {
                return WideColorGamut.forNumber(value);
            }

            public static WideColorGamut forNumber(int value) {
                switch (value) {
                    case 0: {
                        return WIDE_COLOR_GAMUT_UNSET;
                    }
                    case 1: {
                        return WIDE_COLOR_GAMUT_WIDECG;
                    }
                    case 2: {
                        return WIDE_COLOR_GAMUT_NOWIDECG;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<WideColorGamut> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return WideColorGamut.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return WideColorGamut.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(4);
            }

            public static WideColorGamut valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != WideColorGamut.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private WideColorGamut(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<WideColorGamut>(){

                    @Override
                    public WideColorGamut findValueByNumber(int number) {
                        return WideColorGamut.forNumber(number);
                    }
                };
                VALUES = WideColorGamut.values();
            }
        }

        public static enum ScreenRound implements ProtocolMessageEnum
        {
            SCREEN_ROUND_UNSET(0),
            SCREEN_ROUND_ROUND(1),
            SCREEN_ROUND_NOTROUND(2),
            UNRECOGNIZED(-1);

            public static final int SCREEN_ROUND_UNSET_VALUE = 0;
            public static final int SCREEN_ROUND_ROUND_VALUE = 1;
            public static final int SCREEN_ROUND_NOTROUND_VALUE = 2;
            private static final Internal.EnumLiteMap<ScreenRound> internalValueMap;
            private static final ScreenRound[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static ScreenRound valueOf(int value) {
                return ScreenRound.forNumber(value);
            }

            public static ScreenRound forNumber(int value) {
                switch (value) {
                    case 0: {
                        return SCREEN_ROUND_UNSET;
                    }
                    case 1: {
                        return SCREEN_ROUND_ROUND;
                    }
                    case 2: {
                        return SCREEN_ROUND_NOTROUND;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<ScreenRound> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return ScreenRound.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return ScreenRound.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(3);
            }

            public static ScreenRound valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != ScreenRound.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private ScreenRound(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<ScreenRound>(){

                    @Override
                    public ScreenRound findValueByNumber(int number) {
                        return ScreenRound.forNumber(number);
                    }
                };
                VALUES = ScreenRound.values();
            }
        }

        public static enum ScreenLayoutLong implements ProtocolMessageEnum
        {
            SCREEN_LAYOUT_LONG_UNSET(0),
            SCREEN_LAYOUT_LONG_LONG(1),
            SCREEN_LAYOUT_LONG_NOTLONG(2),
            UNRECOGNIZED(-1);

            public static final int SCREEN_LAYOUT_LONG_UNSET_VALUE = 0;
            public static final int SCREEN_LAYOUT_LONG_LONG_VALUE = 1;
            public static final int SCREEN_LAYOUT_LONG_NOTLONG_VALUE = 2;
            private static final Internal.EnumLiteMap<ScreenLayoutLong> internalValueMap;
            private static final ScreenLayoutLong[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static ScreenLayoutLong valueOf(int value) {
                return ScreenLayoutLong.forNumber(value);
            }

            public static ScreenLayoutLong forNumber(int value) {
                switch (value) {
                    case 0: {
                        return SCREEN_LAYOUT_LONG_UNSET;
                    }
                    case 1: {
                        return SCREEN_LAYOUT_LONG_LONG;
                    }
                    case 2: {
                        return SCREEN_LAYOUT_LONG_NOTLONG;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<ScreenLayoutLong> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return ScreenLayoutLong.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return ScreenLayoutLong.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(2);
            }

            public static ScreenLayoutLong valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != ScreenLayoutLong.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private ScreenLayoutLong(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<ScreenLayoutLong>(){

                    @Override
                    public ScreenLayoutLong findValueByNumber(int number) {
                        return ScreenLayoutLong.forNumber(number);
                    }
                };
                VALUES = ScreenLayoutLong.values();
            }
        }

        public static enum ScreenLayoutSize implements ProtocolMessageEnum
        {
            SCREEN_LAYOUT_SIZE_UNSET(0),
            SCREEN_LAYOUT_SIZE_SMALL(1),
            SCREEN_LAYOUT_SIZE_NORMAL(2),
            SCREEN_LAYOUT_SIZE_LARGE(3),
            SCREEN_LAYOUT_SIZE_XLARGE(4),
            UNRECOGNIZED(-1);

            public static final int SCREEN_LAYOUT_SIZE_UNSET_VALUE = 0;
            public static final int SCREEN_LAYOUT_SIZE_SMALL_VALUE = 1;
            public static final int SCREEN_LAYOUT_SIZE_NORMAL_VALUE = 2;
            public static final int SCREEN_LAYOUT_SIZE_LARGE_VALUE = 3;
            public static final int SCREEN_LAYOUT_SIZE_XLARGE_VALUE = 4;
            private static final Internal.EnumLiteMap<ScreenLayoutSize> internalValueMap;
            private static final ScreenLayoutSize[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static ScreenLayoutSize valueOf(int value) {
                return ScreenLayoutSize.forNumber(value);
            }

            public static ScreenLayoutSize forNumber(int value) {
                switch (value) {
                    case 0: {
                        return SCREEN_LAYOUT_SIZE_UNSET;
                    }
                    case 1: {
                        return SCREEN_LAYOUT_SIZE_SMALL;
                    }
                    case 2: {
                        return SCREEN_LAYOUT_SIZE_NORMAL;
                    }
                    case 3: {
                        return SCREEN_LAYOUT_SIZE_LARGE;
                    }
                    case 4: {
                        return SCREEN_LAYOUT_SIZE_XLARGE;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<ScreenLayoutSize> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return ScreenLayoutSize.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return ScreenLayoutSize.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(1);
            }

            public static ScreenLayoutSize valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != ScreenLayoutSize.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private ScreenLayoutSize(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<ScreenLayoutSize>(){

                    @Override
                    public ScreenLayoutSize findValueByNumber(int number) {
                        return ScreenLayoutSize.forNumber(number);
                    }
                };
                VALUES = ScreenLayoutSize.values();
            }
        }

        public static enum LayoutDirection implements ProtocolMessageEnum
        {
            LAYOUT_DIRECTION_UNSET(0),
            LAYOUT_DIRECTION_LTR(1),
            LAYOUT_DIRECTION_RTL(2),
            UNRECOGNIZED(-1);

            public static final int LAYOUT_DIRECTION_UNSET_VALUE = 0;
            public static final int LAYOUT_DIRECTION_LTR_VALUE = 1;
            public static final int LAYOUT_DIRECTION_RTL_VALUE = 2;
            private static final Internal.EnumLiteMap<LayoutDirection> internalValueMap;
            private static final LayoutDirection[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static LayoutDirection valueOf(int value) {
                return LayoutDirection.forNumber(value);
            }

            public static LayoutDirection forNumber(int value) {
                switch (value) {
                    case 0: {
                        return LAYOUT_DIRECTION_UNSET;
                    }
                    case 1: {
                        return LAYOUT_DIRECTION_LTR;
                    }
                    case 2: {
                        return LAYOUT_DIRECTION_RTL;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<LayoutDirection> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return LayoutDirection.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return LayoutDirection.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Configuration.getDescriptor().getEnumTypes().get(0);
            }

            public static LayoutDirection valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != LayoutDirection.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private LayoutDirection(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<LayoutDirection>(){

                    @Override
                    public LayoutDirection findValueByNumber(int number) {
                        return LayoutDirection.forNumber(number);
                    }
                };
                VALUES = LayoutDirection.values();
            }
        }
    }

    public static interface ConfigurationOrBuilder
    extends MessageOrBuilder {
        public int getMcc();

        public int getMnc();

        public String getLocale();

        public ByteString getLocaleBytes();

        public int getLayoutDirectionValue();

        public Configuration.LayoutDirection getLayoutDirection();

        public int getScreenWidth();

        public int getScreenHeight();

        public int getScreenWidthDp();

        public int getScreenHeightDp();

        public int getSmallestScreenWidthDp();

        public int getScreenLayoutSizeValue();

        public Configuration.ScreenLayoutSize getScreenLayoutSize();

        public int getScreenLayoutLongValue();

        public Configuration.ScreenLayoutLong getScreenLayoutLong();

        public int getScreenRoundValue();

        public Configuration.ScreenRound getScreenRound();

        public int getWideColorGamutValue();

        public Configuration.WideColorGamut getWideColorGamut();

        public int getHdrValue();

        public Configuration.Hdr getHdr();

        public int getOrientationValue();

        public Configuration.Orientation getOrientation();

        public int getUiModeTypeValue();

        public Configuration.UiModeType getUiModeType();

        public int getUiModeNightValue();

        public Configuration.UiModeNight getUiModeNight();

        public int getDensity();

        public int getTouchscreenValue();

        public Configuration.Touchscreen getTouchscreen();

        public int getKeysHiddenValue();

        public Configuration.KeysHidden getKeysHidden();

        public int getKeyboardValue();

        public Configuration.Keyboard getKeyboard();

        public int getNavHiddenValue();

        public Configuration.NavHidden getNavHidden();

        public int getNavigationValue();

        public Configuration.Navigation getNavigation();

        public int getSdkVersion();

        public String getProduct();

        public ByteString getProductBytes();
    }
}

