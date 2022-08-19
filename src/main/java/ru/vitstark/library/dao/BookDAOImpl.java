package ru.vitstark.library.dao;

import org.example.models.Book;
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

public class BookDAOImpl implements BookDAO {

    //language=SQL
    private static final String SQL_FIND_BOOKS_OF_READER = "SELECT * FROM book WHERE person_id = ? ORDER BY name";
    //language=SQL
    private static final String SQL_FIND_BOOK_BY_ID = "SELECT * FROM book WHERE id = ?";
    //language=SQL
    private static final String SQL_UPDATE_BOOK = "UPDATE book SET name = ?, author = ?, year_of_create = ?, person_id = ? WHERE id = ?";
    //language=SQL
    private static final String SQL_DELETE_BY_ID = "DELETE FROM book WHERE id = ?";

    private static final RowMapper<Book> bookMapper = (row, rowNumber) -> Book.builder()
            .id(row.getLong("id"))
            .name(row.getString("name"))
            .author(row.getString("author"))
            .date(row.getInt("year_of_create"))
            .personId(row.getLong("person_id"))
            .build();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Book> findBooksOfReaderOrderByName(Person person) {
        return jdbcTemplate.query(SQL_FIND_BOOKS_OF_READER, bookMapper, person.getId());
    }

    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book", bookMapper);
    }

    public void save(Book book) {
        Map<String, Object> paramsAsMap = new HashMap<>();

        paramsAsMap.put("name", book.getName());
        paramsAsMap.put("author", book.getAuthor());
        paramsAsMap.put("year_of_create", book.getDate());
        paramsAsMap.put("person_id", book.getPersonId());

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        Long id = insert.withTableName("book")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(new MapSqlParameterSource(paramsAsMap))
                .longValue();

        book.setId(id);
    }

    public Optional<Book> findByID(Long id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BOOK_BY_ID, bookMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(Book book) {
        int affectedRows = jdbcTemplate.update(SQL_UPDATE_BOOK,
                book.getName(),
                book.getAuthor(),
                book.getDate(),
                book.getPersonId(),
                book.getId());

        if (affectedRows < 1) {
            throw new RuntimeException("Book with name " + book.getName() + " can`t be updated");
        }
    }

    public void delete(Long id) {
        int affectedRows = jdbcTemplate.update(SQL_DELETE_BY_ID, id);

        if (affectedRows < 1) {
            throw new RuntimeException("Book with id " + id + " can`t be deleted");
        }
    }
}
