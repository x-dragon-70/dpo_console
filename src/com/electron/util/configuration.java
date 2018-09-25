package com.electron.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

/**
 *  Используется для получения данных из файла конфигурации доступа к базам данных
 *  @author Иванов В.В.
 *  @version 1.0
 */
public class configuration {

    /** Имя файла конфигурации */
    public static final String FILE_PROPERTIES = "dpo.properties";

    /**
     * Соклаас, хранящий параметры доступа к какой-либо базе данных
     */
    public class dbConfig {
       public String server;
       public String database;
       public String user;
       public String password;

        /**
         * Инициализация парметров доступа к базе данных
         * @param server сервер
         * @param database база данных
         * @param user пользователь
         * @param password пароль
         */
       public void load(String server, String database, String user, String password)
       {
           this.server = server;
           this.database = database;
           this.user = user;
           this.password = password;
       }

    }

    /** Конфигурация доступа к базе данных TFS  */
    public dbConfig TFS;
    /** Конфигурация доступа к базе данных DPO  */
    public dbConfig DPO;
    /** Конфигурация доступа к базе данных WS (WebSupport) */
    public dbConfig WS;

    /**
     * Инициализация параметров доступа ко всем используемым базам данных
     */
    public void loadConfig() {
        TFS = new dbConfig();
        DPO = new dbConfig();
        WS  = new dbConfig();

        FileInputStream fconf;
        Properties property = new Properties();

        try {
            fconf = new FileInputStream(FILE_PROPERTIES);
            property.load(fconf);

            TFS.load(property.getProperty("TFS.server"), property.getProperty("TFS.database"),
                     property.getProperty("TFS.user"), property.getProperty("TFS.password"));
            DPO.load(property.getProperty("DPO.server"), property.getProperty("DPO.database"),
                    property.getProperty("DPO.user"), property.getProperty("DPO.password"));
            WS.load(property.getProperty("WS.server"), property.getProperty("WS.database"),
                    property.getProperty("WS.user"), property.getProperty("WS.password"));

            property.clear();
            fconf.close();
        } catch (IOException e) {
            log_configuration.logger.log(Level.WARNING, " Файл настроек " + FILE_PROPERTIES + " отсуствует!");
        }
    }

    public static void main(String[] args) {

    }

}
