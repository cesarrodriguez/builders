package br.com.builders.domain.service;

import br.com.builders.domain.model.Cliente;
import br.com.builders.infra.mongo.repository.ClienteRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void quantoBuscarUmCliente_entaoRetornaUmCliente() {
        var cliente = new Cliente();
        cliente.setNome("Teste 1");
        cliente.setCpf("111.111.111-11");
        cliente.setDataNascimento(new Date());
        when(repository.findById("1234564")).thenReturn(Optional.of(cliente));
        var clienteDb = service.getClienteById("1234564");
        Assert.assertEquals(cliente, clienteDb.get());
        Assert.assertEquals(cliente.getDataNascimento(), clienteDb.get().getDataNascimento());
    }

    @Test
    public void quantoBuscarUmClienteENaoEncontrar_entaoRetornaNone() {
        when(repository.findById("1234564")).thenReturn(null);
        var clienteDb = service.getClienteById("1234564");
        Assert.assertNull(clienteDb);
    }
}
