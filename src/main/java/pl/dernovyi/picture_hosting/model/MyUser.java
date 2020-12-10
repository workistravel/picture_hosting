package pl.dernovyi.picture_hosting.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private boolean isEnabled;

    @Transient
    private String passwordConfirm;
    @Transient
    private String transRole;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Picture> picturesSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,  cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new HashSet<>();




    public MyUser() {
    }

    public Set<Picture> getPictures() {
        return picturesSet;
    }

    public void setPictures(Set<Picture> pictures) {
        this.picturesSet = pictures;
    }
    public void addPicture(Picture picture){
        this.picturesSet.add(picture);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getTransRole() {
        return transRole;
    }

    public void setTransRole(String transRole) {
        this.transRole = transRole;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRoles(Role role) {
        this.roles.add(role);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
