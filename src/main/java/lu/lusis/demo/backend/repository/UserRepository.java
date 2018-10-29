package lu.lusis.demo.backend.repository;

import lu.lusis.demo.backend.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Integer> {


}
