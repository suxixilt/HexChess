package com.withing.project.UI;

import com.withing.project.AI.*;
import com.withing.project.action.*;
import com.withing.project.utils.NoException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.*;

/***
 * @author computer application
 * @date 2023.09.25
 */
public class HexUI {
    public final static int SIDE = 11;
    public static JFrame frame;
    public static JPanel panel;
    public static JMenuBar menuBar;
    public final AnyShapeButton[][] gamePiece;
    public final int gridSize;
    public final boolean swap;
    public int moveNumber;
    public MoveList moveList;
    // 当前玩家
    public int currentPlayer;
    public PlayingEntity player2;
    public boolean gameOver = false;
    public PlayingEntity player1;
    // 玩家类型 0 人类玩家 PlayerObject 1 AI AIObject
    public int player1Type = 0;
    public int player2Type = 1;
    private boolean game = true;

    public long timeStamp;
    public boolean gameRunning = false;

    private Thread gameThread;

    public static boolean start = false;

    public static JMenuItem buildTree;

    public void setPlayer1Type(int player1Type) {
        this.player1Type = player1Type;
    }

    public void setPlayer2Type(int player2Type) {
        this.player2Type = player2Type;
    }

    // 设置玩家与AI
    public void setPlayer(int x){
        if (x == 1) {
            setPlayer1Type(1);
            setPlayer2Type(0);
        }
    }


