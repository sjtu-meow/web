package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.dao.MediaRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.model.Media;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.AddMomentForm;
import me.sjtumeow.meow.model.form.MediaForm;
import me.sjtumeow.meow.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
    private MomentRepository momentRepository;
	
	@Autowired
    private MediaRepository mediaRepository;
	
	
	public Iterable<Moment> findAllMoments(boolean isAdmin) {
		return isAdmin ? momentRepository.findAll() : momentRepository.findAllActive();
	}
	
	public Iterable<Moment> findAllMomentsPageable(Integer page, Integer size, boolean isAdmin) {
		return isAdmin ?
			momentRepository.findAll(new PageRequest(page, size, Direction.DESC, "createdAt")) 
			: momentRepository.findAllActive(new PageRequest(page, size, Direction.DESC, "createdAt"));
	}
    
	public Moment findMomentById(Long id, boolean isAdmin) {
		return isAdmin ? momentRepository.findOne(id) : momentRepository.findOneActive(id);
	}
	
	public User getMomentCreator(Long id) {
		Moment moment = momentRepository.findOneActive(id);
		if (moment == null)
			return null;
		return moment.getProfile().getUser();
		
	}
	
	@Transactional
	public boolean addMoment(AddMomentForm amf, User user) {
		String content = amf.getContent();
		if ((content == null || content.trim().isEmpty()) && (amf.getMedias() == null || amf.getMedias().isEmpty()))
			return false;
		
		Moment moment = new Moment();
		moment.setProfile(user.getProfile());
		
		if (content != null)
			moment.setContent(content);
		
		momentRepository.save(moment);
		
		if (amf.getMedias() != null) {
			for (MediaForm mf: amf.getMedias()) {
				if (mf.getType() == null || mf.getUrl() == null)
					return false;
				mediaRepository.save(new Media(mf.getType(), mf.getUrl(), moment));
			}
		}
		
		return true;
	}
	
	@Transactional
	public boolean deleteMoment(Long id) {
		if (!momentRepository.existsActive(id))
    		return false;
    	momentRepository.softDelete(id);
    	return true;
	}
}
