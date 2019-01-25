package controller;

import data.SearchResult;
import data.dto.TextSearchDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import utils.SceneManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TextSearchController implements Initializable {
    @FXML
    private GridPane root = new GridPane();
    @FXML
    private ListView results = new ListView();
    @FXML
    private TextField searchWord = new TextField();
    @FXML
    private Button doSearch = new Button();

    @FXML
    private void doSearch(MouseEvent mouseEvent) {
        String word = searchWord.getText().trim();
        if (word.equals(TextSearchDto.searchWord)) {
            return;
        }
        TextSearchDto.searchWord = word;
        TextSearchDto.searchResultList.clear();
        TextSearchDto.searchResultList.setAll(getResults(word));
    }

    //点击某条搜索结果时调用
    @FXML
    private void hitResult(MouseEvent click) {
        if (click.getClickCount() == 2) {
            SearchResult hitted = (SearchResult) results.getSelectionModel()
                    .getSelectedItem();
            if (null == hitted) return;
            TextSearchDto.hitIndex = results.getItems().indexOf(hitted);//保存搜索进度
            TextSearchDto.searchIndex = hitted.indexInString;
            SceneManager.toText();
            return;
        }
    }

    //搜索出结果
    private List<SearchResult> getResults(String searchWord) {
//        int bias = Const.textBias;
//        int[] indexes = TextSearchDto.fullContent;
//        List<SearchResult> list = new ArrayList<>();
//        int length = TextSearchDto.fullContent.length();
//        int count = indexes.length;
//
//        if ((indexes[0] - bias) <= 0) indexes[0] = bias;
//        if ((indexes[count] + bias) >= length) indexes[count] = (length - bias);
//        for (int index : indexes) {
//            list.add(
//                    new SearchResult(index,
//                            TextSearchDto.fullContent.substring(index - bias, index + bias))
//            );
//        }
//        return list;
        return null;
    }

    //显示搜索结果
    private void showResult() {
        results.setItems(TextSearchDto.searchResultList);
        results.setCellFactory((results) -> new ResultCell());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
