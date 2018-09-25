package com.electron.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.*;

/**
 *  Используется для настройки логгирования
 *  @author Иванов В.В.
 *  @version 1.0
 */
public class log_configuration {

    /** Logger для настройки и использования в программе  */
    public static Logger logger = Logger.getLogger("DPO");

    /**
     * Инициализация логгера
     */
    public void Load() {
        logger.setLevel(Level.INFO);

        // Задание строки вывода
        try {
            Formatter formatter = new Formatter() {
                @Override
                public String format(LogRecord record) {
                    SimpleDateFormat logTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                    Calendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(record.getMillis());
                    return record.getLevel() + " "
                            + logTime.format(cal.getTime())
                            + " || "
                            + record.getSourceClassName().substring(
                            record.getSourceClassName().lastIndexOf(".") + 1,
                            record.getSourceClassName().length())
                            + "."
                            + record.getSourceMethodName()
                            + "() : "
                            + record.getMessage() + "\n";
                }
            };

            // Создание TXT-file formatter
            Handler fh = new FileHandler("dpo.log");
            fh.setFormatter(formatter);
            logger.addHandler(fh);

            // Создание Console formatter
            Handler ch = new ConsoleHandler();
            ch.setFormatter(formatter);
            logger.addHandler(ch);

            LogManager lm = LogManager.getLogManager();

            Logger globalLogger = Logger.getLogger("");
            Handler[] handlers = globalLogger.getHandlers();
            for(Handler handler : handlers) {
                globalLogger.removeHandler(handler);
            }

            lm.addLogger(logger);

        } catch (Exception ex) {
        }
    }

    public static void main(String[] args) {
    }

}
