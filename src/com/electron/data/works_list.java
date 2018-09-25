package com.electron.data;

import com.electron.db.db_dpo;
import com.electron.util.*;

import java.sql.ResultSet;
import java.util.logging.Level;

/**
 *  Вывод списков, связанных с приходами/уходами
 *  @author Иванов В.В.
 *  @version 1.0
 */
public class works_list {

    /**
     * Отрисовывает приходы/уходы сотрудников за день, указанный в параметре work_date
     * @param conf ссылка на конфигурацию доступа к базам данных
     * @param work_date дата запроса или current, если текущая
     */
    public void getWorksList(configuration conf, String work_date) {
        db_dpo db = new db_dpo();
        db.Connect(conf);
        ResultSet result = db.getWorkEmployersList(work_date);
        String ret = "ВРЕМЯ ПРИХОДОВ/УХОДОВ";
        String fio = "Не определено";
        try {
            while (result.next()) {
                if (result.getInt("is_dep") > 0) {
                    ret = ret  + "\n" + result.getString("fio");
                } else {
                    ret = ret + "\n  " + str_util.GetFIO(result.getString("fio")) +
                         " (" + str_util.GetTime(result.getString("work_start")) + "-"  +
                         str_util.GetTime(result.getString("work_end")) + ")";
                }
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Запрос к db DPO (список приходов/уходов) не выполнен");
        }
    }

    /**
     * Отрисовывает мои приходы/уходы
     * @param conf ссылка на конфигурацию доступа к базам данных
     */
    public void getMyWorksList(configuration conf) {
        db_dpo db = new db_dpo();
        db.Connect(conf);
        ResultSet result = db.getMyWorkList();
        String ret = "МОЕ РАБОЧЕЕ ВРЕМЯ\n";
        try {
            while (result.next()) {
                ret = ret + result.getString("work_month") + '/' + result.getString("work_year") +
                      "\t" + result.getString("work_time") + "\n";
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Запрос к db DPO (список приходов/уходов) не выполнен");
        }
    }

    /**
     * Отрисовывает мои приходы/уходы за месяц
     * @param conf ссылка на конфигурацию доступа к базам данных
     */
    public void getMyWorksMonthList(configuration conf) {
        db_dpo db = new db_dpo();
        db.Connect(conf);
        ResultSet result = db.getMyWorkMonthList();
        String ret = "МОЕ РАБОЧЕЕ ВРЕМЯ ЗА МЕСЯЦ\n";
        try {
            while (result.next()) {
                ret = ret  +  result.getString(1) + " " +
                      str_util.GetTime2(result.getString(2)) +
                      "-" + str_util.GetTime2(result.getString(3)) + "  " +
                        str_util.GetTime2(result.getString(4)) +
                      "\n";
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Запрос к db DPO (список приходов/уходов за месяц) не выполнен");
        }
    }


}
