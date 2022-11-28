package com.example.demo.closet.service;

import java.io.File;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.closet.dao.IClosetImgInfoDao;
import com.example.demo.closet.dao.IClosetInfoDao;
import com.example.demo.closet.dao.IConnectClosetImgDao;
import com.example.demo.closet.vo.ClosetGetInfoVo;
import com.example.demo.closet.vo.ClosetInfoVo;
import com.example.demo.closet.vo.ConnectClosetImgVo;
import com.example.demo.security.jwt.util.JwtUtil;

@Service
public class ClosetServiceImpl implements IClosetService {
	@Autowired
	JwtUtil JwtUtil;
	@Autowired
	IClosetImgInfoDao IClosetImgInfoDao;
	@Autowired
	IClosetInfoDao IClosetInfoDao;
	@Autowired
	IConnectClosetImgDao IConnectClosetImgDao;

	// 옷 이미지 조회 - 옷 정보 수정에서 사용
//   @Override
//   public String ClosetImgInfoGetDao() {
//	   String img = IClosetImgInfoDao.ClosetImgInfoGetDao();
//   	return img;
//   }

	// 옷 이미지 업로드 (1장)
	@Override
	public String ClosetImgInfoUploadDao(MultipartFile file) {
		String clothImgName; // 이미지 uuid 이름
		String clothImgRealName; // 이미지 본래 이름
		String clothImgFileSize; // 이미지 사이즈
		String clothImgExtension; // 이미지 확장자
		String clothImgFolder = "C:\\clothpick\\img"; // 이미지 저장 경로
		String clothImgTime; // 이미지 저장 시간

		// 이미지 고유 ID
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString().replace("-", "");

		// 현재 저장 시간
		ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00");
		clothImgTime = ZonedDateTime.now(seoulZoneOffset).toString();
		System.out.println("Current Instant = " + clothImgTime);

		clothImgName = uuids;
		clothImgRealName = file.getOriginalFilename();
		clothImgFileSize = String.valueOf(file.getSize());

		clothImgExtension = clothImgRealName.substring(clothImgRealName.lastIndexOf("."), clothImgRealName.length());
		// 파일에 저장하는 사진 이름(uuid + 확장자) - ex) 12Efi8sf.jpg
		String img = clothImgName + clothImgExtension;

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("clothImgName", img);
		paramMap.put("clothImgRealName", clothImgRealName);
		paramMap.put("clothImgFileSize", clothImgFileSize);
		paramMap.put("clothImgExtension", clothImgExtension);
		paramMap.put("clothImgFolder", clothImgFolder);
		paramMap.put("clothImgTime", clothImgTime);

		try {
			File saveFile = new File(clothImgFolder + "\\" + clothImgName + clothImgExtension);
			file.transferTo(saveFile); // 실제 파일 저장 메서드(filewriter) 작업을 손쉽게 처리해준다.
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		IClosetImgInfoDao.ClosetImgInfoUploadDao(paramMap);
		return img; // 이미지 고유 id + 확장자
	}

	// --------------------------------------------------------------

	// 옷 정보 조회
	@Override
	public List<ClosetGetInfoVo> ClosetInfoAllListDao() {
		List<ClosetGetInfoVo> info = IClosetInfoDao.ClosetInfoAllListDao();
		return info;
	}

	// 옷 정보 작성
	@Override
	public String ClosetInfoWrite(String token, ClosetInfoVo closetInfoVo) {
		String id = null;
		UUID uuid = UUID.randomUUID();

		for (int i = 0; i < closetInfoVo.getClothColor().length;) {
			String uuids[] = uuid.toString().split("-");
			String clothId = uuids[0];
			Map<String, Object> paramMap = new HashMap<String, Object>();

			String userid = JwtUtil.getUserIdFromToken(token);
			String username = JwtUtil.getUsernameFromToken(token);
			String usernickname = JwtUtil.getuserNickNameFromToken(token);

			paramMap.put("clothId", clothId);
			paramMap.put("clothType", closetInfoVo.getClothType());
			paramMap.put("clothDetail", closetInfoVo.getClothDetail());
			paramMap.put("clothColor", closetInfoVo.getClothColor()[i]); // 배열
			paramMap.put("clothPattern", closetInfoVo.getClothPattern());
			paramMap.put("clothTexture", closetInfoVo.getClothTexture());
			paramMap.put("clothStyle", closetInfoVo.getClothStyle());
			paramMap.put("clothKeyword", closetInfoVo.getClothKeyword());
			paramMap.put("clothPref", closetInfoVo.getClothPref());

			paramMap.put("userId", userid);
			paramMap.put("username", username);
			paramMap.put("userNickname", usernickname);
			
			// 임시 값
//			paramMap.put("userId", "4");
//			paramMap.put("username", "kdelay");
//			paramMap.put("userNickName", "검둥이");

			IClosetInfoDao.ClosetInfoWrite(paramMap);
			i++;
			id = clothId;
		}
		return id;
	}

	// 옷 정보 수정
	@Override
	public int ClosetInfoEditDao(ClosetInfoVo ClosetInfoVo, String clothId) {
		Map<String, String> paramMap = new HashMap<String, String>();
//		String userid = JwtUtil.getUserIdFromToken(token);
//		paramMap.put("userId", userid);
		paramMap.put("clothId", clothId);
		paramMap.put("clothKeyword", ClosetInfoVo.getClothKeyword());
		
		int result = IClosetInfoDao.ClosetInfoEditDao(paramMap);
		
		return result;
	}

	// 옷 정보 삭제
	@Override
	public int ClosetInfoDeleteDao(String clothId) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("clothId", clothId);

		int result = IClosetInfoDao.ClosetInfoDeleteDao(paramMap);
		return result;
	}

	// --------------------------------------------------------------
	// 옷-이미지 연결 get
	@Override
	public List<ConnectClosetImgVo> ConnectClosetImgGetDao() {
//		String token
//		String userid = JwtUtil.getUserIdFromToken(token);
//		String username = JwtUtil.getUsernameFromToken(token);
//		String usernickname = JwtUtil.getuserNickNameFromToken(token);
		
		List<ConnectClosetImgVo> result = IConnectClosetImgDao.ConnectClosetImgGetDao();
		return result;
	}

	// 옷-이미지 연결 post
	@Override
	public int ConnectClosetImgPostDao(String token, ConnectClosetImgVo ConnectClosetImgVo) {
		ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00");
		String currentout = ZonedDateTime.now(seoulZoneOffset).toString();
		String userid = JwtUtil.getUserIdFromToken(token);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("clothImgName", ConnectClosetImgVo.getClothImgName());
		paramMap.put("clothId", ConnectClosetImgVo.getClothId());

		paramMap.put("createAt", currentout.substring(0, 19));
		paramMap.put("updateAt", currentout.substring(0, 19));
		paramMap.put("userId", userid);

		int result = IConnectClosetImgDao.ConnectClosetImgPostDao(paramMap);
		if (result == 1) {
			return 1;
		} else {
			return 0;
		}
	}

	// 옷-이미지 연결 삭제
	@Override
	public int ConnectClosetImgDeleteDao(String clothId) {
		// TODO Auto-generated method stub
		return 0;
	}
}