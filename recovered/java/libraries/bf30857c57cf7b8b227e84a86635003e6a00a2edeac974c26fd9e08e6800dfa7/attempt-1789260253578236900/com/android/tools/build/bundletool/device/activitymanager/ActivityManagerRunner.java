/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device.activitymanager;

import com.android.tools.build.bundletool.device.AdbShellCommandTask;
import com.android.tools.build.bundletool.device.Device;
import com.android.tools.build.bundletool.device.activitymanager.AbiStringParser;
import com.android.tools.build.bundletool.device.activitymanager.ResourceConfigParser;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ActivityManagerRunner {
    private final Device device;
    private Supplier<ImmutableList<String>> activityManagerCommandResult = Suppliers.memoize(() -> {
        int apiLevel = this.getDevice().getVersion().getApiLevel();
        if (apiLevel < 21) {
            return ImmutableList.of();
        }
        return new AdbShellCommandTask(this.getDevice(), ACTIVITY_MANAGER_CONFIG_COMMAND).execute(60000L, TimeUnit.MILLISECONDS);
    });
    private static final String ACTIVITY_MANAGER_CONFIG_COMMAND = "am get-config";
    private static final String ABI_LINE_PREFIX = "abi: ";
    private static final String RESOURCE_CONFIG_LINE_PREFIX = "config: ";

    public ActivityManagerRunner(Device device) {
        this.device = device;
    }

    public ImmutableList<String> getDeviceLocales() {
        return this.activityManagerCommandResult.get().stream().filter(line -> line.startsWith(RESOURCE_CONFIG_LINE_PREFIX)).findFirst().map(this::parseResourceConfig).orElse(ImmutableList.of());
    }

    public ImmutableList<String> getDeviceAbis() {
        return this.activityManagerCommandResult.get().stream().filter(line -> line.startsWith(ABI_LINE_PREFIX)).findFirst().map(AbiStringParser::parseAbiLine).orElse(ImmutableList.of());
    }

    private ImmutableList<String> parseResourceConfig(String configLine) {
        String resourceConfig = configLine.substring(RESOURCE_CONFIG_LINE_PREFIX.length());
        return ResourceConfigParser.parseDeviceConfig(resourceConfig, new LocaleExtractor());
    }

    private Device getDevice() {
        return this.device;
    }

    private static class LocaleExtractor
    implements ResourceConfigParser.ResourceConfigHandler<ImmutableList<String>> {
        private final ImmutableList.Builder<String> locales = ImmutableList.builder();

        private LocaleExtractor() {
        }

        @Override
        public void onLocale(String locale) {
            this.locales.add((Object)locale);
        }

        @Override
        public ImmutableList<String> getOutput() {
            return this.locales.build();
        }
    }
}

