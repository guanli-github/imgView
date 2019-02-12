package data;

public class ChapteredText {
    String content;//文本
    String[] chapterNames;//章节名的数组
    int[] chaterIndexes;//章节起始点在文本中的位置

    private ChapteredText(){}
    public ChapteredText(String content){
        this.content = content;
        this.chaterIndexes = splitChapter(content);
        //this.chapterNames=;
    }
    /**
     * 解析出章节目录
     * @param content 文本
     * @return 各章节起始位置
     */
    private static int[] splitChapter(String content){
        int[] ints = new int[10];
        return ints;
    }
}
