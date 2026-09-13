/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions;

import com.android.bundle.Errors;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import java.util.zip.ZipEntry;

public class BundleFileTypesException
extends ValidationException {
    @FormatMethod
    protected BundleFileTypesException(@FormatString String message, Object ... args) {
        super(String.format(Preconditions.checkNotNull(message), args));
    }

    public static class DirectoryInBundleException
    extends BundleFileTypesException {
        private final ZipEntry directory;

        public DirectoryInBundleException(ZipEntry directory) {
            super("The App Bundle zip file contains directory zip entry '%s' which is not allowed.", directory.getName());
            this.directory = directory;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setFileTypeDirectoryInBundle(Errors.FileTypeDirectoryInBundleError.newBuilder().setInvalidDirectory(this.directory.getName()));
        }
    }

    public static class MandatoryModuleFileMissingException
    extends BundleFileTypesException {
        private final String moduleName;
        private final ZipPath file;

        public MandatoryModuleFileMissingException(String moduleName, ZipPath file) {
            super("Module '%s' is missing mandatory file '%s'.", moduleName, file);
            this.moduleName = moduleName;
            this.file = file;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setMandatoryModuleFileMissing(Errors.MandatoryModuleFileMissingError.newBuilder().setModuleName(this.moduleName).setMissingFile(this.file.toString()));
        }
    }

    public static class MandatoryBundleFileMissingException
    extends BundleFileTypesException {
        private final ZipPath file;

        public MandatoryBundleFileMissingException(ZipPath file) {
            super("The archive doesn't seem to be an App Bundle, it is missing required file '%s'.", file);
            this.file = file;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setMandatoryBundleFileMissing(Errors.MandatoryBundleFileMissingError.newBuilder().setMissingFile(this.file.toString()));
        }
    }

    public static class FileUsesReservedNameException
    extends BundleFileTypesException {
        private final ZipPath file;

        public FileUsesReservedNameException(ZipPath file, ZipPath nameUnderRoot) {
            super("File '%s' uses reserved file or directory name '%s'.", file, nameUnderRoot);
            this.file = file;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setFileTypeFileUsesReservedName(Errors.FileTypeFileUsesReservedNameError.newBuilder().setInvalidFile(this.file.toString()));
        }
    }

    public static class UnknownFileOrDirectoryFoundInModuleException
    extends BundleFileTypesException {
        private final ZipPath file;

        public UnknownFileOrDirectoryFoundInModuleException(ZipPath file) {
            super("Module files can be only in pre-defined directories, but found '%s'.", file);
            this.file = file;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setFileTypeUnknownFileOrDirectoryInModule(Errors.FileTypeUnknownFileOrDirectoryFoundInModuleError.newBuilder().setInvalidFile(this.file.toString()));
        }
    }

    public static class InvalidNativeArchitectureNameException
    extends BundleFileTypesException {
        private final ZipPath fileOrDirectory;

        public static InvalidNativeArchitectureNameException createForFile(ZipPath file) {
            return new InvalidNativeArchitectureNameException(file, "file");
        }

        public static InvalidNativeArchitectureNameException createForDirectory(ZipPath directory) {
            return new InvalidNativeArchitectureNameException(directory, "directory");
        }

        private InvalidNativeArchitectureNameException(ZipPath fileOrDirectory, String type) {
            super("Unrecognized native architecture for %s '%s'.", type, fileOrDirectory);
            this.fileOrDirectory = fileOrDirectory;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setFileTypeInvalidNativeArchitecture(Errors.FileTypeInvalidNativeArchitectureError.newBuilder().setInvalidArchitectureDirectory(this.fileOrDirectory.toString()));
        }
    }

    public static class InvalidApexImagePathException
    extends BundleFileTypesException {
        private final ZipPath apexDirectory;
        private final ZipPath file;

        public InvalidApexImagePathException(ZipPath apexDirectory, ZipPath file) {
            super("APEX image files need to have paths in form '%s/<file>.img' but found '%s'.", apexDirectory, file);
            this.apexDirectory = apexDirectory;
            this.file = file;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setFileTypeInvalidApexImagePath(Errors.FileTypeInvalidApexImagePathError.newBuilder().setInvalidFile(this.file.toString()).setBundleDirectory(this.apexDirectory.toString()));
        }
    }

    public static class InvalidNativeLibraryPathException
    extends BundleFileTypesException {
        private final ZipPath libDirectory;
        private final ZipPath file;

        public InvalidNativeLibraryPathException(ZipPath libDirectory, ZipPath file) {
            super("Native library files need to have paths in form '%s/<single-directory>/<file>.so' but found '%s'.", libDirectory, file);
            this.libDirectory = libDirectory;
            this.file = file;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setFileTypeInvalidNativeLibraryPath(Errors.FileTypeInvalidNativeLibraryPathError.newBuilder().setInvalidFile(this.file.toString()).setBundleDirectory(this.libDirectory.toString()));
        }
    }

    public static class InvalidFileNameInDirectoryException
    extends BundleFileTypesException {
        private final ZipPath directory;
        private final String allowedFileName;
        private final ZipPath file;

        public InvalidFileNameInDirectoryException(String allowedFileName, ZipPath directory, ZipPath file) {
            super("Only '%s' is accepted under directory '%s/' but found file '%s'.", allowedFileName, directory, file);
            this.allowedFileName = allowedFileName;
            this.directory = directory;
            this.file = file;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setFileTypeInvalidFileName(Errors.FileTypeInvalidFileNameInDirectoryError.newBuilder().setInvalidFile(this.file.toString()).addAllowedFileName(this.allowedFileName).setBundleDirectory(this.directory.toString()));
        }
    }

    public static class InvalidFileExtensionInDirectoryException
    extends BundleFileTypesException {
        private final ZipPath invalidFile;
        private final ZipPath directory;
        private final String extensionRequired;

        public InvalidFileExtensionInDirectoryException(ZipPath directory, String extensionRequired, ZipPath file) {
            super("Files under %s/ must have %s extension, found '%s'.", directory, extensionRequired, file);
            this.directory = directory;
            this.extensionRequired = extensionRequired;
            this.invalidFile = file;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setFileTypeInvalidFileExtension(Errors.FileTypeInvalidFileExtensionError.newBuilder().setRequiredExtension(this.extensionRequired).setBundleDirectory(this.directory.toString()).setInvalidFile(this.invalidFile.toString()));
        }
    }
}

