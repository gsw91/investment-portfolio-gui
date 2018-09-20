package com.invest.Gui.frames;

import com.invest.Gui.listener.common.CloseButtonActionListener;
import com.invest.Gui.tables.QuotationsTable;

import javax.swing.*;
import java.awt.*;

public class QuotationsFrame extends JFrame {

    private String serverUrl;
    private JScrollPane scrollPane;
    private JButton close;

    public QuotationsFrame(String serverUrl) {
        this.serverUrl = serverUrl;
        createQuotationsFrame();
    }

    private void createQuotationsFrame() {
        configureComponents();
        installListenersInComponents();
        configureFrame();
    }

    private void configureComponents() {
        QuotationsTable quotationsTable = new QuotationsTable(serverUrl);
        JTable table = quotationsTable.showTable();
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        close = new JButton("Close");
    }

    private void installListenersInComponents() {
        close.addActionListener(new CloseButtonActionListener(this, CloseButtonActionListener.CLOSE));
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
