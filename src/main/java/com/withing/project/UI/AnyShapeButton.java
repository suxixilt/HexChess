package com.withing.project.UI;

import javax.swing.*;
import java.awt.*;

public class AnyShapeButton extends JButton {
    public static int RorB = 0;
    static int side = 20;
    static double sq3 = Math.sqrt(3);
    static int[] y = {side / 2, side * 3 / 2, 2 * side, side * 3 / 2, side / 2, 0};
    static int[] x = {0, 0, (int) (side / 2 * sq3), (int) (side * sq3), (int) (side * sq3), (int) (side * sq3 / 2)};
    public int x1;
    public int y1;
    public boolean checkedflage = false;
    Polygon shape;
    Color curColor = Color.WHITE;
    int nflag = 0;
    int teamNumber = 0; // 1 is left-right, 2 is top-down
    private Color objectColor = Color.WHITE;

    public AnyShapeButton(Polygon p) {
        init(p);
    }

    public AnyShapeButton(int[] x, int[] y) {
        Polygon p = new Polygon(x, y, x.length);
        init(p);
    }

    public AnyShapeButton() {
        Polygon p = new Polygon(x, y, x.length);
        init(p);
    }


    public static boolean checkWinTeam(byte team, int x, int y, AnyShapeButton[][] gamePeace) { //used for checking victory condition
        if (y < gamePeace.length && x - 1 >= 0
                && gamePeace[x - 1][y].checkpiece(team, x - 1, y, gamePeace)) {
            return true;
        }
        if (y < gamePeace.length && x + 1 < gamePeace.length
                && gamePeace[x + 1][y].checkpiece(team, x + 1, y, gamePeace)) {
            return true;
        }
        if (x < gamePeace.length && y - 1 >= 0
                && gamePeace[x][y - 1].checkpiece(team, x, y - 1, gamePeace)) {
            return true;
        }
        if (x < gamePeace.length && y + 1 < gamePeace.length
                && gamePeace[x][y + 1].checkpiece(team, x, y + 1, gamePeace)) {
            return true;
        }
        if (y + 1 < gamePeace.length
                && x - 1 >= 0
                && gamePeace[x - 1][y + 1].checkpiece(team, x - 1, y + 1,
                gamePeace)) {
            return true;
        }
        if (y - 1 < gamePeace.length
                && x + 1 < gamePeace.length
                && y - 1 >= 0
                && gamePeace[x + 1][y - 1].checkpiece(team, x + 1, y - 1,
                gamePeace)) {
            return true;
        }


        return false;
    }

    public static String findShortestPath(byte team, int x, int y, AnyShapeButton[][] gamePeace) { //used for checking victory condition
        if (checkSpot(team, x, y)) {
            return "";
        }
        String[] allPath = new String[6];
        if (y < gamePeace.length && x - 1 >= 0) {
            allPath[0] = gamePeace[x - 1][y].checkpieceShort(team, x - 1, y, gamePeace);
        }
        if (y < gamePeace.length && x + 1 < gamePeace.length) {
            allPath[1] = gamePeace[x + 1][y].checkpieceShort(team, x + 1, y, gamePeace);

        }
        if (x < gamePeace.length && y - 1 >= 0) {
            allPath[2] = gamePeace[x][y - 1].checkpieceShort(team, x, y - 1, gamePeace);
        }
        if (x < gamePeace.length && y + 1 < gamePeace.length) {
            allPath[3] = gamePeace[x][y + 1].checkpieceShort(team, x, y + 1, gamePeace);
        }
        if (y + 1 < gamePeace.length
                && x - 1 >= 0) {
            allPath[4] = gamePeace[x - 1][y + 1].checkpieceShort(team, x - 1, y + 1,
                    gamePeace);
        }

        if (y - 1 < gamePeace.length
                && x + 1 < gamePeace.length
                && y - 1 >= 0) {
            allPath[5] = gamePeace[x + 1][y - 1].checkpieceShort(team, x + 1, y - 1,
                    gamePeace);
        }
        int dir = findShortestString(allPath, 0, 5);
        if (allPath[dir] == null || allPath[dir] == "null") {
            return null;
        }
        switch (dir) {
            //ud=y-1 & x+1  dd = y+1 & x-1  uy=y-1 dy=y+1 lx=x-1 rx=x+1
            case 0:
                return "lx" + allPath[0];
            case 1:
                return "rx" + allPath[1];
            case 2:
                return "uy" + allPath[2];
            case 3:
                return "dy" + allPath[3];
            case 4:
                return "dd" + allPath[4];
            case 5:
                return "ud" + allPath[5];
        }
        return null;
    }

