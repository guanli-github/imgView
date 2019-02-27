package utils;

import data.Const;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConfigUtils {
    private static String confdir = Const.Conf_Dir;//配置文件主目录

    /**
     * 获取属性值
     *
     * @param propertyName 配置文件名
     * @param key
     * @return
     */
    public static String getConfig(String propertyName, String key) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(confdir + propertyName + ".properties"));
            return props.getProperty(key);
        } catch (IOException e) {
            try {
                if(!new File(confdir).exists()){
                    new File(confdir).mkdir();
                }
                new File(confdir + propertyName + ".properties").createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
                return "";
            }
        }
        return "";
    }

    /**
     * 设置属性值
     *
     * @param propertyName 配置文件名
     * @param key
     * @param value
     * @return
     */
    public static boolean setConfig(String propertyName, String key, String value) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(confdir + propertyName + ".properties"));
            OutputStream fos = new FileOutputStream(confdir + propertyName + ".properties");
            props.setProperty(key, value);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
            props.store(fos, sdf.format(new Date()));
            fos.close();
        } catch (IOException e) {
            try {
                if(!new File(confdir).exists()){
                    new File(confdir).mkdir();
                }
                new File(confdir + propertyName + ".properties").createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean removeConfig(String propertyName, String key){
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(confdir + propertyName + ".properties"));
            OutputStream fos = new FileOutputStream(confdir + propertyName + ".properties");
            props.remove(key);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
            props.store(fos, sdf.format(new Date()));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //获取指定properties文件的所有key值
    public static List<String> listKeys(String propertyName){
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(confdir + propertyName + ".properties"));
            List<String> list =   (List<String>)(List)Collections.list(props.keys());
            return list;
        } catch (IOException e) {
            try {
                new File(confdir + propertyName + ".properties").createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
