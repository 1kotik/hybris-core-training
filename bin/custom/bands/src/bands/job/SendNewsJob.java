package bands.job;

import bands.model.NewsModel;
import bands.service.NewsService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.mail.MailUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Component
public class SendNewsJob extends AbstractJobPerformable<CronJobModel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendNewsJob.class);
    private final NewsService newsService;
    private final ConfigurationService configurationService;

    @Autowired
    public SendNewsJob(NewsService newsService, ConfigurationService configurationService) {
        this.newsService = newsService;
        this.configurationService = configurationService;
    }

    @Override
    public PerformResult perform(CronJobModel cronJobModel) {
        LOGGER.info("Sending daily news");
        final List<NewsModel> news;
        try {
            news = newsService.findByTheDay(new Date());
        } catch (final NoSuchElementException e) {
            LOGGER.warn(e.getMessage());
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }
        final String mailContent = buildMailContent(news);
        try {
            sendEmail(mailContent);
        } catch (final EmailException e) {
            LOGGER.warn("Error sending an email: {}", e.getMessage());
            return new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
        }
        LOGGER.info("Successfully sent daily news");
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private String buildMailContent(final List<NewsModel> news) {
        StringBuilder mailContent = new StringBuilder();
        IntStream.range(1, news.size())
                .forEach(index -> mailContent.append(buildSingleNewsContent(news.get(index - 1), index)));
        return mailContent.toString();
    }

    private String buildSingleNewsContent(final NewsModel news, int index) {
        StringBuilder newsContent = new StringBuilder();
        newsContent.append(index);
        newsContent.append(". ");
        newsContent.append(news.getHeadline());
        newsContent.append("\n");
        newsContent.append(news.getContent());
        newsContent.append("\n\n");
        return newsContent.toString();
    }

    private void sendEmail(final String mailContent) throws EmailException {
        final String subject = "Daily News";
        final Email email = MailUtils.getPreConfiguredEmail();
        final Configuration configuration = configurationService.getConfiguration();
        final String recipient = configuration.getString("news_summary_mailing_address", null);
        email.addTo(recipient);
        email.setSubject(subject);
        email.setMsg(mailContent);
        email.setTLS(true);
        email.send();
    }
}
