package com.hyun.topfeed.repository;

import com.hyun.topfeed.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

  Optional<User> findUserByEmail(String email);

  boolean existsUserByEmail(String email);

  List<User> findAllByStatusTrue();
}