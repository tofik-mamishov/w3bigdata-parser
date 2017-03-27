package com.w3gdata.parser;

import com.w3gdata.parser.action.PauseGame;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AltQQTest extends ParsingTest {

    public AltQQTest() {
        super("altqq_fadzu.w3g");
    }

    @Test
    public void expectHavingPauseAction() throws Exception {
        Optional<PauseGame> pauseGame = w3gInfo.actionBlocks().select(PauseGame.class).findFirst();

        assertThat(pauseGame).isPresent();
    }


}
