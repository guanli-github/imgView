package utils;

import data.ChapteredText;
import data.Setting;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    private static void write(File file, StringBuffer buffer) {
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
        List<Integer> chpIndexes = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        //匹配章节名，划分章节
        //https://www.bbsmax.com/A/Vx5M0YmaJN/
        Pattern p = Pattern.compile("(^.*第)(.{1,9})[章节卷集部篇回](\\s*)(.*)");
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        InputStreamReader isr = null;
        String line = "";
        try{
            String txtCharSet = resolveTxtCharSet(file);
            isr = new InputStreamReader(new FileInputStream(file), txtCharSet);
            br = new BufferedReader(isr);
            while((line = br.readLine()) != null) {
                sb.append(line);
                Matcher matcher = p.matcher(line);
                if (matcher.find()) {
                    chpIndexes.add(sb.length() - line.length());
                    //整个表达式是第一个group
                    String title = matcher.group(0);
                    if(title.length()> Setting.maxTitleLength){
                        title = title.substring(0,Setting.maxTitleLength);
                    }
                    titles.add(title);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        String content = sb.toString();
        //没有匹配到结果的情况
        if(chpIndexes.isEmpty()){
            chpIndexes.add(1);
            titles.add(content.substring(0,20));
        }

        return new ChapteredText(
                content,
                titles.toArray(new String[0]),
                chpIndexes.toArray(new Integer[0])
        );
    }

}