    // gridSize 表示棋盘大小，swap 表示是否进行玩家交换
    HexUI(int gridSize, boolean swap) {
        this.gridSize = gridSize;
        // 创建棋盘上的按钮数组
        gamePiece = new AnyShapeButton[gridSize][gridSize];
        // 初始化棋盘上的按钮
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gamePiece[i][j] = new AnyShapeButton();
                gamePiece[i][j].x1 = i;
                gamePiece[i][j].y1 = j;
                gamePiece[i][j].addActionListener(new MouseAction(i, j, this));
            }
        }
        // 初始化是否进行玩家交换
        this.swap = swap;
        // 初始化移动编号
        moveNumber = 1;
        // 创建移动列表
        moveList = new MoveList();
        // 初始化当前玩家
        currentPlayer = 1;
        // 设置游戏状态为进行中
        game = true;
        // 初始化游戏结束状态
        gameOver = false;
        frame = new JFrame("Lnu-HexChess");
        panel = new JPanel();
        panel.setLayout(null);
        this.timeStamp = System.currentTimeMillis();
        // 初始化棋盘上的按钮的位置和大小
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                // setBounds(坐标x，坐标y，矩形长，矩形宽)j为横向计数，i为纵向计数
                gamePiece[i][j].setBounds(30 + 38 * j + 19 * i, 30 + 34 * i, 34, 40);
                panel.add(gamePiece[i][j]);
            }
        }
        JLabel text[] = new JLabel[11];
        text[0] = new JLabel("A");
        text[1] = new JLabel("B");
        text[2] = new JLabel("C");
        text[3] = new JLabel("D");
        text[4] = new JLabel("E");
        text[5] = new JLabel("F");
        text[6] = new JLabel("G");
        text[7] = new JLabel("H");
        text[8] = new JLabel("I");
        text[9] = new JLabel("J");
        text[10] = new JLabel("K");
        for (int i = 0; i < SIDE; i++) {
            text[i].setBounds(40 + 38 * i, 5, 34, 40);
            panel.add(text[i]);
        }
        JLabel text2[] = new JLabel[11];
        for (int i = 0; i < SIDE; i++) {
            text2[i] = new JLabel("" + (SIDE - i ));
            text2[i].setBounds(10 + 19 * i, 30 + 34 * i, 34, 40);
            panel.add(text2[i]);
        }
        //菜单***************************************
        menuBar = new JMenuBar();
        JMenu Operate = new JMenu("操作");

        // 创建 "先手" 菜单项并绑定事件
        JMenuItem firstPlayerItem = new JMenuItem("先手");
        firstPlayerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (gameRunning) {
                        throw new NoException();
                    }
                } catch (NoException n) {
                    JOptionPane.showMessageDialog(null, "操作好像违规了Ovo，重新开始游戏吧！", "错误", JOptionPane.ERROR_MESSAGE);
                }
                // 处理先手事件的代码
                System.out.println("玩家先行\t" + MoveList.getCurrentTime1(timeStamp));
                HexUI.this.player1Type = 0;
                HexUI.this.player2Type = 1;
                setPlayer1(HexUI.this);
                setPlayer2(HexUI.this);
                start = true;
                HexUI.this.startGame();
            }
        });

        // 创建 "后手" 菜单项并绑定事件
        JMenuItem secondPlayerItem = new JMenuItem("后手");
        secondPlayerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (gameRunning) {
                        throw new NoException();
                    }
                } catch (NoException n) {
                    JOptionPane.showMessageDialog(null, "操作好像违规了Ovo，重新开始游戏吧！", "错误", JOptionPane.ERROR_MESSAGE);
                }
                // 处理后手事件的代码
                System.out.println("AI先行\t\t" + MoveList.getCurrentTime1(timeStamp));
                HexUI.this.player1Type = 1;
                HexUI.this.player2Type = 0;
                setPlayer1(HexUI.this);
                setPlayer2(HexUI.this);
                start = true;
                HexUI.this.startGame();
            }
        });

        // 创建 "新游戏" 菜单项并绑定事件
        JMenuItem newGameItem = new JMenuItem("新游戏");
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理新游戏事件的代码
                System.out.println("海克斯棋 启动！");
                restart(HexUI.this);
            }
        });

        // 将菜单项添加到 "操作" 菜单中
        Operate.add(firstPlayerItem);
        Operate.add(secondPlayerItem);
        Operate.add(newGameItem);

        menuBar.add(Operate);
        frame.setJMenuBar(menuBar);
        //*******************************************
        frame.add(panel);
        // 设置图标
        String iconPath = System.getProperty("user.dir");
        ImageIcon icon = new ImageIcon(iconPath + "/src/main/java/com/withing/project/img/icon.png");
        frame.setIconImage(icon.getImage());
        // 基于side的值设置窗口的大小
        frame.setSize(80 + 38 * SIDE + 19 * SIDE, 110 + 34 * SIDE);
        frame.setVisible(true);
        // 用户可调整窗口大小
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口居中显示
        frame.setLocationRelativeTo(null);

    }

    public static void setPlayer1(HexUI game) {
        // 根据玩家的类型创建不同的实例
        if (game.player1Type == 0) {
            game.player1 = new PlayerObject(1, game);
        } else if (game.player1Type == 1) {
            game.player1 = new AIObject(1, game);
        }
    }

    public static void setPlayer2(HexUI game) {
        if (game.player2Type == 0) {
            game.player2 = new PlayerObject(2, game);
        } else if (game.player2Type == 1) {
            game.player2 = new AIObject(2, game);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 用于初始化游戏界面和相关属性
        HexUI hex_ui = new HexUI(11, true);
    }

    public void restart(HexUI hexUI) {
        hexUI.game = false;
        // 停止和销毁旧的游戏线程
        stopGame();
        if (frame != null) {
            frame.dispose(); // 关闭旧窗口
            frame = null;
        }
        // 创建新游戏对象及其相关GUI元素
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HexUI newGame = new HexUI(11, true);

            }
        });
    }

    private void startGame() {
        if (!gameRunning) {
            gameRunning = true;
            gameThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (game && start) {
                        if (!checkForWinner()) {
                            GameAction.getPlayer((currentPlayer % 2) + 1, HexUI.this);
                            if (GameAction.getPlayer(currentPlayer, HexUI.this) == null) {
                                System.out.println("为空");
                            }
                            try {
                                GameAction.getPlayer(currentPlayer, HexUI.this).getPlayerTurn();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        currentPlayer = (currentPlayer % 2) + 1;
                    }
                    gameRunning = false;

//                    System.out.println("Thread died");
//                    String filePath = MoveList.openFile(HexUI.this);
//                    try {
//                        Path res = Paths.get(filePath);
//                        String content = null;
//                        try {
//                            content = new String(Files.readAllBytes(res));
//                        } catch (NoSuchFileException e) {
////                            System.out.println("操作好像违规了Ovo 试试别的操作吧！");
//                        }
//                        if (content!=null) {
//                            // 获取文件长度
//                            int fileLength = content.length();
//                            int lastIndex = fileLength - 1;
//                            String replacedContent = content.substring(0, lastIndex) + "}";
//                            // 写回到文件
//                            Files.write(res, replacedContent.getBytes());
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            });
            gameThread.start();
        }
    }


    // 停止和销毁游戏线程的方法
    private void stopGame() {
        if (gameThread != null) {
            gameRunning = false;
            gameThread.interrupt(); // 中断游戏线程
            gameThread = null;
        }
    }
    private boolean checkForWinner() {
        GameAction.checkedFlagReset(this);
        if (GameAction.checkWinPlayer(1, this)) {
            game = false;
            gameOver = true;
            player1.win();
            player2.lose();

        } else if (GameAction.checkWinPlayer(2, this)) {
            game = false;
            gameOver = true;
            player1.lose();
            player2.win();

        }

        return gameOver;
    }
}


