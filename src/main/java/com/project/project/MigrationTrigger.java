package com.project.project;

import com.project.project.model.Box;
import com.project.project.model.Item;
import com.project.project.model.Storage;
import com.project.project.model.XMLParsingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

@Component
public class MigrationTrigger {

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationTrigger.class);
    private static List<com.project.project.entity.Box> RESULT_BOXES = new ArrayList<>();
    private static List<Item> ALL_ITEMS = new ArrayList<>();


    protected static XMLParsingResult prepareData(String fileName) {
        Map<String, String> fileNames = new HashMap<>();
        List<com.project.project.entity.Box> boxList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();
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
                    boxList.addAll(MigrationTrigger.init(box));
                }
            }
        }
        return new XMLParsingResult(boxList, itemList);
    }

    protected static void setContainedIn(Box box, List<Integer> ids, List<Item> allItems) {
        List<Box> boxes = box.getBoxes();
        List<Item> items = box.getItems();
        if (items != null) {
            for (Item item : items) {
                item.setContainedIn(box.getBoxId());
                allItems.add(item);
            }
        }
        if (boxes != null) {
            for (Box entity : boxes) {
                entity.setContainedIn(box.getBoxId());
                ids.add(box.getBoxId());
                setContainedIn(entity, ids, allItems);
            }
        }
    }

    protected static List<com.project.project.entity.Box> init(Box box) {
        List<com.project.project.entity.Box> all = new ArrayList<>();
        com.project.project.entity.Box entity = new com.project.project.entity.Box();
        entity.setId(box.getBoxId());
        all.add(entity);
        fillTheBoxContainer(box, all);
        return all;
    }

    private static void fillTheBoxContainer(Box input, List<com.project.project.entity.Box> all) {
        List<Box> boxes = input.getBoxes();
        if (boxes != null) {
            for (Box model : boxes) {
                com.project.project.entity.Box box = new com.project.project.entity.Box();
                box.setId(model.getBoxId());
                box.setContainedIn(model.getContainedIn());
                all.add(box);
                fillTheBoxContainer(model, all);
            }
        }
    }
}