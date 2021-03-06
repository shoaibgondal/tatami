package fr.ippon.tatami.service.search.lucene;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.SearcherManager;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;

/**
 * Service to periodically reload the Lucene IndexReader, so that he sees the latest index entries.
 */
public class LuceneIndexReaderReloader {

    private final Log log = LogFactory.getLog(LuceneIndexReaderReloader.class);

    @Inject
    private SearcherManager searcherManager;

    @PostConstruct
    public void init() {
        log.debug("Lucene IndexReader reloader service started");
    }

    @Scheduled(fixedDelay = 10000)
    public void reloadIndexReader() {
        try {
            searcherManager.maybeRefresh();
        } catch (IOException e) {
            log.error("Lucene IndexReader reloader I/O error : " + e.getMessage());
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
        }
    }
}
