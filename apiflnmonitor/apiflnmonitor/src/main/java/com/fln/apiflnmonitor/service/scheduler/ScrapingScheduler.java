package com.fln.apiflnmonitor.service.scheduler;

import com.fln.apiflnmonitor.service.ScrapingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ScrapingScheduler {

    private static final Logger log = LoggerFactory.getLogger(ScrapingScheduler.class);

    private final ScrapingService scrapingService;
    private final AtomicBoolean executando = new AtomicBoolean(false);

    public ScrapingScheduler(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    // A cada 10 minutos
    @Scheduled(cron = "0 */10 * * * *")
    public void executarScraping() {

        if (!executando.compareAndSet(false, true)) {
          //  log.warn("⏳ Scraping já em execução, ignorando nova chamada");
            return;
        }

        // log.info("🔄 Iniciando scraping automático...");

        try {
            executarFonte("SCC10", scrapingService::importarScc10);
            executarFonte("NSC Total", scrapingService::importarNsctotal);
            executarFonte("ND", scrapingService::importarNd);
            executarFonte("Jornal Razao", scrapingService::importarJornalRazao);
            executarFonte("Secom", scrapingService::importarSecom);
            executarFonte("Agora Floripa", scrapingService::importarAgoraFloripa);
            executarFonte("Informe Floripa", scrapingService::importarInformeFloripa);

        } catch (Exception e) {
            log.error("❌ Erro inesperado no scheduler de scraping", e);

        } finally {
            executando.set(false);
           // log.info("✅ Scraping finalizado");
        }
    }

    private void executarFonte(String nome, Runnable tarefa) {
        try {
            tarefa.run();
        } catch (Exception e) {
            log.error("❌ Erro ao executar scraping da fonte {}", nome, e);
        }
    }
}

