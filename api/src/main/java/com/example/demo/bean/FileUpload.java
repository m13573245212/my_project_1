package com.example.demo.bean;


import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import lombok.*;

import java.io.Serializable;

/**
 * 上传文件的实体类
 *
 * @author 徐林飞
 */
@Component
//全参构造
@AllArgsConstructor
//无参构造
@NoArgsConstructor
@Data
//链式的setter
@Accessors(chain = true)
public class FileUpload implements Serializable {
    private static final long serialVersionUID = 6292320194258000203L;
    private Integer Id=1;
    private String fileName;
    private String describe_file;
    private String path;
    private int isVideo;

}

