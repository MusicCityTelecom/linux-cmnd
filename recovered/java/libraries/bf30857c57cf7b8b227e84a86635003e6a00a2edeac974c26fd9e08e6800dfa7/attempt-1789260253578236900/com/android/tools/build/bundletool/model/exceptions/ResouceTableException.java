/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions;

import com.android.bundle.Errors;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import java.util.stream.Collectors;

public class ResouceTableException
extends ValidationException {
    @FormatMethod
    protected ResouceTableException(@FormatString String message, Object ... args) {
        super(String.format(Preconditions.checkNotNull(message), args));
    }

    public static class ReferencesMissingFilesException
    extends ResouceTableException {
        private final String moduleName;
        private final ImmutableSet<ZipPath> missingFiles;

        public ReferencesMissingFilesException(String moduleName, ImmutableSet<ZipPath> nonExistingFiles) {
            super("Resource table of module '%s' contains references to non-existing files: %s", moduleName, nonExistingFiles);
            this.moduleName = moduleName;
            this.missingFiles = nonExistingFiles;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setResouceTableReferencesMissingFiles(Errors.ResourceTableReferencesMissingFilesError.newBuilder().setModuleName(this.moduleName).addAllFilePath(this.missingFiles.stream().map(Object::toString).collect(Collectors.toList())));
        }
    }

    public static class UnreferencedResourcesException
    extends ResouceTableException {
        private final String moduleName;
        private final ImmutableSet<ZipPath> nonReferencedFiles;

        public UnreferencedResourcesException(String moduleName, ImmutableSet<ZipPath> nonReferencedFiles) {
            super("Module '%s' contains resource files that are not referenced from the resource table: %s", moduleName, nonReferencedFiles);
            this.moduleName = moduleName;
            this.nonReferencedFiles = nonReferencedFiles;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setResouceTableUnreferencedFiles(Errors.ResourceTableUnreferencedFilesError.newBuilder().setModuleName(this.moduleName).addAllFilePath(this.nonReferencedFiles.stream().map(Object::toString).collect(Collectors.toList())));
        }
    }

    public static class ReferencesFileOutsideOfResException
    extends ResouceTableException {
        private final String moduleName;
        private final ZipPath referencedFile;

        public ReferencesFileOutsideOfResException(String moduleName, ZipPath referencedFile, ZipPath resourcesDir) {
            super("Resource table of module '%s' references file '%s' outside of the '%s/' directory.", moduleName, referencedFile, resourcesDir);
            this.moduleName = moduleName;
            this.referencedFile = referencedFile;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setResourceTableReferencesFilesOutsideRes(Errors.ResourceTableReferencesFilesOutsideResError.newBuilder().setModuleName(this.moduleName).setFilePath(this.referencedFile.toString()));
        }
    }

    public static class ResourceTableMissingException
    extends ResouceTableException {
        private final String moduleName;

        public ResourceTableMissingException(String moduleName) {
            super("Module '%s' contains resource files but is missing a resource table.", moduleName);
            this.moduleName = moduleName;
        }

        @Override
        protected void customizeProto(Errors.BundleToolError.Builder builder) {
            builder.setResourceTableMissing(Errors.ResourceTableMissingError.newBuilder().setModuleName(this.moduleName));
        }
    }
}

