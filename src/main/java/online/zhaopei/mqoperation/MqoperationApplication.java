package online.zhaopei.mqoperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MqoperationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqoperationApplication.class, args);
	}

}
