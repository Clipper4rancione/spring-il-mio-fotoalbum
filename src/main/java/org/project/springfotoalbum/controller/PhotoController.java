package org.project.springfotoalbum.controller;

import org.project.springfotoalbum.model.Photo;
import org.project.springfotoalbum.repository.PhotoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    private PhotoRepository photoRepository;

    @GetMapping
    public String index(Model model) {
        List<Photo> photos = photoRepository.findAll();
        model.addAttribute("list", photos);
        return "/photos/index";
    }

}
