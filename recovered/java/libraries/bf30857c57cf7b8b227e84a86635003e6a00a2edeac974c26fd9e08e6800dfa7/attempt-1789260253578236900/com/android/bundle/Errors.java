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
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Errors {
    private static final Descriptors.Descriptor internal_static_android_bundle_BundleToolError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_BundleToolError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestMaxSdkInvalidError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestMaxSdkInvalidError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestMaxSdkLessThanMinInstantSdkError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestMaxSdkLessThanMinInstantSdkError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestMinSdkInvalidError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestMinSdkInvalidError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestMinSdkGreaterThanMaxSdkError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestMinSdkGreaterThanMaxSdkError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestMissingVersionCodeError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestMissingVersionCodeError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestInvalidVersionCodeError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestInvalidVersionCodeError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestBaseModuleExcludedFromFusingError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestBaseModuleExcludedFromFusingError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestModuleFusingConfigurationMissingError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestModuleFusingConfigurationMissingError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestFusingMissingIncludeAttributeError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestFusingMissingIncludeAttributeError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestDuplicateAttributeError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestDuplicateAttributeError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ManifestModulesDifferentVersionCodes_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ManifestModulesDifferentVersionCodes_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_FileTypeInvalidFileExtensionError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_FileTypeInvalidFileExtensionError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_FileTypeInvalidFileNameInDirectoryError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_FileTypeInvalidFileNameInDirectoryError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_FileTypeInvalidNativeLibraryPathError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_FileTypeInvalidNativeLibraryPathError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_FileTypeInvalidApexImagePathError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_FileTypeInvalidApexImagePathError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_FileTypeInvalidNativeArchitectureError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_FileTypeInvalidNativeArchitectureError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_FileTypeFilesInResourceDirectoryRootError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_FileTypeFilesInResourceDirectoryRootError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_FileTypeUnknownFileOrDirectoryFoundInModuleError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_FileTypeUnknownFileOrDirectoryFoundInModuleError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_FileTypeFileUsesReservedNameError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_FileTypeFileUsesReservedNameError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_FileTypeDirectoryInBundleError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_FileTypeDirectoryInBundleError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_MandatoryBundleFileMissingError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_MandatoryBundleFileMissingError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_MandatoryModuleFileMissingError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_MandatoryModuleFileMissingError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ResourceTableReferencesFilesOutsideResError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ResourceTableReferencesFilesOutsideResError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ResourceTableReferencesMissingFilesError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ResourceTableReferencesMissingFilesError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ResourceTableUnreferencedFilesError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ResourceTableUnreferencedFilesError_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_android_bundle_ResourceTableMissingError_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_android_bundle_ResourceTableMissingError_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private Errors() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        Errors.registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\ferrors.proto\u0012\u000eandroid.bundle\"\u00c8\u0014\n\u000fBundleToolError\u0012\u0019\n\u0011exception_message\u0018\u0001 \u0001(\t\u0012X\n\u001dmanifest_missing_version_code\u0018\u0002 \u0001(\u000b2/.android.bundle.ManifestMissingVersionCodeErrorH\u0000\u0012X\n\u001dmanifest_invalid_version_code\u0018\u0003 \u0001(\u000b2/.android.bundle.ManifestInvalidVersionCodeErrorH\u0000\u0012i\n$manifest_fusing_base_module_excluded\u0018\u0004 \u0001(\u000b29.android.bundle.ManifestBaseModuleExcludedFromFusingErrorH\u0000\u0012n\n%manifest_fusing_configuration_mi", "ssing\u0018\u0005 \u0001(\u000b2=.android.bundle.ManifestModuleFusingConfigurationMissingErrorH\u0000\u0012o\n)manifest_fusing_missing_include_attribute\u0018\u0015 \u0001(\u000b2:.android.bundle.ManifestFusingMissingIncludeAttributeErrorH\u0000\u0012N\n\u0018manifest_max_sdk_invalid\u0018\u0016 \u0001(\u000b2*.android.bundle.ManifestMaxSdkInvalidErrorH\u0000\u0012n\n*manifest_max_sdk_less_than_min_instant_sdk\u0018\u0017 \u0001(\u000b28.android.bundle.ManifestMaxSdkLessThanMinInstantSdkErrorH\u0000\u0012N\n\u0018manifest_min_sd", "k_invalid\u0018\u0013 \u0001(\u000b2*.android.bundle.ManifestMinSdkInvalidErrorH\u0000\u0012a\n!manifest_min_sdk_greater_than_max\u0018\u0014 \u0001(\u000b24.android.bundle.ManifestMinSdkGreaterThanMaxSdkErrorH\u0000\u0012W\n\u001cmanifest_duplicate_attribute\u0018\u0019 \u0001(\u000b2/.android.bundle.ManifestDuplicateAttributeErrorH\u0000\u0012h\n(manifest_modules_different_version_codes\u0018\u001b \u0001(\u000b24.android.bundle.ManifestModulesDifferentVersionCodesH\u0000\u0012]\n file_type_invalid_file_extension\u0018\u0006 \u0001(\u000b21.", "android.bundle.FileTypeInvalidFileExtensionErrorH\u0000\u0012^\n\u001bfile_type_invalid_file_name\u0018\u0007 \u0001(\u000b27.android.bundle.FileTypeInvalidFileNameInDirectoryErrorH\u0000\u0012f\n%file_type_invalid_native_library_path\u0018\b \u0001(\u000b25.android.bundle.FileTypeInvalidNativeLibraryPathErrorH\u0000\u0012g\n%file_type_invalid_native_architecture\u0018\t \u0001(\u000b26.android.bundle.FileTypeInvalidNativeArchitectureErrorH\u0000\u0012n\n)file_type_file_in_resource_directory_root", "\u0018\n \u0001(\u000b29.android.bundle.FileTypeFilesInResourceDirectoryRootErrorH\u0000\u0012y\n-file_type_unknown_file_or_directory_in_module\u0018\u000b \u0001(\u000b2@.android.bundle.FileTypeUnknownFileOrDirectoryFoundInModuleErrorH\u0000\u0012^\n!file_type_file_uses_reserved_name\u0018\f \u0001(\u000b21.android.bundle.FileTypeFileUsesReservedNameErrorH\u0000\u0012W\n\u001dfile_type_directory_in_bundle\u0018\u0012 \u0001(\u000b2..android.bundle.FileTypeDirectoryInBundleErrorH\u0000\u0012^\n!file_type_invalid_ape", "x_image_path\u0018\u001a \u0001(\u000b21.android.bundle.FileTypeInvalidApexImagePathErrorH\u0000\u0012X\n\u001dmandatory_bundle_file_missing\u0018\u000e \u0001(\u000b2/.android.bundle.MandatoryBundleFileMissingErrorH\u0000\u0012X\n\u001dmandatory_module_file_missing\u0018\r \u0001(\u000b2/.android.bundle.MandatoryModuleFileMissingErrorH\u0000\u0012r\n+resource_table_references_files_outside_res\u0018\u000f \u0001(\u000b2;.android.bundle.ResourceTableReferencesFilesOutsideResErrorH\u0000\u0012j\n&resouce_table_references_miss", "ing_files\u0018\u0010 \u0001(\u000b28.android.bundle.ResourceTableReferencesMissingFilesErrorH\u0000\u0012_\n resouce_table_unreferenced_files\u0018\u0011 \u0001(\u000b23.android.bundle.ResourceTableUnreferencedFilesErrorH\u0000\u0012K\n\u0016resource_table_missing\u0018\u0018 \u0001(\u000b2).android.bundle.ResourceTableMissingErrorH\u0000B\u000e\n\fcustom_error\"-\n\u001aManifestMaxSdkInvalidError\u0012\u000f\n\u0007max_sdk\u0018\u0001 \u0001(\t\";\n(ManifestMaxSdkLessThanMinInstantSdkError\u0012\u000f\n\u0007max_sdk\u0018\u0001 \u0001(\u0005\"-\n\u001aManifestMinSdkInvalidEr", "ror\u0012\u000f\n\u0007min_sdk\u0018\u0001 \u0001(\t\"H\n$ManifestMinSdkGreaterThanMaxSdkError\u0012\u000f\n\u0007min_sdk\u0018\u0001 \u0001(\u0005\u0012\u000f\n\u0007max_sdk\u0018\u0002 \u0001(\u0005\"!\n\u001fManifestMissingVersionCodeError\"!\n\u001fManifestInvalidVersionCodeError\"+\n)ManifestBaseModuleExcludedFromFusingError\"D\n-ManifestModuleFusingConfigurationMissingError\u0012\u0013\n\u000bmodule_name\u0018\u0001 \u0001(\t\"A\n*ManifestFusingMissingIncludeAttributeError\u0012\u0013\n\u000bmodule_name\u0018\u0001 \u0001(\t\"N\n\u001fManifestDuplicateAttributeError\u0012\u0016\n\u000eattribute_name\u0018", "\u0001 \u0001(\t\u0012\u0013\n\u000bmodule_name\u0018\u0002 \u0001(\t\"=\n$ManifestModulesDifferentVersionCodes\u0012\u0015\n\rversion_codes\u0018\u0001 \u0003(\u0005\"o\n!FileTypeInvalidFileExtensionError\u0012\u0018\n\u0010bundle_directory\u0018\u0001 \u0001(\t\u0012\u001a\n\u0012required_extension\u0018\u0002 \u0001(\t\u0012\u0014\n\finvalid_file\u0018\u0003 \u0001(\t\"t\n'FileTypeInvalidFileNameInDirectoryError\u0012\u0018\n\u0010bundle_directory\u0018\u0001 \u0001(\t\u0012\u0019\n\u0011allowed_file_name\u0018\u0002 \u0003(\t\u0012\u0014\n\finvalid_file\u0018\u0003 \u0001(\t\"W\n%FileTypeInvalidNativeLibraryPathError\u0012\u0018\n\u0010bundle_directory\u0018\u0001 \u0001(\t\u0012\u0014\n\finvalid_f", "ile\u0018\u0002 \u0001(\t\"S\n!FileTypeInvalidApexImagePathError\u0012\u0018\n\u0010bundle_directory\u0018\u0001 \u0001(\t\u0012\u0014\n\finvalid_file\u0018\u0002 \u0001(\t\"P\n&FileTypeInvalidNativeArchitectureError\u0012&\n\u001einvalid_architecture_directory\u0018\u0001 \u0001(\t\"]\n)FileTypeFilesInResourceDirectoryRootError\u0012\u001a\n\u0012resource_directory\u0018\u0001 \u0001(\t\u0012\u0014\n\finvalid_file\u0018\u0002 \u0001(\t\"H\n0FileTypeUnknownFileOrDirectoryFoundInModuleError\u0012\u0014\n\finvalid_file\u0018\u0001 \u0001(\t\"9\n!FileTypeFileUsesReservedNameError\u0012\u0014\n\finvalid_file\u0018\u0001", " \u0001(\t\";\n\u001eFileTypeDirectoryInBundleError\u0012\u0019\n\u0011invalid_directory\u0018\u0001 \u0001(\t\"7\n\u001fMandatoryBundleFileMissingError\u0012\u0014\n\fmissing_file\u0018\u0001 \u0001(\t\"L\n\u001fMandatoryModuleFileMissingError\u0012\u0013\n\u000bmodule_name\u0018\u0001 \u0001(\t\u0012\u0014\n\fmissing_file\u0018\u0002 \u0001(\t\"U\n+ResourceTableReferencesFilesOutsideResError\u0012\u0013\n\u000bmodule_name\u0018\u0001 \u0001(\t\u0012\u0011\n\tfile_path\u0018\u0002 \u0001(\t\"R\n(ResourceTableReferencesMissingFilesError\u0012\u0013\n\u000bmodule_name\u0018\u0001 \u0001(\t\u0012\u0011\n\tfile_path\u0018\u0002 \u0003(\t\"M\n#ResourceTableUnreferenced", "FilesError\u0012\u0013\n\u000bmodule_name\u0018\u0001 \u0001(\t\u0012\u0011\n\tfile_path\u0018\u0002 \u0003(\t\"0\n\u0019ResourceTableMissingError\u0012\u0013\n\u000bmodule_name\u0018\u0001 \u0001(\tB\u0014\n\u0012com.android.bundleb\u0006proto3"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            @Override
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0], assigner);
        internal_static_android_bundle_BundleToolError_descriptor = Errors.getDescriptor().getMessageTypes().get(0);
        internal_static_android_bundle_BundleToolError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_BundleToolError_descriptor, new String[]{"ExceptionMessage", "ManifestMissingVersionCode", "ManifestInvalidVersionCode", "ManifestFusingBaseModuleExcluded", "ManifestFusingConfigurationMissing", "ManifestFusingMissingIncludeAttribute", "ManifestMaxSdkInvalid", "ManifestMaxSdkLessThanMinInstantSdk", "ManifestMinSdkInvalid", "ManifestMinSdkGreaterThanMax", "ManifestDuplicateAttribute", "ManifestModulesDifferentVersionCodes", "FileTypeInvalidFileExtension", "FileTypeInvalidFileName", "FileTypeInvalidNativeLibraryPath", "FileTypeInvalidNativeArchitecture", "FileTypeFileInResourceDirectoryRoot", "FileTypeUnknownFileOrDirectoryInModule", "FileTypeFileUsesReservedName", "FileTypeDirectoryInBundle", "FileTypeInvalidApexImagePath", "MandatoryBundleFileMissing", "MandatoryModuleFileMissing", "ResourceTableReferencesFilesOutsideRes", "ResouceTableReferencesMissingFiles", "ResouceTableUnreferencedFiles", "ResourceTableMissing", "CustomError"});
        internal_static_android_bundle_ManifestMaxSdkInvalidError_descriptor = Errors.getDescriptor().getMessageTypes().get(1);
        internal_static_android_bundle_ManifestMaxSdkInvalidError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestMaxSdkInvalidError_descriptor, new String[]{"MaxSdk"});
        internal_static_android_bundle_ManifestMaxSdkLessThanMinInstantSdkError_descriptor = Errors.getDescriptor().getMessageTypes().get(2);
        internal_static_android_bundle_ManifestMaxSdkLessThanMinInstantSdkError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestMaxSdkLessThanMinInstantSdkError_descriptor, new String[]{"MaxSdk"});
        internal_static_android_bundle_ManifestMinSdkInvalidError_descriptor = Errors.getDescriptor().getMessageTypes().get(3);
        internal_static_android_bundle_ManifestMinSdkInvalidError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestMinSdkInvalidError_descriptor, new String[]{"MinSdk"});
        internal_static_android_bundle_ManifestMinSdkGreaterThanMaxSdkError_descriptor = Errors.getDescriptor().getMessageTypes().get(4);
        internal_static_android_bundle_ManifestMinSdkGreaterThanMaxSdkError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestMinSdkGreaterThanMaxSdkError_descriptor, new String[]{"MinSdk", "MaxSdk"});
        internal_static_android_bundle_ManifestMissingVersionCodeError_descriptor = Errors.getDescriptor().getMessageTypes().get(5);
        internal_static_android_bundle_ManifestMissingVersionCodeError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestMissingVersionCodeError_descriptor, new String[0]);
        internal_static_android_bundle_ManifestInvalidVersionCodeError_descriptor = Errors.getDescriptor().getMessageTypes().get(6);
        internal_static_android_bundle_ManifestInvalidVersionCodeError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestInvalidVersionCodeError_descriptor, new String[0]);
        internal_static_android_bundle_ManifestBaseModuleExcludedFromFusingError_descriptor = Errors.getDescriptor().getMessageTypes().get(7);
        internal_static_android_bundle_ManifestBaseModuleExcludedFromFusingError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestBaseModuleExcludedFromFusingError_descriptor, new String[0]);
        internal_static_android_bundle_ManifestModuleFusingConfigurationMissingError_descriptor = Errors.getDescriptor().getMessageTypes().get(8);
        internal_static_android_bundle_ManifestModuleFusingConfigurationMissingError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestModuleFusingConfigurationMissingError_descriptor, new String[]{"ModuleName"});
        internal_static_android_bundle_ManifestFusingMissingIncludeAttributeError_descriptor = Errors.getDescriptor().getMessageTypes().get(9);
        internal_static_android_bundle_ManifestFusingMissingIncludeAttributeError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestFusingMissingIncludeAttributeError_descriptor, new String[]{"ModuleName"});
        internal_static_android_bundle_ManifestDuplicateAttributeError_descriptor = Errors.getDescriptor().getMessageTypes().get(10);
        internal_static_android_bundle_ManifestDuplicateAttributeError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestDuplicateAttributeError_descriptor, new String[]{"AttributeName", "ModuleName"});
        internal_static_android_bundle_ManifestModulesDifferentVersionCodes_descriptor = Errors.getDescriptor().getMessageTypes().get(11);
        internal_static_android_bundle_ManifestModulesDifferentVersionCodes_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ManifestModulesDifferentVersionCodes_descriptor, new String[]{"VersionCodes"});
        internal_static_android_bundle_FileTypeInvalidFileExtensionError_descriptor = Errors.getDescriptor().getMessageTypes().get(12);
        internal_static_android_bundle_FileTypeInvalidFileExtensionError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_FileTypeInvalidFileExtensionError_descriptor, new String[]{"BundleDirectory", "RequiredExtension", "InvalidFile"});
        internal_static_android_bundle_FileTypeInvalidFileNameInDirectoryError_descriptor = Errors.getDescriptor().getMessageTypes().get(13);
        internal_static_android_bundle_FileTypeInvalidFileNameInDirectoryError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_FileTypeInvalidFileNameInDirectoryError_descriptor, new String[]{"BundleDirectory", "AllowedFileName", "InvalidFile"});
        internal_static_android_bundle_FileTypeInvalidNativeLibraryPathError_descriptor = Errors.getDescriptor().getMessageTypes().get(14);
        internal_static_android_bundle_FileTypeInvalidNativeLibraryPathError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_FileTypeInvalidNativeLibraryPathError_descriptor, new String[]{"BundleDirectory", "InvalidFile"});
        internal_static_android_bundle_FileTypeInvalidApexImagePathError_descriptor = Errors.getDescriptor().getMessageTypes().get(15);
        internal_static_android_bundle_FileTypeInvalidApexImagePathError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_FileTypeInvalidApexImagePathError_descriptor, new String[]{"BundleDirectory", "InvalidFile"});
        internal_static_android_bundle_FileTypeInvalidNativeArchitectureError_descriptor = Errors.getDescriptor().getMessageTypes().get(16);
        internal_static_android_bundle_FileTypeInvalidNativeArchitectureError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_FileTypeInvalidNativeArchitectureError_descriptor, new String[]{"InvalidArchitectureDirectory"});
        internal_static_android_bundle_FileTypeFilesInResourceDirectoryRootError_descriptor = Errors.getDescriptor().getMessageTypes().get(17);
        internal_static_android_bundle_FileTypeFilesInResourceDirectoryRootError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_FileTypeFilesInResourceDirectoryRootError_descriptor, new String[]{"ResourceDirectory", "InvalidFile"});
        internal_static_android_bundle_FileTypeUnknownFileOrDirectoryFoundInModuleError_descriptor = Errors.getDescriptor().getMessageTypes().get(18);
        internal_static_android_bundle_FileTypeUnknownFileOrDirectoryFoundInModuleError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_FileTypeUnknownFileOrDirectoryFoundInModuleError_descriptor, new String[]{"InvalidFile"});
        internal_static_android_bundle_FileTypeFileUsesReservedNameError_descriptor = Errors.getDescriptor().getMessageTypes().get(19);
        internal_static_android_bundle_FileTypeFileUsesReservedNameError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_FileTypeFileUsesReservedNameError_descriptor, new String[]{"InvalidFile"});
        internal_static_android_bundle_FileTypeDirectoryInBundleError_descriptor = Errors.getDescriptor().getMessageTypes().get(20);
        internal_static_android_bundle_FileTypeDirectoryInBundleError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_FileTypeDirectoryInBundleError_descriptor, new String[]{"InvalidDirectory"});
        internal_static_android_bundle_MandatoryBundleFileMissingError_descriptor = Errors.getDescriptor().getMessageTypes().get(21);
        internal_static_android_bundle_MandatoryBundleFileMissingError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_MandatoryBundleFileMissingError_descriptor, new String[]{"MissingFile"});
        internal_static_android_bundle_MandatoryModuleFileMissingError_descriptor = Errors.getDescriptor().getMessageTypes().get(22);
        internal_static_android_bundle_MandatoryModuleFileMissingError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_MandatoryModuleFileMissingError_descriptor, new String[]{"ModuleName", "MissingFile"});
        internal_static_android_bundle_ResourceTableReferencesFilesOutsideResError_descriptor = Errors.getDescriptor().getMessageTypes().get(23);
        internal_static_android_bundle_ResourceTableReferencesFilesOutsideResError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ResourceTableReferencesFilesOutsideResError_descriptor, new String[]{"ModuleName", "FilePath"});
        internal_static_android_bundle_ResourceTableReferencesMissingFilesError_descriptor = Errors.getDescriptor().getMessageTypes().get(24);
        internal_static_android_bundle_ResourceTableReferencesMissingFilesError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ResourceTableReferencesMissingFilesError_descriptor, new String[]{"ModuleName", "FilePath"});
        internal_static_android_bundle_ResourceTableUnreferencedFilesError_descriptor = Errors.getDescriptor().getMessageTypes().get(25);
        internal_static_android_bundle_ResourceTableUnreferencedFilesError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ResourceTableUnreferencedFilesError_descriptor, new String[]{"ModuleName", "FilePath"});
        internal_static_android_bundle_ResourceTableMissingError_descriptor = Errors.getDescriptor().getMessageTypes().get(26);
        internal_static_android_bundle_ResourceTableMissingError_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_android_bundle_ResourceTableMissingError_descriptor, new String[]{"ModuleName"});
    }

    public static final class ResourceTableMissingError
    extends GeneratedMessageV3
    implements ResourceTableMissingErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MODULE_NAME_FIELD_NUMBER = 1;
        private volatile Object moduleName_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ResourceTableMissingError DEFAULT_INSTANCE = new ResourceTableMissingError();
        private static final Parser<ResourceTableMissingError> PARSER = new AbstractParser<ResourceTableMissingError>(){

            @Override
            public ResourceTableMissingError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ResourceTableMissingError(input, extensionRegistry);
            }
        };

        private ResourceTableMissingError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ResourceTableMissingError() {
            this.moduleName_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ResourceTableMissingError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.moduleName_ = s3;
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
            return internal_static_android_bundle_ResourceTableMissingError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ResourceTableMissingError_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceTableMissingError.class, Builder.class);
        }

        @Override
        public String getModuleName() {
            Object ref = this.moduleName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.moduleName_ = s3;
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
            if (!this.getModuleNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.moduleName_);
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
            if (!this.getModuleNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.moduleName_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ResourceTableMissingError)) {
                return super.equals(obj);
            }
            ResourceTableMissingError other = (ResourceTableMissingError)obj;
            boolean result = true;
            result = result && this.getModuleName().equals(other.getModuleName());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ResourceTableMissingError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getModuleName().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ResourceTableMissingError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableMissingError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableMissingError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableMissingError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableMissingError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableMissingError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableMissingError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ResourceTableMissingError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ResourceTableMissingError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ResourceTableMissingError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ResourceTableMissingError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ResourceTableMissingError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ResourceTableMissingError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ResourceTableMissingError prototype) {
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

        public static ResourceTableMissingError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ResourceTableMissingError> parser() {
            return PARSER;
        }

        public Parser<ResourceTableMissingError> getParserForType() {
            return PARSER;
        }

        @Override
        public ResourceTableMissingError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ResourceTableMissingErrorOrBuilder {
            private Object moduleName_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ResourceTableMissingError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ResourceTableMissingError_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceTableMissingError.class, Builder.class);
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
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ResourceTableMissingError_descriptor;
            }

            @Override
            public ResourceTableMissingError getDefaultInstanceForType() {
                return ResourceTableMissingError.getDefaultInstance();
            }

            @Override
            public ResourceTableMissingError build() {
                ResourceTableMissingError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ResourceTableMissingError buildPartial() {
                ResourceTableMissingError result = new ResourceTableMissingError(this);
                result.moduleName_ = this.moduleName_;
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
                if (other instanceof ResourceTableMissingError) {
                    return this.mergeFrom((ResourceTableMissingError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ResourceTableMissingError other) {
                if (other == ResourceTableMissingError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getModuleName().isEmpty()) {
                    this.moduleName_ = other.moduleName_;
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
                ResourceTableMissingError parsedMessage = null;
                try {
                    parsedMessage = (ResourceTableMissingError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ResourceTableMissingError)e2.getUnfinishedMessage();
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
            public String getModuleName() {
                Object ref = this.moduleName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.moduleName_ = s3;
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
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearModuleName() {
                this.moduleName_ = ResourceTableMissingError.getDefaultInstance().getModuleName();
                this.onChanged();
                return this;
            }

            public Builder setModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ResourceTableMissingError.checkByteStringIsUtf8(value);
                this.moduleName_ = value;
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

    public static interface ResourceTableMissingErrorOrBuilder
    extends MessageOrBuilder {
        public String getModuleName();

        public ByteString getModuleNameBytes();
    }

    public static final class ResourceTableUnreferencedFilesError
    extends GeneratedMessageV3
    implements ResourceTableUnreferencedFilesErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int MODULE_NAME_FIELD_NUMBER = 1;
        private volatile Object moduleName_;
        public static final int FILE_PATH_FIELD_NUMBER = 2;
        private LazyStringList filePath_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ResourceTableUnreferencedFilesError DEFAULT_INSTANCE = new ResourceTableUnreferencedFilesError();
        private static final Parser<ResourceTableUnreferencedFilesError> PARSER = new AbstractParser<ResourceTableUnreferencedFilesError>(){

            @Override
            public ResourceTableUnreferencedFilesError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ResourceTableUnreferencedFilesError(input, extensionRegistry);
            }
        };

        private ResourceTableUnreferencedFilesError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ResourceTableUnreferencedFilesError() {
            this.moduleName_ = "";
            this.filePath_ = LazyStringArrayList.EMPTY;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ResourceTableUnreferencedFilesError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.moduleName_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    s3 = input.readStringRequireUtf8();
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.filePath_ = new LazyStringArrayList();
                        mutable_bitField0_ |= 2;
                    }
                    this.filePath_.add(s3);
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
                    this.filePath_ = this.filePath_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_ResourceTableUnreferencedFilesError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ResourceTableUnreferencedFilesError_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceTableUnreferencedFilesError.class, Builder.class);
        }

        @Override
        public String getModuleName() {
            Object ref = this.moduleName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.moduleName_ = s3;
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

        public ProtocolStringList getFilePathList() {
            return this.filePath_;
        }

        @Override
        public int getFilePathCount() {
            return this.filePath_.size();
        }

        @Override
        public String getFilePath(int index) {
            return (String)this.filePath_.get(index);
        }

        @Override
        public ByteString getFilePathBytes(int index) {
            return this.filePath_.getByteString(index);
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
            if (!this.getModuleNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.moduleName_);
            }
            for (int i2 = 0; i2 < this.filePath_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 2, this.filePath_.getRaw(i2));
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
            if (!this.getModuleNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.moduleName_);
            }
            int dataSize = 0;
            for (int i2 = 0; i2 < this.filePath_.size(); ++i2) {
                dataSize += ResourceTableUnreferencedFilesError.computeStringSizeNoTag(this.filePath_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getFilePathList().size();
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ResourceTableUnreferencedFilesError)) {
                return super.equals(obj);
            }
            ResourceTableUnreferencedFilesError other = (ResourceTableUnreferencedFilesError)obj;
            boolean result = true;
            result = result && this.getModuleName().equals(other.getModuleName());
            result = result && this.getFilePathList().equals(other.getFilePathList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ResourceTableUnreferencedFilesError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getModuleName().hashCode();
            if (this.getFilePathCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getFilePathList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ResourceTableUnreferencedFilesError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableUnreferencedFilesError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableUnreferencedFilesError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableUnreferencedFilesError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableUnreferencedFilesError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableUnreferencedFilesError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableUnreferencedFilesError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ResourceTableUnreferencedFilesError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ResourceTableUnreferencedFilesError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ResourceTableUnreferencedFilesError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ResourceTableUnreferencedFilesError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ResourceTableUnreferencedFilesError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ResourceTableUnreferencedFilesError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ResourceTableUnreferencedFilesError prototype) {
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

        public static ResourceTableUnreferencedFilesError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ResourceTableUnreferencedFilesError> parser() {
            return PARSER;
        }

        public Parser<ResourceTableUnreferencedFilesError> getParserForType() {
            return PARSER;
        }

        @Override
        public ResourceTableUnreferencedFilesError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ResourceTableUnreferencedFilesErrorOrBuilder {
            private int bitField0_;
            private Object moduleName_ = "";
            private LazyStringList filePath_ = LazyStringArrayList.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ResourceTableUnreferencedFilesError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ResourceTableUnreferencedFilesError_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceTableUnreferencedFilesError.class, Builder.class);
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
                this.filePath_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ResourceTableUnreferencedFilesError_descriptor;
            }

            @Override
            public ResourceTableUnreferencedFilesError getDefaultInstanceForType() {
                return ResourceTableUnreferencedFilesError.getDefaultInstance();
            }

            @Override
            public ResourceTableUnreferencedFilesError build() {
                ResourceTableUnreferencedFilesError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ResourceTableUnreferencedFilesError buildPartial() {
                ResourceTableUnreferencedFilesError result = new ResourceTableUnreferencedFilesError(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                result.moduleName_ = this.moduleName_;
                if ((this.bitField0_ & 2) == 2) {
                    this.filePath_ = this.filePath_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.filePath_ = this.filePath_;
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
                if (other instanceof ResourceTableUnreferencedFilesError) {
                    return this.mergeFrom((ResourceTableUnreferencedFilesError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ResourceTableUnreferencedFilesError other) {
                if (other == ResourceTableUnreferencedFilesError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getModuleName().isEmpty()) {
                    this.moduleName_ = other.moduleName_;
                    this.onChanged();
                }
                if (!other.filePath_.isEmpty()) {
                    if (this.filePath_.isEmpty()) {
                        this.filePath_ = other.filePath_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureFilePathIsMutable();
                        this.filePath_.addAll(other.filePath_);
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
                ResourceTableUnreferencedFilesError parsedMessage = null;
                try {
                    parsedMessage = (ResourceTableUnreferencedFilesError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ResourceTableUnreferencedFilesError)e2.getUnfinishedMessage();
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
            public String getModuleName() {
                Object ref = this.moduleName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.moduleName_ = s3;
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
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearModuleName() {
                this.moduleName_ = ResourceTableUnreferencedFilesError.getDefaultInstance().getModuleName();
                this.onChanged();
                return this;
            }

            public Builder setModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ResourceTableUnreferencedFilesError.checkByteStringIsUtf8(value);
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            private void ensureFilePathIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.filePath_ = new LazyStringArrayList(this.filePath_);
                    this.bitField0_ |= 2;
                }
            }

            public ProtocolStringList getFilePathList() {
                return this.filePath_.getUnmodifiableView();
            }

            @Override
            public int getFilePathCount() {
                return this.filePath_.size();
            }

            @Override
            public String getFilePath(int index) {
                return (String)this.filePath_.get(index);
            }

            @Override
            public ByteString getFilePathBytes(int index) {
                return this.filePath_.getByteString(index);
            }

            public Builder setFilePath(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFilePathIsMutable();
                this.filePath_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addFilePath(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFilePathIsMutable();
                this.filePath_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllFilePath(Iterable<String> values2) {
                this.ensureFilePathIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.filePath_);
                this.onChanged();
                return this;
            }

            public Builder clearFilePath() {
                this.filePath_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder addFilePathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ResourceTableUnreferencedFilesError.checkByteStringIsUtf8(value);
                this.ensureFilePathIsMutable();
                this.filePath_.add(value);
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

    public static interface ResourceTableUnreferencedFilesErrorOrBuilder
    extends MessageOrBuilder {
        public String getModuleName();

        public ByteString getModuleNameBytes();

        public List<String> getFilePathList();

        public int getFilePathCount();

        public String getFilePath(int var1);

        public ByteString getFilePathBytes(int var1);
    }

    public static final class ResourceTableReferencesMissingFilesError
    extends GeneratedMessageV3
    implements ResourceTableReferencesMissingFilesErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int MODULE_NAME_FIELD_NUMBER = 1;
        private volatile Object moduleName_;
        public static final int FILE_PATH_FIELD_NUMBER = 2;
        private LazyStringList filePath_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ResourceTableReferencesMissingFilesError DEFAULT_INSTANCE = new ResourceTableReferencesMissingFilesError();
        private static final Parser<ResourceTableReferencesMissingFilesError> PARSER = new AbstractParser<ResourceTableReferencesMissingFilesError>(){

            @Override
            public ResourceTableReferencesMissingFilesError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ResourceTableReferencesMissingFilesError(input, extensionRegistry);
            }
        };

        private ResourceTableReferencesMissingFilesError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ResourceTableReferencesMissingFilesError() {
            this.moduleName_ = "";
            this.filePath_ = LazyStringArrayList.EMPTY;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ResourceTableReferencesMissingFilesError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.moduleName_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    s3 = input.readStringRequireUtf8();
                    if ((mutable_bitField0_ & 2) != 2) {
                        this.filePath_ = new LazyStringArrayList();
                        mutable_bitField0_ |= 2;
                    }
                    this.filePath_.add(s3);
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
                    this.filePath_ = this.filePath_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_ResourceTableReferencesMissingFilesError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ResourceTableReferencesMissingFilesError_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceTableReferencesMissingFilesError.class, Builder.class);
        }

        @Override
        public String getModuleName() {
            Object ref = this.moduleName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.moduleName_ = s3;
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

        public ProtocolStringList getFilePathList() {
            return this.filePath_;
        }

        @Override
        public int getFilePathCount() {
            return this.filePath_.size();
        }

        @Override
        public String getFilePath(int index) {
            return (String)this.filePath_.get(index);
        }

        @Override
        public ByteString getFilePathBytes(int index) {
            return this.filePath_.getByteString(index);
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
            if (!this.getModuleNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.moduleName_);
            }
            for (int i2 = 0; i2 < this.filePath_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 2, this.filePath_.getRaw(i2));
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
            if (!this.getModuleNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.moduleName_);
            }
            int dataSize = 0;
            for (int i2 = 0; i2 < this.filePath_.size(); ++i2) {
                dataSize += ResourceTableReferencesMissingFilesError.computeStringSizeNoTag(this.filePath_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getFilePathList().size();
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ResourceTableReferencesMissingFilesError)) {
                return super.equals(obj);
            }
            ResourceTableReferencesMissingFilesError other = (ResourceTableReferencesMissingFilesError)obj;
            boolean result = true;
            result = result && this.getModuleName().equals(other.getModuleName());
            result = result && this.getFilePathList().equals(other.getFilePathList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ResourceTableReferencesMissingFilesError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getModuleName().hashCode();
            if (this.getFilePathCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getFilePathList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ResourceTableReferencesMissingFilesError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableReferencesMissingFilesError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableReferencesMissingFilesError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableReferencesMissingFilesError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableReferencesMissingFilesError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableReferencesMissingFilesError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableReferencesMissingFilesError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ResourceTableReferencesMissingFilesError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ResourceTableReferencesMissingFilesError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ResourceTableReferencesMissingFilesError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ResourceTableReferencesMissingFilesError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ResourceTableReferencesMissingFilesError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ResourceTableReferencesMissingFilesError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ResourceTableReferencesMissingFilesError prototype) {
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

        public static ResourceTableReferencesMissingFilesError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ResourceTableReferencesMissingFilesError> parser() {
            return PARSER;
        }

        public Parser<ResourceTableReferencesMissingFilesError> getParserForType() {
            return PARSER;
        }

        @Override
        public ResourceTableReferencesMissingFilesError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ResourceTableReferencesMissingFilesErrorOrBuilder {
            private int bitField0_;
            private Object moduleName_ = "";
            private LazyStringList filePath_ = LazyStringArrayList.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ResourceTableReferencesMissingFilesError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ResourceTableReferencesMissingFilesError_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceTableReferencesMissingFilesError.class, Builder.class);
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
                this.filePath_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ResourceTableReferencesMissingFilesError_descriptor;
            }

            @Override
            public ResourceTableReferencesMissingFilesError getDefaultInstanceForType() {
                return ResourceTableReferencesMissingFilesError.getDefaultInstance();
            }

            @Override
            public ResourceTableReferencesMissingFilesError build() {
                ResourceTableReferencesMissingFilesError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ResourceTableReferencesMissingFilesError buildPartial() {
                ResourceTableReferencesMissingFilesError result = new ResourceTableReferencesMissingFilesError(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                result.moduleName_ = this.moduleName_;
                if ((this.bitField0_ & 2) == 2) {
                    this.filePath_ = this.filePath_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.filePath_ = this.filePath_;
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
                if (other instanceof ResourceTableReferencesMissingFilesError) {
                    return this.mergeFrom((ResourceTableReferencesMissingFilesError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ResourceTableReferencesMissingFilesError other) {
                if (other == ResourceTableReferencesMissingFilesError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getModuleName().isEmpty()) {
                    this.moduleName_ = other.moduleName_;
                    this.onChanged();
                }
                if (!other.filePath_.isEmpty()) {
                    if (this.filePath_.isEmpty()) {
                        this.filePath_ = other.filePath_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureFilePathIsMutable();
                        this.filePath_.addAll(other.filePath_);
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
                ResourceTableReferencesMissingFilesError parsedMessage = null;
                try {
                    parsedMessage = (ResourceTableReferencesMissingFilesError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ResourceTableReferencesMissingFilesError)e2.getUnfinishedMessage();
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
            public String getModuleName() {
                Object ref = this.moduleName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.moduleName_ = s3;
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
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearModuleName() {
                this.moduleName_ = ResourceTableReferencesMissingFilesError.getDefaultInstance().getModuleName();
                this.onChanged();
                return this;
            }

            public Builder setModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ResourceTableReferencesMissingFilesError.checkByteStringIsUtf8(value);
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            private void ensureFilePathIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.filePath_ = new LazyStringArrayList(this.filePath_);
                    this.bitField0_ |= 2;
                }
            }

            public ProtocolStringList getFilePathList() {
                return this.filePath_.getUnmodifiableView();
            }

            @Override
            public int getFilePathCount() {
                return this.filePath_.size();
            }

            @Override
            public String getFilePath(int index) {
                return (String)this.filePath_.get(index);
            }

            @Override
            public ByteString getFilePathBytes(int index) {
                return this.filePath_.getByteString(index);
            }

            public Builder setFilePath(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFilePathIsMutable();
                this.filePath_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addFilePath(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFilePathIsMutable();
                this.filePath_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllFilePath(Iterable<String> values2) {
                this.ensureFilePathIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.filePath_);
                this.onChanged();
                return this;
            }

            public Builder clearFilePath() {
                this.filePath_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder addFilePathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ResourceTableReferencesMissingFilesError.checkByteStringIsUtf8(value);
                this.ensureFilePathIsMutable();
                this.filePath_.add(value);
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

    public static interface ResourceTableReferencesMissingFilesErrorOrBuilder
    extends MessageOrBuilder {
        public String getModuleName();

        public ByteString getModuleNameBytes();

        public List<String> getFilePathList();

        public int getFilePathCount();

        public String getFilePath(int var1);

        public ByteString getFilePathBytes(int var1);
    }

    public static final class ResourceTableReferencesFilesOutsideResError
    extends GeneratedMessageV3
    implements ResourceTableReferencesFilesOutsideResErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MODULE_NAME_FIELD_NUMBER = 1;
        private volatile Object moduleName_;
        public static final int FILE_PATH_FIELD_NUMBER = 2;
        private volatile Object filePath_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ResourceTableReferencesFilesOutsideResError DEFAULT_INSTANCE = new ResourceTableReferencesFilesOutsideResError();
        private static final Parser<ResourceTableReferencesFilesOutsideResError> PARSER = new AbstractParser<ResourceTableReferencesFilesOutsideResError>(){

            @Override
            public ResourceTableReferencesFilesOutsideResError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ResourceTableReferencesFilesOutsideResError(input, extensionRegistry);
            }
        };

        private ResourceTableReferencesFilesOutsideResError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ResourceTableReferencesFilesOutsideResError() {
            this.moduleName_ = "";
            this.filePath_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ResourceTableReferencesFilesOutsideResError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.moduleName_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    s3 = input.readStringRequireUtf8();
                    this.filePath_ = s3;
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
            return internal_static_android_bundle_ResourceTableReferencesFilesOutsideResError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ResourceTableReferencesFilesOutsideResError_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceTableReferencesFilesOutsideResError.class, Builder.class);
        }

        @Override
        public String getModuleName() {
            Object ref = this.moduleName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.moduleName_ = s3;
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
        public String getFilePath() {
            Object ref = this.filePath_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.filePath_ = s3;
            return s3;
        }

        @Override
        public ByteString getFilePathBytes() {
            Object ref = this.filePath_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.filePath_ = b2;
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
            if (!this.getModuleNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.moduleName_);
            }
            if (!this.getFilePathBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.filePath_);
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
            if (!this.getModuleNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.moduleName_);
            }
            if (!this.getFilePathBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.filePath_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ResourceTableReferencesFilesOutsideResError)) {
                return super.equals(obj);
            }
            ResourceTableReferencesFilesOutsideResError other = (ResourceTableReferencesFilesOutsideResError)obj;
            boolean result = true;
            result = result && this.getModuleName().equals(other.getModuleName());
            result = result && this.getFilePath().equals(other.getFilePath());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ResourceTableReferencesFilesOutsideResError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getModuleName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getFilePath().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ResourceTableReferencesFilesOutsideResError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableReferencesFilesOutsideResError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableReferencesFilesOutsideResError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableReferencesFilesOutsideResError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableReferencesFilesOutsideResError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ResourceTableReferencesFilesOutsideResError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ResourceTableReferencesFilesOutsideResError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ResourceTableReferencesFilesOutsideResError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ResourceTableReferencesFilesOutsideResError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ResourceTableReferencesFilesOutsideResError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ResourceTableReferencesFilesOutsideResError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ResourceTableReferencesFilesOutsideResError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ResourceTableReferencesFilesOutsideResError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ResourceTableReferencesFilesOutsideResError prototype) {
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

        public static ResourceTableReferencesFilesOutsideResError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ResourceTableReferencesFilesOutsideResError> parser() {
            return PARSER;
        }

        public Parser<ResourceTableReferencesFilesOutsideResError> getParserForType() {
            return PARSER;
        }

        @Override
        public ResourceTableReferencesFilesOutsideResError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ResourceTableReferencesFilesOutsideResErrorOrBuilder {
            private Object moduleName_ = "";
            private Object filePath_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ResourceTableReferencesFilesOutsideResError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ResourceTableReferencesFilesOutsideResError_fieldAccessorTable.ensureFieldAccessorsInitialized(ResourceTableReferencesFilesOutsideResError.class, Builder.class);
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
                this.filePath_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ResourceTableReferencesFilesOutsideResError_descriptor;
            }

            @Override
            public ResourceTableReferencesFilesOutsideResError getDefaultInstanceForType() {
                return ResourceTableReferencesFilesOutsideResError.getDefaultInstance();
            }

            @Override
            public ResourceTableReferencesFilesOutsideResError build() {
                ResourceTableReferencesFilesOutsideResError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ResourceTableReferencesFilesOutsideResError buildPartial() {
                ResourceTableReferencesFilesOutsideResError result = new ResourceTableReferencesFilesOutsideResError(this);
                result.moduleName_ = this.moduleName_;
                result.filePath_ = this.filePath_;
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
                if (other instanceof ResourceTableReferencesFilesOutsideResError) {
                    return this.mergeFrom((ResourceTableReferencesFilesOutsideResError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ResourceTableReferencesFilesOutsideResError other) {
                if (other == ResourceTableReferencesFilesOutsideResError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getModuleName().isEmpty()) {
                    this.moduleName_ = other.moduleName_;
                    this.onChanged();
                }
                if (!other.getFilePath().isEmpty()) {
                    this.filePath_ = other.filePath_;
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
                ResourceTableReferencesFilesOutsideResError parsedMessage = null;
                try {
                    parsedMessage = (ResourceTableReferencesFilesOutsideResError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ResourceTableReferencesFilesOutsideResError)e2.getUnfinishedMessage();
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
            public String getModuleName() {
                Object ref = this.moduleName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.moduleName_ = s3;
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
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearModuleName() {
                this.moduleName_ = ResourceTableReferencesFilesOutsideResError.getDefaultInstance().getModuleName();
                this.onChanged();
                return this;
            }

            public Builder setModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ResourceTableReferencesFilesOutsideResError.checkByteStringIsUtf8(value);
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public String getFilePath() {
                Object ref = this.filePath_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.filePath_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getFilePathBytes() {
                Object ref = this.filePath_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.filePath_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setFilePath(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.filePath_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearFilePath() {
                this.filePath_ = ResourceTableReferencesFilesOutsideResError.getDefaultInstance().getFilePath();
                this.onChanged();
                return this;
            }

            public Builder setFilePathBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ResourceTableReferencesFilesOutsideResError.checkByteStringIsUtf8(value);
                this.filePath_ = value;
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

    public static interface ResourceTableReferencesFilesOutsideResErrorOrBuilder
    extends MessageOrBuilder {
        public String getModuleName();

        public ByteString getModuleNameBytes();

        public String getFilePath();

        public ByteString getFilePathBytes();
    }

    public static final class MandatoryModuleFileMissingError
    extends GeneratedMessageV3
    implements MandatoryModuleFileMissingErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MODULE_NAME_FIELD_NUMBER = 1;
        private volatile Object moduleName_;
        public static final int MISSING_FILE_FIELD_NUMBER = 2;
        private volatile Object missingFile_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final MandatoryModuleFileMissingError DEFAULT_INSTANCE = new MandatoryModuleFileMissingError();
        private static final Parser<MandatoryModuleFileMissingError> PARSER = new AbstractParser<MandatoryModuleFileMissingError>(){

            @Override
            public MandatoryModuleFileMissingError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MandatoryModuleFileMissingError(input, extensionRegistry);
            }
        };

        private MandatoryModuleFileMissingError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private MandatoryModuleFileMissingError() {
            this.moduleName_ = "";
            this.missingFile_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MandatoryModuleFileMissingError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.moduleName_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    s3 = input.readStringRequireUtf8();
                    this.missingFile_ = s3;
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
            return internal_static_android_bundle_MandatoryModuleFileMissingError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_MandatoryModuleFileMissingError_fieldAccessorTable.ensureFieldAccessorsInitialized(MandatoryModuleFileMissingError.class, Builder.class);
        }

        @Override
        public String getModuleName() {
            Object ref = this.moduleName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.moduleName_ = s3;
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
        public String getMissingFile() {
            Object ref = this.missingFile_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.missingFile_ = s3;
            return s3;
        }

        @Override
        public ByteString getMissingFileBytes() {
            Object ref = this.missingFile_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.missingFile_ = b2;
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
            if (!this.getModuleNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.moduleName_);
            }
            if (!this.getMissingFileBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.missingFile_);
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
            if (!this.getModuleNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.moduleName_);
            }
            if (!this.getMissingFileBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.missingFile_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MandatoryModuleFileMissingError)) {
                return super.equals(obj);
            }
            MandatoryModuleFileMissingError other = (MandatoryModuleFileMissingError)obj;
            boolean result = true;
            result = result && this.getModuleName().equals(other.getModuleName());
            result = result && this.getMissingFile().equals(other.getMissingFile());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + MandatoryModuleFileMissingError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getModuleName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getMissingFile().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static MandatoryModuleFileMissingError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MandatoryModuleFileMissingError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MandatoryModuleFileMissingError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MandatoryModuleFileMissingError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MandatoryModuleFileMissingError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MandatoryModuleFileMissingError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MandatoryModuleFileMissingError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MandatoryModuleFileMissingError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MandatoryModuleFileMissingError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MandatoryModuleFileMissingError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MandatoryModuleFileMissingError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MandatoryModuleFileMissingError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return MandatoryModuleFileMissingError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MandatoryModuleFileMissingError prototype) {
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

        public static MandatoryModuleFileMissingError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MandatoryModuleFileMissingError> parser() {
            return PARSER;
        }

        public Parser<MandatoryModuleFileMissingError> getParserForType() {
            return PARSER;
        }

        @Override
        public MandatoryModuleFileMissingError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements MandatoryModuleFileMissingErrorOrBuilder {
            private Object moduleName_ = "";
            private Object missingFile_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_MandatoryModuleFileMissingError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_MandatoryModuleFileMissingError_fieldAccessorTable.ensureFieldAccessorsInitialized(MandatoryModuleFileMissingError.class, Builder.class);
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
                this.missingFile_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_MandatoryModuleFileMissingError_descriptor;
            }

            @Override
            public MandatoryModuleFileMissingError getDefaultInstanceForType() {
                return MandatoryModuleFileMissingError.getDefaultInstance();
            }

            @Override
            public MandatoryModuleFileMissingError build() {
                MandatoryModuleFileMissingError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public MandatoryModuleFileMissingError buildPartial() {
                MandatoryModuleFileMissingError result = new MandatoryModuleFileMissingError(this);
                result.moduleName_ = this.moduleName_;
                result.missingFile_ = this.missingFile_;
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
                if (other instanceof MandatoryModuleFileMissingError) {
                    return this.mergeFrom((MandatoryModuleFileMissingError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MandatoryModuleFileMissingError other) {
                if (other == MandatoryModuleFileMissingError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getModuleName().isEmpty()) {
                    this.moduleName_ = other.moduleName_;
                    this.onChanged();
                }
                if (!other.getMissingFile().isEmpty()) {
                    this.missingFile_ = other.missingFile_;
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
                MandatoryModuleFileMissingError parsedMessage = null;
                try {
                    parsedMessage = (MandatoryModuleFileMissingError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (MandatoryModuleFileMissingError)e2.getUnfinishedMessage();
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
            public String getModuleName() {
                Object ref = this.moduleName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.moduleName_ = s3;
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
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearModuleName() {
                this.moduleName_ = MandatoryModuleFileMissingError.getDefaultInstance().getModuleName();
                this.onChanged();
                return this;
            }

            public Builder setModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                MandatoryModuleFileMissingError.checkByteStringIsUtf8(value);
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public String getMissingFile() {
                Object ref = this.missingFile_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.missingFile_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getMissingFileBytes() {
                Object ref = this.missingFile_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.missingFile_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setMissingFile(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.missingFile_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMissingFile() {
                this.missingFile_ = MandatoryModuleFileMissingError.getDefaultInstance().getMissingFile();
                this.onChanged();
                return this;
            }

            public Builder setMissingFileBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                MandatoryModuleFileMissingError.checkByteStringIsUtf8(value);
                this.missingFile_ = value;
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

    public static interface MandatoryModuleFileMissingErrorOrBuilder
    extends MessageOrBuilder {
        public String getModuleName();

        public ByteString getModuleNameBytes();

        public String getMissingFile();

        public ByteString getMissingFileBytes();
    }

    public static final class MandatoryBundleFileMissingError
    extends GeneratedMessageV3
    implements MandatoryBundleFileMissingErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MISSING_FILE_FIELD_NUMBER = 1;
        private volatile Object missingFile_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final MandatoryBundleFileMissingError DEFAULT_INSTANCE = new MandatoryBundleFileMissingError();
        private static final Parser<MandatoryBundleFileMissingError> PARSER = new AbstractParser<MandatoryBundleFileMissingError>(){

            @Override
            public MandatoryBundleFileMissingError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MandatoryBundleFileMissingError(input, extensionRegistry);
            }
        };

        private MandatoryBundleFileMissingError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private MandatoryBundleFileMissingError() {
            this.missingFile_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MandatoryBundleFileMissingError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.missingFile_ = s3;
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
            return internal_static_android_bundle_MandatoryBundleFileMissingError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_MandatoryBundleFileMissingError_fieldAccessorTable.ensureFieldAccessorsInitialized(MandatoryBundleFileMissingError.class, Builder.class);
        }

        @Override
        public String getMissingFile() {
            Object ref = this.missingFile_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.missingFile_ = s3;
            return s3;
        }

        @Override
        public ByteString getMissingFileBytes() {
            Object ref = this.missingFile_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.missingFile_ = b2;
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
            if (!this.getMissingFileBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.missingFile_);
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
            if (!this.getMissingFileBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.missingFile_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MandatoryBundleFileMissingError)) {
                return super.equals(obj);
            }
            MandatoryBundleFileMissingError other = (MandatoryBundleFileMissingError)obj;
            boolean result = true;
            result = result && this.getMissingFile().equals(other.getMissingFile());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + MandatoryBundleFileMissingError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMissingFile().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static MandatoryBundleFileMissingError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MandatoryBundleFileMissingError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MandatoryBundleFileMissingError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MandatoryBundleFileMissingError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MandatoryBundleFileMissingError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MandatoryBundleFileMissingError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MandatoryBundleFileMissingError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MandatoryBundleFileMissingError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MandatoryBundleFileMissingError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static MandatoryBundleFileMissingError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MandatoryBundleFileMissingError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static MandatoryBundleFileMissingError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return MandatoryBundleFileMissingError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MandatoryBundleFileMissingError prototype) {
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

        public static MandatoryBundleFileMissingError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MandatoryBundleFileMissingError> parser() {
            return PARSER;
        }

        public Parser<MandatoryBundleFileMissingError> getParserForType() {
            return PARSER;
        }

        @Override
        public MandatoryBundleFileMissingError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements MandatoryBundleFileMissingErrorOrBuilder {
            private Object missingFile_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_MandatoryBundleFileMissingError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_MandatoryBundleFileMissingError_fieldAccessorTable.ensureFieldAccessorsInitialized(MandatoryBundleFileMissingError.class, Builder.class);
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
                this.missingFile_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_MandatoryBundleFileMissingError_descriptor;
            }

            @Override
            public MandatoryBundleFileMissingError getDefaultInstanceForType() {
                return MandatoryBundleFileMissingError.getDefaultInstance();
            }

            @Override
            public MandatoryBundleFileMissingError build() {
                MandatoryBundleFileMissingError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public MandatoryBundleFileMissingError buildPartial() {
                MandatoryBundleFileMissingError result = new MandatoryBundleFileMissingError(this);
                result.missingFile_ = this.missingFile_;
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
                if (other instanceof MandatoryBundleFileMissingError) {
                    return this.mergeFrom((MandatoryBundleFileMissingError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MandatoryBundleFileMissingError other) {
                if (other == MandatoryBundleFileMissingError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getMissingFile().isEmpty()) {
                    this.missingFile_ = other.missingFile_;
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
                MandatoryBundleFileMissingError parsedMessage = null;
                try {
                    parsedMessage = (MandatoryBundleFileMissingError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (MandatoryBundleFileMissingError)e2.getUnfinishedMessage();
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
            public String getMissingFile() {
                Object ref = this.missingFile_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.missingFile_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getMissingFileBytes() {
                Object ref = this.missingFile_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.missingFile_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setMissingFile(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.missingFile_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMissingFile() {
                this.missingFile_ = MandatoryBundleFileMissingError.getDefaultInstance().getMissingFile();
                this.onChanged();
                return this;
            }

            public Builder setMissingFileBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                MandatoryBundleFileMissingError.checkByteStringIsUtf8(value);
                this.missingFile_ = value;
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

    public static interface MandatoryBundleFileMissingErrorOrBuilder
    extends MessageOrBuilder {
        public String getMissingFile();

        public ByteString getMissingFileBytes();
    }

    public static final class FileTypeDirectoryInBundleError
    extends GeneratedMessageV3
    implements FileTypeDirectoryInBundleErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int INVALID_DIRECTORY_FIELD_NUMBER = 1;
        private volatile Object invalidDirectory_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final FileTypeDirectoryInBundleError DEFAULT_INSTANCE = new FileTypeDirectoryInBundleError();
        private static final Parser<FileTypeDirectoryInBundleError> PARSER = new AbstractParser<FileTypeDirectoryInBundleError>(){

            @Override
            public FileTypeDirectoryInBundleError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileTypeDirectoryInBundleError(input, extensionRegistry);
            }
        };

        private FileTypeDirectoryInBundleError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private FileTypeDirectoryInBundleError() {
            this.invalidDirectory_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileTypeDirectoryInBundleError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.invalidDirectory_ = s3;
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
            return internal_static_android_bundle_FileTypeDirectoryInBundleError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_FileTypeDirectoryInBundleError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeDirectoryInBundleError.class, Builder.class);
        }

        @Override
        public String getInvalidDirectory() {
            Object ref = this.invalidDirectory_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.invalidDirectory_ = s3;
            return s3;
        }

        @Override
        public ByteString getInvalidDirectoryBytes() {
            Object ref = this.invalidDirectory_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.invalidDirectory_ = b2;
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
            if (!this.getInvalidDirectoryBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.invalidDirectory_);
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
            if (!this.getInvalidDirectoryBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.invalidDirectory_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FileTypeDirectoryInBundleError)) {
                return super.equals(obj);
            }
            FileTypeDirectoryInBundleError other = (FileTypeDirectoryInBundleError)obj;
            boolean result = true;
            result = result && this.getInvalidDirectory().equals(other.getInvalidDirectory());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + FileTypeDirectoryInBundleError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getInvalidDirectory().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static FileTypeDirectoryInBundleError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeDirectoryInBundleError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeDirectoryInBundleError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeDirectoryInBundleError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeDirectoryInBundleError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeDirectoryInBundleError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeDirectoryInBundleError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeDirectoryInBundleError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeDirectoryInBundleError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FileTypeDirectoryInBundleError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeDirectoryInBundleError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeDirectoryInBundleError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return FileTypeDirectoryInBundleError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FileTypeDirectoryInBundleError prototype) {
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

        public static FileTypeDirectoryInBundleError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileTypeDirectoryInBundleError> parser() {
            return PARSER;
        }

        public Parser<FileTypeDirectoryInBundleError> getParserForType() {
            return PARSER;
        }

        @Override
        public FileTypeDirectoryInBundleError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements FileTypeDirectoryInBundleErrorOrBuilder {
            private Object invalidDirectory_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_FileTypeDirectoryInBundleError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_FileTypeDirectoryInBundleError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeDirectoryInBundleError.class, Builder.class);
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
                this.invalidDirectory_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_FileTypeDirectoryInBundleError_descriptor;
            }

            @Override
            public FileTypeDirectoryInBundleError getDefaultInstanceForType() {
                return FileTypeDirectoryInBundleError.getDefaultInstance();
            }

            @Override
            public FileTypeDirectoryInBundleError build() {
                FileTypeDirectoryInBundleError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public FileTypeDirectoryInBundleError buildPartial() {
                FileTypeDirectoryInBundleError result = new FileTypeDirectoryInBundleError(this);
                result.invalidDirectory_ = this.invalidDirectory_;
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
                if (other instanceof FileTypeDirectoryInBundleError) {
                    return this.mergeFrom((FileTypeDirectoryInBundleError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileTypeDirectoryInBundleError other) {
                if (other == FileTypeDirectoryInBundleError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getInvalidDirectory().isEmpty()) {
                    this.invalidDirectory_ = other.invalidDirectory_;
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
                FileTypeDirectoryInBundleError parsedMessage = null;
                try {
                    parsedMessage = (FileTypeDirectoryInBundleError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (FileTypeDirectoryInBundleError)e2.getUnfinishedMessage();
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
            public String getInvalidDirectory() {
                Object ref = this.invalidDirectory_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.invalidDirectory_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getInvalidDirectoryBytes() {
                Object ref = this.invalidDirectory_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.invalidDirectory_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setInvalidDirectory(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.invalidDirectory_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInvalidDirectory() {
                this.invalidDirectory_ = FileTypeDirectoryInBundleError.getDefaultInstance().getInvalidDirectory();
                this.onChanged();
                return this;
            }

            public Builder setInvalidDirectoryBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeDirectoryInBundleError.checkByteStringIsUtf8(value);
                this.invalidDirectory_ = value;
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

    public static interface FileTypeDirectoryInBundleErrorOrBuilder
    extends MessageOrBuilder {
        public String getInvalidDirectory();

        public ByteString getInvalidDirectoryBytes();
    }

    public static final class FileTypeFileUsesReservedNameError
    extends GeneratedMessageV3
    implements FileTypeFileUsesReservedNameErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int INVALID_FILE_FIELD_NUMBER = 1;
        private volatile Object invalidFile_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final FileTypeFileUsesReservedNameError DEFAULT_INSTANCE = new FileTypeFileUsesReservedNameError();
        private static final Parser<FileTypeFileUsesReservedNameError> PARSER = new AbstractParser<FileTypeFileUsesReservedNameError>(){

            @Override
            public FileTypeFileUsesReservedNameError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileTypeFileUsesReservedNameError(input, extensionRegistry);
            }
        };

        private FileTypeFileUsesReservedNameError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private FileTypeFileUsesReservedNameError() {
            this.invalidFile_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileTypeFileUsesReservedNameError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.invalidFile_ = s3;
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
            return internal_static_android_bundle_FileTypeFileUsesReservedNameError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_FileTypeFileUsesReservedNameError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeFileUsesReservedNameError.class, Builder.class);
        }

        @Override
        public String getInvalidFile() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.invalidFile_ = s3;
            return s3;
        }

        @Override
        public ByteString getInvalidFileBytes() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.invalidFile_ = b2;
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
            if (!this.getInvalidFileBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.invalidFile_);
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
            if (!this.getInvalidFileBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.invalidFile_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FileTypeFileUsesReservedNameError)) {
                return super.equals(obj);
            }
            FileTypeFileUsesReservedNameError other = (FileTypeFileUsesReservedNameError)obj;
            boolean result = true;
            result = result && this.getInvalidFile().equals(other.getInvalidFile());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + FileTypeFileUsesReservedNameError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getInvalidFile().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static FileTypeFileUsesReservedNameError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeFileUsesReservedNameError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeFileUsesReservedNameError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeFileUsesReservedNameError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeFileUsesReservedNameError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeFileUsesReservedNameError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeFileUsesReservedNameError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeFileUsesReservedNameError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeFileUsesReservedNameError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FileTypeFileUsesReservedNameError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeFileUsesReservedNameError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeFileUsesReservedNameError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return FileTypeFileUsesReservedNameError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FileTypeFileUsesReservedNameError prototype) {
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

        public static FileTypeFileUsesReservedNameError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileTypeFileUsesReservedNameError> parser() {
            return PARSER;
        }

        public Parser<FileTypeFileUsesReservedNameError> getParserForType() {
            return PARSER;
        }

        @Override
        public FileTypeFileUsesReservedNameError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements FileTypeFileUsesReservedNameErrorOrBuilder {
            private Object invalidFile_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_FileTypeFileUsesReservedNameError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_FileTypeFileUsesReservedNameError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeFileUsesReservedNameError.class, Builder.class);
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
                this.invalidFile_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_FileTypeFileUsesReservedNameError_descriptor;
            }

            @Override
            public FileTypeFileUsesReservedNameError getDefaultInstanceForType() {
                return FileTypeFileUsesReservedNameError.getDefaultInstance();
            }

            @Override
            public FileTypeFileUsesReservedNameError build() {
                FileTypeFileUsesReservedNameError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public FileTypeFileUsesReservedNameError buildPartial() {
                FileTypeFileUsesReservedNameError result = new FileTypeFileUsesReservedNameError(this);
                result.invalidFile_ = this.invalidFile_;
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
                if (other instanceof FileTypeFileUsesReservedNameError) {
                    return this.mergeFrom((FileTypeFileUsesReservedNameError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileTypeFileUsesReservedNameError other) {
                if (other == FileTypeFileUsesReservedNameError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getInvalidFile().isEmpty()) {
                    this.invalidFile_ = other.invalidFile_;
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
                FileTypeFileUsesReservedNameError parsedMessage = null;
                try {
                    parsedMessage = (FileTypeFileUsesReservedNameError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (FileTypeFileUsesReservedNameError)e2.getUnfinishedMessage();
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
            public String getInvalidFile() {
                Object ref = this.invalidFile_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.invalidFile_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getInvalidFileBytes() {
                Object ref = this.invalidFile_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.invalidFile_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setInvalidFile(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.invalidFile_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInvalidFile() {
                this.invalidFile_ = FileTypeFileUsesReservedNameError.getDefaultInstance().getInvalidFile();
                this.onChanged();
                return this;
            }

            public Builder setInvalidFileBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeFileUsesReservedNameError.checkByteStringIsUtf8(value);
                this.invalidFile_ = value;
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

    public static interface FileTypeFileUsesReservedNameErrorOrBuilder
    extends MessageOrBuilder {
        public String getInvalidFile();

        public ByteString getInvalidFileBytes();
    }

    public static final class FileTypeUnknownFileOrDirectoryFoundInModuleError
    extends GeneratedMessageV3
    implements FileTypeUnknownFileOrDirectoryFoundInModuleErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int INVALID_FILE_FIELD_NUMBER = 1;
        private volatile Object invalidFile_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final FileTypeUnknownFileOrDirectoryFoundInModuleError DEFAULT_INSTANCE = new FileTypeUnknownFileOrDirectoryFoundInModuleError();
        private static final Parser<FileTypeUnknownFileOrDirectoryFoundInModuleError> PARSER = new AbstractParser<FileTypeUnknownFileOrDirectoryFoundInModuleError>(){

            @Override
            public FileTypeUnknownFileOrDirectoryFoundInModuleError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileTypeUnknownFileOrDirectoryFoundInModuleError(input, extensionRegistry);
            }
        };

        private FileTypeUnknownFileOrDirectoryFoundInModuleError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private FileTypeUnknownFileOrDirectoryFoundInModuleError() {
            this.invalidFile_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileTypeUnknownFileOrDirectoryFoundInModuleError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.invalidFile_ = s3;
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
            return internal_static_android_bundle_FileTypeUnknownFileOrDirectoryFoundInModuleError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_FileTypeUnknownFileOrDirectoryFoundInModuleError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeUnknownFileOrDirectoryFoundInModuleError.class, Builder.class);
        }

        @Override
        public String getInvalidFile() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.invalidFile_ = s3;
            return s3;
        }

        @Override
        public ByteString getInvalidFileBytes() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.invalidFile_ = b2;
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
            if (!this.getInvalidFileBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.invalidFile_);
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
            if (!this.getInvalidFileBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.invalidFile_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FileTypeUnknownFileOrDirectoryFoundInModuleError)) {
                return super.equals(obj);
            }
            FileTypeUnknownFileOrDirectoryFoundInModuleError other = (FileTypeUnknownFileOrDirectoryFoundInModuleError)obj;
            boolean result = true;
            result = result && this.getInvalidFile().equals(other.getInvalidFile());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + FileTypeUnknownFileOrDirectoryFoundInModuleError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getInvalidFile().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return FileTypeUnknownFileOrDirectoryFoundInModuleError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FileTypeUnknownFileOrDirectoryFoundInModuleError prototype) {
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

        public static FileTypeUnknownFileOrDirectoryFoundInModuleError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileTypeUnknownFileOrDirectoryFoundInModuleError> parser() {
            return PARSER;
        }

        public Parser<FileTypeUnknownFileOrDirectoryFoundInModuleError> getParserForType() {
            return PARSER;
        }

        @Override
        public FileTypeUnknownFileOrDirectoryFoundInModuleError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements FileTypeUnknownFileOrDirectoryFoundInModuleErrorOrBuilder {
            private Object invalidFile_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_FileTypeUnknownFileOrDirectoryFoundInModuleError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_FileTypeUnknownFileOrDirectoryFoundInModuleError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeUnknownFileOrDirectoryFoundInModuleError.class, Builder.class);
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
                this.invalidFile_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_FileTypeUnknownFileOrDirectoryFoundInModuleError_descriptor;
            }

            @Override
            public FileTypeUnknownFileOrDirectoryFoundInModuleError getDefaultInstanceForType() {
                return FileTypeUnknownFileOrDirectoryFoundInModuleError.getDefaultInstance();
            }

            @Override
            public FileTypeUnknownFileOrDirectoryFoundInModuleError build() {
                FileTypeUnknownFileOrDirectoryFoundInModuleError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public FileTypeUnknownFileOrDirectoryFoundInModuleError buildPartial() {
                FileTypeUnknownFileOrDirectoryFoundInModuleError result = new FileTypeUnknownFileOrDirectoryFoundInModuleError(this);
                result.invalidFile_ = this.invalidFile_;
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
                if (other instanceof FileTypeUnknownFileOrDirectoryFoundInModuleError) {
                    return this.mergeFrom((FileTypeUnknownFileOrDirectoryFoundInModuleError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileTypeUnknownFileOrDirectoryFoundInModuleError other) {
                if (other == FileTypeUnknownFileOrDirectoryFoundInModuleError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getInvalidFile().isEmpty()) {
                    this.invalidFile_ = other.invalidFile_;
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
                FileTypeUnknownFileOrDirectoryFoundInModuleError parsedMessage = null;
                try {
                    parsedMessage = (FileTypeUnknownFileOrDirectoryFoundInModuleError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (FileTypeUnknownFileOrDirectoryFoundInModuleError)e2.getUnfinishedMessage();
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
            public String getInvalidFile() {
                Object ref = this.invalidFile_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.invalidFile_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getInvalidFileBytes() {
                Object ref = this.invalidFile_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.invalidFile_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setInvalidFile(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.invalidFile_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInvalidFile() {
                this.invalidFile_ = FileTypeUnknownFileOrDirectoryFoundInModuleError.getDefaultInstance().getInvalidFile();
                this.onChanged();
                return this;
            }

            public Builder setInvalidFileBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeUnknownFileOrDirectoryFoundInModuleError.checkByteStringIsUtf8(value);
                this.invalidFile_ = value;
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

    public static interface FileTypeUnknownFileOrDirectoryFoundInModuleErrorOrBuilder
    extends MessageOrBuilder {
        public String getInvalidFile();

        public ByteString getInvalidFileBytes();
    }

    public static final class FileTypeFilesInResourceDirectoryRootError
    extends GeneratedMessageV3
    implements FileTypeFilesInResourceDirectoryRootErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int RESOURCE_DIRECTORY_FIELD_NUMBER = 1;
        private volatile Object resourceDirectory_;
        public static final int INVALID_FILE_FIELD_NUMBER = 2;
        private volatile Object invalidFile_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final FileTypeFilesInResourceDirectoryRootError DEFAULT_INSTANCE = new FileTypeFilesInResourceDirectoryRootError();
        private static final Parser<FileTypeFilesInResourceDirectoryRootError> PARSER = new AbstractParser<FileTypeFilesInResourceDirectoryRootError>(){

            @Override
            public FileTypeFilesInResourceDirectoryRootError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileTypeFilesInResourceDirectoryRootError(input, extensionRegistry);
            }
        };

        private FileTypeFilesInResourceDirectoryRootError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private FileTypeFilesInResourceDirectoryRootError() {
            this.resourceDirectory_ = "";
            this.invalidFile_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileTypeFilesInResourceDirectoryRootError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.resourceDirectory_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    s3 = input.readStringRequireUtf8();
                    this.invalidFile_ = s3;
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
            return internal_static_android_bundle_FileTypeFilesInResourceDirectoryRootError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_FileTypeFilesInResourceDirectoryRootError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeFilesInResourceDirectoryRootError.class, Builder.class);
        }

        @Override
        public String getResourceDirectory() {
            Object ref = this.resourceDirectory_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.resourceDirectory_ = s3;
            return s3;
        }

        @Override
        public ByteString getResourceDirectoryBytes() {
            Object ref = this.resourceDirectory_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.resourceDirectory_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public String getInvalidFile() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.invalidFile_ = s3;
            return s3;
        }

        @Override
        public ByteString getInvalidFileBytes() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.invalidFile_ = b2;
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
            if (!this.getResourceDirectoryBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.resourceDirectory_);
            }
            if (!this.getInvalidFileBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.invalidFile_);
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
            if (!this.getResourceDirectoryBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.resourceDirectory_);
            }
            if (!this.getInvalidFileBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.invalidFile_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FileTypeFilesInResourceDirectoryRootError)) {
                return super.equals(obj);
            }
            FileTypeFilesInResourceDirectoryRootError other = (FileTypeFilesInResourceDirectoryRootError)obj;
            boolean result = true;
            result = result && this.getResourceDirectory().equals(other.getResourceDirectory());
            result = result && this.getInvalidFile().equals(other.getInvalidFile());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + FileTypeFilesInResourceDirectoryRootError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getResourceDirectory().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getInvalidFile().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static FileTypeFilesInResourceDirectoryRootError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeFilesInResourceDirectoryRootError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return FileTypeFilesInResourceDirectoryRootError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FileTypeFilesInResourceDirectoryRootError prototype) {
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

        public static FileTypeFilesInResourceDirectoryRootError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileTypeFilesInResourceDirectoryRootError> parser() {
            return PARSER;
        }

        public Parser<FileTypeFilesInResourceDirectoryRootError> getParserForType() {
            return PARSER;
        }

        @Override
        public FileTypeFilesInResourceDirectoryRootError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements FileTypeFilesInResourceDirectoryRootErrorOrBuilder {
            private Object resourceDirectory_ = "";
            private Object invalidFile_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_FileTypeFilesInResourceDirectoryRootError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_FileTypeFilesInResourceDirectoryRootError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeFilesInResourceDirectoryRootError.class, Builder.class);
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
                this.resourceDirectory_ = "";
                this.invalidFile_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_FileTypeFilesInResourceDirectoryRootError_descriptor;
            }

            @Override
            public FileTypeFilesInResourceDirectoryRootError getDefaultInstanceForType() {
                return FileTypeFilesInResourceDirectoryRootError.getDefaultInstance();
            }

            @Override
            public FileTypeFilesInResourceDirectoryRootError build() {
                FileTypeFilesInResourceDirectoryRootError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public FileTypeFilesInResourceDirectoryRootError buildPartial() {
                FileTypeFilesInResourceDirectoryRootError result = new FileTypeFilesInResourceDirectoryRootError(this);
                result.resourceDirectory_ = this.resourceDirectory_;
                result.invalidFile_ = this.invalidFile_;
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
                if (other instanceof FileTypeFilesInResourceDirectoryRootError) {
                    return this.mergeFrom((FileTypeFilesInResourceDirectoryRootError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileTypeFilesInResourceDirectoryRootError other) {
                if (other == FileTypeFilesInResourceDirectoryRootError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getResourceDirectory().isEmpty()) {
                    this.resourceDirectory_ = other.resourceDirectory_;
                    this.onChanged();
                }
                if (!other.getInvalidFile().isEmpty()) {
                    this.invalidFile_ = other.invalidFile_;
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
                FileTypeFilesInResourceDirectoryRootError parsedMessage = null;
                try {
                    parsedMessage = (FileTypeFilesInResourceDirectoryRootError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (FileTypeFilesInResourceDirectoryRootError)e2.getUnfinishedMessage();
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
            public String getResourceDirectory() {
                Object ref = this.resourceDirectory_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.resourceDirectory_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getResourceDirectoryBytes() {
                Object ref = this.resourceDirectory_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.resourceDirectory_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setResourceDirectory(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.resourceDirectory_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearResourceDirectory() {
                this.resourceDirectory_ = FileTypeFilesInResourceDirectoryRootError.getDefaultInstance().getResourceDirectory();
                this.onChanged();
                return this;
            }

            public Builder setResourceDirectoryBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeFilesInResourceDirectoryRootError.checkByteStringIsUtf8(value);
                this.resourceDirectory_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public String getInvalidFile() {
                Object ref = this.invalidFile_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.invalidFile_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getInvalidFileBytes() {
                Object ref = this.invalidFile_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.invalidFile_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setInvalidFile(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.invalidFile_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInvalidFile() {
                this.invalidFile_ = FileTypeFilesInResourceDirectoryRootError.getDefaultInstance().getInvalidFile();
                this.onChanged();
                return this;
            }

            public Builder setInvalidFileBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeFilesInResourceDirectoryRootError.checkByteStringIsUtf8(value);
                this.invalidFile_ = value;
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

    public static interface FileTypeFilesInResourceDirectoryRootErrorOrBuilder
    extends MessageOrBuilder {
        public String getResourceDirectory();

        public ByteString getResourceDirectoryBytes();

        public String getInvalidFile();

        public ByteString getInvalidFileBytes();
    }

    public static final class FileTypeInvalidNativeArchitectureError
    extends GeneratedMessageV3
    implements FileTypeInvalidNativeArchitectureErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int INVALID_ARCHITECTURE_DIRECTORY_FIELD_NUMBER = 1;
        private volatile Object invalidArchitectureDirectory_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final FileTypeInvalidNativeArchitectureError DEFAULT_INSTANCE = new FileTypeInvalidNativeArchitectureError();
        private static final Parser<FileTypeInvalidNativeArchitectureError> PARSER = new AbstractParser<FileTypeInvalidNativeArchitectureError>(){

            @Override
            public FileTypeInvalidNativeArchitectureError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileTypeInvalidNativeArchitectureError(input, extensionRegistry);
            }
        };

        private FileTypeInvalidNativeArchitectureError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private FileTypeInvalidNativeArchitectureError() {
            this.invalidArchitectureDirectory_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileTypeInvalidNativeArchitectureError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.invalidArchitectureDirectory_ = s3;
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
            return internal_static_android_bundle_FileTypeInvalidNativeArchitectureError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_FileTypeInvalidNativeArchitectureError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeInvalidNativeArchitectureError.class, Builder.class);
        }

        @Override
        public String getInvalidArchitectureDirectory() {
            Object ref = this.invalidArchitectureDirectory_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.invalidArchitectureDirectory_ = s3;
            return s3;
        }

        @Override
        public ByteString getInvalidArchitectureDirectoryBytes() {
            Object ref = this.invalidArchitectureDirectory_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.invalidArchitectureDirectory_ = b2;
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
            if (!this.getInvalidArchitectureDirectoryBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.invalidArchitectureDirectory_);
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
            if (!this.getInvalidArchitectureDirectoryBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.invalidArchitectureDirectory_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FileTypeInvalidNativeArchitectureError)) {
                return super.equals(obj);
            }
            FileTypeInvalidNativeArchitectureError other = (FileTypeInvalidNativeArchitectureError)obj;
            boolean result = true;
            result = result && this.getInvalidArchitectureDirectory().equals(other.getInvalidArchitectureDirectory());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + FileTypeInvalidNativeArchitectureError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getInvalidArchitectureDirectory().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static FileTypeInvalidNativeArchitectureError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidNativeArchitectureError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidNativeArchitectureError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidNativeArchitectureError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidNativeArchitectureError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidNativeArchitectureError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidNativeArchitectureError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeInvalidNativeArchitectureError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeInvalidNativeArchitectureError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FileTypeInvalidNativeArchitectureError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeInvalidNativeArchitectureError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeInvalidNativeArchitectureError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return FileTypeInvalidNativeArchitectureError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FileTypeInvalidNativeArchitectureError prototype) {
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

        public static FileTypeInvalidNativeArchitectureError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileTypeInvalidNativeArchitectureError> parser() {
            return PARSER;
        }

        public Parser<FileTypeInvalidNativeArchitectureError> getParserForType() {
            return PARSER;
        }

        @Override
        public FileTypeInvalidNativeArchitectureError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements FileTypeInvalidNativeArchitectureErrorOrBuilder {
            private Object invalidArchitectureDirectory_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_FileTypeInvalidNativeArchitectureError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_FileTypeInvalidNativeArchitectureError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeInvalidNativeArchitectureError.class, Builder.class);
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
                this.invalidArchitectureDirectory_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_FileTypeInvalidNativeArchitectureError_descriptor;
            }

            @Override
            public FileTypeInvalidNativeArchitectureError getDefaultInstanceForType() {
                return FileTypeInvalidNativeArchitectureError.getDefaultInstance();
            }

            @Override
            public FileTypeInvalidNativeArchitectureError build() {
                FileTypeInvalidNativeArchitectureError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public FileTypeInvalidNativeArchitectureError buildPartial() {
                FileTypeInvalidNativeArchitectureError result = new FileTypeInvalidNativeArchitectureError(this);
                result.invalidArchitectureDirectory_ = this.invalidArchitectureDirectory_;
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
                if (other instanceof FileTypeInvalidNativeArchitectureError) {
                    return this.mergeFrom((FileTypeInvalidNativeArchitectureError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileTypeInvalidNativeArchitectureError other) {
                if (other == FileTypeInvalidNativeArchitectureError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getInvalidArchitectureDirectory().isEmpty()) {
                    this.invalidArchitectureDirectory_ = other.invalidArchitectureDirectory_;
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
                FileTypeInvalidNativeArchitectureError parsedMessage = null;
                try {
                    parsedMessage = (FileTypeInvalidNativeArchitectureError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (FileTypeInvalidNativeArchitectureError)e2.getUnfinishedMessage();
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
            public String getInvalidArchitectureDirectory() {
                Object ref = this.invalidArchitectureDirectory_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.invalidArchitectureDirectory_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getInvalidArchitectureDirectoryBytes() {
                Object ref = this.invalidArchitectureDirectory_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.invalidArchitectureDirectory_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setInvalidArchitectureDirectory(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.invalidArchitectureDirectory_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInvalidArchitectureDirectory() {
                this.invalidArchitectureDirectory_ = FileTypeInvalidNativeArchitectureError.getDefaultInstance().getInvalidArchitectureDirectory();
                this.onChanged();
                return this;
            }

            public Builder setInvalidArchitectureDirectoryBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidNativeArchitectureError.checkByteStringIsUtf8(value);
                this.invalidArchitectureDirectory_ = value;
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

    public static interface FileTypeInvalidNativeArchitectureErrorOrBuilder
    extends MessageOrBuilder {
        public String getInvalidArchitectureDirectory();

        public ByteString getInvalidArchitectureDirectoryBytes();
    }

    public static final class FileTypeInvalidApexImagePathError
    extends GeneratedMessageV3
    implements FileTypeInvalidApexImagePathErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int BUNDLE_DIRECTORY_FIELD_NUMBER = 1;
        private volatile Object bundleDirectory_;
        public static final int INVALID_FILE_FIELD_NUMBER = 2;
        private volatile Object invalidFile_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final FileTypeInvalidApexImagePathError DEFAULT_INSTANCE = new FileTypeInvalidApexImagePathError();
        private static final Parser<FileTypeInvalidApexImagePathError> PARSER = new AbstractParser<FileTypeInvalidApexImagePathError>(){

            @Override
            public FileTypeInvalidApexImagePathError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileTypeInvalidApexImagePathError(input, extensionRegistry);
            }
        };

        private FileTypeInvalidApexImagePathError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private FileTypeInvalidApexImagePathError() {
            this.bundleDirectory_ = "";
            this.invalidFile_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileTypeInvalidApexImagePathError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bundleDirectory_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    s3 = input.readStringRequireUtf8();
                    this.invalidFile_ = s3;
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
            return internal_static_android_bundle_FileTypeInvalidApexImagePathError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_FileTypeInvalidApexImagePathError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeInvalidApexImagePathError.class, Builder.class);
        }

        @Override
        public String getBundleDirectory() {
            Object ref = this.bundleDirectory_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.bundleDirectory_ = s3;
            return s3;
        }

        @Override
        public ByteString getBundleDirectoryBytes() {
            Object ref = this.bundleDirectory_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.bundleDirectory_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public String getInvalidFile() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.invalidFile_ = s3;
            return s3;
        }

        @Override
        public ByteString getInvalidFileBytes() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.invalidFile_ = b2;
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
            if (!this.getBundleDirectoryBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.bundleDirectory_);
            }
            if (!this.getInvalidFileBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.invalidFile_);
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
            if (!this.getBundleDirectoryBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.bundleDirectory_);
            }
            if (!this.getInvalidFileBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.invalidFile_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FileTypeInvalidApexImagePathError)) {
                return super.equals(obj);
            }
            FileTypeInvalidApexImagePathError other = (FileTypeInvalidApexImagePathError)obj;
            boolean result = true;
            result = result && this.getBundleDirectory().equals(other.getBundleDirectory());
            result = result && this.getInvalidFile().equals(other.getInvalidFile());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + FileTypeInvalidApexImagePathError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getBundleDirectory().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getInvalidFile().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static FileTypeInvalidApexImagePathError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidApexImagePathError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidApexImagePathError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidApexImagePathError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidApexImagePathError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidApexImagePathError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidApexImagePathError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeInvalidApexImagePathError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeInvalidApexImagePathError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FileTypeInvalidApexImagePathError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeInvalidApexImagePathError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeInvalidApexImagePathError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return FileTypeInvalidApexImagePathError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FileTypeInvalidApexImagePathError prototype) {
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

        public static FileTypeInvalidApexImagePathError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileTypeInvalidApexImagePathError> parser() {
            return PARSER;
        }

        public Parser<FileTypeInvalidApexImagePathError> getParserForType() {
            return PARSER;
        }

        @Override
        public FileTypeInvalidApexImagePathError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements FileTypeInvalidApexImagePathErrorOrBuilder {
            private Object bundleDirectory_ = "";
            private Object invalidFile_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_FileTypeInvalidApexImagePathError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_FileTypeInvalidApexImagePathError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeInvalidApexImagePathError.class, Builder.class);
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
                this.bundleDirectory_ = "";
                this.invalidFile_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_FileTypeInvalidApexImagePathError_descriptor;
            }

            @Override
            public FileTypeInvalidApexImagePathError getDefaultInstanceForType() {
                return FileTypeInvalidApexImagePathError.getDefaultInstance();
            }

            @Override
            public FileTypeInvalidApexImagePathError build() {
                FileTypeInvalidApexImagePathError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public FileTypeInvalidApexImagePathError buildPartial() {
                FileTypeInvalidApexImagePathError result = new FileTypeInvalidApexImagePathError(this);
                result.bundleDirectory_ = this.bundleDirectory_;
                result.invalidFile_ = this.invalidFile_;
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
                if (other instanceof FileTypeInvalidApexImagePathError) {
                    return this.mergeFrom((FileTypeInvalidApexImagePathError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileTypeInvalidApexImagePathError other) {
                if (other == FileTypeInvalidApexImagePathError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getBundleDirectory().isEmpty()) {
                    this.bundleDirectory_ = other.bundleDirectory_;
                    this.onChanged();
                }
                if (!other.getInvalidFile().isEmpty()) {
                    this.invalidFile_ = other.invalidFile_;
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
                FileTypeInvalidApexImagePathError parsedMessage = null;
                try {
                    parsedMessage = (FileTypeInvalidApexImagePathError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (FileTypeInvalidApexImagePathError)e2.getUnfinishedMessage();
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
            public String getBundleDirectory() {
                Object ref = this.bundleDirectory_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.bundleDirectory_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getBundleDirectoryBytes() {
                Object ref = this.bundleDirectory_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.bundleDirectory_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setBundleDirectory(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bundleDirectory_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearBundleDirectory() {
                this.bundleDirectory_ = FileTypeInvalidApexImagePathError.getDefaultInstance().getBundleDirectory();
                this.onChanged();
                return this;
            }

            public Builder setBundleDirectoryBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidApexImagePathError.checkByteStringIsUtf8(value);
                this.bundleDirectory_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public String getInvalidFile() {
                Object ref = this.invalidFile_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.invalidFile_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getInvalidFileBytes() {
                Object ref = this.invalidFile_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.invalidFile_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setInvalidFile(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.invalidFile_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInvalidFile() {
                this.invalidFile_ = FileTypeInvalidApexImagePathError.getDefaultInstance().getInvalidFile();
                this.onChanged();
                return this;
            }

            public Builder setInvalidFileBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidApexImagePathError.checkByteStringIsUtf8(value);
                this.invalidFile_ = value;
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

    public static interface FileTypeInvalidApexImagePathErrorOrBuilder
    extends MessageOrBuilder {
        public String getBundleDirectory();

        public ByteString getBundleDirectoryBytes();

        public String getInvalidFile();

        public ByteString getInvalidFileBytes();
    }

    public static final class FileTypeInvalidNativeLibraryPathError
    extends GeneratedMessageV3
    implements FileTypeInvalidNativeLibraryPathErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int BUNDLE_DIRECTORY_FIELD_NUMBER = 1;
        private volatile Object bundleDirectory_;
        public static final int INVALID_FILE_FIELD_NUMBER = 2;
        private volatile Object invalidFile_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final FileTypeInvalidNativeLibraryPathError DEFAULT_INSTANCE = new FileTypeInvalidNativeLibraryPathError();
        private static final Parser<FileTypeInvalidNativeLibraryPathError> PARSER = new AbstractParser<FileTypeInvalidNativeLibraryPathError>(){

            @Override
            public FileTypeInvalidNativeLibraryPathError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileTypeInvalidNativeLibraryPathError(input, extensionRegistry);
            }
        };

        private FileTypeInvalidNativeLibraryPathError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private FileTypeInvalidNativeLibraryPathError() {
            this.bundleDirectory_ = "";
            this.invalidFile_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileTypeInvalidNativeLibraryPathError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bundleDirectory_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    s3 = input.readStringRequireUtf8();
                    this.invalidFile_ = s3;
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
            return internal_static_android_bundle_FileTypeInvalidNativeLibraryPathError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_FileTypeInvalidNativeLibraryPathError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeInvalidNativeLibraryPathError.class, Builder.class);
        }

        @Override
        public String getBundleDirectory() {
            Object ref = this.bundleDirectory_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.bundleDirectory_ = s3;
            return s3;
        }

        @Override
        public ByteString getBundleDirectoryBytes() {
            Object ref = this.bundleDirectory_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.bundleDirectory_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public String getInvalidFile() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.invalidFile_ = s3;
            return s3;
        }

        @Override
        public ByteString getInvalidFileBytes() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.invalidFile_ = b2;
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
            if (!this.getBundleDirectoryBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.bundleDirectory_);
            }
            if (!this.getInvalidFileBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.invalidFile_);
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
            if (!this.getBundleDirectoryBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.bundleDirectory_);
            }
            if (!this.getInvalidFileBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.invalidFile_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FileTypeInvalidNativeLibraryPathError)) {
                return super.equals(obj);
            }
            FileTypeInvalidNativeLibraryPathError other = (FileTypeInvalidNativeLibraryPathError)obj;
            boolean result = true;
            result = result && this.getBundleDirectory().equals(other.getBundleDirectory());
            result = result && this.getInvalidFile().equals(other.getInvalidFile());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + FileTypeInvalidNativeLibraryPathError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getBundleDirectory().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getInvalidFile().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static FileTypeInvalidNativeLibraryPathError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidNativeLibraryPathError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidNativeLibraryPathError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidNativeLibraryPathError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidNativeLibraryPathError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidNativeLibraryPathError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidNativeLibraryPathError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeInvalidNativeLibraryPathError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeInvalidNativeLibraryPathError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FileTypeInvalidNativeLibraryPathError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeInvalidNativeLibraryPathError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeInvalidNativeLibraryPathError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return FileTypeInvalidNativeLibraryPathError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FileTypeInvalidNativeLibraryPathError prototype) {
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

        public static FileTypeInvalidNativeLibraryPathError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileTypeInvalidNativeLibraryPathError> parser() {
            return PARSER;
        }

        public Parser<FileTypeInvalidNativeLibraryPathError> getParserForType() {
            return PARSER;
        }

        @Override
        public FileTypeInvalidNativeLibraryPathError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements FileTypeInvalidNativeLibraryPathErrorOrBuilder {
            private Object bundleDirectory_ = "";
            private Object invalidFile_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_FileTypeInvalidNativeLibraryPathError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_FileTypeInvalidNativeLibraryPathError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeInvalidNativeLibraryPathError.class, Builder.class);
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
                this.bundleDirectory_ = "";
                this.invalidFile_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_FileTypeInvalidNativeLibraryPathError_descriptor;
            }

            @Override
            public FileTypeInvalidNativeLibraryPathError getDefaultInstanceForType() {
                return FileTypeInvalidNativeLibraryPathError.getDefaultInstance();
            }

            @Override
            public FileTypeInvalidNativeLibraryPathError build() {
                FileTypeInvalidNativeLibraryPathError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public FileTypeInvalidNativeLibraryPathError buildPartial() {
                FileTypeInvalidNativeLibraryPathError result = new FileTypeInvalidNativeLibraryPathError(this);
                result.bundleDirectory_ = this.bundleDirectory_;
                result.invalidFile_ = this.invalidFile_;
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
                if (other instanceof FileTypeInvalidNativeLibraryPathError) {
                    return this.mergeFrom((FileTypeInvalidNativeLibraryPathError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileTypeInvalidNativeLibraryPathError other) {
                if (other == FileTypeInvalidNativeLibraryPathError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getBundleDirectory().isEmpty()) {
                    this.bundleDirectory_ = other.bundleDirectory_;
                    this.onChanged();
                }
                if (!other.getInvalidFile().isEmpty()) {
                    this.invalidFile_ = other.invalidFile_;
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
                FileTypeInvalidNativeLibraryPathError parsedMessage = null;
                try {
                    parsedMessage = (FileTypeInvalidNativeLibraryPathError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (FileTypeInvalidNativeLibraryPathError)e2.getUnfinishedMessage();
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
            public String getBundleDirectory() {
                Object ref = this.bundleDirectory_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.bundleDirectory_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getBundleDirectoryBytes() {
                Object ref = this.bundleDirectory_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.bundleDirectory_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setBundleDirectory(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bundleDirectory_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearBundleDirectory() {
                this.bundleDirectory_ = FileTypeInvalidNativeLibraryPathError.getDefaultInstance().getBundleDirectory();
                this.onChanged();
                return this;
            }

            public Builder setBundleDirectoryBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidNativeLibraryPathError.checkByteStringIsUtf8(value);
                this.bundleDirectory_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public String getInvalidFile() {
                Object ref = this.invalidFile_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.invalidFile_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getInvalidFileBytes() {
                Object ref = this.invalidFile_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.invalidFile_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setInvalidFile(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.invalidFile_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInvalidFile() {
                this.invalidFile_ = FileTypeInvalidNativeLibraryPathError.getDefaultInstance().getInvalidFile();
                this.onChanged();
                return this;
            }

            public Builder setInvalidFileBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidNativeLibraryPathError.checkByteStringIsUtf8(value);
                this.invalidFile_ = value;
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

    public static interface FileTypeInvalidNativeLibraryPathErrorOrBuilder
    extends MessageOrBuilder {
        public String getBundleDirectory();

        public ByteString getBundleDirectoryBytes();

        public String getInvalidFile();

        public ByteString getInvalidFileBytes();
    }

    public static final class FileTypeInvalidFileNameInDirectoryError
    extends GeneratedMessageV3
    implements FileTypeInvalidFileNameInDirectoryErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int BUNDLE_DIRECTORY_FIELD_NUMBER = 1;
        private volatile Object bundleDirectory_;
        public static final int ALLOWED_FILE_NAME_FIELD_NUMBER = 2;
        private LazyStringList allowedFileName_;
        public static final int INVALID_FILE_FIELD_NUMBER = 3;
        private volatile Object invalidFile_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final FileTypeInvalidFileNameInDirectoryError DEFAULT_INSTANCE = new FileTypeInvalidFileNameInDirectoryError();
        private static final Parser<FileTypeInvalidFileNameInDirectoryError> PARSER = new AbstractParser<FileTypeInvalidFileNameInDirectoryError>(){

            @Override
            public FileTypeInvalidFileNameInDirectoryError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileTypeInvalidFileNameInDirectoryError(input, extensionRegistry);
            }
        };

        private FileTypeInvalidFileNameInDirectoryError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private FileTypeInvalidFileNameInDirectoryError() {
            this.bundleDirectory_ = "";
            this.allowedFileName_ = LazyStringArrayList.EMPTY;
            this.invalidFile_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileTypeInvalidFileNameInDirectoryError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
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
                            this.bundleDirectory_ = s3;
                            continue block12;
                        }
                        case 18: {
                            s3 = input.readStringRequireUtf8();
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.allowedFileName_ = new LazyStringArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.allowedFileName_.add(s3);
                            continue block12;
                        }
                        case 26: 
                    }
                    s3 = input.readStringRequireUtf8();
                    this.invalidFile_ = s3;
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
                    this.allowedFileName_ = this.allowedFileName_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_FileTypeInvalidFileNameInDirectoryError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_FileTypeInvalidFileNameInDirectoryError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeInvalidFileNameInDirectoryError.class, Builder.class);
        }

        @Override
        public String getBundleDirectory() {
            Object ref = this.bundleDirectory_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.bundleDirectory_ = s3;
            return s3;
        }

        @Override
        public ByteString getBundleDirectoryBytes() {
            Object ref = this.bundleDirectory_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.bundleDirectory_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        public ProtocolStringList getAllowedFileNameList() {
            return this.allowedFileName_;
        }

        @Override
        public int getAllowedFileNameCount() {
            return this.allowedFileName_.size();
        }

        @Override
        public String getAllowedFileName(int index) {
            return (String)this.allowedFileName_.get(index);
        }

        @Override
        public ByteString getAllowedFileNameBytes(int index) {
            return this.allowedFileName_.getByteString(index);
        }

        @Override
        public String getInvalidFile() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.invalidFile_ = s3;
            return s3;
        }

        @Override
        public ByteString getInvalidFileBytes() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.invalidFile_ = b2;
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
            if (!this.getBundleDirectoryBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.bundleDirectory_);
            }
            for (int i2 = 0; i2 < this.allowedFileName_.size(); ++i2) {
                GeneratedMessageV3.writeString(output, 2, this.allowedFileName_.getRaw(i2));
            }
            if (!this.getInvalidFileBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 3, this.invalidFile_);
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
            if (!this.getBundleDirectoryBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.bundleDirectory_);
            }
            int dataSize = 0;
            for (int i2 = 0; i2 < this.allowedFileName_.size(); ++i2) {
                dataSize += FileTypeInvalidFileNameInDirectoryError.computeStringSizeNoTag(this.allowedFileName_.getRaw(i2));
            }
            size += dataSize;
            size += 1 * this.getAllowedFileNameList().size();
            if (!this.getInvalidFileBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(3, this.invalidFile_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FileTypeInvalidFileNameInDirectoryError)) {
                return super.equals(obj);
            }
            FileTypeInvalidFileNameInDirectoryError other = (FileTypeInvalidFileNameInDirectoryError)obj;
            boolean result = true;
            result = result && this.getBundleDirectory().equals(other.getBundleDirectory());
            result = result && this.getAllowedFileNameList().equals(other.getAllowedFileNameList());
            result = result && this.getInvalidFile().equals(other.getInvalidFile());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + FileTypeInvalidFileNameInDirectoryError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getBundleDirectory().hashCode();
            if (this.getAllowedFileNameCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAllowedFileNameList().hashCode();
            }
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getInvalidFile().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static FileTypeInvalidFileNameInDirectoryError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeInvalidFileNameInDirectoryError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return FileTypeInvalidFileNameInDirectoryError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FileTypeInvalidFileNameInDirectoryError prototype) {
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

        public static FileTypeInvalidFileNameInDirectoryError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileTypeInvalidFileNameInDirectoryError> parser() {
            return PARSER;
        }

        public Parser<FileTypeInvalidFileNameInDirectoryError> getParserForType() {
            return PARSER;
        }

        @Override
        public FileTypeInvalidFileNameInDirectoryError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements FileTypeInvalidFileNameInDirectoryErrorOrBuilder {
            private int bitField0_;
            private Object bundleDirectory_ = "";
            private LazyStringList allowedFileName_ = LazyStringArrayList.EMPTY;
            private Object invalidFile_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_FileTypeInvalidFileNameInDirectoryError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_FileTypeInvalidFileNameInDirectoryError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeInvalidFileNameInDirectoryError.class, Builder.class);
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
                this.bundleDirectory_ = "";
                this.allowedFileName_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.invalidFile_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_FileTypeInvalidFileNameInDirectoryError_descriptor;
            }

            @Override
            public FileTypeInvalidFileNameInDirectoryError getDefaultInstanceForType() {
                return FileTypeInvalidFileNameInDirectoryError.getDefaultInstance();
            }

            @Override
            public FileTypeInvalidFileNameInDirectoryError build() {
                FileTypeInvalidFileNameInDirectoryError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public FileTypeInvalidFileNameInDirectoryError buildPartial() {
                FileTypeInvalidFileNameInDirectoryError result = new FileTypeInvalidFileNameInDirectoryError(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                result.bundleDirectory_ = this.bundleDirectory_;
                if ((this.bitField0_ & 2) == 2) {
                    this.allowedFileName_ = this.allowedFileName_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                result.allowedFileName_ = this.allowedFileName_;
                result.invalidFile_ = this.invalidFile_;
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
                if (other instanceof FileTypeInvalidFileNameInDirectoryError) {
                    return this.mergeFrom((FileTypeInvalidFileNameInDirectoryError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileTypeInvalidFileNameInDirectoryError other) {
                if (other == FileTypeInvalidFileNameInDirectoryError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getBundleDirectory().isEmpty()) {
                    this.bundleDirectory_ = other.bundleDirectory_;
                    this.onChanged();
                }
                if (!other.allowedFileName_.isEmpty()) {
                    if (this.allowedFileName_.isEmpty()) {
                        this.allowedFileName_ = other.allowedFileName_;
                        this.bitField0_ &= 0xFFFFFFFD;
                    } else {
                        this.ensureAllowedFileNameIsMutable();
                        this.allowedFileName_.addAll(other.allowedFileName_);
                    }
                    this.onChanged();
                }
                if (!other.getInvalidFile().isEmpty()) {
                    this.invalidFile_ = other.invalidFile_;
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
                FileTypeInvalidFileNameInDirectoryError parsedMessage = null;
                try {
                    parsedMessage = (FileTypeInvalidFileNameInDirectoryError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (FileTypeInvalidFileNameInDirectoryError)e2.getUnfinishedMessage();
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
            public String getBundleDirectory() {
                Object ref = this.bundleDirectory_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.bundleDirectory_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getBundleDirectoryBytes() {
                Object ref = this.bundleDirectory_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.bundleDirectory_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setBundleDirectory(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bundleDirectory_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearBundleDirectory() {
                this.bundleDirectory_ = FileTypeInvalidFileNameInDirectoryError.getDefaultInstance().getBundleDirectory();
                this.onChanged();
                return this;
            }

            public Builder setBundleDirectoryBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidFileNameInDirectoryError.checkByteStringIsUtf8(value);
                this.bundleDirectory_ = value;
                this.onChanged();
                return this;
            }

            private void ensureAllowedFileNameIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.allowedFileName_ = new LazyStringArrayList(this.allowedFileName_);
                    this.bitField0_ |= 2;
                }
            }

            public ProtocolStringList getAllowedFileNameList() {
                return this.allowedFileName_.getUnmodifiableView();
            }

            @Override
            public int getAllowedFileNameCount() {
                return this.allowedFileName_.size();
            }

            @Override
            public String getAllowedFileName(int index) {
                return (String)this.allowedFileName_.get(index);
            }

            @Override
            public ByteString getAllowedFileNameBytes(int index) {
                return this.allowedFileName_.getByteString(index);
            }

            public Builder setAllowedFileName(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAllowedFileNameIsMutable();
                this.allowedFileName_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addAllowedFileName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureAllowedFileNameIsMutable();
                this.allowedFileName_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllAllowedFileName(Iterable<String> values2) {
                this.ensureAllowedFileNameIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.allowedFileName_);
                this.onChanged();
                return this;
            }

            public Builder clearAllowedFileName() {
                this.allowedFileName_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.onChanged();
                return this;
            }

            public Builder addAllowedFileNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidFileNameInDirectoryError.checkByteStringIsUtf8(value);
                this.ensureAllowedFileNameIsMutable();
                this.allowedFileName_.add(value);
                this.onChanged();
                return this;
            }

            @Override
            public String getInvalidFile() {
                Object ref = this.invalidFile_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.invalidFile_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getInvalidFileBytes() {
                Object ref = this.invalidFile_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.invalidFile_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setInvalidFile(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.invalidFile_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInvalidFile() {
                this.invalidFile_ = FileTypeInvalidFileNameInDirectoryError.getDefaultInstance().getInvalidFile();
                this.onChanged();
                return this;
            }

            public Builder setInvalidFileBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidFileNameInDirectoryError.checkByteStringIsUtf8(value);
                this.invalidFile_ = value;
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

    public static interface FileTypeInvalidFileNameInDirectoryErrorOrBuilder
    extends MessageOrBuilder {
        public String getBundleDirectory();

        public ByteString getBundleDirectoryBytes();

        public List<String> getAllowedFileNameList();

        public int getAllowedFileNameCount();

        public String getAllowedFileName(int var1);

        public ByteString getAllowedFileNameBytes(int var1);

        public String getInvalidFile();

        public ByteString getInvalidFileBytes();
    }

    public static final class FileTypeInvalidFileExtensionError
    extends GeneratedMessageV3
    implements FileTypeInvalidFileExtensionErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int BUNDLE_DIRECTORY_FIELD_NUMBER = 1;
        private volatile Object bundleDirectory_;
        public static final int REQUIRED_EXTENSION_FIELD_NUMBER = 2;
        private volatile Object requiredExtension_;
        public static final int INVALID_FILE_FIELD_NUMBER = 3;
        private volatile Object invalidFile_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final FileTypeInvalidFileExtensionError DEFAULT_INSTANCE = new FileTypeInvalidFileExtensionError();
        private static final Parser<FileTypeInvalidFileExtensionError> PARSER = new AbstractParser<FileTypeInvalidFileExtensionError>(){

            @Override
            public FileTypeInvalidFileExtensionError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileTypeInvalidFileExtensionError(input, extensionRegistry);
            }
        };

        private FileTypeInvalidFileExtensionError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private FileTypeInvalidFileExtensionError() {
            this.bundleDirectory_ = "";
            this.requiredExtension_ = "";
            this.invalidFile_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileTypeInvalidFileExtensionError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bundleDirectory_ = s3;
                            continue block12;
                        }
                        case 18: {
                            s3 = input.readStringRequireUtf8();
                            this.requiredExtension_ = s3;
                            continue block12;
                        }
                        case 26: 
                    }
                    s3 = input.readStringRequireUtf8();
                    this.invalidFile_ = s3;
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
            return internal_static_android_bundle_FileTypeInvalidFileExtensionError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_FileTypeInvalidFileExtensionError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeInvalidFileExtensionError.class, Builder.class);
        }

        @Override
        public String getBundleDirectory() {
            Object ref = this.bundleDirectory_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.bundleDirectory_ = s3;
            return s3;
        }

        @Override
        public ByteString getBundleDirectoryBytes() {
            Object ref = this.bundleDirectory_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.bundleDirectory_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public String getRequiredExtension() {
            Object ref = this.requiredExtension_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.requiredExtension_ = s3;
            return s3;
        }

        @Override
        public ByteString getRequiredExtensionBytes() {
            Object ref = this.requiredExtension_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.requiredExtension_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public String getInvalidFile() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.invalidFile_ = s3;
            return s3;
        }

        @Override
        public ByteString getInvalidFileBytes() {
            Object ref = this.invalidFile_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.invalidFile_ = b2;
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
            if (!this.getBundleDirectoryBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.bundleDirectory_);
            }
            if (!this.getRequiredExtensionBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.requiredExtension_);
            }
            if (!this.getInvalidFileBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 3, this.invalidFile_);
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
            if (!this.getBundleDirectoryBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.bundleDirectory_);
            }
            if (!this.getRequiredExtensionBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.requiredExtension_);
            }
            if (!this.getInvalidFileBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(3, this.invalidFile_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FileTypeInvalidFileExtensionError)) {
                return super.equals(obj);
            }
            FileTypeInvalidFileExtensionError other = (FileTypeInvalidFileExtensionError)obj;
            boolean result = true;
            result = result && this.getBundleDirectory().equals(other.getBundleDirectory());
            result = result && this.getRequiredExtension().equals(other.getRequiredExtension());
            result = result && this.getInvalidFile().equals(other.getInvalidFile());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + FileTypeInvalidFileExtensionError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getBundleDirectory().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getRequiredExtension().hashCode();
            hash = 37 * hash + 3;
            hash = 53 * hash + this.getInvalidFile().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static FileTypeInvalidFileExtensionError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidFileExtensionError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidFileExtensionError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidFileExtensionError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidFileExtensionError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static FileTypeInvalidFileExtensionError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileTypeInvalidFileExtensionError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeInvalidFileExtensionError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeInvalidFileExtensionError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static FileTypeInvalidFileExtensionError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static FileTypeInvalidFileExtensionError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static FileTypeInvalidFileExtensionError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return FileTypeInvalidFileExtensionError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(FileTypeInvalidFileExtensionError prototype) {
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

        public static FileTypeInvalidFileExtensionError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileTypeInvalidFileExtensionError> parser() {
            return PARSER;
        }

        public Parser<FileTypeInvalidFileExtensionError> getParserForType() {
            return PARSER;
        }

        @Override
        public FileTypeInvalidFileExtensionError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements FileTypeInvalidFileExtensionErrorOrBuilder {
            private Object bundleDirectory_ = "";
            private Object requiredExtension_ = "";
            private Object invalidFile_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_FileTypeInvalidFileExtensionError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_FileTypeInvalidFileExtensionError_fieldAccessorTable.ensureFieldAccessorsInitialized(FileTypeInvalidFileExtensionError.class, Builder.class);
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
                this.bundleDirectory_ = "";
                this.requiredExtension_ = "";
                this.invalidFile_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_FileTypeInvalidFileExtensionError_descriptor;
            }

            @Override
            public FileTypeInvalidFileExtensionError getDefaultInstanceForType() {
                return FileTypeInvalidFileExtensionError.getDefaultInstance();
            }

            @Override
            public FileTypeInvalidFileExtensionError build() {
                FileTypeInvalidFileExtensionError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public FileTypeInvalidFileExtensionError buildPartial() {
                FileTypeInvalidFileExtensionError result = new FileTypeInvalidFileExtensionError(this);
                result.bundleDirectory_ = this.bundleDirectory_;
                result.requiredExtension_ = this.requiredExtension_;
                result.invalidFile_ = this.invalidFile_;
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
                if (other instanceof FileTypeInvalidFileExtensionError) {
                    return this.mergeFrom((FileTypeInvalidFileExtensionError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileTypeInvalidFileExtensionError other) {
                if (other == FileTypeInvalidFileExtensionError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getBundleDirectory().isEmpty()) {
                    this.bundleDirectory_ = other.bundleDirectory_;
                    this.onChanged();
                }
                if (!other.getRequiredExtension().isEmpty()) {
                    this.requiredExtension_ = other.requiredExtension_;
                    this.onChanged();
                }
                if (!other.getInvalidFile().isEmpty()) {
                    this.invalidFile_ = other.invalidFile_;
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
                FileTypeInvalidFileExtensionError parsedMessage = null;
                try {
                    parsedMessage = (FileTypeInvalidFileExtensionError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (FileTypeInvalidFileExtensionError)e2.getUnfinishedMessage();
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
            public String getBundleDirectory() {
                Object ref = this.bundleDirectory_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.bundleDirectory_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getBundleDirectoryBytes() {
                Object ref = this.bundleDirectory_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.bundleDirectory_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setBundleDirectory(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bundleDirectory_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearBundleDirectory() {
                this.bundleDirectory_ = FileTypeInvalidFileExtensionError.getDefaultInstance().getBundleDirectory();
                this.onChanged();
                return this;
            }

            public Builder setBundleDirectoryBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidFileExtensionError.checkByteStringIsUtf8(value);
                this.bundleDirectory_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public String getRequiredExtension() {
                Object ref = this.requiredExtension_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.requiredExtension_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getRequiredExtensionBytes() {
                Object ref = this.requiredExtension_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.requiredExtension_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setRequiredExtension(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.requiredExtension_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearRequiredExtension() {
                this.requiredExtension_ = FileTypeInvalidFileExtensionError.getDefaultInstance().getRequiredExtension();
                this.onChanged();
                return this;
            }

            public Builder setRequiredExtensionBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidFileExtensionError.checkByteStringIsUtf8(value);
                this.requiredExtension_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public String getInvalidFile() {
                Object ref = this.invalidFile_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.invalidFile_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getInvalidFileBytes() {
                Object ref = this.invalidFile_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.invalidFile_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setInvalidFile(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.invalidFile_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInvalidFile() {
                this.invalidFile_ = FileTypeInvalidFileExtensionError.getDefaultInstance().getInvalidFile();
                this.onChanged();
                return this;
            }

            public Builder setInvalidFileBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                FileTypeInvalidFileExtensionError.checkByteStringIsUtf8(value);
                this.invalidFile_ = value;
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

    public static interface FileTypeInvalidFileExtensionErrorOrBuilder
    extends MessageOrBuilder {
        public String getBundleDirectory();

        public ByteString getBundleDirectoryBytes();

        public String getRequiredExtension();

        public ByteString getRequiredExtensionBytes();

        public String getInvalidFile();

        public ByteString getInvalidFileBytes();
    }

    public static final class ManifestModulesDifferentVersionCodes
    extends GeneratedMessageV3
    implements ManifestModulesDifferentVersionCodesOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int VERSION_CODES_FIELD_NUMBER = 1;
        private List<Integer> versionCodes_;
        private int versionCodesMemoizedSerializedSize = -1;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestModulesDifferentVersionCodes DEFAULT_INSTANCE = new ManifestModulesDifferentVersionCodes();
        private static final Parser<ManifestModulesDifferentVersionCodes> PARSER = new AbstractParser<ManifestModulesDifferentVersionCodes>(){

            @Override
            public ManifestModulesDifferentVersionCodes parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestModulesDifferentVersionCodes(input, extensionRegistry);
            }
        };

        private ManifestModulesDifferentVersionCodes(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestModulesDifferentVersionCodes() {
            this.versionCodes_ = Collections.emptyList();
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestModulesDifferentVersionCodes(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            if (!(mutable_bitField0_ & true)) {
                                this.versionCodes_ = new ArrayList<Integer>();
                                mutable_bitField0_ |= true;
                            }
                            this.versionCodes_.add(input.readInt32());
                            continue block11;
                        }
                        case 10: 
                    }
                    int length = input.readRawVarint32();
                    int limit = input.pushLimit(length);
                    if (!(mutable_bitField0_ & true) && input.getBytesUntilLimit() > 0) {
                        this.versionCodes_ = new ArrayList<Integer>();
                        mutable_bitField0_ |= true;
                    }
                    while (input.getBytesUntilLimit() > 0) {
                        this.versionCodes_.add(input.readInt32());
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
                if (mutable_bitField0_ & true) {
                    this.versionCodes_ = Collections.unmodifiableList(this.versionCodes_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_android_bundle_ManifestModulesDifferentVersionCodes_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestModulesDifferentVersionCodes_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestModulesDifferentVersionCodes.class, Builder.class);
        }

        @Override
        public List<Integer> getVersionCodesList() {
            return this.versionCodes_;
        }

        @Override
        public int getVersionCodesCount() {
            return this.versionCodes_.size();
        }

        @Override
        public int getVersionCodes(int index) {
            return this.versionCodes_.get(index);
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
            this.getSerializedSize();
            if (this.getVersionCodesList().size() > 0) {
                output.writeUInt32NoTag(10);
                output.writeUInt32NoTag(this.versionCodesMemoizedSerializedSize);
            }
            for (int i2 = 0; i2 < this.versionCodes_.size(); ++i2) {
                output.writeInt32NoTag(this.versionCodes_.get(i2));
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
            for (int i2 = 0; i2 < this.versionCodes_.size(); ++i2) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(this.versionCodes_.get(i2));
            }
            size += dataSize;
            if (!this.getVersionCodesList().isEmpty()) {
                ++size;
                size += CodedOutputStream.computeInt32SizeNoTag(dataSize);
            }
            this.versionCodesMemoizedSerializedSize = dataSize;
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestModulesDifferentVersionCodes)) {
                return super.equals(obj);
            }
            ManifestModulesDifferentVersionCodes other = (ManifestModulesDifferentVersionCodes)obj;
            boolean result = true;
            result = result && this.getVersionCodesList().equals(other.getVersionCodesList());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestModulesDifferentVersionCodes.getDescriptor().hashCode();
            if (this.getVersionCodesCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getVersionCodesList().hashCode();
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestModulesDifferentVersionCodes parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestModulesDifferentVersionCodes parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestModulesDifferentVersionCodes parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestModulesDifferentVersionCodes parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestModulesDifferentVersionCodes parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestModulesDifferentVersionCodes parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestModulesDifferentVersionCodes parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestModulesDifferentVersionCodes parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestModulesDifferentVersionCodes parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestModulesDifferentVersionCodes parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestModulesDifferentVersionCodes parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestModulesDifferentVersionCodes parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestModulesDifferentVersionCodes.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestModulesDifferentVersionCodes prototype) {
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

        public static ManifestModulesDifferentVersionCodes getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestModulesDifferentVersionCodes> parser() {
            return PARSER;
        }

        public Parser<ManifestModulesDifferentVersionCodes> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestModulesDifferentVersionCodes getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestModulesDifferentVersionCodesOrBuilder {
            private int bitField0_;
            private List<Integer> versionCodes_ = Collections.emptyList();

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestModulesDifferentVersionCodes_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestModulesDifferentVersionCodes_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestModulesDifferentVersionCodes.class, Builder.class);
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
                this.versionCodes_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestModulesDifferentVersionCodes_descriptor;
            }

            @Override
            public ManifestModulesDifferentVersionCodes getDefaultInstanceForType() {
                return ManifestModulesDifferentVersionCodes.getDefaultInstance();
            }

            @Override
            public ManifestModulesDifferentVersionCodes build() {
                ManifestModulesDifferentVersionCodes result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestModulesDifferentVersionCodes buildPartial() {
                ManifestModulesDifferentVersionCodes result = new ManifestModulesDifferentVersionCodes(this);
                int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.versionCodes_ = Collections.unmodifiableList(this.versionCodes_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.versionCodes_ = this.versionCodes_;
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
                if (other instanceof ManifestModulesDifferentVersionCodes) {
                    return this.mergeFrom((ManifestModulesDifferentVersionCodes)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestModulesDifferentVersionCodes other) {
                if (other == ManifestModulesDifferentVersionCodes.getDefaultInstance()) {
                    return this;
                }
                if (!other.versionCodes_.isEmpty()) {
                    if (this.versionCodes_.isEmpty()) {
                        this.versionCodes_ = other.versionCodes_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    } else {
                        this.ensureVersionCodesIsMutable();
                        this.versionCodes_.addAll(other.versionCodes_);
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
                ManifestModulesDifferentVersionCodes parsedMessage = null;
                try {
                    parsedMessage = (ManifestModulesDifferentVersionCodes)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestModulesDifferentVersionCodes)e2.getUnfinishedMessage();
                    throw e2.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureVersionCodesIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.versionCodes_ = new ArrayList<Integer>(this.versionCodes_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<Integer> getVersionCodesList() {
                return Collections.unmodifiableList(this.versionCodes_);
            }

            @Override
            public int getVersionCodesCount() {
                return this.versionCodes_.size();
            }

            @Override
            public int getVersionCodes(int index) {
                return this.versionCodes_.get(index);
            }

            public Builder setVersionCodes(int index, int value) {
                this.ensureVersionCodesIsMutable();
                this.versionCodes_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addVersionCodes(int value) {
                this.ensureVersionCodesIsMutable();
                this.versionCodes_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllVersionCodes(Iterable<? extends Integer> values2) {
                this.ensureVersionCodesIsMutable();
                AbstractMessageLite.Builder.addAll(values2, this.versionCodes_);
                this.onChanged();
                return this;
            }

            public Builder clearVersionCodes() {
                this.versionCodes_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
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

    public static interface ManifestModulesDifferentVersionCodesOrBuilder
    extends MessageOrBuilder {
        public List<Integer> getVersionCodesList();

        public int getVersionCodesCount();

        public int getVersionCodes(int var1);
    }

    public static final class ManifestDuplicateAttributeError
    extends GeneratedMessageV3
    implements ManifestDuplicateAttributeErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int ATTRIBUTE_NAME_FIELD_NUMBER = 1;
        private volatile Object attributeName_;
        public static final int MODULE_NAME_FIELD_NUMBER = 2;
        private volatile Object moduleName_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestDuplicateAttributeError DEFAULT_INSTANCE = new ManifestDuplicateAttributeError();
        private static final Parser<ManifestDuplicateAttributeError> PARSER = new AbstractParser<ManifestDuplicateAttributeError>(){

            @Override
            public ManifestDuplicateAttributeError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestDuplicateAttributeError(input, extensionRegistry);
            }
        };

        private ManifestDuplicateAttributeError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestDuplicateAttributeError() {
            this.attributeName_ = "";
            this.moduleName_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestDuplicateAttributeError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.attributeName_ = s3;
                            continue block11;
                        }
                        case 18: 
                    }
                    s3 = input.readStringRequireUtf8();
                    this.moduleName_ = s3;
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
            return internal_static_android_bundle_ManifestDuplicateAttributeError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestDuplicateAttributeError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestDuplicateAttributeError.class, Builder.class);
        }

        @Override
        public String getAttributeName() {
            Object ref = this.attributeName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.attributeName_ = s3;
            return s3;
        }

        @Override
        public ByteString getAttributeNameBytes() {
            Object ref = this.attributeName_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.attributeName_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public String getModuleName() {
            Object ref = this.moduleName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.moduleName_ = s3;
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
            if (!this.getAttributeNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.attributeName_);
            }
            if (!this.getModuleNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.moduleName_);
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
            if (!this.getAttributeNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.attributeName_);
            }
            if (!this.getModuleNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(2, this.moduleName_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestDuplicateAttributeError)) {
                return super.equals(obj);
            }
            ManifestDuplicateAttributeError other = (ManifestDuplicateAttributeError)obj;
            boolean result = true;
            result = result && this.getAttributeName().equals(other.getAttributeName());
            result = result && this.getModuleName().equals(other.getModuleName());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestDuplicateAttributeError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getAttributeName().hashCode();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getModuleName().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestDuplicateAttributeError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestDuplicateAttributeError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestDuplicateAttributeError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestDuplicateAttributeError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestDuplicateAttributeError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestDuplicateAttributeError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestDuplicateAttributeError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestDuplicateAttributeError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestDuplicateAttributeError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestDuplicateAttributeError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestDuplicateAttributeError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestDuplicateAttributeError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestDuplicateAttributeError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestDuplicateAttributeError prototype) {
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

        public static ManifestDuplicateAttributeError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestDuplicateAttributeError> parser() {
            return PARSER;
        }

        public Parser<ManifestDuplicateAttributeError> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestDuplicateAttributeError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestDuplicateAttributeErrorOrBuilder {
            private Object attributeName_ = "";
            private Object moduleName_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestDuplicateAttributeError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestDuplicateAttributeError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestDuplicateAttributeError.class, Builder.class);
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
                this.attributeName_ = "";
                this.moduleName_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestDuplicateAttributeError_descriptor;
            }

            @Override
            public ManifestDuplicateAttributeError getDefaultInstanceForType() {
                return ManifestDuplicateAttributeError.getDefaultInstance();
            }

            @Override
            public ManifestDuplicateAttributeError build() {
                ManifestDuplicateAttributeError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestDuplicateAttributeError buildPartial() {
                ManifestDuplicateAttributeError result = new ManifestDuplicateAttributeError(this);
                result.attributeName_ = this.attributeName_;
                result.moduleName_ = this.moduleName_;
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
                if (other instanceof ManifestDuplicateAttributeError) {
                    return this.mergeFrom((ManifestDuplicateAttributeError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestDuplicateAttributeError other) {
                if (other == ManifestDuplicateAttributeError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getAttributeName().isEmpty()) {
                    this.attributeName_ = other.attributeName_;
                    this.onChanged();
                }
                if (!other.getModuleName().isEmpty()) {
                    this.moduleName_ = other.moduleName_;
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
                ManifestDuplicateAttributeError parsedMessage = null;
                try {
                    parsedMessage = (ManifestDuplicateAttributeError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestDuplicateAttributeError)e2.getUnfinishedMessage();
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
            public String getAttributeName() {
                Object ref = this.attributeName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.attributeName_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getAttributeNameBytes() {
                Object ref = this.attributeName_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.attributeName_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setAttributeName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.attributeName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearAttributeName() {
                this.attributeName_ = ManifestDuplicateAttributeError.getDefaultInstance().getAttributeName();
                this.onChanged();
                return this;
            }

            public Builder setAttributeNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ManifestDuplicateAttributeError.checkByteStringIsUtf8(value);
                this.attributeName_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public String getModuleName() {
                Object ref = this.moduleName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.moduleName_ = s3;
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
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearModuleName() {
                this.moduleName_ = ManifestDuplicateAttributeError.getDefaultInstance().getModuleName();
                this.onChanged();
                return this;
            }

            public Builder setModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ManifestDuplicateAttributeError.checkByteStringIsUtf8(value);
                this.moduleName_ = value;
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

    public static interface ManifestDuplicateAttributeErrorOrBuilder
    extends MessageOrBuilder {
        public String getAttributeName();

        public ByteString getAttributeNameBytes();

        public String getModuleName();

        public ByteString getModuleNameBytes();
    }

    public static final class ManifestFusingMissingIncludeAttributeError
    extends GeneratedMessageV3
    implements ManifestFusingMissingIncludeAttributeErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MODULE_NAME_FIELD_NUMBER = 1;
        private volatile Object moduleName_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestFusingMissingIncludeAttributeError DEFAULT_INSTANCE = new ManifestFusingMissingIncludeAttributeError();
        private static final Parser<ManifestFusingMissingIncludeAttributeError> PARSER = new AbstractParser<ManifestFusingMissingIncludeAttributeError>(){

            @Override
            public ManifestFusingMissingIncludeAttributeError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestFusingMissingIncludeAttributeError(input, extensionRegistry);
            }
        };

        private ManifestFusingMissingIncludeAttributeError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestFusingMissingIncludeAttributeError() {
            this.moduleName_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestFusingMissingIncludeAttributeError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.moduleName_ = s3;
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
            return internal_static_android_bundle_ManifestFusingMissingIncludeAttributeError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestFusingMissingIncludeAttributeError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestFusingMissingIncludeAttributeError.class, Builder.class);
        }

        @Override
        public String getModuleName() {
            Object ref = this.moduleName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.moduleName_ = s3;
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
            if (!this.getModuleNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.moduleName_);
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
            if (!this.getModuleNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.moduleName_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestFusingMissingIncludeAttributeError)) {
                return super.equals(obj);
            }
            ManifestFusingMissingIncludeAttributeError other = (ManifestFusingMissingIncludeAttributeError)obj;
            boolean result = true;
            result = result && this.getModuleName().equals(other.getModuleName());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestFusingMissingIncludeAttributeError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getModuleName().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestFusingMissingIncludeAttributeError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestFusingMissingIncludeAttributeError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestFusingMissingIncludeAttributeError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestFusingMissingIncludeAttributeError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestFusingMissingIncludeAttributeError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestFusingMissingIncludeAttributeError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestFusingMissingIncludeAttributeError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestFusingMissingIncludeAttributeError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestFusingMissingIncludeAttributeError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestFusingMissingIncludeAttributeError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestFusingMissingIncludeAttributeError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestFusingMissingIncludeAttributeError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestFusingMissingIncludeAttributeError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestFusingMissingIncludeAttributeError prototype) {
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

        public static ManifestFusingMissingIncludeAttributeError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestFusingMissingIncludeAttributeError> parser() {
            return PARSER;
        }

        public Parser<ManifestFusingMissingIncludeAttributeError> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestFusingMissingIncludeAttributeError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestFusingMissingIncludeAttributeErrorOrBuilder {
            private Object moduleName_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestFusingMissingIncludeAttributeError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestFusingMissingIncludeAttributeError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestFusingMissingIncludeAttributeError.class, Builder.class);
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
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestFusingMissingIncludeAttributeError_descriptor;
            }

            @Override
            public ManifestFusingMissingIncludeAttributeError getDefaultInstanceForType() {
                return ManifestFusingMissingIncludeAttributeError.getDefaultInstance();
            }

            @Override
            public ManifestFusingMissingIncludeAttributeError build() {
                ManifestFusingMissingIncludeAttributeError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestFusingMissingIncludeAttributeError buildPartial() {
                ManifestFusingMissingIncludeAttributeError result = new ManifestFusingMissingIncludeAttributeError(this);
                result.moduleName_ = this.moduleName_;
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
                if (other instanceof ManifestFusingMissingIncludeAttributeError) {
                    return this.mergeFrom((ManifestFusingMissingIncludeAttributeError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestFusingMissingIncludeAttributeError other) {
                if (other == ManifestFusingMissingIncludeAttributeError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getModuleName().isEmpty()) {
                    this.moduleName_ = other.moduleName_;
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
                ManifestFusingMissingIncludeAttributeError parsedMessage = null;
                try {
                    parsedMessage = (ManifestFusingMissingIncludeAttributeError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestFusingMissingIncludeAttributeError)e2.getUnfinishedMessage();
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
            public String getModuleName() {
                Object ref = this.moduleName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.moduleName_ = s3;
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
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearModuleName() {
                this.moduleName_ = ManifestFusingMissingIncludeAttributeError.getDefaultInstance().getModuleName();
                this.onChanged();
                return this;
            }

            public Builder setModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ManifestFusingMissingIncludeAttributeError.checkByteStringIsUtf8(value);
                this.moduleName_ = value;
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

    public static interface ManifestFusingMissingIncludeAttributeErrorOrBuilder
    extends MessageOrBuilder {
        public String getModuleName();

        public ByteString getModuleNameBytes();
    }

    public static final class ManifestModuleFusingConfigurationMissingError
    extends GeneratedMessageV3
    implements ManifestModuleFusingConfigurationMissingErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MODULE_NAME_FIELD_NUMBER = 1;
        private volatile Object moduleName_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestModuleFusingConfigurationMissingError DEFAULT_INSTANCE = new ManifestModuleFusingConfigurationMissingError();
        private static final Parser<ManifestModuleFusingConfigurationMissingError> PARSER = new AbstractParser<ManifestModuleFusingConfigurationMissingError>(){

            @Override
            public ManifestModuleFusingConfigurationMissingError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestModuleFusingConfigurationMissingError(input, extensionRegistry);
            }
        };

        private ManifestModuleFusingConfigurationMissingError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestModuleFusingConfigurationMissingError() {
            this.moduleName_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestModuleFusingConfigurationMissingError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.moduleName_ = s3;
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
            return internal_static_android_bundle_ManifestModuleFusingConfigurationMissingError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestModuleFusingConfigurationMissingError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestModuleFusingConfigurationMissingError.class, Builder.class);
        }

        @Override
        public String getModuleName() {
            Object ref = this.moduleName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.moduleName_ = s3;
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
            if (!this.getModuleNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.moduleName_);
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
            if (!this.getModuleNameBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.moduleName_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestModuleFusingConfigurationMissingError)) {
                return super.equals(obj);
            }
            ManifestModuleFusingConfigurationMissingError other = (ManifestModuleFusingConfigurationMissingError)obj;
            boolean result = true;
            result = result && this.getModuleName().equals(other.getModuleName());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestModuleFusingConfigurationMissingError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getModuleName().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestModuleFusingConfigurationMissingError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestModuleFusingConfigurationMissingError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestModuleFusingConfigurationMissingError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestModuleFusingConfigurationMissingError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestModuleFusingConfigurationMissingError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestModuleFusingConfigurationMissingError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestModuleFusingConfigurationMissingError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestModuleFusingConfigurationMissingError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestModuleFusingConfigurationMissingError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestModuleFusingConfigurationMissingError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestModuleFusingConfigurationMissingError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestModuleFusingConfigurationMissingError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestModuleFusingConfigurationMissingError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestModuleFusingConfigurationMissingError prototype) {
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

        public static ManifestModuleFusingConfigurationMissingError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestModuleFusingConfigurationMissingError> parser() {
            return PARSER;
        }

        public Parser<ManifestModuleFusingConfigurationMissingError> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestModuleFusingConfigurationMissingError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestModuleFusingConfigurationMissingErrorOrBuilder {
            private Object moduleName_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestModuleFusingConfigurationMissingError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestModuleFusingConfigurationMissingError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestModuleFusingConfigurationMissingError.class, Builder.class);
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
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestModuleFusingConfigurationMissingError_descriptor;
            }

            @Override
            public ManifestModuleFusingConfigurationMissingError getDefaultInstanceForType() {
                return ManifestModuleFusingConfigurationMissingError.getDefaultInstance();
            }

            @Override
            public ManifestModuleFusingConfigurationMissingError build() {
                ManifestModuleFusingConfigurationMissingError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestModuleFusingConfigurationMissingError buildPartial() {
                ManifestModuleFusingConfigurationMissingError result = new ManifestModuleFusingConfigurationMissingError(this);
                result.moduleName_ = this.moduleName_;
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
                if (other instanceof ManifestModuleFusingConfigurationMissingError) {
                    return this.mergeFrom((ManifestModuleFusingConfigurationMissingError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestModuleFusingConfigurationMissingError other) {
                if (other == ManifestModuleFusingConfigurationMissingError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getModuleName().isEmpty()) {
                    this.moduleName_ = other.moduleName_;
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
                ManifestModuleFusingConfigurationMissingError parsedMessage = null;
                try {
                    parsedMessage = (ManifestModuleFusingConfigurationMissingError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestModuleFusingConfigurationMissingError)e2.getUnfinishedMessage();
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
            public String getModuleName() {
                Object ref = this.moduleName_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.moduleName_ = s3;
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
                this.moduleName_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearModuleName() {
                this.moduleName_ = ManifestModuleFusingConfigurationMissingError.getDefaultInstance().getModuleName();
                this.onChanged();
                return this;
            }

            public Builder setModuleNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ManifestModuleFusingConfigurationMissingError.checkByteStringIsUtf8(value);
                this.moduleName_ = value;
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

    public static interface ManifestModuleFusingConfigurationMissingErrorOrBuilder
    extends MessageOrBuilder {
        public String getModuleName();

        public ByteString getModuleNameBytes();
    }

    public static final class ManifestBaseModuleExcludedFromFusingError
    extends GeneratedMessageV3
    implements ManifestBaseModuleExcludedFromFusingErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestBaseModuleExcludedFromFusingError DEFAULT_INSTANCE = new ManifestBaseModuleExcludedFromFusingError();
        private static final Parser<ManifestBaseModuleExcludedFromFusingError> PARSER = new AbstractParser<ManifestBaseModuleExcludedFromFusingError>(){

            @Override
            public ManifestBaseModuleExcludedFromFusingError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestBaseModuleExcludedFromFusingError(input, extensionRegistry);
            }
        };

        private ManifestBaseModuleExcludedFromFusingError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestBaseModuleExcludedFromFusingError() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestBaseModuleExcludedFromFusingError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block9: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block9;
                        }
                    }
                    if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue;
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
            return internal_static_android_bundle_ManifestBaseModuleExcludedFromFusingError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestBaseModuleExcludedFromFusingError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestBaseModuleExcludedFromFusingError.class, Builder.class);
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
            this.unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestBaseModuleExcludedFromFusingError)) {
                return super.equals(obj);
            }
            ManifestBaseModuleExcludedFromFusingError other = (ManifestBaseModuleExcludedFromFusingError)obj;
            boolean result = true;
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestBaseModuleExcludedFromFusingError.getDescriptor().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestBaseModuleExcludedFromFusingError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestBaseModuleExcludedFromFusingError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestBaseModuleExcludedFromFusingError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestBaseModuleExcludedFromFusingError prototype) {
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

        public static ManifestBaseModuleExcludedFromFusingError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestBaseModuleExcludedFromFusingError> parser() {
            return PARSER;
        }

        public Parser<ManifestBaseModuleExcludedFromFusingError> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestBaseModuleExcludedFromFusingError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestBaseModuleExcludedFromFusingErrorOrBuilder {
            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestBaseModuleExcludedFromFusingError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestBaseModuleExcludedFromFusingError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestBaseModuleExcludedFromFusingError.class, Builder.class);
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
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestBaseModuleExcludedFromFusingError_descriptor;
            }

            @Override
            public ManifestBaseModuleExcludedFromFusingError getDefaultInstanceForType() {
                return ManifestBaseModuleExcludedFromFusingError.getDefaultInstance();
            }

            @Override
            public ManifestBaseModuleExcludedFromFusingError build() {
                ManifestBaseModuleExcludedFromFusingError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestBaseModuleExcludedFromFusingError buildPartial() {
                ManifestBaseModuleExcludedFromFusingError result = new ManifestBaseModuleExcludedFromFusingError(this);
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
                if (other instanceof ManifestBaseModuleExcludedFromFusingError) {
                    return this.mergeFrom((ManifestBaseModuleExcludedFromFusingError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestBaseModuleExcludedFromFusingError other) {
                if (other == ManifestBaseModuleExcludedFromFusingError.getDefaultInstance()) {
                    return this;
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
                ManifestBaseModuleExcludedFromFusingError parsedMessage = null;
                try {
                    parsedMessage = (ManifestBaseModuleExcludedFromFusingError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestBaseModuleExcludedFromFusingError)e2.getUnfinishedMessage();
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
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface ManifestBaseModuleExcludedFromFusingErrorOrBuilder
    extends MessageOrBuilder {
    }

    public static final class ManifestInvalidVersionCodeError
    extends GeneratedMessageV3
    implements ManifestInvalidVersionCodeErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestInvalidVersionCodeError DEFAULT_INSTANCE = new ManifestInvalidVersionCodeError();
        private static final Parser<ManifestInvalidVersionCodeError> PARSER = new AbstractParser<ManifestInvalidVersionCodeError>(){

            @Override
            public ManifestInvalidVersionCodeError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestInvalidVersionCodeError(input, extensionRegistry);
            }
        };

        private ManifestInvalidVersionCodeError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestInvalidVersionCodeError() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestInvalidVersionCodeError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block9: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block9;
                        }
                    }
                    if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue;
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
            return internal_static_android_bundle_ManifestInvalidVersionCodeError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestInvalidVersionCodeError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestInvalidVersionCodeError.class, Builder.class);
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
            this.unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestInvalidVersionCodeError)) {
                return super.equals(obj);
            }
            ManifestInvalidVersionCodeError other = (ManifestInvalidVersionCodeError)obj;
            boolean result = true;
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestInvalidVersionCodeError.getDescriptor().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestInvalidVersionCodeError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestInvalidVersionCodeError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestInvalidVersionCodeError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestInvalidVersionCodeError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestInvalidVersionCodeError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestInvalidVersionCodeError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestInvalidVersionCodeError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestInvalidVersionCodeError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestInvalidVersionCodeError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestInvalidVersionCodeError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestInvalidVersionCodeError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestInvalidVersionCodeError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestInvalidVersionCodeError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestInvalidVersionCodeError prototype) {
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

        public static ManifestInvalidVersionCodeError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestInvalidVersionCodeError> parser() {
            return PARSER;
        }

        public Parser<ManifestInvalidVersionCodeError> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestInvalidVersionCodeError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestInvalidVersionCodeErrorOrBuilder {
            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestInvalidVersionCodeError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestInvalidVersionCodeError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestInvalidVersionCodeError.class, Builder.class);
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
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestInvalidVersionCodeError_descriptor;
            }

            @Override
            public ManifestInvalidVersionCodeError getDefaultInstanceForType() {
                return ManifestInvalidVersionCodeError.getDefaultInstance();
            }

            @Override
            public ManifestInvalidVersionCodeError build() {
                ManifestInvalidVersionCodeError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestInvalidVersionCodeError buildPartial() {
                ManifestInvalidVersionCodeError result = new ManifestInvalidVersionCodeError(this);
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
                if (other instanceof ManifestInvalidVersionCodeError) {
                    return this.mergeFrom((ManifestInvalidVersionCodeError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestInvalidVersionCodeError other) {
                if (other == ManifestInvalidVersionCodeError.getDefaultInstance()) {
                    return this;
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
                ManifestInvalidVersionCodeError parsedMessage = null;
                try {
                    parsedMessage = (ManifestInvalidVersionCodeError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestInvalidVersionCodeError)e2.getUnfinishedMessage();
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
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface ManifestInvalidVersionCodeErrorOrBuilder
    extends MessageOrBuilder {
    }

    public static final class ManifestMissingVersionCodeError
    extends GeneratedMessageV3
    implements ManifestMissingVersionCodeErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestMissingVersionCodeError DEFAULT_INSTANCE = new ManifestMissingVersionCodeError();
        private static final Parser<ManifestMissingVersionCodeError> PARSER = new AbstractParser<ManifestMissingVersionCodeError>(){

            @Override
            public ManifestMissingVersionCodeError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestMissingVersionCodeError(input, extensionRegistry);
            }
        };

        private ManifestMissingVersionCodeError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestMissingVersionCodeError() {
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestMissingVersionCodeError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block9: while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block9;
                        }
                    }
                    if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue;
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
            return internal_static_android_bundle_ManifestMissingVersionCodeError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestMissingVersionCodeError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestMissingVersionCodeError.class, Builder.class);
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
            this.unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestMissingVersionCodeError)) {
                return super.equals(obj);
            }
            ManifestMissingVersionCodeError other = (ManifestMissingVersionCodeError)obj;
            boolean result = true;
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestMissingVersionCodeError.getDescriptor().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestMissingVersionCodeError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMissingVersionCodeError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMissingVersionCodeError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMissingVersionCodeError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMissingVersionCodeError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMissingVersionCodeError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMissingVersionCodeError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestMissingVersionCodeError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestMissingVersionCodeError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestMissingVersionCodeError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestMissingVersionCodeError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestMissingVersionCodeError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestMissingVersionCodeError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestMissingVersionCodeError prototype) {
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

        public static ManifestMissingVersionCodeError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestMissingVersionCodeError> parser() {
            return PARSER;
        }

        public Parser<ManifestMissingVersionCodeError> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestMissingVersionCodeError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestMissingVersionCodeErrorOrBuilder {
            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestMissingVersionCodeError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestMissingVersionCodeError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestMissingVersionCodeError.class, Builder.class);
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
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestMissingVersionCodeError_descriptor;
            }

            @Override
            public ManifestMissingVersionCodeError getDefaultInstanceForType() {
                return ManifestMissingVersionCodeError.getDefaultInstance();
            }

            @Override
            public ManifestMissingVersionCodeError build() {
                ManifestMissingVersionCodeError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestMissingVersionCodeError buildPartial() {
                ManifestMissingVersionCodeError result = new ManifestMissingVersionCodeError(this);
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
                if (other instanceof ManifestMissingVersionCodeError) {
                    return this.mergeFrom((ManifestMissingVersionCodeError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestMissingVersionCodeError other) {
                if (other == ManifestMissingVersionCodeError.getDefaultInstance()) {
                    return this;
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
                ManifestMissingVersionCodeError parsedMessage = null;
                try {
                    parsedMessage = (ManifestMissingVersionCodeError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestMissingVersionCodeError)e2.getUnfinishedMessage();
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
            public final Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public static interface ManifestMissingVersionCodeErrorOrBuilder
    extends MessageOrBuilder {
    }

    public static final class ManifestMinSdkGreaterThanMaxSdkError
    extends GeneratedMessageV3
    implements ManifestMinSdkGreaterThanMaxSdkErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MIN_SDK_FIELD_NUMBER = 1;
        private int minSdk_;
        public static final int MAX_SDK_FIELD_NUMBER = 2;
        private int maxSdk_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestMinSdkGreaterThanMaxSdkError DEFAULT_INSTANCE = new ManifestMinSdkGreaterThanMaxSdkError();
        private static final Parser<ManifestMinSdkGreaterThanMaxSdkError> PARSER = new AbstractParser<ManifestMinSdkGreaterThanMaxSdkError>(){

            @Override
            public ManifestMinSdkGreaterThanMaxSdkError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestMinSdkGreaterThanMaxSdkError(input, extensionRegistry);
            }
        };

        private ManifestMinSdkGreaterThanMaxSdkError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestMinSdkGreaterThanMaxSdkError() {
            this.minSdk_ = 0;
            this.maxSdk_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestMinSdkGreaterThanMaxSdkError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.minSdk_ = input.readInt32();
                            continue block11;
                        }
                        case 16: 
                    }
                    this.maxSdk_ = input.readInt32();
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
            return internal_static_android_bundle_ManifestMinSdkGreaterThanMaxSdkError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestMinSdkGreaterThanMaxSdkError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestMinSdkGreaterThanMaxSdkError.class, Builder.class);
        }

        @Override
        public int getMinSdk() {
            return this.minSdk_;
        }

        @Override
        public int getMaxSdk() {
            return this.maxSdk_;
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
            if (this.minSdk_ != 0) {
                output.writeInt32(1, this.minSdk_);
            }
            if (this.maxSdk_ != 0) {
                output.writeInt32(2, this.maxSdk_);
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
            if (this.minSdk_ != 0) {
                size += CodedOutputStream.computeInt32Size(1, this.minSdk_);
            }
            if (this.maxSdk_ != 0) {
                size += CodedOutputStream.computeInt32Size(2, this.maxSdk_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestMinSdkGreaterThanMaxSdkError)) {
                return super.equals(obj);
            }
            ManifestMinSdkGreaterThanMaxSdkError other = (ManifestMinSdkGreaterThanMaxSdkError)obj;
            boolean result = true;
            result = result && this.getMinSdk() == other.getMinSdk();
            result = result && this.getMaxSdk() == other.getMaxSdk();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestMinSdkGreaterThanMaxSdkError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMinSdk();
            hash = 37 * hash + 2;
            hash = 53 * hash + this.getMaxSdk();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestMinSdkGreaterThanMaxSdkError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestMinSdkGreaterThanMaxSdkError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestMinSdkGreaterThanMaxSdkError prototype) {
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

        public static ManifestMinSdkGreaterThanMaxSdkError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestMinSdkGreaterThanMaxSdkError> parser() {
            return PARSER;
        }

        public Parser<ManifestMinSdkGreaterThanMaxSdkError> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestMinSdkGreaterThanMaxSdkError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestMinSdkGreaterThanMaxSdkErrorOrBuilder {
            private int minSdk_;
            private int maxSdk_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestMinSdkGreaterThanMaxSdkError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestMinSdkGreaterThanMaxSdkError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestMinSdkGreaterThanMaxSdkError.class, Builder.class);
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
                this.minSdk_ = 0;
                this.maxSdk_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestMinSdkGreaterThanMaxSdkError_descriptor;
            }

            @Override
            public ManifestMinSdkGreaterThanMaxSdkError getDefaultInstanceForType() {
                return ManifestMinSdkGreaterThanMaxSdkError.getDefaultInstance();
            }

            @Override
            public ManifestMinSdkGreaterThanMaxSdkError build() {
                ManifestMinSdkGreaterThanMaxSdkError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestMinSdkGreaterThanMaxSdkError buildPartial() {
                ManifestMinSdkGreaterThanMaxSdkError result = new ManifestMinSdkGreaterThanMaxSdkError(this);
                result.minSdk_ = this.minSdk_;
                result.maxSdk_ = this.maxSdk_;
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
                if (other instanceof ManifestMinSdkGreaterThanMaxSdkError) {
                    return this.mergeFrom((ManifestMinSdkGreaterThanMaxSdkError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestMinSdkGreaterThanMaxSdkError other) {
                if (other == ManifestMinSdkGreaterThanMaxSdkError.getDefaultInstance()) {
                    return this;
                }
                if (other.getMinSdk() != 0) {
                    this.setMinSdk(other.getMinSdk());
                }
                if (other.getMaxSdk() != 0) {
                    this.setMaxSdk(other.getMaxSdk());
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
                ManifestMinSdkGreaterThanMaxSdkError parsedMessage = null;
                try {
                    parsedMessage = (ManifestMinSdkGreaterThanMaxSdkError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestMinSdkGreaterThanMaxSdkError)e2.getUnfinishedMessage();
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
            public int getMinSdk() {
                return this.minSdk_;
            }

            public Builder setMinSdk(int value) {
                this.minSdk_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMinSdk() {
                this.minSdk_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public int getMaxSdk() {
                return this.maxSdk_;
            }

            public Builder setMaxSdk(int value) {
                this.maxSdk_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMaxSdk() {
                this.maxSdk_ = 0;
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

    public static interface ManifestMinSdkGreaterThanMaxSdkErrorOrBuilder
    extends MessageOrBuilder {
        public int getMinSdk();

        public int getMaxSdk();
    }

    public static final class ManifestMinSdkInvalidError
    extends GeneratedMessageV3
    implements ManifestMinSdkInvalidErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MIN_SDK_FIELD_NUMBER = 1;
        private volatile Object minSdk_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestMinSdkInvalidError DEFAULT_INSTANCE = new ManifestMinSdkInvalidError();
        private static final Parser<ManifestMinSdkInvalidError> PARSER = new AbstractParser<ManifestMinSdkInvalidError>(){

            @Override
            public ManifestMinSdkInvalidError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestMinSdkInvalidError(input, extensionRegistry);
            }
        };

        private ManifestMinSdkInvalidError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestMinSdkInvalidError() {
            this.minSdk_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestMinSdkInvalidError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.minSdk_ = s3;
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
            return internal_static_android_bundle_ManifestMinSdkInvalidError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestMinSdkInvalidError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestMinSdkInvalidError.class, Builder.class);
        }

        @Override
        public String getMinSdk() {
            Object ref = this.minSdk_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.minSdk_ = s3;
            return s3;
        }

        @Override
        public ByteString getMinSdkBytes() {
            Object ref = this.minSdk_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.minSdk_ = b2;
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
            if (!this.getMinSdkBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.minSdk_);
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
            if (!this.getMinSdkBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.minSdk_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestMinSdkInvalidError)) {
                return super.equals(obj);
            }
            ManifestMinSdkInvalidError other = (ManifestMinSdkInvalidError)obj;
            boolean result = true;
            result = result && this.getMinSdk().equals(other.getMinSdk());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestMinSdkInvalidError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMinSdk().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestMinSdkInvalidError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMinSdkInvalidError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMinSdkInvalidError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMinSdkInvalidError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMinSdkInvalidError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMinSdkInvalidError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMinSdkInvalidError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestMinSdkInvalidError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestMinSdkInvalidError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestMinSdkInvalidError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestMinSdkInvalidError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestMinSdkInvalidError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestMinSdkInvalidError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestMinSdkInvalidError prototype) {
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

        public static ManifestMinSdkInvalidError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestMinSdkInvalidError> parser() {
            return PARSER;
        }

        public Parser<ManifestMinSdkInvalidError> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestMinSdkInvalidError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestMinSdkInvalidErrorOrBuilder {
            private Object minSdk_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestMinSdkInvalidError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestMinSdkInvalidError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestMinSdkInvalidError.class, Builder.class);
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
                this.minSdk_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestMinSdkInvalidError_descriptor;
            }

            @Override
            public ManifestMinSdkInvalidError getDefaultInstanceForType() {
                return ManifestMinSdkInvalidError.getDefaultInstance();
            }

            @Override
            public ManifestMinSdkInvalidError build() {
                ManifestMinSdkInvalidError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestMinSdkInvalidError buildPartial() {
                ManifestMinSdkInvalidError result = new ManifestMinSdkInvalidError(this);
                result.minSdk_ = this.minSdk_;
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
                if (other instanceof ManifestMinSdkInvalidError) {
                    return this.mergeFrom((ManifestMinSdkInvalidError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestMinSdkInvalidError other) {
                if (other == ManifestMinSdkInvalidError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getMinSdk().isEmpty()) {
                    this.minSdk_ = other.minSdk_;
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
                ManifestMinSdkInvalidError parsedMessage = null;
                try {
                    parsedMessage = (ManifestMinSdkInvalidError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestMinSdkInvalidError)e2.getUnfinishedMessage();
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
            public String getMinSdk() {
                Object ref = this.minSdk_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.minSdk_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getMinSdkBytes() {
                Object ref = this.minSdk_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.minSdk_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setMinSdk(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.minSdk_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMinSdk() {
                this.minSdk_ = ManifestMinSdkInvalidError.getDefaultInstance().getMinSdk();
                this.onChanged();
                return this;
            }

            public Builder setMinSdkBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ManifestMinSdkInvalidError.checkByteStringIsUtf8(value);
                this.minSdk_ = value;
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

    public static interface ManifestMinSdkInvalidErrorOrBuilder
    extends MessageOrBuilder {
        public String getMinSdk();

        public ByteString getMinSdkBytes();
    }

    public static final class ManifestMaxSdkLessThanMinInstantSdkError
    extends GeneratedMessageV3
    implements ManifestMaxSdkLessThanMinInstantSdkErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MAX_SDK_FIELD_NUMBER = 1;
        private int maxSdk_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestMaxSdkLessThanMinInstantSdkError DEFAULT_INSTANCE = new ManifestMaxSdkLessThanMinInstantSdkError();
        private static final Parser<ManifestMaxSdkLessThanMinInstantSdkError> PARSER = new AbstractParser<ManifestMaxSdkLessThanMinInstantSdkError>(){

            @Override
            public ManifestMaxSdkLessThanMinInstantSdkError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestMaxSdkLessThanMinInstantSdkError(input, extensionRegistry);
            }
        };

        private ManifestMaxSdkLessThanMinInstantSdkError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestMaxSdkLessThanMinInstantSdkError() {
            this.maxSdk_ = 0;
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestMaxSdkLessThanMinInstantSdkError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.maxSdk_ = input.readInt32();
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
            return internal_static_android_bundle_ManifestMaxSdkLessThanMinInstantSdkError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestMaxSdkLessThanMinInstantSdkError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestMaxSdkLessThanMinInstantSdkError.class, Builder.class);
        }

        @Override
        public int getMaxSdk() {
            return this.maxSdk_;
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
            if (this.maxSdk_ != 0) {
                output.writeInt32(1, this.maxSdk_);
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
            if (this.maxSdk_ != 0) {
                size += CodedOutputStream.computeInt32Size(1, this.maxSdk_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestMaxSdkLessThanMinInstantSdkError)) {
                return super.equals(obj);
            }
            ManifestMaxSdkLessThanMinInstantSdkError other = (ManifestMaxSdkLessThanMinInstantSdkError)obj;
            boolean result = true;
            result = result && this.getMaxSdk() == other.getMaxSdk();
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestMaxSdkLessThanMinInstantSdkError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMaxSdk();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestMaxSdkLessThanMinInstantSdkError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestMaxSdkLessThanMinInstantSdkError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestMaxSdkLessThanMinInstantSdkError prototype) {
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

        public static ManifestMaxSdkLessThanMinInstantSdkError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestMaxSdkLessThanMinInstantSdkError> parser() {
            return PARSER;
        }

        public Parser<ManifestMaxSdkLessThanMinInstantSdkError> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestMaxSdkLessThanMinInstantSdkError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestMaxSdkLessThanMinInstantSdkErrorOrBuilder {
            private int maxSdk_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestMaxSdkLessThanMinInstantSdkError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestMaxSdkLessThanMinInstantSdkError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestMaxSdkLessThanMinInstantSdkError.class, Builder.class);
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
                this.maxSdk_ = 0;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestMaxSdkLessThanMinInstantSdkError_descriptor;
            }

            @Override
            public ManifestMaxSdkLessThanMinInstantSdkError getDefaultInstanceForType() {
                return ManifestMaxSdkLessThanMinInstantSdkError.getDefaultInstance();
            }

            @Override
            public ManifestMaxSdkLessThanMinInstantSdkError build() {
                ManifestMaxSdkLessThanMinInstantSdkError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestMaxSdkLessThanMinInstantSdkError buildPartial() {
                ManifestMaxSdkLessThanMinInstantSdkError result = new ManifestMaxSdkLessThanMinInstantSdkError(this);
                result.maxSdk_ = this.maxSdk_;
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
                if (other instanceof ManifestMaxSdkLessThanMinInstantSdkError) {
                    return this.mergeFrom((ManifestMaxSdkLessThanMinInstantSdkError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestMaxSdkLessThanMinInstantSdkError other) {
                if (other == ManifestMaxSdkLessThanMinInstantSdkError.getDefaultInstance()) {
                    return this;
                }
                if (other.getMaxSdk() != 0) {
                    this.setMaxSdk(other.getMaxSdk());
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
                ManifestMaxSdkLessThanMinInstantSdkError parsedMessage = null;
                try {
                    parsedMessage = (ManifestMaxSdkLessThanMinInstantSdkError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestMaxSdkLessThanMinInstantSdkError)e2.getUnfinishedMessage();
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
            public int getMaxSdk() {
                return this.maxSdk_;
            }

            public Builder setMaxSdk(int value) {
                this.maxSdk_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMaxSdk() {
                this.maxSdk_ = 0;
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

    public static interface ManifestMaxSdkLessThanMinInstantSdkErrorOrBuilder
    extends MessageOrBuilder {
        public int getMaxSdk();
    }

    public static final class ManifestMaxSdkInvalidError
    extends GeneratedMessageV3
    implements ManifestMaxSdkInvalidErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int MAX_SDK_FIELD_NUMBER = 1;
        private volatile Object maxSdk_;
        private byte memoizedIsInitialized = (byte)-1;
        private static final ManifestMaxSdkInvalidError DEFAULT_INSTANCE = new ManifestMaxSdkInvalidError();
        private static final Parser<ManifestMaxSdkInvalidError> PARSER = new AbstractParser<ManifestMaxSdkInvalidError>(){

            @Override
            public ManifestMaxSdkInvalidError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ManifestMaxSdkInvalidError(input, extensionRegistry);
            }
        };

        private ManifestMaxSdkInvalidError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private ManifestMaxSdkInvalidError() {
            this.maxSdk_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ManifestMaxSdkInvalidError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    this.maxSdk_ = s3;
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
            return internal_static_android_bundle_ManifestMaxSdkInvalidError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_ManifestMaxSdkInvalidError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestMaxSdkInvalidError.class, Builder.class);
        }

        @Override
        public String getMaxSdk() {
            Object ref = this.maxSdk_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.maxSdk_ = s3;
            return s3;
        }

        @Override
        public ByteString getMaxSdkBytes() {
            Object ref = this.maxSdk_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.maxSdk_ = b2;
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
            if (!this.getMaxSdkBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.maxSdk_);
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
            if (!this.getMaxSdkBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.maxSdk_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ManifestMaxSdkInvalidError)) {
                return super.equals(obj);
            }
            ManifestMaxSdkInvalidError other = (ManifestMaxSdkInvalidError)obj;
            boolean result = true;
            result = result && this.getMaxSdk().equals(other.getMaxSdk());
            result = result && this.unknownFields.equals(other.unknownFields);
            return result;
        }

        @Override
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + ManifestMaxSdkInvalidError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getMaxSdk().hashCode();
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static ManifestMaxSdkInvalidError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMaxSdkInvalidError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMaxSdkInvalidError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMaxSdkInvalidError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMaxSdkInvalidError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static ManifestMaxSdkInvalidError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static ManifestMaxSdkInvalidError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestMaxSdkInvalidError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestMaxSdkInvalidError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ManifestMaxSdkInvalidError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ManifestMaxSdkInvalidError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ManifestMaxSdkInvalidError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return ManifestMaxSdkInvalidError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(ManifestMaxSdkInvalidError prototype) {
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

        public static ManifestMaxSdkInvalidError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ManifestMaxSdkInvalidError> parser() {
            return PARSER;
        }

        public Parser<ManifestMaxSdkInvalidError> getParserForType() {
            return PARSER;
        }

        @Override
        public ManifestMaxSdkInvalidError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements ManifestMaxSdkInvalidErrorOrBuilder {
            private Object maxSdk_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_ManifestMaxSdkInvalidError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_ManifestMaxSdkInvalidError_fieldAccessorTable.ensureFieldAccessorsInitialized(ManifestMaxSdkInvalidError.class, Builder.class);
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
                this.maxSdk_ = "";
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_ManifestMaxSdkInvalidError_descriptor;
            }

            @Override
            public ManifestMaxSdkInvalidError getDefaultInstanceForType() {
                return ManifestMaxSdkInvalidError.getDefaultInstance();
            }

            @Override
            public ManifestMaxSdkInvalidError build() {
                ManifestMaxSdkInvalidError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public ManifestMaxSdkInvalidError buildPartial() {
                ManifestMaxSdkInvalidError result = new ManifestMaxSdkInvalidError(this);
                result.maxSdk_ = this.maxSdk_;
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
                if (other instanceof ManifestMaxSdkInvalidError) {
                    return this.mergeFrom((ManifestMaxSdkInvalidError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ManifestMaxSdkInvalidError other) {
                if (other == ManifestMaxSdkInvalidError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getMaxSdk().isEmpty()) {
                    this.maxSdk_ = other.maxSdk_;
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
                ManifestMaxSdkInvalidError parsedMessage = null;
                try {
                    parsedMessage = (ManifestMaxSdkInvalidError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (ManifestMaxSdkInvalidError)e2.getUnfinishedMessage();
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
            public String getMaxSdk() {
                Object ref = this.maxSdk_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.maxSdk_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getMaxSdkBytes() {
                Object ref = this.maxSdk_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.maxSdk_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setMaxSdk(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.maxSdk_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMaxSdk() {
                this.maxSdk_ = ManifestMaxSdkInvalidError.getDefaultInstance().getMaxSdk();
                this.onChanged();
                return this;
            }

            public Builder setMaxSdkBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ManifestMaxSdkInvalidError.checkByteStringIsUtf8(value);
                this.maxSdk_ = value;
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

    public static interface ManifestMaxSdkInvalidErrorOrBuilder
    extends MessageOrBuilder {
        public String getMaxSdk();

        public ByteString getMaxSdkBytes();
    }

    public static final class BundleToolError
    extends GeneratedMessageV3
    implements BundleToolErrorOrBuilder {
        private static final long serialVersionUID = 0L;
        private int customErrorCase_ = 0;
        private Object customError_;
        public static final int EXCEPTION_MESSAGE_FIELD_NUMBER = 1;
        private volatile Object exceptionMessage_;
        public static final int MANIFEST_MISSING_VERSION_CODE_FIELD_NUMBER = 2;
        public static final int MANIFEST_INVALID_VERSION_CODE_FIELD_NUMBER = 3;
        public static final int MANIFEST_FUSING_BASE_MODULE_EXCLUDED_FIELD_NUMBER = 4;
        public static final int MANIFEST_FUSING_CONFIGURATION_MISSING_FIELD_NUMBER = 5;
        public static final int MANIFEST_FUSING_MISSING_INCLUDE_ATTRIBUTE_FIELD_NUMBER = 21;
        public static final int MANIFEST_MAX_SDK_INVALID_FIELD_NUMBER = 22;
        public static final int MANIFEST_MAX_SDK_LESS_THAN_MIN_INSTANT_SDK_FIELD_NUMBER = 23;
        public static final int MANIFEST_MIN_SDK_INVALID_FIELD_NUMBER = 19;
        public static final int MANIFEST_MIN_SDK_GREATER_THAN_MAX_FIELD_NUMBER = 20;
        public static final int MANIFEST_DUPLICATE_ATTRIBUTE_FIELD_NUMBER = 25;
        public static final int MANIFEST_MODULES_DIFFERENT_VERSION_CODES_FIELD_NUMBER = 27;
        public static final int FILE_TYPE_INVALID_FILE_EXTENSION_FIELD_NUMBER = 6;
        public static final int FILE_TYPE_INVALID_FILE_NAME_FIELD_NUMBER = 7;
        public static final int FILE_TYPE_INVALID_NATIVE_LIBRARY_PATH_FIELD_NUMBER = 8;
        public static final int FILE_TYPE_INVALID_NATIVE_ARCHITECTURE_FIELD_NUMBER = 9;
        public static final int FILE_TYPE_FILE_IN_RESOURCE_DIRECTORY_ROOT_FIELD_NUMBER = 10;
        public static final int FILE_TYPE_UNKNOWN_FILE_OR_DIRECTORY_IN_MODULE_FIELD_NUMBER = 11;
        public static final int FILE_TYPE_FILE_USES_RESERVED_NAME_FIELD_NUMBER = 12;
        public static final int FILE_TYPE_DIRECTORY_IN_BUNDLE_FIELD_NUMBER = 18;
        public static final int FILE_TYPE_INVALID_APEX_IMAGE_PATH_FIELD_NUMBER = 26;
        public static final int MANDATORY_BUNDLE_FILE_MISSING_FIELD_NUMBER = 14;
        public static final int MANDATORY_MODULE_FILE_MISSING_FIELD_NUMBER = 13;
        public static final int RESOURCE_TABLE_REFERENCES_FILES_OUTSIDE_RES_FIELD_NUMBER = 15;
        public static final int RESOUCE_TABLE_REFERENCES_MISSING_FILES_FIELD_NUMBER = 16;
        public static final int RESOUCE_TABLE_UNREFERENCED_FILES_FIELD_NUMBER = 17;
        public static final int RESOURCE_TABLE_MISSING_FIELD_NUMBER = 24;
        private byte memoizedIsInitialized = (byte)-1;
        private static final BundleToolError DEFAULT_INSTANCE = new BundleToolError();
        private static final Parser<BundleToolError> PARSER = new AbstractParser<BundleToolError>(){

            @Override
            public BundleToolError parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new BundleToolError(input, extensionRegistry);
            }
        };

        private BundleToolError(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private BundleToolError() {
            this.exceptionMessage_ = "";
        }

        @Override
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private BundleToolError(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block36: while (!done) {
                    GeneratedMessageV3.Builder subBuilder;
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block36;
                        }
                        default: {
                            if (this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) continue block36;
                            done = true;
                            continue block36;
                        }
                        case 10: {
                            String s3 = input.readStringRequireUtf8();
                            this.exceptionMessage_ = s3;
                            continue block36;
                        }
                        case 18: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 2) {
                                subBuilder = ((ManifestMissingVersionCodeError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ManifestMissingVersionCodeError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ManifestMissingVersionCodeError.Builder)subBuilder).mergeFrom((ManifestMissingVersionCodeError)this.customError_);
                                this.customError_ = ((ManifestMissingVersionCodeError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 2;
                            continue block36;
                        }
                        case 26: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 3) {
                                subBuilder = ((ManifestInvalidVersionCodeError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ManifestInvalidVersionCodeError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ManifestInvalidVersionCodeError.Builder)subBuilder).mergeFrom((ManifestInvalidVersionCodeError)this.customError_);
                                this.customError_ = ((ManifestInvalidVersionCodeError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 3;
                            continue block36;
                        }
                        case 34: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 4) {
                                subBuilder = ((ManifestBaseModuleExcludedFromFusingError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ManifestBaseModuleExcludedFromFusingError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ManifestBaseModuleExcludedFromFusingError.Builder)subBuilder).mergeFrom((ManifestBaseModuleExcludedFromFusingError)this.customError_);
                                this.customError_ = ((ManifestBaseModuleExcludedFromFusingError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 4;
                            continue block36;
                        }
                        case 42: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 5) {
                                subBuilder = ((ManifestModuleFusingConfigurationMissingError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ManifestModuleFusingConfigurationMissingError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ManifestModuleFusingConfigurationMissingError.Builder)subBuilder).mergeFrom((ManifestModuleFusingConfigurationMissingError)this.customError_);
                                this.customError_ = ((ManifestModuleFusingConfigurationMissingError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 5;
                            continue block36;
                        }
                        case 50: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 6) {
                                subBuilder = ((FileTypeInvalidFileExtensionError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(FileTypeInvalidFileExtensionError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((FileTypeInvalidFileExtensionError.Builder)subBuilder).mergeFrom((FileTypeInvalidFileExtensionError)this.customError_);
                                this.customError_ = ((FileTypeInvalidFileExtensionError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 6;
                            continue block36;
                        }
                        case 58: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 7) {
                                subBuilder = ((FileTypeInvalidFileNameInDirectoryError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(FileTypeInvalidFileNameInDirectoryError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((FileTypeInvalidFileNameInDirectoryError.Builder)subBuilder).mergeFrom((FileTypeInvalidFileNameInDirectoryError)this.customError_);
                                this.customError_ = ((FileTypeInvalidFileNameInDirectoryError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 7;
                            continue block36;
                        }
                        case 66: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 8) {
                                subBuilder = ((FileTypeInvalidNativeLibraryPathError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(FileTypeInvalidNativeLibraryPathError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((FileTypeInvalidNativeLibraryPathError.Builder)subBuilder).mergeFrom((FileTypeInvalidNativeLibraryPathError)this.customError_);
                                this.customError_ = ((FileTypeInvalidNativeLibraryPathError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 8;
                            continue block36;
                        }
                        case 74: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 9) {
                                subBuilder = ((FileTypeInvalidNativeArchitectureError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(FileTypeInvalidNativeArchitectureError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((FileTypeInvalidNativeArchitectureError.Builder)subBuilder).mergeFrom((FileTypeInvalidNativeArchitectureError)this.customError_);
                                this.customError_ = ((FileTypeInvalidNativeArchitectureError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 9;
                            continue block36;
                        }
                        case 82: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 10) {
                                subBuilder = ((FileTypeFilesInResourceDirectoryRootError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(FileTypeFilesInResourceDirectoryRootError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((FileTypeFilesInResourceDirectoryRootError.Builder)subBuilder).mergeFrom((FileTypeFilesInResourceDirectoryRootError)this.customError_);
                                this.customError_ = ((FileTypeFilesInResourceDirectoryRootError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 10;
                            continue block36;
                        }
                        case 90: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 11) {
                                subBuilder = ((FileTypeUnknownFileOrDirectoryFoundInModuleError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(FileTypeUnknownFileOrDirectoryFoundInModuleError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((FileTypeUnknownFileOrDirectoryFoundInModuleError.Builder)subBuilder).mergeFrom((FileTypeUnknownFileOrDirectoryFoundInModuleError)this.customError_);
                                this.customError_ = ((FileTypeUnknownFileOrDirectoryFoundInModuleError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 11;
                            continue block36;
                        }
                        case 98: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 12) {
                                subBuilder = ((FileTypeFileUsesReservedNameError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(FileTypeFileUsesReservedNameError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((FileTypeFileUsesReservedNameError.Builder)subBuilder).mergeFrom((FileTypeFileUsesReservedNameError)this.customError_);
                                this.customError_ = ((FileTypeFileUsesReservedNameError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 12;
                            continue block36;
                        }
                        case 106: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 13) {
                                subBuilder = ((MandatoryModuleFileMissingError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(MandatoryModuleFileMissingError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((MandatoryModuleFileMissingError.Builder)subBuilder).mergeFrom((MandatoryModuleFileMissingError)this.customError_);
                                this.customError_ = ((MandatoryModuleFileMissingError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 13;
                            continue block36;
                        }
                        case 114: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 14) {
                                subBuilder = ((MandatoryBundleFileMissingError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(MandatoryBundleFileMissingError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((MandatoryBundleFileMissingError.Builder)subBuilder).mergeFrom((MandatoryBundleFileMissingError)this.customError_);
                                this.customError_ = ((MandatoryBundleFileMissingError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 14;
                            continue block36;
                        }
                        case 122: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 15) {
                                subBuilder = ((ResourceTableReferencesFilesOutsideResError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ResourceTableReferencesFilesOutsideResError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ResourceTableReferencesFilesOutsideResError.Builder)subBuilder).mergeFrom((ResourceTableReferencesFilesOutsideResError)this.customError_);
                                this.customError_ = ((ResourceTableReferencesFilesOutsideResError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 15;
                            continue block36;
                        }
                        case 130: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 16) {
                                subBuilder = ((ResourceTableReferencesMissingFilesError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ResourceTableReferencesMissingFilesError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ResourceTableReferencesMissingFilesError.Builder)subBuilder).mergeFrom((ResourceTableReferencesMissingFilesError)this.customError_);
                                this.customError_ = ((ResourceTableReferencesMissingFilesError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 16;
                            continue block36;
                        }
                        case 138: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 17) {
                                subBuilder = ((ResourceTableUnreferencedFilesError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ResourceTableUnreferencedFilesError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ResourceTableUnreferencedFilesError.Builder)subBuilder).mergeFrom((ResourceTableUnreferencedFilesError)this.customError_);
                                this.customError_ = ((ResourceTableUnreferencedFilesError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 17;
                            continue block36;
                        }
                        case 146: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 18) {
                                subBuilder = ((FileTypeDirectoryInBundleError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(FileTypeDirectoryInBundleError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((FileTypeDirectoryInBundleError.Builder)subBuilder).mergeFrom((FileTypeDirectoryInBundleError)this.customError_);
                                this.customError_ = ((FileTypeDirectoryInBundleError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 18;
                            continue block36;
                        }
                        case 154: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 19) {
                                subBuilder = ((ManifestMinSdkInvalidError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ManifestMinSdkInvalidError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ManifestMinSdkInvalidError.Builder)subBuilder).mergeFrom((ManifestMinSdkInvalidError)this.customError_);
                                this.customError_ = ((ManifestMinSdkInvalidError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 19;
                            continue block36;
                        }
                        case 162: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 20) {
                                subBuilder = ((ManifestMinSdkGreaterThanMaxSdkError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ManifestMinSdkGreaterThanMaxSdkError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ManifestMinSdkGreaterThanMaxSdkError.Builder)subBuilder).mergeFrom((ManifestMinSdkGreaterThanMaxSdkError)this.customError_);
                                this.customError_ = ((ManifestMinSdkGreaterThanMaxSdkError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 20;
                            continue block36;
                        }
                        case 170: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 21) {
                                subBuilder = ((ManifestFusingMissingIncludeAttributeError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ManifestFusingMissingIncludeAttributeError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ManifestFusingMissingIncludeAttributeError.Builder)subBuilder).mergeFrom((ManifestFusingMissingIncludeAttributeError)this.customError_);
                                this.customError_ = ((ManifestFusingMissingIncludeAttributeError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 21;
                            continue block36;
                        }
                        case 178: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 22) {
                                subBuilder = ((ManifestMaxSdkInvalidError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ManifestMaxSdkInvalidError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ManifestMaxSdkInvalidError.Builder)subBuilder).mergeFrom((ManifestMaxSdkInvalidError)this.customError_);
                                this.customError_ = ((ManifestMaxSdkInvalidError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 22;
                            continue block36;
                        }
                        case 186: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 23) {
                                subBuilder = ((ManifestMaxSdkLessThanMinInstantSdkError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ManifestMaxSdkLessThanMinInstantSdkError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ManifestMaxSdkLessThanMinInstantSdkError.Builder)subBuilder).mergeFrom((ManifestMaxSdkLessThanMinInstantSdkError)this.customError_);
                                this.customError_ = ((ManifestMaxSdkLessThanMinInstantSdkError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 23;
                            continue block36;
                        }
                        case 194: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 24) {
                                subBuilder = ((ResourceTableMissingError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ResourceTableMissingError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ResourceTableMissingError.Builder)subBuilder).mergeFrom((ResourceTableMissingError)this.customError_);
                                this.customError_ = ((ResourceTableMissingError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 24;
                            continue block36;
                        }
                        case 202: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 25) {
                                subBuilder = ((ManifestDuplicateAttributeError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(ManifestDuplicateAttributeError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((ManifestDuplicateAttributeError.Builder)subBuilder).mergeFrom((ManifestDuplicateAttributeError)this.customError_);
                                this.customError_ = ((ManifestDuplicateAttributeError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 25;
                            continue block36;
                        }
                        case 210: {
                            subBuilder = null;
                            if (this.customErrorCase_ == 26) {
                                subBuilder = ((FileTypeInvalidApexImagePathError)this.customError_).toBuilder();
                            }
                            this.customError_ = input.readMessage(FileTypeInvalidApexImagePathError.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                ((FileTypeInvalidApexImagePathError.Builder)subBuilder).mergeFrom((FileTypeInvalidApexImagePathError)this.customError_);
                                this.customError_ = ((FileTypeInvalidApexImagePathError.Builder)subBuilder).buildPartial();
                            }
                            this.customErrorCase_ = 26;
                            continue block36;
                        }
                        case 218: 
                    }
                    subBuilder = null;
                    if (this.customErrorCase_ == 27) {
                        subBuilder = ((ManifestModulesDifferentVersionCodes)this.customError_).toBuilder();
                    }
                    this.customError_ = input.readMessage(ManifestModulesDifferentVersionCodes.parser(), extensionRegistry);
                    if (subBuilder != null) {
                        ((ManifestModulesDifferentVersionCodes.Builder)subBuilder).mergeFrom((ManifestModulesDifferentVersionCodes)this.customError_);
                        this.customError_ = ((ManifestModulesDifferentVersionCodes.Builder)subBuilder).buildPartial();
                    }
                    this.customErrorCase_ = 27;
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
            return internal_static_android_bundle_BundleToolError_descriptor;
        }

        @Override
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_android_bundle_BundleToolError_fieldAccessorTable.ensureFieldAccessorsInitialized(BundleToolError.class, Builder.class);
        }

        @Override
        public CustomErrorCase getCustomErrorCase() {
            return CustomErrorCase.forNumber(this.customErrorCase_);
        }

        @Override
        public String getExceptionMessage() {
            Object ref = this.exceptionMessage_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s3 = bs.toStringUtf8();
            this.exceptionMessage_ = s3;
            return s3;
        }

        @Override
        public ByteString getExceptionMessageBytes() {
            Object ref = this.exceptionMessage_;
            if (ref instanceof String) {
                ByteString b2 = ByteString.copyFromUtf8((String)ref);
                this.exceptionMessage_ = b2;
                return b2;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasManifestMissingVersionCode() {
            return this.customErrorCase_ == 2;
        }

        @Override
        public ManifestMissingVersionCodeError getManifestMissingVersionCode() {
            if (this.customErrorCase_ == 2) {
                return (ManifestMissingVersionCodeError)this.customError_;
            }
            return ManifestMissingVersionCodeError.getDefaultInstance();
        }

        @Override
        public ManifestMissingVersionCodeErrorOrBuilder getManifestMissingVersionCodeOrBuilder() {
            if (this.customErrorCase_ == 2) {
                return (ManifestMissingVersionCodeError)this.customError_;
            }
            return ManifestMissingVersionCodeError.getDefaultInstance();
        }

        @Override
        public boolean hasManifestInvalidVersionCode() {
            return this.customErrorCase_ == 3;
        }

        @Override
        public ManifestInvalidVersionCodeError getManifestInvalidVersionCode() {
            if (this.customErrorCase_ == 3) {
                return (ManifestInvalidVersionCodeError)this.customError_;
            }
            return ManifestInvalidVersionCodeError.getDefaultInstance();
        }

        @Override
        public ManifestInvalidVersionCodeErrorOrBuilder getManifestInvalidVersionCodeOrBuilder() {
            if (this.customErrorCase_ == 3) {
                return (ManifestInvalidVersionCodeError)this.customError_;
            }
            return ManifestInvalidVersionCodeError.getDefaultInstance();
        }

        @Override
        public boolean hasManifestFusingBaseModuleExcluded() {
            return this.customErrorCase_ == 4;
        }

        @Override
        public ManifestBaseModuleExcludedFromFusingError getManifestFusingBaseModuleExcluded() {
            if (this.customErrorCase_ == 4) {
                return (ManifestBaseModuleExcludedFromFusingError)this.customError_;
            }
            return ManifestBaseModuleExcludedFromFusingError.getDefaultInstance();
        }

        @Override
        public ManifestBaseModuleExcludedFromFusingErrorOrBuilder getManifestFusingBaseModuleExcludedOrBuilder() {
            if (this.customErrorCase_ == 4) {
                return (ManifestBaseModuleExcludedFromFusingError)this.customError_;
            }
            return ManifestBaseModuleExcludedFromFusingError.getDefaultInstance();
        }

        @Override
        public boolean hasManifestFusingConfigurationMissing() {
            return this.customErrorCase_ == 5;
        }

        @Override
        public ManifestModuleFusingConfigurationMissingError getManifestFusingConfigurationMissing() {
            if (this.customErrorCase_ == 5) {
                return (ManifestModuleFusingConfigurationMissingError)this.customError_;
            }
            return ManifestModuleFusingConfigurationMissingError.getDefaultInstance();
        }

        @Override
        public ManifestModuleFusingConfigurationMissingErrorOrBuilder getManifestFusingConfigurationMissingOrBuilder() {
            if (this.customErrorCase_ == 5) {
                return (ManifestModuleFusingConfigurationMissingError)this.customError_;
            }
            return ManifestModuleFusingConfigurationMissingError.getDefaultInstance();
        }

        @Override
        public boolean hasManifestFusingMissingIncludeAttribute() {
            return this.customErrorCase_ == 21;
        }

        @Override
        public ManifestFusingMissingIncludeAttributeError getManifestFusingMissingIncludeAttribute() {
            if (this.customErrorCase_ == 21) {
                return (ManifestFusingMissingIncludeAttributeError)this.customError_;
            }
            return ManifestFusingMissingIncludeAttributeError.getDefaultInstance();
        }

        @Override
        public ManifestFusingMissingIncludeAttributeErrorOrBuilder getManifestFusingMissingIncludeAttributeOrBuilder() {
            if (this.customErrorCase_ == 21) {
                return (ManifestFusingMissingIncludeAttributeError)this.customError_;
            }
            return ManifestFusingMissingIncludeAttributeError.getDefaultInstance();
        }

        @Override
        public boolean hasManifestMaxSdkInvalid() {
            return this.customErrorCase_ == 22;
        }

        @Override
        public ManifestMaxSdkInvalidError getManifestMaxSdkInvalid() {
            if (this.customErrorCase_ == 22) {
                return (ManifestMaxSdkInvalidError)this.customError_;
            }
            return ManifestMaxSdkInvalidError.getDefaultInstance();
        }

        @Override
        public ManifestMaxSdkInvalidErrorOrBuilder getManifestMaxSdkInvalidOrBuilder() {
            if (this.customErrorCase_ == 22) {
                return (ManifestMaxSdkInvalidError)this.customError_;
            }
            return ManifestMaxSdkInvalidError.getDefaultInstance();
        }

        @Override
        public boolean hasManifestMaxSdkLessThanMinInstantSdk() {
            return this.customErrorCase_ == 23;
        }

        @Override
        public ManifestMaxSdkLessThanMinInstantSdkError getManifestMaxSdkLessThanMinInstantSdk() {
            if (this.customErrorCase_ == 23) {
                return (ManifestMaxSdkLessThanMinInstantSdkError)this.customError_;
            }
            return ManifestMaxSdkLessThanMinInstantSdkError.getDefaultInstance();
        }

        @Override
        public ManifestMaxSdkLessThanMinInstantSdkErrorOrBuilder getManifestMaxSdkLessThanMinInstantSdkOrBuilder() {
            if (this.customErrorCase_ == 23) {
                return (ManifestMaxSdkLessThanMinInstantSdkError)this.customError_;
            }
            return ManifestMaxSdkLessThanMinInstantSdkError.getDefaultInstance();
        }

        @Override
        public boolean hasManifestMinSdkInvalid() {
            return this.customErrorCase_ == 19;
        }

        @Override
        public ManifestMinSdkInvalidError getManifestMinSdkInvalid() {
            if (this.customErrorCase_ == 19) {
                return (ManifestMinSdkInvalidError)this.customError_;
            }
            return ManifestMinSdkInvalidError.getDefaultInstance();
        }

        @Override
        public ManifestMinSdkInvalidErrorOrBuilder getManifestMinSdkInvalidOrBuilder() {
            if (this.customErrorCase_ == 19) {
                return (ManifestMinSdkInvalidError)this.customError_;
            }
            return ManifestMinSdkInvalidError.getDefaultInstance();
        }

        @Override
        public boolean hasManifestMinSdkGreaterThanMax() {
            return this.customErrorCase_ == 20;
        }

        @Override
        public ManifestMinSdkGreaterThanMaxSdkError getManifestMinSdkGreaterThanMax() {
            if (this.customErrorCase_ == 20) {
                return (ManifestMinSdkGreaterThanMaxSdkError)this.customError_;
            }
            return ManifestMinSdkGreaterThanMaxSdkError.getDefaultInstance();
        }

        @Override
        public ManifestMinSdkGreaterThanMaxSdkErrorOrBuilder getManifestMinSdkGreaterThanMaxOrBuilder() {
            if (this.customErrorCase_ == 20) {
                return (ManifestMinSdkGreaterThanMaxSdkError)this.customError_;
            }
            return ManifestMinSdkGreaterThanMaxSdkError.getDefaultInstance();
        }

        @Override
        public boolean hasManifestDuplicateAttribute() {
            return this.customErrorCase_ == 25;
        }

        @Override
        public ManifestDuplicateAttributeError getManifestDuplicateAttribute() {
            if (this.customErrorCase_ == 25) {
                return (ManifestDuplicateAttributeError)this.customError_;
            }
            return ManifestDuplicateAttributeError.getDefaultInstance();
        }

        @Override
        public ManifestDuplicateAttributeErrorOrBuilder getManifestDuplicateAttributeOrBuilder() {
            if (this.customErrorCase_ == 25) {
                return (ManifestDuplicateAttributeError)this.customError_;
            }
            return ManifestDuplicateAttributeError.getDefaultInstance();
        }

        @Override
        public boolean hasManifestModulesDifferentVersionCodes() {
            return this.customErrorCase_ == 27;
        }

        @Override
        public ManifestModulesDifferentVersionCodes getManifestModulesDifferentVersionCodes() {
            if (this.customErrorCase_ == 27) {
                return (ManifestModulesDifferentVersionCodes)this.customError_;
            }
            return ManifestModulesDifferentVersionCodes.getDefaultInstance();
        }

        @Override
        public ManifestModulesDifferentVersionCodesOrBuilder getManifestModulesDifferentVersionCodesOrBuilder() {
            if (this.customErrorCase_ == 27) {
                return (ManifestModulesDifferentVersionCodes)this.customError_;
            }
            return ManifestModulesDifferentVersionCodes.getDefaultInstance();
        }

        @Override
        public boolean hasFileTypeInvalidFileExtension() {
            return this.customErrorCase_ == 6;
        }

        @Override
        public FileTypeInvalidFileExtensionError getFileTypeInvalidFileExtension() {
            if (this.customErrorCase_ == 6) {
                return (FileTypeInvalidFileExtensionError)this.customError_;
            }
            return FileTypeInvalidFileExtensionError.getDefaultInstance();
        }

        @Override
        public FileTypeInvalidFileExtensionErrorOrBuilder getFileTypeInvalidFileExtensionOrBuilder() {
            if (this.customErrorCase_ == 6) {
                return (FileTypeInvalidFileExtensionError)this.customError_;
            }
            return FileTypeInvalidFileExtensionError.getDefaultInstance();
        }

        @Override
        public boolean hasFileTypeInvalidFileName() {
            return this.customErrorCase_ == 7;
        }

        @Override
        public FileTypeInvalidFileNameInDirectoryError getFileTypeInvalidFileName() {
            if (this.customErrorCase_ == 7) {
                return (FileTypeInvalidFileNameInDirectoryError)this.customError_;
            }
            return FileTypeInvalidFileNameInDirectoryError.getDefaultInstance();
        }

        @Override
        public FileTypeInvalidFileNameInDirectoryErrorOrBuilder getFileTypeInvalidFileNameOrBuilder() {
            if (this.customErrorCase_ == 7) {
                return (FileTypeInvalidFileNameInDirectoryError)this.customError_;
            }
            return FileTypeInvalidFileNameInDirectoryError.getDefaultInstance();
        }

        @Override
        public boolean hasFileTypeInvalidNativeLibraryPath() {
            return this.customErrorCase_ == 8;
        }

        @Override
        public FileTypeInvalidNativeLibraryPathError getFileTypeInvalidNativeLibraryPath() {
            if (this.customErrorCase_ == 8) {
                return (FileTypeInvalidNativeLibraryPathError)this.customError_;
            }
            return FileTypeInvalidNativeLibraryPathError.getDefaultInstance();
        }

        @Override
        public FileTypeInvalidNativeLibraryPathErrorOrBuilder getFileTypeInvalidNativeLibraryPathOrBuilder() {
            if (this.customErrorCase_ == 8) {
                return (FileTypeInvalidNativeLibraryPathError)this.customError_;
            }
            return FileTypeInvalidNativeLibraryPathError.getDefaultInstance();
        }

        @Override
        public boolean hasFileTypeInvalidNativeArchitecture() {
            return this.customErrorCase_ == 9;
        }

        @Override
        public FileTypeInvalidNativeArchitectureError getFileTypeInvalidNativeArchitecture() {
            if (this.customErrorCase_ == 9) {
                return (FileTypeInvalidNativeArchitectureError)this.customError_;
            }
            return FileTypeInvalidNativeArchitectureError.getDefaultInstance();
        }

        @Override
        public FileTypeInvalidNativeArchitectureErrorOrBuilder getFileTypeInvalidNativeArchitectureOrBuilder() {
            if (this.customErrorCase_ == 9) {
                return (FileTypeInvalidNativeArchitectureError)this.customError_;
            }
            return FileTypeInvalidNativeArchitectureError.getDefaultInstance();
        }

        @Override
        public boolean hasFileTypeFileInResourceDirectoryRoot() {
            return this.customErrorCase_ == 10;
        }

        @Override
        public FileTypeFilesInResourceDirectoryRootError getFileTypeFileInResourceDirectoryRoot() {
            if (this.customErrorCase_ == 10) {
                return (FileTypeFilesInResourceDirectoryRootError)this.customError_;
            }
            return FileTypeFilesInResourceDirectoryRootError.getDefaultInstance();
        }

        @Override
        public FileTypeFilesInResourceDirectoryRootErrorOrBuilder getFileTypeFileInResourceDirectoryRootOrBuilder() {
            if (this.customErrorCase_ == 10) {
                return (FileTypeFilesInResourceDirectoryRootError)this.customError_;
            }
            return FileTypeFilesInResourceDirectoryRootError.getDefaultInstance();
        }

        @Override
        public boolean hasFileTypeUnknownFileOrDirectoryInModule() {
            return this.customErrorCase_ == 11;
        }

        @Override
        public FileTypeUnknownFileOrDirectoryFoundInModuleError getFileTypeUnknownFileOrDirectoryInModule() {
            if (this.customErrorCase_ == 11) {
                return (FileTypeUnknownFileOrDirectoryFoundInModuleError)this.customError_;
            }
            return FileTypeUnknownFileOrDirectoryFoundInModuleError.getDefaultInstance();
        }

        @Override
        public FileTypeUnknownFileOrDirectoryFoundInModuleErrorOrBuilder getFileTypeUnknownFileOrDirectoryInModuleOrBuilder() {
            if (this.customErrorCase_ == 11) {
                return (FileTypeUnknownFileOrDirectoryFoundInModuleError)this.customError_;
            }
            return FileTypeUnknownFileOrDirectoryFoundInModuleError.getDefaultInstance();
        }

        @Override
        public boolean hasFileTypeFileUsesReservedName() {
            return this.customErrorCase_ == 12;
        }

        @Override
        public FileTypeFileUsesReservedNameError getFileTypeFileUsesReservedName() {
            if (this.customErrorCase_ == 12) {
                return (FileTypeFileUsesReservedNameError)this.customError_;
            }
            return FileTypeFileUsesReservedNameError.getDefaultInstance();
        }

        @Override
        public FileTypeFileUsesReservedNameErrorOrBuilder getFileTypeFileUsesReservedNameOrBuilder() {
            if (this.customErrorCase_ == 12) {
                return (FileTypeFileUsesReservedNameError)this.customError_;
            }
            return FileTypeFileUsesReservedNameError.getDefaultInstance();
        }

        @Override
        public boolean hasFileTypeDirectoryInBundle() {
            return this.customErrorCase_ == 18;
        }

        @Override
        public FileTypeDirectoryInBundleError getFileTypeDirectoryInBundle() {
            if (this.customErrorCase_ == 18) {
                return (FileTypeDirectoryInBundleError)this.customError_;
            }
            return FileTypeDirectoryInBundleError.getDefaultInstance();
        }

        @Override
        public FileTypeDirectoryInBundleErrorOrBuilder getFileTypeDirectoryInBundleOrBuilder() {
            if (this.customErrorCase_ == 18) {
                return (FileTypeDirectoryInBundleError)this.customError_;
            }
            return FileTypeDirectoryInBundleError.getDefaultInstance();
        }

        @Override
        public boolean hasFileTypeInvalidApexImagePath() {
            return this.customErrorCase_ == 26;
        }

        @Override
        public FileTypeInvalidApexImagePathError getFileTypeInvalidApexImagePath() {
            if (this.customErrorCase_ == 26) {
                return (FileTypeInvalidApexImagePathError)this.customError_;
            }
            return FileTypeInvalidApexImagePathError.getDefaultInstance();
        }

        @Override
        public FileTypeInvalidApexImagePathErrorOrBuilder getFileTypeInvalidApexImagePathOrBuilder() {
            if (this.customErrorCase_ == 26) {
                return (FileTypeInvalidApexImagePathError)this.customError_;
            }
            return FileTypeInvalidApexImagePathError.getDefaultInstance();
        }

        @Override
        public boolean hasMandatoryBundleFileMissing() {
            return this.customErrorCase_ == 14;
        }

        @Override
        public MandatoryBundleFileMissingError getMandatoryBundleFileMissing() {
            if (this.customErrorCase_ == 14) {
                return (MandatoryBundleFileMissingError)this.customError_;
            }
            return MandatoryBundleFileMissingError.getDefaultInstance();
        }

        @Override
        public MandatoryBundleFileMissingErrorOrBuilder getMandatoryBundleFileMissingOrBuilder() {
            if (this.customErrorCase_ == 14) {
                return (MandatoryBundleFileMissingError)this.customError_;
            }
            return MandatoryBundleFileMissingError.getDefaultInstance();
        }

        @Override
        public boolean hasMandatoryModuleFileMissing() {
            return this.customErrorCase_ == 13;
        }

        @Override
        public MandatoryModuleFileMissingError getMandatoryModuleFileMissing() {
            if (this.customErrorCase_ == 13) {
                return (MandatoryModuleFileMissingError)this.customError_;
            }
            return MandatoryModuleFileMissingError.getDefaultInstance();
        }

        @Override
        public MandatoryModuleFileMissingErrorOrBuilder getMandatoryModuleFileMissingOrBuilder() {
            if (this.customErrorCase_ == 13) {
                return (MandatoryModuleFileMissingError)this.customError_;
            }
            return MandatoryModuleFileMissingError.getDefaultInstance();
        }

        @Override
        public boolean hasResourceTableReferencesFilesOutsideRes() {
            return this.customErrorCase_ == 15;
        }

        @Override
        public ResourceTableReferencesFilesOutsideResError getResourceTableReferencesFilesOutsideRes() {
            if (this.customErrorCase_ == 15) {
                return (ResourceTableReferencesFilesOutsideResError)this.customError_;
            }
            return ResourceTableReferencesFilesOutsideResError.getDefaultInstance();
        }

        @Override
        public ResourceTableReferencesFilesOutsideResErrorOrBuilder getResourceTableReferencesFilesOutsideResOrBuilder() {
            if (this.customErrorCase_ == 15) {
                return (ResourceTableReferencesFilesOutsideResError)this.customError_;
            }
            return ResourceTableReferencesFilesOutsideResError.getDefaultInstance();
        }

        @Override
        public boolean hasResouceTableReferencesMissingFiles() {
            return this.customErrorCase_ == 16;
        }

        @Override
        public ResourceTableReferencesMissingFilesError getResouceTableReferencesMissingFiles() {
            if (this.customErrorCase_ == 16) {
                return (ResourceTableReferencesMissingFilesError)this.customError_;
            }
            return ResourceTableReferencesMissingFilesError.getDefaultInstance();
        }

        @Override
        public ResourceTableReferencesMissingFilesErrorOrBuilder getResouceTableReferencesMissingFilesOrBuilder() {
            if (this.customErrorCase_ == 16) {
                return (ResourceTableReferencesMissingFilesError)this.customError_;
            }
            return ResourceTableReferencesMissingFilesError.getDefaultInstance();
        }

        @Override
        public boolean hasResouceTableUnreferencedFiles() {
            return this.customErrorCase_ == 17;
        }

        @Override
        public ResourceTableUnreferencedFilesError getResouceTableUnreferencedFiles() {
            if (this.customErrorCase_ == 17) {
                return (ResourceTableUnreferencedFilesError)this.customError_;
            }
            return ResourceTableUnreferencedFilesError.getDefaultInstance();
        }

        @Override
        public ResourceTableUnreferencedFilesErrorOrBuilder getResouceTableUnreferencedFilesOrBuilder() {
            if (this.customErrorCase_ == 17) {
                return (ResourceTableUnreferencedFilesError)this.customError_;
            }
            return ResourceTableUnreferencedFilesError.getDefaultInstance();
        }

        @Override
        public boolean hasResourceTableMissing() {
            return this.customErrorCase_ == 24;
        }

        @Override
        public ResourceTableMissingError getResourceTableMissing() {
            if (this.customErrorCase_ == 24) {
                return (ResourceTableMissingError)this.customError_;
            }
            return ResourceTableMissingError.getDefaultInstance();
        }

        @Override
        public ResourceTableMissingErrorOrBuilder getResourceTableMissingOrBuilder() {
            if (this.customErrorCase_ == 24) {
                return (ResourceTableMissingError)this.customError_;
            }
            return ResourceTableMissingError.getDefaultInstance();
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
            if (!this.getExceptionMessageBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.exceptionMessage_);
            }
            if (this.customErrorCase_ == 2) {
                output.writeMessage(2, (ManifestMissingVersionCodeError)this.customError_);
            }
            if (this.customErrorCase_ == 3) {
                output.writeMessage(3, (ManifestInvalidVersionCodeError)this.customError_);
            }
            if (this.customErrorCase_ == 4) {
                output.writeMessage(4, (ManifestBaseModuleExcludedFromFusingError)this.customError_);
            }
            if (this.customErrorCase_ == 5) {
                output.writeMessage(5, (ManifestModuleFusingConfigurationMissingError)this.customError_);
            }
            if (this.customErrorCase_ == 6) {
                output.writeMessage(6, (FileTypeInvalidFileExtensionError)this.customError_);
            }
            if (this.customErrorCase_ == 7) {
                output.writeMessage(7, (FileTypeInvalidFileNameInDirectoryError)this.customError_);
            }
            if (this.customErrorCase_ == 8) {
                output.writeMessage(8, (FileTypeInvalidNativeLibraryPathError)this.customError_);
            }
            if (this.customErrorCase_ == 9) {
                output.writeMessage(9, (FileTypeInvalidNativeArchitectureError)this.customError_);
            }
            if (this.customErrorCase_ == 10) {
                output.writeMessage(10, (FileTypeFilesInResourceDirectoryRootError)this.customError_);
            }
            if (this.customErrorCase_ == 11) {
                output.writeMessage(11, (FileTypeUnknownFileOrDirectoryFoundInModuleError)this.customError_);
            }
            if (this.customErrorCase_ == 12) {
                output.writeMessage(12, (FileTypeFileUsesReservedNameError)this.customError_);
            }
            if (this.customErrorCase_ == 13) {
                output.writeMessage(13, (MandatoryModuleFileMissingError)this.customError_);
            }
            if (this.customErrorCase_ == 14) {
                output.writeMessage(14, (MandatoryBundleFileMissingError)this.customError_);
            }
            if (this.customErrorCase_ == 15) {
                output.writeMessage(15, (ResourceTableReferencesFilesOutsideResError)this.customError_);
            }
            if (this.customErrorCase_ == 16) {
                output.writeMessage(16, (ResourceTableReferencesMissingFilesError)this.customError_);
            }
            if (this.customErrorCase_ == 17) {
                output.writeMessage(17, (ResourceTableUnreferencedFilesError)this.customError_);
            }
            if (this.customErrorCase_ == 18) {
                output.writeMessage(18, (FileTypeDirectoryInBundleError)this.customError_);
            }
            if (this.customErrorCase_ == 19) {
                output.writeMessage(19, (ManifestMinSdkInvalidError)this.customError_);
            }
            if (this.customErrorCase_ == 20) {
                output.writeMessage(20, (ManifestMinSdkGreaterThanMaxSdkError)this.customError_);
            }
            if (this.customErrorCase_ == 21) {
                output.writeMessage(21, (ManifestFusingMissingIncludeAttributeError)this.customError_);
            }
            if (this.customErrorCase_ == 22) {
                output.writeMessage(22, (ManifestMaxSdkInvalidError)this.customError_);
            }
            if (this.customErrorCase_ == 23) {
                output.writeMessage(23, (ManifestMaxSdkLessThanMinInstantSdkError)this.customError_);
            }
            if (this.customErrorCase_ == 24) {
                output.writeMessage(24, (ResourceTableMissingError)this.customError_);
            }
            if (this.customErrorCase_ == 25) {
                output.writeMessage(25, (ManifestDuplicateAttributeError)this.customError_);
            }
            if (this.customErrorCase_ == 26) {
                output.writeMessage(26, (FileTypeInvalidApexImagePathError)this.customError_);
            }
            if (this.customErrorCase_ == 27) {
                output.writeMessage(27, (ManifestModulesDifferentVersionCodes)this.customError_);
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
            if (!this.getExceptionMessageBytes().isEmpty()) {
                size += GeneratedMessageV3.computeStringSize(1, this.exceptionMessage_);
            }
            if (this.customErrorCase_ == 2) {
                size += CodedOutputStream.computeMessageSize(2, (ManifestMissingVersionCodeError)this.customError_);
            }
            if (this.customErrorCase_ == 3) {
                size += CodedOutputStream.computeMessageSize(3, (ManifestInvalidVersionCodeError)this.customError_);
            }
            if (this.customErrorCase_ == 4) {
                size += CodedOutputStream.computeMessageSize(4, (ManifestBaseModuleExcludedFromFusingError)this.customError_);
            }
            if (this.customErrorCase_ == 5) {
                size += CodedOutputStream.computeMessageSize(5, (ManifestModuleFusingConfigurationMissingError)this.customError_);
            }
            if (this.customErrorCase_ == 6) {
                size += CodedOutputStream.computeMessageSize(6, (FileTypeInvalidFileExtensionError)this.customError_);
            }
            if (this.customErrorCase_ == 7) {
                size += CodedOutputStream.computeMessageSize(7, (FileTypeInvalidFileNameInDirectoryError)this.customError_);
            }
            if (this.customErrorCase_ == 8) {
                size += CodedOutputStream.computeMessageSize(8, (FileTypeInvalidNativeLibraryPathError)this.customError_);
            }
            if (this.customErrorCase_ == 9) {
                size += CodedOutputStream.computeMessageSize(9, (FileTypeInvalidNativeArchitectureError)this.customError_);
            }
            if (this.customErrorCase_ == 10) {
                size += CodedOutputStream.computeMessageSize(10, (FileTypeFilesInResourceDirectoryRootError)this.customError_);
            }
            if (this.customErrorCase_ == 11) {
                size += CodedOutputStream.computeMessageSize(11, (FileTypeUnknownFileOrDirectoryFoundInModuleError)this.customError_);
            }
            if (this.customErrorCase_ == 12) {
                size += CodedOutputStream.computeMessageSize(12, (FileTypeFileUsesReservedNameError)this.customError_);
            }
            if (this.customErrorCase_ == 13) {
                size += CodedOutputStream.computeMessageSize(13, (MandatoryModuleFileMissingError)this.customError_);
            }
            if (this.customErrorCase_ == 14) {
                size += CodedOutputStream.computeMessageSize(14, (MandatoryBundleFileMissingError)this.customError_);
            }
            if (this.customErrorCase_ == 15) {
                size += CodedOutputStream.computeMessageSize(15, (ResourceTableReferencesFilesOutsideResError)this.customError_);
            }
            if (this.customErrorCase_ == 16) {
                size += CodedOutputStream.computeMessageSize(16, (ResourceTableReferencesMissingFilesError)this.customError_);
            }
            if (this.customErrorCase_ == 17) {
                size += CodedOutputStream.computeMessageSize(17, (ResourceTableUnreferencedFilesError)this.customError_);
            }
            if (this.customErrorCase_ == 18) {
                size += CodedOutputStream.computeMessageSize(18, (FileTypeDirectoryInBundleError)this.customError_);
            }
            if (this.customErrorCase_ == 19) {
                size += CodedOutputStream.computeMessageSize(19, (ManifestMinSdkInvalidError)this.customError_);
            }
            if (this.customErrorCase_ == 20) {
                size += CodedOutputStream.computeMessageSize(20, (ManifestMinSdkGreaterThanMaxSdkError)this.customError_);
            }
            if (this.customErrorCase_ == 21) {
                size += CodedOutputStream.computeMessageSize(21, (ManifestFusingMissingIncludeAttributeError)this.customError_);
            }
            if (this.customErrorCase_ == 22) {
                size += CodedOutputStream.computeMessageSize(22, (ManifestMaxSdkInvalidError)this.customError_);
            }
            if (this.customErrorCase_ == 23) {
                size += CodedOutputStream.computeMessageSize(23, (ManifestMaxSdkLessThanMinInstantSdkError)this.customError_);
            }
            if (this.customErrorCase_ == 24) {
                size += CodedOutputStream.computeMessageSize(24, (ResourceTableMissingError)this.customError_);
            }
            if (this.customErrorCase_ == 25) {
                size += CodedOutputStream.computeMessageSize(25, (ManifestDuplicateAttributeError)this.customError_);
            }
            if (this.customErrorCase_ == 26) {
                size += CodedOutputStream.computeMessageSize(26, (FileTypeInvalidApexImagePathError)this.customError_);
            }
            if (this.customErrorCase_ == 27) {
                size += CodedOutputStream.computeMessageSize(27, (ManifestModulesDifferentVersionCodes)this.customError_);
            }
            this.memoizedSize = size += this.unknownFields.getSerializedSize();
            return size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof BundleToolError)) {
                return super.equals(obj);
            }
            BundleToolError other = (BundleToolError)obj;
            boolean result = true;
            result = result && this.getExceptionMessage().equals(other.getExceptionMessage());
            boolean bl = result = result && this.getCustomErrorCase().equals(other.getCustomErrorCase());
            if (!result) {
                return false;
            }
            switch (this.customErrorCase_) {
                case 2: {
                    result = result && this.getManifestMissingVersionCode().equals(other.getManifestMissingVersionCode());
                    break;
                }
                case 3: {
                    result = result && this.getManifestInvalidVersionCode().equals(other.getManifestInvalidVersionCode());
                    break;
                }
                case 4: {
                    result = result && this.getManifestFusingBaseModuleExcluded().equals(other.getManifestFusingBaseModuleExcluded());
                    break;
                }
                case 5: {
                    result = result && this.getManifestFusingConfigurationMissing().equals(other.getManifestFusingConfigurationMissing());
                    break;
                }
                case 21: {
                    result = result && this.getManifestFusingMissingIncludeAttribute().equals(other.getManifestFusingMissingIncludeAttribute());
                    break;
                }
                case 22: {
                    result = result && this.getManifestMaxSdkInvalid().equals(other.getManifestMaxSdkInvalid());
                    break;
                }
                case 23: {
                    result = result && this.getManifestMaxSdkLessThanMinInstantSdk().equals(other.getManifestMaxSdkLessThanMinInstantSdk());
                    break;
                }
                case 19: {
                    result = result && this.getManifestMinSdkInvalid().equals(other.getManifestMinSdkInvalid());
                    break;
                }
                case 20: {
                    result = result && this.getManifestMinSdkGreaterThanMax().equals(other.getManifestMinSdkGreaterThanMax());
                    break;
                }
                case 25: {
                    result = result && this.getManifestDuplicateAttribute().equals(other.getManifestDuplicateAttribute());
                    break;
                }
                case 27: {
                    result = result && this.getManifestModulesDifferentVersionCodes().equals(other.getManifestModulesDifferentVersionCodes());
                    break;
                }
                case 6: {
                    result = result && this.getFileTypeInvalidFileExtension().equals(other.getFileTypeInvalidFileExtension());
                    break;
                }
                case 7: {
                    result = result && this.getFileTypeInvalidFileName().equals(other.getFileTypeInvalidFileName());
                    break;
                }
                case 8: {
                    result = result && this.getFileTypeInvalidNativeLibraryPath().equals(other.getFileTypeInvalidNativeLibraryPath());
                    break;
                }
                case 9: {
                    result = result && this.getFileTypeInvalidNativeArchitecture().equals(other.getFileTypeInvalidNativeArchitecture());
                    break;
                }
                case 10: {
                    result = result && this.getFileTypeFileInResourceDirectoryRoot().equals(other.getFileTypeFileInResourceDirectoryRoot());
                    break;
                }
                case 11: {
                    result = result && this.getFileTypeUnknownFileOrDirectoryInModule().equals(other.getFileTypeUnknownFileOrDirectoryInModule());
                    break;
                }
                case 12: {
                    result = result && this.getFileTypeFileUsesReservedName().equals(other.getFileTypeFileUsesReservedName());
                    break;
                }
                case 18: {
                    result = result && this.getFileTypeDirectoryInBundle().equals(other.getFileTypeDirectoryInBundle());
                    break;
                }
                case 26: {
                    result = result && this.getFileTypeInvalidApexImagePath().equals(other.getFileTypeInvalidApexImagePath());
                    break;
                }
                case 14: {
                    result = result && this.getMandatoryBundleFileMissing().equals(other.getMandatoryBundleFileMissing());
                    break;
                }
                case 13: {
                    result = result && this.getMandatoryModuleFileMissing().equals(other.getMandatoryModuleFileMissing());
                    break;
                }
                case 15: {
                    result = result && this.getResourceTableReferencesFilesOutsideRes().equals(other.getResourceTableReferencesFilesOutsideRes());
                    break;
                }
                case 16: {
                    result = result && this.getResouceTableReferencesMissingFiles().equals(other.getResouceTableReferencesMissingFiles());
                    break;
                }
                case 17: {
                    result = result && this.getResouceTableUnreferencedFiles().equals(other.getResouceTableUnreferencedFiles());
                    break;
                }
                case 24: {
                    result = result && this.getResourceTableMissing().equals(other.getResourceTableMissing());
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
            hash = 19 * hash + BundleToolError.getDescriptor().hashCode();
            hash = 37 * hash + 1;
            hash = 53 * hash + this.getExceptionMessage().hashCode();
            switch (this.customErrorCase_) {
                case 2: {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getManifestMissingVersionCode().hashCode();
                    break;
                }
                case 3: {
                    hash = 37 * hash + 3;
                    hash = 53 * hash + this.getManifestInvalidVersionCode().hashCode();
                    break;
                }
                case 4: {
                    hash = 37 * hash + 4;
                    hash = 53 * hash + this.getManifestFusingBaseModuleExcluded().hashCode();
                    break;
                }
                case 5: {
                    hash = 37 * hash + 5;
                    hash = 53 * hash + this.getManifestFusingConfigurationMissing().hashCode();
                    break;
                }
                case 21: {
                    hash = 37 * hash + 21;
                    hash = 53 * hash + this.getManifestFusingMissingIncludeAttribute().hashCode();
                    break;
                }
                case 22: {
                    hash = 37 * hash + 22;
                    hash = 53 * hash + this.getManifestMaxSdkInvalid().hashCode();
                    break;
                }
                case 23: {
                    hash = 37 * hash + 23;
                    hash = 53 * hash + this.getManifestMaxSdkLessThanMinInstantSdk().hashCode();
                    break;
                }
                case 19: {
                    hash = 37 * hash + 19;
                    hash = 53 * hash + this.getManifestMinSdkInvalid().hashCode();
                    break;
                }
                case 20: {
                    hash = 37 * hash + 20;
                    hash = 53 * hash + this.getManifestMinSdkGreaterThanMax().hashCode();
                    break;
                }
                case 25: {
                    hash = 37 * hash + 25;
                    hash = 53 * hash + this.getManifestDuplicateAttribute().hashCode();
                    break;
                }
                case 27: {
                    hash = 37 * hash + 27;
                    hash = 53 * hash + this.getManifestModulesDifferentVersionCodes().hashCode();
                    break;
                }
                case 6: {
                    hash = 37 * hash + 6;
                    hash = 53 * hash + this.getFileTypeInvalidFileExtension().hashCode();
                    break;
                }
                case 7: {
                    hash = 37 * hash + 7;
                    hash = 53 * hash + this.getFileTypeInvalidFileName().hashCode();
                    break;
                }
                case 8: {
                    hash = 37 * hash + 8;
                    hash = 53 * hash + this.getFileTypeInvalidNativeLibraryPath().hashCode();
                    break;
                }
                case 9: {
                    hash = 37 * hash + 9;
                    hash = 53 * hash + this.getFileTypeInvalidNativeArchitecture().hashCode();
                    break;
                }
                case 10: {
                    hash = 37 * hash + 10;
                    hash = 53 * hash + this.getFileTypeFileInResourceDirectoryRoot().hashCode();
                    break;
                }
                case 11: {
                    hash = 37 * hash + 11;
                    hash = 53 * hash + this.getFileTypeUnknownFileOrDirectoryInModule().hashCode();
                    break;
                }
                case 12: {
                    hash = 37 * hash + 12;
                    hash = 53 * hash + this.getFileTypeFileUsesReservedName().hashCode();
                    break;
                }
                case 18: {
                    hash = 37 * hash + 18;
                    hash = 53 * hash + this.getFileTypeDirectoryInBundle().hashCode();
                    break;
                }
                case 26: {
                    hash = 37 * hash + 26;
                    hash = 53 * hash + this.getFileTypeInvalidApexImagePath().hashCode();
                    break;
                }
                case 14: {
                    hash = 37 * hash + 14;
                    hash = 53 * hash + this.getMandatoryBundleFileMissing().hashCode();
                    break;
                }
                case 13: {
                    hash = 37 * hash + 13;
                    hash = 53 * hash + this.getMandatoryModuleFileMissing().hashCode();
                    break;
                }
                case 15: {
                    hash = 37 * hash + 15;
                    hash = 53 * hash + this.getResourceTableReferencesFilesOutsideRes().hashCode();
                    break;
                }
                case 16: {
                    hash = 37 * hash + 16;
                    hash = 53 * hash + this.getResouceTableReferencesMissingFiles().hashCode();
                    break;
                }
                case 17: {
                    hash = 37 * hash + 17;
                    hash = 53 * hash + this.getResouceTableUnreferencedFiles().hashCode();
                    break;
                }
                case 24: {
                    hash = 37 * hash + 24;
                    hash = 53 * hash + this.getResourceTableMissing().hashCode();
                    break;
                }
            }
            this.memoizedHashCode = hash = 29 * hash + this.unknownFields.hashCode();
            return hash;
        }

        public static BundleToolError parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BundleToolError parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BundleToolError parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BundleToolError parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BundleToolError parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static BundleToolError parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static BundleToolError parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BundleToolError parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static BundleToolError parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static BundleToolError parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static BundleToolError parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static BundleToolError parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return BundleToolError.newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(BundleToolError prototype) {
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

        public static BundleToolError getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BundleToolError> parser() {
            return PARSER;
        }

        public Parser<BundleToolError> getParserForType() {
            return PARSER;
        }

        @Override
        public BundleToolError getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder
        extends GeneratedMessageV3.Builder<Builder>
        implements BundleToolErrorOrBuilder {
            private int customErrorCase_ = 0;
            private Object customError_;
            private Object exceptionMessage_ = "";
            private SingleFieldBuilderV3<ManifestMissingVersionCodeError, ManifestMissingVersionCodeError.Builder, ManifestMissingVersionCodeErrorOrBuilder> manifestMissingVersionCodeBuilder_;
            private SingleFieldBuilderV3<ManifestInvalidVersionCodeError, ManifestInvalidVersionCodeError.Builder, ManifestInvalidVersionCodeErrorOrBuilder> manifestInvalidVersionCodeBuilder_;
            private SingleFieldBuilderV3<ManifestBaseModuleExcludedFromFusingError, ManifestBaseModuleExcludedFromFusingError.Builder, ManifestBaseModuleExcludedFromFusingErrorOrBuilder> manifestFusingBaseModuleExcludedBuilder_;
            private SingleFieldBuilderV3<ManifestModuleFusingConfigurationMissingError, ManifestModuleFusingConfigurationMissingError.Builder, ManifestModuleFusingConfigurationMissingErrorOrBuilder> manifestFusingConfigurationMissingBuilder_;
            private SingleFieldBuilderV3<ManifestFusingMissingIncludeAttributeError, ManifestFusingMissingIncludeAttributeError.Builder, ManifestFusingMissingIncludeAttributeErrorOrBuilder> manifestFusingMissingIncludeAttributeBuilder_;
            private SingleFieldBuilderV3<ManifestMaxSdkInvalidError, ManifestMaxSdkInvalidError.Builder, ManifestMaxSdkInvalidErrorOrBuilder> manifestMaxSdkInvalidBuilder_;
            private SingleFieldBuilderV3<ManifestMaxSdkLessThanMinInstantSdkError, ManifestMaxSdkLessThanMinInstantSdkError.Builder, ManifestMaxSdkLessThanMinInstantSdkErrorOrBuilder> manifestMaxSdkLessThanMinInstantSdkBuilder_;
            private SingleFieldBuilderV3<ManifestMinSdkInvalidError, ManifestMinSdkInvalidError.Builder, ManifestMinSdkInvalidErrorOrBuilder> manifestMinSdkInvalidBuilder_;
            private SingleFieldBuilderV3<ManifestMinSdkGreaterThanMaxSdkError, ManifestMinSdkGreaterThanMaxSdkError.Builder, ManifestMinSdkGreaterThanMaxSdkErrorOrBuilder> manifestMinSdkGreaterThanMaxBuilder_;
            private SingleFieldBuilderV3<ManifestDuplicateAttributeError, ManifestDuplicateAttributeError.Builder, ManifestDuplicateAttributeErrorOrBuilder> manifestDuplicateAttributeBuilder_;
            private SingleFieldBuilderV3<ManifestModulesDifferentVersionCodes, ManifestModulesDifferentVersionCodes.Builder, ManifestModulesDifferentVersionCodesOrBuilder> manifestModulesDifferentVersionCodesBuilder_;
            private SingleFieldBuilderV3<FileTypeInvalidFileExtensionError, FileTypeInvalidFileExtensionError.Builder, FileTypeInvalidFileExtensionErrorOrBuilder> fileTypeInvalidFileExtensionBuilder_;
            private SingleFieldBuilderV3<FileTypeInvalidFileNameInDirectoryError, FileTypeInvalidFileNameInDirectoryError.Builder, FileTypeInvalidFileNameInDirectoryErrorOrBuilder> fileTypeInvalidFileNameBuilder_;
            private SingleFieldBuilderV3<FileTypeInvalidNativeLibraryPathError, FileTypeInvalidNativeLibraryPathError.Builder, FileTypeInvalidNativeLibraryPathErrorOrBuilder> fileTypeInvalidNativeLibraryPathBuilder_;
            private SingleFieldBuilderV3<FileTypeInvalidNativeArchitectureError, FileTypeInvalidNativeArchitectureError.Builder, FileTypeInvalidNativeArchitectureErrorOrBuilder> fileTypeInvalidNativeArchitectureBuilder_;
            private SingleFieldBuilderV3<FileTypeFilesInResourceDirectoryRootError, FileTypeFilesInResourceDirectoryRootError.Builder, FileTypeFilesInResourceDirectoryRootErrorOrBuilder> fileTypeFileInResourceDirectoryRootBuilder_;
            private SingleFieldBuilderV3<FileTypeUnknownFileOrDirectoryFoundInModuleError, FileTypeUnknownFileOrDirectoryFoundInModuleError.Builder, FileTypeUnknownFileOrDirectoryFoundInModuleErrorOrBuilder> fileTypeUnknownFileOrDirectoryInModuleBuilder_;
            private SingleFieldBuilderV3<FileTypeFileUsesReservedNameError, FileTypeFileUsesReservedNameError.Builder, FileTypeFileUsesReservedNameErrorOrBuilder> fileTypeFileUsesReservedNameBuilder_;
            private SingleFieldBuilderV3<FileTypeDirectoryInBundleError, FileTypeDirectoryInBundleError.Builder, FileTypeDirectoryInBundleErrorOrBuilder> fileTypeDirectoryInBundleBuilder_;
            private SingleFieldBuilderV3<FileTypeInvalidApexImagePathError, FileTypeInvalidApexImagePathError.Builder, FileTypeInvalidApexImagePathErrorOrBuilder> fileTypeInvalidApexImagePathBuilder_;
            private SingleFieldBuilderV3<MandatoryBundleFileMissingError, MandatoryBundleFileMissingError.Builder, MandatoryBundleFileMissingErrorOrBuilder> mandatoryBundleFileMissingBuilder_;
            private SingleFieldBuilderV3<MandatoryModuleFileMissingError, MandatoryModuleFileMissingError.Builder, MandatoryModuleFileMissingErrorOrBuilder> mandatoryModuleFileMissingBuilder_;
            private SingleFieldBuilderV3<ResourceTableReferencesFilesOutsideResError, ResourceTableReferencesFilesOutsideResError.Builder, ResourceTableReferencesFilesOutsideResErrorOrBuilder> resourceTableReferencesFilesOutsideResBuilder_;
            private SingleFieldBuilderV3<ResourceTableReferencesMissingFilesError, ResourceTableReferencesMissingFilesError.Builder, ResourceTableReferencesMissingFilesErrorOrBuilder> resouceTableReferencesMissingFilesBuilder_;
            private SingleFieldBuilderV3<ResourceTableUnreferencedFilesError, ResourceTableUnreferencedFilesError.Builder, ResourceTableUnreferencedFilesErrorOrBuilder> resouceTableUnreferencedFilesBuilder_;
            private SingleFieldBuilderV3<ResourceTableMissingError, ResourceTableMissingError.Builder, ResourceTableMissingErrorOrBuilder> resourceTableMissingBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_android_bundle_BundleToolError_descriptor;
            }

            @Override
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_android_bundle_BundleToolError_fieldAccessorTable.ensureFieldAccessorsInitialized(BundleToolError.class, Builder.class);
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
                this.exceptionMessage_ = "";
                this.customErrorCase_ = 0;
                this.customError_ = null;
                return this;
            }

            @Override
            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_android_bundle_BundleToolError_descriptor;
            }

            @Override
            public BundleToolError getDefaultInstanceForType() {
                return BundleToolError.getDefaultInstance();
            }

            @Override
            public BundleToolError build() {
                BundleToolError result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public BundleToolError buildPartial() {
                BundleToolError result = new BundleToolError(this);
                result.exceptionMessage_ = this.exceptionMessage_;
                if (this.customErrorCase_ == 2) {
                    if (this.manifestMissingVersionCodeBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestMissingVersionCodeBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 3) {
                    if (this.manifestInvalidVersionCodeBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestInvalidVersionCodeBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 4) {
                    if (this.manifestFusingBaseModuleExcludedBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestFusingBaseModuleExcludedBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 5) {
                    if (this.manifestFusingConfigurationMissingBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestFusingConfigurationMissingBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 21) {
                    if (this.manifestFusingMissingIncludeAttributeBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestFusingMissingIncludeAttributeBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 22) {
                    if (this.manifestMaxSdkInvalidBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestMaxSdkInvalidBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 23) {
                    if (this.manifestMaxSdkLessThanMinInstantSdkBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestMaxSdkLessThanMinInstantSdkBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 19) {
                    if (this.manifestMinSdkInvalidBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestMinSdkInvalidBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 20) {
                    if (this.manifestMinSdkGreaterThanMaxBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestMinSdkGreaterThanMaxBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 25) {
                    if (this.manifestDuplicateAttributeBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestDuplicateAttributeBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 27) {
                    if (this.manifestModulesDifferentVersionCodesBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.manifestModulesDifferentVersionCodesBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 6) {
                    if (this.fileTypeInvalidFileExtensionBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.fileTypeInvalidFileExtensionBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 7) {
                    if (this.fileTypeInvalidFileNameBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.fileTypeInvalidFileNameBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 8) {
                    if (this.fileTypeInvalidNativeLibraryPathBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.fileTypeInvalidNativeLibraryPathBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 9) {
                    if (this.fileTypeInvalidNativeArchitectureBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.fileTypeInvalidNativeArchitectureBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 10) {
                    if (this.fileTypeFileInResourceDirectoryRootBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.fileTypeFileInResourceDirectoryRootBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 11) {
                    if (this.fileTypeUnknownFileOrDirectoryInModuleBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.fileTypeUnknownFileOrDirectoryInModuleBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 12) {
                    if (this.fileTypeFileUsesReservedNameBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.fileTypeFileUsesReservedNameBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 18) {
                    if (this.fileTypeDirectoryInBundleBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.fileTypeDirectoryInBundleBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 26) {
                    if (this.fileTypeInvalidApexImagePathBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.fileTypeInvalidApexImagePathBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 14) {
                    if (this.mandatoryBundleFileMissingBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.mandatoryBundleFileMissingBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 13) {
                    if (this.mandatoryModuleFileMissingBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.mandatoryModuleFileMissingBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 15) {
                    if (this.resourceTableReferencesFilesOutsideResBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.resourceTableReferencesFilesOutsideResBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 16) {
                    if (this.resouceTableReferencesMissingFilesBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.resouceTableReferencesMissingFilesBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 17) {
                    if (this.resouceTableUnreferencedFilesBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.resouceTableUnreferencedFilesBuilder_.build();
                    }
                }
                if (this.customErrorCase_ == 24) {
                    if (this.resourceTableMissingBuilder_ == null) {
                        result.customError_ = this.customError_;
                    } else {
                        result.customError_ = this.resourceTableMissingBuilder_.build();
                    }
                }
                result.customErrorCase_ = this.customErrorCase_;
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
                if (other instanceof BundleToolError) {
                    return this.mergeFrom((BundleToolError)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(BundleToolError other) {
                if (other == BundleToolError.getDefaultInstance()) {
                    return this;
                }
                if (!other.getExceptionMessage().isEmpty()) {
                    this.exceptionMessage_ = other.exceptionMessage_;
                    this.onChanged();
                }
                switch (other.getCustomErrorCase()) {
                    case MANIFEST_MISSING_VERSION_CODE: {
                        this.mergeManifestMissingVersionCode(other.getManifestMissingVersionCode());
                        break;
                    }
                    case MANIFEST_INVALID_VERSION_CODE: {
                        this.mergeManifestInvalidVersionCode(other.getManifestInvalidVersionCode());
                        break;
                    }
                    case MANIFEST_FUSING_BASE_MODULE_EXCLUDED: {
                        this.mergeManifestFusingBaseModuleExcluded(other.getManifestFusingBaseModuleExcluded());
                        break;
                    }
                    case MANIFEST_FUSING_CONFIGURATION_MISSING: {
                        this.mergeManifestFusingConfigurationMissing(other.getManifestFusingConfigurationMissing());
                        break;
                    }
                    case MANIFEST_FUSING_MISSING_INCLUDE_ATTRIBUTE: {
                        this.mergeManifestFusingMissingIncludeAttribute(other.getManifestFusingMissingIncludeAttribute());
                        break;
                    }
                    case MANIFEST_MAX_SDK_INVALID: {
                        this.mergeManifestMaxSdkInvalid(other.getManifestMaxSdkInvalid());
                        break;
                    }
                    case MANIFEST_MAX_SDK_LESS_THAN_MIN_INSTANT_SDK: {
                        this.mergeManifestMaxSdkLessThanMinInstantSdk(other.getManifestMaxSdkLessThanMinInstantSdk());
                        break;
                    }
                    case MANIFEST_MIN_SDK_INVALID: {
                        this.mergeManifestMinSdkInvalid(other.getManifestMinSdkInvalid());
                        break;
                    }
                    case MANIFEST_MIN_SDK_GREATER_THAN_MAX: {
                        this.mergeManifestMinSdkGreaterThanMax(other.getManifestMinSdkGreaterThanMax());
                        break;
                    }
                    case MANIFEST_DUPLICATE_ATTRIBUTE: {
                        this.mergeManifestDuplicateAttribute(other.getManifestDuplicateAttribute());
                        break;
                    }
                    case MANIFEST_MODULES_DIFFERENT_VERSION_CODES: {
                        this.mergeManifestModulesDifferentVersionCodes(other.getManifestModulesDifferentVersionCodes());
                        break;
                    }
                    case FILE_TYPE_INVALID_FILE_EXTENSION: {
                        this.mergeFileTypeInvalidFileExtension(other.getFileTypeInvalidFileExtension());
                        break;
                    }
                    case FILE_TYPE_INVALID_FILE_NAME: {
                        this.mergeFileTypeInvalidFileName(other.getFileTypeInvalidFileName());
                        break;
                    }
                    case FILE_TYPE_INVALID_NATIVE_LIBRARY_PATH: {
                        this.mergeFileTypeInvalidNativeLibraryPath(other.getFileTypeInvalidNativeLibraryPath());
                        break;
                    }
                    case FILE_TYPE_INVALID_NATIVE_ARCHITECTURE: {
                        this.mergeFileTypeInvalidNativeArchitecture(other.getFileTypeInvalidNativeArchitecture());
                        break;
                    }
                    case FILE_TYPE_FILE_IN_RESOURCE_DIRECTORY_ROOT: {
                        this.mergeFileTypeFileInResourceDirectoryRoot(other.getFileTypeFileInResourceDirectoryRoot());
                        break;
                    }
                    case FILE_TYPE_UNKNOWN_FILE_OR_DIRECTORY_IN_MODULE: {
                        this.mergeFileTypeUnknownFileOrDirectoryInModule(other.getFileTypeUnknownFileOrDirectoryInModule());
                        break;
                    }
                    case FILE_TYPE_FILE_USES_RESERVED_NAME: {
                        this.mergeFileTypeFileUsesReservedName(other.getFileTypeFileUsesReservedName());
                        break;
                    }
                    case FILE_TYPE_DIRECTORY_IN_BUNDLE: {
                        this.mergeFileTypeDirectoryInBundle(other.getFileTypeDirectoryInBundle());
                        break;
                    }
                    case FILE_TYPE_INVALID_APEX_IMAGE_PATH: {
                        this.mergeFileTypeInvalidApexImagePath(other.getFileTypeInvalidApexImagePath());
                        break;
                    }
                    case MANDATORY_BUNDLE_FILE_MISSING: {
                        this.mergeMandatoryBundleFileMissing(other.getMandatoryBundleFileMissing());
                        break;
                    }
                    case MANDATORY_MODULE_FILE_MISSING: {
                        this.mergeMandatoryModuleFileMissing(other.getMandatoryModuleFileMissing());
                        break;
                    }
                    case RESOURCE_TABLE_REFERENCES_FILES_OUTSIDE_RES: {
                        this.mergeResourceTableReferencesFilesOutsideRes(other.getResourceTableReferencesFilesOutsideRes());
                        break;
                    }
                    case RESOUCE_TABLE_REFERENCES_MISSING_FILES: {
                        this.mergeResouceTableReferencesMissingFiles(other.getResouceTableReferencesMissingFiles());
                        break;
                    }
                    case RESOUCE_TABLE_UNREFERENCED_FILES: {
                        this.mergeResouceTableUnreferencedFiles(other.getResouceTableUnreferencedFiles());
                        break;
                    }
                    case RESOURCE_TABLE_MISSING: {
                        this.mergeResourceTableMissing(other.getResourceTableMissing());
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
                BundleToolError parsedMessage = null;
                try {
                    parsedMessage = (BundleToolError)PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e2) {
                    parsedMessage = (BundleToolError)e2.getUnfinishedMessage();
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
            public CustomErrorCase getCustomErrorCase() {
                return CustomErrorCase.forNumber(this.customErrorCase_);
            }

            public Builder clearCustomError() {
                this.customErrorCase_ = 0;
                this.customError_ = null;
                this.onChanged();
                return this;
            }

            @Override
            public String getExceptionMessage() {
                Object ref = this.exceptionMessage_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s3 = bs.toStringUtf8();
                    this.exceptionMessage_ = s3;
                    return s3;
                }
                return (String)ref;
            }

            @Override
            public ByteString getExceptionMessageBytes() {
                Object ref = this.exceptionMessage_;
                if (ref instanceof String) {
                    ByteString b2 = ByteString.copyFromUtf8((String)ref);
                    this.exceptionMessage_ = b2;
                    return b2;
                }
                return (ByteString)ref;
            }

            public Builder setExceptionMessage(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.exceptionMessage_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearExceptionMessage() {
                this.exceptionMessage_ = BundleToolError.getDefaultInstance().getExceptionMessage();
                this.onChanged();
                return this;
            }

            public Builder setExceptionMessageBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                BundleToolError.checkByteStringIsUtf8(value);
                this.exceptionMessage_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasManifestMissingVersionCode() {
                return this.customErrorCase_ == 2;
            }

            @Override
            public ManifestMissingVersionCodeError getManifestMissingVersionCode() {
                if (this.manifestMissingVersionCodeBuilder_ == null) {
                    if (this.customErrorCase_ == 2) {
                        return (ManifestMissingVersionCodeError)this.customError_;
                    }
                    return ManifestMissingVersionCodeError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 2) {
                    return this.manifestMissingVersionCodeBuilder_.getMessage();
                }
                return ManifestMissingVersionCodeError.getDefaultInstance();
            }

            public Builder setManifestMissingVersionCode(ManifestMissingVersionCodeError value) {
                if (this.manifestMissingVersionCodeBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestMissingVersionCodeBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 2;
                return this;
            }

            public Builder setManifestMissingVersionCode(ManifestMissingVersionCodeError.Builder builderForValue) {
                if (this.manifestMissingVersionCodeBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestMissingVersionCodeBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 2;
                return this;
            }

            public Builder mergeManifestMissingVersionCode(ManifestMissingVersionCodeError value) {
                if (this.manifestMissingVersionCodeBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 2 && this.customError_ != ManifestMissingVersionCodeError.getDefaultInstance() ? ManifestMissingVersionCodeError.newBuilder((ManifestMissingVersionCodeError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 2) {
                        this.manifestMissingVersionCodeBuilder_.mergeFrom(value);
                    }
                    this.manifestMissingVersionCodeBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 2;
                return this;
            }

            public Builder clearManifestMissingVersionCode() {
                if (this.manifestMissingVersionCodeBuilder_ == null) {
                    if (this.customErrorCase_ == 2) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 2) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestMissingVersionCodeBuilder_.clear();
                }
                return this;
            }

            public ManifestMissingVersionCodeError.Builder getManifestMissingVersionCodeBuilder() {
                return this.getManifestMissingVersionCodeFieldBuilder().getBuilder();
            }

            @Override
            public ManifestMissingVersionCodeErrorOrBuilder getManifestMissingVersionCodeOrBuilder() {
                if (this.customErrorCase_ == 2 && this.manifestMissingVersionCodeBuilder_ != null) {
                    return this.manifestMissingVersionCodeBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 2) {
                    return (ManifestMissingVersionCodeError)this.customError_;
                }
                return ManifestMissingVersionCodeError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestMissingVersionCodeError, ManifestMissingVersionCodeError.Builder, ManifestMissingVersionCodeErrorOrBuilder> getManifestMissingVersionCodeFieldBuilder() {
                if (this.manifestMissingVersionCodeBuilder_ == null) {
                    if (this.customErrorCase_ != 2) {
                        this.customError_ = ManifestMissingVersionCodeError.getDefaultInstance();
                    }
                    this.manifestMissingVersionCodeBuilder_ = new SingleFieldBuilderV3((ManifestMissingVersionCodeError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 2;
                this.onChanged();
                return this.manifestMissingVersionCodeBuilder_;
            }

            @Override
            public boolean hasManifestInvalidVersionCode() {
                return this.customErrorCase_ == 3;
            }

            @Override
            public ManifestInvalidVersionCodeError getManifestInvalidVersionCode() {
                if (this.manifestInvalidVersionCodeBuilder_ == null) {
                    if (this.customErrorCase_ == 3) {
                        return (ManifestInvalidVersionCodeError)this.customError_;
                    }
                    return ManifestInvalidVersionCodeError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 3) {
                    return this.manifestInvalidVersionCodeBuilder_.getMessage();
                }
                return ManifestInvalidVersionCodeError.getDefaultInstance();
            }

            public Builder setManifestInvalidVersionCode(ManifestInvalidVersionCodeError value) {
                if (this.manifestInvalidVersionCodeBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestInvalidVersionCodeBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 3;
                return this;
            }

            public Builder setManifestInvalidVersionCode(ManifestInvalidVersionCodeError.Builder builderForValue) {
                if (this.manifestInvalidVersionCodeBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestInvalidVersionCodeBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 3;
                return this;
            }

            public Builder mergeManifestInvalidVersionCode(ManifestInvalidVersionCodeError value) {
                if (this.manifestInvalidVersionCodeBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 3 && this.customError_ != ManifestInvalidVersionCodeError.getDefaultInstance() ? ManifestInvalidVersionCodeError.newBuilder((ManifestInvalidVersionCodeError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 3) {
                        this.manifestInvalidVersionCodeBuilder_.mergeFrom(value);
                    }
                    this.manifestInvalidVersionCodeBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 3;
                return this;
            }

            public Builder clearManifestInvalidVersionCode() {
                if (this.manifestInvalidVersionCodeBuilder_ == null) {
                    if (this.customErrorCase_ == 3) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 3) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestInvalidVersionCodeBuilder_.clear();
                }
                return this;
            }

            public ManifestInvalidVersionCodeError.Builder getManifestInvalidVersionCodeBuilder() {
                return this.getManifestInvalidVersionCodeFieldBuilder().getBuilder();
            }

            @Override
            public ManifestInvalidVersionCodeErrorOrBuilder getManifestInvalidVersionCodeOrBuilder() {
                if (this.customErrorCase_ == 3 && this.manifestInvalidVersionCodeBuilder_ != null) {
                    return this.manifestInvalidVersionCodeBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 3) {
                    return (ManifestInvalidVersionCodeError)this.customError_;
                }
                return ManifestInvalidVersionCodeError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestInvalidVersionCodeError, ManifestInvalidVersionCodeError.Builder, ManifestInvalidVersionCodeErrorOrBuilder> getManifestInvalidVersionCodeFieldBuilder() {
                if (this.manifestInvalidVersionCodeBuilder_ == null) {
                    if (this.customErrorCase_ != 3) {
                        this.customError_ = ManifestInvalidVersionCodeError.getDefaultInstance();
                    }
                    this.manifestInvalidVersionCodeBuilder_ = new SingleFieldBuilderV3((ManifestInvalidVersionCodeError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 3;
                this.onChanged();
                return this.manifestInvalidVersionCodeBuilder_;
            }

            @Override
            public boolean hasManifestFusingBaseModuleExcluded() {
                return this.customErrorCase_ == 4;
            }

            @Override
            public ManifestBaseModuleExcludedFromFusingError getManifestFusingBaseModuleExcluded() {
                if (this.manifestFusingBaseModuleExcludedBuilder_ == null) {
                    if (this.customErrorCase_ == 4) {
                        return (ManifestBaseModuleExcludedFromFusingError)this.customError_;
                    }
                    return ManifestBaseModuleExcludedFromFusingError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 4) {
                    return this.manifestFusingBaseModuleExcludedBuilder_.getMessage();
                }
                return ManifestBaseModuleExcludedFromFusingError.getDefaultInstance();
            }

            public Builder setManifestFusingBaseModuleExcluded(ManifestBaseModuleExcludedFromFusingError value) {
                if (this.manifestFusingBaseModuleExcludedBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestFusingBaseModuleExcludedBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 4;
                return this;
            }

            public Builder setManifestFusingBaseModuleExcluded(ManifestBaseModuleExcludedFromFusingError.Builder builderForValue) {
                if (this.manifestFusingBaseModuleExcludedBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestFusingBaseModuleExcludedBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 4;
                return this;
            }

            public Builder mergeManifestFusingBaseModuleExcluded(ManifestBaseModuleExcludedFromFusingError value) {
                if (this.manifestFusingBaseModuleExcludedBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 4 && this.customError_ != ManifestBaseModuleExcludedFromFusingError.getDefaultInstance() ? ManifestBaseModuleExcludedFromFusingError.newBuilder((ManifestBaseModuleExcludedFromFusingError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 4) {
                        this.manifestFusingBaseModuleExcludedBuilder_.mergeFrom(value);
                    }
                    this.manifestFusingBaseModuleExcludedBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 4;
                return this;
            }

            public Builder clearManifestFusingBaseModuleExcluded() {
                if (this.manifestFusingBaseModuleExcludedBuilder_ == null) {
                    if (this.customErrorCase_ == 4) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 4) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestFusingBaseModuleExcludedBuilder_.clear();
                }
                return this;
            }

            public ManifestBaseModuleExcludedFromFusingError.Builder getManifestFusingBaseModuleExcludedBuilder() {
                return this.getManifestFusingBaseModuleExcludedFieldBuilder().getBuilder();
            }

            @Override
            public ManifestBaseModuleExcludedFromFusingErrorOrBuilder getManifestFusingBaseModuleExcludedOrBuilder() {
                if (this.customErrorCase_ == 4 && this.manifestFusingBaseModuleExcludedBuilder_ != null) {
                    return this.manifestFusingBaseModuleExcludedBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 4) {
                    return (ManifestBaseModuleExcludedFromFusingError)this.customError_;
                }
                return ManifestBaseModuleExcludedFromFusingError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestBaseModuleExcludedFromFusingError, ManifestBaseModuleExcludedFromFusingError.Builder, ManifestBaseModuleExcludedFromFusingErrorOrBuilder> getManifestFusingBaseModuleExcludedFieldBuilder() {
                if (this.manifestFusingBaseModuleExcludedBuilder_ == null) {
                    if (this.customErrorCase_ != 4) {
                        this.customError_ = ManifestBaseModuleExcludedFromFusingError.getDefaultInstance();
                    }
                    this.manifestFusingBaseModuleExcludedBuilder_ = new SingleFieldBuilderV3((ManifestBaseModuleExcludedFromFusingError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 4;
                this.onChanged();
                return this.manifestFusingBaseModuleExcludedBuilder_;
            }

            @Override
            public boolean hasManifestFusingConfigurationMissing() {
                return this.customErrorCase_ == 5;
            }

            @Override
            public ManifestModuleFusingConfigurationMissingError getManifestFusingConfigurationMissing() {
                if (this.manifestFusingConfigurationMissingBuilder_ == null) {
                    if (this.customErrorCase_ == 5) {
                        return (ManifestModuleFusingConfigurationMissingError)this.customError_;
                    }
                    return ManifestModuleFusingConfigurationMissingError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 5) {
                    return this.manifestFusingConfigurationMissingBuilder_.getMessage();
                }
                return ManifestModuleFusingConfigurationMissingError.getDefaultInstance();
            }

            public Builder setManifestFusingConfigurationMissing(ManifestModuleFusingConfigurationMissingError value) {
                if (this.manifestFusingConfigurationMissingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestFusingConfigurationMissingBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 5;
                return this;
            }

            public Builder setManifestFusingConfigurationMissing(ManifestModuleFusingConfigurationMissingError.Builder builderForValue) {
                if (this.manifestFusingConfigurationMissingBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestFusingConfigurationMissingBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 5;
                return this;
            }

            public Builder mergeManifestFusingConfigurationMissing(ManifestModuleFusingConfigurationMissingError value) {
                if (this.manifestFusingConfigurationMissingBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 5 && this.customError_ != ManifestModuleFusingConfigurationMissingError.getDefaultInstance() ? ManifestModuleFusingConfigurationMissingError.newBuilder((ManifestModuleFusingConfigurationMissingError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 5) {
                        this.manifestFusingConfigurationMissingBuilder_.mergeFrom(value);
                    }
                    this.manifestFusingConfigurationMissingBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 5;
                return this;
            }

            public Builder clearManifestFusingConfigurationMissing() {
                if (this.manifestFusingConfigurationMissingBuilder_ == null) {
                    if (this.customErrorCase_ == 5) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 5) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestFusingConfigurationMissingBuilder_.clear();
                }
                return this;
            }

            public ManifestModuleFusingConfigurationMissingError.Builder getManifestFusingConfigurationMissingBuilder() {
                return this.getManifestFusingConfigurationMissingFieldBuilder().getBuilder();
            }

            @Override
            public ManifestModuleFusingConfigurationMissingErrorOrBuilder getManifestFusingConfigurationMissingOrBuilder() {
                if (this.customErrorCase_ == 5 && this.manifestFusingConfigurationMissingBuilder_ != null) {
                    return this.manifestFusingConfigurationMissingBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 5) {
                    return (ManifestModuleFusingConfigurationMissingError)this.customError_;
                }
                return ManifestModuleFusingConfigurationMissingError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestModuleFusingConfigurationMissingError, ManifestModuleFusingConfigurationMissingError.Builder, ManifestModuleFusingConfigurationMissingErrorOrBuilder> getManifestFusingConfigurationMissingFieldBuilder() {
                if (this.manifestFusingConfigurationMissingBuilder_ == null) {
                    if (this.customErrorCase_ != 5) {
                        this.customError_ = ManifestModuleFusingConfigurationMissingError.getDefaultInstance();
                    }
                    this.manifestFusingConfigurationMissingBuilder_ = new SingleFieldBuilderV3((ManifestModuleFusingConfigurationMissingError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 5;
                this.onChanged();
                return this.manifestFusingConfigurationMissingBuilder_;
            }

            @Override
            public boolean hasManifestFusingMissingIncludeAttribute() {
                return this.customErrorCase_ == 21;
            }

            @Override
            public ManifestFusingMissingIncludeAttributeError getManifestFusingMissingIncludeAttribute() {
                if (this.manifestFusingMissingIncludeAttributeBuilder_ == null) {
                    if (this.customErrorCase_ == 21) {
                        return (ManifestFusingMissingIncludeAttributeError)this.customError_;
                    }
                    return ManifestFusingMissingIncludeAttributeError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 21) {
                    return this.manifestFusingMissingIncludeAttributeBuilder_.getMessage();
                }
                return ManifestFusingMissingIncludeAttributeError.getDefaultInstance();
            }

            public Builder setManifestFusingMissingIncludeAttribute(ManifestFusingMissingIncludeAttributeError value) {
                if (this.manifestFusingMissingIncludeAttributeBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestFusingMissingIncludeAttributeBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 21;
                return this;
            }

            public Builder setManifestFusingMissingIncludeAttribute(ManifestFusingMissingIncludeAttributeError.Builder builderForValue) {
                if (this.manifestFusingMissingIncludeAttributeBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestFusingMissingIncludeAttributeBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 21;
                return this;
            }

            public Builder mergeManifestFusingMissingIncludeAttribute(ManifestFusingMissingIncludeAttributeError value) {
                if (this.manifestFusingMissingIncludeAttributeBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 21 && this.customError_ != ManifestFusingMissingIncludeAttributeError.getDefaultInstance() ? ManifestFusingMissingIncludeAttributeError.newBuilder((ManifestFusingMissingIncludeAttributeError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 21) {
                        this.manifestFusingMissingIncludeAttributeBuilder_.mergeFrom(value);
                    }
                    this.manifestFusingMissingIncludeAttributeBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 21;
                return this;
            }

            public Builder clearManifestFusingMissingIncludeAttribute() {
                if (this.manifestFusingMissingIncludeAttributeBuilder_ == null) {
                    if (this.customErrorCase_ == 21) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 21) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestFusingMissingIncludeAttributeBuilder_.clear();
                }
                return this;
            }

            public ManifestFusingMissingIncludeAttributeError.Builder getManifestFusingMissingIncludeAttributeBuilder() {
                return this.getManifestFusingMissingIncludeAttributeFieldBuilder().getBuilder();
            }

            @Override
            public ManifestFusingMissingIncludeAttributeErrorOrBuilder getManifestFusingMissingIncludeAttributeOrBuilder() {
                if (this.customErrorCase_ == 21 && this.manifestFusingMissingIncludeAttributeBuilder_ != null) {
                    return this.manifestFusingMissingIncludeAttributeBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 21) {
                    return (ManifestFusingMissingIncludeAttributeError)this.customError_;
                }
                return ManifestFusingMissingIncludeAttributeError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestFusingMissingIncludeAttributeError, ManifestFusingMissingIncludeAttributeError.Builder, ManifestFusingMissingIncludeAttributeErrorOrBuilder> getManifestFusingMissingIncludeAttributeFieldBuilder() {
                if (this.manifestFusingMissingIncludeAttributeBuilder_ == null) {
                    if (this.customErrorCase_ != 21) {
                        this.customError_ = ManifestFusingMissingIncludeAttributeError.getDefaultInstance();
                    }
                    this.manifestFusingMissingIncludeAttributeBuilder_ = new SingleFieldBuilderV3((ManifestFusingMissingIncludeAttributeError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 21;
                this.onChanged();
                return this.manifestFusingMissingIncludeAttributeBuilder_;
            }

            @Override
            public boolean hasManifestMaxSdkInvalid() {
                return this.customErrorCase_ == 22;
            }

            @Override
            public ManifestMaxSdkInvalidError getManifestMaxSdkInvalid() {
                if (this.manifestMaxSdkInvalidBuilder_ == null) {
                    if (this.customErrorCase_ == 22) {
                        return (ManifestMaxSdkInvalidError)this.customError_;
                    }
                    return ManifestMaxSdkInvalidError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 22) {
                    return this.manifestMaxSdkInvalidBuilder_.getMessage();
                }
                return ManifestMaxSdkInvalidError.getDefaultInstance();
            }

            public Builder setManifestMaxSdkInvalid(ManifestMaxSdkInvalidError value) {
                if (this.manifestMaxSdkInvalidBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestMaxSdkInvalidBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 22;
                return this;
            }

            public Builder setManifestMaxSdkInvalid(ManifestMaxSdkInvalidError.Builder builderForValue) {
                if (this.manifestMaxSdkInvalidBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestMaxSdkInvalidBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 22;
                return this;
            }

            public Builder mergeManifestMaxSdkInvalid(ManifestMaxSdkInvalidError value) {
                if (this.manifestMaxSdkInvalidBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 22 && this.customError_ != ManifestMaxSdkInvalidError.getDefaultInstance() ? ManifestMaxSdkInvalidError.newBuilder((ManifestMaxSdkInvalidError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 22) {
                        this.manifestMaxSdkInvalidBuilder_.mergeFrom(value);
                    }
                    this.manifestMaxSdkInvalidBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 22;
                return this;
            }

            public Builder clearManifestMaxSdkInvalid() {
                if (this.manifestMaxSdkInvalidBuilder_ == null) {
                    if (this.customErrorCase_ == 22) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 22) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestMaxSdkInvalidBuilder_.clear();
                }
                return this;
            }

            public ManifestMaxSdkInvalidError.Builder getManifestMaxSdkInvalidBuilder() {
                return this.getManifestMaxSdkInvalidFieldBuilder().getBuilder();
            }

            @Override
            public ManifestMaxSdkInvalidErrorOrBuilder getManifestMaxSdkInvalidOrBuilder() {
                if (this.customErrorCase_ == 22 && this.manifestMaxSdkInvalidBuilder_ != null) {
                    return this.manifestMaxSdkInvalidBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 22) {
                    return (ManifestMaxSdkInvalidError)this.customError_;
                }
                return ManifestMaxSdkInvalidError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestMaxSdkInvalidError, ManifestMaxSdkInvalidError.Builder, ManifestMaxSdkInvalidErrorOrBuilder> getManifestMaxSdkInvalidFieldBuilder() {
                if (this.manifestMaxSdkInvalidBuilder_ == null) {
                    if (this.customErrorCase_ != 22) {
                        this.customError_ = ManifestMaxSdkInvalidError.getDefaultInstance();
                    }
                    this.manifestMaxSdkInvalidBuilder_ = new SingleFieldBuilderV3((ManifestMaxSdkInvalidError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 22;
                this.onChanged();
                return this.manifestMaxSdkInvalidBuilder_;
            }

            @Override
            public boolean hasManifestMaxSdkLessThanMinInstantSdk() {
                return this.customErrorCase_ == 23;
            }

            @Override
            public ManifestMaxSdkLessThanMinInstantSdkError getManifestMaxSdkLessThanMinInstantSdk() {
                if (this.manifestMaxSdkLessThanMinInstantSdkBuilder_ == null) {
                    if (this.customErrorCase_ == 23) {
                        return (ManifestMaxSdkLessThanMinInstantSdkError)this.customError_;
                    }
                    return ManifestMaxSdkLessThanMinInstantSdkError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 23) {
                    return this.manifestMaxSdkLessThanMinInstantSdkBuilder_.getMessage();
                }
                return ManifestMaxSdkLessThanMinInstantSdkError.getDefaultInstance();
            }

            public Builder setManifestMaxSdkLessThanMinInstantSdk(ManifestMaxSdkLessThanMinInstantSdkError value) {
                if (this.manifestMaxSdkLessThanMinInstantSdkBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestMaxSdkLessThanMinInstantSdkBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 23;
                return this;
            }

            public Builder setManifestMaxSdkLessThanMinInstantSdk(ManifestMaxSdkLessThanMinInstantSdkError.Builder builderForValue) {
                if (this.manifestMaxSdkLessThanMinInstantSdkBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestMaxSdkLessThanMinInstantSdkBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 23;
                return this;
            }

            public Builder mergeManifestMaxSdkLessThanMinInstantSdk(ManifestMaxSdkLessThanMinInstantSdkError value) {
                if (this.manifestMaxSdkLessThanMinInstantSdkBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 23 && this.customError_ != ManifestMaxSdkLessThanMinInstantSdkError.getDefaultInstance() ? ManifestMaxSdkLessThanMinInstantSdkError.newBuilder((ManifestMaxSdkLessThanMinInstantSdkError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 23) {
                        this.manifestMaxSdkLessThanMinInstantSdkBuilder_.mergeFrom(value);
                    }
                    this.manifestMaxSdkLessThanMinInstantSdkBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 23;
                return this;
            }

            public Builder clearManifestMaxSdkLessThanMinInstantSdk() {
                if (this.manifestMaxSdkLessThanMinInstantSdkBuilder_ == null) {
                    if (this.customErrorCase_ == 23) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 23) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestMaxSdkLessThanMinInstantSdkBuilder_.clear();
                }
                return this;
            }

            public ManifestMaxSdkLessThanMinInstantSdkError.Builder getManifestMaxSdkLessThanMinInstantSdkBuilder() {
                return this.getManifestMaxSdkLessThanMinInstantSdkFieldBuilder().getBuilder();
            }

            @Override
            public ManifestMaxSdkLessThanMinInstantSdkErrorOrBuilder getManifestMaxSdkLessThanMinInstantSdkOrBuilder() {
                if (this.customErrorCase_ == 23 && this.manifestMaxSdkLessThanMinInstantSdkBuilder_ != null) {
                    return this.manifestMaxSdkLessThanMinInstantSdkBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 23) {
                    return (ManifestMaxSdkLessThanMinInstantSdkError)this.customError_;
                }
                return ManifestMaxSdkLessThanMinInstantSdkError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestMaxSdkLessThanMinInstantSdkError, ManifestMaxSdkLessThanMinInstantSdkError.Builder, ManifestMaxSdkLessThanMinInstantSdkErrorOrBuilder> getManifestMaxSdkLessThanMinInstantSdkFieldBuilder() {
                if (this.manifestMaxSdkLessThanMinInstantSdkBuilder_ == null) {
                    if (this.customErrorCase_ != 23) {
                        this.customError_ = ManifestMaxSdkLessThanMinInstantSdkError.getDefaultInstance();
                    }
                    this.manifestMaxSdkLessThanMinInstantSdkBuilder_ = new SingleFieldBuilderV3((ManifestMaxSdkLessThanMinInstantSdkError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 23;
                this.onChanged();
                return this.manifestMaxSdkLessThanMinInstantSdkBuilder_;
            }

            @Override
            public boolean hasManifestMinSdkInvalid() {
                return this.customErrorCase_ == 19;
            }

            @Override
            public ManifestMinSdkInvalidError getManifestMinSdkInvalid() {
                if (this.manifestMinSdkInvalidBuilder_ == null) {
                    if (this.customErrorCase_ == 19) {
                        return (ManifestMinSdkInvalidError)this.customError_;
                    }
                    return ManifestMinSdkInvalidError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 19) {
                    return this.manifestMinSdkInvalidBuilder_.getMessage();
                }
                return ManifestMinSdkInvalidError.getDefaultInstance();
            }

            public Builder setManifestMinSdkInvalid(ManifestMinSdkInvalidError value) {
                if (this.manifestMinSdkInvalidBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestMinSdkInvalidBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 19;
                return this;
            }

            public Builder setManifestMinSdkInvalid(ManifestMinSdkInvalidError.Builder builderForValue) {
                if (this.manifestMinSdkInvalidBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestMinSdkInvalidBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 19;
                return this;
            }

            public Builder mergeManifestMinSdkInvalid(ManifestMinSdkInvalidError value) {
                if (this.manifestMinSdkInvalidBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 19 && this.customError_ != ManifestMinSdkInvalidError.getDefaultInstance() ? ManifestMinSdkInvalidError.newBuilder((ManifestMinSdkInvalidError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 19) {
                        this.manifestMinSdkInvalidBuilder_.mergeFrom(value);
                    }
                    this.manifestMinSdkInvalidBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 19;
                return this;
            }

            public Builder clearManifestMinSdkInvalid() {
                if (this.manifestMinSdkInvalidBuilder_ == null) {
                    if (this.customErrorCase_ == 19) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 19) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestMinSdkInvalidBuilder_.clear();
                }
                return this;
            }

            public ManifestMinSdkInvalidError.Builder getManifestMinSdkInvalidBuilder() {
                return this.getManifestMinSdkInvalidFieldBuilder().getBuilder();
            }

            @Override
            public ManifestMinSdkInvalidErrorOrBuilder getManifestMinSdkInvalidOrBuilder() {
                if (this.customErrorCase_ == 19 && this.manifestMinSdkInvalidBuilder_ != null) {
                    return this.manifestMinSdkInvalidBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 19) {
                    return (ManifestMinSdkInvalidError)this.customError_;
                }
                return ManifestMinSdkInvalidError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestMinSdkInvalidError, ManifestMinSdkInvalidError.Builder, ManifestMinSdkInvalidErrorOrBuilder> getManifestMinSdkInvalidFieldBuilder() {
                if (this.manifestMinSdkInvalidBuilder_ == null) {
                    if (this.customErrorCase_ != 19) {
                        this.customError_ = ManifestMinSdkInvalidError.getDefaultInstance();
                    }
                    this.manifestMinSdkInvalidBuilder_ = new SingleFieldBuilderV3((ManifestMinSdkInvalidError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 19;
                this.onChanged();
                return this.manifestMinSdkInvalidBuilder_;
            }

            @Override
            public boolean hasManifestMinSdkGreaterThanMax() {
                return this.customErrorCase_ == 20;
            }

            @Override
            public ManifestMinSdkGreaterThanMaxSdkError getManifestMinSdkGreaterThanMax() {
                if (this.manifestMinSdkGreaterThanMaxBuilder_ == null) {
                    if (this.customErrorCase_ == 20) {
                        return (ManifestMinSdkGreaterThanMaxSdkError)this.customError_;
                    }
                    return ManifestMinSdkGreaterThanMaxSdkError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 20) {
                    return this.manifestMinSdkGreaterThanMaxBuilder_.getMessage();
                }
                return ManifestMinSdkGreaterThanMaxSdkError.getDefaultInstance();
            }

            public Builder setManifestMinSdkGreaterThanMax(ManifestMinSdkGreaterThanMaxSdkError value) {
                if (this.manifestMinSdkGreaterThanMaxBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestMinSdkGreaterThanMaxBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 20;
                return this;
            }

            public Builder setManifestMinSdkGreaterThanMax(ManifestMinSdkGreaterThanMaxSdkError.Builder builderForValue) {
                if (this.manifestMinSdkGreaterThanMaxBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestMinSdkGreaterThanMaxBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 20;
                return this;
            }

            public Builder mergeManifestMinSdkGreaterThanMax(ManifestMinSdkGreaterThanMaxSdkError value) {
                if (this.manifestMinSdkGreaterThanMaxBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 20 && this.customError_ != ManifestMinSdkGreaterThanMaxSdkError.getDefaultInstance() ? ManifestMinSdkGreaterThanMaxSdkError.newBuilder((ManifestMinSdkGreaterThanMaxSdkError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 20) {
                        this.manifestMinSdkGreaterThanMaxBuilder_.mergeFrom(value);
                    }
                    this.manifestMinSdkGreaterThanMaxBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 20;
                return this;
            }

            public Builder clearManifestMinSdkGreaterThanMax() {
                if (this.manifestMinSdkGreaterThanMaxBuilder_ == null) {
                    if (this.customErrorCase_ == 20) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 20) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestMinSdkGreaterThanMaxBuilder_.clear();
                }
                return this;
            }

            public ManifestMinSdkGreaterThanMaxSdkError.Builder getManifestMinSdkGreaterThanMaxBuilder() {
                return this.getManifestMinSdkGreaterThanMaxFieldBuilder().getBuilder();
            }

            @Override
            public ManifestMinSdkGreaterThanMaxSdkErrorOrBuilder getManifestMinSdkGreaterThanMaxOrBuilder() {
                if (this.customErrorCase_ == 20 && this.manifestMinSdkGreaterThanMaxBuilder_ != null) {
                    return this.manifestMinSdkGreaterThanMaxBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 20) {
                    return (ManifestMinSdkGreaterThanMaxSdkError)this.customError_;
                }
                return ManifestMinSdkGreaterThanMaxSdkError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestMinSdkGreaterThanMaxSdkError, ManifestMinSdkGreaterThanMaxSdkError.Builder, ManifestMinSdkGreaterThanMaxSdkErrorOrBuilder> getManifestMinSdkGreaterThanMaxFieldBuilder() {
                if (this.manifestMinSdkGreaterThanMaxBuilder_ == null) {
                    if (this.customErrorCase_ != 20) {
                        this.customError_ = ManifestMinSdkGreaterThanMaxSdkError.getDefaultInstance();
                    }
                    this.manifestMinSdkGreaterThanMaxBuilder_ = new SingleFieldBuilderV3((ManifestMinSdkGreaterThanMaxSdkError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 20;
                this.onChanged();
                return this.manifestMinSdkGreaterThanMaxBuilder_;
            }

            @Override
            public boolean hasManifestDuplicateAttribute() {
                return this.customErrorCase_ == 25;
            }

            @Override
            public ManifestDuplicateAttributeError getManifestDuplicateAttribute() {
                if (this.manifestDuplicateAttributeBuilder_ == null) {
                    if (this.customErrorCase_ == 25) {
                        return (ManifestDuplicateAttributeError)this.customError_;
                    }
                    return ManifestDuplicateAttributeError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 25) {
                    return this.manifestDuplicateAttributeBuilder_.getMessage();
                }
                return ManifestDuplicateAttributeError.getDefaultInstance();
            }

            public Builder setManifestDuplicateAttribute(ManifestDuplicateAttributeError value) {
                if (this.manifestDuplicateAttributeBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestDuplicateAttributeBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 25;
                return this;
            }

            public Builder setManifestDuplicateAttribute(ManifestDuplicateAttributeError.Builder builderForValue) {
                if (this.manifestDuplicateAttributeBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestDuplicateAttributeBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 25;
                return this;
            }

            public Builder mergeManifestDuplicateAttribute(ManifestDuplicateAttributeError value) {
                if (this.manifestDuplicateAttributeBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 25 && this.customError_ != ManifestDuplicateAttributeError.getDefaultInstance() ? ManifestDuplicateAttributeError.newBuilder((ManifestDuplicateAttributeError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 25) {
                        this.manifestDuplicateAttributeBuilder_.mergeFrom(value);
                    }
                    this.manifestDuplicateAttributeBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 25;
                return this;
            }

            public Builder clearManifestDuplicateAttribute() {
                if (this.manifestDuplicateAttributeBuilder_ == null) {
                    if (this.customErrorCase_ == 25) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 25) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestDuplicateAttributeBuilder_.clear();
                }
                return this;
            }

            public ManifestDuplicateAttributeError.Builder getManifestDuplicateAttributeBuilder() {
                return this.getManifestDuplicateAttributeFieldBuilder().getBuilder();
            }

            @Override
            public ManifestDuplicateAttributeErrorOrBuilder getManifestDuplicateAttributeOrBuilder() {
                if (this.customErrorCase_ == 25 && this.manifestDuplicateAttributeBuilder_ != null) {
                    return this.manifestDuplicateAttributeBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 25) {
                    return (ManifestDuplicateAttributeError)this.customError_;
                }
                return ManifestDuplicateAttributeError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestDuplicateAttributeError, ManifestDuplicateAttributeError.Builder, ManifestDuplicateAttributeErrorOrBuilder> getManifestDuplicateAttributeFieldBuilder() {
                if (this.manifestDuplicateAttributeBuilder_ == null) {
                    if (this.customErrorCase_ != 25) {
                        this.customError_ = ManifestDuplicateAttributeError.getDefaultInstance();
                    }
                    this.manifestDuplicateAttributeBuilder_ = new SingleFieldBuilderV3((ManifestDuplicateAttributeError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 25;
                this.onChanged();
                return this.manifestDuplicateAttributeBuilder_;
            }

            @Override
            public boolean hasManifestModulesDifferentVersionCodes() {
                return this.customErrorCase_ == 27;
            }

            @Override
            public ManifestModulesDifferentVersionCodes getManifestModulesDifferentVersionCodes() {
                if (this.manifestModulesDifferentVersionCodesBuilder_ == null) {
                    if (this.customErrorCase_ == 27) {
                        return (ManifestModulesDifferentVersionCodes)this.customError_;
                    }
                    return ManifestModulesDifferentVersionCodes.getDefaultInstance();
                }
                if (this.customErrorCase_ == 27) {
                    return this.manifestModulesDifferentVersionCodesBuilder_.getMessage();
                }
                return ManifestModulesDifferentVersionCodes.getDefaultInstance();
            }

            public Builder setManifestModulesDifferentVersionCodes(ManifestModulesDifferentVersionCodes value) {
                if (this.manifestModulesDifferentVersionCodesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.manifestModulesDifferentVersionCodesBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 27;
                return this;
            }

            public Builder setManifestModulesDifferentVersionCodes(ManifestModulesDifferentVersionCodes.Builder builderForValue) {
                if (this.manifestModulesDifferentVersionCodesBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.manifestModulesDifferentVersionCodesBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 27;
                return this;
            }

            public Builder mergeManifestModulesDifferentVersionCodes(ManifestModulesDifferentVersionCodes value) {
                if (this.manifestModulesDifferentVersionCodesBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 27 && this.customError_ != ManifestModulesDifferentVersionCodes.getDefaultInstance() ? ManifestModulesDifferentVersionCodes.newBuilder((ManifestModulesDifferentVersionCodes)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 27) {
                        this.manifestModulesDifferentVersionCodesBuilder_.mergeFrom(value);
                    }
                    this.manifestModulesDifferentVersionCodesBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 27;
                return this;
            }

            public Builder clearManifestModulesDifferentVersionCodes() {
                if (this.manifestModulesDifferentVersionCodesBuilder_ == null) {
                    if (this.customErrorCase_ == 27) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 27) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.manifestModulesDifferentVersionCodesBuilder_.clear();
                }
                return this;
            }

            public ManifestModulesDifferentVersionCodes.Builder getManifestModulesDifferentVersionCodesBuilder() {
                return this.getManifestModulesDifferentVersionCodesFieldBuilder().getBuilder();
            }

            @Override
            public ManifestModulesDifferentVersionCodesOrBuilder getManifestModulesDifferentVersionCodesOrBuilder() {
                if (this.customErrorCase_ == 27 && this.manifestModulesDifferentVersionCodesBuilder_ != null) {
                    return this.manifestModulesDifferentVersionCodesBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 27) {
                    return (ManifestModulesDifferentVersionCodes)this.customError_;
                }
                return ManifestModulesDifferentVersionCodes.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ManifestModulesDifferentVersionCodes, ManifestModulesDifferentVersionCodes.Builder, ManifestModulesDifferentVersionCodesOrBuilder> getManifestModulesDifferentVersionCodesFieldBuilder() {
                if (this.manifestModulesDifferentVersionCodesBuilder_ == null) {
                    if (this.customErrorCase_ != 27) {
                        this.customError_ = ManifestModulesDifferentVersionCodes.getDefaultInstance();
                    }
                    this.manifestModulesDifferentVersionCodesBuilder_ = new SingleFieldBuilderV3((ManifestModulesDifferentVersionCodes)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 27;
                this.onChanged();
                return this.manifestModulesDifferentVersionCodesBuilder_;
            }

            @Override
            public boolean hasFileTypeInvalidFileExtension() {
                return this.customErrorCase_ == 6;
            }

            @Override
            public FileTypeInvalidFileExtensionError getFileTypeInvalidFileExtension() {
                if (this.fileTypeInvalidFileExtensionBuilder_ == null) {
                    if (this.customErrorCase_ == 6) {
                        return (FileTypeInvalidFileExtensionError)this.customError_;
                    }
                    return FileTypeInvalidFileExtensionError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 6) {
                    return this.fileTypeInvalidFileExtensionBuilder_.getMessage();
                }
                return FileTypeInvalidFileExtensionError.getDefaultInstance();
            }

            public Builder setFileTypeInvalidFileExtension(FileTypeInvalidFileExtensionError value) {
                if (this.fileTypeInvalidFileExtensionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.fileTypeInvalidFileExtensionBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 6;
                return this;
            }

            public Builder setFileTypeInvalidFileExtension(FileTypeInvalidFileExtensionError.Builder builderForValue) {
                if (this.fileTypeInvalidFileExtensionBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.fileTypeInvalidFileExtensionBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 6;
                return this;
            }

            public Builder mergeFileTypeInvalidFileExtension(FileTypeInvalidFileExtensionError value) {
                if (this.fileTypeInvalidFileExtensionBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 6 && this.customError_ != FileTypeInvalidFileExtensionError.getDefaultInstance() ? FileTypeInvalidFileExtensionError.newBuilder((FileTypeInvalidFileExtensionError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 6) {
                        this.fileTypeInvalidFileExtensionBuilder_.mergeFrom(value);
                    }
                    this.fileTypeInvalidFileExtensionBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 6;
                return this;
            }

            public Builder clearFileTypeInvalidFileExtension() {
                if (this.fileTypeInvalidFileExtensionBuilder_ == null) {
                    if (this.customErrorCase_ == 6) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 6) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.fileTypeInvalidFileExtensionBuilder_.clear();
                }
                return this;
            }

            public FileTypeInvalidFileExtensionError.Builder getFileTypeInvalidFileExtensionBuilder() {
                return this.getFileTypeInvalidFileExtensionFieldBuilder().getBuilder();
            }

            @Override
            public FileTypeInvalidFileExtensionErrorOrBuilder getFileTypeInvalidFileExtensionOrBuilder() {
                if (this.customErrorCase_ == 6 && this.fileTypeInvalidFileExtensionBuilder_ != null) {
                    return this.fileTypeInvalidFileExtensionBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 6) {
                    return (FileTypeInvalidFileExtensionError)this.customError_;
                }
                return FileTypeInvalidFileExtensionError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FileTypeInvalidFileExtensionError, FileTypeInvalidFileExtensionError.Builder, FileTypeInvalidFileExtensionErrorOrBuilder> getFileTypeInvalidFileExtensionFieldBuilder() {
                if (this.fileTypeInvalidFileExtensionBuilder_ == null) {
                    if (this.customErrorCase_ != 6) {
                        this.customError_ = FileTypeInvalidFileExtensionError.getDefaultInstance();
                    }
                    this.fileTypeInvalidFileExtensionBuilder_ = new SingleFieldBuilderV3((FileTypeInvalidFileExtensionError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 6;
                this.onChanged();
                return this.fileTypeInvalidFileExtensionBuilder_;
            }

            @Override
            public boolean hasFileTypeInvalidFileName() {
                return this.customErrorCase_ == 7;
            }

            @Override
            public FileTypeInvalidFileNameInDirectoryError getFileTypeInvalidFileName() {
                if (this.fileTypeInvalidFileNameBuilder_ == null) {
                    if (this.customErrorCase_ == 7) {
                        return (FileTypeInvalidFileNameInDirectoryError)this.customError_;
                    }
                    return FileTypeInvalidFileNameInDirectoryError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 7) {
                    return this.fileTypeInvalidFileNameBuilder_.getMessage();
                }
                return FileTypeInvalidFileNameInDirectoryError.getDefaultInstance();
            }

            public Builder setFileTypeInvalidFileName(FileTypeInvalidFileNameInDirectoryError value) {
                if (this.fileTypeInvalidFileNameBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.fileTypeInvalidFileNameBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 7;
                return this;
            }

            public Builder setFileTypeInvalidFileName(FileTypeInvalidFileNameInDirectoryError.Builder builderForValue) {
                if (this.fileTypeInvalidFileNameBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.fileTypeInvalidFileNameBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 7;
                return this;
            }

            public Builder mergeFileTypeInvalidFileName(FileTypeInvalidFileNameInDirectoryError value) {
                if (this.fileTypeInvalidFileNameBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 7 && this.customError_ != FileTypeInvalidFileNameInDirectoryError.getDefaultInstance() ? FileTypeInvalidFileNameInDirectoryError.newBuilder((FileTypeInvalidFileNameInDirectoryError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 7) {
                        this.fileTypeInvalidFileNameBuilder_.mergeFrom(value);
                    }
                    this.fileTypeInvalidFileNameBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 7;
                return this;
            }

            public Builder clearFileTypeInvalidFileName() {
                if (this.fileTypeInvalidFileNameBuilder_ == null) {
                    if (this.customErrorCase_ == 7) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 7) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.fileTypeInvalidFileNameBuilder_.clear();
                }
                return this;
            }

            public FileTypeInvalidFileNameInDirectoryError.Builder getFileTypeInvalidFileNameBuilder() {
                return this.getFileTypeInvalidFileNameFieldBuilder().getBuilder();
            }

            @Override
            public FileTypeInvalidFileNameInDirectoryErrorOrBuilder getFileTypeInvalidFileNameOrBuilder() {
                if (this.customErrorCase_ == 7 && this.fileTypeInvalidFileNameBuilder_ != null) {
                    return this.fileTypeInvalidFileNameBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 7) {
                    return (FileTypeInvalidFileNameInDirectoryError)this.customError_;
                }
                return FileTypeInvalidFileNameInDirectoryError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FileTypeInvalidFileNameInDirectoryError, FileTypeInvalidFileNameInDirectoryError.Builder, FileTypeInvalidFileNameInDirectoryErrorOrBuilder> getFileTypeInvalidFileNameFieldBuilder() {
                if (this.fileTypeInvalidFileNameBuilder_ == null) {
                    if (this.customErrorCase_ != 7) {
                        this.customError_ = FileTypeInvalidFileNameInDirectoryError.getDefaultInstance();
                    }
                    this.fileTypeInvalidFileNameBuilder_ = new SingleFieldBuilderV3((FileTypeInvalidFileNameInDirectoryError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 7;
                this.onChanged();
                return this.fileTypeInvalidFileNameBuilder_;
            }

            @Override
            public boolean hasFileTypeInvalidNativeLibraryPath() {
                return this.customErrorCase_ == 8;
            }

            @Override
            public FileTypeInvalidNativeLibraryPathError getFileTypeInvalidNativeLibraryPath() {
                if (this.fileTypeInvalidNativeLibraryPathBuilder_ == null) {
                    if (this.customErrorCase_ == 8) {
                        return (FileTypeInvalidNativeLibraryPathError)this.customError_;
                    }
                    return FileTypeInvalidNativeLibraryPathError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 8) {
                    return this.fileTypeInvalidNativeLibraryPathBuilder_.getMessage();
                }
                return FileTypeInvalidNativeLibraryPathError.getDefaultInstance();
            }

            public Builder setFileTypeInvalidNativeLibraryPath(FileTypeInvalidNativeLibraryPathError value) {
                if (this.fileTypeInvalidNativeLibraryPathBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.fileTypeInvalidNativeLibraryPathBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 8;
                return this;
            }

            public Builder setFileTypeInvalidNativeLibraryPath(FileTypeInvalidNativeLibraryPathError.Builder builderForValue) {
                if (this.fileTypeInvalidNativeLibraryPathBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.fileTypeInvalidNativeLibraryPathBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 8;
                return this;
            }

            public Builder mergeFileTypeInvalidNativeLibraryPath(FileTypeInvalidNativeLibraryPathError value) {
                if (this.fileTypeInvalidNativeLibraryPathBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 8 && this.customError_ != FileTypeInvalidNativeLibraryPathError.getDefaultInstance() ? FileTypeInvalidNativeLibraryPathError.newBuilder((FileTypeInvalidNativeLibraryPathError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 8) {
                        this.fileTypeInvalidNativeLibraryPathBuilder_.mergeFrom(value);
                    }
                    this.fileTypeInvalidNativeLibraryPathBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 8;
                return this;
            }

            public Builder clearFileTypeInvalidNativeLibraryPath() {
                if (this.fileTypeInvalidNativeLibraryPathBuilder_ == null) {
                    if (this.customErrorCase_ == 8) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 8) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.fileTypeInvalidNativeLibraryPathBuilder_.clear();
                }
                return this;
            }

            public FileTypeInvalidNativeLibraryPathError.Builder getFileTypeInvalidNativeLibraryPathBuilder() {
                return this.getFileTypeInvalidNativeLibraryPathFieldBuilder().getBuilder();
            }

            @Override
            public FileTypeInvalidNativeLibraryPathErrorOrBuilder getFileTypeInvalidNativeLibraryPathOrBuilder() {
                if (this.customErrorCase_ == 8 && this.fileTypeInvalidNativeLibraryPathBuilder_ != null) {
                    return this.fileTypeInvalidNativeLibraryPathBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 8) {
                    return (FileTypeInvalidNativeLibraryPathError)this.customError_;
                }
                return FileTypeInvalidNativeLibraryPathError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FileTypeInvalidNativeLibraryPathError, FileTypeInvalidNativeLibraryPathError.Builder, FileTypeInvalidNativeLibraryPathErrorOrBuilder> getFileTypeInvalidNativeLibraryPathFieldBuilder() {
                if (this.fileTypeInvalidNativeLibraryPathBuilder_ == null) {
                    if (this.customErrorCase_ != 8) {
                        this.customError_ = FileTypeInvalidNativeLibraryPathError.getDefaultInstance();
                    }
                    this.fileTypeInvalidNativeLibraryPathBuilder_ = new SingleFieldBuilderV3((FileTypeInvalidNativeLibraryPathError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 8;
                this.onChanged();
                return this.fileTypeInvalidNativeLibraryPathBuilder_;
            }

            @Override
            public boolean hasFileTypeInvalidNativeArchitecture() {
                return this.customErrorCase_ == 9;
            }

            @Override
            public FileTypeInvalidNativeArchitectureError getFileTypeInvalidNativeArchitecture() {
                if (this.fileTypeInvalidNativeArchitectureBuilder_ == null) {
                    if (this.customErrorCase_ == 9) {
                        return (FileTypeInvalidNativeArchitectureError)this.customError_;
                    }
                    return FileTypeInvalidNativeArchitectureError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 9) {
                    return this.fileTypeInvalidNativeArchitectureBuilder_.getMessage();
                }
                return FileTypeInvalidNativeArchitectureError.getDefaultInstance();
            }

            public Builder setFileTypeInvalidNativeArchitecture(FileTypeInvalidNativeArchitectureError value) {
                if (this.fileTypeInvalidNativeArchitectureBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.fileTypeInvalidNativeArchitectureBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 9;
                return this;
            }

            public Builder setFileTypeInvalidNativeArchitecture(FileTypeInvalidNativeArchitectureError.Builder builderForValue) {
                if (this.fileTypeInvalidNativeArchitectureBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.fileTypeInvalidNativeArchitectureBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 9;
                return this;
            }

            public Builder mergeFileTypeInvalidNativeArchitecture(FileTypeInvalidNativeArchitectureError value) {
                if (this.fileTypeInvalidNativeArchitectureBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 9 && this.customError_ != FileTypeInvalidNativeArchitectureError.getDefaultInstance() ? FileTypeInvalidNativeArchitectureError.newBuilder((FileTypeInvalidNativeArchitectureError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 9) {
                        this.fileTypeInvalidNativeArchitectureBuilder_.mergeFrom(value);
                    }
                    this.fileTypeInvalidNativeArchitectureBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 9;
                return this;
            }

            public Builder clearFileTypeInvalidNativeArchitecture() {
                if (this.fileTypeInvalidNativeArchitectureBuilder_ == null) {
                    if (this.customErrorCase_ == 9) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 9) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.fileTypeInvalidNativeArchitectureBuilder_.clear();
                }
                return this;
            }

            public FileTypeInvalidNativeArchitectureError.Builder getFileTypeInvalidNativeArchitectureBuilder() {
                return this.getFileTypeInvalidNativeArchitectureFieldBuilder().getBuilder();
            }

            @Override
            public FileTypeInvalidNativeArchitectureErrorOrBuilder getFileTypeInvalidNativeArchitectureOrBuilder() {
                if (this.customErrorCase_ == 9 && this.fileTypeInvalidNativeArchitectureBuilder_ != null) {
                    return this.fileTypeInvalidNativeArchitectureBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 9) {
                    return (FileTypeInvalidNativeArchitectureError)this.customError_;
                }
                return FileTypeInvalidNativeArchitectureError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FileTypeInvalidNativeArchitectureError, FileTypeInvalidNativeArchitectureError.Builder, FileTypeInvalidNativeArchitectureErrorOrBuilder> getFileTypeInvalidNativeArchitectureFieldBuilder() {
                if (this.fileTypeInvalidNativeArchitectureBuilder_ == null) {
                    if (this.customErrorCase_ != 9) {
                        this.customError_ = FileTypeInvalidNativeArchitectureError.getDefaultInstance();
                    }
                    this.fileTypeInvalidNativeArchitectureBuilder_ = new SingleFieldBuilderV3((FileTypeInvalidNativeArchitectureError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 9;
                this.onChanged();
                return this.fileTypeInvalidNativeArchitectureBuilder_;
            }

            @Override
            public boolean hasFileTypeFileInResourceDirectoryRoot() {
                return this.customErrorCase_ == 10;
            }

            @Override
            public FileTypeFilesInResourceDirectoryRootError getFileTypeFileInResourceDirectoryRoot() {
                if (this.fileTypeFileInResourceDirectoryRootBuilder_ == null) {
                    if (this.customErrorCase_ == 10) {
                        return (FileTypeFilesInResourceDirectoryRootError)this.customError_;
                    }
                    return FileTypeFilesInResourceDirectoryRootError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 10) {
                    return this.fileTypeFileInResourceDirectoryRootBuilder_.getMessage();
                }
                return FileTypeFilesInResourceDirectoryRootError.getDefaultInstance();
            }

            public Builder setFileTypeFileInResourceDirectoryRoot(FileTypeFilesInResourceDirectoryRootError value) {
                if (this.fileTypeFileInResourceDirectoryRootBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.fileTypeFileInResourceDirectoryRootBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 10;
                return this;
            }

            public Builder setFileTypeFileInResourceDirectoryRoot(FileTypeFilesInResourceDirectoryRootError.Builder builderForValue) {
                if (this.fileTypeFileInResourceDirectoryRootBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.fileTypeFileInResourceDirectoryRootBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 10;
                return this;
            }

            public Builder mergeFileTypeFileInResourceDirectoryRoot(FileTypeFilesInResourceDirectoryRootError value) {
                if (this.fileTypeFileInResourceDirectoryRootBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 10 && this.customError_ != FileTypeFilesInResourceDirectoryRootError.getDefaultInstance() ? FileTypeFilesInResourceDirectoryRootError.newBuilder((FileTypeFilesInResourceDirectoryRootError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 10) {
                        this.fileTypeFileInResourceDirectoryRootBuilder_.mergeFrom(value);
                    }
                    this.fileTypeFileInResourceDirectoryRootBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 10;
                return this;
            }

            public Builder clearFileTypeFileInResourceDirectoryRoot() {
                if (this.fileTypeFileInResourceDirectoryRootBuilder_ == null) {
                    if (this.customErrorCase_ == 10) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 10) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.fileTypeFileInResourceDirectoryRootBuilder_.clear();
                }
                return this;
            }

            public FileTypeFilesInResourceDirectoryRootError.Builder getFileTypeFileInResourceDirectoryRootBuilder() {
                return this.getFileTypeFileInResourceDirectoryRootFieldBuilder().getBuilder();
            }

            @Override
            public FileTypeFilesInResourceDirectoryRootErrorOrBuilder getFileTypeFileInResourceDirectoryRootOrBuilder() {
                if (this.customErrorCase_ == 10 && this.fileTypeFileInResourceDirectoryRootBuilder_ != null) {
                    return this.fileTypeFileInResourceDirectoryRootBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 10) {
                    return (FileTypeFilesInResourceDirectoryRootError)this.customError_;
                }
                return FileTypeFilesInResourceDirectoryRootError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FileTypeFilesInResourceDirectoryRootError, FileTypeFilesInResourceDirectoryRootError.Builder, FileTypeFilesInResourceDirectoryRootErrorOrBuilder> getFileTypeFileInResourceDirectoryRootFieldBuilder() {
                if (this.fileTypeFileInResourceDirectoryRootBuilder_ == null) {
                    if (this.customErrorCase_ != 10) {
                        this.customError_ = FileTypeFilesInResourceDirectoryRootError.getDefaultInstance();
                    }
                    this.fileTypeFileInResourceDirectoryRootBuilder_ = new SingleFieldBuilderV3((FileTypeFilesInResourceDirectoryRootError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 10;
                this.onChanged();
                return this.fileTypeFileInResourceDirectoryRootBuilder_;
            }

            @Override
            public boolean hasFileTypeUnknownFileOrDirectoryInModule() {
                return this.customErrorCase_ == 11;
            }

            @Override
            public FileTypeUnknownFileOrDirectoryFoundInModuleError getFileTypeUnknownFileOrDirectoryInModule() {
                if (this.fileTypeUnknownFileOrDirectoryInModuleBuilder_ == null) {
                    if (this.customErrorCase_ == 11) {
                        return (FileTypeUnknownFileOrDirectoryFoundInModuleError)this.customError_;
                    }
                    return FileTypeUnknownFileOrDirectoryFoundInModuleError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 11) {
                    return this.fileTypeUnknownFileOrDirectoryInModuleBuilder_.getMessage();
                }
                return FileTypeUnknownFileOrDirectoryFoundInModuleError.getDefaultInstance();
            }

            public Builder setFileTypeUnknownFileOrDirectoryInModule(FileTypeUnknownFileOrDirectoryFoundInModuleError value) {
                if (this.fileTypeUnknownFileOrDirectoryInModuleBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.fileTypeUnknownFileOrDirectoryInModuleBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 11;
                return this;
            }

            public Builder setFileTypeUnknownFileOrDirectoryInModule(FileTypeUnknownFileOrDirectoryFoundInModuleError.Builder builderForValue) {
                if (this.fileTypeUnknownFileOrDirectoryInModuleBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.fileTypeUnknownFileOrDirectoryInModuleBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 11;
                return this;
            }

            public Builder mergeFileTypeUnknownFileOrDirectoryInModule(FileTypeUnknownFileOrDirectoryFoundInModuleError value) {
                if (this.fileTypeUnknownFileOrDirectoryInModuleBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 11 && this.customError_ != FileTypeUnknownFileOrDirectoryFoundInModuleError.getDefaultInstance() ? FileTypeUnknownFileOrDirectoryFoundInModuleError.newBuilder((FileTypeUnknownFileOrDirectoryFoundInModuleError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 11) {
                        this.fileTypeUnknownFileOrDirectoryInModuleBuilder_.mergeFrom(value);
                    }
                    this.fileTypeUnknownFileOrDirectoryInModuleBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 11;
                return this;
            }

            public Builder clearFileTypeUnknownFileOrDirectoryInModule() {
                if (this.fileTypeUnknownFileOrDirectoryInModuleBuilder_ == null) {
                    if (this.customErrorCase_ == 11) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 11) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.fileTypeUnknownFileOrDirectoryInModuleBuilder_.clear();
                }
                return this;
            }

            public FileTypeUnknownFileOrDirectoryFoundInModuleError.Builder getFileTypeUnknownFileOrDirectoryInModuleBuilder() {
                return this.getFileTypeUnknownFileOrDirectoryInModuleFieldBuilder().getBuilder();
            }

            @Override
            public FileTypeUnknownFileOrDirectoryFoundInModuleErrorOrBuilder getFileTypeUnknownFileOrDirectoryInModuleOrBuilder() {
                if (this.customErrorCase_ == 11 && this.fileTypeUnknownFileOrDirectoryInModuleBuilder_ != null) {
                    return this.fileTypeUnknownFileOrDirectoryInModuleBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 11) {
                    return (FileTypeUnknownFileOrDirectoryFoundInModuleError)this.customError_;
                }
                return FileTypeUnknownFileOrDirectoryFoundInModuleError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FileTypeUnknownFileOrDirectoryFoundInModuleError, FileTypeUnknownFileOrDirectoryFoundInModuleError.Builder, FileTypeUnknownFileOrDirectoryFoundInModuleErrorOrBuilder> getFileTypeUnknownFileOrDirectoryInModuleFieldBuilder() {
                if (this.fileTypeUnknownFileOrDirectoryInModuleBuilder_ == null) {
                    if (this.customErrorCase_ != 11) {
                        this.customError_ = FileTypeUnknownFileOrDirectoryFoundInModuleError.getDefaultInstance();
                    }
                    this.fileTypeUnknownFileOrDirectoryInModuleBuilder_ = new SingleFieldBuilderV3((FileTypeUnknownFileOrDirectoryFoundInModuleError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 11;
                this.onChanged();
                return this.fileTypeUnknownFileOrDirectoryInModuleBuilder_;
            }

            @Override
            public boolean hasFileTypeFileUsesReservedName() {
                return this.customErrorCase_ == 12;
            }

            @Override
            public FileTypeFileUsesReservedNameError getFileTypeFileUsesReservedName() {
                if (this.fileTypeFileUsesReservedNameBuilder_ == null) {
                    if (this.customErrorCase_ == 12) {
                        return (FileTypeFileUsesReservedNameError)this.customError_;
                    }
                    return FileTypeFileUsesReservedNameError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 12) {
                    return this.fileTypeFileUsesReservedNameBuilder_.getMessage();
                }
                return FileTypeFileUsesReservedNameError.getDefaultInstance();
            }

            public Builder setFileTypeFileUsesReservedName(FileTypeFileUsesReservedNameError value) {
                if (this.fileTypeFileUsesReservedNameBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.fileTypeFileUsesReservedNameBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 12;
                return this;
            }

            public Builder setFileTypeFileUsesReservedName(FileTypeFileUsesReservedNameError.Builder builderForValue) {
                if (this.fileTypeFileUsesReservedNameBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.fileTypeFileUsesReservedNameBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 12;
                return this;
            }

            public Builder mergeFileTypeFileUsesReservedName(FileTypeFileUsesReservedNameError value) {
                if (this.fileTypeFileUsesReservedNameBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 12 && this.customError_ != FileTypeFileUsesReservedNameError.getDefaultInstance() ? FileTypeFileUsesReservedNameError.newBuilder((FileTypeFileUsesReservedNameError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 12) {
                        this.fileTypeFileUsesReservedNameBuilder_.mergeFrom(value);
                    }
                    this.fileTypeFileUsesReservedNameBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 12;
                return this;
            }

            public Builder clearFileTypeFileUsesReservedName() {
                if (this.fileTypeFileUsesReservedNameBuilder_ == null) {
                    if (this.customErrorCase_ == 12) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 12) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.fileTypeFileUsesReservedNameBuilder_.clear();
                }
                return this;
            }

            public FileTypeFileUsesReservedNameError.Builder getFileTypeFileUsesReservedNameBuilder() {
                return this.getFileTypeFileUsesReservedNameFieldBuilder().getBuilder();
            }

            @Override
            public FileTypeFileUsesReservedNameErrorOrBuilder getFileTypeFileUsesReservedNameOrBuilder() {
                if (this.customErrorCase_ == 12 && this.fileTypeFileUsesReservedNameBuilder_ != null) {
                    return this.fileTypeFileUsesReservedNameBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 12) {
                    return (FileTypeFileUsesReservedNameError)this.customError_;
                }
                return FileTypeFileUsesReservedNameError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FileTypeFileUsesReservedNameError, FileTypeFileUsesReservedNameError.Builder, FileTypeFileUsesReservedNameErrorOrBuilder> getFileTypeFileUsesReservedNameFieldBuilder() {
                if (this.fileTypeFileUsesReservedNameBuilder_ == null) {
                    if (this.customErrorCase_ != 12) {
                        this.customError_ = FileTypeFileUsesReservedNameError.getDefaultInstance();
                    }
                    this.fileTypeFileUsesReservedNameBuilder_ = new SingleFieldBuilderV3((FileTypeFileUsesReservedNameError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 12;
                this.onChanged();
                return this.fileTypeFileUsesReservedNameBuilder_;
            }

            @Override
            public boolean hasFileTypeDirectoryInBundle() {
                return this.customErrorCase_ == 18;
            }

            @Override
            public FileTypeDirectoryInBundleError getFileTypeDirectoryInBundle() {
                if (this.fileTypeDirectoryInBundleBuilder_ == null) {
                    if (this.customErrorCase_ == 18) {
                        return (FileTypeDirectoryInBundleError)this.customError_;
                    }
                    return FileTypeDirectoryInBundleError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 18) {
                    return this.fileTypeDirectoryInBundleBuilder_.getMessage();
                }
                return FileTypeDirectoryInBundleError.getDefaultInstance();
            }

            public Builder setFileTypeDirectoryInBundle(FileTypeDirectoryInBundleError value) {
                if (this.fileTypeDirectoryInBundleBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.fileTypeDirectoryInBundleBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 18;
                return this;
            }

            public Builder setFileTypeDirectoryInBundle(FileTypeDirectoryInBundleError.Builder builderForValue) {
                if (this.fileTypeDirectoryInBundleBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.fileTypeDirectoryInBundleBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 18;
                return this;
            }

            public Builder mergeFileTypeDirectoryInBundle(FileTypeDirectoryInBundleError value) {
                if (this.fileTypeDirectoryInBundleBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 18 && this.customError_ != FileTypeDirectoryInBundleError.getDefaultInstance() ? FileTypeDirectoryInBundleError.newBuilder((FileTypeDirectoryInBundleError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 18) {
                        this.fileTypeDirectoryInBundleBuilder_.mergeFrom(value);
                    }
                    this.fileTypeDirectoryInBundleBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 18;
                return this;
            }

            public Builder clearFileTypeDirectoryInBundle() {
                if (this.fileTypeDirectoryInBundleBuilder_ == null) {
                    if (this.customErrorCase_ == 18) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 18) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.fileTypeDirectoryInBundleBuilder_.clear();
                }
                return this;
            }

            public FileTypeDirectoryInBundleError.Builder getFileTypeDirectoryInBundleBuilder() {
                return this.getFileTypeDirectoryInBundleFieldBuilder().getBuilder();
            }

            @Override
            public FileTypeDirectoryInBundleErrorOrBuilder getFileTypeDirectoryInBundleOrBuilder() {
                if (this.customErrorCase_ == 18 && this.fileTypeDirectoryInBundleBuilder_ != null) {
                    return this.fileTypeDirectoryInBundleBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 18) {
                    return (FileTypeDirectoryInBundleError)this.customError_;
                }
                return FileTypeDirectoryInBundleError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FileTypeDirectoryInBundleError, FileTypeDirectoryInBundleError.Builder, FileTypeDirectoryInBundleErrorOrBuilder> getFileTypeDirectoryInBundleFieldBuilder() {
                if (this.fileTypeDirectoryInBundleBuilder_ == null) {
                    if (this.customErrorCase_ != 18) {
                        this.customError_ = FileTypeDirectoryInBundleError.getDefaultInstance();
                    }
                    this.fileTypeDirectoryInBundleBuilder_ = new SingleFieldBuilderV3((FileTypeDirectoryInBundleError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 18;
                this.onChanged();
                return this.fileTypeDirectoryInBundleBuilder_;
            }

            @Override
            public boolean hasFileTypeInvalidApexImagePath() {
                return this.customErrorCase_ == 26;
            }

            @Override
            public FileTypeInvalidApexImagePathError getFileTypeInvalidApexImagePath() {
                if (this.fileTypeInvalidApexImagePathBuilder_ == null) {
                    if (this.customErrorCase_ == 26) {
                        return (FileTypeInvalidApexImagePathError)this.customError_;
                    }
                    return FileTypeInvalidApexImagePathError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 26) {
                    return this.fileTypeInvalidApexImagePathBuilder_.getMessage();
                }
                return FileTypeInvalidApexImagePathError.getDefaultInstance();
            }

            public Builder setFileTypeInvalidApexImagePath(FileTypeInvalidApexImagePathError value) {
                if (this.fileTypeInvalidApexImagePathBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.fileTypeInvalidApexImagePathBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 26;
                return this;
            }

            public Builder setFileTypeInvalidApexImagePath(FileTypeInvalidApexImagePathError.Builder builderForValue) {
                if (this.fileTypeInvalidApexImagePathBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.fileTypeInvalidApexImagePathBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 26;
                return this;
            }

            public Builder mergeFileTypeInvalidApexImagePath(FileTypeInvalidApexImagePathError value) {
                if (this.fileTypeInvalidApexImagePathBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 26 && this.customError_ != FileTypeInvalidApexImagePathError.getDefaultInstance() ? FileTypeInvalidApexImagePathError.newBuilder((FileTypeInvalidApexImagePathError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 26) {
                        this.fileTypeInvalidApexImagePathBuilder_.mergeFrom(value);
                    }
                    this.fileTypeInvalidApexImagePathBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 26;
                return this;
            }

            public Builder clearFileTypeInvalidApexImagePath() {
                if (this.fileTypeInvalidApexImagePathBuilder_ == null) {
                    if (this.customErrorCase_ == 26) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 26) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.fileTypeInvalidApexImagePathBuilder_.clear();
                }
                return this;
            }

            public FileTypeInvalidApexImagePathError.Builder getFileTypeInvalidApexImagePathBuilder() {
                return this.getFileTypeInvalidApexImagePathFieldBuilder().getBuilder();
            }

            @Override
            public FileTypeInvalidApexImagePathErrorOrBuilder getFileTypeInvalidApexImagePathOrBuilder() {
                if (this.customErrorCase_ == 26 && this.fileTypeInvalidApexImagePathBuilder_ != null) {
                    return this.fileTypeInvalidApexImagePathBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 26) {
                    return (FileTypeInvalidApexImagePathError)this.customError_;
                }
                return FileTypeInvalidApexImagePathError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<FileTypeInvalidApexImagePathError, FileTypeInvalidApexImagePathError.Builder, FileTypeInvalidApexImagePathErrorOrBuilder> getFileTypeInvalidApexImagePathFieldBuilder() {
                if (this.fileTypeInvalidApexImagePathBuilder_ == null) {
                    if (this.customErrorCase_ != 26) {
                        this.customError_ = FileTypeInvalidApexImagePathError.getDefaultInstance();
                    }
                    this.fileTypeInvalidApexImagePathBuilder_ = new SingleFieldBuilderV3((FileTypeInvalidApexImagePathError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 26;
                this.onChanged();
                return this.fileTypeInvalidApexImagePathBuilder_;
            }

            @Override
            public boolean hasMandatoryBundleFileMissing() {
                return this.customErrorCase_ == 14;
            }

            @Override
            public MandatoryBundleFileMissingError getMandatoryBundleFileMissing() {
                if (this.mandatoryBundleFileMissingBuilder_ == null) {
                    if (this.customErrorCase_ == 14) {
                        return (MandatoryBundleFileMissingError)this.customError_;
                    }
                    return MandatoryBundleFileMissingError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 14) {
                    return this.mandatoryBundleFileMissingBuilder_.getMessage();
                }
                return MandatoryBundleFileMissingError.getDefaultInstance();
            }

            public Builder setMandatoryBundleFileMissing(MandatoryBundleFileMissingError value) {
                if (this.mandatoryBundleFileMissingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.mandatoryBundleFileMissingBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 14;
                return this;
            }

            public Builder setMandatoryBundleFileMissing(MandatoryBundleFileMissingError.Builder builderForValue) {
                if (this.mandatoryBundleFileMissingBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.mandatoryBundleFileMissingBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 14;
                return this;
            }

            public Builder mergeMandatoryBundleFileMissing(MandatoryBundleFileMissingError value) {
                if (this.mandatoryBundleFileMissingBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 14 && this.customError_ != MandatoryBundleFileMissingError.getDefaultInstance() ? MandatoryBundleFileMissingError.newBuilder((MandatoryBundleFileMissingError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 14) {
                        this.mandatoryBundleFileMissingBuilder_.mergeFrom(value);
                    }
                    this.mandatoryBundleFileMissingBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 14;
                return this;
            }

            public Builder clearMandatoryBundleFileMissing() {
                if (this.mandatoryBundleFileMissingBuilder_ == null) {
                    if (this.customErrorCase_ == 14) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 14) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.mandatoryBundleFileMissingBuilder_.clear();
                }
                return this;
            }

            public MandatoryBundleFileMissingError.Builder getMandatoryBundleFileMissingBuilder() {
                return this.getMandatoryBundleFileMissingFieldBuilder().getBuilder();
            }

            @Override
            public MandatoryBundleFileMissingErrorOrBuilder getMandatoryBundleFileMissingOrBuilder() {
                if (this.customErrorCase_ == 14 && this.mandatoryBundleFileMissingBuilder_ != null) {
                    return this.mandatoryBundleFileMissingBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 14) {
                    return (MandatoryBundleFileMissingError)this.customError_;
                }
                return MandatoryBundleFileMissingError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MandatoryBundleFileMissingError, MandatoryBundleFileMissingError.Builder, MandatoryBundleFileMissingErrorOrBuilder> getMandatoryBundleFileMissingFieldBuilder() {
                if (this.mandatoryBundleFileMissingBuilder_ == null) {
                    if (this.customErrorCase_ != 14) {
                        this.customError_ = MandatoryBundleFileMissingError.getDefaultInstance();
                    }
                    this.mandatoryBundleFileMissingBuilder_ = new SingleFieldBuilderV3((MandatoryBundleFileMissingError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 14;
                this.onChanged();
                return this.mandatoryBundleFileMissingBuilder_;
            }

            @Override
            public boolean hasMandatoryModuleFileMissing() {
                return this.customErrorCase_ == 13;
            }

            @Override
            public MandatoryModuleFileMissingError getMandatoryModuleFileMissing() {
                if (this.mandatoryModuleFileMissingBuilder_ == null) {
                    if (this.customErrorCase_ == 13) {
                        return (MandatoryModuleFileMissingError)this.customError_;
                    }
                    return MandatoryModuleFileMissingError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 13) {
                    return this.mandatoryModuleFileMissingBuilder_.getMessage();
                }
                return MandatoryModuleFileMissingError.getDefaultInstance();
            }

            public Builder setMandatoryModuleFileMissing(MandatoryModuleFileMissingError value) {
                if (this.mandatoryModuleFileMissingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.mandatoryModuleFileMissingBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 13;
                return this;
            }

            public Builder setMandatoryModuleFileMissing(MandatoryModuleFileMissingError.Builder builderForValue) {
                if (this.mandatoryModuleFileMissingBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.mandatoryModuleFileMissingBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 13;
                return this;
            }

            public Builder mergeMandatoryModuleFileMissing(MandatoryModuleFileMissingError value) {
                if (this.mandatoryModuleFileMissingBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 13 && this.customError_ != MandatoryModuleFileMissingError.getDefaultInstance() ? MandatoryModuleFileMissingError.newBuilder((MandatoryModuleFileMissingError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 13) {
                        this.mandatoryModuleFileMissingBuilder_.mergeFrom(value);
                    }
                    this.mandatoryModuleFileMissingBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 13;
                return this;
            }

            public Builder clearMandatoryModuleFileMissing() {
                if (this.mandatoryModuleFileMissingBuilder_ == null) {
                    if (this.customErrorCase_ == 13) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 13) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.mandatoryModuleFileMissingBuilder_.clear();
                }
                return this;
            }

            public MandatoryModuleFileMissingError.Builder getMandatoryModuleFileMissingBuilder() {
                return this.getMandatoryModuleFileMissingFieldBuilder().getBuilder();
            }

            @Override
            public MandatoryModuleFileMissingErrorOrBuilder getMandatoryModuleFileMissingOrBuilder() {
                if (this.customErrorCase_ == 13 && this.mandatoryModuleFileMissingBuilder_ != null) {
                    return this.mandatoryModuleFileMissingBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 13) {
                    return (MandatoryModuleFileMissingError)this.customError_;
                }
                return MandatoryModuleFileMissingError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<MandatoryModuleFileMissingError, MandatoryModuleFileMissingError.Builder, MandatoryModuleFileMissingErrorOrBuilder> getMandatoryModuleFileMissingFieldBuilder() {
                if (this.mandatoryModuleFileMissingBuilder_ == null) {
                    if (this.customErrorCase_ != 13) {
                        this.customError_ = MandatoryModuleFileMissingError.getDefaultInstance();
                    }
                    this.mandatoryModuleFileMissingBuilder_ = new SingleFieldBuilderV3((MandatoryModuleFileMissingError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 13;
                this.onChanged();
                return this.mandatoryModuleFileMissingBuilder_;
            }

            @Override
            public boolean hasResourceTableReferencesFilesOutsideRes() {
                return this.customErrorCase_ == 15;
            }

            @Override
            public ResourceTableReferencesFilesOutsideResError getResourceTableReferencesFilesOutsideRes() {
                if (this.resourceTableReferencesFilesOutsideResBuilder_ == null) {
                    if (this.customErrorCase_ == 15) {
                        return (ResourceTableReferencesFilesOutsideResError)this.customError_;
                    }
                    return ResourceTableReferencesFilesOutsideResError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 15) {
                    return this.resourceTableReferencesFilesOutsideResBuilder_.getMessage();
                }
                return ResourceTableReferencesFilesOutsideResError.getDefaultInstance();
            }

            public Builder setResourceTableReferencesFilesOutsideRes(ResourceTableReferencesFilesOutsideResError value) {
                if (this.resourceTableReferencesFilesOutsideResBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.resourceTableReferencesFilesOutsideResBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 15;
                return this;
            }

            public Builder setResourceTableReferencesFilesOutsideRes(ResourceTableReferencesFilesOutsideResError.Builder builderForValue) {
                if (this.resourceTableReferencesFilesOutsideResBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.resourceTableReferencesFilesOutsideResBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 15;
                return this;
            }

            public Builder mergeResourceTableReferencesFilesOutsideRes(ResourceTableReferencesFilesOutsideResError value) {
                if (this.resourceTableReferencesFilesOutsideResBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 15 && this.customError_ != ResourceTableReferencesFilesOutsideResError.getDefaultInstance() ? ResourceTableReferencesFilesOutsideResError.newBuilder((ResourceTableReferencesFilesOutsideResError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 15) {
                        this.resourceTableReferencesFilesOutsideResBuilder_.mergeFrom(value);
                    }
                    this.resourceTableReferencesFilesOutsideResBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 15;
                return this;
            }

            public Builder clearResourceTableReferencesFilesOutsideRes() {
                if (this.resourceTableReferencesFilesOutsideResBuilder_ == null) {
                    if (this.customErrorCase_ == 15) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 15) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.resourceTableReferencesFilesOutsideResBuilder_.clear();
                }
                return this;
            }

            public ResourceTableReferencesFilesOutsideResError.Builder getResourceTableReferencesFilesOutsideResBuilder() {
                return this.getResourceTableReferencesFilesOutsideResFieldBuilder().getBuilder();
            }

            @Override
            public ResourceTableReferencesFilesOutsideResErrorOrBuilder getResourceTableReferencesFilesOutsideResOrBuilder() {
                if (this.customErrorCase_ == 15 && this.resourceTableReferencesFilesOutsideResBuilder_ != null) {
                    return this.resourceTableReferencesFilesOutsideResBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 15) {
                    return (ResourceTableReferencesFilesOutsideResError)this.customError_;
                }
                return ResourceTableReferencesFilesOutsideResError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ResourceTableReferencesFilesOutsideResError, ResourceTableReferencesFilesOutsideResError.Builder, ResourceTableReferencesFilesOutsideResErrorOrBuilder> getResourceTableReferencesFilesOutsideResFieldBuilder() {
                if (this.resourceTableReferencesFilesOutsideResBuilder_ == null) {
                    if (this.customErrorCase_ != 15) {
                        this.customError_ = ResourceTableReferencesFilesOutsideResError.getDefaultInstance();
                    }
                    this.resourceTableReferencesFilesOutsideResBuilder_ = new SingleFieldBuilderV3((ResourceTableReferencesFilesOutsideResError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 15;
                this.onChanged();
                return this.resourceTableReferencesFilesOutsideResBuilder_;
            }

            @Override
            public boolean hasResouceTableReferencesMissingFiles() {
                return this.customErrorCase_ == 16;
            }

            @Override
            public ResourceTableReferencesMissingFilesError getResouceTableReferencesMissingFiles() {
                if (this.resouceTableReferencesMissingFilesBuilder_ == null) {
                    if (this.customErrorCase_ == 16) {
                        return (ResourceTableReferencesMissingFilesError)this.customError_;
                    }
                    return ResourceTableReferencesMissingFilesError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 16) {
                    return this.resouceTableReferencesMissingFilesBuilder_.getMessage();
                }
                return ResourceTableReferencesMissingFilesError.getDefaultInstance();
            }

            public Builder setResouceTableReferencesMissingFiles(ResourceTableReferencesMissingFilesError value) {
                if (this.resouceTableReferencesMissingFilesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.resouceTableReferencesMissingFilesBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 16;
                return this;
            }

            public Builder setResouceTableReferencesMissingFiles(ResourceTableReferencesMissingFilesError.Builder builderForValue) {
                if (this.resouceTableReferencesMissingFilesBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.resouceTableReferencesMissingFilesBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 16;
                return this;
            }

            public Builder mergeResouceTableReferencesMissingFiles(ResourceTableReferencesMissingFilesError value) {
                if (this.resouceTableReferencesMissingFilesBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 16 && this.customError_ != ResourceTableReferencesMissingFilesError.getDefaultInstance() ? ResourceTableReferencesMissingFilesError.newBuilder((ResourceTableReferencesMissingFilesError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 16) {
                        this.resouceTableReferencesMissingFilesBuilder_.mergeFrom(value);
                    }
                    this.resouceTableReferencesMissingFilesBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 16;
                return this;
            }

            public Builder clearResouceTableReferencesMissingFiles() {
                if (this.resouceTableReferencesMissingFilesBuilder_ == null) {
                    if (this.customErrorCase_ == 16) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 16) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.resouceTableReferencesMissingFilesBuilder_.clear();
                }
                return this;
            }

            public ResourceTableReferencesMissingFilesError.Builder getResouceTableReferencesMissingFilesBuilder() {
                return this.getResouceTableReferencesMissingFilesFieldBuilder().getBuilder();
            }

            @Override
            public ResourceTableReferencesMissingFilesErrorOrBuilder getResouceTableReferencesMissingFilesOrBuilder() {
                if (this.customErrorCase_ == 16 && this.resouceTableReferencesMissingFilesBuilder_ != null) {
                    return this.resouceTableReferencesMissingFilesBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 16) {
                    return (ResourceTableReferencesMissingFilesError)this.customError_;
                }
                return ResourceTableReferencesMissingFilesError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ResourceTableReferencesMissingFilesError, ResourceTableReferencesMissingFilesError.Builder, ResourceTableReferencesMissingFilesErrorOrBuilder> getResouceTableReferencesMissingFilesFieldBuilder() {
                if (this.resouceTableReferencesMissingFilesBuilder_ == null) {
                    if (this.customErrorCase_ != 16) {
                        this.customError_ = ResourceTableReferencesMissingFilesError.getDefaultInstance();
                    }
                    this.resouceTableReferencesMissingFilesBuilder_ = new SingleFieldBuilderV3((ResourceTableReferencesMissingFilesError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 16;
                this.onChanged();
                return this.resouceTableReferencesMissingFilesBuilder_;
            }

            @Override
            public boolean hasResouceTableUnreferencedFiles() {
                return this.customErrorCase_ == 17;
            }

            @Override
            public ResourceTableUnreferencedFilesError getResouceTableUnreferencedFiles() {
                if (this.resouceTableUnreferencedFilesBuilder_ == null) {
                    if (this.customErrorCase_ == 17) {
                        return (ResourceTableUnreferencedFilesError)this.customError_;
                    }
                    return ResourceTableUnreferencedFilesError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 17) {
                    return this.resouceTableUnreferencedFilesBuilder_.getMessage();
                }
                return ResourceTableUnreferencedFilesError.getDefaultInstance();
            }

            public Builder setResouceTableUnreferencedFiles(ResourceTableUnreferencedFilesError value) {
                if (this.resouceTableUnreferencedFilesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.resouceTableUnreferencedFilesBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 17;
                return this;
            }

            public Builder setResouceTableUnreferencedFiles(ResourceTableUnreferencedFilesError.Builder builderForValue) {
                if (this.resouceTableUnreferencedFilesBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.resouceTableUnreferencedFilesBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 17;
                return this;
            }

            public Builder mergeResouceTableUnreferencedFiles(ResourceTableUnreferencedFilesError value) {
                if (this.resouceTableUnreferencedFilesBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 17 && this.customError_ != ResourceTableUnreferencedFilesError.getDefaultInstance() ? ResourceTableUnreferencedFilesError.newBuilder((ResourceTableUnreferencedFilesError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 17) {
                        this.resouceTableUnreferencedFilesBuilder_.mergeFrom(value);
                    }
                    this.resouceTableUnreferencedFilesBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 17;
                return this;
            }

            public Builder clearResouceTableUnreferencedFiles() {
                if (this.resouceTableUnreferencedFilesBuilder_ == null) {
                    if (this.customErrorCase_ == 17) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 17) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.resouceTableUnreferencedFilesBuilder_.clear();
                }
                return this;
            }

            public ResourceTableUnreferencedFilesError.Builder getResouceTableUnreferencedFilesBuilder() {
                return this.getResouceTableUnreferencedFilesFieldBuilder().getBuilder();
            }

            @Override
            public ResourceTableUnreferencedFilesErrorOrBuilder getResouceTableUnreferencedFilesOrBuilder() {
                if (this.customErrorCase_ == 17 && this.resouceTableUnreferencedFilesBuilder_ != null) {
                    return this.resouceTableUnreferencedFilesBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 17) {
                    return (ResourceTableUnreferencedFilesError)this.customError_;
                }
                return ResourceTableUnreferencedFilesError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ResourceTableUnreferencedFilesError, ResourceTableUnreferencedFilesError.Builder, ResourceTableUnreferencedFilesErrorOrBuilder> getResouceTableUnreferencedFilesFieldBuilder() {
                if (this.resouceTableUnreferencedFilesBuilder_ == null) {
                    if (this.customErrorCase_ != 17) {
                        this.customError_ = ResourceTableUnreferencedFilesError.getDefaultInstance();
                    }
                    this.resouceTableUnreferencedFilesBuilder_ = new SingleFieldBuilderV3((ResourceTableUnreferencedFilesError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 17;
                this.onChanged();
                return this.resouceTableUnreferencedFilesBuilder_;
            }

            @Override
            public boolean hasResourceTableMissing() {
                return this.customErrorCase_ == 24;
            }

            @Override
            public ResourceTableMissingError getResourceTableMissing() {
                if (this.resourceTableMissingBuilder_ == null) {
                    if (this.customErrorCase_ == 24) {
                        return (ResourceTableMissingError)this.customError_;
                    }
                    return ResourceTableMissingError.getDefaultInstance();
                }
                if (this.customErrorCase_ == 24) {
                    return this.resourceTableMissingBuilder_.getMessage();
                }
                return ResourceTableMissingError.getDefaultInstance();
            }

            public Builder setResourceTableMissing(ResourceTableMissingError value) {
                if (this.resourceTableMissingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.customError_ = value;
                    this.onChanged();
                } else {
                    this.resourceTableMissingBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 24;
                return this;
            }

            public Builder setResourceTableMissing(ResourceTableMissingError.Builder builderForValue) {
                if (this.resourceTableMissingBuilder_ == null) {
                    this.customError_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.resourceTableMissingBuilder_.setMessage(builderForValue.build());
                }
                this.customErrorCase_ = 24;
                return this;
            }

            public Builder mergeResourceTableMissing(ResourceTableMissingError value) {
                if (this.resourceTableMissingBuilder_ == null) {
                    this.customError_ = this.customErrorCase_ == 24 && this.customError_ != ResourceTableMissingError.getDefaultInstance() ? ResourceTableMissingError.newBuilder((ResourceTableMissingError)this.customError_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    if (this.customErrorCase_ == 24) {
                        this.resourceTableMissingBuilder_.mergeFrom(value);
                    }
                    this.resourceTableMissingBuilder_.setMessage(value);
                }
                this.customErrorCase_ = 24;
                return this;
            }

            public Builder clearResourceTableMissing() {
                if (this.resourceTableMissingBuilder_ == null) {
                    if (this.customErrorCase_ == 24) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                        this.onChanged();
                    }
                } else {
                    if (this.customErrorCase_ == 24) {
                        this.customErrorCase_ = 0;
                        this.customError_ = null;
                    }
                    this.resourceTableMissingBuilder_.clear();
                }
                return this;
            }

            public ResourceTableMissingError.Builder getResourceTableMissingBuilder() {
                return this.getResourceTableMissingFieldBuilder().getBuilder();
            }

            @Override
            public ResourceTableMissingErrorOrBuilder getResourceTableMissingOrBuilder() {
                if (this.customErrorCase_ == 24 && this.resourceTableMissingBuilder_ != null) {
                    return this.resourceTableMissingBuilder_.getMessageOrBuilder();
                }
                if (this.customErrorCase_ == 24) {
                    return (ResourceTableMissingError)this.customError_;
                }
                return ResourceTableMissingError.getDefaultInstance();
            }

            private SingleFieldBuilderV3<ResourceTableMissingError, ResourceTableMissingError.Builder, ResourceTableMissingErrorOrBuilder> getResourceTableMissingFieldBuilder() {
                if (this.resourceTableMissingBuilder_ == null) {
                    if (this.customErrorCase_ != 24) {
                        this.customError_ = ResourceTableMissingError.getDefaultInstance();
                    }
                    this.resourceTableMissingBuilder_ = new SingleFieldBuilderV3((ResourceTableMissingError)this.customError_, this.getParentForChildren(), this.isClean());
                    this.customError_ = null;
                }
                this.customErrorCase_ = 24;
                this.onChanged();
                return this.resourceTableMissingBuilder_;
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

        public static enum CustomErrorCase implements Internal.EnumLite
        {
            MANIFEST_MISSING_VERSION_CODE(2),
            MANIFEST_INVALID_VERSION_CODE(3),
            MANIFEST_FUSING_BASE_MODULE_EXCLUDED(4),
            MANIFEST_FUSING_CONFIGURATION_MISSING(5),
            MANIFEST_FUSING_MISSING_INCLUDE_ATTRIBUTE(21),
            MANIFEST_MAX_SDK_INVALID(22),
            MANIFEST_MAX_SDK_LESS_THAN_MIN_INSTANT_SDK(23),
            MANIFEST_MIN_SDK_INVALID(19),
            MANIFEST_MIN_SDK_GREATER_THAN_MAX(20),
            MANIFEST_DUPLICATE_ATTRIBUTE(25),
            MANIFEST_MODULES_DIFFERENT_VERSION_CODES(27),
            FILE_TYPE_INVALID_FILE_EXTENSION(6),
            FILE_TYPE_INVALID_FILE_NAME(7),
            FILE_TYPE_INVALID_NATIVE_LIBRARY_PATH(8),
            FILE_TYPE_INVALID_NATIVE_ARCHITECTURE(9),
            FILE_TYPE_FILE_IN_RESOURCE_DIRECTORY_ROOT(10),
            FILE_TYPE_UNKNOWN_FILE_OR_DIRECTORY_IN_MODULE(11),
            FILE_TYPE_FILE_USES_RESERVED_NAME(12),
            FILE_TYPE_DIRECTORY_IN_BUNDLE(18),
            FILE_TYPE_INVALID_APEX_IMAGE_PATH(26),
            MANDATORY_BUNDLE_FILE_MISSING(14),
            MANDATORY_MODULE_FILE_MISSING(13),
            RESOURCE_TABLE_REFERENCES_FILES_OUTSIDE_RES(15),
            RESOUCE_TABLE_REFERENCES_MISSING_FILES(16),
            RESOUCE_TABLE_UNREFERENCED_FILES(17),
            RESOURCE_TABLE_MISSING(24),
            CUSTOMERROR_NOT_SET(0);

            private final int value;

            private CustomErrorCase(int value) {
                this.value = value;
            }

            @Deprecated
            public static CustomErrorCase valueOf(int value) {
                return CustomErrorCase.forNumber(value);
            }

            public static CustomErrorCase forNumber(int value) {
                switch (value) {
                    case 2: {
                        return MANIFEST_MISSING_VERSION_CODE;
                    }
                    case 3: {
                        return MANIFEST_INVALID_VERSION_CODE;
                    }
                    case 4: {
                        return MANIFEST_FUSING_BASE_MODULE_EXCLUDED;
                    }
                    case 5: {
                        return MANIFEST_FUSING_CONFIGURATION_MISSING;
                    }
                    case 21: {
                        return MANIFEST_FUSING_MISSING_INCLUDE_ATTRIBUTE;
                    }
                    case 22: {
                        return MANIFEST_MAX_SDK_INVALID;
                    }
                    case 23: {
                        return MANIFEST_MAX_SDK_LESS_THAN_MIN_INSTANT_SDK;
                    }
                    case 19: {
                        return MANIFEST_MIN_SDK_INVALID;
                    }
                    case 20: {
                        return MANIFEST_MIN_SDK_GREATER_THAN_MAX;
                    }
                    case 25: {
                        return MANIFEST_DUPLICATE_ATTRIBUTE;
                    }
                    case 27: {
                        return MANIFEST_MODULES_DIFFERENT_VERSION_CODES;
                    }
                    case 6: {
                        return FILE_TYPE_INVALID_FILE_EXTENSION;
                    }
                    case 7: {
                        return FILE_TYPE_INVALID_FILE_NAME;
                    }
                    case 8: {
                        return FILE_TYPE_INVALID_NATIVE_LIBRARY_PATH;
                    }
                    case 9: {
                        return FILE_TYPE_INVALID_NATIVE_ARCHITECTURE;
                    }
                    case 10: {
                        return FILE_TYPE_FILE_IN_RESOURCE_DIRECTORY_ROOT;
                    }
                    case 11: {
                        return FILE_TYPE_UNKNOWN_FILE_OR_DIRECTORY_IN_MODULE;
                    }
                    case 12: {
                        return FILE_TYPE_FILE_USES_RESERVED_NAME;
                    }
                    case 18: {
                        return FILE_TYPE_DIRECTORY_IN_BUNDLE;
                    }
                    case 26: {
                        return FILE_TYPE_INVALID_APEX_IMAGE_PATH;
                    }
                    case 14: {
                        return MANDATORY_BUNDLE_FILE_MISSING;
                    }
                    case 13: {
                        return MANDATORY_MODULE_FILE_MISSING;
                    }
                    case 15: {
                        return RESOURCE_TABLE_REFERENCES_FILES_OUTSIDE_RES;
                    }
                    case 16: {
                        return RESOUCE_TABLE_REFERENCES_MISSING_FILES;
                    }
                    case 17: {
                        return RESOUCE_TABLE_UNREFERENCED_FILES;
                    }
                    case 24: {
                        return RESOURCE_TABLE_MISSING;
                    }
                    case 0: {
                        return CUSTOMERROR_NOT_SET;
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

    public static interface BundleToolErrorOrBuilder
    extends MessageOrBuilder {
        public String getExceptionMessage();

        public ByteString getExceptionMessageBytes();

        public boolean hasManifestMissingVersionCode();

        public ManifestMissingVersionCodeError getManifestMissingVersionCode();

        public ManifestMissingVersionCodeErrorOrBuilder getManifestMissingVersionCodeOrBuilder();

        public boolean hasManifestInvalidVersionCode();

        public ManifestInvalidVersionCodeError getManifestInvalidVersionCode();

        public ManifestInvalidVersionCodeErrorOrBuilder getManifestInvalidVersionCodeOrBuilder();

        public boolean hasManifestFusingBaseModuleExcluded();

        public ManifestBaseModuleExcludedFromFusingError getManifestFusingBaseModuleExcluded();

        public ManifestBaseModuleExcludedFromFusingErrorOrBuilder getManifestFusingBaseModuleExcludedOrBuilder();

        public boolean hasManifestFusingConfigurationMissing();

        public ManifestModuleFusingConfigurationMissingError getManifestFusingConfigurationMissing();

        public ManifestModuleFusingConfigurationMissingErrorOrBuilder getManifestFusingConfigurationMissingOrBuilder();

        public boolean hasManifestFusingMissingIncludeAttribute();

        public ManifestFusingMissingIncludeAttributeError getManifestFusingMissingIncludeAttribute();

        public ManifestFusingMissingIncludeAttributeErrorOrBuilder getManifestFusingMissingIncludeAttributeOrBuilder();

        public boolean hasManifestMaxSdkInvalid();

        public ManifestMaxSdkInvalidError getManifestMaxSdkInvalid();

        public ManifestMaxSdkInvalidErrorOrBuilder getManifestMaxSdkInvalidOrBuilder();

        public boolean hasManifestMaxSdkLessThanMinInstantSdk();

        public ManifestMaxSdkLessThanMinInstantSdkError getManifestMaxSdkLessThanMinInstantSdk();

        public ManifestMaxSdkLessThanMinInstantSdkErrorOrBuilder getManifestMaxSdkLessThanMinInstantSdkOrBuilder();

        public boolean hasManifestMinSdkInvalid();

        public ManifestMinSdkInvalidError getManifestMinSdkInvalid();

        public ManifestMinSdkInvalidErrorOrBuilder getManifestMinSdkInvalidOrBuilder();

        public boolean hasManifestMinSdkGreaterThanMax();

        public ManifestMinSdkGreaterThanMaxSdkError getManifestMinSdkGreaterThanMax();

        public ManifestMinSdkGreaterThanMaxSdkErrorOrBuilder getManifestMinSdkGreaterThanMaxOrBuilder();

        public boolean hasManifestDuplicateAttribute();

        public ManifestDuplicateAttributeError getManifestDuplicateAttribute();

        public ManifestDuplicateAttributeErrorOrBuilder getManifestDuplicateAttributeOrBuilder();

        public boolean hasManifestModulesDifferentVersionCodes();

        public ManifestModulesDifferentVersionCodes getManifestModulesDifferentVersionCodes();

        public ManifestModulesDifferentVersionCodesOrBuilder getManifestModulesDifferentVersionCodesOrBuilder();

        public boolean hasFileTypeInvalidFileExtension();

        public FileTypeInvalidFileExtensionError getFileTypeInvalidFileExtension();

        public FileTypeInvalidFileExtensionErrorOrBuilder getFileTypeInvalidFileExtensionOrBuilder();

        public boolean hasFileTypeInvalidFileName();

        public FileTypeInvalidFileNameInDirectoryError getFileTypeInvalidFileName();

        public FileTypeInvalidFileNameInDirectoryErrorOrBuilder getFileTypeInvalidFileNameOrBuilder();

        public boolean hasFileTypeInvalidNativeLibraryPath();

        public FileTypeInvalidNativeLibraryPathError getFileTypeInvalidNativeLibraryPath();

        public FileTypeInvalidNativeLibraryPathErrorOrBuilder getFileTypeInvalidNativeLibraryPathOrBuilder();

        public boolean hasFileTypeInvalidNativeArchitecture();

        public FileTypeInvalidNativeArchitectureError getFileTypeInvalidNativeArchitecture();

        public FileTypeInvalidNativeArchitectureErrorOrBuilder getFileTypeInvalidNativeArchitectureOrBuilder();

        public boolean hasFileTypeFileInResourceDirectoryRoot();

        public FileTypeFilesInResourceDirectoryRootError getFileTypeFileInResourceDirectoryRoot();

        public FileTypeFilesInResourceDirectoryRootErrorOrBuilder getFileTypeFileInResourceDirectoryRootOrBuilder();

        public boolean hasFileTypeUnknownFileOrDirectoryInModule();

        public FileTypeUnknownFileOrDirectoryFoundInModuleError getFileTypeUnknownFileOrDirectoryInModule();

        public FileTypeUnknownFileOrDirectoryFoundInModuleErrorOrBuilder getFileTypeUnknownFileOrDirectoryInModuleOrBuilder();

        public boolean hasFileTypeFileUsesReservedName();

        public FileTypeFileUsesReservedNameError getFileTypeFileUsesReservedName();

        public FileTypeFileUsesReservedNameErrorOrBuilder getFileTypeFileUsesReservedNameOrBuilder();

        public boolean hasFileTypeDirectoryInBundle();

        public FileTypeDirectoryInBundleError getFileTypeDirectoryInBundle();

        public FileTypeDirectoryInBundleErrorOrBuilder getFileTypeDirectoryInBundleOrBuilder();

        public boolean hasFileTypeInvalidApexImagePath();

        public FileTypeInvalidApexImagePathError getFileTypeInvalidApexImagePath();

        public FileTypeInvalidApexImagePathErrorOrBuilder getFileTypeInvalidApexImagePathOrBuilder();

        public boolean hasMandatoryBundleFileMissing();

        public MandatoryBundleFileMissingError getMandatoryBundleFileMissing();

        public MandatoryBundleFileMissingErrorOrBuilder getMandatoryBundleFileMissingOrBuilder();

        public boolean hasMandatoryModuleFileMissing();

        public MandatoryModuleFileMissingError getMandatoryModuleFileMissing();

        public MandatoryModuleFileMissingErrorOrBuilder getMandatoryModuleFileMissingOrBuilder();

        public boolean hasResourceTableReferencesFilesOutsideRes();

        public ResourceTableReferencesFilesOutsideResError getResourceTableReferencesFilesOutsideRes();

        public ResourceTableReferencesFilesOutsideResErrorOrBuilder getResourceTableReferencesFilesOutsideResOrBuilder();

        public boolean hasResouceTableReferencesMissingFiles();

        public ResourceTableReferencesMissingFilesError getResouceTableReferencesMissingFiles();

        public ResourceTableReferencesMissingFilesErrorOrBuilder getResouceTableReferencesMissingFilesOrBuilder();

        public boolean hasResouceTableUnreferencedFiles();

        public ResourceTableUnreferencedFilesError getResouceTableUnreferencedFiles();

        public ResourceTableUnreferencedFilesErrorOrBuilder getResouceTableUnreferencedFilesOrBuilder();

        public boolean hasResourceTableMissing();

        public ResourceTableMissingError getResourceTableMissing();

        public ResourceTableMissingErrorOrBuilder getResourceTableMissingOrBuilder();

        public BundleToolError.CustomErrorCase getCustomErrorCase();
    }
}

