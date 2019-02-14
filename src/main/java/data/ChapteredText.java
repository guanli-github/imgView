package data;

public class ChapteredText {
    public String content;//文本
    public String[] titles;//章节名的数组
    public Integer[] chptPositions;//章节起始点在文本中的位置

    private ChapteredText() {
    }

    public double getScrollInChapter(int chapterIndex, int positionInChapter) {
        int length = chptPositions[chapterIndex + 1] - chptPositions[chapterIndex];
        return positionInChapter / (double) length;
    }

    public ChapteredText(String content, String[] chapterNames, Integer[] chaterIndexes) {
        this.content = content;
        this.titles = chapterNames;
        this.chptPositions = chaterIndexes;
    }

}
