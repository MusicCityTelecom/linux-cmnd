package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.ReservationRepository;
import com.tpvision.smartinstall.dao.core.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationManager {
   @Autowired
   private ReservationRepository reservationRepository;

   public List<Reservation> loadAll() {
      return this.reservationRepository.findAll();
   }

   public void deleteByKey(String id) {
      if (this.loadByKey(id) != null) {
         this.reservationRepository.deleteById(id);
      }
   }

   public Reservation loadByKey(String id) {
      return this.reservationRepository.findById(id).orElse(null);
   }

   public List<Reservation> findReservationByRooms(String rooms) {
      return this.reservationRepository.findByRooms(rooms);
   }

   public void save(Reservation reservation) {
      this.reservationRepository.save(reservation);
   }

   public void deleteAll() {
      this.reservationRepository.deleteAll();
   }

   public Map<String, String> findRoomStatusMap() throws SQLException {
      String sql = "SELECT rooms,group_concat(distinct STATUS,'') as status FROM reservation where rooms is not NULL GROUP BY rooms";
      Map<String, String> result = new HashMap<>();

      try (
         Connection conn = JpaManager.getConnection();
         PreparedStatement stat = conn.prepareStatement(sql);
         ResultSet rs = stat.executeQuery();
      ) {
         while (rs.next()) {
            result.put(rs.getString(1), rs.getString(2));
         }
      }

      return result;
   }

   public List<String> findLongtermBookings() {
      List<Reservation> ress = this.reservationRepository.findLongTermBookings();
      return ress.stream().map(t -> t.getReservationId()).distinct().collect(Collectors.toList());
   }
}
