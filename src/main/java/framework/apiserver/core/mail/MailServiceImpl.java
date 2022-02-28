package framework.apiserver.core.mail;

import framework.apiserver.core.file.FileService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@AllArgsConstructor
@Service
public class MailServiceImpl implements MailService{
    private final JavaMailSender mailSender;
    private final FileService fileService;

    @Override
    public void sendSimpleMessage(MailDto mailDto) {
        String from = "no-reply@gmail.com";
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true, "UTF-8");
            mimeMailMessage.setContent(mailDto.getText(), "text/html; charset=utf-8");

            if(!StringUtils.hasText(mailDto.getFrom()))mailDto.setFrom(from);

            mimeMessageHelper.setFrom(mailDto.getFrom());
            mimeMessageHelper.setTo(mailDto.getTo());
            mimeMessageHelper.setSubject(mailDto.getSubject());
            mimeMessageHelper.setText(mailDto.getText());

            mailSender.send(mimeMailMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
