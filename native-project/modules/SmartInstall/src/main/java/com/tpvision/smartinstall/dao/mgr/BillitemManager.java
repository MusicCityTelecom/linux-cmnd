package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.BillItemRepository;
import com.tpvision.smartinstall.dao.core.Billitem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillitemManager {
   @Autowired
   private BillItemRepository billItemRepository;

   public Billitem loadByKey(String id) {
      return this.billItemRepository.findById(id).orElse(null);
   }

   public void deleteByKey(String id) {
      this.billItemRepository.deleteById(id);
   }

   public void save(Billitem billItem) {
      this.billItemRepository.save(billItem);
   }

   public List<Billitem> findBillitemsByRoomId(String roomId) {
      return this.billItemRepository.findByRoomId(roomId);
   }

   public List<Billitem> findBillitemsRoomIdIsNull() {
      return this.billItemRepository.findByRoomIdIsNull();
   }

   public String getBillAmount(String roomId) {
      return this.billItemRepository.getBillBalance(roomId);
   }
}
