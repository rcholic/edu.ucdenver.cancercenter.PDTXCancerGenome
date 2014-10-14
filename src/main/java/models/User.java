package models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tonywang on 10/14/14.
 * Use email as login
 *
 */
@Entity
@Table (name = "pdtx_users")
public class User implements Serializable
{
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "user_id", unique = true, nullable = false)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "access_level")
    private int accessLevel;

    public User() {}

    public User(String firstName, String lastName, String email, String password, String address, int accessLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = getMD5Hash(password);
        this.address = address;
        this.accessLevel = accessLevel;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = getMD5Hash(password);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    private static String getMD5Hash(String password)
    {
        String hashPassword = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            hashPassword = number.toString(16);

            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashPassword.length() < 32) {
                hashPassword = "0" + hashPassword;
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashPassword;

    }
}
