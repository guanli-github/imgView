package utils;

import com.sun.jna.platform.FileUtils;
import data.Const;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    //按文件名排序（同一目录下）
    public static int sortByName(String name1,String name2){
        //首先将String中的数字映射为char
        name1 = mapNumToChar(name1);
        name2 = mapNumToChar(name2);

        //然后使用String的比较方法
        return name1.compareTo(name2);
    }
    //将String中的数字补零，避免比较时出现10<2的情况
    private static String mapNumToChar(String name) {
        String[] numStrArray = name.replaceAll("[^0-9]", ",").split(",");
        //文件名没有数字的情况
        if(numStrArray.length == 0)
            return name;
        //一般需要比较的文件总数不会超过9999
        int fillLen = 4;

        String numStr = "0";
        for(int i = 0; i< numStrArray.length; i++){
            if(!numStrArray[i].isEmpty() && numStrArray[i].length()<fillLen){
                //需要填充的位数
                int fillNo = fillLen - numStrArray[i].length() % fillLen;
                for(int j =0;j<fillNo;j++){
                    numStr+="0";
                }
            }
            numStr+=numStrArray[i];
        }
        //替换所有数字，再加上补零后的数字
        name = name.replaceAll("[0-9]", "")
                .concat(numStr);
        return name;
    }
    //移动文件至回收站
    public static boolean moveFileToTrash(File[] filesToDel) {
        String s = "filesToDel:";
        for(int i=0;i<filesToDel.length;i++){
            s+=filesToDel[i].getName();
        }
        TextUtil.writeLog("D://log.txt",s);
        try{
            FileUtils fileUtils = FileUtils.getInstance();
            TextUtil.writeLog("D://log.txt","fileUtils:"+fileUtils.toString());
            if (!fileUtils.hasTrash()) {
                TextUtil.writeLog("D://log.txt","fileUtils.hasTrash():"+fileUtils.hasTrash());
                return false;
            }
            try {
                fileUtils.moveToTrash(filesToDel);
                return true;
            } catch (IOException e) {
                return false;
            }
        }catch (Exception e){
            TextUtil.writeLog("D:log.txt",e.toString());
            return false;
        }
    }

    public static boolean isSupportImg(String type){
        for (int i = 0; i < Const.img_types.length; i++) {
            if (type.equals(Const.img_types[i])) {
                return true;
            }
        }
        return false;
    }
    //期望：-1；-1；-1；-1；
    public static void main(String[] args) {
        System.out.println(sortByName("1.jpg","2.jpg"));
        System.out.println(sortByName("2.jpg","10.jpg"));

        System.out.println(sortByName("a0002.jpg","b0001"));
        System.out.println(sortByName("0002.jpg","CCF0001"));
    }
}
