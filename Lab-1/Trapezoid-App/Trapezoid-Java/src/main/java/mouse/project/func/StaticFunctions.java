package mouse.project.func;

import java.util.function.Consumer;

public class StaticFunctions {
    public static void repeat(int r, Consumer<Integer> consumer) {
        for (int i = 0; i < r; i++) {
            consumer.accept(i);
        }
    }
}
