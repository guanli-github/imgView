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
    private  void toText() {
        SceneManager.toText();
        return;
    }
    //点击某条搜索结果时调用
    @FXML
    private void hitResult(MouseEvent click) {
        if (click.getClickCount() == 2) {
            SearchResult hitted = (SearchResult) results.getSelectionModel()
                    .getSelectedItem();
            if (null == hitted) return;
            TextSearchDto.hitIndex = results.getItems().indexOf(hitted);//保存搜索进度
            //索引值除以总长度
            TextSearchDto.hitLocal = hitted.indexInString / (double) TextSearchDto.fullContent.length();
            System.out.println(hitted.indexInString /(double) TextSearchDto.fullContent.length());
            SceneManager.toText();
            return;
        }
    }

    //搜索出结果
    private List<SearchResult> getResults(String searchWord) {
        int bias = Const.textBias;
        List<Integer> indexes = new ArrayList();
        int a = TextSearchDto.fullContent.indexOf(searchWord);//*第一个出现的索引位置

        while (a != -1) {
            if(a > Const.txtBias){
                indexes.add(a - Const.txtBias);
            }else{
                indexes.add(Const.txtBias);
            }
            a = TextSearchDto.fullContent.indexOf(searchWord, a + 1);//*从这个索引往后开始第一个出现的位置
        }
        List<SearchResult> list = new ArrayList<>();
        int length = TextSearchDto.fullContent.length();
        int count = indexes.size();

        if ((indexes.get(0) - bias) <= 0) indexes.set(0,bias);
        if ((indexes.get(count-1) + bias) >= length) indexes.set(count-1,(length - bias));
        for (int index : indexes) {
            list.add(
                    new SearchResult(index,
                            TextSearchDto.fullContent.substring(index - bias, index + bias))
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
