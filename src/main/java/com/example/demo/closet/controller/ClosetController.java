package com.example.demo.closet.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.closet.service.IClosetService;
import com.example.demo.closet.vo.ClosetGetInfoVo;
import com.example.demo.closet.vo.ClosetInfoVo;
import com.example.demo.closet.vo.ConnectClosetImgVo;

@RestController
public class ClosetController {
	@Autowired
	IClosetService IClosetService;
	
	// 페이지 샘플
	@GetMapping("/api/info")
	public String projectInfo() {
		return "Sample Project";
	}
	
	//----------------------------------------------------------
	// 옷 이미지 정보 업로드(추가)
	@PostMapping("/api/v1/closetimgupload")
	public String clothimgupload(@RequestParam("file") MultipartFile file) {
		String img = IClosetService.ClosetImgInfoUploadDao(file);
		return img;
	}
	
	// 옷 이미지 경로 접근
	@GetMapping("/api/v1/displayimg/closet/{clothImgName}")
	public ResponseEntity<FileSystemResource> display(@PathVariable(name="clothImgName") String clothImgName, HttpServletRequest httpServletRequest) {
//		String authorizationHeader = httpServletRequest.getHeader("Authorization");
//		String token = authorizationHeader.substring(7);
		
		
		String path = "C:\\clothpick\\img";
		String folder = "\\";
		FileSystemResource resource = new FileSystemResource(path + folder + clothImgName);
		if(!resource.exists())
			return new ResponseEntity<FileSystemResource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		
		try {
			filePath = Paths.get(path + folder + clothImgName);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<FileSystemResource>(resource, header, HttpStatus.OK);
	}
	
	//----------------------------------------------------------
	// 옷 정보 조회
	@GetMapping("/api/v1/closetlist")
	public List<ClosetGetInfoVo> closetlist(HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		
		List<ClosetGetInfoVo> list = IClosetService.ClosetInfoAllListDao();
		return list;
	}
	
	// 옷 정보 작성(추가)
	@PostMapping("/api/v1/closet")
	public String closetupload(HttpServletRequest httpServletRequest, @RequestBody ClosetInfoVo ClosetInfoVo, Model model) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		
		String resultClosetId = IClosetService.ClosetInfoWrite(token, ClosetInfoVo);
		return resultClosetId;
	}
	
	// 옷 정보 삭제
	@DeleteMapping("/api/v1/clothDelete/{clothId}")
	   public String closetdelete(@PathVariable("clothId") String clothId, HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		
	      int result=IClosetService.ClosetInfoDeleteDao(clothId);
	      if(result != 0) {
	         return "ok";
	      }else {
	         return "fail";
	      }
	   }
	
	// 옷 정보 수정(키워드)
	@PutMapping("/api/v1/clothUpdate/{clothId}")
		public String closetupdate(@PathVariable(name = "clothId") String clothId, @RequestBody ClosetInfoVo request, HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		
		int result = IClosetService.ClosetInfoEditDao(request, clothId);
		if(result == 1) {
			return "ok";
		}
		else {
			return "fail";
		}
	}
	
	//----------------------------------------------------------
	// 옷-이미지 정보 조회(get)
	@GetMapping("/api/v1/clothimglist")
	public List<ConnectClosetImgVo> closetimglist(HttpServletRequest httpServletRequest) {
//		String authorizationHeader = httpServletRequest.getHeader("Authorization");
//		String token = authorizationHeader.substring(7);
		List<ConnectClosetImgVo> list = IClosetService.ConnectClosetImgGetDao(); // token
		return list;
	}
	
	// 옷-이미지 정보 추가(post)
	@PostMapping("/api/v1/clothimgconnect")
	public String clothimgconnect(HttpServletRequest httpServletRequest, @RequestBody ConnectClosetImgVo request) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		int result = IClosetService.ConnectClosetImgPostDao(token, request);
		if(result == 1) {
			return "ok";
		}
		else {
			return "fail";
		}
	}
	
	// 옷-이미지 정보 수정
	@GetMapping("/api/v1/clothimgupdate/{img}")
	public String detail(@PathVariable("img") String clothimginfo, Model model, HttpServletRequest httpServletRequest) {
		return clothimginfo;
	}

}
