package nomouse.util.io;

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
}
