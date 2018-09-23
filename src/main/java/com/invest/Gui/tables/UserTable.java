package com.invest.Gui.tables;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.connection.RequestMethod;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class UserTable extends AbstractTableModel implements TableGenerator {

    private static Logger LOGGER = Logger.getLogger(UserTable.class);
    private String[] columnNames = {"Name", "Quantity", "Buy", "Now", "Value", "Change", "Result"};
    private Object[][] data;

    public UserTable() {
    }

    @Override
    public JTable createTable(Long userId) {
        setData(connectToDatabase(userId));
        return new JTable(data, columnNames);
    }

    private void setData(List<UserData> userDataList) {
        data = new Object[userDataList.size()][7];
        for (int i = 0; i < userDataList.size(); i++) {
            data[i][0] = userDataList.get(i).getName();
            data[i][1] = userDataList.get(i).getQuantity();
            data[i][2] = userDataList.get(i).getBuy();
            data[i][3] = userDataList.get(i).getNow();
            data[i][4] = userDataList.get(i).getValue();
            data[i][5] = userDataList.get(i).getChange();
            data[i][6] = userDataList.get(i).getResult();
        }
    }

    private List<UserData> connectToDatabase(Long userId) {
        LOGGER.info("Creating table for user");
        String[] params = setParams("userId");
        String[] values = setValues(String.valueOf(userId));
        String request = generateUrlWithParams(ServiceConfig.SHOW_USER_INSTRUMENT, params, values);
        HttpURLConnection connection = createConnection(request, RequestMethod.GET);
        String[] array = getResponse(connection, "User");
        List<String> transformedResponseList = transformResponse(array);
        return setGeneratedDate(transformedResponseList);
    }

    private String getCurrentPrice(String indexList) {
        String[] params = setParams("name");
        String[] values = setValues(indexList);
        String url = generateUrlWithParams(ServiceConfig.GET_SHARE, params, values);
        HttpURLConnection connection = createConnection(url, RequestMethod.GET);
        String[] array = getResponse(connection, "User");
        array = array[1].split(":");
        return array[1];
    }

    @Override
    public List<UserData> setGeneratedDate(List<String> transformedResponseList) {
        List<UserData> userDataList = new ArrayList<>();
        int modulo = transformedResponseList.size() % 6;
        if (modulo == 0) {
            int quantity = transformedResponseList.size();
            for (int i = 0; i < quantity; i += 6) {
                userDataList.add(new UserData(
                        transformedResponseList.get(i + 3),
                        Long.valueOf(transformedResponseList.get(i + 2)),
                        BigDecimal.valueOf(Double.valueOf(transformedResponseList.get(i + 4))),
                        BigDecimal.valueOf(Double.valueOf(getCurrentPrice(transformedResponseList.get(i + 3))))
                ));
            }
        }
        return userDataList;
    }

    @Override
    public List<String> transformResponse(String[] array) {
        if (array!=null) {
            ArrayList<String> list = new ArrayList<>();
            for (String i : array) {
                String[] nextArray = i.split(":");
                if (nextArray.length == 2) {
                    list.add(nextArray[1]);
                }
            }
            return list;
        } else {
            return new ArrayList<>();
        }
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