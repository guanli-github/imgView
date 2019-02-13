package data.dto;

import data.ChapteredText;
import data.SearchResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class TextSearchDto {
    public static ChapteredText document;
    private static int presentChapter; //当前章节
    public static int presentScroll; //当前章节中的阅读进度
    public static Image bgImg =null;//背景图片
    public static String searchWord;

    public static ObservableList<String> getChapterNames(){
        String[] chapterNames = document.getChapterNames();
        return FXCollections.observableArrayList(chapterNames);
    }
    public static String nextChapter(){
        if(presentChapter==document.getChapterNum())return "";
        presentChapter += 1;
        return document.getChapter(presentChapter);
    }
    public static String preChapter(){
        if(presentChapter==1)return "";
        presentChapter -= 1;
        return document.getChapter(presentChapter);
    }
    public static ObservableList<SearchResult> searchResultList = FXCollections.observableArrayList();
}
