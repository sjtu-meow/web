package me.sjtumeow.meow.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import me.sjtumeow.meow.dao.AnswerRepository;
import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.BannerRepository;
import me.sjtumeow.meow.dao.CommentRepository;
import me.sjtumeow.meow.dao.FavoriteRepository;
import me.sjtumeow.meow.dao.FollowQuestionRepository;
import me.sjtumeow.meow.dao.FollowUserRepository;
import me.sjtumeow.meow.dao.LikeRepository;
import me.sjtumeow.meow.dao.MediaRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.PushArchiveRepository;
import me.sjtumeow.meow.dao.QuestionRepository;
import me.sjtumeow.meow.dao.ReportRepository;
import me.sjtumeow.meow.model.Answer;
import me.sjtumeow.meow.model.Article;
import me.sjtumeow.meow.model.Banner;
import me.sjtumeow.meow.model.Comment;
import me.sjtumeow.meow.model.Favorite;
import me.sjtumeow.meow.model.FollowQuestion;
import me.sjtumeow.meow.model.FollowUser;
import me.sjtumeow.meow.model.Like;
import me.sjtumeow.meow.model.Media;
import me.sjtumeow.meow.model.Media.MediaType;
import me.sjtumeow.meow.model.Moment;
import me.sjtumeow.meow.model.Profile;
import me.sjtumeow.meow.model.PushArchive;
import me.sjtumeow.meow.model.Question;
import me.sjtumeow.meow.model.Report;
import me.sjtumeow.meow.model.User;
import me.sjtumeow.meow.service.UserService;

@Component
public class SeederRunner implements ApplicationRunner {

    @Autowired
    private MomentRepository momentRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FollowQuestionRepository followQuestionRepository;

    @Autowired
    private FollowUserRepository followUserRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PushArchiveRepository pushArchiveRepository;

    @Autowired
    private UserService userService;

    Faker faker = new Faker();

    @Override
    public void run(ApplicationArguments args) {

        // Add two users

        Long userId1 = userService.create("13333333333", "meow233", true, "喵喵喵的伙伴", "Web 开发专家",
                "http://lorempixel.com/50/50");
        User user1 = userService.findById(userId1, false);
        Profile profile1 = user1.getProfile();

        Long userId2 = userService.create("16677778888", "test123", false, "Hacker", "<script>alert('xss!')</script>",
                null);
        User user2 = userService.findById(userId2, false);
        Profile profile2 = user2.getProfile();

        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("Running seeder i = %d...", i));

            Long userId = userService.create(String.format("188%d", 88888001 + i), "111111", false,
                    String.format("吃瓜群众%d", i + 1), i % 2 == 0 ? "不存在的" : null, "http://lorempixel.com/50/50");

            // Add a moment

            Moment moment = new Moment();
            moment.setProfile(i % 2 == 0 ? profile1 : profile2);
            moment.setContent(faker.shakespeare().hamletQuote());
            momentRepository.save(moment);

