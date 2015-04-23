package nomouse.demo.repository;

import nomouse.demo.entity.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface RecordDao extends CrudRepository<Record, Long> {

    @Query(value = "select t from Record t where t.name=?1")
    public List<Record> findByName(String name);

    public List<Record> findByPage(Pageable pageable);
}
