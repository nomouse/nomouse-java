package nomouse.demo.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 通用返回信息，每一个web响应都是此结构
 */
@ApiModel(description = "公用响应体")
public class Res<T> {

    @ApiModelProperty("业务返回码,200表示成功")
    public int code;

    @ApiModelProperty("服务器时间")
    public long time = System.currentTimeMillis();

    @ApiModelProperty("异常信息")
    @JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
    public String msg;

    @ApiModelProperty("业务数据")
    @JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
    public T result;

    @ApiModelProperty("业务数据的总数量用于分页")
    @JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
    public long totalCount;

    /**
     * 默认是成功
     */
    public Res() {
        this.code = ApiCode.SUCCESS;
    }

    /**
     * 正确返回
     *
     * @param result
     */
    public Res(T result) {
        this.code = ApiCode.SUCCESS;
        this.result = result;
    }

    /**
     * 错误返回
     *
     * @param code
     * @param msg
     */
    public Res(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
