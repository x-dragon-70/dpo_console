package com.electron.util;

/**
 *  Всевозможные строковые функции для вывода в консоль
 *  @author Иванов В.В.
 *  @version 1.0
 */
public class str_util {

    public static String GetFIO(String fio) {
        return fio.substring(0, fio.indexOf(" ")+2);
    }

    public static String GetTime(String work) {
        if (work == null) {
            return "";
        } else {
            return work.substring(11, 16);
        }
    }

    public static String GetTime2(String work) {
        if (work == null) {
            return "";
        } else {
            return work.substring(0, 5);
        }
    }

    public static String GetDate(String work) {
        return work.substring(6, 8);
    }

    public static String GetInt(String work) {
        return work.substring(0, work.indexOf("."));
    }


}
