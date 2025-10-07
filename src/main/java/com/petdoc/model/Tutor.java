package com.petdoc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tutor")
public class Tutor implements UserDetails { // <-- MUDANÇA IMPORTANTE AQUI

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @ToString.Exclude
    @OneToMany(mappedBy = "tutor")
    private List<Pet> pets;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Para o MVP, ficou definida uma role padrão "USER".
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email; // No nosso caso, o username é o e-mail.
    }

    @Override
    public boolean isAccountNonExpired() {
        // Para o MVP, não temos lógica de expiração de conta. Retornamos true.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Para o MVP, não temos lógica de bloqueio de conta. Retornamos true.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Para o MVP, não temos lógica de expiração de credenciais. Retornamos true.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Para o MVP, não temos lógica de conta desativada. Retornamos true.
        return true;
    }
}