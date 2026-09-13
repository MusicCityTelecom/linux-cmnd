/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.commands.DumpCommand;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Optional;

final class AutoValue_DumpCommand
extends DumpCommand {
    private final Path bundlePath;
    private final PrintStream outputStream;
    private final DumpCommand.DumpTarget dumpTarget;
    private final Optional<String> moduleName;
    private final Optional<String> XPathExpression;
    private final Optional<Integer> resourceId;
    private final Optional<String> resourceName;
    private final Optional<Boolean> printValues;

    private AutoValue_DumpCommand(Path bundlePath, PrintStream outputStream, DumpCommand.DumpTarget dumpTarget, Optional<String> moduleName, Optional<String> XPathExpression, Optional<Integer> resourceId, Optional<String> resourceName, Optional<Boolean> printValues) {
        this.bundlePath = bundlePath;
        this.outputStream = outputStream;
        this.dumpTarget = dumpTarget;
        this.moduleName = moduleName;
        this.XPathExpression = XPathExpression;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.printValues = printValues;
    }

    @Override
    public Path getBundlePath() {
        return this.bundlePath;
    }

    @Override
    public PrintStream getOutputStream() {
        return this.outputStream;
    }

    @Override
    public DumpCommand.DumpTarget getDumpTarget() {
        return this.dumpTarget;
    }

    @Override
    public Optional<String> getModuleName() {
        return this.moduleName;
    }

    @Override
    public Optional<String> getXPathExpression() {
        return this.XPathExpression;
    }

    @Override
    public Optional<Integer> getResourceId() {
        return this.resourceId;
    }

    @Override
    public Optional<String> getResourceName() {
        return this.resourceName;
    }

    @Override
    public Optional<Boolean> getPrintValues() {
        return this.printValues;
    }

    public String toString() {
        return "DumpCommand{bundlePath=" + this.bundlePath + ", outputStream=" + this.outputStream + ", dumpTarget=" + (Object)((Object)this.dumpTarget) + ", moduleName=" + this.moduleName + ", XPathExpression=" + this.XPathExpression + ", resourceId=" + this.resourceId + ", resourceName=" + this.resourceName + ", printValues=" + this.printValues + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof DumpCommand) {
            DumpCommand that = (DumpCommand)o3;
            return this.bundlePath.equals(that.getBundlePath()) && this.outputStream.equals(that.getOutputStream()) && this.dumpTarget.equals((Object)that.getDumpTarget()) && this.moduleName.equals(that.getModuleName()) && this.XPathExpression.equals(that.getXPathExpression()) && this.resourceId.equals(that.getResourceId()) && this.resourceName.equals(that.getResourceName()) && this.printValues.equals(that.getPrintValues());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.bundlePath.hashCode();
        h$ *= 1000003;
        h$ ^= this.outputStream.hashCode();
        h$ *= 1000003;
        h$ ^= this.dumpTarget.hashCode();
        h$ *= 1000003;
        h$ ^= this.moduleName.hashCode();
        h$ *= 1000003;
        h$ ^= this.XPathExpression.hashCode();
        h$ *= 1000003;
        h$ ^= this.resourceId.hashCode();
        h$ *= 1000003;
        h$ ^= this.resourceName.hashCode();
        h$ *= 1000003;
        return h$ ^= this.printValues.hashCode();
    }

    static final class Builder
    extends DumpCommand.Builder {
        private Path bundlePath;
        private PrintStream outputStream;
        private DumpCommand.DumpTarget dumpTarget;
        private Optional<String> moduleName = Optional.empty();
        private Optional<String> XPathExpression = Optional.empty();
        private Optional<Integer> resourceId = Optional.empty();
        private Optional<String> resourceName = Optional.empty();
        private Optional<Boolean> printValues = Optional.empty();

        Builder() {
        }

        @Override
        public DumpCommand.Builder setBundlePath(Path bundlePath) {
            if (bundlePath == null) {
                throw new NullPointerException("Null bundlePath");
            }
            this.bundlePath = bundlePath;
            return this;
        }

        @Override
        public DumpCommand.Builder setOutputStream(PrintStream outputStream) {
            if (outputStream == null) {
                throw new NullPointerException("Null outputStream");
            }
            this.outputStream = outputStream;
            return this;
        }

        @Override
        public DumpCommand.Builder setDumpTarget(DumpCommand.DumpTarget dumpTarget) {
            if (dumpTarget == null) {
                throw new NullPointerException("Null dumpTarget");
            }
            this.dumpTarget = dumpTarget;
            return this;
        }

        @Override
        public DumpCommand.Builder setModuleName(String moduleName) {
            this.moduleName = Optional.of(moduleName);
            return this;
        }

        @Override
        public DumpCommand.Builder setXPathExpression(String XPathExpression) {
            this.XPathExpression = Optional.of(XPathExpression);
            return this;
        }

        @Override
        public DumpCommand.Builder setResourceId(int resourceId) {
            this.resourceId = Optional.of(resourceId);
            return this;
        }

        @Override
        public DumpCommand.Builder setResourceName(String resourceName) {
            this.resourceName = Optional.of(resourceName);
            return this;
        }

        @Override
        public DumpCommand.Builder setPrintValues(boolean printValues) {
            this.printValues = Optional.of(printValues);
            return this;
        }

        @Override
        public DumpCommand build() {
            String missing = "";
            if (this.bundlePath == null) {
                missing = missing + " bundlePath";
            }
            if (this.outputStream == null) {
                missing = missing + " outputStream";
            }
            if (this.dumpTarget == null) {
                missing = missing + " dumpTarget";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return new AutoValue_DumpCommand(this.bundlePath, this.outputStream, this.dumpTarget, this.moduleName, this.XPathExpression, this.resourceId, this.resourceName, this.printValues);
        }
    }
}

