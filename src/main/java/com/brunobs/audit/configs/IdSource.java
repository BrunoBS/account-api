package com.brunobs.audit.configs;

/**
 * Define a origem do identificador para o processo de auditoria.
 */
public enum IdSource {
    /**
     * Extraído de variáveis de path da URL (ex: /users/{id})
     */
    PATH,

    /**
     * Extraído do corpo da requisição (Request Body)
     */
    BODY,

    /**
     * Extraído dos cabeçalhos da requisição (Request Headers)
     */
    HEADER,

    /**
     * Extraído do corpo da resposta (Response Body) - Útil para IDs gerados no banco
     */
    RESPONSE
}
