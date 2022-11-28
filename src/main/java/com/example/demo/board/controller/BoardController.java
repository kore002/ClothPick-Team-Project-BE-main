package com.example.demo.board.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.service.BoardService;
import com.example.demo.board.vo.BoardCommentLayer1InfoVo;
import com.example.demo.board.vo.BoardCommentVo;
import com.example.demo.board.vo.BoardInfoVo;
import com.example.demo.board.vo.BoardScrapBoardInfoVo;
import com.example.demo.board.vo.BoardScrapVo;
import com.example.demo.board.vo.BoardTextInfoResultVo;
import com.example.demo.board.vo.BoardTextInfoVo;
import com.example.demo.board.vo.ConnectBoardImgVo;

@RestController
public class BoardController {
	@Autowired
	BoardService BoardService;

//게시판 관련*********************************************************************************************************
	// 게시글 조회 ALL LIST
	@GetMapping("/api/v1/boardlist")
	public List<BoardTextInfoVo> BoardTextAllList(HttpServletRequest httpServletRequest) {
		List<BoardTextInfoVo> list1 = BoardService.BoardTextAllList();
		return list1;
	}

	// 게시글 조회 if BoardType
	@GetMapping("/api/v1/boardlist/{boardtype}")
	public List<BoardTextInfoResultVo> BoardTextTypeList(@PathVariable(name = "boardtype") String boardtype) {
		List<BoardTextInfoResultVo> list1 = BoardService.BoardTextTypeList(boardtype);
		return list1;
	}

	// 게시글 작성
	@PostMapping("/api/v1/boardtext")
	public String BoardTextPost(HttpServletRequest httpServletRequest, @RequestBody BoardTextInfoVo request,
			Model model) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		String resultboardId = BoardService.BoardTextPost(token, request);
		return resultboardId;
	}

	// 게시글 수정
	@PutMapping("/api/v1/boardtext/{boardId}")
	public String BoardTextPut(@PathVariable(name = "boardId") String boardId, @RequestBody BoardTextInfoVo request, HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7); 
		int result = BoardService.BoardTextPut(request, boardId, token);
		if (result == 1) {
			return "ok";
		} else {
			return "fail";
		}
	}

	// 게시글 삭제
	@DeleteMapping("/api/v1/boardtext/{boardId}")
	public String BoardTextDelete(@PathVariable(name = "boardId") String boardId, HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		
		int result = BoardService.BoardTextDelete(token, boardId);
		if (result == 1) {
			return "ok";
		} else {
			return "fail";
		}
	}

//이미지 관련********************************************************************************************************
	@PostMapping("/api/v1/boardimg")
	public List<String> BoardImgInfoUpload(HttpServletRequest httpServletRequest,
			@RequestParam("file") MultipartFile file[]) {
		List<String> result = BoardService.BoardImgInfoUpload(file);
		return result;
	}

	// 이미지 리소스 접근
	@GetMapping("/api/v1/displayimg/board/{filename}")
	public ResponseEntity<Resource> display(@PathVariable(name = "filename") String filename) {
		String path = "C:\\testimg\\board";
		String folder = "\\";
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

//이미지 연결 테이블 관련***********************************************************************************************
	@PostMapping("/api/v1/boardimgconnect")
	public String ConnectBoardImgPost(HttpServletRequest httpServletRequest, @RequestBody ConnectBoardImgVo request) {

		int result = BoardService.ConnectBoardImgPost(request);
		if (result == 1) {
			return "ok";
		} else {
			return "fail";
		}
	}

	// 보드 타입 전체 조회 이미지 아이디 조회
	@GetMapping("/api/v1/boardimgconnect/list/boardtype/{boardtype}")
	public List<ConnectBoardImgVo> ConnectBoardImgBoardTypeList(@PathVariable(name = "boardtype") String boardtype) {
		List<ConnectBoardImgVo> list1 = BoardService.ConnectBoardImgTypeList(boardtype);
		return list1;
	}

	// 보드 아이디로 이미지 아이디 조회 =//= 모바일 값 받는 것 때문에 이미지 아이디만 String 배열로 전달함
	@GetMapping("/api/v1/boardimgconnect/list/boardid/{boardid}")
	public List<String> ConnectBoardImgBoardIdList(@PathVariable(name = "boardid") String boardid) {
		List<String> list1 = BoardService.ConnectBoardImgBoardIdList(boardid);
		return list1;
	}

//댓글 관련*********************************************************************************************************
	@PostMapping("/api/v1/comment")
	public String comment(HttpServletRequest httpServletRequest, @RequestBody BoardCommentVo request, Model model) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);

		int result = BoardService.BoardCommentPost(token, request);

		if (result == 1) {
			return "ok";
		} else {
			return "fail";
		}
	}

	// boardid의 댓글, 대댓글 몰 리스트
	@GetMapping("/api/v1/commentlist/{boardid}")
	public List<BoardCommentVo> commentlist(@PathVariable(name = "boardid") String boardid) {
		List<BoardCommentVo> list1 = BoardService.BoardCommentBoardIdList(boardid);
		return list1;
	}

	// 모바일용 조회 레이어1 댓글 정보와 그 댓글의 레이어2 댓글(대댓글) 수를 포함하여 리턴
	@GetMapping("/api/v1/commentlist/layer1/{boardid}")
	public List<BoardCommentLayer1InfoVo> commentlistlayer1(@PathVariable(name = "boardid") String boardid) {
		List<BoardCommentLayer1InfoVo> list1 = BoardService.BoardCommentLayer1List(boardid);
		return list1;
	}

	// 모바일용 조회 댓글에 딸린 레이어2 정보(대댓글) 조회
	@GetMapping("/api/v1/commentlist/layer2/{commentParentsId}")
	public List<BoardCommentVo> commentlayer2list(@PathVariable(name = "commentParentsId") String commentParentsId) {
		List<BoardCommentVo> list1 = BoardService.BoardCommentLayer2List(commentParentsId);
		return list1;
	}
	
	// 댓글 수정
	@PutMapping("/api/v1/comment/{commentId}")
	public String commentUpdate(@PathVariable(name = "commentId") String commentId, @RequestBody BoardCommentVo request, HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		
		int result = BoardService.BoardCommentPut(token, request, commentId);
		if (result == 1) {
			return "ok";
		} else {
			return "fail";
		}
	}
	
	
	// 상위 댓글 | 하위 댓글 삭제
	@DeleteMapping("/api/v1/comment/{commentId}")
	public String commentDeleteAll(@PathVariable(name = "commentId") String commentId, HttpServletRequest httpServletRequest) {
		
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		
		System.out.println("#### --- commentId : " + commentId);
		
		
		int result = BoardService.BoardCommentDelete(commentId, token);
		if (result != 0) {
			return "ok";
		}
		else {
			return "fail";
		}
	}

