package com.example.demo.resources.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.resources.service.ResourcesService;

@RestController
public class ResourcesController {
	@Autowired
	ResourcesService ResourcesService;

	// 배너아이디
	@GetMapping("/api/v1/resources/banner")
	public List<String> Baner() {
		List<String> result = ResourcesService.BanerIdList();
		return result;
	}

	@GetMapping("/api/v1/resources/daily")
	public List<String> Daily() {
		List<String> result = ResourcesService.DailyIdList();
		return result;
	}

	@GetMapping("/api/v1/resources/lukcolor")
	public List<String> Lukcolor() {
		List<String> result = ResourcesService.LukcolorIdList();
		return result;
	}

	@GetMapping("/api/v1/resources/weather/{weather}")
	public List<String> Weather(@PathVariable(name = "weather") String weather) {
		int weatherint = Integer.parseInt(weather);
		List<String> result = ResourcesService.WeatherIdList(weatherint);
		return result;
	}

	@GetMapping("/api/v1/displayimg/resources/{type}/{foldertype}/{filename}")
	public ResponseEntity<Resource> resourcesdisplay(@PathVariable(name = "type") String type,
			@PathVariable(name = "foldertype") String foldertype, @PathVariable(name = "filename") String filename) {
		String path = "C:\\testimg\\resources\\" + type;
//		  C:\testimg\resources\bener\base
		String folder = "\\" + foldertype + "\\";
		Resource resource = new FileSystemResource(path + folder + filename);
		if (!resource.exists())
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(path + folder + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}

}