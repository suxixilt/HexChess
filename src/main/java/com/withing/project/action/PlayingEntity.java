/**
 *
 */
package com.withing.project.action;

import java.awt.*;
import java.io.IOException;


public interface PlayingEntity {

    public void getPlayerTurn() throws IOException;

    public boolean supportsUndo();

    public void undoCalled();

    public boolean supportsNewgame();

    public void newgameCalled();

    public boolean supportsSave();

    public void quit();

    public void win();

    public void lose();

    public void endMove();

    public String getName();

    public void setName(String name);

    public Color getColor();

    public void setColor(Color color);

    /**
     * Store a point to play when its your turn
     * */
    public void setMove(Object o, Point hex);

    /**
     * Return true to announce defeat mid-game
     * */
}
