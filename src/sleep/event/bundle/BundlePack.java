package sleep.event.bundle;

import utilitaire.Util;

import java.util.Date;
import java.util.List;

public record BundlePack(Date startDate, Date endDate, List<Bundle> bundles) {
    public String getWikiCode() {
        StringBuilder sb = new StringBuilder("| rowspan=\"3\" | ").append(Util.dateToString(startDate)).append("<br>—<br>")
                .append(Util.dateToString(endDate));
        for (Bundle bundle : bundles) {
            sb.append("\n").append(bundle.getWikiCode()).append("\n|-");
        }
        return sb.substring(0, sb.length() - 2);
    }
}
