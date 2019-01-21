package utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ConfigUtils {
    private static String confdir = "D:\\config\\";//配置文件主目录

    /**
     * 获取属性值
     * @param propertyName 配置文件名
     * @param key
     * @return
     */
    public static String getConfig(String propertyName, String key) {
        Properties props=new Properties();
        try {
            props.load(new FileInputStream(confdir + propertyName + ".properties"));
            return props.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 设置属性值
     * @param propertyName 配置文件名
     * @param key
     * @param value
     * @return
     */
    public static boolean setConfig(String propertyName, String key, String value) {
        Properties props=new Properties();
        try {
            props.load(new FileInputStream(confdir + propertyName + ".properties"));
            OutputStream fos = new FileOutputStream(confdir + propertyName + ".properties");
            props.setProperty(key, value);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
            props.store(fos, sdf.format(new Date()));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}