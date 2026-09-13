/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 */
package org.apereo.cas.util.ssl;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.net.ssl.X509KeyManager;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apereo.cas.util.CollectionUtils;

public class CompositeX509KeyManager
implements X509KeyManager {
    private final List<X509KeyManager> keyManagers;

    @Override
    public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
        return this.keyManagers.stream().map(keyManager -> keyManager.chooseClientAlias(keyType, issuers, socket)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return this.keyManagers.stream().map(keyManager -> keyManager.chooseServerAlias(keyType, issuers, socket)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        return this.keyManagers.stream().map(keyManager -> keyManager.getPrivateKey(alias)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        return this.keyManagers.stream().map(keyManager -> keyManager.getCertificateChain(alias)).filter(chain -> chain != null && ((X509Certificate[])chain).length > 0).findFirst().orElse(null);
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        ArrayList aliases = new ArrayList(this.keyManagers.size());
        this.keyManagers.forEach(keyManager -> aliases.addAll(CollectionUtils.wrapList(keyManager.getClientAliases(keyType, issuers))));
        return aliases.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        ArrayList aliases = new ArrayList(this.keyManagers.size());
        this.keyManagers.forEach(keyManager -> aliases.addAll(CollectionUtils.wrapList(keyManager.getServerAliases(keyType, issuers))));
        return aliases.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    @Generated
    public CompositeX509KeyManager(List<X509KeyManager> keyManagers) {
        this.keyManagers = keyManagers;
    }
}

