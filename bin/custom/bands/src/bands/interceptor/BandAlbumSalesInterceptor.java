package bands.interceptor;

import bands.model.BandModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BandAlbumSalesInterceptor implements ValidateInterceptor<BandModel> {
    private static final Long NEGATIVE_SALES = 0L;
    private static final Logger LOGGER = LoggerFactory.getLogger(BandAlbumSalesInterceptor.class);

    @Override
    public void onValidate(BandModel band, InterceptorContext interceptorContext) throws InterceptorException {
        if (band == null) {
            final String message = "Cannot save: band is null";
            LOGGER.warn(message);
            throw new InterceptorException(message);
        }
        final Long sales = band.getAlbumSales();
        if (sales == null || sales < NEGATIVE_SALES) {
            final String message = "Cannot save: sales are negative";
            LOGGER.warn(message);
            throw new InterceptorException(message);
        }
    }
}
