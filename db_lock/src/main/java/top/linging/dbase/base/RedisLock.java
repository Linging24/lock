package top.linging.dbase.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author Linging
 * @version 1.0.0
 * @since 1.0
 */
@Component
public class RedisLock implements Lock {

    @Autowired
    private RedisTemplate redisTemplate;

    private String lockName = "lockName";

    @Override
    public void lock() {
        while(true){
            Boolean b = redisTemplate.opsForValue().setIfAbsent("redis_lock", lockName);
            if(b){
                return;
            }else{
                System.out.println("等待中......");
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {


        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        redisTemplate.delete("redis_lock");
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
