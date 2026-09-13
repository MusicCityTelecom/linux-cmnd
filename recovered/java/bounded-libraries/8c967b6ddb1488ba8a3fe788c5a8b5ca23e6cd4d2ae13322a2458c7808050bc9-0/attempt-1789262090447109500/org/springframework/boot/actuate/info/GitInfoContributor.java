/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.info.GitProperties
 *  org.springframework.core.env.PropertiesPropertySource
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.actuate.info;

import java.util.Map;
import java.util.Properties;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoPropertiesInfoContributor;
import org.springframework.boot.info.GitProperties;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

public class GitInfoContributor
extends InfoPropertiesInfoContributor<GitProperties> {
    public GitInfoContributor(GitProperties properties) {
        this(properties, InfoPropertiesInfoContributor.Mode.SIMPLE);
    }

    public GitInfoContributor(GitProperties properties, InfoPropertiesInfoContributor.Mode mode) {
        super(properties, mode);
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("git", this.generateContent());
    }

    @Override
    protected PropertySource<?> toSimplePropertySource() {
        Properties props = new Properties();
        this.copyIfSet(props, "branch");
        String commitId = ((GitProperties)this.getProperties()).getShortCommitId();
        if (commitId != null) {
            props.put("commit.id", commitId);
        }
        this.copyIfSet(props, "commit.time");
        return new PropertiesPropertySource("git", props);
    }

    @Override
    protected void postProcessContent(Map<String, Object> content) {
        this.replaceValue(this.getNestedMap(content, "commit"), "time", ((GitProperties)this.getProperties()).getCommitTime());
        this.replaceValue(this.getNestedMap(content, "build"), "time", ((GitProperties)this.getProperties()).getInstant("build.time"));
    }
}

