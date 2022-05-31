package by.tms.dzen.yandexdzenrestc51;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YandexDzenRestC51Application {
	public static void main(String[] args) {
		SpringApplication.run(YandexDzenRestC51Application.class, args);
	}
}
