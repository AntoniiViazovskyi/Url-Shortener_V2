package com.goit.url.v2;

import com.goit.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "urls")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_id", nullable = false, unique = true)
    private String shortId;

    @Column(name = "long_url", nullable = false)
    private String longUrl;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "click_count", nullable = false)
    private int clickCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Url(String shortId, String longUrl, LocalDateTime creationDate, LocalDateTime expiryDate, int clickCount, User user) {
        this.shortId = shortId;
        this.longUrl = longUrl;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.clickCount = clickCount;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", shortId='" + shortId + '\'' +
                ", longUrl='" + longUrl + '\'' +
                ", creationDate=" + creationDate +
                ", expiryDate=" + expiryDate +
                ", clickCount=" + clickCount +
                ", userId=" + user.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return clickCount == url.clickCount && Objects.equals(id, url.id) && Objects.equals(shortId, url.shortId) && Objects.equals(longUrl, url.longUrl) && Objects.equals(creationDate, url.creationDate) && Objects.equals(expiryDate, url.expiryDate) && Objects.equals(user.getId(), url.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortId, longUrl, creationDate, expiryDate, clickCount, user.getId());
    }
}

