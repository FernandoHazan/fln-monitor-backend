package com.fln.apiflnmonitor.service;
import com.fln.apiflnmonitor.model.Noticia;
import com.fln.apiflnmonitor.repository.NoticiasRepository;
import com.fln.apiflnmonitor.service.scraping.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ScrapingService {

    private static final Logger log = LoggerFactory.getLogger(ScrapingService.class);

    private final Scc10Scraper scc10Scraper;
    private final NoticiasRepository repository;
    private final NscTotalScraper nscTotalScraper;
    private final NdScraper ndScraper;
    private final JornalRazaoScraper jornalRazaoScraper;
    private final SecomScraper secomScraper;
    private final AgoraFloripaScraper agoraFloripaScraper;
    private final InformeFloripaScraper informeFloripaScraper;

    public ScrapingService(
            Scc10Scraper scc10Scraper,
            NoticiasRepository repository,
            NscTotalScraper nscTotalScraper,
            NdScraper ndScraper,
            JornalRazaoScraper jornalRazaoScraper,
            SecomScraper secomScraper,
            AgoraFloripaScraper agoraFloripaScraper,
            InformeFloripaScraper informeFloripaScraper) {

        this.scc10Scraper = scc10Scraper;
        this.repository = repository;
        this.nscTotalScraper = nscTotalScraper;
        this.ndScraper = ndScraper;
        this.jornalRazaoScraper = jornalRazaoScraper;
        this.secomScraper = secomScraper;
        this.agoraFloripaScraper = agoraFloripaScraper;
        this.informeFloripaScraper = informeFloripaScraper;
    }

    public void importarScc10() {
        try {
            List<Noticia> noticias = scc10Scraper.buscarNoticiasScc10();
            if (noticias == null || noticias.isEmpty()) {
                log.warn("Nenhuma notícia retornada pelo SCC10");
                return;
            }
            salvarNoticias(noticias, "SCC10");
        } catch (Exception e) {
            log.error("Erro crítico ao executar scraping do SCC10", e);
        }
    }

    public void importarJornalRazao() {
        try {
            List<Noticia> noticias = jornalRazaoScraper.buscarNoticiasJornalRazao();
            if (noticias == null || noticias.isEmpty()) {
                log.warn("Nenhuma notícia retornada pelo Jornal Razão");
                return;
            }
            salvarNoticias(noticias, "Jornal Razao");
        } catch (Exception e) {
            log.error("Erro crítico ao executar scraping do Jornal Razao", e);
        }
    }

    public void importarNsctotal() {
        try {
            List<Noticia> noticias = nscTotalScraper.buscarNoticiasNsctotal();
            if (noticias == null || noticias.isEmpty()) {
                log.warn("Nenhuma notícia retornada pelo NSC Total");
                return;
            }
            salvarNoticias(noticias, "NSC Total");
        } catch (Exception e) {
            log.error("Erro crítico ao executar scraping do NSC Total", e);
        }
    }

    public void importarNd() {
        try {
            List<Noticia> noticias = ndScraper.buscarNoticiasNd();
            if (noticias == null || noticias.isEmpty()) {
                log.warn("Nenhuma notícia retornada pela ND");
                return;
            }
            salvarNoticias(noticias, "ND");
        } catch (Exception e) {
            log.error("Erro crítico ao executar scraping da ND", e);
        }
    }

    public void importarSecom() {
        try {
            List<Noticia> noticias = secomScraper.buscarNoticiasSecom();
            if (noticias == null || noticias.isEmpty()) {
                log.warn("Nenhuma notícia retornada pelo Secom");
                return;
            }
            salvarNoticias(noticias, "Secom");
        } catch (Exception e) {
            log.error("Erro crítico ao executar scraping do Secom", e);
        }
        log.info("Finalizada importação Secom");
    }

    public void importarAgoraFloripa() {
        try {
            List<Noticia> noticias = agoraFloripaScraper.buscarNoticiasAgoraFloripa();
            if (noticias == null || noticias.isEmpty()) {
                log.warn("Nenhuma notícia retornada pelo AgoraFloripa");
                return;
            }
            salvarNoticias(noticias, "AgoraFloripa");
        } catch (Exception e) {
            log.error("Erro crítico ao executar scraping do AgoraFloripa", e);
        }
        log.info("Finalizada importação AgoraFloripa");
    }

    public void importarInformeFloripa() {
        try {
            List<Noticia> noticias = informeFloripaScraper.buscarNoticiasInformeFloripa();
            if (noticias == null || noticias.isEmpty()) {
                log.warn("Nenhuma notícia retornada pelo InformeFloripa");
                return;
            }
            salvarNoticias(noticias, "InformeFloripa");
        } catch (Exception e) {
            log.error("Erro crítico ao executar scraping do InformeFloripa", e);
        }
        log.info("Finalizada importação InformeFloripa");
    }

    private void salvarNoticias(List<Noticia> noticias, String fonte) {
        for (Noticia noticia : noticias) {
            try {
                if (repository.existsBylink(noticia.getLink())) {
                    continue;
                }
                repository.save(noticia);
            } catch (Exception e) {
                log.error(
                        "Erro ao salvar notícia [{}] da fonte {}",
                        noticia.getLink(),
                        fonte,
                        e
                );
            }
        }
    }
}

