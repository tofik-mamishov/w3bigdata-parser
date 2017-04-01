package com.w3gdata.parser;

import com.google.common.collect.ImmutableList;
import com.w3gdata.parser.action.Action;
import com.w3gdata.parser.action.ActionType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class LongReplayTest extends ParsingTest {

    public LongReplayTest() {
        super("long.w3g");
    }

    @Test
    public void expectHavingPauseAction() throws Exception {
        ImmutableList<Action> actions = this.actions.get(ActionType.AssignGroupHotkey);
        assertFalse(actions.isEmpty());
    }
}
