package bands.interceptor;

import bands.model.NewsModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NewsPrepareInterceptor implements PrepareInterceptor<NewsModel> {
    private final KeyGenerator newsCodeGenerator;

    @Autowired
    public NewsPrepareInterceptor(@Qualifier("newsCodeGenerator") KeyGenerator newsCodeGenerator) {
        this.newsCodeGenerator = newsCodeGenerator;
    }

    @Override
    public void onPrepare(NewsModel newsModel, InterceptorContext interceptorContext) {
        boolean codeHaveToBeGenerated = newsModel != null
                && (newsModel.getCode() == null || newsModel.getCode().isEmpty());
        if (codeHaveToBeGenerated) {
            final String code = String.valueOf(newsCodeGenerator.generate());
            newsModel.setCode(code);
        }
    }
}
