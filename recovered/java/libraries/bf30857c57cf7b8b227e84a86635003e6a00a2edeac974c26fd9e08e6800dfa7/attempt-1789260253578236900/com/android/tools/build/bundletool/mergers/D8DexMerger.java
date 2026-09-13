/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.mergers;

import com.android.tools.build.bundletool.mergers.DexMerger;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.android.tools.build.bundletool.model.utils.ThrowableUtils;
import com.android.tools.build.bundletool.model.utils.files.FilePreconditions;
import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.OutputMode;
import com.google.common.collect.ImmutableList;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public class D8DexMerger
implements DexMerger {
    private static final String DEX_OVERFLOW_MSG = "Cannot fit requested classes in a single dex file";

    @Override
    public ImmutableList<Path> merge(ImmutableList<Path> dexFiles, Path outputDir, Optional<Path> mainDexListFile, boolean isDebuggable, int minSdkVersion) {
        try {
            D8DexMerger.validateInput(dexFiles, outputDir);
            D8Command.Builder command = (D8Command.Builder)((D8Command.Builder)((D8Command.Builder)((D8Command.Builder)D8Command.builder().setOutput(outputDir, OutputMode.DexIndexed)).addProgramFiles(dexFiles)).setMinApiLevel(minSdkVersion)).setMode(isDebuggable ? CompilationMode.DEBUG : CompilationMode.RELEASE);
            mainDexListFile.ifPresent(xva$0 -> command.addMainDexListFiles((Path)xva$0));
            D8.run((D8Command)command.build());
            File[] mergedFiles = outputDir.toFile().listFiles();
            return Arrays.stream(mergedFiles).map(File::toPath).collect(ImmutableList.toImmutableList());
        }
        catch (CompilationFailedException e2) {
            throw D8DexMerger.translateD8Exception(e2);
        }
    }

    private static void validateInput(ImmutableList<Path> dexFiles, Path outputDir) {
        FilePreconditions.checkDirectoryExistsAndEmpty(outputDir);
        dexFiles.forEach(FilePreconditions::checkFileExistsAndReadable);
    }

    private static CommandExecutionException translateD8Exception(CompilationFailedException d8Exception) {
        if (ThrowableUtils.anyInCausalChainOrSuppressedMatches(d8Exception, t3 -> t3.getMessage() != null && t3.getMessage().contains(DEX_OVERFLOW_MSG))) {
            return new CommandExecutionException("Dex merging failed because the result does not fit into a single dex file and multidex is not supported by the input.", d8Exception);
        }
        return new CommandExecutionException("Dex merging failed.", d8Exception);
    }
}

