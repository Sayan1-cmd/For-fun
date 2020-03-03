package com.project.project;

import com.project.project.entity.Box;
import com.project.project.entity.Item;
import com.project.project.interacting.PostRequest;
import com.project.project.service.BoxService;
import com.project.project.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class FrontController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);
    private final BoxService boxService;
    private final ItemService itemService;
    private final NamedParameterJdbcTemplate template;
    private static final Integer REQUEST_LENGTH_THRESHOLD = 25;

    public FrontController(BoxService boxService, ItemService itemService, NamedParameterJdbcTemplate template) {
        this.boxService = boxService;
        this.template = template;
        this.itemService = itemService;
    }

    @PostMapping(value = "/getIds", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> createInfo(@RequestHeader(name = "Content-Length") int length, @RequestBody PostRequest request) {
        if (REQUEST_LENGTH_THRESHOLD < length) {
            return new ResponseEntity<>(Collections.singletonList(length), HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("createInfo: request=[{}]", request);
        Box box = boxService.getBoxById(request.getBox());
        List<Box> allBoxes = new ArrayList<>();
        allBoxes.add(box);
        getAllBoxes(box, allBoxes);
        List<Item> items = itemService.getByBoxesAndColor(allBoxes, request.getColor());
        List<Integer> responseIds = items.stream().map(Item::getId).collect(Collectors.toList());
        return new ResponseEntity<>(responseIds, HttpStatus.OK);
    }

    private void getAllBoxes(Box box, List<Box> allBoxes) {
        String sql = ("select ID, CONTAINED_IN containIn from Box where CONTAINED_IN=:id");
        Map<String, Integer> params = new HashMap();
        params.put("id", box.getId());
        List<Box> boxes = template.query(sql, params, new BoxMapper());
        if (boxes != null && !boxes.isEmpty()) {
            allBoxes.addAll(boxes);
            for (Box model : boxes) {
                getAllBoxes(model, allBoxes);
            }
        }
    }

    private static class BoxMapper implements RowMapper {
        @Override
        public Box mapRow(ResultSet resultSet, int i) throws SQLException {
            Box entity = new Box();
            entity.setId(resultSet.getInt("ID"));
            entity.setContainedIn(resultSet.getInt("CONTAINED_IN"));
            return entity;
        }
    }
}
