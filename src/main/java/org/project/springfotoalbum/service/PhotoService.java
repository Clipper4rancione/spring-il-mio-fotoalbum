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

    public Photo updatePhoto(Photo photoForm, Integer id) throws PhotoNotFoundException {
        Photo photoToUpdate = getById(id);
        photoToUpdate.setTitle(photoForm.getTitle());
        photoToUpdate.setDescription(photoForm.getDescription());
        photoToUpdate.setUrl(photoForm.getUrl());
        return photoRepository.save(photoToUpdate);
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

    public boolean deleteById(Integer id) throws PhotoNotFoundException {
        photoRepository.findById(id).orElseThrow(() -> new PhotoNotFoundException(Integer.toString(id)));
        try {
            photoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
