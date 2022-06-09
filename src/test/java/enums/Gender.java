package enums;

public enum Gender {
    Male("Male"),
    Female("Female"),
    Other("Other");

    public final String desc;

    Gender(String desc) {
        this.desc = desc;
    }
}
