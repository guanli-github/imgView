package controller;

import data.SearchRecord;
import data.SearchResult;
import data.Setting;
import data.dto.TextSearchDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import utils.SceneManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TextSearchController implements Initializable {
    @FXML
    private ListView results = new ListView<>();
    @FXML
    private TextField searchWord = new TextField();
    @FXML
    private ComboBox<String> recordCombo = new ComboBox<>();
    @FXML
    private void doSearch() {
        String word = searchWord.getText();
        if(null != word){
            TextSearchDto.searchWord = word;

            SearchRecord.add(word);
            TextSearchDto.searchResultList.clear();
            TextSearchDto.searchResultList.setAll(getResults(word));
            showResult();
        }
    }

    @FXML
    private void enterSearch(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            doSearch();
        }
    }
    @FXML
    private void toText() {
        TextSearchDto.highlightMode = true;
        SceneManager.toText();
    }
    //点击某条搜索结果时调用
    @FXML
    private void hitResult(MouseEvent click) {
        if (click.getClickCount() != 2) return;
        SearchResult hitted = (SearchResult) results.getSelectionModel()
                .getSelectedItem();
        if (null == hitted) return;
        //记录点击的哪一条
        TextSearchDto.searchIndex = hitted.getSearchIndex();
        toText();
    }

    //get search result
    private List<SearchResult> getResults(String searchWord) {
        List<SearchResult> list = new ArrayList<>();

        int len = TextSearchDto.document.size();
        for(int i=0;i<len;i++){
            String line = TextSearchDto.document.get(i);
            if(line.contains(searchWord)){
                SearchResult sr = new SearchResult(i,line);
                list.add(sr);
            }
        }

        return list;
    }

    //显示搜索结果
    private void showResult() {
        results.setItems(TextSearchDto.searchResultList);
        results.setCellFactory((results) -> new ResultCell());
    }

    private void setFullScreen() {

        double width = SceneManager.getStage().getWidth();
        double height = SceneManager.getStage().getHeight();

        results.setPrefHeight(height);
        results.setPrefWidth(width);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("search init:"+SceneManager.getStage().getWidth());

        if(Setting.isFullScreen){
            SceneManager.getStage().widthProperty().addListener((observable) -> {//屏幕旋转
                Platform.runLater(() -> setFullScreen()
                );
            });
        }
        //获取过去的记录
        List<String> records = SearchRecord.getRecords();
        if(!records.isEmpty()){
            recordCombo.setItems(FXCollections.observableArrayList(records));
            recordCombo.valueProperty().addListener((val)->{
                String word = recordCombo.getSelectionModel().getSelectedItem();
                searchWord.setText(word);
                doSearch();
            });
        }
        recordCombo.getSelectionModel().selectedItemProperty().addListener((e)->{
            searchWord.setText(recordCombo.getSelectionModel().getSelectedItem());
            doSearch();
        });

        if (TextSearchDto.searchWord != null && !"".equals(TextSearchDto.searchWord)) {
            searchWord.setText(TextSearchDto.searchWord);
            doSearch();
        }
        //回到之前点击的地方
        if(TextSearchDto.searchIndex>0 && TextSearchDto.searchIndex < TextSearchDto.searchResultList.size()){
            results.scrollTo(TextSearchDto.searchIndex);
        }
    }

    static class ResultCell extends ListCell<SearchResult> {
        @Override
        public void updateItem(SearchResult item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                this.setWrapText(true);
                this.setPrefWidth(SceneManager.getStage().getWidth());
                this.setText(item.line);
            } else {
                this.setText("");
            }
        }
    }

}
