package concerttours.controller;

import bands.data.TourData;
import concerttours.facade.TourFacade;
import de.hybris.platform.catalog.CatalogVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping
public class TourController {
    private static final String CATALOG_ID = "concertToursProductCatalog";
    private static final String CATALOG_VERSION_NAME = "Online";
    private final CatalogVersionService catalogVersionService;
    private final TourFacade tourFacade;

    @Autowired
    public TourController(CatalogVersionService catalogVersionService, TourFacade tourFacade) {
        this.catalogVersionService = catalogVersionService;
        this.tourFacade = tourFacade;
    }

    @GetMapping("/{tourId}")
    public String showTourDetails(@PathVariable final String tourId, final Model model) {
        catalogVersionService.setSessionCatalogVersion(CATALOG_ID, CATALOG_VERSION_NAME);
        final String decodedTourId = URLDecoder.decode(tourId, StandardCharsets.UTF_8);
        final TourData tour = tourFacade.get(decodedTourId);
        model.addAttribute("tour", tour);
        return "tourDetails";
    }
}