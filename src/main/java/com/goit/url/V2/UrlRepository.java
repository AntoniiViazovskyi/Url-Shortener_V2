package com.goit.url.V2;

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
    List<Url> findAllByUserId(Long userId);
    @Query(value = "SELECT * FROM urls WHERE expiry_date > NOW()", nativeQuery = true)
    List<Url> findAllActiveByUserId(Long userId);

    @Query(value = "SELECT MAX(id) FROM urls", nativeQuery = true)
    Optional<Long> getMaxId();
    @Query(value = "UPDATE urls u set u.click_count = u.click_count + 1 WHERE u.short_id = :shortId", nativeQuery = true)
    void incClicksCount(@Param("shortId") String shortId);
    void deleteUrlByShortId(String shortId);
}
