<<<<<<< HEAD:src/main/java/com/example/demo/domain/User/UserRepository.java
package com.example.demo.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByUserName(String userName);
    User findByPhoneNumber(String phoneNumber);
=======
package com.example.demo.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByUserName(String userName);
    User findByPhoneNumber(String phoneNumber);

>>>>>>> b6b88a1f87b098b9cc0f0df6c91bb3b67b71b298:src/main/java/com/example/demo/User/UserRepository.java
}