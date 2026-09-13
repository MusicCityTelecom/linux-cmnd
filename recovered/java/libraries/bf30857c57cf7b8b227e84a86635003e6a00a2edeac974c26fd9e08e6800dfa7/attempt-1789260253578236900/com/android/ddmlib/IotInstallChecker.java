/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.MultiLineReceiver;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IotInstallChecker {
    private static final String TAG = IotInstallChecker.class.getSimpleName();
    private static final String DUMP_PACKAGES_CMD = "dumpsys package -f";

    public Set<String> getInstalledIotLauncherApps(IDevice device) {
        return this.getInstalledIotLauncherApps(device, 1L, TimeUnit.MINUTES);
    }

    public Set<String> getInstalledIotLauncherApps(IDevice device, long timeout, TimeUnit unit) {
        if (!device.supportsFeature(IDevice.HardwareFeature.EMBEDDED)) {
            return Collections.emptySet();
        }
        LauncherPackagesReceiver launcherPackagesReceiver = new LauncherPackagesReceiver();
        SystemPackagesReceiver systemPackagesReceiver = new SystemPackagesReceiver();
        CombinedReceiver combinedReceiver = new CombinedReceiver(launcherPackagesReceiver, systemPackagesReceiver);
        try {
            device.executeShellCommand(DUMP_PACKAGES_CMD, combinedReceiver, timeout, unit);
        }
        catch (Exception e2) {
            Log.e(TAG, e2);
        }
        HashSet<String> thirdPartyLauncherPackages = new HashSet<String>(launcherPackagesReceiver.getMatchingPackages());
        Set<String> systemPackages = systemPackagesReceiver.getMatchingPackages();
        thirdPartyLauncherPackages.removeAll(systemPackages);
        return thirdPartyLauncherPackages;
    }

    private static abstract class PackageCollectorReceiver
    extends MultiLineReceiver {
        private static final Pattern ParagraphRegex = Pattern.compile("^([\\w\\.]+):$");
        private final Set<String> matchingPackages = new HashSet<String>();
        private String currentPackage;
        private boolean mainPart = false;
        private boolean isCancelled = false;
        private String paragraphName;
        private Pattern packageRegex;

        private PackageCollectorReceiver(String paragraphName, Pattern packageRegex) {
            this.paragraphName = paragraphName;
            this.packageRegex = packageRegex;
        }

        @Override
        public void processNewLines(String[] lines) {
            for (String l2 : lines) {
                this.processNewLine(l2);
            }
        }

        private void processNewLine(String line) {
            boolean stateChanged = this.updateCurrentPart(line);
            if (stateChanged) {
                return;
            }
            if (this.mainPart) {
                stateChanged = this.updateCurrentPackage(line);
                if (stateChanged) {
                    return;
                }
                if (!this.matchingPackages.contains(this.currentPackage) && this.packageQualifies(line)) {
                    this.matchingPackages.add(this.currentPackage);
                }
            }
        }

        private boolean updateCurrentPart(String line) {
            Matcher matcher = ParagraphRegex.matcher(line);
            if (matcher.matches() && matcher.group(1).equals(this.paragraphName)) {
                this.mainPart = true;
                return true;
            }
            return false;
        }

        private boolean updateCurrentPackage(String line) {
            Matcher matcher = this.packageRegex.matcher(line);
            if (matcher.matches()) {
                this.currentPackage = matcher.group(1);
                return true;
            }
            return false;
        }

        abstract boolean packageQualifies(String var1);

        @Override
        public boolean isCancelled() {
            return this.isCancelled;
        }

        public Set<String> getMatchingPackages() {
            return this.matchingPackages;
        }
    }

    static class LauncherPackagesReceiver
    extends PackageCollectorReceiver {
        private static final String FiltersPart = "android.intent.action.MAIN";
        private static final Pattern FiltersPackageRegex = Pattern.compile("^\\w+ ([\\w\\.]+)/\\.\\w+ filter \\w+$");
        private static final String IotLauncher = "android.intent.category.IOT_LAUNCHER";

        LauncherPackagesReceiver() {
            super(FiltersPart, FiltersPackageRegex);
        }

        @Override
        boolean packageQualifies(String line) {
            return line.contains(IotLauncher);
        }
    }

    static class SystemPackagesReceiver
    extends PackageCollectorReceiver {
        private static final String PackagesPart = "Packages";
        private static final Pattern PackagesPackageRegex = Pattern.compile("^Package \\[([\\w\\.]+)\\] \\(\\w+\\):$");
        private static final Pattern FlagsRegex = Pattern.compile("^flags=\\[ ([\\w\\s_]+) \\]$");
        private static final String SYSTEM_FLAG = "SYSTEM";

        SystemPackagesReceiver() {
            super(PackagesPart, PackagesPackageRegex);
        }

        @Override
        boolean packageQualifies(String line) {
            Matcher matcher = FlagsRegex.matcher(line);
            if (matcher.matches()) {
                String[] flags;
                for (String flag : flags = matcher.group(1).split(" ")) {
                    if (!flag.equals(SYSTEM_FLAG)) continue;
                    return true;
                }
            }
            return false;
        }
    }

    static class CombinedReceiver
    extends MultiLineReceiver {
        private MultiLineReceiver[] receivers;

        public CombinedReceiver(MultiLineReceiver ... receivers) {
            this.receivers = receivers;
        }

        @Override
        public void processNewLines(String[] lines) {
            for (MultiLineReceiver receiver : this.receivers) {
                receiver.processNewLines(lines);
            }
        }

        @Override
        public boolean isCancelled() {
            for (MultiLineReceiver receiver : this.receivers) {
                if (receiver.isCancelled()) continue;
                return false;
            }
            return true;
        }
    }
}

