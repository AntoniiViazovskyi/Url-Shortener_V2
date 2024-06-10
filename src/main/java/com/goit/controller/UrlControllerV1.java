package com.goit.controller;

import com.goit.auth.User;
import com.goit.auth.UserServiceImpl;
import com.goit.exception.LogEnum;
import com.goit.exception.exceptions.longURLExceptions.InvalidLongURLException;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLNotFoundException;
import com.goit.exception.exceptions.userExceptions.UserNotFoundException;
import com.goit.request.CreateShortUrlRequest;
import com.goit.response.CustomErrorResponse;
import com.goit.response.UrlResponse;
import com.goit.response.UrlStatsResponse;
import com.goit.url.V2.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/urls")
public class UrlControllerV1 {

    private final UrlCrudServiceImpl urlService;
    private final UrlMapper urlMapper;
    private final UserServiceImpl userService;

    @GetMapping("")
    @Operation(summary = "Get all urls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List urls",
                    content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UrlResponse.class)))}
            )
    })
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<UrlResponse>> urlList(Principal principal) throws UserNotFoundException {
        if (principal == null) throw new UserNotFoundException();
        User user = userService.getUserWithAllUrls(principal.getName());

        log.info("{}: User (id: {}) url's were retrieved from the database", LogEnum.CONTROLLER, user.getId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(urlMapper.toUtlResponseList(user.getUrls()));
}

    @GetMapping("/active")
    @Operation(summary = "Get active urls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List active urls",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UrlResponse.class)))}
            )
    })
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<UrlResponse>> getActiveUrls(Principal principal) throws UserNotFoundException {
        if (principal == null) throw new UserNotFoundException();
        User user = userService.getUserWithActiveUrls(principal.getName());

        log.info("{}: User (id: {}) active url's were retrieved from the database", LogEnum.SERVICE, user.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(urlMapper.toUtlResponseList(user.getUrls()));
    }

    @GetMapping("/{shortId}/stats")
    @Operation(summary = "Get stats by short")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting clicks count",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlStatsResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "ShortUrl not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class)) })

    })
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<UrlStatsResponse> getUrlStatsByShorId(@NotBlank @NotNull @PathVariable("shortId") String shortId, Principal principal)
            throws ShortURLNotFoundException, UserNotFoundException {
        if (principal == null) throw new UserNotFoundException();
        User user = userService.getUserWithActiveUrls(principal.getName());
        UrlDto url = urlService.getURLByShortIdAndUser(shortId, user).orElseThrow(() ->
                        new ShortURLNotFoundException(shortId));

        log.info("{}: Url (id: {}) stats were retrieved from the database", LogEnum.SERVICE, url.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(urlMapper.toUrlStatsResponse(url));
    }

    @PostMapping("/create")
    @Operation(summary = "Create short url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created short url",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class))})
    })
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<UrlResponse> createNote(@Valid @NotNull @RequestBody CreateShortUrlRequest request, Principal principal) throws InvalidLongURLException, UserNotFoundException {
        if (principal == null) throw new UserNotFoundException();
        User user = userService.getByEmail(principal.getName());
        Url url = new Url();
        url.setUser(user);
        url.setLongUrl(request.getLongUrl());
        url.setCreationDate(LocalDateTime.now());
        url.setExpiryDate(request.getExpiryDate());
        UrlDto shortUrl = urlService.createURL(urlMapper.toDTO(url));

        log.info("{}: Url (id: {}) was created", LogEnum.SERVICE, url.getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(urlMapper.toUrlResponse(urlMapper.toEntity(shortUrl)));
    }


    @DeleteMapping("/{shortId}")
    @Operation(summary = "Delete url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Url deleted"),
            @ApiResponse(responseCode = "404", description = "Url not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class)) }) })
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "BearerAuth")
    public void deleteUrlByShortId(@PathVariable("shortId") String  shortId, Principal principal) throws ShortURLNotFoundException, UserNotFoundException {
        if (principal == null) throw new UserNotFoundException();
        User user = userService.getByEmail(principal.getName());
        urlService.deleteByShortIdAndUser(shortId, user);

        log.info("{}: Url was deleted by shortId {}", LogEnum.CONTROLLER, shortId);
    }
}
