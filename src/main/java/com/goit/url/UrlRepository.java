package com.goit.url;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortUrl(String shortUrl);
    List<Url> findAllByUser(User user);
    @Query(value = "SELECT MAX(id) FROM urls", nativeQuery = true)
    Optional<Long> getMaxId();
}
