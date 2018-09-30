package com.invest.Gui.listener.addInstrument;

import com.invest.Gui.dto.UserDto;
import com.invest.Gui.frames.*;

import java.awt.event.*;

public class BuyInstrumentActionListener extends BuyInstrumentRequestCreator implements ActionListener {

    private AddInstrumentFrame frame;

    public BuyInstrumentActionListener(AddInstrumentFrame frame) {
        super(frame);
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserDto userDto = frame.getUserDto();
        sendPostRequest();
        if (BuyInstrumentRequestCreator.IS_INSTRUMENT_ADDED) {
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