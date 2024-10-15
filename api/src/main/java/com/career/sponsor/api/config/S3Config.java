package com.career.sponsor.api.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        // TODO: switch to IAM role
        Dotenv dotenv = Dotenv.load();
        String accessKeyId = dotenv.get("AWS_ACCESS_KEY_ID");
        String secretAccessKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
        Region region = Region.AP_NORTHEAST_2;

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .region(region)
                .build();
    }
}
