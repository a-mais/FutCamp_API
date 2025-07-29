package ifma.ppaulo.futcamp.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TabelaDTO {
    private Integer id;
    private Integer timeId;
    private String timeNome;
    private Integer campeonatoId;
    private String campeonatoNome;
    private Integer pontos;
    private Integer jogos;
    private Integer vitorias;
    private Integer empates;
    private Integer derrotas;
    private Integer golsPro;
    private Integer golsContra;
    private Integer saldoGols;
}
