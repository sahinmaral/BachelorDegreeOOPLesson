package entities;

public class ComboKeyValue {
    private String key;
    private String value;

    public ComboKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString(){
        return key;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
