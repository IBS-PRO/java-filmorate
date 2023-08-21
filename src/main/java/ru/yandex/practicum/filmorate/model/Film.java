package ru.yandex.practicum.filmorate.model;

import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Film {

    private int id;
    @NotBlank
    private String name;
    @Size(max = 200)
    @NotBlank
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @PositiveOrZero
    private int duration;

}
