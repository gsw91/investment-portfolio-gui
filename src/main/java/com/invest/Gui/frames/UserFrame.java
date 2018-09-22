package com.invest.Gui.frames;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.dto.UserDto;
import com.invest.Gui.listener.common.ShowHideActionListener;
import com.invest.Gui.listener.userFrame.LogOutActionListener;
import com.invest.Gui.listener.userFrame.RefreshFrameActionListener;
import com.invest.Gui.tables.UserTable;

import javax.swing.*;
import java.awt.*;

public class UserFrame extends JFrame {

    private AddInstrumentFrame addInstrumentFrame;
    private SellInstrumentFrame sellInstrumentFrame;
    private StatisticsFrame statisticsFrame;
    private QuotationsFrame quotationsFrame;
    private SettingsFrame settingsFrame;
    private UserDto userDto;
    private JButton refreshButton;
    private JButton sellButton;
    private JButton addButton;
    private JButton statsButton;
    private JButton quotationsButton;
    private JButton logOutButton;
    private JButton settings;
    private JScrollPane scrollPane;
    private JPanel buttonsPanel;

    public UserFrame(UserDto userDto) {
        this.userDto = userDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void openUserFrame() {
        configureComponents();
        configurePanels();
        configureOtherFrames();
        installListenersInComponents();
        configureFrame();
    }

    private void configureComponents() {
        refreshButton = new JButton("refresh");
        addButton = new JButton("add");
        sellButton = new JButton("sell");
        statsButton = new JButton("stats");
        quotationsButton = new JButton("quotations");
        settings = new JButton("settings");
        logOutButton = new JButton("log out");
        UserTable userTable = new UserTable();
        JTable table = userTable.showTable(userDto.getId());
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
    }

    private void configurePanels() {
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 8));
        buttonsPanel.add(new JLabel("  User: " + userDto.getLogin()));
        buttonsPanel.add(refreshButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(sellButton);
        buttonsPanel.add(statsButton);
        buttonsPanel.add(quotationsButton);
        buttonsPanel.add(settings);
        buttonsPanel.add(logOutButton);
    }

    private void configureOtherFrames() {
        addInstrumentFrame = new AddInstrumentFrame(this, userDto);
        sellInstrumentFrame = new SellInstrumentFrame(this, userDto);
        statisticsFrame = new StatisticsFrame(userDto);
        quotationsFrame = new QuotationsFrame();
        settingsFrame = new SettingsFrame(this, userDto);
    }

    private void installListenersInComponents() {
        refreshButton.addActionListener(new RefreshFrameActionListener(this));
        addButton.addActionListener(new ShowHideActionListener(addInstrumentFrame, ShowHideActionListener.VISIBLE));
        sellButton.addActionListener(new ShowHideActionListener(sellInstrumentFrame, ShowHideActionListener.VISIBLE));
        statsButton.addActionListener(new ShowHideActionListener(statisticsFrame, ShowHideActionListener.VISIBLE));
        quotationsButton.addActionListener(new ShowHideActionListener(quotationsFrame, ShowHideActionListener.VISIBLE));
        settings.addActionListener(new ShowHideActionListener(settingsFrame, ShowHideActionListener.VISIBLE));
        logOutButton.addActionListener(new LogOutActionListener(this));
    }

    private void configureFrame() {
        this.setTitle("User panel");
        this.setSize(800,600);
        this.setLocation(400,150);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().add(BorderLayout.CENTER, scrollPane);
        this.getContentPane().add(BorderLayout.SOUTH, buttonsPanel);
        this.setVisible(true);
    }

    public void closeAllFrames() {
        this.dispose();
        settingsFrame.dispose();
        addInstrumentFrame.dispose();
        quotationsFrame.dispose();
        statisticsFrame.dispose();
        sellInstrumentFrame.dispose();
    }

}
