package com.invest.Gui.listener.sellInstrument;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.SellInstrumentFrame;
import com.invest.Gui.frames.UserFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SellInstrumentActionListener extends SellInstrumentRequestCreator implements ActionListener {

    private SellInstrumentFrame frame;

    public SellInstrumentActionListener(SellInstrumentFrame frame) {
        super(frame);
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserDto userDto = frame.getUserDto();
        boolean isSold = createPutRequest();
        if (isSold) {
            reloadFrame(userDto);
        }
    }

    private void reloadFrame(UserDto userDto) {
        frame.setVisible(false);
        UserFrame newUserFrame = new UserFrame(userDto);
        newUserFrame.openUserFrame();
        frame.getUserFrame().closeAllFrames();
    }

}