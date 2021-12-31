package cyderx.com.map.data;

/**
 * 用户类型
 */
public class User {
    private String fullName;
    private String username;
    private String password;
    private String gender;
    private String phone;
    private String email;
    private String province;
    private String city;
    private String birthday;
    private String interests;
    private String comment;

    public User() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "姓名：" + fullName + '\n' +
                "账号：" + username + '\n' +
                "密码：" + password + '\n' +
                "性别：" + gender + '\n' +
                "电话：" + phone + '\n' +
                "邮箱：" + email + '\n' +
                "省份：" + province + '\n' +
                "城市：" + city + '\n' +
                "生日：" + birthday + '\n' +
                "兴趣：" + interests + '\n' +
                "自我评价：" + comment;
    }
}
