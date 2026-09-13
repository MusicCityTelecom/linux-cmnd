/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.util.Assert
 */
package org.springframework.boot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.springframework.boot.ExitCodeExceptionMapper;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.Assert;

class ExitCodeGenerators
implements Iterable<ExitCodeGenerator> {
    private List<ExitCodeGenerator> generators = new ArrayList<ExitCodeGenerator>();

    ExitCodeGenerators() {
    }

    void addAll(Throwable exception, ExitCodeExceptionMapper ... mappers) {
        Assert.notNull((Object)exception, (String)"Exception must not be null");
        Assert.notNull((Object)mappers, (String)"Mappers must not be null");
        this.addAll(exception, Arrays.asList(mappers));
    }

    void addAll(Throwable exception, Iterable<? extends ExitCodeExceptionMapper> mappers) {
        Assert.notNull((Object)exception, (String)"Exception must not be null");
        Assert.notNull(mappers, (String)"Mappers must not be null");
        for (ExitCodeExceptionMapper exitCodeExceptionMapper : mappers) {
            this.add(exception, exitCodeExceptionMapper);
        }
    }

    void add(Throwable exception, ExitCodeExceptionMapper mapper) {
        Assert.notNull((Object)exception, (String)"Exception must not be null");
        Assert.notNull((Object)mapper, (String)"Mapper must not be null");
        this.add(new MappedExitCodeGenerator(exception, mapper));
    }

    void addAll(ExitCodeGenerator ... generators) {
        Assert.notNull((Object)generators, (String)"Generators must not be null");
        this.addAll(Arrays.asList(generators));
    }

    void addAll(Iterable<? extends ExitCodeGenerator> generators) {
        Assert.notNull(generators, (String)"Generators must not be null");
        for (ExitCodeGenerator exitCodeGenerator : generators) {
            this.add(exitCodeGenerator);
        }
    }

    void add(ExitCodeGenerator generator) {
        Assert.notNull((Object)generator, (String)"Generator must not be null");
        this.generators.add(generator);
        AnnotationAwareOrderComparator.sort(this.generators);
    }

    @Override
    public Iterator<ExitCodeGenerator> iterator() {
        return this.generators.iterator();
    }

    int getExitCode() {
        int exitCode = 0;
        for (ExitCodeGenerator generator : this.generators) {
            try {
                int value = generator.getExitCode();
                if (value == 0) continue;
                exitCode = value;
                break;
            }
            catch (Exception ex) {
                exitCode = 1;
                ex.printStackTrace();
            }
        }
        return exitCode;
    }

    private static class MappedExitCodeGenerator
    implements ExitCodeGenerator {
        private final Throwable exception;
        private final ExitCodeExceptionMapper mapper;

        MappedExitCodeGenerator(Throwable exception, ExitCodeExceptionMapper mapper) {
            this.exception = exception;
            this.mapper = mapper;
        }

        @Override
        public int getExitCode() {
            return this.mapper.getExitCode(this.exception);
        }
    }
}

