package com.w3gdata.actionblock;

import com.w3gdata.util.ByteReader;

import java.util.HashMap;
import java.util.Map;

public enum ActionBlockFormat {
    PAUSE_GAME(PauseGame.ID, PauseGame.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new PauseGame();
        }
    },
    RESUME_GAME(ResumeGame.ID, ResumeGame.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new PauseGame();
        }
    },
    GAME_SPEED(GameSpeed.ID, GameSpeed.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new GameSpeed(buf.nextByte());
        }
    },
    GAME_SPEED_INCREASING(GameSpeedIncreasing.ID, GameSpeed.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new GameSpeedIncreasing();
        }
    },
    GAME_SPEED_DECREASING(GameSpeedDecreasing.ID, GameSpeedDecreasing.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new GameSpeedDecreasing();
        }
    },
    SAVE_GAME(SaveGame.ID, SaveGame.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new SaveGame(buf.nextNullTerminatedString());
        }
    },
    SAVE_GAME_FINISHED(SaveGameFinished.ID, SaveGameFinished.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new SaveGameFinished(buf.nextDWord());
        }
    },
    UNIT_BUILDING_ABILITY(UnitBuildingAbility.ID, UnitBuildingAbility.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            UnitBuildingAbility ability = new UnitBuildingAbility();
            ability.abilityFlag = buf.nextWord();
            ability.itemId = buf.nextDWord();
            ability.unknownA = buf.nextDWord();
            ability.unknownB = buf.nextDWord();
            return ability;
        }
    },
    UNIT_BUILDING_ABILITY_TARGETED(UnitBuildingAbilityTargeted.ID, UnitBuildingAbilityTargeted.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            UnitBuildingAbilityTargeted ability = new UnitBuildingAbilityTargeted();
            ability.abilityFlag = buf.nextWord();
            ability.itemId = buf.nextDWord();
            ability.unknownA = buf.nextDWord();
            ability.unknownB = buf.nextDWord();
            ability.targetX = buf.nextDWord();
            ability.targetY = buf.nextDWord();
            return ability;
        }
    },
    UNIT_BUILDING_ABILITY_TARGETED_ID(UnitBuildingAbilityTargetedId.ID, UnitBuildingAbilityTargetedId.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            UnitBuildingAbilityTargetedId ability = new UnitBuildingAbilityTargetedId();
            ability.abilityFlag = buf.nextWord();
            ability.itemId = buf.nextDWord();
            ability.unknownA = buf.nextDWord();
            ability.unknownB = buf.nextDWord();
            ability.targetX = buf.nextDWord();
            ability.targetY = buf.nextDWord();
            ability.objId1 = buf.nextDWord();
            ability.objId2 = buf.nextDWord();
            return ability;
        }
    },
    ITEM_GIVEN_DROPPED(ItemGivenDropped.ID, ItemGivenDropped.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            ItemGivenDropped ability = new ItemGivenDropped();
            ability.abilityFlag = buf.nextWord();
            ability.itemId = buf.nextDWord();
            ability.unknownA = buf.nextDWord();
            ability.unknownB = buf.nextDWord();
            ability.targetX = buf.nextDWord();
            ability.targetY = buf.nextDWord();
            ability.targetObjId1 = buf.nextDWord();
            ability.targetObjId2 = buf.nextDWord();
            ability.itemObjId1 = buf.nextDWord();
            ability.itemObjId2 = buf.nextDWord();
            return ability;
        }
    },
    UNIT_BUILDING_ABILITY_2_TARGETS_2_ITEMS(UnitBuildingAbility2Targets2Items.ID,
            UnitBuildingAbility2Targets2Items.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            UnitBuildingAbility2Targets2Items ability = new UnitBuildingAbility2Targets2Items();
            ability.abilityFlag = buf.nextWord();
            ability.itemId = buf.nextDWord();
            ability.unknownA = buf.nextDWord();
            ability.unknownB = buf.nextDWord();
            ability.targetX = buf.nextDWord();
            ability.targetY = buf.nextDWord();
            ability.item2Id = buf.nextDWord();
            ability.unknownBytes = buf.nextBytes(9);
            ability.target2X = buf.nextDWord();
            ability.target2Y = buf.nextDWord();
            return ability;
        }
    },
    CHANGE_SELECTION(ChangeSelection.ID, ChangeSelection.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            ChangeSelection changeSelection = new ChangeSelection();
            changeSelection.selectMode = buf.nextByte();
            changeSelection.unitsBuildingsNumber = buf.nextWord();
            int limit = buf.offset() + changeSelection.unitsBuildingsNumber;
            while (buf.offset() < limit) {
                ObjPair pair = new ObjPair();
                pair.objectId1 = buf.nextDWord();
                pair.objectId2 = buf.nextDWord();
                changeSelection.selectedObjs.add(new ObjPair(buf.nextDWord(), buf.nextDWord()));
            }
            return changeSelection;
        }
    },
    ASSIGN_GROUP_HOTKEY(AssignGroupHotkey.ID, AssignGroupHotkey.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            AssignGroupHotkey assignGroupHotkey = new AssignGroupHotkey();
            assignGroupHotkey.groupNumber = buf.nextByte();
            assignGroupHotkey.selectedObjNumber = buf.nextWord();
            int limit = buf.offset() + assignGroupHotkey.selectedObjNumber;
            while (buf.offset() < limit) {
                assignGroupHotkey.selectedObjs.add(new ObjPair(buf.nextDWord(), buf.nextDWord()));
            }
            return assignGroupHotkey;
        }
    },
    SELECT_GROUP_HOTKEY(SelectGroupHotkey.ID, SelectGroupHotkey.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            SelectGroupHotkey selectGroupHotkey = new SelectGroupHotkey();
            selectGroupHotkey.groupNumber = buf.nextByte();
            selectGroupHotkey.unknown = buf.nextByte();
            return selectGroupHotkey;
        }
    },
    SELECT_SUBGROUP(SelectSubgroup.ID, SelectSubgroup.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new SelectSubgroup(buf.nextByte());
        }
    },
    SELECT_SUBGROUP_1_14b(SelectSubgroup114b.ID, SelectSubgroup114b.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            SelectSubgroup114b selectSubgroup114b = new SelectSubgroup114b();
            selectSubgroup114b.itemId = buf.nextDWord();
            selectSubgroup114b.objPair = new ObjPair(buf.nextDWord(), buf.nextDWord());
            return selectSubgroup114b;
        }
    },
    PRE_SUBSELECTION(PreSubselection.ID, PreSubselection.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new PreSubselection();
        }
    },
    UNKNOWN_0_X_1B(Unknown0x1B.ID, Unknown0x1B.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new Unknown0x1B().skip(buf);
        }
    },
    SELECT_GROUND_ITEM(SelectGroundItem.ID, SelectGroundItem.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            SelectGroundItem selectGroundItem = new SelectGroundItem();
            selectGroundItem.unknown = buf.nextByte();
            selectGroundItem.objPair = new ObjPair(buf.nextDWord(), buf.nextDWord());
            return selectGroundItem;
        }
    },
    CANCEL_HERO_REVIVAL(CancelHeroRevival.ID, CancelHeroRevival.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            CancelHeroRevival cancelHeroRevival = new CancelHeroRevival();
            cancelHeroRevival.unitPair = new ObjPair(buf.nextDWord(), buf.nextDWord());
            return cancelHeroRevival;
        }
    },
    REMOVE_UNIT_FROM_BUILDING_QUEUE(RemoveUnitFromBuildingQueue.ID, RemoveUnitFromBuildingQueue.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            RemoveUnitFromBuildingQueue removeUnitFromBuildingQueue = new RemoveUnitFromBuildingQueue();
            removeUnitFromBuildingQueue.slotNumber = buf.nextByte();
            removeUnitFromBuildingQueue.itemId = buf.nextDWord();
            return removeUnitFromBuildingQueue;
        }
    },
    UNKNOWN_0_X_21(Unknown0x21.ID, Unknown0x21.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new Unknown0x21().skip(buf);
        }
    },
    //SinglePlayerCheats has another way of processing
    CHANGE_ALLY_OPTIONS(ChangeAllyOptions.ID, ChangeAllyOptions.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            ChangeAllyOptions changeAllyOptions = new ChangeAllyOptions();
            changeAllyOptions.playerSlotNumber = buf.nextByte();
            changeAllyOptions.flags = buf.nextDWord();
            return changeAllyOptions;
        }
    },
    TRANSFER_RESOURCES(TransferResources.ID, TransferResources.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            TransferResources transferResources = new TransferResources();
            transferResources.playerSlotNumber = buf.nextByte();
            transferResources.goldToTransfer = buf.nextDWord();
            transferResources.lumberToTransfer = buf.nextDWord();
            return transferResources;
        }
    },
    MAP_TRIGGER_CHAT_COMMAND(MapTriggerChatCommand.ID, MapTriggerChatCommand.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            MapTriggerChatCommand mapTriggerChatCommand = new MapTriggerChatCommand();
            mapTriggerChatCommand.unknownA = buf.nextDWord();
            mapTriggerChatCommand.unknownB = buf.nextDWord();
            mapTriggerChatCommand.chatCommand = buf.nextNullTerminatedString();
            return mapTriggerChatCommand;
        }
    },
    ESC_PRESSED(EscPressed.ID, EscPressed.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new EscPressed();
        }
    },
    SCENARIO_TRIGGER(ScenarioTrigger.ID, ScenarioTrigger.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            ScenarioTrigger scenarioTrigger = new ScenarioTrigger();
            scenarioTrigger.unknownA = buf.nextDWord();
            scenarioTrigger.unknownB = buf.nextDWord();
            scenarioTrigger.unknownCounter = buf.nextDWord();
            return scenarioTrigger;
        }
    },
    ENTER_CHOOSE_HERO_SKILL_SUBMENU(EnterChooseHeroSkillSubmenu.ID, EnterChooseHeroSkillSubmenu.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new EnterChooseHeroSkillSubmenu();
        }
    },
    ENTER_CHOOSE_BUILDING_SUBMENU(EnterChooseBuildingSubmenu.ID, EnterChooseBuildingSubmenu.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new EnterChooseBuildingSubmenu();
        }
    },
    MINIMAP_SIGNAL(MinimapSignal.ID, MinimapSignal.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            MinimapSignal minimapSignal = new MinimapSignal();
            minimapSignal.locationX = buf.nextDWord();
            minimapSignal.locationY = buf.nextDWord();
            minimapSignal.unknown = buf.nextDWord();
            return minimapSignal;
        }
    },
    CONTINUE_GAME_B(ContinueGameB.ID, ContinueGameB.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new ContinueGameB().skip(buf);
        }
    },
    CONTINUE_GAME_A(ContinueGameA.ID, ContinueGameA.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new ContinueGameA().skip(buf);
        }
    },
    UNKNOWN_0_X_6B(Unknown0x6B.ID, Unknown0x6B.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new Unknown0x6B().skip(buf);
        }
    },
    UNKNOWN_0_X_6C(Unknown0x6C.ID, Unknown0x6C.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new Unknown0x6C().skip(buf);
        }
    },
    UNKNOWN_0_X_74(Unknown0x74.ID, Unknown0x74.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
            return new Unknown0x74().skip(buf);
        }
    },
    UNKNOWN_0_X_75(Unknown0x75.ID, Unknown0x75.class) {
        @Override
        public ActionBlock process(ByteReader buf) {
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

    public abstract ActionBlock process(ByteReader buf);
}
