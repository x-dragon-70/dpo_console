package com.electron;

import com.electron.data.phone_list;
import com.electron.data.works_list;
import com.electron.data.ws_list;
import com.electron.data.tz_list;
import com.electron.util.configuration;
import com.electron.util.log_configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * ��������� ��������� ������� ������ �� ������������ ������������ ��
 *  @author ������ �.�.
 *  @version 1.0
 */
public class Main {

    public static log_configuration log;
    /**
     * ���������� ������� ������ ����
     * @param args �� �����
     */
    public static void main(String[] args) {
        // �������� logger
        log = new log_configuration();
        log.Load();

        // �������� ������������ ����������
        configuration conf = new configuration();
        conf.loadConfig();

        Scanner in = new Scanner(System.in);

        while (true) {
            // ����������� ���� �� ��������� ����������� ��������
            int iMenu = ShowMenu(in);

            if (iMenu == 0) {
                break;
            }

            // ������ �����������
            switch (iMenu) {
                case 1:  // 1  - ������������ �� ������� ������
                    tz_list tz = new tz_list();
                    tz.getListTZ(conf);
                    break;
                case 2:  // 2  - ������������� �� ������� ������
                    tz_list tz1 = new tz_list();
                    tz1.getListTZPlan(conf);
                    break;
                case 31: // 31 - ���������� ����������� �������
                    tz_list tz3 = new tz_list();
                    tz3.getListTZExecuteOld(conf);
                    break;
                case 3:  // 3  - ���������� �������� �������
                    tz_list tz2 = new tz_list();
                    tz2.getListTZExecute(conf);
                    break;
                case 4:  // 4  - ����� �������/����� ����������� �������
                    works_list wl = new works_list();
                    wl.getWorksList(conf, "current");
                    break;
                case 5:  // 5  - ����� �������/����� ����������� �� ����
                    System.out.println("������� ���� � ������� DD.MM.YYYY:");
                    String sDate = "";
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
                        sDate = br.readLine();
                    } catch(Exception ex){
                        System.out.println("������� ������� ����");
                    }
                    works_list wld = new works_list();
                    wld.getWorksList(conf, sDate);
                    break;
                case 6:  // 6  - �������� �����������
                    phone_list pl = new phone_list();
                    pl.getPhoneList(conf);
                    break;
                case 7:  // 7  - �������� ������ WebSupport
                    ws_list ws = new ws_list();
                    ws.getListWS(conf);
                    break;
                case 8:  // 8  - ��� ������� �����
                    works_list wl2 = new works_list();
                    wl2.getMyWorksList(conf);
                    break;
                case 9:  // 9  - ��� ������� ����� �� �����
                    works_list wl3 = new works_list();
                    wl3.getMyWorksMonthList(conf);
                    break;
                default:
                    System.out.println("������� ������ ����� ����");
                    break;
            }
        }
    }

    /**
     * ��������� ������� ������ ����
     * @param in ������
     * @return ��������� ����� ����
     */
    static int ShowMenu(Scanner in) {
        System.out.println("1  - ������������ �� ������� ������");
        System.out.println("2  - ������������� �� ������� ������");
        System.out.println("3  - ���������� �������� �������");
        System.out.println("31 - ���������� ����������� �������");
        System.out.println("4  - ����� �������/����� ����������� �������");
        System.out.println("5  - ����� �������/����� ����������� �� ����");
        System.out.println("6  - �������� �����������");
        System.out.println("7  - �������� ������ WebSupport");
        System.out.println("8  - ��� ������� �����");
        System.out.println("9  - ��� ������� ����� �� �����");
        System.out.println("0  - �����");
        System.out.println("�������� ��������:");
        int iMenu = in.nextInt();
        return iMenu;
    }



}
