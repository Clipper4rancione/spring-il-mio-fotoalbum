package org.project.springfotoalbum.api;

import org.project.springfotoalbum.exceptions.PhotoNotFoundException;
import org.project.springfotoalbum.model.Photo;
import org.project.springfotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/photos")
public class PhotoRestController {
    @Autowired
    private PhotoService photoService;

    //Lista di tutte le foto
    @GetMapping
    public List<Photo> photoList(@RequestParam(name = "q") Optional<String> search) {
        if (search.isPresent()) {
            return photoService.getFilteredPhotos(search.get());
        }
        return photoService.getAllPhotos();
    }

    // Singola Foto
    @GetMapping("/{id}")
    public Photo getById(@PathVariable Integer id) {
        try {
            return photoService.getById(id);
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //CREATE FOTO
    @PostMapping
    public Photo create(@RequestBody Photo photo) {
        return photoService.createdPhoto(photo);
    }

}
