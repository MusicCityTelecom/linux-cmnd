/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.utils.files.BufferedIo;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public interface Aapt2Command {
    public void convertApkProtoToBinary(Path var1, Path var2);

    public static Aapt2Command createFromExecutablePath(final Path aapt2Path) {
        return new Aapt2Command(){

            @Override
            public void convertApkProtoToBinary(Path protoApk, Path binaryApk) {
                new CommandExecutor().execute(aapt2Path.toString(), "convert", "--output-format", "binary", "-o", binaryApk.toString(), protoApk.toString());
            }
        };
    }

    public static class Aapt2Exception
    extends RuntimeException {
        private Aapt2Exception(String message) {
            super(message);
        }

        private Aapt2Exception(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class CommandExecutor {
        private static final int TIMEOUT_AAPT2_COMMANDS_SECONDS = 300;

        public void execute(String ... command) {
            try {
                Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
                if (!process.waitFor(300L, TimeUnit.SECONDS)) {
                    CommandExecutor.printOutput(process);
                    throw new Aapt2Exception("Command timed out: " + Arrays.toString(command));
                }
                if (process.exitValue() != 0) {
                    CommandExecutor.printOutput(process);
                    throw new Aapt2Exception(String.format("Command '%s' didn't terminate successfully (exit code: %d). Check the logs.", Arrays.toString(command), process.exitValue()));
                }
            }
            catch (IOException | InterruptedException e2) {
                throw new Aapt2Exception("Error when executing command: " + Arrays.toString(command), e2);
            }
        }

        private static void printOutput(Process process) {
            try (BufferedReader outputReader = BufferedIo.reader(process.getInputStream());){
                String line;
                while ((line = outputReader.readLine()) != null) {
                    System.err.println(line);
                }
            }
            catch (IOException e2) {
                System.err.println("Error when printing output of aapt2 command:" + e2.getMessage());
            }
        }
    }
}

