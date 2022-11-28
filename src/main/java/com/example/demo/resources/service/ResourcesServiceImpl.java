package com.example.demo.resources.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.resources.dao.ResourcesDao;
import com.example.demo.resources.vo.ResourcesVo;

@Service
public class ResourcesServiceImpl implements ResourcesService{
	@Autowired
	ResourcesDao dao;
	//배너 이미지
	@Override
	public List<String> BanerIdList() {
		List<ResourcesVo> list = dao.BanerIdListDao();
		List<String> resultlist = new ArrayList<String>();
//		resultlist.add(list.get(0).getFilefolder());
		
		for(int i = 0; i<list.size(); i++) {
			resultlist.add(list.get(i).getFilename());
		}
		return resultlist;
	}
	// 데일리룩 이미지
	@Override
	public List<String> DailyIdList() {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int monthValue = now.getMonthValue();
		int dayOfMonth = now.getDayOfMonth();
		int random = (year + monthValue + dayOfMonth) % 8;
		
		Map<String, String> paramMap = new HashMap<String,String>();		  
		  paramMap.put("compareNum", Integer.toString(random));
		
		List<ResourcesVo> list = dao.DailyIdListDao(paramMap);
		
		List<String> resultlist = new ArrayList<String>();
		
		for(int i = 0; i<list.size(); i++) {
			resultlist.add(list.get(i).getFilename());
		}
		return resultlist;
	}
	// 행운의 컬러 이미지
	@Override
	public List<String> LukcolorIdList() {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int monthValue = now.getMonthValue();
		int dayOfMonth = now.getDayOfMonth();
		int random = (year + monthValue + dayOfMonth) % 12;
		
		Map<String, String> paramMap = new HashMap<String,String>();		  
		  paramMap.put("compareNum", Integer.toString(random));
		  
		List<ResourcesVo> list = dao.LukcolorIdListDao(paramMap);
		List<String> resultlist = new ArrayList<String>();
		
		for(int i = 0; i<list.size(); i++) {
			resultlist.add(list.get(i).getFilename());
		}
		
		return resultlist;
	}
	//날씨 이미지
	@Override
	public List<String> WeatherIdList(int weather) {
		int resultweather;
		if(weather < 5) {resultweather = 0;}
		else if(weather < 9) {resultweather = 1;}
		else if(weather < 12) {resultweather = 2;}
		else if(weather < 17) {resultweather = 3;}
		else if(weather < 20) {resultweather = 4;}
		else if(weather < 23) {resultweather = 5;}
		else if(weather < 28) {resultweather = 6;}
		else {resultweather = 7;}
		
		Map<String, String> paramMap = new HashMap<String,String>();		  
		  paramMap.put("compareNum", Integer.toString(resultweather));
		  
		List<ResourcesVo> list = dao.WeatherIdListDao(paramMap);
		
		List<String> resultlist = new ArrayList<String>();
		
		for(int i = 0; i<list.size(); i++) {
			resultlist.add(list.get(i).getFilename());
		}
		
		return resultlist;
	}

}