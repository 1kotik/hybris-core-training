package bands.facade.impl;

import bands.data.BandData;
import bands.data.BandManagerData;
import bands.data.BandProducerData;
import bands.data.MusicianData;
import bands.data.TourSummaryData;
import bands.facade.BandFacade;
import bands.model.BandManagerModel;
import bands.model.BandModel;
import bands.model.BandProducerModel;
import bands.model.MusicianModel;
import bands.service.GenericItemModelService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("defaultBandFacade")
public class DefaultBandFacade implements BandFacade {
    private final GenericItemModelService<BandModel> bandService;
    private final Converter<BandModel, BandData> basicBandConverter;
    private final Converter<BandManagerModel, BandManagerData> bandManagerConverter;
    private final Converter<MusicianModel, MusicianData> musicianConverter;
    private final Converter<ProductModel, TourSummaryData> tourSummaryConverter;
    private final Converter<BandProducerModel, BandProducerData> bandProducerConverter;

    @Autowired
    public DefaultBandFacade(GenericItemModelService<BandModel> bandService,
                             Converter<BandModel, BandData> basicBandConverter,
                             Converter<BandManagerModel, BandManagerData> bandManagerConverter,
                             Converter<MusicianModel, MusicianData> musicianConverter,
                             Converter<ProductModel, TourSummaryData> tourSummaryConverter,
                             Converter<BandProducerModel, BandProducerData> bandProducerConverter) {
        this.bandService = bandService;
        this.basicBandConverter = basicBandConverter;
        this.bandManagerConverter = bandManagerConverter;
        this.musicianConverter = musicianConverter;
        this.tourSummaryConverter = tourSummaryConverter;
        this.bandProducerConverter = bandProducerConverter;
    }

    @Override
    public BandData get(final String code) {
        if (code == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        final BandModel band = bandService.findByCode(code);
        final BandData bandData = basicBandConverter.convert(band);
        if(bandData == null) {
            return null;
        }
        final BandManagerData manager = bandManagerConverter.convert(band.getManager());
        final List<MusicianData> members = musicianConverter.convertAll(band.getMembers());
        final List<TourSummaryData> tours = tourSummaryConverter.convertAll(band.getTours());
        final BandProducerData bandProducer = bandProducerConverter.convert(band.getProducer());
        bandData.setManager(manager);
        bandData.setMembers(members);
        bandData.setTours(tours);
        bandData.setProducer(bandProducer);
        return bandData;
    }

    @Override
    public List<BandData> getAll() {
        final List<BandModel> bands = bandService.findAll();
        return bands.stream().map(basicBandConverter::convert).toList();
    }


}
