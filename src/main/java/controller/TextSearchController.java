package controller;

import data.Const;
import data.SearchResult;
import data.dto.TextSearchDto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ListView results = new ListView();
    @FXML
    private TextField searchWord = new TextField();

    @FXML
    private void doSearch() {
        String word = searchWord.getText().trim();
        if (word.equals(TextSearchDto.searchWord)) {
            return;
        }
        TextSearchDto.searchWord = word;
        TextSearchDto.searchResultList.clear();
        TextSearchDto.searchResultList.setAll(getResults(word));
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
    //显示目录
    @FXML
    private void toChapter(){
//        results.setItems(TextSearchDto.getChapterNames());
//        results.setCellFactory((results) -> new ChapterCell());
    }
    //点击章节名时调用
    @FXML
    private void hitChapter(MouseEvent click) {
        if (click.getClickCount() != 2) return;

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

    private void resize() {

        double width = SceneManager.getStage().getWidth();
        double height = SceneManager.getStage().getHeight();

        results.setPrefHeight(height);
        results.setPrefWidth(width);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resize();
        SceneManager.getStage().widthProperty().addListener((observable) -> {//屏幕旋转
            Platform.runLater(() -> {
                        resize();
                    }
            );
        });
        if (TextSearchDto.searchWord != null && !"".equals(TextSearchDto.searchWord)) {
            showResult();
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
