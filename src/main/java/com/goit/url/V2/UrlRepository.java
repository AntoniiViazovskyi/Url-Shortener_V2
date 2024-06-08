package com.goit.url.V2;

import com.goit.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findById(Long id);
    Optional<Url> findByShortId(String shortId);
    Optional<Url> findByShortIdAndUser(String shortId, User user);
    List<Url> findAllByUserId(Long userId);

    //    @Query(value = "SELECT * FROM urls WHERE expiry_date > NOW()", nativeQuery = true)
//    List<Url> findAllActiveByUserId(Long userId);

    @Query(value = "SELECT * FROM urls WHERE user_id = :userId AND expiry_date > NOW()", nativeQuery = true)
    List<Url> findAllActiveByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT MAX(id) FROM urls", nativeQuery = true)
    Optional<Long> getMaxId();

//    @Query(value = "UPDATE urls SET click_count = click_count + 1 WHERE short_id = :shortId", nativeQuery = true)
    @Query("UPDATE Url u SET u.clickCount = u.clickCount + 1 WHERE u.shortId = :shortId")
    void incClicksCount(@Param("shortId") String shortId);

    void deleteUrlByShortIdAndUser(String shortId, User user);
}
