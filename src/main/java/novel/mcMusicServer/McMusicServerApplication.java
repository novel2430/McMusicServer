package novel.mcMusicServer;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class McMusicServerApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(McMusicServerApplication.class, args);
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // Delete Jackson Json
    for (int i = converters.size() - 1; i >= 0; i--) {
      if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
        converters.remove(i);
      }
    }
    // Cofig FastJson
    FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
        SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty);
    fastJsonConverter.setFastJsonConfig(fastJsonConfig);
    List<MediaType> fastMediaType = new ArrayList<MediaType>();
    MediaType mediaType = MediaType.parseMediaType("text/html;charset=UTF-8");
    fastMediaType.add(mediaType);
    fastMediaType.add(MediaType.APPLICATION_JSON);
    fastJsonConverter.setSupportedMediaTypes(fastMediaType);
    converters.add(fastJsonConverter);
  }

}
