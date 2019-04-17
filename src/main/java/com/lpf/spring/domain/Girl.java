package com.lpf.spring.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel
public class Girl {

    @Id
//    @GeneratedValue()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("用户ID")
    private Integer id;

    @Min(value = 18, message = "未成年少女不得入内")
    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("大小")
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
