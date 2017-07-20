package me.sjtumeow.meow.service;

import java.util.List;

import me.sjtumeow.meow.model.form.UpdateBannerForm;
import me.sjtumeow.meow.model.result.BannerResult;

public interface BannerService {

    List<BannerResult> findAll();

    String update(List<UpdateBannerForm> ubfl);

}
