package com.goit.controller;

import com.goit.exception.GlobalExceptionHandler;
import com.goit.exception.exceptions.shortURLExceptions.ShortURLNotFoundException;
import com.goit.url.V2.ShortURLDTO;
import com.goit.url.V2.UrlCrudService;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final UrlCrudService urlCrudService;
    private final GlobalExceptionHandler globalExceptionHandler;

    @GetMapping("")
    public ModelAndView index() {
        return new ModelAndView("redirect:swagger-ui/index.html");
    }

    @GetMapping("/shortId")
    public ModelAndView getNoteById(@NotBlank @PathVariable("shortId") String shortId) throws ShortURLNotFoundException {
        // в запросе учитывать активность урла
        Optional<ShortURLDTO> url = urlCrudService.getShortURLById(Long.valueOf(shortId));

        if (url == null) throw  new ShortURLNotFoundException();

        //urlCrudService.incrementClickCount(shortId);
        return new ModelAndView("redirect:" + url.get().getLongURL());
    }
}
