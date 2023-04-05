package org.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.util.Date;

import static org.springframework.format.annotation.NumberFormat.Style.CURRENCY;

/**
 * @author xcyb
 * @date 2022/10/18 15:32
 * 加上 validation 验证，后端数据验证
 * 在要使用 validation 的地方必须在接受参数加入注解，不是mvc的功能
 *
 *         @Past 被注释的字段必须是一个过去的日期
 *      * @DateTimeFormat 数据格式化
 *      * pattern 模式（格式）
 *
 *         @Max(value = 1000) 最大 1000
 *      * NumberFormat 数据格式化
 *      * Style.CURRENCY：货币类型
 *      * Style.NUMBER：正常数字类型
 *      * Style.PERCENT：百分比类型
 *      * pattern = "##,###,###.##" 货币格式
 *
 *        Hibernate Validator 括展注解
 *      * @Email 被注释的元素 必须是一个 电子邮箱地址
 *      * @Length 被注释的元素的大小必须在指定的范围内
 *      * @NotEmpty 被注释的字符串必须不为空
 *      * @Range 被注释的元素必须在合适的范围内
 *      * @NotEmpty 既不能为空 也不能为null
 */
public class User {

    private int id;
    @NotEmpty//@NotEmpty 既不能为空 也不能为null
    private String name;
    @Length(min = 3,max = 10)//@Length 被注释的元素的大小必须在指定的范围内
    private String password;
    @Min(value = 0)
    @Max(value = 1)
    private int sex;
    @Min(value = 0)
    @Max(value = 200)//@Max(value = 200) 最大 200
    private int age;
    private Addr userCity;
    @Past//@Past 被注释的字段必须是一个过去的日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")//@DateTimeFormat 数据格式化,指定了时间格式
    private Date birth;
    @Max(value = 1000)//@Max(value = 1000) 最大 1000
    @NumberFormat(pattern = "##,###,###.##",style = CURRENCY)//NumberFormat 数据格式化,pattern = "##,###,###.##" 货币格式
    private double salary;
    @NotEmpty//@NotEmpty 既不能为空 也不能为null
    @Email//@Email 被注释的元素 必须是一个 电子邮箱地址
    private String email;
    private String path;
    private String group;

    public User(int id, String name, String password, int sex, Integer age, Addr userCity, Date birth, double salary, String email, String path){
        super();
        this.id = id;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.birth = birth;
        this.salary = salary;
        this.email = email;
        this.path = path;
        this.userCity = userCity;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public User(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Addr getUserCity() {
        return userCity;
    }

    public void setUserCity(Addr userCity) {
        this.userCity = userCity;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", userCity=" + userCity +
                ", birth=" + birth +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                ", path='" + path + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
