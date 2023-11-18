package com.withing.project.UI;

import java.awt.event.*;

class Menu implements ActionListener {
    HexUI hexUI;

    public Menu(HexUI HexUI) {
        this.hexUI = HexUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        hexUI.player1Type = 1;
        hexUI.player2Type = 0;
        System.out.println("AI先行！");

    }
}