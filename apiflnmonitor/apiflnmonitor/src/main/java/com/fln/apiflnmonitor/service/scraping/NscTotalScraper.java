package com.fln.apiflnmonitor.service.scraping;


import com.fln.apiflnmonitor.model.Noticia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class NscTotalScraper {

    private static final String URL = "https://www.nsctotal.com.br/ultimas-noticias";
    private static final int TIMEOUT_MS = 10_000;

    private static final Logger log = LoggerFactory.getLogger(NscTotalScraper.class);

    public List<Noticia> buscarNoticiasNsctotal() {

        List<Noticia> noticias = new ArrayList<>();

        log.info("Iniciando scraping do NSC Total");

        try {
            Document doc = Jsoup.connect(URL)
                    .userAgent("news monitoring; fernandohvk4@gmail.com")
                    .timeout(TIMEOUT_MS)
                    .referrer("https://www.google.com")
                    .get();

            Elements elementos = doc.select("h3.title");

            if (elementos.isEmpty()) {
                log.warn("Nenhuma notícia encontrada no NSC Total — layout pode ter mudado");
            }


            log.info("Encontradas {} notícias no NSC Total", elementos.size());

            for (Element el : elementos) {
                try {
                    Element linkElement = el.selectFirst("a");

                    if (linkElement == null) {
                        log.warn("Elemento <a> não encontrado em uma notícia do NSC Total");
                        continue;
                    }

                    String manchete = linkElement.text();
                    String link = linkElement.attr("href");

                    if (manchete.isBlank() || link.isBlank()) {
                        log.warn("Notícia ignorada por título ou link vazio");
                        continue;
                    }

                    Noticia n = new Noticia();
                    n.setTitulo(manchete);
                    n.setLink(link);
                    n.setFonte("NSC Total");
                    n.setOrgao("portal");

                    noticias.add(n);

                    log.debug("Notícia adicionada: {}", manchete);

                } catch (Exception e) {
                    log.error("Erro ao processar uma notícia do NSC Total", e);
                }
            }

        } catch (IOException e) {
            log.error("Erro ao conectar no site NSC Total - URL: {}", URL, e);
        } catch (Exception e) {
            log.error("Erro inesperado no scraper do NSC Total", e);
        }

        log.info("Scraping do NSC Total finalizado. Total de notícias válidas: {}", noticias.size());

        return noticias;
    }
}

