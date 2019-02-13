package data;

public class SearchResult {
    public Integer chapterIndex; //搜索结果所在的章节位置（未考虑章节名是搜索词一部分的情况）
    public Integer indexInChapter; //当前搜索词在章节（String）中的index
    public String partContent; //包含搜索词上下文的一段文本

    private SearchResult(){}

    public SearchResult(Integer chapterIndex, Integer indexInChapter, String partContent) {
        this.chapterIndex = chapterIndex;
        this.indexInChapter = indexInChapter;
        this.partContent = partContent;
    }
}
