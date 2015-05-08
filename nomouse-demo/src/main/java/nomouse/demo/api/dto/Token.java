package nomouse.demo.api.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel
public class Token {
    @ApiModelProperty(value = "票据")
    public String token;
}
