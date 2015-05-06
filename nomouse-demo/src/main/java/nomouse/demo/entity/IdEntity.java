package nomouse.demo.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 主键默认为自增ID,非自增ID不要继承此类
 */
@MappedSuperclass
public abstract class IdEntity implements Serializable {

    /**
     * 行状态：正常
     */
    public static final int STATUS_NORMAL = 1;

    /**
     * 行状态：标记为删除
     */
    public static final int STATUS_DELETE = -1;

    private static final long serialVersionUID = -5483228362238099915L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}