package data.dto;

import data.SearchResult;
import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class TextSearchDto {
    public static ObservableList<String> document;
    public static Image bgImg =null;//background image
    public static String searchWord = "";

    public static boolean highlightMode = false;

    public static ObservableList<SearchResult> searchResultList = FXCollections.observableArrayList();
    public static int searchIndex = 0; //index of searched line in line list

}
