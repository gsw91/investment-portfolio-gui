package com.invest.Gui.listener.userFrame;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.UserFrame;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefreshFrameActionListener implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(RefreshFrameActionListener.class);
    private UserFrame userFrame;

    public RefreshFrameActionListener(UserFrame userFrame) {
        this.userFrame = userFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserDto userDto = userFrame.getUserDto();
        LOGGER.info("Refreshing shares panel");
        UserFrame newUserFrame = new UserFrame(userDto);
        newUserFrame.openUserFrame();
        userFrame.closeAllFrames();
    }

}