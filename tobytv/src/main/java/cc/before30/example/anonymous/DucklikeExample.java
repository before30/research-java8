package cc.before30.example.anonymous;

/**
 * Created by before30 on 21/11/2016.
 */
public class DucklikeExample {
    public static void main(String[] args) {
        AnonymousTypes.with((AnonymousTypes.Anon & Quacks & Waddles) i -> i, duckLike -> {
            duckLike.quack();
            duckLike.waddle();
        });

        doDucklikeThings((AnonymousTypes.Anon & Quacks & Waddles) i -> i);
    }

    interface Quacks {
        default void quack() {
            System.out.println("Quack");
        }
    }

    interface Waddles {
        default void waddle() {
            System.out.println("Waddle");
        }
    }

    public static <Ducklike extends Quacks & Waddles> void doDucklikeThings(Ducklike ducklike) {
        ducklike.quack();
        ducklike.waddle();
    }
}