            if (i % 10 == 0) {
                mediaRepository
                        .save(new Media(MediaType.Video, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", moment));
            } else {
                for (int j = 0; j < i % 10; ++j) {
                    mediaRepository.save(new Media(MediaType.Image, "http://lorempixel.com/200/200", moment));
                }
            }

            Comment comment1 = new Comment(moment, profile1, String.format("神奇点滴评论%d", i + 1));
            commentRepository.save(comment1);
            commentRepository.save(new Comment(moment, profile2, String.format("神奇点滴又一个评论%d", i + 1)));

            // Add an article

            Article article = new Article();
            article.setTitle(String.format("铲屎官必读文章(%d)", i + 1));
            article.setSummary("99% 的铲屎官都不知道的主子特性！删前速看！");
            article.setContent("<p style=\"color:#63c;\">这是第一段</p><p>这是第二段</p>");
            article.setCover("http://lorempixel.com/400/200");
            article.setProfile(i % 2 == 0 ? profile1 : profile2);
            articleRepository.save(article);

            Comment comment2 = new Comment(article, profile2, String.format("神奇文章评论%d", i + 1));
            commentRepository.save(comment2);
            commentRepository.save(new Comment(article, profile1, String.format("神奇文章又一个评论%d", i + 1)));

            // Add a question 

            Question question = new Question();
            question.setTitle("四川的猫吃辣吗？");
            question.setContent("如题，就是想问问在四川领养了一只猫以后，给它秋刀鱼要加辣椒吗？就这个问题...");
            question.setProfile(i % 2 == 0 ? profile1 : profile2);
            questionRepository.save(question);

            // A question has no comments

            // Add an answer

            Answer answer = new Answer();
            answer.setContent("<p style=\"color:#63c;\">吃的！吃的！</p><p>但就是哭的样子很难看...</p>");
            answer.setQuestion(question);
            answer.setProfile(profile1);
            answerRepository.save(answer);

            Comment comment4 = new Comment(answer, profile2, String.format("神奇回答评论%d", i + 1));
            commentRepository.save(comment4);
            commentRepository.save(new Comment(answer, profile1, String.format("神奇回答又一个评论%d", i + 1)));

            // Interaction test

            if (i % 10 == 5) {

                // Like

                likeRepository.save(new Like(user1, moment));
                likeRepository.save(new Like(user2, article));
                likeRepository.save(new Like(user1, answer));

                // Favorite

                favoriteRepository.save(new Favorite(user1, moment));
                favoriteRepository.save(new Favorite(user1, article));
                favoriteRepository.save(new Favorite(user2, article));
                favoriteRepository.save(new Favorite(user2, answer));

                // Follow

                followQuestionRepository.save(new FollowQuestion(user1, question));
                followUserRepository.save(new FollowUser(user1, userService.findById(userId, false)));

                // Report

                reportRepository.save(new Report(moment, user1.getProfile(), "这点滴不清真啊！"));
                reportRepository.save(new Report(article, user1.getProfile(), "这文章不清真啊！"));
                reportRepository.save(new Report(question, user1.getProfile(), "这问题不清真啊！"));
                reportRepository.save(new Report(answer, user1.getProfile(), "这回答不清真啊！"));

                reportRepository.save(new Report(moment, user2.getProfile(), "这点滴不清真啊！"));
                reportRepository.save(new Report(article, user2.getProfile(), "这文章不清真啊！"));
                reportRepository.save(new Report(question, user2.getProfile(), "这问题不清真啊！"));
                reportRepository.save(new Report(answer, user2.getProfile(), "这回答不清真啊！"));

                reportRepository.save(new Report(comment1, user1.getProfile(), "这评论不清真啊！"));
                reportRepository.save(new Report(comment2, user2.getProfile(), "这评论不清真啊！"));
                reportRepository.save(new Report(comment4, user1.getProfile(), "这评论不清真啊！"));

            }

            // Soft delete test

            if (i % 10 == 0) {
                userService.delete(userId);
                articleRepository.softDelete(article);
                questionRepository.softDelete(question);
                answerRepository.softDelete(answer);
            }
            if (i % 10 == 3) {
                momentRepository.softDelete(moment);
            }
            if (i % 10 >= 8) {
                commentRepository.softDelete(comment1);
                commentRepository.softDelete(comment2);
                commentRepository.softDelete(comment4);
            }

            // XSS test

            if (i % 10 == 2) {
                moment.setContent("<script>alert('xss!')</script>");
                comment1.setContent("<script>alert('xss!')</script>");
                comment2.setContent("<script>alert('xss!')</script>");
                comment4.setContent("<script>alert('xss!')</script>");
                article.setTitle("<script>alert('xss!')</script>");
                article.setSummary("<script>alert('xss!')</script>");
                question.setTitle("<script>alert('xss!')</script>");
                question.setContent("<script>alert('xss!')</script>");
                momentRepository.save(moment);
                commentRepository.save(comment1);
                commentRepository.save(comment2);
                commentRepository.save(comment4);
                articleRepository.save(article);
                questionRepository.save(question);
            }

            // Add banners

            if (i == 1)
                bannerRepository.save(new Banner(3, "http://lorempixel.com/400/200", moment));
            else if (i == 2)
                bannerRepository.save(new Banner(2, "http://lorempixel.com/400/200", article));
            else if (i == 3)
                bannerRepository.save(new Banner(1, "http://lorempixel.com/400/200", question));
            else if (i == 4)
                bannerRepository.save(new Banner(0, "http://lorempixel.com/400/200", answer));

            // Push Archive

            if (i % 10 == 5) {
                pushArchiveRepository.save(new PushArchive(moment, "99% 的人都没注意到的点滴！删前速看！"));
                pushArchiveRepository.save(new PushArchive(article, "99% 的人都没看过的深度好文！删前速看！"));
                pushArchiveRepository.save(new PushArchive(question, "99% 的人都答不出的问题！删前速看！"));
                pushArchiveRepository.save(new PushArchive(answer, "99% 的人都意想不到的回答！删前速看！"));
            }

        }

    }

}
