package top.linging.dbase;

import jdk.nashorn.internal.ir.Block;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import top.linging.dbase.base.DBLock;
import top.linging.dbase.base.LockRecord;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在分布式环境下,单节点的锁不能保证所有节点的被锁
 * @author Linging
 * @version 1.0.0
 * @since 1.0
 */
public class Main {

    private static int num = 1;

    private static Lock lock = new ReentrantLock();

    static class ReduceLock implements Runnable{

        @SneakyThrows
        @Override
        public void run() {
            lock.lock();
            if(num > 0){
                TimeUnit.SECONDS.sleep(1);
                num--;
                System.out.println(Thread.currentThread().getName() + "，减少成功！！！" + num);
            }else{
                System.out.println(Thread.currentThread().getName() + "，减少失败！！！");
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReduceLock reduceLock = new ReduceLock();
        new Thread(reduceLock,"线程1").start();
        new Thread(reduceLock,"线程2").start();
    }
}
