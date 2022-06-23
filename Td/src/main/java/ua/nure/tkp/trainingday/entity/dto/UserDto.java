package ua.nure.tkp.trainingday.entity.dto;

public class UserDto {
    private String login;
    private String password;
    private int age;
    private String nickname;


    public UserDto() {
    }

    public UserDto(String login, String password, int age, String nickname) {
        this.login = login;
        this.password = password;
        this.age = age;
        this.nickname = nickname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
