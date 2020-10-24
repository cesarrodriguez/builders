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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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

    @Test
    public void quantoSalvarUmCliente_entaoRetornaObjetoComId() {
        var cliente = new Cliente();
        cliente.setNome("Teste 1");
        cliente.setCpf("111.111.111-11");
        cliente.setDataNascimento(new Date());

        var clienteComId = new Cliente();
        clienteComId.setId("123456789");
        clienteComId.setNome("Teste 1");
        clienteComId.setCpf("111.111.111-11");
        clienteComId.setDataNascimento(new Date());

        when(repository.save(any())).thenReturn(clienteComId);
        var clienteDb = service.upsertCliente(cliente);
        Assert.assertNotEquals(cliente, clienteDb);
        Assert.assertEquals(cliente.getCpf(), clienteDb.getCpf());
    }

    @Test
    public void quantoAtualizarUmCliente_entaoRetornaObjetoComDadoDiferente() {
        var cliente = new Cliente();
        cliente.setId("123456789");
        cliente.setNome("Teste 1");
        cliente.setCpf("111.111.111-11");
        cliente.setDataNascimento(new Date());

        var clienteComId = new Cliente();
        clienteComId.setId("123456789");
        clienteComId.setNome("Teste 2");
        clienteComId.setCpf("111.111.111-11");
        clienteComId.setDataNascimento(new Date());

        when(repository.save(cliente)).thenReturn(clienteComId);
        var clienteDb = service.upsertCliente(cliente);
        Assert.assertNotEquals(cliente, clienteDb);
        Assert.assertNotEquals(cliente.getNome(), clienteDb.getNome());
    }

    @Test
    public void quantoBuscarUmClientePorCpfeNome_entaoRetornaUmCliente() {
        var cliente = new Cliente();
        cliente.setId("1234564");
        cliente.setNome("Teste 1");
        cliente.setCpf("111.111.111-11");
        cliente.setDataNascimento(new Date());
        List<Cliente> list = new ArrayList<>();
        list.add(cliente);
        Page<Cliente> page = new PageImpl<Cliente>(list);
        when(repository.findByCpfAndNome(any(), any(), any())).thenReturn(page);
        var clienteDb = service.getClienteByCpfAndNome("1234564", "Teste 1",0,10);
        Assert.assertEquals(cliente, clienteDb.get().findFirst().get());
        Assert.assertEquals(cliente.getDataNascimento(), clienteDb.get().findFirst().get().getDataNascimento());
    }

}
