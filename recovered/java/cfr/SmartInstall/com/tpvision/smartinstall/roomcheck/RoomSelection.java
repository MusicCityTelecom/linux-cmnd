/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.roomcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoomSelection {
    private static final String ALL_ROOMS = "All";
    private static final String COMMA = ",";
    private static final String SAPCE = " ";
    private static final String DASH = "-";
    private List<Integer> mSelectedRooms = new ArrayList<Integer>();
    private boolean mAreAllRoomsSelected = false;

    public RoomSelection(String inputString) throws NumberFormatException, IllegalArgumentException {
        this.parseString(inputString);
    }

    public RoomSelection(List<Integer> selectedRooms) {
        this.mSelectedRooms = selectedRooms;
        this.mAreAllRoomsSelected = false;
    }

    public RoomSelection(boolean areAllRoomsSelected) {
        this.mSelectedRooms = new ArrayList<Integer>();
        this.mAreAllRoomsSelected = areAllRoomsSelected;
    }

    public List<Integer> getSelectedRooms() throws UnsupportedOperationException {
        if (this.mAreAllRoomsSelected) {
            throw new UnsupportedOperationException("All rooms are selected!");
        }
        return this.mSelectedRooms;
    }

    public boolean areAllRoomsSelected() {
        return this.mAreAllRoomsSelected;
    }

    private void parseString(String roomSelectionString) throws NumberFormatException, IllegalArgumentException {
        String[] roomSelectionArray;
        this.mSelectedRooms = new ArrayList<Integer>();
        this.mAreAllRoomsSelected = false;
        if (roomSelectionString.toLowerCase().equals(ALL_ROOMS.toLowerCase())) {
            this.mAreAllRoomsSelected = true;
            return;
        }
        roomSelectionString = roomSelectionString.replace(SAPCE, "");
        for (String roomSelection : roomSelectionArray = roomSelectionString.split(COMMA)) {
            if (roomSelection.contains(DASH)) {
                this.mSelectedRooms.addAll(this.generateRangeArray(roomSelection));
                continue;
            }
            this.mSelectedRooms.add(Integer.parseInt(roomSelection));
        }
    }

    private ArrayList<Integer> generateRangeArray(String roomRangeString) throws NumberFormatException, IllegalArgumentException {
        int endRoom;
        ArrayList<Integer> rangeArray = new ArrayList<Integer>();
        String[] dashSeperatedArray = roomRangeString.split(DASH);
        if (dashSeperatedArray.length != 2) {
            throw new IllegalArgumentException("Input string is an invalid room range! InputString = " + roomRangeString);
        }
        int startRoom = Integer.parseInt(dashSeperatedArray[0]);
        if (startRoom >= (endRoom = Integer.parseInt(dashSeperatedArray[1]))) {
            throw new IllegalArgumentException("Start room is higher then end room of range! Start room = " + startRoom + ", End room = " + endRoom);
        }
        for (int roomIndex = startRoom; roomIndex <= endRoom; ++roomIndex) {
            rangeArray.add(roomIndex);
        }
        return rangeArray;
    }

    public String toString() {
        if (this.mAreAllRoomsSelected) {
            return ALL_ROOMS;
        }
        return this.roomsToString();
    }

    private String roomsToString() {
        String result = "";
        if (this.mSelectedRooms.size() == 1) {
            return this.mSelectedRooms.get(0).toString();
        }
        int previousRoom = this.mSelectedRooms.get(0);
        boolean rangeDetected = false;
        for (int i = 1; i < this.mSelectedRooms.size(); ++i) {
            if (previousRoom == this.mSelectedRooms.get(i) - 1) {
                if (!rangeDetected) {
                    result = result + String.format(Locale.ENGLISH, "%05d", previousRoom) + DASH;
                    rangeDetected = true;
                }
            } else {
                result = result + String.format(Locale.ENGLISH, "%05d", previousRoom) + COMMA;
                rangeDetected = false;
            }
            previousRoom = this.mSelectedRooms.get(i);
        }
        result = result + String.format(Locale.ENGLISH, "%05d", previousRoom);
        return result;
    }

    public static List<Integer> parseRoomsNum(String roomsInfo) {
        String[] parts;
        ArrayList<Integer> roomNumbers = new ArrayList<Integer>();
        for (String part : parts = roomsInfo.split(COMMA)) {
            if (part.contains(DASH)) {
                String[] range = part.split(DASH);
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);
                for (int i = start; i <= end; ++i) {
                    roomNumbers.add(i);
                }
                continue;
            }
            roomNumbers.add(Integer.parseInt(part));
        }
        return roomNumbers;
    }

    public static String formatRoomsNum(List<Integer> roomNumbers) {
        int start;
        ArrayList<String> formattedRooms = new ArrayList<String>();
        int end = start = roomNumbers.get(0).intValue();
        for (int i = 1; i < roomNumbers.size(); ++i) {
            int current = roomNumbers.get(i);
            if (current == end + 1) {
                end = current;
                continue;
            }
            if (start == end) {
                formattedRooms.add(String.valueOf(start));
            } else {
                formattedRooms.add(start + DASH + end);
            }
            start = current;
            end = current;
        }
        if (start == end) {
            formattedRooms.add(String.valueOf(start));
        } else {
            formattedRooms.add(start + DASH + end);
        }
        return String.join((CharSequence)COMMA, formattedRooms);
    }
}

