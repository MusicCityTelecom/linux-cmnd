/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.roomcheck;

import com.tpvision.smartinstall.roomcheck.RoomSelection;
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
        ArrayList<Integer> existingCloneItemSelectedRooms = new ArrayList<Integer>(existingCloneItemRoomSelection.getSelectedRooms());
        List<Integer> newCloneItemSelectedRooms = newCloneItemRoomSelection.getSelectedRooms();
        for (Integer newCloneItemSelectedRoom : newCloneItemSelectedRooms) {
            existingCloneItemSelectedRooms.remove(newCloneItemSelectedRoom);
            if (!existingCloneItemSelectedRooms.isEmpty()) continue;
            return null;
        }
        return new RoomSelection(existingCloneItemSelectedRooms);
    }

    public static RoomSelectionOverlap getRoomSelectionOverlapRate(RoomSelection newCloneItemRoomSelection, RoomSelection existingCloneItemRoomSelection) {
        RoomSelection updatedExistingCloneItemRoomSelection = RoomSelectionComparator.getRelevantExistingRoomSelection(newCloneItemRoomSelection, existingCloneItemRoomSelection);
        if (updatedExistingCloneItemRoomSelection == null) {
            return RoomSelectionOverlap.FULL;
        }
        if (updatedExistingCloneItemRoomSelection.areAllRoomsSelected()) {
            if (!newCloneItemRoomSelection.getSelectedRooms().isEmpty()) {
                return RoomSelectionOverlap.PARTIAL;
            }
            return RoomSelectionOverlap.NONE;
        }
        if (updatedExistingCloneItemRoomSelection.getSelectedRooms().size() != existingCloneItemRoomSelection.getSelectedRooms().size()) {
            return RoomSelectionOverlap.PARTIAL;
        }
        return RoomSelectionOverlap.NONE;
    }

    public static enum RoomSelectionOverlap {
        NONE,
        PARTIAL,
        FULL;

    }
}

