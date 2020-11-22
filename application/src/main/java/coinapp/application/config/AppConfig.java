package coinapp.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@Configuration
@ComponentScan(basePackages = "coinapp.domain.service")
public class AppConfig {


}
