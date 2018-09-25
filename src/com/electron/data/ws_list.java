package com.electron.data;

import com.electron.db.db_ws;
import com.electron.util.configuration;
import com.electron.util.log_configuration;

import java.sql.ResultSet;
import java.util.logging.Level;

/**
 *  Вывод списков обращений клиентов на WebSupport
 *  @author Иванов В.В.
 *  @version 1.0
 */
public class ws_list {

    /**
     * Отрисовывает список обращений клиентов на WebSupport
     * @param conf ссылка на конфигурацию доступа к базам данных
     */
    public void getListWS(configuration conf) {
        db_ws db = new db_ws();
        db.Connect(conf);
        ResultSet result = db.getTasksWS();
        String ret = "АКТИВНЫЕ ЗАЯВКИ\n";
        int iCnt = 1;
        try {
            while (result.next()) {
                ret = ret + "\n" + result.getString("id") + ": " + result.getString("DateCreate") + " " +
                        result.getString("CreatorName") + " <" + result.getString("StatusName") + "> (" +
                        iCnt + ")";
                ret = ret + "\n" + result.getString("Name") + "\n";
                iCnt++;
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Запрос к db WS (список заявок) не выполнен");
        }
    }

}
