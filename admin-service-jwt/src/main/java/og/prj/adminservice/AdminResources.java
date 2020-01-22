package og.prj.adminservice;

import og.prj.adminservice.jpafiles.UserRepository;
import og.prj.adminservice.jpafiles.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.security.InvalidParameterException;

@Controller
public class AdminResources implements WebMvcConfigurer {


    @Autowired
    protected UserRepository userRep;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showForm(Users users) {
        return "form";
    }

    @PostMapping("/")
    public String checkPersonInfo(@Valid Users users, BindingResult bindingResult) throws InvalidParameterException{

        if (bindingResult.hasErrors()) {
            return "form";
        } else if(userRep.findByUserName(users.getUserName()).isPresent()) {

           throw new InvalidParameterException("username already exists!!");
        } else {
            userRep.save(users);
        }

        return "redirect:/results";
    }

}
