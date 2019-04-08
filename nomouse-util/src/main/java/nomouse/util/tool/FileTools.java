package nomouse.util.tool;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * 文件工具，用于处理文本
 * Created by nomouse on 18/8/3.
 */
public class FileTools {

    public static void main(String[] args) {
        try {
            // read file content from file
            StringBuffer sb = new StringBuffer("");

            FileReader reader = new FileReader("/Users/nomouse/Java/1.txt");
            BufferedReader br = new BufferedReader(reader);

            String str = null;

            while ((str = br.readLine()) != null) {

                //JSONObject object = JSON.parseObject(str);
                int start = StringUtils.indexOf(str, "redEnvelopeId") + 16;
                String out = str.substring(start, start + 16);

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
