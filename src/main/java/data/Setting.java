package data;

import utils.ConfigUtils;

public class Setting {
    public static final int iconSize = 150; //icon尺寸
    public static int orient = Const.R2L; //指示翻页方向
    public static boolean isFullScreen = true;//是否全屏
    public static int renderDpi = 300; //pdf界面，渲染的dpi值

    public static int longTouchInterval = 500;//长按的事件间隔
    public static String deafultDir =  "E:\\";
    public static String bgImgDir =  "E:\\新規フォルダー\\nn\\pic";
    //密码
    public static String aespass = ConfigUtils.getConfig(Const.SETTING,"pwd");

}
