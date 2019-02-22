package utils;

import data.ChapteredText;
import data.Const;
import data.Setting;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    public static String read(File file) {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        InputStreamReader isr = null;
        String line = "";
        try {
            String txtCharSet = resolveTxtCharSet(file);
            isr = new InputStreamReader(new FileInputStream(file), txtCharSet);
            br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {
                sb.append(line + Const.LINE_BREAK);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void write(File file, StringBuffer buffer) {
        PrintWriter p = null;
        try {
            p = new PrintWriter(new FileOutputStream(file));
            p.write(buffer.toString());
            p.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                p.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //解析txt文件的编码格式
    public static String resolveTxtCharSet(File file) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();

        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;

    }

    /**
     * 解析出章节目录
     *
     * @param file 文本
     * @return 划分好章节的文本
     */
    public static ChapteredText splitChapter(File file) {
//        ^\s*[第卷][0123456789一二三四五六七八九十零〇百千两]*[章回部节集卷].*
//
//^         \s*Chapter\s*[0123456789]*
//          附加规则
//          ^\s*[0123456789１２３４５６]
//          ^\s*(简介|序言|序[1-9]|序曲|简介|后记|尾声)
//            \s*(前言|自序|附录)
        List<Integer> chptPositions = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        //匹配章节名，划分章节
        //https://www.bbsmax.com/A/Vx5M0YmaJN/
        List<Pattern> patterns = new ArrayList<>();
        patterns.add(Pattern.compile("(^.{0,10}第)([0123456789一二三四五六七八九十零〇百千两]{1,9})[章节卷集部篇回](\\s*)(.*)"));
        patterns.add(Pattern.compile("(^.{0,10})([0123456789一二三四五六七八九十零〇百千两]{1,9})[楼#](\\s*)(.*)"));
        //Pattern p = Pattern.compile("(^.{0,10}第)([0123456789一二三四五六七八九十零〇百千两]{1,9})[章节卷集部篇回](\\s*)(.*)");
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        InputStreamReader isr = null;
        String line = "";
        try {
            String txtCharSet = resolveTxtCharSet(file);
            isr = new InputStreamReader(new FileInputStream(file), txtCharSet);
            br = new BufferedReader(isr);

            while ((line = br.readLine()) != null) {
                sb.append(line + Const.LINE_BREAK);
                for (Pattern p : patterns) {
                    Matcher matcher = p.matcher(line);
                    if (matcher.find()) {
                        chptPositions.add(sb.length() - line.length() - 1);
                        //整个表达式是第一个group
                        String title = matcher.group(0);
                        if (title.length() > Setting.maxTitleLength) {
                            title = title.substring(0, Setting.maxTitleLength);
                        }
                        titles.add(title);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //没有匹配到结果的情况
        if (chptPositions.isEmpty()) {
            chptPositions.add(0);
            if (sb.length() >= Setting.maxTitleLength) {
                titles.add(sb.substring(0, Setting.maxTitleLength - 1));
            } else {
                titles.add(sb.toString());
            }
        }
        //文章开头算一章
        if (chptPositions.get(0) != 0) {
            chptPositions.add(0, 0);
            if (sb.length() >= Setting.maxTitleLength) {
                titles.add(0, sb.substring(0, Setting.maxTitleLength - 1));
            } else {
                titles.add(0, sb.toString());
            }
        }
        return new ChapteredText(
                sb.toString(),
                titles.toArray(new String[0]),
                chptPositions.toArray(new Integer[0])
        );
    }

}
