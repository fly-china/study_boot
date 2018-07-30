package com.lpf.spring.dao;

import com.lpf.spring.domain.Girl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lipengfei
 * @create 2018-05-10 13:14
 **/
public interface GirlRepository extends JpaRepository<Girl, Integer> {

    List<Girl> findByAgeEquals(Integer age);

    List<Girl> findByAgeBetweenOrderByAgeDesc(Integer beginAfter, Integer afterAge);

    List<Girl> findByCupSizeEndingWith(String cupSize);

    List<Girl> findByCupSizeEndsWith(String cupSize);

    Page<Girl> findByAgeBetweenOrderByAge(Integer beginAfter, Integer afterAge, Pageable pageable);

}
