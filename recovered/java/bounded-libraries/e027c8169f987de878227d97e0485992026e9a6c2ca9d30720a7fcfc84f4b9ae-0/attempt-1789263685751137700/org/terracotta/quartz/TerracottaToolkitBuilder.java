/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.terracotta.toolkit.Toolkit
 *  org.terracotta.toolkit.ToolkitFactory
 *  org.terracotta.toolkit.ToolkitInstantiationException
 */
package org.terracotta.quartz;

import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.terracotta.toolkit.Toolkit;
import org.terracotta.toolkit.ToolkitFactory;
import org.terracotta.toolkit.ToolkitInstantiationException;

public class TerracottaToolkitBuilder {
    private static final String TC_TUNNELLED_MBEAN_DOMAIN_KEY = "tunnelledMBeanDomains";
    private static final String TC_CONFIG_SNIPPET_KEY = "tcConfigSnippet";
    private static final String TC_REJOIN_KEY = "rejoin";
    private final TCConfigTypeStatus tcConfigTypeStatus = new TCConfigTypeStatus();
    private final Set<String> tunnelledMBeanDomains = Collections.synchronizedSet(new HashSet());
    private boolean rejoin = false;

    public Toolkit buildToolkit() throws IllegalStateException {
        if (this.tcConfigTypeStatus.getState() == TCConfigTypeState.INIT) {
            throw new IllegalStateException("Please set the tcConfigSnippet or tcConfigUrl before attempting to create client");
        }
        Properties properties = new Properties();
        properties.setProperty(TC_TUNNELLED_MBEAN_DOMAIN_KEY, this.getTunnelledDomainCSV());
        properties.setProperty(TC_REJOIN_KEY, Boolean.toString(this.isRejoin()));
        switch (this.tcConfigTypeStatus.getState()) {
            case TC_CONFIG_SNIPPET: {
                properties.setProperty(TC_CONFIG_SNIPPET_KEY, this.tcConfigTypeStatus.getTcConfigSnippet());
                return this.createToolkit("toolkit:terracotta:", properties);
            }
            case TC_CONFIG_URL: {
                return this.createToolkit("toolkit:terracotta://" + this.tcConfigTypeStatus.getTcConfigUrl(), properties);
            }
        }
        throw new IllegalStateException("Unknown tc config type - " + (Object)((Object)this.tcConfigTypeStatus.getState()));
    }

    private Toolkit createToolkit(String toolkitUrl, Properties props) {
        try {
            return ToolkitFactory.createToolkit((String)toolkitUrl, (Properties)props);
        }
        catch (ToolkitInstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTunnelledDomainCSV() {
        StringBuilder sb = new StringBuilder();
        for (String domain : this.tunnelledMBeanDomains) {
            sb.append(domain).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public TerracottaToolkitBuilder addTunnelledMBeanDomain(String tunnelledMBeanDomain) {
        this.tunnelledMBeanDomains.add(tunnelledMBeanDomain);
        return this;
    }

    public Set<String> getTunnelledMBeanDomains() {
        return Collections.unmodifiableSet(this.tunnelledMBeanDomains);
    }

    public TerracottaToolkitBuilder removeTunnelledMBeanDomain(String tunnelledMBeanDomain) {
        this.tunnelledMBeanDomains.remove(tunnelledMBeanDomain);
        return this;
    }

    public TerracottaToolkitBuilder setTCConfigSnippet(String tcConfig) throws IllegalStateException {
        this.tcConfigTypeStatus.setTcConfigSnippet(tcConfig);
        return this;
    }

    public String getTCConfigSnippet() {
        return this.tcConfigTypeStatus.getTcConfigSnippet();
    }

    public TerracottaToolkitBuilder setTCConfigUrl(String tcConfigUrl) throws IllegalStateException {
        this.tcConfigTypeStatus.setTcConfigUrl(tcConfigUrl);
        return this;
    }

    public String getTCConfigUrl() {
        return this.tcConfigTypeStatus.getTcConfigUrl();
    }

    public boolean isConfigUrl() {
        return this.tcConfigTypeStatus.getState() == TCConfigTypeState.TC_CONFIG_URL;
    }

    public TerracottaToolkitBuilder setRejoin(String rejoin) {
        this.rejoin = Boolean.valueOf(rejoin);
        return this;
    }

    public boolean isRejoin() {
        return this.rejoin;
    }

    private static class TCConfigTypeStatus {
        private TCConfigTypeState state = TCConfigTypeState.INIT;
        private String tcConfigSnippet;
        private String tcConfigUrl;

        private TCConfigTypeStatus() {
        }

        public synchronized void setTcConfigSnippet(String tcConfigSnippet) {
            if (this.state == TCConfigTypeState.TC_CONFIG_URL) {
                throw new IllegalStateException("TCConfig url was already set to - " + this.tcConfigUrl);
            }
            this.state = TCConfigTypeState.TC_CONFIG_SNIPPET;
            this.tcConfigSnippet = tcConfigSnippet;
        }

        public synchronized void setTcConfigUrl(String tcConfigUrl) {
            if (this.state == TCConfigTypeState.TC_CONFIG_SNIPPET) {
                throw new IllegalStateException("TCConfig snippet was already set to - " + this.tcConfigSnippet);
            }
            this.state = TCConfigTypeState.TC_CONFIG_URL;
            this.tcConfigUrl = tcConfigUrl;
        }

        public synchronized String getTcConfigSnippet() {
            return this.tcConfigSnippet;
        }

        public synchronized String getTcConfigUrl() {
            return this.tcConfigUrl;
        }

        public TCConfigTypeState getState() {
            return this.state;
        }
    }

    private static enum TCConfigTypeState {
        INIT,
        TC_CONFIG_SNIPPET,
        TC_CONFIG_URL;

    }
}

