package com.electron.data;

import com.electron.db.db_dpo;
import com.electron.util.*;

import java.sql.ResultSet;
import java.util.logging.Level;

/**
 *  ����� ������ ��������� �����������
 *  @author ������ �.�.
 *  @version 1.0
 */
public class phone_list {

    /**
     * ������������ ������ ��������� �����������
     * @param conf ������ �� ������������ ������� � ����� ������
     */
    public void getPhoneList(configuration conf) {
        db_dpo db = new db_dpo();
        db.Connect(conf);
        ResultSet result = db.getPhoneList();
        String ret = "�������� �����������\n";
        try {
            while (result.next()) {
                ret = ret + result.getString("fio") + "\t\t" + result.getString("phone") + "\n";
            }
            System.out.println(ret + "\n");
        } catch (Exception ex) {
            log_configuration.logger.log(Level.WARNING, "������ � db DPO (������ ���������) �� ��������");
        }
    }

}
