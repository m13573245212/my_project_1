package com.example.lbrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 实现自定义的负载均衡算法
 * 服务的每个实例调用5次后切换
 * @author 类中域:徐林飞
 */
public class MyRule extends AbstractLoadBalancerRule {

    //调用次数
    private int total=0;
    //调用的机器编号
    private int currentIndex=0;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        return choose(getLoadBalancer(), o);
    }

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();
            List<Long> sl=new ArrayList<>();
            //遍历upList,按照ID(主机和端口号)进行排序
            for (Server u : upList) {
                //去掉ID中的符号
                String l=u.getId().replaceAll(":","").replaceAll("\\.","");
                sl.add(Long.parseLong(l));
            }
            //对sl进行排序
            Collections.sort(sl);

            //定义一个新的Server集合，用来进行排序
            List<Server> list=new ArrayList<>();
            for (Long s : sl) {
                for (Server ser: upList) {
                    if(s==Long.parseLong(ser.getId().replaceAll(":","").replaceAll("\\.",""))){
                        list.add(ser);
                    }
                }
            }

            int serverCount = allList.size();
            if (serverCount == 0) {
                /*
                 * 没有服务可以被使用
                 */
                return null;
            }

            if(total<5){
                server=list.get(currentIndex);
                total++;
            }else{
                total=0;
                currentIndex++;
                if(currentIndex>=upList.size()){
                    currentIndex=0;
                }
            }

            if (server == null) {
                /*
                 * The only time this should happen is if the server list were
                 * somehow trimmed. This is a transient condition. Retry after
                 * yielding.
                 */
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }

        return server;

    }
}
