package rw.transport.VubaRide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rw.transport.vubaride.service.EmailService;

@SpringBootApplication
public class EmailTestApplication implements CommandLineRunner {

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(EmailTestApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String toEmail = "kabanofesto1@gmail.com";
        String subject = "Test Email";
        String body = "This is a test email to verify the configuration.";
        try {
            emailService.sendSimpleEmail(toEmail, subject, body);
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
