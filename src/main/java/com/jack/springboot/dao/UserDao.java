package com.jack.springboot.dao;

import com.jack.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Long> {

    public User findByUsername(String userName);

    @Query(value = "select * from user where id= ?1",nativeQuery=true)
    public User findUserById(int id);

    public void deleteById(Long id);

}