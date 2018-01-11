package com.example.androidmajeure;

public class RoomContextState {

    private String room;
    private String lightstatus;
    private int light;

    public RoomContextState(String room, String lightstatus, int light) {
        super();
        this.room = room;
        this.lightstatus = lightstatus;
        this.light = light;
    }

    public String getRoom() {
        return this.room;
    }

    public String getLightStatus() {
        return this.lightstatus;
    }

    public int getLight() {
        return this.light;
    }

    public void setLight(String lightstatus) {
        this.lightstatus = lightstatus;
    }

    public void setLight(int light) {
        this.light = light;
    }
}