package net.javaguides.ems.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.javaguides.ems.models.Announcement;
import net.javaguides.ems.models.dto.AnnouncementDto;
import net.javaguides.ems.models.exceptions.AnnouncementNotFoundException;
import net.javaguides.ems.repository.AnnouncementRepository;
import net.javaguides.ems.service.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final AnnouncementRepository announcementRepository;

    // Build Add Announcement REST API
    @PostMapping
    public ResponseEntity<AnnouncementDto> createAnnouncement(@RequestBody AnnouncementDto announcementDto) {
        AnnouncementDto savedAnnouncement = announcementService.createAnouncement(announcementDto);
        return new ResponseEntity<>(savedAnnouncement, HttpStatus.CREATED);
    }

    // Build Get Announcement REST API
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDto> getAnnouncementById(@PathVariable Long id) {
        AnnouncementDto announcementDto = announcementService.getAnouncementById(id);
        return ResponseEntity.ok(announcementDto);
    }

    // Build Get All Announcement REST API
    @GetMapping
    public ResponseEntity<List<AnnouncementDto>> getAllAnnouncements() {
        List<AnnouncementDto> announcements = announcementService.getAllAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    // Build Delete Announcement REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnouncement(id);
        return ResponseEntity.noContent().build();
    }

    // Build Publish Announcement REST API
    @PatchMapping("/{id}/publish")
    public ResponseEntity<AnnouncementDto> publishAnnouncement(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.publishAnouncement(id));
    }
}
