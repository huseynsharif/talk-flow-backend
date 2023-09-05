package com.huseynsharif.talkflow.core.configs;

import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<String, String>();

        config.put("cloud_name","ddpr4hz2f");
        config.put("api_key","763874291443913");
        config.put("api_secret","bpQ-0px8UJtZTdnPg2aYQH9qqUc");

        return new Cloudinary(config);
    }

}
