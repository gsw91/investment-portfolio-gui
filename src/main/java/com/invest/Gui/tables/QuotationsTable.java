package com.invest.Gui.tables;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuotationsTable extends AbstractTableModel {

    private static Logger LOGGER = Logger.getLogger(QuotationsTable.class);
    private String[] columnNames = { "Index", "Current price", "Time"};
    private Object[][] data;
    private String serverUrl;

    public QuotationsTable(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public JTable showTable() {
        try {
            setData(connectToDatabase());
            LOGGER.info("Quotations table has been created");
        } catch (IOException e) {
            LOGGER.error("Quotations table creation failed");
            setData(new ArrayList<>());
        }
        return new JTable(data, columnNames);
    }

    private void setData(List<QuotationsData> quotationsData) {
        data = new Object[quotationsData.size()][3];
        for (int i=0; i<quotationsData.size(); i++) {
            data[i][0] = quotationsData.get(i).getIndex();
            data[i][1] = quotationsData.get(i).getPrice();
            data[i][2] = quotationsData.get(i).getServerActualization();
        }
    }

    private List<QuotationsData> connectToDatabase() throws IOException {
        List<QuotationsData> quotations = new ArrayList<>();
        String request = serverUrl + "/v1/share/all";
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
            allResponse = allResponse.replace("\"", "");
            String[] array = allResponse.split(",");

            ArrayList<String> list = new ArrayList<>();
            for (String i : array) {
                String[] nextArray = i.split(":");
                if (nextArray.length == 3) {
                    list.add(nextArray[2]);
                } else if (nextArray.length == 2) {
                    list.add(nextArray[1]);
                } else if (nextArray.length == 4) {
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(nextArray[1]);
                    buffer.append(":");
                    buffer.append(nextArray[2]);
                    buffer.append(":");
                    buffer.append(nextArray[3]);
                    String datatime = buffer.toString().replace("T", " ");
                    list.add(datatime);
                }
            }
            LOGGER.info("Creating quotations table for user");

            int modulo = list.size()%4;
            if (modulo == 0) {
                int quantity = list.size();
                for(int i=0; i<quantity; i+=4) {
                    quotations.add(new QuotationsData(
                            list.get(i),
                            list.get(i+1),
                            list.get(i+2)
                    ));
                }
            }
        }
        return quotations;
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
