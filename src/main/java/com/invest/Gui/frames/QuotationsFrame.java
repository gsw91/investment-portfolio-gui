package com.invest.Gui.frames;

import com.invest.Gui.tables.QuotationsTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class QuotationsFrame extends JFrame {

    protected QuotationsFrame(boolean visibility) throws HeadlessException {
        this.createQuotationsFrame(visibility);
    }

    private QuotationsFrame getFrame() {
        return this;
    }

    private void createQuotationsFrame(boolean visibility) {
        this.setTitle("Current quotations");
        this.setSize(800, 600);
        this.setLocation(300,200);

        QuotationsTable quotationsTable = new QuotationsTable();
        JTable table = quotationsTable.showTable();
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JButton close = new JButton("Close");
        close.addActionListener(new CloseButtonActionListener());

        this.getContentPane().add(scrollPane);
        this.getContentPane().add(BorderLayout.SOUTH, close);

        this.setVisible(visibility);
    }

    class CloseButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getFrame().dispose();
        }
    }

}
