package com.goit.controller;

import com.goit.exception.LogEnum;
import com.goit.url.v2.UrlCrudService;

import com.goit.url.v2.UrlDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final UrlCrudService urlCrudService;

    @GetMapping("")
    public ModelAndView index() {
        log.info("{}: redirection on index.html page", LogEnum.CONTROLLER);
        return new ModelAndView("redirect:swagger-ui/index.html");
    }

    @GetMapping("/{shortId}")
    public ModelAndView getNoteById(HttpServletResponse response, @NotBlank @PathVariable("shortId") String shortId) throws IOException {
        Optional<UrlDto> url = urlCrudService.getURLByShortId(shortId);

        if (url.isEmpty() || url.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            response.setContentType("text/plain");
            response.setStatus(404);
            response.getWriter().write("Error 404: URL not found!");
            return null;
        }

        urlCrudService.updateClicksCount(shortId);
        return new ModelAndView("redirect:" + url.get().getLongURL());
    }
}
