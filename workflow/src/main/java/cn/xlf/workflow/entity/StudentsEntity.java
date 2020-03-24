package cn.xlf.workflow.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 类中域:徐林飞
 * @date 2020/3/9 14:25
 */
@Entity
@Table(name="STUDENTS")
@Data
public class StudentsEntity  {
    @Id
    @Column(name="ID")
    private String Id;
    @Column(name="NAME")
    private String Name;
    @Column(name="SEX")
    private String Sex;
    @Column(name="ADDRESS")
    private String Address;
    @Column(name = "PHONE")
    private String phone;

}
