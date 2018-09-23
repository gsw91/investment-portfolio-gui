package com.invest.Gui.frames;

import com.invest.Gui.listener.common.ShowHideActionListener;
import com.invest.Gui.tables.QuotationsTable;

import javax.swing.*;
import java.awt.*;

class QuotationsFrame extends JFrame {

    private JScrollPane scrollPane;
    private JButton close;

    protected QuotationsFrame() {
        createQuotationsFrame();
    }

    private void createQuotationsFrame() {
        configureComponents();
        installListenersInComponents();
        configureFrame();
    }

    private void configureComponents() {
        QuotationsTable quotationsTable = new QuotationsTable();
        JTable table = quotationsTable.createTable(0L);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        close = new JButton("Close");
    }

    private void installListenersInComponents() {
        close.addActionListener(new ShowHideActionListener(this, ShowHideActionListener.CLOSE));
    }

    private void configureFrame() {
        this.setTitle("Current quotations");
        this.setSize(800, 600);
        this.setLocation(300,200);
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(BorderLayout.SOUTH, close);
        this.setVisible(false);
    }

}
