package net.javaguides.ems.service;

import net.javaguides.ems.models.dto.AnnouncementDto;

import java.util.List;

public interface AnnouncementService {

    AnnouncementDto createAnouncement(AnnouncementDto anouncementDto);

    List<AnnouncementDto> getAllAnnouncements();

    AnnouncementDto getAnouncementById(Long id);

    AnnouncementDto publishAnouncement(Long id);

    void deleteAnouncement(Long id);

}