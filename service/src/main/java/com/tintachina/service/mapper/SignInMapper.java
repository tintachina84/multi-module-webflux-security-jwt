package com.tintachina.service.mapper;

import com.tintachina.service.entities.auth.SignInRequest;
import com.tintachina.service.entities.user.User;
import com.tintachina.service.mapper.base.GenericMapper;
import org.mapstruct.Mapper;

/**
 * @author seonghyeon.seo
 */
@Mapper(componentModel = "spring")
public interface SignInMapper extends GenericMapper<SignInRequest, User> {
}
