package com.invest.Gui.listener.userFrame;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.UserFrame;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefreshFrameActionListener implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(RefreshFrameActionListener.class);
    private UserFrame frame;

    public RefreshFrameActionListener(UserFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LOGGER.info("Refreshing shares panel");
        reloadFrame(frame.getUserDto());
    }

    private void reloadFrame(UserDto userDto) {
        frame.setVisible(false);
        UserFrame newUserFrame = new UserFrame(userDto);
        newUserFrame.openUserFrame();
        frame.closeAllFrames();
    }

}