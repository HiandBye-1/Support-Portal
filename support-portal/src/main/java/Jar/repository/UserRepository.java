package Jar.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import Jar.User;
import java.util.Optional;//if no user found

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUsername(String username);
}