package com.w3gdata.parser;

import com.google.common.collect.ImmutableList;
import com.w3gdata.parser.action.Action;
import com.w3gdata.parser.action.Actions;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class NWGParsingTest extends ParsingTest {

    public NWGParsingTest() {
        super("longnwg.w3g");
    }

    @Test
    public void expectHavingPauseAction() throws Exception {
        ImmutableList<Action> actions = this.actions.get(Actions.ASSIGN_GROUP_HOTKEY);
        assertFalse(actions.isEmpty());
    }
}
