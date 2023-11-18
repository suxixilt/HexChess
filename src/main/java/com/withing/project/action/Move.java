package com.withing.project.action;

import java.io.*;

public class Move implements Serializable {
    private static final long serialVersionUID = -7439386690818203133L;
    private int x;
    private int y;
    private byte team;
    private int moveNumber;

    public Move(int x, int y, byte team, int moveNumber) {
        this.setX(x);
        this.setY(y);
        this.setTeam(team);
        this.setMoveNumber(moveNumber);

    }

    public int getX() {
        return x;
    }

    protected void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    protected void setY(int y) {
        this.y = y;
    }

    public byte getTeam() {
        return team;
    }

    protected void setTeam(byte team) {
        this.team = team;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }
}