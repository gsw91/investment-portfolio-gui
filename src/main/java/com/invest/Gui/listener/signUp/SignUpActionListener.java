package com.invest.Gui.listener.signUp;

import com.invest.Gui.frames.LogInFrame;
import com.invest.Gui.frames.SignUpFrame;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActionListener implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(SignUpFrame.class);

    private String serverUrl;
    private SignUpFrame signUpFrame;

    public SignUpActionListener(String serverUrl, SignUpFrame signUpFrame) {
        this.serverUrl = serverUrl;
        this.signUpFrame = signUpFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String name = signUpFrame.getLoginField().getText();
        String password = buildPassword(signUpFrame);
        String email = signUpFrame.getEmailField().getText();

        try {
            sendCreateRequest(name, password, email);
            new LogInFrame(serverUrl);
            signUpFrame.dispose();
        } catch (IOException ex) {
            LOGGER.warn("Connection refused");
        }
    }

    private String buildPassword(SignUpFrame signUpFrame) {
        char[] chars = signUpFrame.getPasswordField().getPassword();
        StringBuilder builder = new StringBuilder();
        for(char i: chars) {
            builder.append(i);
        }
        return builder.toString();
    }

    private void sendCreateRequest(String login, String password, String email) throws IOException {
        String request = serverUrl + "/v1/user/create";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");

        JSONObject cred = new JSONObject();
        cred.put("login", login);
        cred.put("password", password);
        cred.put("email", email);

        OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream());
        wr.write(cred.toString());
        wr.flush();

        int responseCode = connection.getResponseCode();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        String allResponse = response.toString();

        if (responseCode==200 && allResponse.equals("User created")) {
            LOGGER.info("User created");
        } else {
            LOGGER.warn(allResponse);
        }
    }

}