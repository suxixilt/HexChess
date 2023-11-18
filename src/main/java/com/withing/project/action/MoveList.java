package com.withing.project.action;

import com.withing.project.UI.HexUI;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MoveList implements Serializable {
    private static final long serialVersionUID = 1L;
    public Move thisMove;
    public MoveList nextMove;

    public MoveList() {
    }

    public MoveList(int x, int y, byte teamNumber, long time, int moveNumber) {
        thisMove = new Move(x, y, teamNumber, moveNumber);
    }

    public MoveList(MoveList oldMove, int x, int y, byte teamNumber, int moveNumber) {
        thisMove = new Move(x, y, teamNumber, moveNumber);
        nextMove = oldMove;
    }

    public MoveList(MoveList oldMove, Move thisMove) {
        this.thisMove = thisMove;
        nextMove = oldMove;
    }

    public Move getmove() {
        return thisMove;
    }

    public void makeMove(int x, int y, byte teamNumber, int moveNumber, HexUI hex) throws IOException {
        // team: 1 2
//       try {
//           String path = openFile(hex);
//           FileWriter writer = new FileWriter(path, true);
//           File file = new File(path);
//           if (file.length() == 0) {
//               writeFirst(path);
//           }
//           String str = ((teamNumber == 1 ? "R(" : "B(" )+ (char)(y + 65) + "," + (11 -x) + ")");
//           writer.write(str + ";");
//           // 关闭 FileWriter
//           writer.close();
//       } catch (Exception e) {
//           e.printStackTrace();
//       }
        nextMove = new MoveList(nextMove, thisMove);
        thisMove = new Move(x, y, teamNumber, moveNumber);
    }

    public static String getCurrentTime(long timestamp) {
        // 创建一个SimpleDateFormat对象，指定所需的日期时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        // 使用SimpleDateFormat将时间戳转换为指定格式的日期时间字符串
        return sdf.format(new Date(timestamp));
    }

    public static String getCurrentTime1(long timestamp) {
        // 创建一个SimpleDateFormat对象，指定所需的日期时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

        // 使用SimpleDateFormat将时间戳转换为指定格式的日期时间字符串
        return sdf.format(new Date(timestamp));
    }

//    public static String openFile(HexUI hex) {
//        // 构建输出文件的相对路径
//        String outputPath = System.getProperty("user.dir") + File.separator + "output";
//        // 获取当前时间作为文件名
//        String fileName = getCurrentTime(hex.timeStamp) + " HexChess.txt";
//        // 创建文件夹（如果不存在）
//        File outputFolder = new File(outputPath);
//        if (!outputFolder.exists()) {
//            outputFolder.mkdirs();
//        }
//        // 构建完整的文件路径
//        // 创建 FileWriter 并指示创建文件
//        return outputPath + File.separator + fileName;
//    }

//    public void writeFirst(String path) {
//        try {
//            FileWriter writer = new FileWriter(path, true);
//            String str = "{[HEX][辽大][沈航][后手胜][" + getCurrentTime1(System.currentTimeMillis()) +" 辽大][2023 \nCCGC];";
//            writer.write(str);
//            writer.close();
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
//    }

}