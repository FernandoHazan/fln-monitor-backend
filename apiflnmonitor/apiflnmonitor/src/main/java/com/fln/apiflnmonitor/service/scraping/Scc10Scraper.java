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
public class Scc10Scraper {

    private static final String URL = "https://scc10.com.br/ultimas-noticias/";
    private static final int TIMEOUT_MS = 10_000;

    private static final Logger log = LoggerFactory.getLogger(Scc10Scraper.class);

    public List<Noticia> buscarNoticiasScc10() {
        List<Noticia> noticias = new ArrayList<>();

        log.info("Iniciando scraping do SCC10");

        try {
            Document doc = Jsoup.connect(URL)
                    .userAgent("news monitoring; fernandohvk4@gmail.com")
                    .timeout(TIMEOUT_MS)
                    .referrer("https://www.google.com")
                    .get();

            Elements elementos = doc.select("section.article");

            if (elementos.isEmpty()) {
                log.warn("Nenhuma notícia encontrada no SCC10 — layout pode ter mudado");
            }

            log.info("Encontradas {} notícias no SCC10", elementos.size());

            for (Element el : elementos) {
                try {
                    String manchete = el.selectFirst("h2.article__title") != null
                            ? el.selectFirst("h2.article__title").text()
                            : "";

                    String link = el.selectFirst("a.article__tiny") != null
                            ? el.selectFirst("a.article__tiny").attr("href")
                            : "";

                    String conteudo = el.selectFirst("p.article__content") != null
                            ? el.selectFirst("p.article__content").text()
                            : "";

                    String tipo = el.selectFirst("a.compartilhar__link") != null
                            ? el.selectFirst("a.compartilhar__link").text()
                            : "N/A";

                    if (manchete.isBlank() || link.isBlank()) {
                        log.warn("Notícia ignorada por dados inválidos (titulo/link vazio)");
                        continue;
                    }

                    Noticia n = new Noticia();
                    n.setTitulo(manchete);
                    n.setLink(link);
                    n.setConteudo(conteudo);
                    n.setFonte("Scc10");
                    n.setTipo(tipo);

                    noticias.add(n);

                } catch (Exception e) {
                    log.error("Erro ao processar uma notícia do SCC10", e);
                }
            }

        } catch (IOException e) {
            log.error("Erro ao conectar no site SCC10 - URL: {}", URL, e);
        } catch (Exception e) {
            log.error("Erro inesperado no scraper do SCC10", e);
        }

        log.info("Scraping do SCC10 finalizado. Total de notícias válidas: {}", noticias.size());

        return noticias;
    }
}
