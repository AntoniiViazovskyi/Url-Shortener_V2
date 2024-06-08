package com.goit.controller;

import com.goit.auth.User;
import com.goit.auth.UserServiceImpl;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLNotFoundException;
import com.goit.response.CustomErrorResponse;
import com.goit.response.UrlResponse;
import com.goit.response.UrlStatsResponse;
import com.goit.url.V2.Url;
import com.goit.url.V2.UrlCrudServiceImpl;
import com.goit.url.V2.UrlDto;
import com.goit.url.V2.UrlMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @Value("${app.domain}")
    private String appDomain;

    @GetMapping("")
    @Operation(summary = "Get all urls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List urls",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlResponse.class)) })
    })
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<UrlResponse>> urlList(Principal principal) {
        User user = userService.getUserWithAllUrls(principal.getName());
        List<UrlResponse> urls = urlMapper.toUtlResponseList(user.getUrls());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(urls);
}

    @GetMapping("/active")
    @Operation(summary = "Get url by short")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found url by short_id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Url not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class)) })

    })
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<List<UrlResponse>> getActiveUrls(Principal principal) {
        User user = userService.getUserWithActiveUrls(principal.getName());
        List<UrlResponse> urls = urlMapper.toUtlResponseList(user.getUrls());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(urls);
    }

    @GetMapping("/{shortId}/stats")
    @Operation(summary = "Get stats by short")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting redirects count",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UrlStatsResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "ShortUrl not found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorResponse.class)) })

    })
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<UrlStatsResponse> getUrlStatsByShort(String shortId, Principal principal) {
        User user = userService.getUserWithActiveUrls(principal.getName());
        List<UrlResponse> urls = urlMapper.toUtlResponseList(user.getUrls());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(urlService.getShortURLById(Long.valueOf(shortId)));
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
    public ResponseEntity<ShortURLDTO> createNote(@Valid @NotNull @RequestBody ShortURLDTO request) {
        ShortURLDTO shortUrl = urlService.createShortURL(request);
        shortUrl.setShortURL(String.format("%s/%s", appDomain, shortUrl.getId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(shortUrl);
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
    public void deleteUrlByShortId(@PathVariable("shortId") String  shortId, Principal principal) throws ShortURLNotFoundException {

        urlService.deleteShortURL(Long.valueOf(shortId));
    }
}
