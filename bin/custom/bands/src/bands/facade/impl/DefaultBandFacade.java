package bands.facade.impl;

import bands.data.BandData;
import bands.data.BandManagerData;
import bands.data.MusicianData;
import bands.data.TourSummaryData;
import bands.enums.MusicInstrument;
import bands.enums.MusicType;
import bands.facade.BandFacade;
import bands.model.BandManagerModel;
import bands.model.BandModel;
import bands.model.MusicianModel;
import bands.service.GenericItemModelService;
import concerttours.model.ConcertModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component("defaultBandFacade")
public class DefaultBandFacade implements BandFacade {
    private final GenericItemModelService<BandModel> bandService;

    @Autowired
    public DefaultBandFacade(GenericItemModelService<BandModel> bandService) {
        this.bandService = bandService;
    }

    @Override
    public BandData get(final String code) {
        if (code == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        final BandModel band = bandService.findByCode(code);
        final List<String> musicGenres = getMusicGenres(band);
        final List<TourSummaryData> toursHistory = getTourHistory(band);
        final Set<MusicianData> members = getMembers(band);
        final BandManagerData manager = getBandManagerData(band);

        final BandData bandData = setBriefBandData(band);
        bandData.setGenres(musicGenres);
        bandData.setTours(toursHistory);
        bandData.setMembers(members);
        bandData.setManager(manager);
        return bandData;
    }

    @Override
    public List<BandData> getAll() {
        final List<BandModel> bands = bandService.findAll();
        return bands.stream().map(this::setBriefBandData).toList();
    }

    private List<String> getMusicGenres(final BandModel band) {
        final Collection<MusicType> musicTypes = band.getTypes();
        return musicTypes == null ? new ArrayList<>()
                : musicTypes.stream().map(MusicType::getCode).toList();
    }

    private List<TourSummaryData> getTourHistory(final BandModel band) {
        final Set<ProductModel> tours = band.getTours();
        return tours == null ? new ArrayList<>()
                : tours.stream().map(this::getTourSummary).toList();
    }

    private TourSummaryData getTourSummary(final ProductModel tour) {
        final TourSummaryData summary = new TourSummaryData();
        summary.setId(tour.getCode());
        summary.setTourName(tour.getName(Locale.ENGLISH));
        final List<ConcertModel> concerts = tour.getVariants().stream()
                .filter(ConcertModel.class::isInstance)
                .map(ConcertModel.class::cast)
                .toList();
        summary.setNumberOfConcerts(String.valueOf(concerts.size()));
        return summary;
    }

    private Set<MusicianData> getMembers(final BandModel band) {
        final Set<MusicianModel> members = band.getMembers();
        return members == null ? new HashSet<>()
                : members.stream().map(this::setMusicianData).collect(Collectors.toSet());
    }

    private MusicianData setMusicianData(final MusicianModel musician) {
        final MusicianData musicianData = new MusicianData();
        final Map<MusicInstrument, Long> instrumentsExperience = musician.getInstrumentsExperience();
        Map<String, Long> formattedInstrumentsExperience = new HashMap<>();
        if (instrumentsExperience != null) {
            formattedInstrumentsExperience = instrumentsExperience.entrySet().stream()
                    .map(entry -> {
                        String stringMusicInstrument = entry.getKey().getCode();
                        Long yearsOfExperience = entry.getValue();
                        return Map.entry(stringMusicInstrument, yearsOfExperience);
                    })
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        musicianData.setId(musician.getCode());
        musicianData.setName(musician.getName());
        musicianData.setInstrumentsExperience(formattedInstrumentsExperience);
        musicianData.setSkillLevel(musician.getSkillLevel().getCode());
        return musicianData;
    }

    private BandManagerData getBandManagerData(final BandModel bandModel) {
        final BandManagerModel bandManager = bandModel.getManager();
        if (bandManager == null) {
            return new BandManagerData();
        }
        final BandManagerData bandManagerData = new BandManagerData();
        bandManagerData.setId(bandManager.getCode());
        bandManagerData.setName(bandManager.getName());
        bandManagerData.setEmail(bandManager.getEmail());
        bandManagerData.setPhone(bandManager.getPhone());
        bandManagerData.setYearsOfExperience(bandManagerData.getYearsOfExperience());
        return bandManagerData;
    }

    private BandData setBriefBandData(final BandModel band) {
        final BandData bandData = new BandData();
        bandData.setId(band.getCode());
        bandData.setName(band.getName());
        bandData.setAlbumsSold(band.getAlbumSales());
        bandData.setDescription(band.getHistory());
        return bandData;
    }
}
