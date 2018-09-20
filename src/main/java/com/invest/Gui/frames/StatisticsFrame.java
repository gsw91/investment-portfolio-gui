package com.invest.Gui.frames;

import com.invest.Gui.listener.common.CloseButtonActionListener;
import com.invest.Gui.tables.StatisticsTable;

import javax.swing.*;
import java.awt.*;

class StatisticsFrame extends JFrame {

    private String serverUrl;
    private JScrollPane scrollPane;
    private JButton close;

    protected StatisticsFrame(Long userId, String serverUrl) throws HeadlessException {
        this.serverUrl = serverUrl;
        createStatisticsFrame(userId);
    }

    private void createStatisticsFrame(Long userId) {
        configureComponents(userId);
        installListenersInComponents();
        configureFrame();
    }

    private void configureComponents(Long userId) {
        StatisticsTable statisticsTable = new StatisticsTable(serverUrl);
        JTable table = statisticsTable.showTable(userId);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        close = new JButton("Close");
    }

    private void installListenersInComponents() {
        close.addActionListener(new CloseButtonActionListener(this));
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
