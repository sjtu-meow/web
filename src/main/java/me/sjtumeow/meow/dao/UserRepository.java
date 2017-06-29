package me.sjtumeow.meow.dao;

import me.sjtumeow.meow.model.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends SoftDeleteRepository<User, Long> {
	
	Iterable<User> findAllActive();
	
	User findById(Long id);

    User findByPhone(String phone);
}
