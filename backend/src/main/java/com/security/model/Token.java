package com.security.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "token")
    private String token;
    @Column(name = "is_logged_out")
    private boolean is_logged_out;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isIs_logged_out() {
        return is_logged_out;
    }

    public void setIs_logged_out(boolean is_logged_out) {
        this.is_logged_out = is_logged_out;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
