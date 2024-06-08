package com.goit.url.V2;

import com.goit.auth.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findById(Long id);
    Optional<Url> findByShortId(String shortId);
    Optional<Url> findByShortIdAndUser(String shortId, User user);
    List<Url> findAllByUser(User user);

    @Query("SELECT u FROM Url u WHERE u.user = :user AND u.expiryDate > :now")
    List<Url> findActiveUrlsByUserId(@Param("user") User user, @Param("now") LocalDateTime now);

    @Query(value = "SELECT MAX(id) FROM urls", nativeQuery = true)
    Optional<Long> getMaxId();

    @Modifying
    @Transactional
    @Query("UPDATE Url u SET u.clickCount = u.clickCount + 1 WHERE u.shortId = :shortId")
    void incrementClickCount(@Param("shortId") String shortId);

    @Modifying
    @Transactional
    int deleteUrlByShortIdAndUser(String shortId, User user);
}
