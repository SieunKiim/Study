package billing.sieunojt.domain.item.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class ItemController {

    @GetMapping
    public String postRedirectTest(
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes attributes
    ) throws IOException {
        attributes.addFlashAttribute(new MyData("sieun", 22, "010-3423-3435"));
        response.sendRedirect("/test/receive");
        return "send redirect!";
    }

    @PostMapping("/receive")
    public MyData receiveTest(@RequestBody MyData myData) {
        return myData;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class MyData {
        private String name;
        private int age;
        private String phone_number;
    }
}
