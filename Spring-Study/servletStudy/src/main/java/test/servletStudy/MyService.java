package test.servletStudy;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    public String greeting() {
        return "this is my home";
    }
}
