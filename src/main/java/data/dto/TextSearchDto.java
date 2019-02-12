package data.dto;

import data.ChapteredText;
import data.SearchResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class TextSearchDto {
    public static ChapteredText document;
    public static Image bgImg =null;//背景图片


    public static ObservableList<SearchResult> searchResultList = FXCollections.observableArrayList();
}
