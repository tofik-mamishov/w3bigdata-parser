package com.w3gdata.parser;

import com.google.common.collect.ImmutableList;
import com.w3gdata.parser.actionblock.ActionBlock;
import com.w3gdata.parser.actionblock.ActionBlockFormat;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ShortReplayTest extends ParsingTest {
    public ShortReplayTest() {
        super("short.w3g");
    }

    @Test
    public void expectHavingPauseAction() throws Exception {
        ImmutableList<ActionBlock> actionBlocks = actions.get(ActionBlockFormat.PAUSE_GAME);
        assertFalse(actionBlocks.isEmpty());
    }


}