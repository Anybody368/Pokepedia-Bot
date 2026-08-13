package sleep.bouffe;

public enum CookingType {
    CURRYS_RAGOUTS("Currys et ragoûts"),
    SALADES("Salades"),
    BOISSONS_DESSERTS("Desserts et boissons");
    private final String name;

    CookingType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
