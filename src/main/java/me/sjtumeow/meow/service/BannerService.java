package me.sjtumeow.meow.service;

import java.util.List;

import me.sjtumeow.meow.model.result.BannerResult;

public interface BannerService {
	
	List<BannerResult> findAllBanners();

}
