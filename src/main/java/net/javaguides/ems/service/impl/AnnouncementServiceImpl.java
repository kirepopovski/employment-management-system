package net.javaguides.ems.service.impl;


import lombok.AllArgsConstructor;
import net.javaguides.ems.models.Announcement;
import net.javaguides.ems.models.dto.AnnouncementDto;
import net.javaguides.ems.models.exceptions.AnnouncementNotFoundException;
import net.javaguides.ems.models.mappers.AnnouncementMapper;
import net.javaguides.ems.repository.AnnouncementRepository;
import net.javaguides.ems.service.AnnouncementService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;


    @Override
    public AnnouncementDto createAnouncement(AnnouncementDto anouncementDto) {
         Announcement announcement = AnnouncementMapper.mapToAnnouncement(anouncementDto);
         announcement.setPublished(false); // Default to draft state
        announcement.setSendDate(LocalDateTime.now()); // current timestand
        Announcement savedAnnouncement = announcementRepository.save(announcement);
        return AnnouncementMapper.mapToAnnouncementDto(savedAnnouncement);
    }

    @Override
    public List<AnnouncementDto> getAllAnnouncements() {
        return announcementRepository.findAll().stream()
                .map(AnnouncementMapper::mapToAnnouncementDto)
                .collect(Collectors.toList());
    }

    @Override
    public AnnouncementDto getAnouncementById(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement is not exists with given id: " + announcementId));
        return AnnouncementMapper.mapToAnnouncementDto(announcement);
    }

    @Override
    public AnnouncementDto publishAnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement is not exists with given id: " + announcementId));
        announcement.setPublished(true);
        announcement.setSendDate(LocalDateTime.now());
        Announcement publishedAnnouncement = announcementRepository.save(announcement);
        return AnnouncementMapper.mapToAnnouncementDto(publishedAnnouncement);
    }

    @Override
    public void deleteAnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement is not exists with given id: " + announcementId));
        announcementRepository.delete(announcement);
    }
}
