package com.project.project;

import com.project.project.entity.ApplicationUser;
import com.project.project.entity.Box;
import com.project.project.model.Item;
import com.project.project.model.XMLParsingResult;
import com.project.project.service.ApplicationUserService;
import com.project.project.service.BoxService;
import com.project.project.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ProjectApplication {


    private final NamedParameterJdbcTemplate jdbcTemplate;
    private List<com.project.project.entity.Box> RESULT_BOXES = new ArrayList<>();
    private  List<Item> ALL_ITEMS = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectApplication.class);
    private static final String MAIN_QUEUE = "mainQueue";
    private final MigrationTrigger trigger;
           @Value("${fileName}")
    private String neededFileName;
    private static final String EXCHANGE_QUEUE = "exchange";

    public ProjectApplication(NamedParameterJdbcTemplate jdbcTemplate, MigrationTrigger trigger) {
        this.jdbcTemplate = jdbcTemplate;
        this.trigger = trigger;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(ProjectApplication.class);
        app.run(args);
    }

    @Bean
    protected CommandLineRunner run(ApplicationUserService service, PasswordEncoder encoder, BoxService boxService, ItemService itemService) {
        return args -> {
            String fileName = neededFileName;
            if (StringUtils.hasText(fileName)) {
                LOGGER.info("input filename = [{}]", fileName);
                XMLParsingResult result = trigger.prepareData(fileName);
                RESULT_BOXES.addAll(result.getBoxList());
                ALL_ITEMS.addAll((result.getItemList()));
            List<Box> newBoxes = new ArrayList<>();
            for (com.project.project.entity.Box box : RESULT_BOXES) {
                Box boxEntity = new Box();
                boxEntity.setId(box.getId());
                boxEntity.setContainedIn(box.getContainedIn());
                newBoxes.add(boxEntity);
            }
            boxService.saveBoxes(newBoxes);
            List<Box> boxes = jdbcTemplate.query("select * from Box", (resultSet, i) -> {
                Box box = new Box();
                box.setId(resultSet.getInt("id"));
                box.setContainedIn(resultSet.getInt("contained_in"));
                return box;
            });
            LOGGER.info("[{}]", boxes.toString());
            List<com.project.project.entity.Item> newItems = new ArrayList<>();
            for (Item item : ALL_ITEMS) {
                com.project.project.entity.Item thing = new com.project.project.entity.Item();
                thing.setId(item.getItemId());
                thing.setBox(boxService.getBoxById(item.getContainedIn()));
                thing.setColor(item.getItemColor());
                newItems.add(thing);
            }
            itemService.saveItems(newItems);
            List<com.project.project.entity.Item> items = jdbcTemplate.query("select * from ITEM", (resultSet, i) -> {
                com.project.project.entity.Item item = new com.project.project.entity.Item();
                item.setId(resultSet.getInt("id"));
                item.setBox(boxService.getBoxById(resultSet.getInt("contained_in")));
                item.setColor(resultSet.getString("color"));
                return item;
            });
            LOGGER.info("[{}]", items.toString());
            ApplicationUser user = new ApplicationUser();
            user.setUserName("admin");
            String password = encoder.encode("password");
            user.setPassword(password);
            service.saveUser(user);

        }
    };}

    @Bean
    public PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }
}
