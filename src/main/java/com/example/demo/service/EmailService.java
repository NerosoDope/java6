package com.example.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EmailService {
    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom("ntr22092300@gmail.com");

            mailSender.send(message);
            System.out.println("Email sent : " + to);

        } catch (MailException e) {
            throw new MailSendException("Error sending email");
        } catch (Exception e) {
            throw e;
        }

    }

    public void sendOTPtoUser(String email, String OTP){
        String content = "Vui lòng không chia sẽ mã OTP này.\n" +
                "\n" +
                "Mã OTP: "+OTP+"\n"+
                "Mã OTP này chỉ có hiệu lực trong vòng 10 phút. Nếu bạn không yêu cầu mã OTP, xin vui lòng bỏ qua email này.";
        this.sendEmail(email, "Mã Xác Nhận", content);
    }

}
