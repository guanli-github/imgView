package controller;

import data.Const;
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
import javafx.scene.layout.HBox;
import utils.SceneManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TextSearchController implements Initializable {
    @FXML
    private ListView results = new ListView();
    @FXML
    private HBox searchField = new HBox();
    @FXML
    private TextField searchWord = new TextField();
    @FXML
    private ComboBox<String> recordCombo = new ComboBox<>();
    @FXML
    private void doSearch() {
        String word = searchWord.getText().trim();
        TextSearchDto.searchWord = word;
        SearchRecord.add(word);
        TextSearchDto.searchResultList.clear();
        TextSearchDto.searchResultList.setAll(getResults(word));
        showResult();
    }
    //显示目录
    @FXML
    private void showChapter(){
        TextSearchDto.searchResultList.clear();
        List<SearchResult>list = new ArrayList<>();
        int len = TextSearchDto.document.titles.length;
        for(int i=0;i<len;i++){
            SearchResult sr  =new SearchResult(
                    TextSearchDto.inChapter(TextSearchDto.document.chptPositions[i]),
                    0,
                    TextSearchDto.document.titles[i]);
            list.add(sr);
        }
        TextSearchDto.searchResultList.setAll(list);
        showResult();
    }
    @FXML
    private void enterSearch(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            doSearch();
        }
    }
    @FXML
    private void toText() {
        SceneManager.toText();
        return;
    }
    //点击某条搜索结果时调用
    @FXML
    private void hitResult(MouseEvent click) {
        if (click.getClickCount() != 2) return;
        SearchResult hitted = (SearchResult) results.getSelectionModel()
                .getSelectedItem();
        if (null == hitted) return;
        TextSearchDto.setPresentChapter(hitted.chapterNo);
        //索引值除以章节长度
        TextSearchDto.presentScroll = TextSearchDto.document.getScrollInChapter(hitted.chapterNo,hitted.positionInChapter);
        SceneManager.toText();
        return;
    }

    //搜索出结果
    private List<SearchResult> getResults(String searchWord) {
        List<Integer> indexes = new ArrayList();
        int a = TextSearchDto.document.content.indexOf(searchWord);//*第一个出现的索引位置

        while (a != -1) {
            indexes.add(a);
            a = TextSearchDto.document.content.indexOf(searchWord, a + 1);//*从这个索引往后开始第一个出现的位置
        }
        List<SearchResult> list = new ArrayList<>();

        if(indexes.isEmpty()){
            return list;
        }
        //截取结果时防止越界
        int length = TextSearchDto.document.content.length();
        int count = indexes.size();
        int bias = Const.textBias;
        if ((indexes.get(0) - bias) <= 0)
            indexes.set(0,bias);
        if (indexes.get(count-1) >=(length-bias))
            indexes.set(count-1,(length - bias));
        //生成结果列表
        for (int index : indexes) {
            int chapterNo= TextSearchDto.inChapter(index);
            int posInChapter = index-TextSearchDto.document.chptPositions[chapterNo];
            list.add(
                    new SearchResult(chapterNo, posInChapter,
                            TextSearchDto.document.content.substring(index - bias, index + bias))
            );
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
    @FXML
    private void changeWord(MouseEvent mouseEvent) {
        String word = recordCombo.getSelectionModel().getSelectedItem();
        searchWord.setText(word);
        doSearch();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Setting.isFullScreen){
            setFullScreen();
            SceneManager.getStage().widthProperty().addListener((observable) -> {//屏幕旋转
                Platform.runLater(() -> {
                            setFullScreen();
                        }
                );
            });
        }
        //获取过去的记录
        List<String> records = SearchRecord.getRecords();
        if(!records.isEmpty()){
            recordCombo.setItems(FXCollections.observableArrayList(records));
        }
        //显示章节
        if(TextSearchDto.TPYE == Const.CHAPTER){
//            searchField.setVisible(false);
//            searchField.setManaged(false);
            showChapter();
            return;
        }
        if (TextSearchDto.searchWord != null && !"".equals(TextSearchDto.searchWord)) {
            searchWord.setText(TextSearchDto.searchWord);
            doSearch();
        }
    }

    static class ResultCell extends ListCell<SearchResult> {
        @Override
        public void updateItem(SearchResult item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                this.setText(item.partContent);
            } else {
                this.setText("");
            }
        }
    }

}
