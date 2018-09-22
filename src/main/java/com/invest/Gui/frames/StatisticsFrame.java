package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.listener.common.ShowHideActionListener;
import com.invest.Gui.tables.StatisticsTable;

import javax.swing.*;
import java.awt.*;

class StatisticsFrame extends JFrame {

    private JScrollPane scrollPane;
    private JButton close;

    protected StatisticsFrame(UserDto userDto) throws HeadlessException {
        createStatisticsFrame(userDto.getId());
    }

    private void createStatisticsFrame(Long userId) {
        configureComponents(userId);
        installListenersInComponents();
        configureFrame();
    }

    private void configureComponents(Long userId) {
        StatisticsTable statisticsTable = new StatisticsTable();
        JTable table = statisticsTable.showTable(userId);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        close = new JButton("Close");
    }

    private void installListenersInComponents() {
        close.addActionListener(new ShowHideActionListener(this, ShowHideActionListener.INVISIBLE));
    }

    private void configureFrame() {
        this.setTitle("Statistics");
        this.setSize(800, 600);
        this.setLocation(300,200);
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(BorderLayout.SOUTH, close);
        this.setVisible(false);
    }

}
