package com.perkpal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee extends BaseEntity implements UserDetails {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "designation", nullable = false)
    private String designation;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "is_manager", nullable = false)
    private boolean isManager;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employees")
    @JoinColumn(name = "du_id", referencedColumnName = "id", nullable = false)
    Du duId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employeeSet")
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    Role roleId;
    @Column(name = "total_points")
    private Long totalPoints;
    @Column(name = "redeemable_points")
    private Long redeemablePoints;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employeeClub")
    @JoinColumn(name = "club_id", referencedColumnName = "id", nullable = false)
    Club clubId;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "last_login")
    private Timestamp lastLogin;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, targetEntity = Participation.class)
    private Set<Participation> participation = new HashSet<>();
    @OneToMany(mappedBy = "approvedBy", cascade = CascadeType.ALL, targetEntity = Participation.class)
    private Set<Participation> participationApproved = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (roleId != null) {
            authorities.add(new SimpleGrantedAuthority(roleId.getRoleName()));
        }
        return authorities;
    }


    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
