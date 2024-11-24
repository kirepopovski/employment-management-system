package net.javaguides.ems.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.ems.models.enumerations.Priority;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {
    private Long Id;
    private String title;
    private String message;
    private LocalDateTime sendDate;
    private Priority priority;
    private boolean isPublished;
}
