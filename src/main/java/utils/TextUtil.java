package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class TextUtil {
    public static ObservableList<String> read(File file) {
        ObservableList<String> lines = FXCollections.observableArrayList();

        BufferedReader br = null;
        InputStreamReader isr = null;
        String line = "";
        try {
            String txtCharSet = resolveTxtCharSet(file);
            isr = new InputStreamReader(new FileInputStream(file), txtCharSet);
            br = new BufferedReader(isr);

             while ((line = br.readLine()) != null) {
//                while (line.length() > charPerLine){
//                    lines.add(line.substring(0,charPerLine));
//                    line = line.substring(charPerLine);
//                }
                lines.add(line);
            }
            br.close();
            isr.close();
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

    public static String readCrypt(File file) {
        byte buffer[] = new byte[(int) file.length()];
        FileInputStream fileinput;
        try {
            fileinput = new FileInputStream(file);
            fileinput.read(buffer);//读取文件中的内容到buffer中
            String str = new String(buffer,"UTF-8");
            fileinput.close();
            return AESUtil.decrypt(str);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void writeCrypt(File file, StringBuffer buffer) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            char[] arry = AESUtil.encrypt(buffer.toString()).toCharArray();
            writer.write(arry);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //resolve coding for the text file
    public static String resolveTxtCharSet(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bin = new BufferedInputStream(fis);
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
        bin.close();
        fis.close();
        return code;

    }

}
