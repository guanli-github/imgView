package utils;

import data.ChapteredText;

import java.io.*;

public class TextUtil {
    public static String readTxt(File file) {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            String txtCharSet = resolveTxtCharSet(file);
            isr = new InputStreamReader(new FileInputStream(file), txtCharSet);
            br = new BufferedReader(isr);
            String data = null;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                isr.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
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
     * @param content 文本
     * @return 划分好章节的文本
     */
    public static ChapteredText splitChapter(String content) {
        String[] patterns;
        int[] ints = new int[10];
        String[] strs = new String[10];
        if(ints.length == 0){

        }
        ChapteredText document = new ChapteredText(content,strs,ints);

        return document;
    }
}
