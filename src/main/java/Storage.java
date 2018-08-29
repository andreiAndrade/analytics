import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Storage {
    private static Storage storage;

    private HashMap<String, List<String>> datas;

    private Storage() {
        this.datas = new HashMap<>();
    }

    public static Storage getInstance() {
        if (Objects.isNull(storage)) {
            storage = new Storage();
        }

        return storage;
    }

    public HashMap<String, List<String>> getDatas() {
        return datas;
    }
}
