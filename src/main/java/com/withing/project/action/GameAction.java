package com.withing.project.action;

import com.withing.project.UI.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.math.*;
import java.security.*;


public class GameAction {
    final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private GameAction() {
    }

    public static synchronized boolean checkWinPlayer(int team, HexUI game) {
        if (team == 1) {
            for (int i = 0; i < game.gridSize; i++) {
                if (AnyShapeButton.checkWinTeam((byte) 1, game.gridSize, i, game.gamePiece)) {
                    System.out.println("Player one wins");
                    checkedFlagReset(game);
                    String path = AnyShapeButton.findShortestPath((byte) 1, game.gridSize, i, game.gamePiece);
                    AnyShapeButton.colorPath(game.gridSize, i, path, game);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < game.gridSize; i++) {
                if (AnyShapeButton.checkWinTeam((byte) 2, i, game.gridSize, game.gamePiece)) {
                    System.out.println("Player two wins");
                    checkedFlagReset(game);
                    String path = AnyShapeButton.findShortestPath((byte) 2, i, game.gridSize, game.gamePiece);
                    AnyShapeButton.colorPath(i, game.gridSize, path, game);
                    return true;
                }
            }
        }
        return false;
    }

    public static void checkedFlagReset(HexUI game) {
        for (int x = game.gridSize - 1; x >= 0; x--) {
            for (int y = game.gridSize - 1; y >= 0; y--) {
                game.gamePiece[x][y].checkedflage = false;
            }
        }
    }

    public static void setPiece(Point p, HexUI game) {
        try {
            getPlayer(game.currentPlayer, game).setMove(new GameAction(), p);
        } catch (NullPointerException e) {
            // 捕获 NullPointerException 异常并显示错误消息对话框
            JOptionPane.showMessageDialog(HexUI.frame, "请选择先后手！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }



    private static void setTeam(byte t, int x, int y, HexUI game) throws IOException {
        game.moveList.makeMove(x, y, t, game.moveNumber,game);
        game.gamePiece[x][y].setTeam(t, game);
        game.moveNumber++;
        //	game.board.postInvalidate();
    }

    public static boolean makeMove(PlayingEntity player, byte team, Point hex, HexUI game) throws IOException {
        if (player != null && game.gamePiece[hex.x][hex.y].getTeam() == 0) {
            setTeam(team, hex.x, hex.y, game);
            return true;
        } else if (player != null && game.moveNumber == 2 && game.gamePiece[hex.x][hex.y].getTeam() == 1) {//Swap rule
            if (game.swap) {
                setTeam(team, hex.x, hex.y, game);
                return true;
            }
        }
        return false;
    }

    public static String insert(String text, String name) {
        String inserted = text.replaceAll("#", name);
        return inserted;
    }

    public static PlayingEntity getPlayer(int i, HexUI game) {
        if (i == 1) {
            return game.player1;
        } else if (i == 2) {
            return game.player2;
        } else {
            return null;
        }
    }

    public static String pointToString(Point p, HexUI game) {
        if (game.moveNumber == 2 && game.moveList.thisMove.equals(game.moveList.nextMove.thisMove)) return "SWAP";
        String str = "";
        str += alphabet.charAt(p.y);
        str += (p.x + 1);
        return str;
    }

    public static Point stringToPoint(String str, HexUI game) {
        if (game.moveNumber == 1 && str.equals("SWAP")) return new Point(-1, -1);
        if (str.equals("SWAP")) return new Point(game.moveList.thisMove.getX(), game.moveList.thisMove.getY());
        int x = Integer.parseInt(str.substring(1)) - 1;
        char y = str.charAt(0);

        return new Point(x, alphabet.indexOf(y));
    }

    public static String md5(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(), 0, s.length());
            String hash = new BigInteger(1, digest.digest()).toString(16);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}