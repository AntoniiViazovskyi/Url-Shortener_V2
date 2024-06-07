package com.goit.url.V2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShortURLRepository extends JpaRepository<ShortURL, Long> {
    Optional<ShortURL> findByUrl(String shortURL);
    List<ShortURL> findAllByCreatorId(Long userId);

    @Query("SELECT MAX(id) FROM ShortURL")
    Optional<Long> getMaxId();
}
