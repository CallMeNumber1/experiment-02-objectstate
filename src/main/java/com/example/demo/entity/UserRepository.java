package com.example.demo.entity;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * 添加用户，并返回包括数据库时间戳的用户对象
     * @param user
     * @return
     */
    public User addUser(User user) {
        em.persist(user);
        em.refresh(user);           // 强制数据库中数据同步到受管对象（基于主键）
        return user;
    }

    /**
     * 添加地址，并指定地址对应的用户
     * @param address
     * @param uid
     * @return
     */
    public Address addAddress(Address address, int uid) {
        User u = em.find(User.class, uid);
        address.setUser(u);
        em.persist(address);
        return address;
    }

    /**
     * 更新指定ID用户的姓名
     * @param uid
     * @param newName
     * @return
     */
    public User updateUser(int uid, String newName) {
        User user = em.find(User.class, uid);
        System.out.println("old-name:" + user.getName());
        user.setName(newName);
        return user;
    }

    /**
     * 尝试使用merge()，以及find()2种方法分别实现
     * 更新指定地址为指定用户
     * @param aid
     * @param uid
     * @return
     */
    public Address updateAddress(int aid, int uid) {
        // 1.find()实现
//        Address address = em.find(Address.class, aid);
//        User user = em.find(User.class, uid);
//        address.setUser(user);
//        return address;
        // 2.merge()实现
        Address a1 = new Address();
        a1.setId(aid);
        Address a2 = em.merge(a1);
        em.refresh(a2);
        System.out.println("old uid:" + a2.getUser().getId());
        a2.setUser(em.find(User.class, uid));
        return a2;
    }

    /**
     * 返回指定用户的全部地址，没有返回空集合，而非null
     * @param uid
     * @return
     */
    public List<Address> listAddresses(int uid) {
        User user = em.find(User.class, uid);
        List<Address> addlist = user.getAddresses();
        // 奇了怪。。这里输出的话，Test调用的时候也能正常输出
        // 这里不输出的时候，Test里调用的时候会报错
        for (Address a : addlist) {
            System.out.println(a.getDetail());
        }
        return addlist;
    }

    public void removeAddress(int aid) {
        Address address = em.find(Address.class, aid);
        em.remove(address);
        return ;
    }

    /**
     * 删除用户，设置级联操作或手动删除相关地址
     * @param uid
     */
    public void removeUser(int uid) {
        User user = em.find(User.class, uid);
        em.remove(user);
        return ;
    }
}

