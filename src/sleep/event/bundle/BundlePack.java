package sleep.event.bundle;

import java.util.Date;
import java.util.List;

public record BundlePack(Date startDate, Date endDate, List<Bundle> bundles) {
}
