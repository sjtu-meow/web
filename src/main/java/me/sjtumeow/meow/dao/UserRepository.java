package me.sjtumeow.meow.dao;

import me.sjtumeow.meow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends SoftDeleteRepository<User, Long> {

    User findByPhone(String phone);
}
