
import javax.swing.plaf.synth.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.Value;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3service {

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;
    @Bean
    public S3Client s3Cilent(){

        S3Client.builder()
                .region(Region.of(region))

    }
	
	
}
