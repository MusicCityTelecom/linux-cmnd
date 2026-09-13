/*
 * Decompiled with CFR 0.152.
 */
package com.vdurmont.semver4j;

import com.vdurmont.semver4j.Requirement;
import com.vdurmont.semver4j.SemverException;
import java.util.Objects;

public class Semver
implements Comparable<Semver> {
    private final String originalValue;
    private final String value;
    private final Integer major;
    private final Integer minor;
    private final Integer patch;
    private final String[] suffixTokens;
    private final String build;
    private final SemverType type;

    public Semver(String value) {
        this(value, SemverType.STRICT);
    }

    public Semver(String value, SemverType type) {
        String[] tmp;
        this.originalValue = value;
        this.type = type;
        value = value.trim();
        if (type == SemverType.NPM && (value.startsWith("v") || value.startsWith("V"))) {
            value = value.substring(1).trim();
        }
        this.value = value;
        String[] tokens = this.hasPreRelease(value) ? value.split("-", 2) : new String[]{value};
        String build = null;
        Integer minor = null;
        Integer patch = null;
        try {
            String[] mainTokens;
            block25: {
                if (tokens.length == 1) {
                    if (tokens[0].endsWith("+")) {
                        throw new SemverException("The build cannot be empty.");
                    }
                    tmp = tokens[0].split("\\+");
                    mainTokens = tmp[0].split("\\.");
                    if (tmp.length == 2) {
                        build = tmp[1];
                    }
                } else {
                    mainTokens = tokens[0].split("\\.");
                }
                try {
                    this.major = Integer.valueOf(mainTokens[0]);
                }
                catch (NumberFormatException e) {
                    throw new SemverException("Invalid version (no major version): " + value);
                }
                catch (IndexOutOfBoundsException e) {
                    throw new SemverException("Invalid version (no major version): " + value);
                }
                try {
                    minor = Integer.valueOf(mainTokens[1]);
                }
                catch (IndexOutOfBoundsException e) {
                    if (type == SemverType.STRICT) {
                        throw new SemverException("Invalid version (no minor version): " + value);
                    }
                }
                catch (NumberFormatException e) {
                    if (type == SemverType.NPM && ("x".equalsIgnoreCase(mainTokens[1]) || "*".equals(mainTokens[1]))) break block25;
                    throw new SemverException("Invalid version (no minor version): " + value);
                }
            }
            try {
                patch = Integer.valueOf(mainTokens[2]);
            }
            catch (IndexOutOfBoundsException e) {
                if (type == SemverType.STRICT) {
                    throw new SemverException("Invalid version (no patch version): " + value);
                }
            }
            catch (NumberFormatException e) {
                if (type != SemverType.NPM || !"x".equalsIgnoreCase(mainTokens[2]) && !"*".equals(mainTokens[2])) {
                    throw new SemverException("Invalid version (no patch version): " + value);
                }
            }
        }
        catch (NumberFormatException e) {
            throw new SemverException("The version is invalid: " + value);
        }
        catch (IndexOutOfBoundsException e) {
            throw new SemverException("The version is invalid: " + value);
        }
        this.minor = minor;
        this.patch = patch;
        String[] suffix = new String[]{};
        try {
            if (tokens[1].endsWith("+")) {
                throw new SemverException("The build cannot be empty.");
            }
            tmp = tokens[1].split("\\+");
            if (tmp.length == 2) {
                suffix = tmp[0].split("\\.");
                build = tmp[1];
            } else {
                suffix = tokens[1].split("\\.");
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
        this.suffixTokens = suffix;
        this.build = build;
        this.validate(type);
    }

    private void validate(SemverType type) {
        if (this.minor == null && type == SemverType.STRICT) {
            throw new SemverException("Invalid version (no minor version): " + this.value);
        }
        if (this.patch == null && type == SemverType.STRICT) {
            throw new SemverException("Invalid version (no patch version): " + this.value);
        }
    }

    private boolean hasPreRelease(String version) {
        int firstIndexOfPlus = this.value.indexOf("+");
        int firstIndexOfHyphen = this.value.indexOf("-");
        if (firstIndexOfHyphen == -1) {
            return false;
        }
        return firstIndexOfPlus == -1 || firstIndexOfHyphen < firstIndexOfPlus;
    }

    public boolean satisfies(Requirement requirement) {
        return requirement.isSatisfiedBy(this);
    }

    public boolean satisfies(String requirement) {
        Requirement req;
        switch (this.type) {
            case STRICT: {
                req = Requirement.buildStrict(requirement);
                break;
            }
            case LOOSE: {
                req = Requirement.buildLoose(requirement);
                break;
            }
            case NPM: {
                req = Requirement.buildNPM(requirement);
                break;
            }
            case COCOAPODS: {
                req = Requirement.buildCocoapods(requirement);
                break;
            }
            case IVY: {
                req = Requirement.buildIvy(requirement);
                break;
            }
            default: {
                throw new SemverException("Invalid requirement type: " + (Object)((Object)this.type));
            }
        }
        return this.satisfies(req);
    }

    public boolean isGreaterThan(String version) {
        return this.isGreaterThan(new Semver(version, this.getType()));
    }

    public boolean isGreaterThan(Semver version) {
        int otherPatch;
        int otherMinor;
        if (this.getMajor() > version.getMajor()) {
            return true;
        }
        if (this.getMajor() < version.getMajor()) {
            return false;
        }
        if (this.type == SemverType.NPM && version.getMinor() == null) {
            return false;
        }
        int n = otherMinor = version.getMinor() != null ? version.getMinor() : 0;
        if (this.getMinor() != null && this.getMinor() > otherMinor) {
            return true;
        }
        if (this.getMinor() != null && this.getMinor() < otherMinor) {
            return false;
        }
        if (this.type == SemverType.NPM && version.getPatch() == null) {
            return false;
        }
        int n2 = otherPatch = version.getPatch() != null ? version.getPatch() : 0;
        if (this.getPatch() != null && this.getPatch() > otherPatch) {
            return true;
        }
        if (this.getPatch() != null && this.getPatch() < otherPatch) {
            return false;
        }
        String[] tokens1 = this.getSuffixTokens();
        String[] tokens2 = version.getSuffixTokens();
        if (tokens1.length == 0 && tokens2.length > 0) {
            return true;
        }
        if (tokens2.length == 0 && tokens1.length > 0) {
            return false;
        }
        for (int i = 0; i < tokens1.length && i < tokens2.length; ++i) {
            int cmp;
            try {
                int t1 = Integer.valueOf(tokens1[i]);
                int t2 = Integer.valueOf(tokens2[i]);
                cmp = t1 - t2;
            }
            catch (NumberFormatException e) {
                cmp = tokens1[i].compareToIgnoreCase(tokens2[i]);
            }
            if (cmp < 0) {
                return false;
            }
            if (cmp <= 0) continue;
            return true;
        }
        return tokens1.length > tokens2.length;
    }

    public boolean isGreaterThanOrEqualTo(String version) {
        return this.isGreaterThanOrEqualTo(new Semver(version, this.type));
    }

    public boolean isGreaterThanOrEqualTo(Semver version) {
        return this.isGreaterThan(version) || this.isEquivalentTo(version);
    }

    public boolean isLowerThan(String version) {
        return this.isLowerThan(new Semver(version, this.type));
    }

    public boolean isLowerThan(Semver version) {
        return !this.isGreaterThan(version) && !this.isEquivalentTo(version);
    }

    public boolean isLowerThanOrEqualTo(String version) {
        return this.isLowerThanOrEqualTo(new Semver(version, this.type));
    }

    public boolean isLowerThanOrEqualTo(Semver version) {
        return !this.isGreaterThan(version);
    }

    public boolean isEquivalentTo(String version) {
        return this.isEquivalentTo(new Semver(version, this.type));
    }

    public boolean isEquivalentTo(Semver version) {
        Semver sem1 = this.getBuild() == null ? this : new Semver(this.getValue().replace("+" + this.getBuild(), ""));
        Semver sem2 = version.getBuild() == null ? version : new Semver(version.getValue().replace("+" + version.getBuild(), ""));
        return sem1.isEqualTo(sem2);
    }

    public boolean isEqualTo(String version) {
        return this.isEqualTo(new Semver(version, this.type));
    }

    public boolean isEqualTo(Semver version) {
        if (this.type == SemverType.NPM) {
            if (this.getMajor() != version.getMajor()) {
                return false;
            }
            if (version.getMinor() == null) {
                return true;
            }
            if (version.getPatch() == null) {
                return true;
            }
        }
        return this.equals(version);
    }

    public boolean isStable() {
        return this.getMajor() != null && this.getMajor() > 0 && (this.getSuffixTokens() == null || this.getSuffixTokens().length == 0);
    }

    public VersionDiff diff(String version) {
        return this.diff(new Semver(version, this.type));
    }

    public VersionDiff diff(Semver version) {
        if (!Objects.equals(this.major, version.getMajor())) {
            return VersionDiff.MAJOR;
        }
        if (!Objects.equals(this.minor, version.getMinor())) {
            return VersionDiff.MINOR;
        }
        if (!Objects.equals(this.patch, version.getPatch())) {
            return VersionDiff.PATCH;
        }
        if (!this.areSameSuffixes(version.getSuffixTokens())) {
            return VersionDiff.SUFFIX;
        }
        if (!Objects.equals(this.build, version.getBuild())) {
            return VersionDiff.BUILD;
        }
        return VersionDiff.NONE;
    }

    private boolean areSameSuffixes(String[] suffixTokens) {
        if (this.suffixTokens == null && suffixTokens == null) {
            return true;
        }
        if (this.suffixTokens == null || suffixTokens == null) {
            return false;
        }
        if (this.suffixTokens.length != suffixTokens.length) {
            return false;
        }
        for (int i = 0; i < this.suffixTokens.length; ++i) {
            if (this.suffixTokens[i].equals(suffixTokens[i])) continue;
            return false;
        }
        return true;
    }

    public Semver toStrict() {
        Integer minor = this.minor != null ? this.minor : 0;
        Integer patch = this.patch != null ? this.patch : 0;
        return Semver.create(SemverType.STRICT, this.major, minor, patch, this.suffixTokens, this.build);
    }

    public Semver withIncMajor() {
        return this.withIncMajor(1);
    }

    public Semver withIncMajor(int increment) {
        return this.withInc(increment, 0, 0);
    }

    public Semver withIncMinor() {
        return this.withIncMinor(1);
    }

    public Semver withIncMinor(int increment) {
        return this.withInc(0, increment, 0);
    }

    public Semver withIncPatch() {
        return this.withIncPatch(1);
    }

    public Semver withIncPatch(int increment) {
        return this.withInc(0, 0, increment);
    }

    private Semver withInc(int majorInc, int minorInc, int patchInc) {
        Integer minor = this.minor;
        Integer patch = this.patch;
        if (this.minor != null) {
            minor = minor + minorInc;
        }
        if (this.patch != null) {
            patch = patch + patchInc;
        }
        return this.with(this.major + majorInc, minor, patch, true, true);
    }

    public Semver withClearedSuffix() {
        return this.with((int)this.major, this.minor, this.patch, false, true);
    }

    public Semver withClearedBuild() {
        return this.with((int)this.major, this.minor, this.patch, true, false);
    }

    public Semver withClearedSuffixAndBuild() {
        return this.with((int)this.major, this.minor, this.patch, false, false);
    }

    public Semver withSuffix(String suffix) {
        return this.with((int)this.major, this.minor, this.patch, suffix.split("\\."), this.build);
    }

    public Semver withBuild(String build) {
        return this.with((int)this.major, this.minor, this.patch, this.suffixTokens, build);
    }

    public Semver nextMajor() {
        return this.with(this.major + 1, (Integer)0, (Integer)0, false, false);
    }

    public Semver nextMinor() {
        return this.with((int)this.major, (Integer)(this.minor + 1), (Integer)0, false, false);
    }

    public Semver nextPatch() {
        return this.with((int)this.major, this.minor, (Integer)(this.patch + 1), false, false);
    }

    private Semver with(int major, Integer minor, Integer patch, boolean suffix, boolean build) {
        minor = this.minor != null ? minor : null;
        patch = this.patch != null ? patch : null;
        String buildStr = build ? this.build : null;
        String[] suffixTokens = suffix ? this.suffixTokens : null;
        return Semver.create(this.type, major, minor, patch, suffixTokens, buildStr);
    }

    private Semver with(int major, Integer minor, Integer patch, String[] suffixTokens, String build) {
        minor = this.minor != null ? minor : null;
        patch = this.patch != null ? patch : null;
        return Semver.create(this.type, major, minor, patch, suffixTokens, build);
    }

    private static Semver create(SemverType type, int major, Integer minor, Integer patch, String[] suffix, String build) {
        StringBuilder sb = new StringBuilder().append(major);
        if (minor != null) {
            sb.append(".").append(minor);
        }
        if (patch != null) {
            sb.append(".").append(patch);
        }
        if (suffix != null) {
            boolean first = true;
            for (String suffixToken : suffix) {
                if (first) {
                    sb.append("-");
                    first = false;
                } else {
                    sb.append(".");
                }
                sb.append(suffixToken);
            }
        }
        if (build != null) {
            sb.append("+").append(build);
        }
        return new Semver(sb.toString(), type);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Semver)) {
            return false;
        }
        Semver version = (Semver)o;
        return this.value.equals(version.value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public int compareTo(Semver version) {
        if (this.isGreaterThan(version)) {
            return 1;
        }
        if (this.isLowerThan(version)) {
            return -1;
        }
        return 0;
    }

    public String toString() {
        return this.getValue();
    }

    public String getOriginalValue() {
        return this.originalValue;
    }

    public String getValue() {
        return this.value;
    }

    public Integer getMajor() {
        return this.major;
    }

    public Integer getMinor() {
        return this.minor;
    }

    public Integer getPatch() {
        return this.patch;
    }

    public String[] getSuffixTokens() {
        return this.suffixTokens;
    }

    public String getBuild() {
        return this.build;
    }

    public SemverType getType() {
        return this.type;
    }

    public static enum SemverType {
        STRICT,
        LOOSE,
        NPM,
        COCOAPODS,
        IVY;

    }

    public static enum VersionDiff {
        NONE,
        MAJOR,
        MINOR,
        PATCH,
        SUFFIX,
        BUILD;

    }
}

