package com.dev.tracing.controller;


import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Span;
import com.dev.tracing.service.TracingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TracingController {

    private final TracingService tracingService;

    private final Tracer tracer;

    public TracingController(TracingService tracingService, Tracer tracer) {
        this.tracingService = tracingService;
        this.tracer = tracer;
    }


    @GetMapping("/custom")
    public String doCustomTracing() {
        Span newSpan = tracer.nextSpan().name("custom-span").start();
        try (Tracer.SpanInScope scope = tracer.withSpan(newSpan)) {
            tracingService.sayHello();
            return "Traced manually!";
        } finally {
            newSpan.end();
        }
    }



}
