package com.w3gdata.parser;

import com.w3gdata.parser.actionblock.ActionBlock;
import com.w3gdata.parser.actionblock.PauseGame;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class AltQQTest extends ParsingTest {

    public AltQQTest() {
        super("altqq_fadzu.w3g");
    }

    @Test
    public void expectHavingPauseAction() throws Exception {
        List<ActionBlock> allActionBlocks = w3gInfo.getAllActionBlocks();
        Optional<ActionBlock> pauseGame = allActionBlocks.stream().filter(a -> a instanceof PauseGame).findFirst();
        assertTrue(pauseGame.isPresent());

//        assertTrue(Files.equal(new File("altqq.w3g"), new File("backToW3g.w3g")));
    }


}
