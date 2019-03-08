package com.jack.springboot.service;

import com.jack.springboot.dao.UserDao;
import com.jack.springboot.domain.User;
import com.jack.springboot.enums.ResultEnum;
import com.jack.springboot.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserDao userDao;

    public List<User> queryAll() {
        return userDao.findAll();
    }

    public User queryUserByUsername(String userName){

        return userDao.findByUsername(userName);
    }

    public int insertUser(User user) {
        this.userDao.save(user);
        return 0;
    }
    public User queryUserById(int id){
       return this.userDao.findUserById(id);
    }

    public User queryUserById2(Long id) throws Exception

    {
        Optional op = this.userDao.findById(id);
        if(op!=null && op.isPresent())
            return this.userDao.findById(id).get();
        else
            throw new CustomException(ResultEnum.USERISEXIST);
    }

    public User updateUser(User user){
        return this.userDao.save(user);
    }

    public void deleteUser(Long id){
         this.userDao.deleteById(id);
    }

}
