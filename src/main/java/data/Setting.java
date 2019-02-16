package data;

import java.io.File;

public class Setting {
    public static int orient = Const.R2L; //指示翻页方向
    public static boolean isFullScreen = false; //是否全屏
    public static int renderDpi = 300; //pdf界面，渲染的dpi值
    public static int maxTitleLength = 20; //标题最大长度
    public static File deafultDir =  new File("E:\\");
    public static File bgImgDir =  new File("E:\\新規フォルダー\\nn\\pic");

//    public static void refresh(){
//        orient = Const.R2L;
//        renderDpi = 300;
//    };
}
