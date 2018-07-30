package com.lpf.spring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

/**
 * 女孩-实体类
 *
 * @author lipengfei
 * @create 2018-05-08 15:53
 **/
@Entity
public class Girl {

    @Id
//    @GeneratedValue()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 18, message = "未成年少女不得入内")
    private Integer age;

    private String cupSize;

    public Girl(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCupSize() {
        return cupSize;
    }

    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }
}