package com.invest.Gui.frames;

import com.invest.Gui.dto.UserDto;

public class frames {

    public static void main(String[] args) {
        UserDto userDto = new UserDto(111L, "gw91", "gw91", "g.wojcik@vp.pl");
        UserFrame userFrame = new UserFrame(userDto);
        userFrame.openUserFrame();
        SettingsFrame settingsFrame = new SettingsFrame(userFrame, userDto);
        settingsFrame.setVisible(true);
    }

}
