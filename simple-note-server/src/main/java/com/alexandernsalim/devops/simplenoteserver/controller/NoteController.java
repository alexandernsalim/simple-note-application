package com.alexandernsalim.devops.simplenoteserver.controller;

import com.alexandernsalim.devops.simplenoteserver.contract.request.NoteRequest;
import com.alexandernsalim.devops.simplenoteserver.contract.response.Response;
import com.alexandernsalim.devops.simplenoteserver.model.Note;
import com.alexandernsalim.devops.simplenoteserver.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notes")
public class NoteController {
    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    @GetMapping
    public Response<List<Note>> getAllNotes(@RequestParam("author") String author) {
        return toResponse(service.getAllNotes(author));
    }

    @GetMapping("/{id}")
    public Response<Note> getNote(@PathVariable Integer id) {
        try {
            return toResponse(service.getNote(id));
        } catch (Exception e) {
            return toResponse(e.getMessage(), null);
        }
    }

    @PostMapping
    public Response<Note> createNote(@RequestBody NoteRequest request) {
        return toResponse(service.createNote(request));
    }

    @PutMapping("/{id}")
    public Response<Note> updateNote(@PathVariable Integer id, @RequestBody NoteRequest request) {
        try {
            return toResponse(service.updateNote(id, request));
        } catch (Exception e) {
            return toResponse(e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public Response<String> deleteNote(@PathVariable Integer id) {
        try {
            service.deleteNote(id);
            return toResponse("Note deleted", null);
        } catch (Exception e) {
            return toResponse(e.getMessage(), null);
        }
    }

    private <T> Response toResponse(T data) {
        return Response.builder()
                .code(200)
                .message("Success")
                .data(data)
                .build();
    }

    private <T> Response toResponse(String message, T data) {
        return Response.builder()
                .code(200)
                .message(message)
                .data(data)
                .build();
    }
}
