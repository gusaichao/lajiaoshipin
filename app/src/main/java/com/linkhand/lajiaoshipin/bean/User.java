package com.linkhand.lajiaoshipin.bean;

public class User {


    /**
     * code : 200
     * info : 登陆成功
     * data : {"id":"10","user_type":"2","sex":"0","birthday":"0","last_login_time":"1574839875","score":"0","coin":"0","balance":"0.00","create_time":"1574756354","user_status":"1","user_login":"","user_pass":"###54c4330528311a252d88f8c2accba2d3","user_nickname":"","user_email":"","user_url":"","avatar":"","signature":"","last_login_ip":"211.143.49.61","user_activation_key":"","mobile":"15708925561","more":null,"recommender":"0","watch_number":"1","watch_time":"0"}
     */

    private int code;
    private String info;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 10
         * user_type : 2
         * sex : 0
         * birthday : 0
         * last_login_time : 1574839875
         * score : 0
         * coin : 0
         * balance : 0.00
         * create_time : 1574756354
         * user_status : 1
         * user_login :
         * user_pass : ###54c4330528311a252d88f8c2accba2d3
         * user_nickname :
         * user_email :
         * user_url :
         * avatar :
         * signature :
         * last_login_ip : 211.143.49.61
         * user_activation_key :
         * mobile : 15708925561
         * more : null
         * recommender : 0
         * watch_number : 1
         * watch_time : 0
         */

        private String id;
        private String user_type;
        private String sex;
        private String birthday;
        private String last_login_time;
        private String score;
        private String coin;
        private String balance;
        private String create_time;
        private String user_status;
        private String user_login;
        private String user_pass;
        private String user_nickname;
        private String user_email;
        private String user_url;
        private String avatar;
        private String signature;
        private String last_login_ip;
        private String user_activation_key;
        private String mobile;
        private Object more;
        private String recommender;
        private String watch_number;
        private String watch_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public String getUser_login() {
            return user_login;
        }

        public void setUser_login(String user_login) {
            this.user_login = user_login;
        }

        public String getUser_pass() {
            return user_pass;
        }

        public void setUser_pass(String user_pass) {
            this.user_pass = user_pass;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_url() {
            return user_url;
        }

        public void setUser_url(String user_url) {
            this.user_url = user_url;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public String getUser_activation_key() {
            return user_activation_key;
        }

        public void setUser_activation_key(String user_activation_key) {
            this.user_activation_key = user_activation_key;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getMore() {
            return more;
        }

        public void setMore(Object more) {
            this.more = more;
        }

        public String getRecommender() {
            return recommender;
        }

        public void setRecommender(String recommender) {
            this.recommender = recommender;
        }

        public String getWatch_number() {
            return watch_number;
        }

        public void setWatch_number(String watch_number) {
            this.watch_number = watch_number;
        }

        public String getWatch_time() {
            return watch_time;
        }

        public void setWatch_time(String watch_time) {
            this.watch_time = watch_time;
        }
    }
}
