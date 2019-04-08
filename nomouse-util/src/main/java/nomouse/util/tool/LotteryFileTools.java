package nomouse.util.tool;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 文件工具，用于处理文本
 * Created by nomouse on 18/8/3.
 */
public class LotteryFileTools {

    public static void main(String[] args) {
        try {
            // read file content from file
            StringBuffer sb = new StringBuffer("");

            FileReader reader = new FileReader("/Users/nomouse/Java/userids");
            BufferedReader br = new BufferedReader(reader);

            String str = null;

            while ((str = br.readLine()) != null) {

                Long userId = Long.parseLong(str);
                HashMap<String, Object> map = new HashMap<>();
                map.put("activityId", 176);
                map.put("class", "com.alibaba.lstmkc.api.lottery.params.LotteryDrawParam");
                map.put("userId", userId);

                List<HashMap<String, Object>> hsfParam = new ArrayList<>();
                hsfParam.add(map);

                String out = JSON.toJSONString(hsfParam);
                sb.append(out + "\r\n");
                System.out.println(out);
            }

            br.close();
            reader.close();

            // write string to file
            FileWriter writer = new FileWriter("/Users/nomouse/Java/2.txt");
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(sb.toString());

            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
