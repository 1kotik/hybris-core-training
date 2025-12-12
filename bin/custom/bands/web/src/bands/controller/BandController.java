package bands.controller;

import bands.data.BandData;
import bands.facade.BandFacade;
import de.hybris.platform.catalog.CatalogVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping
public class BandController {
    private static final String CATALOG_ID = "concertToursProductCatalog";
    private static final String CATALOG_VERSION_NAME = "Online";
    private final CatalogVersionService catalogVersionService;
    private final BandFacade bandFacade;

    @Autowired
    public BandController(CatalogVersionService catalogVersionService, BandFacade bandFacade) {
        this.catalogVersionService = catalogVersionService;
        this.bandFacade = bandFacade;
    }

    @GetMapping
    public String showBands(final Model model) {
        final List<BandData> bands = bandFacade.getAll();
        model.addAttribute("bands", bands);
        return "bandList";
    }

    @GetMapping("/{bandId}")
    public String showBandDetails(@PathVariable final String bandId, final Model model) {
        catalogVersionService.setSessionCatalogVersion(CATALOG_ID, CATALOG_VERSION_NAME);
        final String decodedBandId = URLDecoder.decode(bandId, StandardCharsets.UTF_8);
        final BandData band = bandFacade.get(decodedBandId);
        model.addAttribute("band", band);
        return "bandDetails";
    }
}