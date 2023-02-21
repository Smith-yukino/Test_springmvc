package org.Mappers;

import org.Entity.Page;
import org.Entity.User;

import java.util.List;

/**
 * @author xcyb
 * @date 2022/12/7 15:43
 */
public interface UserMapper {

    List<User> getAll(Page page);

    int UserQueryCount();

    void UserAdd(User user);

    User UserEdit_Query(int id);

    void UserEdit(User user);

    void UserDelete(User user);

}
