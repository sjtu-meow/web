package me.sjtumeow.meow.dao;

import me.sjtumeow.meow.model.Article;

public interface ArticleRepository extends SoftDeleteRepository<Article, Long> {
}
