package data;

import utils.ConfigUtils;

public class Const {
    public static final int L2R = 0; //左到右
    public static final int R2L = 1; //右到左

    public static final int iconSize = 20; //icon尺寸

    public static final int textBias = 30; //中心字与搜索top的差值

    public static final String LINE_BREAK = "\n";

    public static final String TYPE_PDF = ".pdf";
    public static final String TYPE_ZIP = ".zip";
    public static final String TYPE_IMG = ".img";
    public static String[] img_types = {".jpg",".jpeg",".gif",".png",".bmp"};
    public static String[] doc_types = {".zip",".pdf"};
    public static String[] txt_types = {".txt",".html",".log",".java",".py",".sql"};

    public static final int SEARCH = 0; //搜索页面
    public static final int CHAPTER = 1; //目录页面

    //密码
    public static String aespass = ConfigUtils.getConfig("data","pwd");
}
