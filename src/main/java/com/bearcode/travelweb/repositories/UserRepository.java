package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email) ;
    User getUserById(Long id);
    Optional<User> findById(Long id) ;

}
