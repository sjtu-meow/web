package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
    private MomentRepository momentRepository;
	
	public Iterable<Moment> getMoments(Integer page, Integer size) {
		return (page == null || size == null) ? momentRepository.findAllActive()
				: momentRepository.findAllActive(new PageRequest(page, size, Direction.DESC, "createdAt"));
	}
    
}
