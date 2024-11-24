package net.javaguides.ems.models.mappers;

import net.javaguides.ems.models.Announcement;
import net.javaguides.ems.models.dto.AnnouncementDto;

public class AnnouncementMapper {

    public static AnnouncementDto mapToAnnouncementDto(Announcement announcement) {
        return new AnnouncementDto(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getMessage(),
                announcement.getSendDate(),
                announcement.getPriority(),
                announcement.isPublished()
        );
    }

    public static Announcement mapToAnnouncement(AnnouncementDto announcementDto) {
        return new Announcement(
                announcementDto.getId(),
                announcementDto.getTitle(),
                announcementDto.getMessage(),
                announcementDto.getSendDate(),
                announcementDto.getPriority(),
                announcementDto.isPublished()
        );
    }
}
