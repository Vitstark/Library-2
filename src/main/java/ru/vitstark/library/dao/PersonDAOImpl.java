package ru.vitstark.library.dao;

import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PersonDAOImpl implements PersonDAO {

    private static final String SQL_FIND_PERSON_BY_ID = "SELECT * FROM person WHERE id = ?";
    private static final String SQL_FIND_SORTED_PEOPLE = "SELECT * FROM person ORDER BY name";
    private static final String SQL_DELETE_PERSON_BY_ID = "DELETE FROM person WHERE id = ?";
    private static final String SQL_UPDATE_PERSON = "UPDATE person SET name = ?, year_of_birth = ? WHERE id = ?";
    private static final RowMapper<Person> personMapper = (rs, rowNum) ->  Person.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .yearOfBirth(rs.getInt("year_of_birth"))
            .build();


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Person person) {
        Map<String, Object> params = new HashMap<>();

        params.put("name", person.getName());
        params.put("year_of_birth", person.getYearOfBirth());

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        Long id = insert.withTableName("person")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource(params))
                .longValue();

        person.setId(id);
    }

    public Optional<Person> findByID(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_PERSON_BY_ID, personMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(Person person) {
        int affectedRows = jdbcTemplate.update(SQL_UPDATE_PERSON, person.getName(), person.getYearOfBirth(), person.getId());

        if (affectedRows < 1) {
            throw new RuntimeException("person with id = " + person.getId() + " can`t be updated");
        }
    }

    public void delete(Long id) {
        int affectedRows = jdbcTemplate.update(SQL_DELETE_PERSON_BY_ID, id);

        if (affectedRows < 1) {
            throw new RuntimeException("person with id = " + id + " can`t be deleted");
        }
    }

    public List<Person> findAllOrderedByName() {
        return jdbcTemplate.query(SQL_FIND_SORTED_PEOPLE, personMapper);
    }
}
