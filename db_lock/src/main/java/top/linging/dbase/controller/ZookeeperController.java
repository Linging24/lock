package top.linging.dbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.linging.dbase.base.ZookeeperLock;

import java.util.concurrent.TimeUnit;

/**
 * @author Linging
 * @version 1.0.0
 * @since 1.0
 */
@RestController
public class ZookeeperController {

    private static ZookeeperLock lock = new ZookeeperLock();

    private int num = 1;

    @GetMapping("/zookeeperLock")
    public String zookeeperLock(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                if(num > 0){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    num--;
                    System.out.println(Thread.currentThread().getName() + "，减少成功！！！" + num);
                }else{
                    System.out.println(Thread.currentThread().getName() + "，减少失败！！！");
                }
                lock.unlock();
            }
        };
        new Thread(task,"线程1").start();
        new Thread(task,"线程2").start();
        return "ok";
    }
}