//추천 관련*********************************************************************************************************
	// 게시글 타입별 추천 랭킹
	@GetMapping("/api/v1/RecommendRanking")
	public List<BoardTextInfoVo> BoardTypeRecommendRanking() {
		List<BoardTextInfoVo> ranking = BoardService.BoardTypeRecommendRanking();
		return ranking;
	}

	// 게시글 추천 증가 요청
	@PostMapping("/api/v1/RecommendUp/{boardid}")
	public String BoardTextRecommendUp(@PathVariable(name = "boardid") String boardid) {
		int result = BoardService.BoardTextRecommendUp(boardid);
		if (result == 1) {
			return "ok";
		} else {
			return "fail";
		}
	}

	// *****게시글 추천 감소 요청 =//= 추천 취소 기능 없음
	@PostMapping("/api/v1/RecommendDown/{boardid}")
	public String RecommendDown(@PathVariable(name = "boardid") String boardid) {
		int result = BoardService.BoardTextRecommendDawn(boardid);
		if (result == 1) {
			return "ok";
		} else {
			return "fail";
		}
	}

	// 게시글 추천 정보 테이블 입력 요청
	@PostMapping("/api/v1/recommend")
	public String BoardRecommendPost(HttpServletRequest httpServletRequest, @RequestBody BoardInfoVo BoardInfoVo) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);

		String boardid = BoardInfoVo.getBoardId();
		String boardType = BoardInfoVo.getBoardType();

		int result1 = BoardService.BoardTextRecommendUp(boardid);
		int result = BoardService.BoardRecommendPost(token, boardid, boardType);
		if (result == 1 && result1 == 1) {
			String count = Integer.toString(BoardService.BoardRecommendCountSerch(boardid));
			return count;
		} else {
			return "fail";
		}
	}

	// *****게시글 추천 정보 테이블 삭제 요청 =//= 추천 취소 기능 없음
	@DeleteMapping("/api/v1/BoardRecommendDelete/{boardid}")
	public String BoardRecommendDelete(HttpServletRequest httpServletRequest,
			@PathVariable(name = "boardid") String boardid) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		int result = BoardService.BoardRecommendDelete(token, boardid);
		if (result == 1) {
			return "ok";
		} else {
			return "fail";
		}
	}

	// 유저의 추천 게시글 정보
	@GetMapping("/api/v1/recommend/{boardid}")
	public String BoardRecommendSerch(HttpServletRequest httpServletRequest,
			@PathVariable(name = "boardid") String boardid) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		int result = BoardService.BoardRecommendSerch(token, boardid);
		String message = "";
		if (result == 1) {
			message = "overlap";
		} else if (result == 0) {
			message = "possible";
		} else {
			message = "server err";
		}
		return message;

	}

//스크랩 관련********************************************************************************************************
	// 게시글 스크랩 요청
	@PostMapping("/api/v1/scrap")
	public String BoardScrapPost(HttpServletRequest httpServletRequest, @RequestBody BoardScrapVo request) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		int result = BoardService.BoardScrapPost(token, request);
		if (result == 1) {
			return "ok";
		} else {
			return "fail";
		}
	}

	// 유저의 스크랩 게시글 정보
	@GetMapping("/api/v1/scraplist")
	public List<BoardScrapVo> BoardScrapUserIdList(HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		List<BoardScrapVo> result = BoardService.BoardScrapUserIdList(token);
		return result;
	}

	// 유저의 스크랩 게시글 정보 => 게시글 타입으로 세부 검색
	@GetMapping("/api/v1/scraplist/{bosrdType}")
	public List<BoardScrapVo> BoardScrapBoardTypeList(HttpServletRequest httpServletRequest,
			@PathVariable(name = "bosrdType") String bosrdType) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		List<BoardScrapVo> result = BoardService.BoardScrapBoardTypeList(token, bosrdType);
		return result;
	}

	// 유저의 스크랩 게시글 정보 => 게시글의 세부정보 요청
	@GetMapping("/api/v1/scraplist/boardinfo")
	public List<BoardScrapBoardInfoVo> BoardScrapBoardInfoList(HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		List<BoardScrapBoardInfoVo> result = BoardService.BoardScrapBoardInfoList(token);
		return result;
	}

	// 스크랩 정보 삭제 요청
	@DeleteMapping("/api/v1/scrapdel/{boardId}")
	public String BoardScrapDelete(@PathVariable(name = "boardId") String boardId, HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		int result = BoardService.BoardScrapDelete(token, boardId);
		if (result == 1) {
			return "ok";
		} else {
			return "fail";
		}
	}
}