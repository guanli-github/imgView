package data;

public class ChapteredText {
    public String content;//文本
    public String[] chapterNames;//章节名的数组
    public Integer[] chaterIndexes;//章节起始点在文本中的位置

    private ChapteredText() {}
    public double getScrollInChapter(int chapterIndex,int indexInChapter){
        int length = chaterIndexes[chapterIndex+1]-chaterIndexes[chapterIndex];
        return indexInChapter / (double)length;
    }
    public ChapteredText(String content, String[] chapterNames, Integer[] chaterIndexes) {
        this.content = content;
        this.chapterNames = chapterNames;
        this.chaterIndexes = chaterIndexes;
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
