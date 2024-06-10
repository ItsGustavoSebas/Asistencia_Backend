package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private AsistenciaService asistenciaService;

    @Scheduled(cron = "0 0 0 * * ?") // Ejecución diaria a medianoche
    public void scheduleDailyAttendanceGeneration() {
        asistenciaService.generarAsistenciasDiarias();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        asistenciaService.generarAsistenciasDiarias();
    }
}
