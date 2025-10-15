package com.petdoc.api.dto;

/**
 * DTO para respostas de login via API REST
 */
public record LoginResponseDTO(
    boolean sucesso,
    String mensagem,
    String redirectUrl,
    DadosUsuarioDTO usuario
) {
    public record DadosUsuarioDTO(
        String nome,
        String email
    ) {}
}
