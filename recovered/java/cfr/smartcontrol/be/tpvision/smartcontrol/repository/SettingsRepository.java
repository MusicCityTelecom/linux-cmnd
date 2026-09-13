/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Settings;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Repository;

@Repository
public class SettingsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Settings getSettings() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Settings> criteriaQuery = criteriaBuilder.createQuery(Settings.class);
        criteriaQuery.from(Settings.class);
        TypedQuery<Settings> query = this.entityManager.createQuery(criteriaQuery);
        return (Settings)query.getSingleResult();
    }

    public void persist(Settings settings) {
        this.entityManager.persist(settings);
    }

    public Settings merge(Settings settings) {
        Settings currentSettings = this.getSettings();
        Long currentSettingsId = currentSettings.getId();
        settings.setId(currentSettingsId);
        return this.entityManager.merge(settings);
    }

    public void delete(Settings settings) {
        boolean isManagedEntity = this.entityManager.contains(settings);
        Settings settingsToDelete = isManagedEntity ? settings : this.entityManager.merge(settings);
        this.entityManager.remove(settingsToDelete);
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
}

