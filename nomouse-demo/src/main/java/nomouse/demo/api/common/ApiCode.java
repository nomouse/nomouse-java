package nomouse.demo.api.common;

/**
 * 全局返回码
 */
public class ApiCode {

    // --- 2xx Success ---

    /**
     * 成功
     */
    public static final int SUCCESS = 200;


    // --- 4xx Client Error ---

    /**
     * 请求格式错误
     */
    public static final int BAD_REQUEST = 400;
    public static final String BAD_REQUEST_MSG = "请求格式错误";

    /**
     * 参数值错误
     */
    public static final int PARAM_ERROR = 450;

    /**
     * token认证失败
     */
    public static final int UNAUTHORIZED = 401;
    public static final String UNAUTHORIZED_MSG = "用户认证失败";


    /**
     * 联系用户不存在
     */
    public static final int USER_NOT_EXIST = 451;
    public static final String USER_NOT_EXIST_MSG = "用户不存在";

    /**
     * 用户没有操作的权限
     */
    public static final int FORBIDDEN = 403;
    public static final String FORBIDDEN_MSG = "没有操作的权限";

    // --- 5xx Server Error ---
    /**
     * 服务器内部错误
     */
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final String INTERNAL_SERVER_ERROR_MSG = "服务器被外星人劫走了";

    // --- 1xxx Global Error ---

    /**
     * 提示客户端服务器正在维护
     */
    public static final int SERVER_STOPPING = 1000;
    public static final String SERVER_STOPPING_MSG = "服务器正在维护中，烦请稍候再试";

    /**
     * 提示客户端版本过低，需要升级之后才能正常使用
     */
    public static final int CLIENT_VERSION_ERROR = 1001;
    public static final String CLIENT_VERSION_ERROR_MSG = "您当期的版本过低，请升级后再来啰嗦吧";


}
