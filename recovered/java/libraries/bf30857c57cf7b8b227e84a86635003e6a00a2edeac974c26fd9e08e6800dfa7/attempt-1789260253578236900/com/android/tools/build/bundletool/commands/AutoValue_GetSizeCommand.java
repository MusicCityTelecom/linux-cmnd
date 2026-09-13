/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Devices;
import com.android.tools.build.bundletool.commands.GetSizeCommand;
import com.android.tools.build.bundletool.model.GetSizeRequest;
import com.google.common.collect.ImmutableSet;
import java.nio.file.Path;
import java.util.Optional;

final class AutoValue_GetSizeCommand
extends GetSizeCommand {
    private final Path apksArchivePath;
    private final Devices.DeviceSpec deviceSpec;
    private final Optional<ImmutableSet<String>> modules;
    private final ImmutableSet<GetSizeRequest.Dimension> dimensions;
    private final GetSizeCommand.GetSizeSubcommand getSizeSubCommand;
    private final boolean instant;

    private AutoValue_GetSizeCommand(Path apksArchivePath, Devices.DeviceSpec deviceSpec, Optional<ImmutableSet<String>> modules, ImmutableSet<GetSizeRequest.Dimension> dimensions, GetSizeCommand.GetSizeSubcommand getSizeSubCommand, boolean instant) {
        this.apksArchivePath = apksArchivePath;
        this.deviceSpec = deviceSpec;
        this.modules = modules;
        this.dimensions = dimensions;
        this.getSizeSubCommand = getSizeSubCommand;
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
    public Optional<ImmutableSet<String>> getModules() {
        return this.modules;
    }

    @Override
    public ImmutableSet<GetSizeRequest.Dimension> getDimensions() {
        return this.dimensions;
    }

    @Override
    public GetSizeCommand.GetSizeSubcommand getGetSizeSubCommand() {
        return this.getSizeSubCommand;
    }

    @Override
    public boolean getInstant() {
        return this.instant;
    }

    public String toString() {
        return "GetSizeCommand{apksArchivePath=" + this.apksArchivePath + ", deviceSpec=" + this.deviceSpec + ", modules=" + this.modules + ", dimensions=" + this.dimensions + ", getSizeSubCommand=" + (Object)((Object)this.getSizeSubCommand) + ", instant=" + this.instant + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof GetSizeCommand) {
            GetSizeCommand that = (GetSizeCommand)o3;
            return this.apksArchivePath.equals(that.getApksArchivePath()) && this.deviceSpec.equals(that.getDeviceSpec()) && this.modules.equals(that.getModules()) && this.dimensions.equals(that.getDimensions()) && this.getSizeSubCommand.equals((Object)that.getGetSizeSubCommand()) && this.instant == that.getInstant();
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
        h$ ^= this.modules.hashCode();
        h$ *= 1000003;
        h$ ^= this.dimensions.hashCode();
        h$ *= 1000003;
        h$ ^= this.getSizeSubCommand.hashCode();
        h$ *= 1000003;
        return h$ ^= this.instant ? 1231 : 1237;
    }

    static final class Builder
    extends GetSizeCommand.Builder {
        private Path apksArchivePath;
        private Devices.DeviceSpec deviceSpec;
        private Optional<ImmutableSet<String>> modules = Optional.empty();
        private ImmutableSet<GetSizeRequest.Dimension> dimensions;
        private GetSizeCommand.GetSizeSubcommand getSizeSubCommand;
        private Boolean instant;

        Builder() {
        }

        @Override
        public GetSizeCommand.Builder setApksArchivePath(Path apksArchivePath) {
            if (apksArchivePath == null) {
                throw new NullPointerException("Null apksArchivePath");
            }
            this.apksArchivePath = apksArchivePath;
            return this;
        }

        @Override
        public GetSizeCommand.Builder setDeviceSpec(Devices.DeviceSpec deviceSpec) {
            if (deviceSpec == null) {
                throw new NullPointerException("Null deviceSpec");
            }
            this.deviceSpec = deviceSpec;
            return this;
        }

        @Override
        public GetSizeCommand.Builder setModules(ImmutableSet<String> modules) {
            this.modules = Optional.of(modules);
            return this;
        }

        @Override
        public GetSizeCommand.Builder setDimensions(ImmutableSet<GetSizeRequest.Dimension> dimensions) {
            if (dimensions == null) {
                throw new NullPointerException("Null dimensions");
            }
            this.dimensions = dimensions;
            return this;
        }

        @Override
        public GetSizeCommand.Builder setGetSizeSubCommand(GetSizeCommand.GetSizeSubcommand getSizeSubCommand) {
            if (getSizeSubCommand == null) {
                throw new NullPointerException("Null getSizeSubCommand");
            }
            this.getSizeSubCommand = getSizeSubCommand;
            return this;
        }

        @Override
        public GetSizeCommand.Builder setInstant(boolean instant) {
            this.instant = instant;
            return this;
        }

        @Override
        public GetSizeCommand build() {
            String missing = "";
            if (this.apksArchivePath == null) {
                missing = missing + " apksArchivePath";
            }
            if (this.deviceSpec == null) {
                missing = missing + " deviceSpec";
            }
            if (this.dimensions == null) {
                missing = missing + " dimensions";
            }
            if (this.getSizeSubCommand == null) {
                missing = missing + " getSizeSubCommand";
            }
            if (this.instant == null) {
                missing = missing + " instant";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_GetSizeCommand(this.apksArchivePath, this.deviceSpec, this.modules, this.dimensions, this.getSizeSubCommand, this.instant);
        }
    }
}

