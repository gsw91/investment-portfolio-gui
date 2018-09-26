package com.invest.Gui.tables;

import com.invest.Gui.config.ServiceConfig;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticsTable extends AbstractTableModel implements TableGenerator {

    private String[] columnNames = { "Name", "Buy", "Date", "Quantity", "Sell", "Date", "Result", "Rate [%]", "Duration [days]"};
    private Object[][] data;

    @Override
    public JTable createTable(Long userId) {
        setData(connectToDatabase(userId));
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

    private List<StatisticsData> connectToDatabase(Long userId) {
        String[] params = setParams("userId");
        String[] values = setValues(String.valueOf(userId));
        List list = connectToDatabaseWithParams(ServiceConfig.STATS_USER, "statistics", params, values);
        List<StatisticsData>statisticsDataList = new ArrayList<>();
        for(Object o: list) {
            statisticsDataList.add((StatisticsData)o);
        }
        return statisticsDataList;
    }

    @Override
    public List<StatisticsData> setGeneratedDate(List<String> transformedResponseList) {
        List<StatisticsData> stats = new ArrayList<>();
        int modulo = transformedResponseList.size() % 11;
        if (modulo == 0) {
            int quantity = transformedResponseList.size();
            for (int i = 0; i < quantity; i += 11) {
                stats.add(new StatisticsData(
                        transformedResponseList.get(i + 2),
                        Double.valueOf(transformedResponseList.get(i + 3)),
                        LocalDate.parse(transformedResponseList.get(i + 4)),
                        Long.valueOf(transformedResponseList.get(i + 5)),
                        Double.valueOf(transformedResponseList.get(i + 6)),
                        LocalDate.parse(transformedResponseList.get(i + 7)),
                        transformedResponseList.get(i + 8),
                        Double.valueOf(transformedResponseList.get(i + 9)),
                        Long.valueOf(transformedResponseList.get(i + 10))
                ));
            }
        }
        return stats;
    }

    @Override
    public List<String> transformResponse(String[] array) {
        ArrayList<String> list = new ArrayList<>();
        if(array!=null) {
            for (String i : array) {
                String[] nextArray = i.split(":");
                if (nextArray.length == 2) {
                    list.add(nextArray[1]);
                }
            }
        }
        return list;
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
