package com.invest.Gui.tables;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticsTable extends AbstractTableModel {

    private static Logger LOGGER = Logger.getLogger(StatisticsTable.class);
    private String[] columnNames = { "Name", "Buy", "Date", "Quantity", "Sell", "Date", "Result", "Rate [%]", "Duration [days]"};
    private Object[][] data;

    public JTable showTable(Long userId) {
        try {
            setData(connectToDatabase(userId));
            LOGGER.info("Statistics table has been created");
        } catch (IOException e) {
            LOGGER.error("Statistics table creation failed");
            setData(new ArrayList<>());
        }
        return new JTable(data, columnNames);
    }

    private void setData(List<StatisticsData> statisticsData) {
        data = new Object[statisticsData.size()][9];
        for (int i=0; i<statisticsData.size(); i++) {
            data[i][0] = statisticsData.get(i).getIndex();
            data[i][1] = statisticsData.get(i).getBuyingPrice();
            data[i][2] = statisticsData.get(i).getBuyingDate();
            data[i][3] = statisticsData.get(i).getQuantity();
            data[i][4] = statisticsData.get(i).getSellingPrice();
            data[i][5] = statisticsData.get(i).getSellingDate();
            data[i][6] = statisticsData.get(i).getResult();
            data[i][7] = statisticsData.get(i).getReturnRate();
            data[i][8] = statisticsData.get(i).getDuration();
        }
    }

    private List<StatisticsData> connectToDatabase(Long userId) throws IOException {
        List<StatisticsData> stats = new ArrayList<>();
        String request = "https://ancient-gorge-42887.herokuapp.com/v1/stats/show?userId=" + userId;
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
                if (nextArray.length == 2) {
                    list.add(nextArray[1]);
                }
            }
            LOGGER.info("Creating statistics table for user");
            //getPricesModulo
            int modulo = list.size()%11;
            if (modulo == 0) {
                int quantity = list.size();
                for(int i=0; i<quantity; i+=11) {
                    stats.add(new StatisticsData(
                            list.get(i+2),
                            Double.valueOf(list.get(i+3)),
                            LocalDate.parse(list.get(i+4)),
                            Long.valueOf(list.get(i+5)),
                            Double.valueOf(list.get(i+6)),
                            LocalDate.parse(list.get(i+7)),
                            list.get(i+8),
                            Double.valueOf(list.get(i+9)),
                            Long.valueOf(list.get(i+10))
                    ));
                }
            }
        }

        return stats;
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
