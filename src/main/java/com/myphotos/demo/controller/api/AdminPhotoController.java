package com.myphotos.demo.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.myphotos.demo.model.Photo;

@RestController
public class AdminPhotoController {

	private List<Photo> list;
	
	private int lastId;
	
	public AdminPhotoController() {
		
		list = new ArrayList<>();
		
		list.add(new Photo(1, "./img/01.png"));
		list.add(new Photo(2, "./img/02.png"));
		list.add(new Photo(3, "./img/03.png"));
		
		lastId = 3;
	}
	
	@RequestMapping("/admin/api/photos")
	public Iterable<Photo> getAll(){
		
		return list;
	}
	
	@RequestMapping("/admin/api/photos/{id}")
	public Photo getById(@PathVariable int id) {
		
		Optional<Photo> photo = list.stream().filter(item->item.getId() == id).findFirst();
		
		if (photo.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		
		return photo.get();
	}
	
	@RequestMapping(value = "/admin/api/photos", method = RequestMethod.POST)
	public Photo create(@RequestBody Photo photo) {
		lastId++;
		
		photo.setId(lastId);
		
		list.add(photo);
		
		return photo;
	}
	
	@RequestMapping(value = "/admin/api/photos/{id}", method = RequestMethod.PUT)
	public Photo update(@PathVariable int id, @RequestBody Photo photo) {
		
		Optional<Photo> foundPhoto = list.stream().filter(item->item.getId() == id).findFirst();
		
		if (foundPhoto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		
		foundPhoto.get().setUrl(photo.getUrl());
		
		return foundPhoto.get();
	}
	
	@RequestMapping(value = "/admin/api/photos/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		
		Optional<Photo> foundPhoto = list.stream().filter(item->item.getId() == id).findFirst();
		
		if (foundPhoto.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		
		list.remove(foundPhoto.get());
	}
}
