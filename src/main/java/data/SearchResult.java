package data;

public class SearchResult {
    public Integer searchIndex; //index of searched line in line list
    public String line; //full line contain search word
    private SearchResult(){}

    public SearchResult(Integer searchIndex, String line) {
        this.searchIndex = searchIndex;
        this.line = line;
    }

    public Integer getSearchIndex() {
        return searchIndex;
    }

    public void setSearchIndex(Integer searchIndex) {
        this.searchIndex = searchIndex;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
