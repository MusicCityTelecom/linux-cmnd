package com.tpvision.smartinstall.roomcheck;

import java.util.ArrayList;
import java.util.List;

public class RoomSelectionComparator {
   public static RoomSelection getRelevantExistingRoomSelection(RoomSelection newCloneItemRoomSelection, RoomSelection existingCloneItemRoomSelection) {
      if (newCloneItemRoomSelection.areAllRoomsSelected()) {
         return null;
      }

      if (existingCloneItemRoomSelection.areAllRoomsSelected()) {
         return new RoomSelection(true);
      }

      List<Integer> existingCloneItemSelectedRooms = new ArrayList<>(existingCloneItemRoomSelection.getSelectedRooms());

      for (Integer newCloneItemSelectedRoom : newCloneItemRoomSelection.getSelectedRooms()) {
         existingCloneItemSelectedRooms.remove(newCloneItemSelectedRoom);
         if (existingCloneItemSelectedRooms.isEmpty()) {
            return null;
         }
      }

      return new RoomSelection(existingCloneItemSelectedRooms);
   }

   public static RoomSelectionComparator.RoomSelectionOverlap getRoomSelectionOverlapRate(
      RoomSelection newCloneItemRoomSelection, RoomSelection existingCloneItemRoomSelection
   ) {
      RoomSelection updatedExistingCloneItemRoomSelection = getRelevantExistingRoomSelection(newCloneItemRoomSelection, existingCloneItemRoomSelection);
      if (updatedExistingCloneItemRoomSelection == null) {
         return RoomSelectionComparator.RoomSelectionOverlap.FULL;
      } else if (updatedExistingCloneItemRoomSelection.areAllRoomsSelected()) {
         return !newCloneItemRoomSelection.getSelectedRooms().isEmpty()
            ? RoomSelectionComparator.RoomSelectionOverlap.PARTIAL
            : RoomSelectionComparator.RoomSelectionOverlap.NONE;
      } else {
         return updatedExistingCloneItemRoomSelection.getSelectedRooms().size() != existingCloneItemRoomSelection.getSelectedRooms().size()
            ? RoomSelectionComparator.RoomSelectionOverlap.PARTIAL
            : RoomSelectionComparator.RoomSelectionOverlap.NONE;
      }
   }

   public enum RoomSelectionOverlap {
      NONE,
      PARTIAL,
      FULL;
   }
}
