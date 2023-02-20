package com.sample.investorsquo.repository;

import com.sample.investorsquo.domain.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @RestResource
    Optional<User> findUserByEmailIgnoreCase(String email);

//    @Override
//    @RestResource(exported = false)
//    <S extends User> S save(S entity);
//
//    @Override
//    @RestResource(exported = false)
//    void deleteById(String id);
}
