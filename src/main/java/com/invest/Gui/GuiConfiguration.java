package com.invest.Gui;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.frames.LogInFrame;

class GuiConfiguration {

    private ServiceConfig serviceConfig = new ServiceConfig();
    private String serverUrl = serviceConfig.getServiceUrl();

    protected void run() {
        new LogInFrame(serverUrl);
    }

}
