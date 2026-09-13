/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.bundle.Devices;
import com.android.tools.build.bundletool.commands.BuildApksCommand;
import com.android.tools.build.bundletool.device.AdbServer;
import com.android.tools.build.bundletool.model.Aapt2Command;
import com.android.tools.build.bundletool.model.ApkListener;
import com.android.tools.build.bundletool.model.ApkModifier;
import com.android.tools.build.bundletool.model.OptimizationDimension;
import com.android.tools.build.bundletool.model.SigningConfiguration;
import com.android.tools.build.bundletool.model.SourceStamp;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Optional;

final class AutoValue_BuildApksCommand
extends BuildApksCommand {
    private final Path bundlePath;
    private final Path outputFile;
    private final boolean overwriteOutput;
    private final ImmutableSet<OptimizationDimension> optimizationDimensions;
    private final ImmutableSet<String> modules;
    private final Optional<Devices.DeviceSpec> deviceSpec;
    private final boolean generateOnlyForConnectedDevice;
    private final Optional<String> deviceId;
    private final Optional<AdbServer> adbServer;
    private final Optional<Path> adbPath;
    private final BuildApksCommand.ApkBuildMode apkBuildMode;
    private final boolean localTestingMode;
    private final Optional<Aapt2Command> aapt2Command;
    private final Optional<SigningConfiguration> signingConfiguration;
    private final ListeningExecutorService executorServiceInternal;
    private final boolean executorServiceCreatedByBundleTool;
    private final boolean createApkSetArchive;
    private final Optional<ApkListener> apkListener;
    private final Optional<ApkModifier> apkModifier;
    private final ImmutableList<SubValidator> extraValidators;
    private final Optional<Integer> firstVariantNumber;
    private final Optional<PrintStream> outputPrintStream;
    private final Optional<SourceStamp> sourceStamp;

    private AutoValue_BuildApksCommand(Path bundlePath, Path outputFile, boolean overwriteOutput, ImmutableSet<OptimizationDimension> optimizationDimensions, ImmutableSet<String> modules, Optional<Devices.DeviceSpec> deviceSpec, boolean generateOnlyForConnectedDevice, Optional<String> deviceId, Optional<AdbServer> adbServer, Optional<Path> adbPath, BuildApksCommand.ApkBuildMode apkBuildMode, boolean localTestingMode, Optional<Aapt2Command> aapt2Command, Optional<SigningConfiguration> signingConfiguration, ListeningExecutorService executorServiceInternal, boolean executorServiceCreatedByBundleTool, boolean createApkSetArchive, Optional<ApkListener> apkListener, Optional<ApkModifier> apkModifier, ImmutableList<SubValidator> extraValidators, Optional<Integer> firstVariantNumber, Optional<PrintStream> outputPrintStream, Optional<SourceStamp> sourceStamp) {
        this.bundlePath = bundlePath;
        this.outputFile = outputFile;
        this.overwriteOutput = overwriteOutput;
        this.optimizationDimensions = optimizationDimensions;
        this.modules = modules;
        this.deviceSpec = deviceSpec;
        this.generateOnlyForConnectedDevice = generateOnlyForConnectedDevice;
        this.deviceId = deviceId;
        this.adbServer = adbServer;
        this.adbPath = adbPath;
        this.apkBuildMode = apkBuildMode;
        this.localTestingMode = localTestingMode;
        this.aapt2Command = aapt2Command;
        this.signingConfiguration = signingConfiguration;
        this.executorServiceInternal = executorServiceInternal;
        this.executorServiceCreatedByBundleTool = executorServiceCreatedByBundleTool;
        this.createApkSetArchive = createApkSetArchive;
        this.apkListener = apkListener;
        this.apkModifier = apkModifier;
        this.extraValidators = extraValidators;
        this.firstVariantNumber = firstVariantNumber;
        this.outputPrintStream = outputPrintStream;
        this.sourceStamp = sourceStamp;
    }

    @Override
    public Path getBundlePath() {
        return this.bundlePath;
    }

    @Override
    public Path getOutputFile() {
        return this.outputFile;
    }

    @Override
    public boolean getOverwriteOutput() {
        return this.overwriteOutput;
    }

    @Override
    public ImmutableSet<OptimizationDimension> getOptimizationDimensions() {
        return this.optimizationDimensions;
    }

    @Override
    public ImmutableSet<String> getModules() {
        return this.modules;
    }

    @Override
    public Optional<Devices.DeviceSpec> getDeviceSpec() {
        return this.deviceSpec;
    }

    @Override
    public boolean getGenerateOnlyForConnectedDevice() {
        return this.generateOnlyForConnectedDevice;
    }

    @Override
    public Optional<String> getDeviceId() {
        return this.deviceId;
    }

    @Override
    Optional<AdbServer> getAdbServer() {
        return this.adbServer;
    }

    @Override
    public Optional<Path> getAdbPath() {
        return this.adbPath;
    }

    @Override
    public BuildApksCommand.ApkBuildMode getApkBuildMode() {
        return this.apkBuildMode;
    }

    @Override
    public boolean getLocalTestingMode() {
        return this.localTestingMode;
    }

    @Override
    public Optional<Aapt2Command> getAapt2Command() {
        return this.aapt2Command;
    }

    @Override
    public Optional<SigningConfiguration> getSigningConfiguration() {
        return this.signingConfiguration;
    }

    @Override
    ListeningExecutorService getExecutorServiceInternal() {
        return this.executorServiceInternal;
    }

    @Override
    boolean isExecutorServiceCreatedByBundleTool() {
        return this.executorServiceCreatedByBundleTool;
    }

    @Override
    public boolean getCreateApkSetArchive() {
        return this.createApkSetArchive;
    }

    @Override
    public Optional<ApkListener> getApkListener() {
        return this.apkListener;
    }

    @Override
    public Optional<ApkModifier> getApkModifier() {
        return this.apkModifier;
    }

    @Override
    public ImmutableList<SubValidator> getExtraValidators() {
        return this.extraValidators;
    }

    @Override
    public Optional<Integer> getFirstVariantNumber() {
        return this.firstVariantNumber;
    }

    @Override
    public Optional<PrintStream> getOutputPrintStream() {
        return this.outputPrintStream;
    }

    @Override
    public Optional<SourceStamp> getSourceStamp() {
        return this.sourceStamp;
    }

    public String toString() {
        return "BuildApksCommand{bundlePath=" + this.bundlePath + ", outputFile=" + this.outputFile + ", overwriteOutput=" + this.overwriteOutput + ", optimizationDimensions=" + this.optimizationDimensions + ", modules=" + this.modules + ", deviceSpec=" + this.deviceSpec + ", generateOnlyForConnectedDevice=" + this.generateOnlyForConnectedDevice + ", deviceId=" + this.deviceId + ", adbServer=" + this.adbServer + ", adbPath=" + this.adbPath + ", apkBuildMode=" + (Object)((Object)this.apkBuildMode) + ", localTestingMode=" + this.localTestingMode + ", aapt2Command=" + this.aapt2Command + ", signingConfiguration=" + this.signingConfiguration + ", executorServiceInternal=" + this.executorServiceInternal + ", executorServiceCreatedByBundleTool=" + this.executorServiceCreatedByBundleTool + ", createApkSetArchive=" + this.createApkSetArchive + ", apkListener=" + this.apkListener + ", apkModifier=" + this.apkModifier + ", extraValidators=" + this.extraValidators + ", firstVariantNumber=" + this.firstVariantNumber + ", outputPrintStream=" + this.outputPrintStream + ", sourceStamp=" + this.sourceStamp + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof BuildApksCommand) {
            BuildApksCommand that = (BuildApksCommand)o3;
            return this.bundlePath.equals(that.getBundlePath()) && this.outputFile.equals(that.getOutputFile()) && this.overwriteOutput == that.getOverwriteOutput() && this.optimizationDimensions.equals(that.getOptimizationDimensions()) && this.modules.equals(that.getModules()) && this.deviceSpec.equals(that.getDeviceSpec()) && this.generateOnlyForConnectedDevice == that.getGenerateOnlyForConnectedDevice() && this.deviceId.equals(that.getDeviceId()) && this.adbServer.equals(that.getAdbServer()) && this.adbPath.equals(that.getAdbPath()) && this.apkBuildMode.equals((Object)that.getApkBuildMode()) && this.localTestingMode == that.getLocalTestingMode() && this.aapt2Command.equals(that.getAapt2Command()) && this.signingConfiguration.equals(that.getSigningConfiguration()) && this.executorServiceInternal.equals(that.getExecutorServiceInternal()) && this.executorServiceCreatedByBundleTool == that.isExecutorServiceCreatedByBundleTool() && this.createApkSetArchive == that.getCreateApkSetArchive() && this.apkListener.equals(that.getApkListener()) && this.apkModifier.equals(that.getApkModifier()) && this.extraValidators.equals(that.getExtraValidators()) && this.firstVariantNumber.equals(that.getFirstVariantNumber()) && this.outputPrintStream.equals(that.getOutputPrintStream()) && this.sourceStamp.equals(that.getSourceStamp());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.bundlePath.hashCode();
        h$ *= 1000003;
        h$ ^= this.outputFile.hashCode();
        h$ *= 1000003;
        h$ ^= this.overwriteOutput ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.optimizationDimensions.hashCode();
        h$ *= 1000003;
        h$ ^= this.modules.hashCode();
        h$ *= 1000003;
        h$ ^= this.deviceSpec.hashCode();
        h$ *= 1000003;
        h$ ^= this.generateOnlyForConnectedDevice ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.deviceId.hashCode();
        h$ *= 1000003;
        h$ ^= this.adbServer.hashCode();
        h$ *= 1000003;
        h$ ^= this.adbPath.hashCode();
        h$ *= 1000003;
        h$ ^= this.apkBuildMode.hashCode();
        h$ *= 1000003;
        h$ ^= this.localTestingMode ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.aapt2Command.hashCode();
        h$ *= 1000003;
        h$ ^= this.signingConfiguration.hashCode();
        h$ *= 1000003;
        h$ ^= this.executorServiceInternal.hashCode();
        h$ *= 1000003;
        h$ ^= this.executorServiceCreatedByBundleTool ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.createApkSetArchive ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.apkListener.hashCode();
        h$ *= 1000003;
        h$ ^= this.apkModifier.hashCode();
        h$ *= 1000003;
        h$ ^= this.extraValidators.hashCode();
        h$ *= 1000003;
        h$ ^= this.firstVariantNumber.hashCode();
        h$ *= 1000003;
        h$ ^= this.outputPrintStream.hashCode();
        h$ *= 1000003;
        return h$ ^= this.sourceStamp.hashCode();
    }

    static final class Builder
    extends BuildApksCommand.Builder {
        private Path bundlePath;
        private Path outputFile;
        private Boolean overwriteOutput;
        private ImmutableSet<OptimizationDimension> optimizationDimensions;
        private ImmutableSet<String> modules;
        private Optional<Devices.DeviceSpec> deviceSpec = Optional.empty();
        private Boolean generateOnlyForConnectedDevice;
        private Optional<String> deviceId = Optional.empty();
        private Optional<AdbServer> adbServer = Optional.empty();
        private Optional<Path> adbPath = Optional.empty();
        private BuildApksCommand.ApkBuildMode apkBuildMode;
        private Boolean localTestingMode;
        private Optional<Aapt2Command> aapt2Command = Optional.empty();
        private Optional<SigningConfiguration> signingConfiguration = Optional.empty();
        private ListeningExecutorService executorServiceInternal;
        private Boolean executorServiceCreatedByBundleTool;
        private Boolean createApkSetArchive;
        private Optional<ApkListener> apkListener = Optional.empty();
        private Optional<ApkModifier> apkModifier = Optional.empty();
        private ImmutableList<SubValidator> extraValidators;
        private Optional<Integer> firstVariantNumber = Optional.empty();
        private Optional<PrintStream> outputPrintStream = Optional.empty();
        private Optional<SourceStamp> sourceStamp = Optional.empty();

        Builder() {
        }

        @Override
        public BuildApksCommand.Builder setBundlePath(Path bundlePath) {
            if (bundlePath == null) {
                throw new NullPointerException("Null bundlePath");
            }
            this.bundlePath = bundlePath;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setOutputFile(Path outputFile) {
            if (outputFile == null) {
                throw new NullPointerException("Null outputFile");
            }
            this.outputFile = outputFile;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setOverwriteOutput(boolean overwriteOutput) {
            this.overwriteOutput = overwriteOutput;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setOptimizationDimensions(ImmutableSet<OptimizationDimension> optimizationDimensions) {
            if (optimizationDimensions == null) {
                throw new NullPointerException("Null optimizationDimensions");
            }
            this.optimizationDimensions = optimizationDimensions;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setModules(ImmutableSet<String> modules) {
            if (modules == null) {
                throw new NullPointerException("Null modules");
            }
            this.modules = modules;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setDeviceSpec(Devices.DeviceSpec deviceSpec) {
            this.deviceSpec = Optional.of(deviceSpec);
            return this;
        }

        @Override
        public BuildApksCommand.Builder setGenerateOnlyForConnectedDevice(boolean generateOnlyForConnectedDevice) {
            this.generateOnlyForConnectedDevice = generateOnlyForConnectedDevice;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setDeviceId(String deviceId) {
            this.deviceId = Optional.of(deviceId);
            return this;
        }

        @Override
        public BuildApksCommand.Builder setAdbServer(AdbServer adbServer) {
            this.adbServer = Optional.of(adbServer);
            return this;
        }

        @Override
        public BuildApksCommand.Builder setAdbPath(Path adbPath) {
            this.adbPath = Optional.of(adbPath);
            return this;
        }

        @Override
        public BuildApksCommand.Builder setApkBuildMode(BuildApksCommand.ApkBuildMode apkBuildMode) {
            if (apkBuildMode == null) {
                throw new NullPointerException("Null apkBuildMode");
            }
            this.apkBuildMode = apkBuildMode;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setLocalTestingMode(boolean localTestingMode) {
            this.localTestingMode = localTestingMode;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setAapt2Command(Aapt2Command aapt2Command) {
            this.aapt2Command = Optional.of(aapt2Command);
            return this;
        }

        @Override
        public BuildApksCommand.Builder setSigningConfiguration(SigningConfiguration signingConfiguration) {
            this.signingConfiguration = Optional.of(signingConfiguration);
            return this;
        }

        @Override
        BuildApksCommand.Builder setExecutorServiceInternal(ListeningExecutorService executorServiceInternal) {
            if (executorServiceInternal == null) {
                throw new NullPointerException("Null executorServiceInternal");
            }
            this.executorServiceInternal = executorServiceInternal;
            return this;
        }

        @Override
        Optional<ListeningExecutorService> getExecutorServiceInternal() {
            if (this.executorServiceInternal == null) {
                return Optional.empty();
            }
            return Optional.of(this.executorServiceInternal);
        }

        @Override
        BuildApksCommand.Builder setExecutorServiceCreatedByBundleTool(boolean executorServiceCreatedByBundleTool) {
            this.executorServiceCreatedByBundleTool = executorServiceCreatedByBundleTool;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setCreateApkSetArchive(boolean createApkSetArchive) {
            this.createApkSetArchive = createApkSetArchive;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setApkListener(ApkListener apkListener) {
            this.apkListener = Optional.of(apkListener);
            return this;
        }

        @Override
        public BuildApksCommand.Builder setApkModifier(ApkModifier apkModifier) {
            this.apkModifier = Optional.of(apkModifier);
            return this;
        }

        @Override
        public BuildApksCommand.Builder setExtraValidators(ImmutableList<SubValidator> extraValidators) {
            if (extraValidators == null) {
                throw new NullPointerException("Null extraValidators");
            }
            this.extraValidators = extraValidators;
            return this;
        }

        @Override
        public BuildApksCommand.Builder setFirstVariantNumber(int firstVariantNumber) {
            this.firstVariantNumber = Optional.of(firstVariantNumber);
            return this;
        }

        @Override
        public BuildApksCommand.Builder setOutputPrintStream(PrintStream outputPrintStream) {
            this.outputPrintStream = Optional.of(outputPrintStream);
            return this;
        }

        @Override
        public BuildApksCommand.Builder setSourceStamp(SourceStamp sourceStamp) {
            this.sourceStamp = Optional.of(sourceStamp);
            return this;
        }

        @Override
        BuildApksCommand autoBuild() {
            String missing = "";
            if (this.bundlePath == null) {
                missing = missing + " bundlePath";
            }
            if (this.outputFile == null) {
                missing = missing + " outputFile";
            }
            if (this.overwriteOutput == null) {
                missing = missing + " overwriteOutput";
            }
            if (this.optimizationDimensions == null) {
                missing = missing + " optimizationDimensions";
            }
            if (this.modules == null) {
                missing = missing + " modules";
            }
            if (this.generateOnlyForConnectedDevice == null) {
                missing = missing + " generateOnlyForConnectedDevice";
            }
            if (this.apkBuildMode == null) {
                missing = missing + " apkBuildMode";
            }
            if (this.localTestingMode == null) {
                missing = missing + " localTestingMode";
            }
            if (this.executorServiceInternal == null) {
                missing = missing + " executorServiceInternal";
            }
            if (this.executorServiceCreatedByBundleTool == null) {
                missing = missing + " executorServiceCreatedByBundleTool";
            }
            if (this.createApkSetArchive == null) {
                missing = missing + " createApkSetArchive";
            }
            if (this.extraValidators == null) {
                missing = missing + " extraValidators";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_BuildApksCommand(this.bundlePath, this.outputFile, this.overwriteOutput, this.optimizationDimensions, this.modules, this.deviceSpec, this.generateOnlyForConnectedDevice, this.deviceId, this.adbServer, this.adbPath, this.apkBuildMode, this.localTestingMode, this.aapt2Command, this.signingConfiguration, this.executorServiceInternal, this.executorServiceCreatedByBundleTool, this.createApkSetArchive, this.apkListener, this.apkModifier, this.extraValidators, this.firstVariantNumber, this.outputPrintStream, this.sourceStamp);
        }
    }
}