    static int findShortestString(String[] paths, int lo, int hi) { //used for checking victory condition
        if ((lo == hi)) {
            return hi;
        }
        int temp = findShortestString(paths, lo + 1, hi);
        return stringL(paths[lo]) < stringL(paths[temp]) ? lo : temp;

    }

    private static int stringL(String temp) { //used for checking victory condition
        if (temp == null) {
            return Integer.MAX_VALUE;
        } else {
            return temp.length();
        }

    }

    public static void colorPath(int x, int y, String path, HexUI game) {
        while (path != null && path.length() != 0) {
            switch (posDir.valueOf(path.substring(0, 2))) {
                //ud=y-1 & x+1  dd = y+1 & x-1  uy=y-1 dy=y+1 lx=x-1 rx=x+1
                case lx:
                    x -= 1;
                    break;
                case rx:
                    x += 1;
                    break;
                case uy:
                    y -= 1;
                    break;
                case dy:
                    y += 1;
                    break;
                case dd:
                    y += 1;
                    x -= 1;
                    break;
                case ud:
                    y -= 1;
                    x += 1;
                    break;
            }
            game.gamePiece[x][y].setColor(Color.GREEN);
            path = path.substring(2, path.length());
        }
//        System.out.println("done");
    }

    public static boolean checkSpot(byte team, int x, int y) {
        if (team == 1 && x == 0) {
            return true;
        }
        if (team == 2 && y == 0) {
            return true;
        }
        return false;
    }

    private void init(Polygon p) {
        shape = p;
        setOpaque(false);
        setPreferredSize();
        setBorder(null);
    }

    public void setPreferredSize() {
        Rectangle b = shape.getBounds();
        setPreferredSize(new Dimension(b.width, b.height));
    }

    public void SetRed() {
        curColor = Color.red;
        repaint();
    }

    public void SetBlue() {
        curColor = Color.blue;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(curColor);
        g.fillPolygon(shape);
    }

    @Override
    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }

    public void setTeam(byte t, HexUI game) {
        teamNumber = t;
        if (teamNumber == 1)
            SetRed();
        else if (teamNumber == 2)
            SetBlue();
        else {
            this.curColor = Color.white;
            repaint();
        }
    }

    public int getTeam() {
        return teamNumber;
    }

    public boolean checkpiece(byte team, int x, int y, AnyShapeButton[][] gamePeace) { //used for checking victory conditions
        if (team == teamNumber && !checkedflage) {
            checkedflage = !checkedflage;
            if (checkSpot(team, x, y) || checkWinTeam(team, x, y, gamePeace)) {
//				this.curColor = Color.YELLOW;
//				this.repaint();
                return true;
            }
        }
        return false;
    }

    public String checkpieceShort(byte team, int x, int y, AnyShapeButton[][] gamePeace) { //used for checking victory condition
        if (team == teamNumber && !checkedflage) {
            checkedflage = true;
            String tempHolder = findShortestPath(team, x, y, gamePeace);
            checkedflage = false;
            if (tempHolder != null) {
                return tempHolder;
            }
            checkedflage = false;
        }

        return null;

    }

    public Color getColor() {
        return objectColor;
    }

    public void setColor(Color c) {
        objectColor = c;
    }

    private enum posDir {
        lx, rx, uy, dy, dd, ud

    }

}
