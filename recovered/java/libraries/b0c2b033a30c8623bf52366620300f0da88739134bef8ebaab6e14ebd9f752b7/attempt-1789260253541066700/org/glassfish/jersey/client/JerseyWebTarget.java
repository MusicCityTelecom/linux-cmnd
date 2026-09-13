/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.net.URI;
import java.util.LinkedList;
import java.util.Map;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.Initializable;
import org.glassfish.jersey.client.InvocationBuilderListenerStage;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyInvocation;
import org.glassfish.jersey.internal.guava.Preconditions;

public class JerseyWebTarget
implements WebTarget,
Initializable<JerseyWebTarget> {
    private final ClientConfig config;
    private final UriBuilder targetUri;

    JerseyWebTarget(String uri, JerseyClient parent) {
        this(UriBuilder.fromUri(uri), parent.getConfiguration());
    }

    JerseyWebTarget(URI uri, JerseyClient parent) {
        this(UriBuilder.fromUri(uri), parent.getConfiguration());
    }

    JerseyWebTarget(UriBuilder uriBuilder, JerseyClient parent) {
        this(uriBuilder.clone(), parent.getConfiguration());
    }

    JerseyWebTarget(Link link, JerseyClient parent) {
        this(UriBuilder.fromUri(link.getUri()), parent.getConfiguration());
    }

    protected JerseyWebTarget(UriBuilder uriBuilder, JerseyWebTarget that) {
        this(uriBuilder, that.config);
    }

    protected JerseyWebTarget(UriBuilder uriBuilder, ClientConfig clientConfig) {
        clientConfig.checkClient();
        this.targetUri = uriBuilder;
        this.config = clientConfig.snapshot();
    }

    @Override
    public URI getUri() {
        this.checkNotClosed();
        try {
            return this.targetUri.build(new Object[0]);
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    private void checkNotClosed() {
        this.config.getClient().checkNotClosed();
    }

    @Override
    public UriBuilder getUriBuilder() {
        this.checkNotClosed();
        return this.targetUri.clone();
    }

    @Override
    public JerseyWebTarget path(String path) throws NullPointerException {
        this.checkNotClosed();
        Preconditions.checkNotNull(path, "path is 'null'.");
        return new JerseyWebTarget(this.getUriBuilder().path(path), this);
    }

    @Override
    public JerseyWebTarget matrixParam(String name, Object ... values) throws NullPointerException {
        this.checkNotClosed();
        Preconditions.checkNotNull(name, "Matrix parameter name must not be 'null'.");
        if (values == null || values.length == 0 || values.length == 1 && values[0] == null) {
            return new JerseyWebTarget(this.getUriBuilder().replaceMatrixParam(name, null), this);
        }
        JerseyWebTarget.checkForNullValues(name, values);
        return new JerseyWebTarget(this.getUriBuilder().matrixParam(name, values), this);
    }

    @Override
    public JerseyWebTarget queryParam(String name, Object ... values) throws NullPointerException {
        this.checkNotClosed();
        return new JerseyWebTarget(JerseyWebTarget.setQueryParam(this.getUriBuilder(), name, values), this);
    }

    private static UriBuilder setQueryParam(UriBuilder uriBuilder, String name, Object[] values) {
        if (values == null || values.length == 0 || values.length == 1 && values[0] == null) {
            return uriBuilder.replaceQueryParam(name, null);
        }
        JerseyWebTarget.checkForNullValues(name, values);
        return uriBuilder.queryParam(name, values);
    }

    private static void checkForNullValues(String name, Object[] values) {
        Preconditions.checkNotNull(name, "name is 'null'.");
        LinkedList<Integer> indexes = new LinkedList<Integer>();
        for (int i = 0; i < values.length; ++i) {
            if (values[i] != null) continue;
            indexes.add(i);
        }
        int failedIndexCount = indexes.size();
        if (failedIndexCount > 0) {
            String indexTxt;
            String valueTxt;
            if (failedIndexCount == 1) {
                valueTxt = "value";
                indexTxt = "index";
            } else {
                valueTxt = "values";
                indexTxt = "indexes";
            }
            throw new NullPointerException(String.format("'null' %s detected for parameter '%s' on %s : %s", valueTxt, name, indexTxt, ((Object)indexes).toString()));
        }
    }

    @Override
    public JerseyInvocation.Builder request() {
        this.checkNotClosed();
        JerseyInvocation.Builder b = new JerseyInvocation.Builder(this.getUri(), this.config.snapshot());
        return JerseyWebTarget.onBuilder(b);
    }

    @Override
    public JerseyInvocation.Builder request(String ... acceptedResponseTypes) {
        this.checkNotClosed();
        JerseyInvocation.Builder b = new JerseyInvocation.Builder(this.getUri(), this.config.snapshot());
        JerseyWebTarget.onBuilder(b).request().accept(acceptedResponseTypes);
        return b;
    }

    @Override
    public JerseyInvocation.Builder request(MediaType ... acceptedResponseTypes) {
        this.checkNotClosed();
        JerseyInvocation.Builder b = new JerseyInvocation.Builder(this.getUri(), this.config.snapshot());
        JerseyWebTarget.onBuilder(b).request().accept(acceptedResponseTypes);
        return b;
    }

    @Override
    public JerseyWebTarget resolveTemplate(String name, Object value) throws NullPointerException {
        return this.resolveTemplate(name, value, true);
    }

    @Override
    public JerseyWebTarget resolveTemplate(String name, Object value, boolean encodeSlashInPath) throws NullPointerException {
        this.checkNotClosed();
        Preconditions.checkNotNull(name, "name is 'null'.");
        Preconditions.checkNotNull(value, "value is 'null'.");
        return new JerseyWebTarget(this.getUriBuilder().resolveTemplate(name, value, encodeSlashInPath), this);
    }

    @Override
    public JerseyWebTarget resolveTemplateFromEncoded(String name, Object value) throws NullPointerException {
        this.checkNotClosed();
        Preconditions.checkNotNull(name, "name is 'null'.");
        Preconditions.checkNotNull(value, "value is 'null'.");
        return new JerseyWebTarget(this.getUriBuilder().resolveTemplateFromEncoded(name, value), this);
    }

    @Override
    public JerseyWebTarget resolveTemplates(Map<String, Object> templateValues) throws NullPointerException {
        return this.resolveTemplates((Map)templateValues, true);
    }

    @Override
    public JerseyWebTarget resolveTemplates(Map<String, Object> templateValues, boolean encodeSlashInPath) throws NullPointerException {
        this.checkNotClosed();
        this.checkTemplateValues(templateValues);
        if (templateValues.isEmpty()) {
            return this;
        }
        return new JerseyWebTarget(this.getUriBuilder().resolveTemplates(templateValues, encodeSlashInPath), this);
    }

    @Override
    public JerseyWebTarget resolveTemplatesFromEncoded(Map<String, Object> templateValues) throws NullPointerException {
        this.checkNotClosed();
        this.checkTemplateValues(templateValues);
        if (templateValues.isEmpty()) {
            return this;
        }
        return new JerseyWebTarget(this.getUriBuilder().resolveTemplatesFromEncoded(templateValues), this);
    }

    private void checkTemplateValues(Map<String, Object> templateValues) throws NullPointerException {
        Preconditions.checkNotNull(templateValues, "templateValues is 'null'.");
        for (Map.Entry<String, Object> entry : templateValues.entrySet()) {
            Preconditions.checkNotNull(entry.getKey(), "name is 'null'.");
            Preconditions.checkNotNull(entry.getValue(), "value is 'null'.");
        }
    }

    @Override
    public JerseyWebTarget register(Class<?> providerClass) {
        this.checkNotClosed();
        this.config.register((Class)providerClass);
        return this;
    }

    @Override
    public JerseyWebTarget register(Object provider) {
        this.checkNotClosed();
        this.config.register(provider);
        return this;
    }

    @Override
    public JerseyWebTarget register(Class<?> providerClass, int bindingPriority) {
        this.checkNotClosed();
        this.config.register((Class)providerClass, bindingPriority);
        return this;
    }

    @Override
    public JerseyWebTarget register(Class<?> providerClass, Class<?> ... contracts) {
        this.checkNotClosed();
        this.config.register((Class)providerClass, (Class[])contracts);
        return this;
    }

    @Override
    public JerseyWebTarget register(Class<?> providerClass, Map<Class<?>, Integer> contracts) {
        this.checkNotClosed();
        this.config.register((Class)providerClass, (Map)contracts);
        return this;
    }

    @Override
    public JerseyWebTarget register(Object provider, int bindingPriority) {
        this.checkNotClosed();
        this.config.register(provider, bindingPriority);
        return this;
    }

    @Override
    public JerseyWebTarget register(Object provider, Class<?> ... contracts) {
        this.checkNotClosed();
        this.config.register(provider, (Class[])contracts);
        return this;
    }

    @Override
    public JerseyWebTarget register(Object provider, Map<Class<?>, Integer> contracts) {
        this.checkNotClosed();
        this.config.register(provider, (Map)contracts);
        return this;
    }

    @Override
    public JerseyWebTarget property(String name, Object value) {
        this.checkNotClosed();
        this.config.property(name, value);
        return this;
    }

    @Override
    public ClientConfig getConfiguration() {
        this.checkNotClosed();
        return this.config.getConfiguration();
    }

    @Override
    public JerseyWebTarget preInitialize() {
        this.config.preInitialize();
        return this;
    }

    public String toString() {
        return "JerseyWebTarget { " + this.targetUri.toTemplate() + " }";
    }

    private static JerseyInvocation.Builder onBuilder(JerseyInvocation.Builder builder) {
        new InvocationBuilderListenerStage(builder.request().getInjectionManager()).invokeListener(builder);
        return builder;
    }
}

