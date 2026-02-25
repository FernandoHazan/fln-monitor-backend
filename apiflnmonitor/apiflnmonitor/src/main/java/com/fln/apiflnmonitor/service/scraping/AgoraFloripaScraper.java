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
public class AgoraFloripaScraper {
    private static final String URL = "https://www.agorafloripa.com.br/";
    private static final int TIMEOUT_MS = 10_000;
    private static final Logger log = LoggerFactory.getLogger(AgoraFloripaScraper.class);
    public List<Noticia> buscarNoticiasAgoraFloripa() {
        List<Noticia> noticias = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL)
                    .userAgent("news monitoring; fernandohvk4@gmail.com")
                    .timeout(TIMEOUT_MS)
                    .referrer("https://www.google.com")
                    .get();
            Elements elementos = doc.select("article.jeg_post");
            Elements noticia = elementos.select("h3.jeg_post_title");
            if (elementos.isEmpty()) {
                log.warn("Nenhuma notícia encontrada no AgoraFloripa — layout pode ter mudado");
            }
            for (Element el : noticia) {
                try {
                    String manchete = noticia.select("a") != null ? el.selectFirst("a").text() : "";
                    String link = noticia.select("h3.jeg_post_title") != null
                            ? el.selectFirst("a").attr("href")
                            : "";
                    if (manchete.isBlank() || link.isBlank()) {
                        log.warn("Notícia ignorada por dados inválidos (titulo/link vazio)");
                        continue;
                    }
                    Noticia n = new Noticia();
                    n.setTitulo(manchete);
                    n.setLink(link);
                    n.setFonte("Agora Floripa");
                    n.setOrgao("portal");
                    noticias.add(n);
                } catch (Exception e) {
                    log.error("Erro ao processar uma notícia do AgoraFloripa", e);
                }
            }
        } catch (IOException e) {
            log.error("Erro ao conectar no site AgoraFloripa - URL: {}", URL, e);
        } catch (Exception e) {
            log.error("Erro inesperado no scraper do AgoraFloripa", e);
        }
        return noticias;
    }
}
