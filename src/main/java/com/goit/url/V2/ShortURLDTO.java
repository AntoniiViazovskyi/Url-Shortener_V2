package com.goit.url.V2;

import com.goit.auth.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortURLDTO {
    private Long id;
    private String url;
    private String longURL;
    private String shortURL;
    private User creator;
    private List<User> users;
    private LocalDate expiryDate;
    private Long clickCount;
}
