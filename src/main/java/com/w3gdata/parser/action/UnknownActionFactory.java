package com.w3gdata.parser.action;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public enum UnknownActionFactory {
    INSTANCE;

    private final static ImmutableMap<Integer, Integer> SKIP_SIZES;

    static {
        SKIP_SIZES = ImmutableMap.<Integer, Integer>builder()
                .put(0x1B, 10)
                .put(0x6C, 6)
                .put(0x21, 9)
                .put(0x74, 2)
                .put(0x75, 2)
                .put(0x92, 4)
                .put(0x94, 4)
                .build();
    }

    private Map<Integer, UselessAction> created;

    UnknownActionFactory() {
        created = new HashMap<>();
    }

    public UselessAction get(int id) {
        if (created.containsKey(id)) {
            return created.get(id);
        } else {
            UselessAction action = UselessAction.Builder.anUselessAction().withId(id).withSkip(SKIP_SIZES.get(id)).build();
            created.put(id, action);
            return action;
        }
    }
}
