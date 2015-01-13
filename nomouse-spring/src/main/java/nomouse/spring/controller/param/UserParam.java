package nomouse.spring.controller.param;

import javax.validation.constraints.NotNull;

/**
 * Created by nomouse on 2014/12/10.
 */
public class UserParam {

    @NotNull
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
