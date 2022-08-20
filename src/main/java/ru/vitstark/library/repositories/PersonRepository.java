package ru.vitstark.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitstark.library.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
