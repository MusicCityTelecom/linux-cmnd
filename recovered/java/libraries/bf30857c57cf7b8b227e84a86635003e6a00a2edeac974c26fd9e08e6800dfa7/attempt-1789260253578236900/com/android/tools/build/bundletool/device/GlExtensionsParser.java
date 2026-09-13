/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.tools.build.bundletool.model.exceptions.ParseException;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

public class GlExtensionsParser {
    private static final String SURFACE_FLINGER_HEADER = "SurfaceFlinger global state:";
    private static final String GLES_PREFIX = "GLES:";

    public ImmutableList<String> parse(ImmutableList<String> dumpSysOutput) {
        ImmutableList.Builder glExtensions = ImmutableList.builder();
        ParsingState parsingState = ParsingState.SEARCHING_SURFACE_FLINGER_HEADER;
        for (String line : dumpSysOutput) {
            if (line.isEmpty()) continue;
            switch (parsingState) {
                case SEARCHING_SURFACE_FLINGER_HEADER: {
                    if (!line.startsWith(SURFACE_FLINGER_HEADER)) break;
                    parsingState = ParsingState.FOUND_SURFACE_FLINGER_HEADER;
                    break;
                }
                case FOUND_SURFACE_FLINGER_HEADER: {
                    if (!line.startsWith(GLES_PREFIX)) break;
                    parsingState = ParsingState.FOUND_GLES_HEADER;
                    break;
                }
                case FOUND_GLES_HEADER: {
                    glExtensions.addAll(Splitter.on(' ').split(line.trim()));
                    parsingState = ParsingState.FOUND_GL_EXTENSIONS;
                    break;
                }
            }
        }
        if (!parsingState.equals((Object)ParsingState.FOUND_GL_EXTENSIONS)) {
            throw ParseException.builder().withMessage("Unexpected output of 'dumpsys SurfaceFlinger' command: no GL extensions found.").build();
        }
        return glExtensions.build();
    }

    private static enum ParsingState {
        SEARCHING_SURFACE_FLINGER_HEADER,
        FOUND_SURFACE_FLINGER_HEADER,
        FOUND_GLES_HEADER,
        FOUND_GL_EXTENSIONS;

    }
}

