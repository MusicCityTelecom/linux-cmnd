/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  liquibase.changelog.ChangeSet$ExecType
 *  liquibase.changelog.RanChangeSet
 *  liquibase.changelog.StandardChangeLogHistoryService
 *  liquibase.database.Database
 *  liquibase.database.DatabaseConnection
 *  liquibase.database.DatabaseFactory
 *  liquibase.database.jvm.JdbcConnection
 *  liquibase.integration.spring.SpringLiquibase
 *  org.springframework.context.ApplicationContext
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.liquibase;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.StandardChangeLogHistoryService;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Endpoint(id="liquibase")
public class LiquibaseEndpoint {
    private final ApplicationContext context;

    public LiquibaseEndpoint(ApplicationContext context) {
        Assert.notNull((Object)context, (String)"Context must be specified");
        this.context = context;
    }

    @ReadOperation
    public ApplicationLiquibaseBeans liquibaseBeans() {
        ApplicationContext target = this.context;
        HashMap<String, ContextLiquibaseBeans> contextBeans = new HashMap<String, ContextLiquibaseBeans>();
        while (target != null) {
            HashMap liquibaseBeans = new HashMap();
            DatabaseFactory factory = DatabaseFactory.getInstance();
            target.getBeansOfType(SpringLiquibase.class).forEach((name, liquibase) -> liquibaseBeans.put(name, this.createReport((SpringLiquibase)liquibase, factory)));
            ApplicationContext parent = target.getParent();
            contextBeans.put(target.getId(), new ContextLiquibaseBeans(liquibaseBeans, parent != null ? parent.getId() : null));
            target = parent;
        }
        return new ApplicationLiquibaseBeans(contextBeans);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private LiquibaseBean createReport(SpringLiquibase liquibase, DatabaseFactory factory) {
        LiquibaseBean liquibaseBean;
        block9: {
            JdbcConnection connection;
            block8: {
                DataSource dataSource = liquibase.getDataSource();
                connection = new JdbcConnection(dataSource.getConnection());
                Database database = null;
                try {
                    database = factory.findCorrectDatabaseImplementation((DatabaseConnection)connection);
                    String defaultSchema = liquibase.getDefaultSchema();
                    if (StringUtils.hasText((String)defaultSchema)) {
                        database.setDefaultSchemaName(defaultSchema);
                    }
                    database.setDatabaseChangeLogTableName(liquibase.getDatabaseChangeLogTable());
                    database.setDatabaseChangeLogLockTableName(liquibase.getDatabaseChangeLogLockTable());
                    StandardChangeLogHistoryService service = new StandardChangeLogHistoryService();
                    service.setDatabase(database);
                    liquibaseBean = new LiquibaseBean(service.getRanChangeSets().stream().map(ChangeSet::new).collect(Collectors.toList()));
                    if (database == null) break block8;
                }
                catch (Throwable throwable) {
                    try {
                        if (database != null) {
                            database.close();
                        } else {
                            connection.close();
                        }
                        throw throwable;
                    }
                    catch (Exception ex) {
                        throw new IllegalStateException("Unable to get Liquibase change sets", ex);
                    }
                }
                database.close();
                break block9;
            }
            connection.close();
        }
        return liquibaseBean;
    }

    public static class ContextExpression {
        private final Set<String> contexts;

        public ContextExpression(Set<String> contexts) {
            this.contexts = contexts;
        }

        public Set<String> getContexts() {
            return this.contexts;
        }
    }

    public static class ChangeSet {
        private final String author;
        private final String changeLog;
        private final String comments;
        private final Set<String> contexts;
        private final Instant dateExecuted;
        private final String deploymentId;
        private final String description;
        private final ChangeSet.ExecType execType;
        private final String id;
        private final Set<String> labels;
        private final String checksum;
        private final Integer orderExecuted;
        private final String tag;

        public ChangeSet(RanChangeSet ranChangeSet) {
            this.author = ranChangeSet.getAuthor();
            this.changeLog = ranChangeSet.getChangeLog();
            this.comments = ranChangeSet.getComments();
            this.contexts = ranChangeSet.getContextExpression().getContexts();
            this.dateExecuted = Instant.ofEpochMilli(ranChangeSet.getDateExecuted().getTime());
            this.deploymentId = ranChangeSet.getDeploymentId();
            this.description = ranChangeSet.getDescription();
            this.execType = ranChangeSet.getExecType();
            this.id = ranChangeSet.getId();
            this.labels = ranChangeSet.getLabels().getLabels();
            this.checksum = ranChangeSet.getLastCheckSum() != null ? ranChangeSet.getLastCheckSum().toString() : null;
            this.orderExecuted = ranChangeSet.getOrderExecuted();
            this.tag = ranChangeSet.getTag();
        }

        public String getAuthor() {
            return this.author;
        }

        public String getChangeLog() {
            return this.changeLog;
        }

        public String getComments() {
            return this.comments;
        }

        public Set<String> getContexts() {
            return this.contexts;
        }

        public Instant getDateExecuted() {
            return this.dateExecuted;
        }

        public String getDeploymentId() {
            return this.deploymentId;
        }

        public String getDescription() {
            return this.description;
        }

        public ChangeSet.ExecType getExecType() {
            return this.execType;
        }

        public String getId() {
            return this.id;
        }

        public Set<String> getLabels() {
            return this.labels;
        }

        public String getChecksum() {
            return this.checksum;
        }

        public Integer getOrderExecuted() {
            return this.orderExecuted;
        }

        public String getTag() {
            return this.tag;
        }
    }

    public static final class LiquibaseBean {
        private final List<ChangeSet> changeSets;

        public LiquibaseBean(List<ChangeSet> changeSets) {
            this.changeSets = changeSets;
        }

        public List<ChangeSet> getChangeSets() {
            return this.changeSets;
        }
    }

    public static final class ContextLiquibaseBeans {
        private final Map<String, LiquibaseBean> liquibaseBeans;
        private final String parentId;

        private ContextLiquibaseBeans(Map<String, LiquibaseBean> liquibaseBeans, String parentId) {
            this.liquibaseBeans = liquibaseBeans;
            this.parentId = parentId;
        }

        public Map<String, LiquibaseBean> getLiquibaseBeans() {
            return this.liquibaseBeans;
        }

        public String getParentId() {
            return this.parentId;
        }
    }

    public static final class ApplicationLiquibaseBeans {
        private final Map<String, ContextLiquibaseBeans> contexts;

        private ApplicationLiquibaseBeans(Map<String, ContextLiquibaseBeans> contexts) {
            this.contexts = contexts;
        }

        public Map<String, ContextLiquibaseBeans> getContexts() {
            return this.contexts;
        }
    }
}

