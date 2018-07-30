package com.lpf.spring.controller;

import com.lpf.spring.service.GirlService;
import com.lpf.spring.domain.Girl;
import com.lpf.spring.dao.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author lipengfei
 * @create 2018-05-10 13:56
 **/
@RestController
public class GirlController {

    @Autowired
    private GirlRepository girlRepository;

    @Autowired
    private GirlService girlService;

    /**
     * 查询所有女生
     *
     * @return
     */
    @GetMapping("girls")
    public List<Girl> findAll() {
        List<Girl> girlList = girlRepository.findAll();

        return girlList;
    }

    /**
     * 根据Id查询一个女生
     *
     * @param id
     * @return
     */
    @GetMapping("girls/{id}")
    public Girl findById(@PathVariable Integer id) {
        Optional<Girl> byId = girlRepository.findById(id);
        return byId.get();
    }

    /**
     * 根据年龄查询女生列表
     *
     * @param age
     * @return
     */
    @GetMapping("girls/age/{age}")
    public Page<Girl> findByAge(@PathVariable Integer age) {
        List<Girl> girlList = girlRepository.findByAgeEquals(age);

        List<Girl> girlF1 = girlRepository.findByCupSizeEndingWith("F");
        List<Girl> girlF2 = girlRepository.findByCupSizeEndsWith("F");

        // 分页接口
        Pageable pageable = PageRequest.of(0,3);
        Page<Girl> girlListWithPagr =  girlRepository.findByAgeBetweenOrderByAge(age, 100, pageable);

//        List<Girl> girlList = girlRepository.findByAgeBetweenOrderByAgeDesc(age, 100);
        return girlListWithPagr;
    }


    /**
     * 新增一个女生
     *
     * @param girl
     * @return
     */
    @PostMapping("girls/add")
    public Girl addGirl(@Valid Girl girl, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            System.out.println("新增出错，" + bindingResult.getFieldError().getDefaultMessage());
            return null;
        }

        girl.setAge(girl.getAge());
        girl.setCupSize(girl.getCupSize());

        Girl save = girlRepository.save(girl);
        return save;
    }

    /**
     * 修改一个女生
     *
     * @param age
     * @return
     */
    @PutMapping("girls/{id}")
    public Girl updateGirl(@PathVariable("id") Integer id, @RequestParam("age") Integer age, @RequestParam("cupSize") String cupSize) {

        Girl newGirl = new Girl();
        newGirl.setAge(age);
        newGirl.setId(id);
        newGirl.setCupSize(cupSize);

        Girl save = girlRepository.save(newGirl);
        return save;
    }

    /**
     * 修改一个女生
     *
     * @param id
     * @return
     */
    @DeleteMapping("girls/{id}")
    public String updateGirl(@PathVariable("id") Integer id) {

        girlRepository.deleteById(id);
        return "yes";
    }


    /**
     * 新增两个女生，测试事务
     * @return
     */
    @GetMapping("girls/two")
    public String saveTwo(){
        girlService.insertTwo();
        return "yes";
    }
}
