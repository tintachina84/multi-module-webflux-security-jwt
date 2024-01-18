package com.tintachina.service.mapper;

import com.tintachina.service.dtos.user.UserDto;
import com.tintachina.service.entities.user.User;
import com.tintachina.service.mapper.base.GenericMapper;
import org.mapstruct.Mapper;

/**
 * @author seonghyeon.seo
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserDto, User> {

}
