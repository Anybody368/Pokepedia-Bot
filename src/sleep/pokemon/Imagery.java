package sleep.pokemon;

public enum Imagery {
    AGENDER("Sprite / Sprite chromatique", "Miniature / Miniature chromatique"),
    SEXUAL_DIMORPHISM("Sprite ♂ / Sprite ♂ chromatique / Sprite ♀ / Sprite ♀ chromatique", "Miniature ♂ / Miniature ♂ chromatique / Miniature ♀ / Miniature ♀ chromatique"),
    PARTIAL_DIMORPHISM("Sprite ♂ / Sprite ♂ chromatique / Sprite ♀ / Sprite ♀ chromatique", "Miniature / Miniature chromatique"); //For Pokémon having different Sprites, but sharing the same Miniature

    private final String _sprites;

    private final String _miniatures;
    private Imagery(String sprites, String miniatures) {
        _sprites = sprites;
        _miniatures = miniatures;
    }

    public String getSprites() {
        return _sprites;
    }

    public String getMiniatures() {
        return _miniatures;
    }
}