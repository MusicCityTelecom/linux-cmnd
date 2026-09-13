/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Content;
import java.util.Set;

public interface ContentRepositoryJdbc {
    public Set<Content> getContent();

    public Content getContent(String var1);

    public void addContent(Content var1);

    public void updateContent(Content var1);

    public void deleteContent(Content var1);
}

