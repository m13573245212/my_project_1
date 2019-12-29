package com.example.demo.bean;


import lombok.Data;
import org.springframework.stereotype.Component;
import java.io.Serializable;

/**
 * @author 将来的大拿:徐林飞
 */
@Component
@Data
public class Picture implements Serializable {
    private static final long serialVersionUID = 621425411761087538L;
    private Integer ID;
    private String picture_name;
    private Integer md_master;

}




