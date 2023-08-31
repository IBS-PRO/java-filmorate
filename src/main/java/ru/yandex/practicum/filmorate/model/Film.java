package ru.yandex.practicum.filmorate.model;

import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Film {

    private Long id;
    @NotBlank
    private String name;
    @Size(max = 200)
    @NotBlank
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @PositiveOrZero
    private int duration;
    private Set<Long> likes;

    public void addLike(Long userId) {
        likes.add(userId);
    }

    public void removeLike(Long userId) {
        likes.remove(userId);
    }

    public int getLikes() {
        return likes.size();
    }

}
