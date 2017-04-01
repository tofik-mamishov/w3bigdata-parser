package com.w3gdata.parser;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.w3gdata.parser.action.Action;
import com.w3gdata.parser.action.ActionType;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public abstract class ParsingTest {

    private static final Logger logger = Logger.getLogger(ParsingTest.class);

    protected W3gInfo w3gInfo;
    protected W3gParser parser;
    protected ImmutableListMultimap<ActionType, Action> actions;

    public ParsingTest(String name) {
        URL resourceURL = Resources.getResource(name);
        ByteSource replaySourceFile = Resources.asByteSource(resourceURL);
        parser = new W3gParser();
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            w3gInfo = parser.parse(replaySourceFile);
            logger.info("Parsed in " + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + " ms.");

            List<Action> allActions = w3gInfo.getAllActionBlocks();
//            actions = Multimaps.index(allActions, actionBlock -> ActionType.getById(actionBlock.getId()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            fail("Couldn't parse " + name);
        }
        assertNotNull(w3gInfo);
    }
}
