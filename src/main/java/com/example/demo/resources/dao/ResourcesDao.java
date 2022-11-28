package com.example.demo.resources.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.resources.vo.ResourcesVo;

@Mapper
public interface ResourcesDao {
	public List<ResourcesVo> BanerIdListDao();
	public List<ResourcesVo> DailyIdListDao(Map<String, String> map);
	public List<ResourcesVo> LukcolorIdListDao(Map<String, String> map);
	public List<ResourcesVo> WeatherIdListDao(Map<String, String> map);
}	