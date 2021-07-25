package top.linging.dbase.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Linging
 * @version 1.0.0
 * @since 1.0
 */
@Data
@Table(name = "lock_record")
public class LockRecord implements Serializable {

    @Id
    private Integer id;

//    private String lockName;
    private String lock_name;

}
