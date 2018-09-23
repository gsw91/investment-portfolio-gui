package com.invest.Gui.tables;

import com.invest.Gui.config.ServiceConfig;
import com.invest.Gui.connection.RequestMethod;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class QuotationsTable extends AbstractTableModel implements TableGenerator {

    private static Logger LOGGER = Logger.getLogger(QuotationsTable.class);
    private String[] columnNames = { "Index", "Current price", "Time"};
    private Object[][] data;

    public QuotationsTable() {}

    @Override
    public JTable createTable(Long userId) {
        setData(connectToDatabase());
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

    private List<QuotationsData> connectToDatabase() {
        LOGGER.info("Creating quotations table for user");
        String request = generateUrl(ServiceConfig.SHARES_ALL);
        HttpURLConnection connection = createConnection(request, RequestMethod.GET);
        String[] array = getResponse(connection, "Quotations");
        List<String> transformedResponseList = transformResponse(array);
        return setGeneratedDate(transformedResponseList);
    }

    @Override
    public List<QuotationsData> setGeneratedDate(List<String> transformedResponseList) {
        List<QuotationsData> quotations = new ArrayList<>();
        int modulo = transformedResponseList.size() % 4;
        if (modulo == 0) {
            int quantity = transformedResponseList.size();
            for (int i = 0; i < quantity; i += 4) {
                quotations.add(new QuotationsData(
                        transformedResponseList.get(i),
                        transformedResponseList.get(i + 1),
                        transformedResponseList.get(i + 2)
                ));
            }
        }
        return quotations;
    }

    @Override
    public List<String> transformResponse(String[] array) {
        List<String> list = new ArrayList<>();
        if(array!=null) {
            for (String i : array) {
                String[] nextArray = i.split(":");
                if (nextArray.length == 3) {
                    list.add(nextArray[2]);
                } else if (nextArray.length == 2) {
                    list.add(nextArray[1]);
                } else if (nextArray.length == 4) {
                    String data = nextArray[1] + ":" + nextArray[2] + ":" + nextArray[3];
                    data = data.replace("T", " ");
                    list.add(data);
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
