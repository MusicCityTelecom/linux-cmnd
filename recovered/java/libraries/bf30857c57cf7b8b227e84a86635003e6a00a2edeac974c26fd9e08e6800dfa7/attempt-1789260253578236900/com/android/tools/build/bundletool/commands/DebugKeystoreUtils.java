/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.tools.build.bundletool.model.Password;
import com.android.tools.build.bundletool.model.SigningConfiguration;
import com.android.tools.build.bundletool.model.utils.SystemEnvironmentProvider;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public final class DebugKeystoreUtils {
    private static final String ANDROID_SDK_HOME = "ANDROID_SDK_HOME";
    private static final String HOME = "HOME";
    private static final String USER_HOME = "user.home";
    private static final String DEBUG_KEY_ALIAS = "AndroidDebugKey";
    private static final String ANDROID_DOT_DIR = ".android";
    private static final String DEBUG_KEYSTORE_FILENAME = "debug.keystore";
    public static final Password DEBUG_KEY_PASSWORD = new Password(() -> new KeyStore.PasswordProtection("android".toCharArray()));
    public static final LoadingCache<SystemEnvironmentProvider, Optional<Path>> DEBUG_KEYSTORE_CACHE = CacheBuilder.newBuilder().build(CacheLoader.from(DebugKeystoreUtils::getDebugKeystorePath));

    public static Optional<SigningConfiguration> getDebugSigningConfiguration(SystemEnvironmentProvider provider) {
        try {
            return DEBUG_KEYSTORE_CACHE.get(provider).map(keystorePath -> SigningConfiguration.extractFromKeystore(keystorePath, DEBUG_KEY_ALIAS, Optional.of(DEBUG_KEY_PASSWORD), Optional.of(DEBUG_KEY_PASSWORD)));
        }
        catch (ExecutionException e2) {
            return Optional.empty();
        }
    }

    private static Optional<Path> getDebugKeystorePath(SystemEnvironmentProvider envProvider) {
        return Stream.of(envProvider.getVariable(ANDROID_SDK_HOME), envProvider.getProperty(USER_HOME), envProvider.getVariable(HOME)).filter(Optional::isPresent).map(Optional::get).map(x$0 -> Paths.get(x$0, new String[0])).filter(x$0 -> Files.isDirectory(x$0, new LinkOption[0])).map(path -> path.resolve(ANDROID_DOT_DIR).resolve(DEBUG_KEYSTORE_FILENAME)).filter(x$0 -> Files.exists(x$0, new LinkOption[0])).filter(Files::isReadable).findFirst();
    }

    private DebugKeystoreUtils() {
    }
}

