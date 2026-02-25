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
public class SecomScraper {
    private static final String URL = "https://estado.sc.gov.br/noticias/todas-as-noticias/";
    private static final int TIMEOUT_MS = 10_000;
    private static final Logger log = LoggerFactory.getLogger(SecomScraper.class);
    public List<Noticia> buscarNoticiasSecom() {
        List<Noticia> noticias = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL)
                    .userAgent("news monitoring; fernandohvk4@gmail.com")
                    .timeout(TIMEOUT_MS)
                    .referrer("https://www.google.com")
                    .get();
            Elements elementos = doc.select("div.upk-item");
            if (elementos.isEmpty()) {
                log.warn("Nenhuma notícia encontrada no SCC10 — layout pode ter mudado");
            }
            for (Element el : elementos) {
                try {
                    String manchete = el.selectFirst("a.title-animation-") != null
                            ? el.selectFirst("a.title-animation-").text()
                            : "";
                    String link = el.selectFirst("a.title-animation-") != null
                            ? el.selectFirst("a.title-animation-").attr("href")
                            : "";
                    if (manchete.isBlank() || link.isBlank()) {
                        log.warn("Notícia ignorada por dados inválidos (titulo/link vazio)");
                        continue;
                    }
                    Noticia n = new Noticia();
                    n.setTitulo(manchete);
                    n.setLink(link);
                    n.setFonte("Secom");
                    n.setOrgao("servico");
                    noticias.add(n);
                } catch (Exception e) {
                    log.error("Erro ao processar uma notícia do Secom", e);
                }
            }
        } catch (IOException e) {
            log.error("Erro ao conectar no site Secom - URL: {}", URL, e);
        } catch (Exception e) {
            log.error("Erro inesperado no scraper do Secom", e);
        }
        return noticias;
    }
}
