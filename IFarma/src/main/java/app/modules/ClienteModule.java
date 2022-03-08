package app.modules;

import java.util.ArrayList;

import app.Main;
import core.interfaces.ShowObjectInterface;
import core.models.Cliente;
import core.models.ServiceResponse;
import core.services.ClienteService;
import core.utils.DateUtils;
import core.utils.InputUtils;
import core.utils.ModuleUtils;
import core.utils.StringUtils;

public class ClienteModule {
  private static ShowObjectInterface<Cliente> iteratorMiddleware = (item) -> printObj(item);

  public static void list() {
    ServiceResponse<ArrayList<Cliente>> response = new ClienteService().list();
    treatList(response);
  }

  private static ServiceResponse<ArrayList<Cliente>> listWithReturn() {
    ServiceResponse<ArrayList<Cliente>> response = new ClienteService().list();
    treatList(response);
    return response;
  }

  private static void treatList(ServiceResponse<ArrayList<Cliente>> response) {
    if (response.hasError()) {
      System.out.println(response.getError());
      return;
    } else if (response.data.isEmpty()) {
      System.out.println("Não há nenhum cliente cadastrado.");
      return;
    }
    System.out.println("\nLista de clientes:\n");
    ModuleUtils.<Cliente>iterateObjList(response.data, iteratorMiddleware);
  }

  public static void find() {
    ServiceResponse<Cliente> response = new ClienteService().find(1);

    if (response.hasError()) {
      System.out.println(response.getError());
      return;
    }

    iteratorMiddleware.show(response.data);
  }

  public static void create() {
    Cliente cliente = inputCliente();

    ServiceResponse<Boolean> response = new ClienteService().create(cliente);
    if (response.hasError()) {
      System.out.println(response.getError());
    } else if (response.data) {
      System.out.println("Cliente cadastrado com sucesso!");
    }
  }

  public static void delete() {
    ServiceResponse<ArrayList<Cliente>> clienteListResponse = listWithReturn();
    if (clienteListResponse.hasError() || clienteListResponse.data.isEmpty()) {
      return;
    }
    ArrayList<Integer> clienteIds = getClientIds(clienteListResponse.data);
    int idSelecionado = 0;
    while (idSelecionado == 0 || !clienteIds.contains(idSelecionado)) {
      System.out.print("Digite o código do cliente que deseja excluir: ");
      idSelecionado = Integer.parseInt(Main.getScanner().nextLine());
      if (!clienteIds.contains(idSelecionado)) {
        System.out.println("Você deve selecionar um código de cliente existente!");
      }
    }

    ServiceResponse<Boolean> response = new ClienteService().delete(idSelecionado);

    if (response.hasError()) {
      System.out.println(response.getError());
      return;
    }

    System.out.println("Cliente excluído com sucesso!");
  }

  private static ArrayList<Integer> getClientIds(ArrayList<Cliente> clientes) {
    ArrayList<Integer> ids = new ArrayList<>();
    for (Cliente cliente : clientes) {
      ids.add(cliente.getCodigo());
    }
    return ids;
  }

  private static void printObj(Cliente cliente) {
    System.out.println("Cliente: " + cliente.getNome());
    System.out.println("\tCódigo: " + cliente.getCodigo());
    System.out.println("\tCPF: " + StringUtils.maskCPF(cliente.getCpf()));
    System.out.println("\tData de Nascimento: " + DateUtils.formatToBR(cliente.getDataNascimento()));
    System.out.println("\tEndereço: " + cliente.getEndereco());
  }

  private static Cliente inputCliente() {
    System.out.println("Qual o nome do cliente?");
    String nome = InputUtils.inputString(true);
    System.out.println("Qual a data de nascimento do cliente?");
    String dataNascimento = DateUtils.formatToUS(InputUtils.inputString("date", true));
    System.out.println("Qual o endereço do cliente?");
    String endereco = InputUtils.inputString(true);
    System.out.println("Qual o CPF do cliente?");
    String cpf = StringUtils.removeCPFSymbols(InputUtils.inputString("cpf", true));
    return new Cliente(nome, cpf, dataNascimento, endereco);
  }
}
