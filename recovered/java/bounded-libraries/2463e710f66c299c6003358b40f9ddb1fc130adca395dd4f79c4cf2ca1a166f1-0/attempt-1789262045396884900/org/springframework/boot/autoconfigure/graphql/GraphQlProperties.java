/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 */
package org.springframework.boot.autoconfigure.graphql;

import java.time.Duration;
import java.util.Arrays;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.graphql")
public class GraphQlProperties {
    private String path = "/graphql";
    private final Graphiql graphiql = new Graphiql();
    private final Schema schema = new Schema();
    private final Websocket websocket = new Websocket();
    private final Rsocket rsocket = new Rsocket();

    public Graphiql getGraphiql() {
        return this.graphiql;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Schema getSchema() {
        return this.schema;
    }

    public Websocket getWebsocket() {
        return this.websocket;
    }

    public Rsocket getRsocket() {
        return this.rsocket;
    }

    public static class Rsocket {
        private String mapping;

        public String getMapping() {
            return this.mapping;
        }

        public void setMapping(String mapping) {
            this.mapping = mapping;
        }
    }

    public static class Websocket {
        private String path;
        private Duration connectionInitTimeout = Duration.ofSeconds(60L);

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Duration getConnectionInitTimeout() {
            return this.connectionInitTimeout;
        }

        public void setConnectionInitTimeout(Duration connectionInitTimeout) {
            this.connectionInitTimeout = connectionInitTimeout;
        }
    }

    public static class Graphiql {
        private String path = "/graphiql";
        private boolean enabled = false;

        public String getPath() {
            return this.path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class Schema {
        private String[] locations = new String[]{"classpath:graphql/**/"};
        private String[] fileExtensions = new String[]{".graphqls", ".gqls"};
        private final Introspection introspection = new Introspection();
        private final Printer printer = new Printer();

        public String[] getLocations() {
            return this.locations;
        }

        public void setLocations(String[] locations) {
            this.locations = this.appendSlashIfNecessary(locations);
        }

        public String[] getFileExtensions() {
            return this.fileExtensions;
        }

        public void setFileExtensions(String[] fileExtensions) {
            this.fileExtensions = fileExtensions;
        }

        private String[] appendSlashIfNecessary(String[] locations) {
            return (String[])Arrays.stream(locations).map(location -> location.endsWith("/") ? location : location + "/").toArray(String[]::new);
        }

        public Introspection getIntrospection() {
            return this.introspection;
        }

        public Printer getPrinter() {
            return this.printer;
        }

        public static class Printer {
            private boolean enabled = false;

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }

        public static class Introspection {
            private boolean enabled = true;

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }
    }
}

