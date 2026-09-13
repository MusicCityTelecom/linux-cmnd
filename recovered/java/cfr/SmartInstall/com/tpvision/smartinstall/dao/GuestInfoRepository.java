/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.GuestInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface GuestInfoRepository
extends JpaRepository<GuestInfo, String>,
JpaSpecificationExecutor<GuestInfo> {
    @Query(nativeQuery=true, value="select * from guestinfo where cast(roomid as unsigned) = cast(? as unsigned) and roomid is not null")
    public List<GuestInfo> findByRoomid(String var1);

    public List<GuestInfo> findByCheckin(String var1);

    @Query(value="select t from GuestInfo t where (t.checkoutTime is not null and t.checkoutTime != '' and t.checkoutTime <= ?1) and t.checkin=?2")
    public List<GuestInfo> findGuestInfoByCheckoutTimeAndCheckInStatus(String var1, String var2);

    @Query(value="select * from guestinfo t where (t.roomid like CONCAT('%',?1,'%') or t.guestName like CONCAT('%',?1,'%') or t.checkout_time like CONCAT('%',?1,'%')) and t.checkin='Y' order by cast(roomid as unsigned) asc limit ?2,?3", nativeQuery=true)
    public List<GuestInfo> findByKeywordAndCheckInStatusOrderByRoomidAsc(String var1, int var2, int var3);

    @Query(value="select * from guestinfo t where (t.roomid like CONCAT('%',?1,'%') or t.guestName like CONCAT('%',?1,'%') or t.checkout_time like CONCAT('%',?1,'%')) and t.checkin='Y' order by cast(roomid as unsigned) desc limit ?2,?3", nativeQuery=true)
    public List<GuestInfo> findByKeywordAndCheckInStatusOrderByRoomidDesc(String var1, int var2, int var3);

    public List<GuestInfo> findByOrderid(String var1);

    public List<GuestInfo> findByGuestIdLike(String var1);
}

