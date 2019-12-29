//package com.example.demo.config;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * 测试定时任务
// * @author 类中域:徐林飞
// */
//
//@Component
//public class TimeServer {
//    @Scheduled(cron = "0/5 * * * * *")
//    public void scheduled() {
//        System.out.println("=====>>>>>使用cron  {" + System.currentTimeMillis() + "}");
//    }
//
//    @Scheduled(fixedRate = 5000)
//    public void scheduled1() {
//        System.out.println("=====>>>>>使用fixedRate{" + System.currentTimeMillis() + "}");
//    }
//
//    @Scheduled(fixedDelay = 5000)
//    public void scheduled2() {
//        System.out.println("=====>>>>>fixedDelay{" + System.currentTimeMillis() + "}");
//    }
//}
