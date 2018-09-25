package com.electron.data;

import com.electron.db.db_tfs;
import com.electron.util.configuration;
import com.electron.util.log_configuration;
import com.electron.util.str_util;

import java.sql.ResultSet;
import java.util.logging.Level;

/**
 *  ����� ������� �����������
 *  @author ������ �.�.
 *  @version 1.0
 */
public class tz_list {

    /**
     * ������������ ������ ����������� �� ������� ������
     * @param conf ������ �� ������������ ������� � ����� ������
     */
    public void getListTZ(configuration conf) {
        db_tfs db = new db_tfs();
        db.Connect(conf);
        ResultSet result = db.getTZ();
        String ret = "������������ �� ������\n";
        String fio = "�� ����������";
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
                        ret = ret + " �����:" + iTZ;
                    }
                    fio = result.getString("System_AssignedTo");
                    ret = ret + "\n" +  str_util.GetFIO(fio) + ": " + str_util.GetInt(result.getString("TZ")) +
                          "(" + str_util.GetDate(result.getString("CreatedDate")) +")";
                    iTZ = result.getInt("TZ");
                }
                iRowCnt = iRowCnt + 1;
            }
            if (iRowCnt > 0) {
                ret = ret + " �����:" + iTZ;
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db TFS (������ ����������� �� ������� ������) �� ��������");
        }
    }


    /**
     * ������������ ������ �������� ����������� �� ������� ������
     * @param conf ������ �� ������������ ������� � ����� ������
     */
    public void getListTZPlan(configuration conf) {
        db_tfs db = new db_tfs();
        db.Connect(conf);
        ResultSet result = db.getTZPlan();
        String ret = "����������� �� ������ ������������\n";
        try {
            while (result.next()) {
                ret = ret + str_util.GetFIO(result.getString("FIO")) + ": " +
                      str_util.GetInt(result.getString("PlanWork")) + "\n";
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db TFS (������ �������� ����������� �� ������� ������) �� ��������");
        }
    }

    /**
     * ������������ ������ ������ � ���������� �������� �������
     * @param conf ������ �� ������������ ������� � ����� ������
     */
    public void getListTZExecute(configuration conf) {
        db_tfs db = new db_tfs();
        db.Connect(conf);
        ResultSet result = db.getTZExecute();
        String ret = "���������� �������� �������\n";
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
            log_configuration.logger.log(Level.WARNING, "������ � db TFS (���������� �������� �������) �� ��������");
        }
    }

    /**
     * ������������ ������ ������ � ���������� ����������� �������
     * @param conf ������ �� ������������ ������� � ����� ������
     */
    public void getListTZExecuteOld(configuration conf) {
        db_tfs db = new db_tfs();
        db.Connect(conf);
        ResultSet result = db.getTZExecuteOld();
        String ret = "���������� ����������� �������\n";
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
            log_configuration.logger.log(Level.WARNING, "������ � db TFS (���������� ����������� �������) �� ��������");
        }
    }

}
