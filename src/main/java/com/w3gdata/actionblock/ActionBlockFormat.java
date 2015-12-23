package com.w3gdata.actionblock;

import com.w3gdata.util.ByteBuffer;

import java.util.HashMap;
import java.util.Map;

public enum ActionBlockFormat {
    PAUSE_GAME(PauseGame.ID, PauseGame.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new PauseGame();
        }
    },
    RESUME_GAME(ResumeGame.ID, ResumeGame.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new PauseGame();
        }
    },
    GAME_SPEED(GameSpeed.ID, GameSpeed.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new GameSpeed(buf.readByte());
        }
    },
    GAME_SPEED_INCREASING(GameSpeedIncreasing.ID, GameSpeed.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new GameSpeedIncreasing();
        }
    },
    GAME_SPEED_DECREASING(GameSpeedDecreasing.ID, GameSpeedDecreasing.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new GameSpeedDecreasing();
        }
    },
    SAVE_GAME(SaveGame.ID, SaveGame.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new SaveGame(buf.readNullTerminatedString());
        }
    },
    SAVE_GAME_FINISHED(SaveGameFinished.ID, SaveGameFinished.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new SaveGameFinished(buf.readDWord());
        }
    },
    UNIT_BUILDING_ABILITY(UnitBuildingAbility.ID, UnitBuildingAbility.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            UnitBuildingAbility ability = new UnitBuildingAbility();
            ability.abilityFlag = buf.readWord();
            ability.itemId = buf.readDWord();
            ability.unknownA = buf.readDWord();
            ability.unknownB = buf.readDWord();
            return ability;
        }
    },
    UNIT_BUILDING_ABILITY_TARGETED(UnitBuildingAbilityTargeted.ID, UnitBuildingAbilityTargeted.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            UnitBuildingAbilityTargeted ability = new UnitBuildingAbilityTargeted();
            ability.abilityFlag = buf.readWord();
            ability.itemId = buf.readDWord();
            ability.unknownA = buf.readDWord();
            ability.unknownB = buf.readDWord();
            ability.targetX = buf.readDWord();
            ability.targetY = buf.readDWord();
            return ability;
        }
    },
    UNIT_BUILDING_ABILITY_TARGETED_ID(UnitBuildingAbilityTargetedId.ID, UnitBuildingAbilityTargetedId.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            UnitBuildingAbilityTargetedId ability = new UnitBuildingAbilityTargetedId();
            ability.abilityFlag = buf.readWord();
            ability.itemId = buf.readDWord();
            ability.unknownA = buf.readDWord();
            ability.unknownB = buf.readDWord();
            ability.targetX = buf.readDWord();
            ability.targetY = buf.readDWord();
            ability.objId1 = buf.readDWord();
            ability.objId2 = buf.readDWord();
            return ability;
        }
    },
    ITEM_GIVEN_DROPPED(ItemGivenDropped.ID, ItemGivenDropped.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            ItemGivenDropped ability = new ItemGivenDropped();
            ability.abilityFlag = buf.readWord();
            ability.itemId = buf.readDWord();
            ability.unknownA = buf.readDWord();
            ability.unknownB = buf.readDWord();
            ability.targetX = buf.readDWord();
            ability.targetY = buf.readDWord();
            ability.targetObjId1 = buf.readDWord();
            ability.targetObjId2 = buf.readDWord();
            ability.itemObjId1 = buf.readDWord();
            ability.itemObjId2 = buf.readDWord();
            return ability;
        }
    },
    UNIT_BUILDING_ABILITY_2_TARGETS_2_ITEMS(UnitBuildingAbility2Targets2Items.ID,
            UnitBuildingAbility2Targets2Items.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            UnitBuildingAbility2Targets2Items ability = new UnitBuildingAbility2Targets2Items();
            ability.abilityFlag = buf.readWord();
            ability.itemId = buf.readDWord();
            ability.unknownA = buf.readDWord();
            ability.unknownB = buf.readDWord();
            ability.targetX = buf.readDWord();
            ability.targetY = buf.readDWord();
            ability.item2Id = buf.readDWord();
            ability.unknownBytes = buf.readBytes(9);
            ability.target2X = buf.readDWord();
            ability.target2Y = buf.readDWord();
            return ability;
        }
    },
    CHANGE_SELECTION(ChangeSelection.ID, ChangeSelection.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            ChangeSelection changeSelection = new ChangeSelection();
            changeSelection.selectMode = buf.readByte();
            changeSelection.unitsBuildingsNumber = buf.readWord();
            int limit = buf.getOffset() + changeSelection.unitsBuildingsNumber;
            while (buf.getOffset() < limit) {
                ObjPair pair = new ObjPair();
                pair.objectId1 = buf.readDWord();
                pair.objectId2 = buf.readDWord();
                changeSelection.selectedObjs.add(new ObjPair(buf.readDWord(), buf.readDWord()));
            }
            return changeSelection;
        }
    },
    ASSIGN_GROUP_HOTKEY(AssignGroupHotkey.ID, AssignGroupHotkey.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            AssignGroupHotkey assignGroupHotkey = new AssignGroupHotkey();
            assignGroupHotkey.groupNumber = buf.readByte();
            assignGroupHotkey.selectedObjNumber = buf.readWord();
            int limit = buf.getOffset() + assignGroupHotkey.selectedObjNumber;
            while (buf.getOffset() < limit) {
                assignGroupHotkey.selectedObjs.add(new ObjPair(buf.readDWord(), buf.readDWord()));
            }
            return assignGroupHotkey;
        }
    },
    SELECT_GROUP_HOTKEY(SelectGroupHotkey.ID, SelectGroupHotkey.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            SelectGroupHotkey selectGroupHotkey = new SelectGroupHotkey();
            selectGroupHotkey.groupNumber = buf.readByte();
            selectGroupHotkey.unknown = buf.readByte();
            return selectGroupHotkey;
        }
    },
    SELECT_SUBGROUP(SelectSubgroup.ID, SelectSubgroup.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new SelectSubgroup(buf.readByte());
        }
    },
    SELECT_SUBGROUP_1_14b(SelectSubgroup114b.ID, SelectSubgroup114b.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            SelectSubgroup114b selectSubgroup114b = new SelectSubgroup114b();
            selectSubgroup114b.itemId = buf.readDWord();
            selectSubgroup114b.objPair = new ObjPair(buf.readDWord(), buf.readDWord());
            return selectSubgroup114b;
        }
    },
    PRE_SUBSELECTION(PreSubselection.ID, PreSubselection.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new PreSubselection();
        }
    },
    UNKNOWN_0_X_1B(Unknown0x1B.ID, Unknown0x1B.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new Unknown0x1B().skip(buf);
        }
    },
    SELECT_GROUND_ITEM(SelectGroundItem.ID, SelectGroundItem.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            SelectGroundItem selectGroundItem = new SelectGroundItem();
            selectGroundItem.unknown = buf.readByte();
            selectGroundItem.objPair = new ObjPair(buf.readDWord(), buf.readDWord());
            return selectGroundItem;
        }
    },
    CANCEL_HERO_REVIVAL(CancelHeroRevival.ID, CancelHeroRevival.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            CancelHeroRevival cancelHeroRevival = new CancelHeroRevival();
            cancelHeroRevival.unitPair = new ObjPair(buf.readDWord(), buf.readDWord());
            return cancelHeroRevival;
        }
    },
    REMOVE_UNIT_FROM_BUILDING_QUEUE(RemoveUnitFromBuildingQueue.ID, RemoveUnitFromBuildingQueue.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            RemoveUnitFromBuildingQueue removeUnitFromBuildingQueue = new RemoveUnitFromBuildingQueue();
            removeUnitFromBuildingQueue.slotNumber = buf.readByte();
            removeUnitFromBuildingQueue.itemId = buf.readDWord();
            return removeUnitFromBuildingQueue;
        }
    },
    UNKNOWN_0_X_21(Unknown0x21.ID, Unknown0x21.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new Unknown0x21().skip(buf);
        }
    },
    //SinglePlayerCheats has another way of processing
    CHANGE_ALLY_OPTIONS(ChangeAllyOptions.ID, ChangeAllyOptions.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            ChangeAllyOptions changeAllyOptions = new ChangeAllyOptions();
            changeAllyOptions.playerSlotNumber = buf.readByte();
            changeAllyOptions.flags = buf.readDWord();
            return changeAllyOptions;
        }
    },
    TRANSFER_RESOURCES(TransferResources.ID, TransferResources.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            TransferResources transferResources = new TransferResources();
            transferResources.playerSlotNumber = buf.readByte();
            transferResources.goldToTransfer = buf.readDWord();
            transferResources.lumberToTransfer = buf.readDWord();
            return transferResources;
        }
    },
    MAP_TRIGGER_CHAT_COMMAND(MapTriggerChatCommand.ID, MapTriggerChatCommand.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            MapTriggerChatCommand mapTriggerChatCommand = new MapTriggerChatCommand();
            mapTriggerChatCommand.unknownA = buf.readDWord();
            mapTriggerChatCommand.unknownB = buf.readDWord();
            mapTriggerChatCommand.chatCommand = buf.readNullTerminatedString();
            return mapTriggerChatCommand;
        }
    },
    ESC_PRESSED(EscPressed.ID, EscPressed.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new EscPressed();
        }
    },
    SCENARIO_TRIGGER(ScenarioTrigger.ID, ScenarioTrigger.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            ScenarioTrigger scenarioTrigger = new ScenarioTrigger();
            scenarioTrigger.unknownA = buf.readDWord();
            scenarioTrigger.unknownB = buf.readDWord();
            scenarioTrigger.unknownCounter = buf.readDWord();
            return scenarioTrigger;
        }
    },
    ENTER_CHOOSE_HERO_SKILL_SUBMENU(EnterChooseHeroSkillSubmenu.ID, EnterChooseHeroSkillSubmenu.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new EnterChooseHeroSkillSubmenu();
        }
    },
    ENTER_CHOOSE_BUILDING_SUBMENU(EnterChooseBuildingSubmenu.ID, EnterChooseBuildingSubmenu.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new EnterChooseBuildingSubmenu();
        }
    },
    MINIMAP_SIGNAL(MinimapSignal.ID, MinimapSignal.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            MinimapSignal minimapSignal = new MinimapSignal();
            minimapSignal.locationX = buf.readDWord();
            minimapSignal.locationY = buf.readDWord();
            minimapSignal.unknown = buf.readDWord();
            return minimapSignal;
        }
    },
    CONTINUE_GAME_B(ContinueGameB.ID, ContinueGameB.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new ContinueGameB().skip(buf);
        }
    },
    CONTINUE_GAME_A(ContinueGameA.ID, ContinueGameA.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new ContinueGameA().skip(buf);
        }
    },
    UNKNOWN_0_X_6B(Unknown0x6B.ID, Unknown0x6B.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new Unknown0x6B().skip(buf);
        }
    },
    UNKNOWN_0_X_6C(Unknown0x6C.ID, Unknown0x6C.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new Unknown0x6C().skip(buf);
        }
    },
    UNKNOWN_0_X_74(Unknown0x74.ID, Unknown0x74.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new Unknown0x74().skip(buf);
        }
    },
    UNKNOWN_0_X_75(Unknown0x75.ID, Unknown0x75.class) {
        @Override
        public ActionBlock process(ByteBuffer buf) {
            return new Unknown0x75().skip(buf);
        }
    };

    final static Map<Integer, ActionBlockFormat> formats;

    int id;

    Class<? extends ActionBlock> type;

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

    public static Class<? extends ActionBlock> getTypeById(int id) {
        return getById(id).type;
    }

    public static ActionBlockFormat getById(int id) {
        return formats.get(id);
    }

    public abstract ActionBlock process(ByteBuffer buf);
}
