package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.RoominfoRepository;
import com.tpvision.smartinstall.dao.core.Roominfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoominfoManager {
   @Autowired
   private RoominfoRepository roominfoRepository;

   public Roominfo loadByKey(String id) {
      return this.roominfoRepository.findById(id).orElse(null);
   }

   public void save(Roominfo roominfo) {
      this.roominfoRepository.save(roominfo);
   }

   public List<Roominfo> loadAll() {
      return this.roominfoRepository.findAll();
   }

   public void deleteAll() {
      this.roominfoRepository.deleteAll();
   }

   public void delete(Roominfo ri) {
      this.roominfoRepository.delete(ri);
   }

   public Roominfo loadByRoomid(String roomId) {
      List<Roominfo> rooms = this.roominfoRepository.findByRoomid(roomId);
      return rooms.isEmpty() ? null : rooms.get(0);
   }
}
