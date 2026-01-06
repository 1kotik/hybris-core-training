package bands.controller;

import bands.data.BandProducerExtendedData;
import bands.facade.BandProducerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/producers")
public class BandProducerController {
    private final BandProducerFacade bandProducerFacade;

    @Autowired
    public BandProducerController(BandProducerFacade bandProducerFacade) {
        this.bandProducerFacade = bandProducerFacade;
    }

    @GetMapping("/{producerCode}")
    public String showProducerDetails(@PathVariable final String producerCode, final Model model) {
        final BandProducerExtendedData bandProducerData = bandProducerFacade.get(producerCode);
        model.addAttribute("producer", bandProducerData);
        return "bandProducerDetails";
    }
}
