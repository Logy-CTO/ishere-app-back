package com.example.demo.Notice;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoticeMapper {
    public AdminNotice ToNotice(NoticeDTO noticeDTO);
}
