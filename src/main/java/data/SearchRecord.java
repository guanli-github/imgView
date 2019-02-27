package data;

import utils.TextUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

//过去的搜索记录
public class SearchRecord {
    private static final String recordLocation = "D://config/records.txt";
    private static List<String> records = Arrays.asList(TextUtil.readCrypt(new File(recordLocation)).split(";"));
    public static List<String> getRecords(){
        return records;
    }
    public static void add(String word){
        if(records.contains(word)){
            return;
        }
        StringBuffer sb = new StringBuffer();
        for(String record:records){
            sb.append(record+";");
        }
        sb.append(word);
        TextUtil.writeCrypt(new File(recordLocation),sb);
    }
}
