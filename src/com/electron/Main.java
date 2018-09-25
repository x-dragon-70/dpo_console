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
 * Программа просмотра сводных данных по деятельности департамента ПО
 *  @author Иванов В.В.
 *  @version 1.0
 */
public class Main {

    public static log_configuration log;
    /**
     * Обработчик пунктов выбора меню
     * @param args не задан
     */
    public static void main(String[] args) {
        // Создание logger
        log = new log_configuration();
        log.Load();

        // Загрузка конфигурации приложения
        configuration conf = new configuration();
        conf.loadConfig();

        Scanner in = new Scanner(System.in);

        while (true) {
            // Отображение меню ми получение выборанного действия
            int iMenu = ShowMenu(in);

            if (iMenu == 0) {
                break;
            }

            // Запуск обработчика
            switch (iMenu) {
                case 1:  // 1  - Трудозатраты за текущий спринт
                    tz_list tz = new tz_list();
                    tz.getListTZ(conf);
                    break;
                case 2:  // 2  - Запланировано на текущий спринт
                    tz_list tz1 = new tz_list();
                    tz1.getListTZPlan(conf);
                    break;
                case 31: // 31 - Выполнение предыдущего спринта
                    tz_list tz3 = new tz_list();
                    tz3.getListTZExecuteOld(conf);
                    break;
                case 3:  // 3  - Выполнение текущего спринта
                    tz_list tz2 = new tz_list();
                    tz2.getListTZExecute(conf);
                    break;
                case 4:  // 4  - Время прихода/ухода сотрудников сегодня
                    works_list wl = new works_list();
                    wl.getWorksList(conf, "current");
                    break;
                case 5:  // 5  - Время прихода/ухода сотрудников на дату
                    System.out.println("Введите дату в формате DD.MM.YYYY:");
                    String sDate = "";
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
                        sDate = br.readLine();
                    } catch(Exception ex){
                        System.out.println("Неверно указана дата");
                    }
                    works_list wld = new works_list();
                    wld.getWorksList(conf, sDate);
                    break;
                case 6:  // 6  - Телефоны сотрудников
                    phone_list pl = new phone_list();
                    pl.getPhoneList(conf);
                    break;
                case 7:  // 7  - Активные заявки WebSupport
                    ws_list ws = new ws_list();
                    ws.getListWS(conf);
                    break;
                case 8:  // 8  - Мое рабочее время
                    works_list wl2 = new works_list();
                    wl2.getMyWorksList(conf);
                    break;
                case 9:  // 9  - Мое рабочее время за месяц
                    works_list wl3 = new works_list();
                    wl3.getMyWorksMonthList(conf);
                    break;
                default:
                    System.out.println("Неверно указан пункт меню");
                    break;
            }
        }
    }

    /**
     * Отрисовка пунктов выбора меню
     * @param in сканер
     * @return выбранный пункт меню
     */
    static int ShowMenu(Scanner in) {
        System.out.println("1  - Трудозатраты за текущий спринт");
        System.out.println("2  - Запланировано на текущий спринт");
        System.out.println("3  - Выполнение текущего спринта");
        System.out.println("31 - Выполнение предыдущего спринта");
        System.out.println("4  - Время прихода/ухода сотрудников сегодня");
        System.out.println("5  - Время прихода/ухода сотрудников на дату");
        System.out.println("6  - Телефоны сотрудников");
        System.out.println("7  - Активные заявки WebSupport");
        System.out.println("8  - Мое рабочее время");
        System.out.println("9  - Мое рабочее время за месяц");
        System.out.println("0  - Выход");
        System.out.println("Выберите действие:");
        int iMenu = in.nextInt();
        return iMenu;
    }



}
