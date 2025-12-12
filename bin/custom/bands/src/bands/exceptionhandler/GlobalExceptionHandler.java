package bands.exceptionhandler;

import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = AmbiguousIdentifierException.class)
    public String handleAmbiguousIdentifierException(final AmbiguousIdentifierException ex, final Model model) {
        logException(ex);
        model.addAttribute("message", "Cannot respond. Your request has multiple results");
        return "400";
    }

    @ExceptionHandler(value = UnknownIdentifierException.class)
    public String handleUnknownIdentifierException(final UnknownIdentifierException ex) {
        logException(ex);
        return "404";
    }

    private void logException(final Exception ex) {
        if(ex != null) {
            LOGGER.warn("{}: {}", ex.getClass().getName(), ex.getMessage());
        }
    }
}
