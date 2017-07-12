package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.dao.BannerRepository;
import me.sjtumeow.meow.model.Banner;
import me.sjtumeow.meow.model.result.BannerResult;
import me.sjtumeow.meow.service.BannerService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements BannerService {
	
	@Autowired
    private BannerRepository bannerRepository;
	
	public List<BannerResult> findAll() {
		Iterable<Banner> banners = bannerRepository.findAllActive(new Sort(Direction.ASC, "display_order"));
		
		List<BannerResult> result = new ArrayList<BannerResult>();
		for (Banner banner: banners) {
			result.add(new BannerResult(banner));
		}
		
		return result;
	}
	
}
