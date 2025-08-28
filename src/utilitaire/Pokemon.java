package utilitaire;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Pokemon {
    private final int number;
    private final String frenchName;
    private final String englishName;
    private final String japaneseName;
    private final PokeTypes type1;
    private final PokeTypes type2;
    private final ArrayList<String> forms;
    private final ArrayList<Region> regionalForms;
    private final boolean hasMega;
    private final boolean hasGigantamax;

    @JsonCreator
    public Pokemon(@JsonProperty("number") int number,
                   @JsonProperty("frenchName") String frenchName,
                   @JsonProperty("englishName") String englishName,
                   @JsonProperty("japaneseName") String japaneseName,
                   @JsonProperty("type1") PokeTypes type1,
                   @JsonProperty("type2") @Nullable PokeTypes type2,
                   @JsonProperty("forms") ArrayList<String> forms,
                   @JsonProperty("regionalForms") ArrayList<Region> regionalForms,
                   @JsonProperty("HasMega") boolean hasMega,
                   @JsonProperty("HasGigantamax") boolean hasGigantamax) {
        this.number = number;
        this.frenchName = frenchName;
        this.englishName = englishName;
        this.japaneseName = japaneseName;
        this.type1 = type1;
        this.type2 = type2;
        this.forms = forms;
        this.regionalForms = regionalForms;
        this.hasMega = hasMega;
        this.hasGigantamax = hasGigantamax;
    }

    public int getNumber() {
        return number;
    }

    @JsonIgnore
    public String getPokepediaNumber() {
        if(number < 10) return "000" + number;
        if(number < 100) return "00" + number;
        if(number < 1000) return "0" + number;
        return String.valueOf(number);
    }

    public String getFrenchName() {
        return frenchName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getJapaneseName() {
        return japaneseName;
    }

    public PokeTypes getType1() {
        return type1;
    }

    public PokeTypes getType2() {
        return type2;
    }

    public ArrayList<String> getForms() {
        return forms;
    }

    public ArrayList<Region> getRegionalForms() {
        return regionalForms;
    }

    @JsonProperty("HasMega")
    public boolean isHasMega() {
        return hasMega;
    }

    @JsonProperty("HasGigantamax")
    public boolean HasGigantamax() {
        return hasGigantamax;
    }
}
