package com.security.repo;

import com.security.model.Token;
import com.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer> {
    @Query(value = """
            SELECT t FROM Token t
            WHERE t.user.id = :userId and
            t.is_logged_out = false
            """)
    List<Token> findAllTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
