package com.tfg.eventos.servicio;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.jwt-secret}")
    private String jwtSecret;

    @Value("${supabase.storage.bucket}")
    private String bucket;

    private final RestClient restClient = RestClient.create();

    private String buildServiceRoleJwt() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claim("role", "service_role")
                .issuer("supabase")
                .issuedAt(new Date(now))
                .expiration(new Date(now + 1000L * 60 * 60 * 24 * 365))
                .signWith(key)
                .compact();
    }

    public String subirImagen(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }

        String extension = "";
        String originalName = archivo.getOriginalFilename();
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf('.'));
        }

        String nombreArchivo = UUID.randomUUID() + extension;
        String uploadUrl = supabaseUrl + "/storage/v1/object/" + bucket + "/" + nombreArchivo;

        try {
            restClient.post()
                    .uri(uploadUrl)
                    .header("Authorization", "Bearer " + buildServiceRoleJwt())
                    .contentType(MediaType.parseMediaType(
                            archivo.getContentType() != null ? archivo.getContentType() : "image/jpeg"))
                    .body(archivo.getBytes())
                    .retrieve()
                    .toBodilessEntity();

            return supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + nombreArchivo;

        } catch (IOException e) {
            throw new RuntimeException("No se pudo subir la imagen a Supabase Storage", e);
        }
    }
}
