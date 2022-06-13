package tests.domain;

public class Person {
    private String name;
    private Boolean goodBoy;
    private Passport passport;

    public static class Passport {
        private Integer number;

        public Integer getNumber() {
            return number;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGoodBoy() {
        return goodBoy;
    }

    public void setGoodBoy(Boolean goodBoy) {
        this.goodBoy = goodBoy;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
}
