package com.goit.controller;

import com.goit.url.Url;
import com.goit.url.UrlCrudService;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final UrlCrudService urlCrudService;

    @GetMapping("")
    public ModelAndView index() {
        return new ModelAndView("redirect:swagger-ui/index.html");
    }

    @GetMapping("/shortId")
    public ModelAndView getNoteById(@NotBlank @PathVariable("shortId") String shortId) throws UrlNotFoundException {
        // в запросе учитывать активность урла
        Url url = urlCrudService.getByShortId(shortId);

        if (url == null) throw  new UrlNotFoundException();

        urlCrudService.incrementClickCount(shortId);
        return new ModelAndView("redirect:" + url.getLongUrl());
    }
}
