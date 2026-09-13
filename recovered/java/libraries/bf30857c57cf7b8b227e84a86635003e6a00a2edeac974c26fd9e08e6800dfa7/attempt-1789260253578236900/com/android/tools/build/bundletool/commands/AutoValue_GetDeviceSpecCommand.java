/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.GetDeviceSpecCommand;
import com.android.tools.build.bundletool.device.AdbServer;
import java.nio.file.Path;
import java.util.Optional;

final class AutoValue_GetDeviceSpecCommand
extends GetDeviceSpecCommand {
    private final Path adbPath;
    private final Optional<String> deviceId;
    private final Path outputPath;
    private final boolean overwriteOutput;
    private final AdbServer adbServer;

    private AutoValue_GetDeviceSpecCommand(Path adbPath, Optional<String> deviceId, Path outputPath, boolean overwriteOutput, AdbServer adbServer) {
        this.adbPath = adbPath;
        this.deviceId = deviceId;
        this.outputPath = outputPath;
        this.overwriteOutput = overwriteOutput;
        this.adbServer = adbServer;
    }

    @Override
    public Path getAdbPath() {
        return this.adbPath;
    }

    @Override
    public Optional<String> getDeviceId() {
        return this.deviceId;
    }

    @Override
    public Path getOutputPath() {
        return this.outputPath;
    }

    @Override
    public boolean getOverwriteOutput() {
        return this.overwriteOutput;
    }

    @Override
    AdbServer getAdbServer() {
        return this.adbServer;
    }

    public String toString() {
        return "GetDeviceSpecCommand{adbPath=" + this.adbPath + ", deviceId=" + this.deviceId + ", outputPath=" + this.outputPath + ", overwriteOutput=" + this.overwriteOutput + ", adbServer=" + this.adbServer + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof GetDeviceSpecCommand) {
            GetDeviceSpecCommand that = (GetDeviceSpecCommand)o3;
            return this.adbPath.equals(that.getAdbPath()) && this.deviceId.equals(that.getDeviceId()) && this.outputPath.equals(that.getOutputPath()) && this.overwriteOutput == that.getOverwriteOutput() && this.adbServer.equals(that.getAdbServer());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.adbPath.hashCode();
        h$ *= 1000003;
        h$ ^= this.deviceId.hashCode();
        h$ *= 1000003;
        h$ ^= this.outputPath.hashCode();
        h$ *= 1000003;
        h$ ^= this.overwriteOutput ? 1231 : 1237;
        h$ *= 1000003;
        return h$ ^= this.adbServer.hashCode();
    }

    static final class Builder
    extends GetDeviceSpecCommand.Builder {
        private Path adbPath;
        private Optional<String> deviceId = Optional.empty();
        private Path outputPath;
        private Boolean overwriteOutput;
        private AdbServer adbServer;

        Builder() {
        }

        @Override
        public GetDeviceSpecCommand.Builder setAdbPath(Path adbPath) {
            if (adbPath == null) {
                throw new NullPointerException("Null adbPath");
            }
            this.adbPath = adbPath;
            return this;
        }

        @Override
        public GetDeviceSpecCommand.Builder setDeviceId(String deviceId) {
            this.deviceId = Optional.of(deviceId);
            return this;
        }

        @Override
        public GetDeviceSpecCommand.Builder setOutputPath(Path outputPath) {
            if (outputPath == null) {
                throw new NullPointerException("Null outputPath");
            }
            this.outputPath = outputPath;
            return this;
        }

        @Override
        public GetDeviceSpecCommand.Builder setOverwriteOutput(boolean overwriteOutput) {
            this.overwriteOutput = overwriteOutput;
            return this;
        }

        @Override
        public GetDeviceSpecCommand.Builder setAdbServer(AdbServer adbServer) {
            if (adbServer == null) {
                throw new NullPointerException("Null adbServer");
            }
            this.adbServer = adbServer;
            return this;
        }

        @Override
        GetDeviceSpecCommand autoBuild() {
            String missing = "";
            if (this.adbPath == null) {
                missing = missing + " adbPath";
            }
            if (this.outputPath == null) {
                missing = missing + " outputPath";
            }
            if (this.overwriteOutput == null) {
                missing = missing + " overwriteOutput";
            }
            if (this.adbServer == null) {
                missing = missing + " adbServer";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_GetDeviceSpecCommand(this.adbPath, this.deviceId, this.outputPath, this.overwriteOutput, this.adbServer);
        }
    }
}

