package data;

import data.dto.FileDto;
import extractor.FileParser;
import utils.ConfigUtils;

import java.io.File;

public class BookMark {

    /**
     * 保存进度
     * @param file 文件
     * @param index 当前进度
     * @return 成功与否
     */
    public static boolean save(File file, int index){
        if (index <=1)
            return true;
        ConfigUtils.setConfig("bookmark",file.getAbsolutePath(),index+"");
        return true;
    }
    /**
     * 保存当前进度
     */
    public static boolean saveCurrent(){
        save(FileDto.getCurrentFile(), FileParser.currentPage);
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
            String indexStr = ConfigUtils.getConfig("bookmark",file.getAbsolutePath());
            if (null == indexStr || "".equals(indexStr)){
                index = 1;
            }else{
                index = Integer.parseInt(indexStr);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return null !=index?
                index
                :
                1;
    }
}
