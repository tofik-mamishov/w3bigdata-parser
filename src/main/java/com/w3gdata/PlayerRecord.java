package com.w3gdata;

public class PlayerRecord {
    public byte recordId;
    public byte playerId;
    public String name;
    public AdditionalData additionalData = new AdditionalData();

    public static class AdditionalData {
        public int size;
    }
}
