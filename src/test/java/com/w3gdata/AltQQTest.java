package com.w3gdata;

import com.w3gdata.actionblock.ActionBlock;
import com.w3gdata.actionblock.PauseGame;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class AltQQTest extends ParsingTest {

    public AltQQTest() {
        super("altqq.w3g");
    }

    @Test
    public void expectHavingPauseAction() throws Exception {
        List<ActionBlock> allActionBlocks = w3gInfo.getAllActionBlocks();
        Optional<ActionBlock> pauseGame = allActionBlocks.stream().filter(a -> a instanceof PauseGame).findFirst();
        assertTrue(pauseGame.isPresent());
    }


}
