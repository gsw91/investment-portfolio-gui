package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.tables.UserTable;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame {

    private final static Logger LOGGER = Logger.getLogger(UserFrame.class);
    private AddInstrumentFrame addInstrumentFrame;
    private SellInstrumentFrame sellInstrumentFrame;
    private StatisticsFrame statisticsFrame;
    private QuotationsFrame quotationsFrame;
    private SettingsFrame settingsFrame;
    private UserDto userDto;
    private JFrame userFrame;
    private JButton refreshButton;
    private JButton sellButton;
    private JButton addButton;
    private JButton statsButton;
    private JButton quotationsButton;
    private JButton logOutButton;
    private JButton settings;
    private String serverUrl;
    private UserTable userTable;
    private JTable table;
    private JScrollPane scrollPane;

    public UserFrame(UserDto userDto, String serverUrl) {
        this.userDto = userDto;
        this.serverUrl = serverUrl;
    }

    public void openUserFrame() {

        userFrame = new JFrame("User panel");
        userFrame.setSize(800,600);
        userFrame.setLocation(400,150);

        configureOtherFrames();
        configureButtons();

        userTable = new UserTable(serverUrl);
        table = userTable.showTable(userDto.getId());
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 8));
        buttonsPanel.add(new JLabel("  User: " + userDto.getLogin()));
        buttonsPanel.add(refreshButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(sellButton);
        buttonsPanel.add(statsButton);
        buttonsPanel.add(quotationsButton);
        buttonsPanel.add(settings);
        buttonsPanel.add(logOutButton);

        userFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        userFrame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        userFrame.getContentPane().add(BorderLayout.SOUTH, buttonsPanel);
        userFrame.setVisible(true);
    }

    private void configureOtherFrames() {
        addInstrumentFrame = new AddInstrumentFrame(this, userDto, serverUrl);

        sellInstrumentFrame = new SellInstrumentFrame(this, userDto, serverUrl);

        statisticsFrame = new StatisticsFrame(userDto.getId(), serverUrl);

        quotationsFrame = new QuotationsFrame(serverUrl);

        settingsFrame = new SettingsFrame(this, userDto, serverUrl);
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

        settings = new JButton("settings");
        settings.addActionListener(new SettingActionListener());

        logOutButton = new JButton("log out");
        logOutButton.addActionListener(new LogOutButtonActionListener());
    }

    class SettingActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            settingsFrame.setVisible(true);
        }
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
            closeAllFrames();
        }
    }

    class LogOutButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new LogInFrame(serverUrl);
            LOGGER.info("User " + userDto.getLogin() + " logged out");
            closeAllFrames();
        }
    }

    public void closeAllFrames() {
        userFrame.dispose();
        settingsFrame.dispose();
        addInstrumentFrame.dispose();
        quotationsFrame.dispose();
        statisticsFrame.dispose();
        sellInstrumentFrame.dispose();
    }

}
