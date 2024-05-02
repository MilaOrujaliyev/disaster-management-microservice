package org.afetankanet.disastermanagementmicroservice.service;

import org.afetankanet.disastermanagementmicroservice.converter.DateConverter;
import org.afetankanet.disastermanagementmicroservice.model.RssFeed;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import org.afetankanet.disastermanagementmicroservice.entity.DisasterNews;
import org.afetankanet.disastermanagementmicroservice.repository.DisasterNewsRepository;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
@Service
public class DisasterNewsService {

    @Value("${schedule.fixedRate}")
    private long fixedRate;

    private final RestTemplate restTemplate;

    private final DisasterNewsRepository disasterNewsRepository;

    public DisasterNewsService(DisasterNewsRepository disasterNewsRepository) {
        this.restTemplate =  new RestTemplate();
        this.disasterNewsRepository = disasterNewsRepository;
    }

    public Page<DisasterNews> getAllDisasterNewsWithPaging(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("pubDate").descending());
        return disasterNewsRepository.findAll(pageable);
    }
    public Page<DisasterNews> findNewsByCriteria(String queryCriteria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("pubDate").descending());
        return disasterNewsRepository.findByQueryCriteria(queryCriteria, pageable);
    }
    @Scheduled(fixedRateString = "${schedule.fixedRate}")
    public void scheduleFetchNewsAndSave() {

        List<String> queries = Arrays.asList(
                "afet", "sel", "yangın", "deprem",
                "heyelan", "hava durumu"
        );

        queries.parallelStream().forEach(query -> {
            try {
                fetchAndSaveDisasterNews(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void fetchAndSaveDisasterNews(String query) {
        try {
            String url = "https://rss.haberler.com/rss.asp?kategori=" + query;
            String response = restTemplate.getForObject(url, String.class);

            JAXBContext jaxbContext = JAXBContext.newInstance(RssFeed.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            RssFeed rssFeed = (RssFeed) unmarshaller.unmarshal(new StringReader(response));

            DisasterNews newsDuplicate = null;
            for (org.afetankanet.disastermanagementmicroservice.model.Item item : rssFeed.getChannel().getItems()) {
                try {
                    if(item.getMediaContent()!=null){
                        DisasterNews news = new DisasterNews();
                        news.setQueryCriteria(query);
                        news.setTitle(item.getTitle());
                        news.setDescription(item.getDescription());
                        news.setPubDate(new DateConverter().convertStringToDate(item.getPubDate()));
                        news.setLink(item.getLink());
                        news.setGuid(item.getGuid());
                        news.setImageUrl(item.getMediaContent().getUrl());
                        newsDuplicate = news;
                        disasterNewsRepository.save(news);
                    }
                } catch (ConstraintViolationException | DataIntegrityViolationException e) {
                    System.out.println("ConstraintViolationException : The DisasterNews is already exist:"+newsDuplicate.getPubDate() + ":"+newsDuplicate.getGuid());
                }

            }

        } catch (Exception e) {
            System.out.println("An error occurred while fetching and saving disaster news: " + e.getMessage());
        }
    }

}
