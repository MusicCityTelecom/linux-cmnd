/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.ValidateBundleCommand;
import java.nio.file.Path;

final class AutoValue_ValidateBundleCommand
extends ValidateBundleCommand {
    private final Path bundlePath;
    private final Boolean printOutput;

    private AutoValue_ValidateBundleCommand(Path bundlePath, Boolean printOutput) {
        this.bundlePath = bundlePath;
        this.printOutput = printOutput;
    }

    @Override
    public Path getBundlePath() {
        return this.bundlePath;
    }

    @Override
    public Boolean getPrintOutput() {
        return this.printOutput;
    }

    public String toString() {
        return "ValidateBundleCommand{bundlePath=" + this.bundlePath + ", printOutput=" + this.printOutput + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ValidateBundleCommand) {
            ValidateBundleCommand that = (ValidateBundleCommand)o3;
            return this.bundlePath.equals(that.getBundlePath()) && this.printOutput.equals(that.getPrintOutput());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.bundlePath.hashCode();
        h$ *= 1000003;
        return h$ ^= this.printOutput.hashCode();
    }

    static final class Builder
    extends ValidateBundleCommand.Builder {
        private Path bundlePath;
        private Boolean printOutput;

        Builder() {
        }

        @Override
        public ValidateBundleCommand.Builder setBundlePath(Path bundlePath) {
            if (bundlePath == null) {
                throw new NullPointerException("Null bundlePath");
            }
            this.bundlePath = bundlePath;
            return this;
        }

        @Override
        public ValidateBundleCommand.Builder setPrintOutput(Boolean printOutput) {
            if (printOutput == null) {
                throw new NullPointerException("Null printOutput");
            }
            this.printOutput = printOutput;
            return this;
        }

        @Override
        public ValidateBundleCommand build() {
            String missing = "";
            if (this.bundlePath == null) {
                missing = missing + " bundlePath";
            }
            if (this.printOutput == null) {
                missing = missing + " printOutput";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_ValidateBundleCommand(this.bundlePath, this.printOutput);
        }
    }
}

