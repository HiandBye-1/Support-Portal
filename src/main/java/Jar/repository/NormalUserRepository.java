package Jar.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import Jar.entities.NormalUser;

import java.util.Optional;//if no user found

public interface NormalUserRepository extends JpaRepository<NormalUser, Integer> {

    public Optional<NormalUser> findByUsername(String username);
}