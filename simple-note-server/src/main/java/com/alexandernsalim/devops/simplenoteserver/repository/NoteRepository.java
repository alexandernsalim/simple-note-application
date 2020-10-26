package com.alexandernsalim.devops.simplenoteserver.repository;

import com.alexandernsalim.devops.simplenoteserver.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByAuthor(String author);
}
