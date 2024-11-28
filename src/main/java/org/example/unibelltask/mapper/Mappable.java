package org.example.unibelltask.mapper;

/**
 * Интерфейс для преобразования между сущностью и DTO.
 *
 * Данный интерфейс определяет методы для преобразования сущностей
 * (entity) в объекты передачи данных (DTO) и обратно. Это позволяет
 * обеспечить единообразное преобразование данных между слоями
 * приложения.
 *
 * @author Kirill Shinkarev
 * @param <E> тип сущности
 * @param <D> тип DTO
 */
public interface Mappable<E, D> {

    /**
     * Преобразует DTO в сущность.
     *
     * @param dto объект передачи данных
     * @return сущность
     */
    E toEntity(D dto);

    /**
     * Преобразует сущность в DTO.
     *
     * @param entity сущность
     * @return объект передачи данных
     */
    D toDto(E entity);
}
