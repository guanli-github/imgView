package data;

public class ChapteredText {
    private String content;//文本
    private String[] chapterNames;//章节名的数组
    private int[] chaterIndexes;//章节起始点在文本中的位置

    private ChapteredText() {}

    public String getContent() {
        return content;
    }

    public String[] getChapterNames() {
        return chapterNames;
    }

    public double getScrollInChapter(int chapterIndex,int indexInChapter){
        int length = chaterIndexes[chapterIndex+1]-chaterIndexes[chapterIndex];
        return indexInChapter / (double)length;
    }
    public ChapteredText(String content, String[] chapterNames, int[] chaterIndexes) {
        this.content = content;
        this.chapterNames = chapterNames;
        this.chaterIndexes = chaterIndexes;
    }

    public int getChapterNum() {
        return chapterNames.length;
    }

    /**
     * 获取指定章节的文本
     *
     * @param index 章节的编号，从1开始
     * @return
     */
    public String getChapter(int index) {
        if (index > chapterNames.length) {
            return "";
        }
        String chapter = content.substring(
                chaterIndexes[index - 1], chaterIndexes[index]);
        return chapter;
    }

    /**
     * 根据全文中的位置获取章节编号
     * @param index 全文中的位置
     * @return
     */
    public int inChapter(int index){
        int len = chaterIndexes.length;
        for(int i=0;i<len;i++){
            if(chaterIndexes[i]<index && chaterIndexes[i+1]>index){
                return i;
            }
        }
        return 0;
    }

}
