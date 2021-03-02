package in.stack.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import in.stack.boot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
	User findByUserIdAndPassword(String userId,String password);
}
