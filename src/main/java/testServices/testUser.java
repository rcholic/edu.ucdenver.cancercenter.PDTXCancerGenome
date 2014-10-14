package testServices;

import dao.UserDao;
import models.User;

/**
 * Created by tonywang on 10/14/14.
 */
public class testUser
{
    private static UserDao userDao = new UserDao();

    public static void main(String[] args) {
        User adminUser = new User();

        adminUser.setAccessLevel(0); // 0 being the highest level
        adminUser.setFirstName("Tony");
        adminUser.setLastName("Wang");
        adminUser.setAddress("RC1 South Building, 8th Floor, Tan/Jimeno Labs");
        adminUser.setPassword("admin");
        adminUser.setEmail("Guoliang.Wang@UCDenver.edu");

        userDao.saveUser(adminUser);


    }
}
