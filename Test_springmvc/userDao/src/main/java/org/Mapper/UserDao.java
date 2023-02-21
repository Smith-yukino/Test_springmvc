package org.Mapper;

import org.Entity.Addr;
import org.Entity.Page;
import org.Entity.User;
import org.Mappers.AddrMapper;
import org.Mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xcyb
 * @date 2022/10/18 16:02
 *
 * 实现两个接口
 */
@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddrMapper addrMapper;


    public List<User> getAll(Page page) {
        int sIndex = (page.getPageNo() - 1) * page.getPageSize();
        page.setsIndex(sIndex);
        return userMapper.getAll(page);
    }

    public int UserQueryCount() {
        return userMapper.UserQueryCount();
    }

    public void UserAdd(User user) {
        userMapper.UserAdd(user);
    }

    public User UserEdit_Query(int id) {
        return userMapper.UserEdit_Query(id);
    }

    public void UserEdit(User user) {
        userMapper.UserEdit(user);
    }

    public void UserDelete(User user) {
        userMapper.UserDelete(user);
    }

    public List<Addr> getAddr() {
            return addrMapper.getAddr();
    }

}
