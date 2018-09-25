package com.electron.db;

import com.electron.util.configuration;
import com.electron.util.log_configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

/**
 *  »спользуетс€ дл€ получени€ данных из базы данных TFS (MS SQL)
 *  @author »ванов ¬.¬.
 *  @version 1.0
 */
public class db_tfs {

    /** Connection дл€ базы данных WS  */
    Connection connection = null;

    /**
     * ”станавливает соединение с базой данных TFS
     * @param conf параметры доступа к базе данных
     * @return 0 - ошбка соединени€, 1 - соединение установлено
     */
    public int Connect(configuration conf) {
        String url = "jdbc:sqlserver://"+conf.TFS.server+";databaseName="+conf.TFS.database;
        String name = conf.TFS.user;
        String password = conf.TFS.password;

        try {
            //«агружаем драйвер
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //—оздаЄм соединение
            connection = DriverManager.getConnection(url, name, password);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Cоединение с db TFS не установлено: " + ex.getMessage());
            return 0;
        }
        return 1;
    }

    /**
     * ¬озвращает ResultSet со списком трудозатрат за текущий спринт
     * @return ResultSet со списком  трудозатрат за текущий спринт
     */
    public ResultSet getTZ() {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM [Tfs_Warehouse].[dbo].[View_Telegram_TZ] ORDER BY 1,2";
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "«апрос к db TFS (трудозатраты за спринт) не выполнен");
        }
        return result;
    }

    /**
     * ¬озвращает ResultSet со списком запланированных трудозатрат на текущий спринтщ
     * @return ResultSet со списком запланированных трудозатрат на текущий спринтщ
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
            log_configuration.logger.log(Level.WARNING, "«апрос к db TFS (плановые трудозатраты за спринт) не выполнен");
        }
        return result;
    }


    /**
     * ¬озвращает ResultSet со списком данных о выполнении текущего спринта
     * @return ResultSet со списком данных о выполнении текущего спринта
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
            log_configuration.logger.log(Level.WARNING, "«апрос к db TFS (выполнение текущего спринта) не выполнен");
        }
        return result;
    }


    /**
     * ¬озвращает ResultSet со списком данных о выполнении предыдущего спринта
     * @return ResultSet со списком данных о выполнении предыдущего спринта
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
            log_configuration.logger.log(Level.WARNING, "«апрос к db TFS (выполнение предыдущего спринта) не выполнен");
        }
        return result;
    }


}
