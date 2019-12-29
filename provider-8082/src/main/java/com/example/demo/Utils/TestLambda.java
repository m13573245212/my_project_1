package com.example.demo.Utils;

import com.example.demo.bean.User;

import java.util.*;
import java.util.function.Function;

/**
 * @author 类中域:徐林飞
 * @date 2019/12/23 13:46
 */
public class TestLambda {
    public static void a(User u){
        Function<User, String> getAge = User::getUserName;
        String userName=getAge.apply(u);
        System.out.println(userName);
    }
    public static void main(String[] args) {
        User u=new User();
        u.setUserName("111111111111111");
        a(u);
    }
    public void b(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        for (Integer n : list) {
            System.out.println(n);
        }

        // 使用 -> 的 Lambda 表达式
        list.forEach(n -> System.out.println(n));

        // 使用 :: 的 Lambda 表达式
        list.forEach(System.out::println);
    }
}
