package cn.xlf.workflow.process;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程变量
 * @author 类中域:徐林飞
 * @date 2020/1/10 20:36
 */
@Data
@Accessors(chain = true)
public class Holiday implements Serializable {
    private static final long serialVersionUID = 8042335912987842645L;
    //id
    private int id;
    //请假天数
    private int num;
    //请假原因
    private String reason;
    //请假类别
    private String type;
    //申请人
    private String userName;
    //开始时间
    private LocalDateTime beginTime;
    //结束时间
    private LocalDateTime endTime;
}
