package com.project.project;

import com.project.project.entity.ApplicationUser;
import com.project.project.model.Box;
import com.project.project.model.Item;
import com.project.project.model.Storage;
import com.project.project.service.ApplicationUserService;
import com.project.project.service.BoxService;
import com.project.project.service.ItemService;
import com.project.project.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

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
        if (implementTransformation(fileName)) {
            app.run(args);
        } else {
            LOGGER.info("No info provided for the application");
            app.run(args);
        }
    }

    private static boolean implementTransformation(String fileName) {
        System.out.println("input fileName: " + fileName);
        Map<String, String> fileNames = new HashMap<>();
        fileNames.put("InputFile", "/files/InputFile.xml");
        fileNames.put("OneMoreFile", "/files/OneMoreFile.xml");
        if (fileNames.containsKey(fileName)) {
            String filePath = fileNames.get(fileName);
            JAXBContext context;
            Storage store;
            URL resource = ProjectApplication.class.getResource(filePath);
            File file;
            try {
                file = Paths.get(resource.toURI()).toFile();
                InputStream inStream = new FileInputStream(file);
                System.out.println("success");
                context = JAXBContext.newInstance(Storage.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                store = (Storage) unmarshaller.unmarshal(inStream);
            } catch (URISyntaxException e) {
                LOGGER.error("Exception occurred while getting the data resource", e);
                throw new RuntimeException();
            } catch (Exception e) {
                LOGGER.error("Exception occurred", e);
                throw new RuntimeException();
            }
            if (store != null) {
                List<Box> boxes = store.getBoxes();
                for (Box box : boxes) {
                    List<Integer> containedIds = new ArrayList<>();
                    MigrationTrigger.setContainedIn(box, containedIds, ALL_ITEMS);
                    RESULT_BOXES.addAll(MigrationTrigger.init(box));
                }
            }
        }
        return true;
    }

    @Bean
    public PasswordEncoder encode(){
        return new BCryptPasswordEncoder();
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


}
