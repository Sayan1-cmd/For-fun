package com.project.project.service;

import com.project.project.entity.Box;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FrontService {

    private final NamedParameterJdbcTemplate template;

    public FrontService(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public void getAllBoxes(Box box, List<Box> allBoxes) {
        String sql = "select ID, CONTAINED_IN containIn from Box where CONTAINED_IN=:id";
        Map<String, Integer> params = new HashMap<>();
        params.put("id", box.getId());
        List<Box> boxes = template.query(sql, params, new BoxMapper());
        if (boxes != null && !boxes.isEmpty()) {
            allBoxes.addAll(boxes);
            for (Box model : boxes) {
                getAllBoxes(model, allBoxes);
            }
        }
    }

    private static class BoxMapper implements RowMapper<Box> {
        @Override
        public Box mapRow(ResultSet resultSet, int i) throws SQLException {
            Box entity = new Box();
            entity.setId(resultSet.getInt("ID"));
            entity.setContainedIn(resultSet.getInt("CONTAINED_IN"));
            return entity;
        }
    }
}
