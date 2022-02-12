package com.epam.library.entity.user;

import com.epam.library.entity.user.constant.Role;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity of user data for session
 */
public class SessionUser implements Serializable {
    @Serial
    private static final long serialVersionUID = -1184459857120588008L;

    private int id;
    private Role role;
    private String nickname;

    public SessionUser() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionUser that = (SessionUser) o;
        return id == that.id && role == that.role && Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, nickname);
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "id=" + id +
                ", role=" + role +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
