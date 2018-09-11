package com.invest.Gui.frames;

import com.invest.Gui.tables.StatisticsTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StatisticsFrame extends JFrame {

    protected StatisticsFrame(String title, Long userId, boolean visibility) throws HeadlessException {
        super(title);
        createStatisticsFrame(userId, visibility, this);
    }

    private void createStatisticsFrame(Long userId, boolean visibility, StatisticsFrame statisticsFrame) {
        statisticsFrame.setSize(800, 600);
        statisticsFrame.setLocation(300,200);

        StatisticsTable statisticsTable = new StatisticsTable();
        JTable table = statisticsTable.showTable(userId);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JButton close = new JButton("Close");
        close.addActionListener(new CloseButtonActionListener());

        statisticsFrame.getContentPane().add(scrollPane);
        statisticsFrame.getContentPane().add(BorderLayout.SOUTH, close);

        statisticsFrame.setVisible(visibility);
    }

    class CloseButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }

}
