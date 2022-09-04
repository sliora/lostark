package spring.lostark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {

    @GetMapping("/upload/{param}")
    public String imageView(@PathVariable String param) {

        return "image";
    }

}
