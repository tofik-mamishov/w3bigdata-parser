package com.w3gdata.parser;

import com.w3gdata.util.ByteReader;
import com.w3gdata.util.ByteUtils;
import org.apache.log4j.Logger;

public class ReplayDataReader {

    private static final Logger logger = Logger.getLogger(ReplayDataReader.class);

    private final W3gInfo data;
    private final ByteReader reader;

    public ReplayDataReader(W3gInfo data, ByteReader reader) {
        this.data = data;
        this.reader = reader;
    }

    public void read() {
        try {
            while (reader.hasMore()) {
                int replayDataId = reader.nextByte();
                ReplayDataFormat.of(replayDataId).ifPresent(format -> {
                    if (format.isKnown) {
                        processBlockByFormat(format);
                    } else {
                        reader.forward(format.fixedSize - 1);
                    }
                });
            }
        } catch (Exception e) {
            logger.error("Failed on offset: " + reader.offset());
            logger.error(e.getMessage(), e);
            ByteUtils.debugToFile(reader.getBuf(), "full.bin");
            throw new W3gParserException(e);
        }
    }

    private void processBlockByFormat(ReplayDataFormat replayDataFormat) {
        switch (replayDataFormat) {
            case LEAVE_GAME:
                data.leaveGameRecords.add(new LeaveGameRecord(reader));
                break;
            case TIME_SLOT_BLOCK_NEW:
                data.timeSlots.add(new TimeSlot(reader));
                break;
            case PLAYER_CHAT_MESSAGE:
                data.playerChatMessages.add(new PlayerChatMessage(reader));
                break;
            case FORCED_GAME_END_COUNTDOWN:
                data.forcedGameEndCountdownRecords.add(new ForcedGameEndCountdownRecord(reader));
                break;
            case UNKNOWN:
            case FIRST_STARTBLOCK:
            case SECOND_STARTBLOCK:
            case THIRD_STARTBLOCK:
            case TIME_SLOT_BLOCK_OLD:
            case CHECKSUM_RANDOM_SEED:
                break;
        }
    }
}
