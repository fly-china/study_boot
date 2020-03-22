package com.lpf.spring.controller;

import com.lpf.spring.common.ResultModel;
import com.lpf.spring.dao.GirlRepository;
import com.lpf.spring.domain.Girl;
import com.lpf.spring.service.GirlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "用户管理")
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
    @ApiOperation("查询所有用户")
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
    @ApiOperation(value = "通过ID查询", notes = "请遵循参数传递格式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path"),
    })
    public Girl findById(@PathVariable Integer id) {
        Optional<Girl> byId = girlRepository.findById(id);

        byId.ifPresent(girl -> {
            girl.setCupSize(girl.getCupSize() + "好大");
        });

        Girl defaultGirl = new Girl();
        defaultGirl.setId(999);


        return byId.orElse(defaultGirl);
    }

    /**
     * 根据年龄查询女生列表
     *
     * @param age
     * @return
     */
    @GetMapping("girls/age/{age}")
    @ApiOperation(value = "根据年龄分页查询", notes = "请传入最小年龄，最大年龄默认为100")
    public Page<Girl> findByAge(@PathVariable Integer age) {
        List<Girl> girlList = girlRepository.findByAgeEquals(age);

        List<Girl> girlF1 = girlRepository.findByCupSizeEndingWith("F");
        List<Girl> girlF2 = girlRepository.findByCupSizeEndsWith("F");

        // 分页接口
        Pageable pageable = PageRequest.of(0, 3);
        Page<Girl> girlListWithPagr = girlRepository.findByAgeBetweenOrderByAge(age, 100, pageable);

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
    @ApiOperation("新增用户信息")
    public ResultModel addGirl(@Valid Girl girl, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("新增出错，" + bindingResult.getFieldError().getDefaultMessage());
            return ResultModel.failure("新增用户失败,原因：" + bindingResult.getFieldError().getDefaultMessage());
        }

        girl.setAge(girl.getAge());
        girl.setCupSize(girl.getCupSize());

        Girl save = girlRepository.save(girl);
        return ResultModel.success("新增用户成功", save);
    }

    /**
     * 修改一个女生
     *
     * @param age
     * @return
     */
    @PutMapping("girls/{id}")
    @ApiOperation("更新单个用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, paramType = "query"),
            @ApiImplicitParam(name = "cupSize", value = "大小", required = true, paramType = "query")
    })
    public Girl updateGirl(@PathVariable("id") Integer id, @RequestParam("age") Integer age, @RequestParam("cupSize") String cupSize) {

        Girl newGirl = new Girl();
        newGirl.setAge(age);
        newGirl.setId(id);
        newGirl.setCupSize(cupSize);

        Girl save = girlRepository.save(newGirl);
        return save;
    }

    /**
     * 删除一个女生
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @ApiOperation("删除单个用户")
    @ApiImplicitParam(value = "用户ID", required = true)
    public String updateGirl(Integer id) {

        girlRepository.deleteById(id);
        return "yes";
    }


    /**
     * 新增两个女生，测试事务
     *
     * @return
     */
    @GetMapping("girls/two")
    public String saveTwo() {
        girlService.insertTwo();
        return "yes";
    }
}
