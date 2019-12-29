package com.example.demo.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author 类中域:徐林飞
 */
@Data
@Accessors(chain = true)
public class Node implements Serializable {
    private static final long serialVersionUID = -7614360061285118162L;
    private Integer id;
    private Integer fartherId;
    private String nodeId;
    private String nodeText;
    private Integer masterId;
    private String des;
}
