package tz.co.fasthub.ott;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ott")
public class OttController {

    @GetMapping("/sent")
    public String ottSent(){
        return "OttSent";
    }
}
