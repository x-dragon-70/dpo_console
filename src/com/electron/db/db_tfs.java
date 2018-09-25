package com.electron.db;

import com.electron.util.configuration;
import com.electron.util.log_configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

/**
 *  ������������ ��� ��������� ������ �� ���� ������ TFS (MS SQL)
 *  @author ������ �.�.
 *  @version 1.0
 */
public class db_tfs {

    /** Connection ��� ���� ������ WS  */
    Connection connection = null;

    /**
     * ������������� ���������� � ����� ������ TFS
     * @param conf ��������� ������� � ���� ������
     * @return 0 - ����� ����������, 1 - ���������� �����������
     */
    public int Connect(configuration conf) {
        String url = "jdbc:sqlserver://"+conf.TFS.server+";databaseName="+conf.TFS.database;
        String name = conf.TFS.user;
        String password = conf.TFS.password;

        try {
            //��������� �������
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //������ ����������
            connection = DriverManager.getConnection(url, name, password);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "C��������� � db TFS �� �����������: " + ex.getMessage());
            return 0;
        }
        return 1;
    }

    /**
     * ���������� ResultSet �� ������� ����������� �� ������� ������
     * @return ResultSet �� �������  ����������� �� ������� ������
     */
    public ResultSet getTZ() {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM [Tfs_Warehouse].[dbo].[View_Telegram_TZ] ORDER BY 1,2";
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db TFS (������������ �� ������) �� ��������");
        }
        return result;
    }

    /**
     * ���������� ResultSet �� ������� ��������������� ����������� �� ������� �������
     * @return ResultSet �� ������� ��������������� ����������� �� ������� �������
     */
    public ResultSet getTZPlan() {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT [System_AssignedTo] AS FIO, SUM([PlanWork]) AS PlanWork " +
                            "FROM [Tfs_Warehouse].[dbo].[View_DPO_SpintTasks] " +
                            "WHERE [System_AssignedTo] IS NOT NULL " +
                            "GROUP BY [System_AssignedTo] " +
                            "ORDER BY [System_AssignedTo]";
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db TFS (�������� ������������ �� ������) �� ��������");
        }
        return result;
    }


    /**
     * ���������� ResultSet �� ������� ������ � ���������� �������� �������
     * @return ResultSet �� ������� ������ � ���������� �������� �������
     */
    public ResultSet getTZExecute() {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT FIO, PlanWork, " +
                                 "(SELECT ISNULL(SUM(TZ), 0) FROM [Tfs_Warehouse].[dbo].[View_Telegram_TZ]  WHERE System_AssignedTo = FIO) AS RealWork " +
                            "FROM [Tfs_Warehouse].[dbo].[View_DPO_SpintTasks_1] " +
                            "WHERE FIO IS NOT NULL " +
                            "ORDER BY FIO";
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db TFS (���������� �������� �������) �� ��������");
        }
        return result;
    }


    /**
     * ���������� ResultSet �� ������� ������ � ���������� ����������� �������
     * @return ResultSet �� ������� ������ � ���������� ����������� �������
     */
    public ResultSet getTZExecuteOld() {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT FIO, PlanWork, " +
                                "(SELECT ISNULL(SUM(TZ), 0) FROM [Tfs_Warehouse].[dbo].[View_Telegram_TZ_Old]  WHERE System_AssignedTo = FIO) AS RealWork " +
                           "FROM [Tfs_Warehouse].[dbo].[View_DPO_SpintTasks_1_Old] " +
                           "WHERE FIO IS NOT NULL " +
                           "ORDER BY FIO";
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db TFS (���������� ����������� �������) �� ��������");
        }
        return result;
    }


}
