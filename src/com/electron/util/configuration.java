package com.electron.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

/**
 *  ������������ ��� ��������� ������ �� ����� ������������ ������� � ����� ������
 *  @author ������ �.�.
 *  @version 1.0
 */
public class configuration {

    /** ��� ����� ������������ */
    public static final String FILE_PROPERTIES = "dpo.properties";

    /**
     * �������, �������� ��������� ������� � �����-���� ���� ������
     */
    public class dbConfig {
       public String server;
       public String database;
       public String user;
       public String password;

        /**
         * ������������� ��������� ������� � ���� ������
         * @param server ������
         * @param database ���� ������
         * @param user ������������
         * @param password ������
         */
       public void load(String server, String database, String user, String password)
       {
           this.server = server;
           this.database = database;
           this.user = user;
           this.password = password;
       }

    }

    /** ������������ ������� � ���� ������ TFS  */
    public dbConfig TFS;
    /** ������������ ������� � ���� ������ DPO  */
    public dbConfig DPO;
    /** ������������ ������� � ���� ������ WS (WebSupport) */
    public dbConfig WS;

    /**
     * ������������� ���������� ������� �� ���� ������������ ����� ������
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
            log_configuration.logger.log(Level.WARNING, " ���� �������� " + FILE_PROPERTIES + " ����������!");
        }
    }

    public static void main(String[] args) {

    }

}
