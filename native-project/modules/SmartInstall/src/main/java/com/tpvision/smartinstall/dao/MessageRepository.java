package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MessageRepository extends JpaRepository<Message, String> {
   List<Message> findByGuestIdsAndIsSent(String var1, String var2);

   List<Message> findByGuestIds(String var1);

   @Query(nativeQuery = true, value = "select * from message where guestIds = ?1 and unix_timestamp(timeSend)<unix_timestamp(now())+10")
   List<Message> findAccessTimeSendByGuestIds(String var1);

   void deleteByGuestIds(String var1);

   Message findByMsgId(int var1);
}
