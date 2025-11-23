package com.auric.entertainment.auric_backend.service;


import com.auric.entertainment.auric_backend.config.WhatsappProperties;
import com.auric.entertainment.auric_backend.entity.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class WhatsappNotificationService {

    private final WhatsappProperties props;

    private WebClient client() {
        return WebClient.builder()
                .baseUrl(props.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + props.getAccessToken())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void sendBookingAlert(Booking booking) {
        try {
            var event = booking.getEvent();
            var tt = booking.getTicketType();

            String dateLabel = booking.getCreatedAt() != null
                    ? booking.getCreatedAt().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    : "";

            String text = """
                New Auric booking request 🎟️

                Event: %s (ID: %d)
                Date: %s

                Ticket: %s (RM %.2f)
                Quantity: %d

                Name: %s
                Email: %s
                Phone: %s
                """.formatted(
                    event.getTitle(),
                    event.getId(),
                    dateLabel,
                    tt != null ? tt.getName() : "N/A",
                    tt != null ? tt.getPrice() : 0.0,
                    booking.getTickets(),
                    booking.getFullName(),
                    booking.getEmail() != null ? booking.getEmail() : "-",
                    booking.getPhone() != null ? booking.getPhone() : "-"
            );

            // JSON payload (same idea as before)
            String payload = """
                {
                  "messaging_product": "whatsapp",
                  "to": "%s",
                  "type": "text",
                  "text": { "preview_url": false, "body": %s }
                }
                """.formatted(
                    props.getTargetNumber(),   // e.g. 60189545304 (NO + sign)
                    toJsonString(text)
            );

            client()
                    .post()
                    .uri("/" + props.getPhoneNumberId() + "/messages")
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(
                            status -> !status.is2xxSuccessful(),
                            resp -> resp.bodyToMono(String.class)
                                    .doOnNext(body ->
                                            log.error("WhatsApp API error ({}): {}", resp.statusCode(), body)
                                    )
                                    .then(Mono.empty())   // swallow error, don't throw
                    )
                    .bodyToMono(String.class)
                    .doOnNext(resp -> log.info("WhatsApp sent ok: {}", resp))
                    .doOnError(ex -> log.error("Failed to send WhatsApp alert", ex))
                    .block();
        } catch (Exception e) {
            log.error("Error while preparing WhatsApp booking alert", e);
        }
    }

    private String toJsonString(String raw) {
        // super simple escape for quotes/newlines
        return "\"" + raw
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n") + "\"";
    }
}
