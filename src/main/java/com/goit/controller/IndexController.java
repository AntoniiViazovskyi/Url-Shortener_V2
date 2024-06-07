package com.goit.controller;

import com.goit.exception.exceptions.shortURLExceptions.ShortURLNotFoundException;
import com.goit.url.V2.UrlCrudService;

import com.goit.url.V2.UrlDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final UrlCrudService urlCrudService;

    @GetMapping("")
    public ModelAndView index() {
        return new ModelAndView("redirect:swagger-ui/index.html");
    }

    @GetMapping("/{shortId}")
    public ModelAndView getNoteById(@NotBlank @PathVariable("shortId") String shortId) throws ShortURLNotFoundException {
        Optional<UrlDto> url = urlCrudService.getURLByShortId(shortId);

        if (url.isEmpty() || url.get().getExpiryDate().isBefore(LocalDateTime.now())) throw  new ShortURLNotFoundException();

        urlCrudService.increaseClicksCount(shortId);
        return new ModelAndView("redirect:" + url.get().getLongURL());
    }
}
