package com.w3gdata.actionblocks;

import java.util.HashMap;
import java.util.Map;

public enum ActionBlockFormat {
    PAUSE_GAME(PauseGame.ID, PauseGame.class),
    RESUME_GAME(ResumeGame.ID, ResumeGame.class),
    GAME_SPEED(GameSpeed.ID, GameSpeed.class),
    GAME_SPEED_INCREASING(GameSpeedIncreasing.ID, GameSpeed.class),
    GAME_SPEED_DECREASING(GameSpeedDecreasing.ID, GameSpeedDecreasing.class),
    SAVE_GAME(SaveGame.ID, SaveGame.class),
    SAVE_GAME_FINISHED(SaveGameFinished.ID, SaveGameFinished.class),
    UNIT_BUILDING_ABILITY(UnitBuildingAbility.ID, UnitBuildingAbility.class),
    UNIT_BUILDING_ABILITY_TARGETED(UnitBuildingAbilityTargeted.ID, UnitBuildingAbilityTargeted.class),
    UNIT_BUILDING_ABILITY_TARGETED_ID(UnitBuildingAbilityTargetedId.ID, UnitBuildingAbilityTargetedId.class),
    ITEM_GIVEN_DROPPED(ItemGivenDropped.ID, ItemGivenDropped.class),
    UNIT_BUILDING_ABILITY_2_TARGETS_2_ITEMS(UnitBuildingAbility2Targets2Items.ID,
            UnitBuildingAbility2Targets2Items.class),

    ;

    final static Map<Integer, ActionBlockFormat> formats;

    int id;

    Class type;

    static {
        formats = new HashMap<>();
        for (ActionBlockFormat format : ActionBlockFormat.values()) {
            formats.put(format.id, format);
        }
    }

    ActionBlockFormat(int id, Class<? extends ActionBlock> type) {
        this.id = id;
        this.type = type;
    }

    public static Class getTypeById(int id) {
        return getById(id).type;
    }

    private static ActionBlockFormat getById(int id) {
        return formats.get(id);
    }
}
