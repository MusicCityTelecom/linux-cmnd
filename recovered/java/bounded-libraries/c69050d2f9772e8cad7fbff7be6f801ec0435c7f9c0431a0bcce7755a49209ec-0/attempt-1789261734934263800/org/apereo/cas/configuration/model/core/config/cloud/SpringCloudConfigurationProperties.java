/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.config.cloud;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.RestEndpointProperties;
import org.apereo.cas.configuration.model.support.aws.BaseAmazonWebServicesProperties;
import org.apereo.cas.configuration.model.support.dynamodb.AbstractDynamoDbProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

public class SpringCloudConfigurationProperties
implements Serializable {
    private static final long serialVersionUID = -2749293768878152908L;
    private Cloud cloud = new Cloud();

    @Generated
    public Cloud getCloud() {
        return this.cloud;
    }

    @Generated
    public SpringCloudConfigurationProperties setCloud(Cloud cloud) {
        this.cloud = cloud;
        return this;
    }

    @RequiresModule(name="cas-server-support-configuration-cloud-dynamodb")
    public static class AmazonDynamoDb
    extends AbstractDynamoDbProperties {
        private static final long serialVersionUID = -123404249388429120L;
    }

    @RequiresModule(name="cas-server-support-configuration-cloud-aws-s3")
    public static class AmazonS3
    extends BaseAmazonWebServicesProperties {
        private static final long serialVersionUID = -124404249387429120L;
        private String bucketName;

        @Generated
        public String getBucketName() {
            return this.bucketName;
        }

        @Generated
        public AmazonS3 setBucketName(String bucketName) {
            this.bucketName = bucketName;
            return this;
        }
    }

    @RequiresModule(name="cas-server-support-configuration-cloud-aws-ssm")
    public static class AmazonSystemsManagerParameterStore
    extends BaseAmazonWebServicesProperties {
        private static final long serialVersionUID = -224404249387429120L;
    }

    @RequiresModule(name="cas-server-support-configuration-cloud-aws-secretsmanager")
    public static class AmazonSecretsManager
    extends BaseAmazonWebServicesProperties {
        private static final long serialVersionUID = -124404249387429120L;
    }

    @RequiresModule(name="cas-server-support-aws")
    public static class AmazonWebServicesConfiguration
    implements Serializable {
        private static final long serialVersionUID = -124404249388429120L;
        private AmazonSecretsManager secretsManager = new AmazonSecretsManager();
        private AmazonDynamoDb dynamoDb = new AmazonDynamoDb();
        private AmazonS3 s3 = new AmazonS3();
        private AmazonSystemsManagerParameterStore ssm = new AmazonSystemsManagerParameterStore();

        @Generated
        public AmazonSecretsManager getSecretsManager() {
            return this.secretsManager;
        }

        @Generated
        public AmazonDynamoDb getDynamoDb() {
            return this.dynamoDb;
        }

        @Generated
        public AmazonS3 getS3() {
            return this.s3;
        }

        @Generated
        public AmazonSystemsManagerParameterStore getSsm() {
            return this.ssm;
        }

        @Generated
        public AmazonWebServicesConfiguration setSecretsManager(AmazonSecretsManager secretsManager) {
            this.secretsManager = secretsManager;
            return this;
        }

        @Generated
        public AmazonWebServicesConfiguration setDynamoDb(AmazonDynamoDb dynamoDb) {
            this.dynamoDb = dynamoDb;
            return this;
        }

        @Generated
        public AmazonWebServicesConfiguration setS3(AmazonS3 s3) {
            this.s3 = s3;
            return this;
        }

        @Generated
        public AmazonWebServicesConfiguration setSsm(AmazonSystemsManagerParameterStore ssm) {
            this.ssm = ssm;
            return this;
        }
    }

    @RequiresModule(name="cas-server-support-configuration-cloud-jdbc")
    public static class Jdbc
    implements Serializable {
        private static final long serialVersionUID = -7575240387340025345L;
        private String sql;
        private String url;
        private String user;
        private String password;
        private String driverClass;

        @Generated
        public String getSql() {
            return this.sql;
        }

        @Generated
        public String getUrl() {
            return this.url;
        }

        @Generated
        public String getUser() {
            return this.user;
        }

        @Generated
        public String getPassword() {
            return this.password;
        }

        @Generated
        public String getDriverClass() {
            return this.driverClass;
        }

        @Generated
        public Jdbc setSql(String sql) {
            this.sql = sql;
            return this;
        }

        @Generated
        public Jdbc setUrl(String url) {
            this.url = url;
            return this;
        }

        @Generated
        public Jdbc setUser(String user) {
            this.user = user;
            return this;
        }

        @Generated
        public Jdbc setPassword(String password) {
            this.password = password;
            return this;
        }

        @Generated
        public Jdbc setDriverClass(String driverClass) {
            this.driverClass = driverClass;
            return this;
        }
    }

    @RequiresModule(name="cas-server-support-configuration-cloud-rest")
    public static class Rest
    extends RestEndpointProperties {
        private static final long serialVersionUID = -4509143371334754469L;
    }

    @RequiresModule(name="cas-server-support-configuration-cloud-mongo")
    public static class MongoDb
    implements Serializable {
        private static final long serialVersionUID = -6509143371334754469L;
        @RequiredProperty
        private String uri;

        @Generated
        public String getUri() {
            return this.uri;
        }

        @Generated
        public MongoDb setUri(String uri) {
            this.uri = uri;
            return this;
        }
    }

    public static class Cloud
    implements Serializable {
        private static final long serialVersionUID = -6326706651416825269L;
        private MongoDb mongo = new MongoDb();
        private Jdbc jdbc = new Jdbc();
        private Rest rest = new Rest();
        private AmazonWebServicesConfiguration aws = new AmazonWebServicesConfiguration();
        private AmazonDynamoDb dynamoDb = new AmazonDynamoDb();

        @Generated
        public MongoDb getMongo() {
            return this.mongo;
        }

        @Generated
        public Jdbc getJdbc() {
            return this.jdbc;
        }

        @Generated
        public Rest getRest() {
            return this.rest;
        }

        @Generated
        public AmazonWebServicesConfiguration getAws() {
            return this.aws;
        }

        @Generated
        public AmazonDynamoDb getDynamoDb() {
            return this.dynamoDb;
        }

        @Generated
        public Cloud setMongo(MongoDb mongo) {
            this.mongo = mongo;
            return this;
        }

        @Generated
        public Cloud setJdbc(Jdbc jdbc) {
            this.jdbc = jdbc;
            return this;
        }

        @Generated
        public Cloud setRest(Rest rest) {
            this.rest = rest;
            return this;
        }

        @Generated
        public Cloud setAws(AmazonWebServicesConfiguration aws) {
            this.aws = aws;
            return this;
        }

        @Generated
        public Cloud setDynamoDb(AmazonDynamoDb dynamoDb) {
            this.dynamoDb = dynamoDb;
            return this;
        }
    }
}

