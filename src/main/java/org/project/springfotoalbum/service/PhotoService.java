package org.project.springfotoalbum.service;

import org.project.springfotoalbum.exceptions.PhotoNotFoundException;
import org.project.springfotoalbum.model.Photo;
import org.project.springfotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;

    public Photo createdPhoto(Photo photoForm) {
        Photo photoToPersist = new Photo();
        photoToPersist.setTitle(photoForm.getTitle());
        photoToPersist.setDescription(photoForm.getDescription());
        photoToPersist.setUrl(photoForm.getUrl());
        return photoRepository.save(photoToPersist);
    }

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll(Sort.by("title"));
    }

    public List<Photo> getFilteredPhotos(String keyword) {
        return photoRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Photo getById(Integer id) {
        Optional<Photo> result = photoRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PhotoNotFoundException(Integer.toString(id));
        }
    }
}
