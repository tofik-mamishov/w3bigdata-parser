package com.w3gdata.parser;

import com.w3gdata.parser.action.Action;
import com.w3gdata.parser.action.PauseGame;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AltQQTest extends ParsingTest {

    public AltQQTest() {
        super("altqq_fadzu.w3g");
    }

    @Test
    public void expectHavingPauseAction() throws Exception {
        List<Action> allActions = w3gInfo.getAllActionBlocks();
        Optional<Action> pauseGame = allActions.stream().filter(a -> a instanceof PauseGame).findFirst();

        assertThat(pauseGame).isPresent();
    }


}
