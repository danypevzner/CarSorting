import ui.CliClient;
import util.AppContext;

public class App {
    public static void main(String[] args) {
        var context = new AppContext();
        var client = new CliClient(context);

        client.start();
    }
}
