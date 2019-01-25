package data;

public class SearchResult {
    public Integer indexInString; //当前搜索词在文档（String）中的index
    public String partContent; //包含搜索词上下文的一段文本

    public SearchResult(Integer indexInString, String partContent) {
        this.indexInString = indexInString;
        this.partContent = partContent;
    }
}
