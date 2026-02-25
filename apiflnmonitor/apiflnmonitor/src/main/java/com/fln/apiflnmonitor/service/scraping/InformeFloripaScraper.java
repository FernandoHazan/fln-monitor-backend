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
public class InformeFloripaScraper {
    private static final String URL = "https://informefloripa.com/redacao/geral/";
    private static final int TIMEOUT_MS = 10_000;
    private static final Logger log = LoggerFactory.getLogger(InformeFloripaScraper.class);
    public List<Noticia> buscarNoticiasInformeFloripa() {
        List<Noticia> noticias = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL)
                    .userAgent("news monitoring; fernandohvk4@gmail.com")
                    .timeout(TIMEOUT_MS)
                    .referrer("https://www.google.com")
                    .get();
            Elements elementos = doc.select("#tdi_87");
            Elements noticia = elementos.select("h2.entry-title a, h3.entry-title a");
            if (elementos.isEmpty()) {
                log.warn("Nenhuma notícia encontrada no InformeFloripa — layout pode ter mudado");
            }
            for (Element el : noticia) {
                try {
                    String manchete = el.selectFirst("a") != null
                            ? el.selectFirst("a").text()
                            : "";
                    String link = el.selectFirst("a") != null
                            ? el.selectFirst("a").attr("href")
                            : "";
                    if (manchete.isBlank() || link.isBlank()) {
                        log.warn("Notícia ignorada por dados inválidos (titulo/link vazio)");
                        continue;
                    }
                    Noticia n = new Noticia();
                    n.setTitulo(manchete);
                    n.setLink(link);
                    n.setFonte("Informe Floripa");
                    n.setOrgao("portal");
                    noticias.add(n);
                } catch (Exception e) {
                    log.error("Erro ao processar uma notícia do InformeFloripa", e);
                }
            }
        } catch (IOException e) {
            log.error("Erro ao conectar no site InformeFloripa - URL: {}", URL, e);
        } catch (Exception e) {
            log.error("Erro inesperado no scraper do InformeFloripa", e);
        }
        return noticias;
    }
}
