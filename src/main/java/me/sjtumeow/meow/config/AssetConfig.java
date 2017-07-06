package me.sjtumeow.meow.config;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by Vincent on 2017/3/30.
 */
@ControllerAdvice
public class AssetConfig {
    private AssetProperties.Asset admin, publik;

    @Autowired
    public AssetConfig(AssetProperties assetProperties) {
        admin = assetProperties.getAdmin();
        publik = assetProperties.getPublic();
        addAssetPrefix(admin);
        addAssetPrefix(publik);
    }

    private void addAssetPrefix(AssetProperties.Asset asset) {
        asset.setStyles(asset.getStyles().stream().map(s -> "/styles/" + s).collect(Collectors.toList()));
        asset.setScripts(asset.getScripts().stream().map(s -> "/scripts/" + s).collect(Collectors.toList()));
    }

    @ModelAttribute("adminAsset")
    public AssetProperties.Asset getAdminAsset() {
        return admin;
    }

    @ModelAttribute("publicAsset")
    public AssetProperties.Asset getPublicAsset() {
        return publik;
    }

}
