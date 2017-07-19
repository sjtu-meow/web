package me.sjtumeow.meow.controller.api.app;

import me.sjtumeow.meow.authorization.annotation.Authorization;
import me.sjtumeow.meow.authorization.annotation.CurrentUser;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.model.form.ChangePasswordForm;
import me.sjtumeow.meow.model.form.ProfileForm;
import me.sjtumeow.meow.model.form.RegisterForm;
import me.sjtumeow.meow.model.result.AnswerSummaryResult;
import me.sjtumeow.meow.model.result.ArticleSummaryResult;
import me.sjtumeow.meow.model.result.BaseSummaryResult;
import me.sjtumeow.meow.model.result.FailureMessageResult;
import me.sjtumeow.meow.model.result.FollowStatusResult;
import me.sjtumeow.meow.model.result.MomentSummaryResult;
import me.sjtumeow.meow.model.result.NewEntityIdResult;
import me.sjtumeow.meow.model.result.QuestionSummaryResult;
import me.sjtumeow.meow.service.AuthService;
import me.sjtumeow.meow.service.InteractionService;
import me.sjtumeow.meow.service.ItemService;
import me.sjtumeow.meow.service.UserService;
import me.sjtumeow.meow.util.FormatValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private InteractionService interactionService;
    

    @PostMapping(path = "/users", consumes = "application/json")
    ResponseEntity<?> createUser(@RequestBody RegisterForm rf) {
    	
    	String phone = rf.getPhone();
    	String password = rf.getPassword();
    	String code = rf.getCode();
    	
    	if (!FormatValidator.checkPhone(phone))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("手机号格式不正确"));
    	if (!FormatValidator.checkPassword(password))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("密码的长度至少为 6 个字符"));
    	if (!FormatValidator.checkSmsCode(code))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("验证码必须是 6 位数字"));
    	if (!authService.verifySmsCode(phone, code))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("验证码验证失败"));
    	if (userService.findByPhone(phone, true) != null)
			return ResponseEntity.badRequest().body(new FailureMessageResult("该手机号已被注册"));
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(new NewEntityIdResult(userService.create(phone, password)));
    }
    
    @Authorization
    @PutMapping(path = "/user", consumes = "application/json")
    ResponseEntity<?> changePassword(@CurrentUser User user, @RequestBody ChangePasswordForm cpf) {
    	
    	String phone = user.getPhone();
    	String password = cpf.getPassword();
    	String code = cpf.getCode();
    	
    	if (!FormatValidator.checkPassword(password))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("密码的长度至少为 6 个字符"));
    	if (!FormatValidator.checkSmsCode(code))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("验证码必须是 6 位数字"));
    	if (!authService.verifySmsCode(phone, code))
    		return ResponseEntity.badRequest().body(new FailureMessageResult("验证码验证失败"));
    	
    	return userService.changePassword(user.getId(), password) ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    	
    }
    
    @GetMapping("/user/profile")
    @Authorization
    Profile getProfile(@CurrentUser User user) {
    	return user.getProfile();
    }
    
    @PutMapping(path = "/user/profile", consumes = "application/json")
    @Authorization
    ResponseEntity<?> updateProfile(@CurrentUser User user, @RequestBody ProfileForm pf) {
    	userService.UpdateProfile(user, pf);
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/users/{id}/profile")
    ResponseEntity<?> getProfileByUser(@PathVariable("id") Long id) {
    	Profile profile = userService.getProfile(id, false);
    	return profile == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(profile);
    }
    
    @GetMapping("/user/moments")
    @Authorization
    Iterable<MomentSummaryResult> getMoments(@CurrentUser User user, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    	return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findMomentsByUser(user.getId()) : itemService.findMomentsByUserPageable(page, size, user.getId());
    }
    
    @GetMapping("/users/{id}/moments")
    ResponseEntity<?> getMomentsByUser(@PathVariable("id") Long id, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    	if (userService.findById(id, false) == null)
    		return ResponseEntity.notFound().build();
    	return ResponseEntity.ok((!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
    			itemService.findMomentsByUser(id) : itemService.findMomentsByUserPageable(page, size, id));
    }
    
    @GetMapping("/user/articles")
    @Authorization
    Iterable<ArticleSummaryResult> getArticles(@CurrentUser User user, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    	return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findArticlesByUser(user.getId()) : itemService.findArticlesByUserPageable(page, size, user.getId());
    }
    
    @GetMapping("/users/{id}/articles")
    ResponseEntity<?> getArticlesByUser(@PathVariable("id") Long id, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    	if (userService.findById(id, false) == null)
    		return ResponseEntity.notFound().build();
    	return ResponseEntity.ok((!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
    			itemService.findArticlesByUser(id) : itemService.findArticlesByUserPageable(page, size, id));
    }
    
    @GetMapping("/user/questions")
    @Authorization
    Iterable<QuestionSummaryResult> getQuestions(@CurrentUser User user, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    	return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findQuestionsByUser(user.getId()) : itemService.findQuestionsByUserPageable(page, size, user.getId());
    }
    
    @GetMapping("/users/{id}/questions")
    ResponseEntity<?> getQuestionsByUser(@PathVariable("id") Long id, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    	if (userService.findById(id, false) == null)
    		return ResponseEntity.notFound().build();
    	return ResponseEntity.ok((!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
    			itemService.findQuestionsByUser(id) : itemService.findQuestionsByUserPageable(page, size, id));
    }
    
    @GetMapping("/user/answers")
    @Authorization
    Iterable<AnswerSummaryResult> getAnswers(@CurrentUser User user, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    	return (!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
				itemService.findAnswersByUser(user.getId()) : itemService.findAnswersByUserPageable(page, size, user.getId());
    }
    
    @GetMapping("/users/{id}/answers")
    ResponseEntity<?> getAnswersByUser(@PathVariable("id") Long id, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
    	if (userService.findById(id, false) == null)
    		return ResponseEntity.notFound().build();
    	return ResponseEntity.ok((!FormatValidator.checkNonNegativeInt(page) || !FormatValidator.checkPositiveInt(size)) ? 
    			itemService.findAnswersByUser(id) : itemService.findAnswersByUserPageable(page, size, id));
    }
    
    @GetMapping("/user/favorite")
    @Authorization
    Iterable<BaseSummaryResult> getFavorite(@CurrentUser User user) {
    	return interactionService.getUserFavorites(user);
    }
    
    @GetMapping("/users/{id}/favorite")
    ResponseEntity<?> getFavoriteByUser(@PathVariable("id") Long id) {
    	User user = userService.findById(id, false);
    	return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(interactionService.getUserFavorites(user));
    }
    
    @GetMapping("/user/following/questions")
    @Authorization
    Iterable<QuestionSummaryResult> getFollowingQuestions(@CurrentUser User user) {
    	return interactionService.getUserFollowingQuestions(user);
    }
    
    @GetMapping("/users/{id}/following/questions")
    ResponseEntity<?> getFollowingQuestionsByUser(@PathVariable("id") Long id) {
    	User user = userService.findById(id, false);
    	return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(interactionService.getUserFollowingQuestions(user));
    }
    
    @GetMapping("/user/following/users")
    @Authorization
    Iterable<Profile> getFollowees(@CurrentUser User user) {
    	return interactionService.getUserFollowees(user);
    }
    
    @GetMapping("/users/{id}/following/users")
    ResponseEntity<?> getFolloweesByUser(@PathVariable("id") Long id) {
    	User user = userService.findById(id, false);
    	return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(interactionService.getUserFollowees(user));
    }
    
    @GetMapping("/users/{id}/follow")
	@Authorization
	ResponseEntity<?> checkFollowUser(@CurrentUser User user, @PathVariable("id") Long id) {
    	User followee = userService.findById(id, false);
        return followee == null ? ResponseEntity.notFound().build() :
        	ResponseEntity.ok(new FollowStatusResult(interactionService.checkFollowUser(user, followee)));
	}
	
	@PostMapping("/users/{id}/follow")
	@Authorization
	ResponseEntity<?> doFollowUser(@CurrentUser User user, @PathVariable("id") Long id) {
		User followee = userService.findById(id, false);
        if (followee == null)
        	return ResponseEntity.notFound().build();
        interactionService.doFollowUser(user, followee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/users/{id}/follow")
	@Authorization
	ResponseEntity<?> cancelFollowUser(@CurrentUser User user, @PathVariable("id") Long id) {
		User followee = userService.findById(id, false);
        if (followee == null)
        	return ResponseEntity.notFound().build();
        interactionService.cancelFollowUser(user, followee);
        return ResponseEntity.noContent().build();
	}
}