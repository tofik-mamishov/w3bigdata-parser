package com.w3gdata;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class StatisticsData {

    public ReplayInformation replayInformation;
    public PlayerRecord host = new PlayerRecord();
    private List<PlayerRecord> playerRecords;

    private String gameMode;

    private String map;

    private String matchLength;

    public StatisticsData() {
        playerRecords = new ArrayList<PlayerRecord>();
        replayInformation = new ReplayInformation();
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
