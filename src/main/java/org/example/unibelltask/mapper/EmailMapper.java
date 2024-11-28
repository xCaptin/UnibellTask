package org.example.unibelltask.mapper;

import org.example.unibelltask.configuration.GlobalMapperConfiguration;
import org.example.unibelltask.dto.ClientDto;
import org.example.unibelltask.dto.EmailDto;
import org.example.unibelltask.entity.Client;
import org.example.unibelltask.entity.Email;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования сущность в DTO и наоборот.
 *
 * <p>Маппер использует конфигурацию {@link GlobalMapperConfiguration}
 * для настройки общего поведения преобразования.</p>
 *
 * @author Kiryl Shynkarou.
 */
@Mapper(componentModel = "spring",
        config = GlobalMapperConfiguration.class)
public interface EmailMapper extends Mappable<Email, EmailDto> {

    @Override
    Email toEntity(EmailDto dto);

    @Override
    EmailDto toDto(Email entity);
}
