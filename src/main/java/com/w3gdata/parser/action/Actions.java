package com.w3gdata.parser.action;

import org.apache.log4j.Logger;

public enum Actions {
    PAUSE_GAME(new PauseGame()),
    RESUME_GAME(new ResumeGame()),
    GAME_SPEED(new GameSpeed()),
    GAME_SPEED_INCREASING(new GameSpeedIncreasing()),
    GAME_SPEED_DECREASING(new GameSpeedDecreasing()),
    SAVE_GAME(new SaveGame()),
    SAVE_GAME_FINISHED(new SaveGameFinished()),
    UNIT_BUILDING_ABILITY(new UnitBuildingAbility()),
    UNIT_BUILDING_ABILITY_TARGETED(new UnitBuildingAbilityTargeted()),
    UNIT_BUILDING_ABILITY_TARGETED_ID(new UnitBuildingAbilityTargetedId()),
    ITEM_GIVEN_DROPPED(new ItemGivenDropped()),
    UNIT_BUILDING_ABILITY_2_TARGETS_2_ITEMS(new UnitBuildingAbility2Targets2Items()),
    CHANGE_SELECTION(new ChangeSelection()) ,
    ASSIGN_GROUP_HOTKEY(new AssignGroupHotkey()),
    SELECT_GROUP_HOTKEY(new SelectGroupHotkey()),
    SELECT_SUBGROUP(new SelectSubgroup()),
    SELECT_SUBGROUP_1_14b(new SelectSubgroup114b()),
    PRE_SUBSELECTION(new PreSubselection()),
    SELECT_GROUND_ITEM(new SelectGroundItem()),
    CANCEL_HERO_REVIVAL(new CancelHeroRevival()),
    REMOVE_UNIT_FROM_BUILDING_QUEUE(new RemoveUnitFromBuildingQueue()),
    CHANGE_ALLY_OPTIONS(new ChangeAllyOptions()),
    TRANSFER_RESOURCES(new TransferResources()),
    MAP_TRIGGER_CHAT_COMMAND(new MapTriggerChatCommand()),
    ESC_PRESSED(new EscPressed()),
    SCENARIO_TRIGGER(new ScenarioTrigger()),
    ENTER_CHOOSE_HERO_SKILL_SUBMENU(new EnterChooseHeroSkillSubmenu()),
    ENTER_CHOOSE_BUILDING_SUBMENU(new EnterChooseBuildingSubmenu()),
    MINIMAP_SIGNAL(new MinimapSignal()),
    CONTINUE_GAME_B(new ContinueGameB()),
    CONTINUE_GAME_A(new ContinueGameA()),
    MMD_MESSAGE(new MMDMessage()),
    UNKNOWN_0_X_1B(UnknownActionFactory.INSTANCE.get(0x1b)),
    UNKNOWN_0_X_21(UnknownActionFactory.INSTANCE.get(0x21)),
    UNKNOWN_0_X_94(UnknownActionFactory.INSTANCE.get(0x94)),
    UNKNOWN_0_X_6C(UnknownActionFactory.INSTANCE.get(0x6c)),
    UNKNOWN_0_X_74(UnknownActionFactory.INSTANCE.get(0x74)),
    UNKNOWN_0_X_75(UnknownActionFactory.INSTANCE.get(0x75));

    private static final Logger logger = Logger.getLogger(Actions.class);

    public final int id;
    public final Action shape;

    Actions(Action shape) {
        this.id = shape.getId();
        this.shape = shape;
    }

    public static Actions getById(int id) {
        for (Actions action: values()) {
            if (action.id == id) {
                return action;
            }
        }
        logger.error("Unknown action id: " + id);
        return null;
    }
}
