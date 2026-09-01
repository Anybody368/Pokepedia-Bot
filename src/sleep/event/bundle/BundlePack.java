package sleep.event.bundle;

import utilitaire.FileToUpload;
import utilitaire.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public record BundlePack(Date startDate, Date endDate, List<Bundle> bundles) {
    public String getWikiCode() {
        StringBuilder sb = new StringBuilder("| ");

        if (bundles.size() > 1) {
            sb.append("rowspan=\"%d\" | ".formatted(bundles.size()));
        }

        sb.append(Util.dateToString(startDate)).append("<br>—<br>").append(Util.dateToString(endDate));

        for (Bundle bundle : bundles) {
            sb.append("\n").append(bundle.getWikiCode()).append("\n|-");
        }
        return sb.substring(0, sb.length() - 2);
    }

    public List<FileToUpload> getIconsToPublish() {
        List<FileToUpload> iconsToPublish = new ArrayList<>();
        for (Bundle bundle : bundles) {
            iconsToPublish.add(bundle.getIconPage());
        }
        iconsToPublish.removeIf(Objects::isNull);
        return iconsToPublish;
    }
}
