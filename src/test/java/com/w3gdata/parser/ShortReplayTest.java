package com.w3gdata.parser;

import com.google.common.collect.ImmutableList;
import com.w3gdata.parser.action.Action;
import com.w3gdata.parser.action.Actions;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ShortReplayTest extends ParsingTest {
    public ShortReplayTest() {
        super("short.w3g");
    }

    @Test
    public void expectHavingPauseAction() throws Exception {
        ImmutableList<Action> actions = this.actions.get(Actions.PAUSE_GAME);
        assertFalse(actions.isEmpty());
    }


}
