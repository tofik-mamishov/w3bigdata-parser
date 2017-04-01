package com.w3gdata.parser.action;

import com.w3gdata.util.ByteReader;
import org.apache.log4j.Logger;

import java.util.function.Function;

public enum ActionType {
    PauseGame(0x01, NoDataAction::new),
    ResumeGame(0x02, NoDataAction::new),
    GameSpeed(0x03, GameSpeedAction::new),
    GameSpeedIncrease(0x04, NoDataAction::new),
    GameSpeedDecrease(0x05, NoDataAction::new),
    SaveGame(0x06, SaveGameAction::new),
    SaveGameFinished(0x07, SaveGameFinishedAction::new),
    UnitBuildingAbility(0x10, UnitBuildingAbilityAction::new),
    UnitBuildingAbilityTargeted(0x11, UnitBuildingAbilityTargetedAction::new),
    UnitBuildingAbilityTargetedId(0x12, UnitBuildingAbilityTargetedIdAction::new),
    ItemGivenDropped(0x13, ItemGivenDroppedAction::new),
    UnitBuildingAbility2Targets2Items(0x14, UnitBuildingAbility2Targets2ItemsAction::new),
    CHANGE_SELECTION(0x16, ChangeSelectionAction::new),
    ASSIGN_GROUP_HOTKEY(0x17, AssignGroupHotkeyAction::new),
    SELECT_GROUP_HOTKEY(0x18, SelectGroupHotkeyAction::new),
    SELECT_SUBGROUP_1_14b(0x19, SelectSubgroup114bAction::new),
    PreSubSelection(0x1A, NoDataAction::new),
    SELECT_GROUND_ITEM(0x1C, SelectGroundItemAction::new),
    CANCEL_HERO_REVIVAL(0x1D, CancelHeroRevivalAction::new),
    REMOVE_UNIT_FROM_BUILDING_QUEUE(0x1E, RemoveUnitFromBuildingQueueAction::new),
    CHANGE_ALLY_OPTIONS(0x50, ChangeAllyOptionsAction::new),
    TRANSFER_RESOURCES(0x51, TransferResourcesAction::new),
    MAP_TRIGGER_CHAT_COMMAND(0x60, MapTriggerChatCommandAction::new),
    EscPressed(0x61, NoDataAction::new),
    SCENARIO_TRIGGER(0x62, ScenarioTriggerAction::new),
    EnterChooseHeroSkillSubMenu(0x66, NoDataAction::new),
    EnterChooseBuildingSubmenu(0x67, NoDataAction::new),
    MINIMAP_SIGNAL(0x68, MinimapSignalAction::new),
    CONTINUE_GAME_B(0x69, UnknownActionFactory.ofSize(16)),
    CONTINUE_GAME_A(0x6A, UnknownActionFactory.ofSize(17)),
    MMD_MESSAGE(0x6B, MMDMessage::new),
    UNKNOWN_0_X_1B(0x1B, UnknownActionFactory.ofSize(10)),
    UNKNOWN_0_X_21(0x21, UnknownActionFactory.ofSize(9)),
    UNKNOWN_0_X_94(0x94, UnknownActionFactory.ofSize(4)),
    UNKNOWN_0_X_6C(0x6C, UnknownActionFactory.ofSize(6)),
    UNKNOWN_0_X_74(0x74, UnknownActionFactory.ofSize(2)),
    UNKNOWN_0_X_75(0x75, UnknownActionFactory.ofSize(2));

    private static final Logger logger = Logger.getLogger(ActionType.class);

    public final int id;
    public final Function<ByteReader, Action> readAction;

    ActionType(int id, Function<ByteReader, Action> readAction) {
        this.id = id;
        this.readAction = readAction;
    }

    public Action read(ByteReader reader) {
        return readAction.apply(reader);
    }

    public static ActionType getById(int id) {
        for (ActionType action: values()) {
            if (action.id == id) {
                return action;
            }
        }
        logger.error("Unknown action id: " + id);
        return null;
    }
}
