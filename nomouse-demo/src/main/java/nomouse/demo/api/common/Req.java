package nomouse.demo.api.common;

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
     * token
     */
    private String token;

    /**
     * 手机系统
     */
    private String os;

    /**
     * 手机型号
     */
    private String model;

    /**
     * sim卡类型
     */
    private String sim;

    /**
     * 请求网络制式
     */
    private String net;


    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public Req() {
    }

    public Req(String api, String version, String token, String os, String model, String sim, String net) {
        this.api = api;
        this.version = version;
        this.token = token;
        this.os = os;
        this.model = model;
        this.sim = sim;
        this.net = net;
    }

    /**
     * 用户ID
     */
    protected long uid;

    /**
     * 访问时间
     */
    protected long visitTime;

    /**
     * 返回时间
     */
    protected long returnTime;

    /**
     * 请求状态
     */
    protected int status;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(long visitTime) {
        this.visitTime = visitTime;
    }

    public long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
