package com.project.project;

import com.project.project.model.Box;
import com.project.project.model.Item;
import com.project.project.model.Storage;
import com.project.project.service.BoxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ProjectApplication {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private static MigrationTrigger trigger = new MigrationTrigger();
    private static List<com.project.project.entity.Box> resultBoxes = new ArrayList<>();
    private static List<Item> allItems = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProjectApplication.class);
        String fileName = args[0];
        if (implementTransformation(fileName)) {
            app.run(args);
        }
    }

    public static boolean implementTransformation(String fileName) {
        System.out.println("input fileName: " + fileName);
        Map<String, String> fileNames = new HashMap<>();
        fileNames.put("InputFile", "/files/InputFile.xml");
        fileNames.put("OneMoreFile", "/files/OneMoreFile.xml");
        if (fileNames.containsKey(fileName)) {
            String filePath = fileNames.get(fileName);
            JAXBContext context = null;
            Storage store = null;
            URL resource = ProjectApplication.class.getResource(filePath);
            File file = null;
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
                    trigger.setContainedIn(box, containedIds, allItems);
                    resultBoxes.addAll(trigger.init(box));
                }
            }
        }
        return true;
    }

    @Bean
    public CommandLineRunner run(BoxService boxService) throws Exception {
        return args -> {
            for (com.project.project.entity.Box box : resultBoxes) {
                Map<String, Integer> params = new HashMap<>();
                params.put("id", box.getId());
                params.put("contIn", box.getContainedIn());
                jdbcTemplate.update("insert into BOX(id, contained_in) values(:id,:contIn)", params);
            }
            for (Item item : allItems) {
                Map<String, Object> params = new HashMap<>();
                params.put("id", item.getItemId());
                params.put("contIn", item.getContainedIn());
                params.put("color", item.getItemColor());
                jdbcTemplate.update("insert into ITEM(id, contained_in, color) values(:id,:contIn, :color)", params);
            }
        };
    }
}
