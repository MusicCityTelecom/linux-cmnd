/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.solr.client.solrj.SolrClient
 *  org.apache.solr.client.solrj.impl.BaseHttpSolrClient$RemoteSolrException
 *  org.apache.solr.client.solrj.request.CoreAdminRequest
 *  org.apache.solr.client.solrj.response.CoreAdminResponse
 *  org.apache.solr.common.params.CoreAdminParams$CoreAdminAction
 */
package org.springframework.boot.actuate.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.BaseHttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.params.CoreAdminParams;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

public class SolrHealthIndicator
extends AbstractHealthIndicator {
    private static final int HTTP_NOT_FOUND_STATUS = 404;
    private final SolrClient solrClient;
    private volatile StatusCheck statusCheck;

    public SolrHealthIndicator(SolrClient solrClient) {
        super("Solr health check failed");
        this.solrClient = solrClient;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        int statusCode = this.initializeStatusCheck();
        Status status = statusCode != 0 ? Status.DOWN : Status.UP;
        builder.status(status).withDetail("status", statusCode).withDetail("detectedPathType", this.statusCheck.getPathType());
    }

    private int initializeStatusCheck() throws Exception {
        StatusCheck statusCheck = this.statusCheck;
        if (statusCheck != null) {
            return statusCheck.getStatus(this.solrClient);
        }
        try {
            return this.initializeStatusCheck(new RootStatusCheck());
        }
        catch (BaseHttpSolrClient.RemoteSolrException ex) {
            if (ex.code() == 404) {
                return this.initializeStatusCheck(new ParticularCoreStatusCheck());
            }
            throw ex;
        }
    }

    private int initializeStatusCheck(StatusCheck statusCheck) throws Exception {
        int result = statusCheck.getStatus(this.solrClient);
        this.statusCheck = statusCheck;
        return result;
    }

    private static class ParticularCoreStatusCheck
    extends StatusCheck {
        ParticularCoreStatusCheck() {
            super("particular core");
        }

        @Override
        public int getStatus(SolrClient client) throws Exception {
            return client.ping().getStatus();
        }
    }

    private static class RootStatusCheck
    extends StatusCheck {
        RootStatusCheck() {
            super("root");
        }

        @Override
        public int getStatus(SolrClient client) throws Exception {
            CoreAdminRequest request = new CoreAdminRequest();
            request.setAction(CoreAdminParams.CoreAdminAction.STATUS);
            return ((CoreAdminResponse)request.process(client)).getStatus();
        }
    }

    private static abstract class StatusCheck {
        private final String pathType;

        StatusCheck(String pathType) {
            this.pathType = pathType;
        }

        abstract int getStatus(SolrClient var1) throws Exception;

        String getPathType() {
            return this.pathType;
        }
    }
}

