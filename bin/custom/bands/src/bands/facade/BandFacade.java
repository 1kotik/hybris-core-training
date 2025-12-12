package bands.facade;

import bands.data.BandData;

import java.util.List;

public interface BandFacade {
    BandData get(final String code);
    List<BandData> getAll();
}
