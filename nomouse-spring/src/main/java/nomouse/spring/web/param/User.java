package nomouse.spring.web.param;

import javax.validation.constraints.NotNull;

/**
 * Created by nomouse on 2014/12/10.
 */
public class User<T> {

    @NotNull(message = "not be null")
    private long id;

    @NotNull(message = "not be null")
    private String name;

    private T t;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
