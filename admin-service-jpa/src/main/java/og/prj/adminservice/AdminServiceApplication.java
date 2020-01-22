package og.prj.adminservice;

import og.prj.adminservice.jpafiles.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class AdminServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(AdminServiceApplication.class, args);
	}

}
