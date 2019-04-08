package nomouse.util.tool;

import java.io.*;

/**
 * 文件工具，用于生成品牌券压测数据。
 * Created by nomouse on 18/8/3.
 */
public class BrandFileTools {

    public static void main(String[] args) {
        try {
            // read file content from file
            StringBuffer sb = new StringBuffer();

            FileReader reader = new FileReader("/Users/nomouse/AliDrive/lstwork/技术文档/压测/已偏移userId");
            BufferedReader br = new BufferedReader(reader);

            String str;

            while ((str = br.readLine()) != null) {
                String line = "[" + str + ",\"b6fa8d87850e4a64b3140c60b9990b90\",\"12\"]";
                System.out.println(line);
                sb.append(line + "\r\n");
            }

            br.close();
            reader.close();

            // write string to file
            FileWriter writer = new FileWriter("/Users/nomouse/AliDrive/lstwork/技术文档/压测/品牌券领取压测数据");
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(sb.toString());

            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
