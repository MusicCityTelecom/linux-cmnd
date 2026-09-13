/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.bean;

import java.io.IOException;
import java.security.PrivateKey;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.bean.FactoryBean;
import org.cryptacular.io.Resource;
import org.cryptacular.util.KeyPairUtil;

public class ResourceBasedPrivateKeyFactoryBean
implements FactoryBean<PrivateKey> {
    private Resource resource;
    private String password;

    public ResourceBasedPrivateKeyFactoryBean() {
    }

    public ResourceBasedPrivateKeyFactoryBean(Resource resource) {
        this.setResource(resource);
    }

    public ResourceBasedPrivateKeyFactoryBean(Resource resource, String decryptionPassword) {
        this.setResource(resource);
        this.setPassword(decryptionPassword);
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setPassword(String decryptionPassword) {
        this.password = decryptionPassword;
    }

    @Override
    public PrivateKey newInstance() throws EncodingException, StreamException {
        try {
            if (this.password != null) {
                return KeyPairUtil.readPrivateKey(this.resource.getInputStream(), this.password.toCharArray());
            }
            return KeyPairUtil.readPrivateKey(this.resource.getInputStream());
        }
        catch (IOException e) {
            throw new StreamException(e);
        }
    }
}

