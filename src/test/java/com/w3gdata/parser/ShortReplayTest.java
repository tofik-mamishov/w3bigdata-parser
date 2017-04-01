package com.w3gdata.parser;

import com.w3gdata.parser.action.ActionType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortReplayTest extends ParsingTest {
    public ShortReplayTest() {
        super("short.w3g");
    }

    @Test
    public void expectHavingPauseAction() throws Exception {
        assertThat(w3gInfo.actionTypes().has(ActionType.PauseGame)).isTrue();
    }
}
