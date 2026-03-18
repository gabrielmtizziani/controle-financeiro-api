package com.gabrieltizziani.controle_financeiro.repository;

import com.gabrieltizziani.controle_financeiro.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
}
