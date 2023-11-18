package com.withing.project.action;

import com.withing.project.UI.*;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class PlayerObject implements PlayingEntity {
    private String name;
    private Color color = Color.RED;
    private int team;
    private LinkedList<Point> hex = new LinkedList<Point>();
    private HexUI game;

    public PlayerObject(int team, HexUI game) {
        color = Color.red;
        name = "Human";
        this.team = team;
        this.game = game;
    }

    @Override
    public void getPlayerTurn() throws IOException {
        while (!Thread.currentThread().isInterrupted()) {
            while (hex.size() == 0) {
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    // 检测到线程中断，可以选择处理它或退出循环
                    Thread.currentThread().interrupt(); // 重新设置线程的中断状态
                    break; // 退出循环
                }
            }
            if (Thread.currentThread().isInterrupted()) {
                break; // 如果线程已被中断，退出循环
            }
            if (hex.get(0).equals(new Point(-1, -1))) {
                hex.remove(0);
                break;
            }
            if (GameAction.makeMove(this, (byte) team, hex.get(0), game)) {
                hex.remove(0);
                break;
            }
            hex.remove(0);
        }
    }


    @Override
    public void undoCalled() {
    }

    @Override
    public void newgameCalled() {
        endMove();
    }

    @Override
    public boolean supportsUndo() {
        return true;
    }

    @Override
    public boolean supportsNewgame() {
        return true;
    }

    @Override
    public void quit() {
        endMove();
    }

    @Override
    public void win() {
        System.out.println("先手赢");
    }

    @Override
    public void lose() {
        System.out.println("先手输");
    }

    @Override
    public boolean supportsSave() {
        return false;
    }

    @Override
    public void endMove() {
        hex.add(new Point(-1, -1));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setMove(Object o, Point hex) {
        if (o instanceof GameAction && game.currentPlayer == team) {
            this.hex = new LinkedList<Point>();
            this.hex.add(hex);
        }
    }

}