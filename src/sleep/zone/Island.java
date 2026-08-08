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
    ILE_VERTEPOUSSE("Île Vertepousse", false),
    PLAGE_CYAN("Plage Cyan", false),
    GROTTE_SEPIA("Grotte Sépia", false),
    PLAINE_PERCENEIGE("Plaine Perce-neige", false),
    RIVAGE_LAPISLAZULI("Rivage Lapis-lazuli", false),
    VIEILLE_CENTRALE_DOREE("Vieille Centrale Dorée", false),
    CANYON_AMBRE("Canyon Ambre", false),

    EX_ILE_VERTEPOUSSE("Île Vertepousse", true);
    private final String m_name;
    private final boolean m_isExpert;

    Island(String name, boolean isExpert)
    {
        m_name = name;
        m_isExpert = isExpert;
    }

    public boolean isExpert()
    {
        return m_isExpert;
    }

    public String getName(boolean getShort) {
        if(!m_isExpert) {
            return m_name;
        } else {
            if(!getShort) {
                return m_name + " (mode expert)";
            } else {
                return m_name + " (expert)";
            }
        }
    }


}
