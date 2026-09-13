/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Config;
import com.android.tools.build.bundletool.commands.BuildBundleCommand;
import com.android.tools.build.bundletool.model.BundleMetadata;
import com.google.common.collect.ImmutableList;
import java.nio.file.Path;
import java.util.Optional;

final class AutoValue_BuildBundleCommand
extends BuildBundleCommand {
    private final Path outputPath;
    private final ImmutableList<Path> modulesPaths;
    private final Optional<Config.BundleConfig> bundleConfig;
    private final BundleMetadata bundleMetadata;
    private final boolean uncompressedBundle;

    private AutoValue_BuildBundleCommand(Path outputPath, ImmutableList<Path> modulesPaths, Optional<Config.BundleConfig> bundleConfig, BundleMetadata bundleMetadata, boolean uncompressedBundle) {
        this.outputPath = outputPath;
        this.modulesPaths = modulesPaths;
        this.bundleConfig = bundleConfig;
        this.bundleMetadata = bundleMetadata;
        this.uncompressedBundle = uncompressedBundle;
    }

    @Override
    public Path getOutputPath() {
        return this.outputPath;
    }

    @Override
    public ImmutableList<Path> getModulesPaths() {
        return this.modulesPaths;
    }

    @Override
    public Optional<Config.BundleConfig> getBundleConfig() {
        return this.bundleConfig;
    }

    @Override
    public BundleMetadata getBundleMetadata() {
        return this.bundleMetadata;
    }

    @Override
    boolean getUncompressedBundle() {
        return this.uncompressedBundle;
    }

    public String toString() {
        return "BuildBundleCommand{outputPath=" + this.outputPath + ", modulesPaths=" + this.modulesPaths + ", bundleConfig=" + this.bundleConfig + ", bundleMetadata=" + this.bundleMetadata + ", uncompressedBundle=" + this.uncompressedBundle + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof BuildBundleCommand) {
            BuildBundleCommand that = (BuildBundleCommand)o3;
            return this.outputPath.equals(that.getOutputPath()) && this.modulesPaths.equals(that.getModulesPaths()) && this.bundleConfig.equals(that.getBundleConfig()) && this.bundleMetadata.equals(that.getBundleMetadata()) && this.uncompressedBundle == that.getUncompressedBundle();
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.outputPath.hashCode();
        h$ *= 1000003;
        h$ ^= this.modulesPaths.hashCode();
        h$ *= 1000003;
        h$ ^= this.bundleConfig.hashCode();
        h$ *= 1000003;
        h$ ^= this.bundleMetadata.hashCode();
        h$ *= 1000003;
        return h$ ^= this.uncompressedBundle ? 1231 : 1237;
    }

    static final class Builder
    extends BuildBundleCommand.Builder {
        private Path outputPath;
        private ImmutableList<Path> modulesPaths;
        private Optional<Config.BundleConfig> bundleConfig = Optional.empty();
        private BundleMetadata.Builder bundleMetadataBuilder$;
        private BundleMetadata bundleMetadata;
        private Boolean uncompressedBundle;

        Builder() {
        }

        @Override
        public BuildBundleCommand.Builder setOutputPath(Path outputPath) {
            if (outputPath == null) {
                throw new NullPointerException("Null outputPath");
            }
            this.outputPath = outputPath;
            return this;
        }

        @Override
        public BuildBundleCommand.Builder setModulesPaths(ImmutableList<Path> modulesPaths) {
            if (modulesPaths == null) {
                throw new NullPointerException("Null modulesPaths");
            }
            this.modulesPaths = modulesPaths;
            return this;
        }

        @Override
        public BuildBundleCommand.Builder setBundleConfig(Config.BundleConfig bundleConfig) {
            this.bundleConfig = Optional.of(bundleConfig);
            return this;
        }

        @Override
        BundleMetadata.Builder bundleMetadataBuilder() {
            if (this.bundleMetadataBuilder$ == null) {
                this.bundleMetadataBuilder$ = BundleMetadata.builder();
            }
            return this.bundleMetadataBuilder$;
        }

        @Override
        public BuildBundleCommand.Builder setUncompressedBundle(boolean uncompressedBundle) {
            this.uncompressedBundle = uncompressedBundle;
            return this;
        }

        @Override
        public BuildBundleCommand build() {
            if (this.bundleMetadataBuilder$ != null) {
                this.bundleMetadata = this.bundleMetadataBuilder$.build();
            } else if (this.bundleMetadata == null) {
                BundleMetadata.Builder bundleMetadata$builder = BundleMetadata.builder();
                this.bundleMetadata = bundleMetadata$builder.build();
            }
            String missing = "";
            if (this.outputPath == null) {
                missing = missing + " outputPath";
            }
            if (this.modulesPaths == null) {
                missing = missing + " modulesPaths";
            }
            if (this.uncompressedBundle == null) {
                missing = missing + " uncompressedBundle";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_BuildBundleCommand(this.outputPath, this.modulesPaths, this.bundleConfig, this.bundleMetadata, this.uncompressedBundle);
        }
    }
}

