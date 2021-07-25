package top.linging.dbase.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import top.linging.dbase.mapper.LockRecordMapper;
import top.linging.dbase.model.LockRecord;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Linging
 * @version 1.0.0
 * @since 1.0
 */
@Component
public class DBLock implements Lock {

    private String lockName = "lockName";

    @Autowired
    private LockRecordMapper lockRecordMapper;

    /**
     * 加锁，即往数据库插入一条记录
     */
    @Override
    public void lock() {
        while(true){
            if(tryLock()){  //获取到锁
                LockRecord lockRecord = new LockRecord();
                lockRecord.setLock_name(lockName);
                try{
                    int insert = lockRecordMapper.insert(lockRecord);
                    return;
                }catch (DuplicateKeyException e){
                    System.out.println(Thread.currentThread().getName() + ", 获取锁失败");
                }
            }else{
                System.out.println("等待锁........");
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    /**
     * 尝试获取锁，查询数据库是否存在锁记录
     * @return
     */
    @Override
    public boolean tryLock() {
        Example example = new Example(LockRecord.class);
        example.createCriteria().andEqualTo("lock_name", lockName);
        LockRecord lockRecord = lockRecordMapper.selectOneByExample(example);
        return lockRecord == null;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        LockRecord lockRecord = new LockRecord();
        lockRecord.setLock_name(lockName);
        lockRecordMapper.delete(lockRecord);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
