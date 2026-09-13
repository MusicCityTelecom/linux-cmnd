/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.RoomNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoomNotificationRepository
extends JpaRepository<RoomNotification, Integer>,
JpaSpecificationExecutor<RoomNotification> {
    @Modifying
    @Query(value="update RoomNotification set readStatus=1,readTime=?1 where readStatus=0")
    public void markAllAsRead(String var1);
}

