package com.invest.Gui.listener.userFrame;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.LogInFrame;
import com.invest.Gui.frames.UserFrame;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogOutActionListener implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(LogOutActionListener.class);
    private UserFrame userFrame;

    public LogOutActionListener(UserFrame userFrame) {
        this.userFrame = userFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserDto userDto = userFrame.getUserDto();
        new LogInFrame();
        userFrame.closeAllFrames();
        LOGGER.info("User " + userDto.getLogin() + " logged out");
    }

}
