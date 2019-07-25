package data;

public class Const {
    public static final int L2R = 0; //左到右
    public static final int R2L = 1; //右到左

    public static final String TYPE_PDF = ".pdf";
    public static final String TYPE_ZIP = ".zip";
    public static final String TYPE_IMG = ".img";
    public static String[] img_types = {".jpg",".jpeg",".gif",".png",".bmp",".tiff"};
    public static String[] doc_types = {".zip",".pdf"};
    public static String[] txt_types = {".txt",".html",".log",".java",".py",".sql"};

    public static final int SEARCH = 0; //搜索页面
    public static final int CHAPTER = 1; //目录页面

    //config相关properties名
    public static final String Conf_Dir = "D:\\config\\";
    public static final String BOOK_MARK = "bookmark";
    public static final String READED = "readed";
    public static final String SETTING = "setting";
    public static final String THUMBNAIL = "thumbnail";

    public static final String recordLocation = "D:\\config\\records.txt";
    public static final String thumbnailPath = "D:\\thumbnail\\";

}
