package org.Mapper;

import org.Entity.Addr;
import org.Entity.Page;
import org.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xcyb
 * @date 2022/10/18 16:02
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void getAll(Page page){
        page.setSum(userDao.UserQueryCount());
        page.setList(userDao.getAll(page));
    }

    @Transactional//spring事务
    public void UserAdd(User user){
        userDao.UserAdd(user);
    }

    @Transactional
    public void UserDelete(User user){
        userDao.UserDelete(user);
    }

    @Transactional
    public User UserEdit_Query(int id){
        return userDao.UserEdit_Query(id);
    }

    @Transactional
    public void UserEdit(User user){
        userDao.UserEdit(user);
    }

    @Transactional
    public List<Addr> getAddr(){
        List<Addr> list = userDao.getAddr();
        return list;
    }


    /**
     * @Test
     * public void testQueryUser(){
     *         User user = new User();
     *         user.setName("myName");
     *
     * @RunWith(SpringJUnit4ClassRunner.class) //使用Springtest测试框架
     * @ContextConfiguration("/spring.xml") //加载配置
     *
     *         userMapper.UserAdd(user);
     *                 }
     */


}
