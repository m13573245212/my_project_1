package com.example.demo.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author 将来的大拿:徐林飞
 */
@Component
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = -6518986075082734558L;
    private Integer id;
    private String userName;
    private String password;
    private String userPath;

}
