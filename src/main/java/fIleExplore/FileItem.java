package fIleExplore;
import java.io.File;

public class FileItem {
    public String fileName;   // 文件名
    public String firstName;  // 前缀名
    public File file;
    public int type = BAD_FORMAT; // 1, 文本文件; 2,图片文件; -1, 不支持的文件类型

    // 文件类型常量
    public static final int TEXT = 1;
    public static final int IMAGE = 2;
    public static final int BAD_FORMAT = -1;

    private final String[] txtTypes = { "txt", "java" };
    private final String[] imageTypes = { "jpg", "jpeg", "png", "bmp" };

    // 构造函数，传入一个file并取得file的名字和类型
    public FileItem(File file) {
        this.file = file;

        // 取得文件名
        fileName = file.getName();
        firstName = getFileFirstName(fileName);

        // 根据文件后缀来判断文件的类型
        String suffix = getFileSuffix(fileName);
        type = BAD_FORMAT;
        if (contains(txtTypes, suffix))
            type = TEXT;
        else if (contains(imageTypes, suffix))
            type = IMAGE;

    }

    // 判断是否图片
    public boolean contains(String[] types, String suffix) {
        suffix = suffix.toLowerCase(); // 统一转成小写
        for (String s : types) {
            if (s.equals(suffix))
                return true;
        }
        return false;
    }

    // 获取文件名的后缀
    public String getFileSuffix(String name) {
        int pos = name.lastIndexOf('.');
        if (pos > 0)
            return name.substring(pos + 1);

        return ""; // 无后缀文件
    }

    // 获取文件名前缀
    public String getFileFirstName(String name) {
        int pos = name.lastIndexOf('.');
        if(pos > 0) {
            return name.substring(0, pos);
        }
        return "";
    }


}