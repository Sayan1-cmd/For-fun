package com.project.project;

import com.project.project.entity.ApplicationUser;
import com.project.project.model.Item;
import com.project.project.model.XMLParsingResult;
import com.project.project.service.ApplicationUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ProjectApplication {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static List<com.project.project.entity.Box> RESULT_BOXES = new ArrayList<>();
    private static List<Item> ALL_ITEMS = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectApplication.class);

    public ProjectApplication(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProjectApplication.class);
        String fileName = args[0];
        if (StringUtils.hasText(fileName)) {
            LOGGER.info("input filename = [{}]", fileName);
            XMLParsingResult result = MigrationTrigger.prepareData(fileName);
            RESULT_BOXES.addAll(result.getBoxList());
            ALL_ITEMS.addAll((result.getItemList()));
            app.run(args);
        } else {
            LOGGER.info("No additional info provided for the application");
            app.run(args);
        }
    }

    @Bean
    protected CommandLineRunner run(ApplicationUserService service, PasswordEncoder encoder) {
        return args -> {
            for (com.project.project.entity.Box box : RESULT_BOXES) {
                Map<String, Integer> params = new HashMap<>();
                params.put("id", box.getId());
                params.put("contIn", box.getContainedIn());
                jdbcTemplate.update("insert into BOX(id, contained_in) values(:id,:contIn)", params);
            }
            for (Item item : ALL_ITEMS) {
                Map<String, Object> params = new HashMap<>();
                params.put("id", item.getItemId());
                params.put("contIn", item.getContainedIn());
                params.put("color", item.getItemColor());
                jdbcTemplate.update("insert into ITEM(id, contained_in, color) values(:id,:contIn, :color)", params);
            }
            ApplicationUser user = new ApplicationUser();
            user.setUserName("admin");
            String password = encoder.encode("password");
            user.setPassword(password);
            service.saveUser(user);
        };
    }

    @Bean
    public PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }


}
