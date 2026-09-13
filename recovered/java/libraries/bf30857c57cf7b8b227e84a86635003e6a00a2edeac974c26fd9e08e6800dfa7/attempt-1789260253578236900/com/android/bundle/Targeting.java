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
import com.google.protobuf.Int32Value;
import com.google.protobuf.Int32ValueOrBuilder;
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
import com.google.protobuf.WrappersProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Targeting {
    private static final Descriptors.Descriptor internal_static_android_bundle_VariantTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_VariantTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ApkTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ApkTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ModuleTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ModuleTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_UserCountriesTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_UserCountriesTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ScreenDensity_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ScreenDensity_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_SdkVersion_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_SdkVersion_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_GraphicsApi_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_GraphicsApi_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_VulkanVersion_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_VulkanVersion_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_OpenGlVersion_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_OpenGlVersion_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_TextureCompressionFormat_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_TextureCompressionFormat_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_Abi_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Abi_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_MultiAbi_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_MultiAbi_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_Sanitizer_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_Sanitizer_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_DeviceFeature_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_DeviceFeature_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_AssetsDirectoryTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_AssetsDirectoryTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_NativeDirectoryTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_NativeDirectoryTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ApexImageTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ApexImageTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_AbiTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_AbiTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_MultiAbiTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_MultiAbiTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ScreenDensityTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ScreenDensityTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_LanguageTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_LanguageTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_GraphicsApiTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_GraphicsApiTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_SdkVersionTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_SdkVersionTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_TextureCompressionFormatTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_TextureCompressionFormatTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_SanitizerTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_SanitizerTargeting_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_DeviceFeatureTargeting_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_DeviceFeatureTargeting_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private Targeting() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        Targeting.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u000ftargeting.proto\u0012\u000eandroid.bundle\u001a\u001egoogle/protobuf/wrappers.proto\"\u00f6\u0002\n\u0010VariantTargeting\u0012B\n\u0015sdk_version_targeting\u0018\u0001 \u0001(\u000b2#.android.bundle.SdkVersionTargeting\u00123\n\rabi_targeting\u0018\u0002 \u0001(\u000b2\u001c.android.bundle.AbiTargeting\u0012H\n\u0018screen_density_targeting\u0018\u0003 \u0001(\u000b2&.android.bundle.ScreenDensityTargeting\u0012>\n\u0013multi_abi_targeting\u0018\u0004 \u0001(\u000b2!.android.bundle.MultiAbiTargeting\u0012_\n$texture_compression_format_targeting\u0018\u0005 \u0001(\u000b21.androi", "d.bundle.TextureCompressionFormatTargeting\"\u00b8\u0004\n\fApkTargeting\u00123\n\rabi_targeting\u0018\u0001 \u0001(\u000b2\u001c.android.bundle.AbiTargeting\u0012D\n\u0016graphics_api_targeting\u0018\u0002 \u0001(\u000b2$.android.bundle.GraphicsApiTargeting\u0012=\n\u0012language_targeting\u0018\u0003 \u0001(\u000b2!.android.bundle.LanguageTargeting\u0012H\n\u0018screen_density_targeting\u0018\u0004 \u0001(\u000b2&.android.bundle.ScreenDensityTargeting\u0012B\n\u0015sdk_version_targeting\u0018\u0005 \u0001(\u000b2#.android.bundle.SdkVersionTargeting\u0012_\n$texture_c", "ompression_format_targeting\u0018\u0006 \u0001(\u000b21.android.bundle.TextureCompressionFormatTargeting\u0012>\n\u0013multi_abi_targeting\u0018\u0007 \u0001(\u000b2!.android.bundle.MultiAbiTargeting\u0012?\n\u0013sanitizer_targeting\u0018\b \u0001(\u000b2\".android.bundle.SanitizerTargeting\"\u00e9\u0001\n\u000fModuleTargeting\u0012B\n\u0015sdk_version_targeting\u0018\u0001 \u0001(\u000b2#.android.bundle.SdkVersionTargeting\u0012H\n\u0018device_feature_targeting\u0018\u0002 \u0003(\u000b2&.android.bundle.DeviceFeatureTargeting\u0012H\n\u0018user_countries_target", "ing\u0018\u0003 \u0001(\u000b2&.android.bundle.UserCountriesTargeting\"@\n\u0016UserCountriesTargeting\u0012\u0015\n\rcountry_codes\u0018\u0001 \u0003(\t\u0012\u000f\n\u0007exclude\u0018\u0002 \u0001(\b\"\u00fd\u0001\n\rScreenDensity\u0012C\n\rdensity_alias\u0018\u0001 \u0001(\u000e2*.android.bundle.ScreenDensity.DensityAliasH\u0000\u0012\u0015\n\u000bdensity_dpi\u0018\u0002 \u0001(\u0005H\u0000\"\u007f\n\fDensityAlias\u0012\u0017\n\u0013DENSITY_UNSPECIFIED\u0010\u0000\u0012\t\n\u0005NODPI\u0010\u0001\u0012\b\n\u0004LDPI\u0010\u0002\u0012\b\n\u0004MDPI\u0010\u0003\u0012\t\n\u0005TVDPI\u0010\u0004\u0012\b\n\u0004HDPI\u0010\u0005\u0012\t\n\u0005XHDPI\u0010\u0006\u0012\n\n\u0006XXHDPI\u0010\u0007\u0012\u000b\n\u0007XXXHDPI\u0010\bB\u000f\n\rdensity_oneof\"6\n\nSdkVersion\u0012(\n\u0003min\u0018\u0001 \u0001(\u000b2\u001b", ".google.protobuf.Int32Value\"\u0095\u0001\n\u000bGraphicsApi\u0012<\n\u0013min_open_gl_version\u0018\u0001 \u0001(\u000b2\u001d.android.bundle.OpenGlVersionH\u0000\u0012;\n\u0012min_vulkan_version\u0018\u0002 \u0001(\u000b2\u001d.android.bundle.VulkanVersionH\u0000B\u000b\n\tapi_oneof\"-\n\rVulkanVersion\u0012\r\n\u0005major\u0018\u0001 \u0001(\u0005\u0012\r\n\u0005minor\u0018\u0002 \u0001(\u0005\"-\n\rOpenGlVersion\u0012\r\n\u0005major\u0018\u0001 \u0001(\u0005\u0012\r\n\u0005minor\u0018\u0002 \u0001(\u0005\"\u00b0\u0002\n\u0018TextureCompressionFormat\u0012U\n\u0005alias\u0018\u0001 \u0001(\u000e2F.android.bundle.TextureCompressionFormat.TextureCompressionFormatAlias\"\u00bc\u0001\n\u001dTextur", "eCompressionFormatAlias\u0012*\n&UNSPECIFIED_TEXTURE_COMPRESSION_FORMAT\u0010\u0000\u0012\r\n\tETC1_RGB8\u0010\u0001\u0012\f\n\bPALETTED\u0010\u0002\u0012\f\n\bTHREE_DC\u0010\u0003\u0012\u0007\n\u0003ATC\u0010\u0004\u0012\b\n\u0004LATC\u0010\u0005\u0012\b\n\u0004DXT1\u0010\u0006\u0012\b\n\u0004S3TC\u0010\u0007\u0012\t\n\u0005PVRTC\u0010\b\u0012\b\n\u0004ASTC\u0010\t\u0012\b\n\u0004ETC2\u0010\n\"\u00b9\u0001\n\u0003Abi\u0012+\n\u0005alias\u0018\u0001 \u0001(\u000e2\u001c.android.bundle.Abi.AbiAlias\"\u0084\u0001\n\bAbiAlias\u0012 \n\u001cUNSPECIFIED_CPU_ARCHITECTURE\u0010\u0000\u0012\u000b\n\u0007ARMEABI\u0010\u0001\u0012\u000f\n\u000bARMEABI_V7A\u0010\u0002\u0012\r\n\tARM64_V8A\u0010\u0003\u0012\u0007\n\u0003X86\u0010\u0004\u0012\n\n\u0006X86_64\u0010\u0005\u0012\b\n\u0004MIPS\u0010\u0006\u0012\n\n\u0006MIPS64\u0010\u0007\",\n\bMultiAbi\u0012 \n\u0003abi\u0018\u0001 \u0003(\u000b2\u0013.andr", "oid.bundle.Abi\"o\n\tSanitizer\u00127\n\u0005alias\u0018\u0001 \u0001(\u000e2(.android.bundle.Sanitizer.SanitizerAlias\")\n\u000eSanitizerAlias\u0012\b\n\u0004NONE\u0010\u0000\u0012\r\n\tHWADDRESS\u0010\u0001\">\n\rDeviceFeature\u0012\u0014\n\ffeature_name\u0018\u0001 \u0001(\t\u0012\u0017\n\u000ffeature_version\u0018\u0002 \u0001(\u0005\"\u008d\u0002\n\u0018AssetsDirectoryTargeting\u0012)\n\u0003abi\u0018\u0001 \u0001(\u000b2\u001c.android.bundle.AbiTargeting\u0012:\n\fgraphics_api\u0018\u0002 \u0001(\u000b2$.android.bundle.GraphicsApiTargeting\u0012U\n\u001atexture_compression_format\u0018\u0003 \u0001(\u000b21.android.bundle.TextureCompressionForma", "tTargeting\u00123\n\blanguage\u0018\u0004 \u0001(\u000b2!.android.bundle.LanguageTargeting\"\u00eb\u0001\n\u0018NativeDirectoryTargeting\u0012 \n\u0003abi\u0018\u0001 \u0001(\u000b2\u0013.android.bundle.Abi\u00121\n\fgraphics_api\u0018\u0002 \u0001(\u000b2\u001b.android.bundle.GraphicsApi\u0012L\n\u001atexture_compression_format\u0018\u0003 \u0001(\u000b2(.android.bundle.TextureCompressionFormat\u0012,\n\tsanitizer\u0018\u0004 \u0001(\u000b2\u0019.android.bundle.Sanitizer\"J\n\u0012ApexImageTargeting\u00124\n\tmulti_abi\u0018\u0001 \u0001(\u000b2!.android.bundle.MultiAbiTargeting\"]\n\fAbiTargeting\u0012\"\n\u0005val", "ue\u0018\u0001 \u0003(\u000b2\u0013.android.bundle.Abi\u0012)\n\falternatives\u0018\u0002 \u0003(\u000b2\u0013.android.bundle.Abi\"l\n\u0011MultiAbiTargeting\u0012'\n\u0005value\u0018\u0001 \u0003(\u000b2\u0018.android.bundle.MultiAbi\u0012.\n\falternatives\u0018\u0002 \u0003(\u000b2\u0018.android.bundle.MultiAbi\"{\n\u0016ScreenDensityTargeting\u0012,\n\u0005value\u0018\u0001 \u0003(\u000b2\u001d.android.bundle.ScreenDensity\u00123\n\falternatives\u0018\u0002 \u0003(\u000b2\u001d.android.bundle.ScreenDensity\"8\n\u0011LanguageTargeting\u0012\r\n\u0005value\u0018\u0001 \u0003(\t\u0012\u0014\n\falternatives\u0018\u0002 \u0003(\t\"u\n\u0014GraphicsApiTargeting\u0012*\n\u0005value\u0018\u0001", " \u0003(\u000b2\u001b.android.bundle.GraphicsApi\u00121\n\falternatives\u0018\u0002 \u0003(\u000b2\u001b.android.bundle.GraphicsApi\"r\n\u0013SdkVersionTargeting\u0012)\n\u0005value\u0018\u0001 \u0003(\u000b2\u001a.android.bundle.SdkVersion\u00120\n\falternatives\u0018\u0002 \u0003(\u000b2\u001a.android.bundle.SdkVersion\"\u009c\u0001\n!TextureCompressionFormatTargeting\u00127\n\u0005value\u0018\u0001 \u0003(\u000b2(.android.bundle.TextureCompressionFormat\u0012>\n\falternatives\u0018\u0002 \u0003(\u000b2(.android.bundle.TextureCompressionFormat\">\n\u0012SanitizerTargeting\u0012(\n\u0005value\u0018\u0001 \u0003(\u000b2\u0019.a", "ndroid.bundle.Sanitizer\"Q\n\u0016DeviceFeatureTargeting\u00127\n\u0010required_feature\u0018\u0001 \u0001(\u000b2\u001d.android.bundle.DeviceFeatureB\u0014\n\u0012com.android.bundleb\u0006proto3"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            @Override
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[]{WrappersProto.getDescriptor()}, assigner);
        internal_static_android_bundle_VariantTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(0);
        internal_static_android_bundle_VariantTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_VariantTargeting_descriptor, new String[]{"SdkVersionTargeting", "AbiTargeting", "ScreenDensityTargeting", "MultiAbiTargeting", "TextureCompressionFormatTargeting"});
        internal_static_android_bundle_ApkTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(1);
        internal_static_android_bundle_ApkTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ApkTargeting_descriptor, new String[]{"AbiTargeting", "GraphicsApiTargeting", "LanguageTargeting", "ScreenDensityTargeting", "SdkVersionTargeting", "TextureCompressionFormatTargeting", "MultiAbiTargeting", "SanitizerTargeting"});
        internal_static_android_bundle_ModuleTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(2);
        internal_static_android_bundle_ModuleTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ModuleTargeting_descriptor, new String[]{"SdkVersionTargeting", "DeviceFeatureTargeting", "UserCountriesTargeting"});
        internal_static_android_bundle_UserCountriesTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(3);
        internal_static_android_bundle_UserCountriesTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_UserCountriesTargeting_descriptor, new String[]{"CountryCodes", "Exclude"});
        internal_static_android_bundle_ScreenDensity_descriptor = Targeting.getDescriptor().getMessageTypes().get(4);
        internal_static_android_bundle_ScreenDensity_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ScreenDensity_descriptor, new String[]{"DensityAlias", "DensityDpi", "DensityOneof"});
        internal_static_android_bundle_SdkVersion_descriptor = Targeting.getDescriptor().getMessageTypes().get(5);
        internal_static_android_bundle_SdkVersion_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_SdkVersion_descriptor, new String[]{"Min"});
        internal_static_android_bundle_GraphicsApi_descriptor = Targeting.getDescriptor().getMessageTypes().get(6);
        internal_static_android_bundle_GraphicsApi_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_GraphicsApi_descriptor, new String[]{"MinOpenGlVersion", "MinVulkanVersion", "ApiOneof"});
        internal_static_android_bundle_VulkanVersion_descriptor = Targeting.getDescriptor().getMessageTypes().get(7);
        internal_static_android_bundle_VulkanVersion_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_VulkanVersion_descriptor, new String[]{"Major", "Minor"});
        internal_static_android_bundle_OpenGlVersion_descriptor = Targeting.getDescriptor().getMessageTypes().get(8);
        internal_static_android_bundle_OpenGlVersion_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_OpenGlVersion_descriptor, new String[]{"Major", "Minor"});
        internal_static_android_bundle_TextureCompressionFormat_descriptor = Targeting.getDescriptor().getMessageTypes().get(9);
        internal_static_android_bundle_TextureCompressionFormat_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_TextureCompressionFormat_descriptor, new String[]{"Alias"});
        internal_static_android_bundle_Abi_descriptor = Targeting.getDescriptor().getMessageTypes().get(10);
        internal_static_android_bundle_Abi_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Abi_descriptor, new String[]{"Alias"});
        internal_static_android_bundle_MultiAbi_descriptor = Targeting.getDescriptor().getMessageTypes().get(11);
        internal_static_android_bundle_MultiAbi_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_MultiAbi_descriptor, new String[]{"Abi"});
        internal_static_android_bundle_Sanitizer_descriptor = Targeting.getDescriptor().getMessageTypes().get(12);
        internal_static_android_bundle_Sanitizer_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_Sanitizer_descriptor, new String[]{"Alias"});
        internal_static_android_bundle_DeviceFeature_descriptor = Targeting.getDescriptor().getMessageTypes().get(13);
        internal_static_android_bundle_DeviceFeature_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_DeviceFeature_descriptor, new String[]{"FeatureName", "FeatureVersion"});
        internal_static_android_bundle_AssetsDirectoryTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(14);
        internal_static_android_bundle_AssetsDirectoryTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_AssetsDirectoryTargeting_descriptor, new String[]{"Abi", "GraphicsApi", "TextureCompressionFormat", "Language"});
        internal_static_android_bundle_NativeDirectoryTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(15);
        internal_static_android_bundle_NativeDirectoryTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_NativeDirectoryTargeting_descriptor, new String[]{"Abi", "GraphicsApi", "TextureCompressionFormat", "Sanitizer"});
        internal_static_android_bundle_ApexImageTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(16);
        internal_static_android_bundle_ApexImageTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ApexImageTargeting_descriptor, new String[]{"MultiAbi"});
        internal_static_android_bundle_AbiTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(17);
        internal_static_android_bundle_AbiTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_AbiTargeting_descriptor, new String[]{"Value", "Alternatives"});
        internal_static_android_bundle_MultiAbiTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(18);
        internal_static_android_bundle_MultiAbiTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_MultiAbiTargeting_descriptor, new String[]{"Value", "Alternatives"});
        internal_static_android_bundle_ScreenDensityTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(19);
        internal_static_android_bundle_ScreenDensityTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ScreenDensityTargeting_descriptor, new String[]{"Value", "Alternatives"});
        internal_static_android_bundle_LanguageTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(20);
        internal_static_android_bundle_LanguageTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_LanguageTargeting_descriptor, new String[]{"Value", "Alternatives"});
        internal_static_android_bundle_GraphicsApiTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(21);
        internal_static_android_bundle_GraphicsApiTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_GraphicsApiTargeting_descriptor, new String[]{"Value", "Alternatives"});
        internal_static_android_bundle_SdkVersionTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(22);
        internal_static_android_bundle_SdkVersionTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_SdkVersionTargeting_descriptor, new String[]{"Value", "Alternatives"});
        internal_static_android_bundle_TextureCompressionFormatTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(23);
        internal_static_android_bundle_TextureCompressionFormatTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_TextureCompressionFormatTargeting_descriptor, new String[]{"Value", "Alternatives"});
        internal_static_android_bundle_SanitizerTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(24);
        internal_static_android_bundle_SanitizerTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_SanitizerTargeting_descriptor, new String[]{"Value"});
        internal_static_android_bundle_DeviceFeatureTargeting_descriptor = Targeting.getDescriptor().getMessageTypes().get(25);
        internal_static_android_bundle_DeviceFeatureTargeting_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_DeviceFeatureTargeting_descriptor, new String[]{"RequiredFeature"});
        WrappersProto.getDescriptor();
    }

    public static final class DeviceFeatureTargeting
    extends GeneratedMessageV3
    implements DeviceFeatureTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int REQUIRED_FEATURE_FIELD_NUMBER = 1;
        private DeviceFeature requiredFeature_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final DeviceFeatureTargeting DEFAULT_INSTANCE = new DeviceFeatureTargeting();
        private static final Parser<DeviceFeatureTargeting> PARSER = new AbstractParser<DeviceFeatureTargeting>(){

            @Override
            public DeviceFeatureTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DeviceFeatureTargeting(input, extensionRegistry);
            }
        };

        private DeviceFeatureTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private DeviceFeatureTargeting() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DeviceFeatureTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    DeviceFeature.Builder subBuilder = null;
                    if (this.requiredFeature_ != null) {
                        subBuilder = this.requiredFeature_.toBuilder();
                    }
                    this.requiredFeature_ = input.readMessage(DeviceFeature.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    subBuilder.mergeFrom(this.requiredFeature_);
                    this.requiredFeature_ = subBuilder.buildPartial();
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
            return internal_static_android_bundle_DeviceFeatureTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_DeviceFeatureTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceFeatureTargeting.class, Builder.class);
        }

        @Override
        public boolean hasRequiredFeature() {
            return this.requiredFeature_ != null;
        }

        @Override
        public DeviceFeature getRequiredFeature() {
            return this.requiredFeature_ == null ? DeviceFeature.getDefaultInstance() : this.requiredFeature_;
        }

        @Override
        public DeviceFeatureOrBuilder getRequiredFeatureOrBuilder() {
            return this.getRequiredFeature();
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
            if (this.requiredFeature_ != null) {
                output.writeMessage(1, this.getRequiredFeature());
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
            if (this.requiredFeature_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getRequiredFeature());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DeviceFeatureTargeting)) {
                return super.equals(obj);
            }
            DeviceFeatureTargeting other = (DeviceFeatureTargeting)obj;
            boolean result = true;
            boolean bl = result = result && this.hasRequiredFeature() == other.hasRequiredFeature();
            if (this.hasRequiredFeature()) {
                result = result && this.getRequiredFeature().equals(other.getRequiredFeature());
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
            hash = 19 * hash + DeviceFeatureTargeting.getDescriptor().hashCode();
            if (this.hasRequiredFeature()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getRequiredFeature().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static DeviceFeatureTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeviceFeatureTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceFeatureTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeviceFeatureTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceFeatureTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeviceFeatureTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceFeatureTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceFeatureTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceFeatureTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeviceFeatureTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceFeatureTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceFeatureTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return DeviceFeatureTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeviceFeatureTargeting prototype) {
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

        public static DeviceFeatureTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceFeatureTargeting> parser() {
            return PARSER;
        }

        public Parser<DeviceFeatureTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public DeviceFeatureTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements DeviceFeatureTargetingOrBuilder {
            private DeviceFeature requiredFeature_ = null;
            private SingleFieldBuilderV3<DeviceFeature, DeviceFeature.Builder, DeviceFeatureOrBuilder> requiredFeatureBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_DeviceFeatureTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_DeviceFeatureTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceFeatureTargeting.class, Builder.class);
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
                if (this.requiredFeatureBuilder_ == null) {
                    this.requiredFeature_ = null;
                } else {
                    this.requiredFeature_ = null;
                    this.requiredFeatureBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_DeviceFeatureTargeting_descriptor;
            }

            @Override
            public DeviceFeatureTargeting getDefaultInstanceForType() {
                return DeviceFeatureTargeting.getDefaultInstance();
            }

            @Override
            public DeviceFeatureTargeting build() {
                DeviceFeatureTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public DeviceFeatureTargeting buildPartial() {
                DeviceFeatureTargeting result = new DeviceFeatureTargeting(this);
                if (this.requiredFeatureBuilder_ == null) {
                    result.requiredFeature_ = this.requiredFeature_;
                } else {
                    result.requiredFeature_ = this.requiredFeatureBuilder_.build();
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
                if (other instanceof DeviceFeatureTargeting) {
                    return this.mergeFrom((DeviceFeatureTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeviceFeatureTargeting other) {
                if (other == DeviceFeatureTargeting.getDefaultInstance()) {
                    return this;
                }
                if (other.hasRequiredFeature()) {
                    this.mergeRequiredFeature(other.getRequiredFeature());
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
                DeviceFeatureTargeting parsedMessage = null;
                try {
                    parsedMessage = (DeviceFeatureTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (DeviceFeatureTargeting)e2.getUnfinishedMessage();
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
            public boolean hasRequiredFeature() {
                return this.requiredFeatureBuilder_ != null || this.requiredFeature_ != null;
            }

            @Override
            public DeviceFeature getRequiredFeature() {
                if (this.requiredFeatureBuilder_ == null) {
                    return this.requiredFeature_ == null ? DeviceFeature.getDefaultInstance() : this.requiredFeature_;
                }
                return this.requiredFeatureBuilder_.getMessage();
            }

            public Builder setRequiredFeature(DeviceFeature value) {
                if (this.requiredFeatureBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.requiredFeature_ = value;
                    this.onChanged();
                } else {
                    this.requiredFeatureBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setRequiredFeature(DeviceFeature.Builder builderForValue) {
                if (this.requiredFeatureBuilder_ == null) {
                    this.requiredFeature_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.requiredFeatureBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeRequiredFeature(DeviceFeature value) {
                if (this.requiredFeatureBuilder_ == null) {
                    this.requiredFeature_ = this.requiredFeature_ != null ? DeviceFeature.newBuilder(this.requiredFeature_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.requiredFeatureBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearRequiredFeature() {
                if (this.requiredFeatureBuilder_ == null) {
                    this.requiredFeature_ = null;
                    this.onChanged();
                } else {
                    this.requiredFeature_ = null;
                    this.requiredFeatureBuilder_ = null;
                }
                return this;
            }

            public DeviceFeature.Builder getRequiredFeatureBuilder() {
                this.onChanged();
                return this.getRequiredFeatureFieldBuilder().getBuilder();
            }

            @Override
            public DeviceFeatureOrBuilder getRequiredFeatureOrBuilder() {
                if (this.requiredFeatureBuilder_ != null) {
                    return this.requiredFeatureBuilder_.getMessageOrBuilder();
                }
                return this.requiredFeature_ == null ? DeviceFeature.getDefaultInstance() : this.requiredFeature_;
            }

            private SingleFieldBuilderV3<DeviceFeature, DeviceFeature.Builder, DeviceFeatureOrBuilder> getRequiredFeatureFieldBuilder() {
                if (this.requiredFeatureBuilder_ == null) {
                    this.requiredFeatureBuilder_ = new SingleFieldBuilderV3(this.getRequiredFeature(), this.getParentForChildren(), this.isClean());
                    this.requiredFeature_ = null;
                }
                return this.requiredFeatureBuilder_;
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

    public static interface DeviceFeatureTargetingOrBuilder
    extends MessageOrBuilder {
        public boolean hasRequiredFeature();

        public DeviceFeature getRequiredFeature();

        public DeviceFeatureOrBuilder getRequiredFeatureOrBuilder();
    }

    public static final class SanitizerTargeting
    extends GeneratedMessageV3
    implements SanitizerTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private List<Sanitizer> value_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SanitizerTargeting DEFAULT_INSTANCE = new SanitizerTargeting();
        private static final Parser<SanitizerTargeting> PARSER = new AbstractParser<SanitizerTargeting>(){

            @Override
            public SanitizerTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SanitizerTargeting(input, extensionRegistry);
            }
        };

        private SanitizerTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SanitizerTargeting() {
            this.value_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SanitizerTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.value_ = new ArrayList<Sanitizer>();
                        mutable_bitField0_ |= true;
                    }
                    this.value_.add(input.readMessage(Sanitizer.parser(), extensionRegistry));
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
                    this.value_ = Collections.unmodifiableList(this.value_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_SanitizerTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_SanitizerTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(SanitizerTargeting.class, Builder.class);
        }

        @Override
        public List<Sanitizer> getValueList() {
            return this.value_;
        }

        @Override
        public List<? extends SanitizerOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        @Override
        public int getValueCount() {
            return this.value_.size();
        }

        @Override
        public Sanitizer getValue(int index) {
            return this.value_.get(index);
        }

        @Override
        public SanitizerOrBuilder getValueOrBuilder(int index) {
            return this.value_.get(index);
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
            for (int i2 = 0; i2 < this.value_.size(); ++i2) {
                output.writeMessage(1, this.value_.get(i2));
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
            for (int i2 = 0; i2 < this.value_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.value_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SanitizerTargeting)) {
                return super.equals(obj);
            }
            SanitizerTargeting other = (SanitizerTargeting)obj;
            boolean result = true;
            result = result && this.getValueList().equals(other.getValueList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + SanitizerTargeting.getDescriptor().hashCode();
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static SanitizerTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SanitizerTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SanitizerTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SanitizerTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SanitizerTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SanitizerTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SanitizerTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SanitizerTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SanitizerTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SanitizerTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SanitizerTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SanitizerTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return SanitizerTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SanitizerTargeting prototype) {
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

        public static SanitizerTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SanitizerTargeting> parser() {
            return PARSER;
        }

        public Parser<SanitizerTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public SanitizerTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SanitizerTargetingOrBuilder {
            private int bitField0_;
            private List<Sanitizer> value_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Sanitizer, Sanitizer.Builder, SanitizerOrBuilder> valueBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_SanitizerTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_SanitizerTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(SanitizerTargeting.class, Builder.class);
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
                    this.getValueFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.valueBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_SanitizerTargeting_descriptor;
            }

            @Override
            public SanitizerTargeting getDefaultInstanceForType() {
                return SanitizerTargeting.getDefaultInstance();
            }

            @Override
            public SanitizerTargeting build() {
                SanitizerTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public SanitizerTargeting buildPartial() {
                SanitizerTargeting result = new SanitizerTargeting(this);
                int from_bitField0_ = this.bitField0_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.value_ = this.value_;
                } else {
                    result.value_ = this.valueBuilder_.build();
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
                if (other instanceof SanitizerTargeting) {
                    return this.mergeFrom((SanitizerTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SanitizerTargeting other) {
                if (other == SanitizerTargeting.getDefaultInstance()) {
                    return this;
                }
                if (this.valueBuilder_ == null) {
                    if (!other.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = other.value_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureValueIsMutable();
                            this.value_.addAll(other.value_);
                        }
                        this.onChanged();
                    }
                } else if (!other.value_.isEmpty()) {
                    if (this.valueBuilder_.isEmpty()) {
                        this.valueBuilder_.dispose();
                        this.valueBuilder_ = null;
                        this.value_ = other.value_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.valueBuilder_ = alwaysUseFieldBuilders ? this.getValueFieldBuilder() : null;
                    } else {
                        this.valueBuilder_.addAllMessages(other.value_);
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
                SanitizerTargeting parsedMessage = null;
                try {
                    parsedMessage = (SanitizerTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (SanitizerTargeting)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.value_ = new ArrayList<Sanitizer>(this.value_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<Sanitizer> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList(this.value_);
                }
                return this.valueBuilder_.getMessageList();
            }

            @Override
            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }

            @Override
            public Sanitizer getValue(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessage(index);
            }

            public Builder setValue(int index, Sanitizer value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.set(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setValue(int index, Sanitizer.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addValue(Sanitizer value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addValue(int index, Sanitizer value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addValue(Sanitizer.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addValue(int index, Sanitizer.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllValue(Iterable<? extends Sanitizer> values2) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.value_);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.valueBuilder_.clear();
                }
                return this;
            }

            public Builder removeValue(int index) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.remove(index);
                    this.onChanged();
                } else {
                    this.valueBuilder_.remove(index);
                }
                return this;
            }

            public Sanitizer.Builder getValueBuilder(int index) {
                return this.getValueFieldBuilder().getBuilder(index);
            }

            @Override
            public SanitizerOrBuilder getValueOrBuilder(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends SanitizerOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.value_);
            }

            public Sanitizer.Builder addValueBuilder() {
                return this.getValueFieldBuilder().addBuilder(Sanitizer.getDefaultInstance());
            }

            public Sanitizer.Builder addValueBuilder(int index) {
                return this.getValueFieldBuilder().addBuilder(index, Sanitizer.getDefaultInstance());
            }

            public List<Sanitizer.Builder> getValueBuilderList() {
                return this.getValueFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Sanitizer, Sanitizer.Builder, SanitizerOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new RepeatedFieldBuilderV3(this.value_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.value_ = null;
                }
                return this.valueBuilder_;
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

    public static interface SanitizerTargetingOrBuilder
    extends MessageOrBuilder {
        public List<Sanitizer> getValueList();

        public Sanitizer getValue(int var1);

        public int getValueCount();

        public List<? extends SanitizerOrBuilder> getValueOrBuilderList();

        public SanitizerOrBuilder getValueOrBuilder(int var1);
    }

    public static final class TextureCompressionFormatTargeting
    extends GeneratedMessageV3
    implements TextureCompressionFormatTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private List<TextureCompressionFormat> value_;
        public static final int ALTERNATIVES_FIELD_NUMBER = 2;
        private List<TextureCompressionFormat> alternatives_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final TextureCompressionFormatTargeting DEFAULT_INSTANCE = new TextureCompressionFormatTargeting();
        private static final Parser<TextureCompressionFormatTargeting> PARSER = new AbstractParser<TextureCompressionFormatTargeting>(){

            @Override
            public TextureCompressionFormatTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new TextureCompressionFormatTargeting(input, extensionRegistry);
            }
        };

        private TextureCompressionFormatTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private TextureCompressionFormatTargeting() {
            this.value_ = Collections.emptyList();
            this.alternatives_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private TextureCompressionFormatTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.value_ = new ArrayList<TextureCompressionFormat>();
                                mutable_bitField0_ |= 1;
                            }
                            this.value_.add(input.readMessage(TextureCompressionFormat.parser(), extensionRegistry));
                            continue block11;
                        }
                        case 18: 
                    }
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.alternatives_ = new ArrayList<TextureCompressionFormat>();
                        mutable_bitField0_ |= 2;
                    }
                    this.alternatives_.add(input.readMessage(TextureCompressionFormat.parser(), extensionRegistry));
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
                    this.value_ = Collections.unmodifiableList(this.value_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_TextureCompressionFormatTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_TextureCompressionFormatTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(TextureCompressionFormatTargeting.class, Builder.class);
        }

        @Override
        public List<TextureCompressionFormat> getValueList() {
            return this.value_;
        }

        @Override
        public List<? extends TextureCompressionFormatOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        @Override
        public int getValueCount() {
            return this.value_.size();
        }

        @Override
        public TextureCompressionFormat getValue(int index) {
            return this.value_.get(index);
        }

        @Override
        public TextureCompressionFormatOrBuilder getValueOrBuilder(int index) {
            return this.value_.get(index);
        }

        @Override
        public List<TextureCompressionFormat> getAlternativesList() {
            return this.alternatives_;
        }

        @Override
        public List<? extends TextureCompressionFormatOrBuilder> getAlternativesOrBuilderList() {
            return this.alternatives_;
        }

        @Override
        public int getAlternativesCount() {
            return this.alternatives_.size();
        }

        @Override
        public TextureCompressionFormat getAlternatives(int index) {
            return this.alternatives_.get(index);
        }

        @Override
        public TextureCompressionFormatOrBuilder getAlternativesOrBuilder(int index) {
            return this.alternatives_.get(index);
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                output.writeMessage(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                output.writeMessage(2, this.alternatives_.get(i2));
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.alternatives_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof TextureCompressionFormatTargeting)) {
                return super.equals(obj);
            }
            TextureCompressionFormatTargeting other = (TextureCompressionFormatTargeting)obj;
            boolean result = true;
            result = result && this.getValueList().equals(other.getValueList());
            result = result && this.getAlternativesList().equals(other.getAlternativesList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + TextureCompressionFormatTargeting.getDescriptor().hashCode();
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            if (this.getAlternativesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAlternativesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static TextureCompressionFormatTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TextureCompressionFormatTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TextureCompressionFormatTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TextureCompressionFormatTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TextureCompressionFormatTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TextureCompressionFormatTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TextureCompressionFormatTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static TextureCompressionFormatTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static TextureCompressionFormatTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static TextureCompressionFormatTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static TextureCompressionFormatTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static TextureCompressionFormatTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return TextureCompressionFormatTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(TextureCompressionFormatTargeting prototype) {
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

        public static TextureCompressionFormatTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TextureCompressionFormatTargeting> parser() {
            return PARSER;
        }

        public Parser<TextureCompressionFormatTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public TextureCompressionFormatTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements TextureCompressionFormatTargetingOrBuilder {
            private int bitField0_;
            private List<TextureCompressionFormat> value_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<TextureCompressionFormat, TextureCompressionFormat.Builder, TextureCompressionFormatOrBuilder> valueBuilder_;
            private List<TextureCompressionFormat> alternatives_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<TextureCompressionFormat, TextureCompressionFormat.Builder, TextureCompressionFormatOrBuilder> alternativesBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_TextureCompressionFormatTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_TextureCompressionFormatTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(TextureCompressionFormatTargeting.class, Builder.class);
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
                    this.getValueFieldBuilder();
                    this.getAlternativesFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.valueBuilder_.clear();
                }
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_TextureCompressionFormatTargeting_descriptor;
            }

            @Override
            public TextureCompressionFormatTargeting getDefaultInstanceForType() {
                return TextureCompressionFormatTargeting.getDefaultInstance();
            }

            @Override
            public TextureCompressionFormatTargeting build() {
                TextureCompressionFormatTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public TextureCompressionFormatTargeting buildPartial() {
                TextureCompressionFormatTargeting result = new TextureCompressionFormatTargeting(this);
                int from_bitField0_ = this.bitField0_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.value_ = this.value_;
                } else {
                    result.value_ = this.valueBuilder_.build();
                }
                if (this.alternativesBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.alternatives_ = this.alternatives_;
                } else {
                    result.alternatives_ = this.alternativesBuilder_.build();
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
                if (other instanceof TextureCompressionFormatTargeting) {
                    return this.mergeFrom((TextureCompressionFormatTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(TextureCompressionFormatTargeting other) {
                if (other == TextureCompressionFormatTargeting.getDefaultInstance()) {
                    return this;
                }
                if (this.valueBuilder_ == null) {
                    if (!other.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = other.value_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureValueIsMutable();
                            this.value_.addAll(other.value_);
                        }
                        this.onChanged();
                    }
                } else if (!other.value_.isEmpty()) {
                    if (this.valueBuilder_.isEmpty()) {
                        this.valueBuilder_.dispose();
                        this.valueBuilder_ = null;
                        this.value_ = other.value_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.valueBuilder_ = alwaysUseFieldBuilders ? this.getValueFieldBuilder() : null;
                    } else {
                        this.valueBuilder_.addAllMessages(other.value_);
                    }
                }
                if (this.alternativesBuilder_ == null) {
                    if (!other.alternatives_.isEmpty()) {
                        if (this.alternatives_.isEmpty()) {
                            this.alternatives_ = other.alternatives_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureAlternativesIsMutable();
                            this.alternatives_.addAll(other.alternatives_);
                        }
                        this.onChanged();
                    }
                } else if (!other.alternatives_.isEmpty()) {
                    if (this.alternativesBuilder_.isEmpty()) {
                        this.alternativesBuilder_.dispose();
                        this.alternativesBuilder_ = null;
                        this.alternatives_ = other.alternatives_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.alternativesBuilder_ = alwaysUseFieldBuilders ? this.getAlternativesFieldBuilder() : null;
                    } else {
                        this.alternativesBuilder_.addAllMessages(other.alternatives_);
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
                TextureCompressionFormatTargeting parsedMessage = null;
                try {
                    parsedMessage = (TextureCompressionFormatTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (TextureCompressionFormatTargeting)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.value_ = new ArrayList<TextureCompressionFormat>(this.value_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<TextureCompressionFormat> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList(this.value_);
                }
                return this.valueBuilder_.getMessageList();
            }

            @Override
            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }

            @Override
            public TextureCompressionFormat getValue(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessage(index);
            }

            public Builder setValue(int index, TextureCompressionFormat value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.set(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setValue(int index, TextureCompressionFormat.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addValue(TextureCompressionFormat value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addValue(int index, TextureCompressionFormat value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addValue(TextureCompressionFormat.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addValue(int index, TextureCompressionFormat.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllValue(Iterable<? extends TextureCompressionFormat> values2) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.value_);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.valueBuilder_.clear();
                }
                return this;
            }

            public Builder removeValue(int index) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.remove(index);
                    this.onChanged();
                } else {
                    this.valueBuilder_.remove(index);
                }
                return this;
            }

            public TextureCompressionFormat.Builder getValueBuilder(int index) {
                return this.getValueFieldBuilder().getBuilder(index);
            }

            @Override
            public TextureCompressionFormatOrBuilder getValueOrBuilder(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends TextureCompressionFormatOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.value_);
            }

            public TextureCompressionFormat.Builder addValueBuilder() {
                return this.getValueFieldBuilder().addBuilder(TextureCompressionFormat.getDefaultInstance());
            }

            public TextureCompressionFormat.Builder addValueBuilder(int index) {
                return this.getValueFieldBuilder().addBuilder(index, TextureCompressionFormat.getDefaultInstance());
            }

            public List<TextureCompressionFormat.Builder> getValueBuilderList() {
                return this.getValueFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<TextureCompressionFormat, TextureCompressionFormat.Builder, TextureCompressionFormatOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new RepeatedFieldBuilderV3(this.value_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.value_ = null;
                }
                return this.valueBuilder_;
            }

            private void ensureAlternativesIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.alternatives_ = new ArrayList<TextureCompressionFormat>(this.alternatives_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<TextureCompressionFormat> getAlternativesList() {
                if (this.alternativesBuilder_ == null) {
                    return Collections.unmodifiableList(this.alternatives_);
                }
                return this.alternativesBuilder_.getMessageList();
            }

            @Override
            public int getAlternativesCount() {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.size();
                }
                return this.alternativesBuilder_.getCount();
            }

            @Override
            public TextureCompressionFormat getAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessage(index);
            }

            public Builder setAlternatives(int index, TextureCompressionFormat value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setAlternatives(int index, TextureCompressionFormat.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(TextureCompressionFormat value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addAlternatives(int index, TextureCompressionFormat value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addAlternatives(TextureCompressionFormat.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(int index, TextureCompressionFormat.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAlternatives(Iterable<? extends TextureCompressionFormat> values2) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.alternatives_);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearAlternatives() {
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            public Builder removeAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.remove(index);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.remove(index);
                }
                return this;
            }

            public TextureCompressionFormat.Builder getAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().getBuilder(index);
            }

            @Override
            public TextureCompressionFormatOrBuilder getAlternativesOrBuilder(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends TextureCompressionFormatOrBuilder> getAlternativesOrBuilderList() {
                if (this.alternativesBuilder_ != null) {
                    return this.alternativesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.alternatives_);
            }

            public TextureCompressionFormat.Builder addAlternativesBuilder() {
                return this.getAlternativesFieldBuilder().addBuilder(TextureCompressionFormat.getDefaultInstance());
            }

            public TextureCompressionFormat.Builder addAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().addBuilder(index, TextureCompressionFormat.getDefaultInstance());
            }

            public List<TextureCompressionFormat.Builder> getAlternativesBuilderList() {
                return this.getAlternativesFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<TextureCompressionFormat, TextureCompressionFormat.Builder, TextureCompressionFormatOrBuilder> getAlternativesFieldBuilder() {
                if (this.alternativesBuilder_ == null) {
                    this.alternativesBuilder_ = new RepeatedFieldBuilderV3(this.alternatives_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.alternatives_ = null;
                }
                return this.alternativesBuilder_;
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

    public static interface TextureCompressionFormatTargetingOrBuilder
    extends MessageOrBuilder {
        public List<TextureCompressionFormat> getValueList();

        public TextureCompressionFormat getValue(int var1);

        public int getValueCount();

        public List<? extends TextureCompressionFormatOrBuilder> getValueOrBuilderList();

        public TextureCompressionFormatOrBuilder getValueOrBuilder(int var1);

        public List<TextureCompressionFormat> getAlternativesList();

        public TextureCompressionFormat getAlternatives(int var1);

        public int getAlternativesCount();

        public List<? extends TextureCompressionFormatOrBuilder> getAlternativesOrBuilderList();

        public TextureCompressionFormatOrBuilder getAlternativesOrBuilder(int var1);
    }

    public static final class SdkVersionTargeting
    extends GeneratedMessageV3
    implements SdkVersionTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private List<SdkVersion> value_;
        public static final int ALTERNATIVES_FIELD_NUMBER = 2;
        private List<SdkVersion> alternatives_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SdkVersionTargeting DEFAULT_INSTANCE = new SdkVersionTargeting();
        private static final Parser<SdkVersionTargeting> PARSER = new AbstractParser<SdkVersionTargeting>(){

            @Override
            public SdkVersionTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SdkVersionTargeting(input, extensionRegistry);
            }
        };

        private SdkVersionTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SdkVersionTargeting() {
            this.value_ = Collections.emptyList();
            this.alternatives_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SdkVersionTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.value_ = new ArrayList<SdkVersion>();
                                mutable_bitField0_ |= 1;
                            }
                            this.value_.add(input.readMessage(SdkVersion.parser(), extensionRegistry));
                            continue block11;
                        }
                        case 18: 
                    }
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.alternatives_ = new ArrayList<SdkVersion>();
                        mutable_bitField0_ |= 2;
                    }
                    this.alternatives_.add(input.readMessage(SdkVersion.parser(), extensionRegistry));
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
                    this.value_ = Collections.unmodifiableList(this.value_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_SdkVersionTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_SdkVersionTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(SdkVersionTargeting.class, Builder.class);
        }

        @Override
        public List<SdkVersion> getValueList() {
            return this.value_;
        }

        @Override
        public List<? extends SdkVersionOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        @Override
        public int getValueCount() {
            return this.value_.size();
        }

        @Override
        public SdkVersion getValue(int index) {
            return this.value_.get(index);
        }

        @Override
        public SdkVersionOrBuilder getValueOrBuilder(int index) {
            return this.value_.get(index);
        }

        @Override
        public List<SdkVersion> getAlternativesList() {
            return this.alternatives_;
        }

        @Override
        public List<? extends SdkVersionOrBuilder> getAlternativesOrBuilderList() {
            return this.alternatives_;
        }

        @Override
        public int getAlternativesCount() {
            return this.alternatives_.size();
        }

        @Override
        public SdkVersion getAlternatives(int index) {
            return this.alternatives_.get(index);
        }

        @Override
        public SdkVersionOrBuilder getAlternativesOrBuilder(int index) {
            return this.alternatives_.get(index);
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                output.writeMessage(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                output.writeMessage(2, this.alternatives_.get(i2));
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.alternatives_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SdkVersionTargeting)) {
                return super.equals(obj);
            }
            SdkVersionTargeting other = (SdkVersionTargeting)obj;
            boolean result = true;
            result = result && this.getValueList().equals(other.getValueList());
            result = result && this.getAlternativesList().equals(other.getAlternativesList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + SdkVersionTargeting.getDescriptor().hashCode();
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            if (this.getAlternativesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAlternativesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static SdkVersionTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SdkVersionTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SdkVersionTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SdkVersionTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SdkVersionTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SdkVersionTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SdkVersionTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SdkVersionTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SdkVersionTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SdkVersionTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SdkVersionTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SdkVersionTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return SdkVersionTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SdkVersionTargeting prototype) {
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

        public static SdkVersionTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SdkVersionTargeting> parser() {
            return PARSER;
        }

        public Parser<SdkVersionTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public SdkVersionTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SdkVersionTargetingOrBuilder {
            private int bitField0_;
            private List<SdkVersion> value_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<SdkVersion, SdkVersion.Builder, SdkVersionOrBuilder> valueBuilder_;
            private List<SdkVersion> alternatives_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<SdkVersion, SdkVersion.Builder, SdkVersionOrBuilder> alternativesBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_SdkVersionTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_SdkVersionTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(SdkVersionTargeting.class, Builder.class);
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
                    this.getValueFieldBuilder();
                    this.getAlternativesFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.valueBuilder_.clear();
                }
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_SdkVersionTargeting_descriptor;
            }

            @Override
            public SdkVersionTargeting getDefaultInstanceForType() {
                return SdkVersionTargeting.getDefaultInstance();
            }

            @Override
            public SdkVersionTargeting build() {
                SdkVersionTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public SdkVersionTargeting buildPartial() {
                SdkVersionTargeting result = new SdkVersionTargeting(this);
                int from_bitField0_ = this.bitField0_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.value_ = this.value_;
                } else {
                    result.value_ = this.valueBuilder_.build();
                }
                if (this.alternativesBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.alternatives_ = this.alternatives_;
                } else {
                    result.alternatives_ = this.alternativesBuilder_.build();
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
                if (other instanceof SdkVersionTargeting) {
                    return this.mergeFrom((SdkVersionTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SdkVersionTargeting other) {
                if (other == SdkVersionTargeting.getDefaultInstance()) {
                    return this;
                }
                if (this.valueBuilder_ == null) {
                    if (!other.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = other.value_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureValueIsMutable();
                            this.value_.addAll(other.value_);
                        }
                        this.onChanged();
                    }
                } else if (!other.value_.isEmpty()) {
                    if (this.valueBuilder_.isEmpty()) {
                        this.valueBuilder_.dispose();
                        this.valueBuilder_ = null;
                        this.value_ = other.value_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.valueBuilder_ = alwaysUseFieldBuilders ? this.getValueFieldBuilder() : null;
                    } else {
                        this.valueBuilder_.addAllMessages(other.value_);
                    }
                }
                if (this.alternativesBuilder_ == null) {
                    if (!other.alternatives_.isEmpty()) {
                        if (this.alternatives_.isEmpty()) {
                            this.alternatives_ = other.alternatives_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureAlternativesIsMutable();
                            this.alternatives_.addAll(other.alternatives_);
                        }
                        this.onChanged();
                    }
                } else if (!other.alternatives_.isEmpty()) {
                    if (this.alternativesBuilder_.isEmpty()) {
                        this.alternativesBuilder_.dispose();
                        this.alternativesBuilder_ = null;
                        this.alternatives_ = other.alternatives_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.alternativesBuilder_ = alwaysUseFieldBuilders ? this.getAlternativesFieldBuilder() : null;
                    } else {
                        this.alternativesBuilder_.addAllMessages(other.alternatives_);
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
                SdkVersionTargeting parsedMessage = null;
                try {
                    parsedMessage = (SdkVersionTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (SdkVersionTargeting)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.value_ = new ArrayList<SdkVersion>(this.value_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<SdkVersion> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList(this.value_);
                }
                return this.valueBuilder_.getMessageList();
            }

            @Override
            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }

            @Override
            public SdkVersion getValue(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessage(index);
            }

            public Builder setValue(int index, SdkVersion value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.set(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setValue(int index, SdkVersion.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addValue(SdkVersion value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addValue(int index, SdkVersion value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addValue(SdkVersion.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addValue(int index, SdkVersion.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllValue(Iterable<? extends SdkVersion> values2) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.value_);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.valueBuilder_.clear();
                }
                return this;
            }

            public Builder removeValue(int index) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.remove(index);
                    this.onChanged();
                } else {
                    this.valueBuilder_.remove(index);
                }
                return this;
            }

            public SdkVersion.Builder getValueBuilder(int index) {
                return this.getValueFieldBuilder().getBuilder(index);
            }

            @Override
            public SdkVersionOrBuilder getValueOrBuilder(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends SdkVersionOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.value_);
            }

            public SdkVersion.Builder addValueBuilder() {
                return this.getValueFieldBuilder().addBuilder(SdkVersion.getDefaultInstance());
            }

            public SdkVersion.Builder addValueBuilder(int index) {
                return this.getValueFieldBuilder().addBuilder(index, SdkVersion.getDefaultInstance());
            }

            public List<SdkVersion.Builder> getValueBuilderList() {
                return this.getValueFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<SdkVersion, SdkVersion.Builder, SdkVersionOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new RepeatedFieldBuilderV3(this.value_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.value_ = null;
                }
                return this.valueBuilder_;
            }

            private void ensureAlternativesIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.alternatives_ = new ArrayList<SdkVersion>(this.alternatives_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<SdkVersion> getAlternativesList() {
                if (this.alternativesBuilder_ == null) {
                    return Collections.unmodifiableList(this.alternatives_);
                }
                return this.alternativesBuilder_.getMessageList();
            }

            @Override
            public int getAlternativesCount() {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.size();
                }
                return this.alternativesBuilder_.getCount();
            }

            @Override
            public SdkVersion getAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessage(index);
            }

            public Builder setAlternatives(int index, SdkVersion value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setAlternatives(int index, SdkVersion.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(SdkVersion value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addAlternatives(int index, SdkVersion value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addAlternatives(SdkVersion.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(int index, SdkVersion.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAlternatives(Iterable<? extends SdkVersion> values2) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.alternatives_);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearAlternatives() {
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            public Builder removeAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.remove(index);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.remove(index);
                }
                return this;
            }

            public SdkVersion.Builder getAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().getBuilder(index);
            }

            @Override
            public SdkVersionOrBuilder getAlternativesOrBuilder(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends SdkVersionOrBuilder> getAlternativesOrBuilderList() {
                if (this.alternativesBuilder_ != null) {
                    return this.alternativesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.alternatives_);
            }

            public SdkVersion.Builder addAlternativesBuilder() {
                return this.getAlternativesFieldBuilder().addBuilder(SdkVersion.getDefaultInstance());
            }

            public SdkVersion.Builder addAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().addBuilder(index, SdkVersion.getDefaultInstance());
            }

            public List<SdkVersion.Builder> getAlternativesBuilderList() {
                return this.getAlternativesFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<SdkVersion, SdkVersion.Builder, SdkVersionOrBuilder> getAlternativesFieldBuilder() {
                if (this.alternativesBuilder_ == null) {
                    this.alternativesBuilder_ = new RepeatedFieldBuilderV3(this.alternatives_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.alternatives_ = null;
                }
                return this.alternativesBuilder_;
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

    public static interface SdkVersionTargetingOrBuilder
    extends MessageOrBuilder {
        public List<SdkVersion> getValueList();

        public SdkVersion getValue(int var1);

        public int getValueCount();

        public List<? extends SdkVersionOrBuilder> getValueOrBuilderList();

        public SdkVersionOrBuilder getValueOrBuilder(int var1);

        public List<SdkVersion> getAlternativesList();

        public SdkVersion getAlternatives(int var1);

        public int getAlternativesCount();

        public List<? extends SdkVersionOrBuilder> getAlternativesOrBuilderList();

        public SdkVersionOrBuilder getAlternativesOrBuilder(int var1);
    }

    public static final class GraphicsApiTargeting
    extends GeneratedMessageV3
    implements GraphicsApiTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private List<GraphicsApi> value_;
        public static final int ALTERNATIVES_FIELD_NUMBER = 2;
        private List<GraphicsApi> alternatives_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final GraphicsApiTargeting DEFAULT_INSTANCE = new GraphicsApiTargeting();
        private static final Parser<GraphicsApiTargeting> PARSER = new AbstractParser<GraphicsApiTargeting>(){

            @Override
            public GraphicsApiTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new GraphicsApiTargeting(input, extensionRegistry);
            }
        };

        private GraphicsApiTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private GraphicsApiTargeting() {
            this.value_ = Collections.emptyList();
            this.alternatives_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private GraphicsApiTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.value_ = new ArrayList<GraphicsApi>();
                                mutable_bitField0_ |= 1;
                            }
                            this.value_.add(input.readMessage(GraphicsApi.parser(), extensionRegistry));
                            continue block11;
                        }
                        case 18: 
                    }
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.alternatives_ = new ArrayList<GraphicsApi>();
                        mutable_bitField0_ |= 2;
                    }
                    this.alternatives_.add(input.readMessage(GraphicsApi.parser(), extensionRegistry));
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
                    this.value_ = Collections.unmodifiableList(this.value_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_GraphicsApiTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_GraphicsApiTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(GraphicsApiTargeting.class, Builder.class);
        }

        @Override
        public List<GraphicsApi> getValueList() {
            return this.value_;
        }

        @Override
        public List<? extends GraphicsApiOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        @Override
        public int getValueCount() {
            return this.value_.size();
        }

        @Override
        public GraphicsApi getValue(int index) {
            return this.value_.get(index);
        }

        @Override
        public GraphicsApiOrBuilder getValueOrBuilder(int index) {
            return this.value_.get(index);
        }

        @Override
        public List<GraphicsApi> getAlternativesList() {
            return this.alternatives_;
        }

        @Override
        public List<? extends GraphicsApiOrBuilder> getAlternativesOrBuilderList() {
            return this.alternatives_;
        }

        @Override
        public int getAlternativesCount() {
            return this.alternatives_.size();
        }

        @Override
        public GraphicsApi getAlternatives(int index) {
            return this.alternatives_.get(index);
        }

        @Override
        public GraphicsApiOrBuilder getAlternativesOrBuilder(int index) {
            return this.alternatives_.get(index);
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                output.writeMessage(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                output.writeMessage(2, this.alternatives_.get(i2));
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.alternatives_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GraphicsApiTargeting)) {
                return super.equals(obj);
            }
            GraphicsApiTargeting other = (GraphicsApiTargeting)obj;
            boolean result = true;
            result = result && this.getValueList().equals(other.getValueList());
            result = result && this.getAlternativesList().equals(other.getAlternativesList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + GraphicsApiTargeting.getDescriptor().hashCode();
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            if (this.getAlternativesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAlternativesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static GraphicsApiTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GraphicsApiTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GraphicsApiTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GraphicsApiTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GraphicsApiTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GraphicsApiTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GraphicsApiTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GraphicsApiTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static GraphicsApiTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static GraphicsApiTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static GraphicsApiTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GraphicsApiTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return GraphicsApiTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(GraphicsApiTargeting prototype) {
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

        public static GraphicsApiTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GraphicsApiTargeting> parser() {
            return PARSER;
        }

        public Parser<GraphicsApiTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public GraphicsApiTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements GraphicsApiTargetingOrBuilder {
            private int bitField0_;
            private List<GraphicsApi> value_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<GraphicsApi, GraphicsApi.Builder, GraphicsApiOrBuilder> valueBuilder_;
            private List<GraphicsApi> alternatives_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<GraphicsApi, GraphicsApi.Builder, GraphicsApiOrBuilder> alternativesBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_GraphicsApiTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_GraphicsApiTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(GraphicsApiTargeting.class, Builder.class);
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
                    this.getValueFieldBuilder();
                    this.getAlternativesFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.valueBuilder_.clear();
                }
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_GraphicsApiTargeting_descriptor;
            }

            @Override
            public GraphicsApiTargeting getDefaultInstanceForType() {
                return GraphicsApiTargeting.getDefaultInstance();
            }

            @Override
            public GraphicsApiTargeting build() {
                GraphicsApiTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public GraphicsApiTargeting buildPartial() {
                GraphicsApiTargeting result = new GraphicsApiTargeting(this);
                int from_bitField0_ = this.bitField0_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.value_ = this.value_;
                } else {
                    result.value_ = this.valueBuilder_.build();
                }
                if (this.alternativesBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.alternatives_ = this.alternatives_;
                } else {
                    result.alternatives_ = this.alternativesBuilder_.build();
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
                if (other instanceof GraphicsApiTargeting) {
                    return this.mergeFrom((GraphicsApiTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(GraphicsApiTargeting other) {
                if (other == GraphicsApiTargeting.getDefaultInstance()) {
                    return this;
                }
                if (this.valueBuilder_ == null) {
                    if (!other.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = other.value_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureValueIsMutable();
                            this.value_.addAll(other.value_);
                        }
                        this.onChanged();
                    }
                } else if (!other.value_.isEmpty()) {
                    if (this.valueBuilder_.isEmpty()) {
                        this.valueBuilder_.dispose();
                        this.valueBuilder_ = null;
                        this.value_ = other.value_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.valueBuilder_ = alwaysUseFieldBuilders ? this.getValueFieldBuilder() : null;
                    } else {
                        this.valueBuilder_.addAllMessages(other.value_);
                    }
                }
                if (this.alternativesBuilder_ == null) {
                    if (!other.alternatives_.isEmpty()) {
                        if (this.alternatives_.isEmpty()) {
                            this.alternatives_ = other.alternatives_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureAlternativesIsMutable();
                            this.alternatives_.addAll(other.alternatives_);
                        }
                        this.onChanged();
                    }
                } else if (!other.alternatives_.isEmpty()) {
                    if (this.alternativesBuilder_.isEmpty()) {
                        this.alternativesBuilder_.dispose();
                        this.alternativesBuilder_ = null;
                        this.alternatives_ = other.alternatives_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.alternativesBuilder_ = alwaysUseFieldBuilders ? this.getAlternativesFieldBuilder() : null;
                    } else {
                        this.alternativesBuilder_.addAllMessages(other.alternatives_);
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
                GraphicsApiTargeting parsedMessage = null;
                try {
                    parsedMessage = (GraphicsApiTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (GraphicsApiTargeting)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.value_ = new ArrayList<GraphicsApi>(this.value_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<GraphicsApi> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList(this.value_);
                }
                return this.valueBuilder_.getMessageList();
            }

            @Override
            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }

            @Override
            public GraphicsApi getValue(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessage(index);
            }

            public Builder setValue(int index, GraphicsApi value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.set(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setValue(int index, GraphicsApi.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addValue(GraphicsApi value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addValue(int index, GraphicsApi value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addValue(GraphicsApi.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addValue(int index, GraphicsApi.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllValue(Iterable<? extends GraphicsApi> values2) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.value_);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.valueBuilder_.clear();
                }
                return this;
            }

            public Builder removeValue(int index) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.remove(index);
                    this.onChanged();
                } else {
                    this.valueBuilder_.remove(index);
                }
                return this;
            }

            public GraphicsApi.Builder getValueBuilder(int index) {
                return this.getValueFieldBuilder().getBuilder(index);
            }

            @Override
            public GraphicsApiOrBuilder getValueOrBuilder(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends GraphicsApiOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.value_);
            }

            public GraphicsApi.Builder addValueBuilder() {
                return this.getValueFieldBuilder().addBuilder(GraphicsApi.getDefaultInstance());
            }

            public GraphicsApi.Builder addValueBuilder(int index) {
                return this.getValueFieldBuilder().addBuilder(index, GraphicsApi.getDefaultInstance());
            }

            public List<GraphicsApi.Builder> getValueBuilderList() {
                return this.getValueFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<GraphicsApi, GraphicsApi.Builder, GraphicsApiOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new RepeatedFieldBuilderV3(this.value_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.value_ = null;
                }
                return this.valueBuilder_;
            }

            private void ensureAlternativesIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.alternatives_ = new ArrayList<GraphicsApi>(this.alternatives_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<GraphicsApi> getAlternativesList() {
                if (this.alternativesBuilder_ == null) {
                    return Collections.unmodifiableList(this.alternatives_);
                }
                return this.alternativesBuilder_.getMessageList();
            }

            @Override
            public int getAlternativesCount() {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.size();
                }
                return this.alternativesBuilder_.getCount();
            }

            @Override
            public GraphicsApi getAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessage(index);
            }

            public Builder setAlternatives(int index, GraphicsApi value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setAlternatives(int index, GraphicsApi.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(GraphicsApi value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addAlternatives(int index, GraphicsApi value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addAlternatives(GraphicsApi.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(int index, GraphicsApi.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAlternatives(Iterable<? extends GraphicsApi> values2) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.alternatives_);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearAlternatives() {
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            public Builder removeAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.remove(index);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.remove(index);
                }
                return this;
            }

            public GraphicsApi.Builder getAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().getBuilder(index);
            }

            @Override
            public GraphicsApiOrBuilder getAlternativesOrBuilder(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends GraphicsApiOrBuilder> getAlternativesOrBuilderList() {
                if (this.alternativesBuilder_ != null) {
                    return this.alternativesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.alternatives_);
            }

            public GraphicsApi.Builder addAlternativesBuilder() {
                return this.getAlternativesFieldBuilder().addBuilder(GraphicsApi.getDefaultInstance());
            }

            public GraphicsApi.Builder addAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().addBuilder(index, GraphicsApi.getDefaultInstance());
            }

            public List<GraphicsApi.Builder> getAlternativesBuilderList() {
                return this.getAlternativesFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<GraphicsApi, GraphicsApi.Builder, GraphicsApiOrBuilder> getAlternativesFieldBuilder() {
                if (this.alternativesBuilder_ == null) {
                    this.alternativesBuilder_ = new RepeatedFieldBuilderV3(this.alternatives_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.alternatives_ = null;
                }
                return this.alternativesBuilder_;
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

    public static interface GraphicsApiTargetingOrBuilder
    extends MessageOrBuilder {
        public List<GraphicsApi> getValueList();

        public GraphicsApi getValue(int var1);

        public int getValueCount();

        public List<? extends GraphicsApiOrBuilder> getValueOrBuilderList();

        public GraphicsApiOrBuilder getValueOrBuilder(int var1);

        public List<GraphicsApi> getAlternativesList();

        public GraphicsApi getAlternatives(int var1);

        public int getAlternativesCount();

        public List<? extends GraphicsApiOrBuilder> getAlternativesOrBuilderList();

        public GraphicsApiOrBuilder getAlternativesOrBuilder(int var1);
    }

    public static final class LanguageTargeting
    extends GeneratedMessageV3
    implements LanguageTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private LazyStringList value_;
        public static final int ALTERNATIVES_FIELD_NUMBER = 2;
        private LazyStringList alternatives_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final LanguageTargeting DEFAULT_INSTANCE = new LanguageTargeting();
        private static final Parser<LanguageTargeting> PARSER = new AbstractParser<LanguageTargeting>(){

            @Override
            public LanguageTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new LanguageTargeting(input, extensionRegistry);
            }
        };

        private LanguageTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private LanguageTargeting() {
            this.value_ = LazyStringArrayList.EMPTY;
            this.alternatives_ = LazyStringArrayList.EMPTY;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private LanguageTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
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
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.value_ = new LazyStringArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.value_.add(s3);
                            continue block11;
                        }
                        case 18: 
                    }
                    s3 = input.readStringRequireUtf8();
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.alternatives_ = new LazyStringArrayList();
                        mutable_bitField0_ |= 2;
                    }
                    this.alternatives_.add(s3);
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
                    this.value_ = this.value_.getUnmodifiableView();
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.alternatives_ = this.alternatives_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_LanguageTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_LanguageTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(LanguageTargeting.class, Builder.class);
        }

        public ProtocolStringList getValueList() {
            return this.value_;
        }

        @Override
        public int getValueCount() {
            return this.value_.size();
        }

        @Override
        public String getValue(int index) {
            return (String)this.value_.get(index);
        }

        @Override
        public ByteString getValueBytes(int index) {
            return this.value_.getByteString(index);
        }

        public ProtocolStringList getAlternativesList() {
            return this.alternatives_;
        }

        @Override
        public int getAlternativesCount() {
            return this.alternatives_.size();
        }

        @Override
        public String getAlternatives(int index) {
            return (String)this.alternatives_.get(index);
        }

        @Override
        public ByteString getAlternativesBytes(int index) {
            return this.alternatives_.getByteString(index);
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 1, this.value_.getRaw(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 2, this.alternatives_.getRaw(i2));
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                dataSize += LanguageTargeting.computeStringSizeNoTag(this.value_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getValueList().size();
            dataSize = 0;
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                dataSize += LanguageTargeting.computeStringSizeNoTag(this.alternatives_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getAlternativesList().size();
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LanguageTargeting)) {
                return super.equals(obj);
            }
            LanguageTargeting other = (LanguageTargeting)obj;
            boolean result = true;
            result = result && this.getValueList().equals(other.getValueList());
            result = result && this.getAlternativesList().equals(other.getAlternativesList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + LanguageTargeting.getDescriptor().hashCode();
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            if (this.getAlternativesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAlternativesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static LanguageTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LanguageTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LanguageTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LanguageTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LanguageTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static LanguageTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static LanguageTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static LanguageTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static LanguageTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static LanguageTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static LanguageTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static LanguageTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return LanguageTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(LanguageTargeting prototype) {
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

        public static LanguageTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LanguageTargeting> parser() {
            return PARSER;
        }

        public Parser<LanguageTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public LanguageTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements LanguageTargetingOrBuilder {
            private int bitField0_;
            private LazyStringList value_ = LazyStringArrayList.EMPTY;
            private LazyStringList alternatives_ = LazyStringArrayList.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_LanguageTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_LanguageTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(LanguageTargeting.class, Builder.class);
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
                this.value_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.alternatives_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_LanguageTargeting_descriptor;
            }

            @Override
            public LanguageTargeting getDefaultInstanceForType() {
                return LanguageTargeting.getDefaultInstance();
            }

            @Override
            public LanguageTargeting build() {
                LanguageTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public LanguageTargeting buildPartial() {
                LanguageTargeting result = new LanguageTargeting(this);
                int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.value_ = this.value_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.value_ = this.value_;
                if ((this.bitField0_ & 2) == 2) {
                    this.alternatives_ = this.alternatives_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.alternatives_ = this.alternatives_;
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
                if (other instanceof LanguageTargeting) {
                    return this.mergeFrom((LanguageTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(LanguageTargeting other) {
                if (other == LanguageTargeting.getDefaultInstance()) {
                    return this;
                }
                if (!other.value_.isEmpty()) {
                    if (this.value_.isEmpty()) {
                        this.value_ = other.value_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureValueIsMutable();
                        this.value_.addAll(other.value_);
                    }
                    this.onChanged();
                }
                if (!other.alternatives_.isEmpty()) {
                    if (this.alternatives_.isEmpty()) {
                        this.alternatives_ = other.alternatives_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureAlternativesIsMutable();
                        this.alternatives_.addAll(other.alternatives_);
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
                LanguageTargeting parsedMessage = null;
                try {
                    parsedMessage = (LanguageTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (LanguageTargeting)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.value_ = new LazyStringArrayList(this.value_);
                    this.bitField0_ |= 1;
                }
            }

            public ProtocolStringList getValueList() {
                return this.value_.getUnmodifiableView();
            }

            @Override
            public int getValueCount() {
                return this.value_.size();
            }

            @Override
            public String getValue(int index) {
                return (String)this.value_.get(index);
            }

            @Override
            public ByteString getValueBytes(int index) {
                return this.value_.getByteString(index);
            }

            public Builder setValue(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureValueIsMutable();
                this.value_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addValue(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureValueIsMutable();
                this.value_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllValue(Iterable<String> values2) {
                this.ensureValueIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.value_);
                this.onChanged();
                return this;
            }

            public Builder clearValue() {
                this.value_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder addValueBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                LanguageTargeting.checkByteStringIsUtf8(value);
                this.ensureValueIsMutable();
                this.value_.add(value);
                this.onChanged();
                return this;
            }

            private void ensureAlternativesIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.alternatives_ = new LazyStringArrayList(this.alternatives_);
                    this.bitField0_ |= 2;
                }
            }

            public ProtocolStringList getAlternativesList() {
                return this.alternatives_.getUnmodifiableView();
            }

            @Override
            public int getAlternativesCount() {
                return this.alternatives_.size();
            }

            @Override
            public String getAlternatives(int index) {
                return (String)this.alternatives_.get(index);
            }

            @Override
            public ByteString getAlternativesBytes(int index) {
                return this.alternatives_.getByteString(index);
            }

            public Builder setAlternatives(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAlternativesIsMutable();
                this.alternatives_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addAlternatives(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAlternativesIsMutable();
                this.alternatives_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllAlternatives(Iterable<String> values2) {
                this.ensureAlternativesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.alternatives_);
                this.onChanged();
                return this;
            }

            public Builder clearAlternatives() {
                this.alternatives_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder addAlternativesBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                LanguageTargeting.checkByteStringIsUtf8(value);
                this.ensureAlternativesIsMutable();
                this.alternatives_.add(value);
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

    public static interface LanguageTargetingOrBuilder
    extends MessageOrBuilder {
        public List<String> getValueList();

        public int getValueCount();

        public String getValue(int var1);

        public ByteString getValueBytes(int var1);

        public List<String> getAlternativesList();

        public int getAlternativesCount();

        public String getAlternatives(int var1);

        public ByteString getAlternativesBytes(int var1);
    }

    public static final class ScreenDensityTargeting
    extends GeneratedMessageV3
    implements ScreenDensityTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private List<ScreenDensity> value_;
        public static final int ALTERNATIVES_FIELD_NUMBER = 2;
        private List<ScreenDensity> alternatives_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ScreenDensityTargeting DEFAULT_INSTANCE = new ScreenDensityTargeting();
        private static final Parser<ScreenDensityTargeting> PARSER = new AbstractParser<ScreenDensityTargeting>(){

            @Override
            public ScreenDensityTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ScreenDensityTargeting(input, extensionRegistry);
            }
        };

        private ScreenDensityTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ScreenDensityTargeting() {
            this.value_ = Collections.emptyList();
            this.alternatives_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ScreenDensityTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.value_ = new ArrayList<ScreenDensity>();
                                mutable_bitField0_ |= 1;
                            }
                            this.value_.add(input.readMessage(ScreenDensity.parser(), extensionRegistry));
                            continue block11;
                        }
                        case 18: 
                    }
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.alternatives_ = new ArrayList<ScreenDensity>();
                        mutable_bitField0_ |= 2;
                    }
                    this.alternatives_.add(input.readMessage(ScreenDensity.parser(), extensionRegistry));
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
                    this.value_ = Collections.unmodifiableList(this.value_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_ScreenDensityTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ScreenDensityTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(ScreenDensityTargeting.class, Builder.class);
        }

        @Override
        public List<ScreenDensity> getValueList() {
            return this.value_;
        }

        @Override
        public List<? extends ScreenDensityOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        @Override
        public int getValueCount() {
            return this.value_.size();
        }

        @Override
        public ScreenDensity getValue(int index) {
            return this.value_.get(index);
        }

        @Override
        public ScreenDensityOrBuilder getValueOrBuilder(int index) {
            return this.value_.get(index);
        }

        @Override
        public List<ScreenDensity> getAlternativesList() {
            return this.alternatives_;
        }

        @Override
        public List<? extends ScreenDensityOrBuilder> getAlternativesOrBuilderList() {
            return this.alternatives_;
        }

        @Override
        public int getAlternativesCount() {
            return this.alternatives_.size();
        }

        @Override
        public ScreenDensity getAlternatives(int index) {
            return this.alternatives_.get(index);
        }

        @Override
        public ScreenDensityOrBuilder getAlternativesOrBuilder(int index) {
            return this.alternatives_.get(index);
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                output.writeMessage(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                output.writeMessage(2, this.alternatives_.get(i2));
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.alternatives_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ScreenDensityTargeting)) {
                return super.equals(obj);
            }
            ScreenDensityTargeting other = (ScreenDensityTargeting)obj;
            boolean result = true;
            result = result && this.getValueList().equals(other.getValueList());
            result = result && this.getAlternativesList().equals(other.getAlternativesList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ScreenDensityTargeting.getDescriptor().hashCode();
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            if (this.getAlternativesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAlternativesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ScreenDensityTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ScreenDensityTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ScreenDensityTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ScreenDensityTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ScreenDensityTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ScreenDensityTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ScreenDensityTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ScreenDensityTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ScreenDensityTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ScreenDensityTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ScreenDensityTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ScreenDensityTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ScreenDensityTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ScreenDensityTargeting prototype) {
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

        public static ScreenDensityTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ScreenDensityTargeting> parser() {
            return PARSER;
        }

        public Parser<ScreenDensityTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public ScreenDensityTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ScreenDensityTargetingOrBuilder {
            private int bitField0_;
            private List<ScreenDensity> value_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<ScreenDensity, ScreenDensity.Builder, ScreenDensityOrBuilder> valueBuilder_;
            private List<ScreenDensity> alternatives_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<ScreenDensity, ScreenDensity.Builder, ScreenDensityOrBuilder> alternativesBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ScreenDensityTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ScreenDensityTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(ScreenDensityTargeting.class, Builder.class);
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
                    this.getValueFieldBuilder();
                    this.getAlternativesFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.valueBuilder_.clear();
                }
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ScreenDensityTargeting_descriptor;
            }

            @Override
            public ScreenDensityTargeting getDefaultInstanceForType() {
                return ScreenDensityTargeting.getDefaultInstance();
            }

            @Override
            public ScreenDensityTargeting build() {
                ScreenDensityTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ScreenDensityTargeting buildPartial() {
                ScreenDensityTargeting result = new ScreenDensityTargeting(this);
                int from_bitField0_ = this.bitField0_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.value_ = this.value_;
                } else {
                    result.value_ = this.valueBuilder_.build();
                }
                if (this.alternativesBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.alternatives_ = this.alternatives_;
                } else {
                    result.alternatives_ = this.alternativesBuilder_.build();
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
                if (other instanceof ScreenDensityTargeting) {
                    return this.mergeFrom((ScreenDensityTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ScreenDensityTargeting other) {
                if (other == ScreenDensityTargeting.getDefaultInstance()) {
                    return this;
                }
                if (this.valueBuilder_ == null) {
                    if (!other.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = other.value_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureValueIsMutable();
                            this.value_.addAll(other.value_);
                        }
                        this.onChanged();
                    }
                } else if (!other.value_.isEmpty()) {
                    if (this.valueBuilder_.isEmpty()) {
                        this.valueBuilder_.dispose();
                        this.valueBuilder_ = null;
                        this.value_ = other.value_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.valueBuilder_ = alwaysUseFieldBuilders ? this.getValueFieldBuilder() : null;
                    } else {
                        this.valueBuilder_.addAllMessages(other.value_);
                    }
                }
                if (this.alternativesBuilder_ == null) {
                    if (!other.alternatives_.isEmpty()) {
                        if (this.alternatives_.isEmpty()) {
                            this.alternatives_ = other.alternatives_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureAlternativesIsMutable();
                            this.alternatives_.addAll(other.alternatives_);
                        }
                        this.onChanged();
                    }
                } else if (!other.alternatives_.isEmpty()) {
                    if (this.alternativesBuilder_.isEmpty()) {
                        this.alternativesBuilder_.dispose();
                        this.alternativesBuilder_ = null;
                        this.alternatives_ = other.alternatives_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.alternativesBuilder_ = alwaysUseFieldBuilders ? this.getAlternativesFieldBuilder() : null;
                    } else {
                        this.alternativesBuilder_.addAllMessages(other.alternatives_);
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
                ScreenDensityTargeting parsedMessage = null;
                try {
                    parsedMessage = (ScreenDensityTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ScreenDensityTargeting)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.value_ = new ArrayList<ScreenDensity>(this.value_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<ScreenDensity> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList(this.value_);
                }
                return this.valueBuilder_.getMessageList();
            }

            @Override
            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }

            @Override
            public ScreenDensity getValue(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessage(index);
            }

            public Builder setValue(int index, ScreenDensity value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.set(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setValue(int index, ScreenDensity.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addValue(ScreenDensity value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addValue(int index, ScreenDensity value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addValue(ScreenDensity.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addValue(int index, ScreenDensity.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllValue(Iterable<? extends ScreenDensity> values2) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.value_);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.valueBuilder_.clear();
                }
                return this;
            }

            public Builder removeValue(int index) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.remove(index);
                    this.onChanged();
                } else {
                    this.valueBuilder_.remove(index);
                }
                return this;
            }

            public ScreenDensity.Builder getValueBuilder(int index) {
                return this.getValueFieldBuilder().getBuilder(index);
            }

            @Override
            public ScreenDensityOrBuilder getValueOrBuilder(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends ScreenDensityOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.value_);
            }

            public ScreenDensity.Builder addValueBuilder() {
                return this.getValueFieldBuilder().addBuilder(ScreenDensity.getDefaultInstance());
            }

            public ScreenDensity.Builder addValueBuilder(int index) {
                return this.getValueFieldBuilder().addBuilder(index, ScreenDensity.getDefaultInstance());
            }

            public List<ScreenDensity.Builder> getValueBuilderList() {
                return this.getValueFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<ScreenDensity, ScreenDensity.Builder, ScreenDensityOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new RepeatedFieldBuilderV3(this.value_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.value_ = null;
                }
                return this.valueBuilder_;
            }

            private void ensureAlternativesIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.alternatives_ = new ArrayList<ScreenDensity>(this.alternatives_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<ScreenDensity> getAlternativesList() {
                if (this.alternativesBuilder_ == null) {
                    return Collections.unmodifiableList(this.alternatives_);
                }
                return this.alternativesBuilder_.getMessageList();
            }

            @Override
            public int getAlternativesCount() {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.size();
                }
                return this.alternativesBuilder_.getCount();
            }

            @Override
            public ScreenDensity getAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessage(index);
            }

            public Builder setAlternatives(int index, ScreenDensity value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setAlternatives(int index, ScreenDensity.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(ScreenDensity value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addAlternatives(int index, ScreenDensity value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addAlternatives(ScreenDensity.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(int index, ScreenDensity.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAlternatives(Iterable<? extends ScreenDensity> values2) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.alternatives_);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearAlternatives() {
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            public Builder removeAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.remove(index);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.remove(index);
                }
                return this;
            }

            public ScreenDensity.Builder getAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().getBuilder(index);
            }

            @Override
            public ScreenDensityOrBuilder getAlternativesOrBuilder(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends ScreenDensityOrBuilder> getAlternativesOrBuilderList() {
                if (this.alternativesBuilder_ != null) {
                    return this.alternativesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.alternatives_);
            }

            public ScreenDensity.Builder addAlternativesBuilder() {
                return this.getAlternativesFieldBuilder().addBuilder(ScreenDensity.getDefaultInstance());
            }

            public ScreenDensity.Builder addAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().addBuilder(index, ScreenDensity.getDefaultInstance());
            }

            public List<ScreenDensity.Builder> getAlternativesBuilderList() {
                return this.getAlternativesFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<ScreenDensity, ScreenDensity.Builder, ScreenDensityOrBuilder> getAlternativesFieldBuilder() {
                if (this.alternativesBuilder_ == null) {
                    this.alternativesBuilder_ = new RepeatedFieldBuilderV3(this.alternatives_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.alternatives_ = null;
                }
                return this.alternativesBuilder_;
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

    public static interface ScreenDensityTargetingOrBuilder
    extends MessageOrBuilder {
        public List<ScreenDensity> getValueList();

        public ScreenDensity getValue(int var1);

        public int getValueCount();

        public List<? extends ScreenDensityOrBuilder> getValueOrBuilderList();

        public ScreenDensityOrBuilder getValueOrBuilder(int var1);

        public List<ScreenDensity> getAlternativesList();

        public ScreenDensity getAlternatives(int var1);

        public int getAlternativesCount();

        public List<? extends ScreenDensityOrBuilder> getAlternativesOrBuilderList();

        public ScreenDensityOrBuilder getAlternativesOrBuilder(int var1);
    }

    public static final class MultiAbiTargeting
    extends GeneratedMessageV3
    implements MultiAbiTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private List<MultiAbi> value_;
        public static final int ALTERNATIVES_FIELD_NUMBER = 2;
        private List<MultiAbi> alternatives_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final MultiAbiTargeting DEFAULT_INSTANCE = new MultiAbiTargeting();
        private static final Parser<MultiAbiTargeting> PARSER = new AbstractParser<MultiAbiTargeting>(){

            @Override
            public MultiAbiTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MultiAbiTargeting(input, extensionRegistry);
            }
        };

        private MultiAbiTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private MultiAbiTargeting() {
            this.value_ = Collections.emptyList();
            this.alternatives_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MultiAbiTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.value_ = new ArrayList<MultiAbi>();
                                mutable_bitField0_ |= 1;
                            }
                            this.value_.add(input.readMessage(MultiAbi.parser(), extensionRegistry));
                            continue block11;
                        }
                        case 18: 
                    }
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.alternatives_ = new ArrayList<MultiAbi>();
                        mutable_bitField0_ |= 2;
                    }
                    this.alternatives_.add(input.readMessage(MultiAbi.parser(), extensionRegistry));
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
                    this.value_ = Collections.unmodifiableList(this.value_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_MultiAbiTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_MultiAbiTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(MultiAbiTargeting.class, Builder.class);
        }

        @Override
        public List<MultiAbi> getValueList() {
            return this.value_;
        }

        @Override
        public List<? extends MultiAbiOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        @Override
        public int getValueCount() {
            return this.value_.size();
        }

        @Override
        public MultiAbi getValue(int index) {
            return this.value_.get(index);
        }

        @Override
        public MultiAbiOrBuilder getValueOrBuilder(int index) {
            return this.value_.get(index);
        }

        @Override
        public List<MultiAbi> getAlternativesList() {
            return this.alternatives_;
        }

        @Override
        public List<? extends MultiAbiOrBuilder> getAlternativesOrBuilderList() {
            return this.alternatives_;
        }

        @Override
        public int getAlternativesCount() {
            return this.alternatives_.size();
        }

        @Override
        public MultiAbi getAlternatives(int index) {
            return this.alternatives_.get(index);
        }

        @Override
        public MultiAbiOrBuilder getAlternativesOrBuilder(int index) {
            return this.alternatives_.get(index);
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                output.writeMessage(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                output.writeMessage(2, this.alternatives_.get(i2));
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.alternatives_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MultiAbiTargeting)) {
                return super.equals(obj);
            }
            MultiAbiTargeting other = (MultiAbiTargeting)obj;
            boolean result = true;
            result = result && this.getValueList().equals(other.getValueList());
            result = result && this.getAlternativesList().equals(other.getAlternativesList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + MultiAbiTargeting.getDescriptor().hashCode();
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            if (this.getAlternativesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAlternativesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static MultiAbiTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MultiAbiTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MultiAbiTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MultiAbiTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MultiAbiTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MultiAbiTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MultiAbiTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MultiAbiTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MultiAbiTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MultiAbiTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MultiAbiTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MultiAbiTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return MultiAbiTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MultiAbiTargeting prototype) {
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

        public static MultiAbiTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MultiAbiTargeting> parser() {
            return PARSER;
        }

        public Parser<MultiAbiTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public MultiAbiTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements MultiAbiTargetingOrBuilder {
            private int bitField0_;
            private List<MultiAbi> value_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<MultiAbi, MultiAbi.Builder, MultiAbiOrBuilder> valueBuilder_;
            private List<MultiAbi> alternatives_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<MultiAbi, MultiAbi.Builder, MultiAbiOrBuilder> alternativesBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_MultiAbiTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_MultiAbiTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(MultiAbiTargeting.class, Builder.class);
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
                    this.getValueFieldBuilder();
                    this.getAlternativesFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.valueBuilder_.clear();
                }
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_MultiAbiTargeting_descriptor;
            }

            @Override
            public MultiAbiTargeting getDefaultInstanceForType() {
                return MultiAbiTargeting.getDefaultInstance();
            }

            @Override
            public MultiAbiTargeting build() {
                MultiAbiTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public MultiAbiTargeting buildPartial() {
                MultiAbiTargeting result = new MultiAbiTargeting(this);
                int from_bitField0_ = this.bitField0_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.value_ = this.value_;
                } else {
                    result.value_ = this.valueBuilder_.build();
                }
                if (this.alternativesBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.alternatives_ = this.alternatives_;
                } else {
                    result.alternatives_ = this.alternativesBuilder_.build();
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
                if (other instanceof MultiAbiTargeting) {
                    return this.mergeFrom((MultiAbiTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MultiAbiTargeting other) {
                if (other == MultiAbiTargeting.getDefaultInstance()) {
                    return this;
                }
                if (this.valueBuilder_ == null) {
                    if (!other.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = other.value_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureValueIsMutable();
                            this.value_.addAll(other.value_);
                        }
                        this.onChanged();
                    }
                } else if (!other.value_.isEmpty()) {
                    if (this.valueBuilder_.isEmpty()) {
                        this.valueBuilder_.dispose();
                        this.valueBuilder_ = null;
                        this.value_ = other.value_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.valueBuilder_ = alwaysUseFieldBuilders ? this.getValueFieldBuilder() : null;
                    } else {
                        this.valueBuilder_.addAllMessages(other.value_);
                    }
                }
                if (this.alternativesBuilder_ == null) {
                    if (!other.alternatives_.isEmpty()) {
                        if (this.alternatives_.isEmpty()) {
                            this.alternatives_ = other.alternatives_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureAlternativesIsMutable();
                            this.alternatives_.addAll(other.alternatives_);
                        }
                        this.onChanged();
                    }
                } else if (!other.alternatives_.isEmpty()) {
                    if (this.alternativesBuilder_.isEmpty()) {
                        this.alternativesBuilder_.dispose();
                        this.alternativesBuilder_ = null;
                        this.alternatives_ = other.alternatives_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.alternativesBuilder_ = alwaysUseFieldBuilders ? this.getAlternativesFieldBuilder() : null;
                    } else {
                        this.alternativesBuilder_.addAllMessages(other.alternatives_);
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
                MultiAbiTargeting parsedMessage = null;
                try {
                    parsedMessage = (MultiAbiTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (MultiAbiTargeting)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.value_ = new ArrayList<MultiAbi>(this.value_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<MultiAbi> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList(this.value_);
                }
                return this.valueBuilder_.getMessageList();
            }

            @Override
            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }

            @Override
            public MultiAbi getValue(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessage(index);
            }

            public Builder setValue(int index, MultiAbi value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.set(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setValue(int index, MultiAbi.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addValue(MultiAbi value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addValue(int index, MultiAbi value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addValue(MultiAbi.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addValue(int index, MultiAbi.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllValue(Iterable<? extends MultiAbi> values2) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.value_);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.valueBuilder_.clear();
                }
                return this;
            }

            public Builder removeValue(int index) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.remove(index);
                    this.onChanged();
                } else {
                    this.valueBuilder_.remove(index);
                }
                return this;
            }

            public MultiAbi.Builder getValueBuilder(int index) {
                return this.getValueFieldBuilder().getBuilder(index);
            }

            @Override
            public MultiAbiOrBuilder getValueOrBuilder(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends MultiAbiOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.value_);
            }

            public MultiAbi.Builder addValueBuilder() {
                return this.getValueFieldBuilder().addBuilder(MultiAbi.getDefaultInstance());
            }

            public MultiAbi.Builder addValueBuilder(int index) {
                return this.getValueFieldBuilder().addBuilder(index, MultiAbi.getDefaultInstance());
            }

            public List<MultiAbi.Builder> getValueBuilderList() {
                return this.getValueFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<MultiAbi, MultiAbi.Builder, MultiAbiOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new RepeatedFieldBuilderV3(this.value_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.value_ = null;
                }
                return this.valueBuilder_;
            }

            private void ensureAlternativesIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.alternatives_ = new ArrayList<MultiAbi>(this.alternatives_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<MultiAbi> getAlternativesList() {
                if (this.alternativesBuilder_ == null) {
                    return Collections.unmodifiableList(this.alternatives_);
                }
                return this.alternativesBuilder_.getMessageList();
            }

            @Override
            public int getAlternativesCount() {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.size();
                }
                return this.alternativesBuilder_.getCount();
            }

            @Override
            public MultiAbi getAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessage(index);
            }

            public Builder setAlternatives(int index, MultiAbi value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setAlternatives(int index, MultiAbi.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(MultiAbi value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addAlternatives(int index, MultiAbi value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addAlternatives(MultiAbi.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(int index, MultiAbi.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAlternatives(Iterable<? extends MultiAbi> values2) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.alternatives_);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearAlternatives() {
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            public Builder removeAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.remove(index);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.remove(index);
                }
                return this;
            }

            public MultiAbi.Builder getAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().getBuilder(index);
            }

            @Override
            public MultiAbiOrBuilder getAlternativesOrBuilder(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends MultiAbiOrBuilder> getAlternativesOrBuilderList() {
                if (this.alternativesBuilder_ != null) {
                    return this.alternativesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.alternatives_);
            }

            public MultiAbi.Builder addAlternativesBuilder() {
                return this.getAlternativesFieldBuilder().addBuilder(MultiAbi.getDefaultInstance());
            }

            public MultiAbi.Builder addAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().addBuilder(index, MultiAbi.getDefaultInstance());
            }

            public List<MultiAbi.Builder> getAlternativesBuilderList() {
                return this.getAlternativesFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<MultiAbi, MultiAbi.Builder, MultiAbiOrBuilder> getAlternativesFieldBuilder() {
                if (this.alternativesBuilder_ == null) {
                    this.alternativesBuilder_ = new RepeatedFieldBuilderV3(this.alternatives_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.alternatives_ = null;
                }
                return this.alternativesBuilder_;
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

    public static interface MultiAbiTargetingOrBuilder
    extends MessageOrBuilder {
        public List<MultiAbi> getValueList();

        public MultiAbi getValue(int var1);

        public int getValueCount();

        public List<? extends MultiAbiOrBuilder> getValueOrBuilderList();

        public MultiAbiOrBuilder getValueOrBuilder(int var1);

        public List<MultiAbi> getAlternativesList();

        public MultiAbi getAlternatives(int var1);

        public int getAlternativesCount();

        public List<? extends MultiAbiOrBuilder> getAlternativesOrBuilderList();

        public MultiAbiOrBuilder getAlternativesOrBuilder(int var1);
    }

    public static final class AbiTargeting
    extends GeneratedMessageV3
    implements AbiTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private List<Abi> value_;
        public static final int ALTERNATIVES_FIELD_NUMBER = 2;
        private List<Abi> alternatives_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final AbiTargeting DEFAULT_INSTANCE = new AbiTargeting();
        private static final Parser<AbiTargeting> PARSER = new AbstractParser<AbiTargeting>(){

            @Override
            public AbiTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AbiTargeting(input, extensionRegistry);
            }
        };

        private AbiTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private AbiTargeting() {
            this.value_ = Collections.emptyList();
            this.alternatives_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AbiTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.value_ = new ArrayList<Abi>();
                                mutable_bitField0_ |= 1;
                            }
                            this.value_.add(input.readMessage(Abi.parser(), extensionRegistry));
                            continue block11;
                        }
                        case 18: 
                    }
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.alternatives_ = new ArrayList<Abi>();
                        mutable_bitField0_ |= 2;
                    }
                    this.alternatives_.add(input.readMessage(Abi.parser(), extensionRegistry));
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
                    this.value_ = Collections.unmodifiableList(this.value_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_AbiTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_AbiTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(AbiTargeting.class, Builder.class);
        }

        @Override
        public List<Abi> getValueList() {
            return this.value_;
        }

        @Override
        public List<? extends AbiOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        @Override
        public int getValueCount() {
            return this.value_.size();
        }

        @Override
        public Abi getValue(int index) {
            return this.value_.get(index);
        }

        @Override
        public AbiOrBuilder getValueOrBuilder(int index) {
            return this.value_.get(index);
        }

        @Override
        public List<Abi> getAlternativesList() {
            return this.alternatives_;
        }

        @Override
        public List<? extends AbiOrBuilder> getAlternativesOrBuilderList() {
            return this.alternatives_;
        }

        @Override
        public int getAlternativesCount() {
            return this.alternatives_.size();
        }

        @Override
        public Abi getAlternatives(int index) {
            return this.alternatives_.get(index);
        }

        @Override
        public AbiOrBuilder getAlternativesOrBuilder(int index) {
            return this.alternatives_.get(index);
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                output.writeMessage(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                output.writeMessage(2, this.alternatives_.get(i2));
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
            for (i2 = 0; i2 < this.value_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.value_.get(i2));
            }
            for (i2 = 0; i2 < this.alternatives_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.alternatives_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AbiTargeting)) {
                return super.equals(obj);
            }
            AbiTargeting other = (AbiTargeting)obj;
            boolean result = true;
            result = result && this.getValueList().equals(other.getValueList());
            result = result && this.getAlternativesList().equals(other.getAlternativesList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + AbiTargeting.getDescriptor().hashCode();
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            if (this.getAlternativesCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAlternativesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static AbiTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AbiTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AbiTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AbiTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AbiTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AbiTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AbiTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AbiTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AbiTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AbiTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AbiTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AbiTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return AbiTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AbiTargeting prototype) {
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

        public static AbiTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AbiTargeting> parser() {
            return PARSER;
        }

        public Parser<AbiTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public AbiTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AbiTargetingOrBuilder {
            private int bitField0_;
            private List<Abi> value_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Abi, Abi.Builder, AbiOrBuilder> valueBuilder_;
            private List<Abi> alternatives_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Abi, Abi.Builder, AbiOrBuilder> alternativesBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_AbiTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_AbiTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(AbiTargeting.class, Builder.class);
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
                    this.getValueFieldBuilder();
                    this.getAlternativesFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.valueBuilder_.clear();
                }
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_AbiTargeting_descriptor;
            }

            @Override
            public AbiTargeting getDefaultInstanceForType() {
                return AbiTargeting.getDefaultInstance();
            }

            @Override
            public AbiTargeting build() {
                AbiTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public AbiTargeting buildPartial() {
                AbiTargeting result = new AbiTargeting(this);
                int from_bitField0_ = this.bitField0_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.value_ = this.value_;
                } else {
                    result.value_ = this.valueBuilder_.build();
                }
                if (this.alternativesBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.alternatives_ = Collections.unmodifiableList(this.alternatives_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.alternatives_ = this.alternatives_;
                } else {
                    result.alternatives_ = this.alternativesBuilder_.build();
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
                if (other instanceof AbiTargeting) {
                    return this.mergeFrom((AbiTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AbiTargeting other) {
                if (other == AbiTargeting.getDefaultInstance()) {
                    return this;
                }
                if (this.valueBuilder_ == null) {
                    if (!other.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = other.value_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureValueIsMutable();
                            this.value_.addAll(other.value_);
                        }
                        this.onChanged();
                    }
                } else if (!other.value_.isEmpty()) {
                    if (this.valueBuilder_.isEmpty()) {
                        this.valueBuilder_.dispose();
                        this.valueBuilder_ = null;
                        this.value_ = other.value_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.valueBuilder_ = alwaysUseFieldBuilders ? this.getValueFieldBuilder() : null;
                    } else {
                        this.valueBuilder_.addAllMessages(other.value_);
                    }
                }
                if (this.alternativesBuilder_ == null) {
                    if (!other.alternatives_.isEmpty()) {
                        if (this.alternatives_.isEmpty()) {
                            this.alternatives_ = other.alternatives_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureAlternativesIsMutable();
                            this.alternatives_.addAll(other.alternatives_);
                        }
                        this.onChanged();
                    }
                } else if (!other.alternatives_.isEmpty()) {
                    if (this.alternativesBuilder_.isEmpty()) {
                        this.alternativesBuilder_.dispose();
                        this.alternativesBuilder_ = null;
                        this.alternatives_ = other.alternatives_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.alternativesBuilder_ = alwaysUseFieldBuilders ? this.getAlternativesFieldBuilder() : null;
                    } else {
                        this.alternativesBuilder_.addAllMessages(other.alternatives_);
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
                AbiTargeting parsedMessage = null;
                try {
                    parsedMessage = (AbiTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (AbiTargeting)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.value_ = new ArrayList<Abi>(this.value_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<Abi> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList(this.value_);
                }
                return this.valueBuilder_.getMessageList();
            }

            @Override
            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }

            @Override
            public Abi getValue(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessage(index);
            }

            public Builder setValue(int index, Abi value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.set(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setValue(int index, Abi.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addValue(Abi value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addValue(int index, Abi value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(index, value);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addValue(Abi.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addValue(int index, Abi.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllValue(Iterable<? extends Abi> values2) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.value_);
                    this.onChanged();
                } else {
                    this.valueBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.valueBuilder_.clear();
                }
                return this;
            }

            public Builder removeValue(int index) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.remove(index);
                    this.onChanged();
                } else {
                    this.valueBuilder_.remove(index);
                }
                return this;
            }

            public Abi.Builder getValueBuilder(int index) {
                return this.getValueFieldBuilder().getBuilder(index);
            }

            @Override
            public AbiOrBuilder getValueOrBuilder(int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return this.valueBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends AbiOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.value_);
            }

            public Abi.Builder addValueBuilder() {
                return this.getValueFieldBuilder().addBuilder(Abi.getDefaultInstance());
            }

            public Abi.Builder addValueBuilder(int index) {
                return this.getValueFieldBuilder().addBuilder(index, Abi.getDefaultInstance());
            }

            public List<Abi.Builder> getValueBuilderList() {
                return this.getValueFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Abi, Abi.Builder, AbiOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new RepeatedFieldBuilderV3(this.value_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.value_ = null;
                }
                return this.valueBuilder_;
            }

            private void ensureAlternativesIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.alternatives_ = new ArrayList<Abi>(this.alternatives_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<Abi> getAlternativesList() {
                if (this.alternativesBuilder_ == null) {
                    return Collections.unmodifiableList(this.alternatives_);
                }
                return this.alternativesBuilder_.getMessageList();
            }

            @Override
            public int getAlternativesCount() {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.size();
                }
                return this.alternativesBuilder_.getCount();
            }

            @Override
            public Abi getAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessage(index);
            }

            public Builder setAlternatives(int index, Abi value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setAlternatives(int index, Abi.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(Abi value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addAlternatives(int index, Abi value) {
                if (this.alternativesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, value);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addAlternatives(Abi.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAlternatives(int index, Abi.Builder builderForValue) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAlternatives(Iterable<? extends Abi> values2) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.alternatives_);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearAlternatives() {
                if (this.alternativesBuilder_ == null) {
                    this.alternatives_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.clear();
                }
                return this;
            }

            public Builder removeAlternatives(int index) {
                if (this.alternativesBuilder_ == null) {
                    this.ensureAlternativesIsMutable();
                    this.alternatives_.remove(index);
                    this.onChanged();
                } else {
                    this.alternativesBuilder_.remove(index);
                }
                return this;
            }

            public Abi.Builder getAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().getBuilder(index);
            }

            @Override
            public AbiOrBuilder getAlternativesOrBuilder(int index) {
                if (this.alternativesBuilder_ == null) {
                    return this.alternatives_.get(index);
                }
                return this.alternativesBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends AbiOrBuilder> getAlternativesOrBuilderList() {
                if (this.alternativesBuilder_ != null) {
                    return this.alternativesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.alternatives_);
            }

            public Abi.Builder addAlternativesBuilder() {
                return this.getAlternativesFieldBuilder().addBuilder(Abi.getDefaultInstance());
            }

            public Abi.Builder addAlternativesBuilder(int index) {
                return this.getAlternativesFieldBuilder().addBuilder(index, Abi.getDefaultInstance());
            }

            public List<Abi.Builder> getAlternativesBuilderList() {
                return this.getAlternativesFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Abi, Abi.Builder, AbiOrBuilder> getAlternativesFieldBuilder() {
                if (this.alternativesBuilder_ == null) {
                    this.alternativesBuilder_ = new RepeatedFieldBuilderV3(this.alternatives_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.alternatives_ = null;
                }
                return this.alternativesBuilder_;
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

    public static interface AbiTargetingOrBuilder
    extends MessageOrBuilder {
        public List<Abi> getValueList();

        public Abi getValue(int var1);

        public int getValueCount();

        public List<? extends AbiOrBuilder> getValueOrBuilderList();

        public AbiOrBuilder getValueOrBuilder(int var1);

        public List<Abi> getAlternativesList();

        public Abi getAlternatives(int var1);

        public int getAlternativesCount();

        public List<? extends AbiOrBuilder> getAlternativesOrBuilderList();

        public AbiOrBuilder getAlternativesOrBuilder(int var1);
    }

    public static final class ApexImageTargeting
    extends GeneratedMessageV3
    implements ApexImageTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MULTI_ABI_FIELD_NUMBER = 1;
        private MultiAbiTargeting multiAbi_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ApexImageTargeting DEFAULT_INSTANCE = new ApexImageTargeting();
        private static final Parser<ApexImageTargeting> PARSER = new AbstractParser<ApexImageTargeting>(){

            @Override
            public ApexImageTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ApexImageTargeting(input, extensionRegistry);
            }
        };

        private ApexImageTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ApexImageTargeting() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ApexImageTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    MultiAbiTargeting.Builder subBuilder = null;
                    if (this.multiAbi_ != null) {
                        subBuilder = this.multiAbi_.toBuilder();
                    }
                    this.multiAbi_ = input.readMessage(MultiAbiTargeting.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    subBuilder.mergeFrom(this.multiAbi_);
                    this.multiAbi_ = subBuilder.buildPartial();
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
            return internal_static_android_bundle_ApexImageTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ApexImageTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(ApexImageTargeting.class, Builder.class);
        }

        @Override
        public boolean hasMultiAbi() {
            return this.multiAbi_ != null;
        }

        @Override
        public MultiAbiTargeting getMultiAbi() {
            return this.multiAbi_ == null ? MultiAbiTargeting.getDefaultInstance() : this.multiAbi_;
        }

        @Override
        public MultiAbiTargetingOrBuilder getMultiAbiOrBuilder() {
            return this.getMultiAbi();
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
            if (this.multiAbi_ != null) {
                output.writeMessage(1, this.getMultiAbi());
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
            if (this.multiAbi_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getMultiAbi());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ApexImageTargeting)) {
                return super.equals(obj);
            }
            ApexImageTargeting other = (ApexImageTargeting)obj;
            boolean result = true;
            boolean bl = result = result && this.hasMultiAbi() == other.hasMultiAbi();
            if (this.hasMultiAbi()) {
                result = result && this.getMultiAbi().equals(other.getMultiAbi());
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
            hash = 19 * hash + ApexImageTargeting.getDescriptor().hashCode();
            if (this.hasMultiAbi()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getMultiAbi().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ApexImageTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexImageTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexImageTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexImageTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexImageTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApexImageTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApexImageTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApexImageTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApexImageTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ApexImageTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApexImageTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApexImageTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ApexImageTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ApexImageTargeting prototype) {
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

        public static ApexImageTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ApexImageTargeting> parser() {
            return PARSER;
        }

        public Parser<ApexImageTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public ApexImageTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ApexImageTargetingOrBuilder {
            private MultiAbiTargeting multiAbi_ = null;
            private SingleFieldBuilderV3<MultiAbiTargeting, MultiAbiTargeting.Builder, MultiAbiTargetingOrBuilder> multiAbiBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ApexImageTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ApexImageTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(ApexImageTargeting.class, Builder.class);
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
                if (this.multiAbiBuilder_ == null) {
                    this.multiAbi_ = null;
                } else {
                    this.multiAbi_ = null;
                    this.multiAbiBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ApexImageTargeting_descriptor;
            }

            @Override
            public ApexImageTargeting getDefaultInstanceForType() {
                return ApexImageTargeting.getDefaultInstance();
            }

            @Override
            public ApexImageTargeting build() {
                ApexImageTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ApexImageTargeting buildPartial() {
                ApexImageTargeting result = new ApexImageTargeting(this);
                if (this.multiAbiBuilder_ == null) {
                    result.multiAbi_ = this.multiAbi_;
                } else {
                    result.multiAbi_ = this.multiAbiBuilder_.build();
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
                if (other instanceof ApexImageTargeting) {
                    return this.mergeFrom((ApexImageTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ApexImageTargeting other) {
                if (other == ApexImageTargeting.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMultiAbi()) {
                    this.mergeMultiAbi(other.getMultiAbi());
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
                ApexImageTargeting parsedMessage = null;
                try {
                    parsedMessage = (ApexImageTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ApexImageTargeting)e2.getUnfinishedMessage();
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
            public boolean hasMultiAbi() {
                return this.multiAbiBuilder_ != null || this.multiAbi_ != null;
            }

            @Override
            public MultiAbiTargeting getMultiAbi() {
                if (this.multiAbiBuilder_ == null) {
                    return this.multiAbi_ == null ? MultiAbiTargeting.getDefaultInstance() : this.multiAbi_;
                }
                return this.multiAbiBuilder_.getMessage();
            }

            public Builder setMultiAbi(MultiAbiTargeting value) {
                if (this.multiAbiBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.multiAbi_ = value;
                    this.onChanged();
                } else {
                    this.multiAbiBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setMultiAbi(MultiAbiTargeting.Builder builderForValue) {
                if (this.multiAbiBuilder_ == null) {
                    this.multiAbi_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.multiAbiBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeMultiAbi(MultiAbiTargeting value) {
                if (this.multiAbiBuilder_ == null) {
                    this.multiAbi_ = this.multiAbi_ != null ? MultiAbiTargeting.newBuilder(this.multiAbi_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.multiAbiBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearMultiAbi() {
                if (this.multiAbiBuilder_ == null) {
                    this.multiAbi_ = null;
                    this.onChanged();
                } else {
                    this.multiAbi_ = null;
                    this.multiAbiBuilder_ = null;
                }
                return this;
            }

            public MultiAbiTargeting.Builder getMultiAbiBuilder() {
                this.onChanged();
                return this.getMultiAbiFieldBuilder().getBuilder();
            }

            @Override
            public MultiAbiTargetingOrBuilder getMultiAbiOrBuilder() {
                if (this.multiAbiBuilder_ != null) {
                    return this.multiAbiBuilder_.getMessageOrBuilder();
                }
                return this.multiAbi_ == null ? MultiAbiTargeting.getDefaultInstance() : this.multiAbi_;
            }

            private SingleFieldBuilderV3<MultiAbiTargeting, MultiAbiTargeting.Builder, MultiAbiTargetingOrBuilder> getMultiAbiFieldBuilder() {
                if (this.multiAbiBuilder_ == null) {
                    this.multiAbiBuilder_ = new SingleFieldBuilderV3(this.getMultiAbi(), this.getParentForChildren(), this.isClean());
                    this.multiAbi_ = null;
                }
                return this.multiAbiBuilder_;
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

    public static interface ApexImageTargetingOrBuilder
    extends MessageOrBuilder {
        public boolean hasMultiAbi();

        public MultiAbiTargeting getMultiAbi();

        public MultiAbiTargetingOrBuilder getMultiAbiOrBuilder();
    }

    public static final class NativeDirectoryTargeting
    extends GeneratedMessageV3
    implements NativeDirectoryTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ABI_FIELD_NUMBER = 1;
        private Abi abi_;
        public static final int GRAPHICS_API_FIELD_NUMBER = 2;
        private GraphicsApi graphicsApi_;
        public static final int TEXTURE_COMPRESSION_FORMAT_FIELD_NUMBER = 3;
        private TextureCompressionFormat textureCompressionFormat_;
        public static final int SANITIZER_FIELD_NUMBER = 4;
        private Sanitizer sanitizer_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final NativeDirectoryTargeting DEFAULT_INSTANCE = new NativeDirectoryTargeting();
        private static final Parser<NativeDirectoryTargeting> PARSER = new AbstractParser<NativeDirectoryTargeting>(){

            @Override
            public NativeDirectoryTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new NativeDirectoryTargeting(input, extensionRegistry);
            }
        };

        private NativeDirectoryTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private NativeDirectoryTargeting() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private NativeDirectoryTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.abi_ != null) {
                                subBuilder = this.abi_.toBuilder();
                            }
                            this.abi_ = input.readMessage(Abi.parser(), extensionRegistry);
                            if (subBuilder == null) continue block13;
                            ((Abi.Builder)subBuilder).mergeFrom(this.abi_);
                            this.abi_ = ((Abi.Builder)subBuilder).buildPartial();
                            continue block13;
                        }
                        case 18: {
                            subBuilder = null;
                            if (this.graphicsApi_ != null) {
                                subBuilder = this.graphicsApi_.toBuilder();
                            }
                            this.graphicsApi_ = input.readMessage(GraphicsApi.parser(), extensionRegistry);
                            if (subBuilder == null) continue block13;
                            ((GraphicsApi.Builder)subBuilder).mergeFrom(this.graphicsApi_);
                            this.graphicsApi_ = ((GraphicsApi.Builder)subBuilder).buildPartial();
                            continue block13;
                        }
                        case 26: {
                            subBuilder = null;
                            if (this.textureCompressionFormat_ != null) {
                                subBuilder = this.textureCompressionFormat_.toBuilder();
                            }
                            this.textureCompressionFormat_ = input.readMessage(TextureCompressionFormat.parser(), extensionRegistry);
                            if (subBuilder == null) continue block13;
                            ((TextureCompressionFormat.Builder)subBuilder).mergeFrom(this.textureCompressionFormat_);
                            this.textureCompressionFormat_ = ((TextureCompressionFormat.Builder)subBuilder).buildPartial();
                            continue block13;
                        }
                        case 34: 
                    }
                    subBuilder = null;
                    if (this.sanitizer_ != null) {
                        subBuilder = this.sanitizer_.toBuilder();
                    }
                    this.sanitizer_ = input.readMessage(Sanitizer.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    ((Sanitizer.Builder)subBuilder).mergeFrom(this.sanitizer_);
                    this.sanitizer_ = ((Sanitizer.Builder)subBuilder).buildPartial();
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
            return internal_static_android_bundle_NativeDirectoryTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_NativeDirectoryTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(NativeDirectoryTargeting.class, Builder.class);
        }

        @Override
        public boolean hasAbi() {
            return this.abi_ != null;
        }

        @Override
        public Abi getAbi() {
            return this.abi_ == null ? Abi.getDefaultInstance() : this.abi_;
        }

        @Override
        public AbiOrBuilder getAbiOrBuilder() {
            return this.getAbi();
        }

        @Override
        public boolean hasGraphicsApi() {
            return this.graphicsApi_ != null;
        }

        @Override
        public GraphicsApi getGraphicsApi() {
            return this.graphicsApi_ == null ? GraphicsApi.getDefaultInstance() : this.graphicsApi_;
        }

        @Override
        public GraphicsApiOrBuilder getGraphicsApiOrBuilder() {
            return this.getGraphicsApi();
        }

        @Override
        public boolean hasTextureCompressionFormat() {
            return this.textureCompressionFormat_ != null;
        }

        @Override
        public TextureCompressionFormat getTextureCompressionFormat() {
            return this.textureCompressionFormat_ == null ? TextureCompressionFormat.getDefaultInstance() : this.textureCompressionFormat_;
        }

        @Override
        public TextureCompressionFormatOrBuilder getTextureCompressionFormatOrBuilder() {
            return this.getTextureCompressionFormat();
        }

        @Override
        public boolean hasSanitizer() {
            return this.sanitizer_ != null;
        }

        @Override
        public Sanitizer getSanitizer() {
            return this.sanitizer_ == null ? Sanitizer.getDefaultInstance() : this.sanitizer_;
        }

        @Override
        public SanitizerOrBuilder getSanitizerOrBuilder() {
            return this.getSanitizer();
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
            if (this.abi_ != null) {
                output.writeMessage(1, this.getAbi());
            }
            if (this.graphicsApi_ != null) {
                output.writeMessage(2, this.getGraphicsApi());
            }
            if (this.textureCompressionFormat_ != null) {
                output.writeMessage(3, this.getTextureCompressionFormat());
            }
            if (this.sanitizer_ != null) {
                output.writeMessage(4, this.getSanitizer());
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
            if (this.abi_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getAbi());
            }
            if (this.graphicsApi_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getGraphicsApi());
            }
            if (this.textureCompressionFormat_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getTextureCompressionFormat());
            }
            if (this.sanitizer_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getSanitizer());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof NativeDirectoryTargeting)) {
                return super.equals(obj);
            }
            NativeDirectoryTargeting other = (NativeDirectoryTargeting)obj;
            boolean result = true;
            boolean bl = result = result && this.hasAbi() == other.hasAbi();
            if (this.hasAbi()) {
                result = result && this.getAbi().equals(other.getAbi());
            }
            boolean bl2 = result = result && this.hasGraphicsApi() == other.hasGraphicsApi();
            if (this.hasGraphicsApi()) {
                result = result && this.getGraphicsApi().equals(other.getGraphicsApi());
            }
            boolean bl3 = result = result && this.hasTextureCompressionFormat() == other.hasTextureCompressionFormat();
            if (this.hasTextureCompressionFormat()) {
                result = result && this.getTextureCompressionFormat().equals(other.getTextureCompressionFormat());
            }
            boolean bl4 = result = result && this.hasSanitizer() == other.hasSanitizer();
            if (this.hasSanitizer()) {
                result = result && this.getSanitizer().equals(other.getSanitizer());
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
            hash = 19 * hash + NativeDirectoryTargeting.getDescriptor().hashCode();
            if (this.hasAbi()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getAbi().hashCode();
            }
            if (this.hasGraphicsApi()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getGraphicsApi().hashCode();
            }
            if (this.hasTextureCompressionFormat()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getTextureCompressionFormat().hashCode();
            }
            if (this.hasSanitizer()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getSanitizer().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static NativeDirectoryTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static NativeDirectoryTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static NativeDirectoryTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static NativeDirectoryTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static NativeDirectoryTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static NativeDirectoryTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static NativeDirectoryTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static NativeDirectoryTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static NativeDirectoryTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static NativeDirectoryTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static NativeDirectoryTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static NativeDirectoryTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return NativeDirectoryTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(NativeDirectoryTargeting prototype) {
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

        public static NativeDirectoryTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<NativeDirectoryTargeting> parser() {
            return PARSER;
        }

        public Parser<NativeDirectoryTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public NativeDirectoryTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements NativeDirectoryTargetingOrBuilder {
            private Abi abi_ = null;
            private SingleFieldBuilderV3<Abi, Abi.Builder, AbiOrBuilder> abiBuilder_;
            private GraphicsApi graphicsApi_ = null;
            private SingleFieldBuilderV3<GraphicsApi, GraphicsApi.Builder, GraphicsApiOrBuilder> graphicsApiBuilder_;
            private TextureCompressionFormat textureCompressionFormat_ = null;
            private SingleFieldBuilderV3<TextureCompressionFormat, TextureCompressionFormat.Builder, TextureCompressionFormatOrBuilder> textureCompressionFormatBuilder_;
            private Sanitizer sanitizer_ = null;
            private SingleFieldBuilderV3<Sanitizer, Sanitizer.Builder, SanitizerOrBuilder> sanitizerBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_NativeDirectoryTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_NativeDirectoryTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(NativeDirectoryTargeting.class, Builder.class);
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
                if (this.abiBuilder_ == null) {
                    this.abi_ = null;
                } else {
                    this.abi_ = null;
                    this.abiBuilder_ = null;
                }
                if (this.graphicsApiBuilder_ == null) {
                    this.graphicsApi_ = null;
                } else {
                    this.graphicsApi_ = null;
                    this.graphicsApiBuilder_ = null;
                }
                if (this.textureCompressionFormatBuilder_ == null) {
                    this.textureCompressionFormat_ = null;
                } else {
                    this.textureCompressionFormat_ = null;
                    this.textureCompressionFormatBuilder_ = null;
                }
                if (this.sanitizerBuilder_ == null) {
                    this.sanitizer_ = null;
                } else {
                    this.sanitizer_ = null;
                    this.sanitizerBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_NativeDirectoryTargeting_descriptor;
            }

            @Override
            public NativeDirectoryTargeting getDefaultInstanceForType() {
                return NativeDirectoryTargeting.getDefaultInstance();
            }

            @Override
            public NativeDirectoryTargeting build() {
                NativeDirectoryTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public NativeDirectoryTargeting buildPartial() {
                NativeDirectoryTargeting result = new NativeDirectoryTargeting(this);
                if (this.abiBuilder_ == null) {
                    result.abi_ = this.abi_;
                } else {
                    result.abi_ = this.abiBuilder_.build();
                }
                if (this.graphicsApiBuilder_ == null) {
                    result.graphicsApi_ = this.graphicsApi_;
                } else {
                    result.graphicsApi_ = this.graphicsApiBuilder_.build();
                }
                if (this.textureCompressionFormatBuilder_ == null) {
                    result.textureCompressionFormat_ = this.textureCompressionFormat_;
                } else {
                    result.textureCompressionFormat_ = this.textureCompressionFormatBuilder_.build();
                }
                if (this.sanitizerBuilder_ == null) {
                    result.sanitizer_ = this.sanitizer_;
                } else {
                    result.sanitizer_ = this.sanitizerBuilder_.build();
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
                if (other instanceof NativeDirectoryTargeting) {
                    return this.mergeFrom((NativeDirectoryTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(NativeDirectoryTargeting other) {
                if (other == NativeDirectoryTargeting.getDefaultInstance()) {
                    return this;
                }
                if (other.hasAbi()) {
                    this.mergeAbi(other.getAbi());
                }
                if (other.hasGraphicsApi()) {
                    this.mergeGraphicsApi(other.getGraphicsApi());
                }
                if (other.hasTextureCompressionFormat()) {
                    this.mergeTextureCompressionFormat(other.getTextureCompressionFormat());
                }
                if (other.hasSanitizer()) {
                    this.mergeSanitizer(other.getSanitizer());
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
                NativeDirectoryTargeting parsedMessage = null;
                try {
                    parsedMessage = (NativeDirectoryTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (NativeDirectoryTargeting)e2.getUnfinishedMessage();
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
            public boolean hasAbi() {
                return this.abiBuilder_ != null || this.abi_ != null;
            }

            @Override
            public Abi getAbi() {
                if (this.abiBuilder_ == null) {
                    return this.abi_ == null ? Abi.getDefaultInstance() : this.abi_;
                }
                return this.abiBuilder_.getMessage();
            }

            public Builder setAbi(Abi value) {
                if (this.abiBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.abi_ = value;
                    this.onChanged();
                } else {
                    this.abiBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setAbi(Abi.Builder builderForValue) {
                if (this.abiBuilder_ == null) {
                    this.abi_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.abiBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeAbi(Abi value) {
                if (this.abiBuilder_ == null) {
                    this.abi_ = this.abi_ != null ? Abi.newBuilder(this.abi_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.abiBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearAbi() {
                if (this.abiBuilder_ == null) {
                    this.abi_ = null;
                    this.onChanged();
                } else {
                    this.abi_ = null;
                    this.abiBuilder_ = null;
                }
                return this;
            }

            public Abi.Builder getAbiBuilder() {
                this.onChanged();
                return this.getAbiFieldBuilder().getBuilder();
            }

            @Override
            public AbiOrBuilder getAbiOrBuilder() {
                if (this.abiBuilder_ != null) {
                    return this.abiBuilder_.getMessageOrBuilder();
                }
                return this.abi_ == null ? Abi.getDefaultInstance() : this.abi_;
            }

            private SingleFieldBuilderV3<Abi, Abi.Builder, AbiOrBuilder> getAbiFieldBuilder() {
                if (this.abiBuilder_ == null) {
                    this.abiBuilder_ = new SingleFieldBuilderV3(this.getAbi(), this.getParentForChildren(), this.isClean());
                    this.abi_ = null;
                }
                return this.abiBuilder_;
            }

            @Override
            public boolean hasGraphicsApi() {
                return this.graphicsApiBuilder_ != null || this.graphicsApi_ != null;
            }

            @Override
            public GraphicsApi getGraphicsApi() {
                if (this.graphicsApiBuilder_ == null) {
                    return this.graphicsApi_ == null ? GraphicsApi.getDefaultInstance() : this.graphicsApi_;
                }
                return this.graphicsApiBuilder_.getMessage();
            }

            public Builder setGraphicsApi(GraphicsApi value) {
                if (this.graphicsApiBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.graphicsApi_ = value;
                    this.onChanged();
                } else {
                    this.graphicsApiBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setGraphicsApi(GraphicsApi.Builder builderForValue) {
                if (this.graphicsApiBuilder_ == null) {
                    this.graphicsApi_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.graphicsApiBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeGraphicsApi(GraphicsApi value) {
                if (this.graphicsApiBuilder_ == null) {
                    this.graphicsApi_ = this.graphicsApi_ != null ? GraphicsApi.newBuilder(this.graphicsApi_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.graphicsApiBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearGraphicsApi() {
                if (this.graphicsApiBuilder_ == null) {
                    this.graphicsApi_ = null;
                    this.onChanged();
                } else {
                    this.graphicsApi_ = null;
                    this.graphicsApiBuilder_ = null;
                }
                return this;
            }

            public GraphicsApi.Builder getGraphicsApiBuilder() {
                this.onChanged();
                return this.getGraphicsApiFieldBuilder().getBuilder();
            }

            @Override
            public GraphicsApiOrBuilder getGraphicsApiOrBuilder() {
                if (this.graphicsApiBuilder_ != null) {
                    return this.graphicsApiBuilder_.getMessageOrBuilder();
                }
                return this.graphicsApi_ == null ? GraphicsApi.getDefaultInstance() : this.graphicsApi_;
            }

            private SingleFieldBuilderV3<GraphicsApi, GraphicsApi.Builder, GraphicsApiOrBuilder> getGraphicsApiFieldBuilder() {
                if (this.graphicsApiBuilder_ == null) {
                    this.graphicsApiBuilder_ = new SingleFieldBuilderV3(this.getGraphicsApi(), this.getParentForChildren(), this.isClean());
                    this.graphicsApi_ = null;
                }
                return this.graphicsApiBuilder_;
            }

            @Override
            public boolean hasTextureCompressionFormat() {
                return this.textureCompressionFormatBuilder_ != null || this.textureCompressionFormat_ != null;
            }

            @Override
            public TextureCompressionFormat getTextureCompressionFormat() {
                if (this.textureCompressionFormatBuilder_ == null) {
                    return this.textureCompressionFormat_ == null ? TextureCompressionFormat.getDefaultInstance() : this.textureCompressionFormat_;
                }
                return this.textureCompressionFormatBuilder_.getMessage();
            }

            public Builder setTextureCompressionFormat(TextureCompressionFormat value) {
                if (this.textureCompressionFormatBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.textureCompressionFormat_ = value;
                    this.onChanged();
                } else {
                    this.textureCompressionFormatBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setTextureCompressionFormat(TextureCompressionFormat.Builder builderForValue) {
                if (this.textureCompressionFormatBuilder_ == null) {
                    this.textureCompressionFormat_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.textureCompressionFormatBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTextureCompressionFormat(TextureCompressionFormat value) {
                if (this.textureCompressionFormatBuilder_ == null) {
                    this.textureCompressionFormat_ = this.textureCompressionFormat_ != null ? TextureCompressionFormat.newBuilder(this.textureCompressionFormat_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.textureCompressionFormatBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearTextureCompressionFormat() {
                if (this.textureCompressionFormatBuilder_ == null) {
                    this.textureCompressionFormat_ = null;
                    this.onChanged();
                } else {
                    this.textureCompressionFormat_ = null;
                    this.textureCompressionFormatBuilder_ = null;
                }
                return this;
            }

            public TextureCompressionFormat.Builder getTextureCompressionFormatBuilder() {
                this.onChanged();
                return this.getTextureCompressionFormatFieldBuilder().getBuilder();
            }

            @Override
            public TextureCompressionFormatOrBuilder getTextureCompressionFormatOrBuilder() {
                if (this.textureCompressionFormatBuilder_ != null) {
                    return this.textureCompressionFormatBuilder_.getMessageOrBuilder();
                }
                return this.textureCompressionFormat_ == null ? TextureCompressionFormat.getDefaultInstance() : this.textureCompressionFormat_;
            }

            private SingleFieldBuilderV3<TextureCompressionFormat, TextureCompressionFormat.Builder, TextureCompressionFormatOrBuilder> getTextureCompressionFormatFieldBuilder() {
                if (this.textureCompressionFormatBuilder_ == null) {
                    this.textureCompressionFormatBuilder_ = new SingleFieldBuilderV3(this.getTextureCompressionFormat(), this.getParentForChildren(), this.isClean());
                    this.textureCompressionFormat_ = null;
                }
                return this.textureCompressionFormatBuilder_;
            }

            @Override
            public boolean hasSanitizer() {
                return this.sanitizerBuilder_ != null || this.sanitizer_ != null;
            }

            @Override
            public Sanitizer getSanitizer() {
                if (this.sanitizerBuilder_ == null) {
                    return this.sanitizer_ == null ? Sanitizer.getDefaultInstance() : this.sanitizer_;
                }
                return this.sanitizerBuilder_.getMessage();
            }

            public Builder setSanitizer(Sanitizer value) {
                if (this.sanitizerBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.sanitizer_ = value;
                    this.onChanged();
                } else {
                    this.sanitizerBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setSanitizer(Sanitizer.Builder builderForValue) {
                if (this.sanitizerBuilder_ == null) {
                    this.sanitizer_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.sanitizerBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeSanitizer(Sanitizer value) {
                if (this.sanitizerBuilder_ == null) {
                    this.sanitizer_ = this.sanitizer_ != null ? Sanitizer.newBuilder(this.sanitizer_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.sanitizerBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearSanitizer() {
                if (this.sanitizerBuilder_ == null) {
                    this.sanitizer_ = null;
                    this.onChanged();
                } else {
                    this.sanitizer_ = null;
                    this.sanitizerBuilder_ = null;
                }
                return this;
            }

            public Sanitizer.Builder getSanitizerBuilder() {
                this.onChanged();
                return this.getSanitizerFieldBuilder().getBuilder();
            }

            @Override
            public SanitizerOrBuilder getSanitizerOrBuilder() {
                if (this.sanitizerBuilder_ != null) {
                    return this.sanitizerBuilder_.getMessageOrBuilder();
                }
                return this.sanitizer_ == null ? Sanitizer.getDefaultInstance() : this.sanitizer_;
            }

            private SingleFieldBuilderV3<Sanitizer, Sanitizer.Builder, SanitizerOrBuilder> getSanitizerFieldBuilder() {
                if (this.sanitizerBuilder_ == null) {
                    this.sanitizerBuilder_ = new SingleFieldBuilderV3(this.getSanitizer(), this.getParentForChildren(), this.isClean());
                    this.sanitizer_ = null;
                }
                return this.sanitizerBuilder_;
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

    public static interface NativeDirectoryTargetingOrBuilder
    extends MessageOrBuilder {
        public boolean hasAbi();

        public Abi getAbi();

        public AbiOrBuilder getAbiOrBuilder();

        public boolean hasGraphicsApi();

        public GraphicsApi getGraphicsApi();

        public GraphicsApiOrBuilder getGraphicsApiOrBuilder();

        public boolean hasTextureCompressionFormat();

        public TextureCompressionFormat getTextureCompressionFormat();

        public TextureCompressionFormatOrBuilder getTextureCompressionFormatOrBuilder();

        public boolean hasSanitizer();

        public Sanitizer getSanitizer();

        public SanitizerOrBuilder getSanitizerOrBuilder();
    }

    public static final class AssetsDirectoryTargeting
    extends GeneratedMessageV3
    implements AssetsDirectoryTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ABI_FIELD_NUMBER = 1;
        private AbiTargeting abi_;
        public static final int GRAPHICS_API_FIELD_NUMBER = 2;
        private GraphicsApiTargeting graphicsApi_;
        public static final int TEXTURE_COMPRESSION_FORMAT_FIELD_NUMBER = 3;
        private TextureCompressionFormatTargeting textureCompressionFormat_;
        public static final int LANGUAGE_FIELD_NUMBER = 4;
        private LanguageTargeting language_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final AssetsDirectoryTargeting DEFAULT_INSTANCE = new AssetsDirectoryTargeting();
        private static final Parser<AssetsDirectoryTargeting> PARSER = new AbstractParser<AssetsDirectoryTargeting>(){

            @Override
            public AssetsDirectoryTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new AssetsDirectoryTargeting(input, extensionRegistry);
            }
        };

        private AssetsDirectoryTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private AssetsDirectoryTargeting() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private AssetsDirectoryTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.abi_ != null) {
                                subBuilder = this.abi_.toBuilder();
                            }
                            this.abi_ = input.readMessage(AbiTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block13;
                            ((AbiTargeting.Builder)subBuilder).mergeFrom(this.abi_);
                            this.abi_ = ((AbiTargeting.Builder)subBuilder).buildPartial();
                            continue block13;
                        }
                        case 18: {
                            subBuilder = null;
                            if (this.graphicsApi_ != null) {
                                subBuilder = this.graphicsApi_.toBuilder();
                            }
                            this.graphicsApi_ = input.readMessage(GraphicsApiTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block13;
                            ((GraphicsApiTargeting.Builder)subBuilder).mergeFrom(this.graphicsApi_);
                            this.graphicsApi_ = ((GraphicsApiTargeting.Builder)subBuilder).buildPartial();
                            continue block13;
                        }
                        case 26: {
                            subBuilder = null;
                            if (this.textureCompressionFormat_ != null) {
                                subBuilder = this.textureCompressionFormat_.toBuilder();
                            }
                            this.textureCompressionFormat_ = input.readMessage(TextureCompressionFormatTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block13;
                            ((TextureCompressionFormatTargeting.Builder)subBuilder).mergeFrom(this.textureCompressionFormat_);
                            this.textureCompressionFormat_ = ((TextureCompressionFormatTargeting.Builder)subBuilder).buildPartial();
                            continue block13;
                        }
                        case 34: 
                    }
                    subBuilder = null;
                    if (this.language_ != null) {
                        subBuilder = this.language_.toBuilder();
                    }
                    this.language_ = input.readMessage(LanguageTargeting.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    ((LanguageTargeting.Builder)subBuilder).mergeFrom(this.language_);
                    this.language_ = ((LanguageTargeting.Builder)subBuilder).buildPartial();
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
            return internal_static_android_bundle_AssetsDirectoryTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_AssetsDirectoryTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(AssetsDirectoryTargeting.class, Builder.class);
        }

        @Override
        public boolean hasAbi() {
            return this.abi_ != null;
        }

        @Override
        public AbiTargeting getAbi() {
            return this.abi_ == null ? AbiTargeting.getDefaultInstance() : this.abi_;
        }

        @Override
        public AbiTargetingOrBuilder getAbiOrBuilder() {
            return this.getAbi();
        }

        @Override
        public boolean hasGraphicsApi() {
            return this.graphicsApi_ != null;
        }

        @Override
        public GraphicsApiTargeting getGraphicsApi() {
            return this.graphicsApi_ == null ? GraphicsApiTargeting.getDefaultInstance() : this.graphicsApi_;
        }

        @Override
        public GraphicsApiTargetingOrBuilder getGraphicsApiOrBuilder() {
            return this.getGraphicsApi();
        }

        @Override
        public boolean hasTextureCompressionFormat() {
            return this.textureCompressionFormat_ != null;
        }

        @Override
        public TextureCompressionFormatTargeting getTextureCompressionFormat() {
            return this.textureCompressionFormat_ == null ? TextureCompressionFormatTargeting.getDefaultInstance() : this.textureCompressionFormat_;
        }

        @Override
        public TextureCompressionFormatTargetingOrBuilder getTextureCompressionFormatOrBuilder() {
            return this.getTextureCompressionFormat();
        }

        @Override
        public boolean hasLanguage() {
            return this.language_ != null;
        }

        @Override
        public LanguageTargeting getLanguage() {
            return this.language_ == null ? LanguageTargeting.getDefaultInstance() : this.language_;
        }

        @Override
        public LanguageTargetingOrBuilder getLanguageOrBuilder() {
            return this.getLanguage();
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
            if (this.abi_ != null) {
                output.writeMessage(1, this.getAbi());
            }
            if (this.graphicsApi_ != null) {
                output.writeMessage(2, this.getGraphicsApi());
            }
            if (this.textureCompressionFormat_ != null) {
                output.writeMessage(3, this.getTextureCompressionFormat());
            }
            if (this.language_ != null) {
                output.writeMessage(4, this.getLanguage());
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
            if (this.abi_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getAbi());
            }
            if (this.graphicsApi_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getGraphicsApi());
            }
            if (this.textureCompressionFormat_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getTextureCompressionFormat());
            }
            if (this.language_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getLanguage());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AssetsDirectoryTargeting)) {
                return super.equals(obj);
            }
            AssetsDirectoryTargeting other = (AssetsDirectoryTargeting)obj;
            boolean result = true;
            boolean bl = result = result && this.hasAbi() == other.hasAbi();
            if (this.hasAbi()) {
                result = result && this.getAbi().equals(other.getAbi());
            }
            boolean bl2 = result = result && this.hasGraphicsApi() == other.hasGraphicsApi();
            if (this.hasGraphicsApi()) {
                result = result && this.getGraphicsApi().equals(other.getGraphicsApi());
            }
            boolean bl3 = result = result && this.hasTextureCompressionFormat() == other.hasTextureCompressionFormat();
            if (this.hasTextureCompressionFormat()) {
                result = result && this.getTextureCompressionFormat().equals(other.getTextureCompressionFormat());
            }
            boolean bl4 = result = result && this.hasLanguage() == other.hasLanguage();
            if (this.hasLanguage()) {
                result = result && this.getLanguage().equals(other.getLanguage());
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
            hash = 19 * hash + AssetsDirectoryTargeting.getDescriptor().hashCode();
            if (this.hasAbi()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getAbi().hashCode();
            }
            if (this.hasGraphicsApi()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getGraphicsApi().hashCode();
            }
            if (this.hasTextureCompressionFormat()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getTextureCompressionFormat().hashCode();
            }
            if (this.hasLanguage()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getLanguage().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static AssetsDirectoryTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AssetsDirectoryTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AssetsDirectoryTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AssetsDirectoryTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AssetsDirectoryTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static AssetsDirectoryTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static AssetsDirectoryTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AssetsDirectoryTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static AssetsDirectoryTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static AssetsDirectoryTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static AssetsDirectoryTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static AssetsDirectoryTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return AssetsDirectoryTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(AssetsDirectoryTargeting prototype) {
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

        public static AssetsDirectoryTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AssetsDirectoryTargeting> parser() {
            return PARSER;
        }

        public Parser<AssetsDirectoryTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public AssetsDirectoryTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AssetsDirectoryTargetingOrBuilder {
            private AbiTargeting abi_ = null;
            private SingleFieldBuilderV3<AbiTargeting, AbiTargeting.Builder, AbiTargetingOrBuilder> abiBuilder_;
            private GraphicsApiTargeting graphicsApi_ = null;
            private SingleFieldBuilderV3<GraphicsApiTargeting, GraphicsApiTargeting.Builder, GraphicsApiTargetingOrBuilder> graphicsApiBuilder_;
            private TextureCompressionFormatTargeting textureCompressionFormat_ = null;
            private SingleFieldBuilderV3<TextureCompressionFormatTargeting, TextureCompressionFormatTargeting.Builder, TextureCompressionFormatTargetingOrBuilder> textureCompressionFormatBuilder_;
            private LanguageTargeting language_ = null;
            private SingleFieldBuilderV3<LanguageTargeting, LanguageTargeting.Builder, LanguageTargetingOrBuilder> languageBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_AssetsDirectoryTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_AssetsDirectoryTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(AssetsDirectoryTargeting.class, Builder.class);
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
                if (this.abiBuilder_ == null) {
                    this.abi_ = null;
                } else {
                    this.abi_ = null;
                    this.abiBuilder_ = null;
                }
                if (this.graphicsApiBuilder_ == null) {
                    this.graphicsApi_ = null;
                } else {
                    this.graphicsApi_ = null;
                    this.graphicsApiBuilder_ = null;
                }
                if (this.textureCompressionFormatBuilder_ == null) {
                    this.textureCompressionFormat_ = null;
                } else {
                    this.textureCompressionFormat_ = null;
                    this.textureCompressionFormatBuilder_ = null;
                }
                if (this.languageBuilder_ == null) {
                    this.language_ = null;
                } else {
                    this.language_ = null;
                    this.languageBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_AssetsDirectoryTargeting_descriptor;
            }

            @Override
            public AssetsDirectoryTargeting getDefaultInstanceForType() {
                return AssetsDirectoryTargeting.getDefaultInstance();
            }

            @Override
            public AssetsDirectoryTargeting build() {
                AssetsDirectoryTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public AssetsDirectoryTargeting buildPartial() {
                AssetsDirectoryTargeting result = new AssetsDirectoryTargeting(this);
                if (this.abiBuilder_ == null) {
                    result.abi_ = this.abi_;
                } else {
                    result.abi_ = this.abiBuilder_.build();
                }
                if (this.graphicsApiBuilder_ == null) {
                    result.graphicsApi_ = this.graphicsApi_;
                } else {
                    result.graphicsApi_ = this.graphicsApiBuilder_.build();
                }
                if (this.textureCompressionFormatBuilder_ == null) {
                    result.textureCompressionFormat_ = this.textureCompressionFormat_;
                } else {
                    result.textureCompressionFormat_ = this.textureCompressionFormatBuilder_.build();
                }
                if (this.languageBuilder_ == null) {
                    result.language_ = this.language_;
                } else {
                    result.language_ = this.languageBuilder_.build();
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
                if (other instanceof AssetsDirectoryTargeting) {
                    return this.mergeFrom((AssetsDirectoryTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(AssetsDirectoryTargeting other) {
                if (other == AssetsDirectoryTargeting.getDefaultInstance()) {
                    return this;
                }
                if (other.hasAbi()) {
                    this.mergeAbi(other.getAbi());
                }
                if (other.hasGraphicsApi()) {
                    this.mergeGraphicsApi(other.getGraphicsApi());
                }
                if (other.hasTextureCompressionFormat()) {
                    this.mergeTextureCompressionFormat(other.getTextureCompressionFormat());
                }
                if (other.hasLanguage()) {
                    this.mergeLanguage(other.getLanguage());
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
                AssetsDirectoryTargeting parsedMessage = null;
                try {
                    parsedMessage = (AssetsDirectoryTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (AssetsDirectoryTargeting)e2.getUnfinishedMessage();
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
            public boolean hasAbi() {
                return this.abiBuilder_ != null || this.abi_ != null;
            }

            @Override
            public AbiTargeting getAbi() {
                if (this.abiBuilder_ == null) {
                    return this.abi_ == null ? AbiTargeting.getDefaultInstance() : this.abi_;
                }
                return this.abiBuilder_.getMessage();
            }

            public Builder setAbi(AbiTargeting value) {
                if (this.abiBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.abi_ = value;
                    this.onChanged();
                } else {
                    this.abiBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setAbi(AbiTargeting.Builder builderForValue) {
                if (this.abiBuilder_ == null) {
                    this.abi_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.abiBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeAbi(AbiTargeting value) {
                if (this.abiBuilder_ == null) {
                    this.abi_ = this.abi_ != null ? AbiTargeting.newBuilder(this.abi_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.abiBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearAbi() {
                if (this.abiBuilder_ == null) {
                    this.abi_ = null;
                    this.onChanged();
                } else {
                    this.abi_ = null;
                    this.abiBuilder_ = null;
                }
                return this;
            }

            public AbiTargeting.Builder getAbiBuilder() {
                this.onChanged();
                return this.getAbiFieldBuilder().getBuilder();
            }

            @Override
            public AbiTargetingOrBuilder getAbiOrBuilder() {
                if (this.abiBuilder_ != null) {
                    return this.abiBuilder_.getMessageOrBuilder();
                }
                return this.abi_ == null ? AbiTargeting.getDefaultInstance() : this.abi_;
            }

            private SingleFieldBuilderV3<AbiTargeting, AbiTargeting.Builder, AbiTargetingOrBuilder> getAbiFieldBuilder() {
                if (this.abiBuilder_ == null) {
                    this.abiBuilder_ = new SingleFieldBuilderV3(this.getAbi(), this.getParentForChildren(), this.isClean());
                    this.abi_ = null;
                }
                return this.abiBuilder_;
            }

            @Override
            public boolean hasGraphicsApi() {
                return this.graphicsApiBuilder_ != null || this.graphicsApi_ != null;
            }

            @Override
            public GraphicsApiTargeting getGraphicsApi() {
                if (this.graphicsApiBuilder_ == null) {
                    return this.graphicsApi_ == null ? GraphicsApiTargeting.getDefaultInstance() : this.graphicsApi_;
                }
                return this.graphicsApiBuilder_.getMessage();
            }

            public Builder setGraphicsApi(GraphicsApiTargeting value) {
                if (this.graphicsApiBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.graphicsApi_ = value;
                    this.onChanged();
                } else {
                    this.graphicsApiBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setGraphicsApi(GraphicsApiTargeting.Builder builderForValue) {
                if (this.graphicsApiBuilder_ == null) {
                    this.graphicsApi_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.graphicsApiBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeGraphicsApi(GraphicsApiTargeting value) {
                if (this.graphicsApiBuilder_ == null) {
                    this.graphicsApi_ = this.graphicsApi_ != null ? GraphicsApiTargeting.newBuilder(this.graphicsApi_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.graphicsApiBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearGraphicsApi() {
                if (this.graphicsApiBuilder_ == null) {
                    this.graphicsApi_ = null;
                    this.onChanged();
                } else {
                    this.graphicsApi_ = null;
                    this.graphicsApiBuilder_ = null;
                }
                return this;
            }

            public GraphicsApiTargeting.Builder getGraphicsApiBuilder() {
                this.onChanged();
                return this.getGraphicsApiFieldBuilder().getBuilder();
            }

            @Override
            public GraphicsApiTargetingOrBuilder getGraphicsApiOrBuilder() {
                if (this.graphicsApiBuilder_ != null) {
                    return this.graphicsApiBuilder_.getMessageOrBuilder();
                }
                return this.graphicsApi_ == null ? GraphicsApiTargeting.getDefaultInstance() : this.graphicsApi_;
            }

            private SingleFieldBuilderV3<GraphicsApiTargeting, GraphicsApiTargeting.Builder, GraphicsApiTargetingOrBuilder> getGraphicsApiFieldBuilder() {
                if (this.graphicsApiBuilder_ == null) {
                    this.graphicsApiBuilder_ = new SingleFieldBuilderV3(this.getGraphicsApi(), this.getParentForChildren(), this.isClean());
                    this.graphicsApi_ = null;
                }
                return this.graphicsApiBuilder_;
            }

            @Override
            public boolean hasTextureCompressionFormat() {
                return this.textureCompressionFormatBuilder_ != null || this.textureCompressionFormat_ != null;
            }

            @Override
            public TextureCompressionFormatTargeting getTextureCompressionFormat() {
                if (this.textureCompressionFormatBuilder_ == null) {
                    return this.textureCompressionFormat_ == null ? TextureCompressionFormatTargeting.getDefaultInstance() : this.textureCompressionFormat_;
                }
                return this.textureCompressionFormatBuilder_.getMessage();
            }

            public Builder setTextureCompressionFormat(TextureCompressionFormatTargeting value) {
                if (this.textureCompressionFormatBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.textureCompressionFormat_ = value;
                    this.onChanged();
                } else {
                    this.textureCompressionFormatBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setTextureCompressionFormat(TextureCompressionFormatTargeting.Builder builderForValue) {
                if (this.textureCompressionFormatBuilder_ == null) {
                    this.textureCompressionFormat_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.textureCompressionFormatBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTextureCompressionFormat(TextureCompressionFormatTargeting value) {
                if (this.textureCompressionFormatBuilder_ == null) {
                    this.textureCompressionFormat_ = this.textureCompressionFormat_ != null ? TextureCompressionFormatTargeting.newBuilder(this.textureCompressionFormat_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.textureCompressionFormatBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearTextureCompressionFormat() {
                if (this.textureCompressionFormatBuilder_ == null) {
                    this.textureCompressionFormat_ = null;
                    this.onChanged();
                } else {
                    this.textureCompressionFormat_ = null;
                    this.textureCompressionFormatBuilder_ = null;
                }
                return this;
            }

            public TextureCompressionFormatTargeting.Builder getTextureCompressionFormatBuilder() {
                this.onChanged();
                return this.getTextureCompressionFormatFieldBuilder().getBuilder();
            }

            @Override
            public TextureCompressionFormatTargetingOrBuilder getTextureCompressionFormatOrBuilder() {
                if (this.textureCompressionFormatBuilder_ != null) {
                    return this.textureCompressionFormatBuilder_.getMessageOrBuilder();
                }
                return this.textureCompressionFormat_ == null ? TextureCompressionFormatTargeting.getDefaultInstance() : this.textureCompressionFormat_;
            }

            private SingleFieldBuilderV3<TextureCompressionFormatTargeting, TextureCompressionFormatTargeting.Builder, TextureCompressionFormatTargetingOrBuilder> getTextureCompressionFormatFieldBuilder() {
                if (this.textureCompressionFormatBuilder_ == null) {
                    this.textureCompressionFormatBuilder_ = new SingleFieldBuilderV3(this.getTextureCompressionFormat(), this.getParentForChildren(), this.isClean());
                    this.textureCompressionFormat_ = null;
                }
                return this.textureCompressionFormatBuilder_;
            }

            @Override
            public boolean hasLanguage() {
                return this.languageBuilder_ != null || this.language_ != null;
            }

            @Override
            public LanguageTargeting getLanguage() {
                if (this.languageBuilder_ == null) {
                    return this.language_ == null ? LanguageTargeting.getDefaultInstance() : this.language_;
                }
                return this.languageBuilder_.getMessage();
            }

            public Builder setLanguage(LanguageTargeting value) {
                if (this.languageBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.language_ = value;
                    this.onChanged();
                } else {
                    this.languageBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setLanguage(LanguageTargeting.Builder builderForValue) {
                if (this.languageBuilder_ == null) {
                    this.language_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.languageBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeLanguage(LanguageTargeting value) {
                if (this.languageBuilder_ == null) {
                    this.language_ = this.language_ != null ? LanguageTargeting.newBuilder(this.language_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.languageBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearLanguage() {
                if (this.languageBuilder_ == null) {
                    this.language_ = null;
                    this.onChanged();
                } else {
                    this.language_ = null;
                    this.languageBuilder_ = null;
                }
                return this;
            }

            public LanguageTargeting.Builder getLanguageBuilder() {
                this.onChanged();
                return this.getLanguageFieldBuilder().getBuilder();
            }

            @Override
            public LanguageTargetingOrBuilder getLanguageOrBuilder() {
                if (this.languageBuilder_ != null) {
                    return this.languageBuilder_.getMessageOrBuilder();
                }
                return this.language_ == null ? LanguageTargeting.getDefaultInstance() : this.language_;
            }

            private SingleFieldBuilderV3<LanguageTargeting, LanguageTargeting.Builder, LanguageTargetingOrBuilder> getLanguageFieldBuilder() {
                if (this.languageBuilder_ == null) {
                    this.languageBuilder_ = new SingleFieldBuilderV3(this.getLanguage(), this.getParentForChildren(), this.isClean());
                    this.language_ = null;
                }
                return this.languageBuilder_;
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

    public static interface AssetsDirectoryTargetingOrBuilder
    extends MessageOrBuilder {
        public boolean hasAbi();

        public AbiTargeting getAbi();

        public AbiTargetingOrBuilder getAbiOrBuilder();

        public boolean hasGraphicsApi();

        public GraphicsApiTargeting getGraphicsApi();

        public GraphicsApiTargetingOrBuilder getGraphicsApiOrBuilder();

        public boolean hasTextureCompressionFormat();

        public TextureCompressionFormatTargeting getTextureCompressionFormat();

        public TextureCompressionFormatTargetingOrBuilder getTextureCompressionFormatOrBuilder();

        public boolean hasLanguage();

        public LanguageTargeting getLanguage();

        public LanguageTargetingOrBuilder getLanguageOrBuilder();
    }

    public static final class DeviceFeature
    extends GeneratedMessageV3
    implements DeviceFeatureOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int FEATURE_NAME_FIELD_NUMBER = 1;
        private volatile Object featureName_;
        public static final int FEATURE_VERSION_FIELD_NUMBER = 2;
        private int featureVersion_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final DeviceFeature DEFAULT_INSTANCE = new DeviceFeature();
        private static final Parser<DeviceFeature> PARSER = new AbstractParser<DeviceFeature>(){

            @Override
            public DeviceFeature parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DeviceFeature(input, extensionRegistry);
            }
        };

        private DeviceFeature(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private DeviceFeature() {
            this.featureName_ = "";
            this.featureVersion_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DeviceFeature(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.featureName_ = s3;
                            continue block11;
                        }
                        case 16: 
                    }
                    this.featureVersion_ = input.readInt32();
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
            return internal_static_android_bundle_DeviceFeature_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_DeviceFeature_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceFeature.class, Builder.class);
        }

        @Override
        public String getFeatureName() {
            Object ref = this.featureName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.featureName_ = s3;
            return s3;
        }

        @Override
        public ByteString getFeatureNameBytes() {
            Object ref = this.featureName_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.featureName_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public int getFeatureVersion() {
            return this.featureVersion_;
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
            if (!this.getFeatureNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.featureName_);
            }
            if (this.featureVersion_ != 0) {
                output.writeInt32(2, this.featureVersion_);
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
            if (!this.getFeatureNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.featureName_);
            }
            if (this.featureVersion_ != 0) {
                size += CodedOutputStream.computeInt32Size(2, this.featureVersion_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DeviceFeature)) {
                return super.equals(obj);
            }
            DeviceFeature other = (DeviceFeature)obj;
            boolean result = true;
            result = result && this.getFeatureName().equals(other.getFeatureName());
            result = result && this.getFeatureVersion() == other.getFeatureVersion();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + DeviceFeature.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getFeatureName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getFeatureVersion();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static DeviceFeature parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeviceFeature parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceFeature parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeviceFeature parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceFeature parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static DeviceFeature parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static DeviceFeature parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceFeature parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceFeature parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static DeviceFeature parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static DeviceFeature parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static DeviceFeature parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return DeviceFeature.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(DeviceFeature prototype) {
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

        public static DeviceFeature getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceFeature> parser() {
            return PARSER;
        }

        public Parser<DeviceFeature> getParserForType() {
            return PARSER;
        }

        @Override
        public DeviceFeature getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements DeviceFeatureOrBuilder {
            private Object featureName_ = "";
            private int featureVersion_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_DeviceFeature_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_DeviceFeature_fieldAccessorTable.ensureFieldAccessorsInitialized(DeviceFeature.class, Builder.class);
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
                this.featureName_ = "";
                this.featureVersion_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_DeviceFeature_descriptor;
            }

            @Override
            public DeviceFeature getDefaultInstanceForType() {
                return DeviceFeature.getDefaultInstance();
            }

            @Override
            public DeviceFeature build() {
                DeviceFeature result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public DeviceFeature buildPartial() {
                DeviceFeature result = new DeviceFeature(this);
                result.featureName_ = this.featureName_;
                result.featureVersion_ = this.featureVersion_;
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
                if (other instanceof DeviceFeature) {
                    return this.mergeFrom((DeviceFeature)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DeviceFeature other) {
                if (other == DeviceFeature.getDefaultInstance()) {
                    return this;
                }
                if (!other.getFeatureName().isEmpty()) {
                    this.featureName_ = other.featureName_;
                    this.onChanged();
                }
                if (other.getFeatureVersion() != 0) {
                    this.setFeatureVersion(other.getFeatureVersion());
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
                DeviceFeature parsedMessage = null;
                try {
                    parsedMessage = (DeviceFeature)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (DeviceFeature)e2.getUnfinishedMessage();
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
            public String getFeatureName() {
                Object ref = this.featureName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.featureName_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getFeatureNameBytes() {
                Object ref = this.featureName_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.featureName_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setFeatureName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.featureName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearFeatureName() {
                this.featureName_ = DeviceFeature.getDefaultInstance().getFeatureName();
                this.onChanged();
                return this;
            }

            public Builder setFeatureNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                DeviceFeature.checkByteStringIsUtf8(value);
                this.featureName_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public int getFeatureVersion() {
                return this.featureVersion_;
            }

            public Builder setFeatureVersion(int value) {
                this.featureVersion_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearFeatureVersion() {
                this.featureVersion_ = 0;
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

    public static interface DeviceFeatureOrBuilder
    extends MessageOrBuilder {
        public String getFeatureName();

        public ByteString getFeatureNameBytes();

        public int getFeatureVersion();
    }

    public static final class Sanitizer
    extends GeneratedMessageV3
    implements SanitizerOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ALIAS_FIELD_NUMBER = 1;
        private int alias_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Sanitizer DEFAULT_INSTANCE = new Sanitizer();
        private static final Parser<Sanitizer> PARSER = new AbstractParser<Sanitizer>(){

            @Override
            public Sanitizer parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Sanitizer(input, extensionRegistry);
            }
        };

        private Sanitizer(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Sanitizer() {
            this.alias_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Sanitizer(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.alias_ = rawValue = input.readEnum();
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
            return internal_static_android_bundle_Sanitizer_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Sanitizer_fieldAccessorTable.ensureFieldAccessorsInitialized(Sanitizer.class, Builder.class);
        }

        @Override
        public int getAliasValue() {
            return this.alias_;
        }

        @Override
        public SanitizerAlias getAlias() {
            SanitizerAlias result = SanitizerAlias.valueOf(this.alias_);
            return result == null ? SanitizerAlias.UNRECOGNIZED : result;
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
            if (this.alias_ != SanitizerAlias.NONE.getNumber()) {
                output.writeEnum(1, this.alias_);
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
            if (this.alias_ != SanitizerAlias.NONE.getNumber()) {
                size += CodedOutputStream.computeEnumSize(1, this.alias_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Sanitizer)) {
                return super.equals(obj);
            }
            Sanitizer other = (Sanitizer)obj;
            boolean result = true;
            result = result && this.alias_ == other.alias_;
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Sanitizer.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.alias_;
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Sanitizer parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Sanitizer parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Sanitizer parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Sanitizer parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Sanitizer parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Sanitizer parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Sanitizer parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Sanitizer parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Sanitizer parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Sanitizer parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Sanitizer parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Sanitizer parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Sanitizer.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Sanitizer prototype) {
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

        public static Sanitizer getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Sanitizer> parser() {
            return PARSER;
        }

        public Parser<Sanitizer> getParserForType() {
            return PARSER;
        }

        @Override
        public Sanitizer getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SanitizerOrBuilder {
            private int alias_ = 0;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Sanitizer_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Sanitizer_fieldAccessorTable.ensureFieldAccessorsInitialized(Sanitizer.class, Builder.class);
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
                this.alias_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Sanitizer_descriptor;
            }

            @Override
            public Sanitizer getDefaultInstanceForType() {
                return Sanitizer.getDefaultInstance();
            }

            @Override
            public Sanitizer build() {
                Sanitizer result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Sanitizer buildPartial() {
                Sanitizer result = new Sanitizer(this);
                result.alias_ = this.alias_;
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
                if (other instanceof Sanitizer) {
                    return this.mergeFrom((Sanitizer)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Sanitizer other) {
                if (other == Sanitizer.getDefaultInstance()) {
                    return this;
                }
                if (other.alias_ != 0) {
                    this.setAliasValue(other.getAliasValue());
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
                Sanitizer parsedMessage = null;
                try {
                    parsedMessage = (Sanitizer)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Sanitizer)e2.getUnfinishedMessage();
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
            public int getAliasValue() {
                return this.alias_;
            }

            public Builder setAliasValue(int value) {
                this.alias_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public SanitizerAlias getAlias() {
                SanitizerAlias result = SanitizerAlias.valueOf(this.alias_);
                return result == null ? SanitizerAlias.UNRECOGNIZED : result;
            }

            public Builder setAlias(SanitizerAlias value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.alias_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearAlias() {
                this.alias_ = 0;
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

        public static enum SanitizerAlias implements ProtocolMessageEnum
        {
            NONE(0),
            HWADDRESS(1),
            UNRECOGNIZED(-1);

            public static final int NONE_VALUE = 0;
            public static final int HWADDRESS_VALUE = 1;
            private static final Internal.EnumLiteMap<SanitizerAlias> internalValueMap;
            private static final SanitizerAlias[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static SanitizerAlias valueOf(int value) {
                return SanitizerAlias.forNumber(value);
            }

            public static SanitizerAlias forNumber(int value) {
                switch (value) {
                    case 0: {
                        return NONE;
                    }
                    case 1: {
                        return HWADDRESS;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<SanitizerAlias> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return SanitizerAlias.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return SanitizerAlias.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Sanitizer.getDescriptor().getEnumTypes().get(0);
            }

            public static SanitizerAlias valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != SanitizerAlias.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private SanitizerAlias(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<SanitizerAlias>(){

                    @Override
                    public SanitizerAlias findValueByNumber(int number) {
                        return SanitizerAlias.forNumber(number);
                    }
                };
                VALUES = SanitizerAlias.values();
            }
        }
    }

    public static interface SanitizerOrBuilder
    extends MessageOrBuilder {
        public int getAliasValue();

        public Sanitizer.SanitizerAlias getAlias();
    }

    public static final class MultiAbi
    extends GeneratedMessageV3
    implements MultiAbiOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ABI_FIELD_NUMBER = 1;
        private List<Abi> abi_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final MultiAbi DEFAULT_INSTANCE = new MultiAbi();
        private static final Parser<MultiAbi> PARSER = new AbstractParser<MultiAbi>(){

            @Override
            public MultiAbi parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MultiAbi(input, extensionRegistry);
            }
        };

        private MultiAbi(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private MultiAbi() {
            this.abi_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MultiAbi(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        this.abi_ = new ArrayList<Abi>();
                        mutable_bitField0_ |= true;
                    }
                    this.abi_.add(input.readMessage(Abi.parser(), extensionRegistry));
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
                    this.abi_ = Collections.unmodifiableList(this.abi_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_MultiAbi_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_MultiAbi_fieldAccessorTable.ensureFieldAccessorsInitialized(MultiAbi.class, Builder.class);
        }

        @Override
        public List<Abi> getAbiList() {
            return this.abi_;
        }

        @Override
        public List<? extends AbiOrBuilder> getAbiOrBuilderList() {
            return this.abi_;
        }

        @Override
        public int getAbiCount() {
            return this.abi_.size();
        }

        @Override
        public Abi getAbi(int index) {
            return this.abi_.get(index);
        }

        @Override
        public AbiOrBuilder getAbiOrBuilder(int index) {
            return this.abi_.get(index);
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
            for (int i2 = 0; i2 < this.abi_.size(); ++i2) {
                output.writeMessage(1, this.abi_.get(i2));
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
            for (int i2 = 0; i2 < this.abi_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(1, this.abi_.get(i2));
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MultiAbi)) {
                return super.equals(obj);
            }
            MultiAbi other = (MultiAbi)obj;
            boolean result = true;
            result = result && this.getAbiList().equals(other.getAbiList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + MultiAbi.getDescriptor().hashCode();
            if (this.getAbiCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getAbiList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static MultiAbi parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MultiAbi parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MultiAbi parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MultiAbi parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MultiAbi parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MultiAbi parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MultiAbi parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MultiAbi parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MultiAbi parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MultiAbi parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MultiAbi parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MultiAbi parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return MultiAbi.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MultiAbi prototype) {
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

        public static MultiAbi getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MultiAbi> parser() {
            return PARSER;
        }

        public Parser<MultiAbi> getParserForType() {
            return PARSER;
        }

        @Override
        public MultiAbi getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements MultiAbiOrBuilder {
            private int bitField0_;
            private List<Abi> abi_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<Abi, Abi.Builder, AbiOrBuilder> abiBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_MultiAbi_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_MultiAbi_fieldAccessorTable.ensureFieldAccessorsInitialized(MultiAbi.class, Builder.class);
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
                    this.getAbiFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.abiBuilder_ == null) {
                    this.abi_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                } else {
                    this.abiBuilder_.clear();
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_MultiAbi_descriptor;
            }

            @Override
            public MultiAbi getDefaultInstanceForType() {
                return MultiAbi.getDefaultInstance();
            }

            @Override
            public MultiAbi build() {
                MultiAbi result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public MultiAbi buildPartial() {
                MultiAbi result = new MultiAbi(this);
                int from_bitField0_ = this.bitField0_;
                if (this.abiBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.abi_ = Collections.unmodifiableList(this.abi_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.abi_ = this.abi_;
                } else {
                    result.abi_ = this.abiBuilder_.build();
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
                if (other instanceof MultiAbi) {
                    return this.mergeFrom((MultiAbi)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MultiAbi other) {
                if (other == MultiAbi.getDefaultInstance()) {
                    return this;
                }
                if (this.abiBuilder_ == null) {
                    if (!other.abi_.isEmpty()) {
                        if (this.abi_.isEmpty()) {
                            this.abi_ = other.abi_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        } else {
                            this.ensureAbiIsMutable();
                            this.abi_.addAll(other.abi_);
                        }
                        this.onChanged();
                    }
                } else if (!other.abi_.isEmpty()) {
                    if (this.abiBuilder_.isEmpty()) {
                        this.abiBuilder_.dispose();
                        this.abiBuilder_ = null;
                        this.abi_ = other.abi_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.abiBuilder_ = alwaysUseFieldBuilders ? this.getAbiFieldBuilder() : null;
                    } else {
                        this.abiBuilder_.addAllMessages(other.abi_);
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
                MultiAbi parsedMessage = null;
                try {
                    parsedMessage = (MultiAbi)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (MultiAbi)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureAbiIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.abi_ = new ArrayList<Abi>(this.abi_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<Abi> getAbiList() {
                if (this.abiBuilder_ == null) {
                    return Collections.unmodifiableList(this.abi_);
                }
                return this.abiBuilder_.getMessageList();
            }

            @Override
            public int getAbiCount() {
                if (this.abiBuilder_ == null) {
                    return this.abi_.size();
                }
                return this.abiBuilder_.getCount();
            }

            @Override
            public Abi getAbi(int index) {
                if (this.abiBuilder_ == null) {
                    return this.abi_.get(index);
                }
                return this.abiBuilder_.getMessage(index);
            }

            public Builder setAbi(int index, Abi value) {
                if (this.abiBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAbiIsMutable();
                    this.abi_.set(index, value);
                    this.onChanged();
                } else {
                    this.abiBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setAbi(int index, Abi.Builder builderForValue) {
                if (this.abiBuilder_ == null) {
                    this.ensureAbiIsMutable();
                    this.abi_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.abiBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAbi(Abi value) {
                if (this.abiBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAbiIsMutable();
                    this.abi_.add(value);
                    this.onChanged();
                } else {
                    this.abiBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addAbi(int index, Abi value) {
                if (this.abiBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureAbiIsMutable();
                    this.abi_.add(index, value);
                    this.onChanged();
                } else {
                    this.abiBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addAbi(Abi.Builder builderForValue) {
                if (this.abiBuilder_ == null) {
                    this.ensureAbiIsMutable();
                    this.abi_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.abiBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addAbi(int index, Abi.Builder builderForValue) {
                if (this.abiBuilder_ == null) {
                    this.ensureAbiIsMutable();
                    this.abi_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.abiBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllAbi(Iterable<? extends Abi> values2) {
                if (this.abiBuilder_ == null) {
                    this.ensureAbiIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.abi_);
                    this.onChanged();
                } else {
                    this.abiBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearAbi() {
                if (this.abiBuilder_ == null) {
                    this.abi_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                } else {
                    this.abiBuilder_.clear();
                }
                return this;
            }

            public Builder removeAbi(int index) {
                if (this.abiBuilder_ == null) {
                    this.ensureAbiIsMutable();
                    this.abi_.remove(index);
                    this.onChanged();
                } else {
                    this.abiBuilder_.remove(index);
                }
                return this;
            }

            public Abi.Builder getAbiBuilder(int index) {
                return this.getAbiFieldBuilder().getBuilder(index);
            }

            @Override
            public AbiOrBuilder getAbiOrBuilder(int index) {
                if (this.abiBuilder_ == null) {
                    return this.abi_.get(index);
                }
                return this.abiBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends AbiOrBuilder> getAbiOrBuilderList() {
                if (this.abiBuilder_ != null) {
                    return this.abiBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.abi_);
            }

            public Abi.Builder addAbiBuilder() {
                return this.getAbiFieldBuilder().addBuilder(Abi.getDefaultInstance());
            }

            public Abi.Builder addAbiBuilder(int index) {
                return this.getAbiFieldBuilder().addBuilder(index, Abi.getDefaultInstance());
            }

            public List<Abi.Builder> getAbiBuilderList() {
                return this.getAbiFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<Abi, Abi.Builder, AbiOrBuilder> getAbiFieldBuilder() {
                if (this.abiBuilder_ == null) {
                    this.abiBuilder_ = new RepeatedFieldBuilderV3(this.abi_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.abi_ = null;
                }
                return this.abiBuilder_;
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

    public static interface MultiAbiOrBuilder
    extends MessageOrBuilder {
        public List<Abi> getAbiList();

        public Abi getAbi(int var1);

        public int getAbiCount();

        public List<? extends AbiOrBuilder> getAbiOrBuilderList();

        public AbiOrBuilder getAbiOrBuilder(int var1);
    }

    public static final class Abi
    extends GeneratedMessageV3
    implements AbiOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ALIAS_FIELD_NUMBER = 1;
        private int alias_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final Abi DEFAULT_INSTANCE = new Abi();
        private static final Parser<Abi> PARSER = new AbstractParser<Abi>(){

            @Override
            public Abi parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new Abi(input, extensionRegistry);
            }
        };

        private Abi(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private Abi() {
            this.alias_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Abi(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.alias_ = rawValue = input.readEnum();
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
            return internal_static_android_bundle_Abi_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_Abi_fieldAccessorTable.ensureFieldAccessorsInitialized(Abi.class, Builder.class);
        }

        @Override
        public int getAliasValue() {
            return this.alias_;
        }

        @Override
        public AbiAlias getAlias() {
            AbiAlias result = AbiAlias.valueOf(this.alias_);
            return result == null ? AbiAlias.UNRECOGNIZED : result;
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
            if (this.alias_ != AbiAlias.UNSPECIFIED_CPU_ARCHITECTURE.getNumber()) {
                output.writeEnum(1, this.alias_);
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
            if (this.alias_ != AbiAlias.UNSPECIFIED_CPU_ARCHITECTURE.getNumber()) {
                size += CodedOutputStream.computeEnumSize(1, this.alias_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Abi)) {
                return super.equals(obj);
            }
            Abi other = (Abi)obj;
            boolean result = true;
            result = result && this.alias_ == other.alias_;
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + Abi.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.alias_;
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static Abi parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Abi parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Abi parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Abi parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Abi parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static Abi parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static Abi parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Abi parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static Abi parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static Abi parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static Abi parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static Abi parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return Abi.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Abi prototype) {
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

        public static Abi getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Abi> parser() {
            return PARSER;
        }

        public Parser<Abi> getParserForType() {
            return PARSER;
        }

        @Override
        public Abi getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements AbiOrBuilder {
            private int alias_ = 0;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_Abi_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_Abi_fieldAccessorTable.ensureFieldAccessorsInitialized(Abi.class, Builder.class);
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
                this.alias_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_Abi_descriptor;
            }

            @Override
            public Abi getDefaultInstanceForType() {
                return Abi.getDefaultInstance();
            }

            @Override
            public Abi build() {
                Abi result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public Abi buildPartial() {
                Abi result = new Abi(this);
                result.alias_ = this.alias_;
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
                if (other instanceof Abi) {
                    return this.mergeFrom((Abi)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Abi other) {
                if (other == Abi.getDefaultInstance()) {
                    return this;
                }
                if (other.alias_ != 0) {
                    this.setAliasValue(other.getAliasValue());
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
                Abi parsedMessage = null;
                try {
                    parsedMessage = (Abi)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (Abi)e2.getUnfinishedMessage();
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
            public int getAliasValue() {
                return this.alias_;
            }

            public Builder setAliasValue(int value) {
                this.alias_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public AbiAlias getAlias() {
                AbiAlias result = AbiAlias.valueOf(this.alias_);
                return result == null ? AbiAlias.UNRECOGNIZED : result;
            }

            public Builder setAlias(AbiAlias value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.alias_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearAlias() {
                this.alias_ = 0;
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

        public static enum AbiAlias implements ProtocolMessageEnum
        {
            UNSPECIFIED_CPU_ARCHITECTURE(0),
            ARMEABI(1),
            ARMEABI_V7A(2),
            ARM64_V8A(3),
            X86(4),
            X86_64(5),
            MIPS(6),
            MIPS64(7),
            UNRECOGNIZED(-1);

            public static final int UNSPECIFIED_CPU_ARCHITECTURE_VALUE = 0;
            public static final int ARMEABI_VALUE = 1;
            public static final int ARMEABI_V7A_VALUE = 2;
            public static final int ARM64_V8A_VALUE = 3;
            public static final int X86_VALUE = 4;
            public static final int X86_64_VALUE = 5;
            public static final int MIPS_VALUE = 6;
            public static final int MIPS64_VALUE = 7;
            private static final Internal.EnumLiteMap<AbiAlias> internalValueMap;
            private static final AbiAlias[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static AbiAlias valueOf(int value) {
                return AbiAlias.forNumber(value);
            }

            public static AbiAlias forNumber(int value) {
                switch (value) {
                    case 0: {
                        return UNSPECIFIED_CPU_ARCHITECTURE;
                    }
                    case 1: {
                        return ARMEABI;
                    }
                    case 2: {
                        return ARMEABI_V7A;
                    }
                    case 3: {
                        return ARM64_V8A;
                    }
                    case 4: {
                        return X86;
                    }
                    case 5: {
                        return X86_64;
                    }
                    case 6: {
                        return MIPS;
                    }
                    case 7: {
                        return MIPS64;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<AbiAlias> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return AbiAlias.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return AbiAlias.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Abi.getDescriptor().getEnumTypes().get(0);
            }

            public static AbiAlias valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != AbiAlias.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private AbiAlias(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<AbiAlias>(){

                    @Override
                    public AbiAlias findValueByNumber(int number) {
                        return AbiAlias.forNumber(number);
                    }
                };
                VALUES = AbiAlias.values();
            }
        }
    }

    public static interface AbiOrBuilder
    extends MessageOrBuilder {
        public int getAliasValue();

        public Abi.AbiAlias getAlias();
    }

    public static final class TextureCompressionFormat
    extends GeneratedMessageV3
    implements TextureCompressionFormatOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ALIAS_FIELD_NUMBER = 1;
        private int alias_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final TextureCompressionFormat DEFAULT_INSTANCE = new TextureCompressionFormat();
        private static final Parser<TextureCompressionFormat> PARSER = new AbstractParser<TextureCompressionFormat>(){

            @Override
            public TextureCompressionFormat parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new TextureCompressionFormat(input, extensionRegistry);
            }
        };

        private TextureCompressionFormat(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private TextureCompressionFormat() {
            this.alias_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private TextureCompressionFormat(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.alias_ = rawValue = input.readEnum();
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
            return internal_static_android_bundle_TextureCompressionFormat_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_TextureCompressionFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(TextureCompressionFormat.class, Builder.class);
        }

        @Override
        public int getAliasValue() {
            return this.alias_;
        }

        @Override
        public TextureCompressionFormatAlias getAlias() {
            TextureCompressionFormatAlias result = TextureCompressionFormatAlias.valueOf(this.alias_);
            return result == null ? TextureCompressionFormatAlias.UNRECOGNIZED : result;
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
            if (this.alias_ != TextureCompressionFormatAlias.UNSPECIFIED_TEXTURE_COMPRESSION_FORMAT.getNumber()) {
                output.writeEnum(1, this.alias_);
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
            if (this.alias_ != TextureCompressionFormatAlias.UNSPECIFIED_TEXTURE_COMPRESSION_FORMAT.getNumber()) {
                size += CodedOutputStream.computeEnumSize(1, this.alias_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof TextureCompressionFormat)) {
                return super.equals(obj);
            }
            TextureCompressionFormat other = (TextureCompressionFormat)obj;
            boolean result = true;
            result = result && this.alias_ == other.alias_;
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + TextureCompressionFormat.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.alias_;
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static TextureCompressionFormat parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TextureCompressionFormat parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TextureCompressionFormat parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TextureCompressionFormat parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TextureCompressionFormat parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static TextureCompressionFormat parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static TextureCompressionFormat parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static TextureCompressionFormat parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static TextureCompressionFormat parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static TextureCompressionFormat parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static TextureCompressionFormat parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static TextureCompressionFormat parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return TextureCompressionFormat.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(TextureCompressionFormat prototype) {
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

        public static TextureCompressionFormat getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TextureCompressionFormat> parser() {
            return PARSER;
        }

        public Parser<TextureCompressionFormat> getParserForType() {
            return PARSER;
        }

        @Override
        public TextureCompressionFormat getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements TextureCompressionFormatOrBuilder {
            private int alias_ = 0;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_TextureCompressionFormat_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_TextureCompressionFormat_fieldAccessorTable.ensureFieldAccessorsInitialized(TextureCompressionFormat.class, Builder.class);
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
                this.alias_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_TextureCompressionFormat_descriptor;
            }

            @Override
            public TextureCompressionFormat getDefaultInstanceForType() {
                return TextureCompressionFormat.getDefaultInstance();
            }

            @Override
            public TextureCompressionFormat build() {
                TextureCompressionFormat result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public TextureCompressionFormat buildPartial() {
                TextureCompressionFormat result = new TextureCompressionFormat(this);
                result.alias_ = this.alias_;
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
                if (other instanceof TextureCompressionFormat) {
                    return this.mergeFrom((TextureCompressionFormat)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(TextureCompressionFormat other) {
                if (other == TextureCompressionFormat.getDefaultInstance()) {
                    return this;
                }
                if (other.alias_ != 0) {
                    this.setAliasValue(other.getAliasValue());
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
                TextureCompressionFormat parsedMessage = null;
                try {
                    parsedMessage = (TextureCompressionFormat)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (TextureCompressionFormat)e2.getUnfinishedMessage();
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
            public int getAliasValue() {
                return this.alias_;
            }

            public Builder setAliasValue(int value) {
                this.alias_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public TextureCompressionFormatAlias getAlias() {
                TextureCompressionFormatAlias result = TextureCompressionFormatAlias.valueOf(this.alias_);
                return result == null ? TextureCompressionFormatAlias.UNRECOGNIZED : result;
            }

            public Builder setAlias(TextureCompressionFormatAlias value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.alias_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearAlias() {
                this.alias_ = 0;
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

        public static enum TextureCompressionFormatAlias implements ProtocolMessageEnum
        {
            UNSPECIFIED_TEXTURE_COMPRESSION_FORMAT(0),
            ETC1_RGB8(1),
            PALETTED(2),
            THREE_DC(3),
            ATC(4),
            LATC(5),
            DXT1(6),
            S3TC(7),
            PVRTC(8),
            ASTC(9),
            ETC2(10),
            UNRECOGNIZED(-1);

            public static final int UNSPECIFIED_TEXTURE_COMPRESSION_FORMAT_VALUE = 0;
            public static final int ETC1_RGB8_VALUE = 1;
            public static final int PALETTED_VALUE = 2;
            public static final int THREE_DC_VALUE = 3;
            public static final int ATC_VALUE = 4;
            public static final int LATC_VALUE = 5;
            public static final int DXT1_VALUE = 6;
            public static final int S3TC_VALUE = 7;
            public static final int PVRTC_VALUE = 8;
            public static final int ASTC_VALUE = 9;
            public static final int ETC2_VALUE = 10;
            private static final Internal.EnumLiteMap<TextureCompressionFormatAlias> internalValueMap;
            private static final TextureCompressionFormatAlias[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static TextureCompressionFormatAlias valueOf(int value) {
                return TextureCompressionFormatAlias.forNumber(value);
            }

            public static TextureCompressionFormatAlias forNumber(int value) {
                switch (value) {
                    case 0: {
                        return UNSPECIFIED_TEXTURE_COMPRESSION_FORMAT;
                    }
                    case 1: {
                        return ETC1_RGB8;
                    }
                    case 2: {
                        return PALETTED;
                    }
                    case 3: {
                        return THREE_DC;
                    }
                    case 4: {
                        return ATC;
                    }
                    case 5: {
                        return LATC;
                    }
                    case 6: {
                        return DXT1;
                    }
                    case 7: {
                        return S3TC;
                    }
                    case 8: {
                        return PVRTC;
                    }
                    case 9: {
                        return ASTC;
                    }
                    case 10: {
                        return ETC2;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<TextureCompressionFormatAlias> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return TextureCompressionFormatAlias.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return TextureCompressionFormatAlias.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return TextureCompressionFormat.getDescriptor().getEnumTypes().get(0);
            }

            public static TextureCompressionFormatAlias valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != TextureCompressionFormatAlias.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private TextureCompressionFormatAlias(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<TextureCompressionFormatAlias>(){

                    @Override
                    public TextureCompressionFormatAlias findValueByNumber(int number) {
                        return TextureCompressionFormatAlias.forNumber(number);
                    }
                };
                VALUES = TextureCompressionFormatAlias.values();
            }
        }
    }

    public static interface TextureCompressionFormatOrBuilder
    extends MessageOrBuilder {
        public int getAliasValue();

        public TextureCompressionFormat.TextureCompressionFormatAlias getAlias();
    }

    public static final class OpenGlVersion
    extends GeneratedMessageV3
    implements OpenGlVersionOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MAJOR_FIELD_NUMBER = 1;
        private int major_;
        public static final int MINOR_FIELD_NUMBER = 2;
        private int minor_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final OpenGlVersion DEFAULT_INSTANCE = new OpenGlVersion();
        private static final Parser<OpenGlVersion> PARSER = new AbstractParser<OpenGlVersion>(){

            @Override
            public OpenGlVersion parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new OpenGlVersion(input, extensionRegistry);
            }
        };

        private OpenGlVersion(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private OpenGlVersion() {
            this.major_ = 0;
            this.minor_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private OpenGlVersion(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.major_ = input.readInt32();
                            continue block11;
                        }
                        case 16: 
                    }
                    this.minor_ = input.readInt32();
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
            return internal_static_android_bundle_OpenGlVersion_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_OpenGlVersion_fieldAccessorTable.ensureFieldAccessorsInitialized(OpenGlVersion.class, Builder.class);
        }

        @Override
        public int getMajor() {
            return this.major_;
        }

        @Override
        public int getMinor() {
            return this.minor_;
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
            if (this.major_ != 0) {
                output.writeInt32(1, this.major_);
            }
            if (this.minor_ != 0) {
                output.writeInt32(2, this.minor_);
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
            if (this.major_ != 0) {
                size += CodedOutputStream.computeInt32Size(1, this.major_);
            }
            if (this.minor_ != 0) {
                size += CodedOutputStream.computeInt32Size(2, this.minor_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof OpenGlVersion)) {
                return super.equals(obj);
            }
            OpenGlVersion other = (OpenGlVersion)obj;
            boolean result = true;
            result = result && this.getMajor() == other.getMajor();
            result = result && this.getMinor() == other.getMinor();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + OpenGlVersion.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMajor();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getMinor();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static OpenGlVersion parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static OpenGlVersion parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static OpenGlVersion parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static OpenGlVersion parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static OpenGlVersion parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static OpenGlVersion parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static OpenGlVersion parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OpenGlVersion parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static OpenGlVersion parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static OpenGlVersion parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static OpenGlVersion parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static OpenGlVersion parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return OpenGlVersion.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(OpenGlVersion prototype) {
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

        public static OpenGlVersion getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OpenGlVersion> parser() {
            return PARSER;
        }

        public Parser<OpenGlVersion> getParserForType() {
            return PARSER;
        }

        @Override
        public OpenGlVersion getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements OpenGlVersionOrBuilder {
            private int major_;
            private int minor_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_OpenGlVersion_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_OpenGlVersion_fieldAccessorTable.ensureFieldAccessorsInitialized(OpenGlVersion.class, Builder.class);
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
                this.major_ = 0;
                this.minor_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_OpenGlVersion_descriptor;
            }

            @Override
            public OpenGlVersion getDefaultInstanceForType() {
                return OpenGlVersion.getDefaultInstance();
            }

            @Override
            public OpenGlVersion build() {
                OpenGlVersion result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public OpenGlVersion buildPartial() {
                OpenGlVersion result = new OpenGlVersion(this);
                result.major_ = this.major_;
                result.minor_ = this.minor_;
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
                if (other instanceof OpenGlVersion) {
                    return this.mergeFrom((OpenGlVersion)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(OpenGlVersion other) {
                if (other == OpenGlVersion.getDefaultInstance()) {
                    return this;
                }
                if (other.getMajor() != 0) {
                    this.setMajor(other.getMajor());
                }
                if (other.getMinor() != 0) {
                    this.setMinor(other.getMinor());
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
                OpenGlVersion parsedMessage = null;
                try {
                    parsedMessage = (OpenGlVersion)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (OpenGlVersion)e2.getUnfinishedMessage();
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
            public int getMajor() {
                return this.major_;
            }

            public Builder setMajor(int value) {
                this.major_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMajor() {
                this.major_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getMinor() {
                return this.minor_;
            }

            public Builder setMinor(int value) {
                this.minor_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMinor() {
                this.minor_ = 0;
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

    public static interface OpenGlVersionOrBuilder
    extends MessageOrBuilder {
        public int getMajor();

        public int getMinor();
    }

    public static final class VulkanVersion
    extends GeneratedMessageV3
    implements VulkanVersionOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MAJOR_FIELD_NUMBER = 1;
        private int major_;
        public static final int MINOR_FIELD_NUMBER = 2;
        private int minor_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final VulkanVersion DEFAULT_INSTANCE = new VulkanVersion();
        private static final Parser<VulkanVersion> PARSER = new AbstractParser<VulkanVersion>(){

            @Override
            public VulkanVersion parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new VulkanVersion(input, extensionRegistry);
            }
        };

        private VulkanVersion(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private VulkanVersion() {
            this.major_ = 0;
            this.minor_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private VulkanVersion(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.major_ = input.readInt32();
                            continue block11;
                        }
                        case 16: 
                    }
                    this.minor_ = input.readInt32();
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
            return internal_static_android_bundle_VulkanVersion_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_VulkanVersion_fieldAccessorTable.ensureFieldAccessorsInitialized(VulkanVersion.class, Builder.class);
        }

        @Override
        public int getMajor() {
            return this.major_;
        }

        @Override
        public int getMinor() {
            return this.minor_;
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
            if (this.major_ != 0) {
                output.writeInt32(1, this.major_);
            }
            if (this.minor_ != 0) {
                output.writeInt32(2, this.minor_);
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
            if (this.major_ != 0) {
                size += CodedOutputStream.computeInt32Size(1, this.major_);
            }
            if (this.minor_ != 0) {
                size += CodedOutputStream.computeInt32Size(2, this.minor_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof VulkanVersion)) {
                return super.equals(obj);
            }
            VulkanVersion other = (VulkanVersion)obj;
            boolean result = true;
            result = result && this.getMajor() == other.getMajor();
            result = result && this.getMinor() == other.getMinor();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + VulkanVersion.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMajor();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getMinor();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static VulkanVersion parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static VulkanVersion parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static VulkanVersion parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static VulkanVersion parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static VulkanVersion parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static VulkanVersion parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static VulkanVersion parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static VulkanVersion parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static VulkanVersion parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static VulkanVersion parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static VulkanVersion parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static VulkanVersion parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return VulkanVersion.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(VulkanVersion prototype) {
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

        public static VulkanVersion getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<VulkanVersion> parser() {
            return PARSER;
        }

        public Parser<VulkanVersion> getParserForType() {
            return PARSER;
        }

        @Override
        public VulkanVersion getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements VulkanVersionOrBuilder {
            private int major_;
            private int minor_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_VulkanVersion_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_VulkanVersion_fieldAccessorTable.ensureFieldAccessorsInitialized(VulkanVersion.class, Builder.class);
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
                this.major_ = 0;
                this.minor_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_VulkanVersion_descriptor;
            }

            @Override
            public VulkanVersion getDefaultInstanceForType() {
                return VulkanVersion.getDefaultInstance();
            }

            @Override
            public VulkanVersion build() {
                VulkanVersion result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public VulkanVersion buildPartial() {
                VulkanVersion result = new VulkanVersion(this);
                result.major_ = this.major_;
                result.minor_ = this.minor_;
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
                if (other instanceof VulkanVersion) {
                    return this.mergeFrom((VulkanVersion)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(VulkanVersion other) {
                if (other == VulkanVersion.getDefaultInstance()) {
                    return this;
                }
                if (other.getMajor() != 0) {
                    this.setMajor(other.getMajor());
                }
                if (other.getMinor() != 0) {
                    this.setMinor(other.getMinor());
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
                VulkanVersion parsedMessage = null;
                try {
                    parsedMessage = (VulkanVersion)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (VulkanVersion)e2.getUnfinishedMessage();
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
            public int getMajor() {
                return this.major_;
            }

            public Builder setMajor(int value) {
                this.major_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMajor() {
                this.major_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getMinor() {
                return this.minor_;
            }

            public Builder setMinor(int value) {
                this.minor_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMinor() {
                this.minor_ = 0;
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

    public static interface VulkanVersionOrBuilder
    extends MessageOrBuilder {
        public int getMajor();

        public int getMinor();
    }

    public static final class GraphicsApi
    extends GeneratedMessageV3
    implements GraphicsApiOrBuilder {
        private static final long serialVersionUID = 0L;
        private int apiOneofCase_ = 0;
        private Object apiOneof_;
        public static final int MIN_OPEN_GL_VERSION_FIELD_NUMBER = 1;
        public static final int MIN_VULKAN_VERSION_FIELD_NUMBER = 2;
        private byte memoizedIsInitialized = (byte)-1;
        private static final GraphicsApi DEFAULT_INSTANCE = new GraphicsApi();
        private static final Parser<GraphicsApi> PARSER = new AbstractParser<GraphicsApi>(){

            @Override
            public GraphicsApi parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new GraphicsApi(input, extensionRegistry);
            }
        };

        private GraphicsApi(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private GraphicsApi() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private GraphicsApi(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block11;
                            done = true;
                            continue block11;
                        }
                        case 10: {
                            subBuilder = null;
                            if (this.apiOneofCase_ == 1) {
                                subBuilder = ((OpenGlVersion)this.apiOneof_).toBuilder();
                            }
                            this.apiOneof_ = input.readMessage(OpenGlVersion.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((OpenGlVersion.Builder)subBuilder).mergeFrom((OpenGlVersion)this.apiOneof_);
                                this.apiOneof_ = ((OpenGlVersion.Builder)subBuilder).buildPartial();
                            }
                            this.apiOneofCase_ = 1;
                            continue block11;
                        }
                        case 18: 
                    }
                    subBuilder = null;
                    if (this.apiOneofCase_ == 2) {
                        subBuilder = ((VulkanVersion)this.apiOneof_).toBuilder();
                    }
                    this.apiOneof_ = input.readMessage(VulkanVersion.parser(), extensionRegistry);
                    if (subBuilder != null) {
                        ((VulkanVersion.Builder)subBuilder).mergeFrom((VulkanVersion)this.apiOneof_);
                        this.apiOneof_ = ((VulkanVersion.Builder)subBuilder).buildPartial();
                    }
                    this.apiOneofCase_ = 2;
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
            return internal_static_android_bundle_GraphicsApi_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_GraphicsApi_fieldAccessorTable.ensureFieldAccessorsInitialized(GraphicsApi.class, Builder.class);
        }

        @Override
        public ApiOneofCase getApiOneofCase() {
            return ApiOneofCase.forNumber(this.apiOneofCase_);
        }

        @Override
        public boolean hasMinOpenGlVersion() {
            return this.apiOneofCase_ == 1;
        }

        @Override
        public OpenGlVersion getMinOpenGlVersion() {
            if (this.apiOneofCase_ == 1) {
                return (OpenGlVersion)this.apiOneof_;
            }
            return OpenGlVersion.getDefaultInstance();
        }

        @Override
        public OpenGlVersionOrBuilder getMinOpenGlVersionOrBuilder() {
            if (this.apiOneofCase_ == 1) {
                return (OpenGlVersion)this.apiOneof_;
            }
            return OpenGlVersion.getDefaultInstance();
        }

        @Override
        public boolean hasMinVulkanVersion() {
            return this.apiOneofCase_ == 2;
        }

        @Override
        public VulkanVersion getMinVulkanVersion() {
            if (this.apiOneofCase_ == 2) {
                return (VulkanVersion)this.apiOneof_;
            }
            return VulkanVersion.getDefaultInstance();
        }

        @Override
        public VulkanVersionOrBuilder getMinVulkanVersionOrBuilder() {
            if (this.apiOneofCase_ == 2) {
                return (VulkanVersion)this.apiOneof_;
            }
            return VulkanVersion.getDefaultInstance();
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
            if (this.apiOneofCase_ == 1) {
                output.writeMessage(1, (OpenGlVersion)this.apiOneof_);
            }
            if (this.apiOneofCase_ == 2) {
                output.writeMessage(2, (VulkanVersion)this.apiOneof_);
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
            if (this.apiOneofCase_ == 1) {
                size += CodedOutputStream.computeMessageSize(1, (OpenGlVersion)this.apiOneof_);
            }
            if (this.apiOneofCase_ == 2) {
                size += CodedOutputStream.computeMessageSize(2, (VulkanVersion)this.apiOneof_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GraphicsApi)) {
                return super.equals(obj);
            }
            GraphicsApi other = (GraphicsApi)obj;
            boolean result = true;
            boolean bl = result = result && this.getApiOneofCase().equals(other.getApiOneofCase());
            if (!result) {
                return false;
            }
            switch (this.apiOneofCase_) {
                case 1: {
                    result = result && this.getMinOpenGlVersion().equals(other.getMinOpenGlVersion());
                    break;
                }
                case 2: {
                    result = result && this.getMinVulkanVersion().equals(other.getMinVulkanVersion());
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
            hash = 19 * hash + GraphicsApi.getDescriptor().hashCode();
            switch (this.apiOneofCase_) {
                case 1: {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getMinOpenGlVersion().hashCode();
                    break;
                }
                case 2: {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getMinVulkanVersion().hashCode();
                    break;
                }
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static GraphicsApi parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GraphicsApi parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GraphicsApi parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GraphicsApi parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GraphicsApi parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static GraphicsApi parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static GraphicsApi parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GraphicsApi parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static GraphicsApi parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static GraphicsApi parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static GraphicsApi parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static GraphicsApi parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return GraphicsApi.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(GraphicsApi prototype) {
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

        public static GraphicsApi getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GraphicsApi> parser() {
            return PARSER;
        }

        public Parser<GraphicsApi> getParserForType() {
            return PARSER;
        }

        @Override
        public GraphicsApi getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements GraphicsApiOrBuilder {
            private int apiOneofCase_ = 0;
            private Object apiOneof_;
            private SingleFieldBuilderV3<OpenGlVersion, OpenGlVersion.Builder, OpenGlVersionOrBuilder> minOpenGlVersionBuilder_;
            private SingleFieldBuilderV3<VulkanVersion, VulkanVersion.Builder, VulkanVersionOrBuilder> minVulkanVersionBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_GraphicsApi_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_GraphicsApi_fieldAccessorTable.ensureFieldAccessorsInitialized(GraphicsApi.class, Builder.class);
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
                this.apiOneofCase_ = 0;
                this.apiOneof_ = null;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_GraphicsApi_descriptor;
            }

            @Override
            public GraphicsApi getDefaultInstanceForType() {
                return GraphicsApi.getDefaultInstance();
            }

            @Override
            public GraphicsApi build() {
                GraphicsApi result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public GraphicsApi buildPartial() {
                GraphicsApi result = new GraphicsApi(this);
                if (this.apiOneofCase_ == 1) {
                    if (this.minOpenGlVersionBuilder_ == null) {
                        result.apiOneof_ = this.apiOneof_;
                    } else {
                        result.apiOneof_ = this.minOpenGlVersionBuilder_.build();
                    }
                }
                if (this.apiOneofCase_ == 2) {
                    if (this.minVulkanVersionBuilder_ == null) {
                        result.apiOneof_ = this.apiOneof_;
                    } else {
                        result.apiOneof_ = this.minVulkanVersionBuilder_.build();
                    }
                }
                result.apiOneofCase_ = this.apiOneofCase_;
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
                if (other instanceof GraphicsApi) {
                    return this.mergeFrom((GraphicsApi)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(GraphicsApi other) {
                if (other == GraphicsApi.getDefaultInstance()) {
                    return this;
                }
                switch (other.getApiOneofCase()) {
                    case MIN_OPEN_GL_VERSION: {
                        this.mergeMinOpenGlVersion(other.getMinOpenGlVersion());
                        break;
                    }
                    case MIN_VULKAN_VERSION: {
                        this.mergeMinVulkanVersion(other.getMinVulkanVersion());
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
                GraphicsApi parsedMessage = null;
                try {
                    parsedMessage = (GraphicsApi)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (GraphicsApi)e2.getUnfinishedMessage();
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
            public ApiOneofCase getApiOneofCase() {
                return ApiOneofCase.forNumber(this.apiOneofCase_);
            }

            public Builder clearApiOneof() {
                this.apiOneofCase_ = 0;
                this.apiOneof_ = null;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasMinOpenGlVersion() {
                return this.apiOneofCase_ == 1;
            }

            @Override
            public OpenGlVersion getMinOpenGlVersion() {
                if (this.minOpenGlVersionBuilder_ == null) {
                    if (this.apiOneofCase_ == 1) {
                        return (OpenGlVersion)this.apiOneof_;
                    }
                    return OpenGlVersion.getDefaultInstance();
                }
                if (this.apiOneofCase_ == 1) {
                    return this.minOpenGlVersionBuilder_.getMessage();
                }
                return OpenGlVersion.getDefaultInstance();
            }

            public Builder setMinOpenGlVersion(OpenGlVersion value) {
                if (this.minOpenGlVersionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.apiOneof_ = value;
                    this.onChanged();
                } else {
                    this.minOpenGlVersionBuilder_.setMessage(value);
                }
                this.apiOneofCase_ = 1;
                return this;
            }

            public Builder setMinOpenGlVersion(OpenGlVersion.Builder builderForValue) {
                if (this.minOpenGlVersionBuilder_ == null) {
                    this.apiOneof_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.minOpenGlVersionBuilder_.setMessage(builderForValue.build());
                }
                this.apiOneofCase_ = 1;
                return this;
            }

            public Builder mergeMinOpenGlVersion(OpenGlVersion value) {
                if (this.minOpenGlVersionBuilder_ == null) {
                    this.apiOneof_ = this.apiOneofCase_ == 1 && this.apiOneof_ != OpenGlVersion.getDefaultInstance() ? OpenGlVersion.newBuilder((OpenGlVersion)this.apiOneof_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.apiOneofCase_ == 1) {
                        this.minOpenGlVersionBuilder_.mergeFrom(value);
                    }
                    this.minOpenGlVersionBuilder_.setMessage(value);
                }
                this.apiOneofCase_ = 1;
                return this;
            }

            public Builder clearMinOpenGlVersion() {
                if (this.minOpenGlVersionBuilder_ == null) {
                    if (this.apiOneofCase_ == 1) {
                        this.apiOneofCase_ = 0;
                        this.apiOneof_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.apiOneofCase_ == 1) {
                        this.apiOneofCase_ = 0;
                        this.apiOneof_ = null;
                    }
                    this.minOpenGlVersionBuilder_.clear();
                }
                return this;
            }

            public OpenGlVersion.Builder getMinOpenGlVersionBuilder() {
                return this.getMinOpenGlVersionFieldBuilder().getBuilder();
            }

            @Override
            public OpenGlVersionOrBuilder getMinOpenGlVersionOrBuilder() {
                if (this.apiOneofCase_ == 1 && this.minOpenGlVersionBuilder_ != null) {
                    return this.minOpenGlVersionBuilder_.getMessageOrBuilder();
                }
                if (this.apiOneofCase_ == 1) {
                    return (OpenGlVersion)this.apiOneof_;
                }
                return OpenGlVersion.getDefaultInstance();
            }

            private SingleFieldBuilderV3<OpenGlVersion, OpenGlVersion.Builder, OpenGlVersionOrBuilder> getMinOpenGlVersionFieldBuilder() {
                if (this.minOpenGlVersionBuilder_ == null) {
                    if (this.apiOneofCase_ != 1) {
                        this.apiOneof_ = OpenGlVersion.getDefaultInstance();
                    }
                    this.minOpenGlVersionBuilder_ = new SingleFieldBuilderV3((OpenGlVersion)this.apiOneof_, this.getParentForChildren(), this.isClean());
                    this.apiOneof_ = null;
                }
                this.apiOneofCase_ = 1;
                this.onChanged();
                return this.minOpenGlVersionBuilder_;
            }

            @Override
            public boolean hasMinVulkanVersion() {
                return this.apiOneofCase_ == 2;
            }

            @Override
            public VulkanVersion getMinVulkanVersion() {
                if (this.minVulkanVersionBuilder_ == null) {
                    if (this.apiOneofCase_ == 2) {
                        return (VulkanVersion)this.apiOneof_;
                    }
                    return VulkanVersion.getDefaultInstance();
                }
                if (this.apiOneofCase_ == 2) {
                    return this.minVulkanVersionBuilder_.getMessage();
                }
                return VulkanVersion.getDefaultInstance();
            }

            public Builder setMinVulkanVersion(VulkanVersion value) {
                if (this.minVulkanVersionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.apiOneof_ = value;
                    this.onChanged();
                } else {
                    this.minVulkanVersionBuilder_.setMessage(value);
                }
                this.apiOneofCase_ = 2;
                return this;
            }

            public Builder setMinVulkanVersion(VulkanVersion.Builder builderForValue) {
                if (this.minVulkanVersionBuilder_ == null) {
                    this.apiOneof_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.minVulkanVersionBuilder_.setMessage(builderForValue.build());
                }
                this.apiOneofCase_ = 2;
                return this;
            }

            public Builder mergeMinVulkanVersion(VulkanVersion value) {
                if (this.minVulkanVersionBuilder_ == null) {
                    this.apiOneof_ = this.apiOneofCase_ == 2 && this.apiOneof_ != VulkanVersion.getDefaultInstance() ? VulkanVersion.newBuilder((VulkanVersion)this.apiOneof_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.apiOneofCase_ == 2) {
                        this.minVulkanVersionBuilder_.mergeFrom(value);
                    }
                    this.minVulkanVersionBuilder_.setMessage(value);
                }
                this.apiOneofCase_ = 2;
                return this;
            }

            public Builder clearMinVulkanVersion() {
                if (this.minVulkanVersionBuilder_ == null) {
                    if (this.apiOneofCase_ == 2) {
                        this.apiOneofCase_ = 0;
                        this.apiOneof_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.apiOneofCase_ == 2) {
                        this.apiOneofCase_ = 0;
                        this.apiOneof_ = null;
                    }
                    this.minVulkanVersionBuilder_.clear();
                }
                return this;
            }

            public VulkanVersion.Builder getMinVulkanVersionBuilder() {
                return this.getMinVulkanVersionFieldBuilder().getBuilder();
            }

            @Override
            public VulkanVersionOrBuilder getMinVulkanVersionOrBuilder() {
                if (this.apiOneofCase_ == 2 && this.minVulkanVersionBuilder_ != null) {
                    return this.minVulkanVersionBuilder_.getMessageOrBuilder();
                }
                if (this.apiOneofCase_ == 2) {
                    return (VulkanVersion)this.apiOneof_;
                }
                return VulkanVersion.getDefaultInstance();
            }

            private SingleFieldBuilderV3<VulkanVersion, VulkanVersion.Builder, VulkanVersionOrBuilder> getMinVulkanVersionFieldBuilder() {
                if (this.minVulkanVersionBuilder_ == null) {
                    if (this.apiOneofCase_ != 2) {
                        this.apiOneof_ = VulkanVersion.getDefaultInstance();
                    }
                    this.minVulkanVersionBuilder_ = new SingleFieldBuilderV3((VulkanVersion)this.apiOneof_, this.getParentForChildren(), this.isClean());
                    this.apiOneof_ = null;
                }
                this.apiOneofCase_ = 2;
                this.onChanged();
                return this.minVulkanVersionBuilder_;
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

        public static enum ApiOneofCase implements Internal.EnumLite
        {
            MIN_OPEN_GL_VERSION(1),
            MIN_VULKAN_VERSION(2),
            APIONEOF_NOT_SET(0);

            private final int value;

            private ApiOneofCase(int value) {
                this.value = value;
            }

            @Deprecated
            public static ApiOneofCase valueOf(int value) {
                return ApiOneofCase.forNumber(value);
            }

            public static ApiOneofCase forNumber(int value) {
                switch (value) {
                    case 1: {
                        return MIN_OPEN_GL_VERSION;
                    }
                    case 2: {
                        return MIN_VULKAN_VERSION;
                    }
                    case 0: {
                        return APIONEOF_NOT_SET;
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

    public static interface GraphicsApiOrBuilder
    extends MessageOrBuilder {
        public boolean hasMinOpenGlVersion();

        public OpenGlVersion getMinOpenGlVersion();

        public OpenGlVersionOrBuilder getMinOpenGlVersionOrBuilder();

        public boolean hasMinVulkanVersion();

        public VulkanVersion getMinVulkanVersion();

        public VulkanVersionOrBuilder getMinVulkanVersionOrBuilder();

        public GraphicsApi.ApiOneofCase getApiOneofCase();
    }

    public static final class SdkVersion
    extends GeneratedMessageV3
    implements SdkVersionOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MIN_FIELD_NUMBER = 1;
        private Int32Value min_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final SdkVersion DEFAULT_INSTANCE = new SdkVersion();
        private static final Parser<SdkVersion> PARSER = new AbstractParser<SdkVersion>(){

            @Override
            public SdkVersion parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SdkVersion(input, extensionRegistry);
            }
        };

        private SdkVersion(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private SdkVersion() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SdkVersion(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    Int32Value.Builder subBuilder = null;
                    if (this.min_ != null) {
                        subBuilder = this.min_.toBuilder();
                    }
                    this.min_ = input.readMessage(Int32Value.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    subBuilder.mergeFrom(this.min_);
                    this.min_ = subBuilder.buildPartial();
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
            return internal_static_android_bundle_SdkVersion_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_SdkVersion_fieldAccessorTable.ensureFieldAccessorsInitialized(SdkVersion.class, Builder.class);
        }

        @Override
        public boolean hasMin() {
            return this.min_ != null;
        }

        @Override
        public Int32Value getMin() {
            return this.min_ == null ? Int32Value.getDefaultInstance() : this.min_;
        }

        @Override
        public Int32ValueOrBuilder getMinOrBuilder() {
            return this.getMin();
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
            if (this.min_ != null) {
                output.writeMessage(1, this.getMin());
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
            if (this.min_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getMin());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SdkVersion)) {
                return super.equals(obj);
            }
            SdkVersion other = (SdkVersion)obj;
            boolean result = true;
            boolean bl = result = result && this.hasMin() == other.hasMin();
            if (this.hasMin()) {
                result = result && this.getMin().equals(other.getMin());
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
            hash = 19 * hash + SdkVersion.getDescriptor().hashCode();
            if (this.hasMin()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getMin().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static SdkVersion parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SdkVersion parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SdkVersion parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SdkVersion parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SdkVersion parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static SdkVersion parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static SdkVersion parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SdkVersion parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static SdkVersion parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static SdkVersion parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static SdkVersion parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static SdkVersion parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return SdkVersion.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(SdkVersion prototype) {
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

        public static SdkVersion getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SdkVersion> parser() {
            return PARSER;
        }

        public Parser<SdkVersion> getParserForType() {
            return PARSER;
        }

        @Override
        public SdkVersion getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements SdkVersionOrBuilder {
            private Int32Value min_ = null;
            private SingleFieldBuilderV3<Int32Value, Int32Value.Builder, Int32ValueOrBuilder> minBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_SdkVersion_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_SdkVersion_fieldAccessorTable.ensureFieldAccessorsInitialized(SdkVersion.class, Builder.class);
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
                if (this.minBuilder_ == null) {
                    this.min_ = null;
                } else {
                    this.min_ = null;
                    this.minBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_SdkVersion_descriptor;
            }

            @Override
            public SdkVersion getDefaultInstanceForType() {
                return SdkVersion.getDefaultInstance();
            }

            @Override
            public SdkVersion build() {
                SdkVersion result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public SdkVersion buildPartial() {
                SdkVersion result = new SdkVersion(this);
                if (this.minBuilder_ == null) {
                    result.min_ = this.min_;
                } else {
                    result.min_ = this.minBuilder_.build();
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
                if (other instanceof SdkVersion) {
                    return this.mergeFrom((SdkVersion)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SdkVersion other) {
                if (other == SdkVersion.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMin()) {
                    this.mergeMin(other.getMin());
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
                SdkVersion parsedMessage = null;
                try {
                    parsedMessage = (SdkVersion)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (SdkVersion)e2.getUnfinishedMessage();
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
            public boolean hasMin() {
                return this.minBuilder_ != null || this.min_ != null;
            }

            @Override
            public Int32Value getMin() {
                if (this.minBuilder_ == null) {
                    return this.min_ == null ? Int32Value.getDefaultInstance() : this.min_;
                }
                return this.minBuilder_.getMessage();
            }

            public Builder setMin(Int32Value value) {
                if (this.minBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.min_ = value;
                    this.onChanged();
                } else {
                    this.minBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setMin(Int32Value.Builder builderForValue) {
                if (this.minBuilder_ == null) {
                    this.min_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.minBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeMin(Int32Value value) {
                if (this.minBuilder_ == null) {
                    this.min_ = this.min_ != null ? Int32Value.newBuilder(this.min_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.minBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearMin() {
                if (this.minBuilder_ == null) {
                    this.min_ = null;
                    this.onChanged();
                } else {
                    this.min_ = null;
                    this.minBuilder_ = null;
                }
                return this;
            }

            public Int32Value.Builder getMinBuilder() {
                this.onChanged();
                return this.getMinFieldBuilder().getBuilder();
            }

            @Override
            public Int32ValueOrBuilder getMinOrBuilder() {
                if (this.minBuilder_ != null) {
                    return this.minBuilder_.getMessageOrBuilder();
                }
                return this.min_ == null ? Int32Value.getDefaultInstance() : this.min_;
            }

            private SingleFieldBuilderV3<Int32Value, Int32Value.Builder, Int32ValueOrBuilder> getMinFieldBuilder() {
                if (this.minBuilder_ == null) {
                    this.minBuilder_ = new SingleFieldBuilderV3(this.getMin(), this.getParentForChildren(), this.isClean());
                    this.min_ = null;
                }
                return this.minBuilder_;
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

    public static interface SdkVersionOrBuilder
    extends MessageOrBuilder {
        public boolean hasMin();

        public Int32Value getMin();

        public Int32ValueOrBuilder getMinOrBuilder();
    }

    public static final class ScreenDensity
    extends GeneratedMessageV3
    implements ScreenDensityOrBuilder {
        private static final long serialVersionUID = 0L;
        private int densityOneofCase_ = 0;
        private Object densityOneof_;
        public static final int DENSITY_ALIAS_FIELD_NUMBER = 1;
        public static final int DENSITY_DPI_FIELD_NUMBER = 2;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ScreenDensity DEFAULT_INSTANCE = new ScreenDensity();
        private static final Parser<ScreenDensity> PARSER = new AbstractParser<ScreenDensity>(){

            @Override
            public ScreenDensity parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ScreenDensity(input, extensionRegistry);
            }
        };

        private ScreenDensity(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ScreenDensity() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ScreenDensity(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            int rawValue = input.readEnum();
                            this.densityOneofCase_ = 1;
                            this.densityOneof_ = rawValue;
                            continue block11;
                        }
                        case 16: 
                    }
                    this.densityOneofCase_ = 2;
                    this.densityOneof_ = input.readInt32();
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
            return internal_static_android_bundle_ScreenDensity_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ScreenDensity_fieldAccessorTable.ensureFieldAccessorsInitialized(ScreenDensity.class, Builder.class);
        }

        @Override
        public DensityOneofCase getDensityOneofCase() {
            return DensityOneofCase.forNumber(this.densityOneofCase_);
        }

        @Override
        public int getDensityAliasValue() {
            if (this.densityOneofCase_ == 1) {
                return (Integer)this.densityOneof_;
            }
            return 0;
        }

        @Override
        public DensityAlias getDensityAlias() {
            if (this.densityOneofCase_ == 1) {
                DensityAlias result = DensityAlias.valueOf((Integer)this.densityOneof_);
                return result == null ? DensityAlias.UNRECOGNIZED : result;
            }
            return DensityAlias.DENSITY_UNSPECIFIED;
        }

        @Override
        public int getDensityDpi() {
            if (this.densityOneofCase_ == 2) {
                return (Integer)this.densityOneof_;
            }
            return 0;
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
            if (this.densityOneofCase_ == 1) {
                output.writeEnum(1, (Integer)this.densityOneof_);
            }
            if (this.densityOneofCase_ == 2) {
                output.writeInt32(2, (Integer)this.densityOneof_);
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
            if (this.densityOneofCase_ == 1) {
                size += CodedOutputStream.computeEnumSize(1, (Integer)this.densityOneof_);
            }
            if (this.densityOneofCase_ == 2) {
                size += CodedOutputStream.computeInt32Size(2, (Integer)this.densityOneof_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ScreenDensity)) {
                return super.equals(obj);
            }
            ScreenDensity other = (ScreenDensity)obj;
            boolean result = true;
            boolean bl = result = result && this.getDensityOneofCase().equals(other.getDensityOneofCase());
            if (!result) {
                return false;
            }
            switch (this.densityOneofCase_) {
                case 1: {
                    result = result && this.getDensityAliasValue() == other.getDensityAliasValue();
                    break;
                }
                case 2: {
                    result = result && this.getDensityDpi() == other.getDensityDpi();
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
            hash = 19 * hash + ScreenDensity.getDescriptor().hashCode();
            switch (this.densityOneofCase_) {
                case 1: {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getDensityAliasValue();
                    break;
                }
                case 2: {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getDensityDpi();
                    break;
                }
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ScreenDensity parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ScreenDensity parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ScreenDensity parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ScreenDensity parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ScreenDensity parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ScreenDensity parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ScreenDensity parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ScreenDensity parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ScreenDensity parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ScreenDensity parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ScreenDensity parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ScreenDensity parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ScreenDensity.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ScreenDensity prototype) {
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

        public static ScreenDensity getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ScreenDensity> parser() {
            return PARSER;
        }

        public Parser<ScreenDensity> getParserForType() {
            return PARSER;
        }

        @Override
        public ScreenDensity getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ScreenDensityOrBuilder {
            private int densityOneofCase_ = 0;
            private Object densityOneof_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ScreenDensity_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ScreenDensity_fieldAccessorTable.ensureFieldAccessorsInitialized(ScreenDensity.class, Builder.class);
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
                this.densityOneofCase_ = 0;
                this.densityOneof_ = null;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ScreenDensity_descriptor;
            }

            @Override
            public ScreenDensity getDefaultInstanceForType() {
                return ScreenDensity.getDefaultInstance();
            }

            @Override
            public ScreenDensity build() {
                ScreenDensity result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ScreenDensity buildPartial() {
                ScreenDensity result = new ScreenDensity(this);
                if (this.densityOneofCase_ == 1) {
                    result.densityOneof_ = this.densityOneof_;
                }
                if (this.densityOneofCase_ == 2) {
                    result.densityOneof_ = this.densityOneof_;
                }
                result.densityOneofCase_ = this.densityOneofCase_;
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
                if (other instanceof ScreenDensity) {
                    return this.mergeFrom((ScreenDensity)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ScreenDensity other) {
                if (other == ScreenDensity.getDefaultInstance()) {
                    return this;
                }
                switch (other.getDensityOneofCase()) {
                    case DENSITY_ALIAS: {
                        this.setDensityAliasValue(other.getDensityAliasValue());
                        break;
                    }
                    case DENSITY_DPI: {
                        this.setDensityDpi(other.getDensityDpi());
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
                ScreenDensity parsedMessage = null;
                try {
                    parsedMessage = (ScreenDensity)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ScreenDensity)e2.getUnfinishedMessage();
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
            public DensityOneofCase getDensityOneofCase() {
                return DensityOneofCase.forNumber(this.densityOneofCase_);
            }

            public Builder clearDensityOneof() {
                this.densityOneofCase_ = 0;
                this.densityOneof_ = null;
                this.onChanged();
                return this;
            }

            @Override
            public int getDensityAliasValue() {
                if (this.densityOneofCase_ == 1) {
                    return (Integer)this.densityOneof_;
                }
                return 0;
            }

            public Builder setDensityAliasValue(int value) {
                this.densityOneofCase_ = 1;
                this.densityOneof_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public DensityAlias getDensityAlias() {
                if (this.densityOneofCase_ == 1) {
                    DensityAlias result = DensityAlias.valueOf((Integer)this.densityOneof_);
                    return result == null ? DensityAlias.UNRECOGNIZED : result;
                }
                return DensityAlias.DENSITY_UNSPECIFIED;
            }

            public Builder setDensityAlias(DensityAlias value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.densityOneofCase_ = 1;
                this.densityOneof_ = value.getNumber();
                this.onChanged();
                return this;
            }

            public Builder clearDensityAlias() {
                if (this.densityOneofCase_ == 1) {
                    this.densityOneofCase_ = 0;
                    this.densityOneof_ = null;
                    this.onChanged();
                }
                return this;
            }

            @Override
            public int getDensityDpi() {
                if (this.densityOneofCase_ == 2) {
                    return (Integer)this.densityOneof_;
                }
                return 0;
            }

            public Builder setDensityDpi(int value) {
                this.densityOneofCase_ = 2;
                this.densityOneof_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearDensityDpi() {
                if (this.densityOneofCase_ == 2) {
                    this.densityOneofCase_ = 0;
                    this.densityOneof_ = null;
                    this.onChanged();
                }
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

        public static enum DensityOneofCase implements Internal.EnumLite
        {
            DENSITY_ALIAS(1),
            DENSITY_DPI(2),
            DENSITYONEOF_NOT_SET(0);

            private final int value;

            private DensityOneofCase(int value) {
                this.value = value;
            }

            @Deprecated
            public static DensityOneofCase valueOf(int value) {
                return DensityOneofCase.forNumber(value);
            }

            public static DensityOneofCase forNumber(int value) {
                switch (value) {
                    case 1: {
                        return DENSITY_ALIAS;
                    }
                    case 2: {
                        return DENSITY_DPI;
                    }
                    case 0: {
                        return DENSITYONEOF_NOT_SET;
                    }
                }
                return null;
            }

            @Override
            public int getNumber() {
                return this.value;
            }
        }

        public static enum DensityAlias implements ProtocolMessageEnum
        {
            DENSITY_UNSPECIFIED(0),
            NODPI(1),
            LDPI(2),
            MDPI(3),
            TVDPI(4),
            HDPI(5),
            XHDPI(6),
            XXHDPI(7),
            XXXHDPI(8),
            UNRECOGNIZED(-1);

            public static final int DENSITY_UNSPECIFIED_VALUE = 0;
            public static final int NODPI_VALUE = 1;
            public static final int LDPI_VALUE = 2;
            public static final int MDPI_VALUE = 3;
            public static final int TVDPI_VALUE = 4;
            public static final int HDPI_VALUE = 5;
            public static final int XHDPI_VALUE = 6;
            public static final int XXHDPI_VALUE = 7;
            public static final int XXXHDPI_VALUE = 8;
            private static final Internal.EnumLiteMap<DensityAlias> internalValueMap;
            private static final DensityAlias[] VALUES;
            private final int value;

            @Override
            public final int getNumber() {
                if (this == UNRECOGNIZED) {
                    throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
                }
                return this.value;
            }

            @Deprecated
            public static DensityAlias valueOf(int value) {
                return DensityAlias.forNumber(value);
            }

            public static DensityAlias forNumber(int value) {
                switch (value) {
                    case 0: {
                        return DENSITY_UNSPECIFIED;
                    }
                    case 1: {
                        return NODPI;
                    }
                    case 2: {
                        return LDPI;
                    }
                    case 3: {
                        return MDPI;
                    }
                    case 4: {
                        return TVDPI;
                    }
                    case 5: {
                        return HDPI;
                    }
                    case 6: {
                        return XHDPI;
                    }
                    case 7: {
                        return XXHDPI;
                    }
                    case 8: {
                        return XXXHDPI;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<DensityAlias> internalGetValueMap() {
                return internalValueMap;
            }

            @Override
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return DensityAlias.getDescriptor().getValues().get(this.ordinal());
            }

            @Override
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return DensityAlias.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return ScreenDensity.getDescriptor().getEnumTypes().get(0);
            }

            public static DensityAlias valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != DensityAlias.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                if (desc.getIndex() == -1) {
                    return UNRECOGNIZED;
                }
                return VALUES[desc.getIndex()];
            }

            private DensityAlias(int value) {
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<DensityAlias>(){

                    @Override
                    public DensityAlias findValueByNumber(int number) {
                        return DensityAlias.forNumber(number);
                    }
                };
                VALUES = DensityAlias.values();
            }
        }
    }

    public static interface ScreenDensityOrBuilder
    extends MessageOrBuilder {
        public int getDensityAliasValue();

        public ScreenDensity.DensityAlias getDensityAlias();

        public int getDensityDpi();

        public ScreenDensity.DensityOneofCase getDensityOneofCase();
    }

    public static final class UserCountriesTargeting
    extends GeneratedMessageV3
    implements UserCountriesTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int COUNTRY_CODES_FIELD_NUMBER = 1;
        private LazyStringList countryCodes_;
        public static final int EXCLUDE_FIELD_NUMBER = 2;
        private boolean exclude_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final UserCountriesTargeting DEFAULT_INSTANCE = new UserCountriesTargeting();
        private static final Parser<UserCountriesTargeting> PARSER = new AbstractParser<UserCountriesTargeting>(){

            @Override
            public UserCountriesTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new UserCountriesTargeting(input, extensionRegistry);
            }
        };

        private UserCountriesTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private UserCountriesTargeting() {
            this.countryCodes_ = LazyStringArrayList.EMPTY;
            this.exclude_ = false;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private UserCountriesTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (!(mutable_bitField0_ & true)) {
                                this.countryCodes_ = new LazyStringArrayList();
                                mutable_bitField0_ |= true;
                            }
                            this.countryCodes_.add(s3);
                            continue block11;
                        }
                        case 16: 
                    }
                    this.exclude_ = input.readBool();
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
                    this.countryCodes_ = this.countryCodes_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_UserCountriesTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_UserCountriesTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(UserCountriesTargeting.class, Builder.class);
        }

        public ProtocolStringList getCountryCodesList() {
            return this.countryCodes_;
        }

        @Override
        public int getCountryCodesCount() {
            return this.countryCodes_.size();
        }

        @Override
        public String getCountryCodes(int index) {
            return (String)this.countryCodes_.get(index);
        }

        @Override
        public ByteString getCountryCodesBytes(int index) {
            return this.countryCodes_.getByteString(index);
        }

        @Override
        public boolean getExclude() {
            return this.exclude_;
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
            for (int i2 = 0; i2 < this.countryCodes_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 1, this.countryCodes_.getRaw(i2));
            }
            if (this.exclude_) {
                output.writeBool(2, this.exclude_);
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
            for (int i2 = 0; i2 < this.countryCodes_.size(); ++i2) {
                dataSize += UserCountriesTargeting.computeStringSizeNoTag(this.countryCodes_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getCountryCodesList().size();
            if (this.exclude_) {
                size += CodedOutputStream.computeBoolSize(2, this.exclude_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof UserCountriesTargeting)) {
                return super.equals(obj);
            }
            UserCountriesTargeting other = (UserCountriesTargeting)obj;
            boolean result = true;
            result = result && this.getCountryCodesList().equals(other.getCountryCodesList());
            result = result && this.getExclude() == other.getExclude();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + UserCountriesTargeting.getDescriptor().hashCode();
            if (this.getCountryCodesCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCountryCodesList().hashCode();
            }
            hash = 37 * hash + 2;
            hash = 53 * hash + Internal.hashBoolean(this.getExclude());
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static UserCountriesTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UserCountriesTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UserCountriesTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UserCountriesTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UserCountriesTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static UserCountriesTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static UserCountriesTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static UserCountriesTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static UserCountriesTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static UserCountriesTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static UserCountriesTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static UserCountriesTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return UserCountriesTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(UserCountriesTargeting prototype) {
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

        public static UserCountriesTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<UserCountriesTargeting> parser() {
            return PARSER;
        }

        public Parser<UserCountriesTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public UserCountriesTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements UserCountriesTargetingOrBuilder {
            private int bitField0_;
            private LazyStringList countryCodes_ = LazyStringArrayList.EMPTY;
            private boolean exclude_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_UserCountriesTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_UserCountriesTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(UserCountriesTargeting.class, Builder.class);
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
                this.countryCodes_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.exclude_ = false;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_UserCountriesTargeting_descriptor;
            }

            @Override
            public UserCountriesTargeting getDefaultInstanceForType() {
                return UserCountriesTargeting.getDefaultInstance();
            }

            @Override
            public UserCountriesTargeting build() {
                UserCountriesTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public UserCountriesTargeting buildPartial() {
                UserCountriesTargeting result = new UserCountriesTargeting(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    this.countryCodes_ = this.countryCodes_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.countryCodes_ = this.countryCodes_;
                result.exclude_ = this.exclude_;
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
                if (other instanceof UserCountriesTargeting) {
                    return this.mergeFrom((UserCountriesTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(UserCountriesTargeting other) {
                if (other == UserCountriesTargeting.getDefaultInstance()) {
                    return this;
                }
                if (!other.countryCodes_.isEmpty()) {
                    if (this.countryCodes_.isEmpty()) {
                        this.countryCodes_ = other.countryCodes_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureCountryCodesIsMutable();
                        this.countryCodes_.addAll(other.countryCodes_);
                    }
                    this.onChanged();
                }
                if (other.getExclude()) {
                    this.setExclude(other.getExclude());
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
                UserCountriesTargeting parsedMessage = null;
                try {
                    parsedMessage = (UserCountriesTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (UserCountriesTargeting)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureCountryCodesIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.countryCodes_ = new LazyStringArrayList(this.countryCodes_);
                    this.bitField0_ |= 1;
                }
            }

            public ProtocolStringList getCountryCodesList() {
                return this.countryCodes_.getUnmodifiableView();
            }

            @Override
            public int getCountryCodesCount() {
                return this.countryCodes_.size();
            }

            @Override
            public String getCountryCodes(int index) {
                return (String)this.countryCodes_.get(index);
            }

            @Override
            public ByteString getCountryCodesBytes(int index) {
                return this.countryCodes_.getByteString(index);
            }

            public Builder setCountryCodes(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureCountryCodesIsMutable();
                this.countryCodes_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addCountryCodes(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureCountryCodesIsMutable();
                this.countryCodes_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllCountryCodes(Iterable<String> values2) {
                this.ensureCountryCodesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.countryCodes_);
                this.onChanged();
                return this;
            }

            public Builder clearCountryCodes() {
                this.countryCodes_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                this.onChanged();
                return this;
            }

            public Builder addCountryCodesBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                UserCountriesTargeting.checkByteStringIsUtf8(value);
                this.ensureCountryCodesIsMutable();
                this.countryCodes_.add(value);
                this.onChanged();
                return this;
            }

            @Override
            public boolean getExclude() {
                return this.exclude_;
            }

            public Builder setExclude(boolean value) {
                this.exclude_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearExclude() {
                this.exclude_ = false;
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

    public static interface UserCountriesTargetingOrBuilder
    extends MessageOrBuilder {
        public List<String> getCountryCodesList();

        public int getCountryCodesCount();

        public String getCountryCodes(int var1);

        public ByteString getCountryCodesBytes(int var1);

        public boolean getExclude();
    }

    public static final class ModuleTargeting
    extends GeneratedMessageV3
    implements ModuleTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int SDK_VERSION_TARGETING_FIELD_NUMBER = 1;
        private SdkVersionTargeting sdkVersionTargeting_;
        public static final int DEVICE_FEATURE_TARGETING_FIELD_NUMBER = 2;
        private List<DeviceFeatureTargeting> deviceFeatureTargeting_;
        public static final int USER_COUNTRIES_TARGETING_FIELD_NUMBER = 3;
        private UserCountriesTargeting userCountriesTargeting_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ModuleTargeting DEFAULT_INSTANCE = new ModuleTargeting();
        private static final Parser<ModuleTargeting> PARSER = new AbstractParser<ModuleTargeting>(){

            @Override
            public ModuleTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ModuleTargeting(input, extensionRegistry);
            }
        };

        private ModuleTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ModuleTargeting() {
            this.deviceFeatureTargeting_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ModuleTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block12: while (!done) {
                    GeneratedMessageV3.Builder subBuilder;
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
                            subBuilder = null;
                            if (this.sdkVersionTargeting_ != null) {
                                subBuilder = this.sdkVersionTargeting_.toBuilder();
                            }
                            this.sdkVersionTargeting_ = input.readMessage(SdkVersionTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block12;
                            ((SdkVersionTargeting.Builder)subBuilder).mergeFrom(this.sdkVersionTargeting_);
                            this.sdkVersionTargeting_ = ((SdkVersionTargeting.Builder)subBuilder).buildPartial();
                            continue block12;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.deviceFeatureTargeting_ = new ArrayList<DeviceFeatureTargeting>();
                                mutable_bitField0_ |= 2;
                            }
                            this.deviceFeatureTargeting_.add(input.readMessage(DeviceFeatureTargeting.parser(), extensionRegistry));
                            continue block12;
                        }
                        case 26: 
                    }
                    subBuilder = null;
                    if (this.userCountriesTargeting_ != null) {
                        subBuilder = this.userCountriesTargeting_.toBuilder();
                    }
                    this.userCountriesTargeting_ = input.readMessage(UserCountriesTargeting.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    ((UserCountriesTargeting.Builder)subBuilder).mergeFrom(this.userCountriesTargeting_);
                    this.userCountriesTargeting_ = ((UserCountriesTargeting.Builder)subBuilder).buildPartial();
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
                    this.deviceFeatureTargeting_ = Collections.unmodifiableList(this.deviceFeatureTargeting_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_ModuleTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ModuleTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(ModuleTargeting.class, Builder.class);
        }

        @Override
        public boolean hasSdkVersionTargeting() {
            return this.sdkVersionTargeting_ != null;
        }

        @Override
        public SdkVersionTargeting getSdkVersionTargeting() {
            return this.sdkVersionTargeting_ == null ? SdkVersionTargeting.getDefaultInstance() : this.sdkVersionTargeting_;
        }

        @Override
        public SdkVersionTargetingOrBuilder getSdkVersionTargetingOrBuilder() {
            return this.getSdkVersionTargeting();
        }

        @Override
        public List<DeviceFeatureTargeting> getDeviceFeatureTargetingList() {
            return this.deviceFeatureTargeting_;
        }

        @Override
        public List<? extends DeviceFeatureTargetingOrBuilder> getDeviceFeatureTargetingOrBuilderList() {
            return this.deviceFeatureTargeting_;
        }

        @Override
        public int getDeviceFeatureTargetingCount() {
            return this.deviceFeatureTargeting_.size();
        }

        @Override
        public DeviceFeatureTargeting getDeviceFeatureTargeting(int index) {
            return this.deviceFeatureTargeting_.get(index);
        }

        @Override
        public DeviceFeatureTargetingOrBuilder getDeviceFeatureTargetingOrBuilder(int index) {
            return this.deviceFeatureTargeting_.get(index);
        }

        @Override
        public boolean hasUserCountriesTargeting() {
            return this.userCountriesTargeting_ != null;
        }

        @Override
        public UserCountriesTargeting getUserCountriesTargeting() {
            return this.userCountriesTargeting_ == null ? UserCountriesTargeting.getDefaultInstance() : this.userCountriesTargeting_;
        }

        @Override
        public UserCountriesTargetingOrBuilder getUserCountriesTargetingOrBuilder() {
            return this.getUserCountriesTargeting();
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
            if (this.sdkVersionTargeting_ != null) {
                output.writeMessage(1, this.getSdkVersionTargeting());
            }
            for (int i2 = 0; i2 < this.deviceFeatureTargeting_.size(); ++i2) {
                output.writeMessage(2, this.deviceFeatureTargeting_.get(i2));
            }
            if (this.userCountriesTargeting_ != null) {
                output.writeMessage(3, this.getUserCountriesTargeting());
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
            if (this.sdkVersionTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getSdkVersionTargeting());
            }
            for (int i2 = 0; i2 < this.deviceFeatureTargeting_.size(); ++i2) {
                size += CodedOutputStream.computeMessageSize(2, this.deviceFeatureTargeting_.get(i2));
            }
            if (this.userCountriesTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getUserCountriesTargeting());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ModuleTargeting)) {
                return super.equals(obj);
            }
            ModuleTargeting other = (ModuleTargeting)obj;
            boolean result = true;
            boolean bl = result = result && this.hasSdkVersionTargeting() == other.hasSdkVersionTargeting();
            if (this.hasSdkVersionTargeting()) {
                result = result && this.getSdkVersionTargeting().equals(other.getSdkVersionTargeting());
            }
            result = result && this.getDeviceFeatureTargetingList().equals(other.getDeviceFeatureTargetingList());
            boolean bl2 = result = result && this.hasUserCountriesTargeting() == other.hasUserCountriesTargeting();
            if (this.hasUserCountriesTargeting()) {
                result = result && this.getUserCountriesTargeting().equals(other.getUserCountriesTargeting());
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
            hash = 19 * hash + ModuleTargeting.getDescriptor().hashCode();
            if (this.hasSdkVersionTargeting()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getSdkVersionTargeting().hashCode();
            }
            if (this.getDeviceFeatureTargetingCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getDeviceFeatureTargetingList().hashCode();
            }
            if (this.hasUserCountriesTargeting()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getUserCountriesTargeting().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ModuleTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ModuleTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ModuleTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ModuleTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ModuleTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ModuleTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ModuleTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ModuleTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ModuleTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ModuleTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ModuleTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ModuleTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ModuleTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ModuleTargeting prototype) {
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

        public static ModuleTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ModuleTargeting> parser() {
            return PARSER;
        }

        public Parser<ModuleTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public ModuleTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ModuleTargetingOrBuilder {
            private int bitField0_;
            private SdkVersionTargeting sdkVersionTargeting_ = null;
            private SingleFieldBuilderV3<SdkVersionTargeting, SdkVersionTargeting.Builder, SdkVersionTargetingOrBuilder> sdkVersionTargetingBuilder_;
            private List<DeviceFeatureTargeting> deviceFeatureTargeting_ = Collections.emptyList();
            private RepeatedFieldBuilderV3<DeviceFeatureTargeting, DeviceFeatureTargeting.Builder, DeviceFeatureTargetingOrBuilder> deviceFeatureTargetingBuilder_;
            private UserCountriesTargeting userCountriesTargeting_ = null;
            private SingleFieldBuilderV3<UserCountriesTargeting, UserCountriesTargeting.Builder, UserCountriesTargetingOrBuilder> userCountriesTargetingBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ModuleTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ModuleTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(ModuleTargeting.class, Builder.class);
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
                    this.getDeviceFeatureTargetingFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = null;
                } else {
                    this.sdkVersionTargeting_ = null;
                    this.sdkVersionTargetingBuilder_ = null;
                }
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    this.deviceFeatureTargeting_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                } else {
                    this.deviceFeatureTargetingBuilder_.clear();
                }
                if (this.userCountriesTargetingBuilder_ == null) {
                    this.userCountriesTargeting_ = null;
                } else {
                    this.userCountriesTargeting_ = null;
                    this.userCountriesTargetingBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ModuleTargeting_descriptor;
            }

            @Override
            public ModuleTargeting getDefaultInstanceForType() {
                return ModuleTargeting.getDefaultInstance();
            }

            @Override
            public ModuleTargeting build() {
                ModuleTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ModuleTargeting buildPartial() {
                ModuleTargeting result = new ModuleTargeting(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if (this.sdkVersionTargetingBuilder_ == null) {
                    result.sdkVersionTargeting_ = this.sdkVersionTargeting_;
                } else {
                    result.sdkVersionTargeting_ = this.sdkVersionTargetingBuilder_.build();
                }
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.deviceFeatureTargeting_ = Collections.unmodifiableList(this.deviceFeatureTargeting_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.deviceFeatureTargeting_ = this.deviceFeatureTargeting_;
                } else {
                    result.deviceFeatureTargeting_ = this.deviceFeatureTargetingBuilder_.build();
                }
                if (this.userCountriesTargetingBuilder_ == null) {
                    result.userCountriesTargeting_ = this.userCountriesTargeting_;
                } else {
                    result.userCountriesTargeting_ = this.userCountriesTargetingBuilder_.build();
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
                if (other instanceof ModuleTargeting) {
                    return this.mergeFrom((ModuleTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ModuleTargeting other) {
                if (other == ModuleTargeting.getDefaultInstance()) {
                    return this;
                }
                if (other.hasSdkVersionTargeting()) {
                    this.mergeSdkVersionTargeting(other.getSdkVersionTargeting());
                }
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    if (!other.deviceFeatureTargeting_.isEmpty()) {
                        if (this.deviceFeatureTargeting_.isEmpty()) {
                            this.deviceFeatureTargeting_ = other.deviceFeatureTargeting_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        } else {
                            this.ensureDeviceFeatureTargetingIsMutable();
                            this.deviceFeatureTargeting_.addAll(other.deviceFeatureTargeting_);
                        }
                        this.onChanged();
                    }
                } else if (!other.deviceFeatureTargeting_.isEmpty()) {
                    if (this.deviceFeatureTargetingBuilder_.isEmpty()) {
                        this.deviceFeatureTargetingBuilder_.dispose();
                        this.deviceFeatureTargetingBuilder_ = null;
                        this.deviceFeatureTargeting_ = other.deviceFeatureTargeting_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.deviceFeatureTargetingBuilder_ = alwaysUseFieldBuilders ? this.getDeviceFeatureTargetingFieldBuilder() : null;
                    } else {
                        this.deviceFeatureTargetingBuilder_.addAllMessages(other.deviceFeatureTargeting_);
                    }
                }
                if (other.hasUserCountriesTargeting()) {
                    this.mergeUserCountriesTargeting(other.getUserCountriesTargeting());
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
                ModuleTargeting parsedMessage = null;
                try {
                    parsedMessage = (ModuleTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ModuleTargeting)e2.getUnfinishedMessage();
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
            public boolean hasSdkVersionTargeting() {
                return this.sdkVersionTargetingBuilder_ != null || this.sdkVersionTargeting_ != null;
            }

            @Override
            public SdkVersionTargeting getSdkVersionTargeting() {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    return this.sdkVersionTargeting_ == null ? SdkVersionTargeting.getDefaultInstance() : this.sdkVersionTargeting_;
                }
                return this.sdkVersionTargetingBuilder_.getMessage();
            }

            public Builder setSdkVersionTargeting(SdkVersionTargeting value) {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.sdkVersionTargeting_ = value;
                    this.onChanged();
                } else {
                    this.sdkVersionTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setSdkVersionTargeting(SdkVersionTargeting.Builder builderForValue) {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.sdkVersionTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeSdkVersionTargeting(SdkVersionTargeting value) {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = this.sdkVersionTargeting_ != null ? SdkVersionTargeting.newBuilder(this.sdkVersionTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.sdkVersionTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearSdkVersionTargeting() {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = null;
                    this.onChanged();
                } else {
                    this.sdkVersionTargeting_ = null;
                    this.sdkVersionTargetingBuilder_ = null;
                }
                return this;
            }

            public SdkVersionTargeting.Builder getSdkVersionTargetingBuilder() {
                this.onChanged();
                return this.getSdkVersionTargetingFieldBuilder().getBuilder();
            }

            @Override
            public SdkVersionTargetingOrBuilder getSdkVersionTargetingOrBuilder() {
                if (this.sdkVersionTargetingBuilder_ != null) {
                    return this.sdkVersionTargetingBuilder_.getMessageOrBuilder();
                }
                return this.sdkVersionTargeting_ == null ? SdkVersionTargeting.getDefaultInstance() : this.sdkVersionTargeting_;
            }

            private SingleFieldBuilderV3<SdkVersionTargeting, SdkVersionTargeting.Builder, SdkVersionTargetingOrBuilder> getSdkVersionTargetingFieldBuilder() {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargetingBuilder_ = new SingleFieldBuilderV3(this.getSdkVersionTargeting(), this.getParentForChildren(), this.isClean());
                    this.sdkVersionTargeting_ = null;
                }
                return this.sdkVersionTargetingBuilder_;
            }

            private void ensureDeviceFeatureTargetingIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.deviceFeatureTargeting_ = new ArrayList<DeviceFeatureTargeting>(this.deviceFeatureTargeting_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<DeviceFeatureTargeting> getDeviceFeatureTargetingList() {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    return Collections.unmodifiableList(this.deviceFeatureTargeting_);
                }
                return this.deviceFeatureTargetingBuilder_.getMessageList();
            }

            @Override
            public int getDeviceFeatureTargetingCount() {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    return this.deviceFeatureTargeting_.size();
                }
                return this.deviceFeatureTargetingBuilder_.getCount();
            }

            @Override
            public DeviceFeatureTargeting getDeviceFeatureTargeting(int index) {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    return this.deviceFeatureTargeting_.get(index);
                }
                return this.deviceFeatureTargetingBuilder_.getMessage(index);
            }

            public Builder setDeviceFeatureTargeting(int index, DeviceFeatureTargeting value) {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDeviceFeatureTargetingIsMutable();
                    this.deviceFeatureTargeting_.set(index, value);
                    this.onChanged();
                } else {
                    this.deviceFeatureTargetingBuilder_.setMessage(index, value);
                }
                return this;
            }

            public Builder setDeviceFeatureTargeting(int index, DeviceFeatureTargeting.Builder builderForValue) {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    this.ensureDeviceFeatureTargetingIsMutable();
                    this.deviceFeatureTargeting_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.deviceFeatureTargetingBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addDeviceFeatureTargeting(DeviceFeatureTargeting value) {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDeviceFeatureTargetingIsMutable();
                    this.deviceFeatureTargeting_.add(value);
                    this.onChanged();
                } else {
                    this.deviceFeatureTargetingBuilder_.addMessage(value);
                }
                return this;
            }

            public Builder addDeviceFeatureTargeting(int index, DeviceFeatureTargeting value) {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDeviceFeatureTargetingIsMutable();
                    this.deviceFeatureTargeting_.add(index, value);
                    this.onChanged();
                } else {
                    this.deviceFeatureTargetingBuilder_.addMessage(index, value);
                }
                return this;
            }

            public Builder addDeviceFeatureTargeting(DeviceFeatureTargeting.Builder builderForValue) {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    this.ensureDeviceFeatureTargetingIsMutable();
                    this.deviceFeatureTargeting_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.deviceFeatureTargetingBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addDeviceFeatureTargeting(int index, DeviceFeatureTargeting.Builder builderForValue) {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    this.ensureDeviceFeatureTargetingIsMutable();
                    this.deviceFeatureTargeting_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.deviceFeatureTargetingBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllDeviceFeatureTargeting(Iterable<? extends DeviceFeatureTargeting> values2) {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    this.ensureDeviceFeatureTargetingIsMutable();
                    AbstractMessageLite.Builder.addAll(values2, this.deviceFeatureTargeting_);
                    this.onChanged();
                } else {
                    this.deviceFeatureTargetingBuilder_.addAllMessages(values2);
                }
                return this;
            }

            public Builder clearDeviceFeatureTargeting() {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    this.deviceFeatureTargeting_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                } else {
                    this.deviceFeatureTargetingBuilder_.clear();
                }
                return this;
            }

            public Builder removeDeviceFeatureTargeting(int index) {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    this.ensureDeviceFeatureTargetingIsMutable();
                    this.deviceFeatureTargeting_.remove(index);
                    this.onChanged();
                } else {
                    this.deviceFeatureTargetingBuilder_.remove(index);
                }
                return this;
            }

            public DeviceFeatureTargeting.Builder getDeviceFeatureTargetingBuilder(int index) {
                return this.getDeviceFeatureTargetingFieldBuilder().getBuilder(index);
            }

            @Override
            public DeviceFeatureTargetingOrBuilder getDeviceFeatureTargetingOrBuilder(int index) {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    return this.deviceFeatureTargeting_.get(index);
                }
                return this.deviceFeatureTargetingBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends DeviceFeatureTargetingOrBuilder> getDeviceFeatureTargetingOrBuilderList() {
                if (this.deviceFeatureTargetingBuilder_ != null) {
                    return this.deviceFeatureTargetingBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.deviceFeatureTargeting_);
            }

            public DeviceFeatureTargeting.Builder addDeviceFeatureTargetingBuilder() {
                return this.getDeviceFeatureTargetingFieldBuilder().addBuilder(DeviceFeatureTargeting.getDefaultInstance());
            }

            public DeviceFeatureTargeting.Builder addDeviceFeatureTargetingBuilder(int index) {
                return this.getDeviceFeatureTargetingFieldBuilder().addBuilder(index, DeviceFeatureTargeting.getDefaultInstance());
            }

            public List<DeviceFeatureTargeting.Builder> getDeviceFeatureTargetingBuilderList() {
                return this.getDeviceFeatureTargetingFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilderV3<DeviceFeatureTargeting, DeviceFeatureTargeting.Builder, DeviceFeatureTargetingOrBuilder> getDeviceFeatureTargetingFieldBuilder() {
                if (this.deviceFeatureTargetingBuilder_ == null) {
                    this.deviceFeatureTargetingBuilder_ = new RepeatedFieldBuilderV3(this.deviceFeatureTargeting_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.deviceFeatureTargeting_ = null;
                }
                return this.deviceFeatureTargetingBuilder_;
            }

            @Override
            public boolean hasUserCountriesTargeting() {
                return this.userCountriesTargetingBuilder_ != null || this.userCountriesTargeting_ != null;
            }

            @Override
            public UserCountriesTargeting getUserCountriesTargeting() {
                if (this.userCountriesTargetingBuilder_ == null) {
                    return this.userCountriesTargeting_ == null ? UserCountriesTargeting.getDefaultInstance() : this.userCountriesTargeting_;
                }
                return this.userCountriesTargetingBuilder_.getMessage();
            }

            public Builder setUserCountriesTargeting(UserCountriesTargeting value) {
                if (this.userCountriesTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.userCountriesTargeting_ = value;
                    this.onChanged();
                } else {
                    this.userCountriesTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setUserCountriesTargeting(UserCountriesTargeting.Builder builderForValue) {
                if (this.userCountriesTargetingBuilder_ == null) {
                    this.userCountriesTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.userCountriesTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeUserCountriesTargeting(UserCountriesTargeting value) {
                if (this.userCountriesTargetingBuilder_ == null) {
                    this.userCountriesTargeting_ = this.userCountriesTargeting_ != null ? UserCountriesTargeting.newBuilder(this.userCountriesTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.userCountriesTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearUserCountriesTargeting() {
                if (this.userCountriesTargetingBuilder_ == null) {
                    this.userCountriesTargeting_ = null;
                    this.onChanged();
                } else {
                    this.userCountriesTargeting_ = null;
                    this.userCountriesTargetingBuilder_ = null;
                }
                return this;
            }

            public UserCountriesTargeting.Builder getUserCountriesTargetingBuilder() {
                this.onChanged();
                return this.getUserCountriesTargetingFieldBuilder().getBuilder();
            }

            @Override
            public UserCountriesTargetingOrBuilder getUserCountriesTargetingOrBuilder() {
                if (this.userCountriesTargetingBuilder_ != null) {
                    return this.userCountriesTargetingBuilder_.getMessageOrBuilder();
                }
                return this.userCountriesTargeting_ == null ? UserCountriesTargeting.getDefaultInstance() : this.userCountriesTargeting_;
            }

            private SingleFieldBuilderV3<UserCountriesTargeting, UserCountriesTargeting.Builder, UserCountriesTargetingOrBuilder> getUserCountriesTargetingFieldBuilder() {
                if (this.userCountriesTargetingBuilder_ == null) {
                    this.userCountriesTargetingBuilder_ = new SingleFieldBuilderV3(this.getUserCountriesTargeting(), this.getParentForChildren(), this.isClean());
                    this.userCountriesTargeting_ = null;
                }
                return this.userCountriesTargetingBuilder_;
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

    public static interface ModuleTargetingOrBuilder
    extends MessageOrBuilder {
        public boolean hasSdkVersionTargeting();

        public SdkVersionTargeting getSdkVersionTargeting();

        public SdkVersionTargetingOrBuilder getSdkVersionTargetingOrBuilder();

        public List<DeviceFeatureTargeting> getDeviceFeatureTargetingList();

        public DeviceFeatureTargeting getDeviceFeatureTargeting(int var1);

        public int getDeviceFeatureTargetingCount();

        public List<? extends DeviceFeatureTargetingOrBuilder> getDeviceFeatureTargetingOrBuilderList();

        public DeviceFeatureTargetingOrBuilder getDeviceFeatureTargetingOrBuilder(int var1);

        public boolean hasUserCountriesTargeting();

        public UserCountriesTargeting getUserCountriesTargeting();

        public UserCountriesTargetingOrBuilder getUserCountriesTargetingOrBuilder();
    }

    public static final class ApkTargeting
    extends GeneratedMessageV3
    implements ApkTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ABI_TARGETING_FIELD_NUMBER = 1;
        private AbiTargeting abiTargeting_;
        public static final int GRAPHICS_API_TARGETING_FIELD_NUMBER = 2;
        private GraphicsApiTargeting graphicsApiTargeting_;
        public static final int LANGUAGE_TARGETING_FIELD_NUMBER = 3;
        private LanguageTargeting languageTargeting_;
        public static final int SCREEN_DENSITY_TARGETING_FIELD_NUMBER = 4;
        private ScreenDensityTargeting screenDensityTargeting_;
        public static final int SDK_VERSION_TARGETING_FIELD_NUMBER = 5;
        private SdkVersionTargeting sdkVersionTargeting_;
        public static final int TEXTURE_COMPRESSION_FORMAT_TARGETING_FIELD_NUMBER = 6;
        private TextureCompressionFormatTargeting textureCompressionFormatTargeting_;
        public static final int MULTI_ABI_TARGETING_FIELD_NUMBER = 7;
        private MultiAbiTargeting multiAbiTargeting_;
        public static final int SANITIZER_TARGETING_FIELD_NUMBER = 8;
        private SanitizerTargeting sanitizerTargeting_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ApkTargeting DEFAULT_INSTANCE = new ApkTargeting();
        private static final Parser<ApkTargeting> PARSER = new AbstractParser<ApkTargeting>(){

            @Override
            public ApkTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ApkTargeting(input, extensionRegistry);
            }
        };

        private ApkTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ApkTargeting() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ApkTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.abiTargeting_ != null) {
                                subBuilder = this.abiTargeting_.toBuilder();
                            }
                            this.abiTargeting_ = input.readMessage(AbiTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block17;
                            ((AbiTargeting.Builder)subBuilder).mergeFrom(this.abiTargeting_);
                            this.abiTargeting_ = ((AbiTargeting.Builder)subBuilder).buildPartial();
                            continue block17;
                        }
                        case 18: {
                            subBuilder = null;
                            if (this.graphicsApiTargeting_ != null) {
                                subBuilder = this.graphicsApiTargeting_.toBuilder();
                            }
                            this.graphicsApiTargeting_ = input.readMessage(GraphicsApiTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block17;
                            ((GraphicsApiTargeting.Builder)subBuilder).mergeFrom(this.graphicsApiTargeting_);
                            this.graphicsApiTargeting_ = ((GraphicsApiTargeting.Builder)subBuilder).buildPartial();
                            continue block17;
                        }
                        case 26: {
                            subBuilder = null;
                            if (this.languageTargeting_ != null) {
                                subBuilder = this.languageTargeting_.toBuilder();
                            }
                            this.languageTargeting_ = input.readMessage(LanguageTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block17;
                            ((LanguageTargeting.Builder)subBuilder).mergeFrom(this.languageTargeting_);
                            this.languageTargeting_ = ((LanguageTargeting.Builder)subBuilder).buildPartial();
                            continue block17;
                        }
                        case 34: {
                            subBuilder = null;
                            if (this.screenDensityTargeting_ != null) {
                                subBuilder = this.screenDensityTargeting_.toBuilder();
                            }
                            this.screenDensityTargeting_ = input.readMessage(ScreenDensityTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block17;
                            ((ScreenDensityTargeting.Builder)subBuilder).mergeFrom(this.screenDensityTargeting_);
                            this.screenDensityTargeting_ = ((ScreenDensityTargeting.Builder)subBuilder).buildPartial();
                            continue block17;
                        }
                        case 42: {
                            subBuilder = null;
                            if (this.sdkVersionTargeting_ != null) {
                                subBuilder = this.sdkVersionTargeting_.toBuilder();
                            }
                            this.sdkVersionTargeting_ = input.readMessage(SdkVersionTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block17;
                            ((SdkVersionTargeting.Builder)subBuilder).mergeFrom(this.sdkVersionTargeting_);
                            this.sdkVersionTargeting_ = ((SdkVersionTargeting.Builder)subBuilder).buildPartial();
                            continue block17;
                        }
                        case 50: {
                            subBuilder = null;
                            if (this.textureCompressionFormatTargeting_ != null) {
                                subBuilder = this.textureCompressionFormatTargeting_.toBuilder();
                            }
                            this.textureCompressionFormatTargeting_ = input.readMessage(TextureCompressionFormatTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block17;
                            ((TextureCompressionFormatTargeting.Builder)subBuilder).mergeFrom(this.textureCompressionFormatTargeting_);
                            this.textureCompressionFormatTargeting_ = ((TextureCompressionFormatTargeting.Builder)subBuilder).buildPartial();
                            continue block17;
                        }
                        case 58: {
                            subBuilder = null;
                            if (this.multiAbiTargeting_ != null) {
                                subBuilder = this.multiAbiTargeting_.toBuilder();
                            }
                            this.multiAbiTargeting_ = input.readMessage(MultiAbiTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block17;
                            ((MultiAbiTargeting.Builder)subBuilder).mergeFrom(this.multiAbiTargeting_);
                            this.multiAbiTargeting_ = ((MultiAbiTargeting.Builder)subBuilder).buildPartial();
                            continue block17;
                        }
                        case 66: 
                    }
                    subBuilder = null;
                    if (this.sanitizerTargeting_ != null) {
                        subBuilder = this.sanitizerTargeting_.toBuilder();
                    }
                    this.sanitizerTargeting_ = input.readMessage(SanitizerTargeting.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    ((SanitizerTargeting.Builder)subBuilder).mergeFrom(this.sanitizerTargeting_);
                    this.sanitizerTargeting_ = ((SanitizerTargeting.Builder)subBuilder).buildPartial();
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
            return internal_static_android_bundle_ApkTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ApkTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(ApkTargeting.class, Builder.class);
        }

        @Override
        public boolean hasAbiTargeting() {
            return this.abiTargeting_ != null;
        }

        @Override
        public AbiTargeting getAbiTargeting() {
            return this.abiTargeting_ == null ? AbiTargeting.getDefaultInstance() : this.abiTargeting_;
        }

        @Override
        public AbiTargetingOrBuilder getAbiTargetingOrBuilder() {
            return this.getAbiTargeting();
        }

        @Override
        public boolean hasGraphicsApiTargeting() {
            return this.graphicsApiTargeting_ != null;
        }

        @Override
        public GraphicsApiTargeting getGraphicsApiTargeting() {
            return this.graphicsApiTargeting_ == null ? GraphicsApiTargeting.getDefaultInstance() : this.graphicsApiTargeting_;
        }

        @Override
        public GraphicsApiTargetingOrBuilder getGraphicsApiTargetingOrBuilder() {
            return this.getGraphicsApiTargeting();
        }

        @Override
        public boolean hasLanguageTargeting() {
            return this.languageTargeting_ != null;
        }

        @Override
        public LanguageTargeting getLanguageTargeting() {
            return this.languageTargeting_ == null ? LanguageTargeting.getDefaultInstance() : this.languageTargeting_;
        }

        @Override
        public LanguageTargetingOrBuilder getLanguageTargetingOrBuilder() {
            return this.getLanguageTargeting();
        }

        @Override
        public boolean hasScreenDensityTargeting() {
            return this.screenDensityTargeting_ != null;
        }

        @Override
        public ScreenDensityTargeting getScreenDensityTargeting() {
            return this.screenDensityTargeting_ == null ? ScreenDensityTargeting.getDefaultInstance() : this.screenDensityTargeting_;
        }

        @Override
        public ScreenDensityTargetingOrBuilder getScreenDensityTargetingOrBuilder() {
            return this.getScreenDensityTargeting();
        }

        @Override
        public boolean hasSdkVersionTargeting() {
            return this.sdkVersionTargeting_ != null;
        }

        @Override
        public SdkVersionTargeting getSdkVersionTargeting() {
            return this.sdkVersionTargeting_ == null ? SdkVersionTargeting.getDefaultInstance() : this.sdkVersionTargeting_;
        }

        @Override
        public SdkVersionTargetingOrBuilder getSdkVersionTargetingOrBuilder() {
            return this.getSdkVersionTargeting();
        }

        @Override
        public boolean hasTextureCompressionFormatTargeting() {
            return this.textureCompressionFormatTargeting_ != null;
        }

        @Override
        public TextureCompressionFormatTargeting getTextureCompressionFormatTargeting() {
            return this.textureCompressionFormatTargeting_ == null ? TextureCompressionFormatTargeting.getDefaultInstance() : this.textureCompressionFormatTargeting_;
        }

        @Override
        public TextureCompressionFormatTargetingOrBuilder getTextureCompressionFormatTargetingOrBuilder() {
            return this.getTextureCompressionFormatTargeting();
        }

        @Override
        public boolean hasMultiAbiTargeting() {
            return this.multiAbiTargeting_ != null;
        }

        @Override
        public MultiAbiTargeting getMultiAbiTargeting() {
            return this.multiAbiTargeting_ == null ? MultiAbiTargeting.getDefaultInstance() : this.multiAbiTargeting_;
        }

        @Override
        public MultiAbiTargetingOrBuilder getMultiAbiTargetingOrBuilder() {
            return this.getMultiAbiTargeting();
        }

        @Override
        public boolean hasSanitizerTargeting() {
            return this.sanitizerTargeting_ != null;
        }

        @Override
        public SanitizerTargeting getSanitizerTargeting() {
            return this.sanitizerTargeting_ == null ? SanitizerTargeting.getDefaultInstance() : this.sanitizerTargeting_;
        }

        @Override
        public SanitizerTargetingOrBuilder getSanitizerTargetingOrBuilder() {
            return this.getSanitizerTargeting();
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
            if (this.abiTargeting_ != null) {
                output.writeMessage(1, this.getAbiTargeting());
            }
            if (this.graphicsApiTargeting_ != null) {
                output.writeMessage(2, this.getGraphicsApiTargeting());
            }
            if (this.languageTargeting_ != null) {
                output.writeMessage(3, this.getLanguageTargeting());
            }
            if (this.screenDensityTargeting_ != null) {
                output.writeMessage(4, this.getScreenDensityTargeting());
            }
            if (this.sdkVersionTargeting_ != null) {
                output.writeMessage(5, this.getSdkVersionTargeting());
            }
            if (this.textureCompressionFormatTargeting_ != null) {
                output.writeMessage(6, this.getTextureCompressionFormatTargeting());
            }
            if (this.multiAbiTargeting_ != null) {
                output.writeMessage(7, this.getMultiAbiTargeting());
            }
            if (this.sanitizerTargeting_ != null) {
                output.writeMessage(8, this.getSanitizerTargeting());
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
            if (this.abiTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getAbiTargeting());
            }
            if (this.graphicsApiTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getGraphicsApiTargeting());
            }
            if (this.languageTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getLanguageTargeting());
            }
            if (this.screenDensityTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getScreenDensityTargeting());
            }
            if (this.sdkVersionTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(5, this.getSdkVersionTargeting());
            }
            if (this.textureCompressionFormatTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(6, this.getTextureCompressionFormatTargeting());
            }
            if (this.multiAbiTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(7, this.getMultiAbiTargeting());
            }
            if (this.sanitizerTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(8, this.getSanitizerTargeting());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ApkTargeting)) {
                return super.equals(obj);
            }
            ApkTargeting other = (ApkTargeting)obj;
            boolean result = true;
            boolean bl = result = result && this.hasAbiTargeting() == other.hasAbiTargeting();
            if (this.hasAbiTargeting()) {
                result = result && this.getAbiTargeting().equals(other.getAbiTargeting());
            }
            boolean bl2 = result = result && this.hasGraphicsApiTargeting() == other.hasGraphicsApiTargeting();
            if (this.hasGraphicsApiTargeting()) {
                result = result && this.getGraphicsApiTargeting().equals(other.getGraphicsApiTargeting());
            }
            boolean bl3 = result = result && this.hasLanguageTargeting() == other.hasLanguageTargeting();
            if (this.hasLanguageTargeting()) {
                result = result && this.getLanguageTargeting().equals(other.getLanguageTargeting());
            }
            boolean bl4 = result = result && this.hasScreenDensityTargeting() == other.hasScreenDensityTargeting();
            if (this.hasScreenDensityTargeting()) {
                result = result && this.getScreenDensityTargeting().equals(other.getScreenDensityTargeting());
            }
            boolean bl5 = result = result && this.hasSdkVersionTargeting() == other.hasSdkVersionTargeting();
            if (this.hasSdkVersionTargeting()) {
                result = result && this.getSdkVersionTargeting().equals(other.getSdkVersionTargeting());
            }
            boolean bl6 = result = result && this.hasTextureCompressionFormatTargeting() == other.hasTextureCompressionFormatTargeting();
            if (this.hasTextureCompressionFormatTargeting()) {
                result = result && this.getTextureCompressionFormatTargeting().equals(other.getTextureCompressionFormatTargeting());
            }
            boolean bl7 = result = result && this.hasMultiAbiTargeting() == other.hasMultiAbiTargeting();
            if (this.hasMultiAbiTargeting()) {
                result = result && this.getMultiAbiTargeting().equals(other.getMultiAbiTargeting());
            }
            boolean bl8 = result = result && this.hasSanitizerTargeting() == other.hasSanitizerTargeting();
            if (this.hasSanitizerTargeting()) {
                result = result && this.getSanitizerTargeting().equals(other.getSanitizerTargeting());
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
            hash = 19 * hash + ApkTargeting.getDescriptor().hashCode();
            if (this.hasAbiTargeting()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getAbiTargeting().hashCode();
            }
            if (this.hasGraphicsApiTargeting()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getGraphicsApiTargeting().hashCode();
            }
            if (this.hasLanguageTargeting()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getLanguageTargeting().hashCode();
            }
            if (this.hasScreenDensityTargeting()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getScreenDensityTargeting().hashCode();
            }
            if (this.hasSdkVersionTargeting()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getSdkVersionTargeting().hashCode();
            }
            if (this.hasTextureCompressionFormatTargeting()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getTextureCompressionFormatTargeting().hashCode();
            }
            if (this.hasMultiAbiTargeting()) {
                hash = 37 * hash + 7;
                hash = 53 * hash + this.getMultiAbiTargeting().hashCode();
            }
            if (this.hasSanitizerTargeting()) {
                hash = 37 * hash + 8;
                hash = 53 * hash + this.getSanitizerTargeting().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ApkTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApkTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApkTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApkTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApkTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ApkTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ApkTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApkTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApkTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ApkTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ApkTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ApkTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ApkTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ApkTargeting prototype) {
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

        public static ApkTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ApkTargeting> parser() {
            return PARSER;
        }

        public Parser<ApkTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public ApkTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ApkTargetingOrBuilder {
            private AbiTargeting abiTargeting_ = null;
            private SingleFieldBuilderV3<AbiTargeting, AbiTargeting.Builder, AbiTargetingOrBuilder> abiTargetingBuilder_;
            private GraphicsApiTargeting graphicsApiTargeting_ = null;
            private SingleFieldBuilderV3<GraphicsApiTargeting, GraphicsApiTargeting.Builder, GraphicsApiTargetingOrBuilder> graphicsApiTargetingBuilder_;
            private LanguageTargeting languageTargeting_ = null;
            private SingleFieldBuilderV3<LanguageTargeting, LanguageTargeting.Builder, LanguageTargetingOrBuilder> languageTargetingBuilder_;
            private ScreenDensityTargeting screenDensityTargeting_ = null;
            private SingleFieldBuilderV3<ScreenDensityTargeting, ScreenDensityTargeting.Builder, ScreenDensityTargetingOrBuilder> screenDensityTargetingBuilder_;
            private SdkVersionTargeting sdkVersionTargeting_ = null;
            private SingleFieldBuilderV3<SdkVersionTargeting, SdkVersionTargeting.Builder, SdkVersionTargetingOrBuilder> sdkVersionTargetingBuilder_;
            private TextureCompressionFormatTargeting textureCompressionFormatTargeting_ = null;
            private SingleFieldBuilderV3<TextureCompressionFormatTargeting, TextureCompressionFormatTargeting.Builder, TextureCompressionFormatTargetingOrBuilder> textureCompressionFormatTargetingBuilder_;
            private MultiAbiTargeting multiAbiTargeting_ = null;
            private SingleFieldBuilderV3<MultiAbiTargeting, MultiAbiTargeting.Builder, MultiAbiTargetingOrBuilder> multiAbiTargetingBuilder_;
            private SanitizerTargeting sanitizerTargeting_ = null;
            private SingleFieldBuilderV3<SanitizerTargeting, SanitizerTargeting.Builder, SanitizerTargetingOrBuilder> sanitizerTargetingBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ApkTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ApkTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(ApkTargeting.class, Builder.class);
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
                if (this.abiTargetingBuilder_ == null) {
                    this.abiTargeting_ = null;
                } else {
                    this.abiTargeting_ = null;
                    this.abiTargetingBuilder_ = null;
                }
                if (this.graphicsApiTargetingBuilder_ == null) {
                    this.graphicsApiTargeting_ = null;
                } else {
                    this.graphicsApiTargeting_ = null;
                    this.graphicsApiTargetingBuilder_ = null;
                }
                if (this.languageTargetingBuilder_ == null) {
                    this.languageTargeting_ = null;
                } else {
                    this.languageTargeting_ = null;
                    this.languageTargetingBuilder_ = null;
                }
                if (this.screenDensityTargetingBuilder_ == null) {
                    this.screenDensityTargeting_ = null;
                } else {
                    this.screenDensityTargeting_ = null;
                    this.screenDensityTargetingBuilder_ = null;
                }
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = null;
                } else {
                    this.sdkVersionTargeting_ = null;
                    this.sdkVersionTargetingBuilder_ = null;
                }
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    this.textureCompressionFormatTargeting_ = null;
                } else {
                    this.textureCompressionFormatTargeting_ = null;
                    this.textureCompressionFormatTargetingBuilder_ = null;
                }
                if (this.multiAbiTargetingBuilder_ == null) {
                    this.multiAbiTargeting_ = null;
                } else {
                    this.multiAbiTargeting_ = null;
                    this.multiAbiTargetingBuilder_ = null;
                }
                if (this.sanitizerTargetingBuilder_ == null) {
                    this.sanitizerTargeting_ = null;
                } else {
                    this.sanitizerTargeting_ = null;
                    this.sanitizerTargetingBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ApkTargeting_descriptor;
            }

            @Override
            public ApkTargeting getDefaultInstanceForType() {
                return ApkTargeting.getDefaultInstance();
            }

            @Override
            public ApkTargeting build() {
                ApkTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ApkTargeting buildPartial() {
                ApkTargeting result = new ApkTargeting(this);
                if (this.abiTargetingBuilder_ == null) {
                    result.abiTargeting_ = this.abiTargeting_;
                } else {
                    result.abiTargeting_ = this.abiTargetingBuilder_.build();
                }
                if (this.graphicsApiTargetingBuilder_ == null) {
                    result.graphicsApiTargeting_ = this.graphicsApiTargeting_;
                } else {
                    result.graphicsApiTargeting_ = this.graphicsApiTargetingBuilder_.build();
                }
                if (this.languageTargetingBuilder_ == null) {
                    result.languageTargeting_ = this.languageTargeting_;
                } else {
                    result.languageTargeting_ = this.languageTargetingBuilder_.build();
                }
                if (this.screenDensityTargetingBuilder_ == null) {
                    result.screenDensityTargeting_ = this.screenDensityTargeting_;
                } else {
                    result.screenDensityTargeting_ = this.screenDensityTargetingBuilder_.build();
                }
                if (this.sdkVersionTargetingBuilder_ == null) {
                    result.sdkVersionTargeting_ = this.sdkVersionTargeting_;
                } else {
                    result.sdkVersionTargeting_ = this.sdkVersionTargetingBuilder_.build();
                }
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    result.textureCompressionFormatTargeting_ = this.textureCompressionFormatTargeting_;
                } else {
                    result.textureCompressionFormatTargeting_ = this.textureCompressionFormatTargetingBuilder_.build();
                }
                if (this.multiAbiTargetingBuilder_ == null) {
                    result.multiAbiTargeting_ = this.multiAbiTargeting_;
                } else {
                    result.multiAbiTargeting_ = this.multiAbiTargetingBuilder_.build();
                }
                if (this.sanitizerTargetingBuilder_ == null) {
                    result.sanitizerTargeting_ = this.sanitizerTargeting_;
                } else {
                    result.sanitizerTargeting_ = this.sanitizerTargetingBuilder_.build();
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
                if (other instanceof ApkTargeting) {
                    return this.mergeFrom((ApkTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ApkTargeting other) {
                if (other == ApkTargeting.getDefaultInstance()) {
                    return this;
                }
                if (other.hasAbiTargeting()) {
                    this.mergeAbiTargeting(other.getAbiTargeting());
                }
                if (other.hasGraphicsApiTargeting()) {
                    this.mergeGraphicsApiTargeting(other.getGraphicsApiTargeting());
                }
                if (other.hasLanguageTargeting()) {
                    this.mergeLanguageTargeting(other.getLanguageTargeting());
                }
                if (other.hasScreenDensityTargeting()) {
                    this.mergeScreenDensityTargeting(other.getScreenDensityTargeting());
                }
                if (other.hasSdkVersionTargeting()) {
                    this.mergeSdkVersionTargeting(other.getSdkVersionTargeting());
                }
                if (other.hasTextureCompressionFormatTargeting()) {
                    this.mergeTextureCompressionFormatTargeting(other.getTextureCompressionFormatTargeting());
                }
                if (other.hasMultiAbiTargeting()) {
                    this.mergeMultiAbiTargeting(other.getMultiAbiTargeting());
                }
                if (other.hasSanitizerTargeting()) {
                    this.mergeSanitizerTargeting(other.getSanitizerTargeting());
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
                ApkTargeting parsedMessage = null;
                try {
                    parsedMessage = (ApkTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ApkTargeting)e2.getUnfinishedMessage();
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
            public boolean hasAbiTargeting() {
                return this.abiTargetingBuilder_ != null || this.abiTargeting_ != null;
            }

            @Override
            public AbiTargeting getAbiTargeting() {
                if (this.abiTargetingBuilder_ == null) {
                    return this.abiTargeting_ == null ? AbiTargeting.getDefaultInstance() : this.abiTargeting_;
                }
                return this.abiTargetingBuilder_.getMessage();
            }

            public Builder setAbiTargeting(AbiTargeting value) {
                if (this.abiTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.abiTargeting_ = value;
                    this.onChanged();
                } else {
                    this.abiTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setAbiTargeting(AbiTargeting.Builder builderForValue) {
                if (this.abiTargetingBuilder_ == null) {
                    this.abiTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.abiTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeAbiTargeting(AbiTargeting value) {
                if (this.abiTargetingBuilder_ == null) {
                    this.abiTargeting_ = this.abiTargeting_ != null ? AbiTargeting.newBuilder(this.abiTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.abiTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearAbiTargeting() {
                if (this.abiTargetingBuilder_ == null) {
                    this.abiTargeting_ = null;
                    this.onChanged();
                } else {
                    this.abiTargeting_ = null;
                    this.abiTargetingBuilder_ = null;
                }
                return this;
            }

            public AbiTargeting.Builder getAbiTargetingBuilder() {
                this.onChanged();
                return this.getAbiTargetingFieldBuilder().getBuilder();
            }

            @Override
            public AbiTargetingOrBuilder getAbiTargetingOrBuilder() {
                if (this.abiTargetingBuilder_ != null) {
                    return this.abiTargetingBuilder_.getMessageOrBuilder();
                }
                return this.abiTargeting_ == null ? AbiTargeting.getDefaultInstance() : this.abiTargeting_;
            }

            private SingleFieldBuilderV3<AbiTargeting, AbiTargeting.Builder, AbiTargetingOrBuilder> getAbiTargetingFieldBuilder() {
                if (this.abiTargetingBuilder_ == null) {
                    this.abiTargetingBuilder_ = new SingleFieldBuilderV3(this.getAbiTargeting(), this.getParentForChildren(), this.isClean());
                    this.abiTargeting_ = null;
                }
                return this.abiTargetingBuilder_;
            }

            @Override
            public boolean hasGraphicsApiTargeting() {
                return this.graphicsApiTargetingBuilder_ != null || this.graphicsApiTargeting_ != null;
            }

            @Override
            public GraphicsApiTargeting getGraphicsApiTargeting() {
                if (this.graphicsApiTargetingBuilder_ == null) {
                    return this.graphicsApiTargeting_ == null ? GraphicsApiTargeting.getDefaultInstance() : this.graphicsApiTargeting_;
                }
                return this.graphicsApiTargetingBuilder_.getMessage();
            }

            public Builder setGraphicsApiTargeting(GraphicsApiTargeting value) {
                if (this.graphicsApiTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.graphicsApiTargeting_ = value;
                    this.onChanged();
                } else {
                    this.graphicsApiTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setGraphicsApiTargeting(GraphicsApiTargeting.Builder builderForValue) {
                if (this.graphicsApiTargetingBuilder_ == null) {
                    this.graphicsApiTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.graphicsApiTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeGraphicsApiTargeting(GraphicsApiTargeting value) {
                if (this.graphicsApiTargetingBuilder_ == null) {
                    this.graphicsApiTargeting_ = this.graphicsApiTargeting_ != null ? GraphicsApiTargeting.newBuilder(this.graphicsApiTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.graphicsApiTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearGraphicsApiTargeting() {
                if (this.graphicsApiTargetingBuilder_ == null) {
                    this.graphicsApiTargeting_ = null;
                    this.onChanged();
                } else {
                    this.graphicsApiTargeting_ = null;
                    this.graphicsApiTargetingBuilder_ = null;
                }
                return this;
            }

            public GraphicsApiTargeting.Builder getGraphicsApiTargetingBuilder() {
                this.onChanged();
                return this.getGraphicsApiTargetingFieldBuilder().getBuilder();
            }

            @Override
            public GraphicsApiTargetingOrBuilder getGraphicsApiTargetingOrBuilder() {
                if (this.graphicsApiTargetingBuilder_ != null) {
                    return this.graphicsApiTargetingBuilder_.getMessageOrBuilder();
                }
                return this.graphicsApiTargeting_ == null ? GraphicsApiTargeting.getDefaultInstance() : this.graphicsApiTargeting_;
            }

            private SingleFieldBuilderV3<GraphicsApiTargeting, GraphicsApiTargeting.Builder, GraphicsApiTargetingOrBuilder> getGraphicsApiTargetingFieldBuilder() {
                if (this.graphicsApiTargetingBuilder_ == null) {
                    this.graphicsApiTargetingBuilder_ = new SingleFieldBuilderV3(this.getGraphicsApiTargeting(), this.getParentForChildren(), this.isClean());
                    this.graphicsApiTargeting_ = null;
                }
                return this.graphicsApiTargetingBuilder_;
            }

            @Override
            public boolean hasLanguageTargeting() {
                return this.languageTargetingBuilder_ != null || this.languageTargeting_ != null;
            }

            @Override
            public LanguageTargeting getLanguageTargeting() {
                if (this.languageTargetingBuilder_ == null) {
                    return this.languageTargeting_ == null ? LanguageTargeting.getDefaultInstance() : this.languageTargeting_;
                }
                return this.languageTargetingBuilder_.getMessage();
            }

            public Builder setLanguageTargeting(LanguageTargeting value) {
                if (this.languageTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.languageTargeting_ = value;
                    this.onChanged();
                } else {
                    this.languageTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setLanguageTargeting(LanguageTargeting.Builder builderForValue) {
                if (this.languageTargetingBuilder_ == null) {
                    this.languageTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.languageTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeLanguageTargeting(LanguageTargeting value) {
                if (this.languageTargetingBuilder_ == null) {
                    this.languageTargeting_ = this.languageTargeting_ != null ? LanguageTargeting.newBuilder(this.languageTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.languageTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearLanguageTargeting() {
                if (this.languageTargetingBuilder_ == null) {
                    this.languageTargeting_ = null;
                    this.onChanged();
                } else {
                    this.languageTargeting_ = null;
                    this.languageTargetingBuilder_ = null;
                }
                return this;
            }

            public LanguageTargeting.Builder getLanguageTargetingBuilder() {
                this.onChanged();
                return this.getLanguageTargetingFieldBuilder().getBuilder();
            }

            @Override
            public LanguageTargetingOrBuilder getLanguageTargetingOrBuilder() {
                if (this.languageTargetingBuilder_ != null) {
                    return this.languageTargetingBuilder_.getMessageOrBuilder();
                }
                return this.languageTargeting_ == null ? LanguageTargeting.getDefaultInstance() : this.languageTargeting_;
            }

            private SingleFieldBuilderV3<LanguageTargeting, LanguageTargeting.Builder, LanguageTargetingOrBuilder> getLanguageTargetingFieldBuilder() {
                if (this.languageTargetingBuilder_ == null) {
                    this.languageTargetingBuilder_ = new SingleFieldBuilderV3(this.getLanguageTargeting(), this.getParentForChildren(), this.isClean());
                    this.languageTargeting_ = null;
                }
                return this.languageTargetingBuilder_;
            }

            @Override
            public boolean hasScreenDensityTargeting() {
                return this.screenDensityTargetingBuilder_ != null || this.screenDensityTargeting_ != null;
            }

            @Override
            public ScreenDensityTargeting getScreenDensityTargeting() {
                if (this.screenDensityTargetingBuilder_ == null) {
                    return this.screenDensityTargeting_ == null ? ScreenDensityTargeting.getDefaultInstance() : this.screenDensityTargeting_;
                }
                return this.screenDensityTargetingBuilder_.getMessage();
            }

            public Builder setScreenDensityTargeting(ScreenDensityTargeting value) {
                if (this.screenDensityTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.screenDensityTargeting_ = value;
                    this.onChanged();
                } else {
                    this.screenDensityTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setScreenDensityTargeting(ScreenDensityTargeting.Builder builderForValue) {
                if (this.screenDensityTargetingBuilder_ == null) {
                    this.screenDensityTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.screenDensityTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeScreenDensityTargeting(ScreenDensityTargeting value) {
                if (this.screenDensityTargetingBuilder_ == null) {
                    this.screenDensityTargeting_ = this.screenDensityTargeting_ != null ? ScreenDensityTargeting.newBuilder(this.screenDensityTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.screenDensityTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearScreenDensityTargeting() {
                if (this.screenDensityTargetingBuilder_ == null) {
                    this.screenDensityTargeting_ = null;
                    this.onChanged();
                } else {
                    this.screenDensityTargeting_ = null;
                    this.screenDensityTargetingBuilder_ = null;
                }
                return this;
            }

            public ScreenDensityTargeting.Builder getScreenDensityTargetingBuilder() {
                this.onChanged();
                return this.getScreenDensityTargetingFieldBuilder().getBuilder();
            }

            @Override
            public ScreenDensityTargetingOrBuilder getScreenDensityTargetingOrBuilder() {
                if (this.screenDensityTargetingBuilder_ != null) {
                    return this.screenDensityTargetingBuilder_.getMessageOrBuilder();
                }
                return this.screenDensityTargeting_ == null ? ScreenDensityTargeting.getDefaultInstance() : this.screenDensityTargeting_;
            }

            private SingleFieldBuilderV3<ScreenDensityTargeting, ScreenDensityTargeting.Builder, ScreenDensityTargetingOrBuilder> getScreenDensityTargetingFieldBuilder() {
                if (this.screenDensityTargetingBuilder_ == null) {
                    this.screenDensityTargetingBuilder_ = new SingleFieldBuilderV3(this.getScreenDensityTargeting(), this.getParentForChildren(), this.isClean());
                    this.screenDensityTargeting_ = null;
                }
                return this.screenDensityTargetingBuilder_;
            }

            @Override
            public boolean hasSdkVersionTargeting() {
                return this.sdkVersionTargetingBuilder_ != null || this.sdkVersionTargeting_ != null;
            }

            @Override
            public SdkVersionTargeting getSdkVersionTargeting() {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    return this.sdkVersionTargeting_ == null ? SdkVersionTargeting.getDefaultInstance() : this.sdkVersionTargeting_;
                }
                return this.sdkVersionTargetingBuilder_.getMessage();
            }

            public Builder setSdkVersionTargeting(SdkVersionTargeting value) {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.sdkVersionTargeting_ = value;
                    this.onChanged();
                } else {
                    this.sdkVersionTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setSdkVersionTargeting(SdkVersionTargeting.Builder builderForValue) {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.sdkVersionTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeSdkVersionTargeting(SdkVersionTargeting value) {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = this.sdkVersionTargeting_ != null ? SdkVersionTargeting.newBuilder(this.sdkVersionTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.sdkVersionTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearSdkVersionTargeting() {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = null;
                    this.onChanged();
                } else {
                    this.sdkVersionTargeting_ = null;
                    this.sdkVersionTargetingBuilder_ = null;
                }
                return this;
            }

            public SdkVersionTargeting.Builder getSdkVersionTargetingBuilder() {
                this.onChanged();
                return this.getSdkVersionTargetingFieldBuilder().getBuilder();
            }

            @Override
            public SdkVersionTargetingOrBuilder getSdkVersionTargetingOrBuilder() {
                if (this.sdkVersionTargetingBuilder_ != null) {
                    return this.sdkVersionTargetingBuilder_.getMessageOrBuilder();
                }
                return this.sdkVersionTargeting_ == null ? SdkVersionTargeting.getDefaultInstance() : this.sdkVersionTargeting_;
            }

            private SingleFieldBuilderV3<SdkVersionTargeting, SdkVersionTargeting.Builder, SdkVersionTargetingOrBuilder> getSdkVersionTargetingFieldBuilder() {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargetingBuilder_ = new SingleFieldBuilderV3(this.getSdkVersionTargeting(), this.getParentForChildren(), this.isClean());
                    this.sdkVersionTargeting_ = null;
                }
                return this.sdkVersionTargetingBuilder_;
            }

            @Override
            public boolean hasTextureCompressionFormatTargeting() {
                return this.textureCompressionFormatTargetingBuilder_ != null || this.textureCompressionFormatTargeting_ != null;
            }

            @Override
            public TextureCompressionFormatTargeting getTextureCompressionFormatTargeting() {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    return this.textureCompressionFormatTargeting_ == null ? TextureCompressionFormatTargeting.getDefaultInstance() : this.textureCompressionFormatTargeting_;
                }
                return this.textureCompressionFormatTargetingBuilder_.getMessage();
            }

            public Builder setTextureCompressionFormatTargeting(TextureCompressionFormatTargeting value) {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.textureCompressionFormatTargeting_ = value;
                    this.onChanged();
                } else {
                    this.textureCompressionFormatTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setTextureCompressionFormatTargeting(TextureCompressionFormatTargeting.Builder builderForValue) {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    this.textureCompressionFormatTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.textureCompressionFormatTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTextureCompressionFormatTargeting(TextureCompressionFormatTargeting value) {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    this.textureCompressionFormatTargeting_ = this.textureCompressionFormatTargeting_ != null ? TextureCompressionFormatTargeting.newBuilder(this.textureCompressionFormatTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.textureCompressionFormatTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearTextureCompressionFormatTargeting() {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    this.textureCompressionFormatTargeting_ = null;
                    this.onChanged();
                } else {
                    this.textureCompressionFormatTargeting_ = null;
                    this.textureCompressionFormatTargetingBuilder_ = null;
                }
                return this;
            }

            public TextureCompressionFormatTargeting.Builder getTextureCompressionFormatTargetingBuilder() {
                this.onChanged();
                return this.getTextureCompressionFormatTargetingFieldBuilder().getBuilder();
            }

            @Override
            public TextureCompressionFormatTargetingOrBuilder getTextureCompressionFormatTargetingOrBuilder() {
                if (this.textureCompressionFormatTargetingBuilder_ != null) {
                    return this.textureCompressionFormatTargetingBuilder_.getMessageOrBuilder();
                }
                return this.textureCompressionFormatTargeting_ == null ? TextureCompressionFormatTargeting.getDefaultInstance() : this.textureCompressionFormatTargeting_;
            }

            private SingleFieldBuilderV3<TextureCompressionFormatTargeting, TextureCompressionFormatTargeting.Builder, TextureCompressionFormatTargetingOrBuilder> getTextureCompressionFormatTargetingFieldBuilder() {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    this.textureCompressionFormatTargetingBuilder_ = new SingleFieldBuilderV3(this.getTextureCompressionFormatTargeting(), this.getParentForChildren(), this.isClean());
                    this.textureCompressionFormatTargeting_ = null;
                }
                return this.textureCompressionFormatTargetingBuilder_;
            }

            @Override
            public boolean hasMultiAbiTargeting() {
                return this.multiAbiTargetingBuilder_ != null || this.multiAbiTargeting_ != null;
            }

            @Override
            public MultiAbiTargeting getMultiAbiTargeting() {
                if (this.multiAbiTargetingBuilder_ == null) {
                    return this.multiAbiTargeting_ == null ? MultiAbiTargeting.getDefaultInstance() : this.multiAbiTargeting_;
                }
                return this.multiAbiTargetingBuilder_.getMessage();
            }

            public Builder setMultiAbiTargeting(MultiAbiTargeting value) {
                if (this.multiAbiTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.multiAbiTargeting_ = value;
                    this.onChanged();
                } else {
                    this.multiAbiTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setMultiAbiTargeting(MultiAbiTargeting.Builder builderForValue) {
                if (this.multiAbiTargetingBuilder_ == null) {
                    this.multiAbiTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.multiAbiTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeMultiAbiTargeting(MultiAbiTargeting value) {
                if (this.multiAbiTargetingBuilder_ == null) {
                    this.multiAbiTargeting_ = this.multiAbiTargeting_ != null ? MultiAbiTargeting.newBuilder(this.multiAbiTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.multiAbiTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearMultiAbiTargeting() {
                if (this.multiAbiTargetingBuilder_ == null) {
                    this.multiAbiTargeting_ = null;
                    this.onChanged();
                } else {
                    this.multiAbiTargeting_ = null;
                    this.multiAbiTargetingBuilder_ = null;
                }
                return this;
            }

            public MultiAbiTargeting.Builder getMultiAbiTargetingBuilder() {
                this.onChanged();
                return this.getMultiAbiTargetingFieldBuilder().getBuilder();
            }

            @Override
            public MultiAbiTargetingOrBuilder getMultiAbiTargetingOrBuilder() {
                if (this.multiAbiTargetingBuilder_ != null) {
                    return this.multiAbiTargetingBuilder_.getMessageOrBuilder();
                }
                return this.multiAbiTargeting_ == null ? MultiAbiTargeting.getDefaultInstance() : this.multiAbiTargeting_;
            }

            private SingleFieldBuilderV3<MultiAbiTargeting, MultiAbiTargeting.Builder, MultiAbiTargetingOrBuilder> getMultiAbiTargetingFieldBuilder() {
                if (this.multiAbiTargetingBuilder_ == null) {
                    this.multiAbiTargetingBuilder_ = new SingleFieldBuilderV3(this.getMultiAbiTargeting(), this.getParentForChildren(), this.isClean());
                    this.multiAbiTargeting_ = null;
                }
                return this.multiAbiTargetingBuilder_;
            }

            @Override
            public boolean hasSanitizerTargeting() {
                return this.sanitizerTargetingBuilder_ != null || this.sanitizerTargeting_ != null;
            }

            @Override
            public SanitizerTargeting getSanitizerTargeting() {
                if (this.sanitizerTargetingBuilder_ == null) {
                    return this.sanitizerTargeting_ == null ? SanitizerTargeting.getDefaultInstance() : this.sanitizerTargeting_;
                }
                return this.sanitizerTargetingBuilder_.getMessage();
            }

            public Builder setSanitizerTargeting(SanitizerTargeting value) {
                if (this.sanitizerTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.sanitizerTargeting_ = value;
                    this.onChanged();
                } else {
                    this.sanitizerTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setSanitizerTargeting(SanitizerTargeting.Builder builderForValue) {
                if (this.sanitizerTargetingBuilder_ == null) {
                    this.sanitizerTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.sanitizerTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeSanitizerTargeting(SanitizerTargeting value) {
                if (this.sanitizerTargetingBuilder_ == null) {
                    this.sanitizerTargeting_ = this.sanitizerTargeting_ != null ? SanitizerTargeting.newBuilder(this.sanitizerTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.sanitizerTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearSanitizerTargeting() {
                if (this.sanitizerTargetingBuilder_ == null) {
                    this.sanitizerTargeting_ = null;
                    this.onChanged();
                } else {
                    this.sanitizerTargeting_ = null;
                    this.sanitizerTargetingBuilder_ = null;
                }
                return this;
            }

            public SanitizerTargeting.Builder getSanitizerTargetingBuilder() {
                this.onChanged();
                return this.getSanitizerTargetingFieldBuilder().getBuilder();
            }

            @Override
            public SanitizerTargetingOrBuilder getSanitizerTargetingOrBuilder() {
                if (this.sanitizerTargetingBuilder_ != null) {
                    return this.sanitizerTargetingBuilder_.getMessageOrBuilder();
                }
                return this.sanitizerTargeting_ == null ? SanitizerTargeting.getDefaultInstance() : this.sanitizerTargeting_;
            }

            private SingleFieldBuilderV3<SanitizerTargeting, SanitizerTargeting.Builder, SanitizerTargetingOrBuilder> getSanitizerTargetingFieldBuilder() {
                if (this.sanitizerTargetingBuilder_ == null) {
                    this.sanitizerTargetingBuilder_ = new SingleFieldBuilderV3(this.getSanitizerTargeting(), this.getParentForChildren(), this.isClean());
                    this.sanitizerTargeting_ = null;
                }
                return this.sanitizerTargetingBuilder_;
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

    public static interface ApkTargetingOrBuilder
    extends MessageOrBuilder {
        public boolean hasAbiTargeting();

        public AbiTargeting getAbiTargeting();

        public AbiTargetingOrBuilder getAbiTargetingOrBuilder();

        public boolean hasGraphicsApiTargeting();

        public GraphicsApiTargeting getGraphicsApiTargeting();

        public GraphicsApiTargetingOrBuilder getGraphicsApiTargetingOrBuilder();

        public boolean hasLanguageTargeting();

        public LanguageTargeting getLanguageTargeting();

        public LanguageTargetingOrBuilder getLanguageTargetingOrBuilder();

        public boolean hasScreenDensityTargeting();

        public ScreenDensityTargeting getScreenDensityTargeting();

        public ScreenDensityTargetingOrBuilder getScreenDensityTargetingOrBuilder();

        public boolean hasSdkVersionTargeting();

        public SdkVersionTargeting getSdkVersionTargeting();

        public SdkVersionTargetingOrBuilder getSdkVersionTargetingOrBuilder();

        public boolean hasTextureCompressionFormatTargeting();

        public TextureCompressionFormatTargeting getTextureCompressionFormatTargeting();

        public TextureCompressionFormatTargetingOrBuilder getTextureCompressionFormatTargetingOrBuilder();

        public boolean hasMultiAbiTargeting();

        public MultiAbiTargeting getMultiAbiTargeting();

        public MultiAbiTargetingOrBuilder getMultiAbiTargetingOrBuilder();

        public boolean hasSanitizerTargeting();

        public SanitizerTargeting getSanitizerTargeting();

        public SanitizerTargetingOrBuilder getSanitizerTargetingOrBuilder();
    }

    public static final class VariantTargeting
    extends GeneratedMessageV3
    implements VariantTargetingOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int SDK_VERSION_TARGETING_FIELD_NUMBER = 1;
        private SdkVersionTargeting sdkVersionTargeting_;
        public static final int ABI_TARGETING_FIELD_NUMBER = 2;
        private AbiTargeting abiTargeting_;
        public static final int SCREEN_DENSITY_TARGETING_FIELD_NUMBER = 3;
        private ScreenDensityTargeting screenDensityTargeting_;
        public static final int MULTI_ABI_TARGETING_FIELD_NUMBER = 4;
        private MultiAbiTargeting multiAbiTargeting_;
        public static final int TEXTURE_COMPRESSION_FORMAT_TARGETING_FIELD_NUMBER = 5;
        private TextureCompressionFormatTargeting textureCompressionFormatTargeting_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final VariantTargeting DEFAULT_INSTANCE = new VariantTargeting();
        private static final Parser<VariantTargeting> PARSER = new AbstractParser<VariantTargeting>(){

            @Override
            public VariantTargeting parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new VariantTargeting(input, extensionRegistry);
            }
        };

        private VariantTargeting(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private VariantTargeting() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private VariantTargeting(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (this.sdkVersionTargeting_ != null) {
                                subBuilder = this.sdkVersionTargeting_.toBuilder();
                            }
                            this.sdkVersionTargeting_ = input.readMessage(SdkVersionTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((SdkVersionTargeting.Builder)subBuilder).mergeFrom(this.sdkVersionTargeting_);
                            this.sdkVersionTargeting_ = ((SdkVersionTargeting.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 18: {
                            subBuilder = null;
                            if (this.abiTargeting_ != null) {
                                subBuilder = this.abiTargeting_.toBuilder();
                            }
                            this.abiTargeting_ = input.readMessage(AbiTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((AbiTargeting.Builder)subBuilder).mergeFrom(this.abiTargeting_);
                            this.abiTargeting_ = ((AbiTargeting.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 26: {
                            subBuilder = null;
                            if (this.screenDensityTargeting_ != null) {
                                subBuilder = this.screenDensityTargeting_.toBuilder();
                            }
                            this.screenDensityTargeting_ = input.readMessage(ScreenDensityTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((ScreenDensityTargeting.Builder)subBuilder).mergeFrom(this.screenDensityTargeting_);
                            this.screenDensityTargeting_ = ((ScreenDensityTargeting.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 34: {
                            subBuilder = null;
                            if (this.multiAbiTargeting_ != null) {
                                subBuilder = this.multiAbiTargeting_.toBuilder();
                            }
                            this.multiAbiTargeting_ = input.readMessage(MultiAbiTargeting.parser(), extensionRegistry);
                            if (subBuilder == null) continue block14;
                            ((MultiAbiTargeting.Builder)subBuilder).mergeFrom(this.multiAbiTargeting_);
                            this.multiAbiTargeting_ = ((MultiAbiTargeting.Builder)subBuilder).buildPartial();
                            continue block14;
                        }
                        case 42: 
                    }
                    subBuilder = null;
                    if (this.textureCompressionFormatTargeting_ != null) {
                        subBuilder = this.textureCompressionFormatTargeting_.toBuilder();
                    }
                    this.textureCompressionFormatTargeting_ = input.readMessage(TextureCompressionFormatTargeting.parser(), extensionRegistry);
                    if (subBuilder == null) continue;
                    ((TextureCompressionFormatTargeting.Builder)subBuilder).mergeFrom(this.textureCompressionFormatTargeting_);
                    this.textureCompressionFormatTargeting_ = ((TextureCompressionFormatTargeting.Builder)subBuilder).buildPartial();
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
            return internal_static_android_bundle_VariantTargeting_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_VariantTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(VariantTargeting.class, Builder.class);
        }

        @Override
        public boolean hasSdkVersionTargeting() {
            return this.sdkVersionTargeting_ != null;
        }

        @Override
        public SdkVersionTargeting getSdkVersionTargeting() {
            return this.sdkVersionTargeting_ == null ? SdkVersionTargeting.getDefaultInstance() : this.sdkVersionTargeting_;
        }

        @Override
        public SdkVersionTargetingOrBuilder getSdkVersionTargetingOrBuilder() {
            return this.getSdkVersionTargeting();
        }

        @Override
        public boolean hasAbiTargeting() {
            return this.abiTargeting_ != null;
        }

        @Override
        public AbiTargeting getAbiTargeting() {
            return this.abiTargeting_ == null ? AbiTargeting.getDefaultInstance() : this.abiTargeting_;
        }

        @Override
        public AbiTargetingOrBuilder getAbiTargetingOrBuilder() {
            return this.getAbiTargeting();
        }

        @Override
        public boolean hasScreenDensityTargeting() {
            return this.screenDensityTargeting_ != null;
        }

        @Override
        public ScreenDensityTargeting getScreenDensityTargeting() {
            return this.screenDensityTargeting_ == null ? ScreenDensityTargeting.getDefaultInstance() : this.screenDensityTargeting_;
        }

        @Override
        public ScreenDensityTargetingOrBuilder getScreenDensityTargetingOrBuilder() {
            return this.getScreenDensityTargeting();
        }

        @Override
        public boolean hasMultiAbiTargeting() {
            return this.multiAbiTargeting_ != null;
        }

        @Override
        public MultiAbiTargeting getMultiAbiTargeting() {
            return this.multiAbiTargeting_ == null ? MultiAbiTargeting.getDefaultInstance() : this.multiAbiTargeting_;
        }

        @Override
        public MultiAbiTargetingOrBuilder getMultiAbiTargetingOrBuilder() {
            return this.getMultiAbiTargeting();
        }

        @Override
        public boolean hasTextureCompressionFormatTargeting() {
            return this.textureCompressionFormatTargeting_ != null;
        }

        @Override
        public TextureCompressionFormatTargeting getTextureCompressionFormatTargeting() {
            return this.textureCompressionFormatTargeting_ == null ? TextureCompressionFormatTargeting.getDefaultInstance() : this.textureCompressionFormatTargeting_;
        }

        @Override
        public TextureCompressionFormatTargetingOrBuilder getTextureCompressionFormatTargetingOrBuilder() {
            return this.getTextureCompressionFormatTargeting();
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
            if (this.sdkVersionTargeting_ != null) {
                output.writeMessage(1, this.getSdkVersionTargeting());
            }
            if (this.abiTargeting_ != null) {
                output.writeMessage(2, this.getAbiTargeting());
            }
            if (this.screenDensityTargeting_ != null) {
                output.writeMessage(3, this.getScreenDensityTargeting());
            }
            if (this.multiAbiTargeting_ != null) {
                output.writeMessage(4, this.getMultiAbiTargeting());
            }
            if (this.textureCompressionFormatTargeting_ != null) {
                output.writeMessage(5, this.getTextureCompressionFormatTargeting());
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
            if (this.sdkVersionTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(1, this.getSdkVersionTargeting());
            }
            if (this.abiTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(2, this.getAbiTargeting());
            }
            if (this.screenDensityTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(3, this.getScreenDensityTargeting());
            }
            if (this.multiAbiTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(4, this.getMultiAbiTargeting());
            }
            if (this.textureCompressionFormatTargeting_ != null) {
                size += CodedOutputStream.computeMessageSize(5, this.getTextureCompressionFormatTargeting());
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof VariantTargeting)) {
                return super.equals(obj);
            }
            VariantTargeting other = (VariantTargeting)obj;
            boolean result = true;
            boolean bl = result = result && this.hasSdkVersionTargeting() == other.hasSdkVersionTargeting();
            if (this.hasSdkVersionTargeting()) {
                result = result && this.getSdkVersionTargeting().equals(other.getSdkVersionTargeting());
            }
            boolean bl2 = result = result && this.hasAbiTargeting() == other.hasAbiTargeting();
            if (this.hasAbiTargeting()) {
                result = result && this.getAbiTargeting().equals(other.getAbiTargeting());
            }
            boolean bl3 = result = result && this.hasScreenDensityTargeting() == other.hasScreenDensityTargeting();
            if (this.hasScreenDensityTargeting()) {
                result = result && this.getScreenDensityTargeting().equals(other.getScreenDensityTargeting());
            }
            boolean bl4 = result = result && this.hasMultiAbiTargeting() == other.hasMultiAbiTargeting();
            if (this.hasMultiAbiTargeting()) {
                result = result && this.getMultiAbiTargeting().equals(other.getMultiAbiTargeting());
            }
            boolean bl5 = result = result && this.hasTextureCompressionFormatTargeting() == other.hasTextureCompressionFormatTargeting();
            if (this.hasTextureCompressionFormatTargeting()) {
                result = result && this.getTextureCompressionFormatTargeting().equals(other.getTextureCompressionFormatTargeting());
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
            hash = 19 * hash + VariantTargeting.getDescriptor().hashCode();
            if (this.hasSdkVersionTargeting()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getSdkVersionTargeting().hashCode();
            }
            if (this.hasAbiTargeting()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAbiTargeting().hashCode();
            }
            if (this.hasScreenDensityTargeting()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getScreenDensityTargeting().hashCode();
            }
            if (this.hasMultiAbiTargeting()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getMultiAbiTargeting().hashCode();
            }
            if (this.hasTextureCompressionFormatTargeting()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getTextureCompressionFormatTargeting().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static VariantTargeting parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static VariantTargeting parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static VariantTargeting parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static VariantTargeting parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static VariantTargeting parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static VariantTargeting parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static VariantTargeting parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static VariantTargeting parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static VariantTargeting parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static VariantTargeting parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static VariantTargeting parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static VariantTargeting parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return VariantTargeting.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(VariantTargeting prototype) {
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

        public static VariantTargeting getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<VariantTargeting> parser() {
            return PARSER;
        }

        public Parser<VariantTargeting> getParserForType() {
            return PARSER;
        }

        @Override
        public VariantTargeting getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements VariantTargetingOrBuilder {
            private SdkVersionTargeting sdkVersionTargeting_ = null;
            private SingleFieldBuilderV3<SdkVersionTargeting, SdkVersionTargeting.Builder, SdkVersionTargetingOrBuilder> sdkVersionTargetingBuilder_;
            private AbiTargeting abiTargeting_ = null;
            private SingleFieldBuilderV3<AbiTargeting, AbiTargeting.Builder, AbiTargetingOrBuilder> abiTargetingBuilder_;
            private ScreenDensityTargeting screenDensityTargeting_ = null;
            private SingleFieldBuilderV3<ScreenDensityTargeting, ScreenDensityTargeting.Builder, ScreenDensityTargetingOrBuilder> screenDensityTargetingBuilder_;
            private MultiAbiTargeting multiAbiTargeting_ = null;
            private SingleFieldBuilderV3<MultiAbiTargeting, MultiAbiTargeting.Builder, MultiAbiTargetingOrBuilder> multiAbiTargetingBuilder_;
            private TextureCompressionFormatTargeting textureCompressionFormatTargeting_ = null;
            private SingleFieldBuilderV3<TextureCompressionFormatTargeting, TextureCompressionFormatTargeting.Builder, TextureCompressionFormatTargetingOrBuilder> textureCompressionFormatTargetingBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_VariantTargeting_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_VariantTargeting_fieldAccessorTable.ensureFieldAccessorsInitialized(VariantTargeting.class, Builder.class);
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
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = null;
                } else {
                    this.sdkVersionTargeting_ = null;
                    this.sdkVersionTargetingBuilder_ = null;
                }
                if (this.abiTargetingBuilder_ == null) {
                    this.abiTargeting_ = null;
                } else {
                    this.abiTargeting_ = null;
                    this.abiTargetingBuilder_ = null;
                }
                if (this.screenDensityTargetingBuilder_ == null) {
                    this.screenDensityTargeting_ = null;
                } else {
                    this.screenDensityTargeting_ = null;
                    this.screenDensityTargetingBuilder_ = null;
                }
                if (this.multiAbiTargetingBuilder_ == null) {
                    this.multiAbiTargeting_ = null;
                } else {
                    this.multiAbiTargeting_ = null;
                    this.multiAbiTargetingBuilder_ = null;
                }
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    this.textureCompressionFormatTargeting_ = null;
                } else {
                    this.textureCompressionFormatTargeting_ = null;
                    this.textureCompressionFormatTargetingBuilder_ = null;
                }
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_VariantTargeting_descriptor;
            }

            @Override
            public VariantTargeting getDefaultInstanceForType() {
                return VariantTargeting.getDefaultInstance();
            }

            @Override
            public VariantTargeting build() {
                VariantTargeting result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public VariantTargeting buildPartial() {
                VariantTargeting result = new VariantTargeting(this);
                if (this.sdkVersionTargetingBuilder_ == null) {
                    result.sdkVersionTargeting_ = this.sdkVersionTargeting_;
                } else {
                    result.sdkVersionTargeting_ = this.sdkVersionTargetingBuilder_.build();
                }
                if (this.abiTargetingBuilder_ == null) {
                    result.abiTargeting_ = this.abiTargeting_;
                } else {
                    result.abiTargeting_ = this.abiTargetingBuilder_.build();
                }
                if (this.screenDensityTargetingBuilder_ == null) {
                    result.screenDensityTargeting_ = this.screenDensityTargeting_;
                } else {
                    result.screenDensityTargeting_ = this.screenDensityTargetingBuilder_.build();
                }
                if (this.multiAbiTargetingBuilder_ == null) {
                    result.multiAbiTargeting_ = this.multiAbiTargeting_;
                } else {
                    result.multiAbiTargeting_ = this.multiAbiTargetingBuilder_.build();
                }
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    result.textureCompressionFormatTargeting_ = this.textureCompressionFormatTargeting_;
                } else {
                    result.textureCompressionFormatTargeting_ = this.textureCompressionFormatTargetingBuilder_.build();
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
                if (other instanceof VariantTargeting) {
                    return this.mergeFrom((VariantTargeting)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(VariantTargeting other) {
                if (other == VariantTargeting.getDefaultInstance()) {
                    return this;
                }
                if (other.hasSdkVersionTargeting()) {
                    this.mergeSdkVersionTargeting(other.getSdkVersionTargeting());
                }
                if (other.hasAbiTargeting()) {
                    this.mergeAbiTargeting(other.getAbiTargeting());
                }
                if (other.hasScreenDensityTargeting()) {
                    this.mergeScreenDensityTargeting(other.getScreenDensityTargeting());
                }
                if (other.hasMultiAbiTargeting()) {
                    this.mergeMultiAbiTargeting(other.getMultiAbiTargeting());
                }
                if (other.hasTextureCompressionFormatTargeting()) {
                    this.mergeTextureCompressionFormatTargeting(other.getTextureCompressionFormatTargeting());
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
                VariantTargeting parsedMessage = null;
                try {
                    parsedMessage = (VariantTargeting)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (VariantTargeting)e2.getUnfinishedMessage();
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
            public boolean hasSdkVersionTargeting() {
                return this.sdkVersionTargetingBuilder_ != null || this.sdkVersionTargeting_ != null;
            }

            @Override
            public SdkVersionTargeting getSdkVersionTargeting() {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    return this.sdkVersionTargeting_ == null ? SdkVersionTargeting.getDefaultInstance() : this.sdkVersionTargeting_;
                }
                return this.sdkVersionTargetingBuilder_.getMessage();
            }

            public Builder setSdkVersionTargeting(SdkVersionTargeting value) {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.sdkVersionTargeting_ = value;
                    this.onChanged();
                } else {
                    this.sdkVersionTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setSdkVersionTargeting(SdkVersionTargeting.Builder builderForValue) {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.sdkVersionTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeSdkVersionTargeting(SdkVersionTargeting value) {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = this.sdkVersionTargeting_ != null ? SdkVersionTargeting.newBuilder(this.sdkVersionTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.sdkVersionTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearSdkVersionTargeting() {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargeting_ = null;
                    this.onChanged();
                } else {
                    this.sdkVersionTargeting_ = null;
                    this.sdkVersionTargetingBuilder_ = null;
                }
                return this;
            }

            public SdkVersionTargeting.Builder getSdkVersionTargetingBuilder() {
                this.onChanged();
                return this.getSdkVersionTargetingFieldBuilder().getBuilder();
            }

            @Override
            public SdkVersionTargetingOrBuilder getSdkVersionTargetingOrBuilder() {
                if (this.sdkVersionTargetingBuilder_ != null) {
                    return this.sdkVersionTargetingBuilder_.getMessageOrBuilder();
                }
                return this.sdkVersionTargeting_ == null ? SdkVersionTargeting.getDefaultInstance() : this.sdkVersionTargeting_;
            }

            private SingleFieldBuilderV3<SdkVersionTargeting, SdkVersionTargeting.Builder, SdkVersionTargetingOrBuilder> getSdkVersionTargetingFieldBuilder() {
                if (this.sdkVersionTargetingBuilder_ == null) {
                    this.sdkVersionTargetingBuilder_ = new SingleFieldBuilderV3(this.getSdkVersionTargeting(), this.getParentForChildren(), this.isClean());
                    this.sdkVersionTargeting_ = null;
                }
                return this.sdkVersionTargetingBuilder_;
            }

            @Override
            public boolean hasAbiTargeting() {
                return this.abiTargetingBuilder_ != null || this.abiTargeting_ != null;
            }

            @Override
            public AbiTargeting getAbiTargeting() {
                if (this.abiTargetingBuilder_ == null) {
                    return this.abiTargeting_ == null ? AbiTargeting.getDefaultInstance() : this.abiTargeting_;
                }
                return this.abiTargetingBuilder_.getMessage();
            }

            public Builder setAbiTargeting(AbiTargeting value) {
                if (this.abiTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.abiTargeting_ = value;
                    this.onChanged();
                } else {
                    this.abiTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setAbiTargeting(AbiTargeting.Builder builderForValue) {
                if (this.abiTargetingBuilder_ == null) {
                    this.abiTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.abiTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeAbiTargeting(AbiTargeting value) {
                if (this.abiTargetingBuilder_ == null) {
                    this.abiTargeting_ = this.abiTargeting_ != null ? AbiTargeting.newBuilder(this.abiTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.abiTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearAbiTargeting() {
                if (this.abiTargetingBuilder_ == null) {
                    this.abiTargeting_ = null;
                    this.onChanged();
                } else {
                    this.abiTargeting_ = null;
                    this.abiTargetingBuilder_ = null;
                }
                return this;
            }

            public AbiTargeting.Builder getAbiTargetingBuilder() {
                this.onChanged();
                return this.getAbiTargetingFieldBuilder().getBuilder();
            }

            @Override
            public AbiTargetingOrBuilder getAbiTargetingOrBuilder() {
                if (this.abiTargetingBuilder_ != null) {
                    return this.abiTargetingBuilder_.getMessageOrBuilder();
                }
                return this.abiTargeting_ == null ? AbiTargeting.getDefaultInstance() : this.abiTargeting_;
            }

            private SingleFieldBuilderV3<AbiTargeting, AbiTargeting.Builder, AbiTargetingOrBuilder> getAbiTargetingFieldBuilder() {
                if (this.abiTargetingBuilder_ == null) {
                    this.abiTargetingBuilder_ = new SingleFieldBuilderV3(this.getAbiTargeting(), this.getParentForChildren(), this.isClean());
                    this.abiTargeting_ = null;
                }
                return this.abiTargetingBuilder_;
            }

            @Override
            public boolean hasScreenDensityTargeting() {
                return this.screenDensityTargetingBuilder_ != null || this.screenDensityTargeting_ != null;
            }

            @Override
            public ScreenDensityTargeting getScreenDensityTargeting() {
                if (this.screenDensityTargetingBuilder_ == null) {
                    return this.screenDensityTargeting_ == null ? ScreenDensityTargeting.getDefaultInstance() : this.screenDensityTargeting_;
                }
                return this.screenDensityTargetingBuilder_.getMessage();
            }

            public Builder setScreenDensityTargeting(ScreenDensityTargeting value) {
                if (this.screenDensityTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.screenDensityTargeting_ = value;
                    this.onChanged();
                } else {
                    this.screenDensityTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setScreenDensityTargeting(ScreenDensityTargeting.Builder builderForValue) {
                if (this.screenDensityTargetingBuilder_ == null) {
                    this.screenDensityTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.screenDensityTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeScreenDensityTargeting(ScreenDensityTargeting value) {
                if (this.screenDensityTargetingBuilder_ == null) {
                    this.screenDensityTargeting_ = this.screenDensityTargeting_ != null ? ScreenDensityTargeting.newBuilder(this.screenDensityTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.screenDensityTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearScreenDensityTargeting() {
                if (this.screenDensityTargetingBuilder_ == null) {
                    this.screenDensityTargeting_ = null;
                    this.onChanged();
                } else {
                    this.screenDensityTargeting_ = null;
                    this.screenDensityTargetingBuilder_ = null;
                }
                return this;
            }

            public ScreenDensityTargeting.Builder getScreenDensityTargetingBuilder() {
                this.onChanged();
                return this.getScreenDensityTargetingFieldBuilder().getBuilder();
            }

            @Override
            public ScreenDensityTargetingOrBuilder getScreenDensityTargetingOrBuilder() {
                if (this.screenDensityTargetingBuilder_ != null) {
                    return this.screenDensityTargetingBuilder_.getMessageOrBuilder();
                }
                return this.screenDensityTargeting_ == null ? ScreenDensityTargeting.getDefaultInstance() : this.screenDensityTargeting_;
            }

            private SingleFieldBuilderV3<ScreenDensityTargeting, ScreenDensityTargeting.Builder, ScreenDensityTargetingOrBuilder> getScreenDensityTargetingFieldBuilder() {
                if (this.screenDensityTargetingBuilder_ == null) {
                    this.screenDensityTargetingBuilder_ = new SingleFieldBuilderV3(this.getScreenDensityTargeting(), this.getParentForChildren(), this.isClean());
                    this.screenDensityTargeting_ = null;
                }
                return this.screenDensityTargetingBuilder_;
            }

            @Override
            public boolean hasMultiAbiTargeting() {
                return this.multiAbiTargetingBuilder_ != null || this.multiAbiTargeting_ != null;
            }

            @Override
            public MultiAbiTargeting getMultiAbiTargeting() {
                if (this.multiAbiTargetingBuilder_ == null) {
                    return this.multiAbiTargeting_ == null ? MultiAbiTargeting.getDefaultInstance() : this.multiAbiTargeting_;
                }
                return this.multiAbiTargetingBuilder_.getMessage();
            }

            public Builder setMultiAbiTargeting(MultiAbiTargeting value) {
                if (this.multiAbiTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.multiAbiTargeting_ = value;
                    this.onChanged();
                } else {
                    this.multiAbiTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setMultiAbiTargeting(MultiAbiTargeting.Builder builderForValue) {
                if (this.multiAbiTargetingBuilder_ == null) {
                    this.multiAbiTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.multiAbiTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeMultiAbiTargeting(MultiAbiTargeting value) {
                if (this.multiAbiTargetingBuilder_ == null) {
                    this.multiAbiTargeting_ = this.multiAbiTargeting_ != null ? MultiAbiTargeting.newBuilder(this.multiAbiTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.multiAbiTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearMultiAbiTargeting() {
                if (this.multiAbiTargetingBuilder_ == null) {
                    this.multiAbiTargeting_ = null;
                    this.onChanged();
                } else {
                    this.multiAbiTargeting_ = null;
                    this.multiAbiTargetingBuilder_ = null;
                }
                return this;
            }

            public MultiAbiTargeting.Builder getMultiAbiTargetingBuilder() {
                this.onChanged();
                return this.getMultiAbiTargetingFieldBuilder().getBuilder();
            }

            @Override
            public MultiAbiTargetingOrBuilder getMultiAbiTargetingOrBuilder() {
                if (this.multiAbiTargetingBuilder_ != null) {
                    return this.multiAbiTargetingBuilder_.getMessageOrBuilder();
                }
                return this.multiAbiTargeting_ == null ? MultiAbiTargeting.getDefaultInstance() : this.multiAbiTargeting_;
            }

            private SingleFieldBuilderV3<MultiAbiTargeting, MultiAbiTargeting.Builder, MultiAbiTargetingOrBuilder> getMultiAbiTargetingFieldBuilder() {
                if (this.multiAbiTargetingBuilder_ == null) {
                    this.multiAbiTargetingBuilder_ = new SingleFieldBuilderV3(this.getMultiAbiTargeting(), this.getParentForChildren(), this.isClean());
                    this.multiAbiTargeting_ = null;
                }
                return this.multiAbiTargetingBuilder_;
            }

            @Override
            public boolean hasTextureCompressionFormatTargeting() {
                return this.textureCompressionFormatTargetingBuilder_ != null || this.textureCompressionFormatTargeting_ != null;
            }

            @Override
            public TextureCompressionFormatTargeting getTextureCompressionFormatTargeting() {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    return this.textureCompressionFormatTargeting_ == null ? TextureCompressionFormatTargeting.getDefaultInstance() : this.textureCompressionFormatTargeting_;
                }
                return this.textureCompressionFormatTargetingBuilder_.getMessage();
            }

            public Builder setTextureCompressionFormatTargeting(TextureCompressionFormatTargeting value) {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.textureCompressionFormatTargeting_ = value;
                    this.onChanged();
                } else {
                    this.textureCompressionFormatTargetingBuilder_.setMessage(value);
                }
                return this;
            }

            public Builder setTextureCompressionFormatTargeting(TextureCompressionFormatTargeting.Builder builderForValue) {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    this.textureCompressionFormatTargeting_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.textureCompressionFormatTargetingBuilder_.setMessage(builderForValue.build());
                }
                return this;
            }

            public Builder mergeTextureCompressionFormatTargeting(TextureCompressionFormatTargeting value) {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    this.textureCompressionFormatTargeting_ = this.textureCompressionFormatTargeting_ != null ? TextureCompressionFormatTargeting.newBuilder(this.textureCompressionFormatTargeting_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.textureCompressionFormatTargetingBuilder_.mergeFrom(value);
                }
                return this;
            }

            public Builder clearTextureCompressionFormatTargeting() {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    this.textureCompressionFormatTargeting_ = null;
                    this.onChanged();
                } else {
                    this.textureCompressionFormatTargeting_ = null;
                    this.textureCompressionFormatTargetingBuilder_ = null;
                }
                return this;
            }

            public TextureCompressionFormatTargeting.Builder getTextureCompressionFormatTargetingBuilder() {
                this.onChanged();
                return this.getTextureCompressionFormatTargetingFieldBuilder().getBuilder();
            }

            @Override
            public TextureCompressionFormatTargetingOrBuilder getTextureCompressionFormatTargetingOrBuilder() {
                if (this.textureCompressionFormatTargetingBuilder_ != null) {
                    return this.textureCompressionFormatTargetingBuilder_.getMessageOrBuilder();
                }
                return this.textureCompressionFormatTargeting_ == null ? TextureCompressionFormatTargeting.getDefaultInstance() : this.textureCompressionFormatTargeting_;
            }

            private SingleFieldBuilderV3<TextureCompressionFormatTargeting, TextureCompressionFormatTargeting.Builder, TextureCompressionFormatTargetingOrBuilder> getTextureCompressionFormatTargetingFieldBuilder() {
                if (this.textureCompressionFormatTargetingBuilder_ == null) {
                    this.textureCompressionFormatTargetingBuilder_ = new SingleFieldBuilderV3(this.getTextureCompressionFormatTargeting(), this.getParentForChildren(), this.isClean());
                    this.textureCompressionFormatTargeting_ = null;
                }
                return this.textureCompressionFormatTargetingBuilder_;
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

    public static interface VariantTargetingOrBuilder
    extends MessageOrBuilder {
        public boolean hasSdkVersionTargeting();

        public SdkVersionTargeting getSdkVersionTargeting();

        public SdkVersionTargetingOrBuilder getSdkVersionTargetingOrBuilder();

        public boolean hasAbiTargeting();

        public AbiTargeting getAbiTargeting();

        public AbiTargetingOrBuilder getAbiTargetingOrBuilder();

        public boolean hasScreenDensityTargeting();

        public ScreenDensityTargeting getScreenDensityTargeting();

        public ScreenDensityTargetingOrBuilder getScreenDensityTargetingOrBuilder();

        public boolean hasMultiAbiTargeting();

        public MultiAbiTargeting getMultiAbiTargeting();

        public MultiAbiTargetingOrBuilder getMultiAbiTargetingOrBuilder();

        public boolean hasTextureCompressionFormatTargeting();

        public TextureCompressionFormatTargeting getTextureCompressionFormatTargeting();

        public TextureCompressionFormatTargetingOrBuilder getTextureCompressionFormatTargetingOrBuilder();
    }
}

