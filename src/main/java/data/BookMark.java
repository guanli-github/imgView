package data;

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
        bookMarkList.put(file.getAbsolutePath(),index);
        try {
            File saveFile = new File(Const.savePath);
            if(!saveFile.exists()){
                saveFile.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(saveFile)
            );
            oos.writeObject(bookMarkList);
            oos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取进度
     * @param file 文件
     * @return 最近的书签记录，没有则返回1
     */
    public static int read(File file){

        try {
            File saveFile = new File(Const.savePath);
            if(!saveFile.exists()){
                saveFile.createNewFile();
            }
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(saveFile)
            );
            while(ois.readObject()!=null) {
                bookMarkList = (Map<String, Integer>) ois.readObject();
            }
            ois.close();
            return bookMarkList.get(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
