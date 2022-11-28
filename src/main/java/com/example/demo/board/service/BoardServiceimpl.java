package com.example.demo.board.service;

import java.io.File;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.dao.BoardCommentDao;
import com.example.demo.board.dao.BoardImgInfoDao;
import com.example.demo.board.dao.BoardRecommendDao;
import com.example.demo.board.dao.BoardScrapDao;
import com.example.demo.board.dao.BoardTextInfoDao;
import com.example.demo.board.dao.ConnectBoardImgDao;
import com.example.demo.board.vo.BoardCommentLayer1InfoVo;
import com.example.demo.board.vo.BoardCommentVo;
import com.example.demo.board.vo.BoardImgInfoVo;
import com.example.demo.board.vo.BoardScrapBoardInfoVo;
import com.example.demo.board.vo.BoardScrapVo;
import com.example.demo.board.vo.BoardTextInfoResultVo;
import com.example.demo.board.vo.BoardTextInfoVo;
import com.example.demo.board.vo.ConnectBoardImgVo;
import com.example.demo.security.jwt.util.JwtUtil;

@Service
public class BoardServiceimpl implements BoardService{
	@Autowired
	JwtUtil JwtUtil;
	@Autowired
	BoardTextInfoDao BoardTextInfoDao;
	@Autowired
	BoardImgInfoDao BoardImgInfoDao;
	@Autowired
	BoardCommentDao BoardCommentDao;
	@Autowired
	BoardRecommendDao BoardRecommendDao;
	@Autowired
	BoardScrapDao BoardScrapDao;
	@Autowired
	ConnectBoardImgDao ConnectBoardImgDao;
	
	@Override
	public List<BoardTextInfoVo> BoardTextAllList() {
		List<BoardTextInfoVo> result = BoardTextInfoDao.BoardTextAllListDao();
		return result;
	}
	
	// 게시판 타입 조회
	@Override
	public List<BoardTextInfoResultVo> BoardTextTypeList(String boardType) {
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("boardType", boardType);
		
		List<BoardTextInfoResultVo> list = BoardTextInfoDao.BoardTextTypeListDao(paramMap);
        return list;
        
	}
	
	// 게시판별 추천수 랭킹
	@Override
	public List<BoardTextInfoVo> BoardTypeRecommendRanking() {
		List<BoardTextInfoVo> result = BoardTextInfoDao.BoardRecommendRankingDao();
		return result;
	}

	// 게시글 추천수 증가
	@Override
	public int BoardTextRecommendUp(String boardId) {
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("boardId", boardId);
		int result = BoardTextInfoDao.BoardTextRecommendUpDao(paramMap);		
		return result;
	}
	//게시글 추천수 감소 =//= 감소 동작 x 
	@Override
	public int BoardTextRecommendDawn(String boardId) {		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("boardId", boardId);
		int result = BoardTextInfoDao.BoardTextRecommendDawnDao(paramMap);		
		return result;
	}
	
	
	//게시글 작성
	@Override
	public String BoardTextPost(String token, BoardTextInfoVo BoardTextInfoVo) {
		String userid = JwtUtil.getUserIdFromToken(token);
		String username = JwtUtil.getUsernameFromToken(token);
		String usernickname = JwtUtil.getuserNickNameFromToken(token);
		
		ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00");
		String currentout = ZonedDateTime.now(seoulZoneOffset).toString();
		String millisecond = currentout.substring(20,29);
		UUID uuid = UUID.randomUUID();
		String uuids[] = uuid.toString().split("-");
		String boardId = uuids[0]+millisecond;
		
		System.out.println(BoardTextInfoVo.getBoardType());
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("boardId", boardId);
		paramMap.put("boardType", BoardTextInfoVo.getBoardType());
		paramMap.put("boardTitle", BoardTextInfoVo.getBoardTitle());
		paramMap.put("boardContent", BoardTextInfoVo.getBoardContent());		  
		paramMap.put("boardCreateAt", currentout.substring(0, 19));
		paramMap.put("boardUpdateAt", currentout.substring(0, 19));
		paramMap.put("userId", userid);
		paramMap.put("username", username);
		paramMap.put("userNickname", usernickname);
		
//		int result = 
		BoardTextInfoDao.BoardTextPostDao(paramMap);
		// 게시글 이미지의 연결 테이블을 위한 게시글 아이디 리턴
		return boardId;
	}
	
