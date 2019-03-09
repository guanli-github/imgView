package utils;

import com.sun.jna.platform.FileUtils;
import data.Const;
import data.dto.FileDto;
import extractor.FileParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    //按文件名排序（同一目录下）
    public static int sortByName(String name1, String name2) {
        //首先将String中的数字映射为char
        name1 = preProcessName(name1);
        name2 = preProcessName(name2);

        //然后使用String的比较方法
        return name1.compareTo(name2);
    }

    private static String preProcessName(String name) {
        String[] notNums = name.replaceAll("[0-9]+",",").split(",");
        List<String> tmpList = new ArrayList<>();
        for(String str:notNums){
            if(str!=null && str.length()!=0){
                tmpList.add(str);
            }
        }
        notNums = tmpList.toArray(new String[0]);
        //把非数字替换成特殊符号
        name = name.replaceAll("[^0-9]",",");
        boolean startsWithNum = !name.startsWith(",");
        String[] nums = name.split(",");
        tmpList.clear();
        for(String str:nums){
            if(str!=null && str.length()!=0){
                tmpList.add(fillZero(str));
            }
        }

        nums = tmpList.toArray(new String[0]);
        for(String num:nums){
            num=fillZero(num);
        }
        int len = Math.min(notNums.length,nums.length);
        //合并
        StringBuilder result = new StringBuilder();
        for(int i=0;i<len;i++){
            if(startsWithNum){
                result.append(nums[i]);
                result.append(notNums[i]);
            }else{
                result.append(notNums[i]);
                result.append(nums[i]);
            }
        }
        if(nums.length>len){
            result.append(nums[nums.length-1]);
        }else if(notNums.length>len){
            result.append(notNums[notNums.length-1]);
        }
        return result.toString();
    }
    //将String中的数字补零，避免比较时出现10<2的情况
    //不会检查输入
    private static String fillZero(String num){
        //一般需要比较的文件总数不会超过9999
        int fillLen = 4;
        if (!num.isEmpty() && num.length() < fillLen) {
            String tmp = "";
            //需要填充的位数
            int fillNo = fillLen - num.length() % fillLen;
            for (int j = 0; j < fillNo; j++) {
                tmp += "0";
            }
            num = tmp+num;
        }
        return num;
    }

    //移动文件至回收站
    public static boolean moveFileToTrash(File[] filesToDel) {
        FileUtils fileUtils = FileUtils.getInstance();
        if (!fileUtils.hasTrash()) {
            return false;
        }
        try {
            FileParser.closeFile();
            System.gc();
            fileUtils.moveToTrash(filesToDel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSupportImg(String type) {
        for (int i = 0; i < Const.img_types.length; i++) {
            if (type.equals(Const.img_types[i])) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        //期望：-；-；-；-；
//        System.out.println(sortByName("1.jpg", "2.jpg"));
//        System.out.println(sortByName("2.jpg", "10.jpg"));
//
//        System.out.println(sortByName("a0002.jpg", "b0001"));
//        System.out.println(sortByName("0002.jpg", "CCF0001"));

    }
}
