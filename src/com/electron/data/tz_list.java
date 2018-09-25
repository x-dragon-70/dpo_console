package com.electron.data;

import com.electron.db.db_tfs;
import com.electron.util.configuration;
import com.electron.util.log_configuration;
import com.electron.util.str_util;

import java.sql.ResultSet;
import java.util.logging.Level;

/**
 *  Вывод списков трудозатрат
 *  @author Иванов В.В.
 *  @version 1.0
 */
public class tz_list {

    /**
     * Отрисовывает список трудозатрат за текущий спринт
     * @param conf ссылка на конфигурацию доступа к базам данных
     */
    public void getListTZ(configuration conf) {
        db_tfs db = new db_tfs();
        db.Connect(conf);
        ResultSet result = db.getTZ();
        String ret = "ТРУДОЗАТРАТЫ ЗА СПРИНТ\n";
        String fio = "Не определено";
        int iRowCnt = 0;
        int iTZ = 0;
        try {
            while (result.next()) {
                if (fio.equals(result.getString("System_AssignedTo"))) {
                    iTZ = iTZ + result.getInt("TZ");
                    ret = ret + " " + str_util.GetInt(result.getString("TZ")) +
                          '(' + str_util.GetDate(result.getString("CreatedDate")) + ')';
                } else {
                    if (iRowCnt > 0) {
                        ret = ret + " Сумма:" + iTZ;
                    }
                    fio = result.getString("System_AssignedTo");
                    ret = ret + "\n" +  str_util.GetFIO(fio) + ": " + str_util.GetInt(result.getString("TZ")) +
                          "(" + str_util.GetDate(result.getString("CreatedDate")) +")";
                    iTZ = result.getInt("TZ");
                }
                iRowCnt = iRowCnt + 1;
            }
            if (iRowCnt > 0) {
                ret = ret + " Сумма:" + iTZ;
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Запрос к db TFS (список трудозатрат за текущий спринт) не выполнен");
        }
    }


    /**
     * Отрисовывает список плановых трудозатрат за текущий спринт
     * @param conf ссылка на конфигурацию доступа к базам данных
     */
    public void getListTZPlan(configuration conf) {
        db_tfs db = new db_tfs();
        db.Connect(conf);
        ResultSet result = db.getTZPlan();
        String ret = "ПЛАНИРУЕМЫЕ НА СПРИНТ ТРУДОЗАТРАТЫ\n";
        try {
            while (result.next()) {
                ret = ret + str_util.GetFIO(result.getString("FIO")) + ": " +
                      str_util.GetInt(result.getString("PlanWork")) + "\n";
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Запрос к db TFS (список плановых трудозатрат за текущий спринт) не выполнен");
        }
    }

    /**
     * Отрисовывает список данных о выполнении текущего спринта
     * @param conf ссылка на конфигурацию доступа к базам данных
     */
    public void getListTZExecute(configuration conf) {
        db_tfs db = new db_tfs();
        db.Connect(conf);
        ResultSet result = db.getTZExecute();
        String ret = "ВЫПОЛНЕНИЕ ТЕКУЩЕГО СПРИНТА\n";
        try {
            while (result.next()) {
                ret = ret + "\n" + str_util.GetFIO(result.getString("FIO")) + ": \t" +
                        str_util.GetInt(result.getString("RealWork")) + "/" +
                        str_util.GetInt(result.getString("PlanWork"));
                if (result.getString("RealWork").equals(result.getString("PlanWork"))) {
                    ret = ret + "\t +";
                } else {
                    ret = ret + "\t -";
                }
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Запрос к db TFS (выполнение текущего спринта) не выполнен");
        }
    }

    /**
     * Отрисовывает список данных о выполнении предыдущего спринта
     * @param conf ссылка на конфигурацию доступа к базам данных
     */
    public void getListTZExecuteOld(configuration conf) {
        db_tfs db = new db_tfs();
        db.Connect(conf);
        ResultSet result = db.getTZExecuteOld();
        String ret = "ВЫПОЛНЕНИЕ ПРЕДЫДУЩЕГО СПРИНТА\n";
        try {
            while (result.next()) {
                ret = ret + "\n" + str_util.GetFIO(result.getString("FIO")) + ": \t" +
                        str_util.GetInt(result.getString("RealWork")) + "/" +
                        str_util.GetInt(result.getString("PlanWork"));
                if (result.getString("RealWork").equals(result.getString("PlanWork"))) {
                    ret = ret + "\t +";
                } else {
                    ret = ret + "\t -";
                }
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "Запрос к db TFS (выполнение предыдущего спринта) не выполнен");
        }
    }

}
