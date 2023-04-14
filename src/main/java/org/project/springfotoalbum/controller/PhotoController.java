package org.project.springfotoalbum.controller;

import org.project.springfotoalbum.model.Photo;
import org.project.springfotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    @GetMapping
    public String index(Model model) {
        List<Photo> photos = photoRepository.findAll();
        model.addAttribute("photoList", photos);
        return "/photos/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Photo> result = photoRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("photo", result.get());
            return "/photos/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo with id " + id + " not found");
        }

    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(name = "q") String keyword) {
        List<Photo> filteredPhotos = photoRepository.findByTitleContainingIgnoreCase(keyword);
        model.addAttribute("photoList", filteredPhotos);
        return "/photos/index";
    }
}
