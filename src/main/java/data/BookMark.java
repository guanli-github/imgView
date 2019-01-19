package data;

import utils.ConfigUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BookMark {
    private static Map<String,Integer> bookMarkList  = new HashMap<>();

    /**
     * 保存进度
     * @param file 文件
     * @param index 当前进度
     * @return 成功与否
     */
    public static boolean save(File file, int index){
        ConfigUtils.setConfig("bookmark",file.getAbsolutePath(),index+"");
        return true;
    }

    /**
     * 读取进度
     * @param file 文件
     * @return 最近的书签记录，没有则返回1
     */
    public static int read(File file){
        Integer index = null;
        try{
        index = Integer.parseInt(
                ConfigUtils.getConfig("bookmark",file.getAbsolutePath()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null !=index?
                index
                :
                1;
    }
}
