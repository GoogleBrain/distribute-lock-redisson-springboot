package cn.k.distributelockredissionspringboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class RedissonController {

    @Resource
    private RedissonClient redissonClient;

    @RequestMapping("/king")
    public String hello(){


        log.info("进入方法");
        RLock order = redissonClient.getLock("order");
        try {
            order.lock(30, TimeUnit.SECONDS);
            log.info("我获得了锁。。。。");
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            log.info("我释放了锁。。。。");
            order.unlock();
        }
        log.info("方法执行完成");

        return "hello";
    }
}
