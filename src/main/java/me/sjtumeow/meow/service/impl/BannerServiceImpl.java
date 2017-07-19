package me.sjtumeow.meow.service.impl;

import me.sjtumeow.meow.dao.AnswerRepository;
import me.sjtumeow.meow.dao.ArticleRepository;
import me.sjtumeow.meow.dao.BannerRepository;
import me.sjtumeow.meow.dao.MomentRepository;
import me.sjtumeow.meow.dao.QuestionRepository;
import me.sjtumeow.meow.model.Banner;
import me.sjtumeow.meow.model.Item;
import me.sjtumeow.meow.model.form.UpdateBannerForm;
import me.sjtumeow.meow.model.result.BannerResult;
import me.sjtumeow.meow.service.BannerService;
import me.sjtumeow.meow.util.FormatValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private MomentRepository momentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public List<BannerResult> findAll() {
        Iterable<Banner> banners = bannerRepository.findAllActive(new Sort(Direction.ASC, "displayOrder"));

        List<BannerResult> result = new ArrayList<BannerResult>();
        for (Banner banner : banners) {
            result.add(new BannerResult(banner));
        }

        return result;
    }

    @Transactional
    public String update(List<UpdateBannerForm> ubfl) {

        if (ubfl.size() > 5) {
            return "Banner 个数不能超过 5 个";
        }

        for (UpdateBannerForm ubf : ubfl) {

            if (ubf.getDisplayOrder() == null)
                return "缺少显示顺序";
            if (ubf.getImage() == null)
                return "缺少图片";
            if (ubf.getItemId() == null)
                return "缺少项目编号";
            if (!FormatValidator.checkItemType(ubf.getItemType()))
                return "非法的项目类型";

            switch (ubf.getItemType()) {
                case Item.ITEM_TYPE_MOMENT:
                    if (!momentRepository.existsActive(ubf.getItemId()))
                        return "编号为 " + ubf.getItemId() + " 的点滴不存在";
                    break;
                case Item.ITEM_TYPE_ARTICLE:
                    if (!articleRepository.existsActive(ubf.getItemId()))
                        return "编号为 " + ubf.getItemId() + " 的文章不存在";
                    break;
                case Item.ITEM_TYPE_QUESTION:
                    if (!questionRepository.existsActive(ubf.getItemId()))
                        return "编号为 " + ubf.getItemId() + " 的问题不存在";
                    break;
                case Item.ITEM_TYPE_ANSWER:
                    if (!answerRepository.existsActive(ubf.getItemId()))
                        return "编号为 " + ubf.getItemId() + " 的回答不存在";
                    break;
                default:
                    break;
            }
        }

        Collections.sort(ubfl, new Comparator<UpdateBannerForm>() {
            @Override
            public int compare(UpdateBannerForm lhs, UpdateBannerForm rhs) {
                Integer res = lhs.getDisplayOrder().compareTo(rhs.getDisplayOrder());
                return res == 0 ? 0 : res / Math.abs(res);
            }
        });

        for (int i = 0; i < ubfl.size(); ++i) {
            ubfl.get(i).setDisplayOrder(i);
        }

        bannerRepository.deleteAll();

        for (UpdateBannerForm ubf : ubfl) {
            switch (ubf.getItemType()) {
                case Item.ITEM_TYPE_MOMENT:
                    bannerRepository.save(new Banner(ubf.getDisplayOrder(), ubf.getImage(),
                            momentRepository.findOneActive(ubf.getItemId())));
                    break;
                case Item.ITEM_TYPE_ARTICLE:
                    bannerRepository.save(new Banner(ubf.getDisplayOrder(), ubf.getImage(),
                            articleRepository.findOneActive(ubf.getItemId())));
                    break;
                case Item.ITEM_TYPE_QUESTION:
                    bannerRepository.save(new Banner(ubf.getDisplayOrder(), ubf.getImage(),
                            questionRepository.findOneActive(ubf.getItemId())));
                    break;
                case Item.ITEM_TYPE_ANSWER:
                    bannerRepository.save(new Banner(ubf.getDisplayOrder(), ubf.getImage(),
                            answerRepository.findOneActive(ubf.getItemId())));
                    break;
                default:
                    break;
            }
        }

        return "";

    }

}
