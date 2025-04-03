package kr.hhplus.be.server.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "HHPlus Concert API",
                version = "v1",
                description = "HHPlus 콘서트 예약 시스템 API 명세"
        ),
        servers = {
                @Server(url = "/", description = "Local Server")
        }
)
public class SwaggerConfig {
}
