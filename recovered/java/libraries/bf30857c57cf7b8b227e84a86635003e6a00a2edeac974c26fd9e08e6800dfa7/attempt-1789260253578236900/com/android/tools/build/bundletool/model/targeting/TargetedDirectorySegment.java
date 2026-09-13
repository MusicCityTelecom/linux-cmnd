/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.targeting;

import com.android.bundle.Targeting;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.targeting.AutoValue_TargetedDirectorySegment;
import com.android.tools.build.bundletool.model.targeting.TargetingDimension;
import com.android.tools.build.bundletool.model.targeting.TargetingUtils;
import com.android.tools.build.bundletool.model.utils.GraphicsApiUtils;
import com.android.tools.build.bundletool.model.utils.TextureCompressionUtils;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import com.google.errorprone.annotations.Immutable;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.CheckReturnValue;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class TargetedDirectorySegment {
    private static final Pattern DIRECTORY_SEGMENT_PATTERN = Pattern.compile("(?<base>.+?)#(?<key>.+?)_(?<value>.+)");
    private static final Pattern LANGUAGE_CODE_PATTERN = Pattern.compile("^[a-zA-Z]{2,3}$");
    private static final String OPENGL_KEY = "opengl";
    private static final String VULKAN_KEY = "vulkan";
    private static final String LANG_KEY = "lang";
    private static final String TCF_KEY = "tcf";
    private static final ImmutableMap<String, TargetingDimension> KEY_TO_DIMENSION = ImmutableMap.builder().put("opengl", TargetingDimension.GRAPHICS_API).put("vulkan", TargetingDimension.GRAPHICS_API).put("lang", TargetingDimension.LANGUAGE).put("tcf", TargetingDimension.TEXTURE_COMPRESSION_FORMAT).build();
    private static final ImmutableSetMultimap<TargetingDimension, String> DIMENSION_TO_KEY = KEY_TO_DIMENSION.asMultimap().inverse();
    private static final Pattern OPENGL_VALUE_PATTERN = Pattern.compile("(\\d)\\.(\\d)");
    private static final Pattern VULKAN_VALUE_PATTERN = Pattern.compile("(\\d)\\.(\\d)");

    public abstract String getName();

    public abstract Targeting.AssetsDirectoryTargeting getTargeting();

    public Optional<TargetingDimension> getTargetingDimension() {
        ImmutableList<TargetingDimension> dimensions = TargetingUtils.getTargetingDimensions(this.getTargeting());
        Preconditions.checkState(dimensions.size() <= 1);
        if (dimensions.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(dimensions.get(0));
    }

    @CheckReturnValue
    public TargetedDirectorySegment removeTargeting(TargetingDimension dimension) {
        Targeting.AssetsDirectoryTargeting.Builder newTargeting = this.getTargeting().toBuilder();
        if (dimension.equals((Object)TargetingDimension.ABI) && this.getTargeting().hasAbi()) {
            newTargeting.clearAbi();
        } else if (dimension.equals((Object)TargetingDimension.GRAPHICS_API) && this.getTargeting().hasGraphicsApi()) {
            newTargeting.clearGraphicsApi();
        } else if (dimension.equals((Object)TargetingDimension.LANGUAGE) && this.getTargeting().hasLanguage()) {
            newTargeting.clearLanguage();
        } else if (dimension.equals((Object)TargetingDimension.TEXTURE_COMPRESSION_FORMAT) && this.getTargeting().hasTextureCompressionFormat()) {
            newTargeting.clearTextureCompressionFormat();
        } else {
            return this;
        }
        return new AutoValue_TargetedDirectorySegment(this.getName(), newTargeting.build());
    }

    public static TargetedDirectorySegment parse(String directorySegment) {
        if (!directorySegment.contains("#")) {
            return TargetedDirectorySegment.create(directorySegment);
        }
        Matcher matcher = DIRECTORY_SEGMENT_PATTERN.matcher(directorySegment);
        if (matcher.matches()) {
            return TargetedDirectorySegment.create(matcher.group("base"), matcher.group("key"), matcher.group("value"));
        }
        throw ValidationException.builder().withMessage("Cannot tokenize targeted directory '%s'. Expecting either '<name>' or '<name>#<key>_<value>' format.", directorySegment).build();
    }

    public String toPathSegment() {
        ImmutableList<TargetingDimension> dimensions = TargetingUtils.getTargetingDimensions(this.getTargeting());
        Preconditions.checkState(dimensions.size() <= 1);
        Optional<String> key = TargetedDirectorySegment.getTargetingKey(this.getTargeting());
        Optional<String> value = TargetedDirectorySegment.getTargetingValue(this.getTargeting());
        if (!key.isPresent() || !value.isPresent()) {
            return this.getName();
        }
        return String.format("%s#%s_%s", this.getName(), key.get(), value.get());
    }

    public static boolean pathMayContain(String path, TargetingDimension dimension) {
        ImmutableCollection keys2 = DIMENSION_TO_KEY.get((Object)dimension);
        return keys2.stream().anyMatch(key -> path.contains("#" + key + "_"));
    }

    private static TargetedDirectorySegment create(String name) {
        return new AutoValue_TargetedDirectorySegment(name, Targeting.AssetsDirectoryTargeting.getDefaultInstance());
    }

    private static TargetedDirectorySegment create(String name, String key, String value) {
        return new AutoValue_TargetedDirectorySegment(name, TargetedDirectorySegment.toAssetsDirectoryTargeting(name, key, value));
    }

    private static Optional<String> getTargetingKey(Targeting.AssetsDirectoryTargeting targeting) {
        ImmutableList<TargetingDimension> dimensions = TargetingUtils.getTargetingDimensions(targeting);
        Preconditions.checkArgument(dimensions.size() <= 1, "Multiple targeting for a same directory is not supported");
        if (targeting.hasLanguage()) {
            return Optional.of(LANG_KEY);
        }
        if (targeting.hasGraphicsApi()) {
            return TargetedDirectorySegment.getGraphicsApiKey(targeting);
        }
        if (targeting.hasTextureCompressionFormat()) {
            return Optional.of(TCF_KEY);
        }
        return Optional.empty();
    }

    private static Optional<String> getTargetingValue(Targeting.AssetsDirectoryTargeting targeting) {
        ImmutableList<TargetingDimension> dimensions = TargetingUtils.getTargetingDimensions(targeting);
        Preconditions.checkArgument(dimensions.size() <= 1, "Multiple targeting for a same directory is not supported");
        if (targeting.hasLanguage()) {
            return Optional.of(Iterables.getOnlyElement(targeting.getLanguage().getValueList()));
        }
        if (targeting.hasGraphicsApi()) {
            return TargetedDirectorySegment.getGraphicsApiValue(targeting);
        }
        if (targeting.hasTextureCompressionFormat()) {
            return Optional.ofNullable(TextureCompressionUtils.TARGETING_TO_TEXTURE.getOrDefault(Iterables.getOnlyElement(targeting.getTextureCompressionFormat().getValueList()).getAlias(), null));
        }
        return Optional.empty();
    }

    private static Targeting.AssetsDirectoryTargeting toAssetsDirectoryTargeting(String name, String key, String value) {
        if (!KEY_TO_DIMENSION.containsKey(key)) {
            throw ValidationException.builder().withMessage("Directory '%s' contains unsupported key '%s'.", name, key).build();
        }
        switch (KEY_TO_DIMENSION.get(key)) {
            case GRAPHICS_API: {
                return TargetedDirectorySegment.parseGraphicsApi(name, key, value);
            }
            case LANGUAGE: {
                return TargetedDirectorySegment.parseLanguage(name, value);
            }
            case TEXTURE_COMPRESSION_FORMAT: {
                return TargetedDirectorySegment.parseTextureCompressionFormat(name, value);
            }
        }
        throw ValidationException.builder().withMessage("Unrecognized key: '%s'.", key).build();
    }

    private static Targeting.AssetsDirectoryTargeting parseGraphicsApi(String name, String key, String value) {
        Targeting.GraphicsApiTargeting graphicsApiTargeting;
        switch (key) {
            case "opengl": {
                Matcher matcher = OPENGL_VALUE_PATTERN.matcher(value);
                if (!matcher.matches()) {
                    throw ValidationException.builder().withMessage("Could not parse OpenGL version '%s' for the directory '%s'.", value, name).build();
                }
                graphicsApiTargeting = GraphicsApiUtils.openGlVersionFrom(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                break;
            }
            case "vulkan": {
                Matcher matcher = VULKAN_VALUE_PATTERN.matcher(value);
                if (!matcher.matches()) {
                    throw ValidationException.builder().withMessage("Could not parse Vulkan version '%s' for the directory '%s'.", value, name).build();
                }
                graphicsApiTargeting = GraphicsApiUtils.vulkanVersionFrom(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                break;
            }
            default: {
                throw new ValidationException("Not a valid graphics API identifier: " + key);
            }
        }
        return Targeting.AssetsDirectoryTargeting.newBuilder().setGraphicsApi(graphicsApiTargeting).build();
    }

    private static Optional<String> getGraphicsApiKey(Targeting.AssetsDirectoryTargeting targeting) {
        Targeting.GraphicsApi graphicsApi = Iterables.getOnlyElement(targeting.getGraphicsApi().getValueList());
        if (graphicsApi.hasMinOpenGlVersion()) {
            return Optional.of(OPENGL_KEY);
        }
        if (graphicsApi.hasMinVulkanVersion()) {
            return Optional.of(VULKAN_KEY);
        }
        return Optional.empty();
    }

    private static Optional<String> getGraphicsApiValue(Targeting.AssetsDirectoryTargeting targeting) {
        Targeting.GraphicsApi graphicsApi = Iterables.getOnlyElement(targeting.getGraphicsApi().getValueList());
        if (graphicsApi.hasMinOpenGlVersion()) {
            int majorVersion = graphicsApi.getMinOpenGlVersion().getMajor();
            int minorVersion = graphicsApi.getMinOpenGlVersion().getMinor();
            return Optional.of(String.format("%d.%d", majorVersion, minorVersion));
        }
        if (graphicsApi.hasMinVulkanVersion()) {
            int majorVersion = graphicsApi.getMinVulkanVersion().getMajor();
            int minorVersion = graphicsApi.getMinVulkanVersion().getMinor();
            return Optional.of(String.format("%d.%d", majorVersion, minorVersion));
        }
        return Optional.empty();
    }

    private static Targeting.AssetsDirectoryTargeting parseTextureCompressionFormat(String name, String value) {
        if (!TextureCompressionUtils.TEXTURE_TO_TARGETING.containsKey(value)) {
            throw ValidationException.builder().withMessage("Unrecognized value of the texture compression format targeting '%s' for directory '%s'.", value, name).build();
        }
        return Targeting.AssetsDirectoryTargeting.newBuilder().setTextureCompressionFormat(TextureCompressionUtils.TEXTURE_TO_TARGETING.get(value)).build();
    }

    private static Targeting.AssetsDirectoryTargeting parseLanguage(String name, String value) {
        Matcher matcher = LANGUAGE_CODE_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw ValidationException.builder().withMessage("Expected 2- or 3-character language directory but got '%s' for directory '%s'.", value, name).build();
        }
        return Targeting.AssetsDirectoryTargeting.newBuilder().setLanguage(Targeting.LanguageTargeting.newBuilder().addValue(value.toLowerCase())).build();
    }
}

