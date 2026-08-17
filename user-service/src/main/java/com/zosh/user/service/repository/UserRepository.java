package com.zosh.user.service.repository;

import com.zosh.user.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
