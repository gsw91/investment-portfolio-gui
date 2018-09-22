package com.invest.Gui.tables;

import com.invest.Gui.config.ServiceConfig;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserTable extends AbstractTableModel {

    private static Logger LOGGER = Logger.getLogger(UserTable.class);
    private String[] columnNames = { "Name", "Quantity", "Buy", "Now", "Value", "Change", "Result"};
    private Object[][] data;

    public UserTable() {}

    public JTable showTable(Long userId) {
        try {
            setData(connectToDatabase(userId));
            LOGGER.info("User table has been created");
        } catch (IOException e) {
            LOGGER.error("User table creation failed");
            setData(new ArrayList<>());
        }
        return new JTable(data, columnNames);
    }

    private void setData(List<UserData> userDataList) {
        data = new Object[userDataList.size()][7];
        for (int i=0; i<userDataList.size(); i++) {
            data[i][0] = userDataList.get(i).getName();
            data[i][1] = userDataList.get(i).getQuantity();
            data[i][2] = userDataList.get(i).getBuy();
            data[i][3] = userDataList.get(i).getNow();
            data[i][4] = userDataList.get(i).getValue();
            data[i][5] = userDataList.get(i).getChange();
            data[i][6] = userDataList.get(i).getResult();
        }
    }

    private List<UserData> connectToDatabase(Long userId) throws IOException {
        List<UserData> userDataList = new ArrayList<>();
        String request = ServiceConfig.SERVER_URL + ServiceConfig.SHOW_USER_INSTRUMENT + userId;
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode==200) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }

            String allResponse = response.toString();
            allResponse = allResponse.replace("[", "");
            allResponse = allResponse.replace("{", "");
            allResponse = allResponse.replace("}", "");
            allResponse = allResponse.replace("]", "");
            String[] array = allResponse.split(",");

            ArrayList<String> list = new ArrayList<>();
            for (String i : array) {
                String[] nextArray = i.split(":");
                if (nextArray.length == 2) {
                    list.add(nextArray[1]);
                }
            }
            try {
                LOGGER.info("Creating table for user");
                //getPricesModulo
                int modulo = list.size() % 6;
                if (modulo == 0) {
                    int quantity = list.size();
                    for (int i = 0; i < quantity; i += 6) {
                        userDataList.add(new UserData(
                                list.get(i + 3),
                                Long.valueOf(list.get(i + 2)),
                                BigDecimal.valueOf(Double.valueOf(list.get(i + 4))),
                                BigDecimal.valueOf(Double.valueOf(getCurrentPrice(list.get(i + 3))))
                        ));
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e1) {
                throw new IOException();
            }
        }
        return userDataList;
    }


    private String getCurrentPrice(String indexList) throws IOException, ArrayIndexOutOfBoundsException {
        String newRequest = ServiceConfig.SERVER_URL + ServiceConfig.GET_SHARE;
            String mp = newRequest + indexList.replace("\"", "");
            URL newUrl = new URL(mp);
            HttpURLConnection newConnection = (HttpURLConnection) newUrl.openConnection();
            newConnection.setRequestMethod("GET");
            int newResponseCode = newConnection.getResponseCode();
            if (newResponseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(newConnection.getInputStream()));
                String newLine;
                StringBuffer buffer = new StringBuffer();
                while ((newLine = reader.readLine()) != null) {
                    buffer.append(newLine);
                }
                String newResponse = buffer.toString();
                newResponse = newResponse.replace("[", "");
                newResponse = newResponse.replace("{", "");
                newResponse = newResponse.replace("}", "");
                newResponse = newResponse.replace("]", "");
                String[] array = newResponse.split(",");

                array = array[1].split(":");
                return array[1];
            }
        return "";
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

}
