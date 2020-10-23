package controller;

import br.com.builders.domain.model.Cliente;
import br.com.builders.domain.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Optional;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {br.com.builders.BuildersApplication.class})
@TestPropertySource("classpath:application.yml")
public class ClienteControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ClienteService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() throws IOException {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void quandoBuscaUmClienteQueNaoExiste_EntaoRetornsNoContent() throws Exception {
        when(service.getClienteById("1234564")).thenReturn(null);
        this.mockMvc.perform(get("/clientes/1234564")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void quandoBuscaUmClienteEEncontra_EntaoRetornsOk() throws Exception {
        var cliente = new Cliente();
        cliente.setNome("Teste 1");
        cliente.setCpf("111.111.111-11");
        cliente.setDataNascimento(new Date());
        when(service.getClienteById("1234564")).thenReturn(Optional.of(cliente));
        this.mockMvc.perform(get("/clientes/1234564")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.nome").value("Teste 1"));
    }

    @Test
    public void quandoCriarUmCliente_EntaoRetornarObjetoComId() throws Exception {
        var cliente = new Cliente();
        cliente.setId("1234564");
        cliente.setNome("Teste 1");
        cliente.setCpf("111.111.111-11");
        cliente.setDataNascimento(new Date());

        when(service.upsertCliente(any())).thenReturn(cliente);
        this.mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(cliente)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value("1234564"));
    }

    @Test
    public void quandoAtualizarUmCliente_EntaoRetornarOk() throws Exception {
        var cliente = new Cliente();
        cliente.setId("1234564");
        cliente.setNome("Teste 2");
        cliente.setCpf("111.111.111-11");
        cliente.setDataNascimento(new Date());

        when(service.upsertCliente(any())).thenReturn(cliente);
        this.mockMvc.perform(put("/clientes/1234564")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(cliente)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.nome").value("Teste 2"));
    }

    @Test
    public void quandoAtualizarParteDoCliente_EntaoRetornarOk() throws Exception {
        var cliente = new Cliente();
        cliente.setId("1234564");
        cliente.setNome("Teste 3");
        cliente.setDataNascimento(new Date());

        when(service.upsertCliente(any())).thenReturn(cliente);
        this.mockMvc.perform(patch("/clientes/1234564")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(cliente)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.nome").value("Teste 3"));
    }

    @Test
    public void quandoAtualizarParteDoClienteNaoDesejado_EntaoRetornarErro() throws Exception {
        var cliente = new Cliente();
        cliente.setDataNascimento(new Date());
        when(service.upsertCliente(any())).thenReturn(cliente);
        this.mockMvc.perform(patch("/clientes/1234564")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(cliente)))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }
}
