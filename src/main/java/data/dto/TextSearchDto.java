package data.dto;

import data.SearchResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TextSearchDto {
    public static String fullContent;
    public static String searchWord;
    public static int hitIndex =1; //当前选择的是第几个结果
    public static double readIndex = 0.0; //搜索前的位置
    public static double hitLocal = 0.0; //搜索点击的位置


    public static ObservableList<SearchResult> searchResultList = FXCollections.observableArrayList();
}
