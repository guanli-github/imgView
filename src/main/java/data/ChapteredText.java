package data;

public class ChapteredText {
    private String content;//文本
    private String[] chapterNames;//章节名的数组
    private int[] chaterIndexes;//章节起始点在文本中的位置

    private ChapteredText() {
    }

    public String getContent() {
        return content;
    }

    public String[] getChapterNames() {
        return chapterNames;
    }

    public ChapteredText(String content) {
        this.content = content;
        this.chaterIndexes = splitChapter(content);
        //this.chapterNames=;
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
     * 解析出章节目录
     *
     * @param content 文本
     * @return 各章节起始位置
     */
    private static int[] splitChapter(String content) {
        int[] ints = new int[10];
        return ints;
    }
}
