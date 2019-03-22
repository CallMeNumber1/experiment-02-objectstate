package com.example.demo;


import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserRepositoryTest {
    @Autowired
    private UserRepository rep;
    @Test
    public void addUserTest() {
        User user = new User("BO");
        user = rep.addUser(user);
        System.out.println(user.getInsertTime());
    }
    @Test                       // 测试方法前一定要有注解
    public void addAddressTest() {
        Address adr = new Address("923");
        rep.addAddress(adr, 2);
    }
    @Test
    public void updateUserTest() {
        User user = rep.updateUser(1, "HC");
        System.out.println("new name:" + user.getName());
    }
    @Test
    public void updateAddressTest() {
        Address adr = rep.updateAddress(1, 2);
        System.out.println("new uid:" + adr.getUser().getId());
    }
    @Test
    public void listAddressesTest() {
        List<Address> addlist = rep.listAddresses(2);
        addlist.forEach(a -> {
            System.out.println(a.getDetail());
        });
    }
    @Test
    public void removeAddressTes() {
        rep.removeAddress(1);
    }
    @Test
    public void removeUserTest() {
        rep.removeUser(2);
    }
}
