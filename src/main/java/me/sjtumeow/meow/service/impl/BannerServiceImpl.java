package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.dao.BannerRepository;
import me.sjtumeow.meow.model.Banner;
import me.sjtumeow.meow.model.result.BannerResult;
import me.sjtumeow.meow.service.BannerService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService {
	
	@Autowired
    private BannerRepository bannerRepository;
	
	public List<BannerResult> findAllBanners() {
		Iterable<Banner> banners = bannerRepository.findAllActive();
		List<BannerResult> result = new ArrayList<BannerResult>();
		for (Banner banner: banners) {
			result.add(new BannerResult(banner.getId(), banner.getUrl(), banner.getItem().getId(), banner.getItem().getType()));
		}
		return result;
	}
	
}
