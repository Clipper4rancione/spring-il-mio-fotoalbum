package org.project.springfotoalbum.controller;

import jakarta.validation.Valid;
import org.project.springfotoalbum.exceptions.PhotoNotFoundException;
import org.project.springfotoalbum.model.Photo;
import org.project.springfotoalbum.repository.PhotoRepository;
import org.project.springfotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public String index(Model model, @RequestParam(name = "q") Optional<String> keyword) {
        List<Photo> photos = photoRepository.findAll();
        if (keyword.isEmpty()) {
            photos = photoService.getAllPhotos();
        } else {
            photos = photoService.getFilteredPhotos(keyword.get());
            model.addAttribute("keyword", keyword.get());
        }
        model.addAttribute("photoList", photos);
        return "/photos/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        try {
            Photo photo = photoService.getById(id);
            model.addAttribute("photo", photo);
            return "/photos/show";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo with id " + id + " not found");
        }

    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("photo", new Photo());
        return "/photos/create";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("photo") Photo photoForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/photos/create";
        } else {
            photoService.createdPhoto(photoForm);
            return "redirect:/photos";
        }


    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        try {
            Photo photo = photoService.getById(id);
            model.addAttribute("photo", photo);
            return "/photos/edit";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo with id " + id + " not found");
        }

    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @ModelAttribute("photo") Photo photoForm) {
        try {
            Photo updatedPhoto = photoService.updatePhoto(photoForm, id);
            return "redirect:/photos/" + Integer.toString(updatedPhoto.getId());
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo with id " + id + " not found");
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        try {
            boolean success = photoService.deleteById(id);
            if (success) {
                return "redirect:/photos";
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to delete photo wit id " + id);
            }
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
    }

//    @GetMapping("/search")
//    public String search(Model model, @RequestParam(name = "q") String keyword) {
//        List<Photo> filteredPhotos = photoRepository.findByTitleContainingIgnoreCase(keyword);
//        model.addAttribute("photoList", filteredPhotos);
//        return "/photos/index";
//    }
}
