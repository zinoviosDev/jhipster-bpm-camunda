package fr.ippon.jhipster.bpm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.SignalEventReceivedBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/bpm")
public class BPMResource {

    private final RuntimeService runtimeService;

    public BPMResource(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @PostMapping("/signals")
    @Timed
    public ResponseEntity<Void> sendSignal(@RequestBody String signal) {
        SignalEventReceivedBuilder signalEvent = runtimeService.createSignalEvent(signal);
        signalEvent.setVariables(ImmutableMap.of("date", LocalDateTime.now()));
        signalEvent.send();

        return ResponseEntity.noContent().build();
    }
}
