package data;

import data.dto.FileDto;
import extractor.FileParser;
import org.apache.tools.ant.util.DateUtils;
import utils.ConfigUtils;

import java.io.File;
import java.util.Date;

public class BookMark {

    /**
     * 保存进度
     * @param file 文件
     * @param index 当前进度
     * @return 成功与否
     */
    public static boolean save(File file, int index){
        if (index <=1){
            ConfigUtils.removeConfig(Const.BOOK_MARK,file.getAbsolutePath());
            return true;
        }
        ConfigUtils.setConfig(Const.BOOK_MARK,file.getAbsolutePath(),index+"");
        if(index == FileParser.totalPage){ //已经读完
            ConfigUtils.setConfig(Const.READED,file.getAbsolutePath(),DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        }

        return true;
    }
    //是否读完
    public static boolean isReaded(File file){
        String record = ConfigUtils.getConfig(Const.READED,file.getAbsolutePath());
        return null != record?true:false;
    }
        /**
         * 保存当前进度
         */
    public static boolean saveCurrent(){
        if(FileParser.fileType.equals(Const.TYPE_IMG)){
            //图片文件类型
            save(FileDto.currentDir,FileDto.currentFileIndex);
            return true;
        }
        save(FileDto.getCurrentFile(), FileParser.currentPage.getValue());
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
            String indexStr = ConfigUtils.getConfig(Const.BOOK_MARK,file.getAbsolutePath());
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
