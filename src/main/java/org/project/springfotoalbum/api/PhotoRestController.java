package org.project.springfotoalbum.api;

import org.project.springfotoalbum.model.Photo;
import org.project.springfotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoRestController {
    @Autowired
    private PhotoService photoService;

    //Lista di tutte le foto
    @GetMapping
    public List<Photo> photoList() {
        return photoService.getAllPhotos();
    }
}
