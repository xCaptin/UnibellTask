package org.example.unibelltask.mapper;

import org.example.unibelltask.configuration.GlobalMapperConfiguration;
import org.example.unibelltask.dto.ClientDto;
import org.example.unibelltask.entity.Client;
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
public interface ClientMapper extends Mappable<Client, ClientDto> {

    @Override
    Client toEntity(ClientDto dto);

    @Override
    ClientDto toDto(Client entity);
}
