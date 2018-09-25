package com.electron.db;

import com.electron.util.configuration;
import com.electron.util.log_configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

/**
 *  ������������ ��� ��������� ������ �� ���� ������ WS WebSupport (MS SQL)
 *  @author ������ �.�.
 *  @version 1.0
 */
public class db_ws {

    /** Connection ��� ���� ������ WS  */
    Connection connection = null;

    /**
     * ������������� ���������� � ����� ������ WS
     * @param conf ��������� ������� � ���� ������
     * @return 0 - ����� ����������, 1 - ���������� �����������
     */
    public int Connect(configuration conf) {
        String url = "jdbc:sqlserver://"+conf.WS.server+";databaseName="+conf.WS.database;
        String name = conf.WS.user;
        String password = conf.WS.password;

        try {
            //��������� �������
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //������ ����������
            connection = DriverManager.getConnection(url, name, password);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "C��������� � db WS �� �����������: " + ex.getMessage());
            return 0;
        }
        return 1;
    }

    /**
     * ���������� ResultSet �� ������� ��������� �� WS
     * @return ResultSet �� ������� ��������� �� WS
     */
    public ResultSet getTasksWS() {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM [is_support].[dbo].[View_Telegram] ORDER BY 1 DESC";
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db WS (������ ���������) �� ��������");
        }
        return result;
    }

}
