import java.util.ArrayList;
import java.util.List;

public class RestAPI {

    List<Data> dataList;
    public RestAPI(List<Data> dataList) {
        this.dataList = new ArrayList<>(dataList);
    }

    public long countEntries() {
        return this.dataList.size();
    }
}
