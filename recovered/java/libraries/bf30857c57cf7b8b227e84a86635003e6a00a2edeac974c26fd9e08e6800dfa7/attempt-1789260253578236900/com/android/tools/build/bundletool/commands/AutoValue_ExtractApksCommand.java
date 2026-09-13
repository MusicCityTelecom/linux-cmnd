/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Devices;
import com.android.tools.build.bundletool.commands.ExtractApksCommand;
import com.google.common.collect.ImmutableSet;
import java.nio.file.Path;
import java.util.Optional;

final class AutoValue_ExtractApksCommand
extends ExtractApksCommand {
    private final Path apksArchivePath;
    private final Devices.DeviceSpec deviceSpec;
    private final Optional<Path> outputDirectory;
    private final Optional<ImmutableSet<String>> modules;
    private final boolean instant;

    private AutoValue_ExtractApksCommand(Path apksArchivePath, Devices.DeviceSpec deviceSpec, Optional<Path> outputDirectory, Optional<ImmutableSet<String>> modules, boolean instant) {
        this.apksArchivePath = apksArchivePath;
        this.deviceSpec = deviceSpec;
        this.outputDirectory = outputDirectory;
        this.modules = modules;
        this.instant = instant;
    }

    @Override
    public Path getApksArchivePath() {
        return this.apksArchivePath;
    }

    @Override
    public Devices.DeviceSpec getDeviceSpec() {
        return this.deviceSpec;
    }

    @Override
    public Optional<Path> getOutputDirectory() {
        return this.outputDirectory;
    }

    @Override
    public Optional<ImmutableSet<String>> getModules() {
        return this.modules;
    }

    @Override
    public boolean getInstant() {
        return this.instant;
    }

    public String toString() {
        return "ExtractApksCommand{apksArchivePath=" + this.apksArchivePath + ", deviceSpec=" + this.deviceSpec + ", outputDirectory=" + this.outputDirectory + ", modules=" + this.modules + ", instant=" + this.instant + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ExtractApksCommand) {
            ExtractApksCommand that = (ExtractApksCommand)o3;
            return this.apksArchivePath.equals(that.getApksArchivePath()) && this.deviceSpec.equals(that.getDeviceSpec()) && this.outputDirectory.equals(that.getOutputDirectory()) && this.modules.equals(that.getModules()) && this.instant == that.getInstant();
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.apksArchivePath.hashCode();
        h$ *= 1000003;
        h$ ^= this.deviceSpec.hashCode();
        h$ *= 1000003;
        h$ ^= this.outputDirectory.hashCode();
        h$ *= 1000003;
        h$ ^= this.modules.hashCode();
        h$ *= 1000003;
        return h$ ^= this.instant ? 1231 : 1237;
    }

    static final class Builder
    extends ExtractApksCommand.Builder {
        private Path apksArchivePath;
        private Devices.DeviceSpec deviceSpec;
        private Optional<Path> outputDirectory = Optional.empty();
        private Optional<ImmutableSet<String>> modules = Optional.empty();
        private Boolean instant;

        Builder() {
        }

        @Override
        public ExtractApksCommand.Builder setApksArchivePath(Path apksArchivePath) {
            if (apksArchivePath == null) {
                throw new NullPointerException("Null apksArchivePath");
            }
            this.apksArchivePath = apksArchivePath;
            return this;
        }

        @Override
        public ExtractApksCommand.Builder setDeviceSpec(Devices.DeviceSpec deviceSpec) {
            if (deviceSpec == null) {
                throw new NullPointerException("Null deviceSpec");
            }
            this.deviceSpec = deviceSpec;
            return this;
        }

        @Override
        public ExtractApksCommand.Builder setOutputDirectory(Path outputDirectory) {
            this.outputDirectory = Optional.of(outputDirectory);
            return this;
        }

        @Override
        public ExtractApksCommand.Builder setModules(ImmutableSet<String> modules) {
            this.modules = Optional.of(modules);
            return this;
        }

        @Override
        public ExtractApksCommand.Builder setInstant(boolean instant) {
            this.instant = instant;
            return this;
        }

        @Override
        ExtractApksCommand autoBuild() {
            String missing = "";
            if (this.apksArchivePath == null) {
                missing = missing + " apksArchivePath";
            }
            if (this.deviceSpec == null) {
                missing = missing + " deviceSpec";
            }
            if (this.instant == null) {
                missing = missing + " instant";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_ExtractApksCommand(this.apksArchivePath, this.deviceSpec, this.outputDirectory, this.modules, this.instant);
        }
    }
}

