import org.junit.Test;

public class MyTest extends BaseRunner {

    @Test
    public void test() {
        driver.get("https://www.tinkoff.ru/mobile-operator/tariffs/");
        TextInput.driver = driver;
        TextInput imp = new TextInput("Кон");
        TextInput inp = new TextInput("Фами");
        System.out.println(imp.getNameField());
        System.out.println(inp.getNameField());
        imp.fill("kzkzkz");
        inp.fill("kzkzkz");
        System.out.println(inp.isEmpty());
        System.out.println(imp.isEmpty());
        System.out.println(inp.getValue());
        imp.fill("33");
        System.out.println(imp.getValue());

    }
}
