package viewer.ImageParser;

import data.Const;
import data.Status;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        zes =  Collections.list(zipFile.getEntries());
        zes.removeIf((ze) -> ((ZipEntry) ze).isDirectory());//去除目录
        zes.removeIf((ze) -> (!Status.isSupportImg(//去除非图片文件
                ze.getName().substring(
                        ze.getName().lastIndexOf(".")))));
        Collections.sort(zes, new Comparator<ZipEntry>() {
            @Override
            public int compare(ZipEntry o1, ZipEntry o2) {
                return o1.getName().toLowerCase()
                        .compareTo(o2.getName().toLowerCase());
            }
        });
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
