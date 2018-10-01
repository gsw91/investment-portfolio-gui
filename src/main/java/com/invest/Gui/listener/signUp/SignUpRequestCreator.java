package com.invest.Gui.listener.signUp;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.connection.PostRequestCreator;
import com.invest.Gui.connection.RequestMethod;
import com.invest.Gui.frames.SignUpFrame;
import com.invest.Gui.frames.WarningFrame;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

class SignUpRequestCreator implements PostRequestCreator {

    private final static Logger LOGGER = Logger.getLogger(SignUpRequestCreator.class);
    public static boolean isCreated = false;
    private SignUpFrame signUpFrame;
    private String login;
    private String password;
    private String email;

    protected SignUpRequestCreator(SignUpFrame signUpFrame) {
        this.signUpFrame = signUpFrame;
    }

    @Override
    public void sendPostRequest()  {
        login = signUpFrame.getLoginField().getText();
        password = buildPassword(signUpFrame);
        email = signUpFrame.getEmailField().getText();

        try {
            String request = generateUrl(ServiceConfig.USER_CREATE);
            HttpURLConnection connection = createPostConnection(request, RequestMethod.POST);
            JSONObject json = createJson(createKeys(), createValues());
            sendJsonObject(connection, json);
            String allResponse = getResponse(connection);

            if (allResponse.equals("User created")) {
                LOGGER.info("User created");
                isCreated = true;
            } else {
                LOGGER.warn("User creation failed." + allResponse);
                isCreated = false;
                WarningFrame.openWarningFrame(allResponse);
            }
        } catch (IOException ioe) {
            LOGGER.error("Connection refused");
            WarningFrame.openWarningFrame("Connection refused");
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

    private String getResponse(HttpURLConnection connection) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        return response.toString();
    }

    @Override
    public String[] createKeys() {
        return new String[]{"login", "password", "email"};
    }

    @Override
    public Object[] createValues() {
        return new Object[]{login, password, email};
    }
}
