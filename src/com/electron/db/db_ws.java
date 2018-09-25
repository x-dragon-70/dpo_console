package com.electron.db;

import com.electron.util.configuration;
import com.electron.util.log_configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

/**
 *  Используется для получения данных из базы данных WS WebSupport (MS SQL)
 *  @author Иванов В.В.
 *  @version 1.0
 */
public class db_ws {

    /** Connection для базы данных WS  */
    Connection connection = null;

    /**
     * Устанавливает соединение с базой данных WS
     * @param conf параметры доступа к базе данных
     * @return 0 - ошбка соединения, 1 - соединение установлено
     */
    public int Connect(configuration conf) {
        String url = "jdbc:sqlserver://"+conf.WS.server+";databaseName="+conf.WS.database;
        String name = conf.WS.user;
        String password = conf.WS.password;

        try {
            //Загружаем драйвер
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //Создаём соединение
            connection = DriverManager.getConnection(url, name, password);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Cоединение с db WS не установлено: " + ex.getMessage());
            return 0;
        }
        return 1;
    }

    /**
     * Возвращает ResultSet со списком обращений на WS
     * @return ResultSet со списком обращений на WS
     */
    public ResultSet getTasksWS() {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM [is_support].[dbo].[View_Telegram] ORDER BY 1 DESC";
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Запрос к db WS (список обращений) не выполнен");
        }
        return result;
    }

}
