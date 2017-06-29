package me.sjtumeow.meow;

import me.sjtumeow.meow.dao.SoftDeleteRepositoryFactoryBean;
import me.sjtumeow.meow.dao.SoftDeleteRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = SoftDeleteRepositoryImpl.class, repositoryFactoryBeanClass = SoftDeleteRepositoryFactoryBean.class)
public class MeowApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeowApplication.class, args);
	}
}
