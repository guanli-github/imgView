package viewer.ImageParser;

import data.Const;
import data.Glo_Dto;
import javafx.scene.image.Image;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class Zip {
    private static ZipFile zipFile = null;
    private static ArrayList<ZipEntry> zes = null;

    static void reInitZip(File file){
        FileParser.fileType = Const.TYPE_ZIP;
        try {
            zipFile = new ZipFile(file);
            zes =  Collections.list(zipFile.getEntries());
            zes.removeIf((ze) -> ((ZipEntry) ze).isDirectory());//去除目录
            zes.removeIf((ze) -> (!Glo_Dto.isSupportImg(//去除非图片文件
                    ze.getName().substring(
                            ze.getName().lastIndexOf(".")))));
            Collections.sort(zes, new Comparator<ZipEntry>() {
                @Override
                public int compare(ZipEntry o1, ZipEntry o2) {
                    String o1No = o1.getName().substring(
                            o1.getName().lastIndexOf("/") + 1,
                            o1.getName().lastIndexOf(".")
                    );
                    String o2No = o2.getName().substring(
                            o2.getName().lastIndexOf("/") + 1,
                            o2.getName().lastIndexOf(".")
                    );
                    try{
                        return Integer.parseInt(o1No) - Integer.parseInt(o2No);
                    }catch (Exception e){
                        System.out.println(o1.getName()+";"+o2.getName());
                        System.out.println(o1No+";"+o2No);
                        return 0;
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileParser.totalPage = zes.size();
    }

    static Image getImageFromZip(int page){
        ZipEntry ze = zes.get(page);
        try {
            InputStream imgIs = zipFile.getInputStream(ze);
            return  new Image(imgIs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
