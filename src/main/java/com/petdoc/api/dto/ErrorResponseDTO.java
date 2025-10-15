package com.petdoc.api.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO padrão para respostas de erro da API
 */
public record ErrorResponseDTO(
    LocalDateTime timestamp,
    int status,
    String erro,
    String mensagem,
    String path,
    List<String> detalhes
) {
    public ErrorResponseDTO(int status, String erro, String mensagem, String path) {
        this(LocalDateTime.now(), status, erro, mensagem, path, null);
    }
    
    public ErrorResponseDTO(int status, String erro, String mensagem, String path, List<String> detalhes) {
        this(LocalDateTime.now(), status, erro, mensagem, path, detalhes);
    }
}
