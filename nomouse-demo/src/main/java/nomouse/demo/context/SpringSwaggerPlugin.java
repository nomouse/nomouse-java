package nomouse.demo.context;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.configuration.SwaggerGlobalSettings;
import com.mangofactory.swagger.models.configuration.SwaggerModelsConfiguration;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@EnableSwagger
public class SpringSwaggerPlugin {

    public static final String SWAGGER_GROUP = "mobile-api";

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

//    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
//        this.springSwaggerConfig = springSwaggerConfig;
//    }

    @Autowired
    private SwaggerModelsConfiguration swaggerModelsConfiguration;

    @Bean
    public SwaggerGlobalSettings swaggerGlobalSettings() {
        SwaggerGlobalSettings swaggerGlobalSettings = new SwaggerGlobalSettings();
        swaggerGlobalSettings.setGlobalResponseMessages(springSwaggerConfig.defaultResponseMessages());
        swaggerGlobalSettings.setIgnorableParameterTypes(springSwaggerConfig.defaultIgnorableParameterTypes());
        return swaggerGlobalSettings;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "女王驾到API",
                "",
                "",
                "",
                "",
                ""
        );
//        ApiInfo apiInfo = new ApiInfo(
//                "My Apps API Title",
//                "My Apps API Description",
//                "My Apps API terms of service",
//                "My Apps API Contact Email",
//                "My Apps API Licence Type",
//                "My Apps API License URL"
//        );
        return apiInfo;
    }
}