	// 개시글 수정
	@Override
	public int BoardTextPut(BoardTextInfoVo BoardTextInfoVo, String boardId, String token) {
		ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00");
		String currentout = ZonedDateTime.now(seoulZoneOffset).toString();
		
		Map<String, String> paramMap = new HashMap<String,String>();
		String userid = JwtUtil.getUserIdFromToken(token);
		paramMap.put("userId", userid);
		
		paramMap.put("boardId", boardId);
		paramMap.put("boardTitle", BoardTextInfoVo.getBoardTitle());
		paramMap.put("boardContent", BoardTextInfoVo.getBoardContent());
		paramMap.put("boardUpdateAt", currentout.substring(0, 19));
		
		int result = BoardTextInfoDao.BoardTextPutDao(paramMap);
		
		return result;
	}
	
	//게시글 삭제
	@Override
	public int BoardTextDelete(String token, String boardId) {
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("boardId", boardId);
		
		String userid = JwtUtil.getUserIdFromToken(token);
		paramMap.put("userId", userid);
		
		int result = BoardTextInfoDao.BoardTextDeleteDao(paramMap);
		System.out.println(result);
		
		return result;
	}
	
	//이미지 정보 업로드
	@Override
	public List<String> BoardImgInfoUpload(MultipartFile[] file) {
		String fileRealName; //파일 이름
		  String size; // 파일 사이즈
		  String fileExtension; //파일 확장자
		  String uploadFolder = "C:\\testimg\\board";
		  String uniqueName; //서버에 저장할 파일 이름		  
		  /*
		  파일 업로드시 파일명이 동일한 파일이 이미 존재할 수도 있고 사용자가 
		  업로드 하는 파일명이 언어 이외의 언어로 되어있을 수 있습니다. 
		  타인어를 지원하지 않는 환경에서는 정산 동작이 되지 않습니다.(리눅스가 대표적인 예시)
		  고유한 랜던 문자를 통해 db와 서버에 저장할 파일명을 새롭게 만들어 준다.
		 */
		  UUID uuid = UUID.randomUUID();
		  List<String> uuidlist = new ArrayList<String>();
//		  Instant current = Instant.now();
		  ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00");
		  String currentout = ZonedDateTime.now(seoulZoneOffset).toString();
		  System.out.println("Current Instant = "+ currentout);
		  
		  for(int i=0; i<file.length; i++) {
			  fileRealName = file[i].getOriginalFilename();
			  size = String.valueOf(file[i].getSize());
			  fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
			  uuid = UUID.randomUUID();
			  String uuids = uuid.toString().replace("-","");
			  uuidlist.add(uuids+fileExtension);
			  uniqueName = uuids;
	
			  File saveFile = new File(uploadFolder+"\\" + uniqueName + fileExtension);  // 적용 후
				try {
					file[i].transferTo(saveFile);  // 실제 파일 저장메서드(filewriter 작업을 손쉽게 한방에 처리해준다.)
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Map<String, String> paramMap = new HashMap<String,String>();
				  paramMap.put("fileUniqueName", uniqueName+fileExtension);
				  paramMap.put("fileRealName", fileRealName);
				  paramMap.put("fileSize", size);
				  paramMap.put("fileExtension", fileExtension);
				  paramMap.put("fileUploadFolder", uploadFolder);
				  paramMap.put("fileUploadTime", currentout.substring(0, 19));

				  int result = BoardImgInfoDao.BoardImgInfoUploadDao(paramMap);
		  }
		  // 연결테이블 요청을 위한 고유아이디 + 확장자의 리스트 리턴
		  return uuidlist;
	}
	
	// 게시글 이미지 연결 테이블 작성
	@Override
	public int ConnectBoardImgPost(ConnectBoardImgVo ConnectBoardImgVo) {
		ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00");
		String currentout = ZonedDateTime.now(seoulZoneOffset).toString();
		
		Map<String, String> paramMap = new HashMap<String,String>();		  
		  paramMap.put("boardId", ConnectBoardImgVo.getBoardId());
		  paramMap.put("boardType", ConnectBoardImgVo.getBoardType());
		  paramMap.put("fileUniqueName", ConnectBoardImgVo.getFileUniqueName());
		  paramMap.put("createAt", currentout.substring(0, 19));
		  paramMap.put("updateAt", currentout.substring(0, 19));
		  
		int result = ConnectBoardImgDao.ConnectBoardImgPostDao(paramMap);
		  
		return result;
	}
	// 게시글 아이디로 연결 테이블 삭제 => 게시글이 삭제 되거나 수정 될때 실행
	@Override
	public int ConnectBoardImgDelete(String boardId) {
		Map<String, String> paramMap = new HashMap<String,String>();		  
		  paramMap.put("boardId", boardId);
		  
		int result = ConnectBoardImgDao.ConnectBoardImgDeleteDao(paramMap);
		  
		return result;
	}
	
	// 게시판 타입으로 글과 연결된 이미지 전체 검색
	@Override
	public List<ConnectBoardImgVo> ConnectBoardImgTypeList(String boardType) {
		Map<String, String> paramMap = new HashMap<String,String>();		  
		  paramMap.put("boardType", boardType);
		
		 List<ConnectBoardImgVo> list = ConnectBoardImgDao.ConnectBoardImgTypeListDao(paramMap);
		  
		return list;
	}
	
	// 게시글 아이디로 글과 연결된 이미지 검색
	@Override
	public List<String> ConnectBoardImgBoardIdList(String boardId) {
		Map<String, String> paramMap = new HashMap<String,String>();		  
		  paramMap.put("boardId", boardId);
		
		List<ConnectBoardImgVo> list = ConnectBoardImgDao.ConnectBoardImgBoardIdListDao(paramMap);
		
		ArrayList<String> filenamelist = new ArrayList<String>();
		for(int i=0; i<list.size(); i++) {
			filenamelist.add(list.get(i).getFileUniqueName());
		}
		return filenamelist;
	}
	
	// 댓글 ID 검색
	@Override
	public BoardCommentVo BoardCommentIdSearch(String commentId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("commentId", commentId);
		
		BoardCommentVo comment = BoardCommentDao.BoardCommentIdSearchDao(paramMap);
		
		return comment;
	}
	
	// 댓글 작성
	@Override
	public int BoardCommentPost(String token, BoardCommentVo BoardCommentVo) {
		String userid = JwtUtil.getUserIdFromToken(token);
		String username = JwtUtil.getUsernameFromToken(token);
		String usernickname = JwtUtil.getuserNickNameFromToken(token);
		
		ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00");
		String currentout = ZonedDateTime.now(seoulZoneOffset).toString();
		String millisecond = currentout.substring(20,29);
		UUID uuid = UUID.randomUUID();
		String uuids[] = uuid.toString().split("-");
		String commentId = uuids[0]+millisecond;
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("boardId", BoardCommentVo.getBoardId());
		paramMap.put("boardType", BoardCommentVo.getBoardType());
		paramMap.put("userId", userid);
		paramMap.put("username", username);
		paramMap.put("userNickname", usernickname);
		paramMap.put("commentId", commentId);
		
		if(BoardCommentVo.getCommentParentsId() != null) {
			if(BoardCommentVo.getCommentParentsId().equals("0")) {
				paramMap.put("commentLayer", "1");
			} else {
				paramMap.put("commentParentsId", BoardCommentVo.getCommentParentsId());
				paramMap.put("commentLayer", "2");
				
			}
		}else {
			paramMap.put("commentLayer", "1");
		}
		paramMap.put("commentContent", BoardCommentVo.getCommentContent());
		paramMap.put("commentCreateAt", currentout.substring(0, 19));
		paramMap.put("commentUpdateAt", currentout.substring(0, 19));
		
		int result = BoardCommentDao.BoardCommentPostDao(paramMap);
		
		return result;
	}
	
	// 댓글 수정
	@Override
	public int BoardCommentPut(String token, BoardCommentVo BoardCommentVo, String commentId) {
		ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00");
		String currentout = ZonedDateTime.now(seoulZoneOffset).toString();
		System.out.println("---- currentout : " + currentout);
		System.out.println("---- commentId : " + commentId);
		System.out.println("---- commentContent : " + BoardCommentVo.getCommentContent());
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("commentId", commentId);
		paramMap.put("commentContent", BoardCommentVo.getCommentContent());
		paramMap.put("commentUpdateAt", currentout.substring(0, 19));
		
		int result = BoardCommentDao.BoardCommentPutDao(paramMap);
		
		return result;
	}
	
	//댓글 삭제 => 2가지 동작

	// 1 : 하위 레이어 삭제 => 그 대댓글만 삭제
	// 2 : 상위 레이어 삭제 => 연결된 모든 하위 레이어(대댓글) 전부 삭제
	@Override
	public int BoardCommentDelete(String commentId , String token) {
		
		int result = 0;
		
		System.out.println("서비스 동작중");
		
		Map<String, String> paramMap3 = new HashMap<String, String>();
		paramMap3.put("commentId", commentId);
		BoardCommentVo comment = BoardCommentDao.BoardCommentIdSearchDao(paramMap3);
		
		String commentLayer = String.valueOf(comment.getCommentLayer());
		System.out.println("------- commentLayer :" + commentLayer);

		// 상위 댓글 삭제 경우 (하위 댓글 포함 삭제)
		if(commentLayer.equals("1")) {
			System.out.println("#### 1번 실행");
			Map<String, String> paramMap = new HashMap<String,String>();
			paramMap.put("commentId", commentId);
			paramMap.put("commentLayer", "1");
			
			Map<String, String> paramMap2 = new HashMap<String,String>();
			paramMap2.put("commentId", commentId);
			paramMap2.put("commentLayer", "2");
			
			
			int result1 = BoardCommentDao.BoardCommentDeleteDao(paramMap);
			int result2 = BoardCommentDao.BoardCommentDeletelayer2Dao(paramMap2);
			
			if(result1 != 0 && result2 != 0) {
				result = 1;
			}
			else {
				result = 0;
			}
			
			System.out.println("#### result : " + result);
		}
		
		// 하위 댓글 삭제 경우
		else if(commentLayer.equals("2")) {
			System.out.println("#### 2번 실행");
			
			Map<String, String> paramMap2 = new HashMap<String,String>();
			paramMap2.put("commentId", commentId);
			paramMap2.put("commentLayer", "2");
			
			result = BoardCommentDao.BoardCommentDeleteDao(paramMap2);
		}
		
		System.out.println("result : " + result);
		return result;
	}
	
	// 하위 레이어 삭제
	@Override
	public int BoardCommentDeletelayer2(String commentId, String token) {
		Map<String, String> paramMap = new HashMap<String,String>();
		String userid = JwtUtil.getUserIdFromToken(token);
		
		Map<String, String> paramMap3 = new HashMap<String, String>();
		paramMap3.put("commentId", commentId);
		BoardCommentVo comment = BoardCommentDao.BoardCommentIdSearchDao(paramMap3);
		String commentLayer = String.valueOf(comment.getCommentLayer());
		
		paramMap.put("commentId", commentId);
		paramMap.put("commentLayer", commentLayer);
		paramMap.put("userId", userid);
		
		int result = BoardCommentDao.BoardCommentDeletelayer2Dao(paramMap);
		
		System.out.println("##### result : " + result);
		return result;
	}
	
	//boardid의 댓글, 대댓글 몰 리스트
	@Override
	public List<BoardCommentVo> BoardCommentAllList() {
		List<BoardCommentVo> result = BoardCommentDao.BoardCommentAllListDao();
		return result;
	}
	
	//개시글 아이디로 연관 댓글 검색 => 최근 시간 기준 all list 검색 
	@Override
	public List<BoardCommentVo> BoardCommentBoardIdList(String boardId) {
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("boardId", boardId);
		List<BoardCommentVo> result = BoardCommentDao.BoardCommentBoardIdListDao(paramMap);
		return result;
	}
	
	// 해당 개시글의 댓글 리스트 정보 => 댓글이 가지는 대댓글 수 까지 포함할 예정
	//모바일용 조회 레이어1 댓글 정보와 그 댓글의 레이어2 댓글(대댓글) 수를 포함하여 리턴
	@Override
	public List<BoardCommentLayer1InfoVo> BoardCommentLayer1List(String boardId) {
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("boardId", boardId);
		
		List<BoardCommentLayer1InfoVo> result = BoardCommentDao.BoardCommentLayer1ListDao(paramMap);
		return result;
	}
	
	//모바일용 조회 댓글에 딸린 레이어2 정보(대댓글) 조회
	@Override
	public List<BoardCommentVo> BoardCommentLayer2List(String commentParentsId) {
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("commentParentsId", commentParentsId);
		
		List<BoardCommentVo> result = BoardCommentDao.BoardCommentLayer2ListDao(paramMap);
		return result;
	}
	///////////////////////////// 의미불명
	
	//추천 관련
	//해당 게시글에 추천 누름
	@Override
	public int BoardRecommendPost(String token, String boardId, String boardType) {
		String userid = JwtUtil.getUserIdFromToken(token);
		String username = JwtUtil.getUsernameFromToken(token);
		
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", userid);
		paramMap.put("username", username);
		paramMap.put("boardId", boardId);
		paramMap.put("boardType", boardType);
		
		int result = BoardRecommendDao.BoardRecommendPostDao(paramMap);
		
		return result;
	}
	
	//해당 글 추천 눌렀다 취소함
	@Override
	public int BoardRecommendDelete(String token, String boardId) {
		String userid = JwtUtil.getUserIdFromToken(token);

		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", userid);
		paramMap.put("boardId", boardId);
		
		int result = BoardRecommendDao.BoardRecommendDeleteDao(paramMap);
		return result;
	}
	
	// 해당 유저가 추천 누른 게시글 아이디, 타입
	@Override
	public int BoardRecommendSerch(String token, String boardId) {
		String userid = JwtUtil.getUserIdFromToken(token);

		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", userid);
		paramMap.put("boardId", boardId);
		
		int result = BoardRecommendDao.BoardRecommendSerchDao(paramMap);
		return result;
	}
	@Override
	public int BoardRecommendCountSerch(String boardId){
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("boardId", boardId);
		int result = BoardRecommendDao.BoardRecommendCountSerchDao(paramMap);
		return result;
	}
	
	//글 스크랩 관련
	//해당 유저가 특정 글 스크랩 함
	@Override
	public int BoardScrapPost(String token, BoardScrapVo BoardScrapVo) {
		String userid = JwtUtil.getUserIdFromToken(token);
		String username = JwtUtil.getUsernameFromToken(token);
		
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", userid);
		paramMap.put("username", username);
		paramMap.put("boardId", BoardScrapVo.getBoardId());
		paramMap.put("boardType", BoardScrapVo.getBoardType());
		
		int result = BoardScrapDao.BoardScrapPostDao(paramMap);
		
		return result;
	}
	// 스크랩 삭제
	@Override
	public int BoardScrapDelete(String token, String boardId) {
		String userid = JwtUtil.getUserIdFromToken(token);

		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", userid);
		paramMap.put("boardId", boardId);
//		paramMap.put("boardType", BoardScrapVo.getBoardType());
		
		int result = BoardScrapDao.BoardScrapDeleteDao(paramMap);
		return result;
	}
	//유저 아이디로 스크랩 검색
	@Override
	public List<BoardScrapVo> BoardScrapUserIdList(String token) {
		String userid = JwtUtil.getUserIdFromToken(token);
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("userId", userid);
		
		List<BoardScrapVo> result = BoardScrapDao.BoardScrapUserIdListDao(paramMap);
		return result;
	}
	//유저 아이디로 스크랩 검색
		@Override
		public List<BoardScrapVo> BoardScrapBoardTypeList(String token, String boardType) {
			String userid = JwtUtil.getUserIdFromToken(token);
			
			Map<String, String> paramMap = new HashMap<String,String>();
			paramMap.put("userId", userid);
			paramMap.put("boardType", boardType);
			
			List<BoardScrapVo> result = BoardScrapDao.BoardScrapBoardTypeListDao(paramMap);
			return result;
		}
		
		@Override
		public List<BoardScrapBoardInfoVo> BoardScrapBoardInfoList(String token){
			String userid = JwtUtil.getUserIdFromToken(token);
			
			Map<String, String> paramMap = new HashMap<String,String>();
			paramMap.put("userId", userid);
			
			List<BoardScrapBoardInfoVo> result = BoardScrapDao.BoardScrapBoardInfoListDao(paramMap);
			
			return result;
		}

}