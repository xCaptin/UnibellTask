package org.example.unibelltask.configuration;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 * Глобальная конфигурация для маппинга объектов с использованием MapStruct.
 * Устанавливает политику игнорирования немаппированных целей.
 *
 * @author Kiryl Shynkarou
 */
@MapperConfig(
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GlobalMapperConfiguration {
}
