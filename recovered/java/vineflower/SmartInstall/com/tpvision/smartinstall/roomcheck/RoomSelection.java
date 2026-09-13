package com.tpvision.smartinstall.roomcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoomSelection {
   private static final String ALL_ROOMS = "All";
   private static final String COMMA = ",";
   private static final String SAPCE = " ";
   private static final String DASH = "-";
   private List<Integer> mSelectedRooms = new ArrayList<>();
   private boolean mAreAllRoomsSelected = false;

   public RoomSelection(String inputString) throws NumberFormatException, IllegalArgumentException {
      this.parseString(inputString);
   }

   public RoomSelection(List<Integer> selectedRooms) {
      this.mSelectedRooms = selectedRooms;
      this.mAreAllRoomsSelected = false;
   }

   public RoomSelection(boolean areAllRoomsSelected) {
      this.mSelectedRooms = new ArrayList<>();
      this.mAreAllRoomsSelected = areAllRoomsSelected;
   }

   public List<Integer> getSelectedRooms() throws UnsupportedOperationException {
      if (this.mAreAllRoomsSelected) {
         throw new UnsupportedOperationException("All rooms are selected!");
      } else {
         return this.mSelectedRooms;
      }
   }

   public boolean areAllRoomsSelected() {
      return this.mAreAllRoomsSelected;
   }

   private void parseString(String roomSelectionString) throws NumberFormatException, IllegalArgumentException {
      this.mSelectedRooms = new ArrayList<>();
      this.mAreAllRoomsSelected = false;
      if (roomSelectionString.toLowerCase().equals("All".toLowerCase())) {
         this.mAreAllRoomsSelected = true;
      } else {
         roomSelectionString = roomSelectionString.replace(" ", "");
         String[] roomSelectionArray = roomSelectionString.split(",");

         for (String roomSelection : roomSelectionArray) {
            if (roomSelection.contains("-")) {
               this.mSelectedRooms.addAll(this.generateRangeArray(roomSelection));
            } else {
               this.mSelectedRooms.add(Integer.parseInt(roomSelection));
            }
         }
      }
   }

   private ArrayList<Integer> generateRangeArray(String roomRangeString) throws NumberFormatException, IllegalArgumentException {
      ArrayList<Integer> rangeArray = new ArrayList<>();
      String[] dashSeperatedArray = roomRangeString.split("-");
      if (dashSeperatedArray.length != 2) {
         throw new IllegalArgumentException("Input string is an invalid room range! InputString = " + roomRangeString);
      }

      int startRoom = Integer.parseInt(dashSeperatedArray[0]);
      int endRoom = Integer.parseInt(dashSeperatedArray[1]);
      if (startRoom >= endRoom) {
         throw new IllegalArgumentException("Start room is higher then end room of range! Start room = " + startRoom + ", End room = " + endRoom);
      }

      for (int roomIndex = startRoom; roomIndex <= endRoom; roomIndex++) {
         rangeArray.add(roomIndex);
      }

      return rangeArray;
   }

   @Override
   public String toString() {
      return this.mAreAllRoomsSelected ? "All" : this.roomsToString();
   }

   private String roomsToString() {
      String result = "";
      if (this.mSelectedRooms.size() == 1) {
         return this.mSelectedRooms.get(0).toString();
      }

      int previousRoom = this.mSelectedRooms.get(0);
      boolean rangeDetected = false;

      for (int i = 1; i < this.mSelectedRooms.size(); i++) {
         if (previousRoom == this.mSelectedRooms.get(i) - 1) {
            if (!rangeDetected) {
               result = result + String.format(Locale.ENGLISH, "%05d", previousRoom) + "-";
               rangeDetected = true;
            }
         } else {
            result = result + String.format(Locale.ENGLISH, "%05d", previousRoom) + ",";
            rangeDetected = false;
         }

         previousRoom = this.mSelectedRooms.get(i);
      }

      return result + String.format(Locale.ENGLISH, "%05d", previousRoom);
   }

   public static List<Integer> parseRoomsNum(String roomsInfo) {
      List<Integer> roomNumbers = new ArrayList<>();
      String[] parts = roomsInfo.split(",");

      for (String part : parts) {
         if (part.contains("-")) {
            String[] range = part.split("-");
            int start = Integer.parseInt(range[0]);
            int end = Integer.parseInt(range[1]);

            for (int i = start; i <= end; i++) {
               roomNumbers.add(i);
            }
         } else {
            roomNumbers.add(Integer.parseInt(part));
         }
      }

      return roomNumbers;
   }

   public static String formatRoomsNum(List<Integer> roomNumbers) {
      List<String> formattedRooms = new ArrayList<>();
      int start = roomNumbers.get(0);
      int end = start;

      for (int i = 1; i < roomNumbers.size(); i++) {
         int current = roomNumbers.get(i);
         if (current == end + 1) {
            end = current;
         } else {
            if (start == end) {
               formattedRooms.add(String.valueOf(start));
            } else {
               formattedRooms.add(start + "-" + end);
            }

            start = current;
            end = current;
         }
      }

      if (start == end) {
         formattedRooms.add(String.valueOf(start));
      } else {
         formattedRooms.add(start + "-" + end);
      }

      return String.join(",", formattedRooms);
   }
}
