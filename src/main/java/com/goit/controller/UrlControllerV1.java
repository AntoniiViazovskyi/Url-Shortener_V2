package com.goit.controller;

import com.goit.auth.UserMapper;
import com.goit.request.CreateShortUrlRequest;
import com.goit.response.CustomErrorResponse;
import com.goit.response.UrlResponse;
import com.goit.response.UrlStatsResponse;
import com.goit.url.UrlCrudServiceImpl;
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
import java.time.Duration;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/urls")
public class UrlControllerV1 {

    private final UrlCrudServiceImpl urlService;
    private final UserMapper urlMapper;
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
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(urlService.listAll(principal.getName()));
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
    public ResponseEntity<List<UrlResponse>> getUrlsByStatus(String shortId, Principal principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(urlService.listActive(principal.getName()));
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
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(urlService.getOne(shortId, principal.getName()));
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
    public ResponseEntity<UrlResponse> createNote(@Valid @NotNull @RequestBody CreateShortUrlRequest request, Principal principal) {
        UrlResponse urlResponse = urlService.add(request, principal.getName());
        urlResponse.setShortUrl(String.format("%s/%s", appDomain, urlResponse.getShortId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(urlResponse);
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
    public void deleteUrlByShortId(@PathVariable("shortId") String  shortId, Principal principal) throws UrlNotFoundException {

        urlService.deleteByShortId(shortId, principal.getName());
    }
}
