/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capamodel;

/**
 *
 * @author Juan Antonio
 */
public class User {
    private String login;
    private String email;
    private String pwd;
    
    public User(){
        login = "";
        email = "";
        pwd = "";
    }

    public User(String login, String email, String pwd) {
        this.login = login;
        this.email = email;
        this.pwd = pwd;
    }

    public User(String login, String email) {
        this.login = login;
        this.email = email;
        this.pwd = "";
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }  
    
    @Override
    public boolean equals(Object obj){
        return obj == this;
    }

    @Override
    public String toString(){
        return "[ "+login+", "+email+" ]";
    }
    
    
    
}
