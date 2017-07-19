package me.sjtumeow.meow.dao;

import me.sjtumeow.meow.model.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends SoftDeleteRepository<User, Long> {

    User findById(Long id);

    User findByPhone(String phone);

    @Query("from User uu where id in (select u.id from User u, Profile p where u.id = p.id and (u.id = :param1 or phone like :param2 or nickname like :param2))")
    List<User> findByKeyword(@Param("param1") Long id, @Param("param2") String keyword);

    @Query("from User uu where id in (select u.id from User u, Profile p where u.id = p.id and (u.id = :param1 or phone like :param2 or nickname like :param2))")
    List<User> findByKeyword(@Param("param1") Long id, @Param("param2") String keyword, Sort sort);

    @Query("from User uu where id in (select u.id from User u, Profile p where u.id = p.id and (u.id = :param1 or phone like :param2 or nickname like :param2))")
    Page<User> findByKeyword(@Param("param1") Long id, @Param("param2") String keyword, Pageable pageable);

    @Query("from User uu where id in (select u.id from User u, Profile p where u.id = p.id and u.deletedAt is null and (u.id = :param1 or phone like :param2 or nickname like :param2))")
    List<User> findByKeywordActive(@Param("param1") Long id, @Param("param2") String keyword);

    @Query("from User uu where id in (select u.id from User u, Profile p where u.id = p.id and u.deletedAt is null and (u.id = :param1 or phone like :param2 or nickname like :param2))")
    List<User> findByKeywordActive(@Param("param1") Long id, @Param("param2") String keyword, Sort sort);

    @Query("from User uu where id in (select u.id from User u, Profile p where u.id = p.id and u.deletedAt is null and (u.id = :param1 or phone like :param2 or nickname like :param2))")
    Page<User> findByKeywordActive(@Param("param1") Long id, @Param("param2") String keyword, Pageable pageable);
}
