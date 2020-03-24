package cn.xlf.my_urule_pro.entity;

import com.bstek.urule.model.Label;

/**
 * 创建一个实体类,测试库文件
 * @author 类中域:徐林飞
 * @date 2019/12/29 16:56
 */
public class Customer {
    @Label("姓名")
    private String name;
    @Label("电话")
    private String phone;
    @Label("地址")
    private String address;
    @Label("性别")
    private String sex;
    @Label("邮箱")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
