package com.systema.maze.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by vssharma on 13/01/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentStatus {


    private boolean east;
    private boolean north;
    private boolean south;
    private boolean west;

    public CurrentStatus() {
    }

    public boolean isNorth() {
        return north;
    }

    public void setNorth(boolean north) {
        this.north = north;
    }

    public boolean isSouth() {
        return south;
    }

    public void setSouth(boolean south) {
        this.south = south;
    }

    public boolean isWest() {
        return west;
    }

    public void setWest(boolean west) {
        this.west = west;
    }

    public boolean isEast() {

        return east;
    }

    public void setEast(boolean east) {
        this.east = east;
    }
}
