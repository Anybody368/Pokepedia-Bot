package sleep.view;

import org.jetbrains.annotations.NotNull;

public record ComboOption<T>(T value, String label) {

    @Override
    public @NotNull String toString() {
        return label;
    }
}
