package org.Entity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xcyb
 * @date 2022/9/29 15:00
 */
public class Addr {

    int id;
    String city;
    Set<User> users = new HashSet<>();

    public Addr(){
        super();
    }

    public Addr(int id, String city){
        super();
        this.id = id;
        this.city = city;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public String toString() {
        return "AddrMapper{" +
                "id=" + id +
                ", city='" + city + '\'' +
                '}';
    }
}


