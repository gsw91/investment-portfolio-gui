package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.tables.UserTable;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UserFrame {

    private final static Logger LOGGER = Logger.getLogger(UserFrame.class);
    private AddInstrumentFrame addInstrumentFrame;
    private SellInstrumentFrame sellInstrumentFrame;
    private StatisticsFrame statisticsFrame;
    private QuotationsFrame quotationsFrame;
    private UserDto userDto;
    private JFrame userFrame;
    private JButton refreshButton;
    private JButton sellButton;
    private JButton addButton;
    private JButton statsButton;
    private JButton quotationsButton;
    private JButton logOutButton;
    private String serverUrl;

    protected UserFrame(UserDto userDto, String serverUrl) {
        this.userDto = userDto;
        this.serverUrl = serverUrl;
    }

    protected void openUserFrame() {

        userFrame = new JFrame("User panel");
        userFrame.setSize(600,300);
        userFrame.setLocation(500,300);

        configureOtherFrames();
        configureButtons();

        UserTable userTable = new UserTable();
        JTable table = userTable.showTable(userDto.getId());
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(new JLabel("Logged in as " + userDto.getLogin() + "   "));
        buttonsPanel.add(refreshButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(sellButton);
        buttonsPanel.add(statsButton);
        buttonsPanel.add(quotationsButton);
        buttonsPanel.add(logOutButton);

        userFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        userFrame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        userFrame.getContentPane().add(BorderLayout.SOUTH, buttonsPanel);
        userFrame.setVisible(true);
    }

    private void configureOtherFrames() {
        addInstrumentFrame = new AddInstrumentFrame(userFrame, userDto, false, serverUrl);

        sellInstrumentFrame = new SellInstrumentFrame(userFrame, userDto, false, serverUrl);

        statisticsFrame = new StatisticsFrame("Statistics", userDto.getId(), false);

        quotationsFrame = new QuotationsFrame(false);
    }

    private void configureButtons() {

        refreshButton = new JButton("refresh");
        refreshButton.addActionListener(new RefreshButtonActionListener());

        addButton = new JButton("add");
        addButton.addActionListener(new AddButtonActionListener());

        sellButton = new JButton("sell");
        sellButton.addActionListener(new SellButtonActionListener());

        statsButton = new JButton("stats");
        statsButton.addActionListener(new StatsButtonActionListener());

        quotationsButton = new JButton("quotations");
        quotationsButton.addActionListener(new QuotationsButtonActionListener());

        logOutButton = new JButton("log out");
        logOutButton.addActionListener(new LogOutButtonActionListener());

    }

    class QuotationsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            quotationsFrame.setVisible(true);
        }
    }

    class StatsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            statisticsFrame.setVisible(true);
        }
    }

    class SellButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sellInstrumentFrame.setVisible(true);
        }
    }

    class AddButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addInstrumentFrame.setVisible(true);
        }
    }

    class RefreshButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LOGGER.info("Refreshing shares panel");
            UserFrame newUserFrame = new UserFrame(userDto, serverUrl);
            newUserFrame.openUserFrame();
            userFrame.dispose();
        }
    }

    class LogOutButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new LogInFrame(serverUrl);
            LOGGER.info("User " + userDto.getLogin() + " logged out");
            userFrame.dispose();
        }
    }


}
