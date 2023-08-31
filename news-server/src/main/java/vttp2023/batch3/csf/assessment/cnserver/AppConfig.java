package vttp2023.batch3.csf.assessment.cnserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/*
 * You are to use the S3 client created by createS3Client
 * Do not modify this file 
 * If you modify this file, you will invalidate any task that depends on this
 */
@Configuration
public class AppConfig {

	@Value("${s3.key.access}")
	private String accessKey;

	@Value("${s3.key.secret}")
	private String secretKey;

	@Value("${s3.bucket.endpoint}")
	private String bucketEndpoint;

	@Value("${s3.bucket.region}")
	private String region;

	@Bean
	public AmazonS3 createS3Client() {

		BasicAWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);

		EndpointConfiguration epConfig = new EndpointConfiguration(bucketEndpoint, region);

		return AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration(epConfig)
			.withCredentials(new AWSStaticCredentialsProvider(creds))
			.build();
	}
}
