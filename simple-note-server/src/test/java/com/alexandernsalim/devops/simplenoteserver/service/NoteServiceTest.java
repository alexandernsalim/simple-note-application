package com.alexandernsalim.devops.simplenoteserver.service;

import com.alexandernsalim.devops.simplenoteserver.contract.request.NoteRequest;
import com.alexandernsalim.devops.simplenoteserver.model.Note;
import com.alexandernsalim.devops.simplenoteserver.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class NoteServiceTest {
    private final Integer NON_EXIST_NOTE_ID = 0;
    private final Integer EXIST_NOTE_ID = 1;
    private final String AUTHOR = "Simple Note";

    private NoteRequest createRequest;
    private Note createdNote;
    private NoteRequest updateRequest;
    private Note updatedNote;
    private List<Note> listOfNotes;
    private Optional<Note> emptyOptional;
    private Optional<Note> nonEmptyOptional;

    @Mock
    private NoteRepository repository;

    @InjectMocks
    private NoteService service;

    @BeforeEach
    public void setUp() {
        initMocks(this);

        Timestamp now = Timestamp.from(Instant.now());
        createRequest = NoteRequest.builder()
                .title("Among Us")
                .description("Hint of impostor")
                .author("Impostor")
                .build();
        createdNote = Note.builder()
                .title("Among Us")
                .description("Hint of impostor")
                .author("Impostor")
                .createdAt(now)
                .updatedAt(now)
                .build();
        updateRequest = NoteRequest.builder()
                .title("Among Us")
                .description("Hint of impostor, they can use vent")
                .author("Impostor")
                .build();
        updatedNote = Note.builder()
                .title("Among Us")
                .description("Hint of impostor, they can use vent")
                .author("Impostor")
                .createdAt(now)
                .updatedAt(now)
                .build();
        emptyOptional = Optional.empty();
        nonEmptyOptional = Optional.of(createdNote);
        listOfNotes = new ArrayList<>();
        listOfNotes.add(createdNote);
        listOfNotes.add(updatedNote);
    }

    @Test
    public void testCreateNote() {
        when(repository.save(any(Note.class))).thenReturn(createdNote);
        Note actual = service.createNote(createRequest);
        verify(repository).save(any(Note.class));
        assertEquals(createdNote, actual);
    }

    @Test
    public void testUpdateNoteWithNonExistNote() {
        when(repository.findById(NON_EXIST_NOTE_ID)).thenReturn(emptyOptional);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.updateNote(NON_EXIST_NOTE_ID, updateRequest);
        });
        String expectedMessage = "Note is not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateNote() {
        when(repository.findById(EXIST_NOTE_ID)).thenReturn(nonEmptyOptional);
        when(repository.save(any(Note.class))).thenReturn(updatedNote);
        Note actual = service.updateNote(EXIST_NOTE_ID, updateRequest);
        verify(repository).save(any(Note.class));
        assertEquals(updatedNote, actual);
    }

    @Test
    public void testGetAllNotes() {
        when(repository.findAllByAuthor(AUTHOR)).thenReturn(listOfNotes);
        List<Note> actual = service.getAllNotes(AUTHOR);
        verify(repository).findAllByAuthor(AUTHOR);
        assertEquals(listOfNotes, actual);
    }

    @Test
    public void testGetNoteWithNonExistNote() {
        when(repository.findById(NON_EXIST_NOTE_ID)).thenReturn(emptyOptional);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.getNote(NON_EXIST_NOTE_ID);
        });
        String expectedMessage = "Note is not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetNote() {
        when(repository.findById(EXIST_NOTE_ID)).thenReturn(nonEmptyOptional);
        Note actual = service.getNote(EXIST_NOTE_ID);
        verify(repository).findById(EXIST_NOTE_ID);
        assertEquals(createdNote, actual);
    }
}
