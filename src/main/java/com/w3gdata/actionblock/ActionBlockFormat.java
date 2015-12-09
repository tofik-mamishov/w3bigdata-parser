package com.w3gdata.actionblock;

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
    CHANGE_SELECTION(ChangeSelection.ID, ChangeSelection.class),
    ASSIGN_GROUP_HOTKEY(AssignGroupHotkey.ID, AssignGroupHotkey.class),
    SELECT_GROUP_HOTKEY(SelectGroupHotkey.ID, SelectGroupHotkey.class),
    SELECT_SUBGROUP(SelectSubgroup.ID, SelectSubgroup.class),
    SELECT_SUBGROUP_1_14b(SelectSubgroup114b.ID, SelectSubgroup114b.class),
    PRE_SUBSELECTION(PreSubselection.ID, PreSubselection.class),
    UNKNOWN_0_X_1B(Unknown0x1B.ID, Unknown0x1B.class),
    SELECT_GROUND_ITEM(SelectGroundItem.ID, SelectGroundItem.class),
    CANCEL_HERO_REVIVAL(CancelHeroRevival.ID, CancelHeroRevival.class),
    REMOVE_UNIT_FROM_BUILDING_QUEUE(RemoveUnitFromBuildingQueue.ID, RemoveUnitFromBuildingQueue.class),
    //SinglePlayerCheats has another way of processing
    CHANGE_ALLY_OPTIONS(ChangeAllyOptions.ID, ChangeAllyOptions.class),
    TRANSFER_RESOURCES(TransferResources.ID, TransferResources.class),
    MAP_TRIGGER_CHAT_COMMAND(MapTriggerChatCommand.ID, MapTriggerChatCommand.class),
    ESC_PRESSED(EscPressed.ID, EscPressed.class),
    SCENARIO_TRIGGER(ScenarioTrigger.ID, ScenarioTrigger.class),
    ENTER_CHOOSE_HERO_SKILL_SUBMENU(EnterChooseHeroSkillSubmenu.ID, EnterChooseHeroSkillSubmenu.class),
    ENTER_CHOOSE_BUILDING_SUBMENU(EnterChooseBuildingSubmenu.ID, EnterChooseBuildingSubmenu.class),
    MINIMAP_SIGNAL(MinimapSignal.ID, MinimapSignal.class),
    CONTINUE_GAME_B(ContinueGameB.ID, ContinueGameB.class),
    CONTINUE_GAME_A(ContinueGameA.ID, ContinueGameA.class),
    UNKNOWN_0_X_75(Unknown0x75.ID, Unknown0x75.class)
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

    public static ActionBlockFormat getById(int id) {
        return formats.get(id);
    }
}
