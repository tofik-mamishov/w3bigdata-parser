package com.w3gdata;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class W3gInfo {

    public ReplayInformation replayInformation;
    public PlayerRecord host = new PlayerRecord();
    public GameSettings gameSettings = new GameSettings();
    public GameStartRecord gameStartRecord = new GameStartRecord();
    public List<LeaveGameRecord> leaveGameRecords;
    public List<ForcedGameEndCountdownRecord> forcedGameEndCountdownRecords;
    public List<TimeSlotBlock> timeSlotBlocks;
    public List<PlayerChatMessage> playerChatMessages;

    private List<PlayerRecord> playerRecords;

    private String gameMode;

    private String map;

    private String matchLength;
    public String gameName;
    public int playerCount;
    public int gameType;
    public int languageId;

    public W3gInfo() {
        playerRecords = new ArrayList<>();
        leaveGameRecords = new ArrayList<>();
        forcedGameEndCountdownRecords = new ArrayList<>();
        timeSlotBlocks = new ArrayList<>();
        playerChatMessages = new ArrayList<>();
    }

    public List<PlayerRecord> getPlayerRecords() {
        return playerRecords;
    }

    public void setPlayerRecords(List<PlayerRecord> playerRecords) {
        this.playerRecords = playerRecords;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMatchLength() {
        return matchLength;
    }

    public void setMatchLength(String matchLength) {
        this.matchLength = matchLength;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

}
