package com.alexandernsalim.devops.simplenoteserver.service;

import com.alexandernsalim.devops.simplenoteserver.contract.request.NoteRequest;
import com.alexandernsalim.devops.simplenoteserver.model.Note;
import com.alexandernsalim.devops.simplenoteserver.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public List<Note> getAllNotes(String author) {
        return repository.findAllByAuthor(author);
    }

    public Note getNote(Integer id) {
        Optional<Note> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Note is not found");
        }
    }

    public Note createNote(NoteRequest request) {
        Timestamp now = Timestamp.from(Instant.now());
        Note note = Note.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .author(request.getAuthor())
                .createdAt(now)
                .updatedAt(now)
                .build();

        return repository.save(note);
    }

    public Note updateNote(Integer id, NoteRequest request) {
        Optional<Note> optional = repository.findById(id);

        if (optional.isPresent()) {
            Note note = optional.get();

            note.setTitle(request.getTitle());
            note.setDescription(request.getDescription());
            note.setAuthor(request.getAuthor());
            return repository.save(note);
        } else {
            throw new RuntimeException("Note is not found");
        }
    }

    public void deleteNote(Integer id) throws IllegalArgumentException {
        repository.deleteById(id);
    }
}
