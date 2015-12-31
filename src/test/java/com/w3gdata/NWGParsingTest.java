package com.w3gdata;

import com.google.common.collect.ImmutableList;
import com.w3gdata.actionblock.ActionBlock;
import com.w3gdata.actionblock.ActionBlockFormat;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class NWGParsingTest extends ParsingTest {

    public NWGParsingTest() {
        super("longnwg.w3g");
    }

    @Test
    public void expectHavingPauseAction() throws Exception {
        ImmutableList<ActionBlock> actionBlocks = actions.get(ActionBlockFormat.ASSIGN_GROUP_HOTKEY);
        assertFalse(actionBlocks.isEmpty());
    }
}
