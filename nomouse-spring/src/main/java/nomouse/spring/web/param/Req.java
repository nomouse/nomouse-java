package nomouse.spring.web.param;

/**
 * 请求通用信息
 * Created by nomouse on 2015/1/12.
 */
public class Req {

    /**
     * api路径
     */
    private String api;

    /**
     * api版本
     */
    private String version;

    /**
     * 手机系统
     */
    private String mobileType;

    /**
     * 手机型号
     */
    private String mobileModel;

    /**
     * sim卡类型
     */
    private String simType;

    /**
     * 请求网络制式
     */
    private String networkType;

    /**
     * 访问时间
     */
    private long visitTime;

    /**
     * 返回时间
     */
    private long returnTime;

    /**
     * 请求状态
     */
    private int status;

    /**
     * 用户ID
     */
    private long uid;
}
