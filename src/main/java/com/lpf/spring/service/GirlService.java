package com.lpf.spring.service;

import com.lpf.spring.domain.Girl;
import com.lpf.spring.dao.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lipengfei
 * @create 2018-07-27 15:39
 **/
@Service
public class GirlService {

    @Autowired
    private GirlRepository girlRepository;

    @Transactional
    public void insertTwo(){
        Girl girlA = new Girl();
        girlA.setAge(18);
        girlA.setCupSize("A");
        girlRepository.save(girlA);


        int i = 1/0;
        Girl girlB = new Girl();
        girlB.setAge(20);
        girlB.setCupSize("BBBBBBBB");
        girlRepository.save(girlB);

    }

}
