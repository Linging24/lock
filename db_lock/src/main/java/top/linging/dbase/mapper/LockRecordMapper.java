package top.linging.dbase.mapper;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import top.linging.dbase.base.LockRecord;

/**
 * @author Linging
 * @version 1.0.0
 * @since 1.0
 */
@Repository
public interface LockRecordMapper extends Mapper<LockRecord> {
}
