package data.dto;

import data.ChapteredText;
import data.Const;
import data.SearchResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class TextSearchDto {
    public static ChapteredText document;
    private static int presentChapter = 0; //当前章节
    public static double presentScroll=0.0; //当前章节中的阅读进度
    public static Image bgImg =null;//背景图片
    public static String searchWord;
    public static int TPYE = Const.SEARCH;


    public static String nextChapter(){
        if(presentChapter==document.titles.length)
            return "";
        presentChapter += 1;
        return getChapterStr(presentChapter);
    }
    public static String preChapter(){
        if(presentChapter==1)
            return "";
        presentChapter -= 1;
        return getChapterStr(presentChapter);
    }
    public static ObservableList<SearchResult> searchResultList = FXCollections.observableArrayList();
    public static int searchIndex = 0;

    public static void setPresentChapter(int chapterNo) {
        presentChapter = chapterNo;
    }

    public static String getPresentChapterStr() {
        return getChapterStr(presentChapter);
    }

    /**
     * 获取指定章节的文本
     *
     * @param index 章节的编号，从0开始
     * @return
     */
    public static String getChapterStr(int index) {
        if (1 >= document.titles.length) {
            return document.content;
        }
        if ((index+1) >= document.titles.length) {
            return "";
        }
        String chapter = document.content.substring(
                document.chptPositions[index], document.chptPositions[index+1]);
        return chapter;
    }

    /**
     * 根据全文中的位置获取章节编号
     *
     * @param index 全文中的位置
     * @return
     */
    public static int inChapter(int index) {
        int len = document.chptPositions.length;
        for (int i = 0; i < len-1; i++) {
            if (document.chptPositions[i] <= index && document.chptPositions[i + 1] > index) {
                return i;
            }
        }
        return 0;
    }
}
