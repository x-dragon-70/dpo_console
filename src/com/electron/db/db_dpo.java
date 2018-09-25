package com.electron.db;

import com.electron.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

/**
 *  ������������ ��� ��������� ������ �� ���� ������ PDO (PostreSQL)
 *  @author ������ �.�.
 *  @version 1.0
 */

public class db_dpo {

    /** Connection ��� ���� ������ DPO  */
    Connection connection = null;

    /**
     * ������������� ���������� � ����� ������ DPO
     * @param conf ��������� ������� � ���� ������
     * @return 0 - ����� ����������, 1 - ���������� �����������
     */
    public int Connect(configuration conf) {
        String url = "jdbc:postgresql://"+conf.DPO.server+":5432/"+conf.DPO.database;
        String name = conf.DPO.user;
        String password = conf.DPO.password;

        try {
            //��������� �������
            Class.forName("org.postgresql.Driver");
            //������ ����������
            connection = DriverManager.getConnection(url, name, password);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "C��������� � db DPO �� �����������");
            return 0;
        }
        return 1;
    }

    /**
     * ���������� ResultSet �� ������� ��������� �����������
     * @return ResultSet �� ������� ��������� �����������
     */
    public ResultSet getPhoneList() {
         ResultSet result = null;
         try {
             Statement statement = connection.createStatement();
             String sql = "SELECT '  ' || dpo_users.fio fio, "+
                                  "dpo_users.phone, "+
                                  "(select dep.order from private.dpo_unit dep where id=dpo_users.unit_id) as ord1, "+
                                  "dpo_users.fio ord2, "+
                                  "0 is_dep "+
                             "FROM private.dpo_users "+
                             "WHERE end_date is null and phone is not null "+
                          "UNION "+
                          "SELECT dpo_unit.name fio, "+
                                  "'' phone, "+
                                  "dpo_unit.order ord1, "+
                                  "'  ' || dpo_unit.name ord2, "+
                                  "1 is_dep "+
                             "FROM private.dpo_unit "+
                           "ORDER BY ord1, ord2";
             result = statement.executeQuery(sql);
         } catch (Exception ex) {
             log_configuration.logger.log(Level.WARNING, "������ � db DPO (������ ���������) �� ��������");
         }
        return result;
    }

    /**
     * ���������� ResultSet �� ������� ��������/������ �����������
     * @param work_date ���� ������� ��� current, ���� �������
     * @return ResultSet �� ������� �� ������� ��������/������ �����������
     */
    public ResultSet getWorkEmployersList(String work_date) {
        ResultSet result = null;
        String sql;
        try {
            Statement statement = connection.createStatement();

            if (work_date.equals("current")) {
                sql = "SELECT fio, work_date, work_start, work_end, work_end-work_start work_all, " +
                              "(select dpo_unit.order from private.dpo_users, private.dpo_unit where dpo_users.id=v_dpo_timework.user_id and dpo_unit.id=dpo_users.unit_id) as ord1, " +
                              " fio ord2, 0 is_dep " +
                         "FROM private.v_dpo_timework " +
                         "WHERE work_date = (now()::timestamp)::date " +
                      "UNION " +
                      "SELECT dpo_unit.name fio, null work_date, null work_state, " +
                              "null work_end, null work_all, dpo_unit.order ord1, " +
                              "'  ' || dpo_unit.name ord2, 1 is_dep " +
                         "FROM private.dpo_unit " +
                         "ORDER BY ord1, ord2";
            } else {
                 sql = "SELECT fio, work_date, work_start, work_end, work_end-work_start work_all, " +
                              "(select dpo_unit.order from private.dpo_users, private.dpo_unit where dpo_users.id=v_dpo_timework.user_id and dpo_unit.id=dpo_users.unit_id) as ord1, " +
                              "fio ord2, 0 is_dep " +
                         "FROM private.v_dpo_timework " +
                         "WHERE work_date = ('"+work_date+"')::date " +
                      "UNION " +
                      "SELECT dpo_unit.name fio, null work_date, null work_state, " +
                             "null work_end, null work_all, dpo_unit.order ord1, " +
                             "'  ' || dpo_unit.name ord2, 1 is_dep " +
                         "FROM private.dpo_unit " +
                         "ORDER BY ord1, ord2";
            }
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db DPO (�������/����� �����������) �� ��������");
        }
        return result;
    }

    /**
     * ���������� ResultSet �� ������� ���� ��������/������
     * @return ResultSet �� ������� ���� ��������/������
     */
    public ResultSet getMyWorkList() {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT Extract(YEAR from work_date) AS work_year, " +
                                "Extract(MONTH from work_date) AS work_month, " +
                                "SUM(work_end - work_start - interval '30 minute') AS work_time " +
                           "FROM private.v_dpo_timework " +
                           "WHERE user_id = 1001 " +
                           "GROUP BY work_year, work_month " +
                           "ORDER BY work_year asc, work_month asc";
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db DPO (��� �������/�����) �� ��������");
        }
        return result;
    }

    /**
     * ���������� ResultSet �� ������� ���� ��������/������ �� �����
     * @return ResultSet �� ������� ���� ��������/������ �� �����
     */
    public ResultSet getMyWorkMonthList() {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT to_char(work_date::date, 'DD.MM.YY') AS wdate, " +
                                 "work_start::time, " +
                                 "work_end::time, " +
                                 "work_end - work_start - interval '30 minute' AS work_time " +
                           "FROM private.v_dpo_timework " +
                           "WHERE user_id = 1001 AND Extract(YEAR from work_date) = Extract(YEAR from now()) AND Extract(MONTH from work_date) = Extract(MONTH from now())  " +
                           "ORDER BY work_date desc";
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db DPO (��� �������/����� �� �����) �� ��������");
        }
        return result;
    }


}
