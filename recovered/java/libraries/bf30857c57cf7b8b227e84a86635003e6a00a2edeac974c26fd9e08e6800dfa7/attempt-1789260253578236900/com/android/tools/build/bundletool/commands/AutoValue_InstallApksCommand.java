/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.InstallApksCommand;
import com.android.tools.build.bundletool.device.AdbServer;
import com.google.common.collect.ImmutableSet;
import java.nio.file.Path;
import java.util.Optional;

final class AutoValue_InstallApksCommand
extends InstallApksCommand {
    private final Path adbPath;
    private final Path apksArchivePath;
    private final Optional<String> deviceId;
    private final Optional<ImmutableSet<String>> modules;
    private final boolean allowDowngrade;
    private final boolean allowTestOnly;
    private final AdbServer adbServer;

    private AutoValue_InstallApksCommand(Path adbPath, Path apksArchivePath, Optional<String> deviceId, Optional<ImmutableSet<String>> modules, boolean allowDowngrade, boolean allowTestOnly, AdbServer adbServer) {
        this.adbPath = adbPath;
        this.apksArchivePath = apksArchivePath;
        this.deviceId = deviceId;
        this.modules = modules;
        this.allowDowngrade = allowDowngrade;
        this.allowTestOnly = allowTestOnly;
        this.adbServer = adbServer;
    }

    @Override
    public Path getAdbPath() {
        return this.adbPath;
    }

    @Override
    public Path getApksArchivePath() {
        return this.apksArchivePath;
    }

    @Override
    public Optional<String> getDeviceId() {
        return this.deviceId;
    }

    @Override
    public Optional<ImmutableSet<String>> getModules() {
        return this.modules;
    }

    @Override
    public boolean getAllowDowngrade() {
        return this.allowDowngrade;
    }

    @Override
    public boolean getAllowTestOnly() {
        return this.allowTestOnly;
    }

    @Override
    AdbServer getAdbServer() {
        return this.adbServer;
    }

    public String toString() {
        return "InstallApksCommand{adbPath=" + this.adbPath + ", apksArchivePath=" + this.apksArchivePath + ", deviceId=" + this.deviceId + ", modules=" + this.modules + ", allowDowngrade=" + this.allowDowngrade + ", allowTestOnly=" + this.allowTestOnly + ", adbServer=" + this.adbServer + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof InstallApksCommand) {
            InstallApksCommand that = (InstallApksCommand)o3;
            return this.adbPath.equals(that.getAdbPath()) && this.apksArchivePath.equals(that.getApksArchivePath()) && this.deviceId.equals(that.getDeviceId()) && this.modules.equals(that.getModules()) && this.allowDowngrade == that.getAllowDowngrade() && this.allowTestOnly == that.getAllowTestOnly() && this.adbServer.equals(that.getAdbServer());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.adbPath.hashCode();
        h$ *= 1000003;
        h$ ^= this.apksArchivePath.hashCode();
        h$ *= 1000003;
        h$ ^= this.deviceId.hashCode();
        h$ *= 1000003;
        h$ ^= this.modules.hashCode();
        h$ *= 1000003;
        h$ ^= this.allowDowngrade ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.allowTestOnly ? 1231 : 1237;
        h$ *= 1000003;
        return h$ ^= this.adbServer.hashCode();
    }

    static final class Builder
    extends InstallApksCommand.Builder {
        private Path adbPath;
        private Path apksArchivePath;
        private Optional<String> deviceId = Optional.empty();
        private Optional<ImmutableSet<String>> modules = Optional.empty();
        private Boolean allowDowngrade;
        private Boolean allowTestOnly;
        private AdbServer adbServer;

        Builder() {
        }

        @Override
        public InstallApksCommand.Builder setAdbPath(Path adbPath) {
            if (adbPath == null) {
                throw new NullPointerException("Null adbPath");
            }
            this.adbPath = adbPath;
            return this;
        }

        @Override
        public InstallApksCommand.Builder setApksArchivePath(Path apksArchivePath) {
            if (apksArchivePath == null) {
                throw new NullPointerException("Null apksArchivePath");
            }
            this.apksArchivePath = apksArchivePath;
            return this;
        }

        @Override
        public InstallApksCommand.Builder setDeviceId(String deviceId) {
            this.deviceId = Optional.of(deviceId);
            return this;
        }

        @Override
        public InstallApksCommand.Builder setModules(ImmutableSet<String> modules) {
            this.modules = Optional.of(modules);
            return this;
        }

        @Override
        public InstallApksCommand.Builder setAllowDowngrade(boolean allowDowngrade) {
            this.allowDowngrade = allowDowngrade;
            return this;
        }

        @Override
        public InstallApksCommand.Builder setAllowTestOnly(boolean allowTestOnly) {
            this.allowTestOnly = allowTestOnly;
            return this;
        }

        @Override
        public InstallApksCommand.Builder setAdbServer(AdbServer adbServer) {
            if (adbServer == null) {
                throw new NullPointerException("Null adbServer");
            }
            this.adbServer = adbServer;
            return this;
        }

        @Override
        public InstallApksCommand build() {
            String missing = "";
            if (this.adbPath == null) {
                missing = missing + " adbPath";
            }
            if (this.apksArchivePath == null) {
                missing = missing + " apksArchivePath";
            }
            if (this.allowDowngrade == null) {
                missing = missing + " allowDowngrade";
            }
            if (this.allowTestOnly == null) {
                missing = missing + " allowTestOnly";
            }
            if (this.adbServer == null) {
                missing = missing + " adbServer";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_InstallApksCommand(this.adbPath, this.apksArchivePath, this.deviceId, this.modules, this.allowDowngrade, this.allowTestOnly, this.adbServer);
        }
    }
}

