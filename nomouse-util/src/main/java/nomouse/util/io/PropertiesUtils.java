package nomouse.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 *
 */
public class PropertiesUtils {

    public static final String ACTIVE_PROFILE = "spring.profiles.active";
    public static final String DEFAULT_PROFILE = "spring.profiles.default";

    public static final String PRO = "pro";
    public static final String DEV = "dev";
    public static final String REMOTE = "remote";
    public static final String TEST = "test";

    /**
     * 当前是否为生产环境
     *
     * @return
     */
    public static final boolean isPro() {
        return DEV.equals(System.getProperty(ACTIVE_PROFILE));
    }

    private static void loadProperties(Properties properties, String path) {
        if (properties == null) {
            return;
        }
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            properties.load(reader);
        } catch (Exception e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
