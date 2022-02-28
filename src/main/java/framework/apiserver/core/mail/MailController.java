package framework.apiserver.core.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mail")
@CrossOrigin
public class MailController {
    private final MailService mailService;

    @PostMapping("/")
    void sendEmail(@RequestBody MailDto mailDto) {
        mailService.sendSimpleMessage(mailDto);
    }
}
