/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package sleep.zone;

public enum Island {
    ILE_VERTEPOUSSE("Île Vertepousse", false, "L'"),
    PLAGE_CYAN("Plage Cyan", false, "La "),
    GROTTE_SEPIA("Grotte Sépia", false, "La "),
    PLAINE_PERCENEIGE("Plaine Perce-neige", false, "La "),
    RIVAGE_LAPISLAZULI("Rivage Lapis-lazuli", false, "La "),
    VIEILLE_CENTRALE_DOREE("Vieille Centrale Dorée", false, "La "),
    CANYON_AMBRE("Canyon Ambre", false, "La "),

    EX_ILE_VERTEPOUSSE("Île Vertepousse", true, "L'"),
    EX_PLAGE_CYAN("Plage Cyan", true, "La ");
    private final String name;
    private final boolean isExpert;
    private final String article;

    Island(String name, boolean isExpert, String article)
    {
        this.name = name;
        this.isExpert = isExpert;
        this.article = article;
    }

    public boolean isExpert()
    {
        return isExpert;
    }

    public String getFullName(boolean getShort) {
        if(!isExpert) {
            return name;
        } else {
            if(!getShort) {
                return name + " (mode expert)";
            } else {
                return name + " (expert)";
            }
        }
    }

    public String getArticle() {
        return article;
    }

    public String getName() {
        return name;
    }
}
