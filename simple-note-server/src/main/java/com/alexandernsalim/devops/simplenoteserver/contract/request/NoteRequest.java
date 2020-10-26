package com.alexandernsalim.devops.simplenoteserver.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {
    private String title;
    private String description;
    private String author;
}
