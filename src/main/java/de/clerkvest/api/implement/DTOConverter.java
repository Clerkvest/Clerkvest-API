package de.clerkvest.api.implement;

public interface DTOConverter<T, V> {
    V convertToDto(T post);

    T convertToEntity(V postDto);
}
