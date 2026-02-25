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
public class JornalRazaoScraper {
    private static final String URL = "https://jornalrazao.com/ultimas-noticias/";
    private static final int TIMEOUT_MS = 10_000;
    private static final Logger log = LoggerFactory.getLogger(JornalRazaoScraper.class);
    public List<Noticia> buscarNoticiasJornalRazao() {
        List<Noticia> noticias = new ArrayList<>();
        log.info("Iniciando scraping do Jornal Razão");
        try {
            Document doc = Jsoup.connect(URL)
                    .userAgent("news monitoring; fernandohvk4@gmail.com")
                    .timeout(TIMEOUT_MS)
                    .referrer("https://www.google.com")
                    .get();
            Elements elementos = doc.select("div[data-elementor-type=loop-item]");
            if (elementos.isEmpty()) {
                log.warn("Nenhuma notícia encontrada no Jornal Razão — layout pode ter mudado");
            }
            log.info("Encontradas {} notícias no Jornal Razão", elementos.size());
            for (Element el : elementos) {
                try {
                    Element tituloEl = el.selectFirst("h2.elementor-heading-title a");
                    if (tituloEl == null) {
                        log.warn("Notícia ignorada: título não encontrado");
                        continue;
                    }
                    String manchete = tituloEl.text().trim();
                    String link = tituloEl.attr("href").trim();
                    if (manchete.isBlank() || link.isBlank()) {
                        log.warn("Notícia ignorada por dados inválidos (titulo/link vazio)");
                        continue;
                    }
                    Noticia n = new Noticia();
                    n.setTitulo(manchete);
                    n.setLink(link);
                    n.setFonte("Jornal Razão");
                    n.setOrgao("portal");
                    noticias.add(n);
                } catch (Exception e) {
                    log.error("Erro ao processar uma notícia do Jornal Razão", e);
                }
            }
        } catch (IOException e) {
            log.error("Erro ao conectar no site Jornal Razão - URL: {}", URL, e);
        } catch (Exception e) {
            log.error("Erro inesperado no scraper do Jornal Razão", e);
        }
        log.info("Scraping do Jornal Razão finalizado. Total de notícias válidas: {}", noticias.size());
        return noticias;
    }
}
