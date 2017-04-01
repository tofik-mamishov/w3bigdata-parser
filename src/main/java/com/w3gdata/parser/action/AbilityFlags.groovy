package com.w3gdata.parser.action

import com.w3gdata.util.ByteReader


class AbilityFlags {
    EnumSet<AbilityFlag> flags

    static enum AbilityFlag {
        Queued(0),
        AppliedToAllUnitsInSubgroup(1),
        AreaEffect(2),
        GroupCommand(3),
        MoveGroupWithoutFormation(4),
        CtrlHeldDownForSubGroup(6),
        AutocastOn(7),

        final int mask

        AbilityFlag(int shift) {
            this.mask = 1 << shift
        }

        static EnumSet<AbilityFlag> parse(int value) {
            EnumSet<AbilityFlag> result = EnumSet.noneOf(AbilityFlag.class)
            for (AbilityFlag flag : values()) {
                if (flag.mask & value) {
                    result.add(flag)
                }
            }
            return result
        }
    }

    AbilityFlags(ByteReader reader) {
        flags = AbilityFlag.parse(reader.nextWord())
    }

    boolean isQueued() {
        flags.contains(AbilityFlag.Queued)
    }

    boolean isAppliedToAllUnitsInSubgroup() {
        flags.contains(AbilityFlag.AppliedToAllUnitsInSubgroup)
    }

    boolean isAreaEffect() {
        flags.contains(AbilityFlag.AreaEffect)
    }

    boolean isGroupCommand() {
        flags.contains(AbilityFlag.GroupCommand)
    }

    boolean isMoveGroupWithoutFormation() {
        flags.contains(AbilityFlag.MoveGroupWithoutFormation)
    }

    boolean isCtrlHeldDownForSubGroup() {
        flags.contains(AbilityFlag.CtrlHeldDownForSubGroup)
    }

    boolean isAutocastOn() {
        flags.contains(AbilityFlag.AutocastOn)
    }
}
