package com.example.demo.resources.service;

import java.util.List;

public interface ResourcesService {
	public List<String> BanerIdList();
	public List<String> DailyIdList();
	public List<String> LukcolorIdList();
	public List<String> WeatherIdList(int weather);
}