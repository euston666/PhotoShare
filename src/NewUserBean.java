package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class NewUserBean {
    private String email = "";
    private String password1 = "";
    private String password2 = "";
    private String first = "";
    private String last = "";
    private String gender = "";
    private String birth = "";
    private String edu = "";
    private String hometowncity = "";
    private String hometownstate = "";
    private String hometowncountry = "";
    private String currentcity = "";
    private String currentstate = "";
    private String currentcountry = "";

    public String saySomething() {
        System.out.println("Hello!");
        return "Test";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getGender() {
        return gender;
    }

    public String getBirth() {
        return birth;
    }

    public String getEdu() {
        return edu;
    }

    public String getHometowncity() {
        return hometowncity;
    }

    public String getHometownstate() {
        return hometownstate;
    }

    public String getHometowncountry() {
        return hometowncountry;
    }

    public String getCurrentcity() {
        return currentcity;
    }

    public String getCurrentstate() {
        return currentstate;
    }

    public String getCurrentcountry() {
        return currentcountry;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public void setHometowncity(String hometowncity) {
        this.hometowncity = hometowncity;
    }

    public void setHometownstate(String hometownstate) {
        this.hometownstate = hometownstate;
    }

    public void setHometowncountry(String hometowncountry) {
        this.hometowncountry = hometowncountry;
    }

    public void setCurrentcity(String currentcity) {
        this.currentcity = currentcity;
    }

    public void setCurrentstate(String currentstate) {
        this.currentstate = currentstate;
    }

    public void setCurrentcountry(String currentcountry) {
        this.currentcountry = currentcountry;
    }
}
