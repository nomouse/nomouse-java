package nomouse.spring.api.common;

/**
 * Created by nomouse on 2015/3/19.
 */
public class Res<T> {

    public int code;
    public String msg;
    public T